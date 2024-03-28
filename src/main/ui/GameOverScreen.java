package ui;

import model.Cashier;

import javax.swing.*;
import java.awt.*;

// creates a gameover screen
public class GameOverScreen extends JPanel {

    protected static JPanel menuButton;

    // EFFECTS: creates a new game over screen to display cashier final score
    public GameOverScreen(Cashier cashier) {
        setBackground(new Color(0x143F6B));
        setLayout(null);
        JLabel gameOver = new JLabel("GAME OVER!");
        gameOver.setBounds(774, 341, 372, 80);
        gameOver.setForeground(new Color(0xF55353));
        gameOver.setFont(CashierGame.SOMETYPEMONO_BOLD.deriveFont(64f));
        this.add(gameOver);
        score(cashier);
        newButton();

    }

    // MODIFIES: this
    // EFFECTS: makes the return to menu button
    private void newButton() {
        menuButton =  new JPanel();
        menuButton.setLayout(new GridLayout(1, 1));
        menuButton.setBounds(830, 737, 260, 40);
        menuButton.setOpaque(false);
        JLabel label = new JLabel("return to menu");
        label.setForeground(Color.white);
        label.setFont(CashierGame.SOMETYPEMONO.deriveFont(31f));
        menuButton.add(label);
        this.add(menuButton);
    }

    // MODIFIES: this
    // EFFECTS: displays cashiers score
    private void score(Cashier cashier) {
        JPanel panel = new JPanel();
        panel.setBounds(720, 421, 479, 60);
        panel.setLayout(new GridLayout(1, 1));
        JLabel score = new JLabel("SCORE: " + cashier.getScore());
        score.setFont(CashierGame.SOMETYPEMONO.deriveFont(48f));
        score.setForeground(new Color(0xFEB139));
        score.setVerticalAlignment(SwingConstants.CENTER);
        score.setHorizontalAlignment(SwingConstants.CENTER);
        panel.setOpaque(false);
        panel.add(score);
        this.add(panel, 0);
    }


}
