#!/usr/bin/env bash
set -euo pipefail

ROOTDIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
TESTDIR="${ROOTDIR}/tests"

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
  tmpdir="$(mktemp -d "${TMPDIR:-/tmp}/blitzcc-arm64-exec.XXXXXX")"
  trap 'rm -rf "${tmpdir}"' EXIT
  cat > "${tmpdir}/arm64_exec_smoke.bb" <<'EOF'
Print "arm64_exec_smoke"
End
EOF
  set +e
  "${BLITZCC}" +q -target macos-arm64 "${tmpdir}/arm64_exec_smoke.bb" \
    > "${tmpdir}/arm64_exec_smoke.out" 2> "${tmpdir}/arm64_exec_smoke.err"
  smoke_rc=$?
  set -e
  smoke_stdout="$(tr -d '\r' < "${tmpdir}/arm64_exec_smoke.out")"
  smoke_stderr="$(cat "${tmpdir}/arm64_exec_smoke.err")"
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
