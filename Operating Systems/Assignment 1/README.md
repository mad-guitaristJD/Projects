# Assignment 1: Operating System Concepts

## Overview

This assignment demonstrates process management, inter-process communication, and basic system programming using Linux. It is divided into three tasks, each emphasizing different aspects of forking, process synchronization, and system calls. 

---

## Task Summaries

### **Q1: Mean Calculation by Child Processes**

- **Description**: The parent process spawns 7 child processes. Each child generates 4 random numbers between 1 and 100, calculates their mean, prints it, and terminates. The parent waits for all children to finish before exiting.
- **Output**: 7 lines, each showing the mean calculated by a child.

#### **Key Requirements**
1. **File**: `q1.c`
2. **Parent Process**: 
   - Forks 7 child processes in a loop.
   - Calls `wait()` for each child to ensure synchronization.
3. **Child Process**: 
   - Generates 4 random numbers, computes the mean, and prints it.
4. **Output Example**:
   ```
   Mean 1: 45.25
   Mean 2: 50.00
   ...
   Mean 7: 60.75
   ```

---

### **Q2: Binary Search with Forks**

- **Description**: Perform a binary search on a 16-element sorted array using a recursive fork-based approach. The parent compares the target value with the middle element and forks a child process to search the appropriate half. The process continues until the target value is found or determined absent.
- **Output**: The index of the target value or `-1` if not found.

#### **Key Requirements**
1. **File**: `q2.c`
2. **Parent Process**:
   - Defines a 16-element sorted array.
   - Prompts the user for a target value.
   - Recursively forks and delegates search operations to child processes.
   - Calls `wait()` for each child to ensure completion.
3. **Child Process**:
   - Searches its assigned array segment.
   - Prints the index if the target is found or `-1` if not.
4. **Output Example**:
   ```
   Array: 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16
   Enter a target value: 6
   Target value’s index: 5
   ```

---

### **Q3: Custom Linux Commands**

- **Description**: Implements three basic Linux commands (`date`, `cal`, `uptime`) and runs them as child processes via a `main` program. A `Makefile` ensures proper compilation and execution of all programs.

#### **Program Details**
1. **`date.c`**:
   - Mimics the Linux `date` command.
   - Prints the current date in:
     - Default format (when no flags are passed).
     - UTC format (`-u` flag).
     - RFC 2822 format (`-r` flag).
   - Example Output:
     ```
     Current Date: Fri Nov 21 12:34:56 2024
     ```

2. **`cal.c`**:
   - Mimics the Linux `cal` command.
   - Uses Zeller's Congruence to compute the first day of a given month/year and prints the calendar.
   - Example Output:
     ```
     November 2024
     Su Mo Tu We Th Fr Sa
                     1  2
      3  4  5  6  7  8  9
     ...
     ```

3. **`uptime.c`**:
   - Mimics the Linux `uptime` command.
   - Prints the system uptime in hours, minutes, and seconds.
   - Example Output:
     ```
     System Uptime: 4 hours 23 minutes 45 seconds
     ```

4. **`main.c`**:
   - Parent process creates 3 child processes using `fork()`.
   - Each child executes one of the above programs using an `exec` function.
   - The parent waits for all children to finish.

5. **`Makefile`**:
   - Automates compilation:
     - `date.c` → `date`
     - `cal.c` → `cal`
     - `uptime.c` → `uptime`
     - `main.c` → `main`
   - Example Usage:
     ```bash
     make       # Compiles all programs
     ./main     # Runs the main program
     ```

---

## Instructions

### **Setup**
1. Clone or download the assignment files.
2. Navigate to the respective folders (`q1`, `q2`, `q3`) for each question.

### **Compilation and Execution**

#### Q1
1. Compile:
   ```bash
   gcc -o q1 q1.c
   ```
2. Execute:
   ```bash
   ./q1
   ```

#### Q2
1. Compile:
   ```bash
   gcc -o q2 q2.c
   ```
2. Execute:
   ```bash
   ./q2
   ```

#### Q3
1. Navigate to the `q3` folder.
2. Compile:
   ```bash
   make
   ```
3. Execute:
   ```bash
   ./main
   ```

---

## Notes

1. **Random Number Generation**:
   - In `q1.c`, ensure `srand()` is seeded with `time(NULL)` for different random values each run.
2. **Error Handling**:
   - Validate input (e.g., target value in `q2.c`, month/year in `cal.c`).
3. **Zeller’s Congruence**:
   - Use the formula to determine the first day of the month in `cal.c`.
4. **System Calls**:
   - Use `sysinfo()` in `uptime.c` to fetch uptime details.

---

## Example Outputs

### Q1
```
Mean 1: 45.25
Mean 2: 50.00
Mean 3: 38.75
...
Mean 7: 61.00
```

### Q2
```
Array: 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16
Enter a target value: 10
Target value’s index: 9
```

### Q3
```
Current Date: Fri Nov 21 12:34:56 2024
November 2024
Su Mo Tu We Th Fr Sa
                1  2
 3  4  5  6  7  8  9
...
System Uptime: 4 hours 23 minutes 45 seconds
```

---

## Submission Checklist

- **Q1**: `q1.c` (compiled and tested output of 7 means).
- **Q2**: `q2.c` (compiled and tested binary search with recursive forks).
- **Q3**:
  - Folder `q3/` containing:
    - `date.c`
    - `cal.c`
    - `uptime.c`
    - `main.c`
    - `Makefile`
  - Verified `make` compiles all programs successfully.

--- 

## Author

**Jaideep Dahiya**  
B.Tech in Computer Science and Design  
IIIT Delhi