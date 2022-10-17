package onJava;

import java.util.concurrent.locks.ReentrantLock;

public class Stock {

    private int numb;

    private ReentrantLock stockRL;

    public Stock(int numb, ReentrantLock stockRL) {
        this.numb = numb;
        this.stockRL = stockRL;
    }

    public int getNumb() {
        return numb;
    }

    public void setNumb(int numb) {
        this.numb = numb;
    }

    public ReentrantLock getStockRL() {
        return stockRL;
    }
}
