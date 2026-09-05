# Expense Splitter (CLI)

A Java command-line application that helps a group of people split shared expenses and figure out who owes whom. Think of it like a simplified, from-scratch version of Splitwise — I built this to apply the core OOP concepts from this course to something that actually solves a problem I've run into myself while splitting bills with friends.

## Features

- Add members to a group
- Add an expense and split it three different ways:
  - **Equal Split** — everyone pays the same share
  - **Percentage Split** — each person pays a set percentage of the bill
  - **Exact Amount Split** — each person pays a manually entered amount
- Check current balances (who's owed money, who owes money)
- Settle up automatically — the app works out the fewest transactions needed to clear everyone's debts, instead of listing every single owe-relationship separately
- Export the settlement summary to a text file
- Handles bad input and invalid splits gracefully instead of crashing

## Technologies Used

- Java (built and tested on JDK 21, though anything from JDK 8 onward should work fine)
- No external libraries — everything here is plain core Java

## Project Structure
```
expense-splitter/
├── src/
│ └── com/
│ └── splitter/
│ ├── Main.java # Entry point, runs the CLI menu
│ ├── model/
│ │ ├── Member.java # A person in the group
│ │ ├── Expense.java # A single expense record
│ │ └── Group.java # Holds the members and expenses together
│ ├── strategy/
│ │ ├── SplitStrategy.java # Interface all split types follow
│ │ ├── EqualSplit.java
│ │ ├── PercentageSplit.java
│ │ └── ExactAmountSplit.java
│ ├── exception/
│ │ ├── InvalidSplitException.java
│ │ └── MemberNotFoundException.java
│ └── engine/
│ └── SettlementEngine.java # Does the balance math and debt simplification
└── README.md
```

## Prerequisites

You'll need Java's JDK installed. To check if you already have it, run:
```
java -version
javac -version
```

If you get a version number back, you're good to go. If not, grab it from [Oracle's JDK downloads](https://www.oracle.com/java/technologies/downloads/) or use [OpenJDK](https://adoptium.net/) instead.

## Setup & Installation
1. Clone the repo:
```
git clone https://github.com/stutijain10/expense-splitter.git
```

2. Move into the source folder — this is important, since all the commands below assume you're here:
```
cd expense-splitter/src
```

## How to Compile

From inside `src`, just run:
```
javac com/splitter/Main.java
```
You only need to compile `Main.java` — Java automatically finds and compiles everything else it depends on (all the model, strategy, exception, and engine classes), so you don't need to list every file manually.

## How to Run

Still inside `src`:
```
java com.splitter.Main
```

## Usage Example

Here's roughly what a session looks like:
```
Enter your group name: Goa Trip

===== Expense Splitter (Goa Trip) =====

Add Member
Add Expense
View Balances
Settle Up
Export Settlement Report
Exit
Choose an option: 1
Enter member ID: m1
Enter member name: Stuti
Stuti added successfully.

Choose an option: 1
Enter member ID: m2
Enter member name: Rahul
Rahul added successfully.

Choose an option: 2
Enter expense description: Dinner
Enter total amount: 1000
Enter payer's member ID: m1
Enter participant IDs separated by commas (e.g. m1,m2,m3): m1,m2
Choose split type: 1. Equal 2. Percentage 3. Exact Amount
1
Expense added successfully.

Choose an option: 3
--- Current Balances ---
Stuti is owed Rs.500.00
Rahul owes Rs.500.00

Choose an option: 4
--- Settlement Plan ---
Rahul pays Stuti Rs.500.00

Choose an option: 5
Report exported successfully to settlement_report.txt

Choose an option: 6
Goodbye!
```

## Testing Instructions

I tested the app manually across a bunch of scenarios rather than writing automated tests, since the focus here was on applying OOP concepts correctly:

1. Adding members and checking they show up correctly afterward.
2. Equal split with 2+ people — checked the math divides evenly.
3. Percentage split — tried both valid percentages (adding to 100%) and deliberately wrong ones (like 40% + 40%) to confirm the app catches the mismatch and throws `InvalidSplitException` with a clear message instead of just producing wrong numbers.
4. Same idea for Exact Amount split — mismatched totals get rejected.
5. Entered a member ID that doesn't exist as a payer/participant — confirmed `MemberNotFoundException` catches it cleanly.
6. Tried entering only invalid participant IDs for an expense — the app cancels instead of trying to split among nobody.
7. Typed letters instead of numbers at various prompts — the app re-asks instead of crashing.
8. Checked that "Settle Up" actually gives the minimum number of transactions, not just a raw list of who-owes-who per expense.
9. Ran the export feature and opened the resulting `settlement_report.txt` to confirm it's formatted properly.

If you want to try these yourself, just follow the run instructions above and repeat the same scenarios with your own numbers.

## Design Concepts Demonstrated

| Concept | Where it shows up |
|---|---|
| **Encapsulation** | `Member`, `Expense`, and `Group` all keep their fields private and expose them only through getters |
| **Abstraction** | The `SplitStrategy` interface defines *what* a split calculation should return, without caring *how* each type does it |
| **Polymorphism** | `EqualSplit`, `PercentageSplit`, and `ExactAmountSplit` all implement `SplitStrategy` in their own way, but `SettlementEngine` treats them identically |
| **Custom Exceptions** | `InvalidSplitException` catches splits that don't mathematically add up; `MemberNotFoundException` catches bad member references |
| **Collections** | `ArrayList` and `HashMap` are used throughout to store members, expenses, and balances |
| **File I/O** | The settlement report export uses `FileWriter` to write results to disk |

## Known Limitations

- Nothing is saved between runs — once you exit, all data is gone. Adding persistence felt out of scope for what this course was focused on.
- Input validation covers the main failure points (bad numbers, missing members, invalid splits), but I haven't tried to bulletproof every possible weird input.
- Currency is hardcoded to Indian Rupees, shown as "Rs." — wasn't planning to make this multi-currency for a CLI demo project.
- Because of how floating-point math works in Java, equal splits can occasionally be off by a fraction of a rupee. It doesn't affect whether the settlement is correct, just a quirk worth mentioning.

## Author

Stuti Jain