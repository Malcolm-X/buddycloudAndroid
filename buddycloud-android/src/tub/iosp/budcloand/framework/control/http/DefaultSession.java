
package tub.iosp.budcloand.framework.control.http;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Authenticator;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.ProtocolException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import org.json.JSONException;
import org.xbill.DNS.Name;
import org.xbill.DNS.TextParseException;

import tub.iosp.budcloand.framework.exceptions.BCIOException;
import tub.iosp.budcloand.framework.exceptions.BCJSONException;
import tub.iosp.budcloand.framework.exceptions.StatusCodeException;
import tub.iosp.budcloand.framework.helper.BCJSONParser;
import tub.iosp.budcloand.framework.helper.IOHelper;
import tub.iosp.budcloand.framework.model.BCItem;
import tub.iosp.budcloand.framework.model.BCMetaData;
import tub.iosp.budcloand.framework.model.BCSubscribtion;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.util.Pair;


// TODO: Auto-generated Javadoc
/**
 * The Class DefaultSession supplies basic methods for dataexchange with 
 * a buddycloudserver.
 *
 * @author Malcolm-X
 */
public class DefaultSession implements BCSession {
	
	/** The Constant charset. */
	final static String charset = "UTF-8";
	
	/** The Constant TAG. */
	private static final String TAG = "DefaultSession";
	
	/** The home server. */
	private Name homeServer; 
	
	/** The password. */
	private static String password;
	
	/** The api server. */
	private URL apiServer;
	
	/** The x-session-header. */
	private String xSessionHeader;
	
	/** The users jid. */
	private String jid;
	
	/** The username/password. */
	private PasswordAuthentication pwAuth;
	
	/** The ssl context. */
	private SSLContext sslContext;

 
	/**
	 * Instantiates a new default session that trusts the ca specified in the keystore ks.
	 *
	 * @param home the homeservers domain
	 * @param user the username
	 * @param pw the users password
	 * @param ks the ca cert keystore
	 */
	public DefaultSession(String home, String user, String pw, KeyStore ks){
		super();
		sslContext = null;
			try {
				TrustManagerFactory tmf = TrustManagerFactory.getInstance("X509");
				tmf.init(ks);
				sslContext = SSLContext.getInstance("TLS");
				sslContext.init(null, tmf.getTrustManagers(), new java.security.SecureRandom());
			} catch (Exception e1) {
				Log.e(TAG,"An Error occured while initializing the ssl context:", e1);
			}
		try {
			homeServer = new Name(home);
		} catch (TextParseException e) {
			Log.e(TAG,"error while parsing the homedomain", e);
		}
		this.password = pw;
		this.xSessionHeader = "";
		this.jid = user + "@" + home;
		try {
			this.findApi(home);
		} catch (Exception e) {
			Log.e(TAG,"error while setting api server",e);
		}
		this.setPassword();
	}
	
	/**
	 * Instantiates a new default session.
	 *
	 * @param home the home
	 * @param user the user
	 * @param pw the pw
	 */
	public DefaultSession(String home, String user, String pw) {
		super();
		sslContext = null;
		
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			
			@Override
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}
			
			@Override
			public void checkServerTrusted(X509Certificate[] chain, String authType)
					throws CertificateException {
				
			}
			
			@Override
			public void checkClientTrusted(X509Certificate[] chain, String authType)
					throws CertificateException {
			}
		}}; 
			try {
				sslContext = SSLContext.getInstance("TLS");
				sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
			} catch (KeyManagementException e) {
				Log.e(TAG, "an error occured in " + this.getClass(), e);
			} catch (NoSuchAlgorithmException e) {
				Log.e(TAG, "an error occured in " + this.getClass(), e);
			}
		
		try {
			homeServer = new Name(home);
		} catch (TextParseException e) {
			Log.e(TAG,"error while parsing homeserver", e);
		}
		try {
			this.findApi(home);
		} catch (Exception e) {
			Log.e(TAG,"error while setting api server",e);
		}
		this.password = pw;
		this.xSessionHeader = "";
		this.jid = user + "@" + home;
		
		this.setPassword();
	}
	

/**
 * Finds the apiserver for the homedomain using a dns lookup for the server record
 * Not implemented in this version, since there is not server federation,
 * the api.server domain is static and android does not support SRV-Record
 * LookUp in the DNS out of the box.
 *
 * @param home the home
 * @return the An
 * @throws MalformedURLException when the url was bad
 * @throws BCIOException the bCIO exception
 */
 	
 	private boolean findApi(String home) throws MalformedURLException, BCIOException{
 		try {
			this.apiServer = new URL("https://api." + home);
		} catch (MalformedURLException e) {
			Log.e(TAG,"arror while parsing apiserver", e);
		}
		return true;
 		
 		
// 		Record [] records = new Lookup(homeServer, Type.SRV).run();
// 		if (records == null) return null;
// 		else {
// 			this.apiServer = new URL(
// 					"https://" 
// 					+ ((SRVRecord)records[0]).getTarget().toString()
// 					+ ":"
// 					+ ((SRVRecord)records[0]).getPort());
// 			return null;
// 		}
 	}
 
	
	
	/**
	 * Sets the default password authenticator.
	 */
	private void setPassword(){
		Authenticator.setDefault(new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(jid,password.toCharArray());
			}
		});
	}
	
	
	/**
	 * Executes a simple HTTP-post request to the given location on the apiserver.
	 *
	 * @param file the file
	 * @param body the body
	 * @return the connected HttpsURLConnection
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
public HttpsURLConnection doPost(String file, byte[] body) throws IOException{
		URL url = null;
		url = new URL(apiServer.toString() + file);
		Log.d(TAG,"doPost : "+url.toString());
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
		Log.d(TAG,"con.write done");
		con.connect();
		Log.d(TAG,"con.conneted called");
		return con;
	}
	
	/**
	 * Executes a simple HTTP-Get to the apiserver.
	 *
	 * @param file the file
	 * @return the connected HttpsUrlConnection for for this request 
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public HttpsURLConnection doGet(String file) throws IOException{
		URL url = null;
		url = new URL(apiServer.toString() + file);
		Log.d(TAG,"doGet : "+url.toString());
		HttpsURLConnection con = (HttpsURLConnection)url.openConnection();
		this.setPassword();
		//con.setRequestProperty("X-Session-Id", session);
		con.setRequestProperty("Accept", "application/json");
		con.setRequestProperty("Content-Type", "application/json");
		con.setInstanceFollowRedirects(true);
		con.setSSLSocketFactory(sslContext.getSocketFactory());
		try {
			con.setRequestMethod("GET");
		} catch (ProtocolException e) {
			Log.e(TAG,"set get methods failed", e);
		}
		
		con.connect();
		Log.d(TAG,"done : "+url.toString());
		return con;
	}
	
	/**
	 * Executes a simple HTTP-Get to the given URL.
	 *
	 * @param address the address
	 * @return the connected HttpsUrlConnection for for this request
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	
	public HttpsURLConnection doGetExt(String address) throws IOException{
		
		URL url = null;
		url = new URL(address);
		Log.d(TAG,"doGet : "+url.toString());
		HttpsURLConnection con = (HttpsURLConnection)url.openConnection();
		this.setPassword();
		//con.setRequestProperty("X-Session-Id", session); - not yet used
//		con.setRequestProperty("Accept", "application/json");
//		con.setRequestProperty("Content-Type", "application/json");
		con.setInstanceFollowRedirects(true);
		con.setSSLSocketFactory(sslContext.getSocketFactory());
		try {
			con.setRequestMethod("GET");
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			Log.e(TAG,"set get methods failed");
		}
		
		con.connect();
		Log.e(TAG,"done : "+url.toString());
		return con;
	}
	
	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.control.http.BCSession#getSubscribed()
	 */
	@Override
	public List<BCSubscribtion> getSubscribed() throws BCIOException, JSONException{
		// get the InputStream for the requested Endpoint and handle connection errors
		InputStream in = handleGet("/subscribed",200);
		List<BCSubscribtion> result = BCJSONParser.parseSubscribtionList(IOHelper.streamToString(in), this.getUser());
		return result;
	}

	/**
	 * Handles an HTTP-post request to the given location on the apiserver.
	 *
	 * @param file the file
	 * @param body the body
	 * @param expected the expected response statuscode
	 * @return the input stream for the response body
	 * @throws BCIOException on IO errors
	 */
	private InputStream handlePost(String file, byte[] body, int expected) throws BCIOException{
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
		if(statusCode != 201) throw new StatusCodeException(statusCode, statusMessage, expected, "ok.");
		
		return in;
	}
	
	/**
	 * Handles a HTTP-get Request to the given file on the apiserver.
	 *
	 * @param file the file
	 * @param expectedStatuscode the expected statuscode
	 * @return an Inputstream with the servers responsebody
	 * @throws BCIOException on IO errors
	 */
	private InputStream handleGet(String  file, int expectedStatuscode) throws BCIOException{
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
			
			Log.e(TAG,"handleGet wrong");
		}
		if(statusCode != expectedStatuscode) throw new StatusCodeException(statusCode, statusMessage, expectedStatuscode, "ok.");
		//result = new BCSubscriptionList(IOHelper.streamToString(in));
		return in;
	}
	
	/**
	 * Handles a HTTP-get Request to the given file on the apiserver.
	 *
	 * @param file the file
	 * @param expectedStatuscode the expected statuscode
	 * @return an Inputstream with the servers responsebody
	 * @throws BCIOException on IO errors
	 */
	private InputStream handleGetExt(String  file, int expectedStatuscode) throws BCIOException{
		int statusCode = 0;
		String statusMessage = "";
		InputStream in = null;
		try {
			HttpsURLConnection con = doGetExt(file);
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
		if(statusCode != expectedStatuscode) throw new StatusCodeException(statusCode, statusMessage, expectedStatuscode, "ok.");
		//result = new BCSubscriptionList(IOHelper.streamToString(in));
		return in;
	}
	
	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.control.http.BCSession#getMetadata(java.lang.String, java.lang.String)
	 */
	@Override
	public BCMetaData getMetadata(String channel, String node) throws BCIOException{
		// TODO Auto-generated method stub
		InputStream in = handleGet("/" + channel + "/metadata/" + node, 200);
		BCMetaData result = new BCMetaData(IOHelper.streamToString(in),channel);
		return result;
	}

	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.control.http.BCSession#postMetadata(java.lang.String, tub.iosp.budcloand.framework.model.BCMetaData)
	 */
	@Override
	public BCMetaData postMetadata(String name, BCMetaData metadata) throws BCIOException{
		return postMetadata(this.jid, name, metadata);
	}

	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.control.http.BCSession#postMetadata(java.lang.String, java.lang.String, tub.iosp.budcloand.framework.model.BCMetaData)
	 */
	@Override
	public BCMetaData postMetadata(String channel, String name, BCMetaData metadata) throws BCIOException{
		InputStream in = handlePost("/" + channel +  "/metadata/" + name, metadata.getJSONString().getBytes(), 201);
		return new BCMetaData(IOHelper.streamToString(in),channel);
	}

	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.control.http.BCSession#getPosts(java.lang.String, int)
	 */
	@Override
	public List<BCItem> getPosts(String channelName, int max) throws BCIOException, JSONException{
		// TODO Auto-generated method stub
		String query = "";
		if (max != 0) {
			
				query = "?";
			try {
				query = query + IOHelper.makeQuery("max", Integer.toString(max), charset);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				throw new BCIOException("failed to make query");
			}
		}
		List<BCItem> result = getPostsExec("/" + channelName + "/content/posts"+query, channelName);
		if(result == null) Log.d(TAG, "get posts didnt retrieve any items");
		return result;
	}

	/**
	 * Executes a doGet for the given file at the given channel and parses the response.
	 *
	 * @param file the file
	 * @param channel the channel
	 * @return parsed response (posts)
	 * @throws BCIOException on IO errors
	 * @throws JSONException on parsing errors
	 */
	private List<BCItem> getPostsExec(String file, String channel) throws BCIOException, JSONException{
		int statusCode;
		String statusMessage;
		InputStream in;
		List<BCItem> result; 
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
		String jsonString = IOHelper.streamToString(in);
		Log.d(TAG, jsonString);
		result = BCJSONParser.parseItemList(jsonString, channel);
		return result;
	}
	
	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.control.http.BCSession#getPostsOlder(tub.iosp.budcloand.framework.model.BCItem, int)
	 */
	@Override
	public List<BCItem> getPostsOlder(BCItem post, int max) throws BCIOException, JSONException{
		return getPostsOlder(post.getChannel(), post.getRemoteId(), max);
	}
	
	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.control.http.BCSession#getPostsOlder(java.lang.String, java.lang.String, int)
	 */
	@Override
	public List<BCItem> getPostsOlder(String channelName, String remoteID, int max) throws BCIOException, JSONException{
		String query = "";
		String queryMax = "";
		if (max != 0)
			try {
				queryMax =  IOHelper.makeQuery("max", Integer.toString(max) , charset) + "&";
			} catch (UnsupportedEncodingException e1) {
				Log.d(TAG, "BCSession uses unknown charset");
				e1.printStackTrace();
			}
		try {
			query = "?"  + queryMax + IOHelper.makeQuery("after", remoteID, charset);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			throw new BCIOException("The application-wide charset is set wrong: \"" + charset +"\" - is not known");
		}
		return getPostsExec("/" + channelName + "/content/posts" + query, channelName);
	}

	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.control.http.BCSession#getPostsNewer(java.lang.String, java.util.Date, int)
	 */
	@Override
	public List<BCItem> getPostsNewer(String channelName, Date date, int max) throws BCIOException, JSONException{
		//TODO currently hacked and waiting for api change
		//TODO: remove after parser is written and test functionality
		List<Pair<String,Integer>> tmp = getSyncCount(date, max);
		if(tmp==null) {
			Log.d(TAG, "sync retrieved no items");
			return null;
		}
		Log.d(TAG, "sync retrieved items");
		for (Pair<String, Integer> pair : tmp){
			if(pair.first.equals(channelName)){
				Log.d(TAG, "found postlist for this channel");
				try {
					int value = pair.second.intValue()-1;
					if(value <= 0) return null;
					return getPosts(channelName, pair.second.intValue()-1);
				} catch (BCIOException e) {
					Log.e(TAG, "get posts failed to parse max query");
				}
			}
		}
		Log.d(TAG, "no new posts were returned");
		return null;
//		return getPostsExec("/" + channelName + "/content/posts" + query, channelName);
	}
	
	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.control.http.BCSession#getSyncCount(java.util.Date, int)
	 */
	@Override
	public List<Pair<String, Integer>> getSyncCount(Date since, int max) throws BCIOException, JSONException{
		String query = "";
		try {
			query = "?" + IOHelper.makeQuery("since", IOHelper.dateToString(since)+"Z", charset) + "&" + IOHelper.makeQuery("counters", "true", charset) + "&" + IOHelper.makeQuery("max", Integer.toString(max), charset);
		} catch (UnsupportedEncodingException e) {
			Log.d(TAG, "failed to make query");
			e.printStackTrace();
		}
		InputStream in = handleGet("/sync" + query, 200);
		List<Pair<String, Integer>> list = BCJSONParser.parseSyncCount(IOHelper.streamToString(in));
		return list;
	}
	
	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.control.http.BCSession#getSync(java.util.Date, int)
	 */
	@Override
	public List<Pair<String,List<BCItem>>> getSync(Date since, int max) throws BCIOException, JSONException{
		String query = "";
		try {
			query = "?" + IOHelper.makeQuery("since", IOHelper.dateToString(since)+"Z", charset) + "&" + IOHelper.makeQuery("max", Integer.toString(max), charset);
		} catch (UnsupportedEncodingException e) {
			Log.d(TAG, "failed to make query");
			e.printStackTrace();
		}
		InputStream in = handleGet("/sync" + query, 200);
		List<Pair<String, List<BCItem>>> list = BCJSONParser.parseSync(IOHelper.streamToString(in));
		return list;
	}

	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.control.http.BCSession#getImage(java.lang.String)
	 */
	@Override
	public Bitmap getImage(String imageURL) throws BCIOException{
		
		InputStream in = handleGetExt(imageURL, 200);
		Bitmap result = BitmapFactory.decodeStream(in);
		return result;
	}

	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.control.http.BCSession#getAvatar(java.lang.String)
	 */
	@Override
	public Bitmap getAvatar(String channel) throws BCIOException{
		Log.v(TAG,"getAvatar() called : "+channel);
		InputStream in = handleGet("/" + channel + "/media/avatar", 200);
		
		Bitmap result = null;
		
		result = BitmapFactory.decodeStream(in);

		Log.e(TAG,"get avatar : "+channel);
		return result;
	}
	
	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.control.http.BCSession#postPost(java.lang.String, java.lang.String)
	 */
	@Override
    public BCItem postPost(String channelName, String content) throws BCIOException{
    	return postComment(channelName, content, null);
    }
	
	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.control.http.BCSession#postComment(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
    public BCItem postComment(String channelName, String content, String replyTo) throws BCIOException{
    	Date currTime = new Date(System.currentTimeMillis());
    	BCItem item = new BCItem(null, null, channelName, null, currTime, currTime, content, replyTo, null, null);
    	return postPost(item);
    }
    
	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.control.http.BCSession#postPost(tub.iosp.budcloand.framework.model.BCItem)
	 */
	@Override
	public BCItem postPost(BCItem item) throws BCIOException{
		BCItem result;
		InputStream in = handlePost("/" + item.getChannel() +  "/content/posts/", item.getJSONString().getBytes(), 201);
		try {
			result = new BCItem(IOHelper.streamToString(in), item.getChannel());
		} catch (JSONException e) {
			throw new BCJSONException();
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.control.http.BCSession#postComment(tub.iosp.budcloand.framework.model.BCItem)
	 */
	@Override
	public BCItem postComment(BCItem item) throws BCIOException{
		return postPost(item);
	}
	
	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.control.http.BCSession#postSubscribe(tub.iosp.budcloand.framework.model.BCSubscribtion)
	 */
	@Override
	public BCSubscribtion postSubscribe(BCSubscribtion sub) throws BCIOException{
		InputStream in = handlePost("/subscribed", sub.getJSONObject().toString().getBytes(), 200);
		return sub;
	}

	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.control.http.BCSession#postSubscribe(java.lang.String)
	 */
	@Override
	public BCSubscribtion postSubscribe(String channelName)
			throws BCIOException {
		return postSubscribe(channelName, "posts");
	}
	
	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.control.http.BCSession#postSubscribe(java.lang.String, java.lang.String)
	 */
	@Override
	public BCSubscribtion postSubscribe(String channelName, String node)
			throws BCIOException {
		return postSubscribe(new BCSubscribtion(null, getUser(), channelName + "/" + node,"publisher", null));
	}

	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.control.http.BCSession#getUser()
	 */
	@Override
	public String getUser() {
		return this.jid;
	}

	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.control.http.BCSession#searchChannel(java.lang.String, int)
	 */
	@Override
	public List<BCMetaData> searchChannel(String keyword, int max) throws BCIOException {
		String query = "";
		String queryMax = "";
		if(max != 0)
			try {
				queryMax = "&" + IOHelper.makeQuery("max", Integer.toString(max), charset);
			} catch (UnsupportedEncodingException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		try {
			query = "?" + IOHelper.makeQuery("type", "metadata", charset) + "&" + IOHelper.makeQuery("q", keyword, charset) + queryMax;
		} catch (UnsupportedEncodingException e1) {
			Log.e(TAG, "BCSession uses unknown charset");
			e1.printStackTrace();
		}
		
		InputStream in = handleGet("/search" + query,200);
		try {
			return BCJSONParser.parseMetadataList(IOHelper.streamToString(in));
		} catch (JSONException e) {
			throw new BCIOException("Metadatalist not created from search result, because Parser failed to parse JSON response");
		}
	}

	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.control.http.BCSession#searchChannel(java.lang.String, int, int)
	 */
	@Override
	public List<BCMetaData> searchChannel(String keyword, int offset, int max) {
		// TODO not needed yet, left for later implementation
		return null;
	}


}
