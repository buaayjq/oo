package elevator;

enum Order{
    FR,ER
}
public class Ask {
    private Order order = Order.FR;
    private ElevatorState elevatorState = ElevatorState.up;
    private int Floor;
    private int Time;

    public Order getOrder(){
        return order;
    }

    public ElevatorState getelevatorState(){
        return elevatorState;
    }

    public int getFloor(){
        return Floor;
    }

    public int getTime(){
        return Time;
    }

    public Ask(String input){
        String[] strings = input.split("[(,)]");
        if(strings[1].equals("FR")){
            order = Order.FR;
            Floor = Integer.parseInt(strings[2]);
            if (strings[3].equals("up"))
                elevatorState = ElevatorState.up;
            else if (strings[3].equals("down"))
                elevatorState = ElevatorState.down;

            Time = Integer.parseInt(strings[4]);
        }
        else if(strings[1].equals("ER")){
            order = Order.ER;
            Floor = Integer.parseInt(strings[2]);
            Time = Integer.parseInt(strings[3]);
        }
    }
}