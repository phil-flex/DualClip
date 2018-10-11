package clip;

import keys.KeysHook;
import form.Tray;

public class Hilo extends Thread {

	private String []args;



	public Hilo(String []args) {
		this.args = args;
	}



	/**
	 * 	HILO PRINCIPAL
	 */
	public void run() {

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