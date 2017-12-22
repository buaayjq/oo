package elevator;

import java.util.Scanner;

public class Run {
    public static void main(String[] args){
        int time;
        int i;
        time = 0;
        i = 0;
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        AskQueue askQueue = new AskQueue();
        Elevator elevator = new Elevator();
        Dispatch dispatch = new Dispatch();

        while(!input.equals("run")){
            input.replace(" ","");
            if(input.matches("^\\(FR,[1-9],UP,[0-9]+\\)$")
                    || input.matches("^\\(FR,(([2-9])|([1][0])),DOWN,[0-9]+\\)$")
                    || input.matches("^\\(ER,[1-9],[0-9]+\\)$")){
                Ask ask = new Ask(input);
                time = askQueue.addtoQueue(ask,time);
            }
            else{
                System.out.println("此条数据输入格式错误");
            }
            input = scanner.nextLine();
        }
        try {
            for (i = 0; i < askQueue.getaskQueue().size(); i++) {
                dispatch.set_nextFloor(askQueue.getAskFloor());
                dispatch.set_nextTime(askQueue.getAskTime());
                askQueue.vectorPlus();
                elevator.run(dispatch.get_nextFloor(),
                        dispatch.get_nextTime(),
                        askQueue.getaskQueue().get(i).getOrder());
                elevator.resultPrint();
                elevator.doorTime();
            }
        } catch (Throwable t) {
            // TODO 自动生成的 catch 块
            System.out.println(t);
        }
        finally {
            System.exit(0);
        }

    }
}
