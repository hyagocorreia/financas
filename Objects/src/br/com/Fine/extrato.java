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
			processBA = new BA(this.getApplicationContext(), null, null, "br.com.Fine", "br.com.Fine.extrato");
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
		activityBA = new BA(this, layout, processBA, "br.com.Fine", "br.com.Fine.extrato");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "br.com.Fine.extrato", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density);
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
public anywheresoftware.b4a.objects.ListViewWrapper _listview_extrato1 = null;
public anywheresoftware.b4a.objects.ListViewWrapper _listview_extrato2 = null;
public anywheresoftware.b4a.objects.ListViewWrapper _listview_extrato3 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label_saldoatual = null;
public anywheresoftware.b4a.objects.TabHostWrapper _tabhost_transacoes = null;
public anywheresoftware.b4a.objects.SpinnerWrapper _spinner_categorias = null;
public anywheresoftware.b4a.objects.SpinnerWrapper _spinner_meses = null;
public anywheresoftware.b4a.objects.LabelWrapper _label_saldo_mes = null;
public br.com.Fine.main _main = null;
public br.com.Fine.cadastro _cadastro = null;
public br.com.Fine.financeiro _financeiro = null;
public br.com.Fine.creditos _creditos = null;
public br.com.Fine.debitos _debitos = null;
public br.com.Fine.total _total = null;
public br.com.Fine.utilitarios _utilitarios = null;
public br.com.Fine.menu _menu = null;
public br.com.Fine.calculadora _calculadora = null;
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
anywheresoftware.b4a.objects.collections.List _transacoes = null;
int _i = 0;
String _linha1 = "";
String _linha2 = "";
String _linha3 = "";
String _linha4 = "";
double _valor = 0;
anywheresoftware.b4a.objects.collections.List _listinha = null;
anywheresoftware.b4a.objects.drawable.ColorDrawable _cor = null;
 //BA.debugLineNum = 22;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 23;BA.debugLine="Activity.LoadLayout(\"Layout_Extrato\")";
mostCurrent._activity.LoadLayout("Layout_Extrato",mostCurrent.activityBA);
 //BA.debugLineNum = 25;BA.debugLine="Spinner_Categorias.AddAll(Main.Pers.GetCategorias)";
mostCurrent._spinner_categorias.AddAll(mostCurrent._main._pers._getcategorias());
 //BA.debugLineNum = 26;BA.debugLine="Label_SaldoAtual.Text = \"Saldo Atual (R$): \" & NumberFormat(Main.Pers.GetSaldo,1,2)";
mostCurrent._label_saldoatual.setText((Object)("Saldo Atual (R$): "+anywheresoftware.b4a.keywords.Common.NumberFormat((double)(Double.parseDouble(mostCurrent._main._pers._getsaldo())),(int) (1),(int) (2))));
 //BA.debugLineNum = 27;BA.debugLine="Dim transacoes As List";
_transacoes = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 28;BA.debugLine="transacoes.Initialize";
_transacoes.Initialize();
 //BA.debugLineNum = 29;BA.debugLine="transacoes = Main.Pers.GetTransacoes(Main.Pers.Logado)";
_transacoes = mostCurrent._main._pers._gettransacoes(mostCurrent._main._pers._logado());
 //BA.debugLineNum = 30;BA.debugLine="For i = 0 To transacoes.Size -1";
{
final int step20 = 1;
final int limit20 = (int) (_transacoes.getSize()-1);
for (_i = (int) (0); (step20 > 0 && _i <= limit20) || (step20 < 0 && _i >= limit20); _i = ((int)(0 + _i + step20))) {
 //BA.debugLineNum = 31;BA.debugLine="Dim linha1,linha2,linha3,linha4 As String";
_linha1 = "";
_linha2 = "";
_linha3 = "";
_linha4 = "";
 //BA.debugLineNum = 32;BA.debugLine="linha1 = transacoes.Get(i)";
_linha1 = BA.ObjectToString(_transacoes.Get(_i));
 //BA.debugLineNum = 33;BA.debugLine="linha2 = linha1.SubString2(0,linha1.IndexOf(\";\"))";
_linha2 = _linha1.substring((int) (0),_linha1.indexOf(";"));
 //BA.debugLineNum = 34;BA.debugLine="linha3 = linha1.SubString2(linha1.IndexOf(\";\")+1,linha1.LastIndexOf(\";\"))";
_linha3 = _linha1.substring((int) (_linha1.indexOf(";")+1),_linha1.lastIndexOf(";"));
 //BA.debugLineNum = 35;BA.debugLine="linha4 = linha1.SubString(linha1.LastIndexOf(\";\")+1)";
_linha4 = _linha1.substring((int) (_linha1.lastIndexOf(";")+1));
 //BA.debugLineNum = 36;BA.debugLine="Dim valor As Double";
_valor = 0;
 //BA.debugLineNum = 37;BA.debugLine="valor = linha2";
_valor = (double)(Double.parseDouble(_linha2));
 //BA.debugLineNum = 38;BA.debugLine="If valor < 0 Then";
if (_valor<0) { 
 //BA.debugLineNum = 39;BA.debugLine="ListView_Extrato1.AddTwoLinesAndBitmap(\"R$\"&NumberFormat2((valor*(-1)),1,2,2,True), linha3 & \" - \" & linha4,LoadBitmap(File.DirAssets,\"debito.png\"))";
mostCurrent._listview_extrato1.AddTwoLinesAndBitmap("R$"+anywheresoftware.b4a.keywords.Common.NumberFormat2((_valor*(-1)),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True),_linha3+" - "+_linha4,(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"debito.png").getObject()));
 }else {
 //BA.debugLineNum = 41;BA.debugLine="ListView_Extrato1.AddTwoLinesAndBitmap(\"R$\"&NumberFormat2(linha2,1,2,2,True), linha3 & \" - \" & linha4,LoadBitmap(File.DirAssets,\"credito.png\"))";
mostCurrent._listview_extrato1.AddTwoLinesAndBitmap("R$"+anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(Double.parseDouble(_linha2)),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True),_linha3+" - "+_linha4,(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"credito.png").getObject()));
 };
 //BA.debugLineNum = 43;BA.debugLine="ListView_Extrato1.FastScrollEnabled = True";
mostCurrent._listview_extrato1.setFastScrollEnabled(anywheresoftware.b4a.keywords.Common.True);
 }
};
 //BA.debugLineNum = 46;BA.debugLine="Dim listinha As List";
_listinha = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 47;BA.debugLine="listinha.Initialize";
_listinha.Initialize();
 //BA.debugLineNum = 48;BA.debugLine="listinha.AddAll(Array As String(\"Selecione o mês\",\"Janeiro\",\"Fevereiro\",\"Março\",\"Abril\",\"Maio\",\"Junho\",\"Julho\",\"Agosto\",\"Setembro\",\"Outubro\",\"Novembro\",\"Dezembro\"))";
_listinha.AddAll(anywheresoftware.b4a.keywords.Common.ArrayToList(new String[]{"Selecione o mês","Janeiro","Fevereiro","Março","Abril","Maio","Junho","Julho","Agosto","Setembro","Outubro","Novembro","Dezembro"}));
 //BA.debugLineNum = 49;BA.debugLine="Spinner_Meses.AddAll(listinha)";
mostCurrent._spinner_meses.AddAll(_listinha);
 //BA.debugLineNum = 50;BA.debugLine="Dim cor As ColorDrawable";
_cor = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 51;BA.debugLine="cor.Initialize(Colors.RGB(139,0,0),0)";
_cor.Initialize(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (139),(int) (0),(int) (0)),(int) (0));
 //BA.debugLineNum = 52;BA.debugLine="TabHost_Transacoes.Background = cor";
mostCurrent._tabhost_transacoes.setBackground((android.graphics.drawable.Drawable)(_cor.getObject()));
 //BA.debugLineNum = 53;BA.debugLine="TabHost_Transacoes.AddTabWithIcon2(\"\",LoadBitmap(File.DirAssets,\"transico.png\"),LoadBitmap(File.DirAssets,\"transico.png\"),ListView_Extrato1)";
mostCurrent._tabhost_transacoes.AddTabWithIcon2("",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"transico.png").getObject()),(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"transico.png").getObject()),(android.view.View)(mostCurrent._listview_extrato1.getObject()));
 //BA.debugLineNum = 54;BA.debugLine="TabHost_Transacoes.AddTabWithIcon2(\"\",LoadBitmap(File.DirAssets,\"mesico.png\"),LoadBitmap(File.DirAssets,\"mesico.png\"),Spinner_Meses)";
mostCurrent._tabhost_transacoes.AddTabWithIcon2("",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mesico.png").getObject()),(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mesico.png").getObject()),(android.view.View)(mostCurrent._spinner_meses.getObject()));
 //BA.debugLineNum = 55;BA.debugLine="TabHost_Transacoes.AddTabWithIcon2(\"\",LoadBitmap(File.DirAssets,\"categico.png\"),LoadBitmap(File.DirAssets,\"categico.png\"),Spinner_Categorias)";
mostCurrent._tabhost_transacoes.AddTabWithIcon2("",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"categico.png").getObject()),(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"categico.png").getObject()),(android.view.View)(mostCurrent._spinner_categorias.getObject()));
 //BA.debugLineNum = 56;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 62;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 64;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 58;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 60;BA.debugLine="End Sub";
return "";
}
public static String  _button_voltar_click() throws Exception{
 //BA.debugLineNum = 66;BA.debugLine="Sub Button_Voltar_Click";
 //BA.debugLineNum = 67;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 68;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 10;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 11;BA.debugLine="Dim Button_Voltar As Button";
mostCurrent._button_voltar = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 12;BA.debugLine="Dim ListView_Extrato1 As ListView";
mostCurrent._listview_extrato1 = new anywheresoftware.b4a.objects.ListViewWrapper();
 //BA.debugLineNum = 13;BA.debugLine="Dim ListView_Extrato2 As ListView";
mostCurrent._listview_extrato2 = new anywheresoftware.b4a.objects.ListViewWrapper();
 //BA.debugLineNum = 14;BA.debugLine="Dim ListView_Extrato3 As ListView";
mostCurrent._listview_extrato3 = new anywheresoftware.b4a.objects.ListViewWrapper();
 //BA.debugLineNum = 15;BA.debugLine="Private Label_SaldoAtual As Label";
mostCurrent._label_saldoatual = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 16;BA.debugLine="Private TabHost_Transacoes As TabHost";
mostCurrent._tabhost_transacoes = new anywheresoftware.b4a.objects.TabHostWrapper();
 //BA.debugLineNum = 17;BA.debugLine="Private Spinner_Categorias As Spinner";
mostCurrent._spinner_categorias = new anywheresoftware.b4a.objects.SpinnerWrapper();
 //BA.debugLineNum = 18;BA.debugLine="Private Spinner_Meses As Spinner";
mostCurrent._spinner_meses = new anywheresoftware.b4a.objects.SpinnerWrapper();
 //BA.debugLineNum = 19;BA.debugLine="Private Label_Saldo_Mes As Label";
mostCurrent._label_saldo_mes = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 20;BA.debugLine="End Sub";
return "";
}
public static String  _listview_extrato1_itemlongclick(int _position,Object _value) throws Exception{
 //BA.debugLineNum = 70;BA.debugLine="Sub ListView_Extrato1_ItemLongClick (Position As Int, Value As Object)";
 //BA.debugLineNum = 71;BA.debugLine="If Msgbox2(\"Deseja excluir a transação?\", \"Excluir\", \"Sim\", \"\", \"Não\", Null) = DialogResponse.POSITIVE Then";
if (anywheresoftware.b4a.keywords.Common.Msgbox2("Deseja excluir a transação?","Excluir","Sim","","Não",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA)==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
 //BA.debugLineNum = 72;BA.debugLine="Main.Pers.Remover_Transacao(Position)";
mostCurrent._main._pers._remover_transacao(_position);
 //BA.debugLineNum = 73;BA.debugLine="ListView_Extrato1.RemoveAt(Position)";
mostCurrent._listview_extrato1.RemoveAt(_position);
 //BA.debugLineNum = 74;BA.debugLine="Label_SaldoAtual.Text = \"Saldo Atual (R$): \" & NumberFormat(Main.Pers.GetSaldo,1,2)";
mostCurrent._label_saldoatual.setText((Object)("Saldo Atual (R$): "+anywheresoftware.b4a.keywords.Common.NumberFormat((double)(Double.parseDouble(mostCurrent._main._pers._getsaldo())),(int) (1),(int) (2))));
 };
 //BA.debugLineNum = 76;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 8;BA.debugLine="End Sub";
return "";
}
public static String  _spinner_categorias_itemclick(int _position,Object _value) throws Exception{
String _linha1 = "";
String _linha2 = "";
String _linha3 = "";
String _linha4 = "";
String _l4 = "";
String _str = "";
anywheresoftware.b4a.objects.collections.List _transacoes = null;
int _i = 0;
double _valor = 0;
 //BA.debugLineNum = 109;BA.debugLine="Sub Spinner_Categorias_ItemClick (Position As Int, Value As Object)";
 //BA.debugLineNum = 110;BA.debugLine="Dim linha1,linha2,linha3,linha4,l4 As String";
_linha1 = "";
_linha2 = "";
_linha3 = "";
_linha4 = "";
_l4 = "";
 //BA.debugLineNum = 111;BA.debugLine="Dim Str As String = Spinner_Categorias.GetItem(Position)";
_str = mostCurrent._spinner_categorias.GetItem(_position);
 //BA.debugLineNum = 112;BA.debugLine="l4 = Str.SubString(Str.LastIndexOf(\";\")+1)";
_l4 = _str.substring((int) (_str.lastIndexOf(";")+1));
 //BA.debugLineNum = 114;BA.debugLine="ListView_Extrato3.Clear";
mostCurrent._listview_extrato3.Clear();
 //BA.debugLineNum = 115;BA.debugLine="Dim transacoes As List = Main.Pers.GetTransacoes(Main.Pers.Logado)";
_transacoes = new anywheresoftware.b4a.objects.collections.List();
_transacoes = mostCurrent._main._pers._gettransacoes(mostCurrent._main._pers._logado());
 //BA.debugLineNum = 116;BA.debugLine="For i = 0 To transacoes.Size -1";
{
final int step95 = 1;
final int limit95 = (int) (_transacoes.getSize()-1);
for (_i = (int) (0); (step95 > 0 && _i <= limit95) || (step95 < 0 && _i >= limit95); _i = ((int)(0 + _i + step95))) {
 //BA.debugLineNum = 117;BA.debugLine="linha1 = transacoes.Get(i)";
_linha1 = BA.ObjectToString(_transacoes.Get(_i));
 //BA.debugLineNum = 118;BA.debugLine="linha2 = linha1.SubString2(0,linha1.IndexOf(\";\"))";
_linha2 = _linha1.substring((int) (0),_linha1.indexOf(";"));
 //BA.debugLineNum = 119;BA.debugLine="linha3 = linha1.SubString2(linha1.IndexOf(\";\")+1,linha1.LastIndexOf(\";\"))";
_linha3 = _linha1.substring((int) (_linha1.indexOf(";")+1),_linha1.lastIndexOf(";"));
 //BA.debugLineNum = 120;BA.debugLine="linha4 = linha1.SubString(linha1.LastIndexOf(\";\")+1)";
_linha4 = _linha1.substring((int) (_linha1.lastIndexOf(";")+1));
 //BA.debugLineNum = 121;BA.debugLine="If linha4 = l4 Then";
if ((_linha4).equals(_l4)) { 
 //BA.debugLineNum = 122;BA.debugLine="Dim valor As Double";
_valor = 0;
 //BA.debugLineNum = 123;BA.debugLine="valor = linha2";
_valor = (double)(Double.parseDouble(_linha2));
 //BA.debugLineNum = 124;BA.debugLine="If valor < 0 Then";
if (_valor<0) { 
 //BA.debugLineNum = 125;BA.debugLine="ListView_Extrato3.AddTwoLinesAndBitmap(\"R$\"&NumberFormat2((valor*(-1)),1,2,2,True), linha3 & \" - \" & linha4,LoadBitmap(File.DirAssets,\"debito.png\"))";
mostCurrent._listview_extrato3.AddTwoLinesAndBitmap("R$"+anywheresoftware.b4a.keywords.Common.NumberFormat2((_valor*(-1)),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True),_linha3+" - "+_linha4,(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"debito.png").getObject()));
 }else {
 //BA.debugLineNum = 127;BA.debugLine="ListView_Extrato3.AddTwoLinesAndBitmap(\"R$\"&NumberFormat2(linha2,1,2,2,True), linha3 & \" - \" & linha4,LoadBitmap(File.DirAssets,\"credito.png\"))";
mostCurrent._listview_extrato3.AddTwoLinesAndBitmap("R$"+anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(Double.parseDouble(_linha2)),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True),_linha3+" - "+_linha4,(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"credito.png").getObject()));
 };
 //BA.debugLineNum = 130;BA.debugLine="ListView_Extrato3.FastScrollEnabled = True";
mostCurrent._listview_extrato3.setFastScrollEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 131;BA.debugLine="ListView_Extrato3.Visible = True";
mostCurrent._listview_extrato3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 }
};
 //BA.debugLineNum = 134;BA.debugLine="End Sub";
return "";
}
public static String  _spinner_meses_itemclick(int _position,Object _value) throws Exception{
String _linha1 = "";
String _linha2 = "";
String _linha3 = "";
String _linha4 = "";
String _mes_linha = "";
double _valor = 0;
double _valor1 = 0;
anywheresoftware.b4a.objects.collections.List _transacoes = null;
int _i = 0;
int _mes_ = 0;
 //BA.debugLineNum = 78;BA.debugLine="Sub Spinner_Meses_ItemClick (Position As Int, Value As Object)";
 //BA.debugLineNum = 79;BA.debugLine="Dim linha1,linha2,linha3,linha4 As String";
_linha1 = "";
_linha2 = "";
_linha3 = "";
_linha4 = "";
 //BA.debugLineNum = 80;BA.debugLine="Dim mes_linha As String";
_mes_linha = "";
 //BA.debugLineNum = 81;BA.debugLine="Dim valor,valor1 As Double";
_valor = 0;
_valor1 = 0;
 //BA.debugLineNum = 82;BA.debugLine="valor = 0.0";
_valor = 0.0;
 //BA.debugLineNum = 83;BA.debugLine="ListView_Extrato2.Clear";
mostCurrent._listview_extrato2.Clear();
 //BA.debugLineNum = 84;BA.debugLine="Dim transacoes As List = Main.Pers.GetTransacoes(Main.Pers.Logado)";
_transacoes = new anywheresoftware.b4a.objects.collections.List();
_transacoes = mostCurrent._main._pers._gettransacoes(mostCurrent._main._pers._logado());
 //BA.debugLineNum = 85;BA.debugLine="For i = 0 To transacoes.Size -1";
{
final int step67 = 1;
final int limit67 = (int) (_transacoes.getSize()-1);
for (_i = (int) (0); (step67 > 0 && _i <= limit67) || (step67 < 0 && _i >= limit67); _i = ((int)(0 + _i + step67))) {
 //BA.debugLineNum = 86;BA.debugLine="linha1 = transacoes.Get(i)";
_linha1 = BA.ObjectToString(_transacoes.Get(_i));
 //BA.debugLineNum = 87;BA.debugLine="linha2 = linha1.SubString2(0,linha1.IndexOf(\";\"))";
_linha2 = _linha1.substring((int) (0),_linha1.indexOf(";"));
 //BA.debugLineNum = 88;BA.debugLine="linha3 = linha1.SubString2(linha1.IndexOf(\";\")+1,linha1.LastIndexOf(\";\"))";
_linha3 = _linha1.substring((int) (_linha1.indexOf(";")+1),_linha1.lastIndexOf(";"));
 //BA.debugLineNum = 89;BA.debugLine="linha4 = linha1.SubString(linha1.LastIndexOf(\";\")+1)";
_linha4 = _linha1.substring((int) (_linha1.lastIndexOf(";")+1));
 //BA.debugLineNum = 90;BA.debugLine="mes_linha = linha1.SubString2(linha1.IndexOf(\"/\")+1,linha1.LastIndexOf(\"/\"))";
_mes_linha = _linha1.substring((int) (_linha1.indexOf("/")+1),_linha1.lastIndexOf("/"));
 //BA.debugLineNum = 91;BA.debugLine="Dim mes_ As Int = mes_linha";
_mes_ = (int)(Double.parseDouble(_mes_linha));
 //BA.debugLineNum = 92;BA.debugLine="If mes_ = Position Then";
if (_mes_==_position) { 
 //BA.debugLineNum = 93;BA.debugLine="valor = valor + linha2";
_valor = _valor+(double)(Double.parseDouble(_linha2));
 //BA.debugLineNum = 94;BA.debugLine="valor1 = linha2";
_valor1 = (double)(Double.parseDouble(_linha2));
 //BA.debugLineNum = 95;BA.debugLine="If valor1 < 0 Then";
if (_valor1<0) { 
 //BA.debugLineNum = 96;BA.debugLine="ListView_Extrato2.AddTwoLinesAndBitmap(\"R$\"&NumberFormat2((valor1*(-1)),1,2,2,True), linha3 & \" - \" & linha4,LoadBitmap(File.DirAssets,\"debito.png\"))";
mostCurrent._listview_extrato2.AddTwoLinesAndBitmap("R$"+anywheresoftware.b4a.keywords.Common.NumberFormat2((_valor1*(-1)),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True),_linha3+" - "+_linha4,(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"debito.png").getObject()));
 }else {
 //BA.debugLineNum = 98;BA.debugLine="ListView_Extrato2.AddTwoLinesAndBitmap(\"R$\"&NumberFormat2(linha2,1,2,2,True), linha3 & \" - \" & linha4,LoadBitmap(File.DirAssets,\"credito.png\"))";
mostCurrent._listview_extrato2.AddTwoLinesAndBitmap("R$"+anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(Double.parseDouble(_linha2)),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True),_linha3+" - "+_linha4,(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"credito.png").getObject()));
 };
 };
 }
};
 //BA.debugLineNum = 102;BA.debugLine="Label_Saldo_Mes.Text = \"Saldo do Mês (R$): \"& NumberFormat(valor,1,2)";
mostCurrent._label_saldo_mes.setText((Object)("Saldo do Mês (R$): "+anywheresoftware.b4a.keywords.Common.NumberFormat(_valor,(int) (1),(int) (2))));
 //BA.debugLineNum = 103;BA.debugLine="Label_Saldo_Mes.Visible = True";
mostCurrent._label_saldo_mes.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 104;BA.debugLine="ListView_Extrato2.FastScrollEnabled = True";
mostCurrent._listview_extrato2.setFastScrollEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 105;BA.debugLine="ListView_Extrato2.Visible = True";
mostCurrent._listview_extrato2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 107;BA.debugLine="End Sub";
return "";
}
public static String  _tabhost_transacoes_tabchanged() throws Exception{
 //BA.debugLineNum = 136;BA.debugLine="Sub TabHost_Transacoes_TabChanged";
 //BA.debugLineNum = 137;BA.debugLine="If TabHost_Transacoes.CurrentTab = 0 Then";
if (mostCurrent._tabhost_transacoes.getCurrentTab()==0) { 
 //BA.debugLineNum = 138;BA.debugLine="ListView_Extrato3.Visible = False";
mostCurrent._listview_extrato3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 139;BA.debugLine="ListView_Extrato2.Visible = False";
mostCurrent._listview_extrato2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 140;BA.debugLine="Label_Saldo_Mes.Visible = False";
mostCurrent._label_saldo_mes.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 141;BA.debugLine="Label_SaldoAtual.Visible = True";
mostCurrent._label_saldoatual.setVisible(anywheresoftware.b4a.keywords.Common.True);
 }else if(mostCurrent._tabhost_transacoes.getCurrentTab()==1) { 
 //BA.debugLineNum = 143;BA.debugLine="ListView_Extrato3.Visible = False";
mostCurrent._listview_extrato3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 144;BA.debugLine="ListView_Extrato1.Visible = False";
mostCurrent._listview_extrato1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 145;BA.debugLine="Label_SaldoAtual.Visible = False";
mostCurrent._label_saldoatual.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 146;BA.debugLine="Label_Saldo_Mes.Visible = True";
mostCurrent._label_saldo_mes.setVisible(anywheresoftware.b4a.keywords.Common.True);
 }else if(mostCurrent._tabhost_transacoes.getCurrentTab()==2) { 
 //BA.debugLineNum = 148;BA.debugLine="ListView_Extrato2.Visible = False";
mostCurrent._listview_extrato2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 149;BA.debugLine="ListView_Extrato1.Visible = False";
mostCurrent._listview_extrato1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 150;BA.debugLine="Label_Saldo_Mes.Visible = False";
mostCurrent._label_saldo_mes.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 151;BA.debugLine="Label_SaldoAtual.Visible = False";
mostCurrent._label_saldoatual.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 153;BA.debugLine="End Sub";
return "";
}
}
