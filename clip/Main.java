package clip;

import keys.KeysHook;
import form.Tray;


/*
 * DualClip Translator ------- Copyright 2011 Cesar Rodriguez Gonzalez
 *
 * Description:
 * (Spanish) Traducci�n del texto Seleccionado o contenido del Portapapeles, autom�ticamente "powered by Google/Microsoft".
 * Teclas HotKeys de acceso r�pido, Cut/Paste autom�tico para cambiar traducida frases del ingles u m�s idiomas.
 * Ver en globo systray � ventana el resultado de la traducci�n, ademas de ser enviado al portapapeles.
 * Captura de pantalla del Escritorio � Juego, para seguido ser reconocido por el OCR Tesseract
 * (Optical Character Recognition) y el resultado final es traducido "powered by Google/Microsoft".
 *
 * (English) Translation of selected text or clipboard contents automatically "powered by Google/Microsoft."
 * HotKeys for Cut/Paste to change auto translated from English words or more languages.
 * View Balloon systray or window the result of translation, besides being sent to the clipboard.
 * Screen Capture the Desktop/Game, to be recognized by the Tesseract OCR
 * (Optical Character Recognition) and the end result is translated "powered by Google/Microsoft."
 */

/*
 * FAQ: Por que uso casi todo con Metodos Static
 *
 * En cuanto a eficiencia, es m�s r�pido los m�todos est�ticos,
 * puesto que no hay que instanciar clases.
 * Posiblemente al arrancar la aplicaci�n sea un poco lenta,
 * ya que los static se inicializar�n en el arranque
 * o al principio, en los primeros pasos de ejecuci�n del programa.
 * Pero luego es mucho m�s fluido y r�pido.
 *
 *
 * FAQ: Por que hacer un Thread
 *
 * Ejecutarlo desde un Thread, hace posible el poder
 * cambiar la prioridad/velocidad a los procesos del programa.
 *
 */

/**
 * DualClip Translator which Google/Microsoft Translator + Clipboard + System Tray + CommandLine + Tesseract-OCR
 *
 * Copyright (c) 2011 DualClip Translator, Inc. http://sourceforge.net/projects/dcliptranslator/
 * @author Cesar Rodriguez Gonzalez <Stelars@users.sourceforge.net>
 * @version 2.4
 */
public class Main {
    public static void main(String[] args) throws Exception {

    	try {
			Hilo h = new Hilo(args);

			if (Config.getPriority() == Thread.MAX_PRIORITY)
				h.setPriority(Thread.MAX_PRIORITY);

			h.start();
		} catch (Exception e) {


			// Inicializar Preferencias
			  Config.initPreferences();


			  // Linea de Comandos
			  if ( args.length != 0 )
			      new Cli( args );


			  // Global Keys
			  new KeysHook();


			  // Iniciar Ventanas Decorativas
			  Config.initLookAndFeel();


			  // System Tray
			  Tray.create();

		}


    }
}
