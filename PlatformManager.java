package elearning;

import java.io.*;   // Unit 3: Byte Streams (FileOutputStream, FileInputStream)
import java.util.*; // Unit 4: Map, HashMap, ArrayList, List

public class PlatformManager {
    // Unit 4: Collections - Map & HashMap
    Map<Integer, Course> courses = new HashMap<>();
    Map<Integer, Quiz> quizzes = new HashMap<>();
    List<String> results = new ArrayList<>();

    public PlatformManager() {
        // Seed Courses
        courses.put(101, new Course(101, "Core Java Programming", "Prof. Sharma", 
            new String[]{"OOPs & Classes", "Inheritance & Polymorphism", "Multithreading & I/O", "Collections Framework"}));
        courses.put(102, new Course(102, "Web Development Basics", "Dr. Verma", 
            new String[]{"HTML5 & Layout", "CSS Styling", "JavaScript Events", "Backend APIs"}));

        // Seed Quiz with Questions
        Quiz q = new Quiz(201, "Java Core Assessment", 60);
        q.addQuestion(new Question("Which keyword invokes a parent class constructor?", 
            new String[]{"this", "super", "parent", "extends"}, 1));
        q.addQuestion(new Question("Which block ALWAYS executes after try-catch?", 
            new String[]{"throw", "throws", "finally", "static"}, 2));
        q.addQuestion(new Question("Which interface is implemented to create a thread?", 
            new String[]{"Runnable", "Threadable", "Process", "RunnableThread"}, 0));
        quizzes.put(201, q);
    }

    // Unit 2: Exception handling - throw & throws
    public void saveResult(String student, int score, int total) throws InvalidScoreException {
        if (score < 0 || score > total) {
            throw new InvalidScoreException("Invalid test score entered!"); // Unit 2: throw
        }
        String entry = student + " scored " + score + "/" + total + " (" + (score * 100 / total) + "%)";
        results.add(entry);

        // Unit 3: Byte Streams (FileOutputStream)
        try (FileOutputStream fos = new FileOutputStream("quiz_results.txt", true)) {
            fos.write((entry + "\n").getBytes());
        } catch (IOException e) {
            System.out.println("File write error: " + e.getMessage());
        }
    }

    // Unit 3: Byte Streams (FileInputStream)
    public List<String> getResults() {
        List<String> list = new ArrayList<>();
        File file = new File("quiz_results.txt");
        if (!file.exists()) return results;

        try (FileInputStream fis = new FileInputStream(file);
             Scanner sc = new Scanner(fis)) {
            while (sc.hasNextLine()) {
                list.add(sc.nextLine());
            }
        } catch (IOException e) {
            System.out.println("File read error: " + e.getMessage());
        }
        return list;
    }
}
