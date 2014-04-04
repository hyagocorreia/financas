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
	Dim Nome As EditText
	Dim Button_Voltar As Button
	Dim Button_Salvar As Button
	Private Username As EditText
	Private Senha As EditText
	Private Senha_Repetida As EditText
End Sub

Sub Activity_Create(FirstTime As Boolean)
	Activity.LoadLayout("Layout_Cadastro")
End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub

Sub Button_Voltar_Click
	Activity.Finish
End Sub

Sub Button_Salvar_Click
	If Nome.Text = "" OR Username.Text = "" OR Senha.Text = "" OR Senha_Repetida.Text = "" Then
		Msgbox2("Dados preenchidos incorretamente!","Fine","Ok","","",LoadBitmap(File.DirAssets,"fineico.png"))
	Else If Senha.Text <> Senha_Repetida.Text Then
		Msgbox2("Senhas não conferem!","Fine","Ok","","",LoadBitmap(File.DirAssets,"fineico.png"))
	Else
		If Main.Pers.Criar_Login(Nome.Text, Username.Text, Senha.Text, Senha_Repetida.Text) Then
			Msgbox2("Nome: " & Nome.Text & CRLF & "Username: " & Username.Text&CRLF&"Cadastrado com Sucesso!","Fine","Ok","","",LoadBitmap(File.DirAssets,"fineico.png"))
			Activity.Finish
		End If
	End If
End Sub