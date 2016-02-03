package eu.rethink.mn.pipeline.utils;

import java.util.Map;

import javax.naming.InvalidNameException;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

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
				return "FacebookClient";
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
	  public static String getEmail(IDPClientType type, Map <String,Object> atributes) throws InvalidNameException 
	  	{
		
		 switch(type)
		 	{
		 	case FACEBOOK:
				return atributes.get("email").toString();
			case TWITTER:
				return "";
			case GOOGLE_OAUTH:
				JsonArray email_atribute = new JsonArray(atributes.get("emails").toString());
				JsonObject emails = email_atribute.getJsonObject(0);
				return emails.getString("value").toString();
			case GOOGLE_OPENID:
				return "";
			default:
				throw new InvalidNameException("Invalid Client Type");
		 	}
	  	}
	  
	  
}

