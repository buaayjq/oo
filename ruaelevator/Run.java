package ruaelevator;

import java.util.Scanner;

public class Run {
    static long startTime;

    public static void main(String[] args){
        try {
            Run.startTime = System.currentTimeMillis();
            AskQueue askQueue = new AskQueue();
            ALS_Schedule als_schedule = new ALS_Schedule();
            Thread alsThread = new Thread(als_schedule);
            alsThread.start();
            for (int i = 0; i < als_schedule.getThread_elevators().length; i++) {
                als_schedule.getThread_elevators()[i] = new thread_Elevator(i + 1);
                new Thread(als_schedule.getThread_elevators()[i]).start();
            }

            @SuppressWarnings("resource")
            Scanner scanner = new Scanner(System.in);
            String input;
            while (true) {
                if (scanner.hasNextLine()) {
                    input = scanner.nextLine();
                    input = input.replaceAll(" ", "");
                    if (/*input.matches("^\\(FR,[1-9],UP,[0-9]{1,9}\\)$")
                        || input.matches("^\\(FR,(([2-9])|([1][0])),DOWN,[0-9]{1,9}\\)$")
                        || input.matches("^\\(ER,([1-9]|([1][0])),[0-9]{1,9}\\)$")){*/
                            input.matches("^\\(FR,[0-9]{1,2},UP\\)$")
                                    || input.matches("^\\(FR,[0-9]{1,2},DOWN\\)$")
                                    || input.matches("^\\(ER,#[1-3],[0-9]{1,2}\\)$")) {
                        Asking asking = new Asking(input);
                        if (askQueue.addAskingQueue(asking)) {
                            als_schedule.setAskings(asking);
                        }
                    } else if (input.equals("exit")) {
                        System.out.println("end");
                        System.exit(0);
                    } else {
                        //输入不符合正则表达式
                        System.out.println("Invalid Request");
                    }
                }
            }

        }
        catch (Throwable t){
            System.out.println("boom");
            t.printStackTrace();
        }
        finally {
            System.out.println("end");
            System.exit(0);
        }
    }
}