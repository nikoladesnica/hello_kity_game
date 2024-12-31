import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import javax.swing.ImageIcon;
import java.awt.Rectangle;

/**
 * KuromiShape represents the Lizard character in the RPSKL game.
 *
 * @author Nikola Desnica (ndd2131)
 */
public class KuromiShape implements CharacterShape {

    /**
     * Constructor for KuromiShape.
     *
     * @param position The initial position of Kuromi.
     */
    public KuromiShape(Point position) {
        this.position = position;
        this.image = new ImageIcon("kuromi.png").getImage();
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(image, position.x, position.y, STANDARD_WIDTH, STANDARD_HEIGHT, null);
    }

    @Override
    public void move() {
        // Kuromi moves from right to left
        position.x -= MOVEMENT_SPEED;
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(position.x, position.y, STANDARD_WIDTH, STANDARD_HEIGHT);
    }

    @Override
    public String getType() {
        return "kuromi";
    }

    private Point position;
    private Image image;
    private static final int STANDARD_WIDTH = 80;
    private static final int STANDARD_HEIGHT = 100;
    private static final int MOVEMENT_SPEED = 3;
}
