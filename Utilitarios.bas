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
	Dim Button_Calculadora As Button
	Dim Button_Graficos As Button
	Dim Button_Excluir As Button
	Dim Button_Voltar As Button
	Private Button_Exportar As Button
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
	Dim result As Int
	Dim user As String
	user = Main.Pers.Logado
	result = Msgbox2("Deseja excluir todos os dados de Transações e Categorias?","Fine","Sim, tenho certeza.","Cancelar","",LoadBitmap(File.DirAssets,"fineico.png"))
	
	If result = DialogResponse.POSITIVE Then
		File.Delete(File.DirInternal,user&"saldo.txt")
		File.Delete(File.DirInternal,user&"categ.txt")
		File.Delete(File.DirInternal,user&"transacoes.txt")
		Msgbox2("Dados excluidos","Fine","Ok","","",LoadBitmap(File.DirAssets,"fineico.png"))
	End If
	
End Sub

Sub Button_Voltar_Click
	Activity.Finish	
End Sub

Sub Button_Exportar_Click
	Dim str As StringUtils
	Dim headers,trans,lista As List
	headers.Initialize
	trans.Initialize
	lista.Initialize
	headers.AddAll(Array As String("Valor","Data","Categoria"))
	trans = Main.Pers.GetTransacoes(Main.Pers.Logado)
	
	For i = 0 To trans.Size -1
		Dim linha1,linha2,linha3,linha4 As String
		Dim arr(3) As String
		linha1 = trans.Get(i)
		linha2 = linha1.SubString2(0,linha1.IndexOf(";"))
		linha3 = linha1.SubString2(linha1.IndexOf(";")+1,linha1.LastIndexOf(";"))
		linha4 = linha1.SubString(linha1.LastIndexOf(";")+1)
		arr(0) = linha2
		arr(1) = linha3
		arr(2) = linha4
		lista.add(arr)
	Next

	If Not(File.Exists(File.DirRootExternal,"Fine")) Then
		File.MakeDir(File.DirRootExternal,"Fine")
		str.SaveCSV2(File.DirRootExternal&"/Fine","Transacoes.csv",",",lista,headers)
		Msgbox2("Dados exportados com sucesso!","Fine","Ok","","",LoadBitmap(File.DirAssets,"fineico.png"))
	Else
		str.SaveCSV2(File.DirRootExternal&"/Fine","Transacoes.csv",",",lista,headers)
		Msgbox2("Dados exportados com sucesso!","Fine","Ok","","",LoadBitmap(File.DirAssets,"fineico.png"))
	End If
	
End Sub