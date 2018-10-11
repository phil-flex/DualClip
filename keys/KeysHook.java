package keys;
/*
 * JIntellitype ----------------- Copyright 2005-2006 Emil A. Lefkof III
 *
 * I always give it my best shot to make a program useful and solid, but remeber
 * that there is absolutely no warranty for using this program as stated in the
 * following terms:
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 *
 *
 * JIntellitype A Java Implementation for using the Windows API Intellitype
 * commands and the RegisterHotKey and UnRegisterHotkey API calls for globally
 * responding to key events. Intellitype are commands that are using for Play,
 * Stop, Next on Media keyboards or some laptops that have those special keys.
 * <p>
 * JIntellitype class that is used to call Windows API calls using the
 * JIntellitype.dll.
 * <p>
 * This file comes with native code in JINTELLITYPE.DLL The DLL should go in
 * C:/WINDOWS/SYSTEM or in your current directory
 * <p>
 * <p>
 * Copyright (c) 1999-2008 Melloware, Inc. <http://www.melloware.com>
 * @author Emil A. Lefkof III <info@melloware.com>
 * @version 1.3.1
 */

import execute.Traductor;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.swing.JFrame;

import jxgrabkey.HotkeyConflictException;
import jxgrabkey.JXGrabKey;
import ocr.Screen;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import clip.Config;
import clip.Main;

import com.melloware.jintellitype.HotkeyListener;
import com.melloware.jintellitype.JIntellitype;


/**
 * Global Keys para acceso rapido a funciones de traducción
 * <p>
 * Copyright (c) 2011 DualClip Translator, Inc. http://sourceforge.net/projects/dcliptranslator/
 * @author Cesar Rodriguez Gonzalez <Stelars@users.sourceforge.net>
 */
public class KeysHook extends JFrame implements HotkeyListener, jxgrabkey.HotkeyListener{


	private static final long serialVersionUID = -7859592088509265619L;
	private static final Log LOG = LogFactory.getLog( KeysHook.class );
	private static final boolean os = Config.isSystemaOperativo();


	/**
	 * Global Keys para acceso rápido a funciones de traducción
	 */
	public KeysHook() {
	   Init();
	   try {
		   RegisterHotKey();
			} catch (HotkeyConflictException e) {
				LOG.error("*Error Register por Conflicto de HotKeys" + e);
				e.printStackTrace();
			}
   }


	/**
	 * Global Keys para acceso rápido a funciones de traducción
	 */
	public KeysHook(boolean ok) {
	   Init();
	   try {
		   ReactiveRegisterHotKey();
			} catch (HotkeyConflictException e) {
				LOG.error("*Error Reactive por Conflicto de HotKeys" + e);
				e.printStackTrace();
			}
   }



	@Override
	public void onHotkey(int arg0) {
		keyActions(arg0);
	}

	public void onHotKey(int aIdentifier) {
		keyActions(aIdentifier);
	}


   /**
    * Eventos a las Acciones del Teclado Registradas
    * @param key
    */
   private void keyActions(int key){
	      log("HOTKEY recivido: " + Integer.toString(key));

		   // Verify Priority
		   if (Thread.currentThread().getPriority() < Config.getPriority()) {
			   if (Config.isInfo) log("** ERROR Prioridad: " + Thread.currentThread().getPriority());
			   Config.setPriority(Config.getPriority());
			   if (Config.isInfo) log("** CAMBIO Prioridad: " + Thread.currentThread().getPriority());
			   }

	      switch (key) {
	      	// Z	Translate Text of Clipboard, and View Result
	      	case 1:
	      		Traductor.goTraducirMostrar();
	      		break;

		    // X	CHANGE (COPY & PASTE) con la Traducción del texto seleccionado
	    	case 2:
	      		Traductor.goTraducirCopyPaste();
	   			break;

	    	// C	COPY y ver in Balloon/Window la Traducción del texto seleccionado
	    	case 3:
	      		Traductor.goTraducirCopyMostrar();
	      		break;

			// V	Paste the clipboard content translated
	      	case 4:
	      		Traductor.goTraducirPaste();
	      		break;

	      	// W	Write text to translate and View
	    	case 5:
	    		Traductor.goTraducirMessage();
	    		break;

	    	// S 	Screen Capture Rectangle > OCR > Translation
	      	case 6:
     			new Screen().captureRectangle();
	      		break;
			}
   }



   /**
    * Method To Register A Hotkey Using The RegisterHotKey Windows API Call.
    *
    * @param aEvent the ActionEvent fired.
    * @throws IOException
    * @throws HotkeyConflictException
    */
   public void RegisterHotKey() throws HotkeyConflictException{

	      // Detectar sistema operativo para Registrar los Hotkeys con la Libreria correspondiente al sistema
	      if (os) {
	    	  loadLibrary();
	    	  JXGrabKey.setDebugOutput(false);
	    	  JXGrabKey.getInstance().registerAwtHotkey(1, Config.getKey("_Z_Mod"), Config.getKey("_Z"));
	    	  JXGrabKey.getInstance().registerAwtHotkey(2, Config.getKey("_X_Mod"), Config.getKey("_X"));
	    	  JXGrabKey.getInstance().registerAwtHotkey(3, Config.getKey("_C_Mod"), Config.getKey("_C"));
	    	  JXGrabKey.getInstance().registerAwtHotkey(4, Config.getKey("_V_Mod"), Config.getKey("_V"));
	    	  JXGrabKey.getInstance().registerAwtHotkey(5, Config.getKey("_W_Mod"), Config.getKey("_W"));
	    	  JXGrabKey.getInstance().registerAwtHotkey(6, Config.getKey("_S_Mod"), Config.getKey("_S"));
	  	      }
	      else {   		    	  
	    	  JIntellitype.getInstance().registerHotKey(1, swingToIntelliType(Config.getKey("_Z_Mod")), Config.getKey("_Z"));
	    	  JIntellitype.getInstance().registerHotKey(2, swingToIntelliType(Config.getKey("_X_Mod")), Config.getKey("_X"));
	    	  JIntellitype.getInstance().registerHotKey(3, swingToIntelliType(Config.getKey("_C_Mod")), Config.getKey("_C"));
	    	  JIntellitype.getInstance().registerHotKey(4, swingToIntelliType(Config.getKey("_V_Mod")), Config.getKey("_V"));
	    	  JIntellitype.getInstance().registerHotKey(5, swingToIntelliType(Config.getKey("_W_Mod")), Config.getKey("_W"));
	    	  JIntellitype.getInstance().registerHotKey(6, swingToIntelliType(Config.getKey("_S_Mod")), Config.getKey("_S"));
	      }
	      log("RegisterHotKey assigned");
	   }



   /**
    * JXGrabKey Loading Library
    */
   protected void loadLibrary(){
	   try {
		    //Architectura 32 o 64
		    if (Config.isArchitech64()) {
	    		System.load(new File(GetRuta() + "/libJXGrabKey64.so").getCanonicalPath());
	    		//System.load(new File(getClass().getResource("/resource/lib32/libJXGrabKey.so")).getCanonicalPath());
	    	} else {
	    		System.load(new File(GetRuta() + "/libJXGrabKey.so").getCanonicalPath());
	    		//System.load(new File(getClass().getResource("/resource/lib64/libJXGrabKey.so")).getCanonicalPath());
	    	}

	} catch (IOException e) {
		LOG.error("*ERROR* Loading JXGrabKey");
		e.printStackTrace();
	}
   }



   /**
    * Swing modifier value to Jintellipad conversion. If no conversion needed
    * just return the original value. This lets users pass either the original
    * JIntellitype constants or Swing InputEvent constants.
    * <p>
    * @param swingKeystrokeModifier the Swing KeystrokeModifier to check
    * @return Jintellitype the JIntellitype modifier value
    */
   protected static int swingToIntelliType(int swingKeystrokeModifier)
	  {
	  int mask = 0;
	  if ((swingKeystrokeModifier & 0x1) == 1) {
		  mask += 4;
	  }
	  if ((swingKeystrokeModifier & 0x8) == 8) {
		  ++mask;
	  }
	  if ((swingKeystrokeModifier & 0x2) == 2) {
		  mask += 2;
	  }
	  if ((swingKeystrokeModifier & 0x40) == 64) {
		  mask += 4;
	  }
	  if ((swingKeystrokeModifier & 0x200) == 512) {
		  ++mask;
	  }
	  if ((swingKeystrokeModifier & 0x80) == 128) {
		  mask += 2;
	  }
	  return mask;
	  }



   /**
    * Method To Reactive Register A Hotkey Using The RegisterHotKey Windows API Call.
    *
    * @param aEvent the ActionEvent fired.
    * @throws IOException
    * @throws HotkeyConflictException
    */
   public static void ReactiveRegisterHotKey() throws HotkeyConflictException{

	      // Detectar sistema operativo para Registrar los Hotkeys con la Libreria correspondiente al sistema
	      if (os) {
	    	  JXGrabKey.getInstance().registerAwtHotkey(1, Config.getKey("_Z_Mod"), Config.getKey("_Z"));
	    	  JXGrabKey.getInstance().registerAwtHotkey(2, Config.getKey("_X_Mod"), Config.getKey("_X"));
	    	  JXGrabKey.getInstance().registerAwtHotkey(3, Config.getKey("_C_Mod"), Config.getKey("_C"));
	    	  JXGrabKey.getInstance().registerAwtHotkey(4, Config.getKey("_V_Mod"), Config.getKey("_V"));
	    	  JXGrabKey.getInstance().registerAwtHotkey(5, Config.getKey("_W_Mod"), Config.getKey("_W"));
	    	  JXGrabKey.getInstance().registerAwtHotkey(6, Config.getKey("_S_Mod"), Config.getKey("_S"));
	  	      }
	      else {
	    	  JIntellitype.getInstance().registerHotKey(1, swingToIntelliType(Config.getKey("_Z_Mod")), Config.getKey("_Z"));
	    	  JIntellitype.getInstance().registerHotKey(2, swingToIntelliType(Config.getKey("_X_Mod")), Config.getKey("_X"));
	    	  JIntellitype.getInstance().registerHotKey(3, swingToIntelliType(Config.getKey("_C_Mod")), Config.getKey("_C"));
	    	  JIntellitype.getInstance().registerHotKey(4, swingToIntelliType(Config.getKey("_V_Mod")), Config.getKey("_V"));
	    	  JIntellitype.getInstance().registerHotKey(5, swingToIntelliType(Config.getKey("_W_Mod")), Config.getKey("_W"));
	    	  JIntellitype.getInstance().registerHotKey(6, swingToIntelliType(Config.getKey("_S_Mod")), Config.getKey("_S"));
	      }
	      log("Reactive RegisterHotKey assigned");
	   }



   /**
    * Method to unregister a hotkey using the UnregisterHotKey Windows API call.
    *
    * @param aEvent the ActionEvent fired.
    */
   public static void RemoveGlobalHotKeys(){
	   try{
		   if (os){
		    	  JXGrabKey.getInstance().unregisterHotKey(1);
		    	  JXGrabKey.getInstance().unregisterHotKey(2);
		    	  JXGrabKey.getInstance().unregisterHotKey(3);
		    	  JXGrabKey.getInstance().unregisterHotKey(4);
		    	  JXGrabKey.getInstance().unregisterHotKey(5);
		    	  JXGrabKey.getInstance().unregisterHotKey(6);
		   }
		   else
			   JIntellitype.getInstance().cleanUp();
	   }
	    catch (Exception e) {
	    	LOG.error("*ERROR* en Renove de HotKeys " + e);
	    	e.printStackTrace();
	   }
	    log("unRegisterHotKey EXIT");
	   }



   /**
    * Method to unregister a hotkey using the UnregisterHotKey Windows API call.
    *
    * @param aEvent the ActionEvent fired.
    */
   public static void DisableGlobalHotKeys(){
	   try{
		   if (os){
		    	  JXGrabKey.getInstance().unregisterHotKey(1);
		    	  JXGrabKey.getInstance().unregisterHotKey(2);
		    	  JXGrabKey.getInstance().unregisterHotKey(3);
		    	  JXGrabKey.getInstance().unregisterHotKey(4);
		    	  JXGrabKey.getInstance().unregisterHotKey(5);
		    	  JXGrabKey.getInstance().unregisterHotKey(6);
		   }
		   else	  {
				  JIntellitype.getInstance().unregisterHotKey(1);
				  JIntellitype.getInstance().unregisterHotKey(2);
				  JIntellitype.getInstance().unregisterHotKey(3);
				  JIntellitype.getInstance().unregisterHotKey(4);
				  JIntellitype.getInstance().unregisterHotKey(5);
				  JIntellitype.getInstance().unregisterHotKey(6);
		   }

	   }
	    catch (Exception e) {
	    	LOG.error("*ERROR* en Renove de HotKeys " + e);
	    	e.printStackTrace();
	   }
	    log("unRegisterDisableHotKey");
	   }



   /**
    * Initialize The JInitellitype Library Making Sure The DLL Is Located.
    */
   public void Init() {
	   try {
		   //initialize JIntellitype with the frame so All Windows Commands Can Be Attached to this window
		   //JIntellitype.getInstance().addIntellitypeListener(this);
		   log("JIntellitype/JXGrabKey initialized");
		    //set the parent additivity to false

		   if (os)
			   JXGrabKey.getInstance().addHotkeyListener(this);
		   else {
			   
			   	try {
		    		  if (Config.isArchitech64()) {	    			  
		    			  JIntellitype.setLibraryLocation(GetRuta() + "/JIntellitype64.dll");	    			  
		    		  }				
					} catch (Exception e) {
						LOG.error("*Error Library Location: " + e);
						e.printStackTrace();
					}
			   
			   	JIntellitype.getInstance().addHotKeyListener(this);
		   		}

	   } catch (RuntimeException ex) {
         LOG.error("A problem with the library!\n" +
         		"Maybe an instance of this application is already running\n" + ex);
         System.exit(1);
      }
   }



   /**
    * Send the output to the LOG
    *
    * @param text of output log
    */
    private static void log(String text) {
    	if (Config.isInfo) {
    		LOG.info(text + "\n");
    		}
    }




   /**
    *  Detectar ruta para la libreria libJXGrabKey.so
    * @return String
    */
   private String GetRuta() {
		try {
		    File jarFile = null;
			jarFile = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI());
			   String path = jarFile.getParent();
			   return path;

		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

	   return "";
   }




}