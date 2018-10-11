package clip;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.prefs.Preferences;

import javax.swing.JComboBox;
import javax.swing.UIManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * Configuración de las preferencias
 * <p>
 * Copyright (c) 2011 DualClip Translator, Inc. http://sourceforge.net/projects/dcliptranslator/
 * @author Cesar Rodriguez Gonzalez <Stelars@users.sourceforge.net>
 */
public class Config {

	private static final Log LOG = LogFactory.getLog( Message.class );
    private static String idiomaPreferido;
    private static String idiomaDual;
    private static boolean inBalloonOrMessage;
    private static String ocrDpi;
    private static String ocrIdioma;
    private static boolean fileOpenTranslated;
    private static boolean systemaOperativo;
    private static int desktop;
    private static String userLanguage;
    private static HashMap<String, Integer> keys;
    private static int windowXWidth;
    private static int windowYHeight;
    private static int windowFontSize;
    private static int engine;
    private static boolean isEngineSwitch;
    private static boolean keyScreen;
    private static int priority;
    private static boolean architech64;

	public static final boolean isInfo = false;


    /**
     *	Temporal de las Teclas actuales
     *
     * @return Temporal HashMap
     */
    public static HashMap<String, Integer> getKeysToTemp() {
        HashMap<String, Integer> tmpKeys;
        tmpKeys = new LinkedHashMap<String, Integer>();
        Iterator<String> i = Config.keys.keySet().iterator();

        while(i. hasNext()){
            String valor = i.next();
            tmpKeys.put(valor, Config.keys.get(valor));
        }

        return tmpKeys;
    }



    /**
     * 	Recoger valores de todas las Teclas cambiadas
     *
     * @param tempKeys
     */
    public static void setTempToKeys(HashMap<String, Integer> tempKeys) {
        Iterator<String> i = tempKeys.keySet().iterator();

        while(i. hasNext()){
            String valor = i.next();
            Config.keys.put(valor, tempKeys.get(valor));
        }
    }



    /**
     *  HashMap para el Idioma del OCR
     */
    public static String getOcrIdiomaValue() {

        HashMap<String, String> orcIdioma;
        orcIdioma = new LinkedHashMap<String, String>();

        orcIdioma.put("Afrikaans", "afr");
        orcIdioma.put("Arabic", "ara");
        orcIdioma.put("Azerbaijani", "aze");
        orcIdioma.put("Belarusian", "bel");
        orcIdioma.put("Bengali", "ben");
        orcIdioma.put("Bulgarian", "bul");
        orcIdioma.put("Catalan", "cat");
        orcIdioma.put("Czech", "ces");
        orcIdioma.put("Chinese_Simplified", "chi_sim");
        orcIdioma.put("Chinese_Traditional", "chi_tra");
        orcIdioma.put("Cherokee", "chr");
        orcIdioma.put("Danish", "dan");
        orcIdioma.put("German", "deu");
        orcIdioma.put("Greek", "ell");
        orcIdioma.put("English", "eng");
        orcIdioma.put("Esperanto", "epo");
        orcIdioma.put("Esperanto", "epo_alt");
        orcIdioma.put("Math_equation_detection", "equ");
        orcIdioma.put("Estonian", "est");
        orcIdioma.put("Basque", "eus");
        orcIdioma.put("Finnish", "fin");
        orcIdioma.put("French", "fra");
        orcIdioma.put("Frankish", "frk");
        orcIdioma.put("Galician", "glg");
        orcIdioma.put("Ancient_Greek", "grc");
        orcIdioma.put("Hebrew", "heb");
        orcIdioma.put("Hindi", "hin");
        orcIdioma.put("Croatian", "hrv");
        orcIdioma.put("Hungarian", "hun");
        orcIdioma.put("Indonesian", "ind");
        orcIdioma.put("Icelandic", "isl");
        orcIdioma.put("Italian", "ita");
        orcIdioma.put("Italian_Old", "ita_old");
        orcIdioma.put("Japanese", "jpn");
        orcIdioma.put("Kannada", "kan");
        orcIdioma.put("Korean", "kor");
        orcIdioma.put("Latvian", "lav");
        orcIdioma.put("Lithuanian", "lit");
        orcIdioma.put("Malayalam", "mal");
        orcIdioma.put("Macedonian", "mkd");
        orcIdioma.put("Maltese", "mlt");
        orcIdioma.put("Malay", "msa");
        orcIdioma.put("Dutch", "nld");
        orcIdioma.put("Norwegian", "nor");
        orcIdioma.put("Polish", "pol");
        orcIdioma.put("Portuguese", "por");
        orcIdioma.put("Romanian", "ron");
        orcIdioma.put("Russian", "rus");
        orcIdioma.put("Slovakian", "slk");
        orcIdioma.put("Slovenian", "slv");
        orcIdioma.put("Spanish", "spa");
        orcIdioma.put("Spanish_Old", "spa_old");
        orcIdioma.put("Albanian", "sqi");
        orcIdioma.put("Serbian_Latin", "srp");
        orcIdioma.put("Swahili", "swa");
        orcIdioma.put("Swedish", "swe");
        orcIdioma.put("Tamil", "tam");
        orcIdioma.put("Telugu", "tel");
        orcIdioma.put("Tagalog", "tgl");
        orcIdioma.put("Thai", "tha");
        orcIdioma.put("Turkish", "tur");
        orcIdioma.put("Ukrainian", "ukr");
        orcIdioma.put("Vietnamese", "vie");

        return orcIdioma.get( ocrIdioma );
    }



    /**
     *  HashMap para el DPI del OCR
     */
    public static int getOcrDpiValue() {

        HashMap<String, Integer> orcDpi;
        orcDpi = new LinkedHashMap<String, Integer>();

        orcDpi.put("300 Dpi", 300);
        orcDpi.put("400 Dpi", 400);
        orcDpi.put("600 Dpi", 600);

        return orcDpi.get( ocrDpi );
    }



    public static String getOcrDpi() {
        return ocrDpi;
    }
    public static void setOcrDpi(String ocrDpi) {
        Config.ocrDpi = ocrDpi;
    }
    public static String getOcrIdioma() {
        return ocrIdioma;
    }
    public static void setOcrIdioma(String ocrIdioma) {
        Config.ocrIdioma = ocrIdioma;
    }
    public static int getKey(String key){
        return Config.keys.get(key);
    }
    public static void setKeys(String key, int value) {
        Config.keys.put(key, value);
    }
    public static String getUserLanguage() {
        return userLanguage;
    }
    public static void setUserLanguage(String userLanguage) {
        Config.userLanguage = userLanguage;
    }
    public static boolean isSystemaOperativo() {
        return systemaOperativo;
    }
    public static int getDesktop() {
        return desktop;
    }
    public static void setDesktop(int desktop) {
        Config.desktop = desktop;
    }
    public static void setSystemaOperativo(boolean systemaOperativo) {
        Config.systemaOperativo = systemaOperativo;
    }
    public static boolean isFileOpenTranslated() {
        return fileOpenTranslated;
    }
    public static void setFileOpenTranslated(boolean fileOpenTranslated) {
        Config.fileOpenTranslated = fileOpenTranslated;
    }
    public static boolean isInBalloonOrMessage() {
        return inBalloonOrMessage;
    }
    public static void setInBalloonOrMessage(boolean inBalloonOrMessage) {
        Config.inBalloonOrMessage = inBalloonOrMessage;
    }
    public static String getIdiomaPreferido() {
        return idiomaPreferido;
    }
    public static void setIdiomaPreferido(String idiomaPreferido) {
        Config.idiomaPreferido = idiomaPreferido;
    }
    public static String getIdiomaDual() {
        return idiomaDual;
    }
    public static void setIdiomaDual(String idiomaDual) {
        Config.idiomaDual = idiomaDual;
    }
    public static int getWindowXWidth() {
        return windowXWidth;
    }
    public static void setWindowXWidth(int windowXWidth) {
        Config.windowXWidth = windowXWidth;
    }
    public static int getWindowYHeight() {
        return windowYHeight;
    }
    public static void setWindowYHeight(int windowYHeight) {
        Config.windowYHeight = windowYHeight;
    }
    public static int getWindowFontSize() {
        return windowFontSize;
    }
    public static void setWindowFontSize(int windowFontSize) {
        Config.windowFontSize = windowFontSize;
    }
	public static boolean isKeyScreen() {
		return keyScreen;
	}
	public static void setKeyScreen(boolean keyScreen) {
		Config.keyScreen = keyScreen;
	}
	public static boolean isEngineSwitch() {
		return isEngineSwitch;
	}
	public static void setEngineSwitch(boolean isEngineSwitch) {
		Config.isEngineSwitch = isEngineSwitch;
			switch (Config.engine){
			case 0:
				Config.engine = 1;
				break;
			case 1:
				Config.engine = 0;
			}
	}



	/**
	 *	ENGINE INT ACTIVE
	 *
	 * @return int value 0/1 -- Google/Microsoft
	 */
	public static int getEngine() {
		return engine;
    }

    public static void setEngine(int engine) {
        Config.engine = engine;
    }



	/**
     * 	ENGINE NAME ACTIVE
     *
     * @return boolean
     */
    public static String getEngineName(){
    	if (Config.isKeyScreen()){
    		Config.setKeyScreen(false);
    		return "Tesseract OCR";
    	}

    	if (Config.engine == 1){
    		return "Microsoft";
    	}
    	else
            return "Google";
    }



    public static int getPriority() {
    	return priority;
    }

    public static void setPriority(int priority) {
    	Config.priority = priority;
    	Thread.currentThread().setPriority(priority);
    }


    /*
     * GET TIME

	public static long getTime() {
		return (System.currentTimeMillis() - Config.googleTimeSwitch) / (60*1000L);
	}
	public static void setTime() {
		Config.googleTimeSwitch = System.currentTimeMillis();
	}
     */


    /**
     *  SLEEP con Thread definido to 500 miliSec
     */
    public static void sleep(){
//        try {
//            Thread.sleep(500);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }



    /**
     *  Iniciar Ventanas Decorativas
     */
    public static void initLookAndFeel(){
      try {
          UIManager.setLookAndFeel ("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
          }
          catch (Exception e) {
          e.printStackTrace ();
          }
    }



    /**
     *  Default de Ventanas
     */
    public static void defaultLookAndFeel(){
      try {
          UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
          }
          catch (Exception e) {
          e.printStackTrace ();
          }
    }



    /**
     *  SET PREFERENCES OPTIONS
     */
    public static void setPreferences(JComboBox jComboBoxIdiomaDual, JComboBox jComboBoxIdiomaPreferred, JComboBox jComboBoxOcrIdioma, JComboBox jComboBoxOcrDpi){
        Preferences prefs = Preferences.userRoot().node("dualclip");

        String idiomaPreferido = (String) jComboBoxIdiomaPreferred.getSelectedItem();
        String idiomaDual = (String) jComboBoxIdiomaDual.getSelectedItem();
        String ocrIdioma = (String) jComboBoxOcrIdioma.getSelectedItem();
        String ocrDpi = (String) jComboBoxOcrDpi.getSelectedItem();


        // SAVE preferences in the Registry
        prefs.put("Idioma Preferido", idiomaPreferido);
        prefs.put("Idioma Dual", idiomaDual);
        prefs.putInt("Engine", getEngine());
        prefs.putBoolean("BalloonOrMessage", isInBalloonOrMessage());
        prefs.putBoolean("FileOpenTranslated", isFileOpenTranslated());
        prefs.putInt("windowXWidth", getWindowXWidth());
        prefs.putInt("windowYHeight", getWindowYHeight());
        prefs.putInt("windowFontSize", getWindowFontSize());
        prefs.put("OcrIdioma", ocrIdioma);
        prefs.put("OcrDpi", ocrDpi);
        prefs.putInt("Priority", getPriority());


        // SAVE Keys Values
        Iterator<String> i = Config.keys.keySet().iterator();
        while(i. hasNext()){
            String valor = i.next();
            prefs.putInt(valor, Config.keys.get(valor));
        }

        // SET preferences in Static values
        setIdiomaPreferido(idiomaPreferido);
        setIdiomaDual(idiomaDual);
        setOcrIdioma(ocrIdioma);
        setOcrDpi(ocrDpi);

    }



    /**
     *  INIT PREFERENCES OPTIONS
     */
    public static void initPreferences(){
        final int NONE = 1000;
        Preferences prefs 				= Preferences.userRoot().node("dualclip");
        String 		idiomaPreferido 	= prefs.get("Idioma Preferido","Spanish");
        String 		idiomaDual 			= prefs.get("Idioma Dual","English");
        int 		engine 				= prefs.getInt("Engine",0);
        boolean 	inBalloonOrMessage 	= prefs.getBoolean("BalloonOrMessage",true);
        boolean 	fileOpenTranslated 	= prefs.getBoolean("FileOpenTranslated", true);
        int 		windowXWidth 		= prefs.getInt("windowXWidth", 600);
        int 		windowYHeight 		= prefs.getInt("windowYHeight", 500);
        int 		windowFontSize 		= prefs.getInt("windowFontSize", 15);
        String 		ocrIdioma 			= prefs.get("OcrIdioma","English");
        String 		ocrDpi 				= prefs.get("OcrDpi","600 Dpi");
        int 		priority			= prefs.getInt("Priority", Thread.NORM_PRIORITY);



        // GET KEYS Values of Register Preferences of keys define static
        keys = new LinkedHashMap<String, Integer>();
        keys.put("_Z", prefs.getInt("_Z",NONE));		keys.put("_Z_Mod", prefs.getInt("_Z_Mod",NONE));
        keys.put("_X", prefs.getInt("_X",NONE));		keys.put("_X_Mod", prefs.getInt("_X_Mod",NONE));
        keys.put("_C", prefs.getInt("_C",NONE));		keys.put("_C_Mod", prefs.getInt("_C_Mod",NONE));
        keys.put("_V", prefs.getInt("_V",NONE));		keys.put("_V_Mod", prefs.getInt("_V_Mod",NONE));
        keys.put("_W", prefs.getInt("_W",NONE));		keys.put("_W_Mod", prefs.getInt("_W_Mod",NONE));
        keys.put("_S", prefs.getInt("_S",NONE));		keys.put("_S_Mod", prefs.getInt("_S_Mod",NONE));


        // DEFAULT KEYS Values
        HashMap<String, Integer> defaultKeys;
        defaultKeys = new LinkedHashMap<String, Integer>();
        defaultKeys.put("_Z", 90);		defaultKeys.put("_Z_Mod", 512);
        defaultKeys.put("_X", 88);		defaultKeys.put("_X_Mod", 512);
        defaultKeys.put("_C", 67);		defaultKeys.put("_C_Mod", 512);
        defaultKeys.put("_V", 86);		defaultKeys.put("_V_Mod", 512);
        defaultKeys.put("_W", 87);		defaultKeys.put("_W_Mod", 512);
        defaultKeys.put("_S", 83);		defaultKeys.put("_S_Mod", 512);


        // VERIFY KEYS Values and SET si es la primera vez
        Iterator<String> i = defaultKeys.keySet().iterator();
        while(i. hasNext()){
            String valor = i.next();

            if (keys.get(valor).equals(NONE)){
                keys.put(valor, defaultKeys.get(valor));
                prefs.putInt(valor, defaultKeys.get(valor));
            }

        }

        // SAVE preferences in the Registry
        prefs.put("Idioma Dual", idiomaDual);
        prefs.put("Idioma Preferido", idiomaPreferido);
        prefs.putInt("Engine", engine);
        prefs.putBoolean("BalloonOrMessage", inBalloonOrMessage);
        prefs.putBoolean("FileOpenTranslated", fileOpenTranslated);
        prefs.putInt("windowXWidth", windowXWidth);
        prefs.putInt("windowYHeight", windowYHeight);
        prefs.putInt("windowFontSize", windowFontSize);
        prefs.put("OcrIdioma", ocrIdioma);
        prefs.put("OcrDpi", ocrDpi);
        prefs.putInt("Priority", priority);

        // SET preferences in Static values
        setIdiomaPreferido(idiomaPreferido);
        setIdiomaDual(idiomaDual);
        setEngine(engine);
        setInBalloonOrMessage(inBalloonOrMessage);
        setFileOpenTranslated(fileOpenTranslated);
        setWindowXWidth(windowXWidth);
        setWindowYHeight(windowYHeight);
        setWindowFontSize(windowFontSize);
        setOcrIdioma(ocrIdioma);
        setOcrDpi(ocrDpi);
        setPriority(priority);

        // Saber cual es el Systema Operativo
        getSystemProperty();

        // Saber la Arquitectura
        setArchitech64( getOsAchitect() );
    }


    /**
     * GET Arch
     * @return
     */
    public static boolean getOsAchitect(){
    	boolean is64bit = false;
        if (System.getProperty("os.name").contains("Windows")) {
            is64bit = (System.getProperty("sun.arch.data.model").indexOf("64") != -1);
        } else {
            is64bit = (System.getProperty("os.arch").indexOf("64") != -1);
        }
        return is64bit;
    }



    /**
     * GET IN STRING (Ctrl+Alt+Z) OF THE KEY (512 90)
     * @param str
     * @return
     */
    public static String getkeyString(String str){
        return KeyEvent.getModifiersExText(Config.getKey("_" + str + "_Mod")) + "+" +
                KeyEvent.getKeyText(Config.getKey("_" + str));
    }

    /**
     * GET SYSTEM PROPERTY
     */
    public static void getSystemProperty(){
        if ( System.getProperty("os.name").toLowerCase().contains("windows") )
            systemaOperativo = false;
        else
            systemaOperativo = true;


        try {
            String desk = System.getProperty("sun.desktop").toLowerCase();

            if ( desk.contains("windows") )
                desktop = 0;
            else if ( desk.contains("gnome") )
                desktop = 1;
            else
                desktop = 2;

        } catch (Exception e) {
            desktop = 3;
        }


        userLanguage = System.getProperty("user.language"); //user.language=es
    }



	public static boolean isArchitech64() {
		return architech64;
	}



	public static void setArchitech64(boolean architech64) {
		Config.architech64 = architech64;
	}




}
