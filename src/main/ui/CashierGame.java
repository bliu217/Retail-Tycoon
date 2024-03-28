package ui;


import model.Cashier;
import model.Customer;
import model.Highscores;
import model.Item;

import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import java.util.List;
import java.util.Random;
import javax.swing.JFrame;

// Represents the application of the game
public class CashierGame extends JFrame {

    private static final String JSON_SOURCE = "./data/cashier.json";
    private static final int MAX_SCORES = 10;

    private Cashier cashier;
    private List<Cashier> highscores;
    private List<String> customerNames;
    private List<Item> itemList;
    protected static boolean freshGame;
    Random random = new Random();
    private Customer customer;
    private int strikeNumber;
    private final JsonWriter writer;
    private final JsonReader reader;
    private Highscores scores;
    private KeyAdapter keyHandler;
    private static List<ItemHolder> holders;
    private static int purchaseTotal;

    protected static Font COUTURE;
    protected static Font SOMETYPEMONO_BOLDITALIC;
    protected static Font SOMETYPEMONO_BOLD;
    protected static Font SOMETYPEMONO;


    private JPanel currentPanel;

    public static final Integer START_BALANCE = 100; //start game with this balance

    // EFFECTS: constructs a new game and imports highscores
    public CashierGame() {
        initFonts();
        writer = new JsonWriter(JSON_SOURCE);
        reader = new JsonReader(JSON_SOURCE);
        initFrame();
        runCashierGame();
    }

    private void menu() {
        this.removeKeyListener(keyHandler);
        startActions();
    }


    private void initKeyHandler() {
        keyHandler = new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                changeScene(new StartScreenDisplay());
                menu();
            }
        };


    }

    private void changeScene(JPanel panel) {
        getContentPane().removeAll();
        currentPanel = panel;
        getContentPane().add(currentPanel);
        revalidate();
        repaint();
    }

    // EFFECTS: initializes the main frame of the application
    public void initFrame() {
        setTitle("Retail Tycoon");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(StartScreenDisplay.WIDTH, StartScreenDisplay.HEIGHT);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);


        ImageIcon image = new ImageIcon("AppIcon.jpg");
        setIconImage(image.getImage());
        getContentPane().setBackground(StartScreenDisplay.background);
    }

    // EFFECTS: imports highscores from file
    private void initScores() {
        highscores = new ArrayList<>();
        scores = new Highscores();
        try {
            scores = reader.getHighscores();
            highscores = scores.getScores();
        } catch (Exception e) {
            System.out.println("error");

        }

    }

    // EFFECTS: Start menu of the game
    private void runCashierGame() {
        initScores();
        initKeyHandler();
        changeScene(new StartScreenDisplay());
        setVisible(true);
        menu();
    }

    // MODIFIES: cashier, this
    // EFFECTS: collects the user's inputted name
    private void saveNameAction() {
        NewGameDisplay.textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String temp = NewGameDisplay.textField.getText();
                if (e.getActionCommand().equals(temp)) {
                    String text = NewGameDisplay.textField.getText();
                    if (text.length() > 20 || text.length() == 0) {
                        NewGameDisplay.errorMessage.setVisible(true);
                    } else {
                        NewGameDisplay.errorMessage.setVisible(false);
                        cashier.setSaveName(temp);
                        freshGame = true;
                        playGame();
//                        System.out.println(cashier.getSaveName());
                    }
                }
            }
        });

    }

    // EFFECTS: initializes the buttons for the start menu
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private void startActions() {

        StartScreenDisplay.newGame.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                freshGame = true;
                cashier = new Cashier(0, START_BALANCE);
                changeScene(new NewGameDisplay());
                saveNameAction();
            }
        });

        StartScreenDisplay.loadGame.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                loadGame();

            }
        });

        StartScreenDisplay.highScores.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                changeScene(new HighscoreDisplay());
                printHighscores();
            }
        });

        StartScreenDisplay.quit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });
    }

    // EFFECTS: loads previous save written to ./data/cashier.json
    private void loadGame() {
        try {
            cashier = reader.read();
            freshGame = false;
            playGame();

        } catch (Exception e) {
            StartScreenDisplay.error.setVisible(true);
            revalidate();
            repaint();
        }
    }

    // EFFECTS: saves progress of user to file ./data/cashier.json. If file is renamed or removed,
    // throw FileNotFoundException.
    private void saveGame() {
        scores.setScores(highscores);
        try {
            writer.open();
            writer.write(cashier, scores);
            writer.close();
            MainMenu.addText("Progress saved.");
            revalidate();
            repaint();
        } catch (FileNotFoundException e) {
            MainMenu.addText("Unable to save progress: file error");
            revalidate();
            repaint();
        }
    }

    // EFFECTS: initializes the customer names used in the game
    private void createCustomers() {
        customerNames = new ArrayList<>();
        customerNames.add("Jake");
        customerNames.add("Bryan");
        customerNames.add("Ryan");
        customerNames.add("Sean");
        customerNames.add("Arteen Mohammadi");
        customerNames.add("Bruno Morand Diaz");
    }

    // EFFECTS: initializes the items used in the game
    private void initItems() {
        itemList = cashier.initItems();

    }


    // EFFECTS: Generates a customer with name from customerNames and some random balance
    private void generateCustomer() {
        String customerName = customerNames.get(random.nextInt(customerNames.size()));
        int balance = random.nextInt(100);
        customer = new Customer(customerName, balance);
        int cycle;

        if (cashier.getInventory().size() == 1) {
            cycle = 1;
        } else {
            cycle = random.nextInt(cashier.getInventory().size());
        }

        if (cycle == 0) {
            int index = random.nextInt(cashier.getInventory().size());
            customer.addItem(cashier.getInventory().get(index));
        } else {
            for (int i = 0; i < cycle; i++) {
                int index = random.nextInt(cashier.getInventory().size());
                customer.addItem(cashier.getInventory().get(index));
            }
        }
    }

    // EFFECTS: initializes the game based on new or previous save
    private void init() {
        if (!freshGame) {
            MainMenu.addText("Welcome back: " + cashier.getSaveName());
            nextOptions(false);
        }
    }

    // EFFECTS: Runs the game checkout
    private void runGame() {

        strikeNumber = 0;
        generateCustomer();
        changeScene(new CheckoutDisplay(customer, cashier));
        CheckoutDisplay.checkout.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (CheckoutDisplay.matchCustomer()) {
                    for (Item i : customer.getItems()) {
                        String temp = i.getName();
                        cashier.transaction(temp);
                    }

                    if (strikeNumber == 0) {
                        CheckoutDisplay.addText(customer.getName() + ": That was quick!");
                        CheckoutDisplay.circle.setBackground(new Color(0x6AF553));
                        revalidate();
                        repaint();
                    }


                    nextOptions(true);
                } else {
                    strikeActions();
                }
            }
        });

    }

    // EFFECTS: updates game for when cashier inputs incorrect customer order
    private void strikeActions() {
        strikeNumber++;
        cashier.removeBalance(30);
        strikeMessages();
        checkFail();
        CheckoutDisplay.fail.setText("FAIL " + strikeNumber + "/3");
        revalidate();
        repaint();
    }


    // MODIFIES: this
    // EFFECTS: displays messages on textbox for customer and messages
    private void strikeMessages() {
        if (strikeNumber == 1) {
            CheckoutDisplay.addText(customer.getName() + ": That's not what I want!");
            CheckoutDisplay.addText("CEO: Deducted $30 for poor performance.");
            CheckoutDisplay.circle.setBackground(new Color(0xFEB139));
        } else if (strikeNumber == 2) {
            CheckoutDisplay.addText(customer.getName() + ": Hurry up!");
            CheckoutDisplay.addText("CEO: Deducted $30 for poor performance.");
            CheckoutDisplay.circle.setBackground(new Color(0xF55353));
        }
        revalidate();
        repaint();
    }

    // EFFECTS: fails the game if strikeNumber == 3 or if cashier balance <= 0
    private void checkFail() {
        if (strikeNumber == 3 || cashier.getBalance() <= 0) {
            addHighscore(cashier);
            changeScene(new GameOverScreen(cashier));

            cashier = new Cashier(0, START_BALANCE);
            saveGame();
            GameOverScreen.menuButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    runCashierGame();
                }
            });

        }
    }

    // EFFECTS: runs the loop of the game. breaks out if cashier balance <=0 or 3 strikes while performing checkouts.
    private void playGame() {
        createCustomers();
        initItems();
        nextOptions(false);
        init();
    }

    // EFFECTS: intializes save and quit buttons
    private void saveAndQuit() {
        MainMenu.menuSave.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                saveGame();

            }
        });

        MainMenu.menuMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                runCashierGame();
            }
        });
    }

    // EFFECTS: makes a label with given text
    private void makeLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.white);
        label.setFont(SOMETYPEMONO.deriveFont(32f));
        MainMenu.summary.add(label);
    }

    // EFFECTS: menu in-between actions
    private void nextOptions(Boolean wantSummary) {
        changeScene(new MainMenu(cashier, wantSummary));
        if (wantSummary) {
            makeLabel("PROFIT: $" + customer.itemProfits());
            makeLabel("SCORE: " + cashier.getScore());
            makeLabel("BALANCE: $" + cashier.getBalance());
            revalidate();
            repaint();
        }

        saveAndQuit();
        mainButtons();
    }


    // MODIFIES: this
    // EFFECTS: handles the user input while buying inventory. Also adds inventory and does balance calculations
    public static void processStore() {
        purchaseTotal = 0;

        for (ItemHolder holder : holders) {
            purchaseTotal += holder.getTotal();
        }
        StoreDisplay.refreshTotal(purchaseTotal);
    }

    // EFFECTS: displays the available items for cashier to buy for inventory
    private void viewStore() {
        changeScene(new StoreDisplay(cashier));
        StoreDisplay.backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                nextOptions(false);
            }
        });
        holders = new ArrayList<>();

        for (Item i : itemList) {
            ItemHolder holder = new ItemHolder(i, true);
            holders.add(holder);
            StoreDisplay.storePanel.add(holder);
            revalidate();
        }
        activatePurchase();
        processStore();

    }

    // MODIFIES: cashier
    // EFFECTS: runs the purchase system for the cashier
    private void purchase() {
        if (purchaseTotal <= cashier.getBalance()) {
            for (ItemHolder holder : holders) {
                Item i = holder.getItem();
                int qty = holder.getQuantity();
                cashier.purchaseInventory(i, qty);
                holder.refreshQuantity();
                holder.resetAmount();

            }
            processStore();
            StoreDisplay.refreshBalance();
            StoreDisplay.refreshTotal(0);
            StoreDisplay.changeMessage("SUCCESS!");
        } else {
            StoreDisplay.changeMessage("INVALID PURCHASE");
        }
    }

    // EFFECTS: adds mouselistener to purchase button
    private void activatePurchase() {
        StoreDisplay.purchaseButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                purchase();
            }
        });
    }

    // EFFECTS: adds highscore and ranks it among current scores. highscore.size()<MAX_SCORES, otherwise
    // remove the lowest score.
    private void addHighscore(Cashier cashier) {
        Integer score = cashier.getScore();

        if (highscores.isEmpty()) {
            highscores.add(cashier);
        } else {
            for (int i = 0; i < highscores.size(); i++) {
                Cashier currentCashier = highscores.get(i);
                Integer oldScore = currentCashier.getScore();

                if (score > oldScore) {
                    highscores.add(i, cashier);
                    break;
                } else if (i == (highscores.size() - 1)) {
                    highscores.add(cashier);
                    break;
                }
            }

        }

        if (highscores.size() > MAX_SCORES) {
            highscores.remove(MAX_SCORES);
        }
    }

    // EFFECTS: prints the highscores and the saveName or prints no recorded scores if no game has started
    private void printHighscores() {

        HighscoreDisplay.scoreBody.removeAll();
        addKeyListener(keyHandler);

        emptyHighscore();

        for (int i = 0; i < highscores.size(); i++) {
            Cashier currentCashier = highscores.get(i);
            String save = currentCashier.getSaveName();
            String score = Integer.toString(currentCashier.getScore());
            JLabel label = new JLabel(save + " | " + score);
            Color c;
            if (i == 0) {
                c = new Color(0xF55353);
            } else {
                c = Color.white;
            }
            label.setFont(SOMETYPEMONO_BOLD.deriveFont(32f));
            label.setForeground(c);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            HighscoreDisplay.scoreBody.add(label);

        }
        revalidate();
        repaint();
        requestFocus();
    }

    // EFFECTS: displays message if highscore list is empty
    private void emptyHighscore() {
        if (highscores.isEmpty()) {
            JLabel label = new JLabel("NO RECORDED SCORES");
            HighscoreDisplay.scoreBody.add(label);
        }
    }

    // EFFECTS: prints out the inventory and the frequency of each item without duplicates or prints warning if empty
    private void viewInventory() {
        changeScene(new InventoryDisplay(cashier));
        InventoryDisplay.backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                nextOptions(false);
            }
        });

        List<Item> alreadyListed = new ArrayList<>();

        for (Item i : cashier.getInventory()) {
            if (!alreadyListed.contains(i)) {
                InventoryDisplay.addItem(i);
                revalidate();
                repaint();
                alreadyListed.add(i);
            }
        }
    }

    // EFFECTS: intializes the fonts for the program
    private void initFonts() {
        try {
            COUTURE = Font.createFont(Font.TRUETYPE_FONT, new File("CoutureBold.otf"));
            SOMETYPEMONO_BOLD = Font.createFont(Font.TRUETYPE_FONT, new File("SometypeMonoBold.otf"));
            SOMETYPEMONO = Font.createFont(Font.TRUETYPE_FONT, new File("SometypeMonoRegular.otf"));
            SOMETYPEMONO_BOLDITALIC = Font.createFont(Font.TRUETYPE_FONT, new File("SometypeMonoBoldItalic.otf"));

        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }

    // EFFECTS: initializes main buttons of main menu
    private void mainButtons() {
        MainMenu.storeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                viewStore();

            }
        });

        MainMenu.inventoryButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                viewInventory();
            }
        });

        MainMenu.nextDayButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (cashier.getInventory().size() == 0) {
                    MainMenu.addText("Please restock your inventory!");
                } else {
                    runGame();
                }
            }
        });
    }
}
