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
	Private Button_add As Button
	Private Categoria As Spinner
End Sub

Sub Activity_Create(FirstTime As Boolean)
	Activity.LoadLayout("Layout_Creditos")
	DateTime.DateFormat = "dd/MM/yyyy"
	Data.Text = DateTime.Date(DateTime.Now)
	Categoria.AddAll(Main.Pers.GetCategorias)
End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub

Sub Button_Creditar_Click
	If Valor.Text = "" Then 
		Msgbox("Campos Obrigatorios não estão preenchidos", "Aviso!" )
	Else
		Dim Valor_final As Double
		Valor_final = Valor.Text
		
		Msgbox2("Valor: " & NumberFormat2(Valor_final,1,2,2,True) & CRLF & "Categoria: " & Categoria.SelectedItem & CRLF & "Data: " & Data.Text,"Creditado com Sucesso!","Ok","","",LoadBitmap(File.DirAssets,"fineico.png"))
		
		Main.Pers.Salvar_Transacao(Main.Pers.Logado,Valor_final, Data.Text, Categoria.SelectedItem, "Crédito")
		
		result = Msgbox2("Deseja fazer outra operação?","Fine","Sim","","Nao",LoadBitmap(File.DirAssets,"fineico.png"))
			
		If result = DialogResponse.POSITIVE Then
			StartActivity("Creditos")
		Else
			StartActivity("Financeiro")
			Activity.Finish
		End If
	End If
End Sub

Sub Button_Voltar_Click
	Activity.Finish	
End Sub

Sub Button_add_Click
	StartActivity("AddCategoria")
	AddCategoria.tipo ="creditos"
	Activity.Finish
End Sub