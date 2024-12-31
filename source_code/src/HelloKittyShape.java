import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import javax.swing.ImageIcon;
import java.awt.Rectangle;

/**
 * HelloKittyShape represents the Hello Kitty character in the RPSKL game.
 *
 * @author Nikola Desnica (ndd2131)
 */
public class HelloKittyShape implements CharacterShape {

    /**
     * Constructor for HelloKittyShape.
     *
     * @param position The initial position of the Rock.
     */
    public HelloKittyShape(Point position) {
        this.position = position;
        this.image = new ImageIcon("hello_kitty.png").getImage();
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(image, position.x, position.y, STANDARD_WIDTH, STANDARD_HEIGHT, null);
    }

    @Override
    public void move() {
        // Hello Kitty does not move because she is sitting down
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(position.x, position.y, STANDARD_WIDTH, STANDARD_HEIGHT);
    }

    @Override
    public String getType() {
        return "hello kitty";
    }

    private Point position;
    private Image image;
    private static final int STANDARD_WIDTH = 75;
    private static final int STANDARD_HEIGHT = 95;
}