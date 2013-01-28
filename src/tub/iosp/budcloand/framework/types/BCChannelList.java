package tub.iosp.budcloand.framework.types;

import java.util.ArrayList;
import java.util.Iterator;

/*represents a stream of channel content
 */

public class BCChannelList extends BCDatabaseObject{
	
	private static final int OK = 200;
	private static final int CREATED = 201;
	private static final int BAD_REQUEST = 400;
	private static final int UNAUTHORIZED = 401;
	private static final int FORBIDDEN = 403;
	private static final int NOT_FOUND = 404;
		
	ArrayList<BCChannel> listOfChannel;

	
	public BCChannelList(){
		listOfChannel = new ArrayList<BCChannel>();

	}
		
	public ArrayList<BCChannel> getChannelList(){
		return this.listOfChannel;
	}
	
	
	//functionalities
	public void ChannelListGet(){
		//retrieves a channel node's content as Atom feed
		Iterator<BCChannel> it = listOfChannel.iterator();
		BCChannel tempChannel;
        while(it.hasNext()){
        	tempChannel = it.next();
        	tempChannel.channelGet();
        }
	}
	 
	public String ChannelListSet(){
		//Adds an Atom entry to the node. 
		//The created item's URL is returned in the response's Location header
		//TO BE FILLED LATER
		String url = null;
		
		return url;
	}
	
	public void addChannel(BCChannel object){
		listOfChannel.add(object);
	}
	
	public void deleteChannel(int i){
		listOfChannel.remove(i);
	}

}

