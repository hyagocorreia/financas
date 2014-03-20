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
	Dim nome_classe As String = ""
End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.
	
	Private Button_Adicionar As Button
	Private Button_Voltar As Button
	Private Categoria_Text As EditText
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	Activity.LoadLayout("Layout_AddCategoria")

End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub

Sub Button_Adicionar_Click
	
	Lista.Lista_Categorias.Add(Categoria_Text.Text)
	Activity.Finish
	If nome_classe = "débito" Then
		StartActivity("Debito")
	Else If nome_classe = "crédito" Then
		StartActivity("Creditos")
	Else
		StartActivity("Financeiro")
	End If
	
End Sub
Sub Button_Voltar_Click

	Activity.Finish
End Sub