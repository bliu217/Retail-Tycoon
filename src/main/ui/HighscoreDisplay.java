package ui;

import model.Highscores;

import javax.swing.*;
import java.awt.*;

// creates new display of saved highscores
public class HighscoreDisplay extends JPanel {
    protected static JPanel scoreBody;
    protected static JPanel footer;

    // EFFECTS: creates new highscore display saved from file
    public HighscoreDisplay() {
        setLayout(null);
        setSize(StartScreenDisplay.WIDTH, StartScreenDisplay.HEIGHT);
        setBackground(StartScreenDisplay.background);
        init();
        initBody();
        initFooter();
        this.add(footer);
    }

    // MODIFIES: this
    // EFFECTS: initializes high score header
    private void init() {
        JPanel header = StartScreenDisplay.initPanel("HIGH SCORES", 653, 145, 613, 109, Color.white,
                CashierGame.SOMETYPEMONO_BOLD, 95);
        this.add(header);
    }

    // MODIFIES: this
    // EFFECTS: initializes scoreBody to hold highscores
    private void initBody() {
        scoreBody = new JPanel();
        scoreBody.setBounds(514, 392, 865, 488);
        scoreBody.setOpaque(false);
        this.add(scoreBody);
        scoreBody.setLayout(new GridLayout(10, 1));
    }

    // MODIFIES: this
    // EFFECTS: creates footer that prompts user to return to main menu
    protected static void initFooter() {
        footer = new JPanel();
        footer.setBounds(0, 1000, StartScreenDisplay.WIDTH, 40);
        footer.setOpaque(true);
        footer.setBackground(Color.white);
        JLabel label = new JLabel("press any key to return to menu");
        label.setFont(CashierGame.SOMETYPEMONO.deriveFont(24f));
        label.setForeground(StartScreenDisplay.background);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        footer.add(label);

    }

}
