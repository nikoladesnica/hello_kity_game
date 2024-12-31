import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import javax.swing.ImageIcon;
import java.awt.Rectangle;

/**
 * MyMelodyShape represents the Paper character in the RPSKL game.
 *
 * @author Nikola Desnica (ndd2131)
 */
public class MyMelodyShape implements CharacterShape {

    /**
     * Constructor for MyMelodyShape.
     *
     * @param position The initial position of the Paper.
     */
    public MyMelodyShape(Point position) {
        this.position = position;
        this.image = new ImageIcon("my_melody.png").getImage();
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(image, position.x, position.y, STANDARD_WIDTH, STANDARD_HEIGHT, null);
    }

    @Override
    public void move() {
        // MyMelody moves downwards
        position.y += MOVEMENT_SPEED;
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(position.x, position.y, STANDARD_WIDTH, STANDARD_HEIGHT);
    }

    @Override
    public String getType() {
        return "my melody";
    }

    private Point position;
    private Image image;
    private static final int STANDARD_WIDTH = 80;
    private static final int STANDARD_HEIGHT = 120;
    private static final int MOVEMENT_SPEED = 3;
}