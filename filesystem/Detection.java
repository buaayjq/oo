package filesystem;


import java.io.File;
import java.io.IOException;
import java.util.*;

public class Detection implements Runnable{
    private int detecteNumber;
    private File parentFile;
    private HashMap<String, Files> fileInfoMap = new HashMap<>();
    private HashMap<String, Files> preFileInfoMap = new HashMap<>();
    private HashMap<String, Files> lastFileInfoMap = new HashMap<>();
    private ArrayList<String> trigger = new ArrayList<>();
    private ArrayList<String> jobs = new ArrayList<>();
    private HashSet<Files> createFiles = new HashSet<>();
    private HashSet<Files> deleteFiles = new HashSet<>();
    private HashSet<File> modifiedFiles = new HashSet<>();
    private HashSet<File> sizeChangeFiles = new HashSet<>();
    private Summary summary;
    private detail detail;
    private String detectPath;
    private int isFile = 0;
    private String parentPath;

    public Detection(String input, int detecteNumber){
        int i = 0;
        try {
            this.detecteNumber = detecteNumber;
            String[] strings = input.split("\\ ");
            detectPath = strings[1];
            parentPath = strings[1];
            for (i = 2; !strings[i].equals("THEN"); i++)
                trigger.add(strings[i]);
            for (i = i + 1; i < strings.length; i++)
                jobs.add(strings[i]);
            parentFile = new File(detectPath);
            if(parentFile.isFile()){
                isFile = 1;
            }
            if(!parentFile.exists()){
                System.out.println("第 " + detecteNumber + " 个监视目录不存在");
                System.exit(0);
            }
            summary = new Summary(trigger, jobs, detectPath, detecteNumber);
            detail = new detail(trigger, detectPath, jobs, fileInfoMap, detecteNumber);
            if(parentFile.isFile()) {
                detectPath = parentFile.getParent();
                System.out.print("ok");
            }
            setMap(detectPath);
            preFileInfoMap.putAll(fileInfoMap);
            fileInfoMap.clear();
        }
        catch (Throwable t){
            t.printStackTrace();
            System.out.println("输入有误");
            System.exit(0);
        }
    }

    public void setMap(String detectPath){
        File f = new File(detectPath);
        if(f.exists()){
            if(f.isDirectory()){
                fileInfoMap.put(f.getAbsolutePath(),
                        new Files(f.lastModified(), f.getAbsolutePath(), getChildFile(f), f.getParent()));
                File[] files = f.listFiles();
                for (File file : files) {
                    setMap(file.getAbsolutePath());
                }
            }
            else{
                fileInfoMap.put(f.getAbsolutePath(),
                        new Files(f.lastModified(), f.getAbsolutePath(), f.length(), f.getParent()));
            }
        }
    }

    public HashSet<Files> getDeleteFiles(){
        for(Map.Entry<String, Files> entry : preFileInfoMap.entrySet()){
            File file = new File(preFileInfoMap.get(entry.getKey()).getoldPath());
            if(!file.exists()) {
                deleteFiles.add(entry.getValue());
            }
            else if(!file.isDirectory()){
                if(entry.getValue().getoldModified() != lastFileInfoMap.get(entry.getKey()).getoldModified()){
                    modifiedFiles.add(file);
                }
            }
        }
        return deleteFiles;
    }

    public HashSet<Files> getNewFiles(){
        for(Map.Entry<String, Files> entry : lastFileInfoMap.entrySet()){
            if(!preFileInfoMap.containsKey(entry.getKey())) {
                createFiles.add(entry.getValue());
            }
            else{
                Files preFileInfo = preFileInfoMap.get(entry.getKey());
                Files lastFileInfo = lastFileInfoMap.get(entry.getKey());

                if(preFileInfo.getoldSize() != lastFileInfo.getoldSize()){
                    sizeChangeFiles.add(new File(lastFileInfo.getoldPath()));
                }
            }
        }
        return createFiles;
    }

    public void compareDeleteAndNew() throws IOException {
        for(Files df : deleteFiles){
            for(Files cf : createFiles){
                if(df.getoldSize() == cf.getoldSize() && df.getoldModified() == cf.getoldModified()){
                    if(isFile == 1 && jobs.contains("recover")
                            && trigger.contains("renamed")
                            && df.getParentPath().equals(cf.getParentPath())
                            && df.getoldPath().equals(parentPath)) {
                        summary.init("renamed");
                        recover(df.getoldPath(), cf.getoldPath());
                        lastFileInfoMap.put(df.getoldPath(), df);
                    }
                    else if(isFile == 1 && jobs.contains("recover")
                            && trigger.contains("path-changed")
                            && df.getoldPath().equals(parentPath)){
                        summary.init("path-changed");
                        recover(df.getoldPath(), cf.getoldPath());
                        lastFileInfoMap.put(df.getoldPath(), df);
                    }
                    else if(!jobs.contains("recover")){
                        if (df.getParentPath().equals(cf.getParentPath())) {
                            if(isFile == 1){
                                System.out.println("监视文件被重命名");
                                System.out.println("exit");
                                System.exit(0);
                            }
                            summary.init("renamed");
                            detail.printRenamed(preFileInfoMap.get(df.getoldPath()), lastFileInfoMap.get(cf.getoldPath()));
                        } else{
                            if(isFile == 1){
                                System.out.println("监视文件被移动");
                                System.out.println("exit");
                                System.exit(0);
                            }
                            summary.init("path-change");
                            detail.printPath_Changed(preFileInfoMap.get(df.getoldPath()), lastFileInfoMap.get(cf.getoldPath()));
                        }
                    }
                    else if(isFile == 0){
                        recover(df.getoldPath(), cf.getoldPath());
                        lastFileInfoMap.put(df.getoldPath(), df);
                    }
                    deleteFiles.remove(df);
                    createFiles.remove(cf);
                }
            }
        }
    }

    public void traverseSizeChangeFiles() throws IOException{
        for (File file : sizeChangeFiles) {
            System.out.println(jobs.contains("recover"));
            if((isFile == 0) || (isFile == 1 && file.getAbsolutePath().equals(parentPath))){
                System.out.println("sizechanged");
                summary.init("size-changed");
                detail.printSize_Changed(preFileInfoMap.get(file.getAbsolutePath()), lastFileInfoMap.get(file.getAbsolutePath()));
            }
        }
    }

    public void traverseModifiedFiles() throws IOException{
        for(File file : modifiedFiles){
            if((isFile == 0) || (isFile == 1 && file.getAbsolutePath().equals(parentPath))) {
                summary.init("modified");
                detail.printModified(preFileInfoMap.get(file.getAbsolutePath()), lastFileInfoMap.get(file.getAbsolutePath()));
            }
        }
    }

    public long getChildFile(File file){
        long childamount = 0;
        File[] files = file.listFiles();
        for(File f : files){
            if(f.isFile())
                childamount =childamount + f.length();
        }
        return childamount;
    }

    public void recover(String oldPath, String newPath){
        File recoverFile = new File(newPath);
        recoverFile.renameTo(new File(oldPath));

    }

    public void getTrueDeletedFiles() throws IOException {
        if(!lastFileInfoMap.keySet().contains(parentPath)) {
            System.out.println("监视文件被删除");
            System.exit(0);
        }
        if(!(isFile ==1 && jobs.contains("recover"))) {
            if (!deleteFiles.isEmpty()) {
                for (Files fileInfo : deleteFiles) {
                    summary.init("size-changed");
                    detail.printDelete(fileInfo);
                }
            }
        }
    }

    public void getCreateFiles() throws IOException{
        if(!(parentFile.isFile() && jobs.contains("recover"))) {
            if (!createFiles.isEmpty()) {
                for (Files fileInfo : createFiles) {
                    if(isFile == 0) {
                        summary.init("size-changed");
                        detail.printCreate(fileInfo);
                    }
                }
            }
        }
    }
    public void clearinfo(){
        preFileInfoMap.clear();
        preFileInfoMap.putAll(lastFileInfoMap);
        lastFileInfoMap.clear();
        deleteFiles.clear();
        createFiles.clear();
        sizeChangeFiles.clear();
        modifiedFiles.clear();
    }
    @Override
    public void run() {
        try {
            summary.timerPrint();
            while (true) {
                try {
                    setMap(detectPath);
                    lastFileInfoMap.putAll(fileInfoMap);
                    fileInfoMap.clear();
                    getDeleteFiles();
                    getNewFiles();
                    try {
                        compareDeleteAndNew();
                        getCreateFiles();
                        getTrueDeletedFiles();
                        traverseSizeChangeFiles();
                        traverseModifiedFiles();
                        summary.printCounts();
                    } catch (IOException i) {
                        i.printStackTrace();
                    }
                    clearinfo();
                    Thread.sleep(1000);
                } catch (InterruptedException i) {
                    i.printStackTrace();
                }
            }
        }
        catch (Throwable t){
            t.printStackTrace();
            System.out.println("error");
        }
        finally {
            System.out.println("exit");
            System.exit(0);
        }
    }
}