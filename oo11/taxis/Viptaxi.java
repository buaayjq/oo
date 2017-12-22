package taxis;


import java.util.ArrayList;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class Viptaxi extends Taxi implements Runnable{

     /*Overview
  这个类保存出租车的属性并指挥怎么走，继承自taxi，是可追踪的车
  */


    private ArrayList<ArrayList<Integer>> servePath = new ArrayList<>();

    public  Viptaxi(int num){
        super(num);
    }

    public int getServeCount(){
        //Requires: none
        //Modifies: none
        //Effects: 返回已经服务的乘客的次数
        return servePath.size();
    }

    public boolean getAsk(Ask q){
        //Requires: ask
        //Modifies: ask
        //Effects: 回应请求
        if(this.state==0){
            System.out.println("tracing car:"+this.getNum()+"get requestion start at"+q.getStart().x()+","+q.getStart().y());
            this.carask=q;
            this.state=1;
            this.credit+=3;
            return true;
        }
        else{
            return false;
        }
    }

    public ListIterator<Location> listIterator(int count){
        //Requires: none
        //Modifies: none
        //Effects: 返回count指定服务次数时的服务路径的迭代器，越界返回null
        if(count < 0 || count >= getServeCount()){
          //  System.out.println("eeeee");
            return null;
        }
        return new ListIterator<Location>() {

            ArrayList<Integer> arrayList = servePath.get(count);
            int cursor = 0;

            @Override
            public boolean hasNext() {
                //Requires: none
                //Modifies: none
                //Effects: 当且仅当有下一个元素返回true，否则返回false
                return cursor != arrayList.size();
            }

            @SuppressWarnings("unchecked")
            public Location next() {
                //Requires: none
                //Modifies: none
                //Effects: 如果有后一个元素则返回后一个元素，游标加一，否则抛出异常
                int i = cursor;
                if (i >= arrayList.size())
                    throw new NoSuchElementException();
                cursor = i + 1;
                return new Location(arrayList.get(i) % 80, arrayList.get(i) / 80);
            }

            @Override
            public boolean hasPrevious() {
                //Requires: none
                //Modifies: none
                //Effects: 当且仅当有前一个元素返回true，否则返回false
                return cursor != 0;
            }

            @SuppressWarnings("unchecked")
            public Location previous() {
                //Requires: none
                //Modifies: none
                //Effects: 如果有前一个元素则返回前一个元素，游标减一，否则抛出异常
                int i = cursor - 1;
                if (i < 0)
                    throw new NoSuchElementException();
                cursor = i;
                return new Location(arrayList.get(i) % 80, arrayList.get(i) / 80);
            }

            @Override
            public int nextIndex() {
                //Requires: none
                //Modifies: none
                //Effects: 返回cursor
                return cursor;
            }

            @Override
            public int previousIndex() {
                //Requires: none
                //Modifies: none
                //Effects: 返回cursor减一
                return cursor - 1;
            }

            @Override
            public void remove() {
                //Requires: none
                //Modifies: none
                //Effects: 移除游标所指向的元素
                arrayList.remove(cursor);
            }

            @Override
            public void set(Location location) {
                //Requires: none
                //Modifies: none
                //Effects: none
            }

            @Override
            public void add(Location location) {
                //Requires: none
                //Modifies: none
                //Effects: none
            }
        };
    }

    public void move(int x,int y){
        //Requires: x，y
        //Modifies: position
        //Effects: 有目的地的移动
        int flag=FileRead.rr1(position.x(),position.y(),x,y);

        if(flag == 0){

        }
        else if(flag ==1){
            position=FileRead.getmapplus(position.x(),position.y()-1);
        }
        else if(flag ==2){
            position=FileRead.getmapplus(position.x()+1,position.y());
        }
        else if(flag == 3){
            position=FileRead.getmapplus(position.x(),position.y()+1);
        }
        else if(flag == 4){
            position=FileRead.getmapplus(position.x()-1,position.y());
        }
    }

    public void run(){
        while(true){
            switch(state){
                case 0://等待服务
                    move();
                    time++;
                    if(time == 200){//改变状态
                        this.state=3;
                    }
                    break;
                case 1://抢单成功
                    if(carask==null){
                        System.out.println("no aim");
                    }
                    else{
                        move(carask.getStart().x(),carask.getStart().y());
                    }
                    this.getPassenger();
                    break;
                case 2://服务
                    if(carask==null){
                        System.out.println("no aim");
                    }
                    else{
                        move(carask.getEnd().x(),carask.getEnd().y());
                    }
                    this.putPassenger();
                    break;
                case 3://休息
                    try{//改变状态
                        sleep(900);
                    }
                    catch(Exception e){

                    }
                    this.time = 0;
                    this.state = 0;
                    break;
            }
            try{
                sleep(100);
            }
            catch(Exception e){
                System.out.println("sleep wrong");
            }
        }
    }

}
