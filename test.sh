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
  TEST_MODE_DESC="compile-only macOS arm64 alpha validation"
else
  TARGET_FLAG=(-target host)
  EXEC_FLAG=()
  TEST_MODE_DESC="host test execution"
fi

FAILED=0
echo "Running ${TEST_MODE_DESC} across ${TESTDIR}"
while IFS= read -r -d '' f; do
  if ! "${BLITZCC}" "${TARGET_FLAG[@]}" "${EXEC_FLAG[@]}" -t "${f}"; then
    echo "\"${f}\" failed at least one test"
    FAILED=1
  fi
  echo
done < <(find "${TESTDIR}" -type f -name '*.bb' -print0)

if [[ "$(uname -s)" == "Darwin" ]]; then
  echo "Skipping native macOS arm64 execution smoke: runtime execution is not implemented yet."
fi

if [[ "${FAILED}" -eq 1 ]]; then
  echo "Tests failed"
  exit 1
fi

echo "Tests passed"
