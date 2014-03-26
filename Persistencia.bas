Type=Class
Version=3.2
@EndOfDesignText@

Sub Class_Globals

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
 				linha2 = linha1.SubString2(0,linha1.IndexOf(";"))
				linha3 = linha1.SubString2(linha1.IndexOf(";")+1,linha1.LastIndexOf(";"))
				linha4 = linha1.SubString(linha1.LastIndexOf(";")+1)
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

Private Sub Atualizar_Saldo(valor As Float)
	Dim tr As TextReader
	Dim tw As TextWriter
	If Not(File.Exists(File.DirRootExternal,Logado&"saldo.txt")) Then
		tw.Initialize(File.OpenOutput(File.DirRootExternal,Logado&"saldo.txt",True))
		tw.WriteLine("0")
		tw.Close
	End If
	tr.Initialize(File.OpenInput(File.DirRootExternal,Logado&"saldo.txt"))
	Dim saldoTemp As Double
	Dim abc As String = tr.ReadLine
	tr.Close
	saldoTemp = abc
	File.Delete(File.DirRootExternal,Logado&"saldo.txt")
	tw.Initialize(File.OpenOutput(File.DirRootExternal,Logado&"saldo.txt",True))
	Dim Saldo As Double

	Saldo = saldoTemp + valor
	
	tw.WriteLine(Saldo)
	tw.Close
End Sub

Public Sub GetSaldo As String
	Dim tr As TextReader
	Dim tw As TextWriter
	If Not(File.Exists(File.DirRootExternal,Logado&"saldo.txt")) Then
		tw.Initialize(File.OpenOutput(File.DirRootExternal,Logado&"saldo.txt",True))
		tw.Close
		Return 0
	End If
	tr.Initialize(File.OpenInput(File.DirRootExternal,Logado&"saldo.txt"))	
	Dim saldo As String
	saldo = tr.ReadLine
	Return saldo
End Sub

Public Sub Logado As String
	Dim TextReader1 As TextReader
	TextReader1.Initialize(File.OpenInput(File.DirRootExternal,"logado.txt"))
	Dim usuario As String
	usuario = TextReader1.ReadLine
	Return usuario
End Sub

Public Sub GetTransacoes(Usuario As String) As List
	Dim TextReader1 As TextReader
	If Not(File.Exists(File.DirRootExternal,Logado&"transacoes.txt")) Then
		Dim tw As TextWriter
		tw.Initialize(File.OpenOutput(File.DirRootExternal,Logado&"transacoes.txt",True))
		tw.Close
	End If
	TextReader1.Initialize(File.OpenInput(File.DirRootExternal,Logado&"transacoes.txt"))
	Dim transacao,linha1,linha2 As String
	Dim transacoes As List
	transacoes.Initialize
	linha1 = TextReader1.ReadLine
	Do While linha1 <> Null
		linha2 = linha1.SubString2(0,linha1.IndexOf(";"))
		If linha2 = Usuario Then
			transacao = linha1.SubString(linha1.IndexOf(";")+1)
		End If
		linha1 = TextReader1.ReadLine
		transacoes.Add(transacao)
	Loop
	Return transacoes
End Sub

Public Sub Salvar_Transacao(usuario As String, valor As Object, Data As String, Categoria As String, Tipo As String) As Boolean
	Dim valor1 As Float
	valor1 = valor
	If Tipo = "Crédito" Then
		Atualizar_Saldo(valor1)
	Else
		valor1 = valor1 * (-1)
		Atualizar_Saldo(valor1)
	End If
	Dim TextWriter1 As TextWriter
	TextWriter1.Initialize(File.OpenOutput(File.DirRootExternal,Logado&"transacoes.txt",True))
	TextWriter1.WriteLine(usuario &";"& valor1 &";"& Data &";"& Categoria)
	TextWriter1.Close
	Return True
End Sub

Public Sub Remover_Transacao (Pos As Int)
	Dim linha1,linha2 As String
	Dim lista As List
	Dim TextReader1 As TextReader
	Dim TextWriter1 As TextWriter
	
	linha1 = GetTransacoes(Logado).Get(Pos)
	linha2 = linha1.SubString2(0,linha1.IndexOf(";"))
	
	TextReader1.Initialize(File.OpenInput(File.DirRootExternal,Logado&"transacoes.txt"))
	lista = TextReader1.ReadList
	TextReader1.Close
	
	File.Delete(File.DirRootExternal,Logado&"transacoes.txt")
	
	lista.RemoveAt(Pos)
	
	TextWriter1.Initialize(File.OpenOutput(File.DirRootExternal,Logado&"transacoes.txt",True))
	TextWriter1.WriteList(lista)
	Dim Valor As Float = linha2
	TextWriter1.Close
	Atualizar_Saldo(-Valor)
	
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

Public Sub Salvar_Categoria(Categoria As String) As Boolean
	If Categoria = "" Then
		Msgbox("Categoria está em branco!", "Atenção!")
	Else
		Dim TextWriter1 As TextWriter
		TextWriter1.Initialize(File.OpenOutput(File.DirRootExternal, "categ.txt", True))
		TextWriter1.WriteLine(Categoria)
		TextWriter1.Close
		Return True
	End If
	Return False
End Sub

Public Sub Deletar_Categoria(Categoria As String) As Boolean
	Dim TextReader1 As TextReader
	Dim TextWriter1 As TextWriter
	If Categoria = "Agua" OR Categoria = "Luz" OR Categoria = "Telefone" OR Categoria = "Interet" OR Categoria = "TV" OR Categoria = "Salario" OR Categoria = "Alimentacao" OR Categoria = "Combustivel" OR Categoria = "Construcao" Then
		Return False
	Else
		TextReader1.Initialize(File.OpenInput(File.DirRootExternal,"categ.txt"))
		Dim Lista As List
		Lista.Initialize
		Lista.AddAll(TextReader1.ReadList)
		File.Delete(File.DirRootExternal,"categ.txt")
		TextWriter1.Initialize(File.OpenOutput(File.DirRootExternal,"categ.txt",True))
		TextWriter1.WriteList(Lista)
		TextReader1.Close
		TextWriter1.Close
		Return True
	End If
End Sub

Public Sub GetCategorias As List
	Dim TextReader1,TextReader2 As TextReader
	TextReader1.Initialize(File.OpenInput(File.DirAssets,"categ.txt"))
	If Not(File.Exists(File.DirRootExternal,"categ.txt")) Then
		Dim TextWriter1 As TextWriter
		TextWriter1.Initialize(File.OpenOutput(File.DirRootExternal,"categ.txt",True))
		TextWriter1.Close
	End If
	TextReader2.Initialize(File.OpenInput(File.DirRootExternal,"categ.txt"))
	Dim Lista As List
	Lista.Initialize
	Lista.AddAll(TextReader1.ReadList)
	Lista.AddAll(TextReader2.ReadList)
	TextReader1.Close
	TextReader2.Close
	Return Lista
End Sub