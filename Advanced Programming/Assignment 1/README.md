# University Course Registration System - README

## Overview

This project is a **University Course Registration System** developed using **Object-Oriented Programming (OOP)** principles. The system facilitates seamless course registration, schedule management, academic progress tracking, and administrative functionalities. It supports three user roles—**Students**, **Professors**, and **Administrators**, with distinct capabilities for each. 

The project has been enhanced to include advanced features such as a **Generic Feedback System**, a new **Teaching Assistant (TA) user role**, and **Robust Exception Handling** for error scenarios.

---

## Features

### **Student Functionalities**
1. **View Available Courses**: Students can browse courses with details like title, credits, timings, and prerequisites.
2. **Register for Courses**:
   - Students can register for courses in their current semester.
   - Enforces credit limits (max 20 credits per semester).
   - Ensures prerequisites are met.
3. **View Schedule**: Students can see their weekly course schedule with timing, location, and professor details.
4. **Track Academic Progress**:
   - View grades for completed courses.
   - Calculate SGPA and CGPA based on completed semesters.
5. **Drop Courses**: Drop any course during the ongoing semester.
6. **Submit Complaints**:
   - Report issues (e.g., schedule clashes) with statuses (Pending/Resolved).
   - Complaints' statuses are updated by the administrator.

### **Professor Functionalities**
1. **Manage Courses**:
   - Update course details such as syllabus, timings, and prerequisites.
2. **View Enrolled Students**:
   - Access a list of students enrolled in their courses with contact details.

### **Administrator Functionalities**
1. **Manage Course Catalog**:
   - Add, view, and delete courses.
2. **Manage Student Records**:
   - Update student grades and personal information.
3. **Assign Professors to Courses**:
   - Assign professors based on expertise.
4. **Handle Complaints**:
   - View and update complaint statuses.
   - Filter complaints by status or date.

---

## Enhancements (Advanced Features)

### **1. Generic Feedback System**
- Students can provide feedback on completed courses in the form of numeric ratings (e.g., 1–5) and textual comments (e.g., *"Great course!"*).
- A **generic class** is used to store and manage feedback, supporting multiple data types.
- Professors can view course feedback from their interface.

### **2. Enhanced User Role Management**
- Introduced a new **Teaching Assistant (TA)** role:
  - TAs inherit functionalities from the Student class.
  - Additional capabilities include viewing and managing student grades.
  - TAs do not have full professor privileges (e.g., cannot modify course details).
- This role demonstrates the use of **inheritance** and **object classes** in OOP.

### **3. Robust Exception Handling**
- Implemented **custom exceptions** for error scenarios:
  1. **Invalid Course Registration**: Throws `CourseFullException` if a course is already full.
  2. **Invalid Login**: Throws `InvalidLoginException` for incorrect credentials.
  3. **Course Drop Deadline**: Throws `DropDeadlinePassedException` if a course drop attempt is made after the deadline.
- All exceptions are handled using **try-catch blocks**, ensuring graceful error recovery and better user experience.

---

## Application Flow
1. **Startup**:
   - The application prompts users to log in as a Student, Professor, or Administrator.
2. **Role-Based Interfaces**:
   - Each role has a menu-driven interface to access their specific functionalities.
3. **Logout and Exit**:
   - Users can safely log out and exit the application, saving all changes.

---

## Technical Details

### **Key Concepts Applied**
- **Object-Oriented Programming (OOP)**: Used to structure the system with classes, interfaces, inheritance, polymorphism, encapsulation, and abstraction.
- **Generic Programming**: Implemented in the feedback system to handle data of different types.
- **Exception Handling**: Ensures robustness by gracefully managing errors with custom exceptions.

### **Development Tools**
- **Programming Language**: C++
- **Libraries Used**: Standard Template Library (STL) for generic classes and exception handling.
- **Environment**: Terminal-based menu-driven application.

---

## How to Run the Program
1. Compile the program using a C++ compiler (e.g., `g++`).
   ```bash
   g++ main.cpp -o university_system
   ```
2. Run the compiled executable.
   ```bash
   ./university_system
   ```
3. Follow the prompts to log in and access functionalities based on your role.

---

## Demonstration Examples

### **Feedback System**
1. A student completes a course and provides feedback:
   ```
   Feedback: 5/5 - "Great course!"
   ```
2. The professor views the feedback:
   ```
   Course Feedback:
   - Rating: 5/5
   - Comment: Great course!
   ```

### **Exception Handling**
1. A student tries to register for a full course:
   ```
   Error: CourseFullException - The selected course is already full.
   ```
2. A user enters invalid login credentials:
   ```
   Error: InvalidLoginException - Incorrect email or password.
   ```
3. A student attempts to drop a course past the deadline:
   ```
   Error: DropDeadlinePassedException - The course drop deadline has passed.
   ```


---

## Credits
Developed by: **Jaideep Dahiya** 
Course: **Advanced Programming**  
University: **IIIT Delhi**  

---

