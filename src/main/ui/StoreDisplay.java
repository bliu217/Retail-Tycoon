package ui;

import model.Cashier;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class StoreDisplay extends WindowTemplate {
    protected static JPanel storePanel;
    protected static JPanel purchaseButton;
    private JPanel whitePanel;


    public StoreDisplay(Cashier cashier) {
        super(new Color(0xF55353), "STORE", cashier);
        genWhiteBox();
        purchaseButton();
        purchasePanel();
        balance();
        initStore();
    }

    private void initStore() {
        storePanel = new JPanel();
        storePanel.setBounds(290, 400, 1339, 282);
        GridLayout layout = new GridLayout(4,2);
        layout.setHgap(165);
        layout.setVgap(30);
        storePanel.setLayout(layout);
        storePanel.setOpaque(false);
        this.add(storePanel, 0);
    }

    private void balance() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBounds(1507, 240, 172, 60);
        panel.setBackground(new Color(0,0, 0, 75));
        JLabel label = new JLabel("$" + Integer.toString(cashier.getBalance()));
        label.setForeground(Color.white);
        label.setFont(CashierGame.SOMETYPEMONO_BOLD.deriveFont(40f));
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(label, BorderLayout.CENTER);
        this.add(panel, 0);

    }

    private void purchasePanel() {
        JPanel bluePanel = new JPanel();
        bluePanel.setBackground(new Color(0x143F6B));
        bluePanel.setBounds(829, 752,869, 106);
        this.add(bluePanel, 0);

        JPanel redPanel = new JPanel();
        redPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        redPanel.add(Box.createHorizontalStrut(29));
        redPanel.setBounds(849, 774, 849, 84);
        redPanel.setBackground(new Color(0xF55353));
        JLabel label = new JLabel("TOTAL:");
        label.setFont(CashierGame.SOMETYPEMONO_BOLD.deriveFont(40f));
        label.setForeground(Color.white);
        label.setVerticalAlignment(SwingConstants.CENTER);
        redPanel.add(label);
        redPanel.add(Box.createHorizontalStrut(17));
        redPanel.add(whitePanel);
        redPanel.add(Box.createHorizontalStrut(34));
        redPanel.add(purchaseButton);
        this.add(redPanel, 0);
    }

    private void genWhiteBox() {
        whitePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                Graphics2D g2d = (Graphics2D) g.create();
                int width = getWidth();
                int height = getHeight();
                g2d.setColor(getBackground());
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.fillRoundRect(0, 0, width, height, 11, 11);
                g2d.setColor(new Color(0, 0, 0, 0));
                g2d.drawRoundRect(0, 0, width - 1, height - 1, 11, 11);
                g2d.dispose();
            }
        };
        whitePanel.setPreferredSize(new Dimension(288, 84));
        whitePanel.setBackground(Color.white);
        whitePanel.setOpaque(false);

    }



    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private void purchaseButton() {
        purchaseButton = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                Graphics2D g2d = (Graphics2D) g.create();
                int width = getWidth();
                int height = getHeight();
                g2d.setColor(getBackground());
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.fillRoundRect(0, 0, width, height, 50, 50);
                g2d.setColor(new Color(0, 0, 0, 0));
                g2d.drawRoundRect(0, 0, width - 1, height - 1, 50, 50);
                g2d.dispose();
            }
        };
        purchaseButton.setBackground(new Color(0xFEB139));

        purchaseButton.setPreferredSize(new Dimension(308, 56));
        purchaseButton.setOpaque(false);
        JLabel label = new JLabel("PURCHASE");
        label.setForeground(Color.white);
        label.setFont(CashierGame.SOMETYPEMONO_BOLD.deriveFont(40f));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        purchaseButton.add(label);
    }

}
