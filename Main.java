package elearning;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== EduPulse E-Learning System ===");

        PlatformManager pm = new PlatformManager();

        // Unit 1: Static Variable
        System.out.println("[Unit 1] Items Created: " + LearningItem.count);

        // Unit 2: Dynamic Method Dispatch (Runtime Polymorphism)
        LearningItem item1 = pm.courses.get(101);
        LearningItem item2 = pm.quizzes.get(201);
        System.out.println("[Unit 2] Overridden summary: " + item1.getSummary());
        System.out.println("[Unit 2] Overridden summary: " + item2.getSummary());

        // Unit 2: Method Overloading (Compile-time Polymorphism)
        Course c = pm.courses.get(101);
        c.enroll("Rahul");
        c.enroll("Rahul", "FIRST10");

        // Start Web Server
        try {
            WebServer server = new WebServer(8080, pm);
            server.start();
        } catch (Exception e) {
            System.out.println("Server error: " + e.getMessage());
        }
    }
}
