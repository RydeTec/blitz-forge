
#ifndef STD_COMPILER_H
#define STD_COMPILER_H

#include "../config/config.h"
#include "../stdutil/stdutil.h"

#include <set>
#include <map>
#include <list>
#include <string>
#include <vector>
#include <fstream>
#include <iostream>
#include <iomanip>

// Aggregate the C-library facilities the compiler frontend has historically
// pulled in transitively via `<windows.h>` (`memcpy`, `memset`, `pow`, ...).
// On Windows MSVC + libstdc++ on Linux + libc++ on macOS each chase these
// through different paths, so make the dependency explicit at the project's
// common aggregator header rather than relying on transitive inclusion.
#include <cstring>
#include <cmath>

using namespace std;

#ifdef _WIN32
#include <windows.h>
#endif

#endif