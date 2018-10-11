package keys;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;


/**
 * Auto Pulsación de Teclas (Ctrl+C) (Ctrl+V)
 * <p>
 * Copyright (c) 2011 DualClip Translator, Inc. http://sourceforge.net/projects/dcliptranslator/
 * @author Cesar Rodriguez Gonzalez <Stelars@users.sourceforge.net>
 */
public class KeysRobot {


	/**
	 *  PASTE Pulsación de Ctrl+V para ser traducido
	 */
	public static void pulsarPaste(){
        Robot robot = null;

        try {
        	robot = new Robot();
	        // RELEASE
			robot.keyRelease(KeyEvent.VK_SPACE);
			robot.keyRelease(KeyEvent.VK_WINDOWS);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_ALT);
	        robot.keyRelease(KeyEvent.VK_SHIFT);

	        // PRESSED
	        robot.keyPress(KeyEvent.VK_CONTROL);
	        robot.keyPress(KeyEvent.VK_V);
	        Thread.sleep(320);
	        robot.keyRelease(KeyEvent.VK_V);
	        robot.keyRelease(KeyEvent.VK_CONTROL);

		} catch (AWTException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}



	/**
	 *  COPY Pulsación de Ctrl+C para copiar el texto seccionado
	 *  que es recojido/enviado al portapapeles
	 */
	public static void pulsarCopy(){
        Robot robot = null;

        try {
			robot = new Robot();
	        // RELEASE
			robot.keyRelease(KeyEvent.VK_SPACE);
			robot.keyRelease(KeyEvent.VK_WINDOWS);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_ALT);
	        robot.keyRelease(KeyEvent.VK_SHIFT);

	        // PRESSED
	        robot.keyPress(KeyEvent.VK_CONTROL);
	        robot.keyPress(KeyEvent.VK_C);
	        Thread.sleep(320);
	        robot.keyRelease(KeyEvent.VK_C);
	        robot.keyRelease(KeyEvent.VK_CONTROL);

		} catch (AWTException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}

