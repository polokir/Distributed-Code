package a;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Task_A {
    public static void main(String[] args) throws FileNotFoundException {
        Account account = new Account(" ", " "," ","");
        ArrayList<Account>fromFile = new ArrayList<>();
        ReadWriteLock readWriteLock = new ReadWriteLock();
        for (int i=0;i<8;i++) {
            Thread thread = new Thread(new myThread(readWriteLock, account, "name", fromFile));
            Thread th1 = new Thread(new myThread(readWriteLock, account, "phone", fromFile));

            thread.start();
            th1.start();
        }
    }
}
