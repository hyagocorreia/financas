﻿Type=Activity
Version=3.2
@EndOfDesignText@
#Region  Activity Attributes 
	#FullScreen: False
	#IncludeTitle: True
#End Region

Sub Process_Globals

End Sub

Sub Globals
	Private Username As EditText
	Private Senha As EditText
	Private Button_Excluir As Button
	Private Button_Voltar As Button
End Sub

Sub Activity_Create(FirstTime As Boolean)
	Activity.LoadLayout("Layout_Excluir")
End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub

Sub Button_Excluir_Click
	If Main.Pers.Excluir_Login(Username.Text, Senha.Text) Then
		Msgbox("Excluido com sucesso","Atenção")
		StartActivity("Main")
		Main.Fazer_logout = True
		Activity.Finish
	End If
End Sub

Sub Button_Voltar_Click
	Activity.Finish
End Sub