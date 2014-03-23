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
	Dim ListView_Extrato1 As ListView
	Dim ListView_Extrato2 As ListView
	Dim ListView_Extrato3 As ListView
	Private Label_SaldoAtual As Label
	Private TabHost_Transacoes As TabHost
	Private Spinner_Categorias As Spinner
	Private Spinner_Meses As Spinner
	Private Label_Saldo_Mes As Label
End Sub

Sub Activity_Create(FirstTime As Boolean)
	Activity.LoadLayout("Layout_Extrato")

	Spinner_Categorias.AddAll(Lista.Lista_Categorias)
	Label_SaldoAtual.Text = "Saldo Atual (R$): " & NumberFormat(Main.Pers.Saldo,1,2)
	
	For i = 0 To Main.Pers.Lista_Extrato.Size -1
		Dim linha1,linha2,linha3,linha4 As String
		linha1 = Main.Pers.Lista_Extrato.Get(i)
		linha2 = linha1.SubString2(0,linha1.IndexOf("|"))
		linha3 = linha1.SubString2(linha1.IndexOf("|")+1,linha1.LastIndexOf("|"))
		linha4 = linha1.SubString(linha1.LastIndexOf("|")+1)
		Dim valor As Double
		valor = linha2
		If valor < 0 Then
			ListView_Extrato1.AddTwoLinesAndBitmap("R$"&NumberFormat2((valor*(-1)),1,2,2,True), linha3 & " - " & linha4,LoadBitmap(File.DirAssets,"debito.png"))
		Else
			ListView_Extrato1.AddTwoLinesAndBitmap("R$"&NumberFormat2(linha2,1,2,2,True), linha3 & " - " & linha4,LoadBitmap(File.DirAssets,"credito.png"))
		End If
		ListView_Extrato1.FastScrollEnabled = True
	Next
	
	Dim listinha As List
	listinha.Initialize
	listinha.AddAll(Array As String("Selecione o mês","Janeiro","Fevereiro","Março","Abril","Maio","Junho","Julho","Agosto","Setembro","Outubro","Novembro","Dezembro"))
	Spinner_Meses.AddAll(listinha)
	TabHost_Transacoes.SetBackgroundImage(LoadBitmap(File.DirAssets,"iconest.png"))
	TabHost_Transacoes.AddTab2("",ListView_Extrato1)
	TabHost_Transacoes.AddTab2("",Spinner_Meses)
	TabHost_Transacoes.AddTab2("",Spinner_Categorias)

End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub

Sub Button_Voltar_Click
	Activity.Finish
End Sub

Sub ListView_Extrato1_ItemLongClick (Position As Int, Value As Object)
	If Msgbox2("Deseja excluir a transação?", "Excluir", "Sim", "", "Não", Null) = DialogResponse.POSITIVE Then
		Main.Pers.Remover_Transacao(Position)
		ListView_Extrato1.RemoveAt(Position)
		Label_SaldoAtual.Text = Main.Pers.Saldo
	End If
End Sub

Sub Spinner_Meses_ItemClick (Position As Int, Value As Object)
	Dim linha1,linha2,linha3,linha4 As String
	Dim mes_linha As String
	
	ListView_Extrato2.Clear
	
	For i = 0 To Main.Pers.Lista_Extrato.Size -1
		linha1 = Main.Pers.Lista_Extrato.Get(i)
		linha2 = linha1.SubString2(0,linha1.IndexOf("|"))
		linha3 = linha1.SubString2(linha1.IndexOf("|")+1,linha1.LastIndexOf("|"))
		linha4 = linha1.SubString(linha1.LastIndexOf("|")+1)
		mes_linha = linha1.SubString2(linha1.IndexOf("/")+1,linha1.LastIndexOf("/"))
		Dim mes_ As Int = mes_linha
		If mes_ = Position Then
			Dim valor As Double
			valor = linha2
			If valor < 0 Then
				ListView_Extrato2.AddTwoLinesAndBitmap("R$"&NumberFormat2((valor*(-1)),1,2,2,True), linha3 & " - " & linha4,LoadBitmap(File.DirAssets,"debito.png"))
			Else
				ListView_Extrato2.AddTwoLinesAndBitmap("R$"&NumberFormat2(linha2,1,2,2,True), linha3 & " - " & linha4,LoadBitmap(File.DirAssets,"credito.png"))
			End If
			
			Label_Saldo_Mes.Text = "Saldo do Mês (R$): "& NumberFormat(valor,1,2)
			Label_Saldo_Mes.Visible = True
			ListView_Extrato2.FastScrollEnabled = True
			ListView_Extrato2.Visible = True
		End If
	Next
End Sub

Sub Spinner_Categorias_ItemClick (Position As Int, Value As Object)
	Dim linha1,linha2,linha3,linha4,l4 As String
	Dim Str As String = Spinner_Categorias.GetItem(Position)
	l4 = Str.SubString(Str.LastIndexOf("|")+1)

	ListView_Extrato3.Clear
	
	For i = 0 To Main.Pers.Lista_Extrato.Size -1
		linha1 = Main.Pers.Lista_Extrato.Get(i)
		linha2 = linha1.SubString2(0,linha1.IndexOf("|"))
		linha3 = linha1.SubString2(linha1.IndexOf("|")+1,linha1.LastIndexOf("|"))
		linha4 = linha1.SubString(linha1.LastIndexOf("|")+1)
		If linha4 = l4 Then
			Dim valor As Double
			valor = linha2
			If valor < 0 Then
				ListView_Extrato3.AddTwoLinesAndBitmap("R$"&NumberFormat((valor*(-1)),1,2), linha3 & " - " & linha4,LoadBitmap(File.DirAssets,"debito.png"))
			Else
				ListView_Extrato3.AddTwoLinesAndBitmap("R$"&NumberFormat(linha2,1,2), linha3 & " - " & linha4,LoadBitmap(File.DirAssets,"credito.png"))
			End If
			
			ListView_Extrato3.FastScrollEnabled = True
			ListView_Extrato3.Visible = True
		End If
	Next		
End Sub

Sub TabHost_Transacoes_TabChanged
	If TabHost_Transacoes.CurrentTab = 0 Then
		ListView_Extrato3.Visible = False
		ListView_Extrato2.Visible = False
		Label_Saldo_Mes.Visible = False
		Label_SaldoAtual.Visible = True
	Else If TabHost_Transacoes.CurrentTab = 1 Then
		ListView_Extrato3.Visible = False
		ListView_Extrato1.Visible = False
		Label_SaldoAtual.Visible = False
		Label_Saldo_Mes.Visible = True
	Else If TabHost_Transacoes.CurrentTab = 2 Then
		ListView_Extrato2.Visible = False
		ListView_Extrato1.Visible = False
		Label_Saldo_Mes.Visible = False
	End If
End Sub

'Sub ListView_Extrato2_ItemLongClick (Position As Int, Value As Object)
'	If Msgbox2("Deseja excluir a transação?", "Excluir", "Sim", "", "Não", Null) = DialogResponse.POSITIVE Then
'		Main.Pers.Remover_Transacao2(Value)
'		ListView_Extrato2.RemoveAt(Position)
'		Label_SaldoAtual.Text = Main.Pers.Saldo
'	End If
'End Sub
'
'Sub ListView_Extrato3_ItemLongClick (Position As Int, Value As Object)
'	If Msgbox2("Deseja excluir a transação?", "Excluir", "Sim", "", "Não", Null) = DialogResponse.POSITIVE Then
'		Main.Pers.Remover_Transacao2(Value)
'		ListView_Extrato3.RemoveAt(Position)
'		Label_SaldoAtual.Text = Main.Pers.Saldo
'	End If
'End Sub