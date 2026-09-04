package com.splitter.engine;

import com.splitter.model.Expense;
import com.splitter.model.Group;
import com.splitter.model.Member;
import com.splitter.strategy.SplitStrategy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.splitter.exception.InvalidSplitException;

public class SettlementEngine {

    public Map<Member, Double> calculateNetBalances(Group group, Map<Expense, SplitStrategy> expenseStrategies) throws InvalidSplitException {
        Map<Member, Double> balances = new HashMap<>();

        for (Member member : group.getMembers()) {
            balances.put(member, 0.0);
        }

        for (Expense expense : group.getExpenses()) {
            SplitStrategy strategy = expenseStrategies.get(expense);
            Map<Member, Double> shares = strategy.calculateSplit(expense.getAmount(), expense.getParticipants());

            Member payer = expense.getPayer();
            balances.put(payer, balances.get(payer) + expense.getAmount());

            for (Map.Entry<Member, Double> entry : shares.entrySet()) {
                Member member = entry.getKey();
                double owedShare = entry.getValue();
                balances.put(member, balances.get(member) - owedShare);
            }
        }

        return balances;
    }

    public List<String> simplifyDebts(Map<Member, Double> balances) {
        List<String> transactions = new ArrayList<>();

        // We work on a copy so we don't mess with the original balances map
        Map<Member, Double> workingBalances = new HashMap<>(balances);

        while (true) {
            Member maxCreditor = null;
            Member maxDebtor = null;
            double maxCredit = 0.0;
            double maxDebit = 0.0;

            // Find who is owed the most (highest positive balance)
            // and who owes the most (lowest negative balance)
            for (Map.Entry<Member, Double> entry : workingBalances.entrySet()) {
                double amount = entry.getValue();
                if (amount > maxCredit) {
                    maxCredit = amount;
                    maxCreditor = entry.getKey();
                }
                if (amount < maxDebit) {
                    maxDebit = amount;
                    maxDebtor = entry.getKey();
                }
            }

            // If no one owes/is owed anything meaningful, we're done
            if (maxCreditor == null || maxDebtor == null || maxCredit < 0.01 || maxDebit > -0.01) {
                break;
            }

            double settledAmount = Math.min(maxCredit, -maxDebit);

            transactions.add(maxDebtor.getName() + " pays " + maxCreditor.getName()
                    + " Rs." + String.format("%.2f", settledAmount));

            workingBalances.put(maxCreditor, maxCredit - settledAmount);
            workingBalances.put(maxDebtor, maxDebit + settledAmount);
        }

        return transactions;
    }
}
