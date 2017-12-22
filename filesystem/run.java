package filesystem;


import java.util.Scanner;

    public class run {
        private boolean flag = false;
        @SuppressWarnings("resource")
        public static void main(String[] args){
            try {
                int detecteNumber = 0;
                Detection[] detecters = new Detection[9];
                Scanner scanner = new Scanner(System.in);
                String input = scanner.nextLine();
                while (!input.equals("end")) {
                    detecters[detecteNumber] = new Detection(input, detecteNumber);
                    detecteNumber++;
                    if(detecteNumber >= 9) {
                        System.out.println("监测数目超出要求");
                        System.exit(0);
                    }
                    input = scanner.nextLine();
                }
                for (int i = 0; i < detecteNumber; i++) {
                    new Thread(detecters[i]).start();
                }
                while(true){
                    if(scanner.hasNextLine())
                        input = scanner.nextLine();
                    if(input.equals("exit")) {
                        System.out.println("exit");
                        System.exit(0);
                    }
                }
            }
            catch (Throwable t){
                t.printStackTrace();
            }
            finally {
                System.exit(0);
            }
        }

}
