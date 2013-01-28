package tub.iosp.budcloand.framework.types;

import java.util.ArrayList;
import java.util.Iterator;

public class BCMediaDataList{
	
	ArrayList<BCMediaData> mediaList;
	
	public BCMediaDataList(){
		mediaList = new ArrayList<BCMediaData>();
	}

	public ArrayList<BCMediaData> getMediaList(){
		return mediaList;
	}
	
	public void addMedias(BCMediaData md){
		mediaList.add(md);
	}
	
	public void deleteMedias(int i){
		mediaList.remove(i);
	}
	
	//functionalities
	public void mediaDataListGet(){
		//get all media metadata information from the specified channel
		Iterator<BCMediaData> it = mediaList.iterator();
		BCMediaData temp;
		while(it.hasNext()){
			temp = it.next();
			temp.mediaDataGet();		
		}
	}
	
	public void mediaDataListPost(){
		//uploads a media file to the specific channel
		BCMediaData md = new BCMediaData();
		addMedias(md);
	}

	
	
}
