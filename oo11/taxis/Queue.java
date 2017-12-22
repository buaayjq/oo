package taxis;


public class Queue {

    /*Overview
  stack
  */
    private int[][] stack;
    private int waitnum;
    private int index;

    public Queue(){
		// @Requires:none
		  // @Modifies:stack,waitnum,index
		  //@Effects:they will be instantiated

        stack=new int[100][6];
        waitnum=0;
        index=0;
    }

    public int getlength(){
		// @Requires:none
		  // @Modifies:none
		  //@Effects:get index
		  //
        return index;
    }

    public void addlength(){
		// @Requires:none
		  //@Modifies:index
		  // @Effects:index will be plused by 1

        index++;
    }
    public void addwaitnum(){
		//@Requires:none
		// @Modifies:waitnum
		 // @Effects:waitnum will be plused by 1
		 //
        waitnum++;
    }
    public void subwaitnum(){
		//@Requires:none
		 //@Modifies:waitnum
		  //@Effects:waitnum will be minused by 1
		 //
        waitnum--;
    }
    public int getwaitnum(){
		//@Requires:none
		 // @Modifies:none
		  // @Effects:get waitnum
		  //
        return waitnum;
    }
    public int[][] getStack(){
		// @Requires:none
		  //@Modifies:none
		 // @Effects:get stack
		 //
        return stack;
    }

    public void pushstack(int[] request){
		//@Requires:request is not null
		 // @Modifies:stack
		  // @Effects:stack will be changed
		 //
        for(int i=0;i<4;i++){
            stack[index][i]=request[i];
        }
        stack[index][4]=0;     //0为未分配 1为被抢 2为无效单 3需要分配
        stack[index][5]=0;     //记录次数
        index++;
    }
}
