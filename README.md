# Expense Splitter (CLI)

A command-line Java application for splitting group expenses fairly and settling debts with the minimum number of transactions — similar to how apps like Splitwise work, but built from scratch to demonstrate core Object-Oriented Programming concepts.

## Features

- Add members to a group
- Add expenses with three different split types:
  - **Equal Split** — divides the amount evenly among participants
  - **Percentage Split** — each participant pays a specified percentage
  - **Exact Amount Split** — each participant pays a manually specified amount
- View current balances (who owes money, who is owed money)
- Settle up — calculates the minimum number of transactions needed to clear all debts using a greedy debt-simplification algorithm
- Export a settlement report to a text file
- Input validation and custom exception handling to prevent crashes and invalid data

## Technologies Used

- Java (JDK 21 — tested and confirmed working on this version; JDK 8+ should also work)
- No external libraries or frameworks — built entirely with core Java

## Project Structure

```
expense-splitter/
├── src/
│   └── com/
│       └── splitter/
│           ├── Main.java              # Entry point, CLI menu
│           ├── model/
│           │   ├── Member.java        # Represents a person in the group
│           │   ├── Expense.java       # Represents a single expense
│           │   └── Group.java         # Holds members and expenses together
│           ├── strategy/
│           │   ├── SplitStrategy.java     # Interface defining the split contract
│           │   ├── EqualSplit.java        # Equal split implementation
│           │   ├── PercentageSplit.java   # Percentage-based split implementation
│           │   └── ExactAmountSplit.java  # Exact-amount split implementation
│           ├── exception/
│           │   ├── InvalidSplitException.java
│           │   └── MemberNotFoundException.java
│           └── engine/
│               └── SettlementEngine.java  # Calculates balances and simplifies debts
└── README.md
```

## Prerequisites

You need the Java Development Kit (JDK) installed on your system.

To check if it's already installed, open a terminal and run:
```
java -version
javac -version
```

If both commands return a version number (8 or higher), you're ready to go. If not, download and install the JDK from [https://www.oracle.com/java/technologies/downloads/](https://www.oracle.com/java/technologies/downloads/) or use [OpenJDK](https://adoptium.net/).

## Setup & Installation
1. Clone this repository:
```
git clone https://github.com/stutijain10/expense-splitter.git
```

2. Navigate into the project's source folder:
```
cd expense-splitter/src
```

## How to Compile

From inside the `src` folder, run:
```
javac com/splitter/Main.java
```
This will generate `.class` files alongside each `.java` file.

## How to Run

Still from inside the `src` folder, run:
```
java com.splitter.Main
```

## Usage Example
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

## Design Concepts Demonstrated

| Concept | Where it's used |
|---|---|
| **Encapsulation** | Private fields with getters in `Member`, `Expense`, and `Group` |
| **Abstraction** | `SplitStrategy` interface defines *what* a split does without specifying *how* |
| **Polymorphism** | `EqualSplit`, `PercentageSplit`, and `ExactAmountSplit` all implement `SplitStrategy` differently, but are used interchangeably in `SettlementEngine` |
| **Custom Exception Handling** | `InvalidSplitException` validates that splits mathematically add up correctly; `MemberNotFoundException` handles invalid member references |
| **Collections** | `ArrayList` and `HashMap` used throughout for storing members, expenses, and balances |
| **File I/O** | Settlement reports can be exported to a `.txt` file using `FileWriter` |

## Known Limitations

- Data is not persisted between sessions — all members and expenses exist only for the current run.
- The application assumes valid numeric input after retry prompts; it does not handle every possible malformed input scenario.
- Currency is hardcoded as Indian Rupees (displayed as "Rs.").
- Equal splits may show tiny rounding differences (fractions of a rupee) due to floating-point arithmetic; this does not affect the correctness of the final settlement.

## Author

Stuti Jain