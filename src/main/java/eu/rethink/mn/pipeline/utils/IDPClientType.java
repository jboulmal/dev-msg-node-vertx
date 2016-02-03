package eu.rethink.mn.pipeline.utils;

import javax.naming.InvalidNameException;

public enum IDPClientType {
	FACEBOOK("facebook"),
	TWITTER("twitter"),
	GOOGLE_OAUTH("google"),
	GOOGLE_OPENID("openid");
	
	  private String text;

	  IDPClientType(String text) {
	    this.text = text;
	  }

	  public String getText() {
	    return this.text;
	  }
	  
	  public static IDPClientType fromString(String text) {
	    if (text != null) {
	      for (IDPClientType b : IDPClientType.values()) {
	        if (text.equalsIgnoreCase(b.text)) {
	          return b;
	        }
	      }
	    }
	    return null;
	  }
	  
	  public static String classString(IDPClientType type) throws InvalidNameException 
	  	{
		
		 switch(type)
		 	{
		 	case FACEBOOK:
				return "";
			case TWITTER:
				return "";
			case GOOGLE_OAUTH:
				return "Google2Client";
			case GOOGLE_OPENID:
				return "";
			default:
				throw new InvalidNameException("Invalid Client Type");
		 	}
	  	}
}

