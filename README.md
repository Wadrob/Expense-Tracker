# Expense Tracker (Java CLI)

A simple command-line application for tracking personal expenses.  
Built in Java with CSV-based persistence.

# Features

- Add expenses (description, amount, auto-generated date)
- List all expenses
- Delete expense by ID
- Total expenses summary
- Monthly expense summary
- Persistent storage using CSV file

# Project Structure

cli/ → Command-line interface
service/ → Business logic
repository/ → CSV persistence layer
domain/ → Expense model
helper/ → File utilities
Application → Entry point

# Commands

Command	Description:
add	- Add new expense
list - Show all expenses
delete -	Remove expense by ID
summary -	Show total expenses
summary month -	Show monthly summary
info -	Show available commands
quit -	Exit application

# CSV format

id,date,description,amount
1,2026-05-06,food,25.50
2,2026-05-06,transport,10.00

# Tech stack
Java 17+
Stream API
BigDecimal
Jackson CSV
JUnit 5
Mockito

Clean architecture
File-based persistence
Unit testing (JUnit + Mockito)
Java Stream API

Done as training for https://roadmap.sh/projects/expense-tracker
