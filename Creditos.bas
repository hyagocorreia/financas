﻿Type=Activity
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

	'Dim De As EditText
	Dim result As Int
	Dim Valor As EditText
	Dim Button_Creditar As Button
	Dim Button_Voltar As Button
	Dim Data As EditText
	Private Referente As EditText
	Private Categoria As Spinner
	
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
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
		
		'Msgbox(De.Text&CRLF&Valor.Text&CRLF&Observacao.Text&CRLF&Data.Text,"Creditado com sucesso")
		
		Dim xValor As Float = Valor.Text
		
		Financeiro.saldo = Financeiro.saldo + xValor 
		
		Dim linha_extrato As String = Data.Text & " " & "(+)" & xValor & "    " & Limita_campo(Referente.Text, 9)
		
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

Sub Limita_campo(texto As String, qte_caracteres As Int)
	If texto.Length > qte_caracteres Then
		texto = texto.SubString2(1,qte_caracteres)	
	End If
	
	Return texto
	
End Sub

Sub Categoria_ItemClick (Position As Int, Value As Object)
	
	Categoria.AddAll(Array As String("Água", "Gás", "Luz", "Combustível", "Vestuário", "Alimentação", "Móveis", "Materiais De Consatrução"))

End Sub
	