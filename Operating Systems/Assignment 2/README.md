# README

## Memory Management Assignment

### Overview
This project consists of five tasks, each demonstrating various concepts of memory management, including segmentation, paging, virtual memory, and page replacement algorithms. The assignment emphasizes the implementation of memory management techniques in C.

### Tasks Overview
1. **Parallel Merge Sort**: Demonstrates parallel computation by performing merge sort using child processes and pipes for inter-process communication.
2. **Segmentation with Address Translation**: Simulates the translation of logical addresses to physical addresses using segmentation and raises segmentation faults for invalid accesses. *(Worked on by Jaideep Dahiya)*.
3. **Paging with TLB Simulation**: Implements memory management with a page table and Translation Lookaside Buffer (TLB) to demonstrate address translation efficiency.
4. **Two-Level Page Table**: Demonstrates address translation using a two-level page table structure, including handling page faults and maintaining hit/miss statistics.
5. **Clock Algorithm for Page Replacement**: Simulates a memory management system handling page faults using the clock page replacement algorithm with reference and dirty bits. *(Worked on by Jaideep Dahiya)*.

---

### Details of My Contributions
#### Question 2: Segmentation with Address Translation
- Implemented a simulation of address translation using segmentation.
- Defined three segments: Code, Heap, and Stack with distinct base and size limits.
- Translated 16-bit logical addresses into physical addresses based on segment base and offset.
- Handled segmentation faults for addresses exceeding segment limits.

#### Question 5: Clock Algorithm for Page Replacement
- Simulated page replacement using the clock algorithm with reference and dirty bits.
- Implemented a fixed number of memory frames for page storage.
- Evaluated page requests for hits and faults, updating frames as necessary.
- Calculated and displayed the total number of page faults, hits, and the hit rate.

---

### How to Run
1. Clone the repository or copy the files to your system.
2. Compile the programs using `gcc`:
   ```bash
   gcc -o q2 q2.c
   gcc -o q5 q5.c
   ```
3. Execute the compiled programs:
   - **Question 2**:
     ```bash
     ./q2
     ```
     Follow the prompts to input logical addresses in hexadecimal format.
   - **Question 5**:
     ```bash
     ./q5
     ```
     Input the number of frames and a sequence of page requests to simulate memory access.

---

### Assumptions
- **Question 2**:
  - Physical memory size is 64KB.
  - Segments are predefined with fixed base and limit values.
- **Question 5**:
  - All pages are initially empty.
  - Page requests are serviced sequentially.

---

### Key Learning Outcomes
- Implementation of memory management techniques in real-world scenarios.
- Understanding of segmentation and its error handling.
- Simulation of efficient page replacement using the clock algorithm. 

### Acknowledgment
This was a collaborative assignment where Jaideep Dahiya contributed to Questions 2 and 5.