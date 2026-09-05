# Problem Statement

## Problem Statement
Splitting shared expenses among a group of people — such as friends on a trip, roommates, or colleagues sharing a meal — is a common real-world problem. Manually tracking who paid what and who owes whom becomes confusing and error-prone as the number of expenses and participants grows, often resulting in more transactions than necessary to settle up.

## Scope of the Project
This project implements a command-line based Expense Splitter application in Java. It allows a group of people to record shared expenses, choose how each expense should be split (equally, by percentage, or by exact amounts), and calculates the minimum number of transactions required to settle all debts within the group using a greedy debt-simplification algorithm. The scope is limited to a single in-memory session per run and does not include persistent storage, multi-currency support, or a graphical interface, in line with the CLI-based requirement of this evaluation.

## Target Users
- Groups of friends or family splitting costs during trips or outings
- Roommates sharing recurring household expenses
- Colleagues splitting costs for team lunches, gifts, or shared purchases
- Anyone needing a simple, no-signup way to fairly divide a shared bill

## High-Level Features
- Add members to an expense-sharing group
- Record expenses with three flexible split methods: Equal, Percentage-based, and Exact-amount
- View real-time balances showing who owes money and who is owed money
- Automatically calculate the minimum number of transactions needed to settle all debts
- Export a settlement report to a text file
- Robust input validation and custom exception handling to prevent invalid data and crashes