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
	Dim Button_Voltar As Button
	Dim ListView_Extrato As ListView
	Dim Label_SaldoAtual As Label
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	 
	 'If FirstTime Then
		
		Activity.LoadLayout("Layout_Extrato")
	
		'Label_SaldoAtual.Text = Financeiro.saldo
	
		ListView_Extrato.AddSingleLine("    (-)Debito" & "      " & "(+)Credito")
		ListView_Extrato.AddSingleLine("| Data |  " & " | Valor |  " & " | Detalhe |")
		
		For i = 0 To Financeiro.list_Extrato.Size -1
			
			ListView_Extrato.AddSingleLine(Financeiro.list_Extrato.Get(i))
			ListView_Extrato.FastScrollEnabled = True
	
		Next
		
	'End If
	
	
   
End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub


Sub Button_Voltar_Click
	Activity.Finish
End Sub

Sub ListView_Extrato_ItemLongClick (Position As Int, Value As Object)
	If Msgbox2("Deseja excluir a transação?", "Excluir", "Sim", "", "Não", Null) = DialogResponse.POSITIVE Then
		Financeiro.list_Extrato.RemoveAt(Position-2)
		ListView_Extrato.RemoveAt(Position)
	End If
End Sub