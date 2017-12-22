package elevator_als;

import java.util.Vector;

enum Order{//FR和ER相似，放入一个枚举类型里
    FR,ER
}
public class Ask {
    private Order order = Order.FR;
    private ElevatorState elevatorState = ElevatorState.up;
    private int Floor;
    private int askTime;
    private Vector<Ask> askQueue = new Vector<Ask>();

    public Order getOrder(){
        return order;
    }

    public ElevatorState getelevatorState(){
        return elevatorState;
    }

    public int getFloor(){
        return Floor;
    }

    public int getaskTime(){
        return askTime;
    }

    public Ask(String input){
        String[] strings = input.split("[(,)]");//把数据分割
        if(strings[1].equals("FR")){
            order = Order.FR;
            Floor = Integer.parseInt(strings[2]);//取出数值
            if (strings[3].equals("up"))
                elevatorState = ElevatorState.up;
            else if (strings[3].equals("down"))
                elevatorState = ElevatorState.down;

            askTime = Integer.parseInt(strings[4]);
        }
        else if(strings[1].equals("ER")){
            order = Order.ER;
            Floor = Integer.parseInt(strings[2]);
            askTime = Integer.parseInt(strings[3]);
        }
        //else{}
    }
    
    public void removeSameAsking(){//去除时间相同的请求
        int nowTime = askQueue.get(0).getaskTime();
        for(int i = 1; i < askQueue.size(); i++){
            if(nowTime == askQueue.get(i).getaskTime())
                askQueue.remove(i);
            else
                nowTime = askQueue.get(0).getaskTime();
        }
    }
}
