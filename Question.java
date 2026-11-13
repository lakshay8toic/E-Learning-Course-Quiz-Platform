package elearning;

// Unit 1: Classes, Objects, Arrays
public class Question {
    String text;
    String[] options; // Unit 1: Array of options
    int answer;       // correct option index (0 to 3)

    public Question(String text, String[] options, int answer) {
        this.text = text;
        this.options = options;
        this.answer = answer;
    }
}
