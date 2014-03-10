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

public class debito extends Activity implements B4AActivity{
	public static debito mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "Financas.Pessoais", "Financas.Pessoais.debito");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (debito).");
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
		activityBA = new BA(this, layout, processBA, "Financas.Pessoais", "Financas.Pessoais.debito");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "Financas.Pessoais.debito", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (debito) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (debito) Resume **");
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
		return debito.class;
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
        BA.LogInfo("** Activity (debito) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            BA.LogInfo("** Activity (debito) Resume **");
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
public anywheresoftware.b4a.objects.ButtonWrapper _button_voltar = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button_debitar = null;
public anywheresoftware.b4a.objects.EditTextWrapper _data = null;
public anywheresoftware.b4a.objects.EditTextWrapper _referente = null;
public anywheresoftware.b4a.objects.SpinnerWrapper _categoria = null;
public Financas.Pessoais.main _main = null;
public Financas.Pessoais.cadastro _cadastro = null;
public Financas.Pessoais.financeiro _financeiro = null;
public Financas.Pessoais.creditos _creditos = null;
public Financas.Pessoais.total _total = null;
public Financas.Pessoais.utilitários _utilitários = null;
public Financas.Pessoais.excluir _excluir = null;
public Financas.Pessoais.menu _menu = null;
public Financas.Pessoais.calculadora _calculadora = null;
public Financas.Pessoais.extrato _extrato = null;
public Financas.Pessoais.debitos _debitos = null;
  public Object[] GetGlobals() {
		return new Object[] {"Activity",mostCurrent._activity,"Button_Debitar",mostCurrent._button_debitar,"Button_Voltar",mostCurrent._button_voltar,"Cadastro",Debug.moduleToString(Financas.Pessoais.cadastro.class),"Calculadora",Debug.moduleToString(Financas.Pessoais.calculadora.class),"Categoria",mostCurrent._categoria,"Creditos",Debug.moduleToString(Financas.Pessoais.creditos.class),"Data",mostCurrent._data,"Debitos",Debug.moduleToString(Financas.Pessoais.debitos.class),"Excluir",Debug.moduleToString(Financas.Pessoais.excluir.class),"Extrato",Debug.moduleToString(Financas.Pessoais.extrato.class),"Financeiro",Debug.moduleToString(Financas.Pessoais.financeiro.class),"Main",Debug.moduleToString(Financas.Pessoais.main.class),"Menu",Debug.moduleToString(Financas.Pessoais.menu.class),"Referente",mostCurrent._referente,"result",_result,"Total",Debug.moduleToString(Financas.Pessoais.total.class),"Utilitários",Debug.moduleToString(Financas.Pessoais.utilitários.class),"Valor",mostCurrent._valor};
}

public static void initializeProcessGlobals() {
             try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
}
public static String  _activity_create(boolean _firsttime) throws Exception{
		Debug.PushSubsStack("Activity_Create (debito) ","debito",4,mostCurrent.activityBA,mostCurrent);
try {
String _data_hoje = "";
Debug.locals.put("FirstTime", _firsttime);
 BA.debugLineNum = 25;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
Debug.ShouldStop(16777216);
 BA.debugLineNum = 27;BA.debugLine="Activity.LoadLayout(\"Layout_Debitos\")";
Debug.ShouldStop(67108864);
mostCurrent._activity.LoadLayout("Layout_Debitos",mostCurrent.activityBA);
 BA.debugLineNum = 28;BA.debugLine="DateTime.DateFormat = \"dd/MM/yy\"";
Debug.ShouldStop(134217728);
anywheresoftware.b4a.keywords.Common.DateTime.setDateFormat("dd/MM/yy");
 BA.debugLineNum = 29;BA.debugLine="Dim Data_hoje As String = DateTime.Date(DateTime.Now)";
Debug.ShouldStop(268435456);
_data_hoje = anywheresoftware.b4a.keywords.Common.DateTime.Date(anywheresoftware.b4a.keywords.Common.DateTime.getNow());Debug.locals.put("Data_hoje", _data_hoje);Debug.locals.put("Data_hoje", _data_hoje);
 BA.debugLineNum = 30;BA.debugLine="Data.Text = Data_hoje";
Debug.ShouldStop(536870912);
mostCurrent._data.setText((Object)(_data_hoje));
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
		Debug.PushSubsStack("Activity_Pause (debito) ","debito",4,mostCurrent.activityBA,mostCurrent);
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
		Debug.PushSubsStack("Activity_Resume (debito) ","debito",4,mostCurrent.activityBA,mostCurrent);
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
public static String  _button_debitar_click() throws Exception{
		Debug.PushSubsStack("Button_Debitar_Click (debito) ","debito",4,mostCurrent.activityBA,mostCurrent);
try {
float _xvalor = 0f;
String _linha_extrato = "";
 BA.debugLineNum = 46;BA.debugLine="Sub Button_Debitar_Click";
Debug.ShouldStop(8192);
 BA.debugLineNum = 48;BA.debugLine="If Valor.Text = \"\" OR Referente.Text = \"\" Then";
Debug.ShouldStop(32768);
if ((mostCurrent._valor.getText()).equals("") || (mostCurrent._referente.getText()).equals("")) { 
 BA.debugLineNum = 49;BA.debugLine="Msgbox(\"Campos Obrigatórios não Preenchidos\", \"Atenção!\")";
Debug.ShouldStop(65536);
anywheresoftware.b4a.keywords.Common.Msgbox("Campos Obrigatórios não Preenchidos","Atenção!",mostCurrent.activityBA);
 }else {
 BA.debugLineNum = 53;BA.debugLine="Msgbox(\"Valor:\" &Valor.Text & CRLF & \"Observação:\" & CRLF&Referente.Text & CRLF & \"Data:\" &Data.Text,\"Debitado com Sucesso\")";
Debug.ShouldStop(1048576);
anywheresoftware.b4a.keywords.Common.Msgbox("Valor:"+mostCurrent._valor.getText()+anywheresoftware.b4a.keywords.Common.CRLF+"Observação:"+anywheresoftware.b4a.keywords.Common.CRLF+mostCurrent._referente.getText()+anywheresoftware.b4a.keywords.Common.CRLF+"Data:"+mostCurrent._data.getText(),"Debitado com Sucesso",mostCurrent.activityBA);
 BA.debugLineNum = 55;BA.debugLine="Dim xValor As Float = Valor.Text";
Debug.ShouldStop(4194304);
_xvalor = (float)(Double.parseDouble(mostCurrent._valor.getText()));Debug.locals.put("xValor", _xvalor);Debug.locals.put("xValor", _xvalor);
 BA.debugLineNum = 57;BA.debugLine="Financeiro.saldo = Financeiro.saldo - xValor";
Debug.ShouldStop(16777216);
mostCurrent._financeiro._saldo = (float) (mostCurrent._financeiro._saldo-_xvalor);
 BA.debugLineNum = 59;BA.debugLine="Dim linha_extrato As String = Data.Text & \" \" & \"(-)\" & xValor & \"    \" & limita_campo(Referente.Text, 9)";
Debug.ShouldStop(67108864);
_linha_extrato = mostCurrent._data.getText()+" "+"(-)"+BA.NumberToString(_xvalor)+"    "+_limita_campo(mostCurrent._referente.getText(),(int) (9));Debug.locals.put("linha_extrato", _linha_extrato);Debug.locals.put("linha_extrato", _linha_extrato);
 BA.debugLineNum = 61;BA.debugLine="Financeiro.list_Extrato.Add(linha_extrato)";
Debug.ShouldStop(268435456);
mostCurrent._financeiro._list_extrato.Add((Object)(_linha_extrato));
 BA.debugLineNum = 64;BA.debugLine="result = Msgbox2(\"Deseja fazer outra operação?\",\"Aviso!\",\"Sim\",\"\",\"Nao\",Null)";
Debug.ShouldStop(-2147483648);
_result = anywheresoftware.b4a.keywords.Common.Msgbox2("Deseja fazer outra operação?","Aviso!","Sim","","Nao",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA);
 BA.debugLineNum = 66;BA.debugLine="If result = DialogResponse.POSITIVE Then";
Debug.ShouldStop(2);
if (_result==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
 BA.debugLineNum = 67;BA.debugLine="StartActivity(\"Debito\")";
Debug.ShouldStop(4);
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)("Debito"));
 }else {
 BA.debugLineNum = 70;BA.debugLine="StartActivity(\"Financeiro\")";
Debug.ShouldStop(32);
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)("Financeiro"));
 };
 };
 BA.debugLineNum = 78;BA.debugLine="End Sub";
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
public static String  _button_voltar_click() throws Exception{
		Debug.PushSubsStack("Button_Voltar_Click (debito) ","debito",4,mostCurrent.activityBA,mostCurrent);
try {
 BA.debugLineNum = 42;BA.debugLine="Sub Button_Voltar_Click";
Debug.ShouldStop(512);
 BA.debugLineNum = 43;BA.debugLine="Activity.Finish";
Debug.ShouldStop(1024);
mostCurrent._activity.Finish();
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
public static String  _categoria_itemclick(int _position,Object _value) throws Exception{
		Debug.PushSubsStack("Categoria_ItemClick (debito) ","debito",4,mostCurrent.activityBA,mostCurrent);
try {
Debug.locals.put("Position", _position);
Debug.locals.put("Value", _value);
 BA.debugLineNum = 87;BA.debugLine="Sub Categoria_ItemClick (Position As Int, Value As Object)";
Debug.ShouldStop(4194304);
 BA.debugLineNum = 88;BA.debugLine="Categoria.Add(\"Água\")";
Debug.ShouldStop(8388608);
mostCurrent._categoria.Add("Água");
 BA.debugLineNum = 89;BA.debugLine="Categoria.Add(\"Gás\")";
Debug.ShouldStop(16777216);
mostCurrent._categoria.Add("Gás");
 BA.debugLineNum = 90;BA.debugLine="Categoria.Add(\"Luz\")";
Debug.ShouldStop(33554432);
mostCurrent._categoria.Add("Luz");
 BA.debugLineNum = 91;BA.debugLine="Categoria.Add(\"Combustível\")";
Debug.ShouldStop(67108864);
mostCurrent._categoria.Add("Combustível");
 BA.debugLineNum = 92;BA.debugLine="Categoria.Add(\"Vestuário\")";
Debug.ShouldStop(134217728);
mostCurrent._categoria.Add("Vestuário");
 BA.debugLineNum = 93;BA.debugLine="Categoria.Add(\"Alimentação\")";
Debug.ShouldStop(268435456);
mostCurrent._categoria.Add("Alimentação");
 BA.debugLineNum = 94;BA.debugLine="Categoria.Add(\"Móveis\")";
Debug.ShouldStop(536870912);
mostCurrent._categoria.Add("Móveis");
 BA.debugLineNum = 95;BA.debugLine="Categoria.Add(\"Materiais De Consatrução\")";
Debug.ShouldStop(1073741824);
mostCurrent._categoria.Add("Materiais De Consatrução");
 BA.debugLineNum = 96;BA.debugLine="End Sub";
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
public static String  _globals() throws Exception{
 //BA.debugLineNum = 12;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 16;BA.debugLine="Dim result As Int";
_result = 0;
 //BA.debugLineNum = 17;BA.debugLine="Dim Valor As EditText";
mostCurrent._valor = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 18;BA.debugLine="Dim Button_Voltar As Button";
mostCurrent._button_voltar = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 19;BA.debugLine="Dim Button_Debitar As Button";
mostCurrent._button_debitar = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 20;BA.debugLine="Dim Data As EditText";
mostCurrent._data = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 21;BA.debugLine="Private Referente As EditText";
mostCurrent._referente = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Private Categoria As Spinner";
mostCurrent._categoria = new anywheresoftware.b4a.objects.SpinnerWrapper();
 //BA.debugLineNum = 23;BA.debugLine="End Sub";
return "";
}
public static String  _limita_campo(String _texto,int _qte_caracteres) throws Exception{
		Debug.PushSubsStack("limita_campo (debito) ","debito",4,mostCurrent.activityBA,mostCurrent);
try {
Debug.locals.put("texto", _texto);
Debug.locals.put("qte_caracteres", _qte_caracteres);
 BA.debugLineNum = 80;BA.debugLine="Sub limita_campo(texto As String, qte_caracteres As Int)";
Debug.ShouldStop(32768);
 BA.debugLineNum = 81;BA.debugLine="If texto.Length > qte_caracteres Then";
Debug.ShouldStop(65536);
if (_texto.length()>_qte_caracteres) { 
 BA.debugLineNum = 82;BA.debugLine="texto = texto.SubString2(1,qte_caracteres)";
Debug.ShouldStop(131072);
_texto = _texto.substring((int) (1),_qte_caracteres);Debug.locals.put("texto", _texto);
 };
 BA.debugLineNum = 85;BA.debugLine="Return texto";
Debug.ShouldStop(1048576);
if (true) return _texto;
 BA.debugLineNum = 86;BA.debugLine="End Sub";
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
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 10;BA.debugLine="End Sub";
return "";
}
}
