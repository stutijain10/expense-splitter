package com.splitter.model;

import java.util.List;

public class Expense {
    private String description;
    private double amount;
    private Member payer;
    private List<Member> participants;

    public Expense(String description, double amount, Member payer, List<Member> participants) {
        this.description = description;
        this.amount = amount;
        this.payer = payer;
        this.participants = participants;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    public Member getPayer() {
        return payer;
    }

    public List<Member> getParticipants() {
        return participants;
    }
}
