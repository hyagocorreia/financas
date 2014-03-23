package Financas.Pessoais;

import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.B4AClass;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.debug.*;

public class persistencia extends B4AClass.ImplB4AClass implements BA.SubDelegator{
    private static java.util.HashMap<String, java.lang.reflect.Method> htSubs;
    private void innerInitialize(BA _ba) throws Exception {
        if (ba == null) {
            ba = new BA(_ba, this, htSubs, "Financas.Pessoais.persistencia");
            if (htSubs == null) {
                ba.loadHtSubs(this.getClass());
                htSubs = ba.htSubs;
            }
            if (ba.getClass().getName().endsWith("ShellBA")) {
			    ba.raiseEvent2(null, true, "CREATE", true, "Financas.Pessoais.persistencia",
                    ba);
		    }
        }
        ba.raiseEvent2(null, true, "class_globals", false);
    }

 public anywheresoftware.b4a.keywords.Common __c = null;
public anywheresoftware.b4a.objects.collections.List _lista_extrato = null;
public float _saldo = 0f;
public Financas.Pessoais.main _main = null;
public Financas.Pessoais.cadastro _cadastro = null;
public Financas.Pessoais.financeiro _financeiro = null;
public Financas.Pessoais.creditos _creditos = null;
public Financas.Pessoais.debitos _debitos = null;
public Financas.Pessoais.total _total = null;
public Financas.Pessoais.utilitarios _utilitarios = null;
public Financas.Pessoais.menu _menu = null;
public Financas.Pessoais.calculadora _calculadora = null;
public Financas.Pessoais.extrato _extrato = null;
public Financas.Pessoais.excluir _excluir = null;
public Financas.Pessoais.addcategoria _addcategoria = null;
public Financas.Pessoais.remover_categoria _remover_categoria = null;
public Financas.Pessoais.lista _lista = null;
public Financas.Pessoais.editar _editar = null;
  public Object[] GetGlobals() {
		return new Object[] {"AddCategoria",Debug.moduleToString(Financas.Pessoais.addcategoria.class),"Cadastro",Debug.moduleToString(Financas.Pessoais.cadastro.class),"Calculadora",Debug.moduleToString(Financas.Pessoais.calculadora.class),"Creditos",Debug.moduleToString(Financas.Pessoais.creditos.class),"Debitos",Debug.moduleToString(Financas.Pessoais.debitos.class),"Editar",Debug.moduleToString(Financas.Pessoais.editar.class),"Excluir",Debug.moduleToString(Financas.Pessoais.excluir.class),"Extrato",Debug.moduleToString(Financas.Pessoais.extrato.class),"Financeiro",Debug.moduleToString(Financas.Pessoais.financeiro.class),"Lista",Debug.moduleToString(Financas.Pessoais.lista.class),"Lista_Extrato",_lista_extrato,"Main",Debug.moduleToString(Financas.Pessoais.main.class),"Menu",Debug.moduleToString(Financas.Pessoais.menu.class),"Remover_Categoria",Debug.moduleToString(Financas.Pessoais.remover_categoria.class),"Saldo",_saldo,"Total",Debug.moduleToString(Financas.Pessoais.total.class),"Utilitarios",Debug.moduleToString(Financas.Pessoais.utilitarios.class)};
}
public String  _atualizar_saldo(float _valor) throws Exception{
		Debug.PushSubsStack("Atualizar_Saldo (persistencia) ","persistencia",3,ba,this);
try {
Debug.locals.put("Valor", _valor);
 BA.debugLineNum = 132;BA.debugLine="Private Sub Atualizar_Saldo(Valor As Float)";
Debug.ShouldStop(8);
 BA.debugLineNum = 133;BA.debugLine="Saldo = Saldo + Valor";
Debug.ShouldStop(16);
_saldo = (float) (_saldo+_valor);
 BA.debugLineNum = 134;BA.debugLine="End Sub";
Debug.ShouldStop(32);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public String  _class_globals() throws Exception{
 //BA.debugLineNum = 2;BA.debugLine="Sub Class_Globals";
 //BA.debugLineNum = 3;BA.debugLine="Dim Lista_Extrato As List";
_lista_extrato = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 4;BA.debugLine="Lista_Extrato.Initialize";
_lista_extrato.Initialize();
 //BA.debugLineNum = 5;BA.debugLine="Dim Saldo As Float";
_saldo = 0f;
 //BA.debugLineNum = 6;BA.debugLine="End Sub";
return "";
}
public boolean  _criar_login(String _nome,String _username,String _senha,String _senha_repetida) throws Exception{
		Debug.PushSubsStack("Criar_Login (persistencia) ","persistencia",3,ba,this);
try {
anywheresoftware.b4a.objects.streams.File.TextWriterWrapper _textwriter1 = null;
anywheresoftware.b4a.objects.streams.File.TextReaderWrapper _textreader1 = null;
String _linha1 = "";
String _linha2 = "";
String _linha3 = "";
String _linha4 = "";
anywheresoftware.b4a.objects.streams.File.TextWriterWrapper _textwriter2 = null;
Debug.locals.put("Nome", _nome);
Debug.locals.put("Username", _username);
Debug.locals.put("Senha", _senha);
Debug.locals.put("Senha_Repetida", _senha_repetida);
 BA.debugLineNum = 46;BA.debugLine="Public Sub Criar_Login(Nome As String, Username As String, Senha As String, Senha_Repetida As String) As Boolean";
Debug.ShouldStop(8192);
 BA.debugLineNum = 47;BA.debugLine="If Nome = \"\" OR Username = \"\" OR Senha = \"\" OR Senha_Repetida = \"\" Then";
Debug.ShouldStop(16384);
if ((_nome).equals("") || (_username).equals("") || (_senha).equals("") || (_senha_repetida).equals("")) { 
 BA.debugLineNum = 48;BA.debugLine="Msgbox(\"Campos Obrigatórios não Preenchidos\", \"Atenção!\")";
Debug.ShouldStop(32768);
__c.Msgbox("Campos Obrigatórios não Preenchidos","Atenção!",getActivityBA());
 }else 
{ BA.debugLineNum = 49;BA.debugLine="Else If Not(Senha = Senha_Repetida) Then";
Debug.ShouldStop(65536);
if (__c.Not((_senha).equals(_senha_repetida))) { 
 BA.debugLineNum = 50;BA.debugLine="Msgbox(\"Senhas não conferem!\", \"Atenção!\")";
Debug.ShouldStop(131072);
__c.Msgbox("Senhas não conferem!","Atenção!",getActivityBA());
 }else {
 BA.debugLineNum = 52;BA.debugLine="If Not(File.Exists(File.DirRootExternal, \"Logins.txt\")) Then";
Debug.ShouldStop(524288);
if (__c.Not(__c.File.Exists(__c.File.getDirRootExternal(),"Logins.txt"))) { 
 BA.debugLineNum = 53;BA.debugLine="Dim TextWriter1 As TextWriter";
Debug.ShouldStop(1048576);
_textwriter1 = new anywheresoftware.b4a.objects.streams.File.TextWriterWrapper();Debug.locals.put("TextWriter1", _textwriter1);
 BA.debugLineNum = 54;BA.debugLine="TextWriter1.Initialize(File.OpenOutput(File.DirRootExternal, \"Logins.txt\", True))";
Debug.ShouldStop(2097152);
_textwriter1.Initialize((java.io.OutputStream)(__c.File.OpenOutput(__c.File.getDirRootExternal(),"Logins.txt",__c.True).getObject()));
 BA.debugLineNum = 55;BA.debugLine="TextWriter1.WriteLine(Username &\";\"& Senha &\";\"& Nome)";
Debug.ShouldStop(4194304);
_textwriter1.WriteLine(_username+";"+_senha+";"+_nome);
 BA.debugLineNum = 56;BA.debugLine="TextWriter1.Close";
Debug.ShouldStop(8388608);
_textwriter1.Close();
 BA.debugLineNum = 57;BA.debugLine="Return True";
Debug.ShouldStop(16777216);
if (true) return __c.True;
 }else {
 BA.debugLineNum = 59;BA.debugLine="Dim TextReader1 As TextReader";
Debug.ShouldStop(67108864);
_textreader1 = new anywheresoftware.b4a.objects.streams.File.TextReaderWrapper();Debug.locals.put("TextReader1", _textreader1);
 BA.debugLineNum = 60;BA.debugLine="TextReader1.Initialize(File.OpenInput(File.DirRootExternal, \"Logins.txt\"))";
Debug.ShouldStop(134217728);
_textreader1.Initialize((java.io.InputStream)(__c.File.OpenInput(__c.File.getDirRootExternal(),"Logins.txt").getObject()));
 BA.debugLineNum = 61;BA.debugLine="Dim linha1,linha2,linha3,linha4 As String";
Debug.ShouldStop(268435456);
_linha1 = "";Debug.locals.put("linha1", _linha1);
_linha2 = "";Debug.locals.put("linha2", _linha2);
_linha3 = "";Debug.locals.put("linha3", _linha3);
_linha4 = "";Debug.locals.put("linha4", _linha4);
 BA.debugLineNum = 62;BA.debugLine="linha1 = TextReader1.ReadLine";
Debug.ShouldStop(536870912);
_linha1 = _textreader1.ReadLine();Debug.locals.put("linha1", _linha1);
 BA.debugLineNum = 64;BA.debugLine="If linha1 = Null Then";
Debug.ShouldStop(-2147483648);
if (_linha1== null) { 
 BA.debugLineNum = 65;BA.debugLine="Dim TextWriter1 As TextWriter";
Debug.ShouldStop(1);
_textwriter1 = new anywheresoftware.b4a.objects.streams.File.TextWriterWrapper();Debug.locals.put("TextWriter1", _textwriter1);
 BA.debugLineNum = 66;BA.debugLine="TextWriter1.Initialize(File.OpenOutput(File.DirRootExternal, \"Logins.txt\", True))";
Debug.ShouldStop(2);
_textwriter1.Initialize((java.io.OutputStream)(__c.File.OpenOutput(__c.File.getDirRootExternal(),"Logins.txt",__c.True).getObject()));
 BA.debugLineNum = 67;BA.debugLine="TextWriter1.WriteLine(Username &\";\"& Senha &\";\"& Nome)";
Debug.ShouldStop(4);
_textwriter1.WriteLine(_username+";"+_senha+";"+_nome);
 BA.debugLineNum = 68;BA.debugLine="TextWriter1.Close";
Debug.ShouldStop(8);
_textwriter1.Close();
 BA.debugLineNum = 69;BA.debugLine="Return True";
Debug.ShouldStop(16);
if (true) return __c.True;
 };
 BA.debugLineNum = 72;BA.debugLine="linha2 = linha1.SubString2(0,linha1.IndexOf(\";\"))";
Debug.ShouldStop(128);
_linha2 = _linha1.substring((int) (0),_linha1.indexOf(";"));Debug.locals.put("linha2", _linha2);
 BA.debugLineNum = 73;BA.debugLine="linha3 = linha1.SubString2(linha1.IndexOf(\";\")+1,linha1.LastIndexOf(\";\"))";
Debug.ShouldStop(256);
_linha3 = _linha1.substring((int) (_linha1.indexOf(";")+1),_linha1.lastIndexOf(";"));Debug.locals.put("linha3", _linha3);
 BA.debugLineNum = 74;BA.debugLine="linha4 = linha1.SubString(linha1.LastIndexOf(\";\")+1)";
Debug.ShouldStop(512);
_linha4 = _linha1.substring((int) (_linha1.lastIndexOf(";")+1));Debug.locals.put("linha4", _linha4);
 BA.debugLineNum = 76;BA.debugLine="Do While linha1 <> Null";
Debug.ShouldStop(2048);
while (_linha1!= null) {
 BA.debugLineNum = 77;BA.debugLine="If linha2 = Username Then";
Debug.ShouldStop(4096);
if ((_linha2).equals(_username)) { 
 BA.debugLineNum = 78;BA.debugLine="Msgbox(\"Username já existe!\",\"Atenção!\")";
Debug.ShouldStop(8192);
__c.Msgbox("Username já existe!","Atenção!",getActivityBA());
 BA.debugLineNum = 79;BA.debugLine="linha1 = TextReader1.ReadLine";
Debug.ShouldStop(16384);
_linha1 = _textreader1.ReadLine();Debug.locals.put("linha1", _linha1);
 }else {
 BA.debugLineNum = 81;BA.debugLine="Dim TextWriter2 As TextWriter";
Debug.ShouldStop(65536);
_textwriter2 = new anywheresoftware.b4a.objects.streams.File.TextWriterWrapper();Debug.locals.put("TextWriter2", _textwriter2);
 BA.debugLineNum = 82;BA.debugLine="TextWriter2.Initialize(File.OpenOutput(File.DirRootExternal, \"Logins.txt\", True))";
Debug.ShouldStop(131072);
_textwriter2.Initialize((java.io.OutputStream)(__c.File.OpenOutput(__c.File.getDirRootExternal(),"Logins.txt",__c.True).getObject()));
 BA.debugLineNum = 83;BA.debugLine="TextWriter2.WriteLine(Username &\";\"& Senha &\";\"& Nome)";
Debug.ShouldStop(262144);
_textwriter2.WriteLine(_username+";"+_senha+";"+_nome);
 BA.debugLineNum = 84;BA.debugLine="TextWriter2.Close";
Debug.ShouldStop(524288);
_textwriter2.Close();
 BA.debugLineNum = 85;BA.debugLine="Return True";
Debug.ShouldStop(1048576);
if (true) return __c.True;
 };
 }
;
 BA.debugLineNum = 88;BA.debugLine="TextReader1.Close";
Debug.ShouldStop(8388608);
_textreader1.Close();
 };
 }};
 BA.debugLineNum = 91;BA.debugLine="Return False";
Debug.ShouldStop(67108864);
if (true) return __c.False;
 BA.debugLineNum = 92;BA.debugLine="End Sub";
Debug.ShouldStop(134217728);
return false;
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public boolean  _excluir_login(String _username,String _senha) throws Exception{
		Debug.PushSubsStack("Excluir_Login (persistencia) ","persistencia",3,ba,this);
try {
anywheresoftware.b4a.objects.streams.File.TextReaderWrapper _textreader1 = null;
anywheresoftware.b4a.objects.collections.List _texto = null;
String _linha1 = "";
String _linha2 = "";
String _linha3 = "";
String _linha4 = "";
anywheresoftware.b4a.objects.streams.File.TextWriterWrapper _textwriter1 = null;
Debug.locals.put("Username", _username);
Debug.locals.put("Senha", _senha);
 BA.debugLineNum = 94;BA.debugLine="Public Sub Excluir_Login(Username As String, Senha As String) As Boolean";
Debug.ShouldStop(536870912);
 BA.debugLineNum = 95;BA.debugLine="If Username = \"\" OR Senha = \"\" Then";
Debug.ShouldStop(1073741824);
if ((_username).equals("") || (_senha).equals("")) { 
 BA.debugLineNum = 96;BA.debugLine="Msgbox(\"Campos Obrigatórios não Preenchidos\", \"Atenção!\")";
Debug.ShouldStop(-2147483648);
__c.Msgbox("Campos Obrigatórios não Preenchidos","Atenção!",getActivityBA());
 }else {
 BA.debugLineNum = 98;BA.debugLine="Dim TextReader1 As TextReader";
Debug.ShouldStop(2);
_textreader1 = new anywheresoftware.b4a.objects.streams.File.TextReaderWrapper();Debug.locals.put("TextReader1", _textreader1);
 BA.debugLineNum = 99;BA.debugLine="TextReader1.Initialize(File.OpenInput(File.DirRootExternal, \"Logins.txt\"))";
Debug.ShouldStop(4);
_textreader1.Initialize((java.io.InputStream)(__c.File.OpenInput(__c.File.getDirRootExternal(),"Logins.txt").getObject()));
 BA.debugLineNum = 101;BA.debugLine="Dim texto As List";
Debug.ShouldStop(16);
_texto = new anywheresoftware.b4a.objects.collections.List();Debug.locals.put("texto", _texto);
 BA.debugLineNum = 102;BA.debugLine="texto.Initialize";
Debug.ShouldStop(32);
_texto.Initialize();
 BA.debugLineNum = 104;BA.debugLine="Dim linha1,linha2,linha3,linha4 As String";
Debug.ShouldStop(128);
_linha1 = "";Debug.locals.put("linha1", _linha1);
_linha2 = "";Debug.locals.put("linha2", _linha2);
_linha3 = "";Debug.locals.put("linha3", _linha3);
_linha4 = "";Debug.locals.put("linha4", _linha4);
 BA.debugLineNum = 105;BA.debugLine="linha1 = TextReader1.ReadLine";
Debug.ShouldStop(256);
_linha1 = _textreader1.ReadLine();Debug.locals.put("linha1", _linha1);
 BA.debugLineNum = 106;BA.debugLine="linha2 = linha1.SubString2(0,linha1.IndexOf(\";\"))";
Debug.ShouldStop(512);
_linha2 = _linha1.substring((int) (0),_linha1.indexOf(";"));Debug.locals.put("linha2", _linha2);
 BA.debugLineNum = 107;BA.debugLine="linha3 = linha1.SubString2(linha1.IndexOf(\";\")+1,linha1.LastIndexOf(\";\"))";
Debug.ShouldStop(1024);
_linha3 = _linha1.substring((int) (_linha1.indexOf(";")+1),_linha1.lastIndexOf(";"));Debug.locals.put("linha3", _linha3);
 BA.debugLineNum = 108;BA.debugLine="linha4 = linha1.SubString(linha1.LastIndexOf(\";\")+1)";
Debug.ShouldStop(2048);
_linha4 = _linha1.substring((int) (_linha1.lastIndexOf(";")+1));Debug.locals.put("linha4", _linha4);
 BA.debugLineNum = 110;BA.debugLine="Do While linha1 <> Null";
Debug.ShouldStop(8192);
while (_linha1!= null) {
 BA.debugLineNum = 111;BA.debugLine="If linha2 & linha3 = Username & Senha Then";
Debug.ShouldStop(16384);
if ((_linha2+_linha3).equals(_username+_senha)) { 
 BA.debugLineNum = 112;BA.debugLine="If File.Delete(File.DirRootExternal, \"Logins.txt\") Then";
Debug.ShouldStop(32768);
if (__c.File.Delete(__c.File.getDirRootExternal(),"Logins.txt")) { 
 BA.debugLineNum = 113;BA.debugLine="Dim TextWriter1 As TextWriter";
Debug.ShouldStop(65536);
_textwriter1 = new anywheresoftware.b4a.objects.streams.File.TextWriterWrapper();Debug.locals.put("TextWriter1", _textwriter1);
 BA.debugLineNum = 114;BA.debugLine="TextWriter1.Initialize(File.OpenOutput(File.DirRootExternal, \"Logins.txt\", True))";
Debug.ShouldStop(131072);
_textwriter1.Initialize((java.io.OutputStream)(__c.File.OpenOutput(__c.File.getDirRootExternal(),"Logins.txt",__c.True).getObject()));
 BA.debugLineNum = 115;BA.debugLine="If texto <> Null Then";
Debug.ShouldStop(262144);
if (_texto!= null) { 
 BA.debugLineNum = 116;BA.debugLine="TextWriter1.WriteList(texto)";
Debug.ShouldStop(524288);
_textwriter1.WriteList(_texto);
 };
 BA.debugLineNum = 118;BA.debugLine="TextWriter1.Close";
Debug.ShouldStop(2097152);
_textwriter1.Close();
 BA.debugLineNum = 119;BA.debugLine="TextReader1.Close";
Debug.ShouldStop(4194304);
_textreader1.Close();
 BA.debugLineNum = 120;BA.debugLine="Return True";
Debug.ShouldStop(8388608);
if (true) return __c.True;
 };
 }else {
 BA.debugLineNum = 123;BA.debugLine="texto.Add(linha1)";
Debug.ShouldStop(67108864);
_texto.Add((Object)(_linha1));
 BA.debugLineNum = 124;BA.debugLine="linha1 = TextReader1.ReadLine";
Debug.ShouldStop(134217728);
_linha1 = _textreader1.ReadLine();Debug.locals.put("linha1", _linha1);
 };
 }
;
 };
 BA.debugLineNum = 129;BA.debugLine="Return False";
Debug.ShouldStop(1);
if (true) return __c.False;
 BA.debugLineNum = 130;BA.debugLine="End Sub";
Debug.ShouldStop(2);
return false;
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public boolean  _fazer_login(String _username,String _senha) throws Exception{
		Debug.PushSubsStack("Fazer_Login (persistencia) ","persistencia",3,ba,this);
try {
anywheresoftware.b4a.objects.streams.File.TextWriterWrapper _textwriter1 = null;
anywheresoftware.b4a.objects.streams.File.TextReaderWrapper _textreader1 = null;
String _linha1 = "";
String _linha2 = "";
String _linha3 = "";
String _linha4 = "";
Debug.locals.put("Username", _username);
Debug.locals.put("Senha", _senha);
 BA.debugLineNum = 12;BA.debugLine="Public Sub Fazer_Login (Username As String, Senha As String) As Boolean";
Debug.ShouldStop(2048);
 BA.debugLineNum = 13;BA.debugLine="If Username = \"\" OR Senha = \"\" Then";
Debug.ShouldStop(4096);
if ((_username).equals("") || (_senha).equals("")) { 
 BA.debugLineNum = 14;BA.debugLine="Msgbox(\"Campos Obrigatorios não estão preenchidos\", \"Aviso!\" )";
Debug.ShouldStop(8192);
__c.Msgbox("Campos Obrigatorios não estão preenchidos","Aviso!",getActivityBA());
 }else {
 BA.debugLineNum = 16;BA.debugLine="If Not(File.Exists(File.DirRootExternal, \"Logins.txt\")) Then";
Debug.ShouldStop(32768);
if (__c.Not(__c.File.Exists(__c.File.getDirRootExternal(),"Logins.txt"))) { 
 BA.debugLineNum = 17;BA.debugLine="Dim TextWriter1 As TextWriter";
Debug.ShouldStop(65536);
_textwriter1 = new anywheresoftware.b4a.objects.streams.File.TextWriterWrapper();Debug.locals.put("TextWriter1", _textwriter1);
 BA.debugLineNum = 18;BA.debugLine="TextWriter1.Initialize(File.OpenOutput(File.DirRootExternal, \"Logins.txt\", False))";
Debug.ShouldStop(131072);
_textwriter1.Initialize((java.io.OutputStream)(__c.File.OpenOutput(__c.File.getDirRootExternal(),"Logins.txt",__c.False).getObject()));
 BA.debugLineNum = 19;BA.debugLine="TextWriter1.Close";
Debug.ShouldStop(262144);
_textwriter1.Close();
 }else {
 BA.debugLineNum = 21;BA.debugLine="Dim TextReader1 As TextReader";
Debug.ShouldStop(1048576);
_textreader1 = new anywheresoftware.b4a.objects.streams.File.TextReaderWrapper();Debug.locals.put("TextReader1", _textreader1);
 BA.debugLineNum = 22;BA.debugLine="TextReader1.Initialize(File.OpenInput(File.DirRootExternal, \"Logins.txt\"))";
Debug.ShouldStop(2097152);
_textreader1.Initialize((java.io.InputStream)(__c.File.OpenInput(__c.File.getDirRootExternal(),"Logins.txt").getObject()));
 BA.debugLineNum = 23;BA.debugLine="Dim linha1,linha2,linha3,linha4 As String";
Debug.ShouldStop(4194304);
_linha1 = "";Debug.locals.put("linha1", _linha1);
_linha2 = "";Debug.locals.put("linha2", _linha2);
_linha3 = "";Debug.locals.put("linha3", _linha3);
_linha4 = "";Debug.locals.put("linha4", _linha4);
 BA.debugLineNum = 24;BA.debugLine="linha1 = TextReader1.ReadLine";
Debug.ShouldStop(8388608);
_linha1 = _textreader1.ReadLine();Debug.locals.put("linha1", _linha1);
 BA.debugLineNum = 25;BA.debugLine="If linha1 = Null Then";
Debug.ShouldStop(16777216);
if (_linha1== null) { 
 BA.debugLineNum = 26;BA.debugLine="Return False";
Debug.ShouldStop(33554432);
if (true) return __c.False;
 };
 BA.debugLineNum = 28;BA.debugLine="linha2 = linha1.SubString2(0,linha1.IndexOf(\";\"))";
Debug.ShouldStop(134217728);
_linha2 = _linha1.substring((int) (0),_linha1.indexOf(";"));Debug.locals.put("linha2", _linha2);
 BA.debugLineNum = 29;BA.debugLine="linha3 = linha1.SubString2(linha1.IndexOf(\";\")+1,linha1.LastIndexOf(\";\"))";
Debug.ShouldStop(268435456);
_linha3 = _linha1.substring((int) (_linha1.indexOf(";")+1),_linha1.lastIndexOf(";"));Debug.locals.put("linha3", _linha3);
 BA.debugLineNum = 30;BA.debugLine="linha4 = linha1.SubString(linha1.LastIndexOf(\";\")+1)";
Debug.ShouldStop(536870912);
_linha4 = _linha1.substring((int) (_linha1.lastIndexOf(";")+1));Debug.locals.put("linha4", _linha4);
 BA.debugLineNum = 32;BA.debugLine="Do While linha1 <> Null";
Debug.ShouldStop(-2147483648);
while (_linha1!= null) {
 BA.debugLineNum = 33;BA.debugLine="If linha2 & linha3 = Username & Senha Then";
Debug.ShouldStop(1);
if ((_linha2+_linha3).equals(_username+_senha)) { 
 BA.debugLineNum = 34;BA.debugLine="Return True";
Debug.ShouldStop(2);
if (true) return __c.True;
 };
 BA.debugLineNum = 36;BA.debugLine="linha1 = TextReader1.ReadLine";
Debug.ShouldStop(8);
_linha1 = _textreader1.ReadLine();Debug.locals.put("linha1", _linha1);
 }
;
 BA.debugLineNum = 38;BA.debugLine="TextReader1.Close";
Debug.ShouldStop(32);
_textreader1.Close();
 };
 };
 BA.debugLineNum = 42;BA.debugLine="Return False";
Debug.ShouldStop(512);
if (true) return __c.False;
 BA.debugLineNum = 44;BA.debugLine="End Sub";
Debug.ShouldStop(2048);
return false;
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public Object  _getusuario(String _username) throws Exception{
		Debug.PushSubsStack("GetUsuario (persistencia) ","persistencia",3,ba,this);
try {
anywheresoftware.b4a.objects.streams.File.TextReaderWrapper _textreader1 = null;
String _linha1 = "";
String _linha2 = "";
String _linha3 = "";
String _linha4 = "";
Debug.locals.put("Username", _username);
 BA.debugLineNum = 171;BA.debugLine="Public Sub GetUsuario(Username As String) As Object";
Debug.ShouldStop(1024);
 BA.debugLineNum = 172;BA.debugLine="Dim TextReader1 As TextReader";
Debug.ShouldStop(2048);
_textreader1 = new anywheresoftware.b4a.objects.streams.File.TextReaderWrapper();Debug.locals.put("TextReader1", _textreader1);
 BA.debugLineNum = 173;BA.debugLine="TextReader1.Initialize(File.OpenInput(File.DirRootExternal, \"Logins.txt\"))";
Debug.ShouldStop(4096);
_textreader1.Initialize((java.io.InputStream)(__c.File.OpenInput(__c.File.getDirRootExternal(),"Logins.txt").getObject()));
 BA.debugLineNum = 174;BA.debugLine="Dim linha1,linha2,linha3,linha4 As String";
Debug.ShouldStop(8192);
_linha1 = "";Debug.locals.put("linha1", _linha1);
_linha2 = "";Debug.locals.put("linha2", _linha2);
_linha3 = "";Debug.locals.put("linha3", _linha3);
_linha4 = "";Debug.locals.put("linha4", _linha4);
 BA.debugLineNum = 175;BA.debugLine="linha1 = TextReader1.ReadLine";
Debug.ShouldStop(16384);
_linha1 = _textreader1.ReadLine();Debug.locals.put("linha1", _linha1);
 BA.debugLineNum = 176;BA.debugLine="If linha1 = Null Then";
Debug.ShouldStop(32768);
if (_linha1== null) { 
 BA.debugLineNum = 177;BA.debugLine="Return False";
Debug.ShouldStop(65536);
if (true) return (Object)(__c.False);
 };
 BA.debugLineNum = 179;BA.debugLine="linha2 = linha1.SubString2(0,linha1.IndexOf(\";\"))";
Debug.ShouldStop(262144);
_linha2 = _linha1.substring((int) (0),_linha1.indexOf(";"));Debug.locals.put("linha2", _linha2);
 BA.debugLineNum = 180;BA.debugLine="linha3 = linha1.SubString2(linha1.IndexOf(\";\")+1,linha1.LastIndexOf(\";\"))";
Debug.ShouldStop(524288);
_linha3 = _linha1.substring((int) (_linha1.indexOf(";")+1),_linha1.lastIndexOf(";"));Debug.locals.put("linha3", _linha3);
 BA.debugLineNum = 181;BA.debugLine="linha4 = linha1.SubString(linha1.LastIndexOf(\";\")+1)";
Debug.ShouldStop(1048576);
_linha4 = _linha1.substring((int) (_linha1.lastIndexOf(";")+1));Debug.locals.put("linha4", _linha4);
 BA.debugLineNum = 183;BA.debugLine="Do While linha1 <> Null";
Debug.ShouldStop(4194304);
while (_linha1!= null) {
 BA.debugLineNum = 184;BA.debugLine="If linha2 = Username Then";
Debug.ShouldStop(8388608);
if ((_linha2).equals(_username)) { 
 BA.debugLineNum = 185;BA.debugLine="Return linha1";
Debug.ShouldStop(16777216);
if (true) return (Object)(_linha1);
 };
 BA.debugLineNum = 187;BA.debugLine="linha1 = TextReader1.ReadLine";
Debug.ShouldStop(67108864);
_linha1 = _textreader1.ReadLine();Debug.locals.put("linha1", _linha1);
 }
;
 BA.debugLineNum = 189;BA.debugLine="TextReader1.Close";
Debug.ShouldStop(268435456);
_textreader1.Close();
 BA.debugLineNum = 190;BA.debugLine="Return Null";
Debug.ShouldStop(536870912);
if (true) return __c.Null;
 BA.debugLineNum = 191;BA.debugLine="End Sub";
Debug.ShouldStop(1073741824);
return null;
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public String  _initialize(anywheresoftware.b4a.BA _ba) throws Exception{
innerInitialize(_ba);
		Debug.PushSubsStack("Initialize (persistencia) ","persistencia",3,ba,this);
try {
Debug.locals.put("ba", _ba);
 BA.debugLineNum = 8;BA.debugLine="Public Sub Initialize";
Debug.ShouldStop(128);
 BA.debugLineNum = 10;BA.debugLine="End Sub";
Debug.ShouldStop(512);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public String  _remover_transacao(int _pos) throws Exception{
		Debug.PushSubsStack("Remover_Transacao (persistencia) ","persistencia",3,ba,this);
try {
String _linha = "";
int _i = 0;
float _valor = 0f;
Debug.locals.put("Pos", _pos);
 BA.debugLineNum = 150;BA.debugLine="Public Sub Remover_Transacao (Pos As Int)";
Debug.ShouldStop(2097152);
 BA.debugLineNum = 151;BA.debugLine="Dim Linha As String = Lista_Extrato.Get(Pos)";
Debug.ShouldStop(4194304);
_linha = BA.ObjectToString(_lista_extrato.Get(_pos));Debug.locals.put("Linha", _linha);Debug.locals.put("Linha", _linha);
 BA.debugLineNum = 152;BA.debugLine="Dim i As Int = Linha.IndexOf(\"|\")";
Debug.ShouldStop(8388608);
_i = _linha.indexOf("|");Debug.locals.put("i", _i);Debug.locals.put("i", _i);
 BA.debugLineNum = 153;BA.debugLine="Dim Valor As Float = Linha.SubString2(0,i)";
Debug.ShouldStop(16777216);
_valor = (float)(Double.parseDouble(_linha.substring((int) (0),_i)));Debug.locals.put("Valor", _valor);Debug.locals.put("Valor", _valor);
 BA.debugLineNum = 155;BA.debugLine="Saldo = Saldo - Valor";
Debug.ShouldStop(67108864);
_saldo = (float) (_saldo-_valor);
 BA.debugLineNum = 157;BA.debugLine="Lista_Extrato.RemoveAt(Pos)";
Debug.ShouldStop(268435456);
_lista_extrato.RemoveAt(_pos);
 BA.debugLineNum = 158;BA.debugLine="End Sub";
Debug.ShouldStop(536870912);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public String  _remover_transacao2(String _obj) throws Exception{
		Debug.PushSubsStack("Remover_Transacao2 (persistencia) ","persistencia",3,ba,this);
try {
int _pos = 0;
String _linha = "";
int _i = 0;
float _valor = 0f;
Debug.locals.put("Obj", _obj);
 BA.debugLineNum = 160;BA.debugLine="Public Sub Remover_Transacao2 (Obj As String)";
Debug.ShouldStop(-2147483648);
 BA.debugLineNum = 161;BA.debugLine="Dim Pos As Int = Lista_Extrato.IndexOf(Obj)";
Debug.ShouldStop(1);
_pos = _lista_extrato.IndexOf((Object)(_obj));Debug.locals.put("Pos", _pos);Debug.locals.put("Pos", _pos);
 BA.debugLineNum = 162;BA.debugLine="Dim Linha As String = Lista_Extrato.Get(Pos)";
Debug.ShouldStop(2);
_linha = BA.ObjectToString(_lista_extrato.Get(_pos));Debug.locals.put("Linha", _linha);Debug.locals.put("Linha", _linha);
 BA.debugLineNum = 163;BA.debugLine="Dim i As Int = Linha.IndexOf(\"|\")";
Debug.ShouldStop(4);
_i = _linha.indexOf("|");Debug.locals.put("i", _i);Debug.locals.put("i", _i);
 BA.debugLineNum = 164;BA.debugLine="Dim Valor As Float = Linha.SubString2(0,i)";
Debug.ShouldStop(8);
_valor = (float)(Double.parseDouble(_linha.substring((int) (0),_i)));Debug.locals.put("Valor", _valor);Debug.locals.put("Valor", _valor);
 BA.debugLineNum = 166;BA.debugLine="Saldo = Saldo - Valor";
Debug.ShouldStop(32);
_saldo = (float) (_saldo-_valor);
 BA.debugLineNum = 168;BA.debugLine="Lista_Extrato.RemoveAt(Pos)";
Debug.ShouldStop(128);
_lista_extrato.RemoveAt(_pos);
 BA.debugLineNum = 169;BA.debugLine="End Sub";
Debug.ShouldStop(256);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public boolean  _salvar_transacao(Object _valor,String _data,String _categoria,String _tipo) throws Exception{
		Debug.PushSubsStack("Salvar_Transacao (persistencia) ","persistencia",3,ba,this);
try {
float _valor1 = 0f;
Debug.locals.put("valor", _valor);
Debug.locals.put("Data", _data);
Debug.locals.put("Categoria", _categoria);
Debug.locals.put("Tipo", _tipo);
 BA.debugLineNum = 136;BA.debugLine="Public Sub Salvar_Transacao (valor As Object, Data As String, Categoria As String, Tipo As String) As Boolean";
Debug.ShouldStop(128);
 BA.debugLineNum = 137;BA.debugLine="Dim valor1 As Float";
Debug.ShouldStop(256);
_valor1 = 0f;Debug.locals.put("valor1", _valor1);
 BA.debugLineNum = 138;BA.debugLine="valor1 = valor";
Debug.ShouldStop(512);
_valor1 = (float)(BA.ObjectToNumber(_valor));Debug.locals.put("valor1", _valor1);
 BA.debugLineNum = 139;BA.debugLine="If Tipo = \"Crédito\" Then";
Debug.ShouldStop(1024);
if ((_tipo).equals("Crédito")) { 
 BA.debugLineNum = 140;BA.debugLine="Atualizar_Saldo(valor1)";
Debug.ShouldStop(2048);
_atualizar_saldo(_valor1);
 }else {
 BA.debugLineNum = 142;BA.debugLine="valor1 = valor1 * (-1)";
Debug.ShouldStop(8192);
_valor1 = (float) (_valor1*(-1));Debug.locals.put("valor1", _valor1);
 BA.debugLineNum = 143;BA.debugLine="Atualizar_Saldo(valor1)";
Debug.ShouldStop(16384);
_atualizar_saldo(_valor1);
 };
 BA.debugLineNum = 145;BA.debugLine="Lista_Extrato.Add(valor1 & \"|\" & Data & \"|\" & Categoria)";
Debug.ShouldStop(65536);
_lista_extrato.Add((Object)(BA.NumberToString(_valor1)+"|"+_data+"|"+_categoria));
 BA.debugLineNum = 147;BA.debugLine="Return True";
Debug.ShouldStop(262144);
if (true) return __c.True;
 BA.debugLineNum = 148;BA.debugLine="End Sub";
Debug.ShouldStop(524288);
return false;
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public Object callSub(String sub, Object sender, Object[] args) throws Exception {
ba.sharedProcessBA.sender = sender;
return BA.SubDelegator.SubNotFound;
}
}
