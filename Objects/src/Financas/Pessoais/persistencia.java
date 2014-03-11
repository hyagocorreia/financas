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
public Financas.Pessoais.debito _debito = null;
public Financas.Pessoais.total _total = null;
public Financas.Pessoais.utilitários _utilitários = null;
public Financas.Pessoais.excluir _excluir = null;
public Financas.Pessoais.menu _menu = null;
public Financas.Pessoais.calculadora _calculadora = null;
public Financas.Pessoais.extrato _extrato = null;
public Financas.Pessoais.debitos _debitos = null;
  public Object[] GetGlobals() {
		return new Object[] {"Cadastro",Debug.moduleToString(Financas.Pessoais.cadastro.class),"Calculadora",Debug.moduleToString(Financas.Pessoais.calculadora.class),"Creditos",Debug.moduleToString(Financas.Pessoais.creditos.class),"Debito",Debug.moduleToString(Financas.Pessoais.debito.class),"Debitos",Debug.moduleToString(Financas.Pessoais.debitos.class),"Excluir",Debug.moduleToString(Financas.Pessoais.excluir.class),"Extrato",Debug.moduleToString(Financas.Pessoais.extrato.class),"Financeiro",Debug.moduleToString(Financas.Pessoais.financeiro.class),"Main",Debug.moduleToString(Financas.Pessoais.main.class),"Menu",Debug.moduleToString(Financas.Pessoais.menu.class),"Total",Debug.moduleToString(Financas.Pessoais.total.class),"Utilitários",Debug.moduleToString(Financas.Pessoais.utilitários.class)};
}
public String  _class_globals() throws Exception{
 //BA.debugLineNum = 2;BA.debugLine="Sub Class_Globals";
 //BA.debugLineNum = 4;BA.debugLine="End Sub";
return "";
}
public boolean  _criar_login(String _nome,String _username,String _senha,String _senha_repetida) throws Exception{
		Debug.PushSubsStack("Criar_Login (persistencia) ","persistencia",12,ba,this);
try {
anywheresoftware.b4a.objects.streams.File.TextWriterWrapper _textwriter1 = null;
Debug.locals.put("Nome", _nome);
Debug.locals.put("Username", _username);
Debug.locals.put("Senha", _senha);
Debug.locals.put("Senha_Repetida", _senha_repetida);
 BA.debugLineNum = 38;BA.debugLine="Public Sub Criar_Login(Nome As String, Username As String, Senha As String, Senha_Repetida As String) As Boolean";
Debug.ShouldStop(32);
 BA.debugLineNum = 39;BA.debugLine="If Nome = \"\" OR Username = \"\" OR Senha = \"\" OR Senha_Repetida = \"\" Then";
Debug.ShouldStop(64);
if ((_nome).equals("") || (_username).equals("") || (_senha).equals("") || (_senha_repetida).equals("")) { 
 BA.debugLineNum = 40;BA.debugLine="Msgbox(\"Campos Obrigatórios não Preenchidos\", \"Atenção!\")";
Debug.ShouldStop(128);
__c.Msgbox("Campos Obrigatórios não Preenchidos","Atenção!",getActivityBA());
 }else 
{ BA.debugLineNum = 41;BA.debugLine="Else If Not(Senha = Senha_Repetida) Then";
Debug.ShouldStop(256);
if (__c.Not((_senha).equals(_senha_repetida))) { 
 BA.debugLineNum = 42;BA.debugLine="Msgbox(\"Senhas não conferem!\", \"Atenção!\")";
Debug.ShouldStop(512);
__c.Msgbox("Senhas não conferem!","Atenção!",getActivityBA());
 }else {
 BA.debugLineNum = 44;BA.debugLine="Dim TextWriter1 As TextWriter";
Debug.ShouldStop(2048);
_textwriter1 = new anywheresoftware.b4a.objects.streams.File.TextWriterWrapper();Debug.locals.put("TextWriter1", _textwriter1);
 BA.debugLineNum = 45;BA.debugLine="TextWriter1.Initialize(File.OpenOutput(File.DirRootExternal, \"Logins.txt\", True))";
Debug.ShouldStop(4096);
_textwriter1.Initialize((java.io.OutputStream)(__c.File.OpenOutput(__c.File.getDirRootExternal(),"Logins.txt",__c.True).getObject()));
 BA.debugLineNum = 46;BA.debugLine="TextWriter1.WriteLine(Username & Senha)";
Debug.ShouldStop(8192);
_textwriter1.WriteLine(_username+_senha);
 BA.debugLineNum = 47;BA.debugLine="TextWriter1.Close";
Debug.ShouldStop(16384);
_textwriter1.Close();
 BA.debugLineNum = 48;BA.debugLine="Return True";
Debug.ShouldStop(32768);
if (true) return __c.True;
 }};
 BA.debugLineNum = 50;BA.debugLine="Return False";
Debug.ShouldStop(131072);
if (true) return __c.False;
 BA.debugLineNum = 51;BA.debugLine="End Sub";
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
public boolean  _excluir_login(String _username,String _senha) throws Exception{
		Debug.PushSubsStack("Excluir_Login (persistencia) ","persistencia",12,ba,this);
try {
anywheresoftware.b4a.objects.streams.File.TextReaderWrapper _textreader1 = null;
String _line = "";
anywheresoftware.b4a.objects.collections.List _texto = null;
anywheresoftware.b4a.objects.streams.File.TextWriterWrapper _textwriter1 = null;
Debug.locals.put("Username", _username);
Debug.locals.put("Senha", _senha);
 BA.debugLineNum = 53;BA.debugLine="Public Sub Excluir_Login(Username As String, Senha As String) As Boolean";
Debug.ShouldStop(1048576);
 BA.debugLineNum = 54;BA.debugLine="If Username = \"\" OR Senha = \"\" Then";
Debug.ShouldStop(2097152);
if ((_username).equals("") || (_senha).equals("")) { 
 BA.debugLineNum = 55;BA.debugLine="Msgbox(\"Campos Obrigatórios não Preenchidos\", \"Atenção!\")";
Debug.ShouldStop(4194304);
__c.Msgbox("Campos Obrigatórios não Preenchidos","Atenção!",getActivityBA());
 }else {
 BA.debugLineNum = 57;BA.debugLine="Dim TextReader1 As TextReader";
Debug.ShouldStop(16777216);
_textreader1 = new anywheresoftware.b4a.objects.streams.File.TextReaderWrapper();Debug.locals.put("TextReader1", _textreader1);
 BA.debugLineNum = 58;BA.debugLine="TextReader1.Initialize(File.OpenInput(File.DirRootExternal, \"Logins.txt\"))";
Debug.ShouldStop(33554432);
_textreader1.Initialize((java.io.InputStream)(__c.File.OpenInput(__c.File.getDirRootExternal(),"Logins.txt").getObject()));
 BA.debugLineNum = 59;BA.debugLine="Dim line As String";
Debug.ShouldStop(67108864);
_line = "";Debug.locals.put("line", _line);
 BA.debugLineNum = 60;BA.debugLine="Dim texto As List";
Debug.ShouldStop(134217728);
_texto = new anywheresoftware.b4a.objects.collections.List();Debug.locals.put("texto", _texto);
 BA.debugLineNum = 61;BA.debugLine="texto.Initialize";
Debug.ShouldStop(268435456);
_texto.Initialize();
 BA.debugLineNum = 62;BA.debugLine="line = TextReader1.ReadLine";
Debug.ShouldStop(536870912);
_line = _textreader1.ReadLine();Debug.locals.put("line", _line);
 BA.debugLineNum = 63;BA.debugLine="Do While line <> Null";
Debug.ShouldStop(1073741824);
while (_line!= null) {
 BA.debugLineNum = 64;BA.debugLine="If line = Username & Senha Then";
Debug.ShouldStop(-2147483648);
if ((_line).equals(_username+_senha)) { 
 BA.debugLineNum = 65;BA.debugLine="If File.Delete(File.DirRootExternal, \"Logins.txt\") Then";
Debug.ShouldStop(1);
if (__c.File.Delete(__c.File.getDirRootExternal(),"Logins.txt")) { 
 BA.debugLineNum = 66;BA.debugLine="Dim TextWriter1 As TextWriter";
Debug.ShouldStop(2);
_textwriter1 = new anywheresoftware.b4a.objects.streams.File.TextWriterWrapper();Debug.locals.put("TextWriter1", _textwriter1);
 BA.debugLineNum = 67;BA.debugLine="TextWriter1.Initialize(File.OpenOutput(File.DirRootExternal, \"Logins.txt\", True))";
Debug.ShouldStop(4);
_textwriter1.Initialize((java.io.OutputStream)(__c.File.OpenOutput(__c.File.getDirRootExternal(),"Logins.txt",__c.True).getObject()));
 BA.debugLineNum = 68;BA.debugLine="TextWriter1.WriteList(texto)";
Debug.ShouldStop(8);
_textwriter1.WriteList(_texto);
 BA.debugLineNum = 69;BA.debugLine="TextWriter1.Close";
Debug.ShouldStop(16);
_textwriter1.Close();
 BA.debugLineNum = 70;BA.debugLine="TextReader1.Close";
Debug.ShouldStop(32);
_textreader1.Close();
 BA.debugLineNum = 71;BA.debugLine="Return True";
Debug.ShouldStop(64);
if (true) return __c.True;
 };
 }else {
 BA.debugLineNum = 74;BA.debugLine="texto.Add(line)";
Debug.ShouldStop(512);
_texto.Add((Object)(_line));
 BA.debugLineNum = 75;BA.debugLine="line = TextReader1.ReadLine";
Debug.ShouldStop(1024);
_line = _textreader1.ReadLine();Debug.locals.put("line", _line);
 };
 }
;
 };
 BA.debugLineNum = 80;BA.debugLine="Return False";
Debug.ShouldStop(32768);
if (true) return __c.False;
 BA.debugLineNum = 81;BA.debugLine="End Sub";
Debug.ShouldStop(65536);
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
		Debug.PushSubsStack("Fazer_Login (persistencia) ","persistencia",12,ba,this);
try {
anywheresoftware.b4a.objects.streams.File.TextWriterWrapper _textwriter1 = null;
anywheresoftware.b4a.objects.streams.File.TextReaderWrapper _textreader1 = null;
String _line = "";
Debug.locals.put("Username", _username);
Debug.locals.put("Senha", _senha);
 BA.debugLineNum = 11;BA.debugLine="Public Sub Fazer_Login (Username As String, Senha As String) As Boolean";
Debug.ShouldStop(1024);
 BA.debugLineNum = 12;BA.debugLine="If Username = \"\" OR Senha = \"\" Then";
Debug.ShouldStop(2048);
if ((_username).equals("") || (_senha).equals("")) { 
 BA.debugLineNum = 13;BA.debugLine="Msgbox(\"Campos Obrigatorios não estão preenchidos\", \"Aviso!\" )";
Debug.ShouldStop(4096);
__c.Msgbox("Campos Obrigatorios não estão preenchidos","Aviso!",getActivityBA());
 }else {
 BA.debugLineNum = 15;BA.debugLine="If Not(File.Exists(File.DirRootExternal, \"Logins.txt\")) Then";
Debug.ShouldStop(16384);
if (__c.Not(__c.File.Exists(__c.File.getDirRootExternal(),"Logins.txt"))) { 
 BA.debugLineNum = 16;BA.debugLine="Dim TextWriter1 As TextWriter";
Debug.ShouldStop(32768);
_textwriter1 = new anywheresoftware.b4a.objects.streams.File.TextWriterWrapper();Debug.locals.put("TextWriter1", _textwriter1);
 BA.debugLineNum = 17;BA.debugLine="TextWriter1.Initialize(File.OpenOutput(File.DirRootExternal, \"Logins.txt\", False))";
Debug.ShouldStop(65536);
_textwriter1.Initialize((java.io.OutputStream)(__c.File.OpenOutput(__c.File.getDirRootExternal(),"Logins.txt",__c.False).getObject()));
 BA.debugLineNum = 18;BA.debugLine="TextWriter1.Close";
Debug.ShouldStop(131072);
_textwriter1.Close();
 }else {
 BA.debugLineNum = 20;BA.debugLine="Dim TextReader1 As TextReader";
Debug.ShouldStop(524288);
_textreader1 = new anywheresoftware.b4a.objects.streams.File.TextReaderWrapper();Debug.locals.put("TextReader1", _textreader1);
 BA.debugLineNum = 21;BA.debugLine="TextReader1.Initialize(File.OpenInput(File.DirRootExternal, \"Logins.txt\"))";
Debug.ShouldStop(1048576);
_textreader1.Initialize((java.io.InputStream)(__c.File.OpenInput(__c.File.getDirRootExternal(),"Logins.txt").getObject()));
 BA.debugLineNum = 22;BA.debugLine="Dim line As String";
Debug.ShouldStop(2097152);
_line = "";Debug.locals.put("line", _line);
 BA.debugLineNum = 23;BA.debugLine="line = TextReader1.ReadLine";
Debug.ShouldStop(4194304);
_line = _textreader1.ReadLine();Debug.locals.put("line", _line);
 BA.debugLineNum = 24;BA.debugLine="Do While line <> Null";
Debug.ShouldStop(8388608);
while (_line!= null) {
 BA.debugLineNum = 25;BA.debugLine="If line = Username & Senha Then";
Debug.ShouldStop(16777216);
if ((_line).equals(_username+_senha)) { 
 BA.debugLineNum = 26;BA.debugLine="StartActivity(\"Menu\")";
Debug.ShouldStop(33554432);
__c.StartActivity(getActivityBA(),(Object)("Menu"));
 };
 BA.debugLineNum = 28;BA.debugLine="line = TextReader1.ReadLine";
Debug.ShouldStop(134217728);
_line = _textreader1.ReadLine();Debug.locals.put("line", _line);
 }
;
 BA.debugLineNum = 30;BA.debugLine="TextReader1.Close";
Debug.ShouldStop(536870912);
_textreader1.Close();
 };
 };
 BA.debugLineNum = 34;BA.debugLine="Return False";
Debug.ShouldStop(2);
if (true) return __c.False;
 BA.debugLineNum = 36;BA.debugLine="End Sub";
Debug.ShouldStop(8);
return false;
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
		Debug.PushSubsStack("Initialize (persistencia) ","persistencia",12,ba,this);
try {
Debug.locals.put("ba", _ba);
 BA.debugLineNum = 7;BA.debugLine="Public Sub Initialize";
Debug.ShouldStop(64);
 BA.debugLineNum = 9;BA.debugLine="End Sub";
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
public Object callSub(String sub, Object sender, Object[] args) throws Exception {
ba.sharedProcessBA.sender = sender;
return BA.SubDelegator.SubNotFound;
}
}
