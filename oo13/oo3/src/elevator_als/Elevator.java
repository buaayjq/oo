package elevator_als;

import java.util.Vector;


enum ElevatorState{
    up, down, stop
}

public class Elevator implements Move{
    private int now = 1;
    //private int next;
    private double nowTime = 0;//电梯当前时间
    private double time = 0;//电梯到达目标楼层时间
    private ElevatorState elevatorState = ElevatorState.stop;
    private int[] ifStay = new int[10];
    private int main = 0;
    private Vector<Ask> bring = new Vector<>();

    public Elevator(){
        for(int i = 0; i < 10; i++){
            ifStay[i] = 0;
        }
    }

    public double getTime(){
        return time;
    }
    public int getNow(){
        return now;
    }
    public double getnowTime(){
        return  nowTime;
    }
    public int getMain(){
        return main;
    }
    public Vector<Ask> getBring(){
        return bring;
    }

    public void traversal(AskQueue askQueue){
        if(elevatorState == ElevatorState.up) {
            for (int i = now-1; i < 10; i++) {
                for (int j = 0; j < askQueue.getaskQueue().size(); j++) {
                    Ask ask = askQueue.getaskQueue().get(j);
                    if (ask != null && ask.getaskTime() <= time
                            && ask.getFloor() == now) {
                        if(ask.getOrder() == Order.ER) {//命令如果是在电梯内部发出的
                            ifStay[i] = 1;
                            askQueue.getaskQueue().set(j, null);
                            addCarryRequests(ask);
                        }
                        else{
                            if(ask.getelevatorState() == elevatorState && ask.getFloor() <= main){
                                ifStay[i] = 1;
                                askQueue.getaskQueue().set(j, null);
                                addCarryRequests(ask);
                            }
                        }
                    }
                }
            }
        }
        else if(elevatorState == ElevatorState.down){
            for (int i = now-1; i > 0; i--) {
                for (int j = 0; j < askQueue.getaskQueue().size(); j++) {
                    Ask ask = askQueue.getaskQueue().get(j);
                    if (ask != null && ask.getaskTime() <= time && ask.getFloor() == now && ask.getFloor() <= main) {
                        if(ask.getOrder() == Order.ER) {
                            ifStay[i] = 1;
                            askQueue.getaskQueue().set(j, null);
                            addCarryRequests(ask);
                        }
                        else{
                            if(ask.getelevatorState() == elevatorState && ask.getFloor() == now){
                                ifStay[i] = 1;
                                askQueue.getaskQueue().set(j, null);
                                addCarryRequests(ask);
                            }
                        }
                    }
                }
            }
        }
    }

    public void traverseCarryFloors(Vector<Ask> bring){
        for(int i = 0; i < bring.size(); i++){
            if(bring.get(i).getFloor() == now){
                bring.set(i, null);
            }
        }
    }

    public int addCarryRequests(Ask ask){
        for(int i = 0; i < bring.size(); i++){
            if(ask.equals(bring.get(i)))
                return 0;
        }
        bring.add(ask);
        return 1;
    }

    public void run(int nextFloor){
        if(nextFloor > now)
            elevatorState = ElevatorState.up;
        else if(nextFloor < now)
            elevatorState = ElevatorState.down;
        else if(nextFloor == now)
            elevatorState = ElevatorState.stop;
    }

    public int ifStillHaveTrueFloor(){
        for(int i = 0; i < 10; i++){
            if(ifStay[i] == 1)
                return 1;
        }
        return 0;
    }

    public void runrun(AskQueue askQueue, int i){
        Ask asking = askQueue.getaskQueue().get(i);
        main = asking.getFloor();
        bring.add(asking);//将主请求加入队首
        if(asking.getaskTime() > time)//当请求时电梯已经停止运行
            time = asking.getaskTime();
        ifStay[asking.getFloor()-1] = 1;
        run(asking.getFloor());//设置电梯运动方向

        if(elevatorState == ElevatorState.stop){
            //如果电梯处于稳定状态
            ifStay[asking.getFloor()-1] = 0;
            for(int j = 0; j < askQueue.getaskQueue().size(); j++) {
                asking = askQueue.getaskQueue().get(j);
                if(asking != null && asking.getaskTime() <= time && asking.getFloor() == now) {
                    askQueue.getaskQueue().set(j, null);
                }
            }
            resultPrint();
        }

    }
    public void move(AskQueue askQueue){
        if(elevatorState == ElevatorState.up) {
            now++;
            time += 0.5;
            traversal(askQueue);
            if (ifStay[now-1] == 1) {
                resultPrint();
                ifStay[now-1] = 0;
            }
        }
        else if(elevatorState == ElevatorState.down){
            now--;
            time += 0.5;
            traversal(askQueue);
            if (ifStay[now - 1] == 1) {
                resultPrint();
                ifStay[now - 1] = 0;
            }
        }
    }
    public void resultPrint() {
        System.out.print("(" + now + ",");
        if (elevatorState == ElevatorState.up)
            System.out.println("up," + time + ")");
        else if(elevatorState == ElevatorState.down)
            System.out.println("down," + time + ")");
        else
            System.out.println("still," + time + ")");
        time++;
    }
    //输出捎带请求队列
    public void bringPrint(){
        System.out.print("/");
        for(int i = 0; i < bring.size(); i++){
            Ask asking = bring.get(i);
            if(asking != null) {
                if (asking.getOrder() == Order.FR) {
                    System.out.print("[" + asking.getOrder() + "," + asking.getFloor() + "," + asking.getelevatorState() + "," + asking.getaskTime() + "]\n");
                } else {
                    System.out.print("[" + asking.getOrder() + ","  + asking.getFloor() + "," + asking.getaskTime() + "]\n");
                }
            }
        }
        System.out.println();
    }
    public int bringRebuild(){
        for(int i = 0; i < bring.size(); i++){
            Ask asking = bring.get(i);
            if(asking != null && asking.getFloor() <= main){
                bring.set(i, null);
            }
        }
        bring.removeElement(null);
        if(!bring.isEmpty()){
            return 1;
        }
        return  0;
    }
}

