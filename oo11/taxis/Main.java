package taxis;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
class mapInfo{
    int[][] map=new int[80][80];
    public void readmap(String path){//读入地图信息
        //Requires:String类型的地图路径,System.in
        //Modifies:System.out,map[][]
        //Effects:从文件中读入地图信息，储存在map[][]中
        Scanner scan=null;
        File file=new File(path);
        if(file.exists()==false){
            System.out.println("地图文件不存在,程序退出");
            System.exit(1);
            return;
        }
        try {
            scan = new Scanner(new File(path));
        } catch (FileNotFoundException e) {

        }
        for(int i=0;i<80;i++){
            String[] strArray = null;
            try{
                strArray=scan.nextLine().split("");
            }catch(Exception e){
                System.out.println("地图文件信息有误，程序退出");
                System.exit(1);
            }
            for(int j=0;j<80;j++){
                try{
                    this.map[i][j]=Integer.parseInt(strArray[j]);
                }catch(Exception e){
                    System.out.println("地图文件信息有误，程序退出");
                    System.exit(1);
                }
            }
        }
        scan.close();
    }
}
public class Main {
    public static TaxiGUI gui=new TaxiGUI();
    public static void PushRequest(int x1,int y1,int x2,int y2){
        gui.RequestTaxi(new Point(x1,y1), new Point(x2,y2));
    }




    public static void main(String args[]){


        mapInfo mi=new mapInfo();
        mi.readmap("D:\\map.txt");//在这里设置地图文件路径
        gui.LoadMap(mi.map, 80);
//		Scanner scan=new Scanner(System.in);
//		while(true){
//			int x1=scan.nextInt();
//			int y1=scan.nextInt();
//			int x2=scan.nextInt();
//			int y2=scan.nextInt();
//			int status=scan.nextInt();
//			gui.SetRoadStatus(new Point(x1,y1), new Point(x2,y2), status);
//			System.out.println("\n改变");
//		}
        //gui.RequestTaxi(new Point(1,1), new Point(3,5));
        gv.stay(5000);
        gui.SetTaxiStatus(1, new Point(1,1), 1);
        gui.SetTaxiStatus(2, new Point(1,1), 1);
        gv.stay(100);
        gui.SetTaxiStatus(1, new Point(1,2), 1);
        gui.SetTaxiStatus(2, new Point(1,2), 1);
        gv.stay(100);
        gui.SetTaxiStatus(1, new Point(1,3), 1);
        gui.SetTaxiStatus(2, new Point(1,3), 1);
        gv.stay(100);
        gui.SetTaxiStatus(1, new Point(2,3), 1);
        gui.SetTaxiStatus(2, new Point(2,3), 1);
        gv.stay(100);
        gui.SetTaxiStatus(1, new Point(2,4), 1);
        gui.SetTaxiStatus(2, new Point(2,4), 1);
        gv.stay(100);
        gui.SetTaxiStatus(1, new Point(3,4), 1);
        gui.SetTaxiStatus(2, new Point(3,4), 1);
        gv.stay(100);
        gui.SetTaxiStatus(1, new Point(3,5), 1);
        gui.SetTaxiStatus(2, new Point(3,5), 1);
        gui.SetLightStatus(new Point(2,2), 1);//设置东西方向为绿灯
        gui.SetLightStatus(new Point(3,3), 2);//设置东西方向为红灯
        gui.SetTaxiStatus(1, new Point(2,2), 1);
        gui.SetTaxiType(1, 1);






        int k;
        FileRead fileRead=new FileRead();
        Ctrl c=new Ctrl();
        Time time=new Time();
        c.start();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String or = scanner.nextLine();
            Pattern p = Pattern.compile("\\[CR,\\(\\d{1,2},\\d{1,2}\\),\\(\\d{1,2},\\d{1,2}\\)\\]");
            Matcher m = p.matcher(or);
            if(m.matches()){
                char[] ch=or.toCharArray();
                int[] param = new int[4];
                if((ch[6]>='0')&&(ch[6]<='9')){
                    param[0] = (ch[5]-'0')*10+(ch[6]-'0');
                    k=6;
                }
                else{
                    param[0] = ch[5]-'0';
                    k=5;
                }
                if((ch[k+3]>='0')&&(ch[k+3]<='9')){
                    param[1] = (ch[k+2]-'0')*10+(ch[k+3]-'0');
                    k=k+3;
                }
                else{
                    param[1] = ch[k+2]-'0';
                    k=k+2;
                }
                if((ch[k+5]>='0')&&(ch[k+5]<='9')){
                    param[2] = (ch[k+4]-'0')*10+(ch[k+5]-'0');
                    k=k+5;
                }
                else{
                    param[2] = ch[k+4]-'0';
                    k=k+4;
                }
                if((ch[k+3]>='0')&&(ch[k+3]<='9')){
                    param[3] =  (ch[k+2]-'0')*10+(ch[k+3]-'0');
                    k=0;
                }
                else{
                    param[3] = ch[k+2]-'0';
                    k=0;
                }
                try {
                    Input input = new Input("D:\\map.txt", "D:\\maps.txt");/*读入文件建立地图*/
                   // input.buildMap();
                  //  input.buildTrafficLightMap();










                    c.moniter1();

                    Random rand=new Random();
                    Ask test1=new Ask(rand.nextInt(80),rand.nextInt(80),rand.nextInt(80),rand.nextInt(80));
                    c.getAsk(test1);
                    Ask test2=new Ask(rand.nextInt(80),rand.nextInt(80),rand.nextInt(80),rand.nextInt(80));
                    c.getAsk(test2);
                    Ask test3=new Ask(rand.nextInt(80),rand.nextInt(80),rand.nextInt(80),rand.nextInt(80));
                    c.getAsk(test3);
                    Ask test4=new Ask(rand.nextInt(80),rand.nextInt(80),rand.nextInt(80),rand.nextInt(80));
                    c.getAsk(test4);
                    Ask test11=new Ask(param[0],param[1],param[2],param[3]);
                    c.getAsk(test11);
                    c.moniter(1);
                    c.moniter(80);
                   c.statePrint();
                    /*将（1，2）到（1，3）的道路关闭，需为相邻的两个点之间，否则无效，true表示设置道路断开*/
//                Map.setBlocked(new Location(1, 2).oneDimensionalLoc(), new Location(1, 3).oneDimensionalLoc(), true);
//                /*将（1，2）到（1，3）的道路打开，需为相邻的两个点之间，否则无效，false表示设置道路连接*/
//                Map.setBlocked(new Location(1, 2).oneDimensionalLoc(), new Location(1, 3).oneDimensionalLoc(), false);
//                /*获取（1，2）到（1，3）的道路的流量，需为相邻的两个点之间否则返回Integer.MAX_VALUE*/































                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else{
                System.out.println("invalid input");
            }

        }
    /*    try{
            c.automoniter();

            Random rand=new Random();
            Ask test1=new Ask(rand.nextInt(80),rand.nextInt(80),rand.nextInt(80),rand.nextInt(80));
            c.getAsk(test1);
            Ask test2=new Ask(rand.nextInt(80),rand.nextInt(80),rand.nextInt(80),rand.nextInt(80));
            c.getAsk(test2);
            Ask test3=new Ask(rand.nextInt(80),rand.nextInt(80),rand.nextInt(80),rand.nextInt(80));
            c.getAsk(test3);
            Ask test4=new Ask(rand.nextInt(80),rand.nextInt(80),rand.nextInt(80),rand.nextInt(80));
            c.getAsk(test4);
            c.statePrint();

        }
        catch(Exception e){
            System.out.println("error");
        }*/





    }
}