package tub.iosp.budcloand.framework.types;

public enum SubscriptionType { 
	OWNER{
		public String toString(){
			return "owner";
		}
	}
	,PUBLISHER{
		public String toString(){
			return "publisher";
		}
	}
	,MEMBER{
		public String toString(){
			return "member";
		}
	}
	
}
