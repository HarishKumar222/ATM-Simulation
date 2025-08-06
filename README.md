# 💳 ATM-Simulation

A **Java-based ATM Management System** with **MySQL** integration using **JDBC**.  
Supports both **Customer** and **Operator** modes with account persistence and transaction logging.  
Designed with OOP principles, custom exception handling, and a modular architecture.

---

## 🚀 Features

### Customer Mode
- Withdraw and deposit cash
- Check account balance
- Change PIN
- View mini statement (last 5 transactions)

### Operator Mode
- Check ATM machine balance
- Deposit cash into ATM
- Reset user PIN attempt limits
- View all ATM activity logs
- Switch off ATM machine

### Security Features
- PIN verification with 3-attempt limit
- Invalid card detection
- ATM cash limit enforcement

---

## 🛠 Tech Stack
- **Language:** Java 21
- **Database:** MySQL 8+ (tested with MySQL Connector/J 9.4.1)
- **Libraries:** JDBC
- **IDE:** IntelliJ IDEA Community Edition

---

## 📂 Project Structure
src/
├── com/atm/cards/ # Debit card implementations (Axis, HDFC, SBI, Operator)
├── com/atm/exceptions/ # Custom exception classes
├── com/atm/interfaces/ # IATMService interface
├── com/atm/operations/ # ATMOperations.java (main application)
├── com/atm/persistence/ # MySQL Database & Logger classes
└── resources/ # Config files, sample data

sql
Copy
Edit

---

## 🗄 Database Setup
Run the following in MySQL:

```sql
CREATE DATABASE atm_system;
USE atm_system;

-- Accounts Table
CREATE TABLE accounts (
    card_number BIGINT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    balance DOUBLE NOT NULL,
    pin INT NOT NULL,
    type VARCHAR(20) NOT NULL
);

-- Transactions Table
CREATE TABLE transactions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    card_number BIGINT,
    description TEXT,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Sample Data
INSERT INTO accounts VALUES
(222222221, 'Yashas', 50000, 2222, 'axis'),
(3333333331, 'Akshay', 55000, 3333, 'sbi'),
(4444444441, 'Das', 32500, 4444, 'axis'),
(5555555551, 'Aravind', 71000, 5555, 'hdfc'),
(1111111111, 'Operator 1', 0, 1111, 'operator');
⚙️ Installation & Setup
Clone the repository

bash
Copy
Edit
git clone https://github.com/HarishKumar222/ATM-Simulation.git
cd ATM-Simulation
Add MySQL Connector/J to classpath

Download from: MySQL Connector/J

Place the .jar file in a lib/ folder and add it to project dependencies.

Configure DatabaseConnection.java

java
Copy
Edit
private static final String URL = "jdbc:mysql://localhost:3306/atm_system?useSSL=false&serverTimezone=UTC";
private static final String USER = "root"; // your MySQL username
private static final String PASSWORD = "your_password"; // your MySQL password
Run the Project

Run ATMOperations.java from IntelliJ.

💻 Usage Example
Login as Customer
markdown
Copy
Edit
Please enter the Debit Card Number:
222222221
Please enter your PIN:
2222
USER MODE: Yashas
1. Withdraw Amount
2. Deposit Amount
3. Check Balance
4. Change PIN
5. Mini Statement
6. Exit
Login as Operator
mathematica
Copy
Edit
Please enter the Debit Card Number:
1111111111
Please enter your PIN:
1111
OPERATOR MODE: Operator 1
0. Switch Off The Machine
1. Check ATM Machine Balance
2. Deposit Cash In The Machine
3. Reset User PIN Attempts
4. Check ATM Activity Log
5. Exit Operator Mode
📌 Future Improvements
Add a GUI using JavaFX or Swing

Encrypt stored PINs

REST API for ATM operations

Unit tests for all modules

Author: HarishKumar222


 
