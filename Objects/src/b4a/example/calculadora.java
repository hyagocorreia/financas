package b4a.example;

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

public class calculadora extends Activity implements B4AActivity{
	public static calculadora mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "b4a.example", "b4a.example.calculadora");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (calculadora).");
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
		activityBA = new BA(this, layout, processBA, "b4a.example", "b4a.example.calculadora");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "b4a.example.calculadora", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (calculadora) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (calculadora) Resume **");
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
		return calculadora.class;
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
        BA.LogInfo("** Activity (calculadora) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            BA.LogInfo("** Activity (calculadora) Resume **");
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
public static String _operacao = "";
public anywheresoftware.b4a.objects.ButtonWrapper _button_soma = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button_multiplicacao = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button_subtracao = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button_divisao = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button_igual = null;
public anywheresoftware.b4a.objects.EditTextWrapper _numero1 = null;
public anywheresoftware.b4a.objects.EditTextWrapper _numero2 = null;
public anywheresoftware.b4a.objects.LabelWrapper _resultado = null;
public static float _valor1 = 0f;
public static float _valor2 = 0f;
public anywheresoftware.b4a.objects.ButtonWrapper _button_voltar = null;
public b4a.example.main _main = null;
public b4a.example.cadastro _cadastro = null;
public b4a.example.financeiro _financeiro = null;
public b4a.example.creditos _creditos = null;
public b4a.example.debito _debito = null;
public b4a.example.total _total = null;
public b4a.example.extrato _extrato = null;
public b4a.example.utilitários _utilitários = null;
public b4a.example.debitos _debitos = null;
  public Object[] GetGlobals() {
		return new Object[] {"Activity",mostCurrent._activity,"Button_Divisao",mostCurrent._button_divisao,"Button_igual",mostCurrent._button_igual,"Button_Multiplicacao",mostCurrent._button_multiplicacao,"Button_soma",mostCurrent._button_soma,"Button_subtracao",mostCurrent._button_subtracao,"Button_Voltar",mostCurrent._button_voltar,"Cadastro",Debug.moduleToString(b4a.example.cadastro.class),"Creditos",Debug.moduleToString(b4a.example.creditos.class),"Debito",Debug.moduleToString(b4a.example.debito.class),"Debitos",Debug.moduleToString(b4a.example.debitos.class),"Extrato",Debug.moduleToString(b4a.example.extrato.class),"Financeiro",Debug.moduleToString(b4a.example.financeiro.class),"Main",Debug.moduleToString(b4a.example.main.class),"numero1",mostCurrent._numero1,"numero2",mostCurrent._numero2,"operacao",mostCurrent._operacao,"resultado",mostCurrent._resultado,"Total",Debug.moduleToString(b4a.example.total.class),"Utilitários",Debug.moduleToString(b4a.example.utilitários.class),"valor1",_valor1,"valor2",_valor2};
}

public static void initializeProcessGlobals() {
             try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
}
public static String  _activity_create(boolean _firsttime) throws Exception{
		Debug.PushSubsStack("Activity_Create (calculadora) ","calculadora",8,mostCurrent.activityBA,mostCurrent);
try {
Debug.locals.put("FirstTime", _firsttime);
 BA.debugLineNum = 29;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
Debug.ShouldStop(268435456);
 BA.debugLineNum = 31;BA.debugLine="Activity.LoadLayout(\"Layout_Calculadora\")";
Debug.ShouldStop(1073741824);
mostCurrent._activity.LoadLayout("Layout_Calculadora",mostCurrent.activityBA);
 BA.debugLineNum = 33;BA.debugLine="End Sub";
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
public static String  _activity_pause(boolean _userclosed) throws Exception{
		Debug.PushSubsStack("Activity_Pause (calculadora) ","calculadora",8,mostCurrent.activityBA,mostCurrent);
try {
Debug.locals.put("UserClosed", _userclosed);
 BA.debugLineNum = 39;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
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
public static String  _activity_resume() throws Exception{
		Debug.PushSubsStack("Activity_Resume (calculadora) ","calculadora",8,mostCurrent.activityBA,mostCurrent);
try {
 BA.debugLineNum = 35;BA.debugLine="Sub Activity_Resume";
Debug.ShouldStop(4);
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
public static String  _button_divisao_click() throws Exception{
		Debug.PushSubsStack("Button_Divisao_Click (calculadora) ","calculadora",8,mostCurrent.activityBA,mostCurrent);
try {
 BA.debugLineNum = 64;BA.debugLine="Sub Button_Divisao_Click";
Debug.ShouldStop(-2147483648);
 BA.debugLineNum = 66;BA.debugLine="Button_Divisao.Color = Colors.Blue";
Debug.ShouldStop(2);
mostCurrent._button_divisao.setColor(anywheresoftware.b4a.keywords.Common.Colors.Blue);
 BA.debugLineNum = 68;BA.debugLine="operacao = \"divisao\"";
Debug.ShouldStop(8);
mostCurrent._operacao = "divisao";
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
public static String  _button_igual_click() throws Exception{
		Debug.PushSubsStack("Button_igual_Click (calculadora) ","calculadora",8,mostCurrent.activityBA,mostCurrent);
try {
 BA.debugLineNum = 75;BA.debugLine="Sub Button_igual_Click";
Debug.ShouldStop(1024);
 BA.debugLineNum = 77;BA.debugLine="If numero1.Text <> \"\" AND numero2.Text <> \"\" Then";
Debug.ShouldStop(4096);
if ((mostCurrent._numero1.getText()).equals("") == false && (mostCurrent._numero2.getText()).equals("") == false) { 
 BA.debugLineNum = 81;BA.debugLine="If operacao = \"soma\" Then";
Debug.ShouldStop(65536);
if ((mostCurrent._operacao).equals("soma")) { 
 BA.debugLineNum = 82;BA.debugLine="valor1 = numero1.Text";
Debug.ShouldStop(131072);
_valor1 = (float)(Double.parseDouble(mostCurrent._numero1.getText()));
 BA.debugLineNum = 83;BA.debugLine="valor2 = numero2.Text";
Debug.ShouldStop(262144);
_valor2 = (float)(Double.parseDouble(mostCurrent._numero2.getText()));
 BA.debugLineNum = 85;BA.debugLine="resultado.Text = valor1 + valor2";
Debug.ShouldStop(1048576);
mostCurrent._resultado.setText((Object)(_valor1+_valor2));
 BA.debugLineNum = 86;BA.debugLine="Button_soma.Color = Colors.DarkGray";
Debug.ShouldStop(2097152);
mostCurrent._button_soma.setColor(anywheresoftware.b4a.keywords.Common.Colors.DarkGray);
 }else 
{ BA.debugLineNum = 88;BA.debugLine="Else If operacao = \"subtracao\" Then";
Debug.ShouldStop(8388608);
if ((mostCurrent._operacao).equals("subtracao")) { 
 BA.debugLineNum = 89;BA.debugLine="valor1 = numero1.Text";
Debug.ShouldStop(16777216);
_valor1 = (float)(Double.parseDouble(mostCurrent._numero1.getText()));
 BA.debugLineNum = 90;BA.debugLine="valor2 = numero2.Text";
Debug.ShouldStop(33554432);
_valor2 = (float)(Double.parseDouble(mostCurrent._numero2.getText()));
 BA.debugLineNum = 92;BA.debugLine="resultado.Text = valor1 - valor2";
Debug.ShouldStop(134217728);
mostCurrent._resultado.setText((Object)(_valor1-_valor2));
 BA.debugLineNum = 93;BA.debugLine="Button_subtracao.Color = Colors.DarkGray";
Debug.ShouldStop(268435456);
mostCurrent._button_subtracao.setColor(anywheresoftware.b4a.keywords.Common.Colors.DarkGray);
 }else 
{ BA.debugLineNum = 95;BA.debugLine="Else If operacao = \"multiplicacao\" Then";
Debug.ShouldStop(1073741824);
if ((mostCurrent._operacao).equals("multiplicacao")) { 
 BA.debugLineNum = 96;BA.debugLine="valor1 = numero1.Text";
Debug.ShouldStop(-2147483648);
_valor1 = (float)(Double.parseDouble(mostCurrent._numero1.getText()));
 BA.debugLineNum = 97;BA.debugLine="valor2 = numero2.Text";
Debug.ShouldStop(1);
_valor2 = (float)(Double.parseDouble(mostCurrent._numero2.getText()));
 BA.debugLineNum = 99;BA.debugLine="resultado.Text = valor1 * valor2";
Debug.ShouldStop(4);
mostCurrent._resultado.setText((Object)(_valor1*_valor2));
 BA.debugLineNum = 100;BA.debugLine="Button_Multiplicacao.Color = Colors.DarkGray";
Debug.ShouldStop(8);
mostCurrent._button_multiplicacao.setColor(anywheresoftware.b4a.keywords.Common.Colors.DarkGray);
 }else {
 BA.debugLineNum = 104;BA.debugLine="valor1 = numero1.Text";
Debug.ShouldStop(128);
_valor1 = (float)(Double.parseDouble(mostCurrent._numero1.getText()));
 BA.debugLineNum = 105;BA.debugLine="valor2 = numero2.Text";
Debug.ShouldStop(256);
_valor2 = (float)(Double.parseDouble(mostCurrent._numero2.getText()));
 BA.debugLineNum = 107;BA.debugLine="resultado.Text = valor1 / valor2";
Debug.ShouldStop(1024);
mostCurrent._resultado.setText((Object)(_valor1/(double)_valor2));
 BA.debugLineNum = 108;BA.debugLine="Button_Divisao.Color = Colors.DarkGray";
Debug.ShouldStop(2048);
mostCurrent._button_divisao.setColor(anywheresoftware.b4a.keywords.Common.Colors.DarkGray);
 }}};
 }else {
 BA.debugLineNum = 113;BA.debugLine="Msgbox(\"Digite os valores\", \"Atenção!\")";
Debug.ShouldStop(65536);
anywheresoftware.b4a.keywords.Common.Msgbox("Digite os valores","Atenção!",mostCurrent.activityBA);
 };
 BA.debugLineNum = 116;BA.debugLine="numero1.Text = \"\"";
Debug.ShouldStop(524288);
mostCurrent._numero1.setText((Object)(""));
 BA.debugLineNum = 117;BA.debugLine="numero2.Text = \"\"";
Debug.ShouldStop(1048576);
mostCurrent._numero2.setText((Object)(""));
 BA.debugLineNum = 119;BA.debugLine="End Sub";
Debug.ShouldStop(4194304);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _button_multiplicacao_click() throws Exception{
		Debug.PushSubsStack("Button_Multiplicacao_Click (calculadora) ","calculadora",8,mostCurrent.activityBA,mostCurrent);
try {
 BA.debugLineNum = 50;BA.debugLine="Sub Button_Multiplicacao_Click";
Debug.ShouldStop(131072);
 BA.debugLineNum = 52;BA.debugLine="Button_Multiplicacao.Color = Colors.Blue";
Debug.ShouldStop(524288);
mostCurrent._button_multiplicacao.setColor(anywheresoftware.b4a.keywords.Common.Colors.Blue);
 BA.debugLineNum = 54;BA.debugLine="operacao = \"multiplicacao\"";
Debug.ShouldStop(2097152);
mostCurrent._operacao = "multiplicacao";
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
public static String  _button_soma_click() throws Exception{
		Debug.PushSubsStack("Button_soma_Click (calculadora) ","calculadora",8,mostCurrent.activityBA,mostCurrent);
try {
 BA.debugLineNum = 43;BA.debugLine="Sub Button_soma_Click";
Debug.ShouldStop(1024);
 BA.debugLineNum = 45;BA.debugLine="Button_soma.Color = Colors.Blue";
Debug.ShouldStop(4096);
mostCurrent._button_soma.setColor(anywheresoftware.b4a.keywords.Common.Colors.Blue);
 BA.debugLineNum = 47;BA.debugLine="operacao = \"soma\"";
Debug.ShouldStop(16384);
mostCurrent._operacao = "soma";
 BA.debugLineNum = 49;BA.debugLine="End Sub";
Debug.ShouldStop(65536);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _button_subtracao_click() throws Exception{
		Debug.PushSubsStack("Button_subtracao_Click (calculadora) ","calculadora",8,mostCurrent.activityBA,mostCurrent);
try {
 BA.debugLineNum = 57;BA.debugLine="Sub Button_subtracao_Click";
Debug.ShouldStop(16777216);
 BA.debugLineNum = 59;BA.debugLine="Button_subtracao.Color = Colors.Blue";
Debug.ShouldStop(67108864);
mostCurrent._button_subtracao.setColor(anywheresoftware.b4a.keywords.Common.Colors.Blue);
 BA.debugLineNum = 61;BA.debugLine="operacao = \"subtracao\"";
Debug.ShouldStop(268435456);
mostCurrent._operacao = "subtracao";
 BA.debugLineNum = 63;BA.debugLine="End Sub";
Debug.ShouldStop(1073741824);
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
		Debug.PushSubsStack("Button_Voltar_Click (calculadora) ","calculadora",8,mostCurrent.activityBA,mostCurrent);
try {
 BA.debugLineNum = 120;BA.debugLine="Sub Button_Voltar_Click";
Debug.ShouldStop(8388608);
 BA.debugLineNum = 122;BA.debugLine="Activity.Finish";
Debug.ShouldStop(33554432);
mostCurrent._activity.Finish();
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
public static String  _globals() throws Exception{
 //BA.debugLineNum = 12;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 15;BA.debugLine="Dim operacao As String";
mostCurrent._operacao = "";
 //BA.debugLineNum = 16;BA.debugLine="Dim Button_soma As Button";
mostCurrent._button_soma = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 17;BA.debugLine="Dim Button_Multiplicacao As Button";
mostCurrent._button_multiplicacao = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 18;BA.debugLine="Dim Button_subtracao As Button";
mostCurrent._button_subtracao = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 19;BA.debugLine="Dim Button_Divisao As Button";
mostCurrent._button_divisao = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 20;BA.debugLine="Dim Button_igual As Button";
mostCurrent._button_igual = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 21;BA.debugLine="Dim numero1 As EditText";
mostCurrent._numero1 = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Dim numero2 As EditText";
mostCurrent._numero2 = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 23;BA.debugLine="Dim resultado As Label";
mostCurrent._resultado = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 24;BA.debugLine="Dim valor1 As Float";
_valor1 = 0f;
 //BA.debugLineNum = 25;BA.debugLine="Dim valor2 As Float";
_valor2 = 0f;
 //BA.debugLineNum = 26;BA.debugLine="Dim Button_Voltar As Button";
mostCurrent._button_voltar = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 27;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 10;BA.debugLine="End Sub";
return "";
}
}
