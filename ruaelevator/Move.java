package ruaelevator;

import java.io.IOException;

public interface Move{
    void traversal(AskQueue askQueue);
    boolean starToMove(AskQueue askQueue, int i);
    boolean runrun(AskQueue askQueue);
    void resultPrint()throws IOException;
}