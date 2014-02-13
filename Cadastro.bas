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
	Dim Conta As EditText
	Dim Telefone As EditText
	Dim Endereco As EditText
	Dim Button_Voltar As Button
	Dim Button_Salvar As Button
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
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
	If Nome.Text = "" OR Conta.Text = "" OR Telefone.Text = "" OR Endereco.Text = "" Then
		Msgbox("Campos Obrigatórios não Preenchidos", "Atenção!")
	Else
	Msgbox("Nome:" & Nome.Text & CRLF & "Conta:" & Conta.Text & CRLF & "Telefone:" & Telefone.Text & CRLF & "Endereco:" & Endereco.Text,"Cadastrado com Sucesso")
	End If
End Sub