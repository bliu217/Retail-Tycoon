package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Represents the user with their score, name, and an inventory list of items
public class Cashier implements Writable {

    private Integer score;
    private String saveName;
    private Integer balance;
    private List<Item> inventory;
    private List<Item> itemList;


    // EFFECTS: Construct a new cashier with score of 0, no saveName, a balance of START_BALANCE,
    //          and an empty inventory.

    public Cashier(int score, int balance) {
        this.score = score;
        this.saveName = null;
        this.balance = balance;
        this.inventory = new ArrayList<>();
        initItems();
    }

    // REQUIRES: amount > 0
    // MODIFIES: this
    // EFFECTS: adds score by amount
    public void addScore(int amount) {
        this.score += amount;
    }

    // MODIFIES: this
    // EFFECTS: sets the saveName of the cashier
    public void setSaveName(String saveName) {
        this.saveName = saveName;
    }

    // EFFECTS: returns the saveName of the cashier
    public String getSaveName() {
        return saveName;
    }

    public void addInventory(Item i) {
        this.inventory.add(i);
    }

    // REQUIRES: quantity > 0
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

    // REQUIRES: amount > 0
    // MODIFIES: this
    // EFFECTS: increases cashier balance by amount
    public void addBalance(int amount) {
        this.balance += amount;
    }

    // REQUIRES: amount > 0
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

    // REQUIRES: !inventory.isEmpty()
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

    // REQUIRES: item with item name itemName must be in inventory
    // MODIFIES: this
    // EFFECTS: adds balance and score for transaction according to the score calculation of each item
    public void transaction(String itemName) {
        for (Item i : inventory) {
            if (i.getName().equals(itemName)) {
                this.inventory.remove(i);
                addBalance(i.getSellPrice());
                addScore(i.scoreCalculation());
                break;
            }
        }
    }

    // EFFECTS: initializes the items used in the game
    public List<Item> initItems() {
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

        return itemList;

    }

    // REQUIRES: item with given name exists in the list of items
    // EFFECTS: returns the properties of the item with the given item
    public Item stringToItem(String name) {
        for (Item i : itemList) {
            if (i.getName().equals(name)) {
                return i;
            }
        }

        return null;
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("saveName", saveName);
        jsonObject.put("score", score);
        jsonObject.put("balance", balance);
        jsonObject.put("inventory", inventoryToJson());

        return jsonObject;
    }

    // EFFECTS: returns the cashier's inventory as a JSON array.
    private JSONArray inventoryToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Item i : inventory) {
            jsonArray.put(i.toJson());
        }

        return jsonArray;
    }

}
