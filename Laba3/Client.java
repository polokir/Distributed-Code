public class Client extends Thread {
    private boolean isShaved;

    Client() {
        isShaved = false;
    }

    @Override
    public void run() {
        synchronized (Task_B.queueMonitor) {
            System.out.println(Thread.currentThread().getName() + ": now in the queue");
            while (!isShaved) {
                if (!Task_B.isBarberBusy.get()) {
                    synchronized (Task_B.barberMonitor) {
                        Task_B.barberMonitor.notify();
                        Task_B.isBarberBusy.set(true);
                        System.out.println(Thread.currentThread().getName() + ": awaking barber");
                    }
                } else {
                    try {
                        System.out.println(Thread.currentThread().getName() + ": falling asleep");
                        Task_B.queueMonitor.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void startShaving() {
        System.out.println(Thread.currentThread().getName() + ": shaving started");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
