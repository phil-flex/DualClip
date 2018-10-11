package execute;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import clip.Config;


public class Engine {

	private static final Log LOG = LogFactory.getLog( Engine.class );


	/**
	 *  DECTECTOR IDIOMA
	 *
	 * @param txt
	 * @return
	 */
	public static String detect(String txt){
		String out = null;

	    switch (Config.getEngine()){
	    case 0:
	    	out = google.Traductor.detectLanguageGoogle(txt);

	    	if (out == null) {
				Config.setEngineSwitch( true );
				out = microsoft.Traductor.detectLanguageMicrosoft(txt);
				log ("ERROR ENGINE DETECT CONNECTION GOOGLE");
	    	}

	    	break;
	    case 1:
	    	out = microsoft.Traductor.detectLanguageMicrosoft(txt);
	    }

	    Config.sleep();
	    return out;
	}



	/**
	 *  TRADUCCION DEFINIENDO IDIOMAS
	 */
	public static String execute(String txt, String idiomaOrigen, String idiomaDestino) {
	    String txtTraduccion = "";

	    switch (Config.getEngine()){
	    case 0:
				txtTraduccion = google.Traductor.execute(txt, google.Languages.getLanguage(idiomaOrigen), google.Languages.getLanguage(idiomaDestino) );

				if (txtTraduccion == null) {
					Config.setEngineSwitch( true );
					txtTraduccion = microsoft.Traductor.execute(txt, google.Languages.getLanguage(idiomaOrigen), google.Languages.getLanguage(idiomaDestino) );
					log ("ERROR ENGINE CONNECTION GOOGLE");
				}

	    	break;
	    case 1:
	    	txtTraduccion = microsoft.Traductor.execute(txt, idiomaOrigen, microsoft.Languages.getLanguage(idiomaDestino) );
	    }

	    log("Engine execute: " + Config.getEngine() + " " + Config.getEngineName() );

		Config.sleep();
	    return txtTraduccion;
	}



	/**
	 *  TRADUCCION DEFINIENDO IDIOMAS
	 */
	public static String[] execute(String[] txt, String idiomaOrigen, String idiomaDestino) {
	    String[] txtTraduccion = new String[ txt.length ];

	    switch (Config.getEngine()){
	    case 0:
				txtTraduccion = google.Traductor.execute(txt, google.Languages.getLanguage(idiomaOrigen), google.Languages.getLanguage(idiomaDestino) );

				if (txtTraduccion == null) {
					Config.setEngineSwitch( true );
					txtTraduccion = microsoft.Traductor.execute(txt, idiomaOrigen, microsoft.Languages.getLanguage(idiomaDestino) );
					log ("ERROR ENGINE CONNECTION GOOGLE");
				}

	    	break;
	    case 1:
	    	txtTraduccion = microsoft.Traductor.execute(txt, idiomaOrigen, microsoft.Languages.getLanguage(idiomaDestino) );
	    }

	    log("Engine execute: " + Config.getEngine() + " " + Config.getEngineName() );

		Config.sleep();
		return txtTraduccion;
	}



	/**
	 * Verify valid Language of list support Google/Microsoft API
	 * @param language
	 * @return
	 */
	public static String isValidLanguage(String language) {

		String getLanguage = "";

	    switch (Config.getEngine()){
	    case 0:
	    	getLanguage = google.Languages.isValidLanguage( language );
	    	break;
	    case 1:
	    	getLanguage = microsoft.Languages.isValidLanguage( language );
	    }

		return getLanguage;
	}



	/**
	 * View in Console of list de languages
	 */
	public static void listValidLanguages() {
	    switch (Config.getEngine()){
	    case 0:
	    	google.Languages.listValidLanguages();
	    	break;
	    case 1:
	    	microsoft.Languages.listValidLanguages();
	    }
	}



   /**
    * Send the output to the LOG
    *
    * @param text of output log
    */
    private static void log(String text) {
    	if (Config.isInfo) {
    		LOG.info(text + "\n");
    		}
    }



}
