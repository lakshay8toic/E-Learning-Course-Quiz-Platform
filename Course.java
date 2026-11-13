package elearning;

// Unit 1: Inheritance (extends) & super keyword
public class Course extends LearningItem {
    private String instructor;
    private String[] topics; // Unit 1: Arrays

    // Unit 2: Constructor Overloading (Compile-time Polymorphism)
    public Course(int id, String title, String instructor, String[] topics) {
        super(id, title); // Unit 1: super keyword
        this.instructor = instructor;
        this.topics = topics;
    }

    public Course(int id, String title, String instructor) {
        this(id, title, instructor, new String[]{"Basics", "Advanced Topics"});
    }

    // Unit 2: Method Overriding (Runtime Polymorphism / Dynamic Method Dispatch)
    @Override
    public String getSummary() {
        return "Course #" + id + ": " + title + " by " + instructor;
    }

    @Override
    public void showDetails() {
        System.out.println(getSummary());
    }

    // Unit 2: Method Overloading (Compile-time Polymorphism)
    public void enroll(String student) {
        System.out.println(student + " enrolled in " + title);
    }

    public void enroll(String student, String code) {
        System.out.println(student + " enrolled in " + title + " with coupon " + code);
    }

    public String getInstructor() { return instructor; }
    public String[] getTopics() { return topics; }
}
