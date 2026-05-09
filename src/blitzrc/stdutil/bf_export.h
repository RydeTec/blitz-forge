#ifndef BLITZRC_BF_EXPORT_H
#define BLITZRC_BF_EXPORT_H

// Lightweight portability macros for shared-library exports and the legacy
// `_cdecl` calling convention. Kept dependency-free so headers that participate
// in the runtime/linker C ABI (and cannot pull in heavier platform helpers)
// can include it safely.

#if defined(_WIN32)
#  define BF_DLLEXPORT __declspec(dllexport)
#  if defined(_MSC_VER)
#    define BF_CDECL __cdecl
#  else
#    define BF_CDECL __attribute__((cdecl))
#  endif
#else
#  define BF_DLLEXPORT __attribute__((visibility("default")))
#  define BF_CDECL
#endif

// Alias the legacy `_cdecl` keyword used throughout the historical codebase to
// our portable BF_CDECL. On non-MSVC compilers `_cdecl` is not a reserved
// identifier, so substituting via the preprocessor keeps the existing source
// shape intact while avoiding a hard dependency on Microsoft compiler
// extensions on macOS / Linux hosts.
#if !defined(_MSC_VER)
#  ifndef _cdecl
#    define _cdecl BF_CDECL
#  endif
#endif

#endif
