package google;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.api.detect.Detect;
import com.google.api.detect.DetectResult;

import clip.Cadenas;
import clip.Config;


/**
 * Dual Translation Google
 * <p>
 * Copyright (c) 2011 DualClip Translator, Inc. http://sourceforge.net/projects/dcliptranslator/
 * @author Cesar Rodriguez Gonzalez <Stelars@users.sourceforge.net>
 */
public class Traductor {

	private static final Log LOG = LogFactory.getLog( Traductor.class );



	/**
	 *	TRANSLATOR PASER GOOGLE WEB
	 *
	 * @param txt
	 * @param idiomaOrigen
	 * @param idiomaDestino
	 * @return String
	 */
	public static String execute(String txt, String idiomaOrigen, String idiomaDestino){

		String out = null;
//		String in = null;
//		try {
//			in = new String(read(idiomaOrigen, idiomaDestino, txt), "UTF-8");
//			log("GET: " + in);
//		} catch (UnsupportedEncodingException e) {
//			log ("URL Read: " + e.toString());
//		}
//
//		out = getTxtGoogleTranslator( in );

		out = read(idiomaOrigen, idiomaDestino, txt);

		if (out != null) {
			out = getTxtGoogleTranslator( out );
			log("EXECUTE GOOGLE:\n" + out);
		}

		return out;
	}


	/**
	 * 	TRANSLATOR PASER[] GOOGLE WEB
	 *
	 * @param txt
	 * @param idiomaOrigen
	 * @param idiomaDestino
	 * @return String[] Array
	 */
	public static String[] execute(String[] txt, String idiomaOrigen, String idiomaDestino){
		return Cadenas.strArray(
				execute( Cadenas.arrayStr(txt), idiomaOrigen, idiomaDestino)
				);
	}


	/**
	 *	READ URL HTTP://translate.google.com/	SINGLE Pharser
	 *
	 * @param from
	 * @param to
	 * @param string
	 * @return String
	 */
	public static String read(String from, String to, String string) {
		try {
			StringBuilder www = new StringBuilder();
			www.append("http://translate.google.com/translate_a/single?");
			www.append("client=gtx");
			//www.append("&hl=en");
			if (from == null) {
				www.append("&sl=en");
			} else {
				www.append("&sl=");
				www.append(from);
			}
			www.append("&tl=");
			www.append(to);
			www.append("&dt=t");
			www.append("&ie=utf-8&oe=utf-8");
			www.append("&q=");
			www.append(URLEncoder.encode(string.toString(), "UTF-8"));

			//POST /translate_a/t?client=t&sl=auto&tl=en&ie=utf-8&oe=utf-8 HTTP/1.1
			//http://translate.google.com/translate_a/single?client=gtx&hl=en&sl=en&tl=es&dt=t&ie=utf-8&oe=utf-8client=gtx&q=works+for+me+now


			//String agent = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:47.0) Gecko/20100101 Firefox/47.0";
			String agent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.82 Safari/537.36";

    		URL url = new URL( www.toString() );
            URLConnection conn = url.openConnection();

            log("URL String: " + url.toString());

    		// REQUEST PROPERTY of User Agent, simulate Web Browser
            conn.setRequestProperty("User-Agent", agent);

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String json = in.readLine();

            log("URL Read: " + json);


            return json;
//            byte[] bytes = json.getBytes("UTF-8");
//            in.close();
//            return bytes;

        } catch (Exception e) {
        	// java.io.IOException: Server returned HTTP response code: 414 for URL:
        	// java.io.IOException: Server returned HTTP response code: 503 for URL:  // Exceso de peticiones de traduccion
        	log ("URL Connection: " + e.toString());
            return null;
        }
    }



	/**
	 *	GET FRASE TRADUCIDA de READ HTTP://
	 *
	 * @param linea
	 * @return String
	 */
	public static String getTxtGoogleTranslator(String lineas) {

		StringBuilder get = new StringBuilder();
		String[] resultado;

		lineas = lineas.replace("[","Ç@").replace("\\\"","[comill@]");

		for (String str: lineas.split("Ç@")) {
			if (str.length() > 1) {
				log("SPLIT A: " + str);
				resultado = str.split("\",\"");

				log("LINEA: " + resultado[0]);
				get.append(resultado[0].replace("-\\u003e", "--\\u003e").replace("[comill@]","\"").replace("\"",""));
			}
    	}

//		int posA;
//		int posB= 0;
//		int last = lineas.lastIndexOf("orig\":");

//		do {
//			posA = lineas.indexOf("trans\":", posB) + 8;
//			posB = lineas.indexOf("orig\":", posA) - 3;
//
//			log("SUBSTRING:\n" + lineas.substring(posA, posB) +
//					"\nA:" + posA +
//					"\tB:" + posB
//			);
//
//
//
//
//			get.append(							// Restaurar flecha para SRT (Subtitles)
//					lineas.substring(posA, posB).replace("-\\u003e", "--\\u003e")
//					);
//		} while ( (posB +3) != last);

		return Cadenas.replaceEOL_0D0A(
				Cadenas.replaceUnicode( get.toString() )
				);
	}



	/**
	 *	GET FRASE TRADUCIDA de READ HTTP://
	 *
	 * @param linea
	 * @return String
	 */
	public static String getLanguageGoogleDetect(String linea) {

		String get = null;
		String[] arrayGet;

		try {
			int posA;
			int posB;

			log("DETECT LANGUAGE - LINEA:" + linea);

				posB = linea.lastIndexOf("]");
				posA = linea.lastIndexOf("[", posB);
				get = linea.substring(posA, posB);

				arrayGet = get.toString().replace("[","").replace("]","").replace("\"","").split(",");

				if (arrayGet[0] == "" || arrayGet[0] == null) {
					get = "en";
				} else {
					get = arrayGet[0];
				}


				log("DETECT LANGUAGE - SUBSTRING:" +
						"\nIdioma: " + get +
						"\tposicionA: " + posA +
						"\tposicionB: " + posB
				);


		} catch (Exception e) {
			log ("String indexOf/substring: " + e.toString());
		}

		return get;
	}



	/**
	 *  DETECTAR IDIOMA ENTRANTE de READ HTTP:/
	 * @param txt
	 * @return String
	 */
	public static String detectLanguageGoogle(String txt) {

		String detect = "auto";

		detect = read("auto", "en", txt);
		detect = getLanguageGoogleDetect( detect );
		detect = Languages.getLanguageName( detect );

		return detect;
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