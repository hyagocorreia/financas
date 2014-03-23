package Financas.Pessoais;

import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.B4AClass;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.debug.*;

public class searchview extends B4AClass.ImplB4AClass implements BA.SubDelegator{
    private static java.util.HashMap<String, java.lang.reflect.Method> htSubs;
    private void innerInitialize(BA _ba) throws Exception {
        if (ba == null) {
            ba = new BA(_ba, this, htSubs, "Financas.Pessoais.searchview");
            if (htSubs == null) {
                ba.loadHtSubs(this.getClass());
                htSubs = ba.htSubs;
            }
            if (ba.getClass().getName().endsWith("ShellBA")) {
			    ba.raiseEvent2(null, true, "CREATE", true, "Financas.Pessoais.searchview",
                    ba);
		    }
        }
        ba.raiseEvent2(null, true, "class_globals", false);
    }

 public anywheresoftware.b4a.keywords.Common __c = null;
public anywheresoftware.b4a.objects.collections.Map _prefixlist = null;
public anywheresoftware.b4a.objects.collections.Map _substringlist = null;
public anywheresoftware.b4a.objects.EditTextWrapper _et = null;
public anywheresoftware.b4a.objects.ListViewWrapper _lv = null;
public int _min_limit = 0;
public int _max_limit = 0;
public Object _mcallback = null;
public String _meventname = "";
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
		return new Object[] {"AddCategoria",Debug.moduleToString(Financas.Pessoais.addcategoria.class),"Cadastro",Debug.moduleToString(Financas.Pessoais.cadastro.class),"Calculadora",Debug.moduleToString(Financas.Pessoais.calculadora.class),"Creditos",Debug.moduleToString(Financas.Pessoais.creditos.class),"Debitos",Debug.moduleToString(Financas.Pessoais.debitos.class),"Editar",Debug.moduleToString(Financas.Pessoais.editar.class),"et",_et,"Excluir",Debug.moduleToString(Financas.Pessoais.excluir.class),"Extrato",Debug.moduleToString(Financas.Pessoais.extrato.class),"Financeiro",Debug.moduleToString(Financas.Pessoais.financeiro.class),"Lista",Debug.moduleToString(Financas.Pessoais.lista.class),"lv",_lv,"Main",Debug.moduleToString(Financas.Pessoais.main.class),"MAX_LIMIT",_max_limit,"mCallback",_mcallback,"Menu",Debug.moduleToString(Financas.Pessoais.menu.class),"mEventName",_meventname,"MIN_LIMIT",_min_limit,"prefixList",_prefixlist,"Remover_Categoria",Debug.moduleToString(Financas.Pessoais.remover_categoria.class),"substringList",_substringlist,"Total",Debug.moduleToString(Financas.Pessoais.total.class),"Utilitarios",Debug.moduleToString(Financas.Pessoais.utilitarios.class)};
}
public String  _additemstolist(anywheresoftware.b4a.objects.collections.List _li,String _full) throws Exception{
		Debug.PushSubsStack("AddItemsToList (searchview) ","searchview",15,ba,this);
try {
int _i = 0;
String _item = "";
Debug.locals.put("li", _li);
Debug.locals.put("full", _full);
 BA.debugLineNum = 62;BA.debugLine="Private Sub AddItemsToList(li As List, full As String)";
Debug.ShouldStop(536870912);
 BA.debugLineNum = 63;BA.debugLine="If li.IsInitialized = False Then Return";
Debug.ShouldStop(1073741824);
if (_li.IsInitialized()==__c.False) { 
if (true) return "";};
 BA.debugLineNum = 64;BA.debugLine="For i = 0 To li.Size - 1";
Debug.ShouldStop(-2147483648);
{
final int step53 = 1;
final int limit53 = (int) (_li.getSize()-1);
for (_i = (int) (0); (step53 > 0 && _i <= limit53) || (step53 < 0 && _i >= limit53); _i = ((int)(0 + _i + step53))) {
Debug.locals.put("i", _i);
 BA.debugLineNum = 65;BA.debugLine="Dim item As String";
Debug.ShouldStop(1);
_item = "";Debug.locals.put("item", _item);
 BA.debugLineNum = 66;BA.debugLine="item = li.Get(i)";
Debug.ShouldStop(2);
_item = BA.ObjectToString(_li.Get(_i));Debug.locals.put("item", _item);
 BA.debugLineNum = 67;BA.debugLine="If full.Length > MAX_LIMIT AND item.ToLowerCase.Contains(full) = False Then";
Debug.ShouldStop(4);
if (_full.length()>_max_limit && _item.toLowerCase().contains(_full)==__c.False) { 
 BA.debugLineNum = 68;BA.debugLine="Continue";
Debug.ShouldStop(8);
if (true) continue;
 };
 BA.debugLineNum = 70;BA.debugLine="lv.AddSingleLine(li.Get(i))";
Debug.ShouldStop(32);
_lv.AddSingleLine(BA.ObjectToString(_li.Get(_i)));
 }
}Debug.locals.put("i", _i);
;
 BA.debugLineNum = 72;BA.debugLine="End Sub";
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
public String  _addtoparent(anywheresoftware.b4a.objects.PanelWrapper _parent,int _left,int _top,int _width,int _height) throws Exception{
		Debug.PushSubsStack("AddToParent (searchview) ","searchview",15,ba,this);
try {
Debug.locals.put("Parent", _parent);
Debug.locals.put("Left", _left);
Debug.locals.put("Top", _top);
Debug.locals.put("Width", _width);
Debug.locals.put("Height", _height);
 BA.debugLineNum = 31;BA.debugLine="Public Sub AddToParent(Parent As Panel, Left As Int, Top As Int, Width As Int, Height As Int)";
Debug.ShouldStop(1073741824);
 BA.debugLineNum = 32;BA.debugLine="Parent.AddView(et, Left, Top, Width, 60dip)";
Debug.ShouldStop(-2147483648);
_parent.AddView((android.view.View)(_et.getObject()),_left,_top,_width,__c.DipToCurrent((int) (60)));
 BA.debugLineNum = 33;BA.debugLine="Parent.AddView(lv, Left, Top + et.Height, Width, Height - et.Height)";
Debug.ShouldStop(1);
_parent.AddView((android.view.View)(_lv.getObject()),_left,(int) (_top+_et.getHeight()),_width,(int) (_height-_et.getHeight()));
 BA.debugLineNum = 34;BA.debugLine="End Sub";
Debug.ShouldStop(2);
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
 //BA.debugLineNum = 3;BA.debugLine="Sub Class_Globals";
 //BA.debugLineNum = 4;BA.debugLine="Private prefixList As Map";
_prefixlist = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 5;BA.debugLine="Private substringList As Map";
_substringlist = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 6;BA.debugLine="Private et As EditText";
_et = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 7;BA.debugLine="Private lv As ListView";
_lv = new anywheresoftware.b4a.objects.ListViewWrapper();
 //BA.debugLineNum = 8;BA.debugLine="Private MIN_LIMIT, MAX_LIMIT As Int";
_min_limit = 0;
_max_limit = 0;
 //BA.debugLineNum = 9;BA.debugLine="MIN_LIMIT = 1";
_min_limit = (int) (1);
 //BA.debugLineNum = 10;BA.debugLine="MAX_LIMIT = 4 'doesn't limit the words length. Only the index.";
_max_limit = (int) (4);
 //BA.debugLineNum = 11;BA.debugLine="Private mCallback As Object";
_mcallback = new Object();
 //BA.debugLineNum = 12;BA.debugLine="Private mEventName As String";
_meventname = "";
 //BA.debugLineNum = 13;BA.debugLine="End Sub";
return "";
}
public String  _et_textchanged(String _old,String _new) throws Exception{
		Debug.PushSubsStack("et_TextChanged (searchview) ","searchview",15,ba,this);
try {
String _str1 = "";
String _str2 = "";
Debug.locals.put("Old", _old);
Debug.locals.put("New", _new);
 BA.debugLineNum = 47;BA.debugLine="Private Sub et_TextChanged (Old As String, New As String)";
Debug.ShouldStop(16384);
 BA.debugLineNum = 48;BA.debugLine="lv.Clear";
Debug.ShouldStop(32768);
_lv.Clear();
 BA.debugLineNum = 49;BA.debugLine="If lv.Visible = False Then lv.Visible = True";
Debug.ShouldStop(65536);
if (_lv.getVisible()==__c.False) { 
_lv.setVisible(__c.True);};
 BA.debugLineNum = 50;BA.debugLine="If New.Length < MIN_LIMIT Then Return";
Debug.ShouldStop(131072);
if (_new.length()<_min_limit) { 
if (true) return "";};
 BA.debugLineNum = 51;BA.debugLine="Dim str1, str2 As String";
Debug.ShouldStop(262144);
_str1 = "";Debug.locals.put("str1", _str1);
_str2 = "";Debug.locals.put("str2", _str2);
 BA.debugLineNum = 52;BA.debugLine="str1 = New.ToLowerCase";
Debug.ShouldStop(524288);
_str1 = _new.toLowerCase();Debug.locals.put("str1", _str1);
 BA.debugLineNum = 53;BA.debugLine="If str1.Length > MAX_LIMIT Then";
Debug.ShouldStop(1048576);
if (_str1.length()>_max_limit) { 
 BA.debugLineNum = 54;BA.debugLine="str2 = str1.SubString2(0, MAX_LIMIT)";
Debug.ShouldStop(2097152);
_str2 = _str1.substring((int) (0),_max_limit);Debug.locals.put("str2", _str2);
 }else {
 BA.debugLineNum = 56;BA.debugLine="str2 = str1";
Debug.ShouldStop(8388608);
_str2 = _str1;Debug.locals.put("str2", _str2);
 };
 BA.debugLineNum = 58;BA.debugLine="AddItemsToList(prefixList.Get(str2), str1)";
Debug.ShouldStop(33554432);
_additemstolist((anywheresoftware.b4a.objects.collections.List) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.List(), (java.util.List)(_prefixlist.Get((Object)(_str2)))),_str1);
 BA.debugLineNum = 59;BA.debugLine="AddItemsToList(substringList.Get(str2), str1)";
Debug.ShouldStop(67108864);
_additemstolist((anywheresoftware.b4a.objects.collections.List) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.List(), (java.util.List)(_substringlist.Get((Object)(_str2)))),_str1);
 BA.debugLineNum = 60;BA.debugLine="End Sub";
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
public String  _initialize(anywheresoftware.b4a.BA _ba,Object _callback,String _eventname) throws Exception{
innerInitialize(_ba);
		Debug.PushSubsStack("Initialize (searchview) ","searchview",15,ba,this);
try {
Debug.locals.put("ba", _ba);
Debug.locals.put("Callback", _callback);
Debug.locals.put("EventName", _eventname);
 BA.debugLineNum = 16;BA.debugLine="Public Sub Initialize (Callback As Object, EventName As String)";
Debug.ShouldStop(32768);
 BA.debugLineNum = 17;BA.debugLine="et.Initialize(\"et\")";
Debug.ShouldStop(65536);
_et.Initialize(ba,"et");
 BA.debugLineNum = 19;BA.debugLine="et.InputType = Bit.OR(et.INPUT_TYPE_TEXT, 0x00080000)";
Debug.ShouldStop(262144);
_et.setInputType(__c.Bit.Or(_et.INPUT_TYPE_TEXT,(int) (0x00080000)));
 BA.debugLineNum = 20;BA.debugLine="lv.Initialize(\"lv\")";
Debug.ShouldStop(524288);
_lv.Initialize(ba,"lv");
 BA.debugLineNum = 21;BA.debugLine="lv.SingleLineLayout.ItemHeight = 50dip";
Debug.ShouldStop(1048576);
_lv.getSingleLineLayout().setItemHeight(__c.DipToCurrent((int) (50)));
 BA.debugLineNum = 22;BA.debugLine="lv.SingleLineLayout.Label.TextSize = 14";
Debug.ShouldStop(2097152);
_lv.getSingleLineLayout().Label.setTextSize((float) (14));
 BA.debugLineNum = 23;BA.debugLine="lv.Visible = False";
Debug.ShouldStop(4194304);
_lv.setVisible(__c.False);
 BA.debugLineNum = 24;BA.debugLine="prefixList.Initialize";
Debug.ShouldStop(8388608);
_prefixlist.Initialize();
 BA.debugLineNum = 25;BA.debugLine="substringList.Initialize";
Debug.ShouldStop(16777216);
_substringlist.Initialize();
 BA.debugLineNum = 26;BA.debugLine="mCallback = Callback";
Debug.ShouldStop(33554432);
_mcallback = _callback;
 BA.debugLineNum = 27;BA.debugLine="mEventName = EventName";
Debug.ShouldStop(67108864);
_meventname = _eventname;
 BA.debugLineNum = 28;BA.debugLine="End Sub";
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
public String  _lv_itemclick(int _position,Object _value) throws Exception{
		Debug.PushSubsStack("lv_ItemClick (searchview) ","searchview",15,ba,this);
try {
anywheresoftware.b4a.objects.IME _ime = null;
Debug.locals.put("Position", _position);
Debug.locals.put("Value", _value);
 BA.debugLineNum = 36;BA.debugLine="Private Sub lv_ItemClick (Position As Int, Value As Object)";
Debug.ShouldStop(8);
 BA.debugLineNum = 37;BA.debugLine="et.Text = Value";
Debug.ShouldStop(16);
_et.setText(_value);
 BA.debugLineNum = 38;BA.debugLine="et.SelectionStart = et.Text.Length";
Debug.ShouldStop(32);
_et.setSelectionStart(_et.getText().length());
 BA.debugLineNum = 39;BA.debugLine="Dim IME As IME";
Debug.ShouldStop(64);
_ime = new anywheresoftware.b4a.objects.IME();Debug.locals.put("IME", _ime);
 BA.debugLineNum = 40;BA.debugLine="IME.HideKeyboard";
Debug.ShouldStop(128);
_ime.HideKeyboard(ba);
 BA.debugLineNum = 41;BA.debugLine="lv.Visible = False";
Debug.ShouldStop(256);
_lv.setVisible(__c.False);
 BA.debugLineNum = 42;BA.debugLine="If SubExists(mCallback, mEventName & \"_ItemClick\") Then";
Debug.ShouldStop(512);
if (__c.SubExists(ba,_mcallback,_meventname+"_ItemClick")) { 
 BA.debugLineNum = 43;BA.debugLine="CallSub2(mCallback, mEventName & \"_ItemClick\", Value)";
Debug.ShouldStop(1024);
__c.CallSubNew2(ba,_mcallback,_meventname+"_ItemClick",_value);
 };
 BA.debugLineNum = 45;BA.debugLine="End Sub";
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
public String  _setindex(Object _index) throws Exception{
		Debug.PushSubsStack("SetIndex (searchview) ","searchview",15,ba,this);
try {
Object[] _obj = null;
Debug.locals.put("Index", _index);
 BA.debugLineNum = 118;BA.debugLine="Public Sub SetIndex(Index As Object)";
Debug.ShouldStop(2097152);
 BA.debugLineNum = 119;BA.debugLine="Dim obj() As Object";
Debug.ShouldStop(4194304);
_obj = new Object[(int) (0)];
{
int d0 = _obj.length;
for (int i0 = 0;i0 < d0;i0++) {
_obj[i0] = new Object();
}
}
;Debug.locals.put("obj", _obj);
 BA.debugLineNum = 120;BA.debugLine="obj = Index";
Debug.ShouldStop(8388608);
_obj = (Object[])(_index);Debug.locals.put("obj", _obj);
 BA.debugLineNum = 121;BA.debugLine="prefixList = obj(0)";
Debug.ShouldStop(16777216);
_prefixlist.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(_obj[(int) (0)]));
 BA.debugLineNum = 122;BA.debugLine="substringList = obj(1)";
Debug.ShouldStop(33554432);
_substringlist.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(_obj[(int) (1)]));
 BA.debugLineNum = 123;BA.debugLine="End Sub";
Debug.ShouldStop(67108864);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public Object  _setitems(anywheresoftware.b4a.objects.collections.List _items) throws Exception{
		Debug.PushSubsStack("SetItems (searchview) ","searchview",15,ba,this);
try {
long _starttime = 0L;
anywheresoftware.b4a.objects.collections.Map _noduplicates = null;
anywheresoftware.b4a.objects.collections.Map _m = null;
anywheresoftware.b4a.objects.collections.List _li = null;
int _i = 0;
String _item = "";
int _start = 0;
int _count = 0;
String _str = "";
Debug.locals.put("Items", _items);
 BA.debugLineNum = 76;BA.debugLine="Public Sub SetItems(Items As List) As Object";
Debug.ShouldStop(2048);
 BA.debugLineNum = 77;BA.debugLine="Dim startTime As Long";
Debug.ShouldStop(4096);
_starttime = 0L;Debug.locals.put("startTime", _starttime);
 BA.debugLineNum = 78;BA.debugLine="startTime = DateTime.Now";
Debug.ShouldStop(8192);
_starttime = __c.DateTime.getNow();Debug.locals.put("startTime", _starttime);
 BA.debugLineNum = 79;BA.debugLine="ProgressDialogShow2(\"Building index...\", False)";
Debug.ShouldStop(16384);
__c.ProgressDialogShow2(ba,"Building index...",__c.False);
 BA.debugLineNum = 80;BA.debugLine="Dim noDuplicates As Map";
Debug.ShouldStop(32768);
_noduplicates = new anywheresoftware.b4a.objects.collections.Map();Debug.locals.put("noDuplicates", _noduplicates);
 BA.debugLineNum = 81;BA.debugLine="noDuplicates.Initialize";
Debug.ShouldStop(65536);
_noduplicates.Initialize();
 BA.debugLineNum = 82;BA.debugLine="prefixList.Clear";
Debug.ShouldStop(131072);
_prefixlist.Clear();
 BA.debugLineNum = 83;BA.debugLine="substringList.Clear";
Debug.ShouldStop(262144);
_substringlist.Clear();
 BA.debugLineNum = 84;BA.debugLine="Dim m As Map";
Debug.ShouldStop(524288);
_m = new anywheresoftware.b4a.objects.collections.Map();Debug.locals.put("m", _m);
 BA.debugLineNum = 85;BA.debugLine="Dim li As List";
Debug.ShouldStop(1048576);
_li = new anywheresoftware.b4a.objects.collections.List();Debug.locals.put("li", _li);
 BA.debugLineNum = 86;BA.debugLine="For i = 0 To Items.Size - 1";
Debug.ShouldStop(2097152);
{
final int step72 = 1;
final int limit72 = (int) (_items.getSize()-1);
for (_i = (int) (0); (step72 > 0 && _i <= limit72) || (step72 < 0 && _i >= limit72); _i = ((int)(0 + _i + step72))) {
Debug.locals.put("i", _i);
 BA.debugLineNum = 87;BA.debugLine="If i Mod 100 = 0 Then DoEvents";
Debug.ShouldStop(4194304);
if (_i%100==0) { 
__c.DoEvents();};
 BA.debugLineNum = 88;BA.debugLine="Dim item As String";
Debug.ShouldStop(8388608);
_item = "";Debug.locals.put("item", _item);
 BA.debugLineNum = 89;BA.debugLine="item = Items.Get(i)";
Debug.ShouldStop(16777216);
_item = BA.ObjectToString(_items.Get(_i));Debug.locals.put("item", _item);
 BA.debugLineNum = 90;BA.debugLine="item = item.ToLowerCase";
Debug.ShouldStop(33554432);
_item = _item.toLowerCase();Debug.locals.put("item", _item);
 BA.debugLineNum = 91;BA.debugLine="noDuplicates.Clear";
Debug.ShouldStop(67108864);
_noduplicates.Clear();
 BA.debugLineNum = 92;BA.debugLine="For start = 0 To item.Length";
Debug.ShouldStop(134217728);
{
final int step78 = 1;
final int limit78 = _item.length();
for (_start = (int) (0); (step78 > 0 && _start <= limit78) || (step78 < 0 && _start >= limit78); _start = ((int)(0 + _start + step78))) {
Debug.locals.put("start", _start);
 BA.debugLineNum = 93;BA.debugLine="Dim count As Int";
Debug.ShouldStop(268435456);
_count = 0;Debug.locals.put("count", _count);
 BA.debugLineNum = 94;BA.debugLine="count = MIN_LIMIT";
Debug.ShouldStop(536870912);
_count = _min_limit;Debug.locals.put("count", _count);
 BA.debugLineNum = 95;BA.debugLine="Do While count <= MAX_LIMIT AND start + count <= item.Length";
Debug.ShouldStop(1073741824);
while (_count<=_max_limit && _start+_count<=_item.length()) {
 BA.debugLineNum = 96;BA.debugLine="Dim str As String";
Debug.ShouldStop(-2147483648);
_str = "";Debug.locals.put("str", _str);
 BA.debugLineNum = 97;BA.debugLine="str = item.SubString2(start, start + count)";
Debug.ShouldStop(1);
_str = _item.substring(_start,(int) (_start+_count));Debug.locals.put("str", _str);
 BA.debugLineNum = 98;BA.debugLine="If noDuplicates.ContainsKey(str) = False Then";
Debug.ShouldStop(2);
if (_noduplicates.ContainsKey((Object)(_str))==__c.False) { 
 BA.debugLineNum = 99;BA.debugLine="noDuplicates.Put(str, \"\")";
Debug.ShouldStop(4);
_noduplicates.Put((Object)(_str),(Object)(""));
 BA.debugLineNum = 100;BA.debugLine="If start = 0 Then m = prefixList Else m = substringList";
Debug.ShouldStop(8);
if (_start==0) { 
_m = _prefixlist;Debug.locals.put("m", _m);}
else {
_m = _substringlist;Debug.locals.put("m", _m);};
 BA.debugLineNum = 101;BA.debugLine="li = m.Get(str)";
Debug.ShouldStop(16);
_li.setObject((java.util.List)(_m.Get((Object)(_str))));
 BA.debugLineNum = 102;BA.debugLine="If li.IsInitialized = False Then";
Debug.ShouldStop(32);
if (_li.IsInitialized()==__c.False) { 
 BA.debugLineNum = 103;BA.debugLine="li.Initialize";
Debug.ShouldStop(64);
_li.Initialize();
 BA.debugLineNum = 104;BA.debugLine="m.Put(str, li)";
Debug.ShouldStop(128);
_m.Put((Object)(_str),(Object)(_li.getObject()));
 };
 BA.debugLineNum = 106;BA.debugLine="li.Add(Items.Get(i)) 'Preserve the original case";
Debug.ShouldStop(512);
_li.Add(_items.Get(_i));
 };
 BA.debugLineNum = 108;BA.debugLine="count = count + 1";
Debug.ShouldStop(2048);
_count = (int) (_count+1);Debug.locals.put("count", _count);
 }
;
 }
}Debug.locals.put("start", _start);
;
 }
}Debug.locals.put("i", _i);
;
 BA.debugLineNum = 112;BA.debugLine="ProgressDialogHide";
Debug.ShouldStop(32768);
__c.ProgressDialogHide();
 BA.debugLineNum = 113;BA.debugLine="Log(\"Index time: \" & (DateTime.Now - startTime) & \" ms (\" & Items.Size & \" Items)\")";
Debug.ShouldStop(65536);
__c.Log("Index time: "+BA.NumberToString((__c.DateTime.getNow()-_starttime))+" ms ("+BA.NumberToString(_items.getSize())+" Items)");
 BA.debugLineNum = 114;BA.debugLine="Return Array As Object(prefixList, substringList)";
Debug.ShouldStop(131072);
if (true) return (Object)(new Object[]{(Object)(_prefixlist.getObject()),(Object)(_substringlist.getObject())});
 BA.debugLineNum = 115;BA.debugLine="End Sub";
Debug.ShouldStop(262144);
return null;
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
