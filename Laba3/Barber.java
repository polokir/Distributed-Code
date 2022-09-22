public class Barber implements Runnable {
    @Override
    public void run() {
        synchronized (Task_B.barberMonitor) {
            System.out.println(Thread.currentThread().getName() + ": day is started");

            while (!Task_B.isWorkFinished.get() || !Task_B.queue.isEmpty()) {
                if (Task_B.queue.isEmpty()) {
                    try {
                        System.out.println(Thread.currentThread().getName() + ": barber falling asleep");
                        Task_B.isBarberBusy.set(false);
                        Task_B.barberMonitor.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    Client client = Task_B.queue.remove();
                    System.out.println(Thread.currentThread().getName() + ": getting next client" + client.getName());
                    client.startShaving();
                }
            }
            System.out.println(Thread.currentThread().getName() + ": day is finished");
        }
    }
}
