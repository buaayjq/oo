package ruaelevator;

import java.util.Vector;

public class ALS_Schedule extends Dispatch implements Runnable{
    private volatile Vector<Asking> askings = new Vector<>();
    private Asking asking = new Asking();
    private thread_Elevator[] thread_elevators = new thread_Elevator[3];

    public thread_Elevator[] getThread_elevators(){
        return thread_elevators;
    }

    public void setAsking(Asking asking){
        this.asking = asking;
    }
    //加入请求托盘
    public void setAskings(Asking asking){
        askings.add(asking);
    }
    public Asking getAsking(int i){
        return askings.get(i);
    }


    public boolean distribute(Asking asking, thread_Elevator[] thread_elevators){
        if(asking.getOrder() == Order.ER ){
            thread_elevators[asking.getaskingElevator()-1].addAskingQueue(asking);//进入所选择的电梯
            return true;
        }
        else {
            int whichElevatorToAdd = 0;
            int sumFlag = thread_elevators[0].getAmountOfExercise();
            boolean carryFlag = false;
            for (int i = 0; i < thread_elevators.length; i++) {
                if (thread_elevators[i].getElevatorState() == asking.getelevatorState()) {
                    //如果请求方向与电梯方向相同
                    if((asking.getelevatorState() == ElevatorState.up && thread_elevators[i].getMain() >= asking.getaskingFloorNumber())
                            || (asking.getelevatorState() == ElevatorState.down && thread_elevators[i].getMain() <= asking.getaskingFloorNumber())){
                        if(!carryFlag) {
                            whichElevatorToAdd = i;
                            sumFlag = thread_elevators[i].getAmountOfExercise();
                            carryFlag = true;
                        }
                        if(sumFlag > thread_elevators[i].getAmountOfExercise()){
                            whichElevatorToAdd = i;
                            sumFlag = thread_elevators[whichElevatorToAdd].getAmountOfExercise();
                        }
                    }
                }
            }
            if(carryFlag){
                thread_elevators[whichElevatorToAdd].addAskingQueue(asking);
                return true;
            }
        }

        //从多个静止电梯中选择运动量最小的
        int whichElevatorToAdd = -1;
        int sumFlag = Integer.MAX_VALUE;
        boolean addFlag = false;
        for(int i = 0; i < thread_elevators.length; i++){
            if(thread_elevators[i].getAmountOfExercise() < sumFlag && thread_elevators[i].getElevatorState() == ElevatorState.still) {
                whichElevatorToAdd = i;
                sumFlag = thread_elevators[i].getAmountOfExercise();
                addFlag = true;
            }
        }
        if(addFlag){
            thread_elevators[whichElevatorToAdd].addAskingQueue(asking);
            thread_elevators[whichElevatorToAdd].setElevatorState(asking.getaskingFloorNumber());
            return true;
        }
        return false;
    }

    @Override
    public void run(){
        try {
            while (true) {
                for (int i = 0; i < askings.size(); i++) {
                    if (getAsking(i) != null && distribute(getAsking(i), thread_elevators)) {
                        askings.set(i, null);
                    }
                }
            }
        }
        catch (Throwable t){
            System.out.println("调度器发生故障");
            t.printStackTrace();
        }
        finally {
            System.out.println("调度器停止运行");
        }
    }
}