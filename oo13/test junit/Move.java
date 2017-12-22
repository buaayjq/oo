package elevator_als;


public interface Move{
    void traversal(AskQueue askQueue);
    void runrun(AskQueue askQueue, int i);
    void move(AskQueue askQueue);
    void resultPrint();
}
