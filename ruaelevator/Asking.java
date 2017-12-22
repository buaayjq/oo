package ruaelevator;

import java.util.Calendar;

enum Order{
    FR, ER
}

public class Asking {
    private Order order = Order.FR;
    private ElevatorState elevatorState = ElevatorState.up;
    private int askingFloorNumber;
    private int askingElevator;
    private long askingTime;


    public Asking(){

    }

    //long currentTime = System.currentTimeMillis();
   // SimpleDateFormat formatter = new SimpleDateFormat("yyyy年-MM月dd日-HH时mm分ss秒");
    //Date date = new Date(currentTime);
   // System.out.println(formatter.format(date));
    Calendar calendar = Calendar.getInstance();
    //calendar.getTime()

    public Order getOrder(){
        return order;
    }

    public int getaskingFloorNumber(){
        return  askingFloorNumber;
    }

    public ElevatorState getelevatorState(){
        return elevatorState;
    }

    public long getaskingTime(){
        return askingTime;
    }

    public int getaskingElevator(){
        return askingElevator;
    }

    public Asking(String input){
        String[] strings = input.split("[(,)]");//把数据分割
        if(strings[1].equals("FR")) {
            order = Order.FR;
            askingFloorNumber = Integer.parseInt(strings[2]);//取出数值
            if (strings[3].equals("UP"))
                elevatorState = ElevatorState.up;
            else
                elevatorState = ElevatorState.down;
            askingTime = (System.currentTimeMillis() - Run.startTime) / 100;
        }
        else {
            order = Order.ER;
            askingElevator = Integer.parseInt(strings[2].replace("#", ""));
            askingFloorNumber = Integer.parseInt(strings[3]);
            askingTime = (System.currentTimeMillis() - Run.startTime) / 100;
        }
    }

    @Override
    public String toString(){
        if(order == Order.FR){
            return "[FR," + askingFloorNumber + "," + elevatorState + "," + calendar.getTime() + "]";
        }
        else{
            return "[ER,#" + askingElevator + "," + askingFloorNumber + "," + calendar.getTime() + "]";
        }
    }

}