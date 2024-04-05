package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Unit tests for the Event class
 */
public class EventTest {
	private Event e;
	private Date d;

	@BeforeEach
	public void runBefore() {
		e = new Event("Added Item to Inventory");
		d = Calendar.getInstance().getTime();
	}
	
	@Test
	public void testEvent() {
		assertEquals("Added Item to Inventory", e.getDescription());
        assertEquals(d.getYear(), e.getDate().getYear());
        assertEquals(d.getMonth(), e.getDate().getMonth());
		assertEquals(d.getDay(), e.getDate().getDay());
        assertEquals(d.getHours(), e.getDate().getHours());
        assertEquals(d.getMinutes(), e.getDate().getMinutes());
	}

	@Test
	public void testToString() {
		assertEquals(d.toString() + "\n" + "Added Item to Inventory", e.toString());
	}

    @Test
    public void testEquals() {
        assertFalse(e.equals(d));
        assertFalse(e.equals(null));
        assertEquals(e.hashCode(), e.hashCode());
    }
}
