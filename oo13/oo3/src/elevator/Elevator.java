package elevator;


enum ElevatorState{
    up,down,stop
}
public class Elevator {
    private int now = 1;
    private int next;
    private ElevatorState elevatorState = ElevatorState.stop;
    private double time = 0;

    public int getNow(){
        return now;
    }

    public double getTime(){
        return time;
    }

    public void run(int nextFloor, int nextTime, Order order){
        next = nextFloor;
        if(order == Order.FR){
            if(time < nextTime)
                time = Math.abs(now - next) * 0.5 + nextTime;
            else
                time = time + Math.abs(now - next) * 0.5;
        }
        else if(order == Order.ER){
            time = time + Math.abs(now - next) * 0.5;
        }

        if(next == now)
            elevatorState = ElevatorState.stop;
        else if(next > now)
            elevatorState = ElevatorState.up;
        else if(next < now)
            elevatorState = ElevatorState.down;

        now = next;
    }

    public void doorTime(){
        time+=1;
    }

    public void resultPrint(){
        System.out.print("(" + next + ",");
        if (elevatorState == ElevatorState.up)
            System.out.println("UP," + time + ")");
        else if(elevatorState == ElevatorState.down)
            System.out.println("DOWN," + time + ")");
        else
            System.out.println("STILL," + time + ")");
    }

}
