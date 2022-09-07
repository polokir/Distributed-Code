import com.sun.tools.javac.Main;

import javax.swing.*;
import java.awt.event.ActionEvent;



class Thread2 extends Thread{
    private int operand;
    private JSlider jSlider;

    public Thread2(int operand,JSlider slider){
        this.operand=operand;
        jSlider=slider;
    }
    @Override
    public void run() {

        Task_B.semaphore=1;
        Task_B.thread_label.setText("Занято");
        while (!interrupted()){
            int val = jSlider.getValue();
            if((val>10 && operand<0)||(val<90 && operand>0)){
                jSlider.setValue(val+operand);
            }
        }
        Task_B.semaphore=0;
        Task_B.thread_label.setText("Вільно");

    }

}


class Task_B {
    public static int semaphore=0;
    public static Thread2 thread1;
    public static Thread2 thread2;
    public static JLabel thread_label =new JLabel("Вільно");
    public static void main(String[] args) {
        JFrame win = new JFrame();
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        win.setSize(500,500);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));

        JSlider slider = new JSlider(0,100,50);
        slider.setPaintTrack(true);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setMajorTickSpacing(20);
        slider.setMinorTickSpacing(5);


        JPanel threadPanel1= new JPanel();
        JButton threadStart1 =new JButton("ПУСК1");
        JButton threadStop1 =new JButton("СТОП1");
        threadStop1.setEnabled(false);

        JPanel threadPanel2= new JPanel();
        JButton threadStart2 =new JButton("ПУСК2");
        JButton threadStop2 =new JButton("СТОП2");





        threadStart1.addActionListener(e -> {
            if (semaphore==0) {
                thread1 = new Thread2(1, slider);
                thread1.start();
                thread1.setPriority(Thread.MIN_PRIORITY);
                threadStop1.setEnabled(true);
                threadStart1.setEnabled(false);
            }

        });

        threadStop1.addActionListener(e -> {
            if(semaphore==1) {
                thread1.interrupt();
                threadStop1.setEnabled(false);
                threadStart1.setEnabled(true);
            }
        });

        threadStart2.addActionListener(e -> {
            if(semaphore==0) {
                thread2 = new Thread2(-1, slider);
                thread2.start();
                thread2.setPriority(Thread.MAX_PRIORITY);
                threadStop2.setEnabled(true);
                threadStart2.setEnabled(false);
            }
        });

        threadStop2.addActionListener(e -> {
            if (semaphore==1) {
                thread2.interrupt();
                threadStop2.setEnabled(false);
                threadStart2.setEnabled(true);
            }
        });


        threadPanel1.add(threadStart1);
        threadPanel1.add(threadStop1);
        threadPanel2.add(threadStart2);
        threadPanel2.add(threadStop2);
        threadPanel2.add(thread_label);





        panel.add(slider);
        panel.add(threadPanel1);
        panel.add(threadPanel2);

        win.setContentPane(panel);
        win.setVisible(true);
    }
}



