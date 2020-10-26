import java.awt.*;

public class CarObject {
    private int condition;
    private int phase;
    private final int nextPos;
    private final int Pos;
    private static final int width=40;
    private static final int height=40;

    private final Image img;
    private double x;
    private double y;

     static final int left=2;
     static final int right=0;
     static final int up=1;
     static final int down=3;

     static final int phase1=6;
     static final int phase2=7;
     static final int phase3=8;
     static final int phase4=9;

     static final int run=4;
     static final int wait=5;

     private boolean hasPass;
     private boolean hasEnter;



    public CarObject(Image img,int Pos,int nextPos,CarObject Exist) {
        this.condition=wait;
        this.hasEnter=false;
        this.hasPass=false;
        this.Pos=Pos;
        this.nextPos=nextPos;
        this.img = img;
        if(Pos==left){
            x=roadData.leftRoad.X;
            avoidOverlap(this,Exist);
            if(nextPos==right){
                y=roadData.leftRoad.Out_mid_Y;
            }else if(nextPos==up){
                y=roadData.leftRoad.Out_up_Y;
            }else if(nextPos==down){
                y=roadData.leftRoad.Out_down_Y;
            }
        }else if(Pos==right){
            x=roadData.rightRoad.X;
            avoidOverlap(this,Exist);
            if(nextPos==left){
                y=roadData.rightRoad.Out_mid_Y;
            }else if(nextPos==up){
                y=roadData.rightRoad.Out_up_Y;
            }else if(nextPos==down){
                y=roadData.rightRoad.Out_down_Y;
            }
        }else if(Pos==up){
            y=roadData.upRoad.Y;
            avoidOverlap(this,Exist);
            if(nextPos==down){
                x=roadData.upRoad.Out_mid_X;
            }else if(nextPos==left){
                x=roadData.upRoad.Out_left_X;
            }else if(nextPos==right){
                x=roadData.upRoad.Out_right_X;
            }
        }else if(Pos==down){
            y=roadData.downRoad.Y;
            avoidOverlap(this,Exist);
            if(nextPos==up){
                x=roadData.downRoad.Out_mid_X;
            }else if(nextPos==left){
                x=roadData.downRoad.Out_left_X;
            }else if(nextPos==right){
                x=roadData.downRoad.Out_right_X;
            }
        }
        if(Pos==up&&this.x==roadData.upRoad.Out_mid_X||Pos==down&&this.x==roadData.downRoad.Out_mid_X){
            phase=phase1;
        }else if(Pos==up&&this.x==roadData.upRoad.Out_right_X||Pos==down&&this.x==roadData.downRoad.Out_left_X){
            phase=phase2;
        }else if(Pos==left&&this.y==roadData.leftRoad.Out_mid_Y||Pos==right&&this.y==roadData.rightRoad.Out_mid_Y){
            phase=phase3;
        }else if(Pos==left&&this.y==roadData.leftRoad.Out_up_Y||Pos==right&&this.y==roadData.rightRoad.Out_down_Y){
            phase=phase4;
        }
    }

    public boolean isHasPass() {
        return hasPass;
    }

    public int getPos() {
        return Pos;
    }

    public int getPhase() {
        return phase;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public boolean isHasEnter() {
        return hasEnter;
    }

    public int getCondition() {
        return condition;
    }

    public void setCondition(int condition) {
        this.condition = condition;
    }

    //车辆停止行驶函数
    public void stop(Graphics g){
        g.drawImage(img,(int)x,(int)y,null);
    }

    //车俩更改路口函数
    public void changeRoad(Graphics g){
        if(x<=730&&x>=-200&&y>=-400&&y<=500) {
            g.drawImage(img, (int)x, (int)y, null);
            if(nextPos==left){
                if(x<roadData.leftRoad.X){
                    hasEnter=true;
                }
            }else if(nextPos==right){
                if(x>roadData.rightRoad.X){
                    hasEnter=true;
                }
            }else if(nextPos==up){
                if(y<roadData.upRoad.Y){
                    hasEnter=true;
                }
            }else if(nextPos==down){
                if(y>roadData.downRoad.Y){
                    hasEnter=true;
                }
            }
        }else{
            if(nextPos==left){
                if(x<roadData.leftRoad.Edge_X){
                    hasPass=true;
                }
                if(x<roadData.leftRoad.X){
                    hasEnter=true;
                }
            }else if(nextPos==right){
                if(x>roadData.rightRoad.Edge_X){
                    hasPass=true;
                }
                if(x>roadData.rightRoad.X){
                    hasEnter=true;
                }
            }else if(nextPos==up){
                if(y<roadData.upRoad.Edge_Y){
                    hasPass=true;
                }
                if(y<roadData.upRoad.Y){
                    hasEnter=true;
                }
            }else if(nextPos==down){
                if(y>roadData.downRoad.Edge_Y){
                    hasPass=true;
                }
                if(y>roadData.downRoad.Y){
                    hasEnter=true;
                }
            }
        }
        if(Pos==left){
            if(nextPos==right){
                x+=4;
            }else if(nextPos==up){
                if(x<=roadData.upRoad.In_left_X){
                    x+=4;
                    if(x>=roadData.leftRoad.X){
                        y-=2.5;
                    }
                }else{
                    y-=4;
                }
            }else if(nextPos==down){
                if(x<=roadData.downRoad.In_left_X){
                    x+=4;
                    if(x>=roadData.leftRoad.X){
                        y+=2.5;
                    }
                }else{
                    y+=4;
                }
            }
        }else if(Pos==right){
            if(nextPos==left){
                x-=4;
            }else if(nextPos==up){
                if(x>=roadData.upRoad.In_right_X){
                    x-=4;
                    if(x<=roadData.rightRoad.X)
                        y-=2.5;

                }else{
                    y-=4;
                }
            }else if(nextPos==down){
                if(x>=roadData.downRoad.In_right_X){
                    x-=4;
                    if(x<=roadData.rightRoad.X)
                        y+=2.5;
                }else{
                    y+=4;
                }
            }
        }else if(Pos==up){
            if(nextPos==down){
                y+=4;
            }else if(nextPos==left){
                if(y<=roadData.leftRoad.In_up_Y){
                    y+=4;
                    if(y>=roadData.upRoad.Y)
                        x-=2.5;
                }else{
                    x-=4;
                }
            }else if(nextPos==right){
                if(y<=roadData.rightRoad.In_up_Y){
                    y+=4;
                    if(y>=roadData.upRoad.Y)
                        x+=2.5;
                }else{
                    x+=4;
                }
            }
        }else if(Pos==down){
            if(nextPos==up){
                y-=4;
            }else if(nextPos==left){
                if(y>=roadData.leftRoad.In_down_Y){
                    y-=4;
                    if(y<=roadData.downRoad.Y)
                        x-=2.5;
                }else{
                    x-=4;
                }
            }else if(nextPos==right){
                if(y>=roadData.rightRoad.In_down_Y){
                    y-=4;
                    if(y<=roadData.downRoad.Y)
                        x+=2.5;
                }else{
                    x+=4;
                }
            }
        }
    }

    //车辆运行封装
    public void carMove(Graphics g){
        if(condition==wait) {
            stop(g);
        }
        else if(condition==run){
            changeRoad(g);
        }
    }

    //防止车辆位置重叠
    public void avoidOverlap(CarObject Add,CarObject Exist){
        if(Exist==null)
            return;
         if(Add.Pos==left){
             if(Exist.x-Add.x<=CarObject.width){
                 Add.x= Exist.x-CarObject.width;
             }
         }else if(Add.Pos==right){
             if (Add.x - Exist.x <= CarObject.width) {
                 Add.x= Exist.x+CarObject.width;
             }
         }else if(Add.Pos==up){
             if(Add.y- Exist.y<=CarObject.height){
                 Add.y=Exist.y-CarObject.width;
             }
         }else if(Add.Pos==down){
             if(Exist.y-Add.y<=CarObject.height){
                 Add.y= Exist.y+CarObject.width;
             }
         }
   }

}
