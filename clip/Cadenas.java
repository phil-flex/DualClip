package clip;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class Cadenas {

	private static final Log LOG = LogFactory.getLog( Cadenas.class );
	private static final String FLECHA =" --> ";
	private static final String FLECHACHANGE =" 1975 ";
	private static final char PUNTOS =':';
	private static final char COMA =',';
	private static final char PUNTOSCHANGE ='ç';
	private static final char COMACHANGE ='Ç';




   /**
    *	REPLACE to Mark
    *
    * @param String
    * @return String Mark
    */
   public static String replaceEOL_Mark(String txt) {
	   StringBuffer cadena = new StringBuffer();
	   char eolr = '\r';
	   char eoln = '\n';
	   int txtLength = txt.length();

	   for (int i = 0; i < txtLength; i ++) {
	      char caracter = txt.charAt(i);

	      if (caracter == eolr)
	    	 cadena.append("\\r");
	      else
	      if (caracter == eoln)
	 	    	 cadena.append("\\n");
	      else
	    	  cadena.append(caracter);
	   }

	   log("TXT Mark ReplaceEOL:\n" + cadena.toString());
	   return cadena.toString();
   }



   /**
    *	REPLACE Repair EOL
    *
    * @param String
    * @return String EOL
    */
   public static String replaceEOL_0D0A(String txt) {
	   StringBuffer cadena = new StringBuffer( );
	   int txtLength = txt.length();

	   for (int i = 0; i < txtLength; i ++) {
	      char caracter = txt.charAt(i);

	      if (caracter == '\\') {

	    	  if (i+1 < txtLength) {
		    	  char eol = txt.charAt(i+1);
		    	  if (eol == 'r') {
		    		  cadena.append('\r');
		    		  i++;
		    	  }
		    	  else
		    	  if (eol == 'n') {
		    		  cadena.append('\n');
		    		  i++;
		    	  }
	    	  }
	      }
	      else
	    	  cadena.append(caracter);
	   }

	   log("TXT Translation & ReplaceEOL:\n" + cadena.toString());
	   return cadena.toString();
   }






   /**
    *	REPLACE SRT
    *
    * @param String
    * @return String Mark
    */
   public static String replaceSRT_Mark(String txt) {
	   StringBuffer cadena = new StringBuffer();
	   txt = txt.replaceAll(FLECHA, FLECHACHANGE);
	   int txtLength = txt.length();
	   int con = 0;

	   for (int i = 0; i < txtLength; i ++) {
	      char caracter = txt.charAt(i);

	      if (isNumeric(caracter)) con++;
	      else
	    	  con = 0;

    	  if ( con == 2 && i+1 < txtLength ) {
    		  if (txt.charAt(i+1) == PUNTOS) {
    			  cadena.append(caracter);
    			  cadena.append(PUNTOSCHANGE);
    			  i++;
    		  }
    		  else
    		  if (txt.charAt(i+1) == COMA) {
    			  cadena.append(caracter);
    			  cadena.append(COMACHANGE);
    			  i++;
        		  }
    		  else
    			  cadena.append(caracter);

    		  con = 0;
    	  }
    	  else {
    		  cadena.append( caracter);
    	  }

	   }
	   return cadena.toString();
   }



   /**
    *	REPLACE REPAIR SRT
    *
    * @param String
    * @return String Repair Original
    */
   public static String replaceSRT_Original(String txt) {
	   StringBuffer cadena = new StringBuffer();
	   txt = txt.replaceAll(FLECHACHANGE, FLECHA);
	   int txtLength = txt.length();
	   int con = 0;

	   for (int i = 0; i < txtLength; i ++) {
	      char caracter = txt.charAt(i);

	      if (isNumeric(caracter)) con++;
	      else
	    	  con = 0;

    	  if ( con == 2 && i+1 < txtLength ) {
    		  if (txt.charAt(i+1) == PUNTOSCHANGE) {
    			  cadena.append(caracter);
    			  cadena.append(PUNTOS);
    			  i++;
    		  }
    		  else
    		  if (txt.charAt(i+1) == COMACHANGE) {
    			  cadena.append(caracter);
    			  cadena.append(COMA);
    			  i++;
        		  }
    		  else
    			  cadena.append(caracter);

    		  con = 0;
    	  }
    	  else {
    		  cadena.append( caracter);
    	  }

	   }
	   return cadena.toString();
   }



   /**
    *	REPLACE SYMBOLS to SPACE
    *
    * @param String
    * @return String
    */
   public static String replaceSymbols_SPACE(String txt) {
	   StringBuffer cadena = new StringBuffer();
	   int txtLength = txt.length();
	   char eolr = '\r';
	   char eoln = '\n';
	   boolean is;
	   String cad = ",.:;?!\"^~_-/|`'()[]{}<>+-=$%#&*0123456789";
	   int cadLength = cad.length();


	   for (int i = 0; i < txtLength; i ++) {
	      char caracter = txt.charAt(i);

	      is = false;
	      for (int con = 0; con < cadLength; con++) {
	    	  if (cad.charAt(con) == caracter) {
	    		  cadena.append(" ");
	    		  is = true;
	    		  break;
	    	  }
	      }

	      if (caracter == eolr)
	    	 cadena.append(" ");
	      else
	      if (caracter == eoln)
	 	    	 cadena.append(" ");
	      else
	      if (!is)
	    	  cadena.append(caracter);
	   }

	   log("TXT Replace EOL&Symbols to SPACE:\n" + cadena.toString());
	   return cadena.toString();
   }



	/**
	 * Verify String is Numbers o Words
	 *
	 * @param cadena
	 * @return
	 */
	private static boolean isNumeric(char cadena){
		try {
			Double.parseDouble( Character.toString(cadena) );
			return true;
		} catch (NumberFormatException nfe){
			return false;
		}
	}





   /**
    *	Convert String to Array[]
    *
    * @param String
    * @return Array[]
    */
   public static String[] strArray(String lineas) {

	   if (lineas != null) {
		   int contadorLineas = 0;
		   ArrayList<String> lineasNull = new ArrayList<String>();
		   BufferedReader reader = new BufferedReader(
				   new StringReader(lineas));

		   // PHARSER de las lineas del texto a Array
			try {
					while ((lineas = reader.readLine()) != null) {
						contadorLineas++;
						lineasNull.add( lineas );
					}//end while

				} catch(IOException e) {
					log ("StrArray: " + e.toString());
					return null;
				}


				// LINEAS con ARRAY ajustado SIN NULL
				String[] txtTraduccion = new String[ contadorLineas ];

				contadorLineas = 0;
				for (String get : lineasNull) {
					if (get == null) break;
					txtTraduccion [ contadorLineas++ ] = get;
				}

			return txtTraduccion;
	   }

	   return null;
   }



   /**
    * 	Convert Array[] to String
    *
    * @param Array[]
    * @return String
    */
   public static String arrayStr(String[] txt) {

	    StringBuilder out = new StringBuilder();
		String EOL = System.getProperty("line.separator");


		// String ARRAY to BUILDER
		for (String linea : txt) {
			out.append( linea ).append( EOL );
		}

		// Suprimir el EOL final
		out.setLength(
				out.length() - EOL.length()
				);

		return out.toString();
   }


   /**
    * REPLACE Unicode String
    *
    * @param txt
    * @return
    */
   public static String replaceUnicode(String txt) {
		   txt=txt.replace("\\u0001","âº");
		   txt=txt.replace("\\u0002","â»");
		   txt=txt.replace("\\u0003","â¥");
		   txt=txt.replace("\\u0004","â¦");
		   txt=txt.replace("\\u0005","â£");
		   txt=txt.replace("\\u0006","â ");
		   txt=txt.replace("\\u0007","â¢");
		   txt=txt.replace("\\u0008","â");
		   txt=txt.replace("\\u0009","â");
		   txt=txt.replace("\\u000A","â");
		   txt=txt.replace("\\u000B","â");
		   txt=txt.replace("\\u000C","â");
		   txt=txt.replace("\\u000D","âª");
		   txt=txt.replace("\\u000E","â«");
		   txt=txt.replace("\\u000F","â¼");
		   txt=txt.replace("\\u0010","âº");
		   txt=txt.replace("\\u0011","â");
		   txt=txt.replace("\\u0012","â");
		   txt=txt.replace("\\u0013","â¼");
		   txt=txt.replace("\\u0014","Â¶");
		   txt=txt.replace("\\u0015","Â§");
		   txt=txt.replace("\\u0016","?");
		   txt=txt.replace("\\u0017","?");
		   txt=txt.replace("\\u0018","â");
		   txt=txt.replace("\\u0019","â");
		   txt=txt.replace("\\u001A","â");
		   txt=txt.replace("\\u001B","â");
		   txt=txt.replace("\\u001C","â");
		   txt=txt.replace("\\u001D","â");
		   txt=txt.replace("\\u001E","â²");
		   txt=txt.replace("\\u001F","â¼");
		   txt=txt.replace("\\u0020"," ");
		   txt=txt.replace("\\u0021","!");
		   txt=txt.replace("\\u0022","\"");
		   txt=txt.replace("\\u0023","#");
		   txt=txt.replace("\\u0024","$");
		   txt=txt.replace("\\u0025","%");
		   txt=txt.replace("\\u0026","&");
		   txt=txt.replace("\\u0027","'");
		   txt=txt.replace("\\u0028","(");
		   txt=txt.replace("\\u0029",")");
		   txt=txt.replace("\\u002A","*");
		   txt=txt.replace("\\u002B","+");
		   txt=txt.replace("\\u002C",",");
		   txt=txt.replace("\\u002D","-");
		   txt=txt.replace("\\u002E",".");
		   txt=txt.replace("\\u2026","â¦");
		   txt=txt.replace("\\u002F","/");
		   txt=txt.replace("\\u0030","0");
		   txt=txt.replace("\\u0031","1");
		   txt=txt.replace("\\u0032","2");
		   txt=txt.replace("\\u0033","3");
		   txt=txt.replace("\\u0034","4");
		   txt=txt.replace("\\u0035","5");
		   txt=txt.replace("\\u0036","6");
		   txt=txt.replace("\\u0037","7");
		   txt=txt.replace("\\u0038","8");
		   txt=txt.replace("\\u0039","9");
		   txt=txt.replace("\\u003A",":");
		   txt=txt.replace("\\u003B",";");
		   txt=txt.replace("\\u003c","<");
		   txt=txt.replace("\\u003D","=");
		   txt=txt.replace("\\u003e",">");
		   txt=txt.replace("\\u2264","â¤");
		   txt=txt.replace("\\u2265","â¥");
		   txt=txt.replace("\\u003F","?");
		   txt=txt.replace("\\u0040","@");
		   txt=txt.replace("\\u0041","A");
		   txt=txt.replace("\\u0042","B");
		   txt=txt.replace("\\u0043","C");
		   txt=txt.replace("\\u0044","D");
		   txt=txt.replace("\\u0045","E");
		   txt=txt.replace("\\u0046","F");
		   txt=txt.replace("\\u0047","G");
		   txt=txt.replace("\\u0048","H");
		   txt=txt.replace("\\u0049","I");
		   txt=txt.replace("\\u004A","J");
		   txt=txt.replace("\\u004B","K");
		   txt=txt.replace("\\u004C","L");
		   txt=txt.replace("\\u004D","M");
		   txt=txt.replace("\\u004E","N");
		   txt=txt.replace("\\u004F","O");
		   txt=txt.replace("\\u0050","P");
		   txt=txt.replace("\\u0051","Q");
		   txt=txt.replace("\\u0052","R");
		   txt=txt.replace("\\u0053","S");
		   txt=txt.replace("\\u0054","T");
		   txt=txt.replace("\\u0055","U");
		   txt=txt.replace("\\u0056","V");
		   txt=txt.replace("\\u0057","W");
		   txt=txt.replace("\\u0058","X");
		   txt=txt.replace("\\u0059","Y");
		   txt=txt.replace("\\u005A","Z");
		   txt=txt.replace("\\u005B","[");
		   txt=txt.replace("\\u005C","\\");
		   txt=txt.replace("\\u005D","]");
		   txt=txt.replace("\\u005E","^");
		   txt=txt.replace("\\u005F","_");
		   txt=txt.replace("\\u0060","`");
		   txt=txt.replace("\\u0061","a");
		   txt=txt.replace("\\u0062","b");
		   txt=txt.replace("\\u0063","c");
		   txt=txt.replace("\\u0064","d");
		   txt=txt.replace("\\u0065","e");
		   txt=txt.replace("\\u0066","f");
		   txt=txt.replace("\\u0067","g");
		   txt=txt.replace("\\u0068","h");
		   txt=txt.replace("\\u0069","i");
		   txt=txt.replace("\\u006A","j");
		   txt=txt.replace("\\u006B","k");
		   txt=txt.replace("\\u006C","l");
		   txt=txt.replace("\\u006D","m");
		   txt=txt.replace("\\u006E","n");
		   txt=txt.replace("\\u006F","o");
		   txt=txt.replace("\\u0070","p");
		   txt=txt.replace("\\u0071","q");
		   txt=txt.replace("\\u0072","r");
		   txt=txt.replace("\\u0073","s");
		   txt=txt.replace("\\u0074","t");
		   txt=txt.replace("\\u0075","u");
		   txt=txt.replace("\\u0076","v");
		   txt=txt.replace("\\u0077","w");
		   txt=txt.replace("\\u0078","x");
		   txt=txt.replace("\\u0079","y");
		   txt=txt.replace("\\u007A","z");
		   txt=txt.replace("\\u007B","{");
		   txt=txt.replace("\\u007C","|");
		   txt=txt.replace("\\u007D","}");
		   txt=txt.replace("\\u02DC","Ë");
		   txt=txt.replace("\\u007E","â¼");
		   txt=txt.replace("\\u007F","");
		   txt=txt.replace("\\u00A2","Â¢");
		   txt=txt.replace("\\u00A3","Â£");
		   txt=txt.replace("\\u00A4","Â¤");
		   txt=txt.replace("\\u20AC","â¬");
		   txt=txt.replace("\\u00A5","Â¥");
		   txt=txt.replace("\\u0026quot;","\"");
		   txt=txt.replace("\\u0026gt;",">");
		   txt=txt.replace("\\u0026lt;",">");
		   return txt;
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
