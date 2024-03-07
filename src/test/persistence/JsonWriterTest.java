package persistence;

import model.Cashier;
import model.Highscores;
import model.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest extends JsonTest{
    Cashier cashier;
    JsonWriter writer;
    JsonReader reader;
    Highscores highscores;

    @BeforeEach
    void runBefore() {
        highscores = new Highscores();
    }

    @Test
    void testInvalidFile() {
        try {
            Cashier c = new Cashier(0, 100);
            c.setSaveName("name");
            JsonWriter jsonWriter = new JsonWriter("./data/some\0dest:ination.json");
            jsonWriter.open();
            fail("Expected IOException");
        } catch (IOException e){
            //pass
        }
    }

    @Test
    void testWriterStarterCashier() {
        cashier = new Cashier(0, 100);
        cashier.setSaveName("name");
        writer = new JsonWriter("./data/testReaderStarterCashier.json");
        reader = new JsonReader("./data/testReaderStarterCashier.json");

        try {
            writer.open();
            writer.write(cashier, highscores);
            writer.close();

            cashier = reader.read();
            assertEquals(cashier.getSaveName(), "name");
            assertEquals(cashier.getScore(), 0);
            assertEquals(cashier.getBalance(), 100);
            assertEquals(cashier.getInventory().size(), 0);
        } catch (FileNotFoundException e) {
            fail("File exists, not expecting exception");
        } catch (IOException e) {
            fail("Not expecting IOException");
        }
    }

    @Test
    void testWriterGeneralCashier() {
        cashier = new Cashier(50, 888);
        cashier.setSaveName("name");
        Item banana = new Item("Banana", 3, 1);
        Item notebook = new Item("Notebook", 16, 9);
        Item microwave = new Item("Microwave", 40, 30);
        cashier.addInventory(banana);
        cashier.addInventory(banana);
        cashier.addInventory(notebook);
        cashier.addInventory(microwave);
        writer = new JsonWriter("./data/testReaderGeneralCashier.json");
        reader = new JsonReader("./data/testReaderGeneralCashier.json");

        try {
            writer.open();
            writer.write(cashier, highscores);
            writer.close();

            cashier = reader.read();
            assertEquals(cashier.getSaveName(), "name");
            assertEquals(cashier.getScore(), 50);
            assertEquals(cashier.getBalance(), 888);
            assertEquals(cashier.getInventory().size(), 4);

            List<Item> inventory = cashier.getInventory();

            checkItem(inventory.get(0), "Banana", 3,1);
            checkItem(inventory.get(1), "Banana", 3,1);
            checkItem(inventory.get(2), "Notebook", 16,9);
            checkItem(inventory.get(3), "Microwave", 40,30);
        } catch (FileNotFoundException e) {
            fail("File exists, not expecting exception");
        } catch (IOException e) {
            fail("Not expecting IOException");
        }
    }


}