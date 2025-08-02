# MMS: Memory Management System (Java Simulation)

This project simulates a simple Memory Management System (MMS) using linked lists in Java. It was created as part of an academic assignment at York University for the course ITEC 2620 (Summer 2025).

> 📅 Assignment Due: July 6, 2025  
> 🎓 Student: Ahsan Uddin Sabit — 220790549

## 📌 Overview

The MMS manages memory allocation and deallocation requests from processes using two linked lists:
- `allocList`: stores memory blocks allocated to processes
- `freeList`: tracks free/available memory blocks

## 🔧 Features

- Smallest Fit Allocation Strategy
- Dynamic Freeing of Blocks
- Garbage Collection Simulation
- Interactive Command Line Interface

## 🛠️ Usage

### Compile
```bash
javac MMS.java
```

### Run
```bash
java MMS
```

### Commands
```text
alloc <name> <size>
free <name>
printalloc
printfree
exit
```

## ✅ Author

**Ahsan Uddin Sabit**  
Student ID: 220790549  
York University  
