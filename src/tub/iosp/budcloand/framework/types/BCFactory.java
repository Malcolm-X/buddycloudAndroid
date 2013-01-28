/*package tub.iosp.budcloand.framework.types;

import tub.iosp.budcloand.json.JsonProcessor;


public class BCFactory {

	
	BCChannel createChannel(JsonProcessor JProcessor){
		//to be initialized from JToken
		String title = JProcessor.getStringFromList(0);
		String description = JProcessor.getStringFromList(1);
		String creation_date = JProcessor.getStringFromList(2);
		String access_model = JProcessor.getStringFromList(3); 
		String channel_type = JProcessor.getStringFromList(4);
		return new BCChannel(title,description,creation_date,access_model,channel_type);
	}

	BCChannelList createChannelList(){
    	return new BCChannelList();
    }

	BCPost createPost(JsonProcessor JProcessor){
    	String id = JProcessor.getStringFromList(0);
    	String author = JProcessor.getStringFromList(1);
    	String published = JProcessor.getStringFromList(2);
    	String updated = JProcessor.getStringFromList(3);
    	String content = JProcessor.getStringFromList(4);
    	String replyTo = JProcessor.getStringFromList(5);
    	return new BCPost(id,author,published,updated,content,replyTo);
    }
	
	BCUserSettings createUserSettings(JsonProcessor JProcessor){
    	  String email = JProcessor.getStringFromList(0);
    	  String postAfterMe = JProcessor.getStringFromList(1);
    	  String postMentionedMe = JProcessor.getStringFromList(2);
    	  String postOnMyChannel = JProcessor.getStringFromList(3);
    	  String postOnSubscribedChannel = JProcessor.getStringFromList(4);
    	  String followMyChannel = JProcessor.getStringFromList(5);
    	  String followRequest = JProcessor.getStringFromList(6);
    	  return new BCUserSettings(email,postAfterMe,postMentionedMe,postOnMyChannel,postOnSubscribedChannel,followMyChannel,followRequest);
    }

	BCFollowerList createSubscriberList(){
    	return new BCFollowerList();
    }
    
	BCSubscriptionList createSubscriptionsUser(){
    	return new BCSubscriptionList();
    }
    
	BCMediaDataList createMediaDataList(){
    	return new BCMediaDataList();
    }
    
    
}
*/