Type=Activity
Version=3.2
@EndOfDesignText@
#Region  Activity Attributes 
	#FullScreen: False
	#IncludeTitle: True
#End Region

Sub Process_Globals

End Sub

Sub Globals
	
	Private EditText_Nome As EditText
	Private CheckBox_AlterarSenha As CheckBox
	Private EditText_SenhaAntiga As EditText
	Private EditText_NovaSenha1 As EditText
	Private EditText_NovaSenha2 As EditText
	Private Button_Salvar As Button
	Private Button_Cancelar As Button
	Private EditText_UserName As EditText
	Dim linha1,linha2,linha3,linha4 As String
End Sub

Sub Activity_Create(FirstTime As Boolean)
	Activity.LoadLayout("Layout_Editar")
	
	linha1 = Main.Pers.GetUsuario(Main.UN)
	linha2 = linha1.SubString2(0,linha1.IndexOf(";"))
	linha3 = linha1.SubString2(linha1.IndexOf(";")+1,linha1.LastIndexOf(";"))
	linha4 = linha1.SubString(linha1.LastIndexOf(";")+1)
	
	EditText_Nome.Text = linha4
	EditText_UserName.Text = linha2
End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub

Sub CheckBox_AlterarSenha_CheckedChange(Checked As Boolean)
	If CheckBox_AlterarSenha.Checked Then
		EditText_NovaSenha1.Visible = True
		EditText_NovaSenha2.Visible = True
	Else
		EditText_NovaSenha1.Visible = False
		EditText_NovaSenha2.Visible = False
	End If
End Sub

Sub Button_Salvar_Click
	If CheckBox_AlterarSenha.Checked = True Then
		If EditText_SenhaAntiga.Text = linha3 Then
			If Main.Pers.Excluir_Login(linha2,linha3) AND	Main.Pers.Criar_Login(EditText_Nome.Text,EditText_UserName.Text,EditText_NovaSenha1.Text,EditText_NovaSenha2.Text) Then
				Msgbox("Nome: "&EditText_Nome.Text & CRLF & "Username: "&EditText_UserName.Text, "Alterado com sucesso!")
				StartActivity("Menu")
				Activity.Finish
			End If
		Else
			Msgbox("Senha incorreta!", "Atenção!")
		End If
	Else
		If EditText_SenhaAntiga.Text = linha3 Then
			If Main.Pers.Excluir_Login(linha2,linha3) AND	Main.Pers.Criar_Login(EditText_Nome.Text,EditText_UserName.Text,EditText_SenhaAntiga.Text,EditText_SenhaAntiga.Text) Then
				Msgbox("Nome: "&EditText_Nome.Text & CRLF & "Username: "&EditText_UserName.Text, "Alterado com sucesso!")
				StartActivity("Menu")
				Activity.Finish		
			End If
		Else
			Msgbox("Senha incorreta!", "Atenção!")
		End If
	End If
End Sub

Sub Button_Cancelar_Click
	StartActivity("Menu")
	Activity.Finish
End Sub