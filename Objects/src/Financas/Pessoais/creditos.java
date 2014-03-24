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
			processBA = new BA(this.getApplicationContext(), null, null, "Financas.Pessoais", "Financas.Pessoais.creditos");
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
		activityBA = new BA(this, layout, processBA, "Financas.Pessoais", "Financas.Pessoais.creditos");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "Financas.Pessoais.creditos", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density);
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
public static int _result = 0;
public anywheresoftware.b4a.objects.EditTextWrapper _valor = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button_creditar = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button_voltar = null;
public anywheresoftware.b4a.objects.EditTextWrapper _data = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button_add = null;
public anywheresoftware.b4a.objects.SpinnerWrapper _categoria = null;
public Financas.Pessoais.main _main = null;
public Financas.Pessoais.cadastro _cadastro = null;
public Financas.Pessoais.financeiro _financeiro = null;
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
		return new Object[] {"Activity",mostCurrent._activity,"AddCategoria",Debug.moduleToString(Financas.Pessoais.addcategoria.class),"Button_add",mostCurrent._button_add,"Button_Creditar",mostCurrent._button_creditar,"Button_Voltar",mostCurrent._button_voltar,"Cadastro",Debug.moduleToString(Financas.Pessoais.cadastro.class),"Calculadora",Debug.moduleToString(Financas.Pessoais.calculadora.class),"Categoria",mostCurrent._categoria,"Data",mostCurrent._data,"Debitos",Debug.moduleToString(Financas.Pessoais.debitos.class),"Editar",Debug.moduleToString(Financas.Pessoais.editar.class),"Excluir",Debug.moduleToString(Financas.Pessoais.excluir.class),"Extrato",Debug.moduleToString(Financas.Pessoais.extrato.class),"Financeiro",Debug.moduleToString(Financas.Pessoais.financeiro.class),"Lista",Debug.moduleToString(Financas.Pessoais.lista.class),"Main",Debug.moduleToString(Financas.Pessoais.main.class),"Menu",Debug.moduleToString(Financas.Pessoais.menu.class),"Remover_Categoria",Debug.moduleToString(Financas.Pessoais.remover_categoria.class),"result",_result,"Total",Debug.moduleToString(Financas.Pessoais.total.class),"Utilitarios",Debug.moduleToString(Financas.Pessoais.utilitarios.class),"Valor",mostCurrent._valor};
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
Debug.locals.put("FirstTime", _firsttime);
 BA.debugLineNum = 20;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
Debug.ShouldStop(524288);
 BA.debugLineNum = 21;BA.debugLine="Activity.LoadLayout(\"Layout_Creditos\")";
Debug.ShouldStop(1048576);
mostCurrent._activity.LoadLayout("Layout_Creditos",mostCurrent.activityBA);
 BA.debugLineNum = 22;BA.debugLine="DateTime.DateFormat = \"dd/MM/yy\"";
Debug.ShouldStop(2097152);
anywheresoftware.b4a.keywords.Common.DateTime.setDateFormat("dd/MM/yy");
 BA.debugLineNum = 23;BA.debugLine="Data.Text = DateTime.Date(DateTime.Now)";
Debug.ShouldStop(4194304);
mostCurrent._data.setText((Object)(anywheresoftware.b4a.keywords.Common.DateTime.Date(anywheresoftware.b4a.keywords.Common.DateTime.getNow())));
 BA.debugLineNum = 26;BA.debugLine="Categoria.AddAll(Lista.Lista_Categorias)";
Debug.ShouldStop(33554432);
mostCurrent._categoria.AddAll(mostCurrent._lista._lista_categorias);
 BA.debugLineNum = 32;BA.debugLine="End Sub";
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
public static String  _activity_pause(boolean _userclosed) throws Exception{
		Debug.PushSubsStack("Activity_Pause (creditos) ","creditos",3,mostCurrent.activityBA,mostCurrent);
try {
Debug.locals.put("UserClosed", _userclosed);
 BA.debugLineNum = 38;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
Debug.ShouldStop(32);
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
public static String  _activity_resume() throws Exception{
		Debug.PushSubsStack("Activity_Resume (creditos) ","creditos",3,mostCurrent.activityBA,mostCurrent);
try {
 BA.debugLineNum = 34;BA.debugLine="Sub Activity_Resume";
Debug.ShouldStop(2);
 BA.debugLineNum = 36;BA.debugLine="End Sub";
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
public static String  _button_add_click() throws Exception{
		Debug.PushSubsStack("Button_add_Click (creditos) ","creditos",3,mostCurrent.activityBA,mostCurrent);
try {
 BA.debugLineNum = 75;BA.debugLine="Sub Button_add_Click";
Debug.ShouldStop(1024);
 BA.debugLineNum = 76;BA.debugLine="Activity.Finish";
Debug.ShouldStop(2048);
mostCurrent._activity.Finish();
 BA.debugLineNum = 77;BA.debugLine="AddCategoria.nome_classe = \"crédito\"";
Debug.ShouldStop(4096);
mostCurrent._addcategoria._nome_classe = "crédito";
 BA.debugLineNum = 78;BA.debugLine="StartActivity(\"AddCategoria\")";
Debug.ShouldStop(8192);
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)("AddCategoria"));
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
public static String  _button_creditar_click() throws Exception{
		Debug.PushSubsStack("Button_Creditar_Click (creditos) ","creditos",3,mostCurrent.activityBA,mostCurrent);
try {
double _valor_final = 0;
 BA.debugLineNum = 42;BA.debugLine="Sub Button_Creditar_Click";
Debug.ShouldStop(512);
 BA.debugLineNum = 43;BA.debugLine="If Valor.Text = \"\" Then";
Debug.ShouldStop(1024);
if ((mostCurrent._valor.getText()).equals("")) { 
 BA.debugLineNum = 44;BA.debugLine="Msgbox(\"Campos Obrigatorios não estão preenchidos\", \"Aviso!\" )";
Debug.ShouldStop(2048);
anywheresoftware.b4a.keywords.Common.Msgbox("Campos Obrigatorios não estão preenchidos","Aviso!",mostCurrent.activityBA);
 }else {
 BA.debugLineNum = 46;BA.debugLine="Dim Valor_final As Double";
Debug.ShouldStop(8192);
_valor_final = 0;Debug.locals.put("Valor_final", _valor_final);
 BA.debugLineNum = 47;BA.debugLine="Valor_final = Valor.Text";
Debug.ShouldStop(16384);
_valor_final = (double)(Double.parseDouble(mostCurrent._valor.getText()));Debug.locals.put("Valor_final", _valor_final);
 BA.debugLineNum = 49;BA.debugLine="Msgbox(\"Valor: \" & NumberFormat2(Valor_final,1,2,2,True) & CRLF & \"Categoria: \" & Categoria.SelectedItem & CRLF & \"Data: \" & Data.Text,\"Creditado com Sucesso!\")";
Debug.ShouldStop(65536);
anywheresoftware.b4a.keywords.Common.Msgbox("Valor: "+anywheresoftware.b4a.keywords.Common.NumberFormat2(_valor_final,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+anywheresoftware.b4a.keywords.Common.CRLF+"Categoria: "+mostCurrent._categoria.getSelectedItem()+anywheresoftware.b4a.keywords.Common.CRLF+"Data: "+mostCurrent._data.getText(),"Creditado com Sucesso!",mostCurrent.activityBA);
 BA.debugLineNum = 51;BA.debugLine="Main.Pers.Salvar_Transacao(Valor_final, Data.Text, Categoria.SelectedItem, \"Crédito\")";
Debug.ShouldStop(262144);
mostCurrent._main._pers._salvar_transacao((Object)(_valor_final),mostCurrent._data.getText(),mostCurrent._categoria.getSelectedItem(),"Crédito");
 BA.debugLineNum = 53;BA.debugLine="result = Msgbox2(\"Deseja fazer outra operação?\",\"Aviso!\",\"Sim\",\"\",\"Nao\",Null)";
Debug.ShouldStop(1048576);
_result = anywheresoftware.b4a.keywords.Common.Msgbox2("Deseja fazer outra operação?","Aviso!","Sim","","Nao",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA);
 BA.debugLineNum = 55;BA.debugLine="If result = DialogResponse.POSITIVE Then";
Debug.ShouldStop(4194304);
if (_result==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
 BA.debugLineNum = 56;BA.debugLine="StartActivity(\"Creditos\")";
Debug.ShouldStop(8388608);
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)("Creditos"));
 }else {
 BA.debugLineNum = 58;BA.debugLine="StartActivity(\"Financeiro\")";
Debug.ShouldStop(33554432);
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)("Financeiro"));
 BA.debugLineNum = 59;BA.debugLine="Activity.Finish";
Debug.ShouldStop(67108864);
mostCurrent._activity.Finish();
 };
 };
 BA.debugLineNum = 62;BA.debugLine="End Sub";
Debug.ShouldStop(536870912);
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
 BA.debugLineNum = 64;BA.debugLine="Sub Button_Voltar_Click";
Debug.ShouldStop(-2147483648);
 BA.debugLineNum = 65;BA.debugLine="Activity.Finish";
Debug.ShouldStop(1);
mostCurrent._activity.Finish();
 BA.debugLineNum = 66;BA.debugLine="End Sub";
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
public static String  _globals() throws Exception{
 //BA.debugLineNum = 10;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 11;BA.debugLine="Dim result As Int";
_result = 0;
 //BA.debugLineNum = 12;BA.debugLine="Dim Valor As EditText";
mostCurrent._valor = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 13;BA.debugLine="Dim Button_Creditar As Button";
mostCurrent._button_creditar = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 14;BA.debugLine="Dim Button_Voltar As Button";
mostCurrent._button_voltar = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 15;BA.debugLine="Dim Data As EditText";
mostCurrent._data = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 16;BA.debugLine="Private Button_add As Button";
mostCurrent._button_add = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 17;BA.debugLine="Private Categoria As Spinner";
mostCurrent._categoria = new anywheresoftware.b4a.objects.SpinnerWrapper();
 //BA.debugLineNum = 18;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 8;BA.debugLine="End Sub";
return "";
}
}
