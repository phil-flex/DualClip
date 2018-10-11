package ocr;

import form.Tray;
import execute.Traductor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import clip.Config;
import clip.Message;

/**
 * OCR Recognize
 * <p>
 * Copyright (c) 2011 DualClip Translator, Inc. http://sourceforge.net/projects/dcliptranslator/
 * @author Cesar Rodriguez Gonzalez <Stelars@users.sourceforge.net>
 */
public class Ocr {

	private static final Log LOG = LogFactory.getLog( Ocr.class );

    public Ocr(File imageFile) {
        try {
            if (!imageFile.exists()) {
            	Message.viewError("Screen Capture", "Error in Create File Capture");
                return;
            }

            String ocrIdioma = Config.getOcrIdiomaValue();

            if (Config.isSystemaOperativo())
                imageFile = convertImageCmd(imageFile, "tif");

            String result = recognizeText(imageFile, ocrIdioma);

            // FIXED OCR
            result = replaceOCRErrors(result);
			log("* RESULTADO OCR: " + result);


			// LOG IMAGE out verify & String COUNT characters null
			if (Config.isInfo) {
				int con = countOCRSymbols(result);
				result += con +"% de Sysmbols";
				//Message.setBalloonWindowMessage(result);
			}


//			log(	"* FILTER REPLACE SYMBOLS *\n" +
//        			replaceOCRSymbols(result) +
//        			"------------------------------\n");


            // TRANSLATE
			Traductor.goTraducirScreen( result );

			// EXCEPTIONS
	        } catch (Exception e) {
	        	if (e.getMessage().equals("convert")) {
	        		Message.viewError("Need install imagemagick:", "sudo apt-get install imagemagick");
	        	}

	            LOG.error("Error: " + e.getMessage());
	        } finally {
	        	Tray.setIconChange(false);
	            // borrar imagen despues del reconocimiento OCR
	            if (imageFile != null) {
	            	imageFile.delete();
	            }
	        }

	    }



	/**
     *	OCR Runtime Recongnize
     *
     * @author Quan Nguyen
     * @param imageFile jpg, png, tiff
     * @param lang eng default
     * @return String texto recognizer
     * @throws Exception
     */
    public String recognizeText(File imageFile, final String lang) throws Exception {
        final String EOL = "\n";

        String tessPath = null;
        if (Config.isSystemaOperativo()){
        	tessPath = "/usr/bin";
        }
        else{
        	File dir = new File(System.getProperty("user.dir"));
        	tessPath = new File(dir, "/tesseract").getPath();
        }

        File tempTessOutputFile = File.createTempFile("TesseractTemp_", ".txt");
        String outputFileName = tempTessOutputFile.getPath().substring(0, tempTessOutputFile.getPath().length() - 4);

        ProcessBuilder pb = new ProcessBuilder();
        pb.directory(new File(System.getProperty("user.home")));
        pb.redirectErrorStream(true);

        StringBuilder result = new StringBuilder();

        List<String> cmd = new ArrayList<String>();
        cmd.add( tessPath + "/tesseract" );
        cmd.add( imageFile.getPath() );
        cmd.add( outputFileName );
        cmd.add( "-l" );
        cmd.add( lang );

        pb.command( cmd );
        Process process = pb.start();
        MyStreamGobbler outputGobbler = new MyStreamGobbler(process.getInputStream());
        outputGobbler.start();

        int w = process.waitFor();

        if (w == 0) {
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(tempTessOutputFile), "UTF-8"));

            String str;

            while ((str = in.readLine()) != null) {
                result.append(str).append(EOL);
            }

            int length = result.length();
            if (length >= EOL.length()) {
                result.setLength(length - EOL.length()); // remove last EOL
            }
            in.close();
        } else {
            System.out.println("Exit value = " + w);
            tempTessOutputFile.delete();
            String msg = outputGobbler.getMessage(); // get actual message from the engine;
            if (msg.trim().length() == 0) {
                msg = "Errors occurred.";
            }
            throw new RuntimeException(msg);
        }


        tempTessOutputFile.delete();
        return result.toString();
    }



    /**
     *	Convert Runtime
     *
     * @param File input imageFile
     * @param format jpg, png, tif
     * @return File convert of format specify, (File input is delete)
     * @throws Exception
     */
    public File convertImageCmd(File imageFile, String format) throws Exception {

    	File convertOutputFile = File.createTempFile("screentiff_", "." + format);
    	String dpi = Integer.toString( Config.getOcrDpiValue());
    	convertOutputFile.delete();

    	ProcessBuilder pb = new ProcessBuilder();

        List<String> cmd = new ArrayList<String>();
        cmd.add( "convert" );
        cmd.add( imageFile.getPath() );
        cmd.add( "-density" );
        cmd.add( dpi );
        cmd.add( convertOutputFile.getPath() );

        pb.command( cmd );
        Process process = pb.start();
        MyStreamGobbler outputGobbler = new MyStreamGobbler(process.getInputStream());
        outputGobbler.start();

        int w = process.waitFor();
        if (w != 0)
        	System.out.println("Exit value = " + w);

        imageFile.delete();
        return convertOutputFile;
    }



    /**
     * reaplace words & letters error recognized of Tessetact
     *
     * @param input Texto of OCR
     * @return String
     */
    public String replaceOCRErrors(String input) {

    	char[] caracter = {'a', 'e', 'i', 'o', 'u'};
    	for (byte i = 0; i < 5; i++) {
    		// al
    		input = input.replaceAll(
    				Character.valueOf(caracter[i]) + "1",
    				Character.valueOf(caracter[i]) + "l"
    				);

    		// la
    		input = input.replaceAll(
    				"1" + Character.valueOf(caracter[i]),
    				"l" + Character.valueOf(caracter[i])
    				);
		}

    	return input
                .replaceAll("nl",		"ni")
                .replaceAll(" In",		"in")
                .replaceAll(" Ie",		" le")
                .replaceAll("e5",		"es")
                .replaceAll("y5",		"ys")
                .replaceAll("a nd",		"and")
                .replaceAll("aln",		"ain")
                .replaceAll("a11",		"all")
                .replaceAll("A11",		"All")
                .replaceAll("lrs",		"irs")
                .replaceAll("Io",		"Lo")
                .replaceAll("lng",		"ing")
                .replaceAll("ls",		"Is")
                .replaceAll("f7le",		"file")
                .replaceAll("dlo", 		"dio")
                .replaceAll("icfon",		"icon")
                .replaceAll("ifon",			"icon")
                .replaceAll("i§on",			"icon")
                .replaceAll("you re",		"you'e")
                .replaceAll("tliat",		"that")
                .replaceAll("gra nts",		"grants")
                .replaceAll("atthese",		"at these")
                .replaceAll("your-first",	"your first")
                .replaceAll("attheseulocations", "at these locations")
                ;
    }




//    private String replaceOCRSymbols(String result) {
//    	int x = 0;
//    	StringBuffer cad = new StringBuffer();
//    	StringBuffer cadFilter = new StringBuffer();
//    	cad.append(result);
//
//    	for (String str: cad.toString().split(" ")) {
//    		// Cuenta/Busqueda de los que no sean caracteres de este regex
//			if ( !str.matches("([^0-9a-zA-Záéíóú+-=:;. /()@\n\"\'?¿])"))
//				cadFilter.append(str).append(" ");
//    	}
//
//    	// CONTADOR DE CARACTERES ESTRAñOS
//    	for (int i = 0; i < result.length(); i++) {
//    		// Cuenta/Busqueda de los que no sean caracteres de este regex
//			if ( cad.substring(i, i+1).matches("([^0-9a-zA-Záéíóú+-:;. /()@\n\"\'?¿])")){
//				//System.out.println("** "+ cad.substring(i, i+1));
//				x++;
//			}
//		}
//
//    	log("** CARACTERES NO TRANSLATION:" + x );
//    	return cad.toString();
//	}




    /**
     *  CONTADOR DE CARACTERES ESTRAñOS
     */
    private int countOCRSymbols(String result) {
    	int cantidad = 0;
    	StringBuffer cad = new StringBuffer();
    	cad.append(result);

    	for (int i = 0; i < result.length(); i++) {
    		// Cuenta/Busqueda de los que no sean caracteres de este regex
    		//if ( cad.substring(i, i+1).matches("([^0-9a-zA-Záéíóú+-:;. /()@\n\"\'?¿])")){
    		if ( cad.substring(i, i+1).matches("([^0-9a-zA-ZáéíóúòÁÉÍÓÚÒñãêÁãõçÑÊÃÊÁÃÕ+-:;.?])")){
    			// System.out.println("** "+ cad.substring(i, i+1));
    			cantidad++;
    		}
    	}

    	log("CARACTERS NO TRANSLATION: " + cantidad );
    	log("Encontrado un: " + (cantidad * 100)/ result.length() + "%");

    	return (cantidad * 100)/ result.length();
    }




    /**
     * Send the output to the LOG
     *
     * @param text of output log
     */
     private void log(String text) {
     	if (Config.isInfo) {
     		LOG.info(text + "\n");
     		}
     }


}//end Class






/**
 * When Runtime.exec() won't.
 * <p>
 * http://www.javaworld.com/javaworld/jw-12-2000/jw-1229-traps.html
 * http://hermesbox.blogbus.com/logs/52759408.html
 * http://www.blogjava.net/kapok/archive/2005/09/08/12455.html
 */
class MyStreamGobbler extends Thread {

    InputStream is;
    StringBuilder outputMessage = new StringBuilder();

    MyStreamGobbler(InputStream is) {
        this.is = is;
    }

    String getMessage() {
        return outputMessage.toString();
    }

    @Override
    public void run() {
        try {
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                outputMessage.append(line).append("\n");
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
