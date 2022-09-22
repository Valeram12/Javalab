import java.util.Scanner;
import java.util.Random;

class main {
    public static void main(String[] args) throws InterruptedException {

        int min = 1;
        int max = 10;

        System.out.println("Enter the pot size and amount of bees\n");
        Scanner sc = new Scanner(System.in);
        int potSize = sc.nextInt();
        int numBees = sc.nextInt();

        Pot pot = new Pot(potSize, potSize);

        Thread bees[] = new Thread[numBees];

        Thread bear = new Thread(new Bear(pot));


        for(int i = 0; i < numBees; i++) {
            bees[i] = new Thread(new Bees(i+1, min, max, pot));
            bees[i].start();
        }

        bear.start();

        bear.join();

        for(int i = 0; i < numBees; i++) {
            bees[i].join();
        }


        System.out.println("Not for ever??");

    }
}

class Pot {
    int honey;
    int init_honey;

    public Pot(int honey, int init_honey) {
        this.honey = honey;
        this.init_honey = init_honey;
    }

    public synchronized void eating() {
        try {
            this.wait();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.printf("Bear is eating\n");
        honey = 0;
    }

    public synchronized void put(int id) {
        if(honey < init_honey) {
            honey += 1;
            System.out.printf("Bee %d has produced honey, honey: %d\n", id, honey);
        } else
            notify();
    }
}

 class Bees implements Runnable {
    int id;
    int min;
    int max;

    Pot pot;

    public Bees(int id, int min, int max, Pot pot) {
        this.id = id;
        this.min = min;
        this.max = max;
        this.pot = pot;
    }

    public void run() {
        while(true) {
            try {
                pot.put(id);
                Thread.sleep(((new Random()).nextInt(max - min + 1) + min)*1000);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}

class Bear implements Runnable {
    Pot pot;

    public Bear(Pot pot) {
        this.pot = pot;
    }

    public void run() {
        while(true)
            pot.eating();
    }
}