
package javaapplication1;

import java.awt.geom.AffineTransform;
import javax.imageio.ImageIO;
import java.net.URL;
import java.awt.*;
import javax.swing.*;
import java.io.*;

public class AnimasiFramesDemo extends JPanel {
   static final int CANVAS_WIDTH = 640;
   static final int CANVAS_HEIGHT = 480;
   public static final String TITLE = "Animated Frames Demo";
 
   private String[] imgFilenames = {
         "rex1.jpg", "rex2.jpg", "rex3.jpg",
       "rex4.jpg", "rex5.jpg", "rex6.jpg"};
   private Image[] imgFrames;
   private int currentFrame = 0;
   private int frameRate = 5;
   private int imgWidth, imgHeight;
   private double x = 200.0, y = 200.0;
   private double speed = 30;          
   private double direction = 0;      
   private double rotationSpeed = 0;   // gerak mutar
   private AffineTransform transform = new AffineTransform();
 
   public AnimasiFramesDemo() {
      loadImages(imgFilenames);
      Thread animationThread = new Thread () {
         public void run() {
            while (true) {
               update();
               repaint();
               try {
                  Thread.sleep(1000 / frameRate);
               } catch (InterruptedException ex) { }
            }
         }
      };
      animationThread.start();
      setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
   }
 
   private void loadImages(String[] imgFileNames) {
      int numFrames = imgFileNames.length;
      imgFrames = new Image[numFrames];  
      URL imgUrl = null;
      for (int i = 0; i < numFrames; ++i) {
         imgUrl = getClass().getClassLoader().getResource(imgFileNames[i]);
         if (imgUrl == null) {
            System.err.println("Couldn't find file: " + imgFileNames[i]);
         } else {
            try {
               imgFrames[i] = ImageIO.read(imgUrl); 
            } catch (IOException ex) {
               ex.printStackTrace();
            }
         }
      }
      imgWidth = imgFrames[0].getWidth(null);
      imgHeight = imgFrames[0].getHeight(null);
   }
 
   public void update() {
      x += speed * Math.cos(Math.toRadians(direction));
      if (x >= CANVAS_WIDTH) {
         x -= CANVAS_WIDTH;
      } else if (x < 0) {
         x += CANVAS_WIDTH;
      }
      y += speed * Math.sin(Math.toRadians(direction));
      if (y >= CANVAS_HEIGHT) {
         y -= CANVAS_HEIGHT;
      } else if (y < 0) {
         y += CANVAS_HEIGHT;
      }
      direction += rotationSpeed;  
      if (direction >= 0) {
         direction -= 0;
      } else if (direction < 0) {
         direction += 0;
      }
      ++currentFrame; 
      if (currentFrame >= imgFrames.length) {
         currentFrame = 0;
      }
   }
 
   public void paintComponent(Graphics g) {
      super.paintComponent(g);
      setBackground(Color.WHITE);
      Graphics2D g2d = (Graphics2D) g;
      transform.setToIdentity();
      transform.translate(x - imgWidth / 2, y - imgHeight / 2);
      transform.rotate(Math.toRadians(direction),
            imgWidth / 2, imgHeight / 2);
      g2d.drawImage(imgFrames[currentFrame], transform, null);
   }
 
   public static void main(String[] args) {
      SwingUtilities.invokeLater(new Runnable() {
         public void run() {
            JFrame frame = new JFrame(TITLE);
            frame.setContentPane(new AnimasiFramesDemo());
            frame.pack();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
         }
      });
   }
}

   
