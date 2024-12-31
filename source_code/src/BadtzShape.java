import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import javax.swing.ImageIcon;
import java.awt.Rectangle;

/**
 * ScissorsShape represents the Scissors character in the RPSKL game.
 *
 * @author Nikola Desnica (ndd2131)
 */
public class BadtzShape implements CharacterShape {

    /**
     * Constructor for BadtzShape.
     *
     * @param position The initial position of the BadtzShape.
     */
    public BadtzShape(Point position) {
        this.position = position;
        this.image = new ImageIcon("badtz.png").getImage();
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(image, position.x, position.y, STANDARD_WIDTH, STANDARD_HEIGHT, null);
    }

    @Override
    public void move() {
        // Badtz Maru move from left to right
        position.x += MOVEMENT_SPEED;
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(position.x, position.y, STANDARD_WIDTH, STANDARD_HEIGHT);
    }

    @Override
    public String getType() {
        return "badtz maru";
    }

    private Point position;
    private Image image;
    private static final int STANDARD_WIDTH = 75;
    private static final int STANDARD_HEIGHT = 90;
    private static final int MOVEMENT_SPEED = 3;
}
