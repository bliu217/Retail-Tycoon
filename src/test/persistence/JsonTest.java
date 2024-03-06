package persistence;

import model.Item;

import static org.junit.jupiter.api.Assertions.*;

public class JsonTest {
    protected void checkItem(Item i, String name, int sellPrice, int buyPrice) {
        assertEquals(i.getName(), name);
        assertEquals(i.getSellPrice(), sellPrice);
        assertEquals(i.getBuyPrice(), buyPrice);
    }
}
