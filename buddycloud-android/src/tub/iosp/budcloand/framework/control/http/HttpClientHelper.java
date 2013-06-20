package tub.iosp.budcloand.framework.control.http;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.JSONException;

import tub.iosp.budcloand.R;
import tub.iosp.budcloand.framework.exceptions.BCIOException;
import tub.iosp.budcloand.framework.helper.IOHelper;
import tub.iosp.budcloand.framework.model.BCItem;
import tub.iosp.budcloand.framework.model.BCMetaData;
import tub.iosp.budcloand.framework.model.BCSubscribtion;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.Pair;
import android.widget.ImageView;


/**
 * The HttpClientHelper to manage the communication with the server.
 * Encapsulated the functions of BCSession.
 * Support asynchronized image downloading.
 * create as singleton.
 * each call to this helper should be like HttpClientHelper.INSTANCE.your function()
 * need to call the init function before calling any other functions
 * 
 */
public enum HttpClientHelper {
	
	/** The instance. */
	INSTANCE;
	
	/** The tag. */
	private final String TAG = "HttpClientHelper";
	
	/** The context. */
	private Context context;
	
	/** The session. */
	private BCSession session;
	
	/** The current channel. */
	private String currentChannel = "";
	
	/** The password. */
	private String password;
	
	/** The home channel. */
	private String homeChannel;
	
	/** The key store. */
	private KeyStore keyStore;
	
	/** cache of loaded images, using softreference to handle low memory case */
	private final Map<String, SoftReference<Bitmap>> cache;  
    
    /** records of unreachable urls */
    private final HashSet<String> notFound;
    
    /** records of images that are being downloaded now */
    private final HashSet<String> downloading;
    
    /** The pool. */
    private final ExecutorService pool;  
    
    /** The image views. */
    private Map<ImageView, String> imageViews = Collections  
            .synchronizedMap(new WeakHashMap<ImageView, String>());  
    
    /** The placeholder. */
    private Bitmap placeholder; 
	
	/**
	 * Instantiates a new http client helper.
	 */
	private HttpClientHelper(){
		cache = new HashMap<String, SoftReference<Bitmap>>();  
        notFound = new HashSet<String>();
        downloading = new HashSet<String>();
        pool = Executors.newFixedThreadPool(5); 
        keyStore = null;
        
	}
	
	/**
	 * Gets the custom key store.
	 *
	 * @return the custom key store
	 */
	public KeyStore getCustomKeyStore(){
		return keyStore;
	}
		
	
	/**
	 * Sets the context.
	 *
	 * @param context the new context
	 */
	public void setContext(Context context){
		this.context = context;
	}
	
	/**
	 * Gets the home channel.
	 *
	 * @return the home channel
	 */
	public String getHomeChannel(){
		return homeChannel;
	}
	
	/**
	 * Sets the home channel.
	 *
	 * @param home the new home channel
	 */
	public void setHomeChannel(String home){
		this.homeChannel = home;
	}
	
	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword(){
		return homeChannel;
	}
	
	/**
	 * Sets the password.
	 *
	 * @param pwd the new password
	 */
	public void setPassword(String pwd){
		this.password = pwd;
	}
	
	/**
	 * Gets the current channel.
	 *
	 * @return the current channel
	 */
	public String getCurrentChannel(){
		return this.currentChannel;
	}
	
	/**
	 * Sets the current channel.
	 *
	 * @param channel the new current channel
	 */
	public void setCurrentChannel(String channel){
		this.currentChannel = channel;
		Log.d(TAG,"setCurrentChannel called:"+channel);
	}
	
	/**
	 * Start session.
	 *
	 * @param homeDomain the home domain of the api server
	 * @param userName the user name
	 * @param password the password
	 * @return true, if successful
	 */
	public boolean startSession(String homeDomain, String userName, String password){
		return false;
	}
	
	/**
	 * Inits the session using the given name and password
	 *
	 * @param homeChannel the home domain of the api server
	 * @param userName the user name
	 * @param password the password
	 * @return true, if successful
	 */
	public boolean init(String homeChannel, String userName, String password){
		try {
			this.keyStore = KeyStore.getInstance("BKS");
		} catch (KeyStoreException e) {
			e.printStackTrace();
		}
		
		InputStream in = this.context.getResources().openRawResource(R.raw.startcomca);
		try {
            // Initialize the keystore with the provided trusted certificates
            // Also provide the password of the keystore
            this.keyStore.load(in, "buddycloud".toCharArray());
            Log.d(TAG, "keyStore loaded successfully");
        } catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
		List<BCSubscribtion> result = getSubscribed(homeChannel, userName, password, this.keyStore);
		if(result != null){
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * Gets the subscribed channel list of the current user
	 *
	 * @return the subscribed list
	 */
	public List<BCSubscribtion> getSubscribed() {
		List<BCSubscribtion> result = null;
		try {
			result = session.getSubscribed();
		} catch (BCIOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * Gets the subscribed list of a user
	 *
	 * @param homeDomain the home domain of api server
	 * @param userName the user name
	 * @param password the password
	 * @param ks the ks
	 * @return the subscribed
	 */
	private List<BCSubscribtion> getSubscribed(String homeDomain, String userName,
			String password, KeyStore ks){
		
		session = new DefaultSession(homeDomain,userName,password);
		Log.d(TAG,"session created");
		
		List<BCSubscribtion> subList = null;
		
		try{
			subList = session.getSubscribed();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return subList;
	}
	
	/**
	 * Gets the posts given the channel name
	 *
	 * @param channelName the channel name
	 * @param max the max number of posts to get
	 * @return the posts
	 */
	public List<BCItem> getPosts(String channelName, int max){
		List<BCItem> list = null;
		try {
			list = session.getPosts(channelName, max);
		} catch (BCIOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * Post post into a channel
	 *
	 * @param channelName the channel name where the post is created
	 * @param content the content
	 * @return the new created channel
	 * @throws BCIOException the bCIO exception
	 */
	public BCItem postPost(String channelName, String content) throws BCIOException{
		return session.postPost(channelName, content);
	}
	
	/**
	 * Post comment to a certain post in the given channel
	 *
	 * @param channelName the channel name
	 * @param content the content of the comments
	 * @param replyTo the id of post the new comment is replying to 
	 * @return the new comment
	 * @throws BCIOException the bCIO exception
	 */
	public BCItem postComment(String channelName, String content,String replyTo) throws BCIOException{
		return session.postComment(channelName, content, replyTo);
	}
	
  
    /**
     * Sets the default image when download failed
     *
     * @param bmp the default image
     */
    public void setPlaceholder(Bitmap bmp) {  
        placeholder = this.toRoundCorner(bmp, 4);;  
    }  
  
    /**
     * Gets the bitmap from cache.
     *
     * @param url the url to load
     * @return the bitmap from cache
     */
    public Bitmap getBitmapFromCache(String url) {  
        if (cache.containsKey(url)) {  
            return cache.get(url).get();  
        }  
  
        return null;  
    }  
  
    /**
     * handle the image downloading in new thread
     *
     * @param url the url
     * @param imageView the image view
     * @param width the width
     * @param height the height
     * @param isExternal true if the url is not stored in the api server
     */
    private void queueJob(final String url, final ImageView imageView,  
            final int width, final int height, final boolean isExternal) {  
        /* Create handler in UI thread. */  
        final Handler handler = new Handler() {  
            @Override  
            public void handleMessage(Message msg) {  
                String tag = imageViews.get(imageView);  
                if (tag != null && tag.equals(url)) {  
                    if (msg.obj != null) {  
                        imageView.setImageBitmap((Bitmap) msg.obj);  
                    } else {  
                        imageView.setImageBitmap(placeholder);  
                        Log.d(TAG, "fail " + url);  
                    }  
                }  
            }  
        };  
  
        pool.submit(new Runnable() {  
            @Override  
            public void run() {  
                final Bitmap bmp = downloadBitmap(url, width, height, isExternal);  
                Message message = Message.obtain();  
                message.obj = bmp;  
                Log.d(null, "Item downloaded: " + url);  
  
                handler.sendMessage(message);  
            }  
        });  
    }  
  
    /**
     * Load bitmap to the given imageView.
     *
     * @param url the url
     * @param imageView the image view
     * @param width the width
     * @param height the height
     * @param isExternal true if the url is not stored in the api server
     */
    public void loadBitmap(final String url, final ImageView imageView,  
            final int width, final int height, boolean isExternal) {  
    	if(downloading.contains(url))
        	return;
        imageViews.put(imageView, url);  
        
        
        if(notFound.contains(url)){
        	imageView.setImageBitmap(placeholder);
        	return;
        }
        Bitmap bitmap = getBitmapFromCache(url);  
  
        // check in UI thread, so no concurrency issues  
        if (bitmap != null) {  
            Log.d(null, "Item loaded from cache: " + url);  
            bitmap = this.toRoundCorner(bitmap, 8);
            imageView.setImageBitmap(bitmap);  
        } else {  
            imageView.setImageBitmap(placeholder);  
            queueJob(url, imageView, width, height, isExternal);  
        }  
    }  
  
    /**
     * 
     * @param url the url
     * @param width the width
     * @param height the height
     * @param isExternal true if the url is not stored in the api server
     * @return the bitmap
     */
    private Bitmap downloadBitmap(String url, int width, int height, boolean isExternal) {  
    	Bitmap bitmap = null;
    	downloading.add(url);
    	try {  
    		
    		if(isExternal){
    			bitmap = session.getImage(url);
    		}
    		else{
    			bitmap = session.getAvatar(url);
    		}
            bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);  
            cache.put(url, new SoftReference<Bitmap>(bitmap));  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally{
        	if(bitmap == null){
        		notFound.add(url);
        	}
        }
    	downloading.remove(url);
    	return bitmap;
    }



	/**
	 * Gets the posts older than the given one
	 *
	 * @param name the channel address
	 * @param remoteId the flag post's id
	 * @param max the max number of posts to get
	 * @return the posts older than the
	 */
	public List<BCItem> getPostsAfter(String name, String remoteId, int max) {
		List<BCItem> result = null;
		try {
			result = session.getPostsOlder(name, remoteId, max);
		} catch (BCIOException e) {
			//e.printStackTrace();
			Log.e(TAG,e.toString());
		} catch (JSONException e) {
			//e.printStackTrace();
			Log.e(TAG,e.toString());
			
		}
		Log.e(TAG,"result of getPostsAfter == null?"+(result == null));
		return result;
	}

	/**
	 * Gets the newer posts given the date
	 *
	 * @param name the channel address
	 * @param latest the flag date
	 * @param max the max 
	 * @return the newer posts
	 */
	public List<BCItem> getNewerPosts(String name, Date latest, int max) {
		try {
			Log.d(TAG,IOHelper.dateToString(latest));
			List<BCItem> result = session.getPostsNewer(name, latest, max);
			if(result != null){
				for(BCItem item:result){
					Log.d(TAG,"get new post item:"+item.getContent());
				}
			}
			return result;
			//return session.getPostsNewer(name, latest, max);
		} catch (BCIOException e) {
			return null;
		} catch (JSONException e) {
			return null;
		}
	}

	/**
	 * Gets the current user.
	 *
	 * @return the user
	 */
	public String getUser() {
		return session.getUser();
	}

	/**
	 * Gets the metadat of a channel.
	 *
	 * @param channelName the channel name
	 * @return the metadata of the channel
	 */
	public BCMetaData getMetadata(String channelName) {
		BCMetaData result = null;
		try {
			result = session.getMetadata(channelName, "posts");
		} catch (BCIOException e) {
			e.printStackTrace();
		}
		return result;
	}

	

	/**
	 * Search channel that matches the keyword.
	 *
	 * @param keyword the keyword to search
	 * @param max the max
	 * @return the result list
	 */
	public List<BCMetaData> searchChannel(String keyword, int max) {
		List<BCMetaData> result = null;
		try {
			result = session.searchChannel(keyword, max);
		} catch (BCIOException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Subscribe a new channel
	 *
	 * @param channel the channel to subscribe
	 * @return true, if successful
	 */
	public boolean subscribe(String channel) {
		try {
			session.postSubscribe(channel);
		} catch (BCIOException e) {
			e.printStackTrace();
			Log.i(TAG, "Failed to subscribe");
			//return false;
		}
		return true;
	}
	
	/**
	 * Check network info.
	 *
	 * @return true, if connected to Internet
	 */
	public boolean checkNetworkInfo()
    {
        ConnectivityManager conMan = (ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE);

        //mobile 3G Data Network
        State mobile = conMan.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
        if(mobile == State.CONNECTED) 
        	return true;
        
        //wifi
        State wifi = conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        if(wifi == State.CONNECTED)
        	return true;
        return false;
    }
	
	/**
	 * Gets the counts of new posts of all channels the user subscribed
	 *
	 * @param since the flag date
	 * @param max the max
	 * @return a list of pairs that map the channels' names to the counts of their new posts
	 * @throws BCIOException the bCIO exception
	 * @throws JSONException the jSON exception
	 */
	public List<Pair<String, Integer>> getSyncCount(Date since, int max) throws BCIOException, JSONException{
		return this.session.getSyncCount(since, max);
	}
	
	/**
	 * Gets new posts of all channels the user subscribed.
	 *
	 * @param since the flag date
	 * @param max the max
	 * @return a list of pairs that map the channels' names to their new posts
	 * @throws BCIOException the bCIO exception
	 * @throws JSONException the jSON exception
	 */
	public List<Pair<String,List<BCItem>>> getSync(Date since,int max) throws BCIOException, JSONException{
		return this.session.getSync(since, max);
	}

	/**
	 * set the given image into a round-corner one.
	 *
	 * @param bitmap the bitmap
	 * @param pixels the pixels need to smooth
	 * @return the bitmap changed
	 */
	public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {  
	      
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);  
        Canvas canvas = new Canvas(output);  
  
        final int color = 0xff424242;  
        final Paint paint = new Paint();  
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());  
        final RectF rectF = new RectF(rect);  
        final float roundPx = pixels;  
  
        paint.setAntiAlias(true);  
        canvas.drawARGB(0, 0, 0, 0);  
        paint.setColor(color);  
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);  
  
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));  
        canvas.drawBitmap(bitmap, rect, rect, paint);  
  
        return output;  
    }  

}
