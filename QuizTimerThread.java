package elearning;

// QuizTimerThread implements Runnable (Unit 3: Multithreading - Runnable interface, Thread life cycle, sleep)
public class QuizTimerThread implements Runnable {
    private int remainingSeconds;
    private volatile boolean isRunning = true;
    private final Object lock = new Object(); // Unit 3: Synchronizing threads

    public QuizTimerThread(int durationSeconds) {
        this.remainingSeconds = durationSeconds;
    }

    public void stopTimer() {
        synchronized (lock) {
            this.isRunning = false;
        }
    }

    public int getRemainingSeconds() {
        return remainingSeconds;
    }

    @Override
    public void run() {
        try {
            while (remainingSeconds > 0) {
                synchronized (lock) {
                    if (!isRunning) break;
                }
                Thread.sleep(1000); // 1 second tick
                remainingSeconds--;
            }
        } catch (InterruptedException e) {
            // Thread interrupted on quiz completion
            System.out.println("[Timer thread finished/interrupted]");
        }
    }
}
