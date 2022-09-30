package b;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


public class Task_B {


    public static void main(String[] args) throws InterruptedException {

        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        int[][] field = new int[3][3];
        Random random = new Random();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] =random.nextInt(0,2) ;
            }
        }

        Thread th1 = new Thread(new GardenThread(lock,field,"gardener"));
        th1.setDaemon(true);
        th1.start();

        Thread th2 = new Thread(new GardenThread(lock,field,"nature"));
        th2.setDaemon(true);
        th2.start();

        Thread th3 = new Thread(new GardenThread(lock,field,"monitor1"));
        th3.setDaemon(true);
        th3.start();

        Thread th4 = new Thread(new GardenThread(lock,field,"monitor2"));
        th4.setDaemon(true);
        th4.start();



        th1.join();
        th2.join();
        th3.join();
        th4.join();


    }
}
