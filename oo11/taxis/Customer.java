package taxis;


import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Customer {
    /*Overview
  这个类处理控制台输入的请求
  */
    private Queue queue;
    private long stime;
    private float time;

    public boolean repOK(){
        //Effects: returns true if the rep variant holds for this, otherwise returns false
        if(queue==null) return false;

        return true;
    }

    public Customer(Queue queue,long stime) {
    	/* @Requires:stack stime,gui aer not null
		  * @Modifies:stack stime gui
		  * @Effects:stack stime gui will be instantiated
		  */

        this.queue=queue;
        this.stime=stime;
    }
    public void run(){

        int k=0;
        Scanner scanner = new Scanner(System.in);
        try {

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


                    synchronized (queue) {
                        while(queue.getlength()==300){
                            try{
                                queue.wait();
                            }catch(Exception e){
                                e.printStackTrace();
                            }
                        }
                        queue.notify();
                        queue.pushstack(param);
                        queue.addwaitnum();
                    }
                }
            }}
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
