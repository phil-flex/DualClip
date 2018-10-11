package form;

import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.dyno.visual.swing.layouts.Bilateral;
import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;
import org.dyno.visual.swing.layouts.Trailing;

import clip.Config;


//VS4E -- DO NOT REMOVE THIS LINE!
/**
 * HELP Steps Detailed Help Functions
 * <p>
 * Copyright (c) 2011 DualClip Translator, Inc. http://sourceforge.net/projects/dcliptranslator/
 * @author Cesar Rodriguez Gonzalez <Stelars@users.sourceforge.net>
 */
public class Help extends JFrame {

	private static final long serialVersionUID = 1L;
	private JLabel jLabel0;
	private JTree jTree0;
	private JScrollPane jScrollPane2;
	private JButton jButton0;
	private JLabel jLabel1;
	private String help;
	private JLabel jLabel2;
	private JLabel jLabel3;
	private Font font;
	private Idiomas lang;

	public Help() {
		fuente();
		language();
		initComponents();
		centerScreen();
		icono();
	}

	private void initComponents() {
		setLayout(new GroupLayout());
		add(getJLabel0(), new Constraints(new Bilateral(128, 128, 522), new Leading(32, 12, 12)));
		add(getJLabel1(), new Constraints(new Leading(653, 10, 10), new Leading(12, 12, 12)));
		add(getJLabel3(), new Constraints(new Trailing(12, 12, 12), new Leading(550, 10, 10)));
		add(getJLabel2(), new Constraints(new Leading(695, 10, 10), new Leading(551, 10, 10)));
		add(getJButton0(), new Constraints(new Leading(277, 253, 10, 10), new Leading(551, 10, 10)));
		add(getJScrollPane2(), new Constraints(new Bilateral(11, 12, 22), new Leading(71, 461, 12, 12)));
		setSize(813, 657);
	}

	private JLabel getJLabel3() {
		if (jLabel3 == null) {
			jLabel3 = new JLabel();
			jLabel3.setToolTipText(
					lang.getProperty("Pulse_for_Change_Language_Help") );
			jLabel3.setIcon(new ImageIcon(getClass().getResource("/resource/language/flag_en.png")));
			jLabel3.addMouseListener(new MouseAdapter() {

				public void mouseReleased(MouseEvent event) {
					jLabel3MouseMouseClicked(event);
				}
			});
		}
		return jLabel3;
	}

	private JLabel getJLabel2() {
		if (jLabel2 == null) {
			jLabel2 = new JLabel();
			jLabel2.setToolTipText(
					lang.getProperty("Pulse_for_Change_Language_Help") );
			jLabel2.setIcon(new ImageIcon(getClass().getResource("/resource/language/flag_es.png")));
			jLabel2.addMouseListener(new MouseAdapter() {

				public void mouseReleased(MouseEvent event) {
					jLabel2MouseMouseClicked(event);
				}
			});
		}
		return jLabel2;
	}

	private JLabel getJLabel1() {
		if (jLabel1 == null) {
			jLabel1 = new JLabel();
			jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/helpballoon.png")));
		}
		return jLabel1;
	}

	private JButton getJButton0() {
		if (jButton0 == null) {
			jButton0 = new JButton();
			jButton0.setFont( font.deriveFont(Font.PLAIN, 16) );
			jButton0.setIcon(new ImageIcon(getClass().getResource("/resource/ayuda.png")));
			jButton0.setText(
					lang.getProperty("Guide_Online") );
			jButton0.setToolTipText(
					lang.getProperty("ScreenShots") );
			jButton0.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent event) {
					jButton0ActionActionPerformed(event);
				}
			});
		}
		return jButton0;
	}

	private JScrollPane getJScrollPane2() {
		if (jScrollPane2 == null) {
			jScrollPane2 = new JScrollPane();
			jScrollPane2.setViewportView(getJTree0());
		}
		return jScrollPane2;
	}

	private JLabel getJLabel0() {
		if (jLabel0 == null) {
			jLabel0 = new JLabel();
			jLabel0.setFont( font.deriveFont(Font.PLAIN, 25) );
			jLabel0.setText(
					lang.getProperty("Title") );
		}
		return jLabel0;
	}

	private JTree getJTree0() {
		if (jTree0 == null) {
			jTree0 = new JTree();
			jTree0.setFont( font.deriveFont(Font.PLAIN, 14) );

			DefaultTreeModel treeModel = null;

			/* Description & Functions Steps */
			{
				DefaultMutableTreeNode node0 = new DefaultMutableTreeNode(
						lang.getProperty("Description0"));

				/* DESCRIPTION */
				getTreeNode("Description", 1, lang, node0);

				/* Requirements */
				getTreeNode("Requirements", 0, lang, node0);

				/* Detect to Preferred & Dual Languages */
				getTreeNode("Detect", 0, lang, node0);

				/* Engine Select Google/Microsoft */
				getTreeNode("Engine", 0, lang, node0);

				/* Detect to Preferred & Dual Languages */
				getTreeNode("Priority", 0, lang, node0);

				// SPACE Node
				node0.add(new DefaultMutableTreeNode(""));


				/* MOUSE CLICK */
				getTreeNode("KeyM", 0, lang, node0);

				/* Z	CLIP */
				getTreeNode("KeyZ", 0, lang, node0);

				/* X	CHANGE */
				getTreeNode("KeyX", 0, lang, node0);

				/* C	COPY */
				getTreeNode("KeyC", 0, lang, node0);

				/* V	PASTE */
				getTreeNode("KeyV", 0, lang, node0);

				/* W	WRITE */
				getTreeNode("KeyW", 0, lang, node0);

				/* S	SCREEN CAPTURE & OCR */
				getTreeNode("KeyS", 0, lang, node0);

				// SPACE Node
				node0.add(new DefaultMutableTreeNode(""));


				/* TXT Open File for Translation */
				getTreeNode("TxtOpen", 0, lang, node0);

				/* TXT Save File of Original/Translation */
				getTreeNode("TxtSave", 0, lang, node0);

				/* IN WINDOW */
				getTreeNode("InWindow", 0, lang, node0);

				/* Command-Line Console */
				getTreeNode("Console", 0, lang, node0);


				treeModel = new DefaultTreeModel(node0);
			}
			jTree0.setModel(treeModel);
		}
		return jTree0;
	}



    /**
     * CENTER SCREEN WINDOWS FRAME/DIALOG
     */
    private void centerScreen(){
		// Get the size of the screen
	    java.awt.Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
	    // Determine the new location of the window and Move the window
        setLocation((dimension.width - getWidth()) / 2, (dimension.height - getHeight()) / 2);
    }



	/**
	 * ICONO del Frame/Dialog
	 */
	private void icono(){
		setIconImage( new ImageIcon( getClass().getResource("/resource/help.png") ).getImage() );
	}


	/**
	 * LANGUAGE
	 */
	private void language() {
		/* HELP Properties Languages */
		// FIXME BUG, es necesario descomentar "es", para Editar el Diseño
		//lang = new Idiomas("es");
		lang = new Idiomas( Config.getUserLanguage() );
	}


	/**
	 * FUENTE del Frame
	 */
	private void fuente(){

		String nameFont;

		if (Config.isSystemaOperativo())
			nameFont = "FreeSans";
		else
			nameFont = "Dialog";

		font = new Font(nameFont, Font.PLAIN, 1);

		/* TODO Reserve for Future maybe Change */
//		 // Load font from InputStream
//		InputStream is = this.getClass().getResourceAsStream("/resource/.ttf");
//
//		try {
//			font = Font.createFont(Font.TRUETYPE_FONT, is);
//
//		} catch (FontFormatException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		// Used
//	    // Font ttfReal = ttfBase.deriveFont(Font.PLAIN, 24);
	}


	private void jButton0ActionActionPerformed(ActionEvent event) {
		this.setVisible(false);
        this.dispose();
        try {
            URL url = new URL("http://dcliptranslator.sourceforge.net/?guide=1");
            Desktop.getDesktop().browse(url.toURI());
          } catch (Exception ex) {
            Logger.getLogger( Help.class.getName() ).log(Level.SEVERE, null, ex);
          }
	}


	/**
	 * GET TREE NODE PROPERTIES LANGUAGES for HELP
	 *
	 * @param key
	 * @param lang
	 * @param node0
	 */
	private void getTreeNode(String key, int initial, Idiomas lang, DefaultMutableTreeNode node0){
		DefaultMutableTreeNode node1 = new DefaultMutableTreeNode(
				lang.getProperty( key + initial ));

				for (int i = initial+1; i < 25; i++) {
					help = lang.getProperty( key + i );
					if (help == null) break;

					DefaultMutableTreeNode node2 = new DefaultMutableTreeNode( help );
					node1.add(node2);
				}

				node0.add(node1);
	}


	private void jLabel2MouseMouseClicked(MouseEvent event) {
		if (!Config.getUserLanguage().equals("es")){
			this.setVisible(false);
	        this.dispose();

			// Muestra la ayuda con el idioma pulsado a SPANISH
			Config.setUserLanguage("es");
	    	Help helpForm = new Help();
	        helpForm.setVisible(true);
		}
	}

	private void jLabel3MouseMouseClicked(MouseEvent event) {
		if (!Config.getUserLanguage().equals("en")){
			this.setVisible(false);
	        this.dispose();

			// Muestra la ayuda con el idioma pulsado a SPANISH
			Config.setUserLanguage("en");
	    	Help helpForm = new Help();
	        helpForm.setVisible(true);
		}
	}


}
