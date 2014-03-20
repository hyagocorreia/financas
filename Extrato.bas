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
	Dim Button_Voltar As Button
	Dim ListView_Extrato As ListView
	Private Label_Titulo As Label
	Private Label_SaldoAtual As Label
End Sub

Sub Activity_Create(FirstTime As Boolean)
	Activity.LoadLayout("Layout_Extrato")
	Label_SaldoAtual.Text = "Saldo Atual: " & NumberFormat(Main.Pers.Saldo,1,2)
	Label_Titulo.Text = "Valor R$ | Data | Categ."
	
	For i = 0 To Main.Pers.Lista_Extrato.Size -1
		ListView_Extrato.AddSingleLine(Main.Pers.Lista_Extrato.Get(i))
		ListView_Extrato.FastScrollEnabled = True
	Next
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
		Main.Pers.Remover_Transacao(Position)
		ListView_Extrato.RemoveAt(Position)
		Label_SaldoAtual.Text = Main.Pers.Saldo
	End If
End Sub