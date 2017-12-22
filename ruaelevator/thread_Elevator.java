package ruaelevator;

import java.util.ArrayList;
import java.util.Vector;

public class thread_Elevator extends Elevator implements Runnable{
    private ArrayList<Asking> elevatorAskingQueue = new ArrayList<>();
    private AskQueue askQueue = new AskQueue();
    //AskQueue finishAskQueue = new AskQueue();
    private int elevatorNumber;

    public thread_Elevator(int elevatorNumber){
        this.elevatorNumber = elevatorNumber;
    }
    public void addAskingQueue(Asking asking){
        askQueue.addAskingQueue(asking);
    }
    public int getElevatorNumber(){
        return elevatorNumber;
    }

    public Vector<Asking> removeNull(Vector<Asking> askings){
        for (int i = 0; i < askings.size(); i++) {
            Asking asking = askings.get(i);
            if (asking == null) {
                askQueue.getaskingQueue().remove(asking);
                i--;
            }
        }
        return askings;
    }

    @Override
    public String toString(){
        return "(#" + elevatorNumber + ",#" + getNow() + ","
                + getElevatorState() + "," + getAmountOfExercise() + ","
                + getTime() + ")";
    }

    @Override
    public void run() {
        try {
            boolean deleteFlag = false;
            //System.out.println("elevator " + getElevatorNumber() + " is running");
            long last = System.currentTimeMillis();
            long now = System.currentTimeMillis();
            while (true) {
                synchronized (getcarryRequests()) {
                    if (!askQueue.getaskingQueue().isEmpty()) {
                        for (int i = 0; i < askQueue.getaskingQueue().size(); i++) {
                            Asking asking = askQueue.getaskingQueue().get(i);
                            if (asking == null) {
                                askQueue.getaskingQueue().remove(asking);
                                i--;
                            }
                        }
                        if (starToMove(askQueue, 0))
                            do {
                                while (getNow() != getMain()) {
                                    runrun(askQueue);
                                }
                            } while (rebuildCarryRequesets() && ifStillHaveTrueFloor());
                        askQueue.getaskingQueue().removeAllElements();
                        finishAskings.removeAllElements();
                        getcarryRequests().removeAllElements();
                    } else {
                        initElevatorState();
                    }
                }
            }
        }
        catch (Throwable t){
            System.out.println("电梯 " + elevatorNumber + " 发生故障");
            t.printStackTrace();
        }
        finally {
            System.out.println("电梯 " + elevatorNumber + " 停止运行");
        }
    }


}