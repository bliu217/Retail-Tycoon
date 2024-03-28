package ui;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;


// main in-game display
public class MainGameDisplay extends JPanel {
    protected static JPanel textBox;

    // EFFECTS: creates a new main game display with side panel and text box
    public MainGameDisplay() {
        setLayout(null);
        setSize(WIDTH, HEIGHT);
        setBackground(new Color(0x00D1FF));
        initTextBox();
        initSidePanel();
    }

    // MODIFIES: textBox
    // EFFECTS: initializes textbox
    private void initTextBox() {
        textBox = new JPanel(new GridLayout(6, 1));
        textBox.setBounds(731, 722, 1189, 358);
        textBox.setBackground(new Color(0x1E1E1E));
        textBox.setBorder(new EmptyBorder(46, 61, 46, 61));
        this.add(textBox);
    }

    // MODIFIES: textBox
    // EFFECTS: adds text to the textbox
    public static void addText(String s) {
        JLabel label = new JLabel(s);
        label.setFont(CashierGame.SOMETYPEMONO_BOLD.deriveFont(32f));
        label.setForeground(Color.white);
        textBox.add(label);
    }

    // MODIFIES: this
    // EFFECTS: initializes the side panel
    protected void initSidePanel() {
        JPanel panel1 = new JPanel();
        panel1.setSize(731, 1080);
        panel1.setBackground(StartScreenDisplay.background);
        JPanel panel2 = new JPanel();
        panel2.setBounds(0, 87, 731, 906);
        panel2.setBackground(new Color(0x43A0FF));
        this.add(panel2);
        this.add(panel1);
    }





}
