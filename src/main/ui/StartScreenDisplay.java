package ui;

import javax.swing.*;
import java.awt.*;

public class StartScreenDisplay extends JPanel {
    protected static final int WIDTH = 1920;
    protected static final int HEIGHT = 1080;
    protected static final Color background = new Color(0x143F6B);

    protected static JPanel newGame;
    protected static JPanel loadGame;
    protected static JPanel highScores;
    protected static JPanel quit;



    public StartScreenDisplay() {
        setLayout(null);
        setSize(WIDTH, HEIGHT);
        setBackground(background);
        startDisplay();
    }


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
}
