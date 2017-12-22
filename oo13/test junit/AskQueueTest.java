/**
 * 
 */
package elevator_als;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.Vector;

public class AskQueueTest {
	
	AskQueue askQueue = new AskQueue();


	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		System.out.println("prepare");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		System.out.println("End");

	}
	


	/**
	 * {@link elevator.AskQueue#vectorPlus()} 的测试方法。
	 */
	@Test
	public void testVectorPlus() {
		int now;
		now=1;
	}

	/**
	 * {@link elevator_als.AskQueue#addtoQueue(elevator_als.Ask, int)} 的测试方法。
	 */
	@Test
	public void testAddtoQueue() {
		//fail("尚未实现");
		 askQueue.addtoQueue(new Ask("(FR,1,UP,0)"), 0);
		 //askQueue.addtoQueue(new Ask("(FR,1,UP,8)"), 3);
		 askQueue.addtoQueue(new Ask("(FR,1,UP,5)"), 6);
	     askQueue.addtoQueue(new Ask("(FR,2,UP,8)"), 3);
	}

	/**
	 * {@link elevator_als.AskQueue#getaskQueue()} 的测试方法。
	 */
	@Test
	public void testGetaskQueue() {
		//fail("尚未实现");
				askQueue.getaskQueue();
	}
	

}
