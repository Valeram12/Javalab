package socket;

import model.Book;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

class Callback
{
    public boolean shouldEnd = false;
}

class HouseHandler implements Runnable
{
    private Socket socket;
    private Callback callback;
    private List<Book> books;

    public HouseHandler(Socket socket, Callback callback, List<Book> books)
    {
        this.callback = callback;
        this.socket = socket;
        this.books = books;
    }

    @Override
    public void run() {
        try {
            InputStreamReader ois = new InputStreamReader(socket.getInputStream());
            System.out.println("Receiving input");
            BufferedReader reader = new BufferedReader(ois);
            String message = reader.readLine();
            String[] splitMessage = message.split("#");
            String commandIndex = splitMessage[0];
            System.out.println("Command " + splitMessage[0]);

            if (commandIndex.equals("4"))
            {
                System.out.println("Close command");
                callback.shouldEnd = true;
                return;
            }
            List<Book> result = new ArrayList<>();
            switch (commandIndex) {
                case "1" -> {
                    System.out.println(splitMessage);
                    String x = splitMessage[1];
                    for (Book book : books) {
                        if (book.getAuthor().equals(x)) {
                            result.add(book);
                        }
                    }
                    break;
                }
                case "2" -> {
                    System.out.println(splitMessage);
                    String x = splitMessage[2];
                    for (Book book : books) {
                        if (book.getPublishingHouse().equals(x)) {
                            result.add(book);
                        }
                    }
                    break;
                }
                case "3" -> {
                    Integer x = Integer.parseInt(splitMessage[1]);
                    for (Book book : books) {
                        if (book.getYearOfPublishing() > x){
                            result.add(book);
                        }
                    }
                    break;
                }
                default -> throw new IllegalStateException("Unexpected value: " + commandIndex);
            }
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(result);
            ois.close();
            oos.close();
            socket.close();
        }
        catch (IOException e) { }
    }
}


public class ServerSocketTask5 {
    private static ServerSocket server;

    private static int port = 9888;

    public static Callback c = new Callback();

    public static void main(String args[]) throws IOException, ClassNotFoundException{
        server = new ServerSocket(port);
        List<Book> books =  new ArrayList<Book>() {
            {
                add(new Book("1", "Life in deep", 2020, "Cool", "Van",
                        1532, "Gold", 20));
                add(new Book("2", "HardLife", 2010, "Cool", "Billie",
                        111, "Silver", 35));
            }
        };

        while(!c.shouldEnd){
            System.out.println("Waiting for the client request");
            Socket socket = server.accept();
            HouseHandler handler = new HouseHandler(socket, c, books);
            handler.run();
        }
        System.out.println("Shutting down Socket server!!");
        server.close();
    }
}
