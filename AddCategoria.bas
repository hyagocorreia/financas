Type=Activity
Version=3.2
@EndOfDesignText@
#Region  Activity Attributes 
	#FullScreen: False
	#IncludeTitle: True
#End Region

Sub Process_Globals
	Dim nome_classe As String = ""
End Sub

Sub Globals
	Private Button_Adicionar As Button
	Private Button_Voltar As Button
	Private Categoria_Text As EditText
End Sub

Sub Activity_Create(FirstTime As Boolean)
	Activity.LoadLayout("Layout_AddCategoria")
End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub

Sub Button_Adicionar_Click
	If Main.Pers.Salvar_Categoria(Categoria_Text.Text) Then
		Msgbox2("Categoria adicionada com sucesso!","Fine","Ok","","",LoadBitmap(File.DirAssets,"fineico.png"))
	Else
		Msgbox2("Erro ao adicionar categoria!","Fine","Ok","","",LoadBitmap(File.DirAssets,"fineico.png"))
	End If
	Activity.Finish
End Sub
Sub Button_Voltar_Click
	Activity.Finish
End Sub