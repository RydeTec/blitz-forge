#!/usr/bin/env bash
set -euo pipefail

if [[ "$(uname -s)" != "Darwin" ]]; then
  echo "bootstrap_macos.sh is intended for macOS hosts."
  exit 1
fi

if ! command -v brew >/dev/null 2>&1; then
  echo "Homebrew is required. Install from https://brew.sh and re-run."
  exit 1
fi

PACKAGES=(
  cmake
  ninja
  llvm
  sdl2
  openal-soft
  libogg
  libvorbis
  freeimage
)

echo "Installing BlitzForge macOS dependencies..."
brew install "${PACKAGES[@]}"
