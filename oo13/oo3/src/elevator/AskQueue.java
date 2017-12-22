package elevator;

import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

class AskQueueCompare implements Comparator<Object>{ //Comparator接口比较

    public int compare(/*Integer o1, Integer o2*/Object o1, Object o2) {
        Ask a1 = (Ask)o1;
        Ask a2 = (Ask)o2;

        if(a1.getTime() > a2.getTime()){
            return 1;
        }
        else if(a1.getTime() < a2.getTime()){
            return -1;
        }
        else{
            return 0;
        }
    }
}

public class AskQueue {
    private int now = 0;
    private Vector<Ask> askQueue = new Vector<Ask>();

    public int getAskFloor(){
        return askQueue.get(now).getFloor();
    }

    public int getAskTime(){
        return askQueue.get(now).getTime();
    }

    public void vectorPlus(){
        now+=1;
    }

    public int addtoQueue(Ask ask, int nowTime){//加入到队列
        if(nowTime <= ask.getTime()) {
            askQueue.add(ask);
            return ask.getTime();
        }
        else {
            System.out.println("输入数据未按照时间排序");
            return nowTime;
        }
    }

    public Vector<Ask> getaskQueue(){
        return askQueue;
    }

    @SuppressWarnings("unchecked")
    public void sort(){
        Collections.sort(askQueue, new AskQueueCompare());
    }

    public void removeSameAsking(){//去除时间相同的请求
        int nowTime = askQueue.get(0).getTime();
        for(int i = 1; i < askQueue.size(); i++){
            if(nowTime == askQueue.get(i).getTime())
                askQueue.remove(i);
            else
                nowTime = askQueue.get(0).getTime();
        }
    }

}
