package taxis;


public class Points {

    /*Overview
  这个类保存点的属性
  */

    boolean up = false;
    boolean down = false;
    boolean left = false;
    boolean right = false;
    boolean ifPreUsed = false;
    boolean ifLastUsed = false;
    int sumFlow = 0;/*从起点到该点的流量*/
    boolean hasTrafficLight = false;


    public boolean repOK(){
        //Effects: returns true if the rep variant holds for this, otherwise returns false

        return true;
    }


    public Points() {
        //Requires: none
        //Effects: 空构造器
    }

    public Points(boolean up, boolean down, boolean left, boolean right, boolean hasTrafficLight) {
        //Requires: 能否上下左右，是否被遍历过，总流量等均不为NULL
        //Effects: 构造器
        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;
        //this.sumFlow = sumFlow;
        this.hasTrafficLight = hasTrafficLight;
    }
}
