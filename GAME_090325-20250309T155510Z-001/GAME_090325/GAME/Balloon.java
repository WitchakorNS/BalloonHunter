import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

public class Balloon {
    int x, y;
    String color;
    Image image; 
    
    public Balloon(int x, int y, String color) {
        this.x = x;
        this.y = y;
        this.color = color;

        String imagePath = "images/balloon_" + color.toLowerCase() + ".png";
        java.net.URL imgURL = getClass().getResource(imagePath);

        this.image = new ImageIcon(imgURL).getImage();
    }

    public void draw(Graphics g) {
        g.drawImage(image, x, y, 150, 125, null); //w: 120 h: 100
    }
}