# Problem Statement

## Problem Statement
Splitting expenses within a group — whether it's a trip with friends, a shared flat, or a team lunch — is something most people deal with regularly, and it's surprisingly easy to get wrong when done manually. People forget who paid for what, end up doing more transactions than necessary to settle up, or just avoid dealing with it altogether because it feels tedious. This project tries to solve that with a simple command-line tool.

## Scope of the Project
This is a Java-based CLI application that lets a group record shared expenses, pick how each one should be split, and then figure out the smallest possible set of transactions needed to settle everyone's balances. It supports three ways of splitting a bill — equally, by percentage, or by exact custom amounts — and uses a greedy algorithm to simplify the final settlement. The project is intentionally scoped to a single session (no saved data between runs), no database, and no GUI, since the goal was to build something CLI-executable that demonstrates core OOP principles rather than a production-ready app.

## Target Users
- Friends splitting costs on a trip
- Roommates dividing shared household bills
- Coworkers splitting a team lunch or a shared gift
- Basically anyone who wants a quick way to divide a bill fairly without installing a full app or signing up for anything

## High-Level Features
- Add people to a group
- Record an expense and choose between Equal, Percentage, or Exact Amount splitting
- Check who currently owes money and who's owed money
- Get a settlement plan that minimizes the number of payments needed
- Export the final settlement to a text file
- Input validation and custom exceptions so bad input doesn't crash the program or silently produce wrong numbers