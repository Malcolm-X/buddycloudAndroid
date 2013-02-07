package tub.iosp.budcloand.framework.types;

public class BCUserSettings {
    String email;
    boolean postAfterMe;
    boolean postMentionedMe;
    boolean postOnMyChannel;
    boolean postOnSubscribedChannel;
    boolean followMyChannel;
    boolean followRequest;
    
    
    public BCUserSettings(){
    }
    
    public BCUserSettings(String em,String pam,String pmm,String poc,String posc,String fmc,String fr){
   	   this.setEmail(em);
   	   this.setPostAfterMe(pam);
   	   this.setPostMentionedMe(pmm);
   	   this.setPostOnMyChannel(poc);
   	   this.setPostOnSubscribedChannel(posc);
   	   this.setFollowMyChannel(fmc);
   	   this.setFollowRequest(fr);
    }
    
    //setters
    public void setEmail(String str){
   	 this.email = str;
    }
    
    public void setPostAfterMe(String s){
   	 this.postAfterMe = (s.equalsIgnoreCase("true")) ? true : false;
    }
    
    public void setPostMentionedMe(String s){
   	 this.postMentionedMe = (s.equalsIgnoreCase("true")) ? true : false;
    }
    
    public void setPostOnMyChannel(String s){
   	 this.postOnMyChannel = (s.equalsIgnoreCase("true")) ? true : false;
    }
    
    public void setPostOnSubscribedChannel(String s){
   	 this.postOnSubscribedChannel = (s.equalsIgnoreCase("true")) ? true : false;
    }
    
    public void setFollowMyChannel(String s){
   	 this.followMyChannel = (s.equalsIgnoreCase("true")) ? true : false;
    }
    
    public void setFollowRequest(String s){
   	 this.followRequest = (s.equalsIgnoreCase("true")) ? true : false;
    }
    
    //getters
    public String getEmail(){
   	 return this.email;
    }
    
    public boolean getPostAfterMe(){
   	 return this.postAfterMe;
    }
    
    public boolean getPostMentionedMe(){
   	 return this.postMentionedMe;
    }
    
    public boolean getPostOnMyChannel(){
   	 return this.postOnMyChannel;
    }
    
    public boolean getPostOnSubscribedChannel(){
   	 return this.postOnSubscribedChannel;
    }
    
    public boolean getFollowMyChannel(){
   	 return this.followMyChannel;
    }
    
    public boolean getFollowRequest(){
   	 return this.followRequest;
    }
    
    //functionalities
    public void userSettingGet(){
   	 //retrieves the user's own email notification settings
   	System.out.println("email : " + this.email);
		System.out.println("postAfterMe : " + this.postAfterMe);
		System.out.println("postMentionedMe : " + this.postMentionedMe);
		System.out.println("postOnMyChannel : " + this.postOnMyChannel);
		System.out.println("postOnSubscribedChannel : " + this.postOnSubscribedChannel);
		System.out.println("followMyChannel : " + this.followMyChannel);
		System.out.println("followRequest : " + this.followRequest);
    }
    
    public void userSettingPost(String feature,String newValue){
   	 //updates the user's own email notification settings
   		switch(FeatureOfChannel.getFeature(feature)){
   		case EMAIL:
            System.out.println("updates EMAIL");  
            this.setEmail(newValue);
   			break;
   		case POST_AFTER_ME:
            System.out.println("updates POST_AFTER_ME");  
            this.setPostAfterMe(newValue);
   			break;
   		case POST_MENTIONED_ME:
            System.out.println("updates POST_MENTIONED_ME");  
            this.setPostMentionedMe(newValue);
   			break;
   		case POST_ON_MY_CHANNEL:
            System.out.println("updates POST_ON_MY_CHANNEL");  
            this.setPostOnMyChannel(newValue);
   			break;
   		case POST_ON_SUBSCRIBED_CHANNEL:
            System.out.println("updates POST_ON_SUBSCRIBED_CHANNEL");  
            this.setPostOnSubscribedChannel(newValue);
   			break;
   		case FOLLOW_MY_CHANNEL:
               System.out.println("updates FOLLOW_MY_CHANNEL");  
               this.setFollowMyChannel(newValue);
      			break;
   		case FOLLOW_REQUEST:
               System.out.println("updates FOLLOW_REQUEST");  
               this.setFollowRequest(newValue);
      			break;
   		default:
           System.out.println("wrong feature");  	
   		}
    }
		
	
	
	public enum FeatureOfChannel{
		EMAIL,POST_AFTER_ME,POST_MENTIONED_ME,POST_ON_MY_CHANNEL,POST_ON_SUBSCRIBED_CHANNEL,FOLLOW_MY_CHANNEL,FOLLOW_REQUEST;
		
		public static FeatureOfChannel getFeature(String feature){
			return valueOf(feature.toUpperCase());
		}
	}
}
