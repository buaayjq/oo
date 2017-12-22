package elevator_als;

import java.util.Vector;

import java.util.Collections;
import java.util.Comparator;


public class AskQueue {
	
	
	/**
     * OVERVIEW:
     * 请求队列类
     *
     * 表示对象:
     * askingQueue
     *
     * 抽象函数:
     * AF(c) = (askingQueue) where askingQueue = c.askingQueue
     *
     * 不变式:
     * always true
     */
	
    private int now = 0;
    private Vector<Ask> askQueue = new Vector<Ask>();//容器

 /*   public int getNextAskFloor(){
    	//Requires: none
        //Modified: current
        //Effects: return the next ask in askQueue
        return askQueue.get(now).getFloor();
    }

    public int getNextAskTime(){
    	//Requires: none
        //Modified: none
        //Effects: return the current
        return askQueue.get(now).getaskTime();
    }

    public void vectorPlus(){
    	//Requires: none
        //Modified: now++
        //Effects: vector++
        now+=1;
    }
    */

    public int addtoQueue(Ask ask, int nowTime){//加入到队列
    	//Requires: asking != null, current >= 0
        //Modified: System.out, askingQueue
        //Effects:  add the asking to askingQueue and return the time

        if(nowTime <= ask.getaskTime()) {
            askQueue.add(ask);
            return ask.getaskTime();
        }
        else {
            System.out.println("输入数据未按照时间排序");
            return nowTime;
        }
    }

    public Vector<Ask> getaskQueue(){
    	//Requires: none
        //Modified: none
        //Effects: return the askQueue
        return askQueue;
    }
    
    




}
