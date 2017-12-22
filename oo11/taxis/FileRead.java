package taxis;

import java.io.*;
import java.util.*;
import java.util.Queue;
import java.util.Map;

public class FileRead {
    /*Overview
  这个类构建最短路径
  */
    private int[][] map;
    private static short [][][][] minlength;//
    private static point [][] mapplus;//
    private Map<point,point> step;

    public boolean repOK(){
        //Effects: returns true if the rep variant holds for this, otherwise returns false

        // if(filePath==null) return false;

        return true;
    }

    public FileRead(){
        //Requires: none
        //Modifies: none
        //Effects: 读入文件
        boolean n,s,w,e;//东南西北
        step=new HashMap<point,point>();
        map=new int [80][80];
        mapplus=new point[80][80];//初始化广搜用map
        minlength=new short[80][80][80][80];

       read();

        for(int i=0;i<80;i++){
            for(int j=0;j<80;j++){
                mapplus[j][i]=new point(j,i);
                n=false;
                s=false;
                w=false;
                e=false;
                switch(map[j][i]){
                    case 0:{
                        s=false;
                        e=false;
                        break;
                    }
                    case 1:{
                        s=false;
                        e=true;
                        break;
                    }
                    case 2:{
                        e=false;
                        s=true;
                        break;
                    }
                    case 3:{
                        e=true;
                        s=true;
                        break;
                    }
                }
                if(j==0&&i!=0){
                    w=false;
                    if(map[j][i-1]==2 || map[j][i-1]==3){
                        n=true;
                    }
                }
                if(i==0&&j!=0){
                    n=false;
                    if(map[j-1][i]==1 || map[j-1][i]==3){
                        w=true;
                    }
                }
                if(i>0&&j>0){
                    if(map[j-1][i]==1 || map[j-1][i]==3){
                        w=true;
                    }
                    if(map[j][i-1]==2||map[j][i-1]==3){
                        n=true;
                    }
                }
                mapplus[j][i].direction(n, s, w, e);

            }
        }

        try{
            setMinlength();
        }
        catch(Exception ee){
            System.out.println("map error");
            System.exit(1);
        }
    }

    public void read(){
        String filePath = "D:\\map.txt";
        File mapfile=new File(filePath);//文件建立

        if(mapfile.isFile() && mapfile.exists()){
            System.out.println("读取地图");
        }
        int y=0;
        try{
            FileReader fr=new FileReader(mapfile);
            BufferedReader bf = new BufferedReader(fr);
            String line=null;
            while((line=bf.readLine())!=null){
                for(int x=0;x<80;x++){
                    String st=line.substring(x,x+1);
                    map[x][y]=Integer.valueOf(st);
                }
                y++;
            }
        }
        catch (Exception e1){
            System.out.println("map-read fail");
            System.exit(1);
            e1.printStackTrace();

        }

    }

    public void setMinlength(){
        //Requires: none
        //Modifies: min lenth
        //Effects: 寻找地图上的最短路径
        for(int i=0;i<80;i++){
            for(int j=0;j<80;j++){
                minLength(j,i);

            }
            System.out.println("最短路径初始化中");
        }
    }

    public void minLength(int x1,int y1){
        //Requires: none
        //Modifies: none
        //Effects: 寻找最短路径
        Queue<point> q=new LinkedList<point>();//初始化队列
        step.clear();//清空第一步映射地图
        for(int i=0;i<80;i++){
            for(int j=0;j<80;j++){
                mapplus[j][i].devisit();
            }
        }

        point M,N;
        mapplus[x1][y1].visit();
        q.offer(mapplus[x1][y1]);
        while(!q.isEmpty()){
            M=q.poll();
            if(M.notrh()){
                N=mapplus[M.x()][M.y()-1];
                if(!N.visited()){
                    N.visit();
                    q.offer(N);
                    step.put(N,M);
                }
            }
            if(M.east()){
                N=mapplus[M.x()+1][M.y()];
                if(!N.visited()){
                    N.visit();
                    q.offer(N);
                    step.put(N,M);
                }
            }
            if(M.south()){
                N=mapplus[M.x()][M.y()+1];
                if(!N.visited()){
                    N.visit();
                    q.offer(N);
                    step.put(N,M);
                }
            }
            if(M.west()){
                N=mapplus[M.x()-1][M.y()];
                if(!N.visited()){
                    N.visit();
                    q.offer(N);
                    step.put(N,M);
                }
            }
        }
        for(int i=0;i<80;i++){
            for(int j=0;j<80;j++){
                minlength[x1][y1][j][i]=get_first_path(x1,y1,j,i);
            }
        }
    }

    public short get_first_path(int x,int y,int aimx,int aimy){
        //Requires: xy aimx aimy
        //Modifies: none
        //Effects:第一步移动
        point G,V;
        G=mapplus[aimx][aimy];
        V=mapplus[x][y];
        if(x==aimx&&y==aimy){
            return 0;
        }

        while(!(V.x()==step.get(G).x()&&V.y()==step.get(G).y())){
            G=step.get(G);

        }

        if(G.y()==V.y()-1){
            return 1;//north
        }
        if(G.x()==V.x()+1){
            return 2;//east
        }
        if(G.y()==V.y()+1){
            return 3;//south
        }
        if(G.x()==V.x()-1){
            return 4;//west
        }
        return -1;

    }
    public static short rr1(int x1,int y1,int x2,int y2){
        //Requires: x1 x2 y1 y2
        //Modifies: none
        //Effects: 存入数组
        return minlength[x1][y1][x2][y2];
    }
    public static point getmapplus(int x1,int y1){

        //Requires: x1 y1
        //Modifies: none
        //Effects: 存入数组
        return mapplus[x1][y1];
    }
    public static int minLenth(int x1,int y1,int x2,int y2){
        //Requires: x1 y1 x2 y2
        //Modifies: none
        //Effects: 计算距离
        int x=x1;
        int y=y1;
        int length=0;
        while(x!=x2||y!=y2){
            switch(minlength[x][y][x2][y2]){
                case 1:
                    y--;
                    break;
                case 2:
                    x++;
                    break;
                case 3:
                    y++;
                    break;
                case 4:
                    x--;
                    break;
            }
            length=length+1;
        }
        return length;
    }

}