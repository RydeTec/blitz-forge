# BlitzForge

BlitzForge is a modernization of the Blitz3D programming language and toolchain.

## Download
You can download a pre-compiled version of the latest release here: https://github.com/RydeTec/blitz-forge/releases

## Compile
Once you download the repository you can compile easily by running the `compile.bat` batch file.

### macOS (arm64) — Alpha

> **Status: alpha. Not stable. Expect breakage.**
>
> Native macOS arm64 support is under active development. The compiler (`blitzcc`) builds and can target `macos-arm64`, but the runtime execution path is incomplete and many language and standard-library features are not yet wired up. Do not rely on it for shipping work.

On macOS you can build using the CMake-based toolchain:

```bash
./scripts/bootstrap_macos.sh   # one-time: installs cmake/ninja/llvm and runtime deps via Homebrew
./compile.sh                   # builds bin/blitzcc, bin/runtime.dylib, bin/linker.dylib
./test.sh                      # runs tests/*.bb against -target macos-arm64
```

Issues and feedback specific to the macOS port are welcome on the [issue tracker](https://github.com/RydeTec/blitz-forge/issues).

## Contribute

### Users

Users can help contribute to the BlitzForge by submitting [ideas](https://github.com/RydeTec/blitz-forge/discussions/categories/ideas) 
and [bugs](https://github.com/RydeTec/blitz-forge/issues).

### Developers

If you would like to add to BlitzForge source please feel free to clone or fork the repo. If you would like to push a branch and open a 
pull request to have your modifications included as part of the official source please request to join the [RCCE Contributors](https://github.com/orgs/RydeTec/teams/rcce-contributors) group.

There are lots of things that developers of any level can do to help contribute. Simply adding comments to existing code, updating documentation, small code refactors or entirely new features are all welcome additions. It's always best to start small and increase complexity over time.

#### Strategy

Branch from `develop` and target pull request to `develop`. Releases will be a pull request from `develop` targeting `master`.

##### Tests

You could also help writing tests in blitz as well. All tests should live under the [tests](tests) folder and you can run all tests by running the `test.bat` script. Tests are run before every git commit locally and must pass for your commit to succeed.

#### C++

If you are familiar with C++ you can help by making improvements or changes to the Blitz runtime [source code](src\blitzrc).

### Community

You can contribute just by being a part of the community and participating in discussions. We would love to see you there!

Github Discussions: https://github.com/RydeTec/blitz-forge/discussions

## Resources

### Visual Studio
In order to edit and compile the BlitzForge you will need to open their solutions in the community version of Visual Studio https://visualstudio.microsoft.com/

### VS Code
You can also develop using vscode for both C++ and Blitz. To compile the C++ you can run the compile.bat located in the root directory. In the release version of BlitzForge we include a compiled VS Code extension from [https://github.com/RydeTec/vscode-blitz-forge]. This extension will help with developing in the BlitzForge language in VS Code.