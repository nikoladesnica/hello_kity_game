import javax.swing.*;
import java.awt.*;
import java.io.File;
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import kuusisto.tinysound.Music;
import kuusisto.tinysound.TinySound;
/**
 * MainApplication is the main class for the RPSKL game application. It initializes and runs the game,
 * using VLCJ for video playback. This class sets up the main game window, handles video playback,
 * initializes game components, and manages the game state.
 *
 * @author Nikola Desnica (ndd2131)
 */
public class MainApplication extends JFrame {
    private static final int FRAME_WIDTH = 800;
    private static final int FRAME_HEIGHT = 600;
    private BattlefieldComponent battlefieldComponent;
    private MouseController mouseController;
    private int characterSpawnCount = 0;
    private int jumpScareThreshold = (int) (Math.random() * 50);
    private EmbeddedMediaPlayerComponent mediaPlayerComponent;
    private Music backgroundMusic;
    public int firstJumpScare = 3;

    /**
     * The main method that launches the application.
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainApplication app = new MainApplication();
            app.playIntroVideo("intro_video.mp4");
        });
    }

    /**
     * Constructor for MainApplication. Initializes the TinySound library, loads background music,
     * sets up the frame size and default close operation, and initializes game components.
     */
    public MainApplication() {
        // Initialize components
        TinySound.init();
        try {
            this.backgroundMusic = TinySound.loadMusic(new File("background_music.wav"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initializeComponents();
        this.setVisible(true);
    }

    /**
     * Plays the introductory video at the start of the game.
     * @param videoPath The file path to the intro video.
     */
    private void playIntroVideo(String videoPath) {
        JDialog videoDialog = createVideoDialog();
        mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
        videoDialog.add(mediaPlayerComponent);
        videoDialog.setVisible(true);

        MediaPlayer mediaPlayer = mediaPlayerComponent.mediaPlayer();
        mediaPlayer.media().start(videoPath);

        mediaPlayer.events().addMediaPlayerEventListener(new MediaPlayerEventAdapter() {
            @Override
            public void finished(MediaPlayer mediaPlayer) {
                SwingUtilities.invokeLater(() -> {
                    mediaPlayer.controls().stop();
                    videoDialog.dispose();
                    startBackgroundMusic();
                });
            }
        });
    }

    /**
     * Creates a JDialog configured for video playback. This dialog is used for playing videos in full screen.
     * @return The configured JDialog for video playback.
     */
    private JDialog createVideoDialog() {
        JDialog dialog = new JDialog(this, "Video Playback", Dialog.ModalityType.MODELESS);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        if (gd.isFullScreenSupported()) {
            dialog.setUndecorated(true);
            gd.setFullScreenWindow(dialog);
        } else {
            System.err.println("Full screen not supported");
            dialog.setSize(FRAME_WIDTH, FRAME_HEIGHT); // Fallback to windowed mode
        }

        dialog.setLocationRelativeTo(this);
        return dialog;
    }

    /**
     * Starts playing the background music in a loop.
     */
    private void startBackgroundMusic() {
        if (backgroundMusic != null) {
            backgroundMusic.play(true); // Play in loop
        }
    }

    /**
     * Stops the background music.
     */
    private void stopBackgroundMusic() {
        if (backgroundMusic != null) {
            backgroundMusic.stop();
        }
    }

    /**
     * Performs necessary cleanup operations. Shuts down the TinySound library and disposes the JFrame.
     */
    @Override
    public void dispose() {
        TinySound.shutdown();
        super.dispose();
    }

    /**
     * Initializes the main components of the application. Sets up the main frame, adds the battlefield component,
     * and initializes the button panel and mouse controller.
     */
    private void initializeComponents() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;

        this.setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize window if full screen is not available

        this.setSize(width, height); // Set size to screen dimensions as a fallback

        battlefieldComponent = new BattlefieldComponent();
        this.add(battlefieldComponent);

        JPanel buttonPanel = createButtonPanel();
        this.add(buttonPanel, BorderLayout.NORTH);

        mouseController = new MouseController(battlefieldComponent, this);
        battlefieldComponent.addMouseListener(mouseController);

        this.setVisible(true);
    }

    /**
     * Creates and configures a panel with buttons for character selection.
     * @return A JPanel containing character selection buttons.
     */
    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        String[] types = {"Hello Kitty", "My Melody", "Badtz Maru", "Cinnamoroll", "Kuromi"};
        for (String type : types) {
            JButton button = new JButton(type);
            button.addActionListener(e -> createAndAddRandomCharacter(type));
            buttonPanel.add(button);
        }
        return buttonPanel;
    }

    /**
     * Creates and adds a random character to the game based on the selected type.
     * @param type The type of character to create.
     */
    private void createAndAddRandomCharacter(String type) {
        Point randomPoint = getRandomPointInBattlefield();
        mouseController.createAndAddCharacter(randomPoint, type);
    }

    /**
     * Generates a random point within the bounds of the battlefield component.
     * @return A randomly generated Point within the battlefield.
     */
    private Point getRandomPointInBattlefield() {
        int x = (int) (Math.random() * battlefieldComponent.getWidth());
        int y = (int) (Math.random() * battlefieldComponent.getHeight());
        return new Point(x, y);
    }


    /**
     * Checks the game state for a jump scare condition. If the condition is met,
     * it stops the background music and plays one of three random videos, resetting the count.
     * This functionality is continuous (random indefinitely until program is closed).
     */
    public void checkForJumpScare() {
        characterSpawnCount++;
        if (characterSpawnCount == jumpScareThreshold) {
            stopBackgroundMusic();
            int videoChoice = (int) (Math.random() * 8);
            switch (videoChoice) {
                case 0:
                    playJumpScareVideo("scary_video.mp4");
                    break;
                case 1: playJumpScareVideo("pikachu_cat_video.mp4");
                    break;
                case 2:
                    playJumpScareVideo("jake_laugh_video.mp4");
                    break;
                case 3:
                    playJumpScareVideo("minions_video.mp4");
                    break;
                case 4:
                    playJumpScareVideo("peter_griffin_video.mp4");
                    break;
                case 5:
                    playJumpScareVideo("power_puff_video.mp4");
                    break;
                case 6:
                    playJumpScareVideo("rick_video.mp4");
                    break;
                case 7:
                    playJumpScareVideo("marko_video.mp4");
            }
            characterSpawnCount = 0;
            jumpScareThreshold = (int) (Math.random() * 50);
        }
    }

    /**
     * Plays a jump scare video. This method is triggered when the jump scare condition is met.
     * @param videoPath The file path to the jump scare video.
     */
    private void playJumpScareVideo(String videoPath) {
        JDialog videoDialog = createVideoDialog();
        mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
        videoDialog.add(mediaPlayerComponent);
        videoDialog.setVisible(true);

        MediaPlayer mediaPlayer = mediaPlayerComponent.mediaPlayer();
        mediaPlayer.media().play(new File(videoPath).getAbsolutePath());

        mediaPlayer.events().addMediaPlayerEventListener(new MediaPlayerEventAdapter() {
            @Override
            public void finished(MediaPlayer mediaPlayer) {
                SwingUtilities.invokeLater(() -> {
                    mediaPlayer.controls().stop();
                    videoDialog.dispose();
                    startBackgroundMusic();
                });
            }
        });
    }
}
