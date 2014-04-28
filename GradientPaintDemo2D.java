package gameOfLife;

import java.awt.BasicStroke;



import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JApplet;
import javax.swing.JFrame;

public class GradientPaintDemo2D extends JApplet {

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	int x = 130, y = 130;
	  int diameter = 150;
	  Ellipse2D.Double circle0 = new Ellipse2D.Double(x, y, diameter, diameter);
	  
	  static int count = 0;

public void initt() {
    setBackground(Color.white);
    setForeground(Color.white);
  }

  /* public void paint(Graphics g) {
    Graphics2D g2 = (Graphics2D) g;
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON);

    g2.setPaint(Color.gray);
    int x = 5;
    int y = 7;
    // fill RoundRectangle2D.Double
    GradientPaint redtowhite = new GradientPaint(x, y, Color.red, 200, y,
        Color.blue);
    g2.setPaint(redtowhite);
    g2.fill(new RoundRectangle2D.Double(x, y, 200, 200, 10, 10));
    g2.setPaint(Color.black);
    g2.drawString("Filled RoundRectangle2D", x, 250);

  } */
  
  public void paint(Graphics g) {         // Dynamically calculate size information    
	  
	  Graphics2D g2d = (Graphics2D) g;

	  if (count % 3 == 0) {
		  g2d.setColor(Color.RED);
	  } else if (count % 3 == 1) {
		  g2d.setColor(Color.BLUE);
	  } else {
		  g2d.setColor(Color.GREEN);
	  }
	  g2d.fill(circle0);
	  
  }
  
  /*public void paintComponent(Graphics g) {
	  Graphics2D g2d = (Graphics2D) g;
	  g2d.setColor(Color.BLUE);
	  g2d.fill(circle0);
  } */
	                
  

  public static void main(String s[]) throws InterruptedException {
    JFrame f = new JFrame("");
    f.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });
    GradientPaintDemo2D applet = new GradientPaintDemo2D();
    f.getContentPane().add("Center", applet);
    applet.init();
    //f.pack();
    f.setSize(new Dimension(300, 300));
    f.show();
    for (int j = 0; j < 30; j++) {
    	applet.validate();
    	applet.repaint();
    	count += 1;
    	Thread.sleep(500);
    	double x = Math.random();
    	System.out.println(x);
    }
  }
}
