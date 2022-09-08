import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Gui extends JFrame{
    private JSlider slider1;
    private JButton start1;
    private JButton start2;
    private JButton stop1;
    private JButton stop2;
    private JPanel Mainpanel;

    public Gui(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setSize(600,400);
        this.setContentPane(Mainpanel);
        this.pack();
        start1.addActionListener(new Gui.Start1());
        start2.addActionListener(new Gui.Start2());
        stop1.addActionListener(new Gui.Stop1());
        stop2.addActionListener(new Gui.Stop2());
    }


    Thread th1 = new Thread(()->{
        while (!Thread.currentThread().interrupted()){
            slider1.setValue(slider1.getValue()+1);
        }

    });
    Thread th2 = new Thread(()->{
        while (!Thread.currentThread().interrupted()){
            slider1.setValue(slider1.getValue()-1);
        }

    });


    class Start1 implements ActionListener {
        public void actionPerformed( ActionEvent e){
            if (sem.isValue() == false){
               th1 = new Thread(()->{
                    while (!Thread.currentThread().interrupted()){
                        slider1.setValue(slider1.getValue()+1);
                    }
                });
                th1.start();
                sem.setValue(true);
            }
            else {
                JOptionPane.showMessageDialog(null, "Другий потік запущений!");
            }
        }
    }

    class Start2 implements ActionListener {
        public void actionPerformed( ActionEvent e){
            if (sem.isValue() == false){

                th2 = new Thread(()->{
                    while (!Thread.currentThread().interrupted()){
                        slider1.setValue(slider1.getValue()-1);
                    }
                });

                th2.start();
                sem.setValue(true);
            }
            else {
                JOptionPane.showMessageDialog(null, "Перший потік запущений!");
            }
        }
    }
    semafor sem  = new semafor();
    class semafor{
        boolean value = false;

        public synchronized boolean isValue() {
            return value;
        }

        public synchronized void setValue(boolean value) {
            this.value = value;
        }
    }

    class Stop1 implements ActionListener{public void actionPerformed( ActionEvent e){

        sem.setValue(false);
        try {

            Thread.sleep(1);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
        th1.interrupt();


    }}
    class Stop2 implements ActionListener {public void actionPerformed( ActionEvent e){
        sem.setValue(false);
        try {
            Thread.sleep(1);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
        th2.interrupt();


    }}
}
