import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

 class SleepingBarber {

    public static void main(String a[])
    {
        Bshop shop = new Bshop();

        Barber barber = new Barber(shop);
        custRnd cg = new custRnd(shop);

        Thread thbarber = new Thread(barber);
        Thread thcg = new Thread(cg);
        thcg.start();
        thbarber.start();
    }
}

class Barber implements Runnable
{
    Bshop shop;

    public Barber(Bshop shop)
    {
        this.shop = shop;
    }
    public void run()
    {
        try
        {
            Thread.sleep(10000);
        }
        catch(InterruptedException iex)
        {
            iex.printStackTrace();
        }
        System.out.println("Barber started..");
        while(true)
        {
            shop.cutHair();
        }
    }
}
class Customer implements Runnable
{
    String name;


    Bshop shop;

    public Customer(Bshop shop)
    {
        this.shop = shop;
    }

    public String getName() {
        return name;
    }



    public void setName(String name) {
        this.name = name;
    }



    public void run()
    {
        goForHairCut();
    }
    private synchronized void goForHairCut()
    {
        shop.add(this);
    }
}

class custRnd implements Runnable
{
    Bshop shop;

    public custRnd(Bshop shop)
    {
        this.shop = shop;
    }

    public void run()
    {
        while(true)
        {
            Customer customer = new Customer(shop);
            Thread thcustomer = new Thread(customer);
            customer.setName("Customer Thread "+thcustomer.getId());
            thcustomer.start();

            try
            {
                TimeUnit.SECONDS.sleep((long)(Math.random()*10));
            }
            catch(InterruptedException iex)
            {
                iex.printStackTrace();
            }
        }
    }

}

class Bshop
{
    int nchair;
    List<Customer> listCustomer;

    public Bshop()
    {
        nchair = 3;
        listCustomer = new LinkedList<Customer>();
    }

    public void cutHair()
    {
        Customer customer;
        System.out.println("Barber waiting for lock.");
        synchronized (listCustomer)
        {

            while(listCustomer.size()==0)
            {
                System.out.println("Barber is waiting for customer.");
                try
                {
                    listCustomer.wait();
                }
                catch(InterruptedException iex)
                {
                    iex.printStackTrace();
                }
            }
            System.out.println("Barber found a customer in the queue.");
            customer = (Customer)((LinkedList<?>)listCustomer).poll();
        }
        long duration=0;
        try
        {
            System.out.println("Cuting hair of Customer : "+customer.getName());
            duration = (long)(Math.random()*10);
            TimeUnit.SECONDS.sleep(duration);
        }
        catch(InterruptedException iex)
        {
            iex.printStackTrace();
        }
        System.out.println("Completed Cuting hair of Customer : "+customer.getName() + " in "+duration+ " seconds.");
    }

    public void add(Customer customer)
    {
        System.out.println("Customer : "+customer.getName());

        synchronized (listCustomer)
        {
            if(listCustomer.size() == nchair)
            {
                System.out.println("No chair available for customer "+customer.getName());
                System.out.println("Customer "+customer.getName()+"Exists...");
                return ;
            }

            ((LinkedList<Customer>)listCustomer).offer(customer);
            System.out.println("Customer : "+customer.getName()+ " got the chair.");

            if(listCustomer.size()==1)
                listCustomer.notify();
        }
    }
}