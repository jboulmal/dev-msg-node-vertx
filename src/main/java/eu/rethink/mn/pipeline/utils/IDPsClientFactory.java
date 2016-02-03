package eu.rethink.mn.pipeline.utils;




import javax.naming.InvalidNameException;

import org.pac4j.core.client.Clients;
import org.pac4j.oauth.client.FacebookClient;
import org.pac4j.oauth.client.Google2Client;
import org.pac4j.oauth.client.TwitterClient;
import org.pac4j.oidc.client.GoogleOidcClient;


public class IDPsClientFactory {
	final static String key_google = "808329566012-tqr8qoh111942gd2kg007t0s8f277roi.apps.googleusercontent.com";
	final static String key_facebook = "";
	final static String key_twitter = "";
	final static String key_openid = "";
	final static String secret_google = "Xx4rKucb5ZYTaXlcZX9HLfZW";
	final static String secret_facebook = "";
	final static String secret_twitter = "";
	final static String secret_openid = "";
	
	public static Clients getIDPClient(IDPClientType type) throws InvalidNameException
		{
		
		switch(type)
			{
			case FACEBOOK:
				FacebookClient clt = new FacebookClient(key_facebook, secret_facebook);
				return new Clients("http://localhost:8080/callback", clt);
			case TWITTER:
				TwitterClient clt1 = new TwitterClient(key_twitter, secret_twitter);
				return new Clients("http://localhost:8080/callback", clt1);
			case GOOGLE_OAUTH:
				Google2Client clt2 = new Google2Client(key_google,secret_google);
				return new Clients("http://localhost:8080/callback", clt2);
			case GOOGLE_OPENID:
				GoogleOidcClient clt3 = new GoogleOidcClient();
				clt3.setClientID(key_openid);
				clt3.setSecret(secret_openid);
				clt3.setCallbackUrl("http://localhost:8080/callback");
				return new Clients("http://localhost:8080/callback", clt3);
			default:
				throw new InvalidNameException("Invalid Client Type");
			}
		}
		
	
	
	/* Digital Entities
	 * 
	 * 
	 * public static Clients getIDPClients(List<IDPClientType> types, List<String> keys, List<String> secrets) throws InvalidNameException
		{
		
		ListIterator<IDPClientType> it = types.listIterator();
		int index = 0;
		Clients clts = null;
		while(it.hasNext())
			{
			
			switch(it.next())
				{
				case FACEBOOK:
					FacebookClient clt = new FacebookClient(keys.get(index), secrets.get(index));
					if(index == 0)
						clts = new Clients("http://localhost:8080/callback", clt);
					else
						{
						clts.getClients().add(clt);
						}
				break;
				case TWITTER:
					TwitterClient clt1 = new TwitterClient(keys.get(index), secrets.get(index));
					return new Clients("http://localhost:8080/callback", clt1);
				case GOOGLE_OAUTH:
					Google2Client clt2 = new Google2Client(keys.get(index), secrets.get(index));
					if(index == 0)
						clts = new Clients("http://localhost:8080/callback", clt2);
					else
						{
						clts.getClients().add(clt2);
						}
				case GOOGLE_OPENID:
					GoogleOidcClient clt3 = new GoogleOidcClient();
					clt3.setClientID(keys.get(index));
					clt3.setSecret(secrets.get(index));
					clt3.setCallbackUrl("http://localhost:8080/callback");
					if(index == 0)
						clts = new Clients("http://localhost:8080/callback", clt3);
					else
						{
						clts.getClients().add(clt3);
						}
				default:
					throw new InvalidNameException("Invalid Client Type");
				}
			++index;
			}
		return clts;
			
		}
		*/
	
	}
