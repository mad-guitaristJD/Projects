# README

## Multithreading and Synchronization Assignment

### Overview
This assignment explores various multithreading and synchronization problems in C, emphasizing concepts such as deadlock avoidance, mutual exclusion, synchronization primitives, and thread management. The implementation leverages **pthreads** and **semaphores** for efficient resource utilization.

---

### Task Overview
1. **Thread Scheduling with Deadlock Avoidance**  
   Implements a simplified resource allocation system to prevent deadlocks by enforcing a strict lock acquisition order.  
   - **Key Concepts**: Deadlock avoidance, Resource Instance Ordering.

2. **Networked Servers Synchronization Problem**  
   Simulates servers accessing shared network channels with mutual exclusion and starvation prevention using semaphores.  
   - **Key Concepts**: Mutual exclusion, resource allocation, and starvation prevention.

3. **Warehouse Management with Circular Buffer**  
   Models a warehouse system using delivery truck and storage manager threads, coordinating their operations via a circular buffer.  
   - **Key Concepts**: Circular buffer, producer-consumer problem, semaphore/mutex synchronization.

4. **Matrix Multiplication with Multithreading**  
   Multiplies matrices using multiple threads, optimizing the process with a thread pool to reuse threads.  
   - **Key Concepts**: Thread pools, matrix multiplication, dynamic memory allocation, performance optimization.

---

### My Contribution
This assignment was an **individual effort** where I implemented all four tasks.

---

### Details of Implementation

#### **Task 1: Thread Scheduling with Deadlock Avoidance**
- **Goal**: Prevent deadlock in a multi-threaded system.
- **Approach**:
  - Created 3 threads (T1, T2, T3), each acquiring 2 locks in a fixed order: **Lock A** before **Lock B**.
  - Enforced lock acquisition order globally to avoid circular wait conditions.
  - Threads acquire locks 3 times each, with messages indicating lock acquisition and waiting status.
- **Output**:
  - Logs showing lock acquisition order and threads waiting or proceeding without deadlock.

---

#### **Task 2: Networked Servers Synchronization Problem**
- **Goal**: Ensure synchronization among 5 servers accessing shared network channels.
- **Approach**:
  - Represented 5 servers as threads and network channels as semaphores.
  - Enforced mutual exclusion for channel usage and ensured fairness to prevent starvation.
  - Each server processed data for 1 second after acquiring both its left and right channels.
- **Output**:
  - Logs indicating server actions: processing or waiting for channels.
  - Execution terminates after each server processes data 3 times.

---

#### **Task 3: Warehouse Management with Circular Buffer**
- **Goal**: Coordinate delivery trucks and storage managers using a circular buffer.
- **Approach**:
  - Used semaphores and mutexes to manage access to a shared circular buffer.
  - Simulated random arrivals of trucks and storage operations with random product quantities.
  - Maintained synchronization to prevent race conditions, overloading, or underflow.
  - Program runs for a predefined duration or number of deliveries.
- **Output**:
  - Logs showing buffer state, delivery operations, storage operations, and waiting conditions.

---

#### **Task 4: Matrix Multiplication with Multithreading**
**Part 1**: Per-thread Element Calculation  
- **Goal**: Optimize matrix multiplication using threads.
- **Approach**:
  - Created a separate thread for each element of the resultant matrix.
  - Dynamically allocated matrices based on user input dimensions.
  - Compared speedup over sequential implementation.
- **Output**:
  - Resultant matrix.
  - Speedup over sequential computation.

**Part 2**: Matrix Multiplication with Thread Pool  
- **Goal**: Reuse threads for efficient matrix multiplication.
- **Approach**:
  - Implemented a thread pool using **pthreads** with size equal to system core count.
  - Designed a generic API to accept tasks and input data, reusing threads for computation.
  - Compared speedup over Part 1 implementation and sequential computation.
- **Output**:
  - Resultant matrix.
  - Speedup over both sequential and per-thread implementations.

---

### How to Run

1. **Compile the programs**:  
   ```bash
   gcc -o task1 task1.c -lpthread
   gcc -o task2 task2.c -lpthread
   gcc -o task3 task3.c -lpthread
   gcc -o task4 task4.c -lpthread
   ```

2. **Execute the compiled programs**:
   - **Task 1**:
     ```bash
     ./task1
     ```
   - **Task 2**:
     ```bash
     ./task2
     ```
   - **Task 3**:
     ```bash
     ./task3 <number_of_trucks> <number_of_storage_managers>
     ```
   - **Task 4**:
     ```bash
     ./task4
     ```

3. Follow the prompts for user inputs where applicable.

---

### Assumptions
1. **Task 1**:
   - Lock acquisition is strictly ordered to avoid deadlocks.
2. **Task 2**:
   - Network channels are shared only between adjacent servers.
3. **Task 3**:
   - Buffer size and maximum product limits are defined as constants.
4. **Task 4**:
   - Matrix dimensions are user-defined.
   - System core count is used to determine thread pool size.

---

### Key Learning Outcomes
- Understanding of deadlock and starvation prevention in multi-threaded systems.
- Practical implementation of semaphores and mutexes for synchronization.
- Optimization of multithreaded tasks using thread pools.
- Hands-on experience with dynamic memory management and parallel programming.

---

### Acknowledgment
This assignment was completed as part of an individual effort, adhering to the requirements and ensuring efficient solutions for each task.