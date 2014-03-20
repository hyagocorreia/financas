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
	Private Categoria As Spinner
	Dim result As Int
	Dim Valor As EditText
	Dim Button_Voltar As Button
	Dim Button_Debitar As Button
	Dim Data As EditText
	Private Button_add As Button
	
End Sub

Sub Activity_Create(FirstTime As Boolean)
	Activity.LoadLayout("Layout_Debitos")
	DateTime.DateFormat = "dd/MM/yy"
	Dim Data_hoje As String = DateTime.Date(DateTime.Now)
	Data.Text = Data_hoje
	'Lista.Lista_Categorias.Initialize
	
	Categoria.AddAll(Lista.Lista_Categorias)
	
'	If Lista.Lista_Categorias.Size > 1 Then
'		
'	End If
	
End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub

Sub Button_Voltar_Click
	Activity.Finish	
End Sub

Sub Button_Debitar_Click
	If Valor.Text = "" Then
		Msgbox("Campos Obrigatórios não Preenchidos", "Atenção!")
	Else
		Dim Valor_final As Float
		Valor_final = Valor.Text
		
		Msgbox("Valor: " & NumberFormat(Valor_final,1,2) & CRLF & "Categoria: " & Categoria.SelectedItem & CRLF & "Data: " &Data.Text,"Debitado com Sucesso!")
		
		Main.Pers.Salvar_Transacao(NumberFormat(Valor_final,1,2), Data.Text, Categoria.SelectedItem, "Débito")
	
		result = Msgbox2("Deseja fazer outra operação?","Aviso!","Sim","","Nao",Null)

		If result = DialogResponse.POSITIVE Then
			StartActivity("Debito")
		Else
			StartActivity("Financeiro")
			Activity.Finish
		End If
	End If
End Sub

'Sub limita_campo(texto As String, qte_caracteres As Int) As String
'	If texto.Length > qte_caracteres Then
'		texto = texto.SubString2(1,qte_caracteres)	
'	End If	
'	Return texto
'End Sub

Sub Button_add_Click
	Activity.Finish
	AddCategoria.nome_classe = "débito"
	StartActivity("AddCategoria")
End Sub