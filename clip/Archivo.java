package clip;

import java.awt.Desktop;
import java.awt.TrayIcon.MessageType;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import execute.Traductor;
import form.Tray;


/**
 * Manejo de Archivos, Lectura y Escritura
 * <p>
 * Copyright (c) 2011 DualClip Translator, Inc. http://sourceforge.net/projects/dcliptranslator/
 * @author Cesar Rodriguez Gonzalez <Stelars@users.sourceforge.net>
 */
public class Archivo {

	private static String directory;

	//Reader in = new InputStreamReader(new FileInputStream("file"), "UTF-8"));

    /**
     *  LOAD a LIST format of text archive file
     *
     * @param archive String Rute
     * @return List Content
     */
    public List<String> LoadToList(String archive){
          File archivo = null;
          FileReader fr = null;
          //BufferedReader br = null;
          List<String> list = new ArrayList<String>();

          try {
             // Apertura del fichero y creacion de BufferedReader para poder
             // hacer una lectura comoda (disponer del metodo readLine()).
             archivo = new File (archive);
             fr = new FileReader (archivo);
             //br = new BufferedReader(fr);

             BufferedReader br = new BufferedReader(
                     new InputStreamReader(
                         new FileInputStream(archive)
                     )
                    );

             // Lectura del fichero
             String linea;
             while((linea=br.readLine())!=null) {
                // Grabación en ArrayList para lecturas en memoria
                list.add(linea);
                }

          }
          catch(Exception e){
             e.printStackTrace();
          }finally{
             // En el finally cerramos el fichero, para asegurarnos
             // que se cierra tanto si todo va bien como si salta
             // una excepcion.
             try{
                if( null != fr ){
                   fr.close();
                }
             }catch (Exception e){
                e.printStackTrace();
             }
          }

          return list;
    }



    /**
     * LOAD a STRING format of text archive file
     *
     * @param archivo String Rute
     * @return String Content
     */
    public String loadToString(String archivo) {

    	final String EOL = System.getProperty("line.separator");

        //File file = new File (archivo);
        StringBuilder contents = new StringBuilder();
        try
        {
        BufferedReader input = new BufferedReader(
                new InputStreamReader(
                        this.getClass().getResourceAsStream(archivo)));
           try
           {
             String line = null;
             while ((line = input.readLine()) != null) {
                contents.append(line);
                   contents.append(EOL);
                 }
           }
           finally {
             input.close();
          }
         }
         catch (IOException e) {
           e.printStackTrace();
         }

         return contents.toString();
       }



    /**
     * LOAD FILE ENC
     * @param filename
     * @param enc
     * @return
     * @throws IOException
     * @throws UnsupportedEncodingException
     */
    public static String loadfile(String filename, String enc)
    throws IOException, UnsupportedEncodingException {

        StringBuilder mTextArea = new StringBuilder();
        final String EOL = System.getProperty("line.separator");
        String buffer;
        InputStream in;

        in = new FileInputStream(filename);

        // Set up character stream
        BufferedReader r = new BufferedReader(new InputStreamReader(in, enc));
        while ((buffer = r.readLine()) != null) {
            mTextArea.append(buffer + EOL);
        }

        r.close();


        //**********************
//        final File fFile;
//
//        fFile = new File(filename);
//
//        //Note that FileReader is used, not File, since File is not Closeable
//        Scanner scanner = new Scanner(new FileReader(fFile));
//        try {
//          //first use a Scanner to get each line
//          while ( scanner.hasNextLine() ){
//        	  buffer = scanner.nextLine();
//        	  System.out.print( buffer );
//        	  mTextArea.append( buffer + EOL);
//          }
//        }
//        finally {
//          //ensure the underlying stream is always closed
//          //this only has any effect if the item passed to the Scanner
//          //constructor implements Closeable (which it does in this case).
//          scanner.close();
//        }


        //*********************
        return mTextArea.toString();
        }



    /**
     * SAVE FILE with ENCODE
     *
     * @param archive
     * @param text
     */
    public static void savefile(String archive, String text, String enc, boolean append){
        try {
            Writer writer = new OutputStreamWriter(new FileOutputStream(archive, append), enc);
            writer.write(text);
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    /**
     * SAVE file de ARRAY cadena
     *
     * @param archive String Rute
     * @param array String[]
     * @param incremental Boolean
     */
/*    public static void Save(String archive, String[] array, boolean incremental){
        try{
             BufferedWriter bw = new BufferedWriter(new FileWriter(archive,incremental));

             // Escribimos el contenido del Array
             for (int i=0; i<array.length; i++)
               bw.write(array[i] + "\n");

             // Hay que cerrar el fichero
             bw.close();
          } catch (IOException e){
             e.printStackTrace();
          }
    //	}
    }
*/


    /**
     * SAVE file de STRING cadena
     *
     * @param archive String Rute
     * @param cadena String
     * @param incremental Boolean
     */
/*    public static void Save(String archive, String cadena, boolean incremental){
        // Validamos si existe el fichero
    //	File fichero = new File(archive);

    //	if (fichero.exists())
    //	  System.out.println("El fichero " + archive + " ya existe");
    //	else {
          try{
             BufferedWriter bw = new BufferedWriter(new FileWriter(archive,incremental));

             // Escribimos el contenido de la cadena
               bw.write(cadena);

             // Hay que cerrar el fichero
             bw.close();
          } catch (IOException e){
             e.printStackTrace();
          }

    }
*/


    /**
     *	OPEN DESKTOP FILE
     *
     * @param archive
     */
    public static void openDesktop(String archive){
        try {
              Desktop desktop = null;
              if (Desktop.isDesktopSupported()) {
                desktop = Desktop.getDesktop();
              }

               desktop.open(new File( archive ));
            } catch (IOException ioe) {
              ioe.printStackTrace();
            }
    }



    /**
     *  ARCHIVE COMMAND LINE CONSOLE > TRANSLATE > SAVE
     */
    public void txtTranslateCmd(String file){

        File fichero = new File(file);
        if (fichero.exists()){

            // GET RUTE FILE
            String archivo = fichero.getAbsoluteFile().toString();

            String out = "";

            // LOAD FILE ENCODING
            try {
                out =loadfile(archivo, "UTF-8");
            } catch (Exception loadexc) {
                System.out.println(loadexc);
            }

            // TRANSLATE TEXT
            String traduccion = Traductor.goTraducirTextoAvanzado(out, false);

            // EXTENSION REMOVE of File
            String fileSaveName = fichero.getAbsoluteFile().toString();

            int dotIndex=fileSaveName.lastIndexOf('.');
            if(dotIndex>=0) { // to prevent exception if there is no dot
                 fileSaveName=fileSaveName.substring(0,dotIndex);
            }

            // SAVE
            fileSaveName += getPoweredByTranslator();
            Archivo.savefile(fileSaveName, traduccion, "UTF-8", false);
            System.out.println("Realized Translation");
        }

    }



    /**
     *  ARCHIVE DESKTOP CHOOSER > TRANSLATE > SAVE
     */
    public void txtTranslateDesktop(){

        JFileChooser chooserSelected = getFileChooser();

        if (chooserSelected != null){
            // GET RUTE FILE
            String archivo = chooserSelected.getSelectedFile().getAbsoluteFile().toString();
            String out = "";

            // LOAD FILE ENCODING
            try {
                out =loadfile(archivo, "UTF-8");
            } catch (Exception e) {
                e.printStackTrace();
            }

            // TRANSLATE TEXT
            String traduccion = Traductor.goTraducirTextoAvanzado(out, false);

            // EXTENSION REMOVE of File
             String fileSaveName = chooserSelected.getSelectedFile().toString();

             int dotIndex=fileSaveName.lastIndexOf('.');
             if(dotIndex>=0) { // to prevent exception if there is no dot
                 fileSaveName=fileSaveName.substring(0,dotIndex);
             }

             fileSaveName += getPoweredByTranslator();
            Archivo.savefile(fileSaveName, traduccion, "UTF-8", false);

            Tray.setBallonMessage("Realized Translation", MessageType.INFO);

            // FILE OPEN TRANSLATED
            if (Config.isFileOpenTranslated())
                openDesktop(fileSaveName);
        }

    }



    /**
     *	ARCHIVE GET FILE CHOOSER
     */
    public JFileChooser getFileChooser(){
        JFileChooser chooser = new JFileChooser(".");
        chooser.setCurrentDirectory(directory == null ? null : new File(directory));

        // FILTERS
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
        		"TXT (texto),  SRT (subtitles)",
        		"TXT", "SRT");

        chooser.setFileFilter(filter);
        chooser.setFocusable(true);

        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
        	directory = chooser.getCurrentDirectory().getPath();
            try {
                return chooser;

                } catch (Exception ex) {
                  ex.printStackTrace();
                }
        }

        return null;
    }



    /**
     * 	ARCHIVE SAVE FILE CHOOSER
     *
     * @return File String
     */
    public static String saveFileChooser(){
        // Create a file chooser
        JFileChooser chooser = new JFileChooser(".");
        chooser.setCurrentDirectory(directory == null ? null : new File(directory));

        // Obtain the selected file.
        chooser.setSelectedFile (null);
//        chooser.setApproveButtonText("Guardar");
        chooser.setDialogTitle("Guardar como...");
//        chooser.setApproveButtonMnemonic(KeyEvent.VK_G);

        // Save dialog
        if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
        	directory = chooser.getCurrentDirectory().getPath();
            try {
                return chooser.getSelectedFile().getPath();

                } catch (Exception ex) {
                  ex.printStackTrace();
                }
        }

        return null;
    }

    public static void saveTxtOCR(String txtOriginal, String txtTranslate) {

        if (txtOriginal == null || txtTranslate == null) {
            Message.viewError("NO CONTENT TO SAVE", "To be translated before, so that it can record");
            return;
        }


        Tray.setIconChange(true);
//		int choice = 0;
//
//	    if (Config.isInBalloonOrMessage()) {
//		    choice =  JOptionPane.
//		    showConfirmDialog (null,
//		                       "Guardar el Resultado del OCR/Traducción?",
//		                       "SAVE",
//		                       JOptionPane.
//		                       YES_NO_OPTION);
//	    }//end if Balloon
//
//
//	    if (choice != JOptionPane.YES_OPTION) return;


        String nameFile = Archivo.saveFileChooser();

        if (nameFile != null) {
            if (!nameFile.contains( "." )){
                nameFile += ".txt";
            }

            if (txtOriginal.length() != 0)
                Archivo.savefile(nameFile, txtOriginal, "UTF-8", false);

            nameFile = nameFile.replace(".txt", getPoweredByTranslator());


            if (txtTranslate.length() != 0)
                Archivo.savefile(nameFile, txtTranslate, "UTF-8", false);

        }


        Tray.setIconChange(false);
    }


    private static String getPoweredByTranslator(){
    	return "_[powered by " + Config.getEngineName() + "].txt";
    }


}
