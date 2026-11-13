package elearning;

// Abstract Class (Unit 1: Abstract Class, final keyword, this reference)
public abstract class LearningItem implements Displayable {
    protected int id;
    protected String title;
    protected String category;

    // Static counter (Unit 1: static keyword)
    public static int totalItemsCreated = 0;

    // Constructor (Unit 1: Constructor, this reference)
    public LearningItem(int id, String title, String category) {
        this.id = id;
        this.title = title;
        this.category = category;
        totalItemsCreated++;
    }

    // Abstract method to be overridden by subclasses (Unit 2: Runtime polymorphism / Dynamic Method Dispatch)
    public abstract String getSummary();

    // Final method (Unit 1: using final with inheritance)
    public final int getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getCategory() {
        return this.category;
    }
}
