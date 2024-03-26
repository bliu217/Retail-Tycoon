package ui;

import model.Cashier;
import model.Item;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class InventoryDisplay extends WindowTemplate {
    protected static JPanel inventoryBox;

    public InventoryDisplay(Cashier cashier) {
        super(new Color(0xAE53F5), "INVENTORY", cashier);
        initInventoryBox();
        this.add(inventoryBox, 0);
    }

    private void initInventoryBox() {
        inventoryBox = new JPanel();
        inventoryBox.setBounds(320,316, 1280, 492);
        inventoryBox.setOpaque(false);
        GridLayout layout = new GridLayout(7, 2);
        layout.setHgap(232);
        layout.setVgap(12);
        inventoryBox.setLayout(layout);
    }

    public static void addItem(Item i) {
        JPanel panel = itemPanel();
        int quantity = cashier.itemFrequency(i);

        JLabel qty = itemText("x" + quantity);
        panel.add(qty, BorderLayout.LINE_END);

        JLabel name = itemText(i.getName());
        panel.add(name, BorderLayout.LINE_START);
        inventoryBox.add(panel);

    }

    private static JLabel itemText(String text) {
        JLabel label = new JLabel(text);
        label.setFont(CashierGame.SOMETYPEMONO_BOLD.deriveFont(36f));
        label.setForeground(Color.white);
        label.setVerticalAlignment(SwingConstants.CENTER);
        return label;
    }

    private static JPanel itemPanel() {
        JPanel panel = new JPanel() {
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
        panel.setOpaque(false);
        panel.setBackground(new Color(0, 0, 0, 50));
        panel.setPreferredSize(new Dimension(524, 60));
        panel.setLayout(new BorderLayout(0, 0));
        panel.setBorder(new EmptyBorder(0, 27, 0, 27));
        return panel;
    }
}
