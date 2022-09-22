

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;


public class Task_B {

    public static AtomicBoolean isWorkFinished = new AtomicBoolean();
    public static AtomicBoolean isBarberBusy= new AtomicBoolean();
    public static Object barberMonitor = new Object();
    public static final Object queueMonitor = new Object();
    public static Queue<Client> queue = new ConcurrentLinkedQueue<>();

    public static void main(String[] args) {
        barbershopWork();
    }



    public static void barbershopWork() {
        Thread barber = new Thread(new Barber());
        barber.start();
        try {
            Thread.sleep(2000);
        } catch(InterruptedException e){
            e.printStackTrace();
        }
        for(int i = 0; i < 3; i++) {
            Client client = new Client();
            queue.add(client);
            System.out.println(Thread.currentThread().getName() + ": client comes to barbershop");
            client.start();
        }
        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Client client = new Client();
        queue.add(client);
        System.out.println(Thread.currentThread().getName() + ": client comes to barbershop");

        client.start();

        isWorkFinished.set(true);
    }
}