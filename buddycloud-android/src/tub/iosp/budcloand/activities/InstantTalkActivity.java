package tub.iosp.budcloand.activities;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;

import tub.iosp.budcloand.R;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * The Class InstantTalkActivity.
 *
 * @Description activity of online instant talk
 */
public class InstantTalkActivity extends Activity{
	
	/** The handler. */
	Handler handler;
	
	/** The Constant UI_UPDATE. */
	public static final int UI_UPDATE = 12345;
	
	/** The xmpp host. */
	String xmppHost = "crater.buddycloud.org";
	
	/** The xmpp port. */
	String xmppPort = "5222";
	
	/** The xmpp service. */
	String xmppService = "buddycloud.org";
	
	/** The username. */
	String username ; 
	
	/** The password. */
	String password ;
	
	/** The target user. */
	String targetUser ;
	
	/** The info text. */
	TextView infoText;
	
	/** The send button. */
	Button sendButton;
	
	/** The close button. */
	Button closeButton;
	
	/** The send text. */
	EditText sendText;
	
	/** The text to send. */
	String textToSend;
	
	/** The conn config. */
	ConnectionConfiguration connConfig;
	
	/** The xmpp connection. */
	XMPPConnection xmppConnection;
	
	/** The chat manager. */
	ChatManager chatManager;
	
	/** The chat. */
	Chat chat;
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.instant_talk);

		sendButton = (Button)findViewById(R.id.sendButton);
		closeButton = (Button)findViewById(R.id.closeButton);
		infoText = (TextView)findViewById(R.id.infoText);
		sendText = (EditText)findViewById(R.id.inputText);
		
		Bundle bundle = this.getIntent().getExtras();
		String temp = bundle.getString("username");
		username = processPassInName(temp);
		password = bundle.getString("password");
		targetUser = bundle.getString("targetUser");
		
		handler = new Handler(){
			@Override
			public void handleMessage(android.os.Message msg){
				switch(msg.what){
				case UI_UPDATE:
					String str = msg.obj.toString();
					infoText.append(str + "\n");
					break;
					default:
						break;
						
				}
				super.handleMessage(msg);
			}
		};
		
		closeButton.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					onCloseButton();
				}});
		
	    sendButton.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
//					System.out.println("text to send : " + textToSend);
					textToSend = sendText.getEditableText().toString();	
					try{
						infoText.append(username + " : " + textToSend + "\n");
//						chat.sendMessage("hello world");
						sendText.setText("");
						chat.sendMessage(textToSend);
					}catch(XMPPException e){
						e.printStackTrace();
					}
				}});
	       
		//create a connection
		connConfig = new ConnectionConfiguration(xmppHost, Integer.parseInt(xmppPort),xmppService);
		connConfig.setSecurityMode(ConnectionConfiguration.SecurityMode.enabled); 
		connConfig.setSASLAuthenticationEnabled(true);
		connConfig .setTruststorePath("/system/etc/security/cacerts.bks"); 
		connConfig.setTruststorePassword("changeit");
		connConfig.setTruststoreType("bks");   
		xmppConnection = new XMPPConnection(connConfig);
		
		try{
			System.out.println("#### : start to connect");
			xmppConnection.connect();
			System.out.println("#### : connected to " + xmppConnection.getHost());
		}catch(XMPPException e){
			System.out.println("#### : failed to connect");
			e.printStackTrace();
		}
			
		//login
      try {
    	  xmppConnection.login(username, password);
    	  System.out.println("#### : logged as " + xmppConnection.getUser());
//	      Set the status to available
     	  Presence presence = new Presence(Presence.Type.available);
     	  xmppConnection.sendPacket(presence);
     	  
    	  chatManager = xmppConnection.getChatManager();
    	  chat = chatManager.createChat(targetUser, null);
    	  
    	  //add Listener for chat
    	  chatManager.addChatListener(new ChatManagerListener(){
			@Override
			public void chatCreated(Chat chat, boolean create) {
				chat.addMessageListener(new MessageListener(){
					@Override
					public void processMessage(Chat chat, Message msg) {
						final String receiveInfo = processPassInName(chat.getParticipant())  + " : " + msg.getBody();
						System.out.println(receiveInfo);
						
						Thread setTextThread = new Thread(){
							@Override
							public void run(){
								try{
									android.os.Message mMessage = new android.os.Message();
									mMessage.what = UI_UPDATE;
									mMessage.obj = receiveInfo;
									handler.sendMessage(mMessage);
								} catch (Exception e) {
                                    e.printStackTrace();
                                }
							}
							
						};
						setTextThread.start();			
//						infoText.append(processPassInParticipantName(chat.getParticipant()) + " : " + msg.getBody() + "\n");	
					}
				});
			}
    	  });

      } catch (XMPPException e) {
    	  System.out.println("#### : failed to loggin ");
    	  e.printStackTrace();
      }

	}

	/**
	 * On close button.
	 */
	public void onCloseButton(){
		System.out.println("close button is clicked");
		//disconnect the chat
		if(xmppConnection.isConnected()){
			String str = "finish disconnecting xmppConnection";
//  	    Set the status to unavailable
  	        Presence presence = new Presence(Presence.Type.unavailable);
  	        xmppConnection.sendPacket(presence);
			xmppConnection.disconnect(); 
			System.out.println(str);
			infoText.append(str);
		}
		//close the activity,switch back to the BuddycloaAnd Application
		finish();
		
	}
	
	/**
	 * Process pass in name.
	 *
	 * @param name the name
	 * @return the string
	 */
	public String processPassInName(String name){
		String ret = name.substring(0, name.indexOf("@"));
		return ret;
	}

}