package elearning;

// Unit 3: Multithreading - Runnable interface, Thread life cycle, sleep, synchronization
public class QuizTimerThread implements Runnable {
    private int seconds;
    private boolean active = true;

    public QuizTimerThread(int seconds) {
        this.seconds = seconds;
    }

    public synchronized void stop() { // Unit 3: Synchronizing
        active = false;
    }

    @Override
    public void run() {
        try {
            while (seconds > 0 && active) {
                Thread.sleep(1000); // Unit 3: Thread sleep
                seconds--;
            }
        } catch (InterruptedException e) {
            System.out.println("Timer interrupted");
        }
    }
}
