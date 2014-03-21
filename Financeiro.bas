Type=Activity
Version=3.2
@EndOfDesignText@
#Region  Activity Attributes 
	#FullScreen: False
	#IncludeTitle: True
#End Region

Sub Process_Globals
	Dim list_Extrato As List
End Sub

Sub Globals
	Dim Button_Creditos As Button
	Dim Button_Debitos As Button
	Dim Button_Voltar As Button
	Dim Button_total As Button
	Dim Button_Extrato As Button
	Private Button_AddCategoria As Button
	Private Button_RemCategoria As Button
End Sub

Sub Activity_Create(FirstTime As Boolean)
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
	StartActivity("Debitos")
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

Sub Button_AddCategoria_Click
	StartActivity("AddCategoria")	
End Sub

Sub Button_RemCategoria_Click
	StartActivity("Remover_Categoria")
End Sub