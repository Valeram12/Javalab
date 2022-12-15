
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class MyThread extends Thread{
    private String string;
    private CyclicBarrier barrier;
    private Counter counter;

    public MyThread(String string, CyclicBarrier barrier, Counter counter) {
        this.barrier = barrier;
        this.counter = counter;
        this.string = string;
    }
    @Override
    public void run() {
        while (!counter.getStop()) {
            substitute();
            System.out.println(string);
            try {
                counter.setThreadInfo(count());
                barrier.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (BrokenBarrierException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void substitute() {
        Random random = new Random();
        int pos = random.nextInt(string.length());
        switch (string.charAt(pos)) {
            case 'A':
                string = string.substring(0, pos) + 'C' + string.substring(pos + 1);
                break;
            case 'C':
                string = string.substring(0, pos) + 'A' + string.substring(pos + 1);
                break;
            case 'B':
                string = string.substring(0, pos) + 'D' + string.substring(pos + 1);
                break;
            case 'D':
                string = string.substring(0, pos) + 'B' + string.substring(pos + 1);
                break;
        }
    }

    private int count() {
        int count = 0;
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) == 'A' || string.charAt(i) == 'B')
                count++;
        }
        return count;
    }
}
