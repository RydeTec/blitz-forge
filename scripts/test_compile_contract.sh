#!/usr/bin/env bash
set -euo pipefail

ROOTDIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
source "${ROOTDIR}/compile.sh"

fail() {
  echo "FAIL: $*" >&2
  exit 1
}

assert_eq() {
  local expected="$1"
  local actual="$2"
  local label="$3"
  if [[ "${expected}" != "${actual}" ]]; then
    fail "${label}: expected '${expected}', got '${actual}'"
  fi
}

assert_success() {
  local label="$1"
  shift
  if ! "$@"; then
    fail "${label}: command failed"
  fi
}

assert_failure_with_stderr() {
  local expected="$1"
  shift
  local stderr_file
  stderr_file="$(mktemp "${TMPDIR:-/tmp}/compile-contract.XXXXXX")"
  if "$@" > /dev/null 2> "${stderr_file}"; then
    rm -f "${stderr_file}"
    fail "expected failure but command succeeded"
  fi
  if ! grep -Fq "${expected}" "${stderr_file}"; then
    cat "${stderr_file}" >&2
    rm -f "${stderr_file}"
    fail "stderr did not contain '${expected}'"
  fi
  rm -f "${stderr_file}"
}

BLITZFORGE_BUILD_HOST_SYSTEM=Darwin
assert_eq "Darwin" "$(host_system)" "Darwin override"
assert_success "Darwin host is allowed" require_supported_build_host

BLITZFORGE_BUILD_HOST_SYSTEM=Linux
assert_eq "Linux" "$(host_system)" "Linux override"
assert_failure_with_stderr "runnable host builds only on macOS" require_supported_build_host

echo "compile.sh contract tests passed"
