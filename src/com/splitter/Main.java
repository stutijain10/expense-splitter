package com.splitter;

import com.splitter.engine.SettlementEngine;
import com.splitter.model.Group;
import com.splitter.model.Member;
import com.splitter.model.Expense;
import com.splitter.strategy.SplitStrategy;
import com.splitter.strategy.EqualSplit;
import com.splitter.strategy.PercentageSplit;
import com.splitter.strategy.ExactAmountSplit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import com.splitter.exception.InvalidSplitException;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.print("Enter your group name: ");
        String groupName = scanner.nextLine();
        Group group = new Group(groupName);
        Map<Expense, SplitStrategy> expenseStrategies = new HashMap<>();
        SettlementEngine engine = new SettlementEngine();

        while (running) {
            System.out.println("\n===== Expense Splitter (" + group.getGroupName() + ") =====");
            System.out.println("1. Add Member");
            System.out.println("2. Add Expense");
            System.out.println("3. View Balances");
            System.out.println("4. Settle Up");
            System.out.println("5. Export Settlement Report");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.print("Enter member ID: ");
                    String id = scanner.nextLine();
                    System.out.print("Enter member name: ");
                    String name = scanner.nextLine();
                    Member newMember = new Member(id, name);
                    group.addMember(newMember);
                    System.out.println(name + " added successfully.");
                    break;
                case 2:
                    System.out.print("Enter expense description: ");
                    String description = scanner.nextLine();

                    double amount = readValidDouble(scanner, "Enter total amount: ");

                    System.out.print("Enter payer's member ID: ");
                    String payerId = scanner.nextLine();
                    Member payer = null;
                    try {
                        payer = group.findMemberById(payerId);
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                        break;
                    }

                    System.out.print("Enter participant IDs separated by commas (e.g. m1,m2,m3): ");
                    String[] participantIds = scanner.nextLine().split(",");
                    List<Member> participants = new ArrayList<>();
                    for (String pid : participantIds) {
                        try {
                            participants.add(group.findMemberById(pid.trim()));
                        } catch (Exception e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                    }

                    if (participants.isEmpty()) {
                        System.out.println("No valid participants found. Expense cancelled.");
                        break;
                    }

                    System.out.println("Choose split type: 1. Equal  2. Percentage  3. Exact Amount");
                    int splitChoice = Integer.parseInt(scanner.nextLine());

                    SplitStrategy strategy = null;

                    if (splitChoice == 1) {
                        strategy = new EqualSplit();
                    } else if (splitChoice == 2) {
                        Map<Member, Double> percentages = new HashMap<>();
                        for (Member p : participants) {
                            double pct = readValidDouble(scanner, "Enter percentage for " + p.getName() + ": ");
                            percentages.put(p, pct);
                        }
                        strategy = new PercentageSplit(percentages);
                    } else if (splitChoice == 3) {
                        Map<Member, Double> exactAmounts = new HashMap<>();
                        for (Member p : participants) {
                            double amt = readValidDouble(scanner, "Enter exact amount for " + p.getName() + ": ");
                            exactAmounts.put(p, amt);
                        }
                        strategy = new ExactAmountSplit(exactAmounts);
                    } else {
                        System.out.println("Invalid split type, expense not added.");
                        break;
                    }

                    Expense expense = new Expense(description, amount, payer, participants);
                    group.addExpense(expense);
                    expenseStrategies.put(expense, strategy);
                    System.out.println("Expense added successfully.");
                    break;

                case 3:
                    try {
                        Map<Member, Double> balances = engine.calculateNetBalances(group, expenseStrategies);
                        System.out.println("\n--- Current Balances ---");
                        for (Map.Entry<Member, Double> entry : balances.entrySet()) {
                            String status = entry.getValue() >= 0 ? "is owed" : "owes";
                            System.out.println(entry.getKey().getName() + " " + status + " Rs."
                                    + String.format("%.2f", Math.abs(entry.getValue())));
                        }
                    } catch (InvalidSplitException e) {
                        System.out.println("Error calculating balances: " + e.getMessage());
                    }
                    break;
                
                case 4:
                    try {
                        Map<Member, Double> finalBalances = engine.calculateNetBalances(group, expenseStrategies);
                        List<String> settlements = engine.simplifyDebts(finalBalances);
                        System.out.println("\n--- Settlement Plan ---");
                        if (settlements.isEmpty()) {
                            System.out.println("Everyone is settled up already!");
                        } else {
                            for (String transaction : settlements) {
                                System.out.println(transaction);
                            }
                        }
                    } catch (InvalidSplitException e) {
                        System.out.println("Error calculating settlement: " + e.getMessage());
                    }
                    break;

                case 5:
                    try {
                        Map<Member, Double> exportBalances = engine.calculateNetBalances(group, expenseStrategies);
                        List<String> exportSettlements = engine.simplifyDebts(exportBalances);

                        FileWriter writer = new FileWriter("settlement_report.txt");
                        writer.write("Settlement Report for: " + group.getGroupName() + "\n");
                        writer.write("=================================\n\n");
                        writer.write("Balances:\n");
                        for (Map.Entry<Member, Double> entry : exportBalances.entrySet()) {
                            String status = entry.getValue() >= 0 ? "is owed" : "owes";
                            writer.write(entry.getKey().getName() + " " + status + " Rs."
                                    + String.format("%.2f", Math.abs(entry.getValue())) + "\n");
                        }
                        writer.write("\nSettlement Plan:\n");
                        if (exportSettlements.isEmpty()) {
                            writer.write("Everyone is settled up already!\n");
                        } else {
                            for (String transaction : exportSettlements) {
                                writer.write(transaction + "\n");
                            }
                        }
                        writer.close();
                        System.out.println("Report exported successfully to settlement_report.txt");
                    } catch (InvalidSplitException e) {
                        System.out.println("Error generating report: " + e.getMessage());
                    } catch (IOException e) {
                        System.out.println("Error writing file: " + e.getMessage());
                    }
                    break;
                case 6:
                    running = false;
                    System.out.println("Goodbye!");
                    break;

                default:
                    System.out.println("Invalid option, try again.");
            }
        }

        scanner.close();
    }


    private static double readValidDouble(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("That's not a valid number. Please try again.");
            }
        }
    }
}