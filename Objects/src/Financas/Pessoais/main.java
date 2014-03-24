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

public class main extends Activity implements B4AActivity{
	public static main mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "Financas.Pessoais", "Financas.Pessoais.main");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (main).");
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
		activityBA = new BA(this, layout, processBA, "Financas.Pessoais", "Financas.Pessoais.main");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "Financas.Pessoais.main", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (main) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (main) Resume **");
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
		return main.class;
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
        BA.LogInfo("** Activity (main) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            BA.LogInfo("** Activity (main) Resume **");
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
public static Financas.Pessoais.persistencia _pers = null;
public static boolean _fazer_logout = false;
public static String _un = "";
public static String _s = "";
public anywheresoftware.b4a.objects.ButtonWrapper _button_cadastro = null;
public anywheresoftware.b4a.objects.EditTextWrapper _username = null;
public anywheresoftware.b4a.objects.EditTextWrapper _senha = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button_sair = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button_entrar = null;
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
		return new Object[] {"Activity",mostCurrent._activity,"AddCategoria",Debug.moduleToString(Financas.Pessoais.addcategoria.class),"Button_Cadastro",mostCurrent._button_cadastro,"Button_entrar",mostCurrent._button_entrar,"Button_Sair",mostCurrent._button_sair,"Cadastro",Debug.moduleToString(Financas.Pessoais.cadastro.class),"Calculadora",Debug.moduleToString(Financas.Pessoais.calculadora.class),"Creditos",Debug.moduleToString(Financas.Pessoais.creditos.class),"Debitos",Debug.moduleToString(Financas.Pessoais.debitos.class),"Editar",Debug.moduleToString(Financas.Pessoais.editar.class),"Excluir",Debug.moduleToString(Financas.Pessoais.excluir.class),"Extrato",Debug.moduleToString(Financas.Pessoais.extrato.class),"Fazer_logout",_fazer_logout,"Financeiro",Debug.moduleToString(Financas.Pessoais.financeiro.class),"Lista",Debug.moduleToString(Financas.Pessoais.lista.class),"Menu",Debug.moduleToString(Financas.Pessoais.menu.class),"Pers",_pers,"Remover_Categoria",Debug.moduleToString(Financas.Pessoais.remover_categoria.class),"S",_s,"Senha",mostCurrent._senha,"Total",Debug.moduleToString(Financas.Pessoais.total.class),"UN",_un,"Username",mostCurrent._username,"Utilitarios",Debug.moduleToString(Financas.Pessoais.utilitarios.class)};
}

public static boolean isAnyActivityVisible() {
    boolean vis = false;
vis = vis | (main.mostCurrent != null);
vis = vis | (cadastro.mostCurrent != null);
vis = vis | (financeiro.mostCurrent != null);
vis = vis | (creditos.mostCurrent != null);
vis = vis | (debitos.mostCurrent != null);
vis = vis | (total.mostCurrent != null);
vis = vis | (utilitarios.mostCurrent != null);
vis = vis | (menu.mostCurrent != null);
vis = vis | (calculadora.mostCurrent != null);
vis = vis | (extrato.mostCurrent != null);
vis = vis | (excluir.mostCurrent != null);
vis = vis | (addcategoria.mostCurrent != null);
vis = vis | (remover_categoria.mostCurrent != null);
vis = vis | (editar.mostCurrent != null);
return vis;}

public static void killProgram() {
     {
            Activity __a = null;
            if (main.previousOne != null) {
				__a = main.previousOne.get();
			}
            else {
                BA ba = main.mostCurrent.processBA.sharedProcessBA.activityBA.get();
                if (ba != null) __a = ba.activity;
            }
            if (__a != null)
				__a.finish();}

 {
            Activity __a = null;
            if (cadastro.previousOne != null) {
				__a = cadastro.previousOne.get();
			}
            else {
                BA ba = cadastro.mostCurrent.processBA.sharedProcessBA.activityBA.get();
                if (ba != null) __a = ba.activity;
            }
            if (__a != null)
				__a.finish();}

 {
            Activity __a = null;
            if (financeiro.previousOne != null) {
				__a = financeiro.previousOne.get();
			}
            else {
                BA ba = financeiro.mostCurrent.processBA.sharedProcessBA.activityBA.get();
                if (ba != null) __a = ba.activity;
            }
            if (__a != null)
				__a.finish();}

 {
            Activity __a = null;
            if (creditos.previousOne != null) {
				__a = creditos.previousOne.get();
			}
            else {
                BA ba = creditos.mostCurrent.processBA.sharedProcessBA.activityBA.get();
                if (ba != null) __a = ba.activity;
            }
            if (__a != null)
				__a.finish();}

 {
            Activity __a = null;
            if (debitos.previousOne != null) {
				__a = debitos.previousOne.get();
			}
            else {
                BA ba = debitos.mostCurrent.processBA.sharedProcessBA.activityBA.get();
                if (ba != null) __a = ba.activity;
            }
            if (__a != null)
				__a.finish();}

 {
            Activity __a = null;
            if (total.previousOne != null) {
				__a = total.previousOne.get();
			}
            else {
                BA ba = total.mostCurrent.processBA.sharedProcessBA.activityBA.get();
                if (ba != null) __a = ba.activity;
            }
            if (__a != null)
				__a.finish();}

 {
            Activity __a = null;
            if (utilitarios.previousOne != null) {
				__a = utilitarios.previousOne.get();
			}
            else {
                BA ba = utilitarios.mostCurrent.processBA.sharedProcessBA.activityBA.get();
                if (ba != null) __a = ba.activity;
            }
            if (__a != null)
				__a.finish();}

 {
            Activity __a = null;
            if (menu.previousOne != null) {
				__a = menu.previousOne.get();
			}
            else {
                BA ba = menu.mostCurrent.processBA.sharedProcessBA.activityBA.get();
                if (ba != null) __a = ba.activity;
            }
            if (__a != null)
				__a.finish();}

 {
            Activity __a = null;
            if (calculadora.previousOne != null) {
				__a = calculadora.previousOne.get();
			}
            else {
                BA ba = calculadora.mostCurrent.processBA.sharedProcessBA.activityBA.get();
                if (ba != null) __a = ba.activity;
            }
            if (__a != null)
				__a.finish();}

 {
            Activity __a = null;
            if (extrato.previousOne != null) {
				__a = extrato.previousOne.get();
			}
            else {
                BA ba = extrato.mostCurrent.processBA.sharedProcessBA.activityBA.get();
                if (ba != null) __a = ba.activity;
            }
            if (__a != null)
				__a.finish();}

 {
            Activity __a = null;
            if (excluir.previousOne != null) {
				__a = excluir.previousOne.get();
			}
            else {
                BA ba = excluir.mostCurrent.processBA.sharedProcessBA.activityBA.get();
                if (ba != null) __a = ba.activity;
            }
            if (__a != null)
				__a.finish();}

 {
            Activity __a = null;
            if (addcategoria.previousOne != null) {
				__a = addcategoria.previousOne.get();
			}
            else {
                BA ba = addcategoria.mostCurrent.processBA.sharedProcessBA.activityBA.get();
                if (ba != null) __a = ba.activity;
            }
            if (__a != null)
				__a.finish();}

 {
            Activity __a = null;
            if (remover_categoria.previousOne != null) {
				__a = remover_categoria.previousOne.get();
			}
            else {
                BA ba = remover_categoria.mostCurrent.processBA.sharedProcessBA.activityBA.get();
                if (ba != null) __a = ba.activity;
            }
            if (__a != null)
				__a.finish();}

 {
            Activity __a = null;
            if (editar.previousOne != null) {
				__a = editar.previousOne.get();
			}
            else {
                BA ba = editar.mostCurrent.processBA.sharedProcessBA.activityBA.get();
                if (ba != null) __a = ba.activity;
            }
            if (__a != null)
				__a.finish();}

}
public static String  _activity_create(boolean _firsttime) throws Exception{
		Debug.PushSubsStack("Activity_Create (main) ","main",0,mostCurrent.activityBA,mostCurrent);
try {
Debug.locals.put("FirstTime", _firsttime);
 BA.debugLineNum = 28;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
Debug.ShouldStop(134217728);
 BA.debugLineNum = 29;BA.debugLine="If FirstTime Then";
Debug.ShouldStop(268435456);
if (_firsttime) { 
 BA.debugLineNum = 30;BA.debugLine="FirstTime = False";
Debug.ShouldStop(536870912);
_firsttime = anywheresoftware.b4a.keywords.Common.False;Debug.locals.put("FirstTime", _firsttime);
 BA.debugLineNum = 31;BA.debugLine="Activity.LoadLayout(\"Layout_Login\")";
Debug.ShouldStop(1073741824);
mostCurrent._activity.LoadLayout("Layout_Login",mostCurrent.activityBA);
 }else {
 BA.debugLineNum = 33;BA.debugLine="If Fazer_logout Then";
Debug.ShouldStop(1);
if (_fazer_logout) { 
 BA.debugLineNum = 34;BA.debugLine="Activity.LoadLayout(\"Layout_Login\")";
Debug.ShouldStop(2);
mostCurrent._activity.LoadLayout("Layout_Login",mostCurrent.activityBA);
 }else {
 BA.debugLineNum = 36;BA.debugLine="StartActivity(\"Menu\")";
Debug.ShouldStop(8);
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)("Menu"));
 BA.debugLineNum = 37;BA.debugLine="Activity.Finish";
Debug.ShouldStop(16);
mostCurrent._activity.Finish();
 };
 };
 BA.debugLineNum = 40;BA.debugLine="End Sub";
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
public static String  _activity_pause(boolean _userclosed) throws Exception{
		Debug.PushSubsStack("Activity_Pause (main) ","main",0,mostCurrent.activityBA,mostCurrent);
try {
Debug.locals.put("UserClosed", _userclosed);
 BA.debugLineNum = 46;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
Debug.ShouldStop(8192);
 BA.debugLineNum = 48;BA.debugLine="End Sub";
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
public static String  _activity_resume() throws Exception{
		Debug.PushSubsStack("Activity_Resume (main) ","main",0,mostCurrent.activityBA,mostCurrent);
try {
 BA.debugLineNum = 42;BA.debugLine="Sub Activity_Resume";
Debug.ShouldStop(512);
 BA.debugLineNum = 44;BA.debugLine="End Sub";
Debug.ShouldStop(2048);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _button_cadastro_click() throws Exception{
		Debug.PushSubsStack("Button_Cadastro_Click (main) ","main",0,mostCurrent.activityBA,mostCurrent);
try {
 BA.debugLineNum = 50;BA.debugLine="Sub Button_Cadastro_Click";
Debug.ShouldStop(131072);
 BA.debugLineNum = 51;BA.debugLine="StartActivity(\"Cadastro\")";
Debug.ShouldStop(262144);
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)("Cadastro"));
 BA.debugLineNum = 52;BA.debugLine="End Sub";
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
public static String  _button_entrar_click() throws Exception{
		Debug.PushSubsStack("Button_entrar_Click (main) ","main",0,mostCurrent.activityBA,mostCurrent);
try {
 BA.debugLineNum = 58;BA.debugLine="Sub Button_entrar_Click";
Debug.ShouldStop(33554432);
 BA.debugLineNum = 59;BA.debugLine="Pers.Initialize";
Debug.ShouldStop(67108864);
_pers._initialize(processBA);
 BA.debugLineNum = 60;BA.debugLine="UN = Username.Text";
Debug.ShouldStop(134217728);
_un = mostCurrent._username.getText();
 BA.debugLineNum = 61;BA.debugLine="S = Senha.Text";
Debug.ShouldStop(268435456);
_s = mostCurrent._senha.getText();
 BA.debugLineNum = 62;BA.debugLine="If Pers.Fazer_Login(Username.Text, Senha.Text) Then";
Debug.ShouldStop(536870912);
if (_pers._fazer_login(mostCurrent._username.getText(),mostCurrent._senha.getText())) { 
 BA.debugLineNum = 63;BA.debugLine="StartActivity(\"Menu\")";
Debug.ShouldStop(1073741824);
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)("Menu"));
 BA.debugLineNum = 64;BA.debugLine="Activity.Finish";
Debug.ShouldStop(-2147483648);
mostCurrent._activity.Finish();
 }else {
 BA.debugLineNum = 66;BA.debugLine="Msgbox(\"Usuario ou senha invalida!\", \"Aviso!\")";
Debug.ShouldStop(2);
anywheresoftware.b4a.keywords.Common.Msgbox("Usuario ou senha invalida!","Aviso!",mostCurrent.activityBA);
 };
 BA.debugLineNum = 68;BA.debugLine="End Sub";
Debug.ShouldStop(8);
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
		Debug.PushSubsStack("Button_Sair_Click (main) ","main",0,mostCurrent.activityBA,mostCurrent);
try {
 BA.debugLineNum = 54;BA.debugLine="Sub Button_Sair_Click";
Debug.ShouldStop(2097152);
 BA.debugLineNum = 55;BA.debugLine="Activity.Finish";
Debug.ShouldStop(4194304);
mostCurrent._activity.Finish();
 BA.debugLineNum = 56;BA.debugLine="End Sub";
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

public static void initializeProcessGlobals() {
    if (mostCurrent != null && mostCurrent.activityBA != null) {
Debug.StartDebugging(mostCurrent.activityBA, 34989, new int[] {3, 2, 2, 3, 3, 1, 2, 3, 4, 7, 5, 2, 2, 2, 1, 4, 3}, "3d009dfe-f104-4652-8a8e-177abefed135");}

    if (main.processGlobalsRun == false) {
	    main.processGlobalsRun = true;
		try {
		        main._process_globals();
cadastro._process_globals();
financeiro._process_globals();
creditos._process_globals();
debitos._process_globals();
total._process_globals();
utilitarios._process_globals();
menu._process_globals();
calculadora._process_globals();
extrato._process_globals();
excluir._process_globals();
addcategoria._process_globals();
remover_categoria._process_globals();
lista._process_globals();
editar._process_globals();
		
        } catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
}public static String  _globals() throws Exception{
 //BA.debugLineNum = 20;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 21;BA.debugLine="Dim Button_Cadastro As Button";
mostCurrent._button_cadastro = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Dim Username As EditText";
mostCurrent._username = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 23;BA.debugLine="Dim Senha As EditText";
mostCurrent._senha = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 24;BA.debugLine="Dim Button_Sair As Button";
mostCurrent._button_sair = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Dim Button_entrar As Button";
mostCurrent._button_entrar = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 26;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 14;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 15;BA.debugLine="Dim Pers As Persistencia";
_pers = new Financas.Pessoais.persistencia();
 //BA.debugLineNum = 16;BA.debugLine="Dim Fazer_logout As Boolean";
_fazer_logout = false;
 //BA.debugLineNum = 17;BA.debugLine="Dim UN,S As String";
_un = "";
_s = "";
 //BA.debugLineNum = 18;BA.debugLine="End Sub";
return "";
}
}
