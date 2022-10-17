package onJava;

import java.util.concurrent.locks.ReentrantLock;

public class Main {
    public static void main(String[] args) {
        ReentrantLock mL=new ReentrantLock();
        ReentrantLock sl = new ReentrantLock();

        Stock stock = new Stock(10,sl);

        Thread thread1= new Thread(new Market(5,mL,stock));
        Thread thread2= new Thread(new Market(5,mL,stock));
        thread1.start();
        thread2.start();
    }
}
