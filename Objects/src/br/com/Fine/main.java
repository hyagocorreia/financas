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
	public static final boolean fullScreen = true;
	public static final boolean includeTitle = false;
    public static WeakReference<Activity> previousOne;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (isFirst) {
			processBA = new BA(this.getApplicationContext(), null, null, "br.com.Fine", "br.com.Fine.main");
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
		activityBA = new BA(this, layout, processBA, "br.com.Fine", "br.com.Fine.main");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "br.com.Fine.main", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density);
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
public static br.com.Fine.persistencia _pers = null;
public static boolean _fazer_logout = false;
public anywheresoftware.b4a.objects.ButtonWrapper _button_cadastro = null;
public anywheresoftware.b4a.objects.EditTextWrapper _username = null;
public anywheresoftware.b4a.objects.EditTextWrapper _senha = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button_sair = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button_entrar = null;
public br.com.Fine.cadastro _cadastro = null;
public br.com.Fine.financeiro _financeiro = null;
public br.com.Fine.creditos _creditos = null;
public br.com.Fine.debitos _debitos = null;
public br.com.Fine.total _total = null;
public br.com.Fine.utilitarios _utilitarios = null;
public br.com.Fine.menu _menu = null;
public br.com.Fine.calculadora _calculadora = null;
public br.com.Fine.extrato _extrato = null;
public br.com.Fine.excluir _excluir = null;
public br.com.Fine.remover_categoria _remover_categoria = null;
public br.com.Fine.addcategoria _addcategoria = null;
public br.com.Fine.editar _editar = null;
public br.com.Fine.charts _charts = null;
public br.com.Fine.graficos _graficos = null;

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
vis = vis | (remover_categoria.mostCurrent != null);
vis = vis | (addcategoria.mostCurrent != null);
vis = vis | (editar.mostCurrent != null);
vis = vis | (graficos.mostCurrent != null);
return vis;}
public static String  _activity_create(boolean _firsttime) throws Exception{
 //BA.debugLineNum = 28;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 29;BA.debugLine="Pers.Initialize";
_pers._initialize(processBA);
 //BA.debugLineNum = 30;BA.debugLine="If Not(File.Exists(File.DirRootExternal,\"Fine/Data\")) Then";
if (anywheresoftware.b4a.keywords.Common.Not(anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),"Fine/Data"))) { 
 //BA.debugLineNum = 31;BA.debugLine="File.MakeDir(File.DirRootExternal,\"Fine/Data\")";
anywheresoftware.b4a.keywords.Common.File.MakeDir(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),"Fine/Data");
 };
 //BA.debugLineNum = 33;BA.debugLine="If File.Exists(File.DirRootExternal&\"/Fine/Data\",\"logado.txt\") Then";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/Fine/Data","logado.txt")) { 
 //BA.debugLineNum = 34;BA.debugLine="StartActivity(\"Menu\")";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)("Menu"));
 //BA.debugLineNum = 35;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 }else if(_firsttime) { 
 //BA.debugLineNum = 37;BA.debugLine="FirstTime = False";
_firsttime = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 38;BA.debugLine="Activity.LoadLayout(\"Layout_Login\")";
mostCurrent._activity.LoadLayout("Layout_Login",mostCurrent.activityBA);
 }else if(_fazer_logout) { 
 //BA.debugLineNum = 40;BA.debugLine="Activity.LoadLayout(\"Layout_Login\")";
mostCurrent._activity.LoadLayout("Layout_Login",mostCurrent.activityBA);
 };
 //BA.debugLineNum = 42;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 48;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 50;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 44;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 46;BA.debugLine="End Sub";
return "";
}
public static String  _button_cadastro_click() throws Exception{
 //BA.debugLineNum = 52;BA.debugLine="Sub Button_Cadastro_Click";
 //BA.debugLineNum = 53;BA.debugLine="StartActivity(\"Cadastro\")";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)("Cadastro"));
 //BA.debugLineNum = 54;BA.debugLine="End Sub";
return "";
}
public static String  _button_entrar_click() throws Exception{
anywheresoftware.b4a.objects.streams.File.TextWriterWrapper _textwriter1 = null;
 //BA.debugLineNum = 60;BA.debugLine="Sub Button_entrar_Click";
 //BA.debugLineNum = 61;BA.debugLine="Pers.Initialize";
_pers._initialize(processBA);
 //BA.debugLineNum = 62;BA.debugLine="If Pers.Fazer_Login(Username.Text, Senha.Text) Then";
if (_pers._fazer_login(mostCurrent._username.getText(),mostCurrent._senha.getText())) { 
 //BA.debugLineNum = 63;BA.debugLine="Dim TextWriter1 As TextWriter";
_textwriter1 = new anywheresoftware.b4a.objects.streams.File.TextWriterWrapper();
 //BA.debugLineNum = 64;BA.debugLine="TextWriter1.Initialize(File.OpenOutput(File.DirRootExternal&\"/Fine/Data\",\"logado.txt\",True))";
_textwriter1.Initialize((java.io.OutputStream)(anywheresoftware.b4a.keywords.Common.File.OpenOutput(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/Fine/Data","logado.txt",anywheresoftware.b4a.keywords.Common.True).getObject()));
 //BA.debugLineNum = 65;BA.debugLine="TextWriter1.Write(Username.Text)";
_textwriter1.Write(mostCurrent._username.getText());
 //BA.debugLineNum = 66;BA.debugLine="TextWriter1.Close";
_textwriter1.Close();
 //BA.debugLineNum = 67;BA.debugLine="StartActivity(\"Menu\")";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)("Menu"));
 //BA.debugLineNum = 68;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 }else {
 //BA.debugLineNum = 70;BA.debugLine="Msgbox(\"Usuario ou senha invalida!\", \"Aviso!\")";
anywheresoftware.b4a.keywords.Common.Msgbox("Usuario ou senha invalida!","Aviso!",mostCurrent.activityBA);
 };
 //BA.debugLineNum = 72;BA.debugLine="End Sub";
return "";
}
public static String  _button_sair_click() throws Exception{
 //BA.debugLineNum = 56;BA.debugLine="Sub Button_Sair_Click";
 //BA.debugLineNum = 57;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 58;BA.debugLine="End Sub";
return "";
}

public static void initializeProcessGlobals() {
    
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
remover_categoria._process_globals();
addcategoria._process_globals();
editar._process_globals();
charts._process_globals();
graficos._process_globals();
		
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
_pers = new br.com.Fine.persistencia();
 //BA.debugLineNum = 16;BA.debugLine="Dim Fazer_logout As Boolean";
_fazer_logout = false;
 //BA.debugLineNum = 17;BA.debugLine="Fazer_logout = False";
_fazer_logout = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 18;BA.debugLine="End Sub";
return "";
}
}
