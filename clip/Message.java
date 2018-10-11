package clip;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import execute.Traductor;
import form.Tray;


/**
 * Message Windows Confirmations, Input, View
 * <p>
 * Copyright (c) 2011 DualClip Translator, Inc. http://sourceforge.net/projects/dcliptranslator/
 * @author Cesar Rodriguez Gonzalez <Stelars@users.sourceforge.net>
 */
public class Message extends JFrame {

	private static final long serialVersionUID = -3990303174181298550L;
	private static final Log LOG = LogFactory.getLog( Message.class );
	private boolean isWriteResult = false;
	private int X, Y;
	private long tiempoInicio;
    private String txtOriginal;
    private String txtTraducido;




	/* CONSTRUCTORS MESSAGE */
	public Message() {
		super("powered by " + Config.getEngineName());
		if (Config.isInfo) tiempoInicio = System.currentTimeMillis();
	}

	/* CONSTRUCTORS MESSAGE */
	public Message( String message ) {
		super("powered by " + Config.getEngineName());
		if (Config.isInfo) tiempoInicio = System.currentTimeMillis();

		// Para que guarde el contenido de esa ventana
		// y no la de otra traducción, en el caso de mostrar 2 o más ventanas abiertas
		txtOriginal = Traductor.getTxtOriginal();
		txtTraducido = Traductor.getTxtTraducido();

		this.getXYWindow( message );
		this.setWindowMessageScroll( message );

		this.setVisible(true);
	}





	/**
     * VIEW MESSAGE OF TRANSLATION
     *
     * @param message
     */
	public void setWindowMessageScroll( String message ) {

		JTextArea txt = new JTextArea(message);
		JScrollPane jScrollPane1 = new JScrollPane(txt);
		int size = Config.getWindowFontSize();

	    txt.setLineWrap(true);
	    txt.setWrapStyleWord(true);
		txt.setEditable(false);
		txt.setLineWrap(true);
		txt.setFont(new Font(null, Font.PLAIN, size));

	    setIconImage( new ImageIcon( getClass().getResource("/resource/universal.png") ).getImage() );
		getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		setAlwaysOnTop(true);


	    GroupLayout layout = new GroupLayout(getContentPane());
	    getContentPane().setLayout(layout);

	    // Create a parallel group for the horizontal axis
	    ParallelGroup hGroup = layout
	        .createParallelGroup(GroupLayout.Alignment.LEADING);

	    // Create a sequential and a parallel groups
	    SequentialGroup h1 = layout.createSequentialGroup();
	    ParallelGroup h2 = layout
	        .createParallelGroup(GroupLayout.Alignment.TRAILING);

	    // Add a scroll pane and a label to the parallel group h2
	    h2.addComponent(jScrollPane1, GroupLayout.Alignment.LEADING,
	        GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE);

	    // Add the group h2 to the group h1
	    h1.addGroup(h2);

	    // Add the group h1 to the hGroup
	    hGroup.addGroup(GroupLayout.Alignment.TRAILING, h1);

	    // Create the horizontal group
	    layout.setHorizontalGroup(hGroup);

	    // Create a parallel group for the vertical axis
	    ParallelGroup vGroup = layout
	        .createParallelGroup(GroupLayout.Alignment.LEADING);
	    // Create a sequential group v1
	    SequentialGroup v1 = layout.createSequentialGroup();

	    // Create a parallel group v2
	    ParallelGroup v2 = layout
	        .createParallelGroup(GroupLayout.Alignment.BASELINE);

	    // Add the group v2 tp the group v1
	    v1.addGroup(v2);
	    v1.addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, Y,
	    	Short.MAX_VALUE);

	    // Add the group v1 to the group vGroup
	    vGroup.addGroup(v1);

	    // Create the vertical group
	    layout.setVerticalGroup(vGroup);
	    setSize(X, Y);
	    //pack();

	    // Tamaño de la ventana resultante
	    log("Dimensions:  X: " + X + "  Y: " + Y);

		// Get the size of the screen
	    java.awt.Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
	    // Determine the new location of the window and Move the window
        setLocation((dimension.width - getWidth()) / 2, (dimension.height - getHeight()) / 2);


	    // Cerrar ventana con Click Derecho del Mouse
	    MouseListener closedConMouse = new MouseAdapter(){
	        public void mouseClicked (MouseEvent event){
	        	if (event.getButton() == 3)
	        		dispose();
	        }
	    };

	    txt.addMouseListener (closedConMouse);

	    isWriteResult = true;

	    // Cerrar ventana con tecla ESC además de, Save el resultado con la tecla S, A
	    createRootPane();

	    if (Config.isInfo) {
			long totalTiempo = System.currentTimeMillis() - tiempoInicio;
			System.out.println("Ha tardado: " + totalTiempo + " miliseg en ejecutarse Message.");
  			System.out.println("Current: " + Thread.currentThread().getPriority());
  			System.out.println("Pref: " + Config.getPriority());
	    }
	}



	/**
     * GET X Y en ventana Window de resultado traducido
     *
     * @param message
     */
	private void getXYWindow( String message ) {

		JTextArea txt = new JTextArea( message );
		JFrame frame = new JFrame();
		int size = Config.getWindowFontSize();

		txt.setLineWrap(true);
		txt.setWrapStyleWord(true);
		txt.setFont(new Font(null, Font.PLAIN, size));

		frame.getContentPane().add(txt, BorderLayout.CENTER);

		// Ajuste de X según la Metrics de la fuente
		FontMetrics fmText = txt.getFontMetrics(
				new Font(null, Font.PLAIN, size)
				);

		int fontMetricsText = fmText.stringWidth( message );

		log("Font: "+ fmText.getFont());
		log("FontMetrics Text: "+ fontMetricsText);


		// Ajuste al mínimo tamaño de ventana, para mostrar "Powered by Google"
		if (fontMetricsText < 209)
			X = 240;
		else {
			// Buscar la linea más larga
			String linea;
			int esteLength = 0;
			int esteWidth = 0;
			BufferedReader reader = new BufferedReader(
					new StringReader(message));

	        // PHARSER de las lineas del texto
			try {
					while ((linea = reader.readLine()) != null) {
						 if (linea.length() > esteLength) {
							 esteLength =  linea.length();
							 esteWidth = fmText.stringWidth( message );
						 }

					}//end while
				} catch(IOException e) {
					LOG.error("PHARSER de readLine excedido");
				}

			X = esteWidth + 30;
		}

	    // Tamaño de la ventana detectada
	    log("Detectada X: " + X);

		if (X > Config.getWindowXWidth()) X = Config.getWindowXWidth();


		// AUTO SIZE JTEXTAREA
		GroupLayout layout = new GroupLayout(frame.getContentPane());
		frame.getContentPane().setLayout(layout);
	    layout.setVerticalGroup(layout.createParallelGroup()
	        .addComponent(txt)
	    );
	    layout.setHorizontalGroup(layout.createSequentialGroup()
	         .addComponent(txt, 60, X, X)
	    );


	    // Ajuste al tamaño preferido y la disposición
	    frame.pack();
	    layout.invalidateLayout(frame.getContentPane());
	    layout.preferredLayoutSize(frame.getContentPane());
	    frame.pack();

	    X = frame.getWidth();
	    Y = frame.getHeight() + 10;

	    // Tamaño de la ventana detectada
	    log("Detection  Y: " + Y);

	    // Ajuste de Y para posible ultima palabra y al tamaño preferido
	    if ((Y - 25) > Config.getWindowYHeight())
	    	Y = Config.getWindowYHeight();

	    frame.dispose();
	}



	/**
	 * INPUT DIALOG FOR TRANSLATION
	 */
	public void getWindowInputDialogAndTranslation() {
		// crear campo de texto con tamaño predeterminado
		JTextField textField = new JTextField(40);
		Container contenedor = getContentPane();
		int size = Config.getWindowFontSize();

		// contenedor.
		textField.setFont(new Font(null, Font.PLAIN, size));
		textField.setLayout(new FlowLayout());


		textField.setToolTipText("Write or paste the text you want translated");

		textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			   	Message.setBalloonWindowMessage(
			    		Traductor.goTraducirTextoAvanzado(e.getActionCommand(), true)
			    		);
			}
		});

		contenedor.add(textField);

		setUndecorated(true);
		setAlwaysOnTop(true);

	    // Ajuste al tamaño preferido y la disposición de sus subcomponentes
	    pack();
		setSize(getSize().width, getSize().height+3);

		// Get the size of the screen
	    java.awt.Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
	    // Determine the new location of the window and Move the window
        setLocation((dimension.width - getWidth()) / 2, (dimension.height - getHeight()) / 2);
        setVisible(true);

        isWriteResult = false;
        // Cerrar ventana con tecla ESC
	    createRootPane();
	}




/* DIALOG
	// CONFIRMATION
	public int getWindowConfirmDialog( String message ){
        return JOptionPane.showConfirmDialog( this, message );
	}


	// INPUT
	public String getWindowInputDialog( String message ){
        return JOptionPane.showInputDialog( this, message,"" );
	}


	// VIEW MESSAGE ONLY
	public static void setWindowMessageDialog( String message ){
        JOptionPane.showMessageDialog( null, message );
	}
*/



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
     *  VIEW ERROR message via a dialog box.
     *  @param message the message to be presented
     */
    public static void viewError (String title, String message) {
       JOptionPane.showMessageDialog (null, message, title,
                                      JOptionPane.ERROR_MESSAGE);
    }



    /**
     * Metodo de visualizacion del texto traducido Balloon/Window
     */
    public static void setBalloonWindowMessage(String message) {
    	if (Traductor.isContent(message)) {
        	if (Config.isInBalloonOrMessage())
        	    Tray.setBallonMessage( message, MessageType.INFO );
        	else
    	    	new Message( message );
    	}
    	else {
    		if (Config.getUserLanguage().equals("es"))
    			message = "No hay contenido en el Portapapeles";
    		else
    			message = "There is no content in the Clipboard";

    		Tray.setBallonMessage(message, "DualClip Translator", MessageType.INFO );
    	}


    }



    /**
     *  CERRAR Ventana JFrame or Dialog con tecla ESC
     *  SAVE el resultado de OCR/Traducción con la tecla S
     */
	protected JRootPane createRootPane() {
		// ESC Listener
        ActionListener actionListenerEsc = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
            	dispose();
            }
        };
        // SAVE Listener
        ActionListener actionListenerSave = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if (isWriteResult){
                	dispose();
                	Archivo.saveTxtOCR("",
                			txtTraducido
                			);
                }
            }
        };
        // ALL Listener
        ActionListener actionListenerAll = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if (isWriteResult){
                	dispose();
                	Archivo.saveTxtOCR(
                			txtOriginal,
                			txtTraducido
                			);
                }
            }
        };

      KeyStroke strokeEsc = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
      KeyStroke strokeSave = KeyStroke.getKeyStroke(KeyEvent.VK_S, 0);
      KeyStroke strokeAll = KeyStroke.getKeyStroke(KeyEvent.VK_A, 0);

      JRootPane rootPane = new JRootPane();
      rootPane.registerKeyboardAction(actionListenerEsc, strokeEsc, JComponent.WHEN_IN_FOCUSED_WINDOW);
      rootPane.registerKeyboardAction(actionListenerSave, strokeSave, JComponent.WHEN_IN_FOCUSED_WINDOW);
      rootPane.registerKeyboardAction(actionListenerAll, strokeAll, JComponent.WHEN_IN_FOCUSED_WINDOW);
      return rootPane;
    }


}