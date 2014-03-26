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
public Financas.Pessoais.editar _editar = null;
  public Object[] GetGlobals() {
		return new Object[] {"AddCategoria",Debug.moduleToString(Financas.Pessoais.addcategoria.class),"Cadastro",Debug.moduleToString(Financas.Pessoais.cadastro.class),"Calculadora",Debug.moduleToString(Financas.Pessoais.calculadora.class),"Creditos",Debug.moduleToString(Financas.Pessoais.creditos.class),"Debitos",Debug.moduleToString(Financas.Pessoais.debitos.class),"Editar",Debug.moduleToString(Financas.Pessoais.editar.class),"Excluir",Debug.moduleToString(Financas.Pessoais.excluir.class),"Extrato",Debug.moduleToString(Financas.Pessoais.extrato.class),"Financeiro",Debug.moduleToString(Financas.Pessoais.financeiro.class),"Main",Debug.moduleToString(Financas.Pessoais.main.class),"Menu",Debug.moduleToString(Financas.Pessoais.menu.class),"Remover_Categoria",Debug.moduleToString(Financas.Pessoais.remover_categoria.class),"Total",Debug.moduleToString(Financas.Pessoais.total.class),"Utilitarios",Debug.moduleToString(Financas.Pessoais.utilitarios.class)};
}
public String  _atualizar_saldo(float _valor) throws Exception{
		Debug.PushSubsStack("Atualizar_Saldo (persistencia) ","persistencia",9,ba,this);
try {
anywheresoftware.b4a.objects.streams.File.TextReaderWrapper _tr = null;
anywheresoftware.b4a.objects.streams.File.TextWriterWrapper _tw = null;
double _saldotemp = 0;
String _abc = "";
double _saldo = 0;
Debug.locals.put("valor", _valor);
 BA.debugLineNum = 133;BA.debugLine="Private Sub Atualizar_Saldo(valor As Float)";
Debug.ShouldStop(16);
 BA.debugLineNum = 134;BA.debugLine="Dim tr As TextReader";
Debug.ShouldStop(32);
_tr = new anywheresoftware.b4a.objects.streams.File.TextReaderWrapper();Debug.locals.put("tr", _tr);
 BA.debugLineNum = 135;BA.debugLine="Dim tw As TextWriter";
Debug.ShouldStop(64);
_tw = new anywheresoftware.b4a.objects.streams.File.TextWriterWrapper();Debug.locals.put("tw", _tw);
 BA.debugLineNum = 136;BA.debugLine="If Not(File.Exists(File.DirRootExternal,Logado&\"saldo.txt\")) Then";
Debug.ShouldStop(128);
if (__c.Not(__c.File.Exists(__c.File.getDirRootExternal(),_logado()+"saldo.txt"))) { 
 BA.debugLineNum = 137;BA.debugLine="tw.Initialize(File.OpenOutput(File.DirRootExternal,Logado&\"saldo.txt\",True))";
Debug.ShouldStop(256);
_tw.Initialize((java.io.OutputStream)(__c.File.OpenOutput(__c.File.getDirRootExternal(),_logado()+"saldo.txt",__c.True).getObject()));
 BA.debugLineNum = 138;BA.debugLine="tw.WriteLine(\"0\")";
Debug.ShouldStop(512);
_tw.WriteLine("0");
 BA.debugLineNum = 139;BA.debugLine="tw.Close";
Debug.ShouldStop(1024);
_tw.Close();
 };
 BA.debugLineNum = 141;BA.debugLine="tr.Initialize(File.OpenInput(File.DirRootExternal,Logado&\"saldo.txt\"))";
Debug.ShouldStop(4096);
_tr.Initialize((java.io.InputStream)(__c.File.OpenInput(__c.File.getDirRootExternal(),_logado()+"saldo.txt").getObject()));
 BA.debugLineNum = 142;BA.debugLine="Dim saldoTemp As Double";
Debug.ShouldStop(8192);
_saldotemp = 0;Debug.locals.put("saldoTemp", _saldotemp);
 BA.debugLineNum = 143;BA.debugLine="Dim abc As String = tr.ReadLine";
Debug.ShouldStop(16384);
_abc = _tr.ReadLine();Debug.locals.put("abc", _abc);Debug.locals.put("abc", _abc);
 BA.debugLineNum = 144;BA.debugLine="tr.Close";
Debug.ShouldStop(32768);
_tr.Close();
 BA.debugLineNum = 145;BA.debugLine="saldoTemp = abc";
Debug.ShouldStop(65536);
_saldotemp = (double)(Double.parseDouble(_abc));Debug.locals.put("saldoTemp", _saldotemp);
 BA.debugLineNum = 146;BA.debugLine="File.Delete(File.DirRootExternal,Logado&\"saldo.txt\")";
Debug.ShouldStop(131072);
__c.File.Delete(__c.File.getDirRootExternal(),_logado()+"saldo.txt");
 BA.debugLineNum = 147;BA.debugLine="tw.Initialize(File.OpenOutput(File.DirRootExternal,Logado&\"saldo.txt\",True))";
Debug.ShouldStop(262144);
_tw.Initialize((java.io.OutputStream)(__c.File.OpenOutput(__c.File.getDirRootExternal(),_logado()+"saldo.txt",__c.True).getObject()));
 BA.debugLineNum = 148;BA.debugLine="Dim Saldo As Double";
Debug.ShouldStop(524288);
_saldo = 0;Debug.locals.put("Saldo", _saldo);
 BA.debugLineNum = 150;BA.debugLine="Saldo = saldoTemp + valor";
Debug.ShouldStop(2097152);
_saldo = _saldotemp+_valor;Debug.locals.put("Saldo", _saldo);
 BA.debugLineNum = 152;BA.debugLine="tw.WriteLine(Saldo)";
Debug.ShouldStop(8388608);
_tw.WriteLine(BA.NumberToString(_saldo));
 BA.debugLineNum = 153;BA.debugLine="tw.Close";
Debug.ShouldStop(16777216);
_tw.Close();
 BA.debugLineNum = 154;BA.debugLine="End Sub";
Debug.ShouldStop(33554432);
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
 //BA.debugLineNum = 4;BA.debugLine="End Sub";
return "";
}
public boolean  _criar_login(String _nome,String _username,String _senha,String _senha_repetida) throws Exception{
		Debug.PushSubsStack("Criar_Login (persistencia) ","persistencia",9,ba,this);
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
 BA.debugLineNum = 47;BA.debugLine="Public Sub Criar_Login(Nome As String, Username As String, Senha As String, Senha_Repetida As String) As Boolean";
Debug.ShouldStop(16384);
 BA.debugLineNum = 48;BA.debugLine="If Nome = \"\" OR Username = \"\" OR Senha = \"\" OR Senha_Repetida = \"\" Then";
Debug.ShouldStop(32768);
if ((_nome).equals("") || (_username).equals("") || (_senha).equals("") || (_senha_repetida).equals("")) { 
 BA.debugLineNum = 49;BA.debugLine="Msgbox(\"Campos Obrigatórios não Preenchidos\", \"Atenção!\")";
Debug.ShouldStop(65536);
__c.Msgbox("Campos Obrigatórios não Preenchidos","Atenção!",getActivityBA());
 }else 
{ BA.debugLineNum = 50;BA.debugLine="Else If Not(Senha = Senha_Repetida) Then";
Debug.ShouldStop(131072);
if (__c.Not((_senha).equals(_senha_repetida))) { 
 BA.debugLineNum = 51;BA.debugLine="Msgbox(\"Senhas não conferem!\", \"Atenção!\")";
Debug.ShouldStop(262144);
__c.Msgbox("Senhas não conferem!","Atenção!",getActivityBA());
 }else {
 BA.debugLineNum = 53;BA.debugLine="If Not(File.Exists(File.DirRootExternal, \"Logins.txt\")) Then";
Debug.ShouldStop(1048576);
if (__c.Not(__c.File.Exists(__c.File.getDirRootExternal(),"Logins.txt"))) { 
 BA.debugLineNum = 54;BA.debugLine="Dim TextWriter1 As TextWriter";
Debug.ShouldStop(2097152);
_textwriter1 = new anywheresoftware.b4a.objects.streams.File.TextWriterWrapper();Debug.locals.put("TextWriter1", _textwriter1);
 BA.debugLineNum = 55;BA.debugLine="TextWriter1.Initialize(File.OpenOutput(File.DirRootExternal, \"Logins.txt\", True))";
Debug.ShouldStop(4194304);
_textwriter1.Initialize((java.io.OutputStream)(__c.File.OpenOutput(__c.File.getDirRootExternal(),"Logins.txt",__c.True).getObject()));
 BA.debugLineNum = 56;BA.debugLine="TextWriter1.WriteLine(Username &\";\"& Senha &\";\"& Nome)";
Debug.ShouldStop(8388608);
_textwriter1.WriteLine(_username+";"+_senha+";"+_nome);
 BA.debugLineNum = 57;BA.debugLine="TextWriter1.Close";
Debug.ShouldStop(16777216);
_textwriter1.Close();
 BA.debugLineNum = 58;BA.debugLine="Return True";
Debug.ShouldStop(33554432);
if (true) return __c.True;
 }else {
 BA.debugLineNum = 60;BA.debugLine="Dim TextReader1 As TextReader";
Debug.ShouldStop(134217728);
_textreader1 = new anywheresoftware.b4a.objects.streams.File.TextReaderWrapper();Debug.locals.put("TextReader1", _textreader1);
 BA.debugLineNum = 61;BA.debugLine="TextReader1.Initialize(File.OpenInput(File.DirRootExternal, \"Logins.txt\"))";
Debug.ShouldStop(268435456);
_textreader1.Initialize((java.io.InputStream)(__c.File.OpenInput(__c.File.getDirRootExternal(),"Logins.txt").getObject()));
 BA.debugLineNum = 62;BA.debugLine="Dim linha1,linha2,linha3,linha4 As String";
Debug.ShouldStop(536870912);
_linha1 = "";Debug.locals.put("linha1", _linha1);
_linha2 = "";Debug.locals.put("linha2", _linha2);
_linha3 = "";Debug.locals.put("linha3", _linha3);
_linha4 = "";Debug.locals.put("linha4", _linha4);
 BA.debugLineNum = 63;BA.debugLine="linha1 = TextReader1.ReadLine";
Debug.ShouldStop(1073741824);
_linha1 = _textreader1.ReadLine();Debug.locals.put("linha1", _linha1);
 BA.debugLineNum = 65;BA.debugLine="If linha1 = Null Then";
Debug.ShouldStop(1);
if (_linha1== null) { 
 BA.debugLineNum = 66;BA.debugLine="Dim TextWriter1 As TextWriter";
Debug.ShouldStop(2);
_textwriter1 = new anywheresoftware.b4a.objects.streams.File.TextWriterWrapper();Debug.locals.put("TextWriter1", _textwriter1);
 BA.debugLineNum = 67;BA.debugLine="TextWriter1.Initialize(File.OpenOutput(File.DirRootExternal, \"Logins.txt\", True))";
Debug.ShouldStop(4);
_textwriter1.Initialize((java.io.OutputStream)(__c.File.OpenOutput(__c.File.getDirRootExternal(),"Logins.txt",__c.True).getObject()));
 BA.debugLineNum = 68;BA.debugLine="TextWriter1.WriteLine(Username &\";\"& Senha &\";\"& Nome)";
Debug.ShouldStop(8);
_textwriter1.WriteLine(_username+";"+_senha+";"+_nome);
 BA.debugLineNum = 69;BA.debugLine="TextWriter1.Close";
Debug.ShouldStop(16);
_textwriter1.Close();
 BA.debugLineNum = 70;BA.debugLine="Return True";
Debug.ShouldStop(32);
if (true) return __c.True;
 };
 BA.debugLineNum = 73;BA.debugLine="linha2 = linha1.SubString2(0,linha1.IndexOf(\";\"))";
Debug.ShouldStop(256);
_linha2 = _linha1.substring((int) (0),_linha1.indexOf(";"));Debug.locals.put("linha2", _linha2);
 BA.debugLineNum = 74;BA.debugLine="linha3 = linha1.SubString2(linha1.IndexOf(\";\")+1,linha1.LastIndexOf(\";\"))";
Debug.ShouldStop(512);
_linha3 = _linha1.substring((int) (_linha1.indexOf(";")+1),_linha1.lastIndexOf(";"));Debug.locals.put("linha3", _linha3);
 BA.debugLineNum = 75;BA.debugLine="linha4 = linha1.SubString(linha1.LastIndexOf(\";\")+1)";
Debug.ShouldStop(1024);
_linha4 = _linha1.substring((int) (_linha1.lastIndexOf(";")+1));Debug.locals.put("linha4", _linha4);
 BA.debugLineNum = 77;BA.debugLine="Do While linha1 <> Null";
Debug.ShouldStop(4096);
while (_linha1!= null) {
 BA.debugLineNum = 78;BA.debugLine="If linha2 = Username Then";
Debug.ShouldStop(8192);
if ((_linha2).equals(_username)) { 
 BA.debugLineNum = 79;BA.debugLine="Msgbox(\"Username já existe!\",\"Atenção!\")";
Debug.ShouldStop(16384);
__c.Msgbox("Username já existe!","Atenção!",getActivityBA());
 BA.debugLineNum = 80;BA.debugLine="linha1 = TextReader1.ReadLine";
Debug.ShouldStop(32768);
_linha1 = _textreader1.ReadLine();Debug.locals.put("linha1", _linha1);
 }else {
 BA.debugLineNum = 82;BA.debugLine="Dim TextWriter2 As TextWriter";
Debug.ShouldStop(131072);
_textwriter2 = new anywheresoftware.b4a.objects.streams.File.TextWriterWrapper();Debug.locals.put("TextWriter2", _textwriter2);
 BA.debugLineNum = 83;BA.debugLine="TextWriter2.Initialize(File.OpenOutput(File.DirRootExternal, \"Logins.txt\", True))";
Debug.ShouldStop(262144);
_textwriter2.Initialize((java.io.OutputStream)(__c.File.OpenOutput(__c.File.getDirRootExternal(),"Logins.txt",__c.True).getObject()));
 BA.debugLineNum = 84;BA.debugLine="TextWriter2.WriteLine(Username &\";\"& Senha &\";\"& Nome)";
Debug.ShouldStop(524288);
_textwriter2.WriteLine(_username+";"+_senha+";"+_nome);
 BA.debugLineNum = 85;BA.debugLine="TextWriter2.Close";
Debug.ShouldStop(1048576);
_textwriter2.Close();
 BA.debugLineNum = 86;BA.debugLine="Return True";
Debug.ShouldStop(2097152);
if (true) return __c.True;
 };
 }
;
 BA.debugLineNum = 89;BA.debugLine="TextReader1.Close";
Debug.ShouldStop(16777216);
_textreader1.Close();
 };
 }};
 BA.debugLineNum = 92;BA.debugLine="Return False";
Debug.ShouldStop(134217728);
if (true) return __c.False;
 BA.debugLineNum = 93;BA.debugLine="End Sub";
Debug.ShouldStop(268435456);
return false;
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public boolean  _deletar_categoria(String _categoria) throws Exception{
		Debug.PushSubsStack("Deletar_Categoria (persistencia) ","persistencia",9,ba,this);
try {
anywheresoftware.b4a.objects.streams.File.TextReaderWrapper _textreader1 = null;
anywheresoftware.b4a.objects.streams.File.TextWriterWrapper _textwriter1 = null;
anywheresoftware.b4a.objects.collections.List _lista = null;
Debug.locals.put("Categoria", _categoria);
 BA.debugLineNum = 277;BA.debugLine="Public Sub Deletar_Categoria(Categoria As String) As Boolean";
Debug.ShouldStop(1048576);
 BA.debugLineNum = 278;BA.debugLine="Dim TextReader1 As TextReader";
Debug.ShouldStop(2097152);
_textreader1 = new anywheresoftware.b4a.objects.streams.File.TextReaderWrapper();Debug.locals.put("TextReader1", _textreader1);
 BA.debugLineNum = 279;BA.debugLine="Dim TextWriter1 As TextWriter";
Debug.ShouldStop(4194304);
_textwriter1 = new anywheresoftware.b4a.objects.streams.File.TextWriterWrapper();Debug.locals.put("TextWriter1", _textwriter1);
 BA.debugLineNum = 280;BA.debugLine="If Categoria = \"Agua\" OR Categoria = \"Luz\" OR Categoria = \"Telefone\" OR Categoria = \"Interet\" OR Categoria = \"TV\" OR Categoria = \"Salario\" OR Categoria = \"Alimentacao\" OR Categoria = \"Combustivel\" OR Categoria = \"Construcao\" Then";
Debug.ShouldStop(8388608);
if ((_categoria).equals("Agua") || (_categoria).equals("Luz") || (_categoria).equals("Telefone") || (_categoria).equals("Interet") || (_categoria).equals("TV") || (_categoria).equals("Salario") || (_categoria).equals("Alimentacao") || (_categoria).equals("Combustivel") || (_categoria).equals("Construcao")) { 
 BA.debugLineNum = 281;BA.debugLine="Return False";
Debug.ShouldStop(16777216);
if (true) return __c.False;
 }else {
 BA.debugLineNum = 283;BA.debugLine="TextReader1.Initialize(File.OpenInput(File.DirRootExternal,\"categ.txt\"))";
Debug.ShouldStop(67108864);
_textreader1.Initialize((java.io.InputStream)(__c.File.OpenInput(__c.File.getDirRootExternal(),"categ.txt").getObject()));
 BA.debugLineNum = 284;BA.debugLine="Dim Lista As List";
Debug.ShouldStop(134217728);
_lista = new anywheresoftware.b4a.objects.collections.List();Debug.locals.put("Lista", _lista);
 BA.debugLineNum = 285;BA.debugLine="Lista.Initialize";
Debug.ShouldStop(268435456);
_lista.Initialize();
 BA.debugLineNum = 286;BA.debugLine="Lista.AddAll(TextReader1.ReadList)";
Debug.ShouldStop(536870912);
_lista.AddAll(_textreader1.ReadList());
 BA.debugLineNum = 287;BA.debugLine="File.Delete(File.DirRootExternal,\"categ.txt\")";
Debug.ShouldStop(1073741824);
__c.File.Delete(__c.File.getDirRootExternal(),"categ.txt");
 BA.debugLineNum = 288;BA.debugLine="TextWriter1.Initialize(File.OpenOutput(File.DirRootExternal,\"categ.txt\",True))";
Debug.ShouldStop(-2147483648);
_textwriter1.Initialize((java.io.OutputStream)(__c.File.OpenOutput(__c.File.getDirRootExternal(),"categ.txt",__c.True).getObject()));
 BA.debugLineNum = 289;BA.debugLine="TextWriter1.WriteList(Lista)";
Debug.ShouldStop(1);
_textwriter1.WriteList(_lista);
 BA.debugLineNum = 290;BA.debugLine="TextReader1.Close";
Debug.ShouldStop(2);
_textreader1.Close();
 BA.debugLineNum = 291;BA.debugLine="TextWriter1.Close";
Debug.ShouldStop(4);
_textwriter1.Close();
 BA.debugLineNum = 292;BA.debugLine="Return True";
Debug.ShouldStop(8);
if (true) return __c.True;
 };
 BA.debugLineNum = 294;BA.debugLine="End Sub";
Debug.ShouldStop(32);
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
		Debug.PushSubsStack("Excluir_Login (persistencia) ","persistencia",9,ba,this);
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
 BA.debugLineNum = 95;BA.debugLine="Public Sub Excluir_Login(Username As String, Senha As String) As Boolean";
Debug.ShouldStop(1073741824);
 BA.debugLineNum = 96;BA.debugLine="If Username = \"\" OR Senha = \"\" Then";
Debug.ShouldStop(-2147483648);
if ((_username).equals("") || (_senha).equals("")) { 
 BA.debugLineNum = 97;BA.debugLine="Msgbox(\"Campos Obrigatórios não Preenchidos\", \"Atenção!\")";
Debug.ShouldStop(1);
__c.Msgbox("Campos Obrigatórios não Preenchidos","Atenção!",getActivityBA());
 }else {
 BA.debugLineNum = 99;BA.debugLine="Dim TextReader1 As TextReader";
Debug.ShouldStop(4);
_textreader1 = new anywheresoftware.b4a.objects.streams.File.TextReaderWrapper();Debug.locals.put("TextReader1", _textreader1);
 BA.debugLineNum = 100;BA.debugLine="TextReader1.Initialize(File.OpenInput(File.DirRootExternal, \"Logins.txt\"))";
Debug.ShouldStop(8);
_textreader1.Initialize((java.io.InputStream)(__c.File.OpenInput(__c.File.getDirRootExternal(),"Logins.txt").getObject()));
 BA.debugLineNum = 102;BA.debugLine="Dim texto As List";
Debug.ShouldStop(32);
_texto = new anywheresoftware.b4a.objects.collections.List();Debug.locals.put("texto", _texto);
 BA.debugLineNum = 103;BA.debugLine="texto.Initialize";
Debug.ShouldStop(64);
_texto.Initialize();
 BA.debugLineNum = 105;BA.debugLine="Dim linha1,linha2,linha3,linha4 As String";
Debug.ShouldStop(256);
_linha1 = "";Debug.locals.put("linha1", _linha1);
_linha2 = "";Debug.locals.put("linha2", _linha2);
_linha3 = "";Debug.locals.put("linha3", _linha3);
_linha4 = "";Debug.locals.put("linha4", _linha4);
 BA.debugLineNum = 106;BA.debugLine="linha1 = TextReader1.ReadLine";
Debug.ShouldStop(512);
_linha1 = _textreader1.ReadLine();Debug.locals.put("linha1", _linha1);
 BA.debugLineNum = 107;BA.debugLine="linha2 = linha1.SubString2(0,linha1.IndexOf(\";\"))";
Debug.ShouldStop(1024);
_linha2 = _linha1.substring((int) (0),_linha1.indexOf(";"));Debug.locals.put("linha2", _linha2);
 BA.debugLineNum = 108;BA.debugLine="linha3 = linha1.SubString2(linha1.IndexOf(\";\")+1,linha1.LastIndexOf(\";\"))";
Debug.ShouldStop(2048);
_linha3 = _linha1.substring((int) (_linha1.indexOf(";")+1),_linha1.lastIndexOf(";"));Debug.locals.put("linha3", _linha3);
 BA.debugLineNum = 109;BA.debugLine="linha4 = linha1.SubString(linha1.LastIndexOf(\";\")+1)";
Debug.ShouldStop(4096);
_linha4 = _linha1.substring((int) (_linha1.lastIndexOf(";")+1));Debug.locals.put("linha4", _linha4);
 BA.debugLineNum = 111;BA.debugLine="Do While linha1 <> Null";
Debug.ShouldStop(16384);
while (_linha1!= null) {
 BA.debugLineNum = 112;BA.debugLine="If linha2 & linha3 = Username & Senha Then";
Debug.ShouldStop(32768);
if ((_linha2+_linha3).equals(_username+_senha)) { 
 BA.debugLineNum = 113;BA.debugLine="If File.Delete(File.DirRootExternal, \"Logins.txt\") Then";
Debug.ShouldStop(65536);
if (__c.File.Delete(__c.File.getDirRootExternal(),"Logins.txt")) { 
 BA.debugLineNum = 114;BA.debugLine="Dim TextWriter1 As TextWriter";
Debug.ShouldStop(131072);
_textwriter1 = new anywheresoftware.b4a.objects.streams.File.TextWriterWrapper();Debug.locals.put("TextWriter1", _textwriter1);
 BA.debugLineNum = 115;BA.debugLine="TextWriter1.Initialize(File.OpenOutput(File.DirRootExternal, \"Logins.txt\", True))";
Debug.ShouldStop(262144);
_textwriter1.Initialize((java.io.OutputStream)(__c.File.OpenOutput(__c.File.getDirRootExternal(),"Logins.txt",__c.True).getObject()));
 BA.debugLineNum = 116;BA.debugLine="If texto <> Null Then";
Debug.ShouldStop(524288);
if (_texto!= null) { 
 BA.debugLineNum = 117;BA.debugLine="TextWriter1.WriteList(texto)";
Debug.ShouldStop(1048576);
_textwriter1.WriteList(_texto);
 };
 BA.debugLineNum = 119;BA.debugLine="TextWriter1.Close";
Debug.ShouldStop(4194304);
_textwriter1.Close();
 BA.debugLineNum = 120;BA.debugLine="TextReader1.Close";
Debug.ShouldStop(8388608);
_textreader1.Close();
 BA.debugLineNum = 121;BA.debugLine="Return True";
Debug.ShouldStop(16777216);
if (true) return __c.True;
 };
 }else {
 BA.debugLineNum = 124;BA.debugLine="texto.Add(linha1)";
Debug.ShouldStop(134217728);
_texto.Add((Object)(_linha1));
 BA.debugLineNum = 125;BA.debugLine="linha1 = TextReader1.ReadLine";
Debug.ShouldStop(268435456);
_linha1 = _textreader1.ReadLine();Debug.locals.put("linha1", _linha1);
 };
 }
;
 };
 BA.debugLineNum = 130;BA.debugLine="Return False";
Debug.ShouldStop(2);
if (true) return __c.False;
 BA.debugLineNum = 131;BA.debugLine="End Sub";
Debug.ShouldStop(4);
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
		Debug.PushSubsStack("Fazer_Login (persistencia) ","persistencia",9,ba,this);
try {
anywheresoftware.b4a.objects.streams.File.TextWriterWrapper _textwriter1 = null;
anywheresoftware.b4a.objects.streams.File.TextReaderWrapper _textreader1 = null;
String _linha1 = "";
String _linha2 = "";
String _linha3 = "";
String _linha4 = "";
Debug.locals.put("Username", _username);
Debug.locals.put("Senha", _senha);
 BA.debugLineNum = 10;BA.debugLine="Public Sub Fazer_Login (Username As String, Senha As String) As Boolean";
Debug.ShouldStop(512);
 BA.debugLineNum = 11;BA.debugLine="If Username = \"\" OR Senha = \"\" Then";
Debug.ShouldStop(1024);
if ((_username).equals("") || (_senha).equals("")) { 
 BA.debugLineNum = 12;BA.debugLine="Msgbox(\"Campos Obrigatorios não estão preenchidos\", \"Aviso!\" )";
Debug.ShouldStop(2048);
__c.Msgbox("Campos Obrigatorios não estão preenchidos","Aviso!",getActivityBA());
 }else {
 BA.debugLineNum = 14;BA.debugLine="If Not(File.Exists(File.DirRootExternal, \"Logins.txt\")) Then";
Debug.ShouldStop(8192);
if (__c.Not(__c.File.Exists(__c.File.getDirRootExternal(),"Logins.txt"))) { 
 BA.debugLineNum = 15;BA.debugLine="Dim TextWriter1 As TextWriter";
Debug.ShouldStop(16384);
_textwriter1 = new anywheresoftware.b4a.objects.streams.File.TextWriterWrapper();Debug.locals.put("TextWriter1", _textwriter1);
 BA.debugLineNum = 16;BA.debugLine="TextWriter1.Initialize(File.OpenOutput(File.DirRootExternal, \"Logins.txt\", False))";
Debug.ShouldStop(32768);
_textwriter1.Initialize((java.io.OutputStream)(__c.File.OpenOutput(__c.File.getDirRootExternal(),"Logins.txt",__c.False).getObject()));
 BA.debugLineNum = 17;BA.debugLine="TextWriter1.Close";
Debug.ShouldStop(65536);
_textwriter1.Close();
 }else {
 BA.debugLineNum = 19;BA.debugLine="Dim TextReader1 As TextReader";
Debug.ShouldStop(262144);
_textreader1 = new anywheresoftware.b4a.objects.streams.File.TextReaderWrapper();Debug.locals.put("TextReader1", _textreader1);
 BA.debugLineNum = 20;BA.debugLine="TextReader1.Initialize(File.OpenInput(File.DirRootExternal, \"Logins.txt\"))";
Debug.ShouldStop(524288);
_textreader1.Initialize((java.io.InputStream)(__c.File.OpenInput(__c.File.getDirRootExternal(),"Logins.txt").getObject()));
 BA.debugLineNum = 21;BA.debugLine="Dim linha1,linha2,linha3,linha4 As String";
Debug.ShouldStop(1048576);
_linha1 = "";Debug.locals.put("linha1", _linha1);
_linha2 = "";Debug.locals.put("linha2", _linha2);
_linha3 = "";Debug.locals.put("linha3", _linha3);
_linha4 = "";Debug.locals.put("linha4", _linha4);
 BA.debugLineNum = 22;BA.debugLine="linha1 = TextReader1.ReadLine";
Debug.ShouldStop(2097152);
_linha1 = _textreader1.ReadLine();Debug.locals.put("linha1", _linha1);
 BA.debugLineNum = 23;BA.debugLine="If linha1 = Null Then";
Debug.ShouldStop(4194304);
if (_linha1== null) { 
 BA.debugLineNum = 24;BA.debugLine="Return False";
Debug.ShouldStop(8388608);
if (true) return __c.False;
 };
 BA.debugLineNum = 26;BA.debugLine="linha2 = linha1.SubString2(0,linha1.IndexOf(\";\"))";
Debug.ShouldStop(33554432);
_linha2 = _linha1.substring((int) (0),_linha1.indexOf(";"));Debug.locals.put("linha2", _linha2);
 BA.debugLineNum = 27;BA.debugLine="linha3 = linha1.SubString2(linha1.IndexOf(\";\")+1,linha1.LastIndexOf(\";\"))";
Debug.ShouldStop(67108864);
_linha3 = _linha1.substring((int) (_linha1.indexOf(";")+1),_linha1.lastIndexOf(";"));Debug.locals.put("linha3", _linha3);
 BA.debugLineNum = 28;BA.debugLine="linha4 = linha1.SubString(linha1.LastIndexOf(\";\")+1)";
Debug.ShouldStop(134217728);
_linha4 = _linha1.substring((int) (_linha1.lastIndexOf(";")+1));Debug.locals.put("linha4", _linha4);
 BA.debugLineNum = 30;BA.debugLine="Do While linha1 <> Null";
Debug.ShouldStop(536870912);
while (_linha1!= null) {
 BA.debugLineNum = 31;BA.debugLine="If linha2 & linha3 = Username & Senha Then";
Debug.ShouldStop(1073741824);
if ((_linha2+_linha3).equals(_username+_senha)) { 
 BA.debugLineNum = 32;BA.debugLine="Return True";
Debug.ShouldStop(-2147483648);
if (true) return __c.True;
 };
 BA.debugLineNum = 34;BA.debugLine="linha1 = TextReader1.ReadLine";
Debug.ShouldStop(2);
_linha1 = _textreader1.ReadLine();Debug.locals.put("linha1", _linha1);
 BA.debugLineNum = 35;BA.debugLine="linha2 = linha1.SubString2(0,linha1.IndexOf(\";\"))";
Debug.ShouldStop(4);
_linha2 = _linha1.substring((int) (0),_linha1.indexOf(";"));Debug.locals.put("linha2", _linha2);
 BA.debugLineNum = 36;BA.debugLine="linha3 = linha1.SubString2(linha1.IndexOf(\";\")+1,linha1.LastIndexOf(\";\"))";
Debug.ShouldStop(8);
_linha3 = _linha1.substring((int) (_linha1.indexOf(";")+1),_linha1.lastIndexOf(";"));Debug.locals.put("linha3", _linha3);
 BA.debugLineNum = 37;BA.debugLine="linha4 = linha1.SubString(linha1.LastIndexOf(\";\")+1)";
Debug.ShouldStop(16);
_linha4 = _linha1.substring((int) (_linha1.lastIndexOf(";")+1));Debug.locals.put("linha4", _linha4);
 }
;
 BA.debugLineNum = 39;BA.debugLine="TextReader1.Close";
Debug.ShouldStop(64);
_textreader1.Close();
 };
 };
 BA.debugLineNum = 43;BA.debugLine="Return False";
Debug.ShouldStop(1024);
if (true) return __c.False;
 BA.debugLineNum = 45;BA.debugLine="End Sub";
Debug.ShouldStop(4096);
return false;
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public anywheresoftware.b4a.objects.collections.List  _getcategorias() throws Exception{
		Debug.PushSubsStack("GetCategorias (persistencia) ","persistencia",9,ba,this);
try {
anywheresoftware.b4a.objects.streams.File.TextReaderWrapper _textreader1 = null;
anywheresoftware.b4a.objects.streams.File.TextReaderWrapper _textreader2 = null;
anywheresoftware.b4a.objects.streams.File.TextWriterWrapper _textwriter1 = null;
anywheresoftware.b4a.objects.collections.List _lista = null;
 BA.debugLineNum = 296;BA.debugLine="Public Sub GetCategorias As List";
Debug.ShouldStop(128);
 BA.debugLineNum = 297;BA.debugLine="Dim TextReader1,TextReader2 As TextReader";
Debug.ShouldStop(256);
_textreader1 = new anywheresoftware.b4a.objects.streams.File.TextReaderWrapper();Debug.locals.put("TextReader1", _textreader1);
_textreader2 = new anywheresoftware.b4a.objects.streams.File.TextReaderWrapper();Debug.locals.put("TextReader2", _textreader2);
 BA.debugLineNum = 298;BA.debugLine="TextReader1.Initialize(File.OpenInput(File.DirAssets,\"categ.txt\"))";
Debug.ShouldStop(512);
_textreader1.Initialize((java.io.InputStream)(__c.File.OpenInput(__c.File.getDirAssets(),"categ.txt").getObject()));
 BA.debugLineNum = 299;BA.debugLine="If Not(File.Exists(File.DirRootExternal,\"categ.txt\")) Then";
Debug.ShouldStop(1024);
if (__c.Not(__c.File.Exists(__c.File.getDirRootExternal(),"categ.txt"))) { 
 BA.debugLineNum = 300;BA.debugLine="Dim TextWriter1 As TextWriter";
Debug.ShouldStop(2048);
_textwriter1 = new anywheresoftware.b4a.objects.streams.File.TextWriterWrapper();Debug.locals.put("TextWriter1", _textwriter1);
 BA.debugLineNum = 301;BA.debugLine="TextWriter1.Initialize(File.OpenOutput(File.DirRootExternal,\"categ.txt\",True))";
Debug.ShouldStop(4096);
_textwriter1.Initialize((java.io.OutputStream)(__c.File.OpenOutput(__c.File.getDirRootExternal(),"categ.txt",__c.True).getObject()));
 BA.debugLineNum = 302;BA.debugLine="TextWriter1.Close";
Debug.ShouldStop(8192);
_textwriter1.Close();
 };
 BA.debugLineNum = 304;BA.debugLine="TextReader2.Initialize(File.OpenInput(File.DirRootExternal,\"categ.txt\"))";
Debug.ShouldStop(32768);
_textreader2.Initialize((java.io.InputStream)(__c.File.OpenInput(__c.File.getDirRootExternal(),"categ.txt").getObject()));
 BA.debugLineNum = 305;BA.debugLine="Dim Lista As List";
Debug.ShouldStop(65536);
_lista = new anywheresoftware.b4a.objects.collections.List();Debug.locals.put("Lista", _lista);
 BA.debugLineNum = 306;BA.debugLine="Lista.Initialize";
Debug.ShouldStop(131072);
_lista.Initialize();
 BA.debugLineNum = 307;BA.debugLine="Lista.AddAll(TextReader1.ReadList)";
Debug.ShouldStop(262144);
_lista.AddAll(_textreader1.ReadList());
 BA.debugLineNum = 308;BA.debugLine="Lista.AddAll(TextReader2.ReadList)";
Debug.ShouldStop(524288);
_lista.AddAll(_textreader2.ReadList());
 BA.debugLineNum = 309;BA.debugLine="TextReader1.Close";
Debug.ShouldStop(1048576);
_textreader1.Close();
 BA.debugLineNum = 310;BA.debugLine="TextReader2.Close";
Debug.ShouldStop(2097152);
_textreader2.Close();
 BA.debugLineNum = 311;BA.debugLine="Return Lista";
Debug.ShouldStop(4194304);
if (true) return _lista;
 BA.debugLineNum = 312;BA.debugLine="End Sub";
Debug.ShouldStop(8388608);
return null;
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public String  _getsaldo() throws Exception{
		Debug.PushSubsStack("GetSaldo (persistencia) ","persistencia",9,ba,this);
try {
anywheresoftware.b4a.objects.streams.File.TextReaderWrapper _tr = null;
anywheresoftware.b4a.objects.streams.File.TextWriterWrapper _tw = null;
String _saldo = "";
 BA.debugLineNum = 156;BA.debugLine="Public Sub GetSaldo As String";
Debug.ShouldStop(134217728);
 BA.debugLineNum = 157;BA.debugLine="Dim tr As TextReader";
Debug.ShouldStop(268435456);
_tr = new anywheresoftware.b4a.objects.streams.File.TextReaderWrapper();Debug.locals.put("tr", _tr);
 BA.debugLineNum = 158;BA.debugLine="Dim tw As TextWriter";
Debug.ShouldStop(536870912);
_tw = new anywheresoftware.b4a.objects.streams.File.TextWriterWrapper();Debug.locals.put("tw", _tw);
 BA.debugLineNum = 159;BA.debugLine="If Not(File.Exists(File.DirRootExternal,Logado&\"saldo.txt\")) Then";
Debug.ShouldStop(1073741824);
if (__c.Not(__c.File.Exists(__c.File.getDirRootExternal(),_logado()+"saldo.txt"))) { 
 BA.debugLineNum = 160;BA.debugLine="tw.Initialize(File.OpenOutput(File.DirRootExternal,Logado&\"saldo.txt\",True))";
Debug.ShouldStop(-2147483648);
_tw.Initialize((java.io.OutputStream)(__c.File.OpenOutput(__c.File.getDirRootExternal(),_logado()+"saldo.txt",__c.True).getObject()));
 BA.debugLineNum = 161;BA.debugLine="tw.Close";
Debug.ShouldStop(1);
_tw.Close();
 BA.debugLineNum = 162;BA.debugLine="Return 0";
Debug.ShouldStop(2);
if (true) return BA.NumberToString(0);
 };
 BA.debugLineNum = 164;BA.debugLine="tr.Initialize(File.OpenInput(File.DirRootExternal,Logado&\"saldo.txt\"))";
Debug.ShouldStop(8);
_tr.Initialize((java.io.InputStream)(__c.File.OpenInput(__c.File.getDirRootExternal(),_logado()+"saldo.txt").getObject()));
 BA.debugLineNum = 165;BA.debugLine="Dim saldo As String";
Debug.ShouldStop(16);
_saldo = "";Debug.locals.put("saldo", _saldo);
 BA.debugLineNum = 166;BA.debugLine="saldo = tr.ReadLine";
Debug.ShouldStop(32);
_saldo = _tr.ReadLine();Debug.locals.put("saldo", _saldo);
 BA.debugLineNum = 167;BA.debugLine="Return saldo";
Debug.ShouldStop(64);
if (true) return _saldo;
 BA.debugLineNum = 168;BA.debugLine="End Sub";
Debug.ShouldStop(128);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public anywheresoftware.b4a.objects.collections.List  _gettransacoes(String _usuario) throws Exception{
		Debug.PushSubsStack("GetTransacoes (persistencia) ","persistencia",9,ba,this);
try {
anywheresoftware.b4a.objects.streams.File.TextReaderWrapper _textreader1 = null;
anywheresoftware.b4a.objects.streams.File.TextWriterWrapper _tw = null;
String _transacao = "";
String _linha1 = "";
String _linha2 = "";
anywheresoftware.b4a.objects.collections.List _transacoes = null;
Debug.locals.put("Usuario", _usuario);
 BA.debugLineNum = 178;BA.debugLine="Public Sub GetTransacoes(Usuario As String) As List";
Debug.ShouldStop(131072);
 BA.debugLineNum = 179;BA.debugLine="Dim TextReader1 As TextReader";
Debug.ShouldStop(262144);
_textreader1 = new anywheresoftware.b4a.objects.streams.File.TextReaderWrapper();Debug.locals.put("TextReader1", _textreader1);
 BA.debugLineNum = 180;BA.debugLine="If Not(File.Exists(File.DirRootExternal,Logado&\"transacoes.txt\")) Then";
Debug.ShouldStop(524288);
if (__c.Not(__c.File.Exists(__c.File.getDirRootExternal(),_logado()+"transacoes.txt"))) { 
 BA.debugLineNum = 181;BA.debugLine="Dim tw As TextWriter";
Debug.ShouldStop(1048576);
_tw = new anywheresoftware.b4a.objects.streams.File.TextWriterWrapper();Debug.locals.put("tw", _tw);
 BA.debugLineNum = 182;BA.debugLine="tw.Initialize(File.OpenOutput(File.DirRootExternal,Logado&\"transacoes.txt\",True))";
Debug.ShouldStop(2097152);
_tw.Initialize((java.io.OutputStream)(__c.File.OpenOutput(__c.File.getDirRootExternal(),_logado()+"transacoes.txt",__c.True).getObject()));
 BA.debugLineNum = 183;BA.debugLine="tw.Close";
Debug.ShouldStop(4194304);
_tw.Close();
 };
 BA.debugLineNum = 185;BA.debugLine="TextReader1.Initialize(File.OpenInput(File.DirRootExternal,Logado&\"transacoes.txt\"))";
Debug.ShouldStop(16777216);
_textreader1.Initialize((java.io.InputStream)(__c.File.OpenInput(__c.File.getDirRootExternal(),_logado()+"transacoes.txt").getObject()));
 BA.debugLineNum = 186;BA.debugLine="Dim transacao,linha1,linha2 As String";
Debug.ShouldStop(33554432);
_transacao = "";Debug.locals.put("transacao", _transacao);
_linha1 = "";Debug.locals.put("linha1", _linha1);
_linha2 = "";Debug.locals.put("linha2", _linha2);
 BA.debugLineNum = 187;BA.debugLine="Dim transacoes As List";
Debug.ShouldStop(67108864);
_transacoes = new anywheresoftware.b4a.objects.collections.List();Debug.locals.put("transacoes", _transacoes);
 BA.debugLineNum = 188;BA.debugLine="transacoes.Initialize";
Debug.ShouldStop(134217728);
_transacoes.Initialize();
 BA.debugLineNum = 189;BA.debugLine="linha1 = TextReader1.ReadLine";
Debug.ShouldStop(268435456);
_linha1 = _textreader1.ReadLine();Debug.locals.put("linha1", _linha1);
 BA.debugLineNum = 190;BA.debugLine="Do While linha1 <> Null";
Debug.ShouldStop(536870912);
while (_linha1!= null) {
 BA.debugLineNum = 191;BA.debugLine="linha2 = linha1.SubString2(0,linha1.IndexOf(\";\"))";
Debug.ShouldStop(1073741824);
_linha2 = _linha1.substring((int) (0),_linha1.indexOf(";"));Debug.locals.put("linha2", _linha2);
 BA.debugLineNum = 192;BA.debugLine="If linha2 = Usuario Then";
Debug.ShouldStop(-2147483648);
if ((_linha2).equals(_usuario)) { 
 BA.debugLineNum = 193;BA.debugLine="transacao = linha1.SubString(linha1.IndexOf(\";\")+1)";
Debug.ShouldStop(1);
_transacao = _linha1.substring((int) (_linha1.indexOf(";")+1));Debug.locals.put("transacao", _transacao);
 };
 BA.debugLineNum = 195;BA.debugLine="linha1 = TextReader1.ReadLine";
Debug.ShouldStop(4);
_linha1 = _textreader1.ReadLine();Debug.locals.put("linha1", _linha1);
 BA.debugLineNum = 196;BA.debugLine="transacoes.Add(transacao)";
Debug.ShouldStop(8);
_transacoes.Add((Object)(_transacao));
 }
;
 BA.debugLineNum = 198;BA.debugLine="Return transacoes";
Debug.ShouldStop(32);
if (true) return _transacoes;
 BA.debugLineNum = 199;BA.debugLine="End Sub";
Debug.ShouldStop(64);
return null;
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public Object  _getusuario(String _username) throws Exception{
		Debug.PushSubsStack("GetUsuario (persistencia) ","persistencia",9,ba,this);
try {
anywheresoftware.b4a.objects.streams.File.TextReaderWrapper _textreader1 = null;
String _linha1 = "";
String _linha2 = "";
String _linha3 = "";
String _linha4 = "";
Debug.locals.put("Username", _username);
 BA.debugLineNum = 242;BA.debugLine="Public Sub GetUsuario(Username As String) As Object";
Debug.ShouldStop(131072);
 BA.debugLineNum = 243;BA.debugLine="Dim TextReader1 As TextReader";
Debug.ShouldStop(262144);
_textreader1 = new anywheresoftware.b4a.objects.streams.File.TextReaderWrapper();Debug.locals.put("TextReader1", _textreader1);
 BA.debugLineNum = 244;BA.debugLine="TextReader1.Initialize(File.OpenInput(File.DirRootExternal, \"Logins.txt\"))";
Debug.ShouldStop(524288);
_textreader1.Initialize((java.io.InputStream)(__c.File.OpenInput(__c.File.getDirRootExternal(),"Logins.txt").getObject()));
 BA.debugLineNum = 245;BA.debugLine="Dim linha1,linha2,linha3,linha4 As String";
Debug.ShouldStop(1048576);
_linha1 = "";Debug.locals.put("linha1", _linha1);
_linha2 = "";Debug.locals.put("linha2", _linha2);
_linha3 = "";Debug.locals.put("linha3", _linha3);
_linha4 = "";Debug.locals.put("linha4", _linha4);
 BA.debugLineNum = 246;BA.debugLine="linha1 = TextReader1.ReadLine";
Debug.ShouldStop(2097152);
_linha1 = _textreader1.ReadLine();Debug.locals.put("linha1", _linha1);
 BA.debugLineNum = 247;BA.debugLine="If linha1 = Null Then";
Debug.ShouldStop(4194304);
if (_linha1== null) { 
 BA.debugLineNum = 248;BA.debugLine="Return False";
Debug.ShouldStop(8388608);
if (true) return (Object)(__c.False);
 };
 BA.debugLineNum = 250;BA.debugLine="linha2 = linha1.SubString2(0,linha1.IndexOf(\";\"))";
Debug.ShouldStop(33554432);
_linha2 = _linha1.substring((int) (0),_linha1.indexOf(";"));Debug.locals.put("linha2", _linha2);
 BA.debugLineNum = 251;BA.debugLine="linha3 = linha1.SubString2(linha1.IndexOf(\";\")+1,linha1.LastIndexOf(\";\"))";
Debug.ShouldStop(67108864);
_linha3 = _linha1.substring((int) (_linha1.indexOf(";")+1),_linha1.lastIndexOf(";"));Debug.locals.put("linha3", _linha3);
 BA.debugLineNum = 252;BA.debugLine="linha4 = linha1.SubString(linha1.LastIndexOf(\";\")+1)";
Debug.ShouldStop(134217728);
_linha4 = _linha1.substring((int) (_linha1.lastIndexOf(";")+1));Debug.locals.put("linha4", _linha4);
 BA.debugLineNum = 254;BA.debugLine="Do While linha1 <> Null";
Debug.ShouldStop(536870912);
while (_linha1!= null) {
 BA.debugLineNum = 255;BA.debugLine="If linha2 = Username Then";
Debug.ShouldStop(1073741824);
if ((_linha2).equals(_username)) { 
 BA.debugLineNum = 256;BA.debugLine="Return linha1";
Debug.ShouldStop(-2147483648);
if (true) return (Object)(_linha1);
 };
 BA.debugLineNum = 258;BA.debugLine="linha1 = TextReader1.ReadLine";
Debug.ShouldStop(2);
_linha1 = _textreader1.ReadLine();Debug.locals.put("linha1", _linha1);
 }
;
 BA.debugLineNum = 260;BA.debugLine="TextReader1.Close";
Debug.ShouldStop(8);
_textreader1.Close();
 BA.debugLineNum = 261;BA.debugLine="Return Null";
Debug.ShouldStop(16);
if (true) return __c.Null;
 BA.debugLineNum = 262;BA.debugLine="End Sub";
Debug.ShouldStop(32);
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
		Debug.PushSubsStack("Initialize (persistencia) ","persistencia",9,ba,this);
try {
Debug.locals.put("ba", _ba);
 BA.debugLineNum = 6;BA.debugLine="Public Sub Initialize";
Debug.ShouldStop(32);
 BA.debugLineNum = 8;BA.debugLine="End Sub";
Debug.ShouldStop(128);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public String  _logado() throws Exception{
		Debug.PushSubsStack("Logado (persistencia) ","persistencia",9,ba,this);
try {
anywheresoftware.b4a.objects.streams.File.TextReaderWrapper _textreader1 = null;
String _usuario = "";
 BA.debugLineNum = 170;BA.debugLine="Public Sub Logado As String";
Debug.ShouldStop(512);
 BA.debugLineNum = 171;BA.debugLine="Dim TextReader1 As TextReader";
Debug.ShouldStop(1024);
_textreader1 = new anywheresoftware.b4a.objects.streams.File.TextReaderWrapper();Debug.locals.put("TextReader1", _textreader1);
 BA.debugLineNum = 172;BA.debugLine="TextReader1.Initialize(File.OpenInput(File.DirRootExternal,\"logado.txt\"))";
Debug.ShouldStop(2048);
_textreader1.Initialize((java.io.InputStream)(__c.File.OpenInput(__c.File.getDirRootExternal(),"logado.txt").getObject()));
 BA.debugLineNum = 173;BA.debugLine="Dim usuario As String";
Debug.ShouldStop(4096);
_usuario = "";Debug.locals.put("usuario", _usuario);
 BA.debugLineNum = 174;BA.debugLine="usuario = TextReader1.ReadLine";
Debug.ShouldStop(8192);
_usuario = _textreader1.ReadLine();Debug.locals.put("usuario", _usuario);
 BA.debugLineNum = 175;BA.debugLine="Return usuario";
Debug.ShouldStop(16384);
if (true) return _usuario;
 BA.debugLineNum = 176;BA.debugLine="End Sub";
Debug.ShouldStop(32768);
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
		Debug.PushSubsStack("Remover_Transacao (persistencia) ","persistencia",9,ba,this);
try {
String _linha1 = "";
String _linha2 = "";
anywheresoftware.b4a.objects.collections.List _lista = null;
anywheresoftware.b4a.objects.streams.File.TextReaderWrapper _textreader1 = null;
anywheresoftware.b4a.objects.streams.File.TextWriterWrapper _textwriter1 = null;
float _valor = 0f;
Debug.locals.put("Pos", _pos);
 BA.debugLineNum = 217;BA.debugLine="Public Sub Remover_Transacao (Pos As Int)";
Debug.ShouldStop(16777216);
 BA.debugLineNum = 218;BA.debugLine="Dim linha1,linha2 As String";
Debug.ShouldStop(33554432);
_linha1 = "";Debug.locals.put("linha1", _linha1);
_linha2 = "";Debug.locals.put("linha2", _linha2);
 BA.debugLineNum = 219;BA.debugLine="Dim lista As List";
Debug.ShouldStop(67108864);
_lista = new anywheresoftware.b4a.objects.collections.List();Debug.locals.put("lista", _lista);
 BA.debugLineNum = 220;BA.debugLine="Dim TextReader1 As TextReader";
Debug.ShouldStop(134217728);
_textreader1 = new anywheresoftware.b4a.objects.streams.File.TextReaderWrapper();Debug.locals.put("TextReader1", _textreader1);
 BA.debugLineNum = 221;BA.debugLine="Dim TextWriter1 As TextWriter";
Debug.ShouldStop(268435456);
_textwriter1 = new anywheresoftware.b4a.objects.streams.File.TextWriterWrapper();Debug.locals.put("TextWriter1", _textwriter1);
 BA.debugLineNum = 223;BA.debugLine="linha1 = GetTransacoes(Logado).Get(Pos)";
Debug.ShouldStop(1073741824);
_linha1 = BA.ObjectToString(_gettransacoes(_logado()).Get(_pos));Debug.locals.put("linha1", _linha1);
 BA.debugLineNum = 224;BA.debugLine="linha2 = linha1.SubString2(0,linha1.IndexOf(\";\"))";
Debug.ShouldStop(-2147483648);
_linha2 = _linha1.substring((int) (0),_linha1.indexOf(";"));Debug.locals.put("linha2", _linha2);
 BA.debugLineNum = 226;BA.debugLine="TextReader1.Initialize(File.OpenInput(File.DirRootExternal,Logado&\"transacoes.txt\"))";
Debug.ShouldStop(2);
_textreader1.Initialize((java.io.InputStream)(__c.File.OpenInput(__c.File.getDirRootExternal(),_logado()+"transacoes.txt").getObject()));
 BA.debugLineNum = 227;BA.debugLine="lista = TextReader1.ReadList";
Debug.ShouldStop(4);
_lista = _textreader1.ReadList();Debug.locals.put("lista", _lista);
 BA.debugLineNum = 228;BA.debugLine="TextReader1.Close";
Debug.ShouldStop(8);
_textreader1.Close();
 BA.debugLineNum = 230;BA.debugLine="File.Delete(File.DirRootExternal,Logado&\"transacoes.txt\")";
Debug.ShouldStop(32);
__c.File.Delete(__c.File.getDirRootExternal(),_logado()+"transacoes.txt");
 BA.debugLineNum = 232;BA.debugLine="lista.RemoveAt(Pos)";
Debug.ShouldStop(128);
_lista.RemoveAt(_pos);
 BA.debugLineNum = 234;BA.debugLine="TextWriter1.Initialize(File.OpenOutput(File.DirRootExternal,Logado&\"transacoes.txt\",True))";
Debug.ShouldStop(512);
_textwriter1.Initialize((java.io.OutputStream)(__c.File.OpenOutput(__c.File.getDirRootExternal(),_logado()+"transacoes.txt",__c.True).getObject()));
 BA.debugLineNum = 235;BA.debugLine="TextWriter1.WriteList(lista)";
Debug.ShouldStop(1024);
_textwriter1.WriteList(_lista);
 BA.debugLineNum = 236;BA.debugLine="Dim Valor As Float = linha2";
Debug.ShouldStop(2048);
_valor = (float)(Double.parseDouble(_linha2));Debug.locals.put("Valor", _valor);Debug.locals.put("Valor", _valor);
 BA.debugLineNum = 237;BA.debugLine="TextWriter1.Close";
Debug.ShouldStop(4096);
_textwriter1.Close();
 BA.debugLineNum = 238;BA.debugLine="Atualizar_Saldo(-Valor)";
Debug.ShouldStop(8192);
_atualizar_saldo((float) (-_valor));
 BA.debugLineNum = 240;BA.debugLine="End Sub";
Debug.ShouldStop(32768);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public boolean  _salvar_categoria(String _categoria) throws Exception{
		Debug.PushSubsStack("Salvar_Categoria (persistencia) ","persistencia",9,ba,this);
try {
anywheresoftware.b4a.objects.streams.File.TextWriterWrapper _textwriter1 = null;
Debug.locals.put("Categoria", _categoria);
 BA.debugLineNum = 264;BA.debugLine="Public Sub Salvar_Categoria(Categoria As String) As Boolean";
Debug.ShouldStop(128);
 BA.debugLineNum = 265;BA.debugLine="If Categoria = \"\" Then";
Debug.ShouldStop(256);
if ((_categoria).equals("")) { 
 BA.debugLineNum = 266;BA.debugLine="Msgbox(\"Categoria está em branco!\", \"Atenção!\")";
Debug.ShouldStop(512);
__c.Msgbox("Categoria está em branco!","Atenção!",getActivityBA());
 }else {
 BA.debugLineNum = 268;BA.debugLine="Dim TextWriter1 As TextWriter";
Debug.ShouldStop(2048);
_textwriter1 = new anywheresoftware.b4a.objects.streams.File.TextWriterWrapper();Debug.locals.put("TextWriter1", _textwriter1);
 BA.debugLineNum = 269;BA.debugLine="TextWriter1.Initialize(File.OpenOutput(File.DirRootExternal, \"categ.txt\", True))";
Debug.ShouldStop(4096);
_textwriter1.Initialize((java.io.OutputStream)(__c.File.OpenOutput(__c.File.getDirRootExternal(),"categ.txt",__c.True).getObject()));
 BA.debugLineNum = 270;BA.debugLine="TextWriter1.WriteLine(Categoria)";
Debug.ShouldStop(8192);
_textwriter1.WriteLine(_categoria);
 BA.debugLineNum = 271;BA.debugLine="TextWriter1.Close";
Debug.ShouldStop(16384);
_textwriter1.Close();
 BA.debugLineNum = 272;BA.debugLine="Return True";
Debug.ShouldStop(32768);
if (true) return __c.True;
 };
 BA.debugLineNum = 274;BA.debugLine="Return False";
Debug.ShouldStop(131072);
if (true) return __c.False;
 BA.debugLineNum = 275;BA.debugLine="End Sub";
Debug.ShouldStop(262144);
return false;
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public boolean  _salvar_transacao(String _usuario,Object _valor,String _data,String _categoria,String _tipo) throws Exception{
		Debug.PushSubsStack("Salvar_Transacao (persistencia) ","persistencia",9,ba,this);
try {
float _valor1 = 0f;
anywheresoftware.b4a.objects.streams.File.TextWriterWrapper _textwriter1 = null;
Debug.locals.put("usuario", _usuario);
Debug.locals.put("valor", _valor);
Debug.locals.put("Data", _data);
Debug.locals.put("Categoria", _categoria);
Debug.locals.put("Tipo", _tipo);
 BA.debugLineNum = 201;BA.debugLine="Public Sub Salvar_Transacao(usuario As String, valor As Object, Data As String, Categoria As String, Tipo As String) As Boolean";
Debug.ShouldStop(256);
 BA.debugLineNum = 202;BA.debugLine="Dim valor1 As Float";
Debug.ShouldStop(512);
_valor1 = 0f;Debug.locals.put("valor1", _valor1);
 BA.debugLineNum = 203;BA.debugLine="valor1 = valor";
Debug.ShouldStop(1024);
_valor1 = (float)(BA.ObjectToNumber(_valor));Debug.locals.put("valor1", _valor1);
 BA.debugLineNum = 204;BA.debugLine="If Tipo = \"Crédito\" Then";
Debug.ShouldStop(2048);
if ((_tipo).equals("Crédito")) { 
 BA.debugLineNum = 205;BA.debugLine="Atualizar_Saldo(valor1)";
Debug.ShouldStop(4096);
_atualizar_saldo(_valor1);
 }else {
 BA.debugLineNum = 207;BA.debugLine="valor1 = valor1 * (-1)";
Debug.ShouldStop(16384);
_valor1 = (float) (_valor1*(-1));Debug.locals.put("valor1", _valor1);
 BA.debugLineNum = 208;BA.debugLine="Atualizar_Saldo(valor1)";
Debug.ShouldStop(32768);
_atualizar_saldo(_valor1);
 };
 BA.debugLineNum = 210;BA.debugLine="Dim TextWriter1 As TextWriter";
Debug.ShouldStop(131072);
_textwriter1 = new anywheresoftware.b4a.objects.streams.File.TextWriterWrapper();Debug.locals.put("TextWriter1", _textwriter1);
 BA.debugLineNum = 211;BA.debugLine="TextWriter1.Initialize(File.OpenOutput(File.DirRootExternal,Logado&\"transacoes.txt\",True))";
Debug.ShouldStop(262144);
_textwriter1.Initialize((java.io.OutputStream)(__c.File.OpenOutput(__c.File.getDirRootExternal(),_logado()+"transacoes.txt",__c.True).getObject()));
 BA.debugLineNum = 212;BA.debugLine="TextWriter1.WriteLine(usuario &\";\"& valor1 &\";\"& Data &\";\"& Categoria)";
Debug.ShouldStop(524288);
_textwriter1.WriteLine(_usuario+";"+BA.NumberToString(_valor1)+";"+_data+";"+_categoria);
 BA.debugLineNum = 213;BA.debugLine="TextWriter1.Close";
Debug.ShouldStop(1048576);
_textwriter1.Close();
 BA.debugLineNum = 214;BA.debugLine="Return True";
Debug.ShouldStop(2097152);
if (true) return __c.True;
 BA.debugLineNum = 215;BA.debugLine="End Sub";
Debug.ShouldStop(4194304);
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
