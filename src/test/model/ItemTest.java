package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {

    Item twenty;
    Item another;
    Item twentyFive;
    Item hunnit;
    Item overHunnit;

    @BeforeEach
    void runBefore() {
        overHunnit = new Item("Item", 888, 888);
        hunnit = new Item("Item", 100, 100);
        twentyFive = new Item("Item", 25, 25);
        twenty = new Item("Item", 20, 20);
        another = new Item("Another", 5, 5);
    }

    @Test
    void testItemConstructor() {
        assertEquals(twenty.getName(), "Item");
        assertEquals(twenty.getBuyPrice(), 20);
        assertEquals(twenty.getSellPrice(), 20);
    }

    @Test
    void testGetName() {
        assertEquals(twenty.getName(), "Item");
        assertEquals(another.getName(), "Another");
    }

    @Test
    void testGetSellPrice() {
        assertEquals(twenty.getSellPrice(), 20);
        assertEquals(another.getSellPrice(), 5);
    }

    @Test
    void testGetBuyPrice() {
        assertEquals(twenty.getBuyPrice(), 20);
        assertEquals(another.getBuyPrice(), 5);
    }

    @Test
    void testScoreCalculation() {
        assertEquals(another.scoreCalculation(), 5);
        assertEquals(twenty.scoreCalculation(), 100);
        assertEquals(twentyFive.scoreCalculation(), 125);
        assertEquals(hunnit.scoreCalculation(), 1000);
        assertEquals(overHunnit.scoreCalculation(), 8880);
    }

    @Test
    void testEquals() {
        assertNotEquals(hunnit, null);
        assertEquals(hunnit, hunnit);
        assertEquals(hunnit, overHunnit);
        assertNotEquals(hunnit, another);
    }

    @Test
    void testHashCode() {
        assertEquals(hunnit.hashCode(), overHunnit.hashCode());
        assertNotEquals(hunnit.hashCode(), another.hashCode());
    }
}