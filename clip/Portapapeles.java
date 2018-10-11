package clip;
/*
*
* Para recoger datos del Clipboard debemos llamar al método getContents(Object).
* El parámetro puede ser cualquier cosa y actualmente java no lo utiliza para nada.
* Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
* Transferable dato = cb.getContents(this);
* El problema ahora es interpretar ese dato Transferable que recibimos
* para ver si somos capaces de hacer algo con él. Para ello, podemos pedirle al
* Transferable dato los DataFlavor que tiene. Estos DataFlavor son las posibles
* interpretaciones para ese dato (si es un texto html, si es un texto xml,
* si es un texto plano, si es una imagen, etc, etc). De esta forma,
* analizando los DataFlavor obtenidos, sabremos mejor cómo podemos tratar el dato.
* En el caso de que queramos, por ejemplo, ver si lo que hay es un texto que podamos conseguir
* dentro de un String de java, podemos preguntar al dato si soporta un DataFlavor cuyo MimeType
* sea "application/x-java-serialized-object; class=java.lang.String".
*
*/

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.widgets.Display;


/**
 * Clipboard Content
 * <p>
 * Copyright (c) 2011 DualClip Translator, Inc. http://sourceforge.net/projects/dcliptranslator/
 * @author Cesar Rodriguez Gonzalez <Stelars@users.sourceforge.net>
 */
public class Portapapeles {

	/**
	 * OS GET CLIPBOARD
	 * <p>
	 *  Según el Systema Operativo, elige el metodo correcto de coger el contenido del portapapeles
	 */
	public String getClipboardOs(){
		if (Config.isSystemaOperativo())
			return getClipboardLinux();
		else
			return getClipboardWindows();
	}



	/**
	 *  GET del contenido en formato de texto del Portapapeles
	 *  <p>
	 *  ALERT - con ROBOT realizanco autoCopy Ctrl+C, el contenido no es recogido por el portapapeles desde Windows
  	 *  <p>
	 *  FIX - usar metodo con SWT getClipboardWindows() que si funciona en Windows
	 * @return String
	 */
	public String getClipboardLinux(){

		Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
		Transferable contenido = clip.getContents(this);

		try {
			// Construimos el DataFlavor correspondiente al String java
			DataFlavor dataFlavorStringJava;

			dataFlavorStringJava = new DataFlavor("application/x-java-serialized-object; class=java.lang.String");

			// Y si el dato se puede conseguir como String java, lo sacamos
			if (contenido.isDataFlavorSupported(dataFlavorStringJava)) {
			   String texto = (String) contenido.getTransferData(dataFlavorStringJava);
			   return texto;
			}

		} catch (ClassNotFoundException e){
			System.err.println(e);
			e.printStackTrace();
			} catch (UnsupportedFlavorException e) {
				System.err.println(e);
				e.printStackTrace();
				} catch (IOException e) {
					System.err.println(e);
					e.printStackTrace();
				}
	return "";
	}



	/**
	 *  GET del contenido en formato de texto del Portapapeles mediante SWT
	 *  <p>
	 *  Este metodo funciona perfectamente con la Clase Robot al hacer Ctrl+C
	 *  <p>
	 *  Only for Windows
	 * @return String
	 */
	public String getClipboardWindows(){

		Display display = new Display ();
		final org.eclipse.swt.dnd.Clipboard cb = new org.eclipse.swt.dnd.Clipboard(display);

		TextTransfer transfer = TextTransfer.getInstance();
		String data = (String)cb.getContents(transfer);

		display.dispose();
		if (data != null){
			return data;
		}

		return "";
	}



	/**
	 *  SET (static) hacia el Portapapeles, en formato de texto
	 *
	 * @param txt String
	 */
	public static void setClipboard(String txt) {
	    StringSelection string = new StringSelection(txt);
	    try {
	    	Toolkit.getDefaultToolkit().getSystemClipboard().setContents(string, null);
			Thread.sleep(600);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}



    /**
     * GET una imagen del Portapapeles
     *
     * @return image
     */
    public static Image getClipboardImage() {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        try {
            return (Image) clipboard.getData(DataFlavor.imageFlavor);
        } catch (Exception e) {
            return null;
        }
    }

}
