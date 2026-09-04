package com.splitter.model;

public class Member {
    private String id;
    private String name;
    private double balance;

    public Member(String id, String name) {
        this.id = id;
        this.name = name;
        this.balance = 0.0;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public void adjustBalance(double amount) {
        this.balance += amount;
    }
}