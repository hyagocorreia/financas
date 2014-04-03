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

public class graficos extends Activity implements B4AActivity{
	public static graficos mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "Financas.Pessoais", "Financas.Pessoais.graficos");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (graficos).");
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
		activityBA = new BA(this, layout, processBA, "Financas.Pessoais", "Financas.Pessoais.graficos");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "Financas.Pessoais.graficos", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (graficos) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (graficos) Resume **");
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
		return graficos.class;
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
        BA.LogInfo("** Activity (graficos) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            BA.LogInfo("** Activity (graficos) Resume **");
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
public anywheresoftware.b4a.objects.TabHostWrapper _tabhost_grafico = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlline1 = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlline2 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button_voltar = null;
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
public Financas.Pessoais.editar _editar = null;
public Financas.Pessoais.charts _charts = null;
  public Object[] GetGlobals() {
		return new Object[] {"Activity",mostCurrent._activity,"AddCategoria",Debug.moduleToString(Financas.Pessoais.addcategoria.class),"Button_Voltar",mostCurrent._button_voltar,"Cadastro",Debug.moduleToString(Financas.Pessoais.cadastro.class),"Calculadora",Debug.moduleToString(Financas.Pessoais.calculadora.class),"Charts",Debug.moduleToString(Financas.Pessoais.charts.class),"Creditos",Debug.moduleToString(Financas.Pessoais.creditos.class),"Debitos",Debug.moduleToString(Financas.Pessoais.debitos.class),"Editar",Debug.moduleToString(Financas.Pessoais.editar.class),"Excluir",Debug.moduleToString(Financas.Pessoais.excluir.class),"Extrato",Debug.moduleToString(Financas.Pessoais.extrato.class),"Financeiro",Debug.moduleToString(Financas.Pessoais.financeiro.class),"Main",Debug.moduleToString(Financas.Pessoais.main.class),"Menu",Debug.moduleToString(Financas.Pessoais.menu.class),"pnlLine1",mostCurrent._pnlline1,"pnlLine2",mostCurrent._pnlline2,"Remover_Categoria",Debug.moduleToString(Financas.Pessoais.remover_categoria.class),"TabHost_Grafico",mostCurrent._tabhost_grafico,"Total",Debug.moduleToString(Financas.Pessoais.total.class),"Utilitarios",Debug.moduleToString(Financas.Pessoais.utilitarios.class)};
}

public static void initializeProcessGlobals() {
             try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
}
public static String  _activity_create(boolean _firsttime) throws Exception{
		Debug.PushSubsStack("Activity_Create (graficos) ","graficos",17,mostCurrent.activityBA,mostCurrent);
try {
Debug.locals.put("FirstTime", _firsttime);
 BA.debugLineNum = 16;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
Debug.ShouldStop(32768);
 BA.debugLineNum = 17;BA.debugLine="TabHost_Grafico.Initialize(\"TabHost_Grafico\")";
Debug.ShouldStop(65536);
mostCurrent._tabhost_grafico.Initialize(mostCurrent.activityBA,"TabHost_Grafico");
 BA.debugLineNum = 18;BA.debugLine="Button_Voltar.Initialize(\"Button_Voltar\")";
Debug.ShouldStop(131072);
mostCurrent._button_voltar.Initialize(mostCurrent.activityBA,"Button_Voltar");
 BA.debugLineNum = 19;BA.debugLine="pnlLine1.Initialize(\"pnlLine1\")";
Debug.ShouldStop(262144);
mostCurrent._pnlline1.Initialize(mostCurrent.activityBA,"pnlLine1");
 BA.debugLineNum = 20;BA.debugLine="pnlLine2.Initialize(\"pnlLine2\")";
Debug.ShouldStop(524288);
mostCurrent._pnlline2.Initialize(mostCurrent.activityBA,"pnlLine2");
 BA.debugLineNum = 21;BA.debugLine="Activity.AddView(TabHost_Grafico, 0, 0, 100%x, 100%y)";
Debug.ShouldStop(1048576);
mostCurrent._activity.AddView((android.view.View)(mostCurrent._tabhost_grafico.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 BA.debugLineNum = 22;BA.debugLine="CriarGraficoTrans_por_Mes";
Debug.ShouldStop(2097152);
_criargraficotrans_por_mes();
 BA.debugLineNum = 23;BA.debugLine="CriarGraficoTrans_por_Categ";
Debug.ShouldStop(4194304);
_criargraficotrans_por_categ();
 BA.debugLineNum = 24;BA.debugLine="CriarBotaoVoltar";
Debug.ShouldStop(8388608);
_criarbotaovoltar();
 BA.debugLineNum = 25;BA.debugLine="Activity.Title = \"Gráficos\"";
Debug.ShouldStop(16777216);
mostCurrent._activity.setTitle((Object)("Gráficos"));
 BA.debugLineNum = 26;BA.debugLine="End Sub";
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
public static String  _activity_pause(boolean _userclosed) throws Exception{
		Debug.PushSubsStack("Activity_Pause (graficos) ","graficos",17,mostCurrent.activityBA,mostCurrent);
try {
Debug.locals.put("UserClosed", _userclosed);
 BA.debugLineNum = 156;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
Debug.ShouldStop(134217728);
 BA.debugLineNum = 158;BA.debugLine="End Sub";
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
public static String  _activity_resume() throws Exception{
		Debug.PushSubsStack("Activity_Resume (graficos) ","graficos",17,mostCurrent.activityBA,mostCurrent);
try {
 BA.debugLineNum = 152;BA.debugLine="Sub Activity_Resume";
Debug.ShouldStop(8388608);
 BA.debugLineNum = 154;BA.debugLine="End Sub";
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
public static String  _button_voltar_click() throws Exception{
		Debug.PushSubsStack("Button_Voltar_Click (graficos) ","graficos",17,mostCurrent.activityBA,mostCurrent);
try {
anywheresoftware.b4a.objects.drawable.ColorDrawable _cor = null;
 BA.debugLineNum = 160;BA.debugLine="Sub Button_Voltar_Click";
Debug.ShouldStop(-2147483648);
 BA.debugLineNum = 161;BA.debugLine="Dim Cor As ColorDrawable";
Debug.ShouldStop(1);
_cor = new anywheresoftware.b4a.objects.drawable.ColorDrawable();Debug.locals.put("Cor", _cor);
 BA.debugLineNum = 162;BA.debugLine="Cor.Initialize(Colors.RGB(220, 20, 60),5)";
Debug.ShouldStop(2);
_cor.Initialize(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (220),(int) (20),(int) (60)),(int) (5));
 BA.debugLineNum = 163;BA.debugLine="Button_Voltar.Background = Cor";
Debug.ShouldStop(4);
mostCurrent._button_voltar.setBackground((android.graphics.drawable.Drawable)(_cor.getObject()));
 BA.debugLineNum = 164;BA.debugLine="Activity.Finish";
Debug.ShouldStop(8);
mostCurrent._activity.Finish();
 BA.debugLineNum = 165;BA.debugLine="End Sub";
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
public static String  _criarbotaovoltar() throws Exception{
		Debug.PushSubsStack("CriarBotaoVoltar (graficos) ","graficos",17,mostCurrent.activityBA,mostCurrent);
try {
anywheresoftware.b4a.objects.drawable.ColorDrawable _cor = null;
 BA.debugLineNum = 28;BA.debugLine="Sub CriarBotaoVoltar";
Debug.ShouldStop(134217728);
 BA.debugLineNum = 29;BA.debugLine="Dim Cor As ColorDrawable";
Debug.ShouldStop(268435456);
_cor = new anywheresoftware.b4a.objects.drawable.ColorDrawable();Debug.locals.put("Cor", _cor);
 BA.debugLineNum = 30;BA.debugLine="Cor.Initialize(Colors.RGB(139,0,0),5)";
Debug.ShouldStop(536870912);
_cor.Initialize(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (139),(int) (0),(int) (0)),(int) (5));
 BA.debugLineNum = 31;BA.debugLine="Button_Voltar.Background = Cor";
Debug.ShouldStop(1073741824);
mostCurrent._button_voltar.setBackground((android.graphics.drawable.Drawable)(_cor.getObject()));
 BA.debugLineNum = 32;BA.debugLine="Button_Voltar.TextSize = 18";
Debug.ShouldStop(-2147483648);
mostCurrent._button_voltar.setTextSize((float) (18));
 BA.debugLineNum = 33;BA.debugLine="Button_Voltar.Text = \"Voltar\"";
Debug.ShouldStop(1);
mostCurrent._button_voltar.setText((Object)("Voltar"));
 BA.debugLineNum = 34;BA.debugLine="Activity.AddView(Button_Voltar, 170dip, 350dip, 140dip, 50dip)";
Debug.ShouldStop(2);
mostCurrent._activity.AddView((android.view.View)(mostCurrent._button_voltar.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (170)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (350)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (140)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)));
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
public static String  _criargraficotrans_por_categ() throws Exception{
		Debug.PushSubsStack("CriarGraficoTrans_por_Categ (graficos) ","graficos",17,mostCurrent.activityBA,mostCurrent);
try {
anywheresoftware.b4a.objects.PanelWrapper _p = null;
Financas.Pessoais.charts._linedata _ld = null;
int _j1 = 0;
anywheresoftware.b4a.objects.collections.List _listacateg = null;
anywheresoftware.b4a.objects.collections.List _listatrans = null;
String _categ = "";
String _trans = "";
String _str = "";
Financas.Pessoais.charts._graph _g = null;
 BA.debugLineNum = 109;BA.debugLine="Sub CriarGraficoTrans_por_Categ";
Debug.ShouldStop(4096);
 BA.debugLineNum = 110;BA.debugLine="Dim p As Panel";
Debug.ShouldStop(8192);
_p = new anywheresoftware.b4a.objects.PanelWrapper();Debug.locals.put("p", _p);
 BA.debugLineNum = 111;BA.debugLine="p.Initialize(\"\")";
Debug.ShouldStop(16384);
_p.Initialize(mostCurrent.activityBA,"");
 BA.debugLineNum = 112;BA.debugLine="p.AddView(pnlLine2, 0, 0, 95%x, 100%y - 125dip)";
Debug.ShouldStop(32768);
_p.AddView((android.view.View)(mostCurrent._pnlline2.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (95),mostCurrent.activityBA),(int) (anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (125))));
 BA.debugLineNum = 113;BA.debugLine="TabHost_Grafico.AddTab2(\"Por Categoria\", p)";
Debug.ShouldStop(65536);
mostCurrent._tabhost_grafico.AddTab2("Por Categoria",(android.view.View)(_p.getObject()));
 BA.debugLineNum = 114;BA.debugLine="Dim LD As LineData";
Debug.ShouldStop(131072);
_ld = new Financas.Pessoais.charts._linedata();Debug.locals.put("LD", _ld);
 BA.debugLineNum = 115;BA.debugLine="LD.Initialize";
Debug.ShouldStop(262144);
_ld.Initialize();
 BA.debugLineNum = 116;BA.debugLine="LD.Target = pnlLine2";
Debug.ShouldStop(524288);
_ld.Target = mostCurrent._pnlline2;Debug.locals.put("LD", _ld);
 BA.debugLineNum = 117;BA.debugLine="Charts.AddLineColor(LD, Colors.Red)";
Debug.ShouldStop(1048576);
mostCurrent._charts._addlinecolor(mostCurrent.activityBA,_ld,anywheresoftware.b4a.keywords.Common.Colors.Red);
 BA.debugLineNum = 119;BA.debugLine="Dim j1 As Int";
Debug.ShouldStop(4194304);
_j1 = 0;Debug.locals.put("j1", _j1);
 BA.debugLineNum = 120;BA.debugLine="Dim listaCateg, listaTrans As List";
Debug.ShouldStop(8388608);
_listacateg = new anywheresoftware.b4a.objects.collections.List();Debug.locals.put("listaCateg", _listacateg);
_listatrans = new anywheresoftware.b4a.objects.collections.List();Debug.locals.put("listaTrans", _listatrans);
 BA.debugLineNum = 121;BA.debugLine="listaCateg = Main.Pers.GetCategorias";
Debug.ShouldStop(16777216);
_listacateg = mostCurrent._main._pers._getcategorias();Debug.locals.put("listaCateg", _listacateg);
 BA.debugLineNum = 122;BA.debugLine="listaTrans = Main.Pers.GetTransacoes(Main.Pers.Logado)";
Debug.ShouldStop(33554432);
_listatrans = mostCurrent._main._pers._gettransacoes(mostCurrent._main._pers._logado());Debug.locals.put("listaTrans", _listatrans);
 BA.debugLineNum = 124;BA.debugLine="For Each categ As String In listaCateg";
Debug.ShouldStop(134217728);
final anywheresoftware.b4a.BA.IterableList group106 = _listacateg;
final int groupLen106 = group106.getSize();
for (int index106 = 0;index106 < groupLen106 ;index106++){
_categ = BA.ObjectToString(group106.Get(index106));Debug.locals.put("categ", _categ);
Debug.locals.put("categ", _categ);
 BA.debugLineNum = 125;BA.debugLine="j1 = 0";
Debug.ShouldStop(268435456);
_j1 = (int) (0);Debug.locals.put("j1", _j1);
 BA.debugLineNum = 126;BA.debugLine="For Each trans As String In listaTrans";
Debug.ShouldStop(536870912);
final anywheresoftware.b4a.BA.IterableList group108 = _listatrans;
final int groupLen108 = group108.getSize();
for (int index108 = 0;index108 < groupLen108 ;index108++){
_trans = BA.ObjectToString(group108.Get(index108));Debug.locals.put("trans", _trans);
Debug.locals.put("trans", _trans);
 BA.debugLineNum = 127;BA.debugLine="Dim str As String";
Debug.ShouldStop(1073741824);
_str = "";Debug.locals.put("str", _str);
 BA.debugLineNum = 128;BA.debugLine="str = trans.SubString(trans.LastIndexOf(\";\")+1)";
Debug.ShouldStop(-2147483648);
_str = _trans.substring((int) (_trans.lastIndexOf(";")+1));Debug.locals.put("str", _str);
 BA.debugLineNum = 129;BA.debugLine="If categ = str Then";
Debug.ShouldStop(1);
if ((_categ).equals(_str)) { 
 BA.debugLineNum = 130;BA.debugLine="j1 = j1+1";
Debug.ShouldStop(2);
_j1 = (int) (_j1+1);Debug.locals.put("j1", _j1);
 };
 }
Debug.locals.put("trans", _trans);
;
 BA.debugLineNum = 133;BA.debugLine="If categ = \"Selecione a categoria\" Then";
Debug.ShouldStop(16);
if ((_categ).equals("Selecione a categoria")) { 
 }else {
 BA.debugLineNum = 136;BA.debugLine="Charts.AddLinePoint(LD, categ, j1, True)";
Debug.ShouldStop(128);
mostCurrent._charts._addlinepoint(mostCurrent.activityBA,_ld,_categ,(float) (_j1),anywheresoftware.b4a.keywords.Common.True);
 };
 }
Debug.locals.put("categ", _categ);
;
 BA.debugLineNum = 140;BA.debugLine="Dim G As Graph";
Debug.ShouldStop(2048);
_g = new Financas.Pessoais.charts._graph();Debug.locals.put("G", _g);
 BA.debugLineNum = 141;BA.debugLine="G.Initialize";
Debug.ShouldStop(4096);
_g.Initialize();
 BA.debugLineNum = 142;BA.debugLine="G.Title = \"Transações por Categoria\"";
Debug.ShouldStop(8192);
_g.Title = "Transações por Categoria";Debug.locals.put("G", _g);
 BA.debugLineNum = 143;BA.debugLine="G.XAxis = \"\"";
Debug.ShouldStop(16384);
_g.XAxis = "";Debug.locals.put("G", _g);
 BA.debugLineNum = 144;BA.debugLine="G.YAxis = \"Qtde de Transações\"";
Debug.ShouldStop(32768);
_g.YAxis = "Qtde de Transações";Debug.locals.put("G", _g);
 BA.debugLineNum = 145;BA.debugLine="G.YStart = 0";
Debug.ShouldStop(65536);
_g.YStart = (float) (0);Debug.locals.put("G", _g);
 BA.debugLineNum = 146;BA.debugLine="G.YEnd = listaTrans.Size+1";
Debug.ShouldStop(131072);
_g.YEnd = (float) (_listatrans.getSize()+1);Debug.locals.put("G", _g);
 BA.debugLineNum = 147;BA.debugLine="G.YInterval = 1";
Debug.ShouldStop(262144);
_g.YInterval = (float) (1);Debug.locals.put("G", _g);
 BA.debugLineNum = 148;BA.debugLine="G.AxisColor = Colors.White";
Debug.ShouldStop(524288);
_g.AxisColor = anywheresoftware.b4a.keywords.Common.Colors.White;Debug.locals.put("G", _g);
 BA.debugLineNum = 149;BA.debugLine="Charts.DrawLineChart(G, LD, Colors.Black)";
Debug.ShouldStop(1048576);
mostCurrent._charts._drawlinechart(mostCurrent.activityBA,_g,_ld,anywheresoftware.b4a.keywords.Common.Colors.Black);
 BA.debugLineNum = 150;BA.debugLine="End Sub";
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
public static String  _criargraficotrans_por_mes() throws Exception{
		Debug.PushSubsStack("CriarGraficoTrans_por_Mes (graficos) ","graficos",17,mostCurrent.activityBA,mostCurrent);
try {
anywheresoftware.b4a.objects.PanelWrapper _p = null;
Financas.Pessoais.charts._linedata _ld = null;
String _linha1 = "";
String _mes_linha = "";
int _x = 0;
int _j1 = 0;
int _j2 = 0;
int _j3 = 0;
int _j4 = 0;
int _j5 = 0;
int _j6 = 0;
int _j7 = 0;
int _j8 = 0;
int _j9 = 0;
int _j10 = 0;
int _j11 = 0;
int _j12 = 0;
int _mes = 0;
anywheresoftware.b4a.objects.collections.List _lista = null;
int _i = 0;
Financas.Pessoais.charts._graph _g = null;
 BA.debugLineNum = 37;BA.debugLine="Sub CriarGraficoTrans_por_Mes";
Debug.ShouldStop(16);
 BA.debugLineNum = 38;BA.debugLine="Dim p As Panel";
Debug.ShouldStop(32);
_p = new anywheresoftware.b4a.objects.PanelWrapper();Debug.locals.put("p", _p);
 BA.debugLineNum = 39;BA.debugLine="p.Initialize(\"\")";
Debug.ShouldStop(64);
_p.Initialize(mostCurrent.activityBA,"");
 BA.debugLineNum = 40;BA.debugLine="p.AddView(pnlLine1, 0, 0, 95%x, 100%y - 125dip)";
Debug.ShouldStop(128);
_p.AddView((android.view.View)(mostCurrent._pnlline1.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (95),mostCurrent.activityBA),(int) (anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (125))));
 BA.debugLineNum = 41;BA.debugLine="TabHost_Grafico.AddTab2(\"Por Mês\", p)";
Debug.ShouldStop(256);
mostCurrent._tabhost_grafico.AddTab2("Por Mês",(android.view.View)(_p.getObject()));
 BA.debugLineNum = 42;BA.debugLine="Dim LD As LineData";
Debug.ShouldStop(512);
_ld = new Financas.Pessoais.charts._linedata();Debug.locals.put("LD", _ld);
 BA.debugLineNum = 43;BA.debugLine="LD.Initialize";
Debug.ShouldStop(1024);
_ld.Initialize();
 BA.debugLineNum = 44;BA.debugLine="LD.Target = pnlLine1";
Debug.ShouldStop(2048);
_ld.Target = mostCurrent._pnlline1;Debug.locals.put("LD", _ld);
 BA.debugLineNum = 45;BA.debugLine="Charts.AddLineColor(LD, Colors.Red)";
Debug.ShouldStop(4096);
mostCurrent._charts._addlinecolor(mostCurrent.activityBA,_ld,anywheresoftware.b4a.keywords.Common.Colors.Red);
 BA.debugLineNum = 47;BA.debugLine="Dim linha1, mes_linha As String";
Debug.ShouldStop(16384);
_linha1 = "";Debug.locals.put("linha1", _linha1);
_mes_linha = "";Debug.locals.put("mes_linha", _mes_linha);
 BA.debugLineNum = 48;BA.debugLine="Dim x, j1,j2,j3,j4,j5,j6,j7,j8,j9,j10,j11,j12, mes As Int";
Debug.ShouldStop(32768);
_x = 0;Debug.locals.put("x", _x);
_j1 = 0;Debug.locals.put("j1", _j1);
_j2 = 0;Debug.locals.put("j2", _j2);
_j3 = 0;Debug.locals.put("j3", _j3);
_j4 = 0;Debug.locals.put("j4", _j4);
_j5 = 0;Debug.locals.put("j5", _j5);
_j6 = 0;Debug.locals.put("j6", _j6);
_j7 = 0;Debug.locals.put("j7", _j7);
_j8 = 0;Debug.locals.put("j8", _j8);
_j9 = 0;Debug.locals.put("j9", _j9);
_j10 = 0;Debug.locals.put("j10", _j10);
_j11 = 0;Debug.locals.put("j11", _j11);
_j12 = 0;Debug.locals.put("j12", _j12);
_mes = 0;Debug.locals.put("mes", _mes);
 BA.debugLineNum = 49;BA.debugLine="Dim lista As List";
Debug.ShouldStop(65536);
_lista = new anywheresoftware.b4a.objects.collections.List();Debug.locals.put("lista", _lista);
 BA.debugLineNum = 50;BA.debugLine="lista = Main.Pers.GetTransacoes(Main.Pers.Logado)";
Debug.ShouldStop(131072);
_lista = mostCurrent._main._pers._gettransacoes(mostCurrent._main._pers._logado());Debug.locals.put("lista", _lista);
 BA.debugLineNum = 51;BA.debugLine="x = lista.Size";
Debug.ShouldStop(262144);
_x = _lista.getSize();Debug.locals.put("x", _x);
 BA.debugLineNum = 53;BA.debugLine="For i = 0 To x -1";
Debug.ShouldStop(1048576);
{
final int step40 = 1;
final int limit40 = (int) (_x-1);
for (_i = (int) (0); (step40 > 0 && _i <= limit40) || (step40 < 0 && _i >= limit40); _i = ((int)(0 + _i + step40))) {
Debug.locals.put("i", _i);
 BA.debugLineNum = 54;BA.debugLine="linha1 = lista.Get(i)";
Debug.ShouldStop(2097152);
_linha1 = BA.ObjectToString(_lista.Get(_i));Debug.locals.put("linha1", _linha1);
 BA.debugLineNum = 55;BA.debugLine="mes_linha = linha1.SubString2(linha1.IndexOf(\"/\")+1,linha1.LastIndexOf(\"/\"))";
Debug.ShouldStop(4194304);
_mes_linha = _linha1.substring((int) (_linha1.indexOf("/")+1),_linha1.lastIndexOf("/"));Debug.locals.put("mes_linha", _mes_linha);
 BA.debugLineNum = 56;BA.debugLine="mes = mes_linha";
Debug.ShouldStop(8388608);
_mes = (int)(Double.parseDouble(_mes_linha));Debug.locals.put("mes", _mes);
 BA.debugLineNum = 57;BA.debugLine="If mes = 1 Then";
Debug.ShouldStop(16777216);
if (_mes==1) { 
 BA.debugLineNum = 58;BA.debugLine="j1 = j1+1";
Debug.ShouldStop(33554432);
_j1 = (int) (_j1+1);Debug.locals.put("j1", _j1);
 }else 
{ BA.debugLineNum = 59;BA.debugLine="Else If mes = 2 Then";
Debug.ShouldStop(67108864);
if (_mes==2) { 
 BA.debugLineNum = 60;BA.debugLine="j2 = j2+1";
Debug.ShouldStop(134217728);
_j2 = (int) (_j2+1);Debug.locals.put("j2", _j2);
 }else 
{ BA.debugLineNum = 61;BA.debugLine="Else If mes = 3 Then";
Debug.ShouldStop(268435456);
if (_mes==3) { 
 BA.debugLineNum = 62;BA.debugLine="j3 = j3+1";
Debug.ShouldStop(536870912);
_j3 = (int) (_j3+1);Debug.locals.put("j3", _j3);
 }else 
{ BA.debugLineNum = 63;BA.debugLine="Else If mes = 4 Then";
Debug.ShouldStop(1073741824);
if (_mes==4) { 
 BA.debugLineNum = 64;BA.debugLine="j4 = j4+1";
Debug.ShouldStop(-2147483648);
_j4 = (int) (_j4+1);Debug.locals.put("j4", _j4);
 }else 
{ BA.debugLineNum = 65;BA.debugLine="Else If mes = 5 Then";
Debug.ShouldStop(1);
if (_mes==5) { 
 BA.debugLineNum = 66;BA.debugLine="j5 = j5+1";
Debug.ShouldStop(2);
_j5 = (int) (_j5+1);Debug.locals.put("j5", _j5);
 }else 
{ BA.debugLineNum = 67;BA.debugLine="Else If mes = 6 Then";
Debug.ShouldStop(4);
if (_mes==6) { 
 BA.debugLineNum = 68;BA.debugLine="j6 = j6+1";
Debug.ShouldStop(8);
_j6 = (int) (_j6+1);Debug.locals.put("j6", _j6);
 }else 
{ BA.debugLineNum = 69;BA.debugLine="Else If mes = 7 Then";
Debug.ShouldStop(16);
if (_mes==7) { 
 BA.debugLineNum = 70;BA.debugLine="j7 = j7+1";
Debug.ShouldStop(32);
_j7 = (int) (_j7+1);Debug.locals.put("j7", _j7);
 }else 
{ BA.debugLineNum = 71;BA.debugLine="Else If mes = 8 Then";
Debug.ShouldStop(64);
if (_mes==8) { 
 BA.debugLineNum = 72;BA.debugLine="j8 = j8+1";
Debug.ShouldStop(128);
_j8 = (int) (_j8+1);Debug.locals.put("j8", _j8);
 }else 
{ BA.debugLineNum = 73;BA.debugLine="Else If mes = 9 Then";
Debug.ShouldStop(256);
if (_mes==9) { 
 BA.debugLineNum = 74;BA.debugLine="j9 = j9+1";
Debug.ShouldStop(512);
_j9 = (int) (_j9+1);Debug.locals.put("j9", _j9);
 }else 
{ BA.debugLineNum = 75;BA.debugLine="Else If mes = 10 Then";
Debug.ShouldStop(1024);
if (_mes==10) { 
 BA.debugLineNum = 76;BA.debugLine="j10 = j10+1";
Debug.ShouldStop(2048);
_j10 = (int) (_j10+1);Debug.locals.put("j10", _j10);
 }else 
{ BA.debugLineNum = 77;BA.debugLine="Else If mes = 11 Then";
Debug.ShouldStop(4096);
if (_mes==11) { 
 BA.debugLineNum = 78;BA.debugLine="j11 = j11+1";
Debug.ShouldStop(8192);
_j11 = (int) (_j11+1);Debug.locals.put("j11", _j11);
 }else 
{ BA.debugLineNum = 79;BA.debugLine="Else If mes = 12 Then";
Debug.ShouldStop(16384);
if (_mes==12) { 
 BA.debugLineNum = 80;BA.debugLine="j12 = j12+1";
Debug.ShouldStop(32768);
_j12 = (int) (_j12+1);Debug.locals.put("j12", _j12);
 }}}}}}}}}}}};
 }
}Debug.locals.put("i", _i);
;
 BA.debugLineNum = 84;BA.debugLine="Charts.AddLinePoint(LD, \"Jan\", j1, True)";
Debug.ShouldStop(524288);
mostCurrent._charts._addlinepoint(mostCurrent.activityBA,_ld,"Jan",(float) (_j1),anywheresoftware.b4a.keywords.Common.True);
 BA.debugLineNum = 85;BA.debugLine="Charts.AddLinePoint(LD, \"Fev\", j2, True)";
Debug.ShouldStop(1048576);
mostCurrent._charts._addlinepoint(mostCurrent.activityBA,_ld,"Fev",(float) (_j2),anywheresoftware.b4a.keywords.Common.True);
 BA.debugLineNum = 86;BA.debugLine="Charts.AddLinePoint(LD, \"Mar\", j3, True)";
Debug.ShouldStop(2097152);
mostCurrent._charts._addlinepoint(mostCurrent.activityBA,_ld,"Mar",(float) (_j3),anywheresoftware.b4a.keywords.Common.True);
 BA.debugLineNum = 87;BA.debugLine="Charts.AddLinePoint(LD, \"Abr\", j4, True)";
Debug.ShouldStop(4194304);
mostCurrent._charts._addlinepoint(mostCurrent.activityBA,_ld,"Abr",(float) (_j4),anywheresoftware.b4a.keywords.Common.True);
 BA.debugLineNum = 88;BA.debugLine="Charts.AddLinePoint(LD, \"Mai\", j5, True)";
Debug.ShouldStop(8388608);
mostCurrent._charts._addlinepoint(mostCurrent.activityBA,_ld,"Mai",(float) (_j5),anywheresoftware.b4a.keywords.Common.True);
 BA.debugLineNum = 89;BA.debugLine="Charts.AddLinePoint(LD, \"Jun\", j6, True)";
Debug.ShouldStop(16777216);
mostCurrent._charts._addlinepoint(mostCurrent.activityBA,_ld,"Jun",(float) (_j6),anywheresoftware.b4a.keywords.Common.True);
 BA.debugLineNum = 90;BA.debugLine="Charts.AddLinePoint(LD, \"Jul\", j7, True)";
Debug.ShouldStop(33554432);
mostCurrent._charts._addlinepoint(mostCurrent.activityBA,_ld,"Jul",(float) (_j7),anywheresoftware.b4a.keywords.Common.True);
 BA.debugLineNum = 91;BA.debugLine="Charts.AddLinePoint(LD, \"Ago\", j8, True)";
Debug.ShouldStop(67108864);
mostCurrent._charts._addlinepoint(mostCurrent.activityBA,_ld,"Ago",(float) (_j8),anywheresoftware.b4a.keywords.Common.True);
 BA.debugLineNum = 92;BA.debugLine="Charts.AddLinePoint(LD, \"Set\", j9, True)";
Debug.ShouldStop(134217728);
mostCurrent._charts._addlinepoint(mostCurrent.activityBA,_ld,"Set",(float) (_j9),anywheresoftware.b4a.keywords.Common.True);
 BA.debugLineNum = 93;BA.debugLine="Charts.AddLinePoint(LD, \"Out\", j10, True)";
Debug.ShouldStop(268435456);
mostCurrent._charts._addlinepoint(mostCurrent.activityBA,_ld,"Out",(float) (_j10),anywheresoftware.b4a.keywords.Common.True);
 BA.debugLineNum = 94;BA.debugLine="Charts.AddLinePoint(LD, \"Nov\", j11, True)";
Debug.ShouldStop(536870912);
mostCurrent._charts._addlinepoint(mostCurrent.activityBA,_ld,"Nov",(float) (_j11),anywheresoftware.b4a.keywords.Common.True);
 BA.debugLineNum = 95;BA.debugLine="Charts.AddLinePoint(LD, \"Dez\", j12, True)";
Debug.ShouldStop(1073741824);
mostCurrent._charts._addlinepoint(mostCurrent.activityBA,_ld,"Dez",(float) (_j12),anywheresoftware.b4a.keywords.Common.True);
 BA.debugLineNum = 97;BA.debugLine="Dim G As Graph";
Debug.ShouldStop(1);
_g = new Financas.Pessoais.charts._graph();Debug.locals.put("G", _g);
 BA.debugLineNum = 98;BA.debugLine="G.Initialize";
Debug.ShouldStop(2);
_g.Initialize();
 BA.debugLineNum = 99;BA.debugLine="G.Title = \"Transações por Mês\"";
Debug.ShouldStop(4);
_g.Title = "Transações por Mês";Debug.locals.put("G", _g);
 BA.debugLineNum = 100;BA.debugLine="G.XAxis = \"\"";
Debug.ShouldStop(8);
_g.XAxis = "";Debug.locals.put("G", _g);
 BA.debugLineNum = 101;BA.debugLine="G.YAxis = \"Qtde de Transações\"";
Debug.ShouldStop(16);
_g.YAxis = "Qtde de Transações";Debug.locals.put("G", _g);
 BA.debugLineNum = 102;BA.debugLine="G.YStart = 0";
Debug.ShouldStop(32);
_g.YStart = (float) (0);Debug.locals.put("G", _g);
 BA.debugLineNum = 103;BA.debugLine="G.YEnd = x + 1";
Debug.ShouldStop(64);
_g.YEnd = (float) (_x+1);Debug.locals.put("G", _g);
 BA.debugLineNum = 104;BA.debugLine="G.YInterval = 1";
Debug.ShouldStop(128);
_g.YInterval = (float) (1);Debug.locals.put("G", _g);
 BA.debugLineNum = 105;BA.debugLine="G.AxisColor = Colors.White";
Debug.ShouldStop(256);
_g.AxisColor = anywheresoftware.b4a.keywords.Common.Colors.White;Debug.locals.put("G", _g);
 BA.debugLineNum = 106;BA.debugLine="Charts.DrawLineChart(G, LD, Colors.Black)";
Debug.ShouldStop(512);
mostCurrent._charts._drawlinechart(mostCurrent.activityBA,_g,_ld,anywheresoftware.b4a.keywords.Common.Colors.Black);
 BA.debugLineNum = 107;BA.debugLine="End Sub";
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
public static String  _globals() throws Exception{
 //BA.debugLineNum = 10;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 11;BA.debugLine="Private TabHost_Grafico As TabHost";
mostCurrent._tabhost_grafico = new anywheresoftware.b4a.objects.TabHostWrapper();
 //BA.debugLineNum = 12;BA.debugLine="Private pnlLine1,pnlLine2 As Panel";
mostCurrent._pnlline1 = new anywheresoftware.b4a.objects.PanelWrapper();
mostCurrent._pnlline2 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 13;BA.debugLine="Private Button_Voltar As Button";
mostCurrent._button_voltar = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 14;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 8;BA.debugLine="End Sub";
return "";
}
public static String  _voltar_click() throws Exception{
		Debug.PushSubsStack("Voltar_Click (graficos) ","graficos",17,mostCurrent.activityBA,mostCurrent);
try {
 BA.debugLineNum = 167;BA.debugLine="Sub Voltar_Click";
Debug.ShouldStop(64);
 BA.debugLineNum = 168;BA.debugLine="Activity.Finish";
Debug.ShouldStop(128);
mostCurrent._activity.Finish();
 BA.debugLineNum = 169;BA.debugLine="End Sub";
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
}
