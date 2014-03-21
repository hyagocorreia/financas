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
	Label_Titulo.Text = "Transações"
	
	For i = 0 To Main.Pers.Lista_Extrato.Size -1
		Dim linha1,linha2,linha3,linha4 As String
		linha1 = Main.Pers.Lista_Extrato.Get(i)
		linha2 = linha1.SubString2(0,linha1.IndexOf("|"))
		linha3 = linha1.SubString2(linha1.IndexOf("|")+1,linha1.LastIndexOf("|"))
		linha4 = linha1.SubString(linha1.LastIndexOf("|")+1)
		Dim valor As Double
		valor = linha2
		If valor < 0 Then
			ListView_Extrato.AddTwoLinesAndBitmap("R$"&NumberFormat((valor*(-1)),1,2), linha3 & " - " & linha4,LoadBitmap(File.DirAssets,"debito.png"))
		Else
			ListView_Extrato.AddTwoLinesAndBitmap("R$"&NumberFormat(linha2,1,2), linha3 & " - " & linha4,LoadBitmap(File.DirAssets,"credito.png"))
		End If
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