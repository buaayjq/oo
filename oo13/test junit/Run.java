package elevator_als;

import java.util.Scanner;

public class Run {
    private AskQueue askQueue = new AskQueue();//每层楼建立一个请求队列

    public void setAskQueue(Ask ask){
        askQueue.getaskQueue().add(ask);
    }

    public AskQueue getAskQueue(){
        return askQueue;
    }

    public static void main(String[] args){
        try {
            int currentTime = 0;
            @SuppressWarnings("resource")
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            AskQueue askQueue = new AskQueue();
            Elevator elevator = new Elevator();
            while(!input.equals("run")){
                input.replace(" ","");
                if(input.matches("^\\(FR,[1-9],UP,[0-9]{1,9}\\)$")
                        || input.matches("^\\(FR,(([2-9])|([1][0])),DOWN,[0-9]{1,9}\\)$")
                        || input.matches("^\\(ER,([1-9]|([1][0])),[0-9]{1,9}\\)$")){
                    Ask ask = new Ask(input);
                    currentTime = askQueue.addtoQueue(ask,currentTime);
                }
                else{
                    System.out.println("Invalid Request");
                }
                input = scanner.nextLine();
            }
            /*for (i = 0; i < askQueue.getaskQueue().size(); i++) {
			    dispatch.set_nextFloor(askQueue.getAskFloor());
			    dispatch.set_nextTime(askQueue.getAskTime());
			    askQueue.vectorPlus();
			    elevator.run(dispatch.get_nextFloor(),
			    		dispatch.get_nextTime(),
			            askQueue.getaskQueue().get(i).getOrder());
			    elevator.resultPrint();
			    elevator.doorTime();
			}*/
            for(int i = 0; i < askQueue.getaskQueue().size(); i++){
                if(askQueue.getaskQueue().get(i) != null){
                    elevator.runrun(askQueue, i);
                    do{
                        //elevator.bringPrint();
                        while (elevator.getNow() != elevator.getMain()) {//一直运动到main
                            elevator.move(askQueue);
                        }
                        elevator.bringPrint();
                    }while(elevator.bringRebuild() == 1 && elevator.ifStillHaveTrueFloor() == 1);//当重建捎带序列成功,继续循环
                    elevator.getBring().removeAllElements();
                }
            }


        }
        catch (Throwable t){
            System.out.println(t);
        }
        finally {
            System.exit(0);
        }
    }
}