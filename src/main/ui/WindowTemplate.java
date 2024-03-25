package ui;

import model.Cashier;

import javax.swing.*;
import java.awt.*;

public class WindowTemplate extends JPanel {

    protected static JPanel backButton;
    protected Cashier cashier;

    public WindowTemplate(Color c, String s, Cashier cashier) {
        this.cashier = cashier;
        setLayout(null);
        setSize(StartScreenDisplay.WIDTH, StartScreenDisplay.HEIGHT);
        setBackground(new Color(0x00D1FF));
        backButton();
        header(c, s);
        HighscoreDisplay.initFooter();
//        this.add(HighscoreDisplay.footer);
        initPanel();

    }

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


    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private void header(Color c, String s) {
        JPanel panel = new JPanel() {
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
        panel.setOpaque(false);
        panel.setBounds(671, 165, 577, 112);
        panel.setBackground(c);
//        panel.setLayout(new BorderLayout());
        JLabel label = new JLabel(s);
        label.setFont(CashierGame.SOMETYPEMONO_BOLD.deriveFont(72f));
        label.setForeground(Color.white);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        panel.add(label);
        this.add(panel);

    }

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
