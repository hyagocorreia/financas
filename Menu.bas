Type=Activity
Version=3.2
@EndOfDesignText@
#Region  Activity Attributes 
	#FullScreen: False
	#IncludeTitle: False
#End Region

Sub Process_Globals

End Sub

Sub Globals
	Dim Button_Financeiro As Button
	Dim Button_sair As Button
	Dim Button_Utilitarios As Button
	Private Button_ExcluirConta As Button
	Private Button_Logout As Button
	Private Button_EditarConta As Button
End Sub

Sub Activity_Create(FirstTime As Boolean)
	Activity.LoadLayout("Layout_Menu")
End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub

Sub Button_Financeiro_Click
	StartActivity("Financeiro")
End Sub

Sub Button_sair_Click
	Activity.Finish
End Sub

Sub Button_Utilitarios_Click
	StartActivity("Utilitarios")
End Sub

Sub Button_ExcluirConta_Click
	StartActivity("Excluir")
End Sub

Sub Button_Logout_Click
	Main.Fazer_logout = True
	File.Delete(File.DirRootExternal&"/Fine/Data","logado.txt")
	StartActivity("Main")
	Activity.Finish
End Sub

Sub Button_EditarConta_Click
	StartActivity("Editar")
	Activity.Finish
End Sub