package model;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    private String name;
    private List<Item> items;
    private Integer balance;

    public Customer(String n, Integer b) {
        this.name = n;
        this.items = new ArrayList<>();
        this.balance = b;
    }

    public void addItem(Item i) {
        this.items.add(i);
    }

    public List<Item> getItems() {
        return items;
    }

    public String getName() {
        return name;
    }

    public Integer getBalance() {
        return balance;
    }

    public Boolean searchAndRemoveItem(String item) {
        for (Item i : items) {
            if (i.getName().equals(item)) {
                items.remove(i);
                return true;
            }
        }

        return false;
    }

    public Integer itemProfits() {
        Integer sum = 0;

        for (Item i : items) {
            sum += i.getSellPrice();
        }

        return sum;
    }


}
