package ocr;

import java.awt.AWTException;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import clip.Config;
import clip.Message;
import form.Tray;

/**
 * Screen Capture of Rectangle Zone
 * <p>
 * Copyright (c) 2011 DualClip Translator, Inc. http://sourceforge.net/projects/dcliptranslator/
 * @author Cesar Rodriguez Gonzalez <Stelars@users.sourceforge.net>
 */
public class Screen {

    private static final long serialVersionUID = 8600013473248580586L;
    private MouseListener mouseListener;
    private MouseMotionListener mouseMotionListener;
    private BufferedImage fullscreen;
    private Robot robot = null;
    private JFrame window;



    /**
     * Rectangle Graphics of selection
     */
    final Box box = new Box();

    /**
     *	Activated the rectangular selection area to be captured
     */
    boolean pressedDragged = false;

    /**
     * 	Mouse click released count
     */
    int releasedCount = 0;

    /**
     *  Location of FullScreen.
     */
    Rectangle fullRec;

   /**
    *  Stroke-defined outline of selection rectangle.
    */
    private BasicStroke bs;

    /**
     *  Mouse coordinates when mouse button pressed.
     */
    private int srcx, srcy;

    /**
     *  Latest mouse coordinates during drag operation.
     */
    private int destx, desty;

    /**
     *  Mouse coordinates when mouse moved.
     */
    private int curx, cury;

    /**
     *	DPI Metadata Resolution
     */
    private int dpi = Config.getOcrDpiValue();




    public Screen() {
        try {
            robot = new Robot();
            } catch (AWTException e) {
                    Message.viewError ("Screen Capture", e.getMessage ());
                    System.exit (0);
                    }
                catch (SecurityException e) {
                    Message.viewError ("Screen Capture", "Permission required");
                    System.exit (0);
                    }

        // GET SIZE SCREEN FULL
        final Dimension SIZE = Toolkit.getDefaultToolkit().getScreenSize();

        // FULL RECTANGLE
        fullRec = new Rectangle( SIZE );
    }



    /**
     *  FULL SCREEN CAPTURE

	public void captureFull() {

		// CAPTURE BUFFER & RESIZE
		BufferedImage image = robot.createScreenCapture( fullRec );
		image = scaleImageByDpi(image, dpi);

		// SAVE IMAGE
		File screenFile = null;
		try {
			screenFile = new File("screenshot.png");
			ImageIO.write(image, "png", screenFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
		new Ocr( screenFile );
	}
     */


    /**
     *  RECTANGLE SCREEN CAPTURE
     */
    public void captureRectangle() {

        // Define the stroke for drawing selection rectangle outline.
        bs = new BasicStroke (5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
                                    0, new float [] { 12, 12 }, 0);

        fullscreen = robot.createScreenCapture( fullRec );
        ImageIcon i = new ImageIcon( fullscreen );

        // BOX, PANEL, LABEL
        JPanel panel = new JPanel(null);
        JLabel label = new JLabel(i);

        // BOX
        box.setBounds( fullRec );

        // PANEL
        panel.add( box );
        panel.add( label );

        // LABEL
        label.setBounds( fullRec );

        // FRAME Options
        window = new JFrame();
        window.setBackground(Color.black);
        window.setUndecorated( true );
        window.setContentPane( panel );
        window.setBounds( fullRec );
        window.setLocation(0, 0);
        window.setCursor( Cursor.getPredefinedCursor(Cursor.HAND_CURSOR) );



        /*
         * KEY
         */
        KeyListener listener = new KeyListener() {
        	public void keyPressed(KeyEvent e) {
        		//dumpInfo("Pressed", e);
            	if (e.getKeyCode() == 16 || e.getKeyCode() == 17){
            		Config.setKeyScreen (true);
            		box.repaint ();
            	}

            }

            public void keyReleased(KeyEvent e) {
            	//dumpInfo("Released", e);
            	if (e.getKeyCode() == 16 || e.getKeyCode() == 17){
            		Config.setKeyScreen (false);
            		box.repaint ();
            	}
            }

            public void keyTyped(KeyEvent e) {
            	//dumpInfo("Typed", e);
            }

            /* DUMP INFO
            private void dumpInfo(String s, KeyEvent e) {
            	int code = e.getKeyCode();
            	int mods = e.getModifiersEx();

				Logger.getLogger( Screen.class.getName() ).log(Level.INFO, "Key Pulse Info"
            		+ s
	            	+ "\n\tCodN: " 		+ KeyEvent.getKeyText(code)
	            	+ "\n\tCode: " 		+ code
	            	+ "\n\tChar: " 		+ e.getKeyChar()
	            	+ "\n\tMods: " 		+ KeyEvent.getModifiersExText(mods)
	            	+ "\n\tLocation: " 	+ location(e.getKeyLocation())
	            	+ "\n\tAction? " 		+ e.isActionKey()
            	);
            }
            */

            /* LOCATION INFO
            private String location(int location) {
            	switch (location) {
	            	case KeyEvent.KEY_LOCATION_LEFT:
	            		return "Left";
	            	case KeyEvent.KEY_LOCATION_RIGHT:
	            		return "Right";
	            	case KeyEvent.KEY_LOCATION_NUMPAD:
	            		return "NumPad";
	            	case KeyEvent.KEY_LOCATION_STANDARD:
	            		return "Standard";
	            	case KeyEvent.KEY_LOCATION_UNKNOWN:
	            	default:
	            		return "Unknown";
            	}
            }
            */
        };
        window.addKeyListener(listener);



        /*
         *  MOUSE MOTION Listener
         */
        mouseMotionListener = new MouseMotionListener() {
            // DRAGGED
            public void mouseDragged(MouseEvent mouse) {
                pressedDragged = true;
                releasedCount = 1;
                destx = mouse.getX ();
                desty = mouse.getY ();
                box.repaint ();
            }
            // MOVED
            public void mouseMoved(MouseEvent mouse) {
                if (releasedCount == 1){
                    pressedDragged = true;
                    destx = mouse.getX ();
                    desty = mouse.getY ();
                }
                else{
                    curx = mouse.getX ();
                    cury = mouse.getY ();
                }
                box.repaint ();
            }
        };
        window.addMouseMotionListener( mouseMotionListener );



        /*
         *  MOUSE ADAPTER Listener
         */
        mouseListener = new MouseAdapter() {
            // PRESSED
            public void mousePressed(MouseEvent mouse) {
                destx = srcx = mouse.getX ();
                desty = srcy = mouse.getY ();
                box.repaint ();

                if ( releasedCount == 1 && mouse.getButton() == 1)
                    saveCapture();
            }

            // RELEASED
            public void mouseReleased(MouseEvent mouse) {
                releasedCount ++;

                if (mouse.getButton() == 3)	// Cancel the capture by pressing mouse right click
                    off();
                else{
                    // RECTANGLE CAPTURE
                    if ( pressedDragged )
                        saveCapture();
                }//end else
          }//end release
        };
        window.addMouseListener( mouseListener );


        // FRAME VISIBLE
        window.setVisible(true);
        window.setFocusable(true);
        window.requestFocus();
        window.setAlwaysOnTop(true);
    }



    /**
     *  SAVE CAPTURE RECTANGLE AREA
     */
    private void saveCapture() {
        if (box.rec.width > 20 && box.rec.height > 20) {

            // Delimitar el BasicStroke
            box.rec.x += 3;			box.rec.y += 3;
            box.rec.width -= 5;		box.rec.height -= 5;

            // CAPTURE BUFFER
            BufferedImage image = robot.createScreenCapture(box.rec);

//       		// TODO FILTER experimental
//            image = filterImage(image);

//           	// TODO BRILLO Y CONTRASTE experimental
//            final float factor = 1.00f;		// Contraste
//            final float offset = -90.0f;	// Brillo
//            RescaleOp rescale = new RescaleOp(factor, offset, null);
//            rescale.filter(image, image);

            // RESIZE SCALE
            image = scaleImageByDpi(image, dpi);

            off();
            Tray.setIconChange(true);

            // SAVE IMAGE
            File screenFile = null;
            try {
                screenFile = File.createTempFile("screenshot_", ".png");	// en directory Temp para que Teseract funcione con Runtime.getRuntime().exec
                ImageIO.write(image, "png", screenFile);

                // OCR
                new Ocr( screenFile );

		            } catch (IOException e) {
		            	Logger.getLogger( Screen.class.getName() ).log(Level.SEVERE, "WRITE IMAGE", e);
		            }
        }
        else{
            releasedCount = 0;
            pressedDragged = false;
        }//end else if
    }



    /**
     *  TERMINATE
     */
    private void off() {
        window.removeAll();
        window.dispose();
    }



    /**
     * Fundido con fondo negro en la selección
     */
    private void drawSelection(Graphics2D g2d, Rectangle s) {

        float trans = (float)((s.getWidth() + s.getHeight()) / (fullRec.getWidth() + fullRec.getWidth()));
        g2d.setComposite(AlphaComposite.getInstance(3, trans));
        //g2d.setColor(Color.darkGray);

        g2d.fillRect(0, 0, (int)s.getX(), (int)fullRec.getHeight());
        g2d.fillRect((int)s.getX(), 0, (int)fullRec.getWidth(), (int)s.getY());
        g2d.fillRect((int)(s.getX() + s.getWidth()), (int)s.getY(), (int)(fullRec.getWidth() - s.getX() + s.getWidth()), (int)(fullRec.getHeight() - s.getY()));
        g2d.fillRect((int)s.getX(), (int)(s.getY() + s.getHeight()), (int)s.getWidth(), (int)(fullRec.getHeight() - s.getY() + s.getHeight()));
        g2d.setComposite(AlphaComposite.getInstance(3, 1.0F));

        g2d.draw (box.rec);
    }



    /**
     *	BOX Rectangle Graphics
     */
    private class Box extends JLabel {
        private static final long serialVersionUID = 1L;
        public Rectangle rec = new Rectangle();

        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
                g2d.setStroke (bs);
                if (Config.isKeyScreen())
                	g2d.setColor(Color.gray);
                else
                	g2d.setColor(Color.darkGray);

                if (!pressedDragged){
                    g2d.drawLine(curx , 0, curx , fullRec.height);
                    g2d.drawLine(0, cury, fullRec.width, cury);
                }

              // Coordinate Draw the selection rectangle if present
              if (srcx != destx || srcy != desty) {
                  // Compute upper-left and lower-right coordinates for selection rectangle corners
                  int x1 = (srcx < destx) ? srcx : destx;
                  int y1 = (srcy < desty) ? srcy : desty;

                  int x2 = (srcx > destx) ? srcx : destx;
                  int y2 = (srcy > desty) ? srcy : desty;

                  // Rectangle origin
                  rec.x = x1;
                  rec.y = y1;

                  // Rectangle extents
                  rec.width = (x2-x1)+1;
                  rec.height = (y2-y1)+1;

                  // Draw selection rectangle
                  drawSelection(g2d, rec);
              }

              g2d.draw (rec);
        }
    }



    /**
     * RESIZE IMAGE BY DPI
     * <p>
     * @param image BufferedImage
     * @param dpi
     * @param hints boolean for Interpolation, Bilinear, Antialiasing
     * @return
     */
    public BufferedImage scaleImageByDpi(BufferedImage image, int dpi) {
        // Scale for Screen Capture
        //int scale = dpi / (width/ (dpi/100) );
        int scale = dpi / 96;
        int xScale = ( image.getWidth() * scale );
        int yScale = ( image.getHeight() * scale );

        // SCALE by Resample
//        ResampleOp  resampleOp = new ResampleOp ( xScale, yScale );
//        resampleOp.setFilter( ResampleFilters.getBiCubicHighFreqResponse() );
//        BufferedImage rescaledImage = resampleOp.filter(image, null);

        // SCALE by Multi (casi igual que G2D)
//        MultiStepRescaleOp resampleOp = new MultiStepRescaleOp( xScale, yScale,
//        RenderingHints.VALUE_INTERPOLATION_BICUBIC );
//        BufferedImage rescaledImage = resampleOp.filter(image, null);

        // SCALE by G2D
        BufferedImage rescaledImage = new BufferedImage( xScale, yScale, BufferedImage.TYPE_INT_RGB );
        Graphics2D g = rescaledImage.createGraphics();

        // Rendering Pixels
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
//        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//        g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
//        g.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_DISABLE);
//        g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
//        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
//        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
//        g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);

        g.drawImage(image, 0, 0, xScale, yScale, null);
        g.dispose();

        return rescaledImage;
    }


}
