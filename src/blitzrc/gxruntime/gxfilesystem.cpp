#include "std.h"
#include "gxfilesystem.h"

static set<gxDir*> dir_set;

gxFileSystem::gxFileSystem(){
	dir_set.clear();
}

gxFileSystem::~gxFileSystem(){
	while( dir_set.size() ) closeDir( *dir_set.begin() );
}

bool gxFileSystem::createDir( const std::string &dir ){
	return CreateDirectory( dir.c_str(),0 ) ? true : false;
}

bool gxFileSystem::deleteDir( const std::string &dir ){
	return RemoveDirectory( dir.c_str() ) ? true : false;
}

bool gxFileSystem::createFile( const std::string &file ){
	return false;
}

bool gxFileSystem::deleteFile( const std::string &file ){
	return DeleteFile( file.c_str() ) ? true : false;
}

bool gxFileSystem::copyFile( const std::string &src,const string &dest ){
	return CopyFile( src.c_str(),dest.c_str(),false ) ? true : false;
}

bool gxFileSystem::renameFile( const std::string &src,const std::string &dest ){
	return MoveFile( src.c_str(),dest.c_str() ) ? true : false;
}

bool gxFileSystem::setCurrentDir( const std::string &dir ){
	return SetCurrentDirectory( dir.c_str()) ? true : false;
}

string gxFileSystem::getCurrentDir()const{
	char buff[MAX_PATH];
	if( !GetCurrentDirectory( MAX_PATH,buff ) ) return "";
	string t=buff;if( t.size() && t[t.size()-1]!='\\' ) t+='\\';
	return t;
}

int gxFileSystem::getFileSize( const std::string &name )const{
	WIN32_FIND_DATA findData;
	HANDLE h=FindFirstFile( name.c_str(),&findData );
	if( h==INVALID_HANDLE_VALUE ) return 0;
	int n=findData.dwFileAttributes,sz=findData.nFileSizeLow;
	FindClose( h );return n & FILE_ATTRIBUTE_DIRECTORY ? 0 : sz;
}

int gxFileSystem::getFileType( const std::string &name )const{
	DWORD t=GetFileAttributes( name.c_str() );
	return t==-1 ? FILE_TYPE_NONE :
	(t & FILE_ATTRIBUTE_DIRECTORY ? FILE_TYPE_DIR : FILE_TYPE_FILE);
}

gxDir *gxFileSystem::openDir( const std::string &name,int flags ){
	string t=name;
	if( t[t.size()-1]=='\\' ) t+="*";
	else t+="\\*";
	WIN32_FIND_DATA f;
	HANDLE h=FindFirstFile( t.c_str(),&f );
	if( h!=INVALID_HANDLE_VALUE ){
		gxDir *d=d_new gxDir( h,f );
		dir_set.insert( d );
		return d;
	}
	return 0;
}
gxDir *gxFileSystem::verifyDir( gxDir *d ){
	return dir_set.count(d) ? d : 0;
}

void gxFileSystem::closeDir( gxDir *d ){
	if( dir_set.erase( d ) ) delete d;
}

bool gxFileSystem::copyDir(const std::string& src, const std::string& dest) {
    WIN32_FIND_DATA ffd;
    HANDLE hFind = INVALID_HANDLE_VALUE;
    DWORD dwError = 0;

    // Only add extended-length prefix if it's not already present
    std::string longSrc = (src.substr(0,4) == "\\\\?\\") ? src : "\\\\?\\" + src;
    std::string longDest = (dest.substr(0,4) == "\\\\?\\") ? dest : "\\\\?\\" + dest;

    // Create destination with error checking
    if (!CreateDirectory(longDest.c_str(), NULL)) {
        dwError = GetLastError();
        if (dwError != ERROR_ALREADY_EXISTS) {
            cout << "Error creating directory: " << dwError << endl;
            return false;
        }
    }

    std::string searchPath = longSrc + "\\*";
    hFind = FindFirstFile(searchPath.c_str(), &ffd);

    if (hFind == INVALID_HANDLE_VALUE) {
        cout << "Error finding first file: " << GetLastError() << endl;
        return false;
    }

    bool success = true;
    do {
        if (strcmp(ffd.cFileName, ".") != 0 && strcmp(ffd.cFileName, "..") != 0) {
            std::string srcPath = longSrc + "\\" + ffd.cFileName;
            std::string destPath = longDest + "\\" + ffd.cFileName;

            if (ffd.dwFileAttributes & FILE_ATTRIBUTE_DIRECTORY) {
                if (!copyDir(srcPath, destPath)) {
                    success = false;
                    break;
                }
            } else {
                // Preserve file attributes when copying
                if (!CopyFile(srcPath.c_str(), destPath.c_str(), FALSE)) {
                    success = false;
                    break;
                }
                // Copy original attributes to destination
                SetFileAttributes(destPath.c_str(), ffd.dwFileAttributes);
            }
        }
    } while (FindNextFile(hFind, &ffd) != 0);

    dwError = GetLastError();
    FindClose(hFind);

    if (!success) {
        cout << "Error copying directory: " << dwError << endl;
        return false;
    }
    
    return (dwError == ERROR_NO_MORE_FILES);
}
