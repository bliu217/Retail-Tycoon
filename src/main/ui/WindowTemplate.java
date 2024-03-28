package ui;

import model.Cashier;

import javax.swing.*;
import java.awt.*;

// template for inventory and store displays
public class WindowTemplate extends JPanel {

    protected static JPanel backButton;
    protected static Cashier cashier;
    private JPanel header;

    // EFFECTS: creates a window with given header color and text, cashier for information access
    public WindowTemplate(Color c, String s, Cashier cashier) {
        this.cashier = cashier;
        setLayout(null);
        setSize(StartScreenDisplay.WIDTH, StartScreenDisplay.HEIGHT);
        setBackground(new Color(0x00D1FF));
        backButton();
        header(c, s);
        initPanel();

    }

    // MODIFIES: this
    // EFFECTS: creates background panel
    protected void initPanel() {
        JPanel panel = new JPanel();
        panel.setBounds(204, 129,1512, 821);
        panel.setBackground(StartScreenDisplay.background);

        JPanel panel1 = new JPanel();
        panel1.setBounds(222, 221, 1476, 637);
        panel1.setBackground(new Color(0x43A0FF));
        this.add(panel1);
        this.add(panel);

    }

    // MODIFIES: this
    // EFFECTS: creates header with given color and text
    private void header(Color c, String s) {
        header = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                Graphics2D g2d = (Graphics2D) g.create();
                int width = getWidth();
                int height = getHeight();
                g2d.setColor(getBackground());
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.fillRoundRect(0, 0, width, height, 88, 88);
                g2d.setColor(new Color(0, 0, 0, 0));
                g2d.drawRoundRect(0, 0, width - 1, height - 1, 88, 88);
                g2d.dispose();
            }
        };
        header.setOpaque(false);
        header.setBounds(671, 165, 577, 112);
        header.setBackground(c);
        headerText(s);
    }

    // MODIFIES: this
    // EFFECTS: adds text to header
    private void headerText(String s) {
        JLabel label = new JLabel(s);
        label.setFont(CashierGame.SOMETYPEMONO_BOLD.deriveFont(72f));
        label.setForeground(Color.white);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        header.add(label);
        this.add(header);
    }

    // MODIFIES: this
    // EFFECTS: creates a back button
    private void backButton() {
        backButton = new JPanel();
        backButton.setOpaque(false);
        backButton.setBounds(1623, 156, 75, 40);
        JLabel label = new JLabel("BACK");
        label.setFont(CashierGame.SOMETYPEMONO_BOLD.deriveFont(32f));
        label.setForeground(Color.white);
        backButton.add(label);
        this.add(backButton);
    }
}
