package com.example.bamaappredesign.Tickets;

public class Ticket {
    private boolean Ticket;
    private String Bowl;
    private String Gate;

    public Ticket(boolean ticket, String bowl, String gate) {
        Ticket = ticket;
        Bowl = bowl;
        Gate = gate;
    }

    public String getBowl() {
        return Bowl;
    }

    public String getGate() {
        return Gate;
    }

    public void setTicket(boolean ticket) {
        Ticket = ticket;
    }

    public boolean getTicket() {
        return Ticket;
    }
}
