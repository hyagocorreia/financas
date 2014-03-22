package Financas.Pessoais;

import anywheresoftware.b4a.B4AMenuItem;
import android.app.Activity;
import android.os.Bundle;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.B4AActivity;
import anywheresoftware.b4a.ObjectWrapper;
import anywheresoftware.b4a.objects.ActivityWrapper;
import java.lang.reflect.InvocationTargetException;
import anywheresoftware.b4a.B4AUncaughtException;
import anywheresoftware.b4a.debug.*;
import java.lang.ref.WeakReference;

public class extrato extends Activity implements B4AActivity{
	public static extrato mostCurrent;
	static boolean afterFirstLayout;
	static boolean isFirst = true;
    private static boolean processGlobalsRun = false;
	BALayout layout;
	public static BA processBA;
	BA activityBA;
    ActivityWrapper _activity;
    java.util.ArrayList<B4AMenuItem> menuItems;
	public static final boolean fullScreen = false;
	public static final boolean includeTitle = true;
    public static WeakReference<Activity> previousOne;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (isFirst) {
			processBA = new BA(this.getApplicationContext(), null, null, "Financas.Pessoais", "Financas.Pessoais.extrato");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (extrato).");
				p.finish();
			}
		}
		if (!includeTitle) {
        	this.getWindow().requestFeature(android.view.Window.FEATURE_NO_TITLE);
        }
        if (fullScreen) {
        	getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN,   
        			android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
		mostCurrent = this;
        processBA.sharedProcessBA.activityBA = null;
		layout = new BALayout(this);
		setContentView(layout);
		afterFirstLayout = false;
		BA.handler.postDelayed(new WaitForLayout(), 5);

	}
	private static class WaitForLayout implements Runnable {
		public void run() {
			if (afterFirstLayout)
				return;
			if (mostCurrent == null)
				return;
            
			if (mostCurrent.layout.getWidth() == 0) {
				BA.handler.postDelayed(this, 5);
				return;
			}
			mostCurrent.layout.getLayoutParams().height = mostCurrent.layout.getHeight();
			mostCurrent.layout.getLayoutParams().width = mostCurrent.layout.getWidth();
			afterFirstLayout = true;
			mostCurrent.afterFirstLayout();
		}
	}
	private void afterFirstLayout() {
        if (this != mostCurrent)
			return;
		activityBA = new BA(this, layout, processBA, "Financas.Pessoais", "Financas.Pessoais.extrato");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "Financas.Pessoais.extrato", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (extrato) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (extrato) Resume **");
        processBA.raiseEvent(null, "activity_resume");
        if (android.os.Build.VERSION.SDK_INT >= 11) {
			try {
				android.app.Activity.class.getMethod("invalidateOptionsMenu").invoke(this,(Object[]) null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	public void addMenuItem(B4AMenuItem item) {
		if (menuItems == null)
			menuItems = new java.util.ArrayList<B4AMenuItem>();
		menuItems.add(item);
	}
	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		super.onCreateOptionsMenu(menu);
		if (menuItems == null)
			return false;
		for (B4AMenuItem bmi : menuItems) {
			android.view.MenuItem mi = menu.add(bmi.title);
			if (bmi.drawable != null)
				mi.setIcon(bmi.drawable);
            if (android.os.Build.VERSION.SDK_INT >= 11) {
				try {
                    if (bmi.addToBar) {
				        android.view.MenuItem.class.getMethod("setShowAsAction", int.class).invoke(mi, 1);
                    }
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			mi.setOnMenuItemClickListener(new B4AMenuItemsClickListener(bmi.eventName.toLowerCase(BA.cul)));
		}
		return true;
	}
    public void onWindowFocusChanged(boolean hasFocus) {
       super.onWindowFocusChanged(hasFocus);
       if (processBA.subExists("activity_windowfocuschanged"))
           processBA.raiseEvent2(null, true, "activity_windowfocuschanged", false, hasFocus);
    }
	private class B4AMenuItemsClickListener implements android.view.MenuItem.OnMenuItemClickListener {
		private final String eventName;
		public B4AMenuItemsClickListener(String eventName) {
			this.eventName = eventName;
		}
		public boolean onMenuItemClick(android.view.MenuItem item) {
			processBA.raiseEvent(item.getTitle(), eventName + "_click");
			return true;
		}
	}
    public static Class<?> getObject() {
		return extrato.class;
	}
    private Boolean onKeySubExist = null;
    private Boolean onKeyUpSubExist = null;
	@Override
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		if (onKeySubExist == null)
			onKeySubExist = processBA.subExists("activity_keypress");
		if (onKeySubExist) {
			if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK &&
					android.os.Build.VERSION.SDK_INT >= 18) {
				HandleKeyDelayed hk = new HandleKeyDelayed();
				hk.kc = keyCode;
				BA.handler.post(hk);
				return true;
			}
			else {
				boolean res = new HandleKeyDelayed().runDirectly(keyCode);
				if (res)
					return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	private class HandleKeyDelayed implements Runnable {
		int kc;
		public void run() {
			runDirectly(kc);
		}
		public boolean runDirectly(int keyCode) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keypress", false, keyCode);
			if (res == null || res == true) {
                return true;
            }
            else if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK) {
				finish();
				return true;
			}
            return false;
		}
		
	}
    @Override
	public boolean onKeyUp(int keyCode, android.view.KeyEvent event) {
		if (onKeyUpSubExist == null)
			onKeyUpSubExist = processBA.subExists("activity_keyup");
		if (onKeyUpSubExist) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keyup", false, keyCode);
			if (res == null || res == true)
				return true;
		}
		return super.onKeyUp(keyCode, event);
	}
	@Override
	public void onNewIntent(android.content.Intent intent) {
		this.setIntent(intent);
	}
    @Override 
	public void onPause() {
		super.onPause();
        if (_activity == null) //workaround for emulator bug (Issue 2423)
            return;
		anywheresoftware.b4a.Msgbox.dismiss(true);
        BA.LogInfo("** Activity (extrato) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        processBA.raiseEvent2(_activity, true, "activity_pause", false, activityBA.activity.isFinishing());		
        processBA.setActivityPaused(true);
        mostCurrent = null;
        if (!activityBA.activity.isFinishing())
			previousOne = new WeakReference<Activity>(this);
        anywheresoftware.b4a.Msgbox.isDismissing = false;
	}

	@Override
	public void onDestroy() {
        super.onDestroy();
		previousOne = null;
	}
    @Override 
	public void onResume() {
		super.onResume();
        mostCurrent = this;
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (activityBA != null) { //will be null during activity create (which waits for AfterLayout).
        	ResumeMessage rm = new ResumeMessage(mostCurrent);
        	BA.handler.post(rm);
        }
	}
    private static class ResumeMessage implements Runnable {
    	private final WeakReference<Activity> activity;
    	public ResumeMessage(Activity activity) {
    		this.activity = new WeakReference<Activity>(activity);
    	}
		public void run() {
			if (mostCurrent == null || mostCurrent != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (extrato) Resume **");
		    processBA.raiseEvent(mostCurrent._activity, "activity_resume", (Object[])null);
		}
    }
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
	      android.content.Intent data) {
		processBA.onActivityResult(requestCode, resultCode, data);
	}
	private static void initializeGlobals() {
		processBA.raiseEvent2(null, true, "globals", false, (Object[])null);
	}

public anywheresoftware.b4a.keywords.Common __c = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button_voltar = null;
public anywheresoftware.b4a.objects.ListViewWrapper _listview_extrato1 = null;
public anywheresoftware.b4a.objects.ListViewWrapper _listview_extrato2 = null;
public anywheresoftware.b4a.objects.ListViewWrapper _listview_extrato3 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label_saldoatual = null;
public anywheresoftware.b4a.objects.TabHostWrapper _tabhost_transacoes = null;
public anywheresoftware.b4a.objects.SpinnerWrapper _spinner_categorias = null;
public anywheresoftware.b4a.objects.SpinnerWrapper _spinner_meses = null;
public anywheresoftware.b4a.objects.LabelWrapper _label_saldo_mes = null;
public Financas.Pessoais.main _main = null;
public Financas.Pessoais.cadastro _cadastro = null;
public Financas.Pessoais.financeiro _financeiro = null;
public Financas.Pessoais.creditos _creditos = null;
public Financas.Pessoais.debitos _debitos = null;
public Financas.Pessoais.total _total = null;
public Financas.Pessoais.utilitarios _utilitarios = null;
public Financas.Pessoais.menu _menu = null;
public Financas.Pessoais.calculadora _calculadora = null;
public Financas.Pessoais.excluir _excluir = null;
public Financas.Pessoais.addcategoria _addcategoria = null;
public Financas.Pessoais.remover_categoria _remover_categoria = null;
public Financas.Pessoais.lista _lista = null;
  public Object[] GetGlobals() {
		return new Object[] {"Activity",mostCurrent._activity,"AddCategoria",Debug.moduleToString(Financas.Pessoais.addcategoria.class),"Button_Voltar",mostCurrent._button_voltar,"Cadastro",Debug.moduleToString(Financas.Pessoais.cadastro.class),"Calculadora",Debug.moduleToString(Financas.Pessoais.calculadora.class),"Creditos",Debug.moduleToString(Financas.Pessoais.creditos.class),"Debitos",Debug.moduleToString(Financas.Pessoais.debitos.class),"Excluir",Debug.moduleToString(Financas.Pessoais.excluir.class),"Financeiro",Debug.moduleToString(Financas.Pessoais.financeiro.class),"Label_Saldo_Mes",mostCurrent._label_saldo_mes,"Label_SaldoAtual",mostCurrent._label_saldoatual,"Lista",Debug.moduleToString(Financas.Pessoais.lista.class),"ListView_Extrato1",mostCurrent._listview_extrato1,"ListView_Extrato2",mostCurrent._listview_extrato2,"ListView_Extrato3",mostCurrent._listview_extrato3,"Main",Debug.moduleToString(Financas.Pessoais.main.class),"Menu",Debug.moduleToString(Financas.Pessoais.menu.class),"Remover_Categoria",Debug.moduleToString(Financas.Pessoais.remover_categoria.class),"Spinner_Categorias",mostCurrent._spinner_categorias,"Spinner_Meses",mostCurrent._spinner_meses,"TabHost_Transacoes",mostCurrent._tabhost_transacoes,"Total",Debug.moduleToString(Financas.Pessoais.total.class),"Utilitarios",Debug.moduleToString(Financas.Pessoais.utilitarios.class)};
}

public static void initializeProcessGlobals() {
             try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
}
public static String  _activity_create(boolean _firsttime) throws Exception{
		Debug.PushSubsStack("Activity_Create (extrato) ","extrato",10,mostCurrent.activityBA,mostCurrent);
try {
int _i = 0;
String _linha1 = "";
String _linha2 = "";
String _linha3 = "";
String _linha4 = "";
double _valor = 0;
anywheresoftware.b4a.objects.collections.List _listinha = null;
Debug.locals.put("FirstTime", _firsttime);
 BA.debugLineNum = 22;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
Debug.ShouldStop(2097152);
 BA.debugLineNum = 23;BA.debugLine="Activity.LoadLayout(\"Layout_Extrato\")";
Debug.ShouldStop(4194304);
mostCurrent._activity.LoadLayout("Layout_Extrato",mostCurrent.activityBA);
 BA.debugLineNum = 25;BA.debugLine="Spinner_Categorias.AddAll(Lista.Lista_Categorias)";
Debug.ShouldStop(16777216);
mostCurrent._spinner_categorias.AddAll(mostCurrent._lista._lista_categorias);
 BA.debugLineNum = 26;BA.debugLine="Label_SaldoAtual.Text = \"Saldo Atual (R$): \" & NumberFormat(Main.Pers.Saldo,1,2)";
Debug.ShouldStop(33554432);
mostCurrent._label_saldoatual.setText((Object)("Saldo Atual (R$): "+anywheresoftware.b4a.keywords.Common.NumberFormat(mostCurrent._main._pers._saldo,(int) (1),(int) (2))));
 BA.debugLineNum = 28;BA.debugLine="For i = 0 To Main.Pers.Lista_Extrato.Size -1";
Debug.ShouldStop(134217728);
{
final int step17 = 1;
final int limit17 = (int) (mostCurrent._main._pers._lista_extrato.getSize()-1);
for (_i = (int) (0); (step17 > 0 && _i <= limit17) || (step17 < 0 && _i >= limit17); _i = ((int)(0 + _i + step17))) {
Debug.locals.put("i", _i);
 BA.debugLineNum = 29;BA.debugLine="Dim linha1,linha2,linha3,linha4 As String";
Debug.ShouldStop(268435456);
_linha1 = "";Debug.locals.put("linha1", _linha1);
_linha2 = "";Debug.locals.put("linha2", _linha2);
_linha3 = "";Debug.locals.put("linha3", _linha3);
_linha4 = "";Debug.locals.put("linha4", _linha4);
 BA.debugLineNum = 30;BA.debugLine="linha1 = Main.Pers.Lista_Extrato.Get(i)";
Debug.ShouldStop(536870912);
_linha1 = BA.ObjectToString(mostCurrent._main._pers._lista_extrato.Get(_i));Debug.locals.put("linha1", _linha1);
 BA.debugLineNum = 31;BA.debugLine="linha2 = linha1.SubString2(0,linha1.IndexOf(\"|\"))";
Debug.ShouldStop(1073741824);
_linha2 = _linha1.substring((int) (0),_linha1.indexOf("|"));Debug.locals.put("linha2", _linha2);
 BA.debugLineNum = 32;BA.debugLine="linha3 = linha1.SubString2(linha1.IndexOf(\"|\")+1,linha1.LastIndexOf(\"|\"))";
Debug.ShouldStop(-2147483648);
_linha3 = _linha1.substring((int) (_linha1.indexOf("|")+1),_linha1.lastIndexOf("|"));Debug.locals.put("linha3", _linha3);
 BA.debugLineNum = 33;BA.debugLine="linha4 = linha1.SubString(linha1.LastIndexOf(\"|\")+1)";
Debug.ShouldStop(1);
_linha4 = _linha1.substring((int) (_linha1.lastIndexOf("|")+1));Debug.locals.put("linha4", _linha4);
 BA.debugLineNum = 34;BA.debugLine="Dim valor As Double";
Debug.ShouldStop(2);
_valor = 0;Debug.locals.put("valor", _valor);
 BA.debugLineNum = 35;BA.debugLine="valor = linha2";
Debug.ShouldStop(4);
_valor = (double)(Double.parseDouble(_linha2));Debug.locals.put("valor", _valor);
 BA.debugLineNum = 36;BA.debugLine="If valor < 0 Then";
Debug.ShouldStop(8);
if (_valor<0) { 
 BA.debugLineNum = 37;BA.debugLine="ListView_Extrato1.AddTwoLinesAndBitmap(\"R$\"&NumberFormat((valor*(-1)),1,2), linha3 & \" - \" & linha4,LoadBitmap(File.DirAssets,\"debito.png\"))";
Debug.ShouldStop(16);
mostCurrent._listview_extrato1.AddTwoLinesAndBitmap("R$"+anywheresoftware.b4a.keywords.Common.NumberFormat((_valor*(-1)),(int) (1),(int) (2)),_linha3+" - "+_linha4,(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"debito.png").getObject()));
 }else {
 BA.debugLineNum = 39;BA.debugLine="ListView_Extrato1.AddTwoLinesAndBitmap(\"R$\"&NumberFormat(linha2,1,2), linha3 & \" - \" & linha4,LoadBitmap(File.DirAssets,\"credito.png\"))";
Debug.ShouldStop(64);
mostCurrent._listview_extrato1.AddTwoLinesAndBitmap("R$"+anywheresoftware.b4a.keywords.Common.NumberFormat((double)(Double.parseDouble(_linha2)),(int) (1),(int) (2)),_linha3+" - "+_linha4,(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"credito.png").getObject()));
 };
 BA.debugLineNum = 41;BA.debugLine="ListView_Extrato1.FastScrollEnabled = True";
Debug.ShouldStop(256);
mostCurrent._listview_extrato1.setFastScrollEnabled(anywheresoftware.b4a.keywords.Common.True);
 }
}Debug.locals.put("i", _i);
;
 BA.debugLineNum = 44;BA.debugLine="Dim listinha As List";
Debug.ShouldStop(2048);
_listinha = new anywheresoftware.b4a.objects.collections.List();Debug.locals.put("listinha", _listinha);
 BA.debugLineNum = 45;BA.debugLine="listinha.Initialize";
Debug.ShouldStop(4096);
_listinha.Initialize();
 BA.debugLineNum = 46;BA.debugLine="listinha.AddAll(Array As String(\"Selecione o mês\",\"Janeiro\",\"Fevereiro\",\"Março\",\"Abril\",\"Maio\",\"Junho\",\"Julho\",\"Agosto\",\"Setembro\",\"Outubro\",\"Novembro\",\"Dezembro\"))";
Debug.ShouldStop(8192);
_listinha.AddAll(anywheresoftware.b4a.keywords.Common.ArrayToList(new String[]{"Selecione o mês","Janeiro","Fevereiro","Março","Abril","Maio","Junho","Julho","Agosto","Setembro","Outubro","Novembro","Dezembro"}));
 BA.debugLineNum = 47;BA.debugLine="Spinner_Meses.AddAll(listinha)";
Debug.ShouldStop(16384);
mostCurrent._spinner_meses.AddAll(_listinha);
 BA.debugLineNum = 48;BA.debugLine="TabHost_Transacoes.SetBackgroundImage(LoadBitmap(File.DirAssets,\"iconest.png\"))";
Debug.ShouldStop(32768);
mostCurrent._tabhost_transacoes.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"iconest.png").getObject()));
 BA.debugLineNum = 49;BA.debugLine="TabHost_Transacoes.AddTab2(\"\",ListView_Extrato1)";
Debug.ShouldStop(65536);
mostCurrent._tabhost_transacoes.AddTab2("",(android.view.View)(mostCurrent._listview_extrato1.getObject()));
 BA.debugLineNum = 50;BA.debugLine="TabHost_Transacoes.AddTab2(\"\",Spinner_Meses)";
Debug.ShouldStop(131072);
mostCurrent._tabhost_transacoes.AddTab2("",(android.view.View)(mostCurrent._spinner_meses.getObject()));
 BA.debugLineNum = 51;BA.debugLine="TabHost_Transacoes.AddTab2(\"\",Spinner_Categorias)";
Debug.ShouldStop(262144);
mostCurrent._tabhost_transacoes.AddTab2("",(android.view.View)(mostCurrent._spinner_categorias.getObject()));
 BA.debugLineNum = 53;BA.debugLine="End Sub";
Debug.ShouldStop(1048576);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _activity_pause(boolean _userclosed) throws Exception{
		Debug.PushSubsStack("Activity_Pause (extrato) ","extrato",10,mostCurrent.activityBA,mostCurrent);
try {
Debug.locals.put("UserClosed", _userclosed);
 BA.debugLineNum = 59;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
Debug.ShouldStop(67108864);
 BA.debugLineNum = 61;BA.debugLine="End Sub";
Debug.ShouldStop(268435456);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _activity_resume() throws Exception{
		Debug.PushSubsStack("Activity_Resume (extrato) ","extrato",10,mostCurrent.activityBA,mostCurrent);
try {
 BA.debugLineNum = 55;BA.debugLine="Sub Activity_Resume";
Debug.ShouldStop(4194304);
 BA.debugLineNum = 57;BA.debugLine="End Sub";
Debug.ShouldStop(16777216);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _button_voltar_click() throws Exception{
		Debug.PushSubsStack("Button_Voltar_Click (extrato) ","extrato",10,mostCurrent.activityBA,mostCurrent);
try {
 BA.debugLineNum = 63;BA.debugLine="Sub Button_Voltar_Click";
Debug.ShouldStop(1073741824);
 BA.debugLineNum = 64;BA.debugLine="Activity.Finish";
Debug.ShouldStop(-2147483648);
mostCurrent._activity.Finish();
 BA.debugLineNum = 65;BA.debugLine="End Sub";
Debug.ShouldStop(1);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 10;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 11;BA.debugLine="Dim Button_Voltar As Button";
mostCurrent._button_voltar = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 12;BA.debugLine="Dim ListView_Extrato1 As ListView";
mostCurrent._listview_extrato1 = new anywheresoftware.b4a.objects.ListViewWrapper();
 //BA.debugLineNum = 13;BA.debugLine="Dim ListView_Extrato2 As ListView";
mostCurrent._listview_extrato2 = new anywheresoftware.b4a.objects.ListViewWrapper();
 //BA.debugLineNum = 14;BA.debugLine="Dim ListView_Extrato3 As ListView";
mostCurrent._listview_extrato3 = new anywheresoftware.b4a.objects.ListViewWrapper();
 //BA.debugLineNum = 15;BA.debugLine="Private Label_SaldoAtual As Label";
mostCurrent._label_saldoatual = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 16;BA.debugLine="Private TabHost_Transacoes As TabHost";
mostCurrent._tabhost_transacoes = new anywheresoftware.b4a.objects.TabHostWrapper();
 //BA.debugLineNum = 17;BA.debugLine="Private Spinner_Categorias As Spinner";
mostCurrent._spinner_categorias = new anywheresoftware.b4a.objects.SpinnerWrapper();
 //BA.debugLineNum = 18;BA.debugLine="Private Spinner_Meses As Spinner";
mostCurrent._spinner_meses = new anywheresoftware.b4a.objects.SpinnerWrapper();
 //BA.debugLineNum = 19;BA.debugLine="Private Label_Saldo_Mes As Label";
mostCurrent._label_saldo_mes = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 20;BA.debugLine="End Sub";
return "";
}
public static String  _listview_extrato1_itemlongclick(int _position,Object _value) throws Exception{
		Debug.PushSubsStack("ListView_Extrato1_ItemLongClick (extrato) ","extrato",10,mostCurrent.activityBA,mostCurrent);
try {
Debug.locals.put("Position", _position);
Debug.locals.put("Value", _value);
 BA.debugLineNum = 67;BA.debugLine="Sub ListView_Extrato1_ItemLongClick (Position As Int, Value As Object)";
Debug.ShouldStop(4);
 BA.debugLineNum = 68;BA.debugLine="If Msgbox2(\"Deseja excluir a transação?\", \"Excluir\", \"Sim\", \"\", \"Não\", Null) = DialogResponse.POSITIVE Then";
Debug.ShouldStop(8);
if (anywheresoftware.b4a.keywords.Common.Msgbox2("Deseja excluir a transação?","Excluir","Sim","","Não",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA)==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
 BA.debugLineNum = 69;BA.debugLine="Main.Pers.Remover_Transacao(Position)";
Debug.ShouldStop(16);
mostCurrent._main._pers._remover_transacao(_position);
 BA.debugLineNum = 70;BA.debugLine="ListView_Extrato1.RemoveAt(Position)";
Debug.ShouldStop(32);
mostCurrent._listview_extrato1.RemoveAt(_position);
 BA.debugLineNum = 71;BA.debugLine="Label_SaldoAtual.Text = Main.Pers.Saldo";
Debug.ShouldStop(64);
mostCurrent._label_saldoatual.setText((Object)(mostCurrent._main._pers._saldo));
 };
 BA.debugLineNum = 73;BA.debugLine="End Sub";
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
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 8;BA.debugLine="End Sub";
return "";
}
public static String  _spinner_categorias_itemclick(int _position,Object _value) throws Exception{
		Debug.PushSubsStack("Spinner_Categorias_ItemClick (extrato) ","extrato",10,mostCurrent.activityBA,mostCurrent);
try {
String _linha1 = "";
String _linha2 = "";
String _linha3 = "";
String _linha4 = "";
String _l4 = "";
String _str = "";
int _i = 0;
double _valor = 0;
Debug.locals.put("Position", _position);
Debug.locals.put("Value", _value);
 BA.debugLineNum = 105;BA.debugLine="Sub Spinner_Categorias_ItemClick (Position As Int, Value As Object)";
Debug.ShouldStop(256);
 BA.debugLineNum = 106;BA.debugLine="Dim linha1,linha2,linha3,linha4,l4 As String";
Debug.ShouldStop(512);
_linha1 = "";Debug.locals.put("linha1", _linha1);
_linha2 = "";Debug.locals.put("linha2", _linha2);
_linha3 = "";Debug.locals.put("linha3", _linha3);
_linha4 = "";Debug.locals.put("linha4", _linha4);
_l4 = "";Debug.locals.put("l4", _l4);
 BA.debugLineNum = 107;BA.debugLine="Dim Str As String = Spinner_Categorias.GetItem(Position)";
Debug.ShouldStop(1024);
_str = mostCurrent._spinner_categorias.GetItem(_position);Debug.locals.put("Str", _str);Debug.locals.put("Str", _str);
 BA.debugLineNum = 108;BA.debugLine="l4 = Str.SubString(Str.LastIndexOf(\"|\")+1)";
Debug.ShouldStop(2048);
_l4 = _str.substring((int) (_str.lastIndexOf("|")+1));Debug.locals.put("l4", _l4);
 BA.debugLineNum = 110;BA.debugLine="ListView_Extrato3.Clear";
Debug.ShouldStop(8192);
mostCurrent._listview_extrato3.Clear();
 BA.debugLineNum = 112;BA.debugLine="For i = 0 To Main.Pers.Lista_Extrato.Size -1";
Debug.ShouldStop(32768);
{
final int step86 = 1;
final int limit86 = (int) (mostCurrent._main._pers._lista_extrato.getSize()-1);
for (_i = (int) (0); (step86 > 0 && _i <= limit86) || (step86 < 0 && _i >= limit86); _i = ((int)(0 + _i + step86))) {
Debug.locals.put("i", _i);
 BA.debugLineNum = 113;BA.debugLine="linha1 = Main.Pers.Lista_Extrato.Get(i)";
Debug.ShouldStop(65536);
_linha1 = BA.ObjectToString(mostCurrent._main._pers._lista_extrato.Get(_i));Debug.locals.put("linha1", _linha1);
 BA.debugLineNum = 114;BA.debugLine="linha2 = linha1.SubString2(0,linha1.IndexOf(\"|\"))";
Debug.ShouldStop(131072);
_linha2 = _linha1.substring((int) (0),_linha1.indexOf("|"));Debug.locals.put("linha2", _linha2);
 BA.debugLineNum = 115;BA.debugLine="linha3 = linha1.SubString2(linha1.IndexOf(\"|\")+1,linha1.LastIndexOf(\"|\"))";
Debug.ShouldStop(262144);
_linha3 = _linha1.substring((int) (_linha1.indexOf("|")+1),_linha1.lastIndexOf("|"));Debug.locals.put("linha3", _linha3);
 BA.debugLineNum = 116;BA.debugLine="linha4 = linha1.SubString(linha1.LastIndexOf(\"|\")+1)";
Debug.ShouldStop(524288);
_linha4 = _linha1.substring((int) (_linha1.lastIndexOf("|")+1));Debug.locals.put("linha4", _linha4);
 BA.debugLineNum = 117;BA.debugLine="If linha4 = l4 Then";
Debug.ShouldStop(1048576);
if ((_linha4).equals(_l4)) { 
 BA.debugLineNum = 118;BA.debugLine="Dim valor As Double";
Debug.ShouldStop(2097152);
_valor = 0;Debug.locals.put("valor", _valor);
 BA.debugLineNum = 119;BA.debugLine="valor = linha2";
Debug.ShouldStop(4194304);
_valor = (double)(Double.parseDouble(_linha2));Debug.locals.put("valor", _valor);
 BA.debugLineNum = 120;BA.debugLine="If valor < 0 Then";
Debug.ShouldStop(8388608);
if (_valor<0) { 
 BA.debugLineNum = 121;BA.debugLine="ListView_Extrato3.AddTwoLinesAndBitmap(\"R$\"&NumberFormat((valor*(-1)),1,2), linha3 & \" - \" & linha4,LoadBitmap(File.DirAssets,\"debito.png\"))";
Debug.ShouldStop(16777216);
mostCurrent._listview_extrato3.AddTwoLinesAndBitmap("R$"+anywheresoftware.b4a.keywords.Common.NumberFormat((_valor*(-1)),(int) (1),(int) (2)),_linha3+" - "+_linha4,(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"debito.png").getObject()));
 }else {
 BA.debugLineNum = 123;BA.debugLine="ListView_Extrato3.AddTwoLinesAndBitmap(\"R$\"&NumberFormat(linha2,1,2), linha3 & \" - \" & linha4,LoadBitmap(File.DirAssets,\"credito.png\"))";
Debug.ShouldStop(67108864);
mostCurrent._listview_extrato3.AddTwoLinesAndBitmap("R$"+anywheresoftware.b4a.keywords.Common.NumberFormat((double)(Double.parseDouble(_linha2)),(int) (1),(int) (2)),_linha3+" - "+_linha4,(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"credito.png").getObject()));
 };
 BA.debugLineNum = 126;BA.debugLine="ListView_Extrato3.FastScrollEnabled = True";
Debug.ShouldStop(536870912);
mostCurrent._listview_extrato3.setFastScrollEnabled(anywheresoftware.b4a.keywords.Common.True);
 BA.debugLineNum = 127;BA.debugLine="ListView_Extrato3.Visible = True";
Debug.ShouldStop(1073741824);
mostCurrent._listview_extrato3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 }
}Debug.locals.put("i", _i);
;
 BA.debugLineNum = 130;BA.debugLine="End Sub";
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
public static String  _spinner_meses_itemclick(int _position,Object _value) throws Exception{
		Debug.PushSubsStack("Spinner_Meses_ItemClick (extrato) ","extrato",10,mostCurrent.activityBA,mostCurrent);
try {
String _linha1 = "";
String _linha2 = "";
String _linha3 = "";
String _linha4 = "";
String _mes_linha = "";
int _i = 0;
int _mes_ = 0;
double _valor = 0;
Debug.locals.put("Position", _position);
Debug.locals.put("Value", _value);
 BA.debugLineNum = 75;BA.debugLine="Sub Spinner_Meses_ItemClick (Position As Int, Value As Object)";
Debug.ShouldStop(1024);
 BA.debugLineNum = 76;BA.debugLine="Dim linha1,linha2,linha3,linha4 As String";
Debug.ShouldStop(2048);
_linha1 = "";Debug.locals.put("linha1", _linha1);
_linha2 = "";Debug.locals.put("linha2", _linha2);
_linha3 = "";Debug.locals.put("linha3", _linha3);
_linha4 = "";Debug.locals.put("linha4", _linha4);
 BA.debugLineNum = 77;BA.debugLine="Dim mes_linha As String";
Debug.ShouldStop(4096);
_mes_linha = "";Debug.locals.put("mes_linha", _mes_linha);
 BA.debugLineNum = 79;BA.debugLine="ListView_Extrato2.Clear";
Debug.ShouldStop(16384);
mostCurrent._listview_extrato2.Clear();
 BA.debugLineNum = 81;BA.debugLine="For i = 0 To Main.Pers.Lista_Extrato.Size -1";
Debug.ShouldStop(65536);
{
final int step59 = 1;
final int limit59 = (int) (mostCurrent._main._pers._lista_extrato.getSize()-1);
for (_i = (int) (0); (step59 > 0 && _i <= limit59) || (step59 < 0 && _i >= limit59); _i = ((int)(0 + _i + step59))) {
Debug.locals.put("i", _i);
 BA.debugLineNum = 82;BA.debugLine="linha1 = Main.Pers.Lista_Extrato.Get(i)";
Debug.ShouldStop(131072);
_linha1 = BA.ObjectToString(mostCurrent._main._pers._lista_extrato.Get(_i));Debug.locals.put("linha1", _linha1);
 BA.debugLineNum = 83;BA.debugLine="linha2 = linha1.SubString2(0,linha1.IndexOf(\"|\"))";
Debug.ShouldStop(262144);
_linha2 = _linha1.substring((int) (0),_linha1.indexOf("|"));Debug.locals.put("linha2", _linha2);
 BA.debugLineNum = 84;BA.debugLine="linha3 = linha1.SubString2(linha1.IndexOf(\"|\")+1,linha1.LastIndexOf(\"|\"))";
Debug.ShouldStop(524288);
_linha3 = _linha1.substring((int) (_linha1.indexOf("|")+1),_linha1.lastIndexOf("|"));Debug.locals.put("linha3", _linha3);
 BA.debugLineNum = 85;BA.debugLine="linha4 = linha1.SubString(linha1.LastIndexOf(\"|\")+1)";
Debug.ShouldStop(1048576);
_linha4 = _linha1.substring((int) (_linha1.lastIndexOf("|")+1));Debug.locals.put("linha4", _linha4);
 BA.debugLineNum = 86;BA.debugLine="mes_linha = linha1.SubString2(linha1.IndexOf(\"/\")+1,linha1.LastIndexOf(\"/\"))";
Debug.ShouldStop(2097152);
_mes_linha = _linha1.substring((int) (_linha1.indexOf("/")+1),_linha1.lastIndexOf("/"));Debug.locals.put("mes_linha", _mes_linha);
 BA.debugLineNum = 87;BA.debugLine="Dim mes_ As Int = mes_linha";
Debug.ShouldStop(4194304);
_mes_ = (int)(Double.parseDouble(_mes_linha));Debug.locals.put("mes_", _mes_);Debug.locals.put("mes_", _mes_);
 BA.debugLineNum = 88;BA.debugLine="If mes_ = Position Then";
Debug.ShouldStop(8388608);
if (_mes_==_position) { 
 BA.debugLineNum = 89;BA.debugLine="Dim valor As Double";
Debug.ShouldStop(16777216);
_valor = 0;Debug.locals.put("valor", _valor);
 BA.debugLineNum = 90;BA.debugLine="valor = linha2";
Debug.ShouldStop(33554432);
_valor = (double)(Double.parseDouble(_linha2));Debug.locals.put("valor", _valor);
 BA.debugLineNum = 91;BA.debugLine="If valor < 0 Then";
Debug.ShouldStop(67108864);
if (_valor<0) { 
 BA.debugLineNum = 92;BA.debugLine="ListView_Extrato2.AddTwoLinesAndBitmap(\"R$\"&NumberFormat((valor*(-1)),1,2), linha3 & \" - \" & linha4,LoadBitmap(File.DirAssets,\"debito.png\"))";
Debug.ShouldStop(134217728);
mostCurrent._listview_extrato2.AddTwoLinesAndBitmap("R$"+anywheresoftware.b4a.keywords.Common.NumberFormat((_valor*(-1)),(int) (1),(int) (2)),_linha3+" - "+_linha4,(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"debito.png").getObject()));
 }else {
 BA.debugLineNum = 94;BA.debugLine="ListView_Extrato2.AddTwoLinesAndBitmap(\"R$\"&NumberFormat(linha2,1,2), linha3 & \" - \" & linha4,LoadBitmap(File.DirAssets,\"credito.png\"))";
Debug.ShouldStop(536870912);
mostCurrent._listview_extrato2.AddTwoLinesAndBitmap("R$"+anywheresoftware.b4a.keywords.Common.NumberFormat((double)(Double.parseDouble(_linha2)),(int) (1),(int) (2)),_linha3+" - "+_linha4,(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"credito.png").getObject()));
 };
 BA.debugLineNum = 97;BA.debugLine="Label_Saldo_Mes.Text = \"Saldo do Mês (R$): \"& NumberFormat(valor,1,2)";
Debug.ShouldStop(1);
mostCurrent._label_saldo_mes.setText((Object)("Saldo do Mês (R$): "+anywheresoftware.b4a.keywords.Common.NumberFormat(_valor,(int) (1),(int) (2))));
 BA.debugLineNum = 98;BA.debugLine="Label_Saldo_Mes.Visible = True";
Debug.ShouldStop(2);
mostCurrent._label_saldo_mes.setVisible(anywheresoftware.b4a.keywords.Common.True);
 BA.debugLineNum = 99;BA.debugLine="ListView_Extrato2.FastScrollEnabled = True";
Debug.ShouldStop(4);
mostCurrent._listview_extrato2.setFastScrollEnabled(anywheresoftware.b4a.keywords.Common.True);
 BA.debugLineNum = 100;BA.debugLine="ListView_Extrato2.Visible = True";
Debug.ShouldStop(8);
mostCurrent._listview_extrato2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 }
}Debug.locals.put("i", _i);
;
 BA.debugLineNum = 103;BA.debugLine="End Sub";
Debug.ShouldStop(64);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _tabhost_transacoes_tabchanged() throws Exception{
		Debug.PushSubsStack("TabHost_Transacoes_TabChanged (extrato) ","extrato",10,mostCurrent.activityBA,mostCurrent);
try {
 BA.debugLineNum = 132;BA.debugLine="Sub TabHost_Transacoes_TabChanged";
Debug.ShouldStop(8);
 BA.debugLineNum = 133;BA.debugLine="If TabHost_Transacoes.CurrentTab = 0 Then";
Debug.ShouldStop(16);
if (mostCurrent._tabhost_transacoes.getCurrentTab()==0) { 
 BA.debugLineNum = 134;BA.debugLine="ListView_Extrato3.Visible = False";
Debug.ShouldStop(32);
mostCurrent._listview_extrato3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 BA.debugLineNum = 135;BA.debugLine="ListView_Extrato2.Visible = False";
Debug.ShouldStop(64);
mostCurrent._listview_extrato2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 BA.debugLineNum = 136;BA.debugLine="Label_Saldo_Mes.Visible = False";
Debug.ShouldStop(128);
mostCurrent._label_saldo_mes.setVisible(anywheresoftware.b4a.keywords.Common.False);
 BA.debugLineNum = 137;BA.debugLine="Label_SaldoAtual.Visible = True";
Debug.ShouldStop(256);
mostCurrent._label_saldoatual.setVisible(anywheresoftware.b4a.keywords.Common.True);
 }else 
{ BA.debugLineNum = 138;BA.debugLine="Else If TabHost_Transacoes.CurrentTab = 1 Then";
Debug.ShouldStop(512);
if (mostCurrent._tabhost_transacoes.getCurrentTab()==1) { 
 BA.debugLineNum = 139;BA.debugLine="ListView_Extrato3.Visible = False";
Debug.ShouldStop(1024);
mostCurrent._listview_extrato3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 BA.debugLineNum = 140;BA.debugLine="ListView_Extrato1.Visible = False";
Debug.ShouldStop(2048);
mostCurrent._listview_extrato1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 BA.debugLineNum = 141;BA.debugLine="Label_SaldoAtual.Visible = False";
Debug.ShouldStop(4096);
mostCurrent._label_saldoatual.setVisible(anywheresoftware.b4a.keywords.Common.False);
 BA.debugLineNum = 142;BA.debugLine="Label_Saldo_Mes.Visible = True";
Debug.ShouldStop(8192);
mostCurrent._label_saldo_mes.setVisible(anywheresoftware.b4a.keywords.Common.True);
 }else 
{ BA.debugLineNum = 143;BA.debugLine="Else If TabHost_Transacoes.CurrentTab = 2 Then";
Debug.ShouldStop(16384);
if (mostCurrent._tabhost_transacoes.getCurrentTab()==2) { 
 BA.debugLineNum = 144;BA.debugLine="ListView_Extrato2.Visible = False";
Debug.ShouldStop(32768);
mostCurrent._listview_extrato2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 BA.debugLineNum = 145;BA.debugLine="ListView_Extrato1.Visible = False";
Debug.ShouldStop(65536);
mostCurrent._listview_extrato1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 BA.debugLineNum = 146;BA.debugLine="Label_Saldo_Mes.Visible = False";
Debug.ShouldStop(131072);
mostCurrent._label_saldo_mes.setVisible(anywheresoftware.b4a.keywords.Common.False);
 }}};
 BA.debugLineNum = 148;BA.debugLine="End Sub";
Debug.ShouldStop(524288);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
}
