package br.com.Fine;

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

public class utilitarios extends Activity implements B4AActivity{
	public static utilitarios mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "br.com.Fine", "br.com.Fine.utilitarios");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (utilitarios).");
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
		activityBA = new BA(this, layout, processBA, "br.com.Fine", "br.com.Fine.utilitarios");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "br.com.Fine.utilitarios", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (utilitarios) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (utilitarios) Resume **");
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
		return utilitarios.class;
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
        BA.LogInfo("** Activity (utilitarios) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            BA.LogInfo("** Activity (utilitarios) Resume **");
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
public anywheresoftware.b4a.objects.ButtonWrapper _button_calculadora = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button_graficos = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button_excluir = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button_voltar = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button_exportar = null;
public br.com.Fine.main _main = null;
public br.com.Fine.cadastro _cadastro = null;
public br.com.Fine.financeiro _financeiro = null;
public br.com.Fine.creditos _creditos = null;
public br.com.Fine.debitos _debitos = null;
public br.com.Fine.total _total = null;
public br.com.Fine.menu _menu = null;
public br.com.Fine.calculadora _calculadora = null;
public br.com.Fine.extrato _extrato = null;
public br.com.Fine.excluir _excluir = null;
public br.com.Fine.remover_categoria _remover_categoria = null;
public br.com.Fine.addcategoria _addcategoria = null;
public br.com.Fine.editar _editar = null;
public br.com.Fine.charts _charts = null;
public br.com.Fine.graficos _graficos = null;

public static void initializeProcessGlobals() {
             try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
}
public static String  _activity_create(boolean _firsttime) throws Exception{
 //BA.debugLineNum = 18;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 19;BA.debugLine="Activity.LoadLayout(\"Layout_Utilitarios\")";
mostCurrent._activity.LoadLayout("Layout_Utilitarios",mostCurrent.activityBA);
 //BA.debugLineNum = 20;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 26;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 28;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 22;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 24;BA.debugLine="End Sub";
return "";
}
public static String  _button_calculadora_click() throws Exception{
 //BA.debugLineNum = 30;BA.debugLine="Sub Button_Calculadora_Click";
 //BA.debugLineNum = 31;BA.debugLine="StartActivity(\"Calculadora\")";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)("Calculadora"));
 //BA.debugLineNum = 32;BA.debugLine="End Sub";
return "";
}
public static String  _button_excluir_click() throws Exception{
int _result = 0;
String _user = "";
 //BA.debugLineNum = 38;BA.debugLine="Sub Button_Excluir_Click";
 //BA.debugLineNum = 39;BA.debugLine="Dim result As Int";
_result = 0;
 //BA.debugLineNum = 40;BA.debugLine="Dim user As String";
_user = "";
 //BA.debugLineNum = 41;BA.debugLine="user = Main.Pers.Logado";
_user = mostCurrent._main._pers._logado();
 //BA.debugLineNum = 42;BA.debugLine="result = Msgbox2(\"Deseja excluir todos os dados de Transações e Categorias?\",\"Fine\",\"Sim, tenho certeza.\",\"Cancelar\",\"\",LoadBitmap(File.DirAssets,\"fineico.png\"))";
_result = anywheresoftware.b4a.keywords.Common.Msgbox2("Deseja excluir todos os dados de Transações e Categorias?","Fine","Sim, tenho certeza.","Cancelar","",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"fineico.png").getObject()),mostCurrent.activityBA);
 //BA.debugLineNum = 44;BA.debugLine="If result = DialogResponse.POSITIVE Then";
if (_result==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
 //BA.debugLineNum = 45;BA.debugLine="File.Delete(File.DirRootExternal&\"/Fine/Data\",user&\"saldo.txt\")";
anywheresoftware.b4a.keywords.Common.File.Delete(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/Fine/Data",_user+"saldo.txt");
 //BA.debugLineNum = 46;BA.debugLine="File.Delete(File.DirRootExternal&\"/Fine/Data\",user&\"categ.txt\")";
anywheresoftware.b4a.keywords.Common.File.Delete(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/Fine/Data",_user+"categ.txt");
 //BA.debugLineNum = 47;BA.debugLine="File.Delete(File.DirRootExternal&\"/Fine/Data\",user&\"transacoes.txt\")";
anywheresoftware.b4a.keywords.Common.File.Delete(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/Fine/Data",_user+"transacoes.txt");
 //BA.debugLineNum = 48;BA.debugLine="Msgbox2(\"Dados excluidos\",\"Fine\",\"Ok\",\"\",\"\",LoadBitmap(File.DirAssets,\"fineico.png\"))";
anywheresoftware.b4a.keywords.Common.Msgbox2("Dados excluidos","Fine","Ok","","",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"fineico.png").getObject()),mostCurrent.activityBA);
 };
 //BA.debugLineNum = 51;BA.debugLine="End Sub";
return "";
}
public static String  _button_exportar_click() throws Exception{
anywheresoftware.b4a.objects.StringUtils _str = null;
anywheresoftware.b4a.objects.collections.List _headers = null;
anywheresoftware.b4a.objects.collections.List _trans = null;
anywheresoftware.b4a.objects.collections.List _lista = null;
int _i = 0;
String _linha1 = "";
String _linha2 = "";
String _linha3 = "";
String _linha4 = "";
String[] _arr = null;
 //BA.debugLineNum = 57;BA.debugLine="Sub Button_Exportar_Click";
 //BA.debugLineNum = 58;BA.debugLine="Dim str As StringUtils";
_str = new anywheresoftware.b4a.objects.StringUtils();
 //BA.debugLineNum = 59;BA.debugLine="Dim headers,trans,lista As List";
_headers = new anywheresoftware.b4a.objects.collections.List();
_trans = new anywheresoftware.b4a.objects.collections.List();
_lista = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 60;BA.debugLine="headers.Initialize";
_headers.Initialize();
 //BA.debugLineNum = 61;BA.debugLine="trans.Initialize";
_trans.Initialize();
 //BA.debugLineNum = 62;BA.debugLine="lista.Initialize";
_lista.Initialize();
 //BA.debugLineNum = 63;BA.debugLine="headers.AddAll(Array As String(\"Valor\",\"Data\",\"Categoria\"))";
_headers.AddAll(anywheresoftware.b4a.keywords.Common.ArrayToList(new String[]{"Valor","Data","Categoria"}));
 //BA.debugLineNum = 64;BA.debugLine="trans = Main.Pers.GetTransacoes(Main.Pers.Logado)";
_trans = mostCurrent._main._pers._gettransacoes(mostCurrent._main._pers._logado());
 //BA.debugLineNum = 66;BA.debugLine="For i = 0 To trans.Size -1";
{
final int step45 = 1;
final int limit45 = (int) (_trans.getSize()-1);
for (_i = (int) (0); (step45 > 0 && _i <= limit45) || (step45 < 0 && _i >= limit45); _i = ((int)(0 + _i + step45))) {
 //BA.debugLineNum = 67;BA.debugLine="Dim linha1,linha2,linha3,linha4 As String";
_linha1 = "";
_linha2 = "";
_linha3 = "";
_linha4 = "";
 //BA.debugLineNum = 68;BA.debugLine="Dim arr(3) As String";
_arr = new String[(int) (3)];
java.util.Arrays.fill(_arr,"");
 //BA.debugLineNum = 69;BA.debugLine="linha1 = trans.Get(i)";
_linha1 = BA.ObjectToString(_trans.Get(_i));
 //BA.debugLineNum = 70;BA.debugLine="linha2 = linha1.SubString2(0,linha1.IndexOf(\";\"))";
_linha2 = _linha1.substring((int) (0),_linha1.indexOf(";"));
 //BA.debugLineNum = 71;BA.debugLine="linha3 = linha1.SubString2(linha1.IndexOf(\";\")+1,linha1.LastIndexOf(\";\"))";
_linha3 = _linha1.substring((int) (_linha1.indexOf(";")+1),_linha1.lastIndexOf(";"));
 //BA.debugLineNum = 72;BA.debugLine="linha4 = linha1.SubString(linha1.LastIndexOf(\";\")+1)";
_linha4 = _linha1.substring((int) (_linha1.lastIndexOf(";")+1));
 //BA.debugLineNum = 73;BA.debugLine="arr(0) = linha2";
_arr[(int) (0)] = _linha2;
 //BA.debugLineNum = 74;BA.debugLine="arr(1) = linha3";
_arr[(int) (1)] = _linha3;
 //BA.debugLineNum = 75;BA.debugLine="arr(2) = linha4";
_arr[(int) (2)] = _linha4;
 //BA.debugLineNum = 76;BA.debugLine="lista.add(arr)";
_lista.Add((Object)(_arr));
 }
};
 //BA.debugLineNum = 79;BA.debugLine="If Not(File.Exists(File.DirRootExternal,\"Fine\")) Then";
if (anywheresoftware.b4a.keywords.Common.Not(anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),"Fine"))) { 
 //BA.debugLineNum = 80;BA.debugLine="File.MakeDir(File.DirRootExternal,\"Fine\")";
anywheresoftware.b4a.keywords.Common.File.MakeDir(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),"Fine");
 //BA.debugLineNum = 81;BA.debugLine="str.SaveCSV2(File.DirRootExternal&\"/Fine\",\"Transacoes.csv\",\",\",lista,headers)";
_str.SaveCSV2(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/Fine","Transacoes.csv",BA.ObjectToChar(","),_lista,_headers);
 //BA.debugLineNum = 82;BA.debugLine="Msgbox2(\"Dados exportados com sucesso!\",\"Fine\",\"Ok\",\"\",\"\",LoadBitmap(File.DirAssets,\"fineico.png\"))";
anywheresoftware.b4a.keywords.Common.Msgbox2("Dados exportados com sucesso!","Fine","Ok","","",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"fineico.png").getObject()),mostCurrent.activityBA);
 }else {
 //BA.debugLineNum = 84;BA.debugLine="str.SaveCSV2(File.DirRootExternal&\"/Fine\",\"Transacoes.csv\",\",\",lista,headers)";
_str.SaveCSV2(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/Fine","Transacoes.csv",BA.ObjectToChar(","),_lista,_headers);
 //BA.debugLineNum = 85;BA.debugLine="Msgbox2(\"Dados exportados com sucesso!\",\"Fine\",\"Ok\",\"\",\"\",LoadBitmap(File.DirAssets,\"fineico.png\"))";
anywheresoftware.b4a.keywords.Common.Msgbox2("Dados exportados com sucesso!","Fine","Ok","","",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"fineico.png").getObject()),mostCurrent.activityBA);
 };
 //BA.debugLineNum = 88;BA.debugLine="End Sub";
return "";
}
public static String  _button_graficos_click() throws Exception{
 //BA.debugLineNum = 34;BA.debugLine="Sub Button_Graficos_Click";
 //BA.debugLineNum = 35;BA.debugLine="StartActivity(\"Graficos\")";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)("Graficos"));
 //BA.debugLineNum = 36;BA.debugLine="End Sub";
return "";
}
public static String  _button_voltar_click() throws Exception{
 //BA.debugLineNum = 53;BA.debugLine="Sub Button_Voltar_Click";
 //BA.debugLineNum = 54;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 55;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 10;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 11;BA.debugLine="Dim Button_Calculadora As Button";
mostCurrent._button_calculadora = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 12;BA.debugLine="Dim Button_Graficos As Button";
mostCurrent._button_graficos = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 13;BA.debugLine="Dim Button_Excluir As Button";
mostCurrent._button_excluir = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 14;BA.debugLine="Dim Button_Voltar As Button";
mostCurrent._button_voltar = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 15;BA.debugLine="Private Button_Exportar As Button";
mostCurrent._button_exportar = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 16;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 8;BA.debugLine="End Sub";
return "";
}
}
