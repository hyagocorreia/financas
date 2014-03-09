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
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.

	Private Username As EditText
	Private Senha As EditText
	Private Button_Excluir As Button
	Private Button_Voltar As Button
End Sub

Sub Activity_Create(FirstTime As Boolean)
	Activity.LoadLayout("Layout_Excluir")
End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub

Sub Button_Excluir_Click
	If Username.Text = "" OR Senha.Text = "" Then
		Msgbox("Campos Obrigatórios não Preenchidos", "Atenção!")
	Else
		Dim TextReader1 As TextReader
    	TextReader1.Initialize(File.OpenInput(File.DirRootExternal, "Logins.txt"))
    	Dim line As String
		Dim texto As List
		texto.Initialize
    	line = TextReader1.ReadLine    
    	Do While line <> Null
			If line = Username.Text & Senha.Text Then
				If File.Delete(File.DirRootExternal, "Logins.txt") Then
					Msgbox("","Deletado com sucesso!")
					Dim TextWriter1 As TextWriter
   					TextWriter1.Initialize(File.OpenOutput(File.DirRootExternal, "Logins.txt", True))
					TextWriter1.WriteList(texto)
    				TextWriter1.Close
					TextReader1.Close
					Activity.Finish
				End If
			Else
				texto.Add(line)
        		line = TextReader1.ReadLine
			End If
    	Loop
	End If
End Sub

Sub Button_Voltar_Click
	Activity.Finish
End Sub