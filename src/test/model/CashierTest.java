package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CashierTest {

    Cashier cashier;
    Item equal;
    Item item;
    Item ice;
    List<Item> empty;

    @BeforeEach
    void runBefore() {
        cashier = new Cashier();
        equal = new Item("Equal", 10, 10);
        item = new Item("Item", 1, 1);
        ice = new Item("Ice", 5, 4);
        empty = new ArrayList<>();
        assertEquals(cashier.getBalance(), 100);
        assertTrue(cashier.getInventory().isEmpty());
    }

    @Test
    void testAddScore() {
        assertEquals(cashier.getScore(), 0);
        cashier.addScore(100);
        assertEquals(cashier.getScore(), 100);
        cashier.addScore(200);
        assertEquals(cashier.getScore(), 300);
    }

    @Test
    void testSetSaveName() {
        assertNull(cashier.getSaveName());
        cashier.setSaveName("Bob");
        assertEquals(cashier.getSaveName(), "Bob");
        cashier.setSaveName("Jeremy");
        assertEquals(cashier.getSaveName(), "Jeremy");
    }

    @Test
    void testGetSaveName() {
        assertNull(cashier.getSaveName());
        cashier.setSaveName("Bob");
        assertEquals(cashier.getSaveName(), "Bob");
        cashier.setSaveName("Jeremy");
        assertEquals(cashier.getSaveName(), "Jeremy");
    }

    @Test
    void testPurchaseInventoryEqualBalance() {

        assertTrue(cashier.purchaseInventory(equal, 10));
        assertEquals(cashier.getBalance(), 0);
        assertEquals(cashier.getInventory().size(), 10);
    }

    @Test
    void testPurchaseInventoryLessThan() {
        assertTrue(cashier.purchaseInventory(equal, 1));
        assertEquals(cashier.getBalance(), 90);
        assertEquals(cashier.getInventory().size(), 1);
    }

    @Test
    void testPurchaseInventoryFail() {
        assertFalse(cashier.purchaseInventory(equal, 100));
        assertEquals(cashier.getBalance(), 100);
        assertEquals(cashier.getInventory().size(), 0);
    }

    @Test
    void testAddBalanceOne() {
        cashier.addBalance(1);
        assertEquals(cashier.getBalance(), 101);
    }

    @Test
    void testAddBalanceGreater() {
        cashier.addBalance(100);
        assertEquals(cashier.getBalance(), 200);
    }

    @Test
    void testRemoveBalanceOne() {
        cashier.removeBalance(1);
        assertEquals(cashier.getBalance(), 99);
    }

    @Test
    void testRemoveBalanceGreater() {
        cashier.removeBalance(10);
        assertEquals(cashier.getBalance(), 90);
    }

    @Test
    void testGetBalance() {
        cashier.addBalance(10);
        assertEquals(cashier.getBalance(), 110);
    }

    @Test
    void testGetScore() {
        assertEquals(cashier.getScore(), 0);
        cashier.addScore(1);
        assertEquals(cashier.getScore(), 1);
        cashier.addScore(40);
        assertEquals(cashier.getScore(), 41);
    }

    @Test
    void testGetInventory() {
        assertEquals(cashier.getInventory(), empty);
        cashier.purchaseInventory(equal, 1);
        empty.add(equal);
        assertEquals(cashier.getInventory(), empty);
        cashier.purchaseInventory(equal, 2);
        empty.add(equal);
        empty.add(equal);
        assertEquals(cashier.getInventory(), empty);
    }

    @Test
    void testItemFrequencyOne() {
        cashier.purchaseInventory(equal, 1);
        assertEquals(cashier.itemFrequency(equal), 1);
    }

    @Test
    void testItemFrequencyMultiple() {
        cashier.purchaseInventory(equal, 1);
        cashier.purchaseInventory(item, 2);
        cashier.purchaseInventory(equal, 1);
        cashier.purchaseInventory(item, 1);
        cashier.purchaseInventory(equal, 1);
        cashier.purchaseInventory(equal, 1);
        assertEquals(cashier.itemFrequency(equal), 4);
        assertEquals(cashier.itemFrequency(item), 3);
    }

    @Test
    void testItemFrequencyZero() {
        cashier.purchaseInventory(equal, 1);
        assertEquals(cashier.itemFrequency(ice), 0);
        cashier.purchaseInventory(item, 2);
        assertEquals(cashier.itemFrequency(ice), 0);
    }

    @Test
    void testTransaction() {
        cashier.purchaseInventory(equal, 1);
        cashier.transaction("Equal");
        assertEquals(cashier.getInventory(), empty);
        assertEquals(cashier.getBalance(), 100);
        assertEquals(cashier.getScore(), 10);
        cashier.purchaseInventory(equal, 1);
        cashier.purchaseInventory(ice, 1);
        cashier.purchaseInventory(ice, 1);
        empty.add(equal);
        empty.add(ice);
        cashier.transaction("Ice");
        assertEquals(cashier.getInventory(), empty);
        assertEquals(cashier.getBalance(), 87);
        assertEquals(cashier.getScore(), 15);
    }
}