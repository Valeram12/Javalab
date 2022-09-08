import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Gui2 extends JFrame {
    private JButton button1;
    private JPanel Mainpanel;
    private JSpinner spinner1;
    private JSpinner spinner2;
    private JSlider slider1;

    public Gui2(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(500,500,500,250);
        this.setContentPane(Mainpanel);
        this.pack();
        spinner1.setValue(1);
        spinner2.setValue(1);
        button1.addActionListener(new Gui2.ButtonEvent());
    }

    class ButtonEvent implements ActionListener {
        public void actionPerformed( ActionEvent e){
            Thread th1= new Thread(
                    ()->{
                        while (true){
                            synchronized (slider1){
                                slider1.setValue(slider1.getValue()+1);
                            }
                        }
                    });


            Thread th2= new Thread(
                    ()->{
                        while (true){
                            synchronized (slider1){
                                slider1.setValue(slider1.getValue()-1);
                            }
                        }
                    });

            th1.setPriority((Integer) spinner1.getValue());
            th2.setPriority((Integer) spinner2.getValue());
            th1.start();
            th2.start();

//            while (th1.isAlive()||th2.isAlive()){
//                try{
//                    Thread.sleep(100);
//                } catch (InterruptedException a){
//                    a.printStackTrace();
//                }
//            }
        }
    }

}
