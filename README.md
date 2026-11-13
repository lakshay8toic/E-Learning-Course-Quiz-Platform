# 🎓 EduPulse: E-Learning Course & Quiz Platform

A clean, modular Java-based E-Learning & Assessment web platform built strictly using concepts from **Units 1 to 4** of the Java curriculum. 

Includes an interactive local web application with dynamic course listings, a live timed quiz engine, automated grading, custom exception handling, and persistent file logging via Java Byte Streams.

---

## 👥 Project Team Details

| Role | Name | Roll Number |
| :--- | :--- | :--- |
| **Team Lead** | **Lakshay** | `AP24110010677` |
| **Team Member** | **Md Ahab Firoz** | `AP24110011219` |

---

## 📑 Syllabus Mapping (Units 1 to 4)

| Unit | Core Topics Implemented | Source Code Reference |
| :--- | :--- | :--- |
| **Unit 1: Overview of Java & Inheritance** | • Classes, Objects, Constructors (`this`, `super`)<br>• `static` counter & `final` method restrictions<br>• Abstract Class (`LearningItem`) & Interface (`Displayable`)<br>• Inheritance (`Course extends LearningItem`, `Quiz extends LearningItem`)<br>• Arrays for syllabus modules & question options | `LearningItem.java`<br>`Displayable.java`<br>`Course.java`<br>`Quiz.java` |
| **Unit 2: Polymorphism & Exception Handling** | • **Compile-time Polymorphism:** Method overloading (`enroll()`) & Constructor overloading<br>• **Runtime Polymorphism:** Dynamic method dispatch via `getSummary()`<br>• **Exception Handling:** User-defined exception (`InvalidScoreException`), `throw`, `throws`, and `try-catch-finally` | `InvalidScoreException.java`<br>`Course.java`<br>`PlatformManager.java` |
| **Unit 3: Multithreading & Stream I/O** | • **Multithreading:** `QuizTimerThread` implementing `Runnable`, Thread life cycle, `sleep()`, and thread synchronization (`synchronized(lock)`)<br>• **Byte Streams (I/O):** Appending records via `FileOutputStream` & reading history via `FileInputStream` | `QuizTimerThread.java`<br>`PlatformManager.java` |
| **Unit 4: Collections Framework** | • `ArrayList` for dynamic questions & result entries<br>• `HashMap` / `Map` for constant-time ID lookups of courses and quizzes | `Quiz.java`<br>`PlatformManager.java` |

---

## 🚀 How to Run & Localhost from this GitHub Repo

Follow these simple steps on any Mac, Windows, or Linux system:

### 1. Clone the Repository
Open your terminal (or Command Prompt) and run:
```bash
git clone https://github.com/lakshay8toic/E-Learning-Course-Quiz-Platform.git
cd E-Learning-Course-Quiz-Platform
```

### 2. Check Java Installation
Ensure JDK 17 (or JDK 11+) is installed:
```bash
java -version
javac -version
```

### 3. Compile the Java Files
Compile all Java source code into package structure:
```bash
javac -d . *.java
```

### 4. Run the Project
Start the main application:
```bash
java elearning.Main
```

*(On macOS/Linux, you can also simply run `./run.sh`)*

### 5. Access the Local Website
Open your browser and navigate to:
👉 **`http://localhost:8080/`**

---

## 🌐 Platform Pages & Features

- **Home (`/`)**: Overview of the platform and syllabus architecture.
- **Courses (`/courses`)**: Browse available courses, view instructors, syllabus modules, and simulated enrollment.
- **Live Quizzes (`/quizzes`)**: Select an interactive assessment.
- **Quiz Engine (`/quiz?id=201`)**: Real-time countdown timer powered by a background Java thread.
- **Instant Result Evaluation**: Immediate percentage, pass/distinction grading, score validation, and persistence.
- **Results History (`/results`)**: Historical attempt logs loaded from local storage using Java Byte Streams.

---

## 📁 Project Directory Structure
```
├── Displayable.java            # Unit 1: Interface
├── LearningItem.java           # Unit 1: Abstract class with static counter & final methods
├── Course.java                 # Unit 1 & 2: Subclass with constructor & method overloading
├── Question.java               # Unit 1: Question model with option arrays
├── Quiz.java                   # Unit 1 & 4: Subclass with ArrayList of Questions
├── InvalidScoreException.java  # Unit 2: User-defined custom Exception
├── QuizTimerThread.java        # Unit 3: Multithreading with Runnable & Synchronization
├── PlatformManager.java        # Unit 3 & 4: HashMap, Collections, FileOutputStream, FileInputStream
├── WebServer.java              # Interactive HTTP Web Server & Route Handlers
├── Main.java                   # Application Entry Point & Console Demonstration
├── run.sh                      # Quick start shell script
└── README.md                   # Project documentation & execution guide
```
