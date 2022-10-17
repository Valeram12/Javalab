import java.util.concurrent.Semaphore;


public class Main {
    private static final int numberOfBus = 20;
    private static final Semaphore semaphore = new Semaphore(5, true);

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < numberOfBus; i++) {
            new Thread(new Bus(i + 1)).start();
            Thread.sleep(500);
        }
    }

    public static class Bus implements Runnable {
        private int busI;

        Bus(int busI) {
            this.busI = busI;
        }

        @Override
        public void run() {
            try {

                semaphore.acquire();

                System.out.printf("Автобус №%d приїхав на зупинку.\n", busI);
                Thread.sleep(1500);

            } catch (InterruptedException e) {
                throw new Error();
            }

            semaphore.release();
            System.out.printf("Автобус №%d виїхав з зупинку.\n", busI);
        }
    }
}
