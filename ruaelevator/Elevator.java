package ruaelevator;

import java.io.*;
import java.util.Calendar;
import java.util.Vector;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.locks.Lock;

enum ElevatorState{
    up,down,still
}

public class Elevator implements Move{
    private int now = 1;
    private double nowTime = 0;//电梯当前时间
    private double time = 0;//电梯到达目标楼层时间
    private ElevatorState elevatorState = ElevatorState.still;
    private boolean[] ifStay = new boolean[20];
    private int main = 0;
    private volatile Vector<Asking> carryRequests = new Vector<>();
    protected Vector<Asking> finishAskings = new Vector<>();
    private int amountOfExercise = 0;
    private long sleepTime = 3000;
    File file = new File("D:\\result.txt");
    Calendar calendar = Calendar.getInstance();

    //构造方法
    public Elevator(){
        for(int i = 0; i < 20; i++){
            ifStay[i] = false;
        }
    }

    public double getTime(){
        return time;
    }
    public int getNow(){
        return now;
    }
    public double getNowTime(){
        return  nowTime;
    }

    public int getMain(){
        return main;
    }
    public void setMain(int primaryFloor){
        main = primaryFloor;
    }

    public Vector<Asking> getcarryRequests(){
        return carryRequests;
    }

    public int getAmountOfExercise(){
        return amountOfExercise;
    }

    public ElevatorState getElevatorState(){
        return elevatorState;
    }
    public void initElevatorState(){//初始化静止状态
        elevatorState = ElevatorState.still;
    }

    //电梯每到一层就遍历整个队列，如果有在电梯运动方向上的且在时间范围内的就记录下来
    public void traversal(AskQueue askQueue){
        if(elevatorState == ElevatorState.up) {
            for (int j = 0; j < askQueue.getaskingQueue().size(); j++) {
                Asking asking = askQueue.getaskingQueue().get(j);
                if (asking != null ){
                    if(asking.getOrder() == Order.ER) {
                        ifStay[asking.getaskingFloorNumber()-1] = true;
                        finishAskings.add(askQueue.getaskingQueue().get(j));
                        askQueue.getaskingQueue().set(j, null);
                        addCarryRequests(asking);//将捎带请求加入队列
                    }
                    else{
                        //电梯外部
                        if(asking.getelevatorState() == elevatorState && asking.getaskingFloorNumber() <= main){
                            ifStay[asking.getaskingFloorNumber()-1] = true;
                            finishAskings.add(askQueue.getaskingQueue().get(j));
                            askQueue.getaskingQueue().set(j, null);
                            addCarryRequests(asking);//将捎带请求加入队列
                        }
                    }
                }
            }
        }
        //}
        else if(elevatorState == ElevatorState.down){
            for (int j = 0; j < askQueue.getaskingQueue().size(); j++) {
                Asking asking = askQueue.getaskingQueue().get(j);
                if (asking != null){
                    if(asking.getOrder() == Order.ER) {
                        ifStay[asking.getaskingFloorNumber()-1] = true;
                        finishAskings.add(askQueue.getaskingQueue().get(j));
                        askQueue.getaskingQueue().set(j, null);
                        addCarryRequests(asking);//将捎带请求加入队列
                    }
                    else{
                        //电梯外部的
                        if(asking.getelevatorState() == elevatorState
                                && asking.getaskingFloorNumber() >= main){
                            ifStay[asking.getaskingFloorNumber()-1] = true;
                            finishAskings.add(askQueue.getaskingQueue().get(j));
                            askQueue.getaskingQueue().set(j, null);
                            addCarryRequests(asking);//将捎带请求加入队列
                        }
                    }
                }
            }
        }
    }
    //遍历捎带请求队列
    public void traverseCarryFloors(Vector<Asking> carryRequests){
        for(int i = 0; i < carryRequests.size(); i++){
            if(carryRequests.get(i).getaskingFloorNumber() == now){
                carryRequests.set(i, null);
            }
        }
    }
    //将捎带请求加入捎带请求队列
    public boolean addCarryRequests(Asking asking){
        for(int i = 0; i < carryRequests.size(); i++){
            if(asking.equals(carryRequests.get(i)))
                return false;
        }
        carryRequests.add(asking);
        return true;
    }
    public boolean setM_ifStay(int i){
        return ifStay[i];
    }
    //判断是否还有楼层没有响应
    public boolean ifStillHaveTrueFloor(){
        for(int i = 0; i < 20; i++){
            if(ifStay[i])
                return true;
        }
        return false;
    }
    //电梯刚刚从静止状态开始运动,取整个队列中最近的请求
    public boolean starToMove(AskQueue askQueue, int i){
        Asking asking = askQueue.getaskingQueue().get(i);
        main = asking.getaskingFloorNumber();
        carryRequests.add(asking);//将主请求加入队首
        if(asking.getaskingTime() > time)//当请求时电梯已经停止运行
            time = asking.getaskingTime();
        ifStay[asking.getaskingFloorNumber()-1] = true;
        setElevatorState(asking.getaskingFloorNumber());//设置电梯运动方向

        if(elevatorState == ElevatorState.still){
            //如果电梯处于稳定状态
            ifStay[asking.getaskingFloorNumber()-1] = false;
            for(int j = 0; j < askQueue.getaskingQueue().size(); j++) {
                asking = askQueue.getaskingQueue().get(j);
                if(asking != null && asking.getaskingTime() <= time && asking.getaskingFloorNumber() == now) {
                    askQueue.getaskingQueue().set(j, null);
                }
            }
            synchronized (System.out) {
                try {

                   // FileWriter fwc = new FileWriter("d:\\result.txt",true);
                   // PrintWriter pwc=new PrintWriter(fwc);
                   // pwc.println(calendar.getTime());
                    resultPrint();
                    //System.out.print(calendar.getTime()+":");
                    resultPrints();
                time += 60;
                    Thread.sleep(sleepTime*2);
                    finishAskings.add(asking);
                printFinishAsking(asking.getaskingFloorNumber());
                printFinishAskings(asking.getaskingFloorNumber());
                }
                catch (Exception e){
                    e.printStackTrace();
                }

            }
            traversal(askQueue);
            return false;
        }
        return true;
    }
    //输出完成的函数
    public void printFinishAsking(int floorNumber)throws IOException{

        //fileWriter.write(calendar.getTime());
        for(int i = 0; i < finishAskings.size(); i++){
            Asking asking = finishAskings.get(i);
            if(asking != null){
                if(asking.getaskingFloorNumber() == floorNumber){
                    String ii = asking.toString();
                    //FileOutputStream fos = new FileOutputStream("D:\\result.txt",true);
                    //fos.write(ii.getBytes());
                    //fos.close();
                   // System.out.print(ii);
                   // FileWriter fileWriter = new FileWriter(file);
                    //BufferedWriter out = new BufferedWriter(fileWriter);
                    //out.write(ii,0,ii.length()-1);
                   // out.close();
                   // System.out.print(asking.toString());
                    FileWriter fw = new FileWriter("d:\\result.txt",true);
                    PrintWriter pw=new PrintWriter(fw);
                    //pw.println(Calendar.getInstance());
                    pw.println(asking.toString());   //字符串末尾不需要换行符
                    pw.println("...");
                    pw.close () ;
                    fw.close () ;
                    System.out.print(asking.toString());
                   // PrintStream ps = new PrintStream(new FileOutputStream(file));
                    //ps.println(asking.toString());// 往文件里写入字符串
                    finishAskings.set(i, null);
                }
            }
        }
        System.out.println();
    }
    public void printFinishAskings(int floorNumber){

        for(int i = 0; i < finishAskings.size(); i++){
            Asking asking = finishAskings.get(i);
            if(asking != null){
                if(asking.getaskingFloorNumber() == floorNumber){
                    System.out.print(asking.toString());
                    finishAskings.set(i, null);
                }
            }
        }
        System.out.println();
    }

    //电梯一层一层地运动
    public boolean runrun(AskQueue askQueue){
        amountOfExercise++;
        if(elevatorState == ElevatorState.up) {
            now++;
            time += 30;
            try {
                Thread.sleep(sleepTime);
            }
            catch (InterruptedException i){
                i.printStackTrace();
            }
            traversal(askQueue);
            if (ifStay[now-1] == true) {
                synchronized (System.out) {
                    //System.out.print(calendar.getTime()+":");
                    resultPrints();
                    System.out.print("/");
                    printFinishAskings(now);
                    try{
                        FileWriter fwa = new FileWriter("d:\\result.txt",true);
                        PrintWriter pwa=new PrintWriter(fwa);
                        pwa.println(calendar.getTime());
                    resultPrint();
                    printFinishAsking(now);}
                    catch (Exception ee)
                    {

                    }
                }
                ifStay[now-1] = false;//过了该层之后变成false
                return true;
            }
        }
        else if(elevatorState == ElevatorState.down){
            now--;
            time += 30;
            try {
                Thread.sleep(sleepTime);
            }
            catch (InterruptedException i){
                i.printStackTrace();
            }
            traversal(askQueue);
            if (ifStay[now - 1] == true) {
                synchronized (System.out) {
                   // System.out.print(calendar.getTime()+":");
                    resultPrints();
                    System.out.print("/");
                    printFinishAskings(now);
                    try{
                        FileWriter fwb = new FileWriter("d:\\result.txt",true);
                        PrintWriter pwb=new PrintWriter(fwb);
                        pwb.println(calendar.getTime());
                        resultPrint();
                        printFinishAsking(now);}
                    catch (Exception ee)
                    {

                    }
                }
                ifStay[now - 1] = false;//过了该层之后变成false
                return true;
            }
        }
        else{
        }
        return false;
    }
    public void resultPrint()throws IOException {
        //FileWriter fileWriter = new FileWriter(file);
        String a = toString();
        FileWriter fws = new FileWriter("d:\\result.txt",true);
        PrintWriter pws=new PrintWriter(fws);
        //pw.println(Calendar.getInstance());
        pws.println(calendar.getTime());
        pws.println(toString());   //字符串末尾不需要换行符
        pws.close () ;
        fws.close () ;
       // System.out.print(toString());
        //PrintStream ps = new PrintStream(new FileOutputStream(file));
        //ps.println(a);// 往文件里写入字符串
        //BufferedWriter out = new BufferedWriter(fileWriter);
       // out.write(a,0,a.length()-1);
       // out.close();
        //FileWriter fileWriters = new FileWriter(file);
        //fileWriters.write(toString());
       // System.out.print(toString());
        time += 60;//开关门附加时间
        try {
            Thread.sleep(sleepTime*2);
        }
        catch (InterruptedException i){
            i.printStackTrace();
        }
    }

    public void resultPrints() {
        System.out.print(calendar.getTime()+":");
        System.out.print(toString());
        time += 60;//开关门附加时间
        try {
            Thread.sleep(sleepTime*2);
        }
        catch (InterruptedException i){
            i.printStackTrace();
        }
    }


    @Override
    public String toString(){
        return "(" + now + "," + elevatorState + "," + time + ")";
    }
    //输出捎带请求队列

    //重新设定捎带队列,将最近的一个未完成捎带请求变成主请求
    public boolean rebuildCarryRequesets(){
        for(int i = 0; i < carryRequests.size(); i++){
            Asking asking = carryRequests.get(i);
            if(asking != null && asking.getaskingFloorNumber() <= main){
                carryRequests.set(i, null);
            }
        }
        for(int i = 0; i < carryRequests.size(); i++) {
            if(carryRequests.get(i) != null) {
                main = carryRequests.get(i).getaskingFloorNumber();
                setElevatorState(main);
                return true;
            }
        }
        return  false;
    }
    public boolean rebuildCarryRequesets(AskQueue askQueue){
        carryRequests.add(askQueue.getaskingQueue().lastElement());
        for(int i = 0; i < carryRequests.size(); i++){
            Asking asking = carryRequests.get(i);
            if(asking != null && asking.getaskingFloorNumber() <= main){
                carryRequests.set(i, null);
            }
        }
        for(int i = 0; i < carryRequests.size(); i++) {
            if(carryRequests.get(i) != null) {
                main = carryRequests.get(i).getaskingFloorNumber();
                return true;
            }
        }
        return  false;
    }

    //设置电梯运动状态
    public void setElevatorState(int nextFloor){
        if(nextFloor > now)
            elevatorState = ElevatorState.up;
        else if(nextFloor < now)
            elevatorState = ElevatorState.down;
        else if(nextFloor == now)
            elevatorState = ElevatorState.still;
    }

}