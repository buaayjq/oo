package elevator_als;

public class ALS_Schedule extends Dispatch {
    private int arriveTime;

    public int getarriveTime(){
        return arriveTime;
    }

    public void setarriveTime(Elevator elevator){
        elevator.getTime();
    }

	/*public int bringJudge(Elevator elevator,Ask nextAsk){
		if(nextAsk.getFloor() >= elevator.getNow()){
			double ifNeedtobring = (nextAsk.getFloor()-elevator.getNow())/2;
			if((elevator.getTime() + ifNeedtobring) >= nextAsk.getaskTime()){
				return 1;
			}
		}
		return 0;
	}*/
}