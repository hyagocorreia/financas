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
	Dim Button_Calculadora As Button
	Dim Button_Graficos As Button
	Dim Button_Excluir As Button
	Dim Button_Voltar As Button
End Sub

Sub Activity_Create(FirstTime As Boolean)	
	Activity.LoadLayout("Layout_Utilitarios")
End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub

Sub Button_Calculadora_Click
	StartActivity("Calculadora")
End Sub

Sub Button_Graficos_Click
	StartActivity("Graficos")
End Sub

Sub Button_Excluir_Click
	StartActivity("Excluir")
End Sub

Sub Button_Voltar_Click
	Activity.Finish	
End Sub