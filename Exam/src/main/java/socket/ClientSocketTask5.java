package socket;

import model.Book;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;


public class ClientSocketTask5 {
    private static int port = 9888;

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException{
        InetAddress host = InetAddress.getLocalHost();
        Socket socket = null;
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        Scanner scan = new Scanner(System.in);
        while (true)
        {
            System.out.println("Choose option:\n"
                    + "1 - books by a given author\n"
                    + "2 - books published by a given publisher\n"
                    + "3 - books published after a given year\n"
                    + "4 - exit");
            socket = new Socket(host.getHostName(), port);
            oos = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("Sending request to Socket Server");
            int commandIndex = scan.nextInt();
            if (commandIndex == 4)
            {
                socket = new Socket(host.getHostName(), port);
                oos = new ObjectOutputStream(socket.getOutputStream());
                System.out.println("Sending close Request");
                oos.writeInt(commandIndex); oos.flush();
                break;
            }
            switch (commandIndex) {
                case 1,2,3 -> {
                    System.out.println("Enter X:");
                    String x = scan.nextLine();
                    String message = "" + commandIndex + "#" + x;
                    oos.writeBytes(message);
                    oos.flush();
                    break;
                }

            }
            System.out.println("Results: ");
            ois = new ObjectInputStream(socket.getInputStream());
            ArrayList<Book> results = (ArrayList<Book>) ois.readObject();
            for (Book book : results)
            {
                System.out.println(book.toString());
            }
            ois.close();
            oos.close();
            Thread.sleep(100);
        }
        oos.writeInt(3);
        System.out.println("Shutting down client!!");
    }
}
