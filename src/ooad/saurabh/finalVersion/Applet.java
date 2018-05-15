package ooad.saurabh.finalVersion;

import javax.swing.JApplet;

/**
 * Nested applet class that can be used to run Checkers as an
 * applet.  Size of the applet should be 350-by-250
 */
public class Applet extends JApplet {
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void init() {
	      setContentPane( new Checkers() );
	   }
}//end of class Applet

