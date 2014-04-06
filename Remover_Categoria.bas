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
	Private ListView_Categorias As ListView
	Private Button_Voltar As Button
End Sub

Sub Activity_Create(FirstTime As Boolean)
	Activity.LoadLayout("Layout_Remover_Categoria")

	For i = 0 To Main.Pers.GetCategorias.Size -1
		ListView_Categorias.AddSingleLine(Main.Pers.GetCategorias.Get(i))
		ListView_Categorias.FastScrollEnabled = True
	Next
End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub

Sub Button_Voltar_Click
	Activity.Finish
End Sub

Sub ListView_Categorias_ItemClick (Position As Int, Value As Object)
	If Msgbox2("Deseja excluir a categoria?", "Excluir", "Sim", "", "Não", Null) = DialogResponse.POSITIVE Then
		If Main.Pers.Deletar_Categoria(Value) = "True" Then
			Msgbox2("Categoria: " & Value & " foi removida com sucesso!","Fine","Ok","","",LoadBitmap(File.DirAssets,"fineico.png"))
			ListView_Categorias.RemoveAt(Position)
		Else If Main.Pers.Deletar_Categoria(Value) = "False" Then
			Msgbox2("Categorias predefinidas não podem ser deletadas: " & Value,"Fine","Ok","","",LoadBitmap(File.DirAssets,"fineico.png"))
		Else 
			Msgbox2("Existe uma Transação associada a categoria:" & Value,"ATENÇÃO!","Ok","","",LoadBitmap(File.DirAssets,"fineico.png"))
		End If
	End If	
End Sub