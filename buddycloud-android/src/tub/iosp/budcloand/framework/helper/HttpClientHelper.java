package tub.iosp.budcloand.framework.helper;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.util.Collection;
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

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import tub.iosp.budcloand.framework.Http.BCSession;
import tub.iosp.budcloand.framework.Http.DefaultSession;
import tub.iosp.budcloand.framework.database.channelPostsScope;
import tub.iosp.budcloand.framework.exceptions.BCIOException;
import tub.iosp.budcloand.framework.types.BCItem;
import tub.iosp.budcloand.framework.types.BCMetaData;
import tub.iosp.budcloand.framework.types.BCSubscribtion;


public enum HttpClientHelper {
	INSTANCE;
	
	private final String TAG = "HttpClientHelper";
	
	private Context context;
	
	private BCSession session;
	
	private String currentChannel = "";
	private String homeChannel = "";
	private String password = "";
	
	private final Map<String, SoftReference<Bitmap>> cache;  
    private final HashSet<String> notFound;
    private final HashSet<String> downloading;
    private final ExecutorService pool;  
    private Map<ImageView, String> imageViews = Collections  
            .synchronizedMap(new WeakHashMap<ImageView, String>());  
    private Bitmap placeholder; 
	
	private HttpClientHelper(){
		cache = new HashMap<String, SoftReference<Bitmap>>();  
        notFound = new HashSet<String>();
        downloading = new HashSet<String>();
        pool = Executors.newFixedThreadPool(5); 
	}
	
	public void setContext(Context context){
		this.context = context;
	}
	
	public String getHomeChannel(){
		return homeChannel;
	}
	
	public void setHomeChannel(String home){
		this.homeChannel = home;
	}
	
	public String getPassword(){
		return homeChannel;
	}
	
	public void setPassword(String pwd){
		this.password = pwd;
	}
	
	public String getCurrentChannel(){
		return this.currentChannel;
	}
	
	public void setCurrentChannel(String channel){
		this.currentChannel = channel;
	}
	
	public boolean startSession(String homeDomain, String userName, String password){
		return false;
	}
	
	public boolean init(String homeChannel, String userName, String password){
		List<BCSubscribtion> result = getSubscribed(homeChannel, userName, password);
		if(result != null){
			return true;
		}
		else{
			return false;
		}
	}
	
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
	
	public List<BCSubscribtion> getSubscribed(String homeDomain, String userName, String password){
		
		session = new DefaultSession(homeDomain,userName,password);
		
		List<BCSubscribtion> subList = null;
		
		try{
			subList = session.getSubscribed();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return subList;
	}
	
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
	
	public BCItem postPost(String channelName, String content) throws BCIOException{
		// TODO Auto-generated method stub
		return session.postPost(channelName, content);
	}
	
	public BCItem postComment(String channelName, String content,String replyTo) throws BCIOException{
		// TODO Auto-generated method stub
		return session.postComment(channelName, content, replyTo);
	}
	
  
    public void setPlaceholder(Bitmap bmp) {  
        placeholder = bmp;  
    }  
  
    public Bitmap getBitmapFromCache(String url) {  
        if (cache.containsKey(url)) {  
            return cache.get(url).get();  
        }  
  
        return null;  
    }  
  
    private void queueJob(final String url, final ImageView imageView,  
            final int width, final int height) {  
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
                        Log.d(null, "fail " + url);  
                    }  
                }  
            }  
        };  
  
        pool.submit(new Runnable() {  
            @Override  
            public void run() {  
                final Bitmap bmp = downloadBitmap(url, width, height);  
                Message message = Message.obtain();  
                message.obj = bmp;  
                Log.d(null, "Item downloaded: " + url);  
  
                handler.sendMessage(message);  
            }  
        });  
    }  
  
    public void loadBitmap(final String url, final ImageView imageView,  
            final int width, final int height) {  
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
            imageView.setImageBitmap(bitmap);  
        } else {  
            imageView.setImageBitmap(placeholder);  
            queueJob(url, imageView, width, height);  
        }  
    }  
  
    private Bitmap downloadBitmap(String url, int width, int height) {  
    	Bitmap bitmap = null;
    	downloading.add(url);
    	try {  
            bitmap = session.getAvatar(url); 
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

//	public List<BCItem> getPosts(String name, Date first, Date last, int max) {
//		return null;
//		// TODO Auto-generated method stub
//		
//	}

	public List<BCItem> getPostsAfter(String name, Date last, int max) {
		List<BCItem> result = null;
		try {
			session.getPostsOlder(name, last, max);
		} catch (BCIOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}

	public List<BCItem> getNewerPosts(String name, Date latest, int max) {
		try {
			session.getPostsNewer(name, latest, max);
		} catch (BCIOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getUser() {
		return session.getUser();
	}

	public BCMetaData getMetadata(String channelName) {
		BCMetaData result = null;
		try {
			result = session.getMetadata(channelName, "posts");
		} catch (BCIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	

	public List<BCMetaData> searchChannel(String keyword, int max) {
		List<BCMetaData> result = null;
		try {
			result = session.searchChannel(keyword, max);
		} catch (BCIOException e) {
			return null;
			// TODO Auto-generated catch block
		}
		return result;
	}

	public boolean subscribe(String channel) {
		try {
			session.postSubscribe(channel);
		} catch (BCIOException e) {
			// TODO Auto-generated catch block
			return false;
		}
		return true;
	}
	
	private boolean checkNetworkInfo()
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


}
