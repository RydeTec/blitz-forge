cmake_minimum_required(VERSION 3.20)

project(BlitzForge LANGUAGES CXX)

option(BLITZFORGE_BUILD_RUNTIME "Build runtime/linker shared libraries (experimental)." OFF)

set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED ON)
set(CMAKE_CXX_EXTENSIONS OFF)

add_subdirectory(src/blitzrc)
