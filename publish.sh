#!/usr/bin/env bash
set -euo pipefail

ROOTDIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
RELEASE_DIR="${ROOTDIR}/release"

host_system() {
  if [[ -n "${BLITZFORGE_PUBLISH_HOST_SYSTEM:-}" ]]; then
    printf '%s\n' "${BLITZFORGE_PUBLISH_HOST_SYSTEM}"
    return
  fi

  uname -s
}

require_supported_release_host() {
  local system_name="${1:-$(host_system)}"

  case "${system_name}" in
    Darwin) return 0 ;;
  esac

  echo "publish.sh currently supports release archives only on macOS hosts." >&2
  echo "Linux source builds can stop at ./compile.sh until runtime/linker packaging exists there." >&2
  return 1
}

host_archive_basename() {
  case "${1:-$(host_system)}" in
    Darwin) echo "blitzforge-macos-arm64" ;;
    *)
      echo "Unsupported release host: ${1:-$(host_system)}" >&2
      return 1
      ;;
  esac
}

create_zip_archive() {
  local source_parent="$1"
  local source_name="$2"
  local archive_path="$3"

  if command -v zip >/dev/null 2>&1; then
    (
      cd "${source_parent}"
      zip -qr "${archive_path}" "${source_name}"
    )
    return
  fi

  if command -v python3 >/dev/null 2>&1; then
    (
      cd "${source_parent}"
      python3 -m zipfile -c "${archive_path}" "${source_name}"
    )
    return
  fi

  echo "Unable to create release archive: install zip or python3." >&2
  exit 1
}

main() {
  require_supported_release_host

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

  ARCHIVE_BASENAME="$(host_archive_basename)"
  ARCHIVE_PATH="${RELEASE_DIR}/${ARCHIVE_BASENAME}.zip"
  PACKAGE_TMPDIR="$(mktemp -d "${TMPDIR:-/tmp}/blitzforge-package.XXXXXX")"
  trap 'rm -rf "${PACKAGE_TMPDIR}"' EXIT
  PACKAGE_ROOT="${PACKAGE_TMPDIR}/${ARCHIVE_BASENAME}"
  mkdir -p "${PACKAGE_ROOT}"
  rsync -a "${RELEASE_DIR}/" "${PACKAGE_ROOT}/"
  rm -f "${ARCHIVE_PATH}"
  create_zip_archive "${PACKAGE_TMPDIR}" "${ARCHIVE_BASENAME}" "${ARCHIVE_PATH}"

  echo "Created release archive: ${ARCHIVE_PATH}"
}

if [[ "${BASH_SOURCE[0]}" == "$0" ]]; then
  main "$@"
fi
