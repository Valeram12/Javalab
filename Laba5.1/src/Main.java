
import java.util.Random;
public class Main {
    private static final int ROOKIE_COUNT = 300;
    private static final int THREAD_COUNT = 3;
    private static Position[] rookies = new Position[ROOKIE_COUNT];
    public static void main(String[] args) {
        fillArray();
        Thread[] threads = new RookieThread[THREAD_COUNT];
        Barrier barrier = new Barrier(THREAD_COUNT);

        for (int i = 0; i < THREAD_COUNT; i++) {
            threads[i] = new RookieThread(i * ROOKIE_COUNT / THREAD_COUNT,
                    (i+1) * ROOKIE_COUNT / THREAD_COUNT - 1, barrier, rookies);
            threads[i].start();
        }
    }

    private static void fillArray() {
        Random random = new Random();
        for (int i = 0; i < ROOKIE_COUNT; i++) {
            if (random.nextBoolean()) {
                rookies[i] = Position.RIGHT;
            } else {
                rookies[i] = Position.LEFT;
            }
        }
    }
}
