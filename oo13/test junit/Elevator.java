package elevator_als;

import java.util.Vector;


enum ElevatorState{
    up, down, stop
}

public class Elevator implements Move{
	
	 /**
     * OVERVIEW:
     * 电梯类
     * <p>
     * 表示对象:
     * currentFloor, currentTime, time, elevatorState, ifStay[], primaryFloor, carryRequests
     * <p>
     * 抽象函数:
     * AF(c) = (currentFloor, currentTime, time, elevatorState, ifStay[], primaryFloor, carryRequests)
     *         where currentFloor = c.currentFloor, currentTime = c.currentTime, time = c.time,
     *         elevatorState = c.elevatorState, ifStay = c.ifStay, primaryFloor = c.primaryFloor,
     *         carryRequests = c.carryRequests
     * <P>
     * 不变式;
     * 1 <= currentFloor <= 10 && time >= 0 && 1 <= primaryFloor <= 10
     */
	
     int now = 1;
    //private int next;
    private double nowTime = 0;//电梯当前时间
     double time = 0;//电梯到达目标楼层时间
    private ElevatorState elevatorState = ElevatorState.stop;
     int[] ifStay = new int[10];
     int main = 0;
     Vector<Ask> bring = new Vector<>();

    public Elevator(){
    	//Requires: none
        //Modified: none
        //Effects: constructor
        for(int i = 0; i < 10; i++){
            ifStay[i] = 0;
        }
    }

    public double getTime(){
    	//Requires: none
        //Modified: none
        //Effects: return time
        return time;
    }
    public int getNow(){
    	//Requires: none
        //Modified: none
        //Effects: return now
        return now;
    }
    public double getnowTime(){
    	//Requires: none
        //Modified: none
        //Effects: return nowTime
        return  nowTime;
    }
    public int getMain(){
    	//Requires: none
        //Modified: none
        //Effects: return main
        return main;
    }
    public Vector<Ask> getBring(){
    	//Requires: none
        //Modified: none
        //Effects: return bring
        return bring;
    }

    public void traversal(AskQueue askQueue){
    	//Requires: AskQueue != null
        //Modified: ifStay[], askQueue
        //Effects: 电梯每到一层就遍历整个队列，如果有在电梯运动方向上的且在时间范围内的就记录下来
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
    	//Requires: AskQueue != null
        //Modified: ifStay[], askQueue
    	//Effects： 遍历捎带请求队列
        for(int i = 0; i < bring.size(); i++){
            if(bring.get(i).getFloor() == now){
                bring.set(i, null);
            }
        }
    }

    public int addCarryRequests(Ask ask){
    	//Requires: asking != null
        //Modified: carryRequests
        //Effects: 将捎带请求加入捎带请求队列
        for(int i = 0; i < bring.size(); i++){
            if(ask.equals(bring.get(i)))
                return 0;
        }
        bring.add(ask);
        return 1;
    }

    public void run(int nextFloor){
    	//Requires: none
        //Modified: none
        //Effects: 设定电梯状态
        if(nextFloor > now)
            elevatorState = ElevatorState.up;
        else if(nextFloor < now)
            elevatorState = ElevatorState.down;
        else if(nextFloor == now)
            elevatorState = ElevatorState.stop;
    }

    public int ifStillHaveTrueFloor(){
    	//Requires: none
        //Modified: none
        //Effects: if one of the ifStay is 0, return 1,
        for(int i = 0; i < 10; i++){
            if(ifStay[i] == 1)
                return 1;
        }
        return 0;
    }

    public void runrun(AskQueue askQueue, int i){
    	//Requires:askQueue != null,
        // i is the first element that make the askQueue.getAskingQueue().get(i) != null
        //Modified: primaryFloor, carryRequests, time, ifStay[], askQueue
       //Effects: 取整个队列中最近的请求
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
    	//Requires: askQueue != null
        //Modified: currentFloor, time, ifStay[]
        //Effects: 电梯运行并改变状态
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
    	//Requires: none
        //Modified: System.out
        //Effects: 输出
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
    	//Requires: none
        //Modified: System.out
        //Effects: 输出
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
    	//Requires: none
        //Modified: carryRequests, primaryFloor
        //Effects: 重构队列
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

