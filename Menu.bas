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
	Dim Button_Financeiro As Button
	Dim Button_sair As Button
	Dim Button_Utilitarios As Button
	Dim ImageView_Foto As ImageView
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	Activity.LoadLayout("Layout_Menu")

End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub

Sub Button_Financeiro_Click
StartActivity("Financeiro")
	
End Sub
Sub Button_sair_Click
ExitApplication

End Sub
Sub Button_Utilitarios_Click
StartActivity("Utilitários")
	
End Sub
