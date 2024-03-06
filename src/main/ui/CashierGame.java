package ui;


import model.Cashier;
import model.Customer;
import model.Item;

import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import java.util.List;
import java.util.Random;
//import javax.swing.*;
//import java.awt.event.KeyAdapter;
//import java.awt.event.KeyEvent;
//import javax.swing.JFrame;

// Represents the application of the game
public class CashierGame {

    private static final String JSON_SOURCE = "./data/cashier.json";

    private Cashier cashier;
    private final List<Cashier> highscores;
    private List<String> customerNames;
    private List<Item> itemList;
    private boolean runGame;
    private boolean freshGame;
    private Scanner input;
    Random random = new Random();
    private Customer customer;
    private int strikeNumber;
    private JsonWriter writer;
    private JsonReader reader;


    public static final Integer START_BALANCE = 100; //start game with this balance

    // EFFECTS: constructs a new game with a new highscore list
    public CashierGame() {
        highscores = new ArrayList<>();
        writer = new JsonWriter(JSON_SOURCE);
        reader = new JsonReader(JSON_SOURCE);
        runCashierGame();
    }

    // EFFECTS: Start menu of the game
    private void runCashierGame() {

        startDisplay();
        input = new Scanner(System.in);
        String nextCommand = input.next();
        nextCommand = nextCommand.toLowerCase();

        if (nextCommand.equals("n")) {
            freshGame = true;
            cashier = new Cashier(0, START_BALANCE);
            System.out.println("Enter save name: ");
            cashier.setSaveName(input.next());
            playGame();
        } else if (nextCommand.equals("s")) {
            viewScores();
        } else if (nextCommand.equals("0")) {
            System.exit(0);
        } else if (nextCommand.equals("l")) {
            loadGame();
            freshGame = false;
            playGame();
        } else {
            runCashierGame();
        }
    }

    // EFFECTS: loads previous save written to ./data/cashier.json
    private void loadGame() {
        try {
            cashier = reader.read();
        } catch (IOException e) {
            System.out.println("Unable to load progress: file error");
        }
    }

    // EFFECTS: saves progress of user to file ./data/cashier.json. If file is renamed or removed,
    // throw FileNotFoundException.
    private void saveGame() {
        try {
            writer.open();
            writer.write(cashier);
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
        }

        for (int i = 0; i < cycle; i++) {
            int index = random.nextInt(cashier.getInventory().size());
            customer.addItem(cashier.getInventory().get(index));
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
        strikeNumber = 0;
        createCustomers();
        initItems();
        init();
        while (runGame) {
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
        System.out.println("Game Over!");
        addHighscore(cashier);
        runCashierGame();
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
        menuDisplay();
        String nextCommand = input.next();
        nextCommand = nextCommand.toLowerCase();

        if (nextCommand.equals("o")) {
            runGame = true;
        } else if (nextCommand.equals("r")) {
            viewStore();
        } else if (nextCommand.equals("i")) {
            viewInventory();
            nextOptions();
        } else if (nextCommand.equals("s")) {
            saveGame();
            nextOptions();
        } else if (nextCommand.equals("0")) {
            System.out.println("Quitting game...");
            System.exit(0);
        } else {
            nextOptions();
        }
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
        System.out.println("Welcome to your first shift: " + cashier.getSaveName() + "!");
        System.out.println("Let's buy some inventory for your store.\n");
        viewStore();
        System.out.println("Here comes your first customer.");

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
            Integer quantity = input.nextInt();
            purchaseQuantity(quantity, currentItem);

        }
    }

    // MODIFIES: this
    // EFFECTS: purchases a quantity items. If given a quantity <= 0,
    // keep prompting user for valid quantity of items.

    private void purchaseQuantity(int quantity, Item currentItem) {
        Boolean invalid;

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
        System.out.println("------------Store------------");
        System.out.println("Balance: $" + cashier.getBalance());
        for (int i = 0; i < itemList.size(); i++) {
            Item item = itemList.get(i);
            int index = i + 1;
            System.out.println("\t" + "[" + index + "] " + item.getName() + " |$" + item.getBuyPrice());
        }
        System.out.println("-----------------------------\n");
        processStore();
        nextOptions();

    }

    // EFFECTS: prints out the highscores of current session
    private void viewScores() {
        System.out.println("---------High Scores---------\n");
        printHighscores();
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

    // EFFECTS: adds highscore and ranks it among current scores
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
                } else if (i == highscores.size()) {
                    highscores.add(cashier);
                }
            }

        }
    }

    // EFFECTS: prints the highscores and the saveName or prints no recorded scores if no game has started
    private void printHighscores() {

        if (highscores.isEmpty()) {
            System.out.println("No recorded scores\n");
        }

        for (int i = 0; i < highscores.size(); i++) {
            Cashier currentCashier = highscores.get(i);
            String save = currentCashier.getSaveName();
            String score = Integer.toString(currentCashier.getScore());

            System.out.println(i + 1 + ". " + save + " | " + score);
        }
    }

    // EFFECTS: prints out the inventory and the frequency of each item without duplicates or prints warning if empty
    private void viewInventory() {

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
    }
}
