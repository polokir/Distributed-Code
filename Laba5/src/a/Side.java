package a;

public class Side {
    private final int sidesStart;
    private int sideAwait;

    public Side(int sides){
        this.sideAwait=sides;
        this.sidesStart=sides;
    }
    public synchronized void await() throws InterruptedException {
        sideAwait--;
        if(sideAwait > 0) this.wait();
        sideAwait = sidesStart;
        notifyAll();
    }
}
