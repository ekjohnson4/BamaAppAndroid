package com.example.bamaappredesign;

import com.example.bamaappredesign.Tickets.Ticket;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TicketTest {
    @Test
    public void testTickets() {
        Ticket t = new Ticket(true, "Lower", "Gate 31");

        assertTrue(t.getTicket());
        assertEquals(t.getBowl(), "Lower");
        assertEquals(t.getGate(), "Gate 31");

        t.setTicket(false);
        assertFalse(t.getTicket());
    }
}
