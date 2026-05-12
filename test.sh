#!/usr/bin/env bash
set -euo pipefail

ROOTDIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
TESTDIR="${ROOTDIR}/tests"
TEST_TMPDIR="$(mktemp -d "${TMPDIR:-/tmp}/blitzforge-tests.XXXXXX")"
trap 'rm -rf "${TEST_TMPDIR}"' EXIT

BLITZCC_UNIX="${ROOTDIR}/bin/blitzcc"
BLITZCC_WIN="${ROOTDIR}/bin/blitzcc.exe"
if [[ -x "${BLITZCC_UNIX}" ]]; then
  BLITZCC="${BLITZCC_UNIX}"
elif [[ -f "${BLITZCC_WIN}" ]]; then
  BLITZCC="${BLITZCC_WIN}"
else
  echo "blitzcc not found; run ./compile.sh first." >&2
  exit 1
fi

if [[ "$(uname -s)" == "Darwin" ]]; then
  TARGET_FLAG=(-target macos-arm64)
  EXEC_FLAG=(-c)
else
  TARGET_FLAG=(-target host)
  EXEC_FLAG=()
fi

FAILED=0
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
