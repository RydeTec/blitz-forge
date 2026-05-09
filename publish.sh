#!/usr/bin/env bash
set -euo pipefail

ROOTDIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
RELEASE_DIR="${ROOTDIR}/release"

"${ROOTDIR}/compile.sh"

rm -rf "${RELEASE_DIR}"
mkdir -p "${RELEASE_DIR}"

for dir in bin cfg games help media mediaview samples tutorials userlibs; do
  if [[ -d "${ROOTDIR}/${dir}" ]]; then
    rsync -a "${ROOTDIR}/${dir}/" "${RELEASE_DIR}/${dir}/"
  fi
done

if [[ -d "${ROOTDIR}/../../extras/vscode-blitz-forge" && -x "${ROOTDIR}/../../extras/vscode-blitz-forge/compile.sh" ]]; then
  "${ROOTDIR}/../../extras/vscode-blitz-forge/compile.sh"
  find "${ROOTDIR}/../../extras/vscode-blitz-forge" -maxdepth 1 -name '*.vsix' -exec cp {} "${RELEASE_DIR}/" \;
fi

cat > "${RELEASE_DIR}/README.txt" <<'EOF'
BlitzForge is a compiler for an enhanced version of the Blitz3D language.
It is a fork of the Blitz3D compiler and adds support for BlitzForge commands and syntax.
You can develop with BlitzForge in Visual Studio Code by installing the bundled .vsix extension.
EOF
