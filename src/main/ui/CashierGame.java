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
import java.util.Scanner;

import java.util.List;
import java.util.Random;
import javax.swing.JFrame;

// Represents the application of the game
public class CashierGame extends JFrame {

    private static final String JSON_SOURCE = "./data/cashier.json";
    private static final int MAX_SCORES = 10;
    private static final int MENU = 1;
    private static final int START = 0;

    private Cashier cashier;
    private List<Cashier> highscores;
    private List<String> customerNames;
    private List<Item> itemList;
    private boolean runGame;
    private boolean freshGame;
    private Scanner input;
    Random random = new Random();
    private Customer customer;
    private int strikeNumber;
    private final JsonWriter writer;
    private final JsonReader reader;
    private Highscores scores;
    private KeyAdapter keyHandler;

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
        initScores();
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


    public void initFrame() {
        setTitle("Retail Tycoon");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
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

        //        currentPanel = new StoreDisplay(); //temporary
        currentPanel = new StartScreenDisplay(); //uncomment when done testing
        add(currentPanel);
        setVisible(true);
        initKeyHandler();
        menu();// uncomment when done testing

//        startDisplay();
//        input = new Scanner(System.in);
//        String nextCommand = input.next();
//        nextCommand = nextCommand.toLowerCase();
//
//        if (nextCommand.equals("n")) {
//            freshGame = true;
//            cashier = new Cashier(0, START_BALANCE);
//            System.out.println("Enter save name: ");
//            cashier.setSaveName(input.next());
//            playGame();
//        } else if (nextCommand.equals("s")) {
//            viewScores();
//        } else if (nextCommand.equals("0")) {
//            System.exit(0);
//        } else if (nextCommand.equals("l")) {
//            loadGame();
//            freshGame = false;
//            playGame();
//        } else {
//            runCashierGame();
//        }
    }


    private void saveNameAction() {
        NewGameDisplay.textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String temp = NewGameDisplay.textField.getText();
                if (e.getActionCommand().equals(temp)) {
                    if (NewGameDisplay.textField.getText().length() > 20) {
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
                freshGame = false;
                playGame();
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
        } catch (Exception e) {
            System.out.println("No previous save found.");
            runCashierGame();
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
            System.out.println("Progress saved");
        } catch (FileNotFoundException e) {
            System.out.println("Unable to save progress: file error");
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
        if (freshGame) {
            newGameMessage();
        } else {
            System.out.println("Welcome back: " + cashier.getSaveName());
            nextOptions();
        }
    }

    // EFFECTS: runs the loop of the game. breaks out if cashier balance <=0 or 3 strikes while performing checkouts.
    private void playGame() {
        createCustomers();
        initItems();
        nextOptions();
        init();

        while (runGame) {
            strikeNumber = 0;
            generateCustomer();

            System.out.println(customer.getName() + ": Scan these items please");
            checkout();
            if (cashier.getBalance() <= 0 | strikeNumber == 3) {
                break;
            }

            if (cashier.getInventory().isEmpty()) {
                System.out.println("Your inventory is empty, please restock!\n");
                viewStore();
            }
            nextOptions();
        }
////        System.out.println("Game Over!");
//        addHighscore(cashier);
//        cashier = new Cashier(0,START_BALANCE);
//        saveGame();
//        runCashierGame();
    }



    // EFFECTS: on screen printouts to prompt user input for desired action
    private void menuDisplay() {
        System.out.println("Current Balance: $" + cashier.getBalance());
        System.out.println("Current Score: " + cashier.getScore() + " pts\n");
        System.out.println("open business [o]");
        System.out.println("restock inventory [r]");
        System.out.println("view inventory [i]");
        System.out.println("save [s]");
        System.out.println("quit game [0]");
    }

    // EFFECTS: menu in-between actions
    private void nextOptions() {
        changeScene(new MainMenu());
        mainButtons();

//        menuDisplay();
//        String nextCommand = input.next();
//        nextCommand = nextCommand.toLowerCase();
//
//        if (nextCommand.equals("o")) {
//            runGame = true;
//        } else if (nextCommand.equals("r")) {
//            viewStore();
//        } else if (nextCommand.equals("i")) {
//            viewInventory();
//            nextOptions();
//        } else if (nextCommand.equals("s")) {
//            saveGame();
//            nextOptions();
//        } else if (nextCommand.equals("0")) {
//            System.out.println("Quitting game...");
//            System.exit(0);
//        } else {
//            nextOptions();
//        }
    }

    private void nextOptionsDisplay() {
        changeScene(new SummaryDisplay());
//        SummaryDisplay.summary.add()
    }




    // MODIFIES: this, customer
    // EFFECTS: performs the checkout of the customer items, adds profits to balance appropriately or fails the game
    private void checkout() {
        List<Item> items = customer.getItems();
        while (!(items.isEmpty())) {
            scanItems();
            System.out.println("Add to checkout: (type items)");
            String itemInput = input.next();
            if (customer.searchAndRemoveItem(itemInput)) {
                System.out.println("Success!\n");
                cashier.transaction(itemInput);
                checkout();
            } else {
                strikeNumber++;
                if (strikeNumber == 3) {
                    System.out.println("You're Fired!");
                    items.clear();
                    break;
                }
                System.out.println("Item not in customer checkout list");
                System.out.println("Strike (" + strikeNumber + "/3)");
                cashier.removeBalance(30);
                checkout();
            }
        }
        cashier.addBalance(customer.itemProfits());
    }

    // EFFECTS: prints out the list of customer items needed to be scanned
    private void scanItems() {
        System.out.println("----------Checkout-----------");
        for (Item i : customer.getItems()) {
            System.out.println(i.getName());
        }
        System.out.println("-----------------------------");
    }

    // EFFECTS: startup message for first creating a new game, includes buying first inventory for shop
    private void newGameMessage() {
        MainGameDisplay.addText("Welcome to your first shift: " + cashier.getSaveName() + "!");
        MainGameDisplay.addText("Let's buy some inventory for your store.");
        revalidate();
//        System.out.println("Welcome to your first shift: " + cashier.getSaveName() + "!");
//        System.out.println("Let's buy some inventory for your store.\n");
//        viewStore();
//        System.out.println("Here comes your first customer.");
    }



    // MODIFIES: this
    // EFFECTS: handles the user input while buying inventory. Also adds inventory and does balance calculations
    private void processStore() {
        System.out.println("Type the number of the item you would like to stock: (type 0 to exit)");
        int option = input.nextInt();

        if (option == 0) {
            if (cashier.getInventory().isEmpty()) {
                System.out.println("Your inventory is empty, please restock!\n");
                processStore();
            } else {
                runGame = true;
            }
        } else {

            int index = option - 1;
            Item currentItem = itemList.get(index);
            System.out.println("Type the quantity of item: ");
            int quantity = input.nextInt();
            purchaseQuantity(quantity, currentItem);

        }
    }

    // MODIFIES: this
    // EFFECTS: purchases a quantity items. If given a quantity <= 0,
    // keep prompting user for valid quantity of items.

    private void purchaseQuantity(int quantity, Item currentItem) {
        boolean invalid;

        if (quantity <= 0) {
            invalid = true;
            while (invalid) {
                System.out.println("Invalid quantity. Try again:");
                quantity = input.nextInt();
                if (quantity > 0) {
                    invalid = false;
                }
            }
        }

        if (cashier.purchaseInventory(currentItem, quantity)) {
            System.out.println("Success! x" + quantity + " " + currentItem.getName() + " added to inventory.");
        } else {
            System.out.println("Insufficient funds!");
        }
    }

    // EFFECTS: displays the available items for cashier to buy for inventory
    private void viewStore() {
        changeScene(new StoreDisplay(cashier));
        StoreDisplay.backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                nextOptions();
            }
        });

        for (Item i : itemList) {
            StoreDisplay.storePanel.add(new ItemHolder(i, true));
            revalidate();
        }





//        System.out.println("------------Store------------");
//        System.out.println("Balance: $" + cashier.getBalance());
//        for (int i = 0; i < itemList.size(); i++) {
//            Item item = itemList.get(i);
//            int index = i + 1;
//            System.out.println("\t" + "[" + index + "] " + item.getName() + " |$" + item.getBuyPrice());
//        }
//        System.out.println("-----------------------------\n");
//        processStore();
//        nextOptions();

    }

    // EFFECTS: prints out the highscores of current session
    private void viewScores() {
        System.out.println("---------High Scores---------\n");
        printHighscores();
        System.out.println("-----------------------------\n");
        System.out.println("return to menu [M]");


        String nextCommand = input.next();
        nextCommand = nextCommand.toLowerCase();
        if (nextCommand.equals("m")) {
            runCashierGame();
        } else {
            viewScores();
        }
    }


    // EFFECTS: prints out the messages for the main menu on startup
    private void startDisplay() {
        System.out.println("Welcome to the Cashier Game\n");
        System.out.println("New Game [press N]");
        System.out.println("Load Game [press L]");
        System.out.println("View Scores [press S]");
        System.out.println("Exit Game [press 0]");
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
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private void printHighscores() {

        HighscoreDisplay.scoreBody.removeAll();


        if (highscores.isEmpty()) {
            JLabel label = new JLabel("NO RECORDED SCORES");
            HighscoreDisplay.scoreBody.add(label);
        }

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
        this.addKeyListener(keyHandler);

    }

    // EFFECTS: prints out the inventory and the frequency of each item without duplicates or prints warning if empty
    private void viewInventory() {
        System.out.println("----------Inventory----------");
        List<Item> alreadyListed = new ArrayList<>();

        if (cashier.getInventory().isEmpty()) {
            System.out.println("Empty Inventory! Restock Immediately.");
            return;
        }

        for (Item i : cashier.getInventory()) {
            if (!alreadyListed.contains(i)) {
                System.out.println(i.getName() + " | x" + cashier.itemFrequency(i));
                alreadyListed.add(i);
            }
        }
        System.out.println("-----------------------------\n");
    }

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

    private void mainButtons() {
        MainGameDisplay.storeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                viewStore();

            }
        });

        MainGameDisplay.inventoryButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }
        });

        MainGameDisplay.nextDayButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }
        });
    }
}
