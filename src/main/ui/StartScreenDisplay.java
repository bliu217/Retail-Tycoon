package ui;

import javax.swing.*;
import java.awt.*;

// start display for when the game is launched
public class StartScreenDisplay extends JPanel {
    protected static final int WIDTH = 1920;
    protected static final int HEIGHT = 1080;
    protected static final Color background = new Color(0x143F6B);

    protected static JPanel newGame;
    protected static JPanel loadGame;
    protected static JPanel highScores;
    protected static JPanel quit;
    protected static JLabel error;

    // EFFECTS: creates display for application launch with options and logo
    public StartScreenDisplay() {
        setLayout(null);
        setSize(WIDTH, HEIGHT);
        setBackground(background);
        startDisplay();
        copyright();
        errorMessage();
    }

    // MODIFIES: this
    // EFFECTS: adds option panels and logo to screen
    private void startDisplay() {
        newGame = initPanel("New Game", 848, 681, 223, 60, new Color(0xF55353),
                CashierGame.SOMETYPEMONO_BOLD, 48);
        loadGame = initPanel("load game", 875, 764, 168, 40, Color.white,
                CashierGame.SOMETYPEMONO, 31);
        highScores = initPanel("high scores", 857, 828, 205, 40, Color.white,
                CashierGame.SOMETYPEMONO, 31);
        quit = initPanel("quit", 922, 892, 75, 40, Color.white,
                CashierGame.SOMETYPEMONO, 31);
        this.add(newGame);
        this.add(loadGame);
        this.add(highScores);
        this.add(quit);
        ImageIcon big = new ImageIcon("bigLogo.png");
        JLabel bigLogo = new JLabel(big);
        bigLogo.setLocation(447, 284);
        bigLogo.setSize(1024, 386);
        this.add(bigLogo);
    }

    // MODIFIES: this
    // EFFECTS: returns a panel with JLabel given text, x, y, width, height, color, font, and font size
    public static JPanel initPanel(String s, int x, int y, int width, int height, Color c, Font f, float fs) {
        JPanel panel = new JPanel();
        panel.setBounds(x, y, width, height);
        JLabel label = new JLabel(s);
        label.setForeground(c);
        label.setFont(f.deriveFont(fs));
        panel.setLayout(new GridBagLayout());
        panel.setOpaque(false);
        panel.add(label);
        return panel;
    }

    // MODIFIES: this
    // EFFECTS: copyright logo at bottom of screen
    private void copyright() {
        ImageIcon copyright = new ImageIcon("copyright.png");
        JLabel label = new JLabel(copyright);
        label.setBounds(863, 1023, 193, 26);
        this.add(label);
    }

    // MODIFIES: this
    // EFFECTS: error message for no previous save
    private void errorMessage() {
        error = new JLabel("error: no save found");
        error.setFont(CashierGame.SOMETYPEMONO.deriveFont(24f));
        error.setBounds(820,961,280,30);
        error.setForeground(new Color(0xF55353));
        error.setVisible(false);
        this.add(error, 0);
    }
}
