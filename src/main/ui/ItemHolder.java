package ui;

import model.Item;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// an item holder to display item with item name and (optional) price
public class ItemHolder extends JPanel {

    private Item item;
    boolean store;
    private int quantity;
    protected int amount;
    protected JPanel subtractPanel;
    protected JPanel addPanel;

    // MODIFIES: this
    // EFFECTS: creates item holder for given item
    public ItemHolder(Item i, boolean store) {
        this.store = store;
        this.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        this.setSize(587, 48);
        this.setBackground(Color.white);
        this.quantity = 0;
        this.item = i;
        this.amount = 0;
        initHolder();
        actions();


    }

    // MODIFIES: this
    // EFFECTS: initializes the main holder
    private void initHolder() {
        String name = item.getName();

        JLabel label = new JLabel(name);
        label.setForeground(new Color(0x143F6B));
        label.setFont(CashierGame.SOMETYPEMONO_BOLD.deriveFont(32f));
        label.setPreferredSize(new Dimension(239, 40));
        label.setMinimumSize(new Dimension(239, 40));
        label.setMaximumSize(new Dimension(355, 40));
        label.setVerticalAlignment(SwingConstants.CENTER);
        this.add(Box.createHorizontalStrut(11));
        this.add(label);
        costPanel();
        this.subtractPanel = genPanel(new Color(0xF55353), "-");
        qtyPanel();
        this.addPanel = genPanel(new Color(0x6AF553), "+");

    }

    // MODIFIES: this
    // EFFECTS: creates the panel that displays quantity of item
    private void qtyPanel() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(82, 48));
        panel.setBackground(Color.white);

        JLabel qty = new JLabel(Integer.toString(this.quantity));
        qty.setForeground(new Color(0x143F6B));
        qty.setFont(CashierGame.SOMETYPEMONO_BOLD.deriveFont(32f));
        qty.setHorizontalAlignment(SwingConstants.CENTER);
        qty.setVerticalAlignment(SwingConstants.CENTER);
        panel.add(qty);
        this.add(panel);
    }

    // MODIFIES: this
    // EFFECTS: creates a small panel with given color and text
    private JPanel genPanel(Color c, String s) {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(64, 48));
        panel.setBackground(c);

        JLabel label = new JLabel(s);
        label.setFont(CashierGame.SOMETYPEMONO_BOLD.deriveFont(32f));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setForeground(Color.white);

        panel.add(label);
        this.add(panel);
        return panel;
    }

    // MODIFIES: this
    // EFFECTS: used to refresh the quantity panel when it is updated
    private void refresh() {
        this.removeAll();
        initHolder();
        revalidate();
        actions();
    }

    // MODIFIES: this
    // EFFECTS: adds mouse listener to the add and subtract buttons
    private void actions() {
        this.subtractPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (quantity > 0) {
                    subQuantity();
                }

                if (store) {
                    CashierGame.processStore();
                }

                refresh();
            }
        });

        this.addPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                addQuantity();
                if (store) {
                    CashierGame.processStore();
                }
                refresh();
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: creates the panel to display item cost
    private void costPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setPreferredSize(new Dimension(127,  48));
        panel.setBackground(new Color(0xFEB139));
        JLabel label = new JLabel("$" + item.getBuyPrice());
        label.setForeground(Color.white);
        label.setFont(CashierGame.SOMETYPEMONO_BOLD.deriveFont(24f));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(label, BorderLayout.CENTER);
        if (!store) {
            this.add(Box.createHorizontalStrut(127));
            panel.setVisible(false);
        }

        this.add(panel);

    }

    // EFFECTS: returns total cost of items to be purchased
    public int getTotal() {
        return amount;
    }

    // MODIFIES: this
    // EFFECTS: sets the quantity of holder to 0
    public void refreshQuantity() {
        this.quantity = 0;
        refresh();
    }

    // MODIFIES: this
    // EFFECTS: subtracts 1 from quantity
    public void subQuantity() {
        this.amount -= item.getBuyPrice();
        this.quantity--;
    }

    // MODIFIES: this
    // EFFECTS: adds 1 to quantity
    public void addQuantity() {
        this.amount += item.getBuyPrice();
        this.quantity++;
    }

    public void resetAmount() {
        this.amount = 0;
    }

    // EFFECTS: returns total quantity of item
    public int getQuantity() {
        return quantity;
    }

    // EFFECTS: returns item of holder
    public Item getItem() {
        return item;
    }
}
