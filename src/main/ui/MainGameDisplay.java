package ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;

public class MainGameDisplay extends JPanel {
    protected static JPanel textBox;


    public MainGameDisplay() {
        setLayout(null);
        setSize(WIDTH, HEIGHT);
        setBackground(new Color(0x00D1FF));
        initTextBox();
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





}
