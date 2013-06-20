package tub.iosp.budcloand.framework.model;

import java.util.Locale;

// TODO: Auto-generated Javadoc
/**
 * The Class BCMediaData. This class represents a set of mediadata used 
 * by the buddycloud server to describe media features.
 * This class is currently not used in the project and is only
 * present for later support for later support of buddycloud mediaservers
 */
public class BCMediaData  {
    
    /** The local id in the database. */
    String id;
    
    /** The file name. */
    String fileName; //required the uploaded filename (including extension)
    
    /** The author's jid. */
    String author; //required the buddycloudID of the author
    
    /** The title. */
    String title; //optional media title
    
    /** The mime type. */
    String mimeType; //required mimetype
    
    /** The description. */
    String description; //optional media description
    
    /** The file extension. */
    String fileExtension;
    
    /** The sha checksum. */
    String shaChecksum; //required SHA1 file checksum
    
    /** The file size. */
    int fileSize;
    
    /** The height. */
    int height; //server-set height of the uploaded image or video. calculated by the server and not editable
    
    /** The width. */
    int width; //server-set width of the uploaded image or video. calculated by the server and not editable.
    
    /** The entity id. */
    String entityId; // required the buddycloudID of the channel or user that owns the media
    
    /**
     * Instantiates a new bC media data.
     *
     * @param id the database id
     * @param fileName the file name
     * @param author the author
     * @param mimeType the mime type
     * @param shaChecksum the sha checksum
     * @param height the height
     * @param width the width
     * @param entityId the entity id
     */
    public BCMediaData(String id,String fileName,String author,String mimeType,String shaChecksum,int height,int width,String entityId){
    	  this.id = id;
    	  this.fileName = fileName; 
    	  this.author = author;
    	  this.mimeType = mimeType;
    	  this.shaChecksum = shaChecksum; 
    	  this.height = height; 
    	  this.width = width;
    	  this.entityId = entityId; 
    }
    
    //setters
    /**
     * Sets the databse id.
     *
     * @param id the new id
     */
    public void setId(String id){
    	this.id = id;
    }
    
    /**
     * Sets the file name.
     *
     * @param fileName the new file name
     */
    public void setFileName(String fileName){
    	this.fileName = fileName;
    }
    
    /**
     * Sets the author.
     *
     * @param author the new author
     */
    public void setAuthor(String author){
    	this.author = author;
    }
    
    /**
     * Sets the mime type.
     *
     * @param mimeType the new mime type
     */
    public void setMimeType(String mimeType){
    	this.mimeType = mimeType;
    }
    
    /**
     * Sets the sha checksum.
     *
     * @param sha the new sha checksum
     */
    public void setShaChecksum(String sha){
    	this.shaChecksum = sha;
    }
    
    /**
     * Sets the entity id.
     *
     * @param entityId the new entity id
     */
    public void setEntityId(String entityId){
    	this.entityId = entityId;
    }
    
    /**
     * Sets the title.
     *
     * @param title the new title
     */
    public void setTitle(String title){
    	this.title = title;
    }
    
    /**
     * Sets the description.
     *
     * @param description the new description
     */
    public void setDescription(String description){
    	this.description = description;
    }
  
    /**
     * Gets the databse id.
     *
     * @return the id
     */
    public String getId(){ return this.id;}
    
    /**
     * Gets the file name.
     *
     * @return the file name
     */
    public String getFileName(){ return this.fileName; }
    
    /**
     * Gets the author.
     *
     * @return the author
     */
    public String getAuthor(){ return this.author;}
    
    /**
     * Gets the title.
     *
     * @return the title
     */
    public String getTitle(){return this.title;}
    
    /**
     * Gets the mime type.
     *
     * @return the mime type
     */
    public String getMimeType(){ return this.mimeType;} 
    
    /**
     * Gets the description.
     *
     * @return the description
     */
    public String getDescription(){ return this.description;} 
    
    /**
     * Gets the file extension.
     *
     * @return the file extension
     */
    public String getFileExtension(){ return this.fileExtension;}
    
    /**
     * Gets the sha checksum.
     *
     * @return the sha checksum
     */
    public String getShaChecksum(){ return this.shaChecksum; }
    
    /**
     * Gets the file size.
     *
     * @return the file size
     */
    public int getFileSize() {return this.fileSize;}
    
    /**
     * Gets the height.
     *
     * @return the height
     */
    public int getHeight() {return this.height;} 
    
    /**
     * Gets the width.
     *
     * @return the width
     */
    public int getWidth(){ return this.width;} 
    
    /**
     * Gets the entity id.
     *
     * @return the entity id
     */
    public String getEntityId(){return this.entityId;}
    
    /**
     * Instantiates a new bC media data.
     */
    public BCMediaData(){
    	
    }
    
    //functionalities
    /**
     * Get the mediadata.
     */
    public void mediaDataGet(){
    	System.out.println("here is media data GET");
    }
    
    /**
     * Change a feature of the media.
     *
     * @param feature the featurename
     * @param newValue the new value
     */
    public void mediaDataPost(String feature, String newValue){
    	System.out.println("here is media data POST");
		switch(FeatureOfMediaData.getFeature(feature)){
		case ID:
            System.out.println("updates ID");  
            this.setId(newValue);
			break;
		case FILE_NAME:
            System.out.println("updates FILE_NAME");  
            this.setFileName(newValue);
			break;
		case AUTHOR:
            System.out.println("updates AUTHOR");  
            this.setAuthor(newValue);
			break;
		case MIME_TYPE:
            System.out.println("updates MIME_TYPE");  
            this.setMimeType(newValue);
			break;
		case SHA:
            System.out.println("updates SHA");  
            this.setShaChecksum(newValue);
			break;
		case ENTITY_ID:
            System.out.println("updates ENTITY_ID");  
            this.setEntityId(newValue);
			break;
		case TITLE:
            System.out.println("updates TITLE");  
            this.setTitle(newValue);
			break;
		case DESCRIPTION:
            System.out.println("updates DESCRIPTION");  
            this.setDescription(newValue);
			break;
		default:
           System.out.println("wrong feature");  	
		}
    	
    }
    
    /**
     * delete mediadata.
     */
    public void mediaDataDelete(){
    	System.out.println("here is media data DELETE");
    }
    
    
	/**
	 * The features of mediadatas.
	 */
	public enum FeatureOfMediaData{
		
		/** The id. */
		ID,
/** The file name. */
FILE_NAME,
/** The author. */
AUTHOR,
/** The mime type. */
MIME_TYPE,
/** The sha. */
SHA,
/** The entity id. */
ENTITY_ID,
/** The title. */
TITLE,
/** The description. */
DESCRIPTION;
		
		/**
		 * Gets the feature.
		 *
		 * @param feature the featurename
		 * @return the feature
		 */
		public static FeatureOfMediaData getFeature(String feature){
			return valueOf(feature.toUpperCase(Locale.getDefault()));
		}
	}
    
}