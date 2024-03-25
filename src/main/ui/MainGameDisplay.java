package ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class MainGameDisplay extends JPanel {
    protected static JPanel textBox;
    protected static JPanel lowInventoryMessage;
    protected static JPanel storeButton;
    protected static JPanel inventoryButton;
    protected static JPanel nextDayButton;


    public MainGameDisplay() {
        setLayout(null);
        setSize(WIDTH, HEIGHT);
        setBackground(new Color(0x00D1FF));
        initTextBox();
        storeButton();
        inventoryButton();
        initLowInventoryError();
        initNextDay();
        initSidePanel();
        // add function to switch to inventory/store panel when buttons are clicked
    }

    private void initTextBox() {
        textBox = new JPanel(new GridLayout(6, 1));
        textBox.setBounds(731, 722, 1189, 358);
        textBox.setBackground(new Color(0x1E1E1E));
        textBox.setBorder(new EmptyBorder(46, 61, 46, 61));
        this.add(textBox);
    }

    public static void addText(String s) {
        JLabel label = new JLabel(s);
        label.setFont(CashierGame.SOMETYPEMONO_BOLD.deriveFont(32f));
        label.setForeground(Color.white);
        textBox.add(label);
    }

    public void refreshTextBox() {
        textBox.removeAll();
    }

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

    private void storeButton() {
        storeButton = new JPanel();
        storeButton.setBounds(0, 831, 366, 81);
        storeButton.setBackground(new Color(0xF55353));
        storeButton.setLayout(new GridLayout(1,1));

        JLabel label = new JLabel("STORE");
        label.setForeground(Color.white);
        label.setFont(CashierGame.SOMETYPEMONO_BOLD.deriveFont(32f));
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        storeButton.add(label);
        this.add(storeButton);
    }

    private void inventoryButton() {
        inventoryButton = new JPanel();
        inventoryButton.setBounds(366,831,365,81);
        inventoryButton.setBackground(new Color(0xAE53F5));
        inventoryButton.setLayout(new GridLayout(1,1));

        JLabel label = new JLabel("INVENTORY");
        label.setForeground(Color.white);
        label.setFont(CashierGame.SOMETYPEMONO_BOLD.deriveFont(32f));
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        inventoryButton.add(label);
        this.add(inventoryButton);

    }

    private void initLowInventoryError() {
        lowInventoryMessage = new JPanel();
        lowInventoryMessage.setOpaque(false);
        lowInventoryMessage.setBounds(203, 789, 325, 25);
        JLabel label = new JLabel("LOW INVENTORY! MUST RESTOCK!");
        label.setFont(CashierGame.SOMETYPEMONO_BOLD.deriveFont(18f));
        label.setForeground(Color.white);
        lowInventoryMessage.add(label);
        lowInventoryMessage.setVisible(false);
        this.add(lowInventoryMessage);
    }

    private void initNextDay() {
        nextDayButton = new JPanel();
        nextDayButton.setBounds(0, 912, 731, 81);
        nextDayButton.setBackground(new Color(0x6AF553));
        nextDayButton.setLayout(new GridLayout(1,1));
        JLabel label = new JLabel("NEXT DAY");
        label.setForeground(Color.white);
        label.setFont(CashierGame.SOMETYPEMONO_BOLD.deriveFont(32f));
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        nextDayButton.add(label);
        this.add(nextDayButton);
    }



}
