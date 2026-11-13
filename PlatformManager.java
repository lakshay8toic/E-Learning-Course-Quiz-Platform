package elearning;

import java.io.*; // Unit 3: Input/Output - Stream classes, FileOutputStream, FileInputStream, etc.
import java.util.*; // Unit 4: Collections - Map, HashMap, ArrayList, Iterator

public class PlatformManager {
    // Unit 4: Collections - Map, HashMap, ArrayList
    private Map<Integer, Course> courseMap;
    private Map<Integer, Quiz> quizMap;
    private List<String> studentResults;

    public PlatformManager() {
        courseMap = new HashMap<>();
        quizMap = new HashMap<>();
        studentResults = new ArrayList<>();
        seedData();
    }

    private void seedData() {
        // Course 1
        String[] javaModules = {
            "Introduction to Java & OOP (Classes, Objects, Inheritance)",
            "Polymorphism & Exception Handling (Overriding, Try-Catch)",
            "Multithreading & Stream I/O",
            "Collections Framework & Mini Project Integration"
        };
        Course c1 = new Course(101, "Core Java Specialization", "Computer Science", "Prof. Sharma", 
            "A complete beginner to intermediate course covering units 1 through 4 of object-oriented Java programming.", javaModules);
        courseMap.put(c1.getId(), c1);

        // Course 2
        String[] webModules = {
            "HTML5 Structure & Semantic Layout",
            "CSS3 Styling, Flexbox & Responsive Design",
            "JavaScript DOM Manipulation & Events",
            "RESTful API Integration & Client-Side Logic"
        };
        Course c2 = new Course(102, "Full Stack Web Development", "Web Technologies", "Dr. A. Verma", 
            "Hands-on guide to building modern, responsive e-learning web applications.", webModules);
        courseMap.put(c2.getId(), c2);

        // Course 3
        String[] dsModules = {
            "Arrays, Linked Lists, Stacks & Queues",
            "Trees, Binary Search Trees & Heaps",
            "Graph Algorithms (BFS, DFS, Dijkstra)",
            "Dynamic Programming & Algorithm Complexity"
        };
        Course c3 = new Course(103, "Data Structures & Algorithms", "Core CS", "Prof. R. Mehta", 
            "Master essential data structures and problem solving for technical interviews.", dsModules);
        courseMap.put(c3.getId(), c3);

        // Quiz 1: Java Basics
        Quiz q1 = new Quiz(201, "Java OOP & Exception Handling Quiz", "Java", 60);
        q1.addQuestion(new Question(
            "Which keyword is used by a subclass to call a parent class constructor?",
            new String[]{"this", "super", "parent", "extends"}, 1));
        q1.addQuestion(new Question(
            "Which of these cannot be used for a variable name in Java?",
            new String[]{"identifier", "keyword", "class", "myVar"}, 2));
        q1.addQuestion(new Question(
            "Which block is ALWAYS executed whether an exception is handled or not?",
            new String[]{"try", "catch", "finally", "throw"}, 2));
        q1.addQuestion(new Question(
            "Which interface must be implemented to create a thread using the runnable approach?",
            new String[]{"Runnable", "Threadable", "Process", "Callable"}, 0));
        quizMap.put(q1.getId(), q1);

        // Quiz 2: Web Dev
        Quiz q2 = new Quiz(202, "Web Fundamentals & Design Quiz", "Web", 45);
        q2.addQuestion(new Question(
            "What does HTML stand for?",
            new String[]{"Hyper Text Markup Language", "High Tech Modern Language", "Hyperlink Text Mode Layout", "Home Tool Markup Language"}, 0));
        q2.addQuestion(new Question(
            "Which CSS property controls text size?",
            new String[]{"font-style", "text-size", "font-size", "text-style"}, 2));
        quizMap.put(q2.getId(), q2);
    }

    public Collection<Course> getAllCourses() {
        return courseMap.values();
    }

    public Collection<Quiz> getAllQuizzes() {
        return quizMap.values();
    }

    public Course getCourse(int id) {
        return courseMap.get(id);
    }

    public Quiz getQuiz(int id) {
        return quizMap.get(id);
    }

    // Unit 2: Exception handling - throw, throws, custom exception
    public void recordResult(String studentName, int quizId, int score, int total) throws InvalidScoreException {
        if (score < 0 || score > total) {
            throw new InvalidScoreException("Invalid score: " + score + " out of " + total);
        }
        double percentage = ((double) score / total) * 100.0;
        String record = String.format("%s - Quiz #%d - Score: %d/%d (%.1f%%) on %s", 
            studentName, quizId, score, total, percentage, new Date());
        studentResults.add(record);

        // Unit 3: Input/Output - FileOutputStream & Byte Streams to save result to file
        saveResultToFile(record);
    }

    // Unit 3: Byte Stream I/O (FileOutputStream, try-with-resources / exception handling)
    private void saveResultToFile(String record) {
        File file = new File("quiz_results.txt");
        try (FileOutputStream fos = new FileOutputStream(file, true)) { // append mode
            byte[] bytes = (record + System.lineSeparator()).getBytes();
            fos.write(bytes);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    // Unit 3: Byte Stream I/O (FileInputStream)
    public List<String> readResultsFromFile() {
        List<String> list = new ArrayList<>();
        File file = new File("quiz_results.txt");
        if (!file.exists()) return studentResults;

        try (FileInputStream fis = new FileInputStream(file);
             BufferedReader reader = new BufferedReader(new InputStreamReader(fis))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    list.add(line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return list;
    }
}
