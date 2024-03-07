package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HighscoresTest {

    Highscores highscores;
    List<Cashier> temp;
    Cashier c1;
    Cashier c2;
    Cashier c3;

    @BeforeEach
    void runBefore() {
        highscores = new Highscores();
        temp = new ArrayList<>();
        c3 = new Cashier(32, 200);
        c2 = new Cashier(100, 245);
        c1 = new Cashier(532, 13);
        assertEquals(highscores.getScores().size(), 0);
    }

    @Test
    void testGetScores() {
        assertEquals(highscores.getScores().size(), 0);
        temp.add(c1);
        highscores.setScores(temp);
        assertEquals(highscores.getScores().size(), 1);
        temp.add(c2);
        temp.add(c3);
        assertEquals(highscores.getScores().size(), 3);
    }

    @Test
    void testSetScores() {
        highscores.setScores(temp);
        assertEquals(highscores.getScores().size(), 0);
        temp.add(c1);
        assertEquals(highscores.getScores().size(), 1);
        temp.add(c2);
        temp.add(c3);
        assertEquals(highscores.getScores().size(), 3);
    }
}