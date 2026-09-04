package com.splitter.strategy;

import com.splitter.model.Member;
import com.splitter.exception.InvalidSplitException;
import java.util.List;
import java.util.Map;

public interface SplitStrategy {
    Map<Member, Double> calculateSplit(double totalAmount, List<Member> participants) throws InvalidSplitException;
}