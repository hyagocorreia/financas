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

public class editar extends Activity implements B4AActivity{
	public static editar mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "Financas.Pessoais", "Financas.Pessoais.editar");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (editar).");
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
		activityBA = new BA(this, layout, processBA, "Financas.Pessoais", "Financas.Pessoais.editar");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "Financas.Pessoais.editar", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (editar) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (editar) Resume **");
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
		return editar.class;
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
        BA.LogInfo("** Activity (editar) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            BA.LogInfo("** Activity (editar) Resume **");
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
public anywheresoftware.b4a.objects.EditTextWrapper _edittext_nome = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _checkbox_alterarsenha = null;
public anywheresoftware.b4a.objects.EditTextWrapper _edittext_senhaantiga = null;
public anywheresoftware.b4a.objects.EditTextWrapper _edittext_novasenha1 = null;
public anywheresoftware.b4a.objects.EditTextWrapper _edittext_novasenha2 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button_salvar = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button_cancelar = null;
public anywheresoftware.b4a.objects.EditTextWrapper _edittext_username = null;
public static String _linha1 = "";
public static String _linha2 = "";
public static String _linha3 = "";
public static String _linha4 = "";
public Financas.Pessoais.main _main = null;
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
  public Object[] GetGlobals() {
		return new Object[] {"Activity",mostCurrent._activity,"AddCategoria",Debug.moduleToString(Financas.Pessoais.addcategoria.class),"Button_Cancelar",mostCurrent._button_cancelar,"Button_Salvar",mostCurrent._button_salvar,"Cadastro",Debug.moduleToString(Financas.Pessoais.cadastro.class),"Calculadora",Debug.moduleToString(Financas.Pessoais.calculadora.class),"CheckBox_AlterarSenha",mostCurrent._checkbox_alterarsenha,"Creditos",Debug.moduleToString(Financas.Pessoais.creditos.class),"Debitos",Debug.moduleToString(Financas.Pessoais.debitos.class),"EditText_Nome",mostCurrent._edittext_nome,"EditText_NovaSenha1",mostCurrent._edittext_novasenha1,"EditText_NovaSenha2",mostCurrent._edittext_novasenha2,"EditText_SenhaAntiga",mostCurrent._edittext_senhaantiga,"EditText_UserName",mostCurrent._edittext_username,"Excluir",Debug.moduleToString(Financas.Pessoais.excluir.class),"Extrato",Debug.moduleToString(Financas.Pessoais.extrato.class),"Financeiro",Debug.moduleToString(Financas.Pessoais.financeiro.class),"linha1",mostCurrent._linha1,"linha2",mostCurrent._linha2,"linha3",mostCurrent._linha3,"linha4",mostCurrent._linha4,"Main",Debug.moduleToString(Financas.Pessoais.main.class),"Menu",Debug.moduleToString(Financas.Pessoais.menu.class),"Remover_Categoria",Debug.moduleToString(Financas.Pessoais.remover_categoria.class),"Total",Debug.moduleToString(Financas.Pessoais.total.class),"Utilitarios",Debug.moduleToString(Financas.Pessoais.utilitarios.class)};
}

public static void initializeProcessGlobals() {
             try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
}
public static String  _activity_create(boolean _firsttime) throws Exception{
		Debug.PushSubsStack("Activity_Create (editar) ","editar",14,mostCurrent.activityBA,mostCurrent);
try {
Debug.locals.put("FirstTime", _firsttime);
 BA.debugLineNum = 23;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
Debug.ShouldStop(4194304);
 BA.debugLineNum = 24;BA.debugLine="Activity.LoadLayout(\"Layout_Editar\")";
Debug.ShouldStop(8388608);
mostCurrent._activity.LoadLayout("Layout_Editar",mostCurrent.activityBA);
 BA.debugLineNum = 26;BA.debugLine="linha1 = Main.Pers.GetUsuario(Main.Pers.Logado)";
Debug.ShouldStop(33554432);
mostCurrent._linha1 = BA.ObjectToString(mostCurrent._main._pers._getusuario(mostCurrent._main._pers._logado()));
 BA.debugLineNum = 27;BA.debugLine="linha2 = linha1.SubString2(0,linha1.IndexOf(\";\"))";
Debug.ShouldStop(67108864);
mostCurrent._linha2 = mostCurrent._linha1.substring((int) (0),mostCurrent._linha1.indexOf(";"));
 BA.debugLineNum = 28;BA.debugLine="linha3 = linha1.SubString2(linha1.IndexOf(\";\")+1,linha1.LastIndexOf(\";\"))";
Debug.ShouldStop(134217728);
mostCurrent._linha3 = mostCurrent._linha1.substring((int) (mostCurrent._linha1.indexOf(";")+1),mostCurrent._linha1.lastIndexOf(";"));
 BA.debugLineNum = 29;BA.debugLine="linha4 = linha1.SubString(linha1.LastIndexOf(\";\")+1)";
Debug.ShouldStop(268435456);
mostCurrent._linha4 = mostCurrent._linha1.substring((int) (mostCurrent._linha1.lastIndexOf(";")+1));
 BA.debugLineNum = 31;BA.debugLine="EditText_Nome.Text = linha4";
Debug.ShouldStop(1073741824);
mostCurrent._edittext_nome.setText((Object)(mostCurrent._linha4));
 BA.debugLineNum = 32;BA.debugLine="EditText_UserName.Text = linha2";
Debug.ShouldStop(-2147483648);
mostCurrent._edittext_username.setText((Object)(mostCurrent._linha2));
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
		Debug.PushSubsStack("Activity_Pause (editar) ","editar",14,mostCurrent.activityBA,mostCurrent);
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
		Debug.PushSubsStack("Activity_Resume (editar) ","editar",14,mostCurrent.activityBA,mostCurrent);
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
public static String  _button_cancelar_click() throws Exception{
		Debug.PushSubsStack("Button_Cancelar_Click (editar) ","editar",14,mostCurrent.activityBA,mostCurrent);
try {
 BA.debugLineNum = 77;BA.debugLine="Sub Button_Cancelar_Click";
Debug.ShouldStop(4096);
 BA.debugLineNum = 78;BA.debugLine="StartActivity(\"Menu\")";
Debug.ShouldStop(8192);
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)("Menu"));
 BA.debugLineNum = 79;BA.debugLine="Activity.Finish";
Debug.ShouldStop(16384);
mostCurrent._activity.Finish();
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
public static String  _button_salvar_click() throws Exception{
		Debug.PushSubsStack("Button_Salvar_Click (editar) ","editar",14,mostCurrent.activityBA,mostCurrent);
try {
 BA.debugLineNum = 53;BA.debugLine="Sub Button_Salvar_Click";
Debug.ShouldStop(1048576);
 BA.debugLineNum = 54;BA.debugLine="If CheckBox_AlterarSenha.Checked = True Then";
Debug.ShouldStop(2097152);
if (mostCurrent._checkbox_alterarsenha.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 BA.debugLineNum = 55;BA.debugLine="If EditText_SenhaAntiga.Text = linha3 Then";
Debug.ShouldStop(4194304);
if ((mostCurrent._edittext_senhaantiga.getText()).equals(mostCurrent._linha3)) { 
 BA.debugLineNum = 56;BA.debugLine="If Main.Pers.Excluir_Login(linha2,linha3) AND	Main.Pers.Criar_Login(EditText_Nome.Text,EditText_UserName.Text,EditText_NovaSenha1.Text,EditText_NovaSenha2.Text) Then";
Debug.ShouldStop(8388608);
if (mostCurrent._main._pers._excluir_login(mostCurrent._linha2,mostCurrent._linha3) && mostCurrent._main._pers._criar_login(mostCurrent._edittext_nome.getText(),mostCurrent._edittext_username.getText(),mostCurrent._edittext_novasenha1.getText(),mostCurrent._edittext_novasenha2.getText())) { 
 BA.debugLineNum = 57;BA.debugLine="Msgbox(\"Nome: \"&EditText_Nome.Text & CRLF & \"Username: \"&EditText_UserName.Text, \"Alterado com sucesso!\")";
Debug.ShouldStop(16777216);
anywheresoftware.b4a.keywords.Common.Msgbox("Nome: "+mostCurrent._edittext_nome.getText()+anywheresoftware.b4a.keywords.Common.CRLF+"Username: "+mostCurrent._edittext_username.getText(),"Alterado com sucesso!",mostCurrent.activityBA);
 BA.debugLineNum = 58;BA.debugLine="StartActivity(\"Menu\")";
Debug.ShouldStop(33554432);
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)("Menu"));
 BA.debugLineNum = 59;BA.debugLine="Activity.Finish";
Debug.ShouldStop(67108864);
mostCurrent._activity.Finish();
 };
 }else {
 BA.debugLineNum = 62;BA.debugLine="Msgbox(\"Senha incorreta!\", \"Atenção!\")";
Debug.ShouldStop(536870912);
anywheresoftware.b4a.keywords.Common.Msgbox("Senha incorreta!","Atenção!",mostCurrent.activityBA);
 };
 }else {
 BA.debugLineNum = 65;BA.debugLine="If EditText_SenhaAntiga.Text = linha3 Then";
Debug.ShouldStop(1);
if ((mostCurrent._edittext_senhaantiga.getText()).equals(mostCurrent._linha3)) { 
 BA.debugLineNum = 66;BA.debugLine="If Main.Pers.Excluir_Login(linha2,linha3) AND	Main.Pers.Criar_Login(EditText_Nome.Text,EditText_UserName.Text,EditText_SenhaAntiga.Text,EditText_SenhaAntiga.Text) Then";
Debug.ShouldStop(2);
if (mostCurrent._main._pers._excluir_login(mostCurrent._linha2,mostCurrent._linha3) && mostCurrent._main._pers._criar_login(mostCurrent._edittext_nome.getText(),mostCurrent._edittext_username.getText(),mostCurrent._edittext_senhaantiga.getText(),mostCurrent._edittext_senhaantiga.getText())) { 
 BA.debugLineNum = 67;BA.debugLine="Msgbox(\"Nome: \"&EditText_Nome.Text & CRLF & \"Username: \"&EditText_UserName.Text, \"Alterado com sucesso!\")";
Debug.ShouldStop(4);
anywheresoftware.b4a.keywords.Common.Msgbox("Nome: "+mostCurrent._edittext_nome.getText()+anywheresoftware.b4a.keywords.Common.CRLF+"Username: "+mostCurrent._edittext_username.getText(),"Alterado com sucesso!",mostCurrent.activityBA);
 BA.debugLineNum = 68;BA.debugLine="StartActivity(\"Menu\")";
Debug.ShouldStop(8);
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)("Menu"));
 BA.debugLineNum = 69;BA.debugLine="Activity.Finish";
Debug.ShouldStop(16);
mostCurrent._activity.Finish();
 };
 }else {
 BA.debugLineNum = 72;BA.debugLine="Msgbox(\"Senha incorreta!\", \"Atenção!\")";
Debug.ShouldStop(128);
anywheresoftware.b4a.keywords.Common.Msgbox("Senha incorreta!","Atenção!",mostCurrent.activityBA);
 };
 };
 BA.debugLineNum = 75;BA.debugLine="End Sub";
Debug.ShouldStop(1024);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _checkbox_alterarsenha_checkedchange(boolean _checked) throws Exception{
		Debug.PushSubsStack("CheckBox_AlterarSenha_CheckedChange (editar) ","editar",14,mostCurrent.activityBA,mostCurrent);
try {
Debug.locals.put("Checked", _checked);
 BA.debugLineNum = 43;BA.debugLine="Sub CheckBox_AlterarSenha_CheckedChange(Checked As Boolean)";
Debug.ShouldStop(1024);
 BA.debugLineNum = 44;BA.debugLine="If CheckBox_AlterarSenha.Checked Then";
Debug.ShouldStop(2048);
if (mostCurrent._checkbox_alterarsenha.getChecked()) { 
 BA.debugLineNum = 45;BA.debugLine="EditText_NovaSenha1.Visible = True";
Debug.ShouldStop(4096);
mostCurrent._edittext_novasenha1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 BA.debugLineNum = 46;BA.debugLine="EditText_NovaSenha2.Visible = True";
Debug.ShouldStop(8192);
mostCurrent._edittext_novasenha2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 }else {
 BA.debugLineNum = 48;BA.debugLine="EditText_NovaSenha1.Visible = False";
Debug.ShouldStop(32768);
mostCurrent._edittext_novasenha1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 BA.debugLineNum = 49;BA.debugLine="EditText_NovaSenha2.Visible = False";
Debug.ShouldStop(65536);
mostCurrent._edittext_novasenha2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 BA.debugLineNum = 51;BA.debugLine="End Sub";
Debug.ShouldStop(262144);
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
 //BA.debugLineNum = 12;BA.debugLine="Private EditText_Nome As EditText";
mostCurrent._edittext_nome = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 13;BA.debugLine="Private CheckBox_AlterarSenha As CheckBox";
mostCurrent._checkbox_alterarsenha = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 14;BA.debugLine="Private EditText_SenhaAntiga As EditText";
mostCurrent._edittext_senhaantiga = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 15;BA.debugLine="Private EditText_NovaSenha1 As EditText";
mostCurrent._edittext_novasenha1 = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 16;BA.debugLine="Private EditText_NovaSenha2 As EditText";
mostCurrent._edittext_novasenha2 = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 17;BA.debugLine="Private Button_Salvar As Button";
mostCurrent._button_salvar = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 18;BA.debugLine="Private Button_Cancelar As Button";
mostCurrent._button_cancelar = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 19;BA.debugLine="Private EditText_UserName As EditText";
mostCurrent._edittext_username = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 20;BA.debugLine="Dim linha1,linha2,linha3,linha4 As String";
mostCurrent._linha1 = "";
mostCurrent._linha2 = "";
mostCurrent._linha3 = "";
mostCurrent._linha4 = "";
 //BA.debugLineNum = 21;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 8;BA.debugLine="End Sub";
return "";
}
}
