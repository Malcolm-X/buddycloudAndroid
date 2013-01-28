package tub.iosp.budcloand.framework.helper;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import tub.iosp.budcloand.framework.Http.BCSession;
import tub.iosp.budcloand.framework.Http.DefaultSession;
import tub.iosp.budcloand.framework.exceptions.BCIOException;
import tub.iosp.budcloand.framework.types.BCItem;
import tub.iosp.budcloand.framework.types.BCPost;
import tub.iosp.budcloand.framework.types.BCPostList;
import tub.iosp.budcloand.framework.types.BCSubscriptionList;


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
	
	public BCSubscriptionList getSubscribed(String homeDomain, String userName, String password){
		
		session = new DefaultSession(homeDomain,userName,password);
		
		BCSubscriptionList subList = null;
		
		try{
			subList = session.getSubscribed();
		}catch(Exception e){
			//e.printStackTrace();
			e.printStackTrace();
		}
		
		return subList;
	}
	
	public BCPostList getPosts(String channelName){
		BCPostList list = null;
		try {
			list = session.getPosts(channelName);
		} catch (BCIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public BCPost postPost(String channelName, String content) throws BCIOException{
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
}
