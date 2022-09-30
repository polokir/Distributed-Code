package b;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class GardenThread implements Runnable {
    private int[][] peach;
    private int SIZE = 3;
    private String type;
    ReentrantReadWriteLock reentrantLock;

    public GardenThread(ReentrantReadWriteLock reentrantLock,int[][] peach,String type ){
        this.reentrantLock=reentrantLock;
        this.peach=peach;
        this.type=type;
    }



    public void Gardener() {
        reentrantLock.readLock().lock();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (peach[i][j] == 0) {
                    peach[i][j] = 1;
                    break;
                }
            }
            break;
        }

        reentrantLock.readLock().unlock();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    public void Nature() {
        reentrantLock.readLock().lock();
        Random random = new Random();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                peach[i][j] = random.nextInt(0, 2);
                break;
            }
            break;
        }

        reentrantLock.readLock().unlock();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void Show() {
        reentrantLock.readLock().lock();
        Arrays.stream(peach).map(Arrays::toString).forEach(System.out::println);

        reentrantLock.readLock().unlock();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void fileWriter() throws IOException {
        reentrantLock.writeLock().lock();

        FileWriter writer=new FileWriter("./src/b/garden.txt",true);
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                writer.write(peach[i][j] + " ");
            }
            writer.write("\n");
        }
        writer.write("----------------------------");
        writer.write("\n");
        writer.close();

        reentrantLock.writeLock().unlock();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void run() {
        while (true) {
            switch (type) {
                case "nature":
                    Nature();
                    System.out.println("nature");
                    break;
                case "gardener":
                    Gardener();
                    System.out.println("gardener");
                    break;
                case "monitor1":
                    Show();
                    System.out.println("monitor1");
                case "monitor2":
                    try {
                        fileWriter();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("monitor2");
                    break;
                default:
                    System.out.println("Error type");
            }

        }
    }
}
