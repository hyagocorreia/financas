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
	Private TabHost_Grafico As TabHost
	Private pnlLine1,pnlLine2 As Panel
	Private Button_Voltar As Button
End Sub

Sub Activity_Create(FirstTime As Boolean)
	Activity.Title = "Gráficos"
	Activity.TitleColor = Colors.RGB(139,0,0)
	Activity.Color = Colors.Black
	TabHost_Grafico.Initialize("TabHost_Grafico")
	Button_Voltar.Initialize("Button_Voltar")
	pnlLine1.Initialize("pnlLine1")
	pnlLine2.Initialize("pnlLine2")
	Activity.AddView(TabHost_Grafico, 0, 0, 100%x, 100%y)	
	CriarGraficoTrans_por_Mes
	CriarGraficoTrans_por_Categ
	CriarBotaoVoltar
End Sub

Sub CriarBotaoVoltar
	Dim Cor As ColorDrawable
	Cor.Initialize(Colors.RGB(139,0,0),5)
	Button_Voltar.Background = Cor
	Button_Voltar.TextSize = 18
	Button_Voltar.Text = "Voltar"
	Activity.AddView(Button_Voltar, 170dip, 95%y-40dip, 140dip, 50dip)
End Sub

Sub CriarGraficoTrans_por_Mes
	Dim p As Panel
	p.Initialize("")
	p.AddView(pnlLine1, 0, 0, 95%x, 100%y - 125dip)
	TabHost_Grafico.AddTab2("Por Mês", p)
	Dim LD As LineData
	LD.Initialize
	LD.Target = pnlLine1
	Charts.AddLineColor(LD, Colors.Red)
	
	Dim linha1, mes_linha As String
	Dim x, j1,j2,j3,j4,j5,j6,j7,j8,j9,j10,j11,j12, mes As Int
	Dim lista As List
	lista = Main.Pers.GetTransacoes(Main.Pers.Logado)
	x = lista.Size
	
	For i = 0 To x -1
		linha1 = lista.Get(i)
		mes_linha = linha1.SubString2(linha1.IndexOf("/")+1,linha1.LastIndexOf("/"))
		mes = mes_linha
		If mes = 1 Then
			j1 = j1+1
		Else If mes = 2 Then
			j2 = j2+1
		Else If mes = 3 Then
			j3 = j3+1
		Else If mes = 4 Then
			j4 = j4+1
		Else If mes = 5 Then
			j5 = j5+1
		Else If mes = 6 Then
			j6 = j6+1
		Else If mes = 7 Then
			j7 = j7+1
		Else If mes = 8 Then
			j8 = j8+1
		Else If mes = 9 Then
			j9 = j9+1
		Else If mes = 10 Then
			j10 = j10+1
		Else If mes = 11 Then
			j11 = j11+1
		Else If mes = 12 Then
			j12 = j12+1
		End If
	Next
	
	Charts.AddLinePoint(LD, "Jan", j1, True)
	Charts.AddLinePoint(LD, "Fev", j2, True)
	Charts.AddLinePoint(LD, "Mar", j3, True)
	Charts.AddLinePoint(LD, "Abr", j4, True)
	Charts.AddLinePoint(LD, "Mai", j5, True)
	Charts.AddLinePoint(LD, "Jun", j6, True)
	Charts.AddLinePoint(LD, "Jul", j7, True)
	Charts.AddLinePoint(LD, "Ago", j8, True)
	Charts.AddLinePoint(LD, "Set", j9, True)
	Charts.AddLinePoint(LD, "Out", j10, True)
	Charts.AddLinePoint(LD, "Nov", j11, True)
	Charts.AddLinePoint(LD, "Dez", j12, True)
	
	Dim G As Graph
	G.Initialize
	G.Title = "Transações por Mês"
	G.XAxis = ""
	G.YAxis = "Qtde de Transações"
	G.YStart = 0
	G.YEnd = x + 1
	G.YInterval = 1
	G.AxisColor = Colors.White
	Charts.DrawLineChart(G, LD, Colors.Black)
End Sub

Sub CriarGraficoTrans_por_Categ
	Dim p As Panel
	p.Initialize("")
	p.AddView(pnlLine2, 0, 0, 95%x, 100%y - 125dip)
	TabHost_Grafico.AddTab2("Por Categoria", p)
	Dim LD As LineData
	LD.Initialize
	LD.Target = pnlLine2
	Charts.AddLineColor(LD, Colors.Red)
	
	Dim j1 As Int
	Dim listaCateg, listaTrans As List
	listaCateg = Main.Pers.GetCategorias
	listaTrans = Main.Pers.GetTransacoes(Main.Pers.Logado)
	
	For Each categ As String In listaCateg
		j1 = 0
		For Each trans As String In listaTrans
			Dim str As String
			str = trans.SubString(trans.LastIndexOf(";")+1)
			If categ = str Then
				j1 = j1+1
			End If
		Next
		If categ = "Selecione a categoria" Then
	
		Else
			Charts.AddLinePoint(LD, categ, j1, True)
		End If
	Next
	
	Dim G As Graph
	G.Initialize
	G.Title = "Transações por Categoria"
	G.XAxis = ""
	G.YAxis = "Qtde de Transações"
	G.YStart = 0
	G.YEnd = listaTrans.Size+1
	G.YInterval = 1
	G.AxisColor = Colors.White
	Charts.DrawLineChart(G, LD, Colors.Black)
End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub

Sub Button_Voltar_Click
	Dim Cor As ColorDrawable
	Cor.Initialize(Colors.RGB(220, 20, 60),5)
	Button_Voltar.Background = Cor
	Activity.Finish
End Sub

Sub Voltar_Click
	Activity.Finish
End Sub