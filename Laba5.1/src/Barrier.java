
public class Barrier {
    private final int threadNumber;
    private int threadsAwait;

    public Barrier(int threads) {
        this.threadNumber = threads;
        this.threadsAwait = threads;
    }

    public synchronized void await() throws InterruptedException {
        threadsAwait--;
        if(threadsAwait > 0) {
            this.wait();
        }

        threadsAwait = threadNumber;
        notifyAll();
        System.out.println("[Barrier]: notified all threads!");
    }
}