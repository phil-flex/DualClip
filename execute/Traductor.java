package execute;

import java.awt.TrayIcon.MessageType;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import keys.KeysRobot;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import clip.Cadenas;
import clip.Config;
import clip.Message;
import clip.Portapapeles;
import form.Tray;


/**
 * Dual Translation Execute
 * <p>
 * Copyright (c) 2011 DualClip Translator, Inc. http://sourceforge.net/projects/dcliptranslator/
 * @author Cesar Rodriguez Gonzalez <Stelars@users.sourceforge.net>
 */
public class Traductor {

    private static String txtOriginal;
    private static String txtTraducido;
    private static final Log LOG = LogFactory.getLog( Traductor.class );




    public static String getTxtOriginal() {
        return txtOriginal;
    }
    public static void setTxtOriginal(String txtOriginal) {
        Traductor.txtOriginal = txtOriginal;
    }
    public static String getTxtTraducido() {
        return txtTraducido;
    }
    public static void setTxtTraducido(String txtTraducido) {
        Traductor.txtTraducido = txtTraducido;
    }



    /**
     *  DETECTION DEL IDIOMA
     *
     * @param txt
     * @return String
     */
    public static String detectLanguage(String txt) {
        // Comprobar el idioma del contexto
            StringBuilder idiomasLog = new StringBuilder();
            String[] idioma = new String[2];
            String idiomaDetectado = null;
            String idiomaAsignado = null;
            String linea;
            int esteLength = 0;
            int vecesDetectado = 0;


            // GET IDIOMA DETECT
            idioma = idiomaDetectFilter(txt);

            // SWITCH DETECT ENGINE
            if (idioma[0] == null && idioma[1] == null) {
                log("\nMETHOD SWITCH DETECT DUE NULL");
                Config.setEngineSwitch( true );
                   idioma = idiomaDetectFilter(txt);
                Config.setEngineSwitch( false );
            }

            idiomaDetectado = idioma[0];
            idiomaAsignado 	= idioma[1];


         // NULL del Idioma
            if (idiomaAsignado != null) {
                   if (!idiomaAsignado.equals("AUTO_DETECT") && !idiomaAsignado.equals("ENGLISH"))
                    idiomaDetectado = idiomaAsignado;

                if (idiomaAsignado.equals("URDU"))
                           idiomaDetectado = "ARABIC";
            }



        /* Detectar por Línea el Idioma
         * cuando los filtros fallan en textos de Asia
         */
        if (idiomaDetectado == null) {
            log("Method 1 SEARCH LANGUAGE");

            BufferedReader reader = new BufferedReader(
                    new StringReader(txt));

            try {
                 while ((linea = reader.readLine()) != null) {
                     if (linea.length() > 2){
                         idiomaDetectado = ( Engine.detect(
                                 linea//.replaceAll("[^a-zA-Záéíóú:;.¡!¿?]", " ")
                                 ));
                         log("Line Read: " + linea.length() +" :: "+ idiomaDetectado +" :: "+ linea);

                         // METODO ESTADISTICA (Idioma de la línea con mayor longitud detectada, suele ser la correcta)
                         if (idiomaDetectado != null && !idiomaDetectado.equals("AUTO_DETECT")) {
                             // LOG
                             if (Config.isInfo) idiomasLog.append( idiomaDetectado ).append(" ");

                             if (linea.length() > esteLength) {
                                 idiomaAsignado = idiomaDetectado;
                                 esteLength =  linea.length();
                             }
                             if (++vecesDetectado > 1 ) {
                                 break;
                             }
                         }

                     }
                 }// end while
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                idiomaDetectado = idiomaAsignado;
            }

            // AUTO
            if (idiomaDetectado == null) {
                idiomaDetectado = "Auto_detect";
                log("Method 2 Auto");
            }

            // LOG LANGUAGE
            log("Language Find: "+ vecesDetectado +"veces  " +idiomasLog);
        }//end idiomaDetectado == null

        // Capitalize first letter for recognizer of language
        if (idiomaDetectado.length() != 0){
            idiomaDetectado = String.format( "%s%s",
                    idiomaDetectado.charAt(0),
                    idiomaDetectado.substring(1).toLowerCase() );
        }

        log("Language Detect: " + idiomaDetectado);

        return idiomaDetectado;
      }



    /**
     *  FILTER DETECT IDIOMA
     *
     * @param txt
     * @return idioma[]
     */
    private static String[] idiomaDetectFilter(String txt) {

        String[] idioma = new String[2];
        String txtFilter;
        final int cut = 100;	// Limite de caracteres a enviar al Detector de idiomas
        int length;

            String latin ="[^a-zA-Z0-9áéíóúòÁÉÍÓÚÜÒñãêÁãõäöüðýèßÅ©®€“”’«»çÑÊÃÊÁÃÕ]";
            String more = "[a-zA-Z0-9áéíóúòÁÉÍÓÚÜÒñêãêÁãõäöüðýèßÅ©®€“”’«»çÑÊÃÊÁÃÕ:;,.¡!¿?|]";

            // FILTER SYMBOLS */
            txt = Cadenas.replaceSymbols_SPACE(txt);

            /* LATIN */// FILTER SPACES */
            txtFilter = txt.replaceAll( latin, " ").replaceAll("  ", "").trim();

            // CUT/LIMITAR longitud del texto a Detectar */
            length = txtFilter.length();
            txtFilter = txtFilter.substring(0,
                (length > cut)? cut: length);

            // DETECT */
            if (txtFilter.length() != 0)
                   idioma[0] = Engine.detect( txtFilter );


            /* MORE */// FILTER SPACES */
            txtFilter = txt.replaceAll( more, " ").replaceAll("  ", "").trim();
            log ("Method 0 FILTER - TAMAÑO LATIN ALL: " + length + "    /3:" + length/3 + "    TAMAÑO MORE: " + txtFilter.length());

            if (idioma[0] == null || (length/3) < txtFilter.length()) {
                log("Method 0 FILTER - MORE ANALYZE");

                	length = txtFilter.length();
                	// CUT/LIMITAR longitud del texto a Detectar */
                	txtFilter = txtFilter.substring(0,
                			(length > cut)? cut: length);

                	// DETECT */
                	if (txtFilter.length() != 0)
                		idioma[1] = Engine.detect( txtFilter );

                	if (idioma[1] == null) {
                		Config.setEngineSwitch( true );
                		idioma[1] = Engine.detect( txtFilter );
                		Config.setEngineSwitch( false );
                		log("\nMETHOD SWITCH DETECT ONE "+ idioma[1]);
                	}
            }


            // LOG of Filter Detect
            log("Method 0 FILTER DETECT - LATIN: "+ idioma[0]
                    + " ."+ txt.replaceAll( latin, " ").replaceAll("  ", "").trim() + ".");
            log("Method 0 FILTER DETECT - MORE: "+ idioma[1]
                    + " ."+ txt.replaceAll( more, "").replaceAll("  ", "").trim() + ".");

        return idioma;
    }



    /**
     *  DUAL TRANSLATION LITE de un TXT			(Texto)
     *
     * @param enviarTraduccionAlClipboard
     * @return String
     */
    public static String goTraducirTextoLite(String txt, String idiomaDetectado, boolean enviarTraduccionAlClipboard){

        StringBuilder txtTraducido = new StringBuilder();
        String idiomaPreferido = Config.getIdiomaPreferido();
        String idiomaDual = Config.getIdiomaDual();

// TODO revisar y quitar
//      if (isContent(txt)) {

            // Detectar la Traducción Dual/Preferida
            if (idiomaDetectado.equals(idiomaPreferido)){
                txtTraducido.append( Engine.execute(
                                            txt,
                                            idiomaDetectado,
                                            idiomaDual
                                            ));
            }
            else
            {
                txtTraducido.append( Engine.execute(
                                            txt,
                                            idiomaDetectado,
                                            idiomaPreferido
                                            ));
            }

            // El texto contenido en el portapapeles, pudiendo ser enviado traducido al portapapeles
            if (enviarTraduccionAlClipboard)
                Portapapeles.setClipboard(txtTraducido.toString());
//        }


        if (txtTraducido == null || txtTraducido.toString().equals("null"))
        	return null;
        else
        	return txtTraducido.toString();
    }



    /**
     *  DUAL TRANSLATION LITE de un TXT ARRAY			(Texto Array)
     *
     * @param enviarTraduccionAlClipboard
     * @return String
     */
    public static String[] goTraducirTextoLite(String[] txt, String idiomaDetectado){

        //StringBuilder txtTraducido = new StringBuilder();
        String[] txtTraducido;
        String idiomaPreferido = Config.getIdiomaPreferido();
        String idiomaDual = Config.getIdiomaDual();

// TODO revisar y quitar
//        if (isContent(txt)) {
	        // Detectar la Traducción Dual/Preferida
	        if (idiomaDetectado.equals(idiomaPreferido)){
	            txtTraducido = Engine.execute(
	                                        txt,
	                                        idiomaDetectado,
	                                        idiomaDual
	                                        );
	        }
	        else
	        {
	            txtTraducido = Engine.execute(
	                                        txt,
	                                        idiomaDetectado,
	                                        idiomaPreferido
	                                        );
	        }
	        return txtTraducido;
//        }// end if

//        return null;
    }



    /**
     *  DUAL ADVANCED TRANSLATION del PORTAPAPELES / Clipboard
     *
     * @param enviarTraduccionAlClipboard
     * @return String
     */
    public static String goTraducirPortapapelesAvanzado(boolean enviarTraduccionAlClipboard){

    	Portapapeles clip = new Portapapeles();
        String txt = clip.getClipboardOs();

        if (isContent(txt))
        	return goTraducirTextoAvanzado(txt, enviarTraduccionAlClipboard);
        else
        	return "";
    }



    /**
     *  DUAL TRANSLATION COMPLEJA (Speed Method) de un Texto
     *
     * @param enviarTraduccionAlClipboard
     * @return String
     */
    public static String goTraducirTextoAvanzado(String txt, boolean enviarTraduccionAlClipboard){

        StringBuilder txtBuilderTraducido = new StringBuilder();
        String linea;
        String idiomaDetectado = "";
        String[] txtArrayTraducido = null;
        ArrayList<String> lineasNull = new ArrayList<String>();
        String EOL = System.getProperty("line.separator");
        int contadorLineas = 0;
        int limite = 0;
        int length;



        if (!isContent(txt)) return "";


        // DETECT IDIOMA
        idiomaDetectado = detectLanguage( txt );
        if (idiomaDetectado == null)
            return "No Internet Conection to Google/Microsoft";

        setTxtOriginal( txt );

        // REPLACE for Microsoft
        if (Config.getEngine() == 1) {
            // REPLACE EOL for Microsoft
            txt = Cadenas.replaceEOL_Mark( txt );
            // REPLACE : SRT (Subtitles) for Microsoft
            txt = Cadenas.replaceSRT_Mark( txt );
        }

        BufferedReader reader = new BufferedReader(
                new StringReader( txt ));

        // PHARSER de las lineas del texto a Array
        try {
                while ((linea = reader.readLine()) != null) {
                    contadorLineas++;
                    lineasNull.add( linea );
                }//end while
            } catch(IOException e) {
                e.printStackTrace();
            }


            // LINEAS con ARRAY ajustado SIN NULL
            String[] lineas = new String[ contadorLineas ];

            contadorLineas = 0;
            for (String get : lineasNull) {
                if (get == null) break;
                lineas [ contadorLineas++ ] = get;
            }


            // Limite de las lineas que puede traducir en una petición
            switch (Config.getEngine()) {
            case 0:
                limite = 1200;	// Limite de Google
                break;

            case 1:
                limite = 1800;	// Limite de Microsoft
            }


            length = txt.length();

            if (length != 0) {
                if (length < limite) {
                    txtArrayTraducido = Traductor.goTraducirTextoLite(lineas, idiomaDetectado);

                    if (txtArrayTraducido != null) {             // ARRAY Method
                        for (String get : txtArrayTraducido) {	// String ARRAY to BUILDER
                            txtBuilderTraducido.append( get ).append( EOL );
                        }

                        // Suprimir el EOL final
                        txtBuilderTraducido.setLength(
                                txtBuilderTraducido.length() - EOL.length()
                                );
                    }//end if Array
                }//end if lengthMaximo


                // ALTERNATIVE METHOD, cuando no traduce correctamente o son más del "limite" de palabras en el array
                if (txtArrayTraducido == null) {
                    log("ALTERNATIVE METHOD LINEAS");
                    txtBuilderTraducido.delete(0, txtBuilderTraducido.length());
                    txtBuilderTraducido.append(
                            Traductor.goTraducirTextoAvanzadoLineas(txt, idiomaDetectado, false)
                            );
                }//end Alternative


            // REPLACE for Microsoft
            if (Config.getEngine() == 1) {
                // REPLACE EOL for Microsoft
                txt = Cadenas.replaceEOL_0D0A( txtBuilderTraducido.toString() );
                // REPLACE : SRT (Subtitles) for Microsoft
                txt = Cadenas.replaceSRT_Original( txt );
                // ReBuilder
                txtBuilderTraducido.delete(0, txtBuilderTraducido.length());
                txtBuilderTraducido.append( txt );
            }


            // Mostrar en Consola la Traducción
            System.out.println( txtBuilderTraducido.toString() );

            // El texto contenido en el portapapeles, pudiendo ser enviado traducido al portapapeles
            if (enviarTraduccionAlClipboard)
                Portapapeles.setClipboard( txtBuilderTraducido.toString() );

            setTxtTraducido( txtBuilderTraducido.toString() );
            }//end if lenth Zero

        return txtBuilderTraducido.toString();
    }



    /**
     *  DUAL TRANSLATION LINEAS (Slow Method) de un Texto
     * <p>
     * Este metodo llama al traductor google por linea procesada.
     * Es necesario cuando el contexto completo no es traducido.
     * @param enviarTraduccionAlClipboard
     * @return String
     */
    public static String goTraducirTextoAvanzadoLineas(String txt, String idiomaDetectado, boolean enviarTraduccionAlClipboard){

        StringBuilder out = new StringBuilder(); // Mejor rendimiento en concatenación de cadenas
        String linea, get;
        String enter = System.getProperty("line.separator");
        boolean verify = false;

        BufferedReader reader = new BufferedReader(
          new StringReader(txt));


        setTxtOriginal(txt);

        // PHARSER de las lineas del texto
        try {
            while ((linea = reader.readLine()) != null) {

            	// Enter
            	if ( verify )
                	out.append(enter);

                // Traducir Linea
                if (linea.length() != 0) {
                    if (linea.matches("[0-9,.:\\-> ]*"))	// Lineas numericas indice de subtitles
                        get = linea;
                    else{
                           get = Traductor.goTraducirTextoLite(linea, idiomaDetectado, false);
                           log("GET LITE: " + get);

                            if (get == null) {
                            	get = Traductor.goTraducirTextoLite(linea, "Auto_detect", false);
                                log("GET LITE Auto: " + get);

                                // AutoSwitch Engine en la Linea
	                            if (get == null && !Config.isEngineSwitch()) {
	                            	log("AutoSwitch Engine en la Linea");
	                            	Config.setEngineSwitch(true);
	                                switch (verifyChangeLanguageAvailableInGoogleMicrosft()) {
	                                case 1:
	                                	idiomaDetectado = "Auto_detect";
	                                	break;
	                                case 2:
	                                case 3:
	                                	Tray.setBallonMessage("No available Language in Alternative Engine with "+ Config.getEngineName(), MessageType.ERROR);
	                                	Config.setEngineSwitch(false);
	                                	return linea;
	                                }

                                    // REPLACE EOL&SRT Repair, of Microsoft to Google (line 272)
                                    if (Config.getEngine() == 0) {
                                        linea = Cadenas.replaceEOL_0D0A( linea );
                                        linea = Cadenas.replaceSRT_Original( linea );
                                    }

                                    get = Traductor.goTraducirTextoLite(linea, "Auto_detect", false);

                                    // Restore AutoSwitch Engine
                                    Config.setEngineSwitch(false);

	                            }//end if autoswitch


                               // Dejar Linea Original
                               if (get == null)
                            	   get = linea;
                            }//end null
                    }

                    log("LITE is NUMERIC: " + linea.matches("[0-9,.:\\--> ]*"));
                    log("LITE ORIGINAL  ** " + linea);
                    log("LITE TRANSLATE <> " + get);

                    out.append( get );
                    verify = true;	//Saltos de linea

                    // Mostrar en Consola la Traducción
                    System.out.println(get);
                }//end if

            }//end while

        } catch(IOException e) {
          e.printStackTrace();
        }

        // El texto contenido en el portapapeles, pudiendo ser enviado traducido al portapapeles
        if (enviarTraduccionAlClipboard)
            Portapapeles.setClipboard(out.toString());

        setTxtTraducido( out.toString() );
        return out.toString();
    }



    /**
     * VERIFY IF LANGUAGE CHANGE IS AVAILABLE
     *
     * @return Available Idioma (0 Ok, 1 Auto, 2 null)
     */
    private static byte verifyChangeLanguageAvailableInGoogleMicrosft() {

        String idiomaOrigen = Config.getIdiomaDual();
        String idiomaDestino = Config.getIdiomaPreferido();

        String idiomaDualGoogle = google.Languages.isValidLanguage( idiomaOrigen );
        String idiomaDualMicrosoft = microsoft.Languages.isValidLanguage( idiomaOrigen );

        String idiomaPreferidoGoogle = google.Languages.isValidLanguage( idiomaDestino );
        String idiomaPreferidoMicrosoft = microsoft.Languages.isValidLanguage( idiomaDestino );

        byte err = 0;

        if (!idiomaDualGoogle.equals( idiomaDualMicrosoft ))
            err = 1;

        if (!idiomaPreferidoGoogle.equals( idiomaPreferidoMicrosoft ))
            err += 2;

        log("Available Idioma: " + err + " -- (0 Ok, 1 Auto, 2 null)");
        return err;
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



    /**
     *  Contenido del TXT con palabras o esta null
     *
     * @param txt
     * @return boolean
     */
    public static boolean isContent(String txt) {
    	if (txt != null && txt.length() != 0)
    		return true;
    	else
    		return false;
    }



    /**
     *  TRADUCIR y VISUALIZAR en en Balloon/Window el contenido que haya en el Clipboard
     */
    public static void goTraducirVisualizar() {
        Tray.setIconChange(true);
        // Traducimos el contenido del Portapapeles, no cambiamos el contenido del portapapeles y si lo mostramos en el SystemTray o en un Frame la traduccion
        Message.setBalloonWindowMessage(
                Traductor.goTraducirPortapapelesAvanzado(false)
                );
        Tray.setIconChange(false);
    }



    /**
     *  WINDOW insert text and view in Balloon/Window, además ser enviado al Clipboard
     */
    public static void goTraducirMessage() {
        Tray.setIconChange(true);

        Message win = new Message();
        win.getWindowInputDialogAndTranslation();

        Tray.setIconChange(false);
    }



    /**
     *  TRADUCIR el texto que haya en el Clipboard y Mostrarlo
     */
    public static void goTraducirMostrar() {
        Tray.setIconChange(true);

        Message.setBalloonWindowMessage(
                Traductor.goTraducirPortapapelesAvanzado(true));

        Tray.setIconChange(false);
    }



    /**
     *  COPY & PASTE con la Traducción del texto seleccionado, ademas de ser enviado al Clipboard
     */
    public static void goTraducirCopyPaste() {
        Tray.setIconChange(true);

        Portapapeles.setClipboard("");

        KeysRobot.pulsarCopy();
        goTraducirPortapapelesAvanzado(true);
        KeysRobot.pulsarPaste();

        Tray.setIconChange(false);
    }



    /**
     *  COPY & MOSTAR in Balloon/Window la Traducción del texto seleccionado, además de ser enviado al Clipboard
     */
    public static void goTraducirCopyMostrar() {
    Tray.setIconChange(true);

    Portapapeles.setClipboard("");

    KeysRobot.pulsarCopy();
    Message.setBalloonWindowMessage(
            Traductor.goTraducirPortapapelesAvanzado(true));

    Tray.setIconChange(false);
    }



    /**
     *  PASTE de la Traducción del contenido que haya en el Clipboard
     */
    public static void goTraducirPaste() {
        Tray.setIconChange(true);

        Traductor.goTraducirPortapapelesAvanzado(true);
        KeysRobot.pulsarPaste();

        Tray.setIconChange(false);
   }



    /**
     *  SCREEN CAPTURE > OCR TESSERACT > TRANSLATION
     */
    public static void goTraducirScreen(String txt) {
        if (Config.isKeyScreen()) {
            setTxtTraducido( txt );
            Portapapeles.setClipboard( txt );
            Message.setBalloonWindowMessage( txt );
        }
        else
            Message.setBalloonWindowMessage(
                    Traductor.goTraducirTextoAvanzado(txt, true));
   }



}
