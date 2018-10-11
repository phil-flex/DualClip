package microsoft;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import clip.Config;

import com.memetix.mst.detect.Detect;
import com.memetix.mst.language.Language;
import com.memetix.mst.translate.Translate;

/**
 * Dual Translation Microsoft
 * <p>
 * Copyright (c) 2011 DualClip Translator, Inc. http://sourceforge.net/projects/dcliptranslator/
 * @author Cesar Rodriguez Gonzalez <Stelars@users.sourceforge.net>
 */
public class Traductor {

	final static protected String ApiKey = "EC7354E23A1B5808C7A35505AAE12EFB4218DA2B";
	private static final Log LOG = LogFactory.getLog( Traductor.class );



	/**
	 *  TRADUCCION DEFINIENDO IDIOMAS
	 */
	public static String execute(String txt, String idiomaOrigen, String idiomaDestino) {

        // Set the Microsoft Translator API Key - Get yours at http://www.bing.com/developers/createapp.aspx
        Translate.setKey(ApiKey);
		String txtTraduccion = "";

	    try {
			txtTraduccion = Translate.execute(
					txt,
					Languages.getLanguageEnum(idiomaOrigen), Language.fromString(idiomaDestino));
			} catch (Exception e) {
				//java.lang.Exception: [microsoft-translator-api] Error retrieving translation.
				log("*MICROSOFT ERROR " + e);
				return null;
			}

		return txtTraduccion;
	  }



	/**
	 *  TRADUCCION DEFINIENDO IDIOMAS
	 */
	public static String[] execute(String[] txt, String idiomaOrigen, String idiomaDestino) {

		// Set the Microsoft Translator API Key - Get yours at http://www.bing.com/developers/createapp.aspx
        Translate.setKey(ApiKey);
		String[] txtTraduccion = new String[ txt.length ];

	    try {
			txtTraduccion = Translate.execute(
					txt,
					Languages.getLanguageEnum(idiomaOrigen), Language.fromString(idiomaDestino));
			} catch (Exception e) {
				log("*MICROSOFT ERROR [] " + e);
				return null;
			}

		return txtTraduccion;
	}



	/**
	 *  DETECTAR IDIOMA ENTRANTE
	 * @param txt
	 * @return String
	 */
	public static String detectLanguageMicrosoft(String txt) {
	    // Set the HTTP referrer to your website address.
	    Detect.setKey(ApiKey);
	    Language txtTraduccion = null;
	    String result = null;

	    try {
    		txtTraduccion = Detect.execute(txt);
    		result = txtTraduccion.name();
			} catch (Exception e) {
				log("*MICROSOFT DETECT LANGUAGE ERROR, SEARCH LANGUAGE");
				return null;
			}

		return result;
	}



   /**
    *  Send the output to the LOG
    *
    * @param text of output log
    */
    private static void log(String text) {
    	if (Config.isInfo) {
    		LOG.info(text + "\n");
    		}
    }




}
