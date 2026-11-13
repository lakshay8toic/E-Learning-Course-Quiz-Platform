package elearning;

// Question Class (Unit 1: Classes, Objects, Constructors)
public class Question {
    private String prompt;
    private String[] options; // Unit 1: Arrays
    private int correctOptionIndex; // 0-based

    public Question(String prompt, String[] options, int correctOptionIndex) {
        this.prompt = prompt;
        this.options = options;
        this.correctOptionIndex = correctOptionIndex;
    }

    public String getPrompt() { return prompt; }
    public String[] getOptions() { return options; }
    public int getCorrectOptionIndex() { return correctOptionIndex; }

    public boolean checkAnswer(int selectedOptionIndex) {
        return this.correctOptionIndex == selectedOptionIndex;
    }
}
