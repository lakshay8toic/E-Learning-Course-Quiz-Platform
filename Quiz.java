package elearning;

import java.util.ArrayList; // Unit 4: Collections (ArrayList)

// Quiz extends LearningItem (Unit 1: Inheritance)
public class Quiz extends LearningItem {
    private ArrayList<Question> questions; // Unit 4: Collections Overview, ArrayList
    private int timeLimitSeconds;

    public Quiz(int id, String title, String category, int timeLimitSeconds) {
        super(id, title, category);
        this.timeLimitSeconds = timeLimitSeconds;
        this.questions = new ArrayList<>();
    }

    public void addQuestion(Question q) {
        this.questions.add(q);
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public int getTimeLimitSeconds() {
        return timeLimitSeconds;
    }

    // Dynamic Method Dispatch / Overriding (Unit 2)
    @Override
    public String getSummary() {
        return "Quiz #" + id + ": " + title + " (" + questions.size() + " Questions, Time: " + timeLimitSeconds + "s)";
    }

    @Override
    public void showDetails() {
        System.out.println("=== Quiz Information ===");
        System.out.println("ID: " + id);
        System.out.println("Title: " + title);
        System.out.println("Category: " + category);
        System.out.println("Questions Count: " + questions.size());
        System.out.println("Time Limit: " + timeLimitSeconds + " seconds");
    }
}
