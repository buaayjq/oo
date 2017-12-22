package taxis;

import java.util.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
public class Ctrl extends Thread {

    /*Overview
    这个类保存一个配对关系，既每个乘客和乘客最终请求的出租车，用以向控制中心发出请求，passenger表示发出请求的乘客，car表示乘客选择的车
    */

    private Taxi []taxilist;
    private Viptaxi[]viptaxilist;
    private boolean moniter;
    private Vector<Ask> AskQueue;//建立请求队列
    private int taxiSystemcalled;
    private File file = new File("D:\\result.txt");

    public boolean repOK(){
        //Effects: returns true if the rep variant holds for this, otherwise returns false
        if(taxilist==null) return false;

        return true;
    }

    public Ctrl(){
        //Requires: 各种属性
        //Modifies: 各种属性
        //Effects: 构造器
        taxilist=new Taxi[100];
        viptaxilist=new Viptaxi[30];
        taxiSystemcalled = -1;
        moniter = false;
        for(int i = 0;i<100;i++){
            taxilist[i] = new Taxi(i);
        }
        for(int i = 0;i<30;i++){
            viptaxilist[i] = new Viptaxi(i);
        }
        AskQueue=new Vector<Ask>();
    }

    public void run(){
        Taxi bestaxi=null;
        Viptaxi bestviptaxi = null;


        for(int i=0;i<100;i++){
            taxilist[i].start();
        }
        for(int i = 0;i <30;i++){
            viptaxilist[i].start();
        }

        while(true){
            try{
            taxiCalled(taxiSystemcalled);}
            catch (Exception e){

            }
            if(!this.AskQueue.isEmpty()){
                for(int i=0;i<AskQueue.size();i++){
                    if(!AskQueue.get(i).ifcomplete()){
                        AskQueue.remove(AskQueue.get(i));
                        i--;
                        continue;
                    }
                    if(AskQueue.get(i).judge()){
                        AskQueue.get(i).taxiState();
                        if((bestaxi=AskQueue.get(i).getBest())!=null){
                            bestaxi.getAsk(AskQueue.get(i));

                            if(moniter){
                                taxiSystemcalled=bestaxi.getNum();
                                moniter=false;
                            }
                        }

                        AskQueue.remove(AskQueue.get(i));
                        i--;
                        continue;
                    }
                    for(Taxi t:taxilist){
                        if(t.getx()>=AskQueue.get(i).getStart().x()-2&&t.getx()<=AskQueue.get(i).getStart().x()+2&&t.gety()>=AskQueue.get(i).getStart().y()-2&&t.gety()<=AskQueue.get(i).getStart().x()+2){
                            AskQueue.get(i).addTaxi(t);
                        }
                    }
                    AskQueue.get(i).timeFlow();
                }
            }
            try{
                sleep(100);
            }
            catch(Exception e){

            }
        }


    }
    public void taxiCalled (int i)throws IOException{
        //Requires: i
        //Modifies: none
        //Effects: 输出
        FileWriter fileWriter = new FileWriter(file, true);
        if(i==-1){

        }
        else{
            if(taxilist[i].getstate()==3){
                this.taxiSystemcalled=-1;
            }


            if(i<71&&i>-1){
            fileWriter.write("taxi"+taxilist[i].getNum()+" is at"+taxilist[i].getx()+","+taxilist[i].gety()+","+taxilist[i].getTaxiState()+",time is:"+Time.timeask()+"\n");
            fileWriter.close();
            }
            if(i>70&&i<100){
                System.out.print("viptaxi"+taxilist[i].getNum()+" is at:"+taxilist[i].getx()+","+taxilist[i].gety()+","+taxilist[i].getTaxiState()+",time is:"+Time.timeask());
                fileWriter.write("viptaxi"+taxilist[i].getNum()+" is at:"+taxilist[i].getx()+","+taxilist[i].gety()+","+taxilist[i].getTaxiState()+",time is:"+Time.timeask()+"\n");
                fileWriter.close();
            }
        }
    }
    public void moniter(int i){
        //Requires: i
        //Modifies: none
        //Effects:监视
        this.taxiSystemcalled=i;
    }
    public void moniter1(){
        //Requires: none
        //Modifies: none
        //Effects: 监视第一辆工作的车
        this.moniter=true;
    }
    public void printCredit()throws  IOException{
        //Requires: none
        //Modifies: none
        //Effects: printcredit
        FileWriter fileWriter = new FileWriter(file, true);
        for(int i=0;i<taxilist.length;i++){
        fileWriter.write(taxilist[i].getCredit());
        fileWriter.close();}
    }
    public void getAsk (Ask ask)throws IOException{
        //Requires: ask
        //Modifies: none
        //Effects: 启动
        FileWriter fileWriter = new FileWriter(file, true);
        //System.out.println("新请求到达，起点"+ask.getStart().x()+","+ask.getStart().y());
        fileWriter.write("新请求到达，起点"+ask.getStart().x()+","+ask.getStart().y());
        fileWriter.close();
        this.AskQueue.add(ask);
    }
    public void statePrint (){
        //Requires: none
        //Modifies: none
        //Effects: 输出车辆状态
        for(int i=0;i<70;i++){
            System.out.println("taxi"+taxilist[i].getNum()+"'s state:"+taxilist[i].getx()+","+taxilist[i].gety()+","+taxilist[i].getTaxiState()+"credit is:"+taxilist[i].getCredit()+",time is:"+Time.timeask());

        }
        for(int i=70;i<100;i++){
            System.out.println("viptaxi"+taxilist[i].getNum()+"'s state:"+taxilist[i].getx()+","+taxilist[i].gety()+","+taxilist[i].getTaxiState()+"credit is:"+taxilist[i].getCredit()+",time is:"+Time.timeask());

        }
    }
    public void init_tzxi(){
        for(int i=0;i<70;i++){
            System.out.println("taxi"+taxilist[i].getNum()+"'s state:"+taxilist[i].getx()+","+taxilist[i].gety()+","+taxilist[i].getTaxiState()+"credit is:"+taxilist[i].getCredit()+",time is:"+Time.timeask());

        }
        for(int i=70;i<100;i++){
            System.out.println("viptaxi"+taxilist[i].getNum()+"'s state:"+taxilist[i].getx()+","+taxilist[i].gety()+","+taxilist[i].getTaxiState()+"credit is:"+taxilist[i].getCredit()+",time is:"+Time.timeask());

        }

    }
}