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
	Dim operacao As String
	Dim Button_soma As Button
	Dim Button_Multiplicacao As Button
	Dim Button_subtracao As Button
	Dim Button_Divisao As Button
	Dim Button_igual As Button
	Dim numero1 As EditText
	Dim numero2 As EditText
	Dim resultado As Label
	Dim valor1 As Float
	Dim valor2 As Float 
	Dim Button_Voltar As Button
End Sub

Sub Activity_Create(FirstTime As Boolean)
	Activity.LoadLayout("Layout_Calculadora")
End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub

Sub Button_soma_Click

Button_soma.Color = Colors.Blue

operacao = "soma"
	
End Sub
Sub Button_Multiplicacao_Click

Button_Multiplicacao.Color = Colors.Blue

operacao = "multiplicacao"
	
End Sub
Sub Button_subtracao_Click

Button_subtracao.Color = Colors.Blue

operacao = "subtracao"
	
End Sub
Sub Button_Divisao_Click

Button_Divisao.Color = Colors.Blue

operacao = "divisao"
	
End Sub

Sub Button_igual_Click

If numero1.Text <> "" AND numero2.Text <> "" Then
	
	
	
	If operacao = "soma" Then
		valor1 = numero1.Text
		valor2 = numero2.Text
	
		resultado.Text = valor1 + valor2
		Button_soma.Color = Colors.DarkGray
	
Else If operacao = "subtracao" Then
	valor1 = numero1.Text
	valor2 = numero2.Text
	
	resultado.Text = valor1 - valor2
	Button_subtracao.Color = Colors.DarkGray
	
Else If operacao = "multiplicacao" Then
	valor1 = numero1.Text
	valor2 = numero2.Text
	
	resultado.Text = valor1 * valor2
	Button_Multiplicacao.Color = Colors.DarkGray
	
Else 
	
	valor1 = numero1.Text
	valor2 = numero2.Text
	
	resultado.Text = valor1 / valor2
	Button_Divisao.Color = Colors.DarkGray
	
End If

Else
	Msgbox("Digite os valores", "Atenção!")
	
	End If
	numero1.Text = ""
	numero2.Text = ""
	
End Sub

Sub Button_Voltar_Click
	Activity.Finish
End Sub