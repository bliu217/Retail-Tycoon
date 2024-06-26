package persistence;

import model.Cashier;
import model.Highscores;
import model.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTest{

    JsonReader reader;
    Cashier cashier;
    Highscores highscores;
    List<Cashier> temp;

    @BeforeEach
    void runBefore() {
        highscores = new Highscores();
        temp = new ArrayList<>();
    }

    @Test
    void testReaderInvalidFile() {
        reader = new JsonReader("./data/someFile.json");

        try {
            cashier = reader.read();
            fail("Expected IOException");
        } catch (IOException e) {
            //pass
        }
    }

    @Test
    void testReaderStarterCashier() {
        reader = new JsonReader("./data/testReaderStarterCashier.json");
        try {
            cashier = reader.read();
            highscores = reader.getHighscores();
            temp = highscores.getScores();
        } catch (IOException e) {
            fail("Did not expect exception");
        }

        assertEquals(cashier.getSaveName(), "name");
        assertEquals(cashier.getScore(), 0);
        assertEquals(cashier.getBalance(), 100);
        assertEquals(cashier.getInventory().size(), 0);
        assertEquals(temp.size(), 0);
    }

    @Test
    void testReaderGeneralCashier() {
        reader = new JsonReader("./data/testReaderGeneralCashier.json");
        try {
            cashier = reader.read();
            highscores = reader.getHighscores();
            temp = highscores.getScores();
        } catch (IOException e) {
            fail("Did not expect exception");
        }

        assertEquals(cashier.getSaveName(), "name");
        assertEquals(cashier.getScore(), 50);
        assertEquals(cashier.getBalance(), 888);
        assertEquals(cashier.getInventory().size(), 4);
        assertEquals(temp.size(), 1);

        List<Item> inventory = cashier.getInventory();

        checkItem(inventory.get(0), "Banana", 3,1);
        checkItem(inventory.get(1), "Banana", 3,1);
        checkItem(inventory.get(2), "Notebook", 16,9);
        checkItem(inventory.get(3), "Microwave", 40,30);

    }
}