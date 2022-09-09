package com.example.laboratory1;

import javax.swing.*;
import java.awt.event.ActionEvent;


class Thread1 extends Thread{
    private int operand;
    private JSlider jSlider;
    private int priority;
    private int count;
    private static int BOUND = 1000000;

    private JSpinner spinner;

    public Thread1(int operand,JSlider slider, int priority, JSpinner spinner){
        this.operand=operand;
        jSlider=slider;
        this.priority=(int)spinner.getValue();
        this.spinner=spinner;
        setPriority(priority);
    }
    @Override
    public void run() {
        while(!interrupted()){
            int val = (int)(jSlider.getValue());
            ++count;

            if(count > BOUND){
                priority=(int)spinner.getValue();
                setPriority(priority);
                synchronized (jSlider) {
                    jSlider.setValue(val + operand);
                }
                count = 0;
            }
        }
    }

}


 class Task_A {
    public static void main(String[] args) {
        JFrame win = new JFrame();
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        win.setSize(500,500);
        JPanel panel = new JPanel();

        JSlider slider = new JSlider(0,100,50);
        slider.setPaintTrack(true);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setMajorTickSpacing(20);
        slider.setMinorTickSpacing(5);

        JSpinner txt1=new JSpinner();
        JSpinner txt2=new JSpinner();




        txt1.setValue(1);
        txt2.setValue(1);


       Thread1 th1 =new Thread1(1,slider,9,txt1);
       Thread1 th2 =new Thread1(-1,slider,1,txt2);





        JButton btn = new JButton("Ok");
        btn.addActionListener(
                (ActionEvent e) -> {
                   

                       th1.start();
                       th2.start();
                    
                });

//        JButton btnPriority = new JButton("set prior");
//        btnPriority.addActionListener(
//                (ActionEvent e) ->{
//                    synchronized (slider){
//                        th1.setPriority((int)txt1.getValue());
//                        th2.setPriority((int)txt2.getValue());
//                    }
//
//        });




        panel.add(btn);
       // panel.add(btnPriority);
        panel.add(slider);
        panel.add(txt1);
        panel.add(txt2);

        win.setContentPane(panel);
        win.setVisible(true);
    }
}



