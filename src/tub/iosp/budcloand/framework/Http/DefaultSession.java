/**
 * 
 */
package tub.iosp.budcloand.framework.Http;

import java.io.BufferedInputStream;

import tub.iosp.budcloand.framework.exceptions.BCIOException;
import tub.iosp.budcloand.framework.exceptions.StatusCodeException;
import tub.iosp.budcloand.framework.helper.HttpClientHelper;
import tub.iosp.budcloand.framework.helper.IOHelper;
import tub.iosp.budcloand.framework.types.BCComment;
import tub.iosp.budcloand.framework.types.BCItem;
import tub.iosp.budcloand.framework.types.BCMetaData;
import tub.iosp.budcloand.framework.types.BCPost;
import tub.iosp.budcloand.framework.types.BCPostList;
import tub.iosp.budcloand.framework.types.BCSubscription;
import tub.iosp.budcloand.framework.types.BCSubscriptionList;
import tub.iosp.budcloand.framework.types.SubscriptionType;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.sql.Connection;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import org.apache.http.message.BasicHttpResponse;
import org.json.JSONArray;
import org.xbill.DNS.*;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;


/**
 * @author Malcolm-X
 * @Comment Please stop hacking the code....
 */
public class DefaultSession implements BCSession {
	final static String charset = "UTF-8";
	private static final String TAG = "DefaultSession";
	// Debuggers
	private boolean debug = false;
	
	
	public enum APILookupResponse {NO_API_SERVER_FOR_DOMAIN, API_FOUND}
	public enum ConnectionResponse {SUCCESS}
	public enum LoginResponse {SUCCESS}
	private Name homeServer; 
	//private String username;
	private static String password;
	private URL apiServer;
	private String xSessionHeader;
	private String sHeader; //The current Session Id
	private String jid;
	private PasswordAuthentication pwAuth;
	private SSLContext sslContext;
	/*
	 * Standart constructor
	 * @params
	 * home the homeserver of the user
	 * user username 
	 * pw userpassword
	 */

/*
 * Temporary Constructor for testing and prototype (no ssl cert. check)
 * will be depricated
 */
	public DefaultSession(String home, String user, String pw, KeyStore ks){
		super();
		sslContext = null;
		try {
			homeServer = new Name(home);
		} catch (TextParseException e) {
			// TODO Auto-generated catch block
			Log.e(TAG,e.getMessage());
		}
		//username = user;
		password = pw;
		xSessionHeader = "";
		jid = user + "@" + home;
		//this.getApi(); currently bugged
		try {
			this.apiServer = new URL("https://api." + home);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			Log.e(TAG,e.getMessage());
		}
		this.setPassword();
	}
	
	public DefaultSession(String home, String user, String pw) {
		super();
		sslContext = null;
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			
			@Override
			public X509Certificate[] getAcceptedIssuers() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public void checkServerTrusted(X509Certificate[] chain, String authType)
					throws CertificateException {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void checkClientTrusted(X509Certificate[] chain, String authType)
					throws CertificateException {
				// TODO Auto-generated method stub
				
			}
		}}; 
			try {
				//TrustManagerFactory tmf = TrustManagerFactory.getInstance("X509");
				//tmf.init(keyStore);
				
				sslContext = SSLContext.getInstance("TLS");
				sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
				
			} catch (KeyManagementException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (NoSuchAlgorithmException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		
		try {
			homeServer = new Name(home);
		} catch (TextParseException e) {
			// TODO Auto-generated catch block
			Log.e(TAG,e.toString());
		}
		//username = user;
		password = pw;
		xSessionHeader = "";
		jid = user + "@" + home;
		//this.getApi(); currently bugged
		try {
			this.apiServer = new URL("https://api." + home);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			Log.e(TAG,e.toString());
		}
		this.setPassword();
	}
	
/*
 * real constructor to supply certificate as keystore
 */
//	public BCSession(String home, String user, String pw, KeyStore keyStore) throws KeyStoreException {
//		sslContext = null;
//			try {
//				TrustManagerFactory tmf = TrustManagerFactory.getInstance("X509");
//				tmf.init(keyStore);
//
//				sslContext = SSLContext.getInstance("TLS");
//				sslContext.init(null, tmf.getTrustManagers(), null);
//			} catch (KeyManagementException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			} catch (NoSuchAlgorithmException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//		
//		try {
//			homeServer = new Name(home);
//		} catch (TextParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		//username = user;
//		password = pw;
//		xSessionHeader = "";
//		jid = user + "@" + home;
//		//this.getApi(); currently bugged
//		try {
//			this.apiServer = new URL("https://api." + home);
//		} catch (MalformedURLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		this.setPassword();
//	}
	
	/*
	 * Call this method to start an SRV lookup for the BC APIserver on the given home domain.
	 * TODO: who checks for connection?
	 * TODO: use server with highest prio
	 * TODO: check for concurrent protocol(url)
	 * @returns status)
	 */
	private APILookupResponse getApi() throws MalformedURLException{
		Record [] records = new Lookup(homeServer, Type.SRV).run();
		if (records == null) return APILookupResponse.NO_API_SERVER_FOR_DOMAIN;
		else {
			this.apiServer = new URL(
					"https://" 
					+ ((SRVRecord)records[0]).getTarget().toString()
					+ ":"
					+ ((SRVRecord)records[0]).getPort());
			return APILookupResponse.API_FOUND;
		}
	}
	
	
	private void setPassword(){
		
		//TODO ERRORS
		Authenticator.setDefault(new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(jid,password.toCharArray());
			}
		});
		//return ConnectionResponse.SUCCESS;
	}
	
//	public LoginResponse login(){
//		// TODO check login
//		return LoginResponse.SUCCESS;
//	}
	/*public BCResponse getContent(String name){
		return getContent(this.jid, name);
	}*/
	
	public HttpsURLConnection doPost(String file, byte[] body) throws IOException{
		URL url = null;
		url = new URL(apiServer.toString() + file);
		HttpsURLConnection con = (HttpsURLConnection)url.openConnection();
		this.setPassword();
		con.setSSLSocketFactory(sslContext.getSocketFactory());
		con.setDoOutput(true);
		//con.setRequestProperty("X-Session-Id", session);
		con.setRequestProperty("Accept", "application/json");
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("Content-Length", String.valueOf(body.length));
		try {
			con.setRequestMethod("POST");
		} catch (ProtocolException e) {
			Log.e(TAG,e.toString());
		}
		con.setInstanceFollowRedirects(true);
		OutputStream out = con.getOutputStream();
		out.write(body);
		Log.e(TAG,"con.write done");
		con.connect();
		Log.e(TAG,"con.conneted called");
		return con;
	}
	
	public HttpsURLConnection doGet(String file) throws IOException{
		//String query = "";
		//if (params != null &&params.length%2 == 0){
			//String.format(format, args)
		//}
		URL url = null;
		url = new URL(apiServer.toString() + file);
		Log.e(TAG,"doGet : "+url.toString());
		HttpsURLConnection con = (HttpsURLConnection)url.openConnection();
		this.setPassword();
		Log.e(TAG,"set password done");
		//con.setRequestProperty("X-Session-Id", session);
		con.setRequestProperty("Accept", "application/json");
		con.setRequestProperty("Content-Type", "application/json");
		con.setInstanceFollowRedirects(true);
		con.setSSLSocketFactory(sslContext.getSocketFactory());
		Log.e(TAG,"set head done");
		try {
			con.setRequestMethod("GET");
			Log.e(TAG,"set get done");
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			Log.e(TAG,"set get methods failed");
		}
		
		con.connect();
		Log.e(TAG,"done : "+url.toString());
		return con;
	}
	

	
	// method stubs for easy application lifecycle management
	public void onSaveInstanceState(){
		//TODO
	}
	public void onCreate(){
		//TODO
	}
	
	public void onStart(){
		//TODO
	}
	
	public void onRestart(){
		//TODO
	}
	
	public void onResume(){
		//TODO
	}
	
	public void onStop(){
		//TODO
	}
	
	public void onPause(){
		//TODO
	}

	public void onDestroy(){
		//TODO
	}
	public boolean isDebug() {
		return debug;
	}
	
	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	@Override
	public BCSubscriptionList getSubscribed() throws BCIOException{
		// get the InputStream for the requested Endpoint and handle connection errors
		InputStream in = handleGet("/subscribed");
		BCSubscriptionList result = new BCSubscriptionList(IOHelper.streamToString(in));
		return result;
	}

	private InputStream handlePost(String file, byte[] body) throws BCIOException{
		int statusCode;
		String statusMessage;
		InputStream in;
		try{
			HttpsURLConnection con = doPost(file, body);
			statusCode = con.getResponseCode();
			Log.e(TAG,"statusCode : "+statusCode);
			in = con.getInputStream();
			statusMessage = con.getResponseMessage();
		}
		catch(IOException e){
			// TODO
			throw new BCIOException(e.toString());
		}
		if(statusCode != 201) throw new StatusCodeException(statusCode, statusMessage, 200, "ok.");
		
		return in;
	}
	
	private InputStream handleGet(String  file) throws BCIOException{
		int statusCode = 0;
		String statusMessage = "";
		InputStream in = null;
		try {
			HttpsURLConnection con = doGet(file);
			statusCode = con.getResponseCode();
			Log.e(TAG,"get status code:"+statusCode);
			in = con.getInputStream();
			Log.e(TAG,"get inputstream");
			statusMessage = con.getResponseMessage();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//Log.e(TAG,e.toString());
			//throw new BCIOException(e.toString());
			Log.e(TAG,"ddddd");
		}
		if(statusCode != 200) throw new StatusCodeException(statusCode, statusMessage, 200, "ok.");
		//result = new BCSubscriptionList(IOHelper.streamToString(in));
		return in;
	}
	@Override
	public BCMetaData getMetadata(String channel, String node) throws BCIOException{
		// TODO Auto-generated method stub
		InputStream in = handleGet("/" + channel + "/metadata/" + node);
		BCMetaData result = new BCMetaData(IOHelper.streamToString(in));
		return result;
	}

	@Override
	public BCMetaData postMetadata(String name, BCMetaData metadata) throws BCIOException{
		return postMetadata(this.jid, name, metadata);
	}

	@Override
	public BCMetaData postMetadata(String channel, String name, BCMetaData metadata) throws BCIOException{
		InputStream in = handlePost("/" + channel +  "/metadata/" + name, metadata.getJSONString().getBytes());
		return new BCMetaData(IOHelper.streamToString(in));
	}

	@Override
	public BCPostList getPosts(String channelName) throws BCIOException{
		// TODO Auto-generated method stub
		
		return getPostsExec("/" + channelName + "/content/posts");
	}

	private BCPostList getPostsExec(String file) throws BCIOException{
		int statusCode;
		String statusMessage;
		InputStream in;
		BCPostList result; 
		try {
			HttpsURLConnection con = doGet(file);
			statusCode = con.getResponseCode();
			in = con.getInputStream();
			statusMessage = con.getResponseMessage();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.e(TAG,e.toString());
			throw new BCIOException(e.toString());
		}
		if(statusCode != 200) throw new StatusCodeException(statusCode, statusMessage, 200, "ok.");
		result = new BCPostList(IOHelper.streamToString(in));
		return result;
	}
	
	@Override
	public BCPostList getPostsBefore(String channelName, String date) throws BCIOException{
		// TODO Auto-generated method stub
		// TODO parse DATE???
		String query = null;
		try {
			query = "?" + IOHelper.makeQuery("after", date, charset);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			throw new BCIOException("The application-wide charset is set wrong: \"" + charset +"\" - is not known");
		}
		return getPostsExec("/" + channelName + "/content/posts" + query);
	}

	@Override
	public BCPostList getPostsAfter(String channelName, String date) throws BCIOException{
		// TODO Auto-generated method stub
		//TODO currently the same as before
		String query = null;
		try {
			query = "?" + IOHelper.makeQuery("after", date, charset);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			throw new BCIOException("The application-wide charset is set wrong: \"" + charset +"\" - is not known");
		}
		return getPostsExec("/" + channelName + "/content/posts" + query);
	}

	@Override
	public BCPostList getPostsSync(String channelName, String sinceDate,
			int max) throws BCIOException{
		// TODO Auto-generated method stub
		// fix later
		return null;
	}

	@Override
	public Bitmap getImage(String imageURL) throws BCIOException{
		// wrong method, if imageURL is complete, the api prefix added by doGet() will lead to failure!!!
		InputStream in = handleGet(imageURL);
		Bitmap result = BitmapFactory.decodeStream(in);
		return result;
	}

	@Override
	public Bitmap getAvatar(String channel) throws BCIOException{
		// TODO Auto-generated method stub
		Log.v(TAG,"getAvatar() called : "+channel);
		InputStream in = handleGet("/" + channel + "/media/avatar");
		
		Bitmap result = null;
		
		result = BitmapFactory.decodeStream(in);

		/*ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		try {
			while ((len = in.read(buffer)) != -1) {
				outStream.write(buffer, 0, len);
			}
		} catch (IOException e) {
			Log.e(TAG,"decode bitmap failed : "+channel);
		}
		byte[] data = outStream.toByteArray();
		result = BitmapFactory.decodeByteArray(data, 0,
                data.length);*/
		
		
		//Bitmap result = BitmapFactory.decodeStream(in);
		Log.e(TAG,"get avatar : "+channel);
		return result;
	}
    
	@Override
	public BCPost postPost(String channel, String content) throws BCIOException{
		// TODO Auto-generated method stub
		Log.e(TAG,"postPost() called : "+channel+":"+content);
		//BCItem item = new BCItem("","","","",content);
		//String JsonText = item.getJSONObject().toString();
		//InputStream in = handlePost("/" + channel +  "/content/posts", JsonText.getBytes());
		return new BCPost("");
	}

	@Override
	public BCItem postComment(String channelName, String content,
			String replyTo) throws BCIOException{
		//create JSON
		Log.e(TAG,"postComment() called : "+channelName+":"+content);
		//BCItem item = new BCItem("","","","",content,replyTo);
		//String JsonText = item.getJSONObject().toString();
		InputStream in = handlePost("/" + channelName +  "/content/posts/", "".getBytes());
		return new BCItem(IOHelper.streamToString(in));
	}
	
	private BCSubscription postSubscribe(String channelName, String node, SubscriptionType role) throws BCIOException{
		BCSubscription sub = new BCSubscription(channelName, role);
		InputStream in = handlePost("/subscribed/", sub.getJSONObject().toString().getBytes());
		return sub;
	}
	
	@Override
	public BCSubscription postSubscribe(String channelName) throws BCIOException{
		return postSubscribe(channelName, "posts", SubscriptionType.PUBLISHER);
	}


}
