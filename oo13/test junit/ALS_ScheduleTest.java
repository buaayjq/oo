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

public class ALS_ScheduleTest {
	
	
	ALS_Schedule alstest = new ALS_Schedule();

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
	 * {@link elevator_als.ALS_Schedule#getarriveTime()} 的测试方法。
	 */
	@Test
	public void testGetarriveTime() {
		//fail("尚未实现");
		alstest.getarriveTime();
	}

	/**
	 * {@link elevator_als.ALS_Schedule#setarriveTime(elevator_als.Elevator)} 的测试方法。
	 */
	@Test
	public void testSetarriveTime() {
		//fail("尚未实现");
		alstest.setarriveTime(new Elevator());
	}

}
