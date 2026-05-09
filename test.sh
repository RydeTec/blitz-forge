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
  tmpdir="$(mktemp -d "${TMPDIR:-/tmp}/blitzcc-arm64-exec.XXXXXX")"
  trap 'rm -rf "${tmpdir}"' EXIT
  cat > "${tmpdir}/arm64_exec_smoke.bb" <<'EOF'
Print "arm64_exec_smoke"
End
EOF
  if ! "${BLITZCC}" +q -target macos-arm64 "${tmpdir}/arm64_exec_smoke.bb" > "${tmpdir}/arm64_exec_smoke.out" 2>&1; then
    echo "arm64 execution smoke failed: blitzcc returned non-zero status" >&2
    FAILED=1
  elif [[ "$(tr -d '\r' < "${tmpdir}/arm64_exec_smoke.out")" != "arm64_exec_smoke" ]]; then
    echo "arm64 execution smoke failed: unexpected output" >&2
    FAILED=1
  fi
fi

if [[ "${FAILED}" -eq 1 ]]; then
  echo "Tests failed"
  exit 1
fi

echo "Tests passed"
