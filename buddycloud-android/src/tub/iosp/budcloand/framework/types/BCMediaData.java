package tub.iosp.budcloand.framework.types;

public class BCMediaData  {
    String id;
    String fileName; //required the uploaded filename (including extension)
    String author; //required the buddycloudID of the author
    String title; //optional media title
    String mimeType; //required mimetype
    String description; //optional media description
    String fileExtension;
    String shaChecksum; //required SHA1 file checksum
    int fileSize;
    int height; //server-set height of the uploaded image or video. calculated by the server and not editable
    int width; //server-set width of the uploaded image or video. calculated by the server and not editable.
    String entityId; // required the buddycloudID of the channel or user that owns the media
    
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
    public void setId(String id){
    	this.id = id;
    }
    public void setFileName(String fileName){
    	this.fileName = fileName;
    }
    public void setAuthor(String author){
    	this.author = author;
    }
    public void setMimeType(String mimeType){
    	this.mimeType = mimeType;
    }
    public void setShaChecksum(String sha){
    	this.shaChecksum = sha;
    }
    public void setEntityId(String entityId){
    	this.entityId = entityId;
    }
    public void setTitle(String title){
    	this.title = title;
    }
    public void setDescription(String description){
    	this.description = description;
    }
  
    //getters
    public String getId(){ return this.id;}
    public String getFileName(){ return this.fileName; }
    public String getAuthor(){ return this.author;}
    public String getTitle(){return this.title;}
    public String getMimeType(){ return this.mimeType;} 
    public String getDescription(){ return this.description;} 
    public String getFileExtension(){ return this.fileExtension;}
    public String getShaChecksum(){ return this.shaChecksum; }
    public int getFileSize() {return this.fileSize;}
    public int getHeight() {return this.height;} 
    public int getWidth(){ return this.width;} 
    public String getEntityId(){return this.entityId;}
    
    public BCMediaData(){
    	
    }
    
    //functionalities
    public void mediaDataGet(){
    	System.out.println("here is media data GET");
    }
    
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
    
    public void mediaDataDelete(){
    	System.out.println("here is media data DELETE");
    }
    
    
	public enum FeatureOfMediaData{
		ID,FILE_NAME,AUTHOR,MIME_TYPE,SHA,ENTITY_ID,TITLE,DESCRIPTION;
		public static FeatureOfMediaData getFeature(String feature){
			return valueOf(feature.toUpperCase());
		}
	}
    
}