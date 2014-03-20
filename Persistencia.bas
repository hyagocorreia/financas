Type=Class
Version=3.2
@EndOfDesignText@

Sub Class_Globals
	Dim Lista_Extrato As List
	Lista_Extrato.Initialize
	Dim Saldo As Float
End Sub

Public Sub Initialize
	
End Sub

Public Sub Fazer_Login (Username As String, Senha As String) As Boolean
	If Username = "" OR Senha = "" Then 
		Msgbox("Campos Obrigatorios não estão preenchidos", "Aviso!" )
	Else
		If Not(File.Exists(File.DirRootExternal, "Logins.txt")) Then
			Dim TextWriter1 As TextWriter
   			TextWriter1.Initialize(File.OpenOutput(File.DirRootExternal, "Logins.txt", False))
			TextWriter1.Close
		Else
			Dim TextReader1 As TextReader
	    	TextReader1.Initialize(File.OpenInput(File.DirRootExternal, "Logins.txt"))
	    	Dim line As String
	    	line = TextReader1.ReadLine    
	    	Do While line <> Null
	        	If line = Username & Senha Then
					Return True
				End If
	        	line = TextReader1.ReadLine
	    	Loop
	    	TextReader1.Close
		End If
	End If
	
	Return False

End Sub

Public Sub Criar_Login(Nome As String, Username As String, Senha As String, Senha_Repetida As String) As Boolean
	If Nome = "" OR Username = "" OR Senha = "" OR Senha_Repetida = "" Then
		Msgbox("Campos Obrigatórios não Preenchidos", "Atenção!")
	Else If Not(Senha = Senha_Repetida) Then
		Msgbox("Senhas não conferem!", "Atenção!")
	Else
		Dim TextWriter1 As TextWriter
   		TextWriter1.Initialize(File.OpenOutput(File.DirRootExternal, "Logins.txt", True))
		TextWriter1.WriteLine(Username & Senha)
    	TextWriter1.Close
		Return True
	End If
	Return False
End Sub

Public Sub Excluir_Login(Username As String, Senha As String) As Boolean 
	If Username = "" OR Senha = "" Then
		Msgbox("Campos Obrigatórios não Preenchidos", "Atenção!")
	Else
		Dim TextReader1 As TextReader
    	TextReader1.Initialize(File.OpenInput(File.DirRootExternal, "Logins.txt"))
    	Dim line As String
		Dim texto As List
		texto.Initialize
    	line = TextReader1.ReadLine    
    	Do While line <> Null
			If line = Username & Senha Then
				If File.Delete(File.DirRootExternal, "Logins.txt") Then
					Dim TextWriter1 As TextWriter
   					TextWriter1.Initialize(File.OpenOutput(File.DirRootExternal, "Logins.txt", True))
					TextWriter1.WriteList(texto)
    				TextWriter1.Close
					TextReader1.Close
					Return True
				End If
			Else
				texto.Add(line)
        		line = TextReader1.ReadLine
			End If
    	Loop
	End If
	
	Return False
End Sub

Private Sub Atualizar_Saldo(Valor As Float)
	Saldo = Saldo + Valor
End Sub

Public Sub Salvar_Transacao (Valor As Float, Data As String, Categoria As String, Tipo As String) As Boolean
	
	If Tipo = "Crédito" Then
		Atualizar_Saldo(Valor)
	Else
		Valor = Valor * (-1)
		Atualizar_Saldo(Valor)
	End If
	Lista_Extrato.Add(NumberFormat(Valor,1,2) & "|" & Data & "|" & Categoria)

	Return True
End Sub

Public Sub Remover_Transacao (Pos As Int)
	Dim Linha As String = Lista_Extrato.Get(Pos)
	Dim i As Int = Linha.IndexOf("|")
	Dim Valor As Float = Linha.SubString2(0,i)
	
	Saldo = Saldo - Valor

	Lista_Extrato.RemoveAt(Pos)
End Sub

Public Sub Remover_Categoria(Pos As Int)
	
End Sub