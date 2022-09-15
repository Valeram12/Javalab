public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        Q q = new Q();
        new Producer(q);
        new Consumer(q);
        new Consumer1(q);

        System.out.println("Press Control-C to stop.");
    }

}

class Q{
    static int pl=0;
    static int sk=100;
    static int carv=0;


    int valueSet = 0;
    boolean val = false;

    synchronized void FromSquareToCar() {
        while(valueSet==0||pl==0) {
            try {
                //Thread.sleep(500);
                wait();
            } catch (InterruptedException e) {
                System.out.println("InterruptedException caught");
            }
        }
        pl--;
        carv++;
        System.out.println("FromSquareToCar : "+pl);

        valueSet = 0;
        notify();

    }

    synchronized void InCar() {
        while(valueSet==1||carv==0) {
            try {
                //Thread.sleep(1000);
                wait();
            } catch (InterruptedException e) {
                System.out.println("InterruptedException caught");
            }
        }
        System.out.println("In CAr count :  " + carv);


        valueSet=1;
        notify();
    }

    synchronized void FromSkladToSquare() {
        while (valueSet==2||sk==0){
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("InterruptedException caught");
            }
        }
        sk--;
        pl++;

        System.out.println("FromSkladToSquare : "+sk);

        valueSet = 2;


        notify();

    }
}



class Producer implements Runnable {

    Q q;
    Producer(Q q) {
        this.q = q;
        new Thread(this, "Producer").start();
    }

    public void run() {

        while(true) {
            q.FromSkladToSquare();
        }
    }
}

class Consumer implements Runnable {

    Q q;
    Consumer(Q q) {
        this.q = q;
        new Thread(this, "Consumer").start();
    }

    public void run() {

        while(true) {
            q.FromSquareToCar();
        }
    }
}

class Consumer1 implements Runnable {
    Q q;

    Consumer1(Q q) {
        this.q = q;
        new Thread(this, "Consumer1").start();
    }

    public void run() {
        while(true) {
            q.InCar();
        }
    }
}

