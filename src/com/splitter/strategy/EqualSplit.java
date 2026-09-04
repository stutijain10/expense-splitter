package com.splitter.strategy;

import com.splitter.exception.InvalidSplitException;
import com.splitter.model.Member;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EqualSplit implements SplitStrategy {

    @Override
    public Map<Member, Double> calculateSplit(double totalAmount, List<Member> participants) throws InvalidSplitException {
        Map<Member, Double> shares = new HashMap<>();
        double perPersonShare = totalAmount / participants.size();

        for (Member member : participants) {
            shares.put(member, perPersonShare);
        }

        return shares;
    }
}
