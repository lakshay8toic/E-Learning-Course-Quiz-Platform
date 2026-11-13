package elearning;

import java.util.ArrayList; // Unit 4: Collection Classes (ArrayList)

// Unit 1: Multilevel / Class Hierarchy
public class Quiz extends LearningItem {
    ArrayList<Question> questions = new ArrayList<>(); // Unit 4: ArrayList
    int timeLimit;

    public Quiz(int id, String title, int timeLimit) {
        super(id, title);
        this.timeLimit = timeLimit;
    }

    public void addQuestion(Question q) {
        questions.add(q);
    }

    // Unit 2: Method Overriding
    @Override
    public String getSummary() {
        return "Quiz #" + id + ": " + title + " (" + questions.size() + " Qs, " + timeLimit + "s)";
    }

    @Override
    public void showDetails() {
        System.out.println(getSummary());
    }
}
