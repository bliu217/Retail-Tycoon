package ui;

import model.Cashier;
import model.Customer;
import model.Item;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CheckoutDisplay extends MainGameDisplay {

    private static Customer customer;
    private Cashier cashier;
    private JPanel customerCart;
    protected static JPanel checkout;
    protected static JLabel fail;
    protected static JPanel checkoutInventory;
    protected static List<ItemHolder> checkoutHolders;

    public CheckoutDisplay(Customer customer, Cashier cashier) {
        super();
        this.customer = customer;
        this.cashier = cashier;
        customerItemList();
        addText(customer.getName() + ": Please scan my items!");
        customerCharacter();
        initCheckout();
        fail(0);
        this.add(fail, 0);
        itemHolders();
        initInventory();
    }

    private void customerItemList() {
        customerCart = new JPanel();
        customerCart.setBounds(791, 0, 500, 650);
        customerCart.setBackground(Color.white);
        customerCart.setLayout(new BoxLayout(customerCart, BoxLayout.PAGE_AXIS));
        customerCart.setBorder(new EmptyBorder(50, 47, 0, 47));
        JLabel label = new JLabel(customer.getName() + "'s Cart");
        label.setForeground(new Color(0x1E1E1E));
        label.setFont(CashierGame.SOMETYPEMONO_BOLD.deriveFont(40f));
        label.setAlignmentX(CENTER_ALIGNMENT);
        customerCart.add(label);
        sections("item", "qty");
        items();
        this.add(customerCart);
    }

    private void sections(String name, String qty) {
        JPanel panel = new JPanel();
        panel.setMaximumSize(new Dimension(441, 36));
        panel.setOpaque(false);
        panel.setLayout(new BorderLayout(0, 0));
        JLabel i = new JLabel(name);
        i.setFont(CashierGame.SOMETYPEMONO.deriveFont(32f));
        i.setForeground(new Color(0x1E1E1E));
        panel.add(i, BorderLayout.LINE_START);
        JLabel q = new JLabel(qty);
        q.setFont(CashierGame.SOMETYPEMONO.deriveFont(32f));
        q.setForeground(new Color(0x1E1E1E));
        panel.add(q, BorderLayout.LINE_END);
        customerCart.add(Box.createRigidArea(new Dimension(441, 20)));
        customerCart.add(panel);
    }

    private void items() {

        List<Item> alreadyListed = new ArrayList<>();

        for (Item i : customer.getItems()) {
            if (!alreadyListed.contains(i)) {
                sections(i.getName(), Integer.toString(Collections.frequency(customer.getItems(), i)));
                alreadyListed.add(i);
            }

        }
    }

    private void customerCharacter() {
        JPanel circle = new JPanel() {

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                int centerX = getWidth() / 2;
                int centerY = getHeight() / 2;
                int radius = 80;

                g.setColor(Color.white);

                g.fillOval(centerX - radius, centerY - radius, 160, 160);
            }
        };

        circle.setOpaque(false);
        circle.setBounds(1523, 324, 160, 160);
        this.add(circle, 0);
        customerBody();
    }

    private void customerBody() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                Graphics2D g2d = (Graphics2D) g.create();
                int width = getWidth();
                int height = getHeight();
                g2d.setColor(getBackground());
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.fillRoundRect(0, 0, width, height, 110, 110);
                g2d.setColor(new Color(0, 0, 0, 0));
                g2d.drawRoundRect(0, 0, width - 1, height - 1, 110, 110);
                g2d.dispose();
            }
        };

        panel.setBounds(1440, 507, 325, 446);
        panel.setBackground(new Color(0x43A0FF));
        panel.setOpaque(false);
        this.add(panel, 2);

    }

    private void initCheckout() {
        checkout = new JPanel();
        checkout.setBounds(0, 912, 731, 81);
        checkout.setBackground(new Color(0xF55353));
        checkout.setLayout(new GridLayout(1,1));
        JLabel label = new JLabel("ADD TO CHECKOUT");
        label.setForeground(Color.white);
        label.setFont(CashierGame.SOMETYPEMONO_BOLD.deriveFont(32f));
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        checkout.add(label);
        this.add(checkout, 0);
    }
    
    private void fail(int strike) {
        fail = new JLabel("FAIL " + strike + "/3");
        fail.setBounds(291, 1014, 149, 40);
        fail.setFont(CashierGame.SOMETYPEMONO_BOLD.deriveFont(31f));
        fail.setForeground(Color.white);
    }

    private void itemHolders() {
        checkoutInventory = new JPanel();
        checkoutInventory.setOpaque(false);
        checkoutInventory.setBounds(72, 163, 587, 680);
//        checkoutInventory.setBackground(Color.white);
        GridLayout layout = new GridLayout(9, 1);
        layout.setVgap(29);
        checkoutInventory.setLayout(layout);
        this.add(checkoutInventory, 0);
    }

    private void initInventory() {

        List<Item> alreadyListed = new ArrayList<>();
        checkoutHolders = new ArrayList<>();

        for (Item i : cashier.getInventory()) {
            if (!alreadyListed.contains(i)) {
                ItemHolder holder = new ItemHolder(i, false);
                checkoutInventory.add(holder);
                checkoutHolders.add(holder);
                revalidate();
                repaint();
                alreadyListed.add(i);
            }
        }
    }

    public static boolean matchCustomer() {
        for (ItemHolder holder : checkoutHolders) {
            Item temp = holder.getItem();
            if (customer.getItems().contains(temp)) {
                if (Collections.frequency(customer.getItems(), temp) != holder.getQuantity()) {
                    return false;
                }
            }
        }


        return true;
    }


}
