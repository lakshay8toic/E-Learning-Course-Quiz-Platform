package elearning;

// Course extends LearningItem (Unit 1: Inheritance, super keyword)
public class Course extends LearningItem {
    private String instructor;
    private String description;
    private String[] syllabusModules; // Unit 1: Arrays

    // Constructor Overloading (Unit 2: Constructor Overloading / Compile-time polymorphism)
    public Course(int id, String title, String category, String instructor, String description, String[] syllabusModules) {
        super(id, title, category); // Unit 1: super uses
        this.instructor = instructor;
        this.description = description;
        this.syllabusModules = syllabusModules;
    }

    public Course(int id, String title, String category, String instructor) {
        this(id, title, category, instructor, "Standard comprehensive course.", new String[]{"Basics", "Advanced"});
    }

    // Method Overriding (Unit 2: Runtime polymorphism / Method Overriding)
    @Override
    public String getSummary() {
        return "Course #" + id + ": " + title + " (Instructor: " + instructor + ")";
    }

    @Override
    public void showDetails() {
        System.out.println("=== Course Details ===");
        System.out.println("ID: " + id);
        System.out.println("Title: " + title);
        System.out.println("Category: " + category);
        System.out.println("Instructor: " + instructor);
        System.out.println("Description: " + description);
        System.out.println("Syllabus Modules:");
        for (int i = 0; i < syllabusModules.length; i++) {
            System.out.println("  " + (i + 1) + ". " + syllabusModules[i]);
        }
    }

    // Method Overloading (Unit 2: Method Overloading / Compile-time polymorphism)
    public void enroll(String studentName) {
        System.out.println("Student " + studentName + " enrolled in " + title);
    }

    public void enroll(String studentName, String couponCode) {
        System.out.println("Student " + studentName + " enrolled in " + title + " with discount coupon [" + couponCode + "]");
    }

    public String getInstructor() { return instructor; }
    public String getDescription() { return description; }
    public String[] getSyllabusModules() { return syllabusModules; }
}
