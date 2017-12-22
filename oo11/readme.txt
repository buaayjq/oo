默认路径为d盘map.txt和maps.txt
运行时会先进行初始化，随后由控制台输入，标准输入格式[CR,(x1,y1),(x2,y2)],错误输入或不合法输入会输出invalid input并忽略。
例如[CR,(1,1),(6,6)]
关于初始化，改了这几次后初始化越来越慢0.0，输入请求后才会有输出
即时是从地图初始化完那一刻开始计时的
除了用户输入外，我还在下面产生了4个随机的请求
前70个出租车是源氏出租车，后30个是本次新添加的，我用viptaxi来写的
测试方法介绍，
所有测试方法的调用限于main类 main方法
c.moniter(int i)
观察一辆车直到它休息，i为车代号（我在程序里监视了下1和71号车）
c.stateprint()
显示所有车的状态
c.moniter1();
自动监视开始干活的第一辆车
Map.setBlocked(new Location(1, 2).transfer(), new Location(1, 3).transfer(), true);
                    /*将（1，2）到（1，3）的道路打开，需为相邻的两个点之间，否则无效，false表示设置道路连接*/
Map.setBlocked(new Location(1, 2).transfer(), new Location(1, 3).transfer(), false);
                    /*获取（1，2）到（1，3）的道路的流量，需为相邻的两个点之间否则返回Integer.MAX_VALUE*/
int flow = Map.getFlows(new Location(1, 2).transfer(), new Location(1, 3).transfer());


public void init_tzxi(){

for(int i=0;i<70;i++){
            System.out.println("taxi"+taxilist[i].getNum()+"'s state:"+taxilist[i].getx()+","+taxilist[i].gety()+","+taxilist[i].getTaxiState()+"credit is:"+taxilist[i].getCredit()+",time is:"+Time.timeask());

        }
        for(int i=70;i<100;i++){
            System.out.println("viptaxi"+taxilist[i].getNum()+"'s state:"+taxilist[i].getx()+","+taxilist[i].gety()+","+taxilist[i].getTaxiState()+"credit is:"+taxilist[i].getCredit()+",time is:"+Time.timeask());

        }
    }

输出时为了方便测试在控制台也保留了一些输出
viptaxi的追踪在控制台有
输出文件在D盘result.txt
