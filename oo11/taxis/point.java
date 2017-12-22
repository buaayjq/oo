package taxis;

public class point {
    /*Overview
  这个类保存点的属性
  */

    private int x,y;
    private boolean visited = false;
    private boolean north = false;
    private boolean south = false;
    private boolean east = false;
    private boolean west = false;
    int flow = 0;

    public boolean repOK(){
        //Effects: returns true if the rep variant holds for this, otherwise returns false
        if(x<0||x>80) return false;
        if(y <0|| y>80) return false;

        return true;
    }

    public point(int x,int y){
        //Requires: none
        //Modifies: none
        //Effects: 空构造器
        this.x=x;
        this.y=y;
        this.visited=false;

    }
    public void visit(){
        //Requires: none
        //Modifies: none
        //Effects: 此点被访问
        this.visited=true;
    }
    public void devisit(){

        //Requires: none
        //Modifies: none
        //Effects: 此点被访问
        this.visited=false;
    }
    public int x(){
        //Requires: none
        //Modifies: none
        //Effects: x坐标
        return this.x;
    }
    public int y(){
        //Requires: none
        //Modifies: none
        //Effects: y坐标
        return this.y;
    }
    public boolean visited(){
        //Requires: none
        //Modifies: none
        //Effects: 此点被访问
        return this.visited;
    }

    public void direction(boolean n,boolean s,boolean w,boolean e){
        //Requires: nswe
        //Modifies: nswe
        //Effects: 东西南北坐标
        this.north=n;
        this.south=s;
        this.west=w;
        this.east=e;
    }

    public boolean notrh(){
        //Requires: n
        //Modifies: n
        //Effects: n坐标
        return this.north;
    }
    public boolean south(){
        //Requires: s
        //Modifies: s
        //Effects: s坐标
        return this.south;
    }
    public boolean west(){
        //Requires: w
        //Modifies: w
        //Effects: w坐标
        return this.west;
    }
    public boolean east(){
        //Requires: e
        //Modifies: e
        //Effects: e坐标
        return this.east;
    }
    /*public point() {
        //Requires: none
        //Modifies: none
        //Effects: 空构造器
    }

    public point(int x, int y, boolean north, boolean south, boolean west, boolean east, boolean visited, int flow) {
        //Requires: 能否东南西北的属性，是否被遍历过，总流量
        //Modifies: none
        //Effects: 构造器
        this.x = x;
        this.y = y;
        this.north = north;
        this.south = south;
        this.west = west;
        this.east = east;
        this.visited = visited;
        this.flow = flow;
    }

    public point clone(){
        //Requires: none
        //Modifies: none
        //Effects: 返回当前对象的一个拷贝
        return new point(x, y, north, south, west, east, visited, flow);
    }*/

}