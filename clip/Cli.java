package clip;


import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;

import execute.Engine;
import execute.Traductor;



/**
 * Command Line for Traducir desde consola
 * <p>
 * Copyright (c) 2011 DualClip Translator, Inc. http://sourceforge.net/projects/dcliptranslator/
 * @author Cesar Rodriguez Gonzalez <Stelars@users.sourceforge.net>
 */
public class Cli {

	private static final String TITLE =
        "DualClip Translator 2.4  Copyright 2011 Cesar Rodriguez Gonzalez  2017-01-29\n";
	private static final String USAGE =
		" ";
	private static final String WEB =
		"For new version: http://dcliptranslator.sourceforge.net/";


/**
 * Command Line for Traducir desde consola
 * @param args
 */
    public Cli(String args[]) {

            String language = "";

            // Create Parser Options
            CommandLineParser parser = new GnuParser();
            Options options = new Options();
            options.addOption("h", "help", 			false, "Print this usage information");
            options.addOption("a", "about", 		false, "About info");
            options.addOption("v", "viewlaguages", 	false, "View laguages support\n");
            options.addOption("e", "examples", 		false, "more examples usages");
            options.addOption("c", "clipboard", 	false, "Clipboard Translation");
            options.addOption("m", "microsoft", 	false, "powered by Microsoft");
            options.addOption("g", "google", 		false, "powered by Google");

            // Language Parser
            OptionBuilder.hasArg(true);
			OptionBuilder.withArgName("language");
			OptionBuilder.withLongOpt("language");
			OptionBuilder.withDescription("Select language to Translate");
			options.addOption(OptionBuilder.create('l'));

			// Text Parser
            OptionBuilder.hasArg(true);
			OptionBuilder.withArgName("text");
			OptionBuilder.withLongOpt("text");
			OptionBuilder.withDescription("Text to be translated");
			options.addOption( OptionBuilder.create('t') );

			// File Parser
            OptionBuilder.hasArg(true);
			OptionBuilder.withArgName("file");
			OptionBuilder.withLongOpt("file");
			OptionBuilder.withDescription("TXT file to be translated");
			options.addOption( OptionBuilder.create('f') );


            // Parse de los arguments
            try {
                CommandLine commandLine = parser.parse( options, args );

                // HELP
                if( commandLine.hasOption('h') ) {
                    printUsage( options );
                    System.exit(0);
                }


                // ABOUT
                if( commandLine.hasOption('a') ) {
                	Archivo file = new Archivo();
                    System.out.println(file.loadToString("/resource/about.txt"));
                    System.exit(0);
                }


                // ENGINE
                if( commandLine.hasOption('g') ) {
                	Config.setEngine(0);// GOOGLE
                }
                else
                if( commandLine.hasOption('m') ) {
                	Config.setEngine(1);// MICROSOFT
                	}


                // VIEW LANGUAGES SUPPORT
                if( commandLine.hasOption('v') ) {
                    Engine.listValidLanguages();
                    System.exit(0);
                }


                // EXAMPLES
                if( commandLine.hasOption('e') ) {
                	examples();
                    System.exit(0);
                }


                // LANGUAGE
                if( commandLine.hasOption('l') ) {
                    language = commandLine.getOptionValue('l');
                    // Capitalize first letter for recognizer of language list
                    language = String.format( "%s%s",
                    		Character.toUpperCase(language.charAt(0)),
                    		language.substring(1) );


                    if (!Engine.isValidLanguage(language).equals(language)){
                    	System.out.println("Unsupported language, [-v] displays the list of available lenguages.");
                    	System.exit(0);
                    }

                    Config.setIdiomaDual("English");
                    Config.setIdiomaPreferido(language);
                }


                // TEXT
                if( commandLine.hasOption('t') ) {
                    String text = commandLine.getOptionValue('t');
               		Traductor.goTraducirTextoAvanzado( text, true);
                }


                // FILE
                else
            	if( commandLine.hasOption('f') ) {
                    String file = commandLine.getOptionValue('f');
                	Archivo traducirArchivo = new Archivo();
                	traducirArchivo.txtTranslateCmd(file);
                }


                // CLIPBOARD
                else
                if( commandLine.hasOption('c') ) {
                	Traductor.goTraducirPortapapelesAvanzado(true);
                	}
                else
                	{
                	printUsage( options );
					System.exit(0);
                	}

              // ... do important stuff ...
            } catch( Exception e ) {
              System.out.println( "Invalid arguments! View more -e examples" + e );
              printUsage( options );

            } finally {
                System.exit(1);
            }

    }



    /**
     * PRINT HELP
     * @param options
     */
    private static void printUsage(Options options) {
    	HelpFormatter helpFormatter = new HelpFormatter( );
    	helpFormatter.setWidth( 80 );
    	helpFormatter.printHelp( USAGE, TITLE, options, WEB, false);
    	System.out.println(
    			"\nExample Specify Language:\n" +
    			"\tDualClip Translator.jar -l \"Chinese_simplified\" -t hola\n" +
                "\tResult (Chinese):\t\t 你好\n\n" +

                "Example Translating contents of the clipboard to Preferred Language:\n" +
    			"\tDualClip Translator.jar -c\n" +
    			"\tClipboard Content (Spanish):\t Protectores de la paz y la justicia.\n" +
    			"\tResult (English):\t\t Protectors of peace and justice."
    	);
    }



    /**
     * PRINT EXAMPLES
     */
    private void examples(){
    	System.out.println(
                "Example with language options Dual/Preferred seleccionados:\n" +
                "\tDualClip Translator.jar -t \"Der Vogel ist in meinem Haus\"\n" +
                "\tResult (Spanish):\t\t El pajaro esta en mi casa\n\n" +

                "Example Translating clipboard:\n" +
                "\tDualClip Translator.jar -c -l english\n" +
                "\tClipboard Content (Japanese):\t 遠い未来の惑星\n" +
                "\tResult (English):\t\t a planet in the distant future\n\n" +

                "Example Translating contents of the clipboard to Preferred Language:\n" +
                "\tDualClip Translator.jar -c\n" +
                "\tClipboard Content (Spanish):\t El universo contiene muchas maravillas\n" +
                "\tResult (Russian):\t\t Вселенная содержит много чудес\n\n" +

                "Example Translating of TXT file to Spanish language:\n" +
                "\tDualClip Translator.jar -lspanish -t \"archive.txt\"\n"
        );
    }

}
