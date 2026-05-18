# Licensing Map

BlitzForge does not currently publish a single repo-root project license file.
Instead, the checked-in licensing terms live with the components they cover.

This document is a pointer map to those authoritative notices. It is not a
replacement for the original license texts.

## Core compiler/runtime source

- [`src/blitzrc/LICENSE.TXT`](src/blitzrc/LICENSE.TXT)
  - Covers the inherited Blitz3D source release under `src/blitzrc/`.
  - States that Blitz3D is released under the `zlib/libpng` license.

## Bundled third-party libraries

- [`src/libogg/COPYING`](src/libogg/COPYING)
  - Redistribution terms for the bundled libogg sources from Xiph.org.

- [`src/libvorbis/COPYING`](src/libvorbis/COPYING)
  - Redistribution terms for the bundled libvorbis sources from Xiph.org.

- [`src/freeimage241/license-fi.txt`](src/freeimage241/license-fi.txt)
  - FreeImage Public License text shipped with the bundled FreeImage sources.

- [`src/freeimage241/license-gpl.txt`](src/freeimage241/license-gpl.txt)
  - GPL alternative text shipped alongside the bundled FreeImage sources.

- [`src/freeimage241/Source/LibMNG/LICENSE`](src/freeimage241/Source/LibMNG/LICENSE)
  - License notice for the bundled LibMNG sources that ship inside FreeImage.

- [`src/freeimage241/Source/LibPNG/LICENSE`](src/freeimage241/Source/LibPNG/LICENSE)
  - License notice for the bundled LibPNG sources that ship inside FreeImage.

## Release artifact review

The release packaging scripts copy compiled binaries plus bundled assets from
directories such as `bin/`, `cfg/`, `games/`, `help/`, `media/`, `samples/`,
`tutorials/`, and `userlibs/`. If you plan to redistribute a release archive,
review the license notice(s) for every bundled component you ship, not just the
compiler core.
