package form;

import java.awt.Color;
import java.awt.Font;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

import jxgrabkey.HotkeyConflictException;
import keys.KeysHook;

import org.dyno.visual.swing.layouts.Bilateral;
import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;
import org.dyno.visual.swing.layouts.Trailing;

import clip.Config;
import clip.Message;

//VS4E -- DO NOT REMOVE THIS LINE!
/**
 * OPCIONES del lenguage Dual y Preferido, así como desea visualizar los resultados
 * <p>
 * Copyright (c) 2011 DualClip Translator, Inc. http://sourceforge.net/projects/dcliptranslator/
 * @author Cesar Rodriguez Gonzalez <Stelars@users.sourceforge.net>
 */
public class Options extends JFrame {

	private static final long serialVersionUID = 3554352049352979538L;
	private JComboBox jComboBoxIdiomaPreferred;
	private JComboBox jComboBoxIdiomaDual;
	private JLabel jLabel0;
	private JLabel jLabel1;
	private JButton jButton0;
	private JLabel jLabel2;
	private JPanel jPanel0;
	private JRadioButton jRadioButton0;
	private JRadioButton jRadioButton1;
	private JLabel jLabel3;
	private JPanel jPanel1;
	private JCheckBox jCheckBox0;
	private JPanel jPanel2;
	private JLabel jLabel5;
	private JTextField jTextField0;
	private JLabel jLabel6;
	private JLabel jLabel7;
	private JLabel jLabel4;
	private JLabel jLabel8;
	private JTextField jTextField1;
	private JTextField jTextField2;
	private JTextField jTextField3;
	private JTextField jTextField4;
	private JLabel jLabel9;
	private JTextField jTextField5;
	private JLabel jLabel10;
	private int keyCodePress = -2;
	private HashMap<String, Integer> tempKeys;
	private JLabel jLabel11;
	private JPanel jPanel3;
	private JComboBox jComboBoxOcrDpi;
	private JComboBox jComboBoxOcrIdioma;
	private JTextField jTextFieldWindowXWidth;
	private JTextField jTextFieldWindowYHeight;
	private JTextField jTextFieldWindowFontSize;
	private JLabel jLabel12;
	private JLabel jLabel13;
	private JLabel jLabel14;
	private Font font;
	private form.Idiomas lang;
	private JLabel jLabel15;
	private JRadioButton jRadioButtonGoogle;
	private JRadioButton jRadioButtonMicrosoft;
	private JPanel jPanel4;
	private int eventoEngine = 0;
	private JPanel jPanel5;
	private JLabel jLabel16;
	private JRadioButton jRadioButtonHighPriority;
	private JRadioButton jRadioButtonNormalPriority;


	public Options() {
		fuente();
		language();
		initComponents();
		setInBalloonOrMessage();
		setEngineGoogleorMicrosoft();
		setPrioritySelected();
		setComboBoxOcr();
		IdiomasComboBox();
		centerScreen();
		icono();
		teclas();
	}

	private void initComponents() {
		setLayout(new GroupLayout());
		add(getJLabel1(), new Constraints(new Leading(12, 116, 156, 156), new Leading(12, 50, 50)));
		add(getJComboBox1(), new Constraints(new Leading(12, 110, 10, 10), new Leading(39, 50, 50)));
		add(getJPanel2(), new Constraints(new Bilateral(12, 12, 425), new Leading(196, 208, 10, 10)));
		add(getJComboBox0(), new Constraints(new Leading(140, 120, 352, 363), new Leading(39, 12, 12)));
		add(getJPanel4(), new Constraints(new Trailing(12, 190, 285, 285), new Leading(12, 57, 12, 12)));
		add(getJButton0(), new Constraints(new Bilateral(241, 240, 62), new Leading(422, 12, 12)));
		add(getJLabel0(), new Constraints(new Leading(195, 207, 214), new Leading(12, 12, 12)));
		add(getJPanel0(), new Constraints(new Leading(12, 248, 352, 363), new Leading(87, 91, 10, 10)));
		add(getJPanel3(), new Constraints(new Leading(278, 131, 211, 214), new Leading(87, 90, 12, 12)));
		add(getJPanel1(), new Constraints(new Leading(427, 144, 10, 10), new Leading(87, 91, 12, 12)));
		add(getJPanel5(), new Constraints(new Bilateral(589, 12, 112), new Leading(87, 91, 12, 12)));
		addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent event) {
				windowWindowClosing(event);
			}
		});
		setSize(730, 500);
	}

	private JRadioButton getJRadioButton4() {
		if (jRadioButtonNormalPriority == null) {
			jRadioButtonNormalPriority = new JRadioButton();
			jRadioButtonNormalPriority.setText("Normal");
			jRadioButtonNormalPriority.setToolTipText(
					lang.getProperty("Normal_Priority") );
			jRadioButtonNormalPriority.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent event) {
					jRadioButtonNormalPriorityItemStateChanged(event);
				}
			});
		}
		return jRadioButtonNormalPriority;
	}

	private JRadioButton getJRadioButton5() {
		if (jRadioButtonHighPriority == null) {
			jRadioButtonHighPriority = new JRadioButton();
			jRadioButtonHighPriority.setText("High");
			jRadioButtonHighPriority.setToolTipText(
					lang.getProperty("High_Priority") );
			jRadioButtonHighPriority.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent event) {
					jRadioButtonHighPriorityItemStateChanged(event);
				}
			});
		}
		return jRadioButtonHighPriority;
	}

	private JLabel getJLabel16() {
		if (jLabel16 == null) {
			jLabel16 = new JLabel();
			jLabel16.setFont(new Font("Dialog", Font.PLAIN, 14));
			jLabel16.setText("Priority");
		}
		return jLabel16;
	}

	private JPanel getJPanel5() {
		if (jPanel5 == null) {
			jPanel5 = new JPanel();
			jPanel5.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
			jPanel5.setLayout(new GroupLayout());
			jPanel5.add(getJLabel16(), new Constraints(new Leading(0, 55, 10, 10), new Leading(0, 12, 12)));
			jPanel5.add(getJRadioButton5(), new Constraints(new Leading(12, 84, 12, 12), new Leading(25, 12, 12)));
			jPanel5.add(getJRadioButton4(), new Constraints(new Leading(12, 84, 12, 12), new Leading(55, 12, 12)));
		}
		return jPanel5;
	}

	private JPanel getJPanel4() {
		if (jPanel4 == null) {
			jPanel4 = new JPanel();
			jPanel4.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
			jPanel4.setLayout(new GroupLayout());
			jPanel4.add(getJLabel15(), new Constraints(new Leading(0, 56, 12, 12), new Leading(0, 12, 12)));
			jPanel4.add(getJRadioButton3(), new Constraints(new Trailing(12, 76, 76), new Leading(19, 12, 12)));
			jPanel4.add(getJRadioButton2(), new Constraints(new Leading(12, 103, 103), new Leading(19, 12, 12)));
		}
		return jPanel4;
	}

	private JRadioButton getJRadioButton3() {
		if (jRadioButtonMicrosoft == null) {
			jRadioButtonMicrosoft = new JRadioButton();
			jRadioButtonMicrosoft.setText("Microsoft");
			jRadioButtonMicrosoft.addItemListener(new ItemListener() {

				public void itemStateChanged(ItemEvent event) {
					jRadioButtonMicrosoftItemStateChanged(event);
				}
			});
		}
		return jRadioButtonMicrosoft;
	}

	private JRadioButton getJRadioButton2() {
		if (jRadioButtonGoogle == null) {
			jRadioButtonGoogle = new JRadioButton();
			jRadioButtonGoogle.setSelected(true); //Requerido para estabilidad del evento
			jRadioButtonGoogle.setText("Google");
			jRadioButtonGoogle.addItemListener(new ItemListener() {

				public void itemStateChanged(ItemEvent event) {
					jRadioButtonGoogleItemStateChanged(event);
				}
			});
		}
		return jRadioButtonGoogle;
	}

	private JLabel getJLabel15() {
		if (jLabel15 == null) {
			jLabel15 = new JLabel();
			jLabel15.setFont( font.deriveFont(Font.PLAIN, 14) );
			jLabel15.setText("Engine");
		}
		return jLabel15;
	}

	private JLabel getJLabel14() {
		if (jLabel14 == null) {
			jLabel14 = new JLabel();
			jLabel14.setForeground(Color.gray);
			jLabel14.setText("Font");
		}
		return jLabel14;
	}

	private JLabel getJLabel13() {
		if (jLabel13 == null) {
			jLabel13 = new JLabel();
			jLabel13.setForeground(Color.gray);
			jLabel13.setText("Y");
		}
		return jLabel13;
	}

	private JLabel getJLabel12() {
		if (jLabel12 == null) {
			jLabel12 = new JLabel();
			jLabel12.setForeground(Color.gray);
			jLabel12.setText("X");
		}
		return jLabel12;
	}

	private JTextField getJTextFieldWindowFontSize() {
		if (jTextFieldWindowFontSize == null) {
			jTextFieldWindowFontSize = new JTextField();
			jTextFieldWindowFontSize.setToolTipText("Font Size");
		}
		return jTextFieldWindowFontSize;
	}

	private JTextField getJTextFieldWindowXWidth() {
		if (jTextFieldWindowXWidth == null) {
			jTextFieldWindowXWidth = new JTextField();
			jTextFieldWindowXWidth.setToolTipText(
					lang.getProperty("Max_Width_Size") );
		}
		return jTextFieldWindowXWidth;
	}

	private JTextField getJTextFieldWindowYHeight() {
		if (jTextFieldWindowYHeight == null) {
			jTextFieldWindowYHeight = new JTextField();
			jTextFieldWindowYHeight.setToolTipText(
					lang.getProperty("Max_Height_Size") );
		}
		return jTextFieldWindowYHeight;
	}

	private JComboBox getJComboBox3() {
		if (jComboBoxOcrIdioma == null) {
			jComboBoxOcrIdioma = new JComboBox();
			jComboBoxOcrIdioma.setModel(new DefaultComboBoxModel(new Object[] { "Afrikaans", "Arabic", "Azerbaijani", "Belarusian", "Bengali", "Bulgarian", "Catalan", "Czech", "Chinese_Simplified", "Chinese_Traditional", "Cherokee", "Danish", "German", "Greek", "English", "Esperanto", "Esperanto", "Math_equation_detection", "Estonian", "Basque", "Finnish", "French", "Frankish", "Galician", "Ancient_Greek", "Hebrew", "Hindi", "Croatian", "Hungarian", "Indonesian", "Icelandic", "Italian", "Italian_Old", "Japanese", "Kannada", "Korean", "Latvian", "Lithuanian", "Malayalam", "Macedonian", "Maltese", "Malay", "Dutch", "Norwegian", "Polish", "Portuguese", "Romanian", "Russian", "Slovakian", "Slovenian", "Spanish", "Spanish_Old", "Albanian", "Serbian_Latin", "Swahili", "Swedish", "Tamil", "Telugu", "Tagalog", "Thai", "Turkish", "Ukrainian", "Vietnamese" }));
			jComboBoxOcrIdioma.setDoubleBuffered(false);
			jComboBoxOcrIdioma.setBorder(null);
		}
		return jComboBoxOcrIdioma;
	}

	private JComboBox getJComboBox2() {
		if (jComboBoxOcrDpi == null) {
			jComboBoxOcrDpi = new JComboBox();
			jComboBoxOcrDpi.setModel(new DefaultComboBoxModel(new Object[] { "300 Dpi", "400 Dpi", "600 Dpi" }));
			jComboBoxOcrDpi.setDoubleBuffered(false);
			jComboBoxOcrDpi.setBorder(null);
		}
		return jComboBoxOcrDpi;
	}

	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jPanel3 = new JPanel();
			jPanel3.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
			jPanel3.setLayout(new GroupLayout());
			jPanel3.add(getJLabel11(), new Constraints(new Leading(0, 56, 10, 10), new Leading(0, 12, 12)));
			jPanel3.add(getJComboBox3(), new Constraints(new Leading(12, 93, 10, 10), new Leading(56, 12, 12)));
			jPanel3.add(getJComboBox2(), new Constraints(new Leading(12, 92, 12, 12), new Leading(25, 12, 12)));
		}
		return jPanel3;
	}

	private JLabel getJLabel11() {
		if (jLabel11 == null) {
			jLabel11 = new JLabel();
			jLabel11.setFont( font.deriveFont(Font.PLAIN, 14) );
			jLabel11.setText("OCR");
		}
		return jLabel11;
	}

	private JTextField getJTextField5() {
		if (jTextField5 == null) {
			jTextField5 = new JTextField();
			jTextField5.setEditable(false);
			jTextField5.setName("X");
			jTextField5.setToolTipText(
					lang.getProperty("Pulse_for_Change_Hotkey") );
			jTextField5.addKeyListener(new MyKeyListener(jTextField5));
		}
		return jTextField5;
	}

	private JTextField getJTextField4() {
		if (jTextField4 == null) {
			jTextField4 = new JTextField();
			jTextField4.setEditable(false);
			jTextField4.setName("C");
			jTextField4.setToolTipText(
					lang.getProperty("Pulse_for_Change_Hotkey") );
			jTextField4.addKeyListener(new MyKeyListener(jTextField4));
		}
		return jTextField4;
	}

	private JTextField getJTextField3() {
		if (jTextField3 == null) {
			jTextField3 = new JTextField();
			jTextField3.setEditable(false);
			jTextField3.setName("V");
			jTextField3.setToolTipText(
					lang.getProperty("Pulse_for_Change_Hotkey") );
			jTextField3.addKeyListener(new MyKeyListener(jTextField3));
		}
		return jTextField3;
	}

	private JTextField getJTextField2() {
		if (jTextField2 == null) {
			jTextField2 = new JTextField();
			jTextField2.setEditable(false);
			jTextField2.setName("S");
			jTextField2.setToolTipText(
					lang.getProperty("Pulse_for_Change_Hotkey") );
			jTextField2.addKeyListener(new MyKeyListener(jTextField2));
		}
		return jTextField2;
	}

	private JTextField getJTextField1() {
		if (jTextField1 == null) {
			jTextField1 = new JTextField();
			jTextField1.setEditable(false);
			jTextField1.setName("W");
			jTextField1.setToolTipText(
					lang.getProperty("Pulse_for_Change_Hotkey") );
			jTextField1.addKeyListener(new MyKeyListener(jTextField1));
		}
		return jTextField1;
	}

	private JTextField getJTextField0() {
		if (jTextField0 == null) {
			jTextField0 = new JTextField();
			jTextField0.setEditable(false);
			jTextField0.setName("Z");
			jTextField0.setToolTipText(
					lang.getProperty("Pulse_for_Change_Hotkey") );
			jTextField0.addKeyListener(new MyKeyListener(jTextField0));
		}
		return jTextField0;
	}

	private JLabel getJLabel8() {
		if (jLabel8 == null) {
			jLabel8 = new JLabel();
			jLabel8.setText(
					lang.getProperty("Feature_V") );
		}
		return jLabel8;
	}

	private JLabel getJLabel4() {
		if (jLabel4 == null) {
			jLabel4 = new JLabel();
			jLabel4.setText(
					lang.getProperty("Feature_Z") );
		}
		return jLabel4;
	}

	private JLabel getJLabel10() {
		if (jLabel10 == null) {
			jLabel10 = new JLabel();
			jLabel10.setText(
					lang.getProperty("Feature_X") );
		}
		return jLabel10;
	}

	private JLabel getJLabel9() {
		if (jLabel9 == null) {
			jLabel9 = new JLabel();
			jLabel9.setText(
					lang.getProperty("Feature_C") );
		}
		return jLabel9;
	}

	private JLabel getJLabel6() {
		if (jLabel6 == null) {
			jLabel6 = new JLabel();
			jLabel6.setText(
					lang.getProperty("Feature_W") );
		}
		return jLabel6;
	}

	private JLabel getJLabel7() {
		if (jLabel7 == null) {
			jLabel7 = new JLabel();
			jLabel7.setText(
					lang.getProperty("Feature_S") );
		}
		return jLabel7;
	}

	private JLabel getJLabel5() {
		if (jLabel5 == null) {
			jLabel5 = new JLabel();
			jLabel5.setFont( font.deriveFont(Font.PLAIN, 14) );
			jLabel5.setText(
					lang.getProperty("Hot_Keys_Change") );
		}
		return jLabel5;
	}

	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
			jPanel2.setLayout(new GroupLayout());
			jPanel2.add(getJLabel5(), new Constraints(new Leading(0, 164, 12, 12), new Leading(0, 12, 12)));
			jPanel2.add(getJTextField0(), new Constraints(new Leading(5, 154, 12, 12), new Leading(31, 12, 12)));
			jPanel2.add(getJTextField5(), new Constraints(new Leading(5, 154, 12, 12), new Leading(57, 12, 12)));
			jPanel2.add(getJTextField4(), new Constraints(new Leading(5, 154, 12, 12), new Leading(82, 12, 12)));
			jPanel2.add(getJTextField3(), new Constraints(new Leading(5, 154, 12, 12), new Leading(108, 10, 10)));
			jPanel2.add(getJTextField1(), new Constraints(new Leading(5, 154, 12, 12), new Leading(133, 12, 12)));
			jPanel2.add(getJTextField2(), new Constraints(new Leading(5, 154, 12, 12), new Leading(159, 12, 12)));
			jPanel2.add(getJLabel10(), new Constraints(new Leading(192, 394, 10, 10), new Leading(59, 12, 12)));
			jPanel2.add(getJLabel9(), new Constraints(new Leading(192, 394, 12, 12), new Leading(84, 12, 12)));
			jPanel2.add(getJLabel8(), new Constraints(new Leading(192, 394, 12, 12), new Leading(110, 12, 12)));
			jPanel2.add(getJLabel6(), new Constraints(new Leading(191, 396, 12, 12), new Leading(135, 12, 12)));
			jPanel2.add(getJLabel7(), new Constraints(new Leading(192, 394, 12, 12), new Leading(161, 12, 12)));
			jPanel2.add(getJLabel4(), new Constraints(new Leading(191, 396, 12, 12), new Leading(33, 12, 12)));
		}
		return jPanel2;
	}

	private JCheckBox getJCheckBox0() {
		if (jCheckBox0 == null) {
			jCheckBox0 = new JCheckBox();
			jCheckBox0.setText(
					lang.getProperty("File_Open_Translated")	);
			jCheckBox0.setToolTipText(
					lang.getProperty("Edit_Open_TXT") );
			jCheckBox0.setSelected(Config.isFileOpenTranslated());
			jCheckBox0.addItemListener(new ItemListener() {

				public void itemStateChanged(ItemEvent event) {
					jCheckBox0ItemItemStateChanged(event);
				}
			});
		}
		return jCheckBox0;
	}

	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
			jPanel1.setLayout(new GroupLayout());
			jPanel1.add(getJLabel3(), new Constraints(new Leading(0, 12, 12), new Leading(0, 12, 12)));
			jPanel1.add(getJCheckBox0(), new Constraints(new Leading(12, 12, 12), new Leading(25, 12, 12)));
		}
		return jPanel1;
	}

	private JLabel getJLabel3() {
		if (jLabel3 == null) {
			jLabel3 = new JLabel();
			jLabel3.setFont( font.deriveFont(Font.PLAIN, 14) );
			jLabel3.setText("File");
		}
		return jLabel3;
	}

	private JRadioButton getJRadioButton1() {
		if (jRadioButton1 == null) {
			jRadioButton1 = new JRadioButton();
			jRadioButton1.setSelected(true);
			jRadioButton1.setText("in Window");
			jRadioButton1.addItemListener(new ItemListener() {

				public void itemStateChanged(ItemEvent event) {
					jRadioButtonWindowItemStateChanged(event);
				}
			});
		}
		return jRadioButton1;
	}

	private JRadioButton getJRadioButton0() {
		if (jRadioButton0 == null) {
			jRadioButton0 = new JRadioButton();
			jRadioButton0.setText("in Balloon");
			jRadioButton0.addItemListener(new ItemListener() {

				public void itemStateChanged(ItemEvent event) {
					jRadioButtonBalloonItemStateChanged(event);
				}
			});
		}
		return jRadioButton0;
	}

	private JPanel getJPanel0() {
		if (jPanel0 == null) {
			jPanel0 = new JPanel();
			jPanel0.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
			jPanel0.setLayout(new GroupLayout());
			jPanel0.add(getJLabel2(), new Constraints(new Leading(2, 10, 10), new Leading(0, 12, 12)));
			jPanel0.add(getJRadioButton0(), new Constraints(new Leading(8, 10, 10), new Leading(25, 12, 12)));
			jPanel0.add(getJRadioButton1(), new Constraints(new Leading(8, 12, 12), new Leading(55, 12, 12)));
			jPanel0.add(getJTextFieldWindowXWidth(), new Constraints(new Leading(98, 42, 12, 12), new Leading(55, 12, 12)));
			jPanel0.add(getJTextFieldWindowYHeight(), new Constraints(new Leading(146, 43, 12, 12), new Leading(55, 12, 12)));
			jPanel0.add(getJLabel12(), new Constraints(new Leading(115, 12, 12), new Leading(39, 10, 10)));
			jPanel0.add(getJLabel13(), new Constraints(new Leading(164, 12, 12), new Leading(39, 12, 12)));
			jPanel0.add(getJTextFieldWindowFontSize(), new Constraints(new Leading(195, 33, 10, 10), new Leading(55, 12, 12)));
			jPanel0.add(getJLabel14(), new Constraints(new Leading(199, 12, 12), new Leading(39, 12, 12)));
		}
		return jPanel0;
	}

	private JLabel getJLabel2() {
		if (jLabel2 == null) {
			jLabel2 = new JLabel();
			jLabel2.setFont( font.deriveFont(Font.PLAIN, 14) );
			jLabel2.setText(
					lang.getProperty("View_Result_of_Translation") );
		}
		return jLabel2;
	}

	private JLabel getJLabel1() {
		if (jLabel1 == null) {
			jLabel1 = new JLabel();
			jLabel1.setFont( font.deriveFont(Font.PLAIN, 16) );
			jLabel1.setText(
					lang.getProperty("Dual_Laguage") );
		}
		return jLabel1;
	}

	private JButton getJButton0() {
		if (jButton0 == null) {
			jButton0 = new JButton();
			jButton0.setText("Save");
			jButton0.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					jButton0ActionActionPerformed(event);
				}
			});
		}
		return jButton0;
	}

	private JLabel getJLabel0() {
		if (jLabel0 == null) {
			jLabel0 = new JLabel();
			jLabel0.setFont( font.deriveFont(Font.PLAIN, 16) );
			jLabel0.setText(
					lang.getProperty("Laguage_Preferred") );
		}
		return jLabel0;
	}

	private JComboBox getJComboBox0() {
		if (jComboBoxIdiomaPreferred == null) {
			jComboBoxIdiomaPreferred = new JComboBox();
			// Los 10 Idiomas más usados en Internet 2011
			jComboBoxIdiomaPreferred.setModel(new DefaultComboBoxModel(new Object[] {
					"English",
					"Chinese",
					"Spanish",
					"Japanese",
					"Portuguese",
					"German",
					"Arabic",
					"French",
					"Ruso",
					"Korean"
			}));
			jComboBoxIdiomaPreferred.setDoubleBuffered(false);
			jComboBoxIdiomaPreferred.setBorder(null);
		}
		return jComboBoxIdiomaPreferred;
	}

	private JComboBox getJComboBox1() {
		if (jComboBoxIdiomaDual == null) {
			jComboBoxIdiomaDual = new JComboBox();
			// Los 10 Idiomas más usados en Internet 2011
			jComboBoxIdiomaDual.setModel(new DefaultComboBoxModel(new Object[] {
					"English",
					"Chinese",
					"Spanish",
					"Japanese",
					"Portuguese",
					"German",
					"Arabic",
					"French",
					"Ruso",
					"Korean"
			}));
			jComboBoxIdiomaDual.setDoubleBuffered(false);
			jComboBoxIdiomaDual.setBorder(null);
		}
		return jComboBoxIdiomaDual;
	}


	/*
	 *  LISTENER
	 */
	public class MyKeyListener extends KeyAdapter {
		JTextField textField;

		public MyKeyListener(JTextField txtField) {
			textField = txtField;
		}

		public void keyPressed(KeyEvent e) {
			displayKey(e, "PRESSED", textField);
		}
		public void keyReleased(KeyEvent e) {
			displayKey(e, "RELEASED", textField);
		}

	}



	/**
	 *  KEYS DISPLAY CHANGE
	 * @param e KeyEvent
	 * @param keyStatus String
	 * @param textField JTextField
	 */
    private void displayKey(KeyEvent e, String keyStatus, JTextField textField){

        // You should only rely on the key char if the event is a key typed event.
        int id = e.getID();
//        String keyString;
        int keyCode = 0;

        if (id == KeyEvent.KEY_TYPED) {
//            char c = e.getKeyChar();
//            keyString = "key character = '" + c + "'";
        } else {
            keyCode = e.getKeyCode();
//            keyString = "key code = " + keyCode
//                    + " ("
//                    + KeyEvent.getKeyText(keyCode)
//                    + ")";
        }

        int modifiersEx = e.getModifiersEx();
        String modString = "extended modifiers = " + modifiersEx;
        String tmpString = KeyEvent.getModifiersExText(modifiersEx);

        if (tmpString.length() > 0) {
            modString += " (" + tmpString + ")";
        } else {
            modString += "";
        }

        String actionString = "action key? ";
        if (e.isActionKey()) {
            actionString += "YES";
        } else {
            actionString += "NO";
        }

        String locationString = "";
        int location = e.getKeyLocation();
        if (location == KeyEvent.KEY_LOCATION_STANDARD) {
            locationString += "standard";
        } else if (location == KeyEvent.KEY_LOCATION_LEFT) {
            locationString += "left";
        } else if (location == KeyEvent.KEY_LOCATION_RIGHT) {
            locationString += "right";
        } else if (location == KeyEvent.KEY_LOCATION_NUMPAD) {
            locationString += "numpad";
        } else { // (location == KeyEvent.KEY_LOCATION_UNKNOWN)
            locationString += "unknown";
        }


//        if (keyString.length() !=0 &&
//        			locationString.equals("standard")){
//        	System.out.println("* "+ keyStatus +"   " + tmpString+"  "+keyString);
//        	}


        // PRESSED
        if (keyStatus.equals("PRESSED")){
        	if (locationString.equals("standard")){
        		if (tmpString.length() != 0)
        			textField.setText(tmpString + "+" + KeyEvent.getKeyText(keyCode));
        		else
        			textField.setText(KeyEvent.getKeyText(keyCode));
        	}
        	else{
        		textField.setText(tmpString);
        	}

        	keyCodePress = keyCode;
        }


        // RELEASED
        if (keyStatus.equals("RELEASED") && locationString.equals("standard")){
                if (keyCode != keyCodePress) {
                	Tray.setBallonMessage(
                			KeyEvent.getModifiersExText(modifiersEx) +
                			"+" + KeyEvent.getKeyText(keyCode) + "\n" +
                			lang.getProperty("In_use_by_another_program")
                			,MessageType.ERROR
                			);

                	textField.setText(
                			Config.getkeyString( textField.getName() )
                			);
                } else{
                	tempKeys.put( "_"+textField.getName(), keyCode);
                	tempKeys.put( "_"+textField.getName()+"_Mod", modifiersEx);
                }
        }//end Released

    }



	/**
	 * TODO SAVE BUTTON OPTIONS
	 * @param event
	 */
	private void jButton0ActionActionPerformed(ActionEvent event) {
		// Asigna los valores para las preferencias de las Teclas Hookeys
		Config.setTempToKeys(tempKeys);

		try {
			// Asigna los valores para las preferencias de la Ventana (in Window) resultado de la traducción
			Config.setWindowXWidth( Integer.parseInt( jTextFieldWindowXWidth.getText()));
			Config.setWindowYHeight( Integer.parseInt( jTextFieldWindowYHeight.getText()));
			Config.setWindowFontSize( Integer.parseInt( jTextFieldWindowFontSize.getText()));
		} catch (Exception e) {
			Message.viewError ( lang.getProperty("View_Result_of_Translation"),  e.getMessage () +"\n" + lang.getProperty("Please_write_only_numbers") );
			KeysHook.DisableGlobalHotKeys();

			// Muestra las opciones nuevamente para que el usuario cambie correctamente el valor
	    	Options optionsForm = new Options();
	        optionsForm.setVisible(true);
		}


		// Activa la Prioridad asignada
		if (jRadioButtonHighPriority.isSelected())
			Config.setPriority(Thread.MAX_PRIORITY); else Config.setPriority(Thread.NORM_PRIORITY);

		// Guarda todas las preferencias: Keys, Window, Language, Ocr,..
		Config.setPreferences(jComboBoxIdiomaDual, jComboBoxIdiomaPreferred, jComboBoxOcrIdioma, jComboBoxOcrDpi);

		// HooKey reActive
		try {
			KeysHook.ReactiveRegisterHotKey();
		} catch (HotkeyConflictException e) {
			Logger.getLogger( Options.class.getName() ).log(Level.SEVERE, "Key Conflict Reactive", e);
			e.printStackTrace();
		}

		tempKeys.clear();
		this.setVisible(false);
        this.dispose();
	}



	/**
	 * ICONO del Frame/Dialog
	 */
	private void icono(){
		setIconImage( new ImageIcon( getClass().getResource("/resource/universal.png") ).getImage() );
	}


	/**
	 * TECLAS INICIALES
	 * <p>
	 * FIXME BUG, es necesario comentar, para Editar el Diseño
	 */
	private void teclas(){
		tempKeys = Config.getKeysToTemp();
		jTextField0.setText( Config.getkeyString("Z") );
		jTextField5.setText( Config.getkeyString("X") );
		jTextField4.setText( Config.getkeyString("C") );
		jTextField3.setText( Config.getkeyString("V") );
		jTextField1.setText( Config.getkeyString("W") );
		jTextField2.setText( Config.getkeyString("S") );
	}


	/**
	 * LANGUAGE Properties
	 * <p>
	 * FIXME BUG, es necesario comentar, para Editar el Diseño
	 */
	private void language() {
    	//lang = new Idiomas("es"); // dejar solo esta linea, sin comentar, para editar
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


	/**
	 *  COMBOBOX de los Idiomas
	 */
	private void IdiomasComboBox() {

		HashMap<String, com.google.api.translate.Language> idiomasGoogle;
		HashMap<String, com.memetix.mst.language.Language> idiomasMicrosoft;

		idiomasGoogle = google.Languages.idiomasHashMap();
		idiomasMicrosoft = microsoft.Languages.idiomasHashMap();

		// Clear Box
        jComboBoxIdiomaPreferred.removeAllItems();
        jComboBoxIdiomaDual.removeAllItems();

        Iterator<String> languages = null;

	    switch ( Config.getEngine() ){
	    case 0:
	    	languages = idiomasGoogle.keySet().iterator();
	    	break;
	    case 1:
	    	languages = idiomasMicrosoft.keySet().iterator();
	    	break;
	    }


        // Generate List de los Lenguages del ComboBox
        while (languages.hasNext()) {
        		String lang = languages.next();
        		if (!lang.equals("Auto_detect")) {
                	jComboBoxIdiomaPreferred.addItem( lang );
                	jComboBoxIdiomaDual.addItem( lang );
        		}
        }

        // Posicionar ComboBox en los Idiomas de Configuracion
        jComboBoxIdiomaPreferred.setSelectedItem( Config.getIdiomaPreferido() );
        jComboBoxIdiomaDual.setSelectedItem( Config.getIdiomaDual() );
		}



    /**
     * VERIFY IF LANGUAGE CHANGE IS AVAILABLE
     */
    private void verifyLanguageAvailable(int event, String dual, String preferido) {

    	String idiomaDualGoogle = google.Languages.isValidLanguage( dual );
    	String idiomaDualMicrosoft = microsoft.Languages.isValidLanguage( dual );

    	String idiomaPreferidoGoogle = google.Languages.isValidLanguage( preferido );
    	String idiomaPreferidoMicrosoft = microsoft.Languages.isValidLanguage( preferido );


        switch (event) {
        case 0:
            if (!idiomaDualGoogle.equals( idiomaDualMicrosoft )) {
				Message.viewError(
						idiomaDualMicrosoft
						,idiomaDualMicrosoft + " " + lang.getProperty("Not_in_Google_Translator")
						);
            }

            if (!idiomaPreferidoGoogle.equals( idiomaPreferidoMicrosoft )) {
				Message.viewError(
						idiomaPreferidoMicrosoft
						,idiomaPreferidoMicrosoft + " " + lang.getProperty("Not_in_Google_Translator")
						);
            }
            break;

        case 1:
            if (!idiomaDualGoogle.equals( idiomaDualMicrosoft )) {
                Message.viewError(
                        idiomaDualGoogle
                        ,idiomaDualGoogle + " " + lang.getProperty("Not_in_Microsoft_Translator")
                        );
            }

            if (!idiomaPreferidoGoogle.equals( idiomaPreferidoMicrosoft )) {
                Message.viewError(
                        idiomaPreferidoGoogle
                        ,idiomaPreferidoGoogle + " " + lang.getProperty("Not_in_Microsoft_Translator")
                        );
            }
            break;
        }
    }



	/**
	 * 	TODO SELECTED BALLOON/MESSAGE
	 */
	private void setInBalloonOrMessage(){
		if (Config.isInBalloonOrMessage()){
			jRadioButton0.setSelected(true);
			jRadioButton1.setSelected(false);
		}
		else{
			jRadioButton0.setSelected(false);
			jRadioButton1.setSelected(true);
		}

		jTextFieldWindowXWidth.setText(	Integer.toString( Config.getWindowXWidth()));
		jTextFieldWindowYHeight.setText( Integer.toString( Config.getWindowYHeight()));
		jTextFieldWindowFontSize.setText( Integer.toString( Config.getWindowFontSize()));
	}

    /*
     *  EVENTO BALLOON
     */
	private void jRadioButtonBalloonItemStateChanged(ItemEvent event) {
		if (event.getStateChange() == 1){
			jRadioButton0.setSelected(true);	// Balloon
			jRadioButton1.setSelected(false);	// Window
			Config.setInBalloonOrMessage(true);
		}
		else{
			jRadioButton0.setSelected(false);	// Balloon
			jRadioButton1.setSelected(true);	// Window
			Config.setInBalloonOrMessage(false);
		}
	}

	/*
	 *  EVENTO WINDOW
	 */
	private void jRadioButtonWindowItemStateChanged(ItemEvent event) {
		if (event.getStateChange() == 1){
			jRadioButton0.setSelected(false);	// Balloon
			jRadioButton1.setSelected(true);	// Window
			Config.setInBalloonOrMessage(false);
		}
		else{
			jRadioButton0.setSelected(true);	// Balloon
			jRadioButton1.setSelected(false);	// Window
			Config.setInBalloonOrMessage(true);
		}
	}



	/**
	 * 	SELECTED OCR SETTINGS
	 */
	private void setComboBoxOcr(){
			jComboBoxOcrIdioma.setSelectedItem( Config.getOcrIdioma());
			jComboBoxOcrDpi.setSelectedItem( Config.getOcrDpi());
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



	/*
	 *  EVENTO FILE OPEN TRANSLATED
	 */
	private void jCheckBox0ItemItemStateChanged(ItemEvent event) {
		if (event.getStateChange() == 1){
			jCheckBox0.setSelected(true);
			Config.setFileOpenTranslated(true);
		}
		else{
			jCheckBox0.setSelected(false);
			Config.setFileOpenTranslated(false);
		}

	}

	/*
	 *  EVENTO CLOSED
	 */
	private void windowWindowClosing(WindowEvent event) {
		tempKeys.clear();

		try {
			KeysHook.ReactiveRegisterHotKey();
		} catch (HotkeyConflictException e) {
			Logger.getLogger( Options.class.getName() ).log(Level.SEVERE, "Key Conflict Reactive", e);
		}

		this.dispose();
	}



	/**
	 * 	TODO GOOGLE/MICROSFT SELECTED ENGINE
	 */
	private void setEngineGoogleorMicrosoft(){
		int engine = Config.getEngine();

		switch (engine) {
		case 0:
			jRadioButtonGoogle.setSelected(true);
			jRadioButtonMicrosoft.setSelected(false);
			break;

		case 1:
			jRadioButtonGoogle.setSelected(false);
			jRadioButtonMicrosoft.setSelected(true);
			break;
		}
	}

	/*
	 *  EVENTO GOOGLE
	 */
	private void jRadioButtonGoogleItemStateChanged(ItemEvent event) {
		eventoEngine ++;
		String dual, preferido;

        if (eventoEngine == 1)
		if (event.getStateChange() == 1){
			jRadioButtonGoogle.setSelected(true);
			jRadioButtonMicrosoft.setSelected(false);
//			Config.setGoogleExceeded(false); // Reset Quota
			Config.setEngine(0);	// Google

			// GET Idioma del BOX actual
			dual = jComboBoxIdiomaDual.getSelectedItem().toString();
			preferido = jComboBoxIdiomaPreferred.getSelectedItem().toString();

			// VERIFY if exist in the OTHER ENGINE
			verifyLanguageAvailable( 0, dual, preferido);
			IdiomasComboBox();

			// Posicionar ComboBox en al Idioma que se selecciono
	        jComboBoxIdiomaDual.setSelectedItem( dual );
	        jComboBoxIdiomaPreferred.setSelectedItem( preferido );
		}
		else {
			jRadioButtonGoogle.setSelected(true);
			jRadioButtonMicrosoft.setSelected(false);
			}

        // event state out
		if (eventoEngine == 2) eventoEngine = 0;
	}

	/*
	 *  EVENTO MICROSOFT
	 */
	private void jRadioButtonMicrosoftItemStateChanged(ItemEvent event) {
		eventoEngine ++;
		String dual, preferido;

        if (eventoEngine == 1)
		if (event.getStateChange() == 1){
			jRadioButtonGoogle.setSelected(false);
			jRadioButtonMicrosoft.setSelected(true);
//			Config.setGoogleExceeded(false); // Reset Quota
			Config.setEngine(1);	// Microsoft

			// GET Idioma del BOX actual
			dual = jComboBoxIdiomaDual.getSelectedItem().toString();
			preferido = jComboBoxIdiomaPreferred.getSelectedItem().toString();

			// VERIFY if exist in the OTHER ENGINE
			verifyLanguageAvailable( 1, dual , preferido );
			IdiomasComboBox();

			// Posicionar ComboBox en al Idioma que se selecciono
	        jComboBoxIdiomaDual.setSelectedItem( dual );
	        jComboBoxIdiomaPreferred.setSelectedItem( preferido );
		}
		else {
			jRadioButtonGoogle.setSelected(false);
			jRadioButtonMicrosoft.setSelected(true);
		}

        // event state out
		if (eventoEngine == 2) eventoEngine = 0;
	}



	/**
	 * 	TODO PRIORITY SELECTED
	 */
	private void setPrioritySelected(){
		switch (Config.getPriority()) {
		case Thread.MAX_PRIORITY:
			jRadioButtonHighPriority.setSelected(true);
			jRadioButtonNormalPriority.setSelected(false);
			break;
		case Thread.NORM_PRIORITY:
		case 6:
			jRadioButtonHighPriority.setSelected(false);
			jRadioButtonNormalPriority.setSelected(true);
		}
	}

	/*
	 *  EVENT HIGH PRIORITY
	 */
	private void jRadioButtonHighPriorityItemStateChanged(ItemEvent event) {
		if (event.getStateChange() == 1) {
			jRadioButtonHighPriority.setSelected(true);
			jRadioButtonNormalPriority.setSelected(false);
		}
		else {
			jRadioButtonHighPriority.setSelected(false);
			jRadioButtonNormalPriority.setSelected(true);
		}
	}

	/*
	 *  EVENT NORMAL PRIORITY
	 */
	private void jRadioButtonNormalPriorityItemStateChanged(ItemEvent event) {
		if (event.getStateChange() == 1) {
			jRadioButtonNormalPriority.setSelected(true);
			jRadioButtonHighPriority.setSelected(false);
		}
		else {
			jRadioButtonNormalPriority.setSelected(false);
			jRadioButtonHighPriority.setSelected(true);
		}
	}




}