import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Task_A {
    public static final int numberOfBees = 6;


    public static void main(String[] args) {

        Forest forest =new Forest();

        Bee[] bee_hive = new Bee[numberOfBees];

        for (int i = 0; i < numberOfBees; i++) {
            bee_hive[i] = new Bee("Bee" + String.valueOf(i),forest);
            Thread thread = new Thread(bee_hive[i]);
            thread.start();
        }
    }
}

class Forest{
    public static final int SIZE=300;
    private int[][] forest = new int[SIZE][SIZE];
    private  AtomicInteger counter=new AtomicInteger(0);
    private AtomicBoolean isFound=new AtomicBoolean(false);


    public boolean isForestEmpty(){
        return counter.get()<0;
    }

    public int getCounter(){
        return counter.get();
    }

    public boolean iSpoofFind(){
        return isFound.get();
    }
    public void setPoohFind(boolean is_f){
        this.isFound.set(is_f);
    }
     public int[] getRow(){
        if(!isForestEmpty() && counter.get()<SIZE-1) {
            return forest[counter.getAndIncrement()];
        }
        else{
            return forest[SIZE-1];
        }
    }

    public Forest(){
        forest[(int) (Math.random()*SIZE)][(int) (Math.random()*SIZE)] = 1;
    }

}



class Bee implements Runnable {
    private String beeName;

    private Forest forest;
    Bee(String name,Forest forest) {
        this.beeName = name;
        this.forest=forest;
    }


    public String getBeeName() {
        return beeName;
    }

    public void setBeeName(String name) {
        this.beeName = name;
    }

    @Override
    public void run() {
        while (!forest.isForestEmpty() && !forest.iSpoofFind()) {
            for (int i = 0; i < forest.SIZE; i++) {
                System.out.println(beeName + " - " + forest.getCounter());
                if (forest.getRow()[i] == 1 && !forest.isForestEmpty() ) {
                    forest.setPoohFind(true);
                    System.out.println(beeName + " found on " + forest.getCounter());
                    break;
                }
            }
        }
    }
}



