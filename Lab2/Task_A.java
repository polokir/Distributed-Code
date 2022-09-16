import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Task_A {
    public static final int forest_size = 25000;
    public static final int numberOfBees = 6;
    public static Control manager = new Control();
    public static int[][] forest = new int[forest_size][forest_size];

    public static void main(String[] args) {

       Pooh pooh = new Pooh();
        forest[pooh.row][pooh.col] = 1;

        Bee[] bee_hive = new Bee[numberOfBees];

        for (int i = 0; i < numberOfBees; i++) {
            bee_hive[i] = new Bee("Bee" + String.valueOf(i));
            Thread thread = new Thread(bee_hive[i]);
            thread.start();
        }
    }
}

class Pooh {
    public int row;
    public int col;

    public Pooh(){
        this.row = (int) (Math.random() * Task_A.forest_size);
        this.col =(int)  (Math.random() * Task_A.forest_size);
    }
}

class Bee implements Runnable {
    private String beeName;
    private int row = 0;

    Bee(String name) {
        this.beeName = name;
    }

    public String getBeeName() {
        return beeName;
    }

    public void setBeeName(String name) {
        this.beeName = name;
    }

    @Override
    public void run() {
        while (!Task_A.manager.IsFound.get()) {
            synchronized (Task_A.manager) {
                row = Task_A.manager.counter.getAndIncrement();
            }
            System.out.println(beeName + " - " + row);
            for (int i = 0; i < Task_A.forest_size; i++) {
                if (Task_A.forest[row][i] == 1 && !Task_A.manager.IsFound.get() && Task_A.manager.counter.get()<Task_A.forest_size) {
                    Task_A.manager.IsFound.set(true);

                    System.out.println(beeName + " found on " + row);

                    break;
                }
            }
        }
    }
}


class Control {
    public  AtomicInteger counter=new AtomicInteger();
    public  AtomicBoolean IsFound=new AtomicBoolean();

    Control(){
        this.counter.set(0);
        this.IsFound.set(false);
    }


}
