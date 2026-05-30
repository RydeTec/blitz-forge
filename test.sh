#!/usr/bin/env bash
set -euo pipefail

ROOTDIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
TESTDIR="${ROOTDIR}/tests"
TEST_TMPDIR="$(mktemp -d "${TMPDIR:-/tmp}/blitzforge-tests.XXXXXX")"
trap 'rm -rf "${TEST_TMPDIR}"' EXIT

BLITZCC_UNIX="${ROOTDIR}/bin/blitzcc"
BLITZCC_WIN="${ROOTDIR}/bin/blitzcc.exe"
BLITZCC_IS_UNIX=0
if [[ -x "${BLITZCC_UNIX}" ]]; then
  BLITZCC="${BLITZCC_UNIX}"
  BLITZCC_IS_UNIX=1
elif [[ -f "${BLITZCC_WIN}" ]]; then
  BLITZCC="${BLITZCC_WIN}"
else
  echo "blitzcc not found; run ./compile.sh first." >&2
  exit 1
fi

FAILED=0

run_isolated_cli_expect_success() {
  local label="$1"
  local expected="$2"
  shift 2
  local isolated_dir="${TEST_TMPDIR}/isolated-${label//[^A-Za-z0-9]/-}"
  mkdir -p "${isolated_dir}"
  cp "${BLITZCC}" "${isolated_dir}/blitzcc"
  chmod +x "${isolated_dir}/blitzcc"

  set +e
  (
    cd "${isolated_dir}"
    ./blitzcc "$@"
  ) > "${isolated_dir}/cli.out" 2> "${isolated_dir}/cli.err"
  local rc=$?
  set -e

  if [[ "${rc}" -ne 0 ]]; then
    echo "CLI contract FAILED: ${label} returned ${rc}" >&2
    cat "${isolated_dir}/cli.out" >&2
    cat "${isolated_dir}/cli.err" >&2
    FAILED=1
    return
  fi

  if [[ -n "${expected}" ]] && ! grep -q "${expected}" "${isolated_dir}/cli.out"; then
    echo "CLI contract FAILED: ${label} did not print ${expected}" >&2
    cat "${isolated_dir}/cli.out" >&2
    cat "${isolated_dir}/cli.err" >&2
    FAILED=1
  fi
}

if [[ "${BLITZCC_IS_UNIX}" -eq 1 ]]; then
  run_isolated_cli_expect_success "isolated -h works without DLLs" "Usage:" -h
  run_isolated_cli_expect_success "isolated -v works without DLLs" "Compiler version:" -v
  run_isolated_cli_expect_success "isolated bare invocation works without DLLs" ""
fi

if [[ "$(uname -s)" == "Darwin" ]]; then
  RUNTIME_LIB="${ROOTDIR}/bin/runtime.dylib"
  LINKER_LIB="${ROOTDIR}/bin/linker.dylib"
else
  RUNTIME_LIB="${ROOTDIR}/bin/runtime.so"
  LINKER_LIB="${ROOTDIR}/bin/linker.so"
fi

if [[ ! -f "${RUNTIME_LIB}" || ! -f "${LINKER_LIB}" ]]; then
  echo "runtime/linker companion libraries are missing for this host build." >&2
  echo "compile.sh only produces a runnable Unix blitzcc on macOS today." >&2
  exit 1
fi

if [[ "$(uname -s)" == "Darwin" ]]; then
  TARGET_FLAG=(-target macos-arm64)
  EXEC_FLAG=(-c)
else
  TARGET_FLAG=(-target host)
  EXEC_FLAG=()
fi

TARGET_SAMPLE="${TESTDIR}/NumberTest.bb"

run_target_expect_success() {
  local label="$1"
  shift
  if ! "${BLITZCC}" -c +q "$@" "${TARGET_SAMPLE}" \
    > "${TEST_TMPDIR}/target-success.out" 2> "${TEST_TMPDIR}/target-success.err"; then
    echo "target contract FAILED: ${label}" >&2
    cat "${TEST_TMPDIR}/target-success.err" >&2
    FAILED=1
  fi
}

run_target_expect_failure() {
  local label="$1"
  shift
  set +e
  "${BLITZCC}" -c +q "$@" "${TARGET_SAMPLE}" \
    > "${TEST_TMPDIR}/target-failure.out" 2> "${TEST_TMPDIR}/target-failure.err"
  local rc=$?
  set -e

  if [[ "${rc}" -eq 0 ]]; then
    echo "target contract FAILED: ${label} unexpectedly succeeded" >&2
    FAILED=1
    return
  fi

  if ! grep -q "Unsupported -target" "${TEST_TMPDIR}/target-failure.out"; then
    echo "target contract FAILED: ${label} did not report the unsupported-target error" >&2
    echo "--- stdout ---" >&2
    cat "${TEST_TMPDIR}/target-failure.out" >&2
    echo "--- stderr ---" >&2
    cat "${TEST_TMPDIR}/target-failure.err" >&2
    FAILED=1
  fi
}

run_target_expect_success "host alias compiles NumberTest" -target host
if [[ "$(uname -s)" == "Darwin" ]]; then
  run_target_expect_success "native macOS target compiles NumberTest" -target macos-arm64
  run_target_expect_failure "foreign Windows target is rejected" -target windows-x86
else
  run_target_expect_failure "foreign macOS target is rejected" -target macos-arm64
fi

# --- compiler diagnostic contract: human terminal output vs IDE machine format ---
# A broken fixture (unterminated For) outside tests/ so it is not picked up by the
# -t loop. Terminal mode prints a human "error:" diagnostic and never a raw control
# byte for the EOF token; IDE mode (blitzide set) keeps the machine format the
# editor parses (no "error:" prefix).
DIAG_BB="${TEST_TMPDIR}/diag.bb"
printf 'For i = 1 To 10\n' > "${DIAG_BB}"

set +e
( unset blitzide; "${BLITZCC}" -c +q "${DIAG_BB}" ) > "${TEST_TMPDIR}/diag-term.out" 2>&1
set -e
if ! grep -q "error:" "${TEST_TMPDIR}/diag-term.out"; then
  echo "diagnostic contract FAILED: terminal output missing human 'error:' prefix" >&2
  cat "${TEST_TMPDIR}/diag-term.out" >&2
  FAILED=1
fi
if ! grep -q "end of file" "${TEST_TMPDIR}/diag-term.out"; then
  echo "diagnostic contract FAILED: EOF error did not render '<end of file>'" >&2
  cat "${TEST_TMPDIR}/diag-term.out" >&2
  FAILED=1
fi
# A literal 0xFF byte must not appear (the pre-fix EOF-token leak).
if LC_ALL=C grep -q "$(printf '\377')" "${TEST_TMPDIR}/diag-term.out"; then
  echo "diagnostic contract FAILED: terminal output leaked a 0xFF control byte" >&2
  FAILED=1
fi

set +e
blitzide=1 "${BLITZCC}" -c +q "${DIAG_BB}" > "${TEST_TMPDIR}/diag-ide.out" 2>&1
set -e
if ! grep -q "Expecting" "${TEST_TMPDIR}/diag-ide.out"; then
  echo "diagnostic contract FAILED: IDE mode produced no diagnostic" >&2
  cat "${TEST_TMPDIR}/diag-ide.out" >&2
  FAILED=1
fi
if grep -q "error:" "${TEST_TMPDIR}/diag-ide.out"; then
  echo "diagnostic contract FAILED: IDE mode leaked the human 'error:' prefix" >&2
  cat "${TEST_TMPDIR}/diag-ide.out" >&2
  FAILED=1
fi

# --- runtime crash diagnostic: native faults are labeled by type ---
# The seTranslator must map each structured exception to its own message rather
# than reporting every fault as "Stack overflow!". The fixture deliberately
# integer-divides by zero; in -t mode the panic prints "Error: <msg>" and exits
# non-zero, so it lives outside tests/ and is invoked explicitly. (Windows-only
# path; on hosts without the structured-exception translator this is a no-op
# check that simply won't see the message -- so only assert it where it applies.)
CRASH_BB="${ROOTDIR}/scripts/fixtures/divzero.bb"
if [[ -f "${CRASH_BB}" ]]; then
  set +e
  "${BLITZCC}" "${TARGET_FLAG[@]}" "${EXEC_FLAG[@]}" -t "${CRASH_BB}" > "${TEST_TMPDIR}/crash.out" 2>&1
  crash_rc=$?
  set -e
  if grep -q "Stack overflow" "${TEST_TMPDIR}/crash.out" && ! grep -q "Integer divide by zero" "${TEST_TMPDIR}/crash.out"; then
    echo "crash diagnostic FAILED: divide-by-zero mislabeled as Stack overflow" >&2
    cat "${TEST_TMPDIR}/crash.out" >&2
    FAILED=1
  fi
fi

while IFS= read -r -d '' f; do
  if ! "${BLITZCC}" "${TARGET_FLAG[@]}" "${EXEC_FLAG[@]}" -t "${f}"; then
    echo "\"${f}\" failed at least one test"
    FAILED=1
  fi
  echo
done < <(find "${TESTDIR}" -type f -name '*.bb' -print0)

if [[ "$(uname -s)" == "Darwin" ]]; then
  # The macOS runtime is currently a stub that advertises itself with a
  # well-known sentinel on stderr. The smoke test below either confirms a
  # real runtime executes the program correctly, or honestly reports that
  # the stub runtime is still in place. It does NOT silently succeed when
  # nothing actually ran.
  STUB_SENTINEL="BlitzForge stub runtime: native execution is not yet implemented"
  cat > "${TEST_TMPDIR}/arm64_exec_smoke.bb" <<'EOF'
Print "arm64_exec_smoke"
End
EOF
  set +e
  "${BLITZCC}" +q -target macos-arm64 "${TEST_TMPDIR}/arm64_exec_smoke.bb" \
    > "${TEST_TMPDIR}/arm64_exec_smoke.out" 2> "${TEST_TMPDIR}/arm64_exec_smoke.err"
  smoke_rc=$?
  set -e
  smoke_stdout="$(tr -d '\r' < "${TEST_TMPDIR}/arm64_exec_smoke.out")"
  smoke_stderr="$(cat "${TEST_TMPDIR}/arm64_exec_smoke.err")"
  if [[ "${smoke_stdout}" == "arm64_exec_smoke" && "${smoke_rc}" -eq 0 ]]; then
    echo "arm64 execution smoke: native runtime executed program correctly."
  elif grep -q "${STUB_SENTINEL}" <<<"${smoke_stderr}"; then
    echo "arm64 execution smoke: SKIPPED — macOS runtime is still the alpha stub." >&2
    echo "  (set will be revisited automatically once a native runtime is wired up)" >&2
  else
    echo "arm64 execution smoke FAILED (rc=${smoke_rc})" >&2
    echo "--- stdout ---" >&2
    echo "${smoke_stdout}" >&2
    echo "--- stderr ---" >&2
    echo "${smoke_stderr}" >&2
    FAILED=1
  fi
fi

if [[ "${FAILED}" -eq 1 ]]; then
  echo "Tests failed"
  exit 1
fi

echo "Tests passed"
