package elevator_als;

public class Dispatch {
    private int nextFloor;
    private int nextTime;

    public int get_nextFloor(){
        return nextFloor;
    }

    public int get_nextTime(){
        return nextTime;
    }

    public void set_nextFloor(int Floor){
        nextFloor = Floor;
    }

    public void set_nextTime(int Time){
        nextTime = Time;
    }
}
