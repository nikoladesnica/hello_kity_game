import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Point;
import javax.swing.SwingUtilities;
import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;

/**
 * MouseController handles mouse interactions for the RPSKL game.
 * It allows for the creation of character shapes at specific points on the screen
 * via a right-click context menu.
 */
public class MouseController extends MouseAdapter {
    private final BattlefieldComponent battlefieldComponent;
    private final MainApplication mainApp; // Reference to MainApplication
    private JPopupMenu popupMenu;
    private Point lastClickPoint; // Store the last click point for character creation

    /**
     * Constructor for MouseController.
     * Initializes the controller with a reference to the BattlefieldComponent
     * and MainApplication, and sets up the right-click context menu for character creation.
     *
     * @param battlefieldComponent The BattlefieldComponent this controller is associated with.
     * @param mainApp The MainApplication instance.
     */
    public MouseController(BattlefieldComponent battlefieldComponent, MainApplication mainApp) {
        this.battlefieldComponent = battlefieldComponent;
        this.mainApp = mainApp;
        createPopupMenu();
    }

    /**
     * Creates a popup menu with options to create each type of character shape.
     */
    private void createPopupMenu() {
        popupMenu = new JPopupMenu();
        String[] types = {"Hello Kitty", "My Melody", "Badtz Maru", "Cinnamoroll", "Kuromi"};
        for (String type : types) {
            JMenuItem item = new JMenuItem(type);
            item.addActionListener(e -> createAndAddCharacter(lastClickPoint, type));
            popupMenu.add(item);
        }
    }

    /**
     * Overrides the mouseClicked method to display a popup menu on right-click.
     * This menu allows for the creation of character shapes at the click location.
     *
     * @param e The MouseEvent to process.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if (SwingUtilities.isRightMouseButton(e)) {
            lastClickPoint = e.getPoint();
            popupMenu.show(e.getComponent(), e.getX(), e.getY());
        }
    }

    /**
     * Creates and adds a character shape to the battlefield at the specified point.
     * The type of the character is determined based on the selection from the popup menu.
     * Notifies the MainApplication to check for the jump scare condition.
     *
     * @param point The point on the screen where the character should be created.
     * @param type  The type of the character to create.
     */
    protected void createAndAddCharacter(Point point, String type) {
        CharacterShape character = getCharacterShapeByType(type, point);
        if (character != null) {
            battlefieldComponent.addCharacter(character);
            // Notify MainApplication to check for jump scare
            mainApp.checkForJumpScare();
        }
    }

    /**
     * Returns a new instance of a CharacterShape based on the specified type.
     *
     * @param type  The type of character to create.
     * @param point The location for the character.
     * @return A new CharacterShape instance or null if the type is invalid.
     */
    private CharacterShape getCharacterShapeByType(String type, Point point) {
        switch (type.toLowerCase()) {
            case "hello kitty":
                return new HelloKittyShape(point);
            case "my melody":
                return new MyMelodyShape(point);
            case "badtz maru":
                return new BadtzShape(point);
            case "cinnamoroll":
                return new CinnamorollShape(point);
            case "kuromi":
                return new KuromiShape(point);
            default:
                return null; // Invalid type
        }
    }
}