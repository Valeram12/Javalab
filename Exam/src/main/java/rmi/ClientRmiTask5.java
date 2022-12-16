package rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;


public class ClientRmiTask5 {
    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {
        int choice = 1000;
        String x;
        Scanner in = new Scanner(System.in);
        try {
            RMIServer st = (RMIServer) Naming.lookup("//localhost:123/exam");
            System.out.println("Choose option:\n"
                    + "1 - books by a given author\n"
                    + "2 - books published by a given publisher\n"
                    + "3 - books published after a given year\n"
                    + "4 - exit");
            choice = in.nextInt();
            switch (choice) {
                case 1 -> {
                    System.out.println("Enter X:");
                    x = in.nextLine();
                    System.out.println(st.displayWithAuthor(x));
                }

                case 2 -> {
                    System.out.println("Enter X:");
                    x = in.nextLine();
                    System.out.println(st.displayWithPublishingHouse(x));
                }

                case 3 -> {
                    System.out.println("Enter X:");
                    x = "100";
                    System.out.println(st.displayYearOfPublishingMoreThan(Integer.parseInt(x)));
                }
            }
        } catch (RemoteException | NotBoundException | MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
