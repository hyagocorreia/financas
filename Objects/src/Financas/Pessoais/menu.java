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

public class menu extends Activity implements B4AActivity{
	public static menu mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "Financas.Pessoais", "Financas.Pessoais.menu");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (menu).");
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
		activityBA = new BA(this, layout, processBA, "Financas.Pessoais", "Financas.Pessoais.menu");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "Financas.Pessoais.menu", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (menu) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (menu) Resume **");
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
		return menu.class;
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
        BA.LogInfo("** Activity (menu) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            BA.LogInfo("** Activity (menu) Resume **");
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
public anywheresoftware.b4a.objects.ButtonWrapper _button_financeiro = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button_sair = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button_utilitarios = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button_excluirconta = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button_logout = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button_editarconta = null;
public Financas.Pessoais.main _main = null;
public Financas.Pessoais.cadastro _cadastro = null;
public Financas.Pessoais.financeiro _financeiro = null;
public Financas.Pessoais.creditos _creditos = null;
public Financas.Pessoais.debitos _debitos = null;
public Financas.Pessoais.total _total = null;
public Financas.Pessoais.utilitarios _utilitarios = null;
public Financas.Pessoais.calculadora _calculadora = null;
public Financas.Pessoais.extrato _extrato = null;
public Financas.Pessoais.excluir _excluir = null;
public Financas.Pessoais.addcategoria _addcategoria = null;
public Financas.Pessoais.remover_categoria _remover_categoria = null;
public Financas.Pessoais.lista _lista = null;
public Financas.Pessoais.editar _editar = null;
  public Object[] GetGlobals() {
		return new Object[] {"Activity",mostCurrent._activity,"AddCategoria",Debug.moduleToString(Financas.Pessoais.addcategoria.class),"Button_EditarConta",mostCurrent._button_editarconta,"Button_ExcluirConta",mostCurrent._button_excluirconta,"Button_Financeiro",mostCurrent._button_financeiro,"Button_Logout",mostCurrent._button_logout,"Button_sair",mostCurrent._button_sair,"Button_Utilitarios",mostCurrent._button_utilitarios,"Cadastro",Debug.moduleToString(Financas.Pessoais.cadastro.class),"Calculadora",Debug.moduleToString(Financas.Pessoais.calculadora.class),"Creditos",Debug.moduleToString(Financas.Pessoais.creditos.class),"Debitos",Debug.moduleToString(Financas.Pessoais.debitos.class),"Editar",Debug.moduleToString(Financas.Pessoais.editar.class),"Excluir",Debug.moduleToString(Financas.Pessoais.excluir.class),"Extrato",Debug.moduleToString(Financas.Pessoais.extrato.class),"Financeiro",Debug.moduleToString(Financas.Pessoais.financeiro.class),"Lista",Debug.moduleToString(Financas.Pessoais.lista.class),"Main",Debug.moduleToString(Financas.Pessoais.main.class),"Remover_Categoria",Debug.moduleToString(Financas.Pessoais.remover_categoria.class),"Total",Debug.moduleToString(Financas.Pessoais.total.class),"Utilitarios",Debug.moduleToString(Financas.Pessoais.utilitarios.class)};
}

public static void initializeProcessGlobals() {
             try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
}
public static String  _activity_create(boolean _firsttime) throws Exception{
		Debug.PushSubsStack("Activity_Create (menu) ","menu",7,mostCurrent.activityBA,mostCurrent);
try {
Debug.locals.put("FirstTime", _firsttime);
 BA.debugLineNum = 19;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
Debug.ShouldStop(262144);
 BA.debugLineNum = 20;BA.debugLine="Activity.LoadLayout(\"Layout_Menu\")";
Debug.ShouldStop(524288);
mostCurrent._activity.LoadLayout("Layout_Menu",mostCurrent.activityBA);
 BA.debugLineNum = 22;BA.debugLine="If Lista.Lista_Categorias.Size = 0 Then";
Debug.ShouldStop(2097152);
if (mostCurrent._lista._lista_categorias.getSize()==0) { 
 BA.debugLineNum = 23;BA.debugLine="Lista.Lista_Categorias.Add(\"Selecione Categoria\")";
Debug.ShouldStop(4194304);
mostCurrent._lista._lista_categorias.Add((Object)("Selecione Categoria"));
 BA.debugLineNum = 24;BA.debugLine="Lista.Lista_Categorias.Add(\"Água\")";
Debug.ShouldStop(8388608);
mostCurrent._lista._lista_categorias.Add((Object)("Água"));
 BA.debugLineNum = 25;BA.debugLine="Lista.Lista_Categorias.Add(\"Gás\")";
Debug.ShouldStop(16777216);
mostCurrent._lista._lista_categorias.Add((Object)("Gás"));
 BA.debugLineNum = 26;BA.debugLine="Lista.Lista_Categorias.Add(\"Luz\")";
Debug.ShouldStop(33554432);
mostCurrent._lista._lista_categorias.Add((Object)("Luz"));
 BA.debugLineNum = 27;BA.debugLine="Lista.Lista_Categorias.Add(\"Combustível\")";
Debug.ShouldStop(67108864);
mostCurrent._lista._lista_categorias.Add((Object)("Combustível"));
 BA.debugLineNum = 28;BA.debugLine="Lista.Lista_Categorias.Add(\"Vestuário\")";
Debug.ShouldStop(134217728);
mostCurrent._lista._lista_categorias.Add((Object)("Vestuário"));
 BA.debugLineNum = 29;BA.debugLine="Lista.Lista_Categorias.Add(\"Alimentação\")";
Debug.ShouldStop(268435456);
mostCurrent._lista._lista_categorias.Add((Object)("Alimentação"));
 BA.debugLineNum = 30;BA.debugLine="Lista.Lista_Categorias.Add(\"Móveis\")";
Debug.ShouldStop(536870912);
mostCurrent._lista._lista_categorias.Add((Object)("Móveis"));
 BA.debugLineNum = 31;BA.debugLine="Lista.Lista_Categorias.Add(\"Construção\")";
Debug.ShouldStop(1073741824);
mostCurrent._lista._lista_categorias.Add((Object)("Construção"));
 };
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
public static String  _activity_pause(boolean _userclosed) throws Exception{
		Debug.PushSubsStack("Activity_Pause (menu) ","menu",7,mostCurrent.activityBA,mostCurrent);
try {
Debug.locals.put("UserClosed", _userclosed);
 BA.debugLineNum = 40;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
Debug.ShouldStop(128);
 BA.debugLineNum = 42;BA.debugLine="End Sub";
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
public static String  _activity_resume() throws Exception{
		Debug.PushSubsStack("Activity_Resume (menu) ","menu",7,mostCurrent.activityBA,mostCurrent);
try {
 BA.debugLineNum = 36;BA.debugLine="Sub Activity_Resume";
Debug.ShouldStop(8);
 BA.debugLineNum = 38;BA.debugLine="End Sub";
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
public static String  _button_editarconta_click() throws Exception{
		Debug.PushSubsStack("Button_EditarConta_Click (menu) ","menu",7,mostCurrent.activityBA,mostCurrent);
try {
 BA.debugLineNum = 66;BA.debugLine="Sub Button_EditarConta_Click";
Debug.ShouldStop(2);
 BA.debugLineNum = 67;BA.debugLine="StartActivity(\"Editar\")";
Debug.ShouldStop(4);
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)("Editar"));
 BA.debugLineNum = 68;BA.debugLine="Activity.Finish";
Debug.ShouldStop(8);
mostCurrent._activity.Finish();
 BA.debugLineNum = 69;BA.debugLine="End Sub";
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
public static String  _button_excluirconta_click() throws Exception{
		Debug.PushSubsStack("Button_ExcluirConta_Click (menu) ","menu",7,mostCurrent.activityBA,mostCurrent);
try {
 BA.debugLineNum = 56;BA.debugLine="Sub Button_ExcluirConta_Click";
Debug.ShouldStop(8388608);
 BA.debugLineNum = 57;BA.debugLine="StartActivity(\"Excluir\")";
Debug.ShouldStop(16777216);
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)("Excluir"));
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
public static String  _button_financeiro_click() throws Exception{
		Debug.PushSubsStack("Button_Financeiro_Click (menu) ","menu",7,mostCurrent.activityBA,mostCurrent);
try {
 BA.debugLineNum = 44;BA.debugLine="Sub Button_Financeiro_Click";
Debug.ShouldStop(2048);
 BA.debugLineNum = 45;BA.debugLine="StartActivity(\"Financeiro\")";
Debug.ShouldStop(4096);
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)("Financeiro"));
 BA.debugLineNum = 46;BA.debugLine="End Sub";
Debug.ShouldStop(8192);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _button_logout_click() throws Exception{
		Debug.PushSubsStack("Button_Logout_Click (menu) ","menu",7,mostCurrent.activityBA,mostCurrent);
try {
 BA.debugLineNum = 60;BA.debugLine="Sub Button_Logout_Click";
Debug.ShouldStop(134217728);
 BA.debugLineNum = 61;BA.debugLine="Main.Fazer_logout = True";
Debug.ShouldStop(268435456);
mostCurrent._main._fazer_logout = anywheresoftware.b4a.keywords.Common.True;
 BA.debugLineNum = 62;BA.debugLine="StartActivity(\"Main\")";
Debug.ShouldStop(536870912);
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)("Main"));
 BA.debugLineNum = 63;BA.debugLine="Activity.Finish";
Debug.ShouldStop(1073741824);
mostCurrent._activity.Finish();
 BA.debugLineNum = 64;BA.debugLine="End Sub";
Debug.ShouldStop(-2147483648);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _button_sair_click() throws Exception{
		Debug.PushSubsStack("Button_sair_Click (menu) ","menu",7,mostCurrent.activityBA,mostCurrent);
try {
 BA.debugLineNum = 48;BA.debugLine="Sub Button_sair_Click";
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
public static String  _button_utilitarios_click() throws Exception{
		Debug.PushSubsStack("Button_Utilitarios_Click (menu) ","menu",7,mostCurrent.activityBA,mostCurrent);
try {
 BA.debugLineNum = 52;BA.debugLine="Sub Button_Utilitarios_Click";
Debug.ShouldStop(524288);
 BA.debugLineNum = 53;BA.debugLine="StartActivity(\"Utilitarios\")";
Debug.ShouldStop(1048576);
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)("Utilitarios"));
 BA.debugLineNum = 54;BA.debugLine="End Sub";
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
public static String  _globals() throws Exception{
 //BA.debugLineNum = 10;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 11;BA.debugLine="Dim Button_Financeiro As Button";
mostCurrent._button_financeiro = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 12;BA.debugLine="Dim Button_sair As Button";
mostCurrent._button_sair = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 13;BA.debugLine="Dim Button_Utilitarios As Button";
mostCurrent._button_utilitarios = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 14;BA.debugLine="Private Button_ExcluirConta As Button";
mostCurrent._button_excluirconta = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 15;BA.debugLine="Private Button_Logout As Button";
mostCurrent._button_logout = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 16;BA.debugLine="Private Button_EditarConta As Button";
mostCurrent._button_editarconta = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 17;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 8;BA.debugLine="End Sub";
return "";
}
}
