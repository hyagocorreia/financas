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
public String _caminho = "";
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
public Financas.Pessoais.charts _charts = null;
public Financas.Pessoais.graficos _graficos = null;
public String  _atualizar_saldo(float _valor) throws Exception{
anywheresoftware.b4a.objects.streams.File.TextReaderWrapper _tr = null;
anywheresoftware.b4a.objects.streams.File.TextWriterWrapper _tw = null;
double _saldotemp = 0;
String _abc = "";
double _saldo = 0;
 //BA.debugLineNum = 137;BA.debugLine="Private Sub Atualizar_Saldo(valor As Float)";
 //BA.debugLineNum = 138;BA.debugLine="Dim tr As TextReader";
_tr = new anywheresoftware.b4a.objects.streams.File.TextReaderWrapper();
 //BA.debugLineNum = 139;BA.debugLine="Dim tw As TextWriter";
_tw = new anywheresoftware.b4a.objects.streams.File.TextWriterWrapper();
 //BA.debugLineNum = 140;BA.debugLine="If Not(File.Exists(caminho,Logado&\"saldo.txt\")) Then";
if (__c.Not(__c.File.Exists(_caminho,_logado()+"saldo.txt"))) { 
 //BA.debugLineNum = 141;BA.debugLine="tw.Initialize(File.OpenOutput(caminho,Logado&\"saldo.txt\",True))";
_tw.Initialize((java.io.OutputStream)(__c.File.OpenOutput(_caminho,_logado()+"saldo.txt",__c.True).getObject()));
 //BA.debugLineNum = 142;BA.debugLine="tw.WriteLine(\"0\")";
_tw.WriteLine("0");
 //BA.debugLineNum = 143;BA.debugLine="tw.Close";
_tw.Close();
 };
 //BA.debugLineNum = 145;BA.debugLine="tr.Initialize(File.OpenInput(caminho,Logado&\"saldo.txt\"))";
_tr.Initialize((java.io.InputStream)(__c.File.OpenInput(_caminho,_logado()+"saldo.txt").getObject()));
 //BA.debugLineNum = 146;BA.debugLine="Dim saldoTemp As Double";
_saldotemp = 0;
 //BA.debugLineNum = 147;BA.debugLine="Dim abc As String = tr.ReadLine";
_abc = _tr.ReadLine();
 //BA.debugLineNum = 148;BA.debugLine="tr.Close";
_tr.Close();
 //BA.debugLineNum = 149;BA.debugLine="saldoTemp = abc";
_saldotemp = (double)(Double.parseDouble(_abc));
 //BA.debugLineNum = 150;BA.debugLine="File.Delete(caminho,Logado&\"saldo.txt\")";
__c.File.Delete(_caminho,_logado()+"saldo.txt");
 //BA.debugLineNum = 151;BA.debugLine="tw.Initialize(File.OpenOutput(caminho,Logado&\"saldo.txt\",True))";
_tw.Initialize((java.io.OutputStream)(__c.File.OpenOutput(_caminho,_logado()+"saldo.txt",__c.True).getObject()));
 //BA.debugLineNum = 152;BA.debugLine="Dim Saldo As Double";
_saldo = 0;
 //BA.debugLineNum = 154;BA.debugLine="Saldo = saldoTemp + valor";
_saldo = _saldotemp+_valor;
 //BA.debugLineNum = 156;BA.debugLine="tw.WriteLine(Saldo)";
_tw.WriteLine(BA.NumberToString(_saldo));
 //BA.debugLineNum = 157;BA.debugLine="tw.Close";
_tw.Close();
 //BA.debugLineNum = 158;BA.debugLine="End Sub";
return "";
}
public String  _atualizar_username(String _unovo) throws Exception{
anywheresoftware.b4a.objects.streams.File.TextWriterWrapper _textwriter1 = null;
 //BA.debugLineNum = 326;BA.debugLine="Public Sub Atualizar_Username(uNovo As String)";
 //BA.debugLineNum = 327;BA.debugLine="Dim caminho As String";
_caminho = "";
 //BA.debugLineNum = 328;BA.debugLine="caminho = caminho";
_caminho = _caminho;
 //BA.debugLineNum = 330;BA.debugLine="If File.Exists(caminho, Logado&\"saldo.txt\") Then";
if (__c.File.Exists(_caminho,_logado()+"saldo.txt")) { 
 //BA.debugLineNum = 331;BA.debugLine="RenameFile(caminho&\"/\"&Logado&\"saldo.txt\",caminho&\"/\"&uNovo&\"saldo.txt\")";
_renamefile(_caminho+"/"+_logado()+"saldo.txt",_caminho+"/"+_unovo+"saldo.txt");
 //BA.debugLineNum = 332;BA.debugLine="File.Delete(caminho,Logado&\"saldo.txt\")";
__c.File.Delete(_caminho,_logado()+"saldo.txt");
 };
 //BA.debugLineNum = 335;BA.debugLine="If File.Exists(caminho, Logado&\"categ.txt\") Then";
if (__c.File.Exists(_caminho,_logado()+"categ.txt")) { 
 //BA.debugLineNum = 336;BA.debugLine="RenameFile(caminho&\"/\"&Logado&\"categ.txt\",caminho&\"/\"&uNovo&\"categ.txt\")";
_renamefile(_caminho+"/"+_logado()+"categ.txt",_caminho+"/"+_unovo+"categ.txt");
 //BA.debugLineNum = 337;BA.debugLine="File.Delete(caminho, Logado&\"categ.txt\")";
__c.File.Delete(_caminho,_logado()+"categ.txt");
 };
 //BA.debugLineNum = 340;BA.debugLine="If File.Exists(caminho, Logado&\"transacoes.txt\") Then";
if (__c.File.Exists(_caminho,_logado()+"transacoes.txt")) { 
 //BA.debugLineNum = 341;BA.debugLine="RenameFile(caminho&\"/\"&Logado&\"transacoes.txt\",caminho&\"/\"&uNovo&\"transacoes.txt\")";
_renamefile(_caminho+"/"+_logado()+"transacoes.txt",_caminho+"/"+_unovo+"transacoes.txt");
 //BA.debugLineNum = 342;BA.debugLine="File.Delete(caminho, Logado&\"transacoes.txt\")";
__c.File.Delete(_caminho,_logado()+"transacoes.txt");
 };
 //BA.debugLineNum = 345;BA.debugLine="If File.Exists(caminho,\"logado.txt\") Then";
if (__c.File.Exists(_caminho,"logado.txt")) { 
 //BA.debugLineNum = 346;BA.debugLine="File.Delete(caminho,\"logado.txt\")";
__c.File.Delete(_caminho,"logado.txt");
 };
 //BA.debugLineNum = 348;BA.debugLine="Dim TextWriter1 As TextWriter";
_textwriter1 = new anywheresoftware.b4a.objects.streams.File.TextWriterWrapper();
 //BA.debugLineNum = 349;BA.debugLine="TextWriter1.Initialize(File.OpenOutput(caminho,\"logado.txt\",True))";
_textwriter1.Initialize((java.io.OutputStream)(__c.File.OpenOutput(_caminho,"logado.txt",__c.True).getObject()));
 //BA.debugLineNum = 350;BA.debugLine="TextWriter1.Write(uNovo)";
_textwriter1.Write(_unovo);
 //BA.debugLineNum = 351;BA.debugLine="TextWriter1.Close";
_textwriter1.Close();
 //BA.debugLineNum = 352;BA.debugLine="End Sub";
return "";
}
public String  _class_globals() throws Exception{
 //BA.debugLineNum = 2;BA.debugLine="Sub Class_Globals";
 //BA.debugLineNum = 3;BA.debugLine="Private caminho As String";
_caminho = "";
 //BA.debugLineNum = 4;BA.debugLine="End Sub";
return "";
}
public boolean  _criar_login(String _nome,String _username,String _senha,String _senha_repetida) throws Exception{
anywheresoftware.b4a.objects.streams.File.TextWriterWrapper _textwriter1 = null;
anywheresoftware.b4a.objects.streams.File.TextReaderWrapper _textreader1 = null;
String _linha1 = "";
String _linha2 = "";
String _linha3 = "";
String _linha4 = "";
anywheresoftware.b4a.objects.streams.File.TextWriterWrapper _textwriter2 = null;
 //BA.debugLineNum = 51;BA.debugLine="Public Sub Criar_Login(Nome As String, Username As String, Senha As String, Senha_Repetida As String) As Boolean";
 //BA.debugLineNum = 52;BA.debugLine="If Nome = \"\" OR Username = \"\" OR Senha = \"\" OR Senha_Repetida = \"\" Then";
if ((_nome).equals("") || (_username).equals("") || (_senha).equals("") || (_senha_repetida).equals("")) { 
 //BA.debugLineNum = 53;BA.debugLine="Msgbox(\"Campos Obrigatórios não Preenchidos\", \"Atenção!\")";
__c.Msgbox("Campos Obrigatórios não Preenchidos","Atenção!",getActivityBA());
 }else if(__c.Not((_senha).equals(_senha_repetida))) { 
 //BA.debugLineNum = 55;BA.debugLine="Msgbox(\"Senhas não conferem!\", \"Atenção!\")";
__c.Msgbox("Senhas não conferem!","Atenção!",getActivityBA());
 }else {
 //BA.debugLineNum = 57;BA.debugLine="If Not(File.Exists(caminho, \"Logins.txt\")) Then";
if (__c.Not(__c.File.Exists(_caminho,"Logins.txt"))) { 
 //BA.debugLineNum = 58;BA.debugLine="Dim TextWriter1 As TextWriter";
_textwriter1 = new anywheresoftware.b4a.objects.streams.File.TextWriterWrapper();
 //BA.debugLineNum = 59;BA.debugLine="TextWriter1.Initialize(File.OpenOutput(caminho, \"Logins.txt\", True))";
_textwriter1.Initialize((java.io.OutputStream)(__c.File.OpenOutput(_caminho,"Logins.txt",__c.True).getObject()));
 //BA.debugLineNum = 60;BA.debugLine="TextWriter1.WriteLine(Username &\";\"& Senha &\";\"& Nome)";
_textwriter1.WriteLine(_username+";"+_senha+";"+_nome);
 //BA.debugLineNum = 61;BA.debugLine="TextWriter1.Close";
_textwriter1.Close();
 //BA.debugLineNum = 62;BA.debugLine="Return True";
if (true) return __c.True;
 }else {
 //BA.debugLineNum = 64;BA.debugLine="Dim TextReader1 As TextReader";
_textreader1 = new anywheresoftware.b4a.objects.streams.File.TextReaderWrapper();
 //BA.debugLineNum = 65;BA.debugLine="TextReader1.Initialize(File.OpenInput(caminho, \"Logins.txt\"))";
_textreader1.Initialize((java.io.InputStream)(__c.File.OpenInput(_caminho,"Logins.txt").getObject()));
 //BA.debugLineNum = 66;BA.debugLine="Dim linha1,linha2,linha3,linha4 As String";
_linha1 = "";
_linha2 = "";
_linha3 = "";
_linha4 = "";
 //BA.debugLineNum = 67;BA.debugLine="linha1 = TextReader1.ReadLine";
_linha1 = _textreader1.ReadLine();
 //BA.debugLineNum = 69;BA.debugLine="If linha1 = Null Then";
if (_linha1== null) { 
 //BA.debugLineNum = 70;BA.debugLine="Dim TextWriter1 As TextWriter";
_textwriter1 = new anywheresoftware.b4a.objects.streams.File.TextWriterWrapper();
 //BA.debugLineNum = 71;BA.debugLine="TextWriter1.Initialize(File.OpenOutput(caminho, \"Logins.txt\", True))";
_textwriter1.Initialize((java.io.OutputStream)(__c.File.OpenOutput(_caminho,"Logins.txt",__c.True).getObject()));
 //BA.debugLineNum = 72;BA.debugLine="TextWriter1.WriteLine(Username &\";\"& Senha &\";\"& Nome)";
_textwriter1.WriteLine(_username+";"+_senha+";"+_nome);
 //BA.debugLineNum = 73;BA.debugLine="TextWriter1.Close";
_textwriter1.Close();
 //BA.debugLineNum = 74;BA.debugLine="Return True";
if (true) return __c.True;
 };
 //BA.debugLineNum = 77;BA.debugLine="linha2 = linha1.SubString2(0,linha1.IndexOf(\";\"))";
_linha2 = _linha1.substring((int) (0),_linha1.indexOf(";"));
 //BA.debugLineNum = 78;BA.debugLine="linha3 = linha1.SubString2(linha1.IndexOf(\";\")+1,linha1.LastIndexOf(\";\"))";
_linha3 = _linha1.substring((int) (_linha1.indexOf(";")+1),_linha1.lastIndexOf(";"));
 //BA.debugLineNum = 79;BA.debugLine="linha4 = linha1.SubString(linha1.LastIndexOf(\";\")+1)";
_linha4 = _linha1.substring((int) (_linha1.lastIndexOf(";")+1));
 //BA.debugLineNum = 81;BA.debugLine="Do While linha1 <> Null";
while (_linha1!= null) {
 //BA.debugLineNum = 82;BA.debugLine="If linha2 = Username Then";
if ((_linha2).equals(_username)) { 
 //BA.debugLineNum = 83;BA.debugLine="Msgbox(\"Username já existe!\",\"Atenção!\")";
__c.Msgbox("Username já existe!","Atenção!",getActivityBA());
 //BA.debugLineNum = 84;BA.debugLine="linha1 = TextReader1.ReadLine";
_linha1 = _textreader1.ReadLine();
 }else {
 //BA.debugLineNum = 86;BA.debugLine="Dim TextWriter2 As TextWriter";
_textwriter2 = new anywheresoftware.b4a.objects.streams.File.TextWriterWrapper();
 //BA.debugLineNum = 87;BA.debugLine="TextWriter2.Initialize(File.OpenOutput(caminho, \"Logins.txt\", True))";
_textwriter2.Initialize((java.io.OutputStream)(__c.File.OpenOutput(_caminho,"Logins.txt",__c.True).getObject()));
 //BA.debugLineNum = 88;BA.debugLine="TextWriter2.WriteLine(Username &\";\"& Senha &\";\"& Nome)";
_textwriter2.WriteLine(_username+";"+_senha+";"+_nome);
 //BA.debugLineNum = 89;BA.debugLine="TextWriter2.Close";
_textwriter2.Close();
 //BA.debugLineNum = 90;BA.debugLine="Return True";
if (true) return __c.True;
 };
 }
;
 //BA.debugLineNum = 93;BA.debugLine="TextReader1.Close";
_textreader1.Close();
 };
 };
 //BA.debugLineNum = 96;BA.debugLine="Return False";
if (true) return __c.False;
 //BA.debugLineNum = 97;BA.debugLine="End Sub";
return false;
}
public String  _deletar_categoria(String _categoria) throws Exception{
anywheresoftware.b4a.objects.streams.File.TextReaderWrapper _textreader1 = null;
anywheresoftware.b4a.objects.streams.File.TextWriterWrapper _textwriter1 = null;
anywheresoftware.b4a.objects.collections.List _listatrans = null;
String _trans = "";
String _str = "";
anywheresoftware.b4a.objects.collections.List _lista = null;
 //BA.debugLineNum = 275;BA.debugLine="Public Sub Deletar_Categoria(Categoria As String) As String";
 //BA.debugLineNum = 276;BA.debugLine="Dim TextReader1 As TextReader";
_textreader1 = new anywheresoftware.b4a.objects.streams.File.TextReaderWrapper();
 //BA.debugLineNum = 277;BA.debugLine="Dim TextWriter1 As TextWriter";
_textwriter1 = new anywheresoftware.b4a.objects.streams.File.TextWriterWrapper();
 //BA.debugLineNum = 278;BA.debugLine="If Categoria = \"Agua\" OR Categoria = \"Luz\" OR Categoria = \"Telefone\" OR Categoria = \"Interet\" OR Categoria = \"TV\" OR Categoria = \"Salario\" OR Categoria = \"Alimentacao\" OR Categoria = \"Combustivel\" OR Categoria = \"Construcao\" Then";
if ((_categoria).equals("Agua") || (_categoria).equals("Luz") || (_categoria).equals("Telefone") || (_categoria).equals("Interet") || (_categoria).equals("TV") || (_categoria).equals("Salario") || (_categoria).equals("Alimentacao") || (_categoria).equals("Combustivel") || (_categoria).equals("Construcao")) { 
 //BA.debugLineNum = 279;BA.debugLine="Return \"False\"";
if (true) return "False";
 }else {
 //BA.debugLineNum = 281;BA.debugLine="Dim listaTrans As List";
_listatrans = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 282;BA.debugLine="listaTrans = GetTransacoes(Main.Pers.Logado)";
_listatrans = _gettransacoes(_main._pers._logado());
 //BA.debugLineNum = 284;BA.debugLine="For Each trans As String In listaTrans";
final anywheresoftware.b4a.BA.IterableList group249 = _listatrans;
final int groupLen249 = group249.getSize();
for (int index249 = 0;index249 < groupLen249 ;index249++){
_trans = BA.ObjectToString(group249.Get(index249));
 //BA.debugLineNum = 285;BA.debugLine="Dim str As String";
_str = "";
 //BA.debugLineNum = 286;BA.debugLine="str = trans.SubString(trans.LastIndexOf(\";\")+1)";
_str = _trans.substring((int) (_trans.lastIndexOf(";")+1));
 //BA.debugLineNum = 287;BA.debugLine="If Categoria = str Then";
if ((_categoria).equals(_str)) { 
 //BA.debugLineNum = 288;BA.debugLine="Return \"Null\"";
if (true) return "Null";
 };
 }
;
 //BA.debugLineNum = 292;BA.debugLine="TextReader1.Initialize(File.OpenInput(caminho,Logado&\"categ.txt\"))";
_textreader1.Initialize((java.io.InputStream)(__c.File.OpenInput(_caminho,_logado()+"categ.txt").getObject()));
 //BA.debugLineNum = 294;BA.debugLine="Dim Lista As List";
_lista = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 295;BA.debugLine="Lista.Initialize";
_lista.Initialize();
 //BA.debugLineNum = 296;BA.debugLine="Lista.AddAll(TextReader1.ReadList)";
_lista.AddAll(_textreader1.ReadList());
 //BA.debugLineNum = 297;BA.debugLine="TextReader1.Close";
_textreader1.Close();
 //BA.debugLineNum = 299;BA.debugLine="File.Delete(caminho,Logado&\"categ.txt\")";
__c.File.Delete(_caminho,_logado()+"categ.txt");
 //BA.debugLineNum = 301;BA.debugLine="TextWriter1.Initialize(File.OpenOutput(caminho,Logado&\"categ.txt\",True))";
_textwriter1.Initialize((java.io.OutputStream)(__c.File.OpenOutput(_caminho,_logado()+"categ.txt",__c.True).getObject()));
 //BA.debugLineNum = 302;BA.debugLine="TextWriter1.WriteList(Lista)";
_textwriter1.WriteList(_lista);
 //BA.debugLineNum = 303;BA.debugLine="TextWriter1.Close";
_textwriter1.Close();
 //BA.debugLineNum = 304;BA.debugLine="Return \"True\"";
if (true) return "True";
 };
 //BA.debugLineNum = 306;BA.debugLine="End Sub";
return "";
}
public boolean  _excluir_login(String _username,String _senha) throws Exception{
anywheresoftware.b4a.objects.streams.File.TextReaderWrapper _textreader1 = null;
anywheresoftware.b4a.objects.collections.List _texto = null;
String _linha1 = "";
String _linha2 = "";
String _linha3 = "";
String _linha4 = "";
anywheresoftware.b4a.objects.streams.File.TextWriterWrapper _textwriter1 = null;
 //BA.debugLineNum = 99;BA.debugLine="Public Sub Excluir_Login(Username As String, Senha As String) As Boolean";
 //BA.debugLineNum = 100;BA.debugLine="If Username = \"\" OR Senha = \"\" Then";
if ((_username).equals("") || (_senha).equals("")) { 
 //BA.debugLineNum = 101;BA.debugLine="Msgbox(\"Campos Obrigatórios não Preenchidos\", \"Atenção!\")";
__c.Msgbox("Campos Obrigatórios não Preenchidos","Atenção!",getActivityBA());
 }else {
 //BA.debugLineNum = 103;BA.debugLine="Dim TextReader1 As TextReader";
_textreader1 = new anywheresoftware.b4a.objects.streams.File.TextReaderWrapper();
 //BA.debugLineNum = 104;BA.debugLine="TextReader1.Initialize(File.OpenInput(caminho, \"Logins.txt\"))";
_textreader1.Initialize((java.io.InputStream)(__c.File.OpenInput(_caminho,"Logins.txt").getObject()));
 //BA.debugLineNum = 106;BA.debugLine="Dim texto As List";
_texto = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 107;BA.debugLine="texto.Initialize";
_texto.Initialize();
 //BA.debugLineNum = 109;BA.debugLine="Dim linha1,linha2,linha3,linha4 As String";
_linha1 = "";
_linha2 = "";
_linha3 = "";
_linha4 = "";
 //BA.debugLineNum = 110;BA.debugLine="linha1 = TextReader1.ReadLine";
_linha1 = _textreader1.ReadLine();
 //BA.debugLineNum = 111;BA.debugLine="linha2 = linha1.SubString2(0,linha1.IndexOf(\";\"))";
_linha2 = _linha1.substring((int) (0),_linha1.indexOf(";"));
 //BA.debugLineNum = 112;BA.debugLine="linha3 = linha1.SubString2(linha1.IndexOf(\";\")+1,linha1.LastIndexOf(\";\"))";
_linha3 = _linha1.substring((int) (_linha1.indexOf(";")+1),_linha1.lastIndexOf(";"));
 //BA.debugLineNum = 113;BA.debugLine="linha4 = linha1.SubString(linha1.LastIndexOf(\";\")+1)";
_linha4 = _linha1.substring((int) (_linha1.lastIndexOf(";")+1));
 //BA.debugLineNum = 115;BA.debugLine="Do While linha1 <> Null";
while (_linha1!= null) {
 //BA.debugLineNum = 116;BA.debugLine="If linha2 & linha3 = Username & Senha Then";
if ((_linha2+_linha3).equals(_username+_senha)) { 
 //BA.debugLineNum = 117;BA.debugLine="If File.Delete(caminho, \"Logins.txt\") Then";
if (__c.File.Delete(_caminho,"Logins.txt")) { 
 //BA.debugLineNum = 118;BA.debugLine="Dim TextWriter1 As TextWriter";
_textwriter1 = new anywheresoftware.b4a.objects.streams.File.TextWriterWrapper();
 //BA.debugLineNum = 119;BA.debugLine="TextWriter1.Initialize(File.OpenOutput(caminho, \"Logins.txt\", True))";
_textwriter1.Initialize((java.io.OutputStream)(__c.File.OpenOutput(_caminho,"Logins.txt",__c.True).getObject()));
 //BA.debugLineNum = 120;BA.debugLine="If texto <> Null Then";
if (_texto!= null) { 
 //BA.debugLineNum = 121;BA.debugLine="TextWriter1.WriteList(texto)";
_textwriter1.WriteList(_texto);
 };
 //BA.debugLineNum = 123;BA.debugLine="TextWriter1.Close";
_textwriter1.Close();
 //BA.debugLineNum = 124;BA.debugLine="TextReader1.Close";
_textreader1.Close();
 //BA.debugLineNum = 125;BA.debugLine="Return True";
if (true) return __c.True;
 };
 }else {
 //BA.debugLineNum = 128;BA.debugLine="texto.Add(linha1)";
_texto.Add((Object)(_linha1));
 //BA.debugLineNum = 129;BA.debugLine="linha1 = TextReader1.ReadLine";
_linha1 = _textreader1.ReadLine();
 };
 }
;
 };
 //BA.debugLineNum = 134;BA.debugLine="Return False";
if (true) return __c.False;
 //BA.debugLineNum = 135;BA.debugLine="End Sub";
return false;
}
public boolean  _fazer_login(String _username,String _senha) throws Exception{
anywheresoftware.b4a.objects.streams.File.TextWriterWrapper _textwriter1 = null;
anywheresoftware.b4a.objects.streams.File.TextReaderWrapper _textreader1 = null;
String _linha1 = "";
String _linha2 = "";
String _linha3 = "";
String _linha4 = "";
 //BA.debugLineNum = 10;BA.debugLine="Public Sub Fazer_Login (Username As String, Senha As String) As Boolean";
 //BA.debugLineNum = 11;BA.debugLine="If Username = \"\" OR Senha = \"\" Then";
if ((_username).equals("") || (_senha).equals("")) { 
 //BA.debugLineNum = 12;BA.debugLine="Msgbox(\"Campos Obrigatorios não estão preenchidos\", \"Aviso!\" )";
__c.Msgbox("Campos Obrigatorios não estão preenchidos","Aviso!",getActivityBA());
 }else {
 //BA.debugLineNum = 14;BA.debugLine="If Not(File.Exists(caminho, \"Logins.txt\")) Then";
if (__c.Not(__c.File.Exists(_caminho,"Logins.txt"))) { 
 //BA.debugLineNum = 15;BA.debugLine="Dim TextWriter1 As TextWriter";
_textwriter1 = new anywheresoftware.b4a.objects.streams.File.TextWriterWrapper();
 //BA.debugLineNum = 16;BA.debugLine="TextWriter1.Initialize(File.OpenOutput(caminho, \"Logins.txt\", False))";
_textwriter1.Initialize((java.io.OutputStream)(__c.File.OpenOutput(_caminho,"Logins.txt",__c.False).getObject()));
 //BA.debugLineNum = 17;BA.debugLine="TextWriter1.Close";
_textwriter1.Close();
 }else {
 //BA.debugLineNum = 19;BA.debugLine="Dim TextReader1 As TextReader";
_textreader1 = new anywheresoftware.b4a.objects.streams.File.TextReaderWrapper();
 //BA.debugLineNum = 20;BA.debugLine="TextReader1.Initialize(File.OpenInput(caminho, \"Logins.txt\"))";
_textreader1.Initialize((java.io.InputStream)(__c.File.OpenInput(_caminho,"Logins.txt").getObject()));
 //BA.debugLineNum = 21;BA.debugLine="Dim linha1,linha2,linha3,linha4 As String";
_linha1 = "";
_linha2 = "";
_linha3 = "";
_linha4 = "";
 //BA.debugLineNum = 22;BA.debugLine="linha1 = TextReader1.ReadLine";
_linha1 = _textreader1.ReadLine();
 //BA.debugLineNum = 23;BA.debugLine="If linha1 = Null Then";
if (_linha1== null) { 
 //BA.debugLineNum = 24;BA.debugLine="Return False";
if (true) return __c.False;
 };
 //BA.debugLineNum = 26;BA.debugLine="linha2 = linha1.SubString2(0,linha1.IndexOf(\";\"))";
_linha2 = _linha1.substring((int) (0),_linha1.indexOf(";"));
 //BA.debugLineNum = 27;BA.debugLine="linha3 = linha1.SubString2(linha1.IndexOf(\";\")+1,linha1.LastIndexOf(\";\"))";
_linha3 = _linha1.substring((int) (_linha1.indexOf(";")+1),_linha1.lastIndexOf(";"));
 //BA.debugLineNum = 28;BA.debugLine="linha4 = linha1.SubString(linha1.LastIndexOf(\";\")+1)";
_linha4 = _linha1.substring((int) (_linha1.lastIndexOf(";")+1));
 //BA.debugLineNum = 30;BA.debugLine="Do While linha1 <> Null";
while (_linha1!= null) {
 //BA.debugLineNum = 31;BA.debugLine="If linha2 & linha3 = Username & Senha Then";
if ((_linha2+_linha3).equals(_username+_senha)) { 
 //BA.debugLineNum = 32;BA.debugLine="Return True";
if (true) return __c.True;
 };
 //BA.debugLineNum = 34;BA.debugLine="linha1 = TextReader1.ReadLine";
_linha1 = _textreader1.ReadLine();
 //BA.debugLineNum = 35;BA.debugLine="If linha1 <> Null Then";
if (_linha1!= null) { 
 //BA.debugLineNum = 36;BA.debugLine="linha2 = linha1.SubString2(0,linha1.IndexOf(\";\"))";
_linha2 = _linha1.substring((int) (0),_linha1.indexOf(";"));
 //BA.debugLineNum = 37;BA.debugLine="linha3 = linha1.SubString2(linha1.IndexOf(\";\")+1,linha1.LastIndexOf(\";\"))";
_linha3 = _linha1.substring((int) (_linha1.indexOf(";")+1),_linha1.lastIndexOf(";"));
 //BA.debugLineNum = 38;BA.debugLine="linha4 = linha1.SubString(linha1.LastIndexOf(\";\")+1)";
_linha4 = _linha1.substring((int) (_linha1.lastIndexOf(";")+1));
 }else {
 //BA.debugLineNum = 40;BA.debugLine="Return False";
if (true) return __c.False;
 };
 }
;
 //BA.debugLineNum = 43;BA.debugLine="TextReader1.Close";
_textreader1.Close();
 };
 };
 //BA.debugLineNum = 47;BA.debugLine="Return False";
if (true) return __c.False;
 //BA.debugLineNum = 49;BA.debugLine="End Sub";
return false;
}
public anywheresoftware.b4a.objects.collections.List  _getcategorias() throws Exception{
anywheresoftware.b4a.objects.streams.File.TextReaderWrapper _textreader1 = null;
anywheresoftware.b4a.objects.streams.File.TextReaderWrapper _textreader2 = null;
anywheresoftware.b4a.objects.streams.File.TextWriterWrapper _textwriter1 = null;
anywheresoftware.b4a.objects.collections.List _lista = null;
 //BA.debugLineNum = 308;BA.debugLine="Public Sub GetCategorias As List";
 //BA.debugLineNum = 309;BA.debugLine="Dim TextReader1,TextReader2 As TextReader";
_textreader1 = new anywheresoftware.b4a.objects.streams.File.TextReaderWrapper();
_textreader2 = new anywheresoftware.b4a.objects.streams.File.TextReaderWrapper();
 //BA.debugLineNum = 310;BA.debugLine="TextReader1.Initialize(File.OpenInput(File.DirAssets,\"categ.txt\"))";
_textreader1.Initialize((java.io.InputStream)(__c.File.OpenInput(__c.File.getDirAssets(),"categ.txt").getObject()));
 //BA.debugLineNum = 311;BA.debugLine="If Not(File.Exists(caminho,Logado&\"categ.txt\")) Then";
if (__c.Not(__c.File.Exists(_caminho,_logado()+"categ.txt"))) { 
 //BA.debugLineNum = 312;BA.debugLine="Dim TextWriter1 As TextWriter";
_textwriter1 = new anywheresoftware.b4a.objects.streams.File.TextWriterWrapper();
 //BA.debugLineNum = 313;BA.debugLine="TextWriter1.Initialize(File.OpenOutput(caminho,Logado&\"categ.txt\",True))";
_textwriter1.Initialize((java.io.OutputStream)(__c.File.OpenOutput(_caminho,_logado()+"categ.txt",__c.True).getObject()));
 //BA.debugLineNum = 314;BA.debugLine="TextWriter1.Close";
_textwriter1.Close();
 };
 //BA.debugLineNum = 316;BA.debugLine="TextReader2.Initialize(File.OpenInput(caminho,Logado&\"categ.txt\"))";
_textreader2.Initialize((java.io.InputStream)(__c.File.OpenInput(_caminho,_logado()+"categ.txt").getObject()));
 //BA.debugLineNum = 317;BA.debugLine="Dim Lista As List";
_lista = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 318;BA.debugLine="Lista.Initialize";
_lista.Initialize();
 //BA.debugLineNum = 319;BA.debugLine="Lista.AddAll(TextReader1.ReadList)";
_lista.AddAll(_textreader1.ReadList());
 //BA.debugLineNum = 320;BA.debugLine="Lista.AddAll(TextReader2.ReadList)";
_lista.AddAll(_textreader2.ReadList());
 //BA.debugLineNum = 321;BA.debugLine="TextReader1.Close";
_textreader1.Close();
 //BA.debugLineNum = 322;BA.debugLine="TextReader2.Close";
_textreader2.Close();
 //BA.debugLineNum = 323;BA.debugLine="Return Lista";
if (true) return _lista;
 //BA.debugLineNum = 324;BA.debugLine="End Sub";
return null;
}
public String  _getsaldo() throws Exception{
anywheresoftware.b4a.objects.streams.File.TextReaderWrapper _tr = null;
anywheresoftware.b4a.objects.streams.File.TextWriterWrapper _tw = null;
String _saldo = "";
 //BA.debugLineNum = 160;BA.debugLine="Public Sub GetSaldo As String";
 //BA.debugLineNum = 161;BA.debugLine="Dim tr As TextReader";
_tr = new anywheresoftware.b4a.objects.streams.File.TextReaderWrapper();
 //BA.debugLineNum = 162;BA.debugLine="Dim tw As TextWriter";
_tw = new anywheresoftware.b4a.objects.streams.File.TextWriterWrapper();
 //BA.debugLineNum = 163;BA.debugLine="If Not(File.Exists(caminho,Logado&\"saldo.txt\")) Then";
if (__c.Not(__c.File.Exists(_caminho,_logado()+"saldo.txt"))) { 
 //BA.debugLineNum = 164;BA.debugLine="tw.Initialize(File.OpenOutput(caminho,Logado&\"saldo.txt\",True))";
_tw.Initialize((java.io.OutputStream)(__c.File.OpenOutput(_caminho,_logado()+"saldo.txt",__c.True).getObject()));
 //BA.debugLineNum = 165;BA.debugLine="tw.Close";
_tw.Close();
 //BA.debugLineNum = 166;BA.debugLine="Return 0";
if (true) return BA.NumberToString(0);
 };
 //BA.debugLineNum = 168;BA.debugLine="tr.Initialize(File.OpenInput(caminho,Logado&\"saldo.txt\"))";
_tr.Initialize((java.io.InputStream)(__c.File.OpenInput(_caminho,_logado()+"saldo.txt").getObject()));
 //BA.debugLineNum = 169;BA.debugLine="Dim saldo As String";
_saldo = "";
 //BA.debugLineNum = 170;BA.debugLine="saldo = tr.ReadLine";
_saldo = _tr.ReadLine();
 //BA.debugLineNum = 171;BA.debugLine="If saldo = Null Then";
if (_saldo== null) { 
 //BA.debugLineNum = 172;BA.debugLine="Return 0";
if (true) return BA.NumberToString(0);
 };
 //BA.debugLineNum = 174;BA.debugLine="Return saldo";
if (true) return _saldo;
 //BA.debugLineNum = 175;BA.debugLine="End Sub";
return "";
}
public anywheresoftware.b4a.objects.collections.List  _gettransacoes(String _usuario) throws Exception{
anywheresoftware.b4a.objects.streams.File.TextReaderWrapper _textreader1 = null;
anywheresoftware.b4a.objects.streams.File.TextWriterWrapper _tw = null;
anywheresoftware.b4a.objects.collections.List _transacoes = null;
 //BA.debugLineNum = 185;BA.debugLine="Public Sub GetTransacoes(Usuario As String) As List";
 //BA.debugLineNum = 186;BA.debugLine="Dim TextReader1 As TextReader";
_textreader1 = new anywheresoftware.b4a.objects.streams.File.TextReaderWrapper();
 //BA.debugLineNum = 187;BA.debugLine="If Not(File.Exists(caminho,Logado&\"transacoes.txt\")) Then";
if (__c.Not(__c.File.Exists(_caminho,_logado()+"transacoes.txt"))) { 
 //BA.debugLineNum = 188;BA.debugLine="Dim tw As TextWriter";
_tw = new anywheresoftware.b4a.objects.streams.File.TextWriterWrapper();
 //BA.debugLineNum = 189;BA.debugLine="tw.Initialize(File.OpenOutput(caminho,Logado&\"transacoes.txt\",True))";
_tw.Initialize((java.io.OutputStream)(__c.File.OpenOutput(_caminho,_logado()+"transacoes.txt",__c.True).getObject()));
 //BA.debugLineNum = 190;BA.debugLine="tw.Close";
_tw.Close();
 };
 //BA.debugLineNum = 192;BA.debugLine="TextReader1.Initialize(File.OpenInput(caminho,Logado&\"transacoes.txt\"))";
_textreader1.Initialize((java.io.InputStream)(__c.File.OpenInput(_caminho,_logado()+"transacoes.txt").getObject()));
 //BA.debugLineNum = 193;BA.debugLine="Dim transacoes As List";
_transacoes = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 194;BA.debugLine="transacoes.Initialize";
_transacoes.Initialize();
 //BA.debugLineNum = 195;BA.debugLine="transacoes = TextReader1.ReadList";
_transacoes = _textreader1.ReadList();
 //BA.debugLineNum = 196;BA.debugLine="Return transacoes";
if (true) return _transacoes;
 //BA.debugLineNum = 197;BA.debugLine="End Sub";
return null;
}
public Object  _getusuario(String _username) throws Exception{
anywheresoftware.b4a.objects.streams.File.TextReaderWrapper _textreader1 = null;
String _linha1 = "";
String _linha2 = "";
String _linha3 = "";
String _linha4 = "";
 //BA.debugLineNum = 240;BA.debugLine="Public Sub GetUsuario(Username As String) As Object";
 //BA.debugLineNum = 241;BA.debugLine="Dim TextReader1 As TextReader";
_textreader1 = new anywheresoftware.b4a.objects.streams.File.TextReaderWrapper();
 //BA.debugLineNum = 242;BA.debugLine="TextReader1.Initialize(File.OpenInput(caminho, \"Logins.txt\"))";
_textreader1.Initialize((java.io.InputStream)(__c.File.OpenInput(_caminho,"Logins.txt").getObject()));
 //BA.debugLineNum = 243;BA.debugLine="Dim linha1,linha2,linha3,linha4 As String";
_linha1 = "";
_linha2 = "";
_linha3 = "";
_linha4 = "";
 //BA.debugLineNum = 244;BA.debugLine="linha1 = TextReader1.ReadLine";
_linha1 = _textreader1.ReadLine();
 //BA.debugLineNum = 245;BA.debugLine="If linha1 = Null Then";
if (_linha1== null) { 
 //BA.debugLineNum = 246;BA.debugLine="Return False";
if (true) return (Object)(__c.False);
 };
 //BA.debugLineNum = 248;BA.debugLine="linha2 = linha1.SubString2(0,linha1.IndexOf(\";\"))";
_linha2 = _linha1.substring((int) (0),_linha1.indexOf(";"));
 //BA.debugLineNum = 249;BA.debugLine="linha3 = linha1.SubString2(linha1.IndexOf(\";\")+1,linha1.LastIndexOf(\";\"))";
_linha3 = _linha1.substring((int) (_linha1.indexOf(";")+1),_linha1.lastIndexOf(";"));
 //BA.debugLineNum = 250;BA.debugLine="linha4 = linha1.SubString(linha1.LastIndexOf(\";\")+1)";
_linha4 = _linha1.substring((int) (_linha1.lastIndexOf(";")+1));
 //BA.debugLineNum = 252;BA.debugLine="Do While linha1 <> Null";
while (_linha1!= null) {
 //BA.debugLineNum = 253;BA.debugLine="If linha2 = Username Then";
if ((_linha2).equals(_username)) { 
 //BA.debugLineNum = 254;BA.debugLine="Return linha1";
if (true) return (Object)(_linha1);
 };
 //BA.debugLineNum = 256;BA.debugLine="linha1 = TextReader1.ReadLine";
_linha1 = _textreader1.ReadLine();
 }
;
 //BA.debugLineNum = 258;BA.debugLine="TextReader1.Close";
_textreader1.Close();
 //BA.debugLineNum = 259;BA.debugLine="Return Null";
if (true) return __c.Null;
 //BA.debugLineNum = 260;BA.debugLine="End Sub";
return null;
}
public String  _initialize(anywheresoftware.b4a.BA _ba) throws Exception{
innerInitialize(_ba);
 //BA.debugLineNum = 6;BA.debugLine="Public Sub Initialize";
 //BA.debugLineNum = 7;BA.debugLine="caminho = File.DirInternal";
_caminho = __c.File.getDirInternal();
 //BA.debugLineNum = 8;BA.debugLine="End Sub";
return "";
}
public String  _logado() throws Exception{
anywheresoftware.b4a.objects.streams.File.TextReaderWrapper _textreader1 = null;
String _usuario = "";
 //BA.debugLineNum = 177;BA.debugLine="Public Sub Logado As String";
 //BA.debugLineNum = 178;BA.debugLine="Dim TextReader1 As TextReader";
_textreader1 = new anywheresoftware.b4a.objects.streams.File.TextReaderWrapper();
 //BA.debugLineNum = 179;BA.debugLine="TextReader1.Initialize(File.OpenInput(caminho,\"logado.txt\"))";
_textreader1.Initialize((java.io.InputStream)(__c.File.OpenInput(_caminho,"logado.txt").getObject()));
 //BA.debugLineNum = 180;BA.debugLine="Dim usuario As String";
_usuario = "";
 //BA.debugLineNum = 181;BA.debugLine="usuario = TextReader1.ReadLine";
_usuario = _textreader1.ReadLine();
 //BA.debugLineNum = 182;BA.debugLine="Return usuario";
if (true) return _usuario;
 //BA.debugLineNum = 183;BA.debugLine="End Sub";
return "";
}
public String  _remover_transacao(int _pos) throws Exception{
String _linha1 = "";
String _linha2 = "";
anywheresoftware.b4a.objects.collections.List _lista = null;
anywheresoftware.b4a.objects.streams.File.TextReaderWrapper _textreader1 = null;
anywheresoftware.b4a.objects.streams.File.TextWriterWrapper _textwriter1 = null;
float _valor = 0f;
 //BA.debugLineNum = 215;BA.debugLine="Public Sub Remover_Transacao (Pos As Int)";
 //BA.debugLineNum = 216;BA.debugLine="Dim linha1,linha2 As String";
_linha1 = "";
_linha2 = "";
 //BA.debugLineNum = 217;BA.debugLine="Dim lista As List";
_lista = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 218;BA.debugLine="Dim TextReader1 As TextReader";
_textreader1 = new anywheresoftware.b4a.objects.streams.File.TextReaderWrapper();
 //BA.debugLineNum = 219;BA.debugLine="Dim TextWriter1 As TextWriter";
_textwriter1 = new anywheresoftware.b4a.objects.streams.File.TextWriterWrapper();
 //BA.debugLineNum = 221;BA.debugLine="linha1 = GetTransacoes(Logado).Get(Pos)";
_linha1 = BA.ObjectToString(_gettransacoes(_logado()).Get(_pos));
 //BA.debugLineNum = 222;BA.debugLine="linha2 = linha1.SubString2(0,linha1.IndexOf(\";\"))";
_linha2 = _linha1.substring((int) (0),_linha1.indexOf(";"));
 //BA.debugLineNum = 224;BA.debugLine="TextReader1.Initialize(File.OpenInput(caminho,Logado&\"transacoes.txt\"))";
_textreader1.Initialize((java.io.InputStream)(__c.File.OpenInput(_caminho,_logado()+"transacoes.txt").getObject()));
 //BA.debugLineNum = 225;BA.debugLine="lista = TextReader1.ReadList";
_lista = _textreader1.ReadList();
 //BA.debugLineNum = 226;BA.debugLine="TextReader1.Close";
_textreader1.Close();
 //BA.debugLineNum = 228;BA.debugLine="File.Delete(caminho,Logado&\"transacoes.txt\")";
__c.File.Delete(_caminho,_logado()+"transacoes.txt");
 //BA.debugLineNum = 230;BA.debugLine="lista.RemoveAt(Pos)";
_lista.RemoveAt(_pos);
 //BA.debugLineNum = 232;BA.debugLine="TextWriter1.Initialize(File.OpenOutput(caminho,Logado&\"transacoes.txt\",True))";
_textwriter1.Initialize((java.io.OutputStream)(__c.File.OpenOutput(_caminho,_logado()+"transacoes.txt",__c.True).getObject()));
 //BA.debugLineNum = 233;BA.debugLine="TextWriter1.WriteList(lista)";
_textwriter1.WriteList(_lista);
 //BA.debugLineNum = 234;BA.debugLine="Dim Valor As Float = linha2";
_valor = (float)(Double.parseDouble(_linha2));
 //BA.debugLineNum = 235;BA.debugLine="TextWriter1.Close";
_textwriter1.Close();
 //BA.debugLineNum = 236;BA.debugLine="Atualizar_Saldo(-Valor)";
_atualizar_saldo((float) (-_valor));
 //BA.debugLineNum = 238;BA.debugLine="End Sub";
return "";
}
public boolean  _renamefile(String _originalfilename,String _newfilename) throws Exception{
int _result = 0;
anywheresoftware.b4a.keywords.StringBuilderWrapper _stdout = null;
anywheresoftware.b4a.keywords.StringBuilderWrapper _stderr = null;
anywheresoftware.b4a.phone.Phone _ph = null;
 //BA.debugLineNum = 354;BA.debugLine="Private Sub RenameFile(OriginalFileName As String, NewFileName As String) As Boolean";
 //BA.debugLineNum = 355;BA.debugLine="Dim Result As Int";
_result = 0;
 //BA.debugLineNum = 356;BA.debugLine="Dim StdOut, StdErr As StringBuilder";
_stdout = new anywheresoftware.b4a.keywords.StringBuilderWrapper();
_stderr = new anywheresoftware.b4a.keywords.StringBuilderWrapper();
 //BA.debugLineNum = 357;BA.debugLine="StdOut.Initialize";
_stdout.Initialize();
 //BA.debugLineNum = 358;BA.debugLine="StdErr.Initialize";
_stderr.Initialize();
 //BA.debugLineNum = 359;BA.debugLine="Dim Ph As Phone";
_ph = new anywheresoftware.b4a.phone.Phone();
 //BA.debugLineNum = 360;BA.debugLine="Result = Ph.Shell(\"mv \" & OriginalFileName & \" \" & NewFileName, Null,  StdOut, StdErr)";
_result = _ph.Shell("mv "+_originalfilename+" "+_newfilename,(String[])(__c.Null),(java.lang.StringBuilder)(_stdout.getObject()),(java.lang.StringBuilder)(_stderr.getObject()));
 //BA.debugLineNum = 361;BA.debugLine="If Result = 0 Then";
if (_result==0) { 
 //BA.debugLineNum = 362;BA.debugLine="Return True";
if (true) return __c.True;
 }else {
 //BA.debugLineNum = 364;BA.debugLine="Return False";
if (true) return __c.False;
 };
 //BA.debugLineNum = 366;BA.debugLine="End Sub";
return false;
}
public boolean  _salvar_categoria(String _categoria) throws Exception{
anywheresoftware.b4a.objects.streams.File.TextWriterWrapper _textwriter1 = null;
 //BA.debugLineNum = 262;BA.debugLine="Public Sub Salvar_Categoria(Categoria As String) As Boolean";
 //BA.debugLineNum = 263;BA.debugLine="If Categoria = \"\" Then";
if ((_categoria).equals("")) { 
 //BA.debugLineNum = 264;BA.debugLine="Msgbox(\"Categoria está em branco!\", \"Atenção!\")";
__c.Msgbox("Categoria está em branco!","Atenção!",getActivityBA());
 }else {
 //BA.debugLineNum = 266;BA.debugLine="Dim TextWriter1 As TextWriter";
_textwriter1 = new anywheresoftware.b4a.objects.streams.File.TextWriterWrapper();
 //BA.debugLineNum = 267;BA.debugLine="TextWriter1.Initialize(File.OpenOutput(caminho, Logado&\"categ.txt\", True))";
_textwriter1.Initialize((java.io.OutputStream)(__c.File.OpenOutput(_caminho,_logado()+"categ.txt",__c.True).getObject()));
 //BA.debugLineNum = 268;BA.debugLine="TextWriter1.WriteLine(Categoria)";
_textwriter1.WriteLine(_categoria);
 //BA.debugLineNum = 269;BA.debugLine="TextWriter1.Close";
_textwriter1.Close();
 //BA.debugLineNum = 270;BA.debugLine="Return True";
if (true) return __c.True;
 };
 //BA.debugLineNum = 272;BA.debugLine="Return False";
if (true) return __c.False;
 //BA.debugLineNum = 273;BA.debugLine="End Sub";
return false;
}
public boolean  _salvar_transacao(String _usuario,Object _valor,String _data,String _categoria,String _tipo) throws Exception{
float _valor1 = 0f;
anywheresoftware.b4a.objects.streams.File.TextWriterWrapper _textwriter1 = null;
 //BA.debugLineNum = 199;BA.debugLine="Public Sub Salvar_Transacao(usuario As String, valor As Object, Data As String, Categoria As String, Tipo As String) As Boolean";
 //BA.debugLineNum = 200;BA.debugLine="Dim valor1 As Float";
_valor1 = 0f;
 //BA.debugLineNum = 201;BA.debugLine="valor1 = valor";
_valor1 = (float)(BA.ObjectToNumber(_valor));
 //BA.debugLineNum = 202;BA.debugLine="If Tipo = \"Crédito\" Then";
if ((_tipo).equals("Crédito")) { 
 //BA.debugLineNum = 203;BA.debugLine="Atualizar_Saldo(valor1)";
_atualizar_saldo(_valor1);
 }else {
 //BA.debugLineNum = 205;BA.debugLine="valor1 = valor1 * (-1)";
_valor1 = (float) (_valor1*(-1));
 //BA.debugLineNum = 206;BA.debugLine="Atualizar_Saldo(valor1)";
_atualizar_saldo(_valor1);
 };
 //BA.debugLineNum = 208;BA.debugLine="Dim TextWriter1 As TextWriter";
_textwriter1 = new anywheresoftware.b4a.objects.streams.File.TextWriterWrapper();
 //BA.debugLineNum = 209;BA.debugLine="TextWriter1.Initialize(File.OpenOutput(caminho,Logado&\"transacoes.txt\",True))";
_textwriter1.Initialize((java.io.OutputStream)(__c.File.OpenOutput(_caminho,_logado()+"transacoes.txt",__c.True).getObject()));
 //BA.debugLineNum = 210;BA.debugLine="TextWriter1.WriteLine(valor1 &\";\"& Data &\";\"& Categoria)";
_textwriter1.WriteLine(BA.NumberToString(_valor1)+";"+_data+";"+_categoria);
 //BA.debugLineNum = 211;BA.debugLine="TextWriter1.Close";
_textwriter1.Close();
 //BA.debugLineNum = 212;BA.debugLine="Return True";
if (true) return __c.True;
 //BA.debugLineNum = 213;BA.debugLine="End Sub";
return false;
}
public Object callSub(String sub, Object sender, Object[] args) throws Exception {
ba.sharedProcessBA.sender = sender;
return BA.SubDelegator.SubNotFound;
}
}
