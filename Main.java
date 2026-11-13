package elearning;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("   EduPulse: E-Learning Course & Quiz Platform   ");
        System.out.println("=================================================");

        // Unit 1: Classes, Objects, Static members
        PlatformManager manager = new PlatformManager();
        System.out.println("[Unit 1] Total Learning Items Initialized: " + LearningItem.totalItemsCreated);

        // Unit 2: Polymorphism demonstration in terminal
        System.out.println("\n[Unit 2] Demonstrating Dynamic Method Dispatch (Runtime Polymorphism):");
        LearningItem item1 = manager.getCourse(101);
        LearningItem item2 = manager.getQuiz(201);
        System.out.println("-> " + item1.getSummary()); // calls Course.getSummary()
        System.out.println("-> " + item2.getSummary()); // calls Quiz.getSummary()

        // Unit 2: Compile-time polymorphism (Method Overloading)
        System.out.println("\n[Unit 2] Demonstrating Method Overloading (Compile-time Polymorphism):");
        Course c = manager.getCourse(101);
        c.enroll("Amit");
        c.enroll("Amit", "DIWALI50");

        // Starting Web Server for Live Website Demo
        int port = 8080;
        try {
            WebServer webServer = new WebServer(port, manager);
            webServer.start();
            System.out.println("\n-------------------------------------------------");
            System.out.println("✔ Web Server started successfully!");
            System.out.println("✔ Open your browser at: http://localhost:" + port + "/");
            System.out.println("-------------------------------------------------");
            System.out.println("Press Ctrl+C in terminal when you want to stop the server.");
        } catch (IOException e) {
            System.err.println("Could not start server on port " + port + ": " + e.getMessage());
        }
    }
}
