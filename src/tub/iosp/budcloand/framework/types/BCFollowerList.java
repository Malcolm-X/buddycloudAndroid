package tub.iosp.budcloand.framework.types;

import java.util.ArrayList;
import java.util.Iterator;

/*
 * List of subscribers of a certain channel node
 * 
 */
public class BCFollowerList {
	
	ArrayList<BCSubscription> subList;
	
	public BCFollowerList(){
		subList = new ArrayList<BCSubscription>();
	}
	
	public ArrayList<BCSubscription> getSubList(){
		return subList;
	}
	
	public void subscriptionsUserGet(){
		//Retrieves the list 
		Iterator<BCSubscription> it = subList.iterator();
		BCSubscription tempSub;
		while(it.hasNext()){
			tempSub = it.next();
			/*System.out.println("user name : " + tempSub.getUserName());
			System.out.println("member type : " + tempSub.getMemberType());		*/
		}
		
	}
	
	public void deleteSubs(int i){
		subList.remove(i);
	}
	
	public void addSubs(BCSubscription s){
		subList.add(s);
	}
	
	
}
