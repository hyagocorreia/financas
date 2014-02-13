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

public class creditos extends Activity implements B4AActivity{
	public static creditos mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "b4a.example", "b4a.example.creditos");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (creditos).");
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
		activityBA = new BA(this, layout, processBA, "b4a.example", "b4a.example.creditos");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "b4a.example.creditos", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (creditos) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (creditos) Resume **");
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
		return creditos.class;
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
        BA.LogInfo("** Activity (creditos) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            BA.LogInfo("** Activity (creditos) Resume **");
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
public anywheresoftware.b4a.objects.EditTextWrapper _de = null;
public anywheresoftware.b4a.objects.EditTextWrapper _valor = null;
public anywheresoftware.b4a.objects.EditTextWrapper _observacao = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button_creditar = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button_voltar = null;
public anywheresoftware.b4a.objects.EditTextWrapper _data = null;
public b4a.example.main _main = null;
public b4a.example.cadastro _cadastro = null;
public b4a.example.financeiro _financeiro = null;
public b4a.example.debito _debito = null;
public b4a.example.total _total = null;
public b4a.example.extrato _extrato = null;
public b4a.example.utilitários _utilitários = null;
public b4a.example.calculadora _calculadora = null;
public b4a.example.debitos _debitos = null;
  public Object[] GetGlobals() {
		return new Object[] {"Activity",mostCurrent._activity,"Button_Creditar",mostCurrent._button_creditar,"Button_Voltar",mostCurrent._button_voltar,"Cadastro",Debug.moduleToString(b4a.example.cadastro.class),"Calculadora",Debug.moduleToString(b4a.example.calculadora.class),"Data",mostCurrent._data,"De",mostCurrent._de,"Debito",Debug.moduleToString(b4a.example.debito.class),"Debitos",Debug.moduleToString(b4a.example.debitos.class),"Extrato",Debug.moduleToString(b4a.example.extrato.class),"Financeiro",Debug.moduleToString(b4a.example.financeiro.class),"Main",Debug.moduleToString(b4a.example.main.class),"Observacao",mostCurrent._observacao,"Total",Debug.moduleToString(b4a.example.total.class),"Utilitários",Debug.moduleToString(b4a.example.utilitários.class),"Valor",mostCurrent._valor};
}

public static void initializeProcessGlobals() {
             try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
}
public static String  _activity_create(boolean _firsttime) throws Exception{
		Debug.PushSubsStack("Activity_Create (creditos) ","creditos",3,mostCurrent.activityBA,mostCurrent);
try {
String _data_hoje = "";
Debug.locals.put("FirstTime", _firsttime);
 BA.debugLineNum = 24;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
Debug.ShouldStop(8388608);
 BA.debugLineNum = 26;BA.debugLine="Activity.LoadLayout(\"Layout_Creditos\")";
Debug.ShouldStop(33554432);
mostCurrent._activity.LoadLayout("Layout_Creditos",mostCurrent.activityBA);
 BA.debugLineNum = 27;BA.debugLine="DateTime.DateFormat = \"dd/MM/yy\"";
Debug.ShouldStop(67108864);
anywheresoftware.b4a.keywords.Common.DateTime.setDateFormat("dd/MM/yy");
 BA.debugLineNum = 28;BA.debugLine="Dim Data_hoje As  String = DateTime.Date(DateTime.Now)";
Debug.ShouldStop(134217728);
_data_hoje = anywheresoftware.b4a.keywords.Common.DateTime.Date(anywheresoftware.b4a.keywords.Common.DateTime.getNow());Debug.locals.put("Data_hoje", _data_hoje);Debug.locals.put("Data_hoje", _data_hoje);
 BA.debugLineNum = 29;BA.debugLine="Data.Text = Data_hoje";
Debug.ShouldStop(268435456);
mostCurrent._data.setText((Object)(_data_hoje));
 BA.debugLineNum = 31;BA.debugLine="End Sub";
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
public static String  _activity_pause(boolean _userclosed) throws Exception{
		Debug.PushSubsStack("Activity_Pause (creditos) ","creditos",3,mostCurrent.activityBA,mostCurrent);
try {
Debug.locals.put("UserClosed", _userclosed);
 BA.debugLineNum = 37;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
Debug.ShouldStop(16);
 BA.debugLineNum = 39;BA.debugLine="End Sub";
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
public static String  _activity_resume() throws Exception{
		Debug.PushSubsStack("Activity_Resume (creditos) ","creditos",3,mostCurrent.activityBA,mostCurrent);
try {
 BA.debugLineNum = 33;BA.debugLine="Sub Activity_Resume";
Debug.ShouldStop(1);
 BA.debugLineNum = 35;BA.debugLine="End Sub";
Debug.ShouldStop(4);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _button_creditar_click() throws Exception{
		Debug.PushSubsStack("Button_Creditar_Click (creditos) ","creditos",3,mostCurrent.activityBA,mostCurrent);
try {
float _xvalor = 0f;
String _linha_extrato = "";
 BA.debugLineNum = 43;BA.debugLine="Sub Button_Creditar_Click";
Debug.ShouldStop(1024);
 BA.debugLineNum = 44;BA.debugLine="If De.Text = \"\" OR Valor.Text = \"\" OR Observacao.Text = \"\" Then";
Debug.ShouldStop(2048);
if ((mostCurrent._de.getText()).equals("") || (mostCurrent._valor.getText()).equals("") || (mostCurrent._observacao.getText()).equals("")) { 
 BA.debugLineNum = 45;BA.debugLine="Msgbox(\"Campos Obrigatorios não estão preenchidos\", \"Aviso!\" )";
Debug.ShouldStop(4096);
anywheresoftware.b4a.keywords.Common.Msgbox("Campos Obrigatorios não estão preenchidos","Aviso!",mostCurrent.activityBA);
 }else {
 BA.debugLineNum = 47;BA.debugLine="Msgbox(\"Nome:\" & De.Text&CRLF&\"Valor:\"&Valor.Text&CRLF&\"Observação:\"&CRLF&Observacao.Text&CRLF&\"Data:\"&Data.Text,\"Creditado com Sucesso\")";
Debug.ShouldStop(16384);
anywheresoftware.b4a.keywords.Common.Msgbox("Nome:"+mostCurrent._de.getText()+anywheresoftware.b4a.keywords.Common.CRLF+"Valor:"+mostCurrent._valor.getText()+anywheresoftware.b4a.keywords.Common.CRLF+"Observação:"+anywheresoftware.b4a.keywords.Common.CRLF+mostCurrent._observacao.getText()+anywheresoftware.b4a.keywords.Common.CRLF+"Data:"+mostCurrent._data.getText(),"Creditado com Sucesso",mostCurrent.activityBA);
 BA.debugLineNum = 51;BA.debugLine="Dim xValor As Float = Valor.Text";
Debug.ShouldStop(262144);
_xvalor = (float)(Double.parseDouble(mostCurrent._valor.getText()));Debug.locals.put("xValor", _xvalor);Debug.locals.put("xValor", _xvalor);
 BA.debugLineNum = 53;BA.debugLine="Financeiro.saldo = Financeiro.saldo + xValor";
Debug.ShouldStop(1048576);
mostCurrent._financeiro._saldo = (float) (mostCurrent._financeiro._saldo+_xvalor);
 BA.debugLineNum = 55;BA.debugLine="Dim linha_extrato As String = Data.Text & \" \" & \"(+)\" & xValor & \"    \" & limita_campo(Observacao.Text, 9)";
Debug.ShouldStop(4194304);
_linha_extrato = mostCurrent._data.getText()+" "+"(+)"+BA.NumberToString(_xvalor)+"    "+_limita_campo(mostCurrent._observacao.getText(),(int) (9));Debug.locals.put("linha_extrato", _linha_extrato);Debug.locals.put("linha_extrato", _linha_extrato);
 BA.debugLineNum = 57;BA.debugLine="Financeiro.list_Extrato.Add(linha_extrato)";
Debug.ShouldStop(16777216);
mostCurrent._financeiro._list_extrato.Add((Object)(_linha_extrato));
 };
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
public static String  _button_voltar_click() throws Exception{
		Debug.PushSubsStack("Button_Voltar_Click (creditos) ","creditos",3,mostCurrent.activityBA,mostCurrent);
try {
 BA.debugLineNum = 66;BA.debugLine="Sub Button_Voltar_Click";
Debug.ShouldStop(2);
 BA.debugLineNum = 67;BA.debugLine="Activity.Finish";
Debug.ShouldStop(4);
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
public static String  _globals() throws Exception{
 //BA.debugLineNum = 12;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 16;BA.debugLine="Dim De As EditText";
mostCurrent._de = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 17;BA.debugLine="Dim Valor As EditText";
mostCurrent._valor = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 18;BA.debugLine="Dim Observacao As EditText";
mostCurrent._observacao = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 19;BA.debugLine="Dim Button_Creditar As Button";
mostCurrent._button_creditar = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 20;BA.debugLine="Dim Button_Voltar As Button";
mostCurrent._button_voltar = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 21;BA.debugLine="Dim Data As EditText";
mostCurrent._data = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 22;BA.debugLine="End Sub";
return "";
}
public static String  _limita_campo(String _texto,int _qte_caracteres) throws Exception{
		Debug.PushSubsStack("limita_campo (creditos) ","creditos",3,mostCurrent.activityBA,mostCurrent);
try {
Debug.locals.put("texto", _texto);
Debug.locals.put("qte_caracteres", _qte_caracteres);
 BA.debugLineNum = 71;BA.debugLine="Sub limita_campo(texto As String, qte_caracteres As Int)";
Debug.ShouldStop(64);
 BA.debugLineNum = 72;BA.debugLine="If texto.Length > qte_caracteres Then";
Debug.ShouldStop(128);
if (_texto.length()>_qte_caracteres) { 
 BA.debugLineNum = 73;BA.debugLine="texto = texto.SubString2(1,qte_caracteres)";
Debug.ShouldStop(256);
_texto = _texto.substring((int) (1),_qte_caracteres);Debug.locals.put("texto", _texto);
 };
 BA.debugLineNum = 76;BA.debugLine="Return texto";
Debug.ShouldStop(2048);
if (true) return _texto;
 BA.debugLineNum = 80;BA.debugLine="End Sub";
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
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 10;BA.debugLine="End Sub";
return "";
}
}
