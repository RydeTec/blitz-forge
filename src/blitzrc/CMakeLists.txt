set(BLITZCC_SOURCES
  blitz/main.cpp
  blitz/libs.cpp
  compiler/compiler.cpp
  compiler/decl.cpp
  compiler/declnode.cpp
  compiler/environ.cpp
  compiler/exprnode.cpp
  compiler/node.cpp
  compiler/parser.cpp
  compiler/prognode.cpp
  compiler/stmtnode.cpp
  compiler/std.cpp
  compiler/toker.cpp
  compiler/type.cpp
  compiler/varnode.cpp
  compiler/assem_x86/assem_x86.cpp
  compiler/assem_x86/asm_insts.cpp
  compiler/assem_x86/operand.cpp
  compiler/codegen_arm64/codegen_arm64.cpp
  compiler/codegen_x86/codegen_x86.cpp
  compiler/codegen_x86/tile.cpp
  stdutil/stdutil.cpp
)

add_executable(blitzcc ${BLITZCC_SOURCES})

target_include_directories(blitzcc PRIVATE
  ${CMAKE_CURRENT_SOURCE_DIR}
  ${CMAKE_CURRENT_SOURCE_DIR}/blitz
  ${CMAKE_CURRENT_SOURCE_DIR}/compiler
  ${CMAKE_CURRENT_SOURCE_DIR}/compiler/assem_x86
  ${CMAKE_CURRENT_SOURCE_DIR}/compiler/codegen_arm64
  ${CMAKE_CURRENT_SOURCE_DIR}/compiler/codegen_x86
  ${CMAKE_CURRENT_SOURCE_DIR}/bbruntime_dll
  ${CMAKE_CURRENT_SOURCE_DIR}/linker
  ${CMAKE_CURRENT_SOURCE_DIR}/stdutil
  ${CMAKE_CURRENT_SOURCE_DIR}/config
  ${CMAKE_CURRENT_SOURCE_DIR}/debugger
)

if(CMAKE_CXX_COMPILER_ID MATCHES "Clang|GNU")
  target_compile_options(blitzcc PRIVATE -Wno-unknown-pragmas -Wno-multichar -Wno-c++11-narrowing)
endif()

if(WIN32)
  set_target_properties(blitzcc PROPERTIES OUTPUT_NAME "blitzcc")
else()
  target_compile_definitions(blitzcc PRIVATE BF_PLATFORM_POSIX=1)
  set_target_properties(blitzcc PROPERTIES OUTPUT_NAME "blitzcc")
endif()

set_target_properties(blitzcc PROPERTIES
  RUNTIME_OUTPUT_DIRECTORY "${PROJECT_SOURCE_DIR}/bin"
)

if(APPLE AND BLITZFORGE_BUILD_RUNTIME)
  file(GLOB_RECURSE RUNTIME_SYMBOL_SOURCE_FILES
    "${CMAKE_CURRENT_SOURCE_DIR}/bbruntime/*.cpp"
    "${CMAKE_CURRENT_SOURCE_DIR}/blitz3d/*.cpp"
    "${CMAKE_CURRENT_SOURCE_DIR}/gxruntime/*.cpp"
  )
  set(RUNTIME_SYMBOLS_INC "${CMAKE_CURRENT_BINARY_DIR}/generated_runtime_symbols.inc")
  add_custom_command(
    OUTPUT "${RUNTIME_SYMBOLS_INC}"
    COMMAND "${CMAKE_COMMAND}"
      -DSOURCE_ROOT=${CMAKE_CURRENT_SOURCE_DIR}
      -DOUTPUT_FILE=${RUNTIME_SYMBOLS_INC}
      -P "${CMAKE_CURRENT_SOURCE_DIR}/stubs/generate_runtime_symbols.cmake"
    DEPENDS
      "${CMAKE_CURRENT_SOURCE_DIR}/stubs/generate_runtime_symbols.cmake"
      ${RUNTIME_SYMBOL_SOURCE_FILES}
    COMMENT "Generating macOS runtime symbol catalog"
    VERBATIM
  )

  add_library(runtime SHARED
    "${RUNTIME_SYMBOLS_INC}"
    stubs/runtime_stub.cpp
  )
  target_include_directories(runtime PRIVATE
    ${CMAKE_CURRENT_SOURCE_DIR}
    ${CMAKE_CURRENT_SOURCE_DIR}/bbruntime_dll
    ${CMAKE_CURRENT_SOURCE_DIR}/debugger
    ${CMAKE_CURRENT_SOURCE_DIR}/stdutil
    ${CMAKE_CURRENT_SOURCE_DIR}/config
    ${CMAKE_CURRENT_BINARY_DIR}
  )
  set_target_properties(runtime PROPERTIES
    PREFIX ""
    OUTPUT_NAME "runtime"
    LIBRARY_OUTPUT_DIRECTORY "${PROJECT_SOURCE_DIR}/bin"
  )

  add_library(linker SHARED
    linker/linker.cpp
    linker_dll/linker_dll.cpp
  )
  target_include_directories(linker PRIVATE
    ${CMAKE_CURRENT_SOURCE_DIR}
    ${CMAKE_CURRENT_SOURCE_DIR}/linker
    ${CMAKE_CURRENT_SOURCE_DIR}/config
  )
  target_compile_definitions(linker PRIVATE BF_PLATFORM_POSIX=1)
  set_target_properties(linker PROPERTIES
    PREFIX ""
    OUTPUT_NAME "linker"
    LIBRARY_OUTPUT_DIRECTORY "${PROJECT_SOURCE_DIR}/bin"
  )
endif()
