#ifndef BLITZRC_PLATFORM_H
#define BLITZRC_PLATFORM_H

#include <algorithm>
#include <cerrno>
#include <cstdlib>
#include <cstring>
#include <string>
#include <vector>

#ifdef _WIN32
#include <windows.h>
#else
#include <dirent.h>
#include <dlfcn.h>
#include <limits.h>
#include <sys/stat.h>
#include <unistd.h>
#ifdef __APPLE__
#include <mach-o/dyld.h>
#endif
#endif

namespace bfplatform {

#ifdef _WIN32
typedef HMODULE SharedLibHandle;
typedef HINSTANCE NativeModuleHandle;
#else
typedef void *SharedLibHandle;
typedef void *NativeModuleHandle;
#endif

inline bool isWindows() {
#ifdef _WIN32
	return true;
#else
	return false;
#endif
}

inline bool isMacOS() {
#ifdef __APPLE__
	return true;
#else
	return false;
#endif
}

inline std::string normalizeSlashes(const std::string &input) {
	std::string out = input;
	for (size_t i = 0; i < out.size(); ++i) {
		if (out[i] == '\\') out[i] = '/';
	}
	return out;
}

inline bool hasSuffix(const std::string &value, const std::string &suffix) {
	if (suffix.size() > value.size()) return false;
	return value.compare(value.size() - suffix.size(), suffix.size(), suffix) == 0;
}

inline std::string sharedLibraryExtension() {
#ifdef _WIN32
	return ".dll";
#elif __APPLE__
	return ".dylib";
#else
	return ".so";
#endif
}

inline std::string executableExtension() {
#ifdef _WIN32
	return ".exe";
#else
	return "";
#endif
}

inline std::string toLowerAscii(std::string s) {
	std::transform(s.begin(), s.end(), s.begin(), [](unsigned char c) { return static_cast<char>(::tolower(c)); });
	return s;
}

inline std::string mapLibraryNameForHost(const std::string &name) {
	const std::string lower = toLowerAscii(name);
#ifdef _WIN32
	if (hasSuffix(lower, ".dll") || hasSuffix(lower, ".dylib") || hasSuffix(lower, ".so")) {
		const size_t dot = name.find_last_of('.');
		return name.substr(0, dot) + sharedLibraryExtension();
	}
	if (!name.empty() && name != " " && name.find('/') == std::string::npos && name.find('\\') == std::string::npos &&
		name.find('.') == std::string::npos) {
		return name + sharedLibraryExtension();
	}
	return name;
#else
	// On macOS/Linux normalize logical library names to lowercase so lookups remain stable on case-sensitive filesystems.
	const bool hasPath = name.find('/') != std::string::npos || name.find('\\') != std::string::npos;
	if (hasPath) return name;
	if (hasSuffix(lower, ".dll") || hasSuffix(lower, ".dylib") || hasSuffix(lower, ".so")) {
		const size_t dot = lower.find_last_of('.');
		return lower.substr(0, dot) + sharedLibraryExtension();
	}
	if (!name.empty() && name != " " && name.find('.') == std::string::npos) {
		return lower + sharedLibraryExtension();
	}
	return lower;
#endif
}

inline std::string sharedLibraryFileName(const std::string &baseName) {
	std::string resolved = mapLibraryNameForHost(baseName);
	const std::string lower = toLowerAscii(resolved);
	if (hasSuffix(lower, ".dll") || hasSuffix(lower, ".dylib") || hasSuffix(lower, ".so")) return resolved;
	return resolved + sharedLibraryExtension();
}

inline std::string joinPath(const std::string &base, const std::string &leaf) {
	if (base.empty()) return normalizeSlashes(leaf);
	if (leaf.empty()) return normalizeSlashes(base);
	std::string b = normalizeSlashes(base);
	std::string l = normalizeSlashes(leaf);
	if (b[b.size() - 1] == '/') return b + l;
	return b + "/" + l;
}

inline std::string dirname(const std::string &path) {
	std::string p = normalizeSlashes(path);
	const size_t n = p.find_last_of('/');
	if (n == std::string::npos) return "";
	if (n == 0) return "/";
	return p.substr(0, n);
}

inline std::string absolutePath(const std::string &path) {
	if (path.empty()) return "";
#ifdef _WIN32
	char buff[MAX_PATH + 1];
	char *filePart = 0;
	if (GetFullPathNameA(path.c_str(), MAX_PATH, buff, &filePart)) return normalizeSlashes(buff);
	return normalizeSlashes(path);
#else
	char resolved[PATH_MAX + 1];
	if (realpath(path.c_str(), resolved)) return normalizeSlashes(resolved);

	// Keep behavior predictable even when target does not exist yet.
	char cwd[PATH_MAX + 1];
	if (getcwd(cwd, PATH_MAX)) return normalizeSlashes(joinPath(cwd, path));
	return normalizeSlashes(path);
#endif
}

inline std::string normalizeIncludeCachePath(const std::string &path) {
	std::string normalized = normalizeSlashes(absolutePath(path));
	if (isWindows()) normalized = toLowerAscii(normalized);
	return normalized;
}

inline bool setCurrentDirectory(const std::string &path) {
#ifdef _WIN32
	return SetCurrentDirectoryA(path.c_str()) != 0;
#else
	return chdir(path.c_str()) == 0;
#endif
}

inline std::string getCurrentDirectory() {
#ifdef _WIN32
	char buffer[MAX_PATH + 1];
	DWORD ret = GetCurrentDirectoryA(MAX_PATH, buffer);
	if (ret > 0) return normalizeSlashes(buffer);
	return "";
#else
	char buffer[PATH_MAX + 1];
	if (getcwd(buffer, PATH_MAX)) return normalizeSlashes(buffer);
	return "";
#endif
}

inline std::string executablePath() {
#ifdef _WIN32
	char buff[MAX_PATH + 1];
	if (GetModuleFileNameA(0, buff, MAX_PATH)) return normalizeSlashes(buff);
	return "";
#elif __APPLE__
	uint32_t size = 0;
	_NSGetExecutablePath(0, &size);
	if (!size) return "";
	std::vector<char> tmp(size + 1, 0);
	if (_NSGetExecutablePath(&tmp[0], &size) != 0) return "";
	return absolutePath(&tmp[0]);
#else
	char buff[PATH_MAX + 1];
	ssize_t n = readlink("/proc/self/exe", buff, PATH_MAX);
	if (n > 0) {
		buff[n] = 0;
		return normalizeSlashes(buff);
	}
	return "";
#endif
}

inline std::string executableDirectory() {
	return dirname(executablePath());
}

inline NativeModuleHandle currentModuleHandle() {
#ifdef _WIN32
	return GetModuleHandleA(0);
#else
	return 0;
#endif
}

inline SharedLibHandle loadSharedLibrary(const std::string &path) {
#ifdef _WIN32
	return LoadLibraryA(path.c_str());
#else
	return dlopen(path.c_str(), RTLD_NOW | RTLD_LOCAL);
#endif
}

inline void *loadSymbol(SharedLibHandle handle, const char *name) {
	if (!handle) return 0;
#ifdef _WIN32
	return reinterpret_cast<void *>(GetProcAddress(handle, name));
#else
	return dlsym(handle, name);
#endif
}

inline void closeSharedLibrary(SharedLibHandle handle) {
	if (!handle) return;
#ifdef _WIN32
	FreeLibrary(handle);
#else
	dlclose(handle);
#endif
}

inline void showInfoMessage(const std::string &title, const std::string &message) {
#ifdef _WIN32
	MessageBoxA(0, message.c_str(), title.c_str(), MB_OK | MB_SETFOREGROUND | MB_TOPMOST);
#else
	(void)title;
	(void)message;
#endif
}

inline std::vector<std::string> listFilesWithExtension(const std::string &dir, const std::string &extension) {
	std::vector<std::string> out;
	const std::string extLower = toLowerAscii(extension);
	const std::string safeDir = normalizeSlashes(dir);
#ifdef _WIN32
	WIN32_FIND_DATAA fd;
	const std::string mask = joinPath(safeDir, "*" + extension);
	HANDLE h = FindFirstFileA(mask.c_str(), &fd);
	if (h == INVALID_HANDLE_VALUE) return out;
	do {
		if (!(fd.dwFileAttributes & FILE_ATTRIBUTE_DIRECTORY)) out.push_back(fd.cFileName);
	} while (FindNextFileA(h, &fd));
	FindClose(h);
#else
	DIR *d = opendir(safeDir.c_str());
	if (!d) return out;
	while (dirent *ent = readdir(d)) {
		if (!ent->d_name[0]) continue;
		std::string name = ent->d_name;
		if (name == "." || name == "..") continue;
		if (!hasSuffix(toLowerAscii(name), extLower)) continue;
		out.push_back(name);
	}
	closedir(d);
#endif
	std::sort(out.begin(), out.end());
	return out;
}

} // namespace bfplatform

#endif
