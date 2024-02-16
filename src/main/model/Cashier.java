package model;

import java.util.ArrayList;
import java.util.List;

public class Cashier {

    private Integer score;
    private String saveName;
    private Integer balance;
    private List<Item> inventory;

    public static final Integer START_BALANCE = 100; //start game with this balance


    // EFFECTS: Construct a new cashier with score of 0, no saveName, a balance of START_BALANCE,
    //          and an empty inventory.

    public Cashier() {
        this.score = 0;
        this.saveName = null;
        this.balance = START_BALANCE;
        this.inventory = new ArrayList<>();
    }

    // REQUIRES: amount > 0
    // MODIFIES: this
    // EFFECTS: adds score by amount
    public void addScore(int amount) {
        this.score += amount;
    }

    // REQUIRES: amount > 0
    // MODIFIES: this
    // EFFECTS: subtracts score by amount
    public void setSaveName(String saveName) {
        this.saveName = saveName;
    }

    public String getSaveName() {
        return saveName;
    }

    // MODIFIES: this
    // EFFECTS: adds item to inventory if balance is sufficient and returns true,
    //          else return false
    public boolean purchaseInventory(Item item, Integer quantity) {

        int totalPrice = item.getBuyPrice() * quantity;
        if (totalPrice <= balance) {
            this.balance -= totalPrice;
            for (int i = 0; i < quantity; i++) {
                this.inventory.add(item);
            }
            return true;
        }

        return false;
    }

    // REQUIRES: amount >= 0
    // MODIFIES: this
    // EFFECTS: increases cashier balance by amount
    public void addBalance(int amount) {
        this.balance += amount;
    }

    // REQUIRES: amount >= 0
    // MODIFIES: this
    // EFFECTS: subtracts cashier balance by amount
    public void removeBalance(int amount) {
        this.balance -= amount;
    }

    // EFFECTS: returns balance of cashier
    public Integer getBalance() {
        return balance;
    }

    // EFFECTS: returns score of the cashier
    public Integer getScore() {
        return score;
    }

    // EFFECTS: returns current inventory of cashier
    public List<Item> getInventory() {
        return inventory;
    }

    // EFFECTS: returns frequency of that item in the list
    public Integer itemFrequency(Item item) {
        int frequency = 0;
        for (Item i : inventory) {
            if (i.equals(item)) {
                frequency++;
            }
        }

        return frequency;
    }


    // EFFECTS: adds balance and score for transaction according to the score calculation of each item
    public void transaction(String itemName) {
        for (Item i : inventory) {
            if (i.getName().equals(itemName)) {
                inventory.remove(i);
                addBalance(i.getSellPrice());
                addScore(i.scoreCalculation());
                break;
            }
        }
    }
}
