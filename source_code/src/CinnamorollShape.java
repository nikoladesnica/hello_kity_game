import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import javax.swing.ImageIcon;
import java.awt.Rectangle;

/**
 * CinnamorollShape represents the Spock character in the RPSKL game.
 *
 * @author Nikola Desnica (ndd2131)
 */
public class CinnamorollShape implements CharacterShape {

    /**
     * Constructor for CinnamorollShape.
     *
     * @param position The initial position of the Spock.
     */
    public CinnamorollShape(Point position) {
        this.position = position;
        this.image = new ImageIcon("cinnamoroll.png").getImage();
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(image, position.x, position.y, STANDARD_WIDTH, STANDARD_HEIGHT, null);
    }

    @Override
    public void move() {
        // Cinnamoroll flies upwards
        position.y -= MOVEMENT_SPEED;
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(position.x, position.y, STANDARD_WIDTH, STANDARD_HEIGHT);
    }

    @Override
    public String getType() {
        return "cinnamoroll";
    }

    private Point position;
    private Image image;
    private static final int STANDARD_WIDTH = 150;
    private static final int STANDARD_HEIGHT = 120;
    private static final int MOVEMENT_SPEED = 3;
}