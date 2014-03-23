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
	    	Dim linha1,linha2,linha3,linha4 As String
			linha1 = TextReader1.ReadLine
			If linha1 = Null Then
				Return False
			End If
			linha2 = linha1.SubString2(0,linha1.IndexOf(";"))
			linha3 = linha1.SubString2(linha1.IndexOf(";")+1,linha1.LastIndexOf(";"))
			linha4 = linha1.SubString(linha1.LastIndexOf(";")+1)
			
			Do While linha1 <> Null
	        	If linha2 & linha3 = Username & Senha Then
					Return True
				End If
	        	linha1 = TextReader1.ReadLine
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
		If Not(File.Exists(File.DirRootExternal, "Logins.txt")) Then
			Dim TextWriter1 As TextWriter
			TextWriter1.Initialize(File.OpenOutput(File.DirRootExternal, "Logins.txt", True))
			TextWriter1.WriteLine(Username &";"& Senha &";"& Nome)
			TextWriter1.Close
			Return True
		Else
			Dim TextReader1 As TextReader
	    	TextReader1.Initialize(File.OpenInput(File.DirRootExternal, "Logins.txt"))
	    	Dim linha1,linha2,linha3,linha4 As String
			linha1 = TextReader1.ReadLine
			
			If linha1 = Null Then
				Dim TextWriter1 As TextWriter
				TextWriter1.Initialize(File.OpenOutput(File.DirRootExternal, "Logins.txt", True))
				TextWriter1.WriteLine(Username &";"& Senha &";"& Nome)
				TextWriter1.Close
				Return True
			End If
			
			linha2 = linha1.SubString2(0,linha1.IndexOf(";"))
			linha3 = linha1.SubString2(linha1.IndexOf(";")+1,linha1.LastIndexOf(";"))
			linha4 = linha1.SubString(linha1.LastIndexOf(";")+1)
			
			Do While linha1 <> Null
	        	If linha2 = Username Then
					Msgbox("Username já existe!","Atenção!")
					linha1 = TextReader1.ReadLine
				Else
					Dim TextWriter2 As TextWriter
					TextWriter2.Initialize(File.OpenOutput(File.DirRootExternal, "Logins.txt", True))
					TextWriter2.WriteLine(Username &";"& Senha &";"& Nome)
					TextWriter2.Close
					Return True
				End If
	    	Loop
	    	TextReader1.Close
		End If
	End If
	Return False
End Sub

Public Sub Excluir_Login(Username As String, Senha As String) As Boolean 
	If Username = "" OR Senha = "" Then
		Msgbox("Campos Obrigatórios não Preenchidos", "Atenção!")
	Else
		Dim TextReader1 As TextReader
    	TextReader1.Initialize(File.OpenInput(File.DirRootExternal, "Logins.txt"))
		
		Dim texto As List
		texto.Initialize
		
	    Dim linha1,linha2,linha3,linha4 As String
		linha1 = TextReader1.ReadLine
		linha2 = linha1.SubString2(0,linha1.IndexOf(";"))
		linha3 = linha1.SubString2(linha1.IndexOf(";")+1,linha1.LastIndexOf(";"))
		linha4 = linha1.SubString(linha1.LastIndexOf(";")+1)

		Do While linha1 <> Null
			If linha2 & linha3 = Username & Senha Then
				If File.Delete(File.DirRootExternal, "Logins.txt") Then
					Dim TextWriter1 As TextWriter
   					TextWriter1.Initialize(File.OpenOutput(File.DirRootExternal, "Logins.txt", True))
					If texto <> Null Then
						TextWriter1.WriteList(texto)
    				End If
					TextWriter1.Close
					TextReader1.Close
					Return True
				End If
			Else
				texto.Add(linha1)
        		linha1 = TextReader1.ReadLine
			End If
    	Loop
	End If
	
	Return False
End Sub

Private Sub Atualizar_Saldo(Valor As Float)
	Saldo = Saldo + Valor
End Sub

Public Sub Salvar_Transacao (valor As Object, Data As String, Categoria As String, Tipo As String) As Boolean
	Dim valor1 As Float
	valor1 = valor
	If Tipo = "Crédito" Then
		Atualizar_Saldo(valor1)
	Else
		valor1 = valor1 * (-1)
		Atualizar_Saldo(valor1)
	End If
	Lista_Extrato.Add(valor1 & "|" & Data & "|" & Categoria)

	Return True
End Sub

Public Sub Remover_Transacao (Pos As Int)
	Dim Linha As String = Lista_Extrato.Get(Pos)
	Dim i As Int = Linha.IndexOf("|")
	Dim Valor As Float = Linha.SubString2(0,i)
	
	Saldo = Saldo - Valor

	Lista_Extrato.RemoveAt(Pos)
End Sub

Public Sub Remover_Transacao2 (Obj As String)
	Dim Pos As Int = Lista_Extrato.IndexOf(Obj)
	Dim Linha As String = Lista_Extrato.Get(Pos)
	Dim i As Int = Linha.IndexOf("|")
	Dim Valor As Float = Linha.SubString2(0,i)
	
	Saldo = Saldo - Valor

	Lista_Extrato.RemoveAt(Pos)
End Sub

Public Sub GetUsuario(Username As String) As Object
	Dim TextReader1 As TextReader
	TextReader1.Initialize(File.OpenInput(File.DirRootExternal, "Logins.txt"))
	Dim linha1,linha2,linha3,linha4 As String
	linha1 = TextReader1.ReadLine
	If linha1 = Null Then
		Return False
	End If
	linha2 = linha1.SubString2(0,linha1.IndexOf(";"))
	linha3 = linha1.SubString2(linha1.IndexOf(";")+1,linha1.LastIndexOf(";"))
	linha4 = linha1.SubString(linha1.LastIndexOf(";")+1)
	
	Do While linha1 <> Null
    	If linha2 = Username Then
			Return linha1
		End If
    	linha1 = TextReader1.ReadLine
	Loop
	TextReader1.Close
	Return Null
End Sub