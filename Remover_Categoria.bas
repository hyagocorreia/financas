Type=Activity
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
	Private ListView_Categorias As ListView
	Private Button_Voltar As Button
End Sub

Sub Activity_Create(FirstTime As Boolean)
	Activity.LoadLayout("Layout_Remover_Categoria")

	For i = 0 To Lista.Lista_Categorias.Size -1
		ListView_Categorias.AddSingleLine(Lista.Lista_Categorias.Get(i))
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
		Lista.Lista_Categorias.RemoveAt(Position)
		ListView_Categorias.RemoveAt(Position)
	End If	
End Sub