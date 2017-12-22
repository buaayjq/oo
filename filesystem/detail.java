package filesystem;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class detail {
    private ArrayList<String> records = new ArrayList<>();
    private ArrayList<String> trigger = new ArrayList<>();
    private ArrayList<String> jobs = new ArrayList<>();
    @SuppressWarnings("unused")
    private HashMap<String, Files> fileMap = new HashMap<>();
    private String detailPath;
    private int detectNumber;
    private File file = new File("D:\\thread " + detectNumber + " record-detail.txt");

    public detail(ArrayList<String> trigger, String detailPath, ArrayList<String> jobs,
                  HashMap<String, Files> fileMap, int detectNumber){
        this.trigger = trigger;
        this.detailPath = detailPath;
        this.jobs = jobs;
        this.fileMap = fileMap;
        this.detectNumber = detectNumber;
    }

    public void printRenamed(Files preFiles, Files oldFiles) throws IOException{
        FileWriter fileWriter = new FileWriter(file, true);
        if(trigger.contains("renamed")){
       //     System.out.println(new File(preFiles.getoldPath()).getParent() + " 发生了重命名 "
        //            + "old ：" + new File(preFiles.getoldPath()).getName()
        //            + "now : " + new File(oldFiles.getoldPath()).getName());

            fileWriter.write(preFiles.getoldPath() + " 发生了修改 "
                    + "old ：" + preFiles.getoldPath()
                    + "now : " + oldFiles.getoldPath() + "\n");
            fileWriter.close();
        }
    }

    public void printModified(Files preFiles, Files oldFiles) throws IOException{
        FileWriter fileWriter = new FileWriter(file, true);
        if(trigger.contains("modified")){
          //  System.out.println(preFiles.getoldPath() + "发生了修改 "
         //           + "上次修改时间 ：" + new Date(preFiles.getoldModified())
           //         + " 最后一次修改时间 : " + new Date(oldFiles.getoldModified()));

            fileWriter.write(preFiles.getoldPath() + "发生了修改 "
                    + "上次修改时间 ：" + new Date(preFiles.getoldModified())
                    + " 最后一次修改时间 : " + new Date(oldFiles.getoldModified()) + "\n");
            fileWriter.close();
        }
    }

    public void printPath_Changed(Files preFiles, Files oldFiles) throws IOException{
        FileWriter fileWriter = new FileWriter(file, true);
        if(trigger.contains("path-changed")){
          //  System.out.println("发生了路径改变"
         //           + "old ：" + preFiles.getoldPath()
          //          + "now : " + oldFiles.getoldPath());

            fileWriter.write("路径发生改变 "
                    + "old ：" + preFiles.getoldPath()
                    + "now : " + oldFiles.getoldPath() + "\n");
            fileWriter.close();
        }
    }

    public void printSize_Changed(Files preFiles, Files oldFiles) throws IOException{
        FileWriter fileWriter = new FileWriter(file, true);
        if(trigger.contains("size-changed")){
            //System.out.println(preFiles.getoldPath() + " 发生了大小变化 "
             //       + "old ：" + preFiles.getoldSize()
             //       + "now : " + oldFiles.getoldSize());

            fileWriter.write(preFiles.getParentPath() + " 发生了大小变化 "
                    + "old ：" + preFiles.getoldSize()
                    + "now : " + oldFiles.getoldSize() + "\n");
            fileWriter.close();
        }
    }

    public void printDelete(Files preFiles) throws IOException{
        FileWriter fileWriter = new FileWriter(file, true);
        if(trigger.contains("size-changed")){
            //System.out.println(preFiles.getoldPath() + " 发生了删除文件的大小变化 "
             //       + "old ：" + preFiles.getoldSize()
              //      + "now : " + 0);

            fileWriter.write(preFiles.getoldPath() + " 发生了删除文件大小变化 "
                    + "old ：" + preFiles.getoldSize()
                    + "now : " + 0 + "\n");
            fileWriter.close();
        }
    }

    public void printCreate(Files oldFiles) throws IOException{
        FileWriter fileWriter = new FileWriter(file, true);
        if(trigger.contains("size-changed")){
           // System.out.println(oldFiles.getoldPath() + " 发生了新建文件大小变化 "
           //         + "old ：" + 0
          //          + "now : " + oldFiles.getoldSize());

            fileWriter.write(oldFiles.getoldPath() + " 发生了新建文件大小变化 "
                    + "old ：" + 0
                    + "now : " + oldFiles.getoldSize() + "\n");
            fileWriter.close();
        }
    }

    public void printDelete_Create(Files preFiles, Files oldFiles) throws IOException{
        FileWriter fileWriter = new FileWriter(file, true);
        if(trigger.contains("size-changed")){
          //  System.out.println(preFiles.getoldPath() + " 发生了大小变化 "
          //          + "old ：" + preFiles.getoldSize()
          //          + "now : " + oldFiles.getoldSize());

            fileWriter.write(preFiles.getoldPath() + " 发生了大小变化 "
                    + "old ：" + preFiles.getoldSize()
                    + "now : " + oldFiles.getoldSize() + "\n");
            fileWriter.close();
        }
    }

    public void printAll(Files preFiles, Files oldFiles) throws IOException{
        if(jobs.contains("record-detail")){
            printModified(preFiles, oldFiles);
            printRenamed(preFiles, oldFiles);
            printSize_Changed(preFiles, oldFiles);
            printPath_Changed(preFiles, oldFiles);
        }
    }

    public synchronized void printRecord() throws IOException {
        File file = new File(detailPath + "_record-detail.txt");
        FileWriter fileWriter = new FileWriter(file, true);
        for(String record : records){
            fileWriter.write(record);
        }
        fileWriter.close();
        records.clear();
    }
    public void timerPrint(){
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    printRecord();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        timer.schedule(timerTask, 10000);
    }

}
