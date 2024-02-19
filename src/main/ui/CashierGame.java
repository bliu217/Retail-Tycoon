package ui;


import model.Cashier;
import model.Customer;
import model.Item;

import java.util.ArrayList;
import java.util.Scanner;

import java.util.List;
import java.util.Random;
//import javax.swing.*;
//import java.awt.event.KeyAdapter;
//import java.awt.event.KeyEvent;
//import javax.swing.JFrame;


public class CashierGame {

    private Cashier cashier;
    private final List<Cashier> highscores;
    private List<String> customerNames;
    private List<Item> itemList;
    private boolean runGame;
    private Scanner input;
    Random random = new Random();
    private Customer customer;
    private int strikeNumber;

    // EFFECTS: constructs a new game with a new highscore list
    public CashierGame() {
        highscores = new ArrayList<>();
        runCashierGame();
    }

    // EFFECTS: Start menu of the game
    private void runCashierGame() {

        startDisplay();
        input = new Scanner(System.in);
        String nextCommand = input.next();
        nextCommand = nextCommand.toLowerCase();

        if (nextCommand.equals("n")) {
            init();
            System.out.println("Enter save name: ");
            cashier.setSaveName(input.next());
            playGame();
        } else if (nextCommand.equals("s")) {
            viewScores();
        } else if (nextCommand.equals("0")) {
            System.exit(0);
        } else {
            runCashierGame();
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
        itemList = new ArrayList<>();

        Item banana = new Item("Banana", 3, 1);
        Item dogFood = new Item("DogFood", 24, 14);
        Item detergent = new Item("Detergent", 10, 5);
        Item notebook = new Item("Notebook", 16, 9);
        Item microwave = new Item("Microwave", 40, 30);
        Item water = new Item("Goodwater", 4, 1);

        itemList.add(banana);
        itemList.add(dogFood);
        itemList.add(detergent);
        itemList.add(notebook);
        itemList.add(microwave);
        itemList.add(water);

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

    // EFFECTS: runs the loop of the game. breaks out if cashier balance <=0 or 3 strikes while performing checkouts.
    private void playGame() {
        createCustomers();
        initItems();
        newGameMessage();

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

    // EFFECTS: menu in-between actions
    public void nextOptions() {
        System.out.println("Current Balance: $" + cashier.getBalance());
        System.out.println("Current Score: " + cashier.getScore() + " pts\n");
        System.out.println("open business [o]");
        System.out.println("restock inventory [r]");
        System.out.println("view inventory [i]\n");

        String nextCommand = input.next();
        nextCommand = nextCommand.toLowerCase();

        if (nextCommand.equals("o")) {
            runGame = true;
        } else if (nextCommand.equals("r")) {
            viewStore();
        } else if (nextCommand.equals("i")) {
            viewInventory();

            nextOptions();
        } else {
            nextOptions();
        }
    }



    // EFFECTS: performs the checkout of the customer items, adds profits to balance appropriately or fails the game
    public void checkout() {
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
    public void scanItems() {
        System.out.println("----------Checkout-----------");
        for (Item i : customer.getItems()) {
            System.out.println(i.getName());
        }
        System.out.println("-----------------------------");
    }

    // EFFECTS: startup message for first creating a new game, includes buying first inventory for shop
    public void newGameMessage() {
        System.out.println("Welcome to your first shift: " + cashier.getSaveName() + "!");
        System.out.println("Let's buy some inventory for your store.\n");
        viewStore();
        System.out.println("Here comes your first customer.");

    }

    // EFFECTS: handles the user input while buying inventory. Also adds inventory and does balance calculations
    public void processStore() {
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

            if (cashier.purchaseInventory(currentItem, quantity)) {
                System.out.println("Success! x" + quantity + " " + currentItem.getName() + " added to inventory.");
            } else {
                System.out.println("Insufficient funds!");
            }

        }
    }

    // EFFECTS: displays the available items for cashier to buy for inventory
    public void viewStore() {
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
    public void viewScores() {
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

    // EFFECTS: initializes main cashier and strikeNumber
    public void init() {
        cashier = new Cashier();
        strikeNumber = 0;
    }

    // EFFECTS: prints out the messages for the main menu on startup
    public void startDisplay() {
        System.out.println("Welcome to the Cashier Game\n");
        System.out.println("New Game [press N]");
        System.out.println("View Scores [press S]");
        System.out.println("Exit Game [press 0]");
    }

    // EFFECTS: adds highscore and ranks it among current scores
    public void addHighscore(Cashier cashier) {
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
    public void printHighscores() {

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
    public void viewInventory() {

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
