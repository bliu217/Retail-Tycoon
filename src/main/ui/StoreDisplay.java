package ui;

import model.Cashier;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class StoreDisplay extends WindowTemplate {
    protected static JPanel storePanel;
    protected static JPanel purchaseButton;
    private static JPanel whitePanel;
    private static JLabel total;
    protected static JLabel message;
    private static JPanel balanceBox;
    private static JLabel balance;
    private static int amount;



    public StoreDisplay(Cashier cashier) {
        super(new Color(0xF55353), "STORE", cashier);
        initMessageBox();
        genWhiteBox();
        initTotal(0);
        purchaseAmount();
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

    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private void balance() {
        balanceBox = new JPanel() {
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
        balanceBox.setOpaque(false);
        balanceBox.setLayout(new BorderLayout());
        balanceBox.setBounds(1507, 240, 172, 60);
        balanceBox.setBackground(new Color(0,0, 0, 75));
        balance = new JLabel("$" + cashier.getBalance());
        balance.setForeground(Color.white);
        balance.setFont(CashierGame.SOMETYPEMONO_BOLD.deriveFont(40f));
        balance.setVerticalAlignment(SwingConstants.CENTER);
        balance.setHorizontalAlignment(SwingConstants.CENTER);
        balanceBox.add(balance, BorderLayout.CENTER);
        this.add(balanceBox, 0);

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
        whitePanel.setBorder(new EmptyBorder(0, 10, 0, 10));
        whitePanel.setLayout(new BorderLayout(0,0));
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

    private void purchaseAmount() {
        JLabel dollar =  new JLabel("$");
        dollar.setFont(CashierGame.SOMETYPEMONO_BOLD.deriveFont(40f));
        dollar.setForeground(new Color(0x143F6B));
        dollar.setVerticalAlignment(SwingConstants.CENTER);
        whitePanel.add(dollar, BorderLayout.LINE_START);
        whitePanel.add(total, BorderLayout.LINE_END, 0);
    }

    private static void initTotal(int input) {

        amount = input;

//        System.out.println("Amount: " + amount);
        total = new JLabel(Integer.toString(amount));
//        System.out.println("Text: " + total.getText());
        total.setVerticalAlignment(SwingConstants.CENTER);
        total.setFont(CashierGame.SOMETYPEMONO_BOLD.deriveFont(40f));
        total.setForeground(new Color(0x143F6B));
    }

    public static void refreshTotal(int input) {
        whitePanel.remove(total);
        initTotal(input);
        whitePanel.add(total, BorderLayout.LINE_END, 0);
        whitePanel.revalidate();
        whitePanel.repaint();
    }

    public static void refreshBalance() {
        balance.setText("$" + cashier.getBalance());
        balance.revalidate();
        balance.repaint();
    }

    private void initMessageBox() {
        message = new JLabel("test");
        message.setBounds(848, 894, 223, 30);
        message.setForeground(Color.white);
        message.setFont(CashierGame.SOMETYPEMONO_BOLD.deriveFont(23f));
        message.setVerticalAlignment(SwingConstants.CENTER);
        message.setHorizontalAlignment(SwingConstants.CENTER);
        message.setVisible(false);
        this.add(message, 0);
    }

    public static void changeMessage(String s) {
        message.setVisible(true);
        message.setText(s);
        message.repaint();
        message.revalidate();
    }




}
