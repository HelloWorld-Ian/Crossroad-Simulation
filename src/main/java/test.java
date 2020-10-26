import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class test extends Frame {
     Image carImg=imagetool.getImage("main/image/car.png");
     Image background=imagetool.getImage("main/image/road.png");
     CarObject car=new CarObject(carImg,10,10,null);
     public test(){
        JButton addButton = new JButton("添加");
        addButton.setLocation(1100,1100);
        addButton.setSize(80,40);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("123");
            }
        });
        this.add(addButton);
        JButton c=new JButton(" ");
        c.setContentAreaFilled(false);		//按键透明
        c.setBorderPainted(false);		//边框透明
        c.setEnabled(false);
        this.add(c);
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(background,0,0,null);
        g.drawImage(carImg,roadData.leftRoad.X,roadData.leftRoad.Out_mid_Y,null);
        g.drawImage(carImg,90,-420,null);



    }
    public void launchFrame() {
        this.setSize(1250,1000);
        this.setVisible(true);
        new paintThread().start();
    }
    class paintThread extends Thread{
        @Override
        public void run() {
            while (true) {
                try {
                    repaint();
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
     private Image off=null;
     public void update (Graphics h)
     {

         if(off==null)
             off=this.createImage(500, 780);
         Graphics goff=off.getGraphics();
         paint(goff);
         h.drawImage(off, 0, 0, null);

     }
    public static void main(String[] args) {
        test test = new test();
        test.launchFrame();
    }
}
