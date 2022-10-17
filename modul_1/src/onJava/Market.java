package onJava;

import java.util.concurrent.locks.ReentrantLock;

public class Market implements Runnable {
    private int normalIndex=10;
    private int quantity;
    private int marketIndex;
    private ReentrantLock marketRl;

    private Stock stock;

    public Market(int quantity, ReentrantLock marketRl, Stock stock) {
        this.quantity = quantity;
        this.marketRl = marketRl;
        this.stock = stock;
    }

    private void buyStock() throws InterruptedException {
        stock.getStockRL().lock();
        if(stock.getNumb()>1){
            int bought=stock.getNumb();
            stock.setNumb(bought-1);
        }
        System.out.println("buy a stock "+ "NUMB "+stock.getNumb());
        stock.getStockRL().unlock();

        marketRl.lock();
        marketIndex-=1/quantity;
        if(normalIndex-marketIndex>0.5){
            System.out.println("Normalizing");
            Thread.sleep(2000);
            marketIndex=normalIndex;
        }
        marketRl.unlock();
    }

    private void sellStock(){
        stock.getStockRL().lock();
        int sold = stock.getNumb()+1;
        stock.setNumb(sold);
        System.out.println("Selling " + stock.getNumb());
        stock.getStockRL().unlock();

        marketRl.lock();
        marketIndex+=1/quantity;
        marketRl.unlock();
    }

    @Override
    public void run() {
        while(true) {
            try {
                buyStock();
                sellStock();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
