package ui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

// a display for when users choose to start a new game
public class NewGameDisplay extends JPanel {
    protected static JTextField textField;
    protected static JPanel errorMessage;


    // EFFECTS: creates a new game display that prompts user for their save name
    public NewGameDisplay() {
        setLayout(null);
        setSize(WIDTH, HEIGHT);
        setBackground(StartScreenDisplay.background);
        initMessage();
        initNameLine();
        initTextBox();
        newErrorMessage();
    }

    // MODIFIES: this
    // EFFECTS: creates user prompt message
    private void initMessage() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2,1));
        panel.setBounds(343, 458, 1233, 100);
        JLabel label = new JLabel("Welcome to the start of your multi-million dollar business!");
        label.setForeground(Color.white);
        label.setFont(CashierGame.SOMETYPEMONO.deriveFont(32f));
        label.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel label2 = new JLabel("What's your name?");
        label2.setForeground(new Color(0xF55353));
        label2.setFont(CashierGame.SOMETYPEMONO_BOLD.deriveFont(32f));
        label2.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(label);
        panel.add(label2);
        panel.setOpaque(false);
        this.add(panel);
    }

    // MODIFIES: this
    // EFFECTS: creates white line
    private void initNameLine() {
        JPanel line = new JPanel();
        line.setBounds(673, 671, 574,3);
        this.add(line);
    }

    // MODIFIES: this
    // EFFECTS: creates a text field for user to input name
    private void initTextBox() {
        textField = new JTextField() {
            @Override public void setBorder(Border border) {
            }
        };
        textField.setCaretColor(Color.white);
        textField.setBounds(673, 621, 574, 40);
        textField.setOpaque(false);
        textField.setFont(CashierGame.SOMETYPEMONO_BOLD.deriveFont(32f));
        textField.setForeground(Color.white);
        this.add(textField);

    }

    // MODIFIES: this
    // EFFECTS: creates error message to indicate that an incorrect name has been entered
    private void newErrorMessage() {
        errorMessage = new JPanel();
        errorMessage.setOpaque(false);
        errorMessage.setBounds(674, 733, 571, 30);
        JLabel label = new JLabel("NAME MUST BE 1 - 20 CHARACTERS LONG");
        label.setFont(CashierGame.SOMETYPEMONO_BOLDITALIC.deriveFont(24f));
        label.setForeground(Color.white);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        errorMessage.add(label);
        errorMessage.setVisible(false);
        this.add(errorMessage);
    }
}
