#!/usr/bin/env bash
set -euo pipefail

ROOTDIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
BUILD_RUNTIME=0

host_system() {
  if [[ -n "${BLITZFORGE_BUILD_HOST_SYSTEM:-}" ]]; then
    printf '%s\n' "${BLITZFORGE_BUILD_HOST_SYSTEM}"
    return
  fi

  uname -s
}

require_supported_build_host() {
  local system_name="${1:-$(host_system)}"

  case "${system_name}" in
    Darwin|MINGW*|MSYS*|CYGWIN*) return 0 ;;
    Linux)
      echo "compile.sh currently supports runnable host builds only on macOS." >&2
      echo "Linux builds still lack the companion runtime/linker shared libraries blitzcc loads at startup." >&2
      return 1
      ;;
  esac

  return 0
}

usage() {
  cat <<'EOF'
BlitzForge Compiler Script

-r | --with-runtime   Build runtime/linker shared libraries for host-target macOS support
-h | --help           Show this help

Note: runnable Unix host builds are currently supported only on macOS.
EOF
}

main() {
  while [[ $# -gt 0 ]]; do
    case "$1" in
      -r|--with-runtime) BUILD_RUNTIME=1 ;;
      -h|--help) usage; exit 0 ;;
      *) echo "Unknown flag: $1" >&2; usage; exit 1 ;;
    esac
    shift
  done

  HOST_SYSTEM="$(host_system)"

  if [[ "${HOST_SYSTEM}" == "Darwin" && "${BUILD_RUNTIME}" -eq 0 ]]; then
    # macOS host builds need the companion runtime/linker libraries for compiler loading.
    BUILD_RUNTIME=1
  fi

  if [[ "${HOST_SYSTEM}" == "Darwin" || "${HOST_SYSTEM}" == "Linux" ]]; then
    require_supported_build_host "${HOST_SYSTEM}"

    if ! command -v cmake >/dev/null 2>&1; then
      if [[ "${HOST_SYSTEM}" == "Darwin" && -x "${ROOTDIR}/scripts/bootstrap_macos.sh" ]]; then
        echo "cmake not found, running macOS dependency bootstrap..."
        "${ROOTDIR}/scripts/bootstrap_macos.sh"
      else
        echo "cmake is required. Install cmake (and optionally ninja), then re-run." >&2
        exit 1
      fi
    fi
    if ! command -v cmake >/dev/null 2>&1; then
      echo "cmake is still unavailable after bootstrap." >&2
      exit 1
    fi
    BUILD_DIR="${ROOTDIR}/build/host"
    CMAKE_ARGS=(-S "${ROOTDIR}" -B "${BUILD_DIR}" -DCMAKE_BUILD_TYPE=Release)
    if [[ "${BUILD_RUNTIME}" -eq 1 ]]; then
      CMAKE_ARGS+=(-DBLITZFORGE_BUILD_RUNTIME=ON)
    else
      CMAKE_ARGS+=(-DBLITZFORGE_BUILD_RUNTIME=OFF)
    fi
    if command -v ninja >/dev/null 2>&1; then
      CMAKE_ARGS+=(-G Ninja)
    fi
    cmake "${CMAKE_ARGS[@]}"
    TARGETS=(blitzcc)
    if [[ "${HOST_SYSTEM}" == "Darwin" && "${BUILD_RUNTIME}" -eq 1 ]]; then
      TARGETS+=(runtime linker)
    fi
    cmake --build "${BUILD_DIR}" --target "${TARGETS[@]}" --parallel
  else
    "${ROOTDIR}/compile.bat"
  fi
}

if [[ "${BASH_SOURCE[0]}" == "$0" ]]; then
  main "$@"
fi
