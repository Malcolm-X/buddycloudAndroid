/*package tub.iosp.budcloand.framework.test;

import static org.junit.Assert.*;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.KeyStoreException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xbill.DNS.TextParseException;

import tub.iosp.budcloand.framework.BCSession;
import tub.iosp.budcloand.framework.DefaultSession;
import tub.iosp.budcloand.framework.helper.IOHelper;
import tub.iosp.budcloand.framework.types.BCResponse;

public class BCSessionTest {
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
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
	public void simpleDoGetTest() {
		System.out.println("### STARTING SIMPLE DO GET TEST ###");
		DefaultSession session = null;
		session = new DefaultSession("buddycloud.org", "malcolm-x", "asdf");
		assertNotNull(session);
		session.setDebug(true);
		HttpsURLConnection con = null;
		try {
			con = session.doGet("/malcolm-x@buddycloud.org/content/posts", null);
			assertEquals(200, con.getResponseCode());
			System.out.println(con.getContent());
			System.out.println(IOHelper.streamToString(con.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("+++ DO GET SUCCESS +++");
		
	}
	
	@Test
	public void SimpleDoPostTest() {
		System.out.println("### STARTING SIMPLE DO POST TEST ###");
		DefaultSession session = null;
		session = new DefaultSession("buddycloud.org", "malcolm-x", "asdf");
		assertNotNull(session);
		session.setDebug(true);
		HttpsURLConnection con = null;
		String content = "{\"content\":\"SimplePostTest() created a Post\"}"; 
		try {
			con = session.doPost("/malcolm-x@buddycloud.org/content/posts", null, content.getBytes("UTF-8"));
			assertEquals(201, con.getResponseCode());
			System.out.println(con.getContent());
			System.out.println(IOHelper.streamToString(con.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("+++ DO POST SUCCESS +++");
	}

//	@Test
//	public void advancedDoGetTest() {
//		System.out.println("### STARTING SIMPLE DO GET TEST ###");
//		DefaultSession session = null;
//		session = new DefaultSession("buddycloud.org", "malcolm-x", "asdf");
//		assertNotNull(session);
//		session.setDebug(true);
//		HttpsURLConnection con = null;
//		try {
//			DateFormat df = new SimpleDateFormat()
//			con = session.doGet("/malcolm-x@buddycloud.org/posts?max=" + URLEncoder.encode("1", charset) + "&before=" + URLEncoder.encode((new Date(2012,1,1).), charset), null);
//			assertEquals(200, con.getResponseCode());
//			System.out.println(con.getContent());
//			System.out.println(IOHelper.streamToString(con.getInputStream()));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		System.out.println("+++ DO GET SUCCESS +++");
//	}
	
//	@Test
//	public void simpleLoginTest() {
////		//File file = new File("res/raw/textfile.txt");
////
////	    FileInputStream fis = null;
////
////	    try {
////	      fis = new FileInputStream(file);
////
////	      fis.close();
////
////	    } catch (FileNotFoundException e) {
////	      e.printStackTrace();
////	    } catch (IOException e) {
////	      e.printStackTrace();
////	    }
//	  
//		DefaultSession session = null;
//		//KeyStore keyStore = null;
//		
//		//keyStore = new KeyStore
//
//		session = new DefaultSession("buddycloud.org", "malcolm-x", "asdf");
//		
//		assertNotNull(session);
//		session.setDebug(true);
//
//		BCResponse response = session.getSubscribed();
//		assertNotNull(response);
//		System.out.println(response.getStatusCode() + " " + response.getStatusText());
//		System.out.println(response.getStatusText());
//		assertNotSame("failed to retrieve content", response);
//		System.out.println(response);
//
//	}
//	
//	@Test
//	public void simpleGetContentTest() {
//		DefaultSession session = null;
//		session = new DefaultSession("buddycloud.org", "malcolm-x", "asdf");
//		assertNotNull(session);
//		session.setDebug(true);
//		BCResponse response = session.getContent("posts");
//		assertNotNull(response);
//		System.out.println(response.getStatusCode() + " " + response.getStatusText());
//		System.out.println(response.getStatusText());
//	}
//	@Test
//	public void simpleConenctionTest() {
//		System.out.println("Starting simple Connection Test");
//		URL url = null;
//		HttpURLConnection con;
//		InputStream in = null;
//		String r = null;
//		try{
//			new URL ("http://www.google.de");
//			con = (HttpURLConnection)url.openConnection();
//			con.getInputStream();
//			r = IOHelper.streamToString(in);
//		}
//		catch (Exception e) {
//			e.printStackTrace();
//			System.out.println(e.getMessage());
//			// TODO: handle exception
//		}
//		assertNotNull(r);
//		System.out.println("result was:");
//		System.out.println(r);
//		
//	}

}
*/