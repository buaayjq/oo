package taxis;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;


public class Taxi extends Thread implements Runnable{
    /*Overview
  这个类保存出租车的属性并指挥怎么走
  */


     int num;
      int time;
     int credit;
     point position;
     Ask carask;
     TaxiState taxiState;
      int state;
    boolean hasPassenger = false;



    public boolean repOK(){
        //Effects: returns true if the rep variant holds for this, otherwise returns false
        if(num>100|num<0) return false;
        if(credit<-1) return false;

        return true;
    }


    public Taxi(int num){
        //Requires: 各种属性
        //Modifies: 各种属性
        //Effects: 构造器
        this.num = num;
        time = 0;
        this.credit = 0;
        carask = null;
        Random random = new Random();
        position = FileRead.getmapplus(random.nextInt(80),random.nextInt(80));
        state = 0;
         taxiState = TaxiState.runnimg;

    }

    /*获取当前出租车是否有乘客*/
    public void setHasPassenger(boolean hasPassenger) {
        //Requires: boolean型的hasPassenger
        //Modifies: hasPassenger属性
        //Effects: 设置hasPassenger属性为hasPassenger
        this.hasPassenger = hasPassenger;
    }
    /*设置是否有乘客*/
    public boolean isHasPassenger() {
        //Requires: none
        //Modifies: none
        //Effects: 返回hasPassenger属性
        return hasPassenger;
    }
    /*获取格式化时间*/
    public String getCurrentTimeDate(){
        //Requires: none
        //Modifies: none
        //Effects: 返回出租车的当前时间
        return new Date(System.currentTimeMillis()).toString();
    }
    /*获取毫秒单位时间*/
    public long getCurrentTimeLong(){
        //Requires: none
        //Modifies: none
        //Effects: 返回出租车的当前时间
        return System.currentTimeMillis();
    }
    public void run(){
        while(true){
            switch(state){
                case 0://等待服务
                    move();
                    time++;
                    if(time == 200){//改变状态
                        this.state=3;
                    }
                    break;
                case 1://抢单成功
                    if(carask==null){
                        System.out.println("no aim");
                    }
                    else{
                        move(carask.getStart().x(),carask.getStart().y());
                    }
                    this.getPassenger();
                    break;
                case 2://服务
                    if(carask==null){
                        System.out.println("no aim");
                    }
                    else{
                        move(carask.getEnd().x(),carask.getEnd().y());
                    }
                    this.putPassenger();
                    break;
                case 3://休息
                    try{//改变状态
                        sleep(900);
                    }
                    catch(Exception e){

                    }
                    this.time = 0;
                    this.state = 0;
                    break;
            }
            try{
                sleep(100);
            }
            catch(Exception e){
                System.out.println("sleep wrong");
            }
        }
    }

    public void move(){
        //Requires: none
        //Modifies: position
        //Effects: 出租车移动
        ArrayList <Integer> direction=new ArrayList<Integer>();
        if(position.notrh()){
            direction.add(0);
        }
        if(position.east()){
            direction.add(1);
        }
        if(position.south()){
            direction.add(2);
        }
        if(position.west()){
            direction.add(3);
        }
        Random rand=new Random();
        int go=direction.get(rand.nextInt(direction.size()));

         if(go ==0){
            position=FileRead.getmapplus(position.x(),position.y()-1);
        }
        else if(go ==1){
            position=FileRead.getmapplus(position.x()+1,position.y());
        }
        else if(go == 2){
            position=FileRead.getmapplus(position.x(),position.y()+1);
        }
        else if(go == 3){
            position=FileRead.getmapplus(position.x()-1,position.y());
        }
        direction.clear();
    }

    public void move(int x,int y){
        //Requires: x，y
        //Modifies: position
        //Effects: 有目的地的移动
        int flag=FileRead.rr1(position.x(),position.y(),x,y);

        if(flag == 0){

        }
        else if(flag ==1){
            position=FileRead.getmapplus(position.x(),position.y()-1);
        }
        else if(flag ==2){
            position=FileRead.getmapplus(position.x()+1,position.y());
        }
        else if(flag == 3){
            position=FileRead.getmapplus(position.x(),position.y()+1);
        }
        else if(flag == 4){
            position=FileRead.getmapplus(position.x()-1,position.y());
        }
    }

    public void getPassenger(){
        //Requires: none
        //Modifies: none
        //Effects: 接乘客
        if(this.position.x()==carask.getStart().x()&&this.position.y()==carask.getStart().y()){
            this.state=2;
            try{
                Thread.sleep(900);
            }
            catch(Exception e){

            }
        }
    }
    public void putPassenger(){
        //Requires: none
        //Modifies: state credit
        //Effects: 完成请求，方下乘客
        if(this.position.x()==carask.getEnd().x()&&this.position.y()==carask.getEnd().y()){
            this.state=0;
            this.time=0;
            this.carask=null;
            System.out.println(this.num +"号出租车"+"完成请求");
            try{
                Thread.sleep(900);
            }
            catch(Exception e){

            }
        }
    }

    public boolean getAsk(Ask q){
        //Requires: ask
        //Modifies: ask
        //Effects: 回应请求
        if(this.state==0){
            System.out.println("car:"+this.getNum()+"get requestion start at"+q.getStart().x()+","+q.getStart().y());
            this.carask=q;
            this.state=1;
            this.credit+=3;
            return true;
        }
        else{
            return false;
        }
    }

    public void grab(){
        //Requires: none
        //Modifies: credit
        //Effects: 信用++
        this.credit++;
    }

    public int getCredit()
    {
        //Requires: none
        //Modifies: credit
        //Effects: 信用
        return this.credit;
    }
    public void credit1(){
        this.credit++;
    }
    public void credit3(){
        credit= credit+3;
    }
    public int getx()
    {
        //Requires: none
        //Modifies: none
        //Effects: 获得x
        return this.position.x();
    }
    public int gety()
    {
        //Requires: none
        //Modifies: none
        //Effects: 获得y
        return this.position.y();
    }
    public int getstate()
    {
        //Requires: none
        //Modifies: none
        //Effects: 获得state
        return this.state;
    }
    public int getNum(){
        //Requires: none
        //Modifies: none
        //Effects: 出租车号
        return this.num;
    }
    public TaxiState getTaxiState(){
        //Requires: state
        //Modifies: none
        //Effects: taxistate
        if(this.state == 0){
            return taxiState.waitting;}
        if(this.state == 1){
            return taxiState.going;}
        if(this.state == 2){
            return  taxiState.runnimg;}
        else if(this.state == 3){
            return  taxiState.stop;}
        return  null;

    }

}