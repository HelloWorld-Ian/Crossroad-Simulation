import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import static javafx.scene.input.KeyCode.H;

class study extends Frame {
    private  int span=1000;
    public Map<Integer,Map<Integer,Deque<CarObject>>>runningCars=new HashMap<Integer, Map<Integer, Deque<CarObject>>>();
    private boolean inRound=false;
    private int roadOpen=-1;
    Image carImg=imagetool.getImage("main/image/car.png");
    CarObject[] cars=new CarObject[40];
    Image background=imagetool.getImage("main/image/road.png");
    private int carNums=0;
    public study(){
        JButton AddAllButton=new JButton("全部添加");
        JButton AddButton=new JButton("添加车辆");
        JButton StartButton=new JButton("开始移动");

        AddAllButton.setLocation(1130,700);
        AddAllButton.setSize(100,50);
        AddButton.setLocation(1130,900);
        AddButton.setSize(100,50);
        StartButton.setLocation(1130,800);
        StartButton.setSize(100,50);


        for(int i=0;i<4;i++){
            Map<Integer,Deque<CarObject>>temp=new HashMap<Integer, Deque<CarObject>>();
            for(int j=0;j<4;j++){
                if(j!=i){
                    temp.put(j,new LinkedList<CarObject>());
                }
            }
            runningCars.put(i,temp);
        }

        //路口车辆开始行驶
        StartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    inRound=true;
                    Random random = new Random();
                    roadOpen= random.nextInt(4)+6;
                    for (CarObject x : cars) {
                        if(x!=null) {
                            if (isright(x)) {
                                x.setCondition(CarObject.run);
                            } else if (x.getPhase() == roadOpen) {
                                x.setCondition(CarObject.run);
                            }
                        }
                    }

            }
        });

        //添加车辆
        AddButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!inRound){
                    if (carNums < cars.length) {
                        Random rand = new Random();
                        int Pos = rand.nextInt(4);
                        int nextPos = Pos;
                        while (Pos == nextPos) {
                            nextPos = rand.nextInt(4);
                        }
                        CarObject Exist = runningCars.get(Pos).get(nextPos).isEmpty() ?
                                null : runningCars.get(Pos).get(nextPos).peekLast();
                        addCars(carNums, Pos, nextPos, Exist);
                        runningCars.get(Pos).get(nextPos).addLast(cars[carNums]);
                        carNums++;
                    } else {
                        carNums = 0;
                    }

                }
            }
        });

        AddAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!inRound) {
                    boolean allNull = true;
                    for (CarObject car : cars) {
                        if (car != null) {
                            allNull = false;
                            break;
                        }
                    }
                    if (!allNull) {
                        for (int i = 0; i < 40; i++) {
                            cars[i] = null;
                        }
                        for (int i = 0; i < 4; i++) {
                            for (int j = 0; j < 4; j++) {
                                if (i != j)
                                    runningCars.get(i).get(j).clear();
                            }
                        }
                    }
                        Random rand = new Random();
                        for (int i = 0; i < 40; i++) {
                            int Pos = rand.nextInt(4);
                            int nextPos = Pos;
                            while (Pos == nextPos) {
                                nextPos = rand.nextInt(4);
                            }
                            CarObject Exist = runningCars.get(Pos).get(nextPos).isEmpty() ?
                                    null : runningCars.get(Pos).get(nextPos).peekLast();
                            addCars(i, Pos, nextPos, Exist);
                            runningCars.get(Pos).get(nextPos).addLast(cars[i]);
                        }

                    }
                }

        });

        this.add(AddAllButton);
        this.add(StartButton);
        this.add(AddButton);

        JButton c=new JButton(" ");
        c.setContentAreaFilled(false);
        c.setBorderPainted(false);
        c.setEnabled(false);
        this.add(c);
    }

    //判断该车是否在路口的右行车道，若在，则不影响路口的车辆通行
    private boolean isright(CarObject car){
        if(car.getPos()==CarObject.left){
            return car.getY() == roadData.leftRoad.Out_down_Y;
        }else if(car.getPos()==CarObject.right){
            return car.getY() == roadData.rightRoad.Out_up_Y;
        }else if(car.getPos()==CarObject.up){
            return car.getX() == roadData.upRoad.Out_left_X;
        }else if(car.getPos()==CarObject.down){
            return car.getX() == roadData.downRoad.Out_right_X;
        }
        return false;
    }
    //判断该车是否在道路封闭前行驶出路口
    private boolean isWait(CarObject car){

            if (car.getPos() == CarObject.left&&car.getY()!=roadData.leftRoad.Out_down_Y) {
                return car.getX() < roadData.leftRoad.X;
            } else if (car.getPos() == CarObject.right&&car.getY()!=roadData.rightRoad.Out_up_Y) {
                return car.getX() > roadData.rightRoad.X;
            } else if (car.getPos() == CarObject.up&&car.getX()!=roadData.upRoad.Out_left_X) {
                return car.getY() < roadData.upRoad.Y;
            } else if (car.getPos() == CarObject.down&&car.getX()!=roadData.downRoad.Out_right_X) {
                return car.getY() > roadData.downRoad.Y;
            }
            return false;
    }

    //车辆添加的函数封装
    private void addCars(int i, int Pos, int nextPos,CarObject Exist){
        cars[i]=new CarObject(carImg,Pos,nextPos,Exist);
    }

    //程序视觉效果展示
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawLine(1120,0,1120,1000);
        g.drawImage(background,0,0,null);
        for(CarObject x:cars){
            if(x!=null) {
                x.carMove(g);
            }
        }

    }

    //更改通行的道路
    private void changeOpenRoad(){
        if(!inRound)
            return;
        int newOpen=roadOpen==CarObject.phase4?6:roadOpen+1;

        //更改车辆状态
        int lastOpen=roadOpen;
        roadOpen=newOpen;
        for (CarObject x : cars) {
            if(x!=null) {
                if(x.getPhase()==lastOpen){
                    if(isWait(x))
                        x.setCondition(CarObject.wait);
                }else if (x.getPhase() == newOpen)
                    x.setCondition(CarObject.run);
            }
        }
    }

    public void launchFrame() {
        this.setSize(1250,1000);
        this.setVisible(true);
        new paintThread().start();
        new timer().start();
        new checkIsPassed().start();
        new checkAllPassed().start();
        //new switchRoad().start();
    }

    //使画面动起来的线程
    class paintThread extends Thread{
        @Override
        public void run() {
            while (true) {
                try {
                    repaint();
                    Thread.sleep(45);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private boolean checkIsPass(){
        boolean allPassed=true;
        for(CarObject x:cars){
            if(x!=null) {
                if(x.getPhase()==roadOpen&&!isright(x)&&x.getCondition()==CarObject.run) {
                    if(!x.isHasEnter())
                        allPassed = false;
                }
            }
        }
        return allPassed;
    }
    //时间间隔参数，算法设计核心，控制通行路口的转换
    class timer extends Thread{
        @Override
        public void run(){
            while(true){
                   try {
                        if(inRound) {
                            Thread.sleep(span);
                        }else{
                            Thread.sleep(15);
                        }
                        changeOpenRoad();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

            }
        }
    }

    //判断车辆是否已经驶出路口
    class checkIsPassed extends Thread{
        @Override
        public void run() {
            while(true){
                   try {
                        Thread.sleep(15);
                        for (int i = 0; i < cars.length; i++) {
                            if (cars[i] != null) {
                                if (cars[i].isHasPass())
                                    cars[i] = null;
                            }
                        }

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
            }
        }
    }
    class switchRoad extends Thread{
        @Override
        public void run() {
            while(true){
                try{
                    Thread.sleep(10);
                    boolean allPassed=true;
                    for(CarObject x:cars){
                        if(x!=null) {
                            if(x.getPhase()==roadOpen) {
                                if(!x.isHasEnter())
                                    allPassed = false;
                            }
                        }
                    }
                    if(allPassed){
                        changeOpenRoad();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    class checkAllPassed extends Thread{
        @Override
        public void run() {
            while(true){
                try{
                    Thread.sleep(35);
                    //判断所有车辆是否都已通过
                    boolean allNull=true;
                    for(CarObject car:cars){
                        if (car != null) {
                            allNull = false;
                            break;
                        }
                    }
                    if(allNull) {
                        inRound = false;
                        roadOpen = -1;
                        for (int i = 0; i < 4; i++) {
                            for (int j = 0; j < 4; j++) {
                                if (i != j)
                                    runningCars.get(i).get(j).clear();
                            }
                        }
                        carNums = 0;
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //双缓冲，解决闪烁问题
    private Image off=null;
    public void update (Graphics h)
    {
        if(off==null)
            off=this.createImage(1100, 1000);
        Graphics goff=off.getGraphics();
        paint(goff);
        h.drawImage(off, -60, 0, null);

    }

    public static void main(String[] args) {
        study test = new study();
        test.launchFrame();
    }
}
