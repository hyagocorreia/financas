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
	Dim result As Int
	Dim Valor As EditText
	Dim Button_Creditar As Button
	Dim Button_Voltar As Button
	Dim Data As EditText
	Private Referente As EditText
	Private Categoria As Spinner
End Sub

Sub Activity_Create(FirstTime As Boolean)
	Activity.LoadLayout("Layout_Creditos")
	DateTime.DateFormat = "dd/MM/yy"
	Dim Data_hoje As  String = DateTime.Date(DateTime.Now)
	Data.Text = Data_hoje
End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub



Sub Button_Creditar_Click
	If Valor.Text = "" OR Referente.Text = "" Then 
		Msgbox("Campos Obrigatorios não estão preenchidos", "Aviso!" )
	Else
		Msgbox("Valor: "&Valor.Text&CRLF&"Referente: "&CRLF&Referente.Text&CRLF&"Data: "&Data.Text,"Creditado com Sucesso!")
	
		Dim xValor As Float = Valor.Text
		
		Financeiro.saldo = Financeiro.saldo + xValor 
		
		Dim linha_extrato As String = Data.Text & " " & "(+)" & xValor & "    " & Limita_Campo(Referente.Text, 9)
		
		Financeiro.list_Extrato.Add(linha_extrato)
		
		result = Msgbox2("Deseja fazer outra operação?","Aviso!","Sim","","Nao",Null)
			
			If result = DialogResponse.POSITIVE Then
				StartActivity("Creditos")
			
			Else
				StartActivity("Financeiro")			
	
			End If
	
	End If
End Sub

Sub Button_Voltar_Click
	Activity.Finish	
End Sub

Sub Limita_Campo(texto As String, qte_caracteres As Int) As String
	If texto.Length > qte_caracteres Then
		texto = texto.SubString2(1,qte_caracteres)	
	End If
	Return texto
End Sub

Sub Categoria_ItemClick (Position As Int, Value As Object)
	StartActivity(Categoria)
	Categoria.Add("Água")
	Categoria.Add("Gás")
	Categoria.Add("Luz")
	Categoria.Add("Combustível")
	Categoria.Add("Vestuário")
	Categoria.Add("Alimentação")
	Categoria.Add("Móveis")
	Categoria.Add("Materiais De Consatrução")
End Sub