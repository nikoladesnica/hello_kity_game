import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * Interface for character shapes in the RPSKL game.
 *
 * @author Nikola Desnica (ndd2131)
 */
public interface CharacterShape {

    /**
     * Draws the character shape.
     *
     * @param g2 The Graphics2D object used for drawing.
     */
    void draw(Graphics2D g2);

    /**
     * Moves the character shape.
     */
    void move();

    /**
     * Gets the bounds of each shape to calculate collisions.
     *
     * @return The bounds of the respective shape.
     */
    Rectangle getBounds();

    /**
     * Gets the type of the character shape.
     *
     * @return The type of the character as a String.
     */
    String getType();
}