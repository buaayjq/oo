package elevator_als;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ElevatorTest {
	
	private Elevator elevator = new Elevator();
    private AskQueue askQueue = new AskQueue();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("prepare");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("End");
	}

	@Before
	public void setUp() throws Exception {
		askQueue.getaskQueue().add(new Ask("(FR,1,UP,0)"));
		askQueue.getaskQueue().add(new Ask("(FR,2,UP,0)"));
		askQueue.getaskQueue().add(new Ask("(FR,2,DOWN,1)"));
        askQueue.getaskQueue().add(new Ask("(FR,3,UP,1)"));
        askQueue.getaskQueue().add(new Ask("(FR,5,DOWN,0)"));
        askQueue.getaskQueue().add(new Ask("(ER,5,2)"));
        askQueue.getaskQueue().add(new Ask("(FR,3,UP,3)"));
        askQueue.getaskQueue().add(new Ask("(ER,1,4)"));
        askQueue.getaskQueue().add(new Ask("(FR,8,UP,1)"));
        askQueue.getaskQueue().add(new Ask("(FR,3,UP,1)"));
        askQueue.getaskQueue().add(new Ask("(FR,3,UP,5)"));
        askQueue.getaskQueue().add(new Ask("(FR,5,DOWN,5)"));
        askQueue.getaskQueue().add(new Ask("(FR,10,DOWN,0)"));
        askQueue.getaskQueue().add(new Ask("(ER,5,1)"));
        askQueue.getaskQueue().add(new Ask("(ER,7,3)"));
        askQueue.getaskQueue().add(new Ask("(ER,6,2)"));
	}

	@After
	public void tearDown() throws Exception {
		askQueue.getaskQueue().clear();
		System.out.println("End");
	}

	@Test
	public void testGetTime() throws Exception{
		System.out.println(elevator.getTime());
		//fail("尚未实现");
	}

	@Test
	public void testGetNow() throws Exception{
		//fail("尚未实现");
		System.out.println(elevator.getNow());
	}

	@Test
	public void testGetnowTime() throws Exception{
		System.out.println(elevator.getnowTime());
		System.out.println(elevator.getnowTime());
		//fail("尚未实现");
	}

	@Test
	public void testGetMain() throws Exception{
		//fail("尚未实现");
		System.out.println(elevator.getMain());
	}

	@Test
	public void testGetBring() throws Exception{
		//fail("尚未实现");
		elevator.getBring().add(0, askQueue.getaskQueue().get(0));
        elevator.resultPrint();
        elevator.bringPrint();
	}

	@Test
	public void testTraversal() throws Exception{
		//fail("尚未实现");
		elevator.run(5);
        elevator.now = 3;
        elevator.time = 10;
        elevator.main = 5;
        elevator.traversal(askQueue);
        
        elevator.run(2);
        elevator.time = 9;
        elevator.main = 1;
        elevator.traversal(askQueue);


	}

//	@Test
//	public void testTraverseCarryFloors() {
//		fail("尚未实现");
//	}

	@Test
	public void testAddCarryRequests() throws Exception{
		//fail("尚未实现");
		 Ask ask = askQueue.getaskQueue().get(0);
		 
	     elevator.addCarryRequests(ask);
	     
	}

	@Test
	public void testIfStillHaveTrueFloor() throws Exception{
		//fail("尚未实现");
		elevator.ifStay[1] = 1;
        System.out.println(elevator.ifStillHaveTrueFloor());
        elevator.ifStay[1] = 0;
        System.out.println(elevator.ifStillHaveTrueFloor());
	}
	
	@Test
	public void testRun() throws Exception{
		elevator.now = 7;
        elevator.run(6);
        elevator.run(7);
        elevator.run(8);
        
        
        
		//fail("尚未实现");
	}

	@Test
	public void testRunrun() throws Exception{
		elevator.time = -1;
        elevator.run(1);
        elevator.runrun(askQueue, 0);
        
        
		//fail("尚未实现");
	}

	@Test
	public void testMove()throws Exception{
		//fail("尚未实现");
		elevator.run(5);
        elevator.now = 4;
        elevator.ifStay[4] = 1;
        elevator.move(askQueue);
        
        elevator.run(1);
        elevator.now = 2;
        elevator.ifStay[4] = 1;
        elevator.move(askQueue);
        
        elevator.run(2);
        elevator.now = 1;
        elevator.ifStay[0] = 0;
        elevator.move(askQueue);
        
        elevator.run(4);
        elevator.now = 5;
        elevator.ifStay[0] = 0;
        elevator.move(askQueue);
        
        elevator.run(4);
        elevator.now = 4;
        elevator.ifStay[0] = 0;
        elevator.move(askQueue);
        
        

       
	}

	@Test
	public void testResultPrint() {
		//fail("尚未实现");
		elevator.resultPrint();
	}

	@Test
	public void testBringPrint() {
		//fail("尚未实现");
		elevator.bring.add(askQueue.getaskQueue().get(2));
		elevator.bringPrint();
		
		elevator.bring.add(askQueue.getaskQueue().get(0));
        elevator.bring.add(askQueue.getaskQueue().get(1));
        elevator.bring.add(askQueue.getaskQueue().get(2));
        elevator.bring.add(askQueue.getaskQueue().get(3));
        
		elevator.bringPrint();
	}

	@Test
	public void testBringRebuild() throws Exception{
		//fail("尚未实现");
		elevator.bringRebuild();
		elevator.bring.add(askQueue.getaskQueue().get(0));
        elevator.bring.add(askQueue.getaskQueue().get(1));
        elevator.bring.add(askQueue.getaskQueue().get(2));
        elevator.bring.add(askQueue.getaskQueue().get(3));
        elevator.main = 3;
        elevator.bringRebuild();
		
	}

}
