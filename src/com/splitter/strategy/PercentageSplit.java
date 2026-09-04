package com.splitter.strategy;

import com.splitter.model.Member;
import com.splitter.exception.InvalidSplitException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PercentageSplit implements SplitStrategy {

    private Map<Member, Double> percentages;

    public PercentageSplit(Map<Member, Double> percentages) {
        this.percentages = percentages;
    }

    @Override
    public Map<Member, Double> calculateSplit(double totalAmount, List<Member> participants) throws InvalidSplitException {
        double totalPercent = 0.0;
        for (Member member : participants) {
            totalPercent += percentages.get(member);
        }

        if (Math.abs(totalPercent - 100.0) > 0.01) {
            throw new InvalidSplitException("Percentages must add up to 100%. Current total: " + totalPercent + "%");
        }

        Map<Member, Double> shares = new HashMap<>();

        for (Member member : participants) {
            double percent = percentages.get(member);
            double share = (percent / 100.0) * totalAmount;
            shares.put(member, share);
        }

        return shares;
    }
}