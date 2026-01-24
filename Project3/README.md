# 🏦 Simple Banking Application

A console-based banking system built with Core Java demonstrating OOP, file handling, and data persistence.

## ✨ Features
- Create account with 6-digit account number and 4-digit PIN
- Secure login with authentication
- Deposit, withdraw, and check balance
- View transaction history with timestamps
- Data persistence using file serialization

## 🛠️ Tech Stack
**Core Java Only:** Scanner, HashMap, ArrayList, File I/O (Serialization)

## 🚀 How to Run
1. Save code as `BankingApplication.java`
2. Compile: `javac BankingApplication.java`
3. Run: `java BankingApplication`

## 💾 Data Storage
Uses Java Serialization to save all accounts to `bank_data.dat` file. Data automatically loads on startup and saves after each transaction.

## 📚 Learning Outcomes
Master OOP concepts, Collections Framework, exception handling, file I/O, input validation, and building menu-driven console applications.

## ⚠️ Note
**Educational project only.** Not production-ready (no encryption, single-user access, file-based storage).