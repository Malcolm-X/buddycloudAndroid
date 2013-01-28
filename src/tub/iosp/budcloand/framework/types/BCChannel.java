package tub.iosp.budcloand.framework.types;

import java.util.ArrayList;

/*
 * Model of a channel node
 */
public class BCChannel extends BCDatabaseObject {
	String channelAddress;
	BCMetaData metaData;
	
	ArrayList<BCSubscription> followerList;
	ArrayList<BCSubscription> subscriptionList;
	
	ArrayList<BCMediaData> mediaDataList;
	BCUser user;
	BCChannelAvatar avatar;
	
	public BCChannel(String channelAddress){
		this.channelAddress = channelAddress;
		
		followerList = new ArrayList<BCSubscription>();
		subscriptionList = new ArrayList<BCSubscription>();
		
		mediaDataList = null;
		user = null;
		avatar = null;
	}
	

	public BCChannel(){
		//create a channel with default configuration
	}
	
	public void addFollowers(BCSubscription newFollower){
		this.followerList.add(newFollower);
	}
	
	public void addSubscription(BCSubscription newSub){
		this.subscriptionList.add(newSub);
	}
	//setters
	
	
	//functionalities
	public void channelGet(){
		//retrieves values in the channel node's metadata
		//the following part to be remove later
		/*System.out.println("title : " + this.title);
		System.out.println("channel_type : " + this.channel_type);
		System.out.println("creation_date : " + this.creation_date);
		System.out.println("description : " + this.description);
		System.out.println("access_model : " + this.access_model);*/
	}
	
	/*public void channelPost(String feature,String newValue){
		//updates values in the channel node's metadata
		switch(FeatureOfChannel.getFeature(feature)){
		case TITLE:
            System.out.println("updates TITLE");  
            this.setTitle(newValue);
			break;
		case CHANNEL_TYPE:
            System.out.println("updates CHANNEL_TYPE");  
            this.setChannelType(newValue);
			break;
		case CREATION_DATE:
            System.out.println("updates CREATION_DATE");  
            this.setCreationDate(newValue);
			break;
		case DESCRIPTION:
            System.out.println("updates DESCRIPTION");  
            this.setDescription(newValue);
			break;
		case ACCESS_MODEL:
            System.out.println("updates ACCESS_MODEL");  
            this.setAccessModel(newValue);
			break;
		default:
           System.out.println("wrong feature");  	
		}
		
	}*/
	
	/*public enum FeatureOfChannel{
		TITLE,CHANNEL_TYPE,CREATION_DATE,DESCRIPTION,ACCESS_MODEL;
		public static FeatureOfChannel getFeature(String feature){
			return valueOf(feature.toUpperCase());
		}
	}*/
	
}
