
import java.util.concurrent.CyclicBarrier;

public class Main {
    public static void main(String[] args) {
        CyclicBarrier barrier = new CyclicBarrier(4);
        Counter counter = new Counter();
        Thread thread1 = new MyThread("AAAAAA", barrier, counter);
        Thread thread2 = new MyThread("BBBBBB", barrier, counter);
        Thread thread3 = new MyThread("CCCCCC", barrier, counter);
        Thread thread4 = new MyThread("DDDDDD", barrier, counter);
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
    }
}
