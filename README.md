# MMS: Memory Management System (Java Simulation)

This project simulates a simple Memory Management System (MMS) using linked lists in Java.

---

## 📌 Overview

The MMS manages memory allocation and deallocation requests from processes using two linked lists:
- `allocList`: stores memory blocks allocated to processes
- `freeList`: tracks free/available memory blocks

---

## 🔧 Features

- **Smallest Fit Allocation Strategy**: Allocates memory using the smallest available block that fits the request.
- **Dynamic Freeing**: Frees all memory blocks associated with a process name.
- **Garbage Collection**: Automatically compacts memory when no block is large enough due to fragmentation.
- **Sorted Allocation List**: Maintains `allocList` in increasing order of address for simpler garbage collection.

---

## 🛠️ How to Compile and Run

### 1. Compile:
```bash
javac MMS.java
