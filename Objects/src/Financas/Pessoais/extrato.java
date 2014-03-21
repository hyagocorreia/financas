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
public anywheresoftware.b4a.objects.ListViewWrapper _listview_extrato = null;
public anywheresoftware.b4a.objects.LabelWrapper _label_titulo = null;
public anywheresoftware.b4a.objects.LabelWrapper _label_saldoatual = null;
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
		return new Object[] {"Activity",mostCurrent._activity,"AddCategoria",Debug.moduleToString(Financas.Pessoais.addcategoria.class),"Button_Voltar",mostCurrent._button_voltar,"Cadastro",Debug.moduleToString(Financas.Pessoais.cadastro.class),"Calculadora",Debug.moduleToString(Financas.Pessoais.calculadora.class),"Creditos",Debug.moduleToString(Financas.Pessoais.creditos.class),"Debitos",Debug.moduleToString(Financas.Pessoais.debitos.class),"Excluir",Debug.moduleToString(Financas.Pessoais.excluir.class),"Financeiro",Debug.moduleToString(Financas.Pessoais.financeiro.class),"Label_SaldoAtual",mostCurrent._label_saldoatual,"Label_Titulo",mostCurrent._label_titulo,"Lista",Debug.moduleToString(Financas.Pessoais.lista.class),"ListView_Extrato",mostCurrent._listview_extrato,"Main",Debug.moduleToString(Financas.Pessoais.main.class),"Menu",Debug.moduleToString(Financas.Pessoais.menu.class),"Remover_Categoria",Debug.moduleToString(Financas.Pessoais.remover_categoria.class),"Total",Debug.moduleToString(Financas.Pessoais.total.class),"Utilitarios",Debug.moduleToString(Financas.Pessoais.utilitarios.class)};
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
Debug.locals.put("FirstTime", _firsttime);
 BA.debugLineNum = 17;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
Debug.ShouldStop(65536);
 BA.debugLineNum = 18;BA.debugLine="Activity.LoadLayout(\"Layout_Extrato\")";
Debug.ShouldStop(131072);
mostCurrent._activity.LoadLayout("Layout_Extrato",mostCurrent.activityBA);
 BA.debugLineNum = 19;BA.debugLine="Label_SaldoAtual.Text = \"Saldo Atual: \" & NumberFormat(Main.Pers.Saldo,1,2)";
Debug.ShouldStop(262144);
mostCurrent._label_saldoatual.setText((Object)("Saldo Atual: "+anywheresoftware.b4a.keywords.Common.NumberFormat(mostCurrent._main._pers._saldo,(int) (1),(int) (2))));
 BA.debugLineNum = 20;BA.debugLine="Label_Titulo.Text = \"Transações\"";
Debug.ShouldStop(524288);
mostCurrent._label_titulo.setText((Object)("Transações"));
 BA.debugLineNum = 22;BA.debugLine="For i = 0 To Main.Pers.Lista_Extrato.Size -1";
Debug.ShouldStop(2097152);
{
final int step12 = 1;
final int limit12 = (int) (mostCurrent._main._pers._lista_extrato.getSize()-1);
for (_i = (int) (0); (step12 > 0 && _i <= limit12) || (step12 < 0 && _i >= limit12); _i = ((int)(0 + _i + step12))) {
Debug.locals.put("i", _i);
 BA.debugLineNum = 23;BA.debugLine="Dim linha1,linha2,linha3,linha4 As String";
Debug.ShouldStop(4194304);
_linha1 = "";Debug.locals.put("linha1", _linha1);
_linha2 = "";Debug.locals.put("linha2", _linha2);
_linha3 = "";Debug.locals.put("linha3", _linha3);
_linha4 = "";Debug.locals.put("linha4", _linha4);
 BA.debugLineNum = 24;BA.debugLine="linha1 = Main.Pers.Lista_Extrato.Get(i)";
Debug.ShouldStop(8388608);
_linha1 = BA.ObjectToString(mostCurrent._main._pers._lista_extrato.Get(_i));Debug.locals.put("linha1", _linha1);
 BA.debugLineNum = 25;BA.debugLine="linha2 = linha1.SubString2(0,linha1.IndexOf(\"|\"))";
Debug.ShouldStop(16777216);
_linha2 = _linha1.substring((int) (0),_linha1.indexOf("|"));Debug.locals.put("linha2", _linha2);
 BA.debugLineNum = 26;BA.debugLine="linha3 = linha1.SubString2(linha1.IndexOf(\"|\")+1,linha1.LastIndexOf(\"|\"))";
Debug.ShouldStop(33554432);
_linha3 = _linha1.substring((int) (_linha1.indexOf("|")+1),_linha1.lastIndexOf("|"));Debug.locals.put("linha3", _linha3);
 BA.debugLineNum = 27;BA.debugLine="linha4 = linha1.SubString(linha1.LastIndexOf(\"|\")+1)";
Debug.ShouldStop(67108864);
_linha4 = _linha1.substring((int) (_linha1.lastIndexOf("|")+1));Debug.locals.put("linha4", _linha4);
 BA.debugLineNum = 28;BA.debugLine="Dim valor As Double";
Debug.ShouldStop(134217728);
_valor = 0;Debug.locals.put("valor", _valor);
 BA.debugLineNum = 29;BA.debugLine="valor = linha2";
Debug.ShouldStop(268435456);
_valor = (double)(Double.parseDouble(_linha2));Debug.locals.put("valor", _valor);
 BA.debugLineNum = 30;BA.debugLine="If valor < 0 Then";
Debug.ShouldStop(536870912);
if (_valor<0) { 
 BA.debugLineNum = 31;BA.debugLine="ListView_Extrato.AddTwoLinesAndBitmap(\"R$\"&NumberFormat((valor*(-1)),1,2), linha3 & \" - \" & linha4,LoadBitmap(File.DirAssets,\"debito.png\"))";
Debug.ShouldStop(1073741824);
mostCurrent._listview_extrato.AddTwoLinesAndBitmap("R$"+anywheresoftware.b4a.keywords.Common.NumberFormat((_valor*(-1)),(int) (1),(int) (2)),_linha3+" - "+_linha4,(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"debito.png").getObject()));
 }else {
 BA.debugLineNum = 33;BA.debugLine="ListView_Extrato.AddTwoLinesAndBitmap(\"R$\"&NumberFormat(linha2,1,2), linha3 & \" - \" & linha4,LoadBitmap(File.DirAssets,\"credito.png\"))";
Debug.ShouldStop(1);
mostCurrent._listview_extrato.AddTwoLinesAndBitmap("R$"+anywheresoftware.b4a.keywords.Common.NumberFormat((double)(Double.parseDouble(_linha2)),(int) (1),(int) (2)),_linha3+" - "+_linha4,(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"credito.png").getObject()));
 };
 BA.debugLineNum = 35;BA.debugLine="ListView_Extrato.FastScrollEnabled = True";
Debug.ShouldStop(4);
mostCurrent._listview_extrato.setFastScrollEnabled(anywheresoftware.b4a.keywords.Common.True);
 }
}Debug.locals.put("i", _i);
;
 BA.debugLineNum = 37;BA.debugLine="End Sub";
Debug.ShouldStop(16);
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
 BA.debugLineNum = 43;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
Debug.ShouldStop(1024);
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
public static String  _activity_resume() throws Exception{
		Debug.PushSubsStack("Activity_Resume (extrato) ","extrato",10,mostCurrent.activityBA,mostCurrent);
try {
 BA.debugLineNum = 39;BA.debugLine="Sub Activity_Resume";
Debug.ShouldStop(64);
 BA.debugLineNum = 41;BA.debugLine="End Sub";
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
public static String  _button_voltar_click() throws Exception{
		Debug.PushSubsStack("Button_Voltar_Click (extrato) ","extrato",10,mostCurrent.activityBA,mostCurrent);
try {
 BA.debugLineNum = 48;BA.debugLine="Sub Button_Voltar_Click";
Debug.ShouldStop(32768);
 BA.debugLineNum = 49;BA.debugLine="Activity.Finish";
Debug.ShouldStop(65536);
mostCurrent._activity.Finish();
 BA.debugLineNum = 50;BA.debugLine="End Sub";
Debug.ShouldStop(131072);
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
 //BA.debugLineNum = 12;BA.debugLine="Dim ListView_Extrato As ListView";
mostCurrent._listview_extrato = new anywheresoftware.b4a.objects.ListViewWrapper();
 //BA.debugLineNum = 13;BA.debugLine="Private Label_Titulo As Label";
mostCurrent._label_titulo = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 14;BA.debugLine="Private Label_SaldoAtual As Label";
mostCurrent._label_saldoatual = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 15;BA.debugLine="End Sub";
return "";
}
public static String  _listview_extrato_itemlongclick(int _position,Object _value) throws Exception{
		Debug.PushSubsStack("ListView_Extrato_ItemLongClick (extrato) ","extrato",10,mostCurrent.activityBA,mostCurrent);
try {
Debug.locals.put("Position", _position);
Debug.locals.put("Value", _value);
 BA.debugLineNum = 52;BA.debugLine="Sub ListView_Extrato_ItemLongClick (Position As Int, Value As Object)";
Debug.ShouldStop(524288);
 BA.debugLineNum = 53;BA.debugLine="If Msgbox2(\"Deseja excluir a transação?\", \"Excluir\", \"Sim\", \"\", \"Não\", Null) = DialogResponse.POSITIVE Then";
Debug.ShouldStop(1048576);
if (anywheresoftware.b4a.keywords.Common.Msgbox2("Deseja excluir a transação?","Excluir","Sim","","Não",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA)==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
 BA.debugLineNum = 54;BA.debugLine="Main.Pers.Remover_Transacao(Position)";
Debug.ShouldStop(2097152);
mostCurrent._main._pers._remover_transacao(_position);
 BA.debugLineNum = 55;BA.debugLine="ListView_Extrato.RemoveAt(Position)";
Debug.ShouldStop(4194304);
mostCurrent._listview_extrato.RemoveAt(_position);
 BA.debugLineNum = 56;BA.debugLine="Label_SaldoAtual.Text = Main.Pers.Saldo";
Debug.ShouldStop(8388608);
mostCurrent._label_saldoatual.setText((Object)(mostCurrent._main._pers._saldo));
 };
 BA.debugLineNum = 58;BA.debugLine="End Sub";
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
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 8;BA.debugLine="End Sub";
return "";
}
}
