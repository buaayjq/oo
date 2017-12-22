package ruaelevator;

import java.util.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;


public class AskQueue{
    private int now = 0;
    private Vector<Asking> askingQueue = new Vector<Asking>();

    public int getNextAskingFloor(){
        return askingQueue.get(now).getaskingFloorNumber();
    }

    public long getNextAskingTime(){
        return askingQueue.get(now).getaskingTime();
    }

    public void moveCurrentVectorOneStep(){
        now++;
    }

    public boolean addAskingQueue(Asking asking){
        if(1 <= asking.getaskingFloorNumber() && asking.getaskingFloorNumber() <= 20) {
            if(asking.getOrder() == Order.ER)
                askingQueue.add(asking);
            else if(asking.getOrder() == Order.FR && asking.getaskingFloorNumber() <= 19 && asking.getelevatorState() == ElevatorState.up)
                askingQueue.add(asking);
            else if(asking.getOrder() == Order.FR && asking.getaskingFloorNumber() >= 2 && asking.getelevatorState() == ElevatorState.down)
                askingQueue.add(asking);
            else {
                System.out.println("Invalid Request");
                return false;
            }
            return true;
        }
        else {
            System.out.println("Invalid Request");
            return false;
        }
    }
    /*@SuppressWarnings("unchecked")
        public void sort(){
            Collections.sort(askQueue, new AskQueueCompare());
        }*/
    public Vector<Asking> getaskingQueue(){
        return askingQueue;
    }

    public void removeSameAsking(){
        //去除时间相同的请求
        long nowTime = askingQueue.get(0).getaskingTime();
        for(int i = 1; i < askingQueue.size(); i++){
            if(nowTime == askingQueue.get(i).getaskingTime())
                askingQueue.remove(i);
            else
                nowTime = askingQueue.get(0).getaskingTime();
        }
    }
}
