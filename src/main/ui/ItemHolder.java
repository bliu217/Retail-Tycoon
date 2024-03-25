package ui;

import model.Item;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ItemHolder extends JPanel {

    private Item item;
    boolean store;
    private int quantity;
    protected JPanel subtractPanel;
    protected JPanel addPanel;

    public ItemHolder(Item i, boolean store) {
        this.store = store;
        this.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        this.setSize(587, 48);
        this.setBackground(Color.white);
        this.quantity = 0;
        this.item = i;
        initHolder();
        actions();


    }

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
        if (!store) {
            panel.setVisible(false);
        }
        this.add(panel);
    }

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

    private void refresh() {
        this.removeAll();
        initHolder();
        revalidate();
        actions();
    }

    private void actions() {
        this.subtractPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (quantity > 0) {
                    subQuantity();
                    refresh();
                }
            }
        });

        this.addPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                addQuantity();
                refresh();
            }
        });
    }

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

        this.add(panel);

    }



    public void subQuantity() {
        this.quantity--;
    }

    public void addQuantity() {
        this.quantity++;
    }

    public int getQuantity() {
        return quantity;
    }
}
