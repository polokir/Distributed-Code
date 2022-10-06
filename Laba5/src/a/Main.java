package a;


import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Random;

public class Main {
    static final int SIZE=150;
    static SecureRandom random = new SecureRandom();
    static int side_count=3;
    static Thread[] threads = new Thread[side_count];
    static int[]soldiers = new int[SIZE];
    static Side side = new Side(side_count);

    public static void main(String[] args) {
        Soldier.initArr(side_count);
        for( int i=0;i<SIZE;i++){
            if(random.nextBoolean()){
                soldiers[i]=1;
            }else{
                soldiers[i]=0;
            }
        }
        startTread();

        System.out.println("Result: "+ Arrays.toString(soldiers));
    }

    private static void startTread(){
        for( int i=0;i<threads.length;i++){
            if (i==0) threads[i]=new Thread(new Soldier(soldiers,side,i,0,(i+1)*(SIZE/side_count)+1));
            else if(i==threads.length-1) threads[i]=new Thread(new Soldier(soldiers,side,i,(i)*(SIZE/side_count)-1,(i+1)*(SIZE/side_count)));
            else threads[i]=new Thread(new Soldier(soldiers,side,i,(i)*(SIZE/side_count)-1,(i+1)*(SIZE/side_count)+1));
        }

        for(var item :threads) item.start();
        for (Thread thread : threads) {
            try {thread.join();}
            catch (InterruptedException e) {e.printStackTrace();}
        }
    }
}
