#!/usr/bin/env bash
set -euo pipefail

ROOTDIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
BUILD_RUNTIME=0

usage() {
  cat <<'EOF'
BlitzForge Compiler Script

-r | --with-runtime   Build runtime/linker shared libraries for host-target macOS support
-h | --help           Show this help
EOF
}

while [[ $# -gt 0 ]]; do
  case "$1" in
    -r|--with-runtime) BUILD_RUNTIME=1 ;;
    -h|--help) usage; exit 0 ;;
    *) echo "Unknown flag: $1" >&2; usage; exit 1 ;;
  esac
  shift
done

if [[ "$(uname -s)" == "Darwin" && "${BUILD_RUNTIME}" -eq 0 ]]; then
  # macOS host builds need the companion runtime/linker libraries for compiler loading.
  BUILD_RUNTIME=1
fi

if [[ "$(uname -s)" == "Darwin" || "$(uname -s)" == "Linux" ]]; then
  if ! command -v cmake >/dev/null 2>&1; then
    if [[ "$(uname -s)" == "Darwin" && -x "${ROOTDIR}/scripts/bootstrap_macos.sh" ]]; then
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
  if [[ "$(uname -s)" == "Darwin" && "${BUILD_RUNTIME}" -eq 1 ]]; then
    TARGETS+=(runtime linker)
  fi
  cmake --build "${BUILD_DIR}" --target "${TARGETS[@]}" --parallel
else
  "${ROOTDIR}/compile.bat"
fi
