package tub.iosp.budcloand.framework.model;

// TODO: Auto-generated Javadoc
/**
 * The Class BCUserSettings represents a set of settings stored at the buddycloud server for a user
 * This Class is not relevant for the current project but is left here for later use.
 */
public class BCUserSettings {
    
    /** The email. */
    String email;
    
    /** The post after me. */
    boolean postAfterMe;
    
    /** The post mentioned me. */
    boolean postMentionedMe;
    
    /** The post on my channel. */
    boolean postOnMyChannel;
    
    /** The post on subscribed channel. */
    boolean postOnSubscribedChannel;
    
    /** The follow my channel. */
    boolean followMyChannel;
    
    /** The follow request. */
    boolean followRequest;
    
    
    /**
     * Instantiates a new bC user settings.
     */
    public BCUserSettings(){
    }
    
    /**
     * Instantiates a new bC user settings.
     *
     * @param em the email
     * @param pam the "posts after me" attribute
     * @param pmm the "posts mentioning me" attribute
     * @param poc the "posts on my channel" attribute
     * @param posc the "posts on subscribed channels" attribute
     * @param fmc the "follow my channel" attribute
     * @param fr the "follow requests" attribute
     */
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
    /**
     * Sets the email.
     *
     * @param str the new email
     */
    public void setEmail(String str){
   	 this.email = str;
    }
    
    /**
     * Sets the post after me.
     *
     * @param s the new post after me
     */
    public void setPostAfterMe(String s){
   	 this.postAfterMe = (s.equalsIgnoreCase("true")) ? true : false;
    }
    
    /**
     * Sets the post mentioned me.
     *
     * @param s the new post mentioned me
     */
    public void setPostMentionedMe(String s){
   	 this.postMentionedMe = (s.equalsIgnoreCase("true")) ? true : false;
    }
    
    /**
     * Sets the post on my channel.
     *
     * @param s the new post on my channel
     */
    public void setPostOnMyChannel(String s){
   	 this.postOnMyChannel = (s.equalsIgnoreCase("true")) ? true : false;
    }
    
    /**
     * Sets the post on subscribed channel.
     *
     * @param s the new post on subscribed channel
     */
    public void setPostOnSubscribedChannel(String s){
   	 this.postOnSubscribedChannel = (s.equalsIgnoreCase("true")) ? true : false;
    }
    
    /**
     * Sets the follow my channel.
     *
     * @param s the new follow my channel
     */
    public void setFollowMyChannel(String s){
   	 this.followMyChannel = (s.equalsIgnoreCase("true")) ? true : false;
    }
    
    /**
     * Sets the follow request.
     *
     * @param s the new follow request
     */
    public void setFollowRequest(String s){
   	 this.followRequest = (s.equalsIgnoreCase("true")) ? true : false;
    }
    
    //getters
    /**
     * Gets the email.
     *
     * @return the email
     */
    public String getEmail(){
   	 return this.email;
    }
    
    /**
     * Gets the post after me.
     *
     * @return the post after me
     */
    public boolean getPostAfterMe(){
   	 return this.postAfterMe;
    }
    
    /**
     * Gets the post mentioned me.
     *
     * @return the post mentioned me
     */
    public boolean getPostMentionedMe(){
   	 return this.postMentionedMe;
    }
    
    /**
     * Gets the post on my channel.
     *
     * @return the post on my channel
     */
    public boolean getPostOnMyChannel(){
   	 return this.postOnMyChannel;
    }
    
    /**
     * Gets the post on subscribed channel.
     *
     * @return the post on subscribed channel
     */
    public boolean getPostOnSubscribedChannel(){
   	 return this.postOnSubscribedChannel;
    }
    
    /**
     * Gets the follow my channel.
     *
     * @return the follow my channel
     */
    public boolean getFollowMyChannel(){
   	 return this.followMyChannel;
    }
    
    /**
     * Gets the follow request.
     *
     * @return the follow request
     */
    public boolean getFollowRequest(){
   	 return this.followRequest;
    }
    
    //functionalities
    /**
     * User setting get.
     */
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
    
    /**
     * User setting post.
     *
     * @param feature the feature
     * @param newValue the new value
     */
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
		
	
	
	/**
	 * The Enum FeatureOfChannel.
	 */
	public enum FeatureOfChannel{
		
		/** The email. */
		EMAIL,
/** The post after me. */
POST_AFTER_ME,
/** The post mentioned me. */
POST_MENTIONED_ME,
/** The post on my channel. */
POST_ON_MY_CHANNEL,
/** The post on subscribed channel. */
POST_ON_SUBSCRIBED_CHANNEL,
/** The follow my channel. */
FOLLOW_MY_CHANNEL,
/** The follow request. */
FOLLOW_REQUEST;
		
		/**
		 * Gets the feature.
		 *
		 * @param feature the feature
		 * @return the feature
		 */
		public static FeatureOfChannel getFeature(String feature){
			return valueOf(feature.toUpperCase());
		}
	}
}
