Type=Activity
Version=3.2
@EndOfDesignText@
#Region  Activity Attributes 
	#FullScreen: False
	#IncludeTitle: True
#End Region

Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.

End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.

	Dim Nome As EditText
	Dim Button_Voltar As Button
	Dim Button_Salvar As Button
	Private Username As EditText
	Private Senha As EditText
	Private Senha_Repetida As EditText
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout File created with the visual designer. For example:
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
		Msgbox("Campos Obrigatórios não Preenchidos", "Atenção!")
	Else If Not(Senha.Text = Senha_Repetida.Text) Then
		Msgbox("Senhas não conferem!", "Atenção!")
	Else
		Dim TextWriter1 As TextWriter
   		TextWriter1.Initialize(File.OpenOutput(File.DirRootExternal, "Logins.txt", False))
		TextWriter1.WriteLine(Username.Text & Senha.Text)
    	TextWriter1.Close
		Msgbox("Nome:" & Nome.Text & CRLF,"Cadastrado com Sucesso")
	End If
End Sub
