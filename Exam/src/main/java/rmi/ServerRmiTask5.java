package rmi;

import model.Book;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

interface RMIServer extends Remote {
    List<Book> displayWithAuthor(String x);
    List<Book> displayWithPublishingHouse(String x);
    List<Book> displayYearOfPublishingMoreThan(Integer x);
}


public class ServerRmiTask5 {
    public static void main(String[] args) throws RemoteException {
        Registry registry = LocateRegistry.createRegistry(123);
        RMIServer service = new Service();
        registry.rebind("exam", service);
        System.out.println("Server started!");
    }

    static class Service extends UnicastRemoteObject implements RMIServer {
        List<Book> books;

        Service() throws RemoteException {
            super();
            books = new ArrayList<Book>() {
                {
                    add(new Book("1", "Life in deep", 2020, "Cool", "Van",
                            1532, "Gold", 20));
                    add(new Book("2", "HardLife", 2010, "Cool", "Billie",
                            111, "Silver", 35));
                }
            };
        }

        @Override
        public List<Book> displayWithAuthor(String x) {
            List<Book> results = new ArrayList<>();
            for (Book book : books) {
                if (book.getAuthor().equals(x)) {
                    results.add(book);
                }
            }
            return results;
        }

        @Override
        public List<Book> displayWithPublishingHouse(String x) {
            List<Book> results = new ArrayList<>();
            for (Book book : books) {
                if (book.getPublishingHouse().equals(x)) {
                    results.add(book);
                }
            }
            return results;
        }

        @Override
        public List<Book> displayYearOfPublishingMoreThan(Integer x){
            List<Book> results = new ArrayList<>();
            for (Book book : books) {
                if (book.getYearOfPublishing() > x){
                    results.add(book);
                }
            }
            return results;
        }
    }
}
