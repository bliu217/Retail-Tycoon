package model;

import java.util.ArrayList;
import java.util.List;

// Represents a customer in the store with a name and a list of items
public class Customer {
    private String name;
    private List<Item> items;
    private Integer balance;


    // EFFECTS: constructs a customer with given name and balance with empty list of items
    public Customer(String n, Integer b) {
        this.name = n;
        this.items = new ArrayList<>();
        this.balance = b;
    }

    // EFFECTS: returns customer balance
    public Integer getBalance() {
        return balance;
    }

    // MODIFIES: this
    // EFFECTS: adds an item to the customers shopping cart
    public void addItem(Item i) {
        this.items.add(i);
    }

    // EFFECTS: returns the customer's shopping cart
    public List<Item> getItems() {
        return items;
    }

    // EFFECTS: returns the customers name
    public String getName() {
        return name;
    }


    // REQUIRES: customer items is not empty
    // MODIFIES: this
    // EFFECTS: search the customer items for the item given and removes it. returns true if item is in list else false
    public Boolean searchAndRemoveItem(String item) {
        for (Item i : items) {
            if (i.getName().equals(item)) {
                items.remove(i);
                return true;
            }
        }

        return false;
    }

    // REQUIRES: customer items is not empty
    // EFFECTS: sums and returns the total sell price of all the items in the customer's shopping cart
    public Integer itemProfits() {
        Integer sum = 0;

        for (Item i : items) {
            sum += i.getSellPrice();
        }

        return sum;
    }


}
