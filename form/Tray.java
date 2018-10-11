/*
 * Copyright 2008 Sun Microsystems, Inc., 4150 Network Circle,
 * Santa Clara, California 95054, U.S.A. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */

package form;

import execute.Traductor;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;


import keys.KeysHook;
import clip.Archivo;
import clip.Config;


/**
 * Based on a blog post from Alexander Potochkin at the following url:
 * http://weblogs.java.net/blog/alexfromsun/archive/2008/02/jtrayicon_updat.html
 *
 * @author Alexander Potochkin
 * @author Stephen Chin
 * @author Keith Combs
 * @author Carlos
 * @author Cesar Rodriguez Gonzalez
 */
public class Tray extends TrayIcon {
    private JPopupMenu menu;
    private static final JDialog DIALOG = new JDialog((Frame) null, "DualClip Translator");
    static {
    	DIALOG.setUndecorated(true);
    	DIALOG.setAlwaysOnTop(true);
    }

    private static PopupMenuListener popupListener = new PopupMenuListener() {
        public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
        }

        public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
        	DIALOG.setVisible(false);
        }

        public void popupMenuCanceled(PopupMenuEvent e) {
        	DIALOG.setVisible(false);
        }
    };


    /*
     * MOUSE
     */
    public Tray(Image image) {
        super(image);
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                showJPopupMenu(e);
            }

            public void mouseReleased(MouseEvent e) {
            	if (e.getButton() == 1)
            		Traductor.goTraducirVisualizar();
            	else
            		showJPopupMenu(e);
            }
        });
    }

    private void showJPopupMenu(MouseEvent e) {
        if (e.isPopupTrigger() && menu != null) {
            Dimension size = menu.getPreferredSize();
            int adjustedY = e.getY() - size.height;

            DIALOG.setLocation(e.getX()-5, adjustedY < 0 ? e.getY() : adjustedY);
            DIALOG.setVisible(true);
            menu.show(DIALOG.getContentPane(), 0, 0);
            DIALOG.setVisible(true);

            // FOCUSED (this only work good in Windows, click need in Linux)
            DIALOG.toFront();
            DIALOG.addWindowFocusListener(new WindowFocusListener() {
                public void windowLostFocus(WindowEvent e) {
                	DIALOG.setVisible(false);
                }
                public void windowGainedFocus(WindowEvent e) {
                }
            });
        }
    }

    public JPopupMenu getJPopuMenu() {
        return menu;
    }

    public void setJPopuMenu(JPopupMenu menu) {
        if (this.menu != null) {
            this.menu.removePopupMenuListener(popupListener);
        }
        this.menu = menu;
        menu.addPopupMenuListener(popupListener);
    }

    public static void create() {
        Tray tray = new Tray( getIcono() );

        // AUTO DIMENSIONES del tamaño del Icono en el SystemTray
        int iconoDimensiones = (int)SystemTray.getSystemTray().getTrayIconSize().getHeight();
        tray.setImage(tray.getImage().getScaledInstance(iconoDimensiones, iconoDimensiones, 48));
        tray.setImageAutoSize(false);

        tray.setJPopuMenu(createJPopupMenu());

        try {
            SystemTray.getSystemTray().add(tray);
        } catch (AWTException e) {
            e.printStackTrace();
        }

    }



    /**
     * ICONO
     * Se asigna la imagen que veremos en el tray icon
     * @return Image
     */
    static Image getIcono() {
    	String desk;

    	switch (Config.getDesktop()) {
		case 1:
			desk = "gnome";
			break;

		case 3:
			desk = "kde";
			break;

		default:
			desk = "windows";
			break;
		}

    	Image icono = Toolkit.getDefaultToolkit().getImage(
    			Tray.class.getResource( "/resource/"+ desk +"/universalAzul.png" ));
        return icono;
    }



    /**
     * MENUS
     * @return
     */
    static JPopupMenu createJPopupMenu() {
        final JPopupMenu m = new JPopupMenu();

        /*
         * FILE
         */
        JMenuItem file = new JMenuItem("Open");
        file.setIcon(new ImageIcon(Tray.class.getResource("/resource/file.png")));
        // FILE listener open txt file
        ActionListener fileListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                javax.swing.SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                    	archive();
                    }
                });
            }
        };
        file.addActionListener(fileListener);
        m.add(file);

        /*
         * SAVE
         */
        JMenuItem save = new JMenuItem("Save");
        save.setIcon(new ImageIcon(Tray.class.getResource("/resource/save.png")));
        // SAVE listener open txt file
        ActionListener saveListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                javax.swing.SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                    	Archivo.saveTxtOCR("",
                    			Traductor.getTxtTraducido()
                    			);
                    }
                });
            }
        };
        save.addActionListener(saveListener);
        m.add(save);

        /*
         * OPTIONS
         */
        JMenuItem opciones = new JMenuItem("Options");
        opciones.setIcon(new ImageIcon(Tray.class.getResource("/resource/opciones.png")));
        // OPTIONS listener donde se cambia las opciones del programa
        ActionListener opcionesListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                javax.swing.SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                    	options();
                    }
                });
            }
        };
        opciones.addActionListener(opcionesListener);
        m.add(opciones);

        /*
         * INFO SUBMENU
         */
        JMenu submenu = new JMenu("Info");
        submenu.setIcon(new ImageIcon(Tray.class.getResource("/resource/info.png")));

        /*
         * HELP
         */
        JMenuItem help = new JMenuItem("Help");
        help.setIcon(new ImageIcon(Tray.class.getResource("/resource/help.png")));
        // HELP listener
        ActionListener helpListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                javax.swing.SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                    	help();
                    }
                });
            }
        };
        help.addActionListener(helpListener);
        submenu.add(help);

        /*
         * ABOUT
         */
        JMenuItem about = new JMenuItem("About");
        about.setIcon(new ImageIcon(Tray.class.getResource("/resource/universal.png")));
        // ABOUT listener donde ver sobre el autor e history
        ActionListener aboutListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                javax.swing.SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                    	about();
                    }
                });
            }
        };
        about.addActionListener(aboutListener);
        submenu.add(about);

        m.add(submenu);

        /*
         * EXIT
         */
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.setIcon(new ImageIcon(Tray.class.getResource("/resource/exit.png")));
        // EXIT listener para salir del programa
        ActionListener exitListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                javax.swing.SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                    	exit();
                    }
                });
            }
        };
        exitItem.addActionListener(exitListener);
        m.add(exitItem);

        return m;
    }
  //--------------------------------------------------------------------------




    /**
     *  TRAY BALLON MESSAGE
     */
    public static void setBallonMessage(String mensaje, MessageType is){

    	// Instanciar Tray del Icono Actual
    	SystemTray tray = SystemTray.getSystemTray();
		TrayIcon[] trayIcon = tray.getTrayIcons();

	  	// Mostramos mensaje en nuestro TrayIcon
        trayIcon[0].displayMessage("DualClip powered by " + Config.getEngineName(),
            mensaje,
            is);
    }



    /**
     *  TRAY BALLON MESSAGE & Title custom
     */
    public static void setBallonMessage(String title, String mensaje, MessageType is){

    	// Instanciar Tray del Icono Actual
    	SystemTray tray = SystemTray.getSystemTray();
		TrayIcon[] trayIcon = tray.getTrayIcons();

	  	// Mostramos mensaje en nuestro TrayIcon
        trayIcon[0].displayMessage(title, mensaje, is);
    }



    /**
     *  TRAY ICON CHANGE
     */
    public static void setIconChange(boolean isChange){

    	// Instanciar Tray del Icono Actual
    	SystemTray tray = SystemTray.getSystemTray();
		TrayIcon[] trayIcon = tray.getTrayIcons();
		Image ico;
		String desk;

		int iconoDimensiones = (int)SystemTray.getSystemTray().getTrayIconSize().getHeight();

    	switch (Config.getDesktop()) {
		case 1:
			desk = "gnome";
			break;

		case 3:
			desk = "kde";
			break;

		default:
			desk = "windows";
			break;
		}

		// IMAGEN del Icono
		if (isChange){
			ico = Toolkit.getDefaultToolkit().getImage(
					Tray.class.getResource("/resource/"+ desk +"/universalVerde.png")).getScaledInstance(iconoDimensiones, iconoDimensiones, 48);
		}
		else{
			ico = Toolkit.getDefaultToolkit().getImage(
					Tray.class.getResource("/resource/"+ desk +"/universalAzul.png")).getScaledInstance(iconoDimensiones, iconoDimensiones, 48);
		}
		// VER Icono
		trayIcon[0].setImage(ico);

    }



    /**
     *  TRAY CALL ARCHIVE DESKTOP CHOOSER > TRANSLATE > SAVE
     */
    private static void archive(){
    	Tray.setIconChange(true);

    	Archivo traducirArchivo = new Archivo();
    	traducirArchivo.txtTranslateDesktop();

    	Tray.setIconChange(false);
    }



    /**
     *  OPTIONS FORM
     */
    private static void options(){
    	KeysHook.DisableGlobalHotKeys();

    	Options optionsForm = new Options();
        optionsForm.setVisible(true);
    }


    /**
     *  HELP FORM
     */
    private static void help(){
    	Help helpForm = new Help();
        helpForm.setVisible(true);
    }


    /**
     *  ABOUT FORM
     */
    private static void about(){
    	About aboutForm = new About();
        aboutForm.setVisible(true);
        aboutForm.requestFocus();
    }


    /**
     *  EXIT
     */
    private static void exit() {
    	KeysHook.RemoveGlobalHotKeys();
    	System.exit(0);
    }

}
