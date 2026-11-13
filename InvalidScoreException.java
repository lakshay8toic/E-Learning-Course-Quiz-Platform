package elearning;

// Unit 2: User-defined Custom Exception
public class InvalidScoreException extends Exception {
    public InvalidScoreException(String msg) {
        super(msg);
    }
}
