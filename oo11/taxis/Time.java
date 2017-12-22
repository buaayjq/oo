package taxis;

public class Time {
    /*Overview
 这个类处理时间
 */
    private static long time;
    private static long  begintime;
    public boolean repOK(){
        //Effects: returns true if the rep variant holds for this, otherwise returns false


        return true;
    }

    public Time(){
        //Requires: none
        //Modifies: time
        //Effects: 空构造器
        begintime=System.currentTimeMillis();
        time=0;
    }
    public static long timeask(){
        //Requires: none
        //Modifies: time
        //Effects: 获得时间
        time=System.currentTimeMillis()-begintime;
        if(time-time/100*100>=50){
            return time/100+1;
        }
        // }
        else{
            return time/100;
        }
    }

}
