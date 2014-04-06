Type=Class
Version=3.2
@EndOfDesignText@

Sub Class_Globals
	Private caminho As String
End Sub

Public Sub Initialize
	caminho = File.DirRootExternal&"/Fine/Data"
End Sub

Public Sub Fazer_Login (Username As String, Senha As String) As Boolean
	If Username = "" OR Senha = "" Then 
		Msgbox("Campos Obrigatorios não estão preenchidos", "Aviso!" )
	Else
		If Not(File.Exists(caminho, "Logins.txt")) Then
			Dim TextWriter1 As TextWriter
   			TextWriter1.Initialize(File.OpenOutput(caminho, "Logins.txt", False))
			TextWriter1.Close
		Else
			Dim TextReader1 As TextReader
	    	TextReader1.Initialize(File.OpenInput(caminho, "Logins.txt"))
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
				If linha1 <> Null Then
	 				linha2 = linha1.SubString2(0,linha1.IndexOf(";"))
					linha3 = linha1.SubString2(linha1.IndexOf(";")+1,linha1.LastIndexOf(";"))
					linha4 = linha1.SubString(linha1.LastIndexOf(";")+1)
				Else
					Return False
				End If
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
		If Not(File.Exists(caminho, "Logins.txt")) Then
			Dim TextWriter1 As TextWriter
			TextWriter1.Initialize(File.OpenOutput(caminho, "Logins.txt", True))
			TextWriter1.WriteLine(Username &";"& Senha &";"& Nome)
			TextWriter1.Close
			Return True
		Else
			Dim TextReader1 As TextReader
	    	TextReader1.Initialize(File.OpenInput(caminho, "Logins.txt"))
	    	Dim linha1,linha2,linha3,linha4 As String
			linha1 = TextReader1.ReadLine
			
			If linha1 = Null Then
				Dim TextWriter1 As TextWriter
				TextWriter1.Initialize(File.OpenOutput(caminho, "Logins.txt", True))
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
					TextWriter2.Initialize(File.OpenOutput(caminho, "Logins.txt", True))
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
    	TextReader1.Initialize(File.OpenInput(caminho, "Logins.txt"))
		
		Dim texto As List
		texto.Initialize
		
	    Dim linha1,linha2,linha3,linha4 As String
		linha1 = TextReader1.ReadLine
		linha2 = linha1.SubString2(0,linha1.IndexOf(";"))
		linha3 = linha1.SubString2(linha1.IndexOf(";")+1,linha1.LastIndexOf(";"))
		linha4 = linha1.SubString(linha1.LastIndexOf(";")+1)

		Do While linha1 <> Null
			If linha2 & linha3 = Username & Senha Then
				If File.Delete(caminho, "Logins.txt") Then
					Dim TextWriter1 As TextWriter
   					TextWriter1.Initialize(File.OpenOutput(caminho, "Logins.txt", True))
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
	If Not(File.Exists(caminho,Logado&"saldo.txt")) Then
		tw.Initialize(File.OpenOutput(caminho,Logado&"saldo.txt",True))
		tw.WriteLine("0")
		tw.Close
	End If
	tr.Initialize(File.OpenInput(caminho,Logado&"saldo.txt"))
	Dim saldoTemp As Double
	Dim abc As String = tr.ReadLine
	tr.Close
	saldoTemp = abc
	File.Delete(caminho,Logado&"saldo.txt")
	tw.Initialize(File.OpenOutput(caminho,Logado&"saldo.txt",True))
	Dim Saldo As Double

	Saldo = saldoTemp + valor
	
	tw.WriteLine(Saldo)
	tw.Close
End Sub

Public Sub GetSaldo As String
	Dim tr As TextReader
	Dim tw As TextWriter
	If Not(File.Exists(caminho,Logado&"saldo.txt")) Then
		tw.Initialize(File.OpenOutput(caminho,Logado&"saldo.txt",True))
		tw.Close
		Return 0
	End If
	tr.Initialize(File.OpenInput(caminho,Logado&"saldo.txt"))	
	Dim saldo As String
	saldo = tr.ReadLine
	If saldo = Null Then
		Return 0
	End If
	Return saldo
End Sub

Public Sub Logado As String
	Dim TextReader1 As TextReader
	TextReader1.Initialize(File.OpenInput(caminho,"logado.txt"))
	Dim usuario As String
	usuario = TextReader1.ReadLine
	TextReader1.Close
	Return usuario
End Sub

Public Sub GetTransacoes(Usuario As String) As List
	Dim TextReader1 As TextReader
	If Not(File.Exists(caminho,Logado&"transacoes.txt")) Then
		Dim tw As TextWriter
		tw.Initialize(File.OpenOutput(caminho,Logado&"transacoes.txt",True))
		tw.Close
	End If
	TextReader1.Initialize(File.OpenInput(caminho,Logado&"transacoes.txt"))
	Dim transacoes As List
	transacoes.Initialize
	transacoes = TextReader1.ReadList
	TextReader1.Close
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
	TextWriter1.Initialize(File.OpenOutput(caminho,Logado&"transacoes.txt",True))
	TextWriter1.WriteLine(valor1 &";"& Data &";"& Categoria)
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
	
	TextReader1.Initialize(File.OpenInput(caminho,Logado&"transacoes.txt"))
	lista = TextReader1.ReadList
	TextReader1.Close
	
	File.Delete(caminho,Logado&"transacoes.txt")
	
	lista.RemoveAt(Pos)
	
	TextWriter1.Initialize(File.OpenOutput(caminho,Logado&"transacoes.txt",True))
	TextWriter1.WriteList(lista)
	Dim Valor As Float = linha2
	TextWriter1.Close
	Atualizar_Saldo(-Valor)
	
End Sub

Public Sub GetUsuario(Username As String) As Object
	Dim TextReader1 As TextReader
	TextReader1.Initialize(File.OpenInput(caminho, "Logins.txt"))
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
		TextWriter1.Initialize(File.OpenOutput(caminho, Logado&"categ.txt", True))
		TextWriter1.WriteLine(Categoria)
		TextWriter1.Close
		Return True
	End If
	Return False
End Sub

Public Sub Deletar_Categoria(Categoria As String) As String
	Dim TextReader1 As TextReader
	Dim TextWriter1 As TextWriter
	If Categoria = "Agua" OR Categoria = "Luz" OR Categoria = "Telefone" OR Categoria = "Interet" OR Categoria = "TV" OR Categoria = "Salario" OR Categoria = "Alimentacao" OR Categoria = "Combustivel" OR Categoria = "Construcao" Then
		Return "False"
	Else
		Dim listaTrans As List
		listaTrans = GetTransacoes(Main.Pers.Logado)
		
		For Each trans As String In listaTrans
			Dim str As String
			str = trans.SubString(trans.LastIndexOf(";")+1)
			If Categoria = str Then
				Return "Null" 
			End If
		Next
	
		TextReader1.Initialize(File.OpenInput(caminho,Logado&"categ.txt"))
		
		Dim Lista As List
		Lista.Initialize
		Lista.AddAll(TextReader1.ReadList)
		TextReader1.Close
		
		File.Delete(caminho,Logado&"categ.txt")
		
		Dim i As Int = 0
		For Each categ As String In Lista
			Dim str As String
			str = categ.SubString(categ.LastIndexOf(";")+1)
			If Categoria = str Then
				Lista.RemoveAt(i) 
			End If
			i=i+1
		Next
		
		TextWriter1.Initialize(File.OpenOutput(caminho,Logado&"categ.txt",True))
		TextWriter1.WriteList(Lista)
		TextWriter1.Close
		Return "True"
	End If
End Sub

Public Sub GetCategorias As List
	Dim TextReader1,TextReader2 As TextReader
	TextReader1.Initialize(File.OpenInput(File.DirAssets,"categ.txt"))
	If Not(File.Exists(caminho,Logado&"categ.txt")) Then
		Dim TextWriter1 As TextWriter
		TextWriter1.Initialize(File.OpenOutput(caminho,Logado&"categ.txt",True))
		TextWriter1.Close
	End If
	TextReader2.Initialize(File.OpenInput(caminho,Logado&"categ.txt"))
	Dim Lista As List
	Lista.Initialize
	Lista.AddAll(TextReader1.ReadList)
	Lista.AddAll(TextReader2.ReadList)
	TextReader1.Close
	TextReader2.Close
	Return Lista
End Sub

Public Sub Atualizar_Username(uNovo As String)
	If File.Exists(caminho, Logado&"saldo.txt") Then
		RenameFile(caminho&"/"&Logado&"saldo.txt",caminho&"/"&uNovo&"saldo.txt")
		File.Delete(caminho,Logado&"saldo.txt")
	End If
	
	If File.Exists(caminho, Logado&"categ.txt") Then
		RenameFile(caminho&"/"&Logado&"categ.txt",caminho&"/"&uNovo&"categ.txt")
		File.Delete(caminho, Logado&"categ.txt")
	End If
	
	If File.Exists(caminho, Logado&"transacoes.txt") Then
		RenameFile(caminho&"/"&Logado&"transacoes.txt",caminho&"/"&uNovo&"transacoes.txt")
		File.Delete(caminho, Logado&"transacoes.txt")
	End If
	
	If File.Exists(caminho,"logado.txt") Then
		File.Delete(caminho,"logado.txt")
		Dim TextWriter1 As TextWriter
		TextWriter1.Initialize(File.OpenOutput(caminho,"logado.txt",True))
		TextWriter1.Write(uNovo)
		TextWriter1.Close
	Else
		Dim TextWriter1 As TextWriter
		TextWriter1.Initialize(File.OpenOutput(caminho,"logado.txt",True))
		TextWriter1.Write(uNovo)
		TextWriter1.Close
	End If
End Sub

Private Sub RenameFile(OriginalFileName As String, NewFileName As String) As Boolean
   Dim Result As Int
   Dim StdOut, StdErr As StringBuilder
   StdOut.Initialize
   StdErr.Initialize
   Dim Ph As Phone
   Result = Ph.Shell("mv " & OriginalFileName & " " & NewFileName, Null,  StdOut, StdErr)
   If Result = 0 Then
      Return True
   Else
      Return False
   End If
End Sub