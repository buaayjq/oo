package taxis;

import java.util.ArrayList;
import java.util.Random;

public class Ask {

    /*Overview
    这个类处理请求
    */
    private point Start;
    private point End;
    private int Time;
    private ArrayList<Taxi> chooseList;


    public boolean repOK(){
        //Effects: returns true if the rep variant holds for this, otherwise returns false
        if(Start==null) return false;
        if(End == null) return false;

        return true;
    }

    public Ask(int xs,int ys,int xe,int ye){
        //Requires: 各种属性
        //Modifies: 各种属性
        //Effects: 构造器
        Start = new point(xs,ys);
        End = new point(xe,ye);
        Time=0;
        this.chooseList=new ArrayList<Taxi>();
    }

    public point getStart(){
        //Requires: none
        //Modifies: none
        //Effects: null
        return this.Start;
    }
    public point getEnd(){
        //Requires: none
        //Modifies: none
        //Effects: null
        return this.End;
    }
    public void timeFlow(){
        //Requires: none
        //Modifies: none
        //Effects: null
        this.Time++;
    }
    public boolean judge(){
        //Requires: none
        //Modifies: none
        //Effects: 判断
        if(this.Time>=30){
            return true;
        }
        else{
            return false;
        }
    }

    public void addTaxi(Taxi t){
        //Requires: t
        //Modifies: t
        //Effects: 添加taxi
        if(!this.chooseList.contains(t)){
            this.chooseList.add(t);
            t.credit1();
        }

    }
    public void taxiState(){
        //Requires: none
        //Modifies: taxistate
        //Effects: 改变taxistate
        for(int i=0;i<this.chooseList.size();i++){
            if(this.chooseList.get(i).getstate()!=0){
                this.chooseList.remove(this.chooseList.get(i));
                i--;
            }
        }
    }
    public Taxi getBest(){
        //Requires: none
        //Modifies: none
        //Effects: 选择最合适的
        int maxcredit=-1;
        if(this.chooseList.isEmpty()){
            System.out.println("起点为"+this.Start.x()+","+this.Start.y()+"的请求无车辆接受");
            return null;
        }
        for(Taxi t:this.chooseList){
            if(t.getCredit()>maxcredit){
                maxcredit=t.getCredit();
            }
        }
        for(int i=0;i<this.chooseList.size();i++){
            if(this.chooseList.get(i).getCredit()!=maxcredit){
                this.chooseList.remove(this.chooseList.get(i));
                i--;
            }
        }
        if(this.chooseList.size()==1){
            return this.chooseList.get(0);
        }
        else{
            int minlength=180;
            for(Taxi t:this.chooseList){
                if(FileRead.minLenth(t.getx(),t.gety(),Start.x(),Start.y())<minlength){
                    minlength=FileRead.minLenth(t.getx(),t.gety(),Start.x(),Start.y());
                }
            }
            for(int i=0;i<this.chooseList.size();i++){
                if(FileRead.minLenth(this.chooseList.get(i).getx(),this.chooseList.get(i).gety(),Start.x(),Start.y())!=minlength){
                    this.chooseList.remove(this.chooseList.get(i));
                    i--;
                }
            }
            if(this.chooseList.size()==1){
                return this.chooseList.get(0);
            }
            else{
                Random random=new Random();
                return this.chooseList.get(random.nextInt(this.chooseList.size()));
            }
        }
    }
    public boolean ifcomplete(){
        //Requires: none
        //Modifies: none
        //Effects: 判断请求是否完成
        boolean endcheck=this.End.x()>=0&&this.End.x()<80&&this.End.y()>=0&&this.End.y()<80;
        boolean startcheck=this.Start.x()>=0&&this.Start.x()<80&&this.Start.y()>=0&&this.Start.y()<80;
        if(endcheck&&startcheck){
            return true;
        }
        else{
            return false;
        }
    }

}