
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;

class Pot {

    private int voulume;
    private int currentVolume=0;

    private Semaphore beeSemaphore = new Semaphore(3);

    private Semaphore poohSemaphore = new Semaphore(0);


    public Pot (int volume){
        this.voulume=volume;
    }

    public void collectHoney(){
        try {
            beeSemaphore.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        while (currentVolume != voulume){
            currentVolume++;
            System.out.println(Thread.currentThread().getName() + " put honey");
        }
        System.out.println("Pot has filled");
        poohSemaphore.release(1);
    }

    public void stealHoney(){
        try {
            poohSemaphore.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        currentVolume=0;
        System.out.println("Pooh has eaten all honey");
        beeSemaphore.release(3);
    }


}

class Bee implements Runnable{
    private Pot potter;

    Bee(Pot pot){
        this.potter=pot;
    }

    @Override
    public void run() {
        for(int i =0;i<3;i++){
            potter.collectHoney();
        }
    }
}

class Pooh implements Runnable {
    Pot pot;
    Pooh(Pot pot){
        this.pot=pot;
    }

    @Override

    public void run() {
        for( int i=0;i<3;i++) {
            pot.stealHoney();
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}



public class Task_A {
    public static void main(String[] args) {
        Pot potter = new Pot(14);
        for( int i=0;i<4;++i){
            Thread thread = new Thread(new Bee(potter));
            thread.start();
        }
        Thread thr=new Thread(new Pooh(potter));

        thr.start();
    }
}
