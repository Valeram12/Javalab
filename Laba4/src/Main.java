import java.util.concurrent.Semaphore;
import java.io.*;
import java.util.Scanner;
class WriterReadersSecond {

    static int readerCount = 0;
    static Semaphore x = new Semaphore(1);
    static Semaphore z = new Semaphore(1);
    static Semaphore rsem = new Semaphore(1);
    static Semaphore wsem = new Semaphore(1);


    static class Read1 implements Runnable {
        @Override
        public void run() {
            try {
                z.acquire();
                rsem.acquire();
                x.acquire();
                readerCount++;
                if (readerCount == 1) wsem.acquire();
                x.release();
                System.out.println("Thread "+Thread.currentThread().getName() + " is READING");
                System.out.println("Enter FIO: ");
                File file= new File("src/lab");
                Scanner scanner= new Scanner(file);
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(System.in));
                String n = reader.readLine();
                boolean a = false;
                System.out.println("Thread "+Thread.currentThread().getName() + " is READING");
                while(scanner.hasNextLine()&&a==false){
                    if (scanner.next().matches(".*\\b"+n+"\\b.*")){
                        System.out.println("Number of "+n+" : "+ scanner.next()+"\n");
                        a=true;
                    }

                }
                Thread.sleep(1500);
                System.out.println("Thread "+Thread.currentThread().getName() + " has FINISHED READING");
                scanner.close();
                x.acquire();
                readerCount--;
                if (readerCount == 0) wsem.release();
                x.release();
                rsem.release();
                z.release();

            } catch (InterruptedException | FileNotFoundException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    static class Read2 implements Runnable {
        @Override
        public void run() {
            try {
                z.acquire();
                rsem.acquire();
                x.acquire();
                readerCount++;
                if (readerCount == 1) wsem.acquire();
                x.release();
                System.out.println("Thread "+Thread.currentThread().getName() + " is READING");
                System.out.println("Enter number : ");
                File file= new File("src/lab");
                Scanner scanner= new Scanner(file);
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(System.in));
                String n = reader.readLine();
                boolean a= false;
                String prev = null;
                while(scanner.hasNextLine()&&a==false){
                    String word = scanner.next();
                    if (scanner.next().contains(n)){
                        System.out.println("FIO of "+n+" : "+ word+"\n");
                        a=true;
                    }

                }
                Thread.sleep(1500);
                System.out.println("Thread "+Thread.currentThread().getName() + " has FINISHED READING");
                scanner.close();
                x.acquire();
                readerCount--;
                if (readerCount == 0) wsem.release();
                x.release();
                rsem.release();
                z.release();

            } catch (InterruptedException | FileNotFoundException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    static class Delete implements Runnable {
        @Override
        public void run() {
            try {
                z.acquire();
                rsem.acquire();
                x.acquire();
                readerCount++;
                if (readerCount == 1) wsem.acquire();
                x.release();
                System.out.println("Thread "+Thread.currentThread().getName() + " is READING");

                System.out.println("Enter Line to delete: ");
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(System.in));
                String n = reader.readLine();
                removeLineFromFile("src/lab",n);

                Thread.sleep(1500);
                System.out.println("Thread "+Thread.currentThread().getName() + " has FINISHED READING");
                x.acquire();
                readerCount--;
                if (readerCount == 0) wsem.release();
                x.release();
                rsem.release();
                z.release();

            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void removeLineFromFile(String file, String lineToRemove) {

        try {

            File inFile = new File(file);

            if (!inFile.isFile()) {
                System.out.println("Parameter is not an existing file");
                return;
            }

            //Construct the new file that will later be renamed to the original filename.
            File tempFile = new File(inFile.getAbsolutePath() + ".tmp");

            BufferedReader br = new BufferedReader(new FileReader(file));
            PrintWriter pw = new PrintWriter(new FileWriter(tempFile));

            String line = null;

            //Read from the original file and write to the new
            //unless content matches data to be removed.
            while ((line = br.readLine()) != null) {

                if (!line.trim().equals(lineToRemove)) {

                    pw.println(line);
                    pw.flush();
                }
            }
            pw.close();
            br.close();

            //Delete the original file
            if (!inFile.delete()) {
                System.out.println("Could not delete file");
                return;
            }

            //Rename the new file to the filename the original file had.
            if (!tempFile.renameTo(inFile))
                System.out.println("Could not rename file");

        }
        catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    static class Write implements Runnable {
        @Override
        public void run() {
            try {
                rsem.acquire();
                wsem.acquire();
                System.out.println("Thread "+Thread.currentThread().getName() + " is WRITING");
                System.out.println("Enter Line to write: ");
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(System.in));
                String n = reader.readLine();

                File file= new File("src/lab");
                FileWriter fr = new FileWriter(file, true);
                fr.write(n);
                fr.close();

                Thread.sleep(2500);
                System.out.println("Thread "+Thread.currentThread().getName() + " has finished WRITING");
                wsem.release();
                rsem.release();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Read1 read1 = new Read1();
        Read2 read2 = new Read2();
        Delete delete = new Delete();
        Write write = new Write();
        Thread t1 = new Thread(read1);
        t1.setName("thread1");
        Thread t2 = new Thread(read2);
        t2.setName("thread2");
        Thread t3 = new Thread(write);
        t3.setName("thread3");
        Thread t4 = new Thread(delete);
        t4.setName("thread4");
        t3.start();
        t1.start();
        t2.start();
        t4.start();
    }
}