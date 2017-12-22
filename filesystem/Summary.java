package filesystem;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Summary {
    private int countRenamed,countModified,countPathChanged,countSizeChanged;
    private ArrayList<String> triggers;
    private ArrayList<String> missions;
    @SuppressWarnings("unused")
    private String summaryPath;
    private int detecteNumber;

    public Summary(ArrayList<String> trigger, ArrayList<String> missions, String summaryPath, int detecteNumber){
        this.triggers = trigger;
        this.summaryPath = summaryPath;
        this.missions = missions;
        this.detecteNumber = detecteNumber;
    }

    public void init(String trigger){
        if(missions.contains("record-summary")) {
            if (trigger.equals("renamed"))
                countRenamed = countRenamed + 1;
            if (trigger.equals("modified"))
                countModified = countModified + 1;
            if (trigger.equals("path-changed"))
                countPathChanged = countPathChanged + 1;
            if (trigger.equals("size-changed"))
                countSizeChanged = countSizeChanged + 1;
        }
    }

    public void printCounts() throws IOException {
        if(missions.contains("record-summary")) {
            File file = new File("D:\\thread " + detecteNumber + " record-summary.txt");
            FileWriter fileWriter = new FileWriter(file);
            if (triggers.contains("renamed"))
                fileWriter.write("renamed : " + countRenamed + " 次\n");
            if (triggers.contains("modified"))
                fileWriter.write("modified : " + countModified + " 次\n");
            if (triggers.contains("path-changed"))
                fileWriter.write("path-changed : " + countPathChanged + " 次\n");
            if (triggers.contains("size-changed"))
                fileWriter.write("size-changed : " + countSizeChanged + " 次\n");
            fileWriter.close();
        }
    }

    public void timerPrint(){
        final Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    printCounts();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        timer.schedule(timerTask, 1000);
    }
}
