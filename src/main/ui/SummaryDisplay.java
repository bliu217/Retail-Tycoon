package ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class SummaryDisplay extends MainGameDisplay {
    protected static JPanel summary;

    public SummaryDisplay() {
        super();
        summaryPanel();
//        super.initSidePanel();
    }

    private void summaryPanel() {
        summary = new JPanel();
        summary.setBounds(210, 287,312, 120);
        summary.setOpaque(false);
        summary.setLayout(new GridLayout(3, 1));
        this.add(summary, 0);

        JPanel line = new JPanel();
        line.setBounds(210, 242, 312, 3);
        this.add(line);

        JPanel tb = new JPanel();
        tb.setBounds(226, 202, 279, 40);
        tb.setOpaque(false);
        JLabel label = new JLabel("TODAY'S SUMMARY");
        label.setFont(CashierGame.SOMETYPEMONO_BOLD.deriveFont(32f));
        label.setForeground(Color.white);
        tb.add(label);
        this.add(tb);
        initImage();
    }

    private void initImage() {
        BufferedImage image;
        try {
            image = ImageIO.read(new File("img.png"));
            Image greenCheck = image.getScaledInstance(100,100,Image.SCALE_DEFAULT);
            ImageIcon icon = new ImageIcon(greenCheck);

            JLabel check = new JLabel(icon);
            check.setBounds(316, 466, 100, 100);
            this.add(check);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
