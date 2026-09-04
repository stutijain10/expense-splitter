package com.splitter.strategy;

import com.splitter.model.Member;
import com.splitter.exception.InvalidSplitException;
import java.util.List;
import java.util.Map;

public class ExactAmountSplit implements SplitStrategy {

    private Map<Member, Double> exactAmounts;

    public ExactAmountSplit(Map<Member, Double> exactAmounts) {
        this.exactAmounts = exactAmounts;
    }

    @Override
    public Map<Member, Double> calculateSplit(double totalAmount, List<Member> participants) throws InvalidSplitException {
        double totalExact = 0.0;
        for (Double value : exactAmounts.values()) {
            totalExact += value;
        }

        if (Math.abs(totalExact - totalAmount) > 0.01) {
            throw new InvalidSplitException("Exact amounts must add up to the total bill (" + totalAmount + "). Current total: " + totalExact);
        }

        return exactAmounts;
    }
}