package google;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

import com.google.api.translate.Language;


/**
 * Lista de Languages Soportados por Google API Translator
 * <p>
 * Copyright (c) 2011 DualClip Translator, Inc. http://sourceforge.net/projects/dcliptranslator/
 * @author Cesar Rodriguez Gonzalez <Stelars@users.sourceforge.net>
 */
public class Languages {

	private static final long serialVersionUID = -2203304643279961082L;


	public static String getLanguage(String idioma_Spanish){
		HashMap<String, Language> idiomas;
		idiomas = idiomasHashMap();

		return (idiomas.get(idioma_Spanish).toString());
	}


//	public static Language getLanguageEnum(String idioma){
//		HashMap<String, Language> idiomas;
//		idiomas = idiomasHashMap();
//
//		return idiomas.get(idioma);
//	}


	public static String getLanguageName(String idioma_es){
		String lang = null;

		for (Language ENUM : Language.values())
			if (ENUM.toString().equals(idioma_es))
				return ENUM.name();

		return lang;
	}


	/**
	 *  HashMap para el COMBOBOX de los Idiomas
	 */
	public static HashMap<String, Language> idiomasHashMap() {

		HashMap<String, Language> idiomas;
		idiomas = new LinkedHashMap<String, Language>();

		idiomas.put("Auto_detect", Language.AUTO_DETECT);
		idiomas.put("Afrikaans", Language.AFRIKAANS);
        idiomas.put("Albanian", Language.ALBANIAN);
        idiomas.put("Arabic", Language.ARABIC);
        idiomas.put("Belarusian", Language.BELARUSIAN);
        idiomas.put("Bulgarian", Language.BULGARIAN);
        idiomas.put("Catalan", Language.CATALAN);
        idiomas.put("Chinese_simplified", Language.CHINESE_SIMPLIFIED);
        idiomas.put("Chinese_traditional", Language.CHINESE_TRADITIONAL);
        idiomas.put("Croatian", Language.CROATIAN);
        idiomas.put("Czech", Language.CZECH);
        idiomas.put("Danish", Language.DANISH);
        idiomas.put("Dutch", Language.DUTCH);
        idiomas.put("English", Language.ENGLISH);
        idiomas.put("Estonian", Language.ESTONIAN);
        idiomas.put("Filipino", Language.FILIPINO);
        idiomas.put("Finnish", Language.FINNISH);
        idiomas.put("French", Language.FRENCH);
        idiomas.put("Galician", Language.GALICIAN);
        idiomas.put("Georgian", Language.GEORGIAN);
        idiomas.put("German", Language.GERMAN);
        idiomas.put("Greek", Language.GREEK);
        idiomas.put("Hebrew", Language.HEBREW);
        idiomas.put("Hindi", Language.HINDI);
        idiomas.put("Hungarian", Language.HUNGARIAN);
        idiomas.put("Icelandic", Language.ICELANDIC);
        idiomas.put("Indonesian", Language.INDONESIAN);
        idiomas.put("Irish", Language.IRISH);
        idiomas.put("Italian", Language.ITALIAN);
        idiomas.put("Japanese", Language.JAPANESE);
        idiomas.put("Korean", Language.KOREAN);
        idiomas.put("Latvian", Language.LATVIAN);
        idiomas.put("Lithuanian", Language.LITHUANIAN);
        idiomas.put("Macedonian", Language.MACEDONIAN);
        idiomas.put("Malay", Language.MALAY);
        idiomas.put("Maltese", Language.MALTESE);
        idiomas.put("Norwegian", Language.NORWEGIAN);
        idiomas.put("Persian", Language.PERSIAN);
        idiomas.put("Polish", Language.POLISH);
        idiomas.put("Portuguese", Language.PORTUGUESE);
        idiomas.put("Romanian", Language.ROMANIAN);
        idiomas.put("Russian", Language.RUSSIAN);
        idiomas.put("Serbian", Language.SERBIAN);
        idiomas.put("Slovak", Language.SLOVAK);
        idiomas.put("Slovenian", Language.SLOVENIAN);
        idiomas.put("Spanish", Language.SPANISH);
        idiomas.put("Swahili", Language.SWAHILI);
        idiomas.put("Swedish", Language.SWEDISH);
        idiomas.put("Thai", Language.THAI);
        idiomas.put("Turkish", Language.TURKISH);
        idiomas.put("Ukranian", Language.UKRANIAN);
        idiomas.put("Vietnamese", Language.VIETNAMESE);
        idiomas.put("Welsh", Language.WELSH);
        idiomas.put("Yiddish", Language.YIDDISH);

        return idiomas;
	}



	/**
	 * Verify valid Language of list support Google API
	 * @param language
	 * @return
	 */
	public static String isValidLanguage(String language){
		HashMap<String, Language> idiomas;
		idiomas = idiomasHashMap();

		/*
        To get all keys stored in HashMap use keySet method
        Signature of the keysSet method is,
        Set keySet()
        */

        Iterator<String> i = idiomas.keySet().iterator();

        while(i. hasNext()){
        	if (i.next().equals(language)){
        	return language;
        	}
        }

        return "";
	}



	/**
	 * View in Console of list de languages
	 */
	public static void listValidLanguages(){
		HashMap<String, Language> idiomas;
		idiomas = idiomasHashMap();

		/*
        To get all keys stored in HashMap use keySet method
        Signature of the keysSet method is,
        Set keySet()
        */

        Iterator<String> i = idiomas.keySet().iterator();

        while(i. hasNext()){
        	System.out.print( i.next() +"\n");
        }

        System.out.println();
	}



}
