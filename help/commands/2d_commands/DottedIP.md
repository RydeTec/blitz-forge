# DottedIP\$( IP )

# Parameters

  -------------------------
  IP - integer IP address
  -------------------------

# Description

  -------
  None.
  -------

# [Example](../2d_examples/DottedIP.bb)

  -----------------------------------------------------------------------
  ; First call CountHostIPs (blank infers the local machine)\
  n = CountHostIPs(\"\")\
  ; n now contains the total number of known host machines.\
  \
  ; Obtain the internal id for the IP address\
  ip = HostIP(1)\
  \
  ; Convert it to human readable IP address\
  ipaddress\$ = DottedIP\$(ip)\
  \
  Print \"Dotted IP Test\"\
  Print \"==============\"\
  Print \"\"\
  Print \"Internal Host IP ID:\" + ip\
  Print \"Dotted IP Address:\" + ipaddress\$\
  Print \"\"\
  Print \"Press any key to continue\"\
  \
  WaitKey()\
  \
  End

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=DottedIP&ref=comments){target="_blank"}
to view the latest version of this page online
