package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {
    Customer customer;
    List<Item> items;
    Item banana;
    Item dogFood;
    Item water;

    @BeforeEach
    void runBefore() {
        customer = new Customer("Bernard", 69);
        banana = new Item("Banana", 3, 1);
        dogFood = new Item("DogFood", 24, 14);
        water = new Item("Goodwater", 4, 1);
        items = new ArrayList<>();
    }

    @Test
    void testCustomerConstructor() {
        assertEquals(customer.getName(), "Bernard");
        assertEquals(customer.getBalance(), 69);
        assertEquals(customer.getItems(), items);
    }

    @Test
    void testGetBalance() {
        assertEquals(customer.getBalance(), 69);
    }

    @Test
    void testAddItems() {
        customer.addItem(banana);
        items.add(banana);
        assertEquals(customer.getItems(), items);
        customer.addItem(banana);
        customer.addItem(water);
        items.add(banana);
        items.add(water);
        assertEquals(customer.getItems(), items);
    }

    @Test
    void testGetItems() {
        customer.addItem(banana);
        items.add(banana);
        assertEquals(customer.getItems(), items);
        customer.addItem(banana);
        customer.addItem(water);
        items.add(banana);
        items.add(water);
        assertEquals(customer.getItems(), items);
    }

    @Test
    void testGetName() {
        assertEquals(customer.getName(), "Bernard");
    }

    @Test
    void testSearchAndRemoveItemTrue() {
        customer.addItem(banana);
        assertTrue(customer.searchAndRemoveItem("Banana"));
        assertEquals(customer.getItems(), items);
        customer.addItem(banana);
        customer.addItem(water);
        customer.addItem(water);
        assertTrue(customer.searchAndRemoveItem("Goodwater"));
        items.add(banana);
        items.add(water);
        assertEquals(customer.getItems(), items);
    }

    @Test
    void testSearchAndRemoveItemFalse() {
        customer.addItem(banana);
        assertFalse(customer.searchAndRemoveItem("Water"));
        items.add(banana);
        assertEquals(customer.getItems(), items);
        customer.addItem(banana);
        customer.addItem(water);
        customer.addItem(water);
        assertFalse(customer.searchAndRemoveItem("Bob"));
        items.add(banana);
        items.add(water);
        items.add(water);
        assertEquals(customer.getItems(), items);
    }

    @Test
    void testItemProfits() {
        customer.addItem(banana);
        assertEquals(customer.itemProfits(), 3);
        customer.addItem(banana);
        customer.addItem(water);
        customer.addItem(dogFood);
        assertEquals(customer.itemProfits(), 34);
    }
}