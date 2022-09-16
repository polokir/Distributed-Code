import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.atomic.AtomicInteger;


class ProducerTask implements Runnable {

    SynchronousQueue<Integer> queue;

    public ProducerTask(SynchronousQueue<Integer> queue) {
        this.queue = queue;
        new Thread(this, "Producer").start();
    }

    @Override
    public void run() {
        while (Task_B.item.get() < Task_B.STORAGE_SIZE) {
            try {
                Task_B.item.getAndIncrement();
                queue.put(Task_B.item.get());
                System.out.println("Producer" + " " + Task_B.item.get());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}


class ProducerConsumerTask implements Runnable {

    static SynchronousQueue<Integer> queue1;
    static SynchronousQueue<Integer> queue2;

    public ProducerConsumerTask(SynchronousQueue<Integer> queue1, SynchronousQueue<Integer> queue2) {
        this.queue2 = queue2;
        this.queue1 = queue1;
        new Thread(this, "ProducerConsumer").start();
    }

    @Override
    public void run() {
        while (queue1.isEmpty() || queue2.isEmpty()) {
            try {
                int val = queue1.take();
                queue1.remove(val);
                queue2.put(val);
                System.out.println("Producer&Consumer" + val);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}


class ConsumerTask implements Runnable {

    SynchronousQueue<Integer> queue1;

    public ConsumerTask(SynchronousQueue<Integer> queue1) {
        this.queue1 = queue1;
        new Thread(this, "Consumer").start();
    }

    @Override
    public void run() {
        while (queue1.isEmpty()) {
            try {
                int counter = queue1.take();
                queue1.remove(counter);
                System.out.println("Consumer" + counter);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}


public class Task_B {

    public static int STORAGE_SIZE = 100;
    public static AtomicInteger item = new AtomicInteger(0);


    public static void main(String[] args) {

        SynchronousQueue<Integer> queue1 = new SynchronousQueue<Integer>();
        SynchronousQueue<Integer> queue2 = new SynchronousQueue<Integer>();
        new ProducerTask(queue1);
        new ProducerConsumerTask(queue1, queue2);
        new ConsumerTask(queue2);


    }
}
