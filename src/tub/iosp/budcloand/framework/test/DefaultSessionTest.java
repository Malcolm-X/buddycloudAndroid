package tub.iosp.budcloand.framework.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import tub.iosp.budcloand.framework.Http.DefaultSession;

public class DefaultSessionTest {
	
	private DefaultSession ds;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("################################################");
		System.out.println("#         Starting DefaultSession Test         #");
		System.out.println("################################################");
		System.out.println("");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}
	
	@Test
	public void SimpleConnectionTest(){
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		System.out.println("+         strarting simple Connection Tests            +");
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		DefaultSession ds = createSession();
		assertNotNull("Failed to call constructor 		of DefaultSession implementation of BCSession", ds);
		//String getPost
		
	}
	
	@Test
//	public void 

	private static DefaultSession createSession(){
		return new DefaultSession("buddycloud.org", "malcolm-x", "asdf");
	}
}
