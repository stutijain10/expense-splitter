package com.splitter.model;

import com.splitter.exception.MemberNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Group {

    private String groupName;
    private List<Member> members;
    private List<Expense> expenses;

    public Group(String groupName) {
        this.groupName = groupName;
        this.members = new ArrayList<>();
        this.expenses = new ArrayList<>();
    }

    public void addMember(Member member) {
        members.add(member);
    }

    public void addExpense(Expense expense) {
        expenses.add(expense);
    }

    public Member findMemberById(String id) throws MemberNotFoundException {
        for (Member member : members) {
            if (member.getId().equals(id)) {
                return member;
            }
        }
        throw new MemberNotFoundException("No member found with ID: " + id);
    }

    public List<Member> getMembers() {
        return members;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public String getGroupName() {
        return groupName;
    }
}
