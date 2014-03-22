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
  public Object[] GetGlobals() {
		return new Object[] {"AddCategoria",Debug.moduleToString(Financas.Pessoais.addcategoria.class),"Cadastro",Debug.moduleToString(Financas.Pessoais.cadastro.class),"Calculadora",Debug.moduleToString(Financas.Pessoais.calculadora.class),"Creditos",Debug.moduleToString(Financas.Pessoais.creditos.class),"Debitos",Debug.moduleToString(Financas.Pessoais.debitos.class),"Excluir",Debug.moduleToString(Financas.Pessoais.excluir.class),"Extrato",Debug.moduleToString(Financas.Pessoais.extrato.class),"Financeiro",Debug.moduleToString(Financas.Pessoais.financeiro.class),"Lista",Debug.moduleToString(Financas.Pessoais.lista.class),"Lista_Extrato",_lista_extrato,"Main",Debug.moduleToString(Financas.Pessoais.main.class),"Menu",Debug.moduleToString(Financas.Pessoais.menu.class),"Remover_Categoria",Debug.moduleToString(Financas.Pessoais.remover_categoria.class),"Saldo",_saldo,"Total",Debug.moduleToString(Financas.Pessoais.total.class),"Utilitarios",Debug.moduleToString(Financas.Pessoais.utilitarios.class)};
}
public String  _atualizar_saldo(float _valor) throws Exception{
		Debug.PushSubsStack("Atualizar_Saldo (persistencia) ","persistencia",3,ba,this);
try {
Debug.locals.put("Valor", _valor);
 BA.debugLineNum = 84;BA.debugLine="Private Sub Atualizar_Saldo(Valor As Float)";
Debug.ShouldStop(524288);
 BA.debugLineNum = 85;BA.debugLine="Saldo = Saldo + Valor";
Debug.ShouldStop(1048576);
_saldo = (float) (_saldo+_valor);
 BA.debugLineNum = 86;BA.debugLine="End Sub";
Debug.ShouldStop(2097152);
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
Debug.locals.put("Nome", _nome);
Debug.locals.put("Username", _username);
Debug.locals.put("Senha", _senha);
Debug.locals.put("Senha_Repetida", _senha_repetida);
 BA.debugLineNum = 39;BA.debugLine="Public Sub Criar_Login(Nome As String, Username As String, Senha As String, Senha_Repetida As String) As Boolean";
Debug.ShouldStop(64);
 BA.debugLineNum = 40;BA.debugLine="If Nome = \"\" OR Username = \"\" OR Senha = \"\" OR Senha_Repetida = \"\" Then";
Debug.ShouldStop(128);
if ((_nome).equals("") || (_username).equals("") || (_senha).equals("") || (_senha_repetida).equals("")) { 
 BA.debugLineNum = 41;BA.debugLine="Msgbox(\"Campos Obrigatórios não Preenchidos\", \"Atenção!\")";
Debug.ShouldStop(256);
__c.Msgbox("Campos Obrigatórios não Preenchidos","Atenção!",getActivityBA());
 }else 
{ BA.debugLineNum = 42;BA.debugLine="Else If Not(Senha = Senha_Repetida) Then";
Debug.ShouldStop(512);
if (__c.Not((_senha).equals(_senha_repetida))) { 
 BA.debugLineNum = 43;BA.debugLine="Msgbox(\"Senhas não conferem!\", \"Atenção!\")";
Debug.ShouldStop(1024);
__c.Msgbox("Senhas não conferem!","Atenção!",getActivityBA());
 }else {
 BA.debugLineNum = 45;BA.debugLine="Dim TextWriter1 As TextWriter";
Debug.ShouldStop(4096);
_textwriter1 = new anywheresoftware.b4a.objects.streams.File.TextWriterWrapper();Debug.locals.put("TextWriter1", _textwriter1);
 BA.debugLineNum = 46;BA.debugLine="TextWriter1.Initialize(File.OpenOutput(File.DirRootExternal, \"Logins.txt\", True))";
Debug.ShouldStop(8192);
_textwriter1.Initialize((java.io.OutputStream)(__c.File.OpenOutput(__c.File.getDirRootExternal(),"Logins.txt",__c.True).getObject()));
 BA.debugLineNum = 47;BA.debugLine="TextWriter1.WriteLine(Username & Senha)";
Debug.ShouldStop(16384);
_textwriter1.WriteLine(_username+_senha);
 BA.debugLineNum = 48;BA.debugLine="TextWriter1.Close";
Debug.ShouldStop(32768);
_textwriter1.Close();
 BA.debugLineNum = 49;BA.debugLine="Return True";
Debug.ShouldStop(65536);
if (true) return __c.True;
 }};
 BA.debugLineNum = 51;BA.debugLine="Return False";
Debug.ShouldStop(262144);
if (true) return __c.False;
 BA.debugLineNum = 52;BA.debugLine="End Sub";
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
public boolean  _excluir_login(String _username,String _senha) throws Exception{
		Debug.PushSubsStack("Excluir_Login (persistencia) ","persistencia",3,ba,this);
try {
anywheresoftware.b4a.objects.streams.File.TextReaderWrapper _textreader1 = null;
String _line = "";
anywheresoftware.b4a.objects.collections.List _texto = null;
anywheresoftware.b4a.objects.streams.File.TextWriterWrapper _textwriter1 = null;
Debug.locals.put("Username", _username);
Debug.locals.put("Senha", _senha);
 BA.debugLineNum = 54;BA.debugLine="Public Sub Excluir_Login(Username As String, Senha As String) As Boolean";
Debug.ShouldStop(2097152);
 BA.debugLineNum = 55;BA.debugLine="If Username = \"\" OR Senha = \"\" Then";
Debug.ShouldStop(4194304);
if ((_username).equals("") || (_senha).equals("")) { 
 BA.debugLineNum = 56;BA.debugLine="Msgbox(\"Campos Obrigatórios não Preenchidos\", \"Atenção!\")";
Debug.ShouldStop(8388608);
__c.Msgbox("Campos Obrigatórios não Preenchidos","Atenção!",getActivityBA());
 }else {
 BA.debugLineNum = 58;BA.debugLine="Dim TextReader1 As TextReader";
Debug.ShouldStop(33554432);
_textreader1 = new anywheresoftware.b4a.objects.streams.File.TextReaderWrapper();Debug.locals.put("TextReader1", _textreader1);
 BA.debugLineNum = 59;BA.debugLine="TextReader1.Initialize(File.OpenInput(File.DirRootExternal, \"Logins.txt\"))";
Debug.ShouldStop(67108864);
_textreader1.Initialize((java.io.InputStream)(__c.File.OpenInput(__c.File.getDirRootExternal(),"Logins.txt").getObject()));
 BA.debugLineNum = 60;BA.debugLine="Dim line As String";
Debug.ShouldStop(134217728);
_line = "";Debug.locals.put("line", _line);
 BA.debugLineNum = 61;BA.debugLine="Dim texto As List";
Debug.ShouldStop(268435456);
_texto = new anywheresoftware.b4a.objects.collections.List();Debug.locals.put("texto", _texto);
 BA.debugLineNum = 62;BA.debugLine="texto.Initialize";
Debug.ShouldStop(536870912);
_texto.Initialize();
 BA.debugLineNum = 63;BA.debugLine="line = TextReader1.ReadLine";
Debug.ShouldStop(1073741824);
_line = _textreader1.ReadLine();Debug.locals.put("line", _line);
 BA.debugLineNum = 64;BA.debugLine="Do While line <> Null";
Debug.ShouldStop(-2147483648);
while (_line!= null) {
 BA.debugLineNum = 65;BA.debugLine="If line = Username & Senha Then";
Debug.ShouldStop(1);
if ((_line).equals(_username+_senha)) { 
 BA.debugLineNum = 66;BA.debugLine="If File.Delete(File.DirRootExternal, \"Logins.txt\") Then";
Debug.ShouldStop(2);
if (__c.File.Delete(__c.File.getDirRootExternal(),"Logins.txt")) { 
 BA.debugLineNum = 67;BA.debugLine="Dim TextWriter1 As TextWriter";
Debug.ShouldStop(4);
_textwriter1 = new anywheresoftware.b4a.objects.streams.File.TextWriterWrapper();Debug.locals.put("TextWriter1", _textwriter1);
 BA.debugLineNum = 68;BA.debugLine="TextWriter1.Initialize(File.OpenOutput(File.DirRootExternal, \"Logins.txt\", True))";
Debug.ShouldStop(8);
_textwriter1.Initialize((java.io.OutputStream)(__c.File.OpenOutput(__c.File.getDirRootExternal(),"Logins.txt",__c.True).getObject()));
 BA.debugLineNum = 69;BA.debugLine="TextWriter1.WriteList(texto)";
Debug.ShouldStop(16);
_textwriter1.WriteList(_texto);
 BA.debugLineNum = 70;BA.debugLine="TextWriter1.Close";
Debug.ShouldStop(32);
_textwriter1.Close();
 BA.debugLineNum = 71;BA.debugLine="TextReader1.Close";
Debug.ShouldStop(64);
_textreader1.Close();
 BA.debugLineNum = 72;BA.debugLine="Return True";
Debug.ShouldStop(128);
if (true) return __c.True;
 };
 }else {
 BA.debugLineNum = 75;BA.debugLine="texto.Add(line)";
Debug.ShouldStop(1024);
_texto.Add((Object)(_line));
 BA.debugLineNum = 76;BA.debugLine="line = TextReader1.ReadLine";
Debug.ShouldStop(2048);
_line = _textreader1.ReadLine();Debug.locals.put("line", _line);
 };
 }
;
 };
 BA.debugLineNum = 81;BA.debugLine="Return False";
Debug.ShouldStop(65536);
if (true) return __c.False;
 BA.debugLineNum = 82;BA.debugLine="End Sub";
Debug.ShouldStop(131072);
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
String _line = "";
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
 BA.debugLineNum = 23;BA.debugLine="Dim line As String";
Debug.ShouldStop(4194304);
_line = "";Debug.locals.put("line", _line);
 BA.debugLineNum = 24;BA.debugLine="line = TextReader1.ReadLine";
Debug.ShouldStop(8388608);
_line = _textreader1.ReadLine();Debug.locals.put("line", _line);
 BA.debugLineNum = 25;BA.debugLine="Do While line <> Null";
Debug.ShouldStop(16777216);
while (_line!= null) {
 BA.debugLineNum = 26;BA.debugLine="If line = Username & Senha Then";
Debug.ShouldStop(33554432);
if ((_line).equals(_username+_senha)) { 
 BA.debugLineNum = 27;BA.debugLine="Return True";
Debug.ShouldStop(67108864);
if (true) return __c.True;
 };
 BA.debugLineNum = 29;BA.debugLine="line = TextReader1.ReadLine";
Debug.ShouldStop(268435456);
_line = _textreader1.ReadLine();Debug.locals.put("line", _line);
 }
;
 BA.debugLineNum = 31;BA.debugLine="TextReader1.Close";
Debug.ShouldStop(1073741824);
_textreader1.Close();
 };
 };
 BA.debugLineNum = 35;BA.debugLine="Return False";
Debug.ShouldStop(4);
if (true) return __c.False;
 BA.debugLineNum = 37;BA.debugLine="End Sub";
Debug.ShouldStop(16);
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
public String  _remover_categoria(int _pos) throws Exception{
		Debug.PushSubsStack("Remover_Categoria (persistencia) ","persistencia",3,ba,this);
try {
Debug.locals.put("Pos", _pos);
 BA.debugLineNum = 122;BA.debugLine="Public Sub Remover_Categoria(Pos As Int)";
Debug.ShouldStop(33554432);
 BA.debugLineNum = 124;BA.debugLine="End Sub";
Debug.ShouldStop(134217728);
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
 BA.debugLineNum = 101;BA.debugLine="Public Sub Remover_Transacao (Pos As Int)";
Debug.ShouldStop(16);
 BA.debugLineNum = 102;BA.debugLine="Dim Linha As String = Lista_Extrato.Get(Pos)";
Debug.ShouldStop(32);
_linha = BA.ObjectToString(_lista_extrato.Get(_pos));Debug.locals.put("Linha", _linha);Debug.locals.put("Linha", _linha);
 BA.debugLineNum = 103;BA.debugLine="Dim i As Int = Linha.IndexOf(\"|\")";
Debug.ShouldStop(64);
_i = _linha.indexOf("|");Debug.locals.put("i", _i);Debug.locals.put("i", _i);
 BA.debugLineNum = 104;BA.debugLine="Dim Valor As Float = Linha.SubString2(0,i)";
Debug.ShouldStop(128);
_valor = (float)(Double.parseDouble(_linha.substring((int) (0),_i)));Debug.locals.put("Valor", _valor);Debug.locals.put("Valor", _valor);
 BA.debugLineNum = 106;BA.debugLine="Saldo = Saldo - Valor";
Debug.ShouldStop(512);
_saldo = (float) (_saldo-_valor);
 BA.debugLineNum = 108;BA.debugLine="Lista_Extrato.RemoveAt(Pos)";
Debug.ShouldStop(2048);
_lista_extrato.RemoveAt(_pos);
 BA.debugLineNum = 109;BA.debugLine="End Sub";
Debug.ShouldStop(4096);
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
 BA.debugLineNum = 111;BA.debugLine="Public Sub Remover_Transacao2 (Obj As String)";
Debug.ShouldStop(16384);
 BA.debugLineNum = 112;BA.debugLine="Dim Pos As Int = Lista_Extrato.IndexOf(Obj)";
Debug.ShouldStop(32768);
_pos = _lista_extrato.IndexOf((Object)(_obj));Debug.locals.put("Pos", _pos);Debug.locals.put("Pos", _pos);
 BA.debugLineNum = 113;BA.debugLine="Dim Linha As String = Lista_Extrato.Get(Pos)";
Debug.ShouldStop(65536);
_linha = BA.ObjectToString(_lista_extrato.Get(_pos));Debug.locals.put("Linha", _linha);Debug.locals.put("Linha", _linha);
 BA.debugLineNum = 114;BA.debugLine="Dim i As Int = Linha.IndexOf(\"|\")";
Debug.ShouldStop(131072);
_i = _linha.indexOf("|");Debug.locals.put("i", _i);Debug.locals.put("i", _i);
 BA.debugLineNum = 115;BA.debugLine="Dim Valor As Float = Linha.SubString2(0,i)";
Debug.ShouldStop(262144);
_valor = (float)(Double.parseDouble(_linha.substring((int) (0),_i)));Debug.locals.put("Valor", _valor);Debug.locals.put("Valor", _valor);
 BA.debugLineNum = 117;BA.debugLine="Saldo = Saldo - Valor";
Debug.ShouldStop(1048576);
_saldo = (float) (_saldo-_valor);
 BA.debugLineNum = 119;BA.debugLine="Lista_Extrato.RemoveAt(Pos)";
Debug.ShouldStop(4194304);
_lista_extrato.RemoveAt(_pos);
 BA.debugLineNum = 120;BA.debugLine="End Sub";
Debug.ShouldStop(8388608);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public boolean  _salvar_transacao(double _valor,String _data,String _categoria,String _tipo) throws Exception{
		Debug.PushSubsStack("Salvar_Transacao (persistencia) ","persistencia",3,ba,this);
try {
Debug.locals.put("Valor", _valor);
Debug.locals.put("Data", _data);
Debug.locals.put("Categoria", _categoria);
Debug.locals.put("Tipo", _tipo);
 BA.debugLineNum = 88;BA.debugLine="Public Sub Salvar_Transacao (Valor As Double, Data As String, Categoria As String, Tipo As String) As Boolean";
Debug.ShouldStop(8388608);
 BA.debugLineNum = 90;BA.debugLine="If Tipo = \"Crédito\" Then";
Debug.ShouldStop(33554432);
if ((_tipo).equals("Crédito")) { 
 BA.debugLineNum = 91;BA.debugLine="Atualizar_Saldo(Valor)";
Debug.ShouldStop(67108864);
_atualizar_saldo((float) (_valor));
 }else {
 BA.debugLineNum = 93;BA.debugLine="Valor = Valor * (-1)";
Debug.ShouldStop(268435456);
_valor = _valor*(-1);Debug.locals.put("Valor", _valor);
 BA.debugLineNum = 94;BA.debugLine="Atualizar_Saldo(Valor)";
Debug.ShouldStop(536870912);
_atualizar_saldo((float) (_valor));
 };
 BA.debugLineNum = 96;BA.debugLine="Lista_Extrato.Add(NumberFormat(Valor,1,2) & \"|\" & Data & \"|\" & Categoria)";
Debug.ShouldStop(-2147483648);
_lista_extrato.Add((Object)(__c.NumberFormat(_valor,(int) (1),(int) (2))+"|"+_data+"|"+_categoria));
 BA.debugLineNum = 98;BA.debugLine="Return True";
Debug.ShouldStop(2);
if (true) return __c.True;
 BA.debugLineNum = 99;BA.debugLine="End Sub";
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
public Object callSub(String sub, Object sender, Object[] args) throws Exception {
ba.sharedProcessBA.sender = sender;
return BA.SubDelegator.SubNotFound;
}
}
