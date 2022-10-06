package a;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class Soldier implements Runnable {
    private static final AtomicBoolean isFinished =  new AtomicBoolean(false);
    private static final List<Boolean> finished = new ArrayList<>();

    private int[] soldiers;
    private int sideIndex;
    private int leftIndex;
    private int rightIndex;
    private Side side;

    public Soldier(int[] soldiers,Side side, int sideIndex, int leftIndex, int rightIndex ){
        this.soldiers=soldiers;
        this.leftIndex=leftIndex;
        this.rightIndex=rightIndex;
        this.sideIndex=sideIndex;
        this.side = side;
    }


    @Override
    public void run() {
        while (!isFinished.get()){
            boolean current = finished.get(sideIndex);
            if(!current) {
                if (sideIndex == 0) System.out.println(Arrays.toString(soldiers));
                boolean isFormated = true;
                for (int i = leftIndex; i < rightIndex - 1; i++) {
                    if (soldiers[i] != soldiers[i + 1]) {
                        if (soldiers[i] == 1)
                            soldiers[i] = 0;
                        else
                            soldiers[i] = 1;
                        isFormated = false;
                    }

                }
                if (isFormated) finish();
            }
            try {
                side.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }

    private void finish(){
        finished.set(sideIndex,true);
        for(var side : finished)
            if(!side) return;
        isFinished.set(true);
    }

    public static void initArr(int n){
        for (int i=0;i<n;i++){
            finished.add(false);
        }
    }
}
