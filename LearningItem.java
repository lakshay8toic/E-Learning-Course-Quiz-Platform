package elearning;

// Unit 1: Abstract Class, final keyword, this keyword, static keyword
public abstract class LearningItem implements Displayable {
    protected int id;
    protected String title;
    public static int count = 0; // Unit 1: static variable

    public LearningItem(int id, String title) {
        this.id = id;       // Unit 1: this keyword
        this.title = title;
        count++;
    }

    // Unit 2: Abstract method for Dynamic Method Dispatch
    public abstract String getSummary();

    // Unit 1: final method (cannot be overridden)
    public final int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
