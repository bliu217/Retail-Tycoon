package ui;

import model.Cashier;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;

// the main display components on top on the main game display
public class MainMenu extends MainGameDisplay {
    private final Cashier cashier;
    protected static boolean nextDay;
    private MouseAdapter off;
    protected static JPanel lowInventoryMessage;
    protected static JPanel storeButton;
    protected static JPanel inventoryButton;
    protected static JPanel nextDayButton;
    protected static JPanel menuSave;
    protected static JPanel menuMenu;
    protected static JPanel holder;
    protected static JPanel summary;

    // EFFECTS: creates new main menu that displays the save, quit, next day, inventory, store buttons
    public MainMenu(Cashier cashier, boolean b) {
        super();
        saveAndQuitButtons();
        storeButton();
        inventoryButton();
        initLowInventoryError();
        initNextDay();
        initSidePanel();
        this.cashier = cashier;
        off();
        messages();

        if (b) {
            summaryPanel();
        } else {
            showLog();
            addContent();
        }
    }

    // EFFECTS: adds appropriate text to textBox
    private void messages() {
        if (cashier.getInventory().size() == 0) {
            lowInventoryMessage.setVisible(true);
            if (!CashierGame.freshGame) {
                addText("Restock your inventory!");
            }
        }

        if (!CashierGame.freshGame && !nextDay) {
            nextDayButton.removeMouseListener(off);
            addText("What's the move, " + cashier.getSaveName() + "?");
        }

        if (CashierGame.freshGame || nextDay) {

            if (cashier.getInventory().size() > 0) {
                addText("Let's welcome your new customer " + cashier.getSaveName() + "!");
                addText("Press NEXT DAY to open your business.");
                CashierGame.freshGame = false;
                nextDay = true;
                nextDayButton.addMouseListener(off);
            } else {
                addText("Welcome to your first shift: " + cashier.getSaveName() + "!");
                addText("Let's buy some inventory for your store.");
            }

        }
    }

    // EFFECTS: initializes the off mouse adapter that switches off nextDay
    private void off() {
        off = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                nextDay = false;
            }
        };
    }

    // MODIFIES: this
    // EFFECTS: creates the store button
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
        this.add(storeButton, 0);
    }

    // MODIFIES: this
    // EFFECTS: creates the inventory button
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
        this.add(inventoryButton, 0);

    }

    // MODIFIES: this
    // EFFECTS: creates the low inventory error
    private void initLowInventoryError() {
        lowInventoryMessage = new JPanel();
        lowInventoryMessage.setOpaque(false);
        lowInventoryMessage.setBounds(203, 789, 325, 25);
        JLabel label = new JLabel("LOW INVENTORY! MUST RESTOCK!");
        label.setFont(CashierGame.SOMETYPEMONO_BOLD.deriveFont(18f));
        label.setForeground(Color.white);
        lowInventoryMessage.add(label);
        lowInventoryMessage.setVisible(false);
        this.add(lowInventoryMessage, 0);
    }

    // MODIFIES: this
    // EFFECTS: creates the next day button
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
        this.add(nextDayButton, 0);
    }

    // EFFECTS: creates save and quit buttons
    private void saveAndQuitButtons() {
        menuSave = makeButton(new Color(0x6AF553), "SAVE");
        menuMenu = makeButton(new Color(0xF55353), "MENU");
        holder();
    }

    // MODIFIES: this
    // EFFECTS: creates holder for save and quit buttons
    private void holder() {
        holder = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                Graphics2D g2d = (Graphics2D) g.create();
                int width = getWidth();
                int height = getHeight();
                g2d.setColor(getBackground());
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.fillRoundRect(0, 0, width, height, 75, 75);
                g2d.setColor(new Color(0, 0, 0, 0));
                g2d.drawRoundRect(0, 0, width - 1, height - 1, 75, 75);
                g2d.dispose();
            }
        };
        holder.setOpaque(false);
        holder.setBounds(1654, 17, 248, 78);
        holder.setBackground(new Color(20,63,107,75));
        holder.setBorder(new EmptyBorder(14, 13, 14, 13));
        holder.setLayout(new BorderLayout());
        holder.add(menuSave, BorderLayout.LINE_START);
        holder.add(menuMenu, BorderLayout.LINE_END);
        this.add(holder, 0);
    }

    // EFFECTS: creates a JPanel with given color and text
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private JPanel makeButton(Color color, String text) {
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
        panel.setPreferredSize(new Dimension(107,49));
        panel.setMaximumSize(new Dimension(107,49));
        panel.setOpaque(false);
        panel.setBackground(color);
        panel.setLayout(new BorderLayout());
        JLabel label = new JLabel(text);
        label.setForeground(Color.white);
        label.setFont(CashierGame.SOMETYPEMONO_BOLD.deriveFont(24f));
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(label, BorderLayout.CENTER);
        return panel;
    }

    // MODIFIES: this
    // EFFECTS: creates the panel to hold summary of previous transaction
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
        this.add(tb, 0);
        initImage();
    }

    // MODIFIES: this
    // EFFECTS: creates checkmark image
    private void initImage() {
        BufferedImage image;
        try {
            image = ImageIO.read(new File("img.png"));
            Image greenCheck = image.getScaledInstance(100,100,Image.SCALE_DEFAULT);
            ImageIcon icon = new ImageIcon(greenCheck);

            JLabel check = new JLabel(icon);
            check.setBounds(316, 466, 100, 100);
            this.add(check, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // MODIFIES: this
    // EFFECTS: creates the MANAGER cashierName header for side panel
    private void showLog() {
        JPanel logText = new JPanel();
        logText.setLayout(new GridLayout(2, 1));
        logText.setBounds(181, 249, 370, 116);
        logText.setOpaque(false);
        JLabel one = makeLabel("MANAGER");
        JLabel two = makeLabel(cashier.getSaveName());
        logText.add(one);
        logText.add(two);
        this.add(logText, 0);
    }

    // EFFECTS: helper to make JLabel with given text
    private JLabel makeLabel(String text) {
        JLabel label1 = new JLabel(text);
        label1.setFont(CashierGame.COUTURE.deriveFont(48f));
        label1.setForeground(Color.white);
        label1.setHorizontalAlignment(SwingConstants.CENTER);
        return label1;
    }

    // MODIFIES: this
    // EFFECTS: adds the summary components to the summary panel
    private void addContent() {
        JPanel log = new JPanel();
        log.setOpaque(false);
        log.setBounds(109, 448, 514, 183);
        GridLayout layout = new GridLayout(2, 1);
        layout.setVgap(27);
        log.setLayout(layout);
        JPanel score = createRoundPanel("SCORE: " + cashier.getScore());
        JPanel balance = createRoundPanel("BALANCE: $" + cashier.getBalance());
        log.add(score);
        log.add(balance);
        this.add(log, 0);

    }

    // EFFECTS: returns a round JPanel with given string
    private JPanel createRoundPanel(String text) {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setColor(getBackground());
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 100, 100);
                g2d.setColor(new Color(0, 0, 0, 0));
                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 100, 100);
                g2d.dispose();
            }
        };
        panel.setBackground(new Color(20,63,107,50));
        panel.setLayout(new GridLayout(1,1));
        panel.setOpaque(false);
        JLabel label = new JLabel(text);
        label.setFont(CashierGame.SOMETYPEMONO_BOLD.deriveFont(32f));
        label.setForeground(Color.white);
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(label);
        return panel;
    }







}
