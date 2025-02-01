# CallDLL( dll_name\$, proc_name\$\[,in_bank,out_bank\] )

# Parameters

  -----------------------------------------------------------------------
  dll_name\$ - name of dll\
  proc_name\$ - name of procedure\
  in_bank (optional) - handle of bank that is made available from Blitz
  to DLL procedure\
  out_bank (optional) - handle of bank that is made available from DLL
  procedure to Blitz

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  The DLL is called with pointers to and sizes of bank memory. Dll
  function prototypes should like something like this (Visual C++)
  example:\
  \
  extern \"C\"{\
  \_declspec(dllexport) int \_cdecl my_dll_func( const void \*in,int
  in_size,void \*out,int out_sz );\
  }\
  \
  The \'extern \"C\"\' bit prevents C++ \'name-mangling\', and the
  \_cdecl bit prevents name decoration. You could call this function
  using something like:\
  \
  in_bank=CreateBank(\...)\
  out_bank=CreateBank(\...)\
  \
  ;poke input parameters into in_bank\
  result=CallDLL( \"mydll\",\"my_dll_func\",bank1,bank2 )\
  ;peek output results from out_bank

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=CallDLL&ref=comments){target="_blank"}
to view the latest version of this page online
