import java.util.Arrays;

public class Main {
    static  int[][] array = new int[10][10];
    public static void main(String[] args) {

        System.out.println("Hello world!");

        for(int i=0;i<10;i++){
            for(int j=0;j<10;j++){
                array[i][j]=0;
            }
        }

        array[9][9]=1;
        for (int i = 0; i < array.length; i++) {
            System.out.println(Arrays.toString(array[i]));
        }

        th1.start();
        th2.start();

    }

    public static void Stop1(){
        try {

            Thread.sleep(10);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
        th1.interrupt();
    }
    public static void Stop2(){
        try {

            Thread.sleep(10);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
        th2.interrupt();
    }
    static IntI i  = new IntI();
    static semafor sem = new semafor();
    static semafor2 sem2 = new semafor2();
    static Thread th1 = new Thread(()->{
        while (!Thread.currentThread().interrupted()) {
            if (sem2.isValue() != true) {
                if (i.isValue() != 10 && sem.isValue() == false) {
                    sem2.setValue(true);
                    for (int j = 0; j < 10; j++) {
                        if (array[i.isValue()][j] != 0) {
                            System.out.println("th1 Find Winnie the Pooh!");
                            System.out.println(array[i.isValue()][j]);
                            System.out.print(i.isValue());
                            System.out.println(j);
                            System.out.println("---------------");
                            j = 10;
                            sem.setValue(true);
                            Stop1();
                        } else {
                        }

                    }
                    i.setValue(i.isValue() + 1);
                    sem2.setValue(false);
                } else {

                    Stop1();
                    break;
                }
            }
        }

//        }

    });

    static Thread th2 = new Thread(()->{
        while (!Thread.currentThread().interrupted()) {

            if (sem2.isValue() != true) {
                if (i.isValue() != 10 && sem.isValue() == false) {
                    sem2.setValue(true);
                    for (int j = 0; j < 10; j++) {
                        if (array[i.isValue()][j] != 0) {
                            System.out.println("th2 Find Winnie the Pooh!");
                            System.out.println(array[i.isValue()][j]);
                            System.out.print(i.isValue());
                            System.out.println(j);
                            System.out.println("---------------");
                            j = 10;
                            sem.setValue(true);
                            Stop2();
                        } else {
                        }

                    }
                    i.setValue(i.isValue() + 1);
                    sem2.setValue(false);
                } else {

                    Stop2();
                    break;
                }
            }
        }


    });



    static class IntI{
        int value = 0;

        public synchronized int isValue() {
            return value;
        }

        public synchronized void setValue(int value) {
            this.value = value;
        }
    }

    static class semafor{
        boolean value = false;

        public synchronized boolean isValue() {
            return value;
        }

        public synchronized void setValue(boolean value) {
            this.value = value;
        }
    }
    static class semafor2{
        boolean value = false;

        public synchronized boolean isValue() {
            return value;
        }

        public synchronized void setValue(boolean value) {
            this.value = value;
        }
    }
}