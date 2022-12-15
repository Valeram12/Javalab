

public class RookieThread extends Thread {
    private int begin;
    private int end;
    private Barrier barrier;
    private Position[] rookies;
    public RookieThread(int begin, int end, Barrier barrier, Position[] rookies) {
        this.barrier = barrier; this.rookies = rookies; this.begin = begin;
        this.end = end;
    }

    @Override
    public void run() {
        while (!isReady()) {
            for (int i = begin; i < end; i++) {
                if (rookies[i] != rookies[i+1]) {
                    Position temp = rookies[i];
                    rookies[i] = rookies[i+1];
                    rookies[i+1] = temp;
                }
            }
            try {
                barrier.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private boolean isReady() {
        for (int i = begin; i < end - 1; i++) {
            if (rookies[i] != rookies[i + 1])
                return false;
        }
        return true;
    }
}
