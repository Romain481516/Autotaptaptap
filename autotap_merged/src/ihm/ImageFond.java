package ihm;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ImageFond extends JPanel{

	    private BufferedImage image;

	    public ImageFond() {
	       try {                
	          image = ImageIO.read(new File("fondfenjeu.jpg"));
	          System.out.println("image trouvee");
	       } catch (IOException ex) {
	    	   System.out.println("image non trouvee");
	       }
	    }

	    protected void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        g.drawImage(image, 0, 0, null);            
	    }

	}