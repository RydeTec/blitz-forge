Function CheckSphere(x#,y#,z#, sx#,sy#,sz#,r#)
	Return TeoremaPifagora(x,y,z,sx,sy,sz)<r
End Function

Function CheckBox(x#,y#,z#,bx#,by#,bz#,w#,h#,l#)

	; Перемещение - относительно центра

	x = x-bx
	y = y-by
	z = z-bz

	If x < w And x > w*(-1) Then
		If y < h And y > h*(-1)
			If z < l And z > l*(-1)
				Return 1
			End If
		End If
	End If
End Function

Function TeoremaPifagora#(x1#,y1#,z1#,x2#,y2#=0,z2#=0)
	Return Sqr((x1 - x2)^2+(y1 - y2)^2+(z1 - z2)^2)
End Function