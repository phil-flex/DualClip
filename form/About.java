package form;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import org.dyno.visual.swing.layouts.Bilateral;
import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;

import clip.Archivo;


//VS4E -- DO NOT REMOVE THIS LINE!
/**
 * ABOUT of Author & Program History
 * <p>
 * Copyright (c) 2011 DualClip Translator, Inc. http://sourceforge.net/projects/dcliptranslator/
 * @author Cesar Rodriguez Gonzalez <Stelars@users.sourceforge.net>
 */
public class About extends JDialog {

	private static final long serialVersionUID = 1435536616966992429L;
	private JLabel jLabelVersion;
	private JLabel jLabelLogo;
	private JLabel jLabel2;
	private JTextArea jTextArea0;
	private JScrollPane jScrollPane1;
	private JButton jButton1;
	public About() {
		initComponents();
		centerScreen();
		icono();
	}


	private void initComponents() {
		setFont(new Font("Dialog", Font.PLAIN, 12));
		setBackground(Color.lightGray);
		setForeground(Color.black);
		setLayout(new GroupLayout());
		add(getJLabel2(), new Constraints(new Bilateral(138, 138, 0), new Leading(85, 10, 10)));
		add(getJLabel0(), new Constraints(new Bilateral(180, 181, 203), new Leading(66, 40, 821)));
		add(getJLabel1(), new Constraints(new Bilateral(264, 263, 48), new Leading(12, 40, 949)));
		add(getJScrollPane1(), new Constraints(new Bilateral(12, 12, 22), new Bilateral(161, 12, 22)));
		add(getJButton1(), new Constraints(new Bilateral(236, 236, 124), new Leading(117, 40, 3029)));
		setSize(596, 503);
	}


	// BUTTON WEB PROJECT
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setIcon(new ImageIcon(getClass().getResource("/resource/project.png")));
			jButton1.setText("Web Project");
			jButton1.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent event) {
					jButton1ActionActionPerformed(event);
				}
			});
		}
		return jButton1;
	}


	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getJTextArea0());
			jScrollPane1.setSize(100, 100);
		}
		return jScrollPane1;
	}

	// TXT ABOUT
	private JTextArea getJTextArea0() {
		if (jTextArea0 == null) {
			jTextArea0 = new JTextArea();
			jTextArea0.setEditable(false);
			jTextArea0.setText(
				loadAbout("/resource/about.txt"));
			jTextArea0.setCaretPosition(0);
		}
		return jTextArea0;
	}

	// AUTHOR
	private JLabel getJLabel2() {
		if (jLabel2 == null) {
			jLabel2 = new JLabel();
	        jLabel2.setFont(new java.awt.Font("DejaVu Sans", 0, 11));
	        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
	        jLabel2.setText("<html><\u00A9 2011 Developed by César Rodriguez Gonzalez</html>");
	        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

		}
		return jLabel2;
	}

	// LOGO
	private JLabel getJLabel1() {
		if (jLabelLogo == null) {
			jLabelLogo = new JLabel();
			jLabelLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/windows/universalAzul.png")));
		}
		return jLabelLogo;
	}

	// TITLE
	private JLabel getJLabel0() {
		if (jLabelVersion == null) {
			jLabelVersion = new JLabel();
			jLabelVersion.setFont(new Font("DejaVu Sans", Font.BOLD, 14));
			jLabelVersion.setHorizontalAlignment(SwingConstants.CENTER);
			jLabelVersion.setText("DualClip Translator v2.4");
		}
		return jLabelVersion;
	}


	// BOTON ACTION URL
	private void jButton1ActionActionPerformed(ActionEvent event) {
		this.setVisible(false);
        this.dispose();
        try {
            URL url = new URL("http://dcliptranslator.sourceforge.net/");
            Desktop.getDesktop().browse(url.toURI());
          } catch (Exception ex) {
            Logger.getLogger( About.class.getName() ).log(Level.SEVERE, null, ex);
          }
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
	 * LOAD ABOUT FILE
	 */
	private String loadAbout(String archivo){
		Archivo file = new Archivo();
		return file.loadToString(archivo);
	}


	/**
	 * ICONO del Frame/Dialog
	 */
	private void icono(){
		setIconImage( new ImageIcon( getClass().getResource("/resource/universal.png") ).getImage() );
	}

}
