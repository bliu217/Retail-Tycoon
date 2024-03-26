package model;

import org.json.JSONObject;
import persistence.Writable;

import java.util.Objects;

// Represents an item in the store
public class Item implements Writable {
    private String name;
    private Integer sellPrice;
    private Integer buyPrice;

    // EFFECTS: constructs an Item with name, a customer price, and an inventory price
    public Item(String name, Integer sellPrice, Integer buyPrice) {
        this.name = name;
        this.sellPrice = sellPrice;
        this.buyPrice = buyPrice;
    }

    // EFFECTS: returns the name of item
    public String getName() {
        return name;
    }

    // EFFECTS: returns what the item costs to customers
    public Integer getSellPrice() {
        return sellPrice;
    }

    // EFFECTS: returns what the item costs to buy for inventory
    public Integer getBuyPrice() {
        return buyPrice;
    }

    // EFFECTS: calculates the score multiplier for this item with its price
    public Integer scoreCalculation() {
        if (sellPrice >= 100) {
            return sellPrice * 10;
        } else if (sellPrice >= 20) {
            return sellPrice * 5;
        } else {
            return sellPrice;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Item item = (Item) o;
        return Objects.equals(name, item.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", getName());
        return jsonObject;
    }
}
