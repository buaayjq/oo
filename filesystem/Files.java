package filesystem;


public class Files {
    private long oldModified;
    private String oldPath;
    private long oldSize;
    private String parentPath;

    public Files(long oldModified, String absolutePath, long oldSize, String parentPath){
        this.oldModified = oldModified;
        oldPath = absolutePath;
        this.oldSize = oldSize;
        this.parentPath = parentPath;
    }

    public long getoldModified(){
        return oldModified;
    }
    public void setoldModified(long oldModified){
        this.oldModified = oldModified;
    }
    public String getoldPath(){
        return oldPath;
    }
    public void setoldPath(String oldPath){
        this.oldPath = oldPath;
    }
    public long getoldSize() {
        return oldSize;
    }
    public void setoldSize(long oldSize){
        this.oldSize = oldSize;
    }
    public String getParentPath(){
        return parentPath;
    }
    public void setParentPath(String parentPath){
        this.parentPath = parentPath;
    }
}