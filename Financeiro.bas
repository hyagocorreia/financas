﻿Type=Activity
Version=3.2
@EndOfDesignText@
#Region  Activity Attributes 
	#FullScreen: False
	#IncludeTitle: True
#End Region

Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.
	Dim saldo As Float
	Dim list_Extrato As List
	
End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.
	
	Dim Button_Creditos As Button
	Dim Button_Debitos As Button
	Dim Button_Voltar As Button
	Dim Button_total As Button
	Dim Button_Extrato As Button
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	Activity.LoadLayout("Layout_Financeiro")
	If list_Extrato.IsInitialized = False Then
		list_Extrato.Initialize()
	End If
	
End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub

Sub Button_Creditos_Click

	StartActivity("Creditos")

End Sub

Sub Button_Debitos_Click
	StartActivity("Debito")
End Sub

Sub Button_Voltar_Click

	Activity.Finish	

End Sub
Sub Button_total_Click
	StartActivity("Total")
	
End Sub
Sub Button_Extrato_Click
	StartActivity("Extrato")
	
End Sub