Type=Activity
Version=3
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

	Dim Para As EditText
	Dim Valor As EditText
	Dim Observacao As EditText
	Dim Button_Voltar As Button
	Dim Button_Debitar As Button
	Dim Data As EditText
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	Activity.LoadLayout("Layout_Debitos")
	DateTime.DateFormat = "dd/MM/yy"
	Dim Data_hoje As String = DateTime.Date(DateTime.Now)
	Data.Text = Data_hoje

End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub

Sub Button_Voltar_Click
	Activity.Finish
	
End Sub
Sub Button_Debitar_Click
	If Para.Text = "" OR Valor.Text = "" OR Observacao.Text = "" Then
		Msgbox("Campos Obrigatórios não Preenchidos", "Atenção!")
	Else
		Msgbox("Nome:" & Para.Text& CRLF & "Valor:" &Valor.Text & CRLF & "Observação:" & CRLF&Observacao.Text & CRLF & "Data:" &Data.Text,"Debitado com Sucesso")
		Dim xValor As Float = Valor.Text
		Financeiro.saldo = Financeiro.saldo - xValor 
		
		Dim linha_extrato As String = Data.Text & " " & "(-)" & xValor & "    " & limita_campo(Observacao.Text, 9)
		
		Financeiro.list_Extrato.Add(linha_extrato)
		
		
		
	End If
	
End Sub

Sub limita_campo(texto As String, qte_caracteres As Int)
	If texto.Length > qte_caracteres Then
		texto = texto.SubString2(1,qte_caracteres)	
	End If
	
	Return texto
	
	

End Sub

