package ui;

import model.Cashier;
import model.Item;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

// creates a screen to display cashier's inventory
public class InventoryDisplay extends WindowTemplate {
    protected static JPanel inventoryBox;

    // EFFECTS: an inventory display for given cashier
    public InventoryDisplay(Cashier cashier) {
        super(new Color(0xAE53F5), "INVENTORY", cashier);
        initInventoryBox();
        this.add(inventoryBox, 0);
        if (cashier.getInventory().size() == 0) {
            emptyMessage();
        }
    }

    // MODIFIES: inventoryBox
    // EFFECTS: creates holder for the inventory panels
    private void initInventoryBox() {
        inventoryBox = new JPanel();
        inventoryBox.setBounds(320,316, 1280, 492);
        inventoryBox.setOpaque(false);
        GridLayout layout = new GridLayout(7, 2);
        layout.setHgap(232);
        layout.setVgap(12);
        inventoryBox.setLayout(layout);
    }

    // MODIFIES: inventoryBox
    // EFFECTS: adds item to the holder
    public static void addItem(Item i) {
        JPanel panel = itemPanel();
        int quantity = cashier.itemFrequency(i);

        JLabel qty = itemText("x" + quantity);
        panel.add(qty, BorderLayout.LINE_END);

        JLabel name = itemText(i.getName());
        panel.add(name, BorderLayout.LINE_START);
        inventoryBox.add(panel);

    }


    // EFFECTS: helper method to create label with text
    private static JLabel itemText(String text) {
        JLabel label = new JLabel(text);
        label.setFont(CashierGame.SOMETYPEMONO_BOLD.deriveFont(36f));
        label.setForeground(Color.white);
        label.setVerticalAlignment(SwingConstants.CENTER);
        return label;
    }

    // EFFECTS: creates a display panel to hold an item with name and quantity
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

    // MODIFIES: this
    // EFFECTS: adds messages to screen if inventory is empty
    private void emptyMessage() {
        JLabel label = new JLabel("it's empty in here...");
        label.setForeground(new Color(255,255,255,100));
        label.setFont(CashierGame.SOMETYPEMONO.deriveFont(23f));
        label.setBounds(820, 548, 285, 30);
        this.add(label, 0);
    }
}
