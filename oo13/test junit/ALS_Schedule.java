package elevator_als;

public class ALS_Schedule extends Dispatch {
	/**
     * OVERVIEW:
     * ALS类
     * <p>
     * 表示对象:
     * arriveTime
     * <p>
     * 抽象函数:
     * AF(c) = (arriveTime) where arriveTime = c.arriveTime
     * <p>
     * 不变式:
     * always true
     */
	
	
    private int arriveTime;

    public int getarriveTime(){
    	 //Requires: none
        //Modified: none
        //Effects: return arriveTime
        return arriveTime;
    }

    public void setarriveTime(Elevator elevator){
    	//Requires: elevator != null
        //Modified: getTime
        //Effects: set the arriveTime to the elevator's time
        elevator.getTime();
    }

}