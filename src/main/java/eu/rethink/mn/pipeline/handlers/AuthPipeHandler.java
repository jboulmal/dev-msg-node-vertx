package eu.rethink.mn.pipeline.handlers;

import java.util.Map;

import javax.naming.InvalidNameException;

import org.pac4j.core.client.Clients;
import org.pac4j.core.profile.UserProfile;
import org.pac4j.oauth.client.BaseOAuth20StateClient;
import org.pac4j.oauth.profile.OAuth20Profile;

import eu.rethink.mn.pipeline.PipeContext;
import eu.rethink.mn.pipeline.message.PipeMessage;
import eu.rethink.mn.pipeline.utils.IDPClientType;
import eu.rethink.mn.pipeline.utils.IDPsClientFactory;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public class AuthPipeHandler implements Handler<PipeContext> {
	public static String NAME = "mn:/authenticator";


	@Override
	public void handle(PipeContext ctx) {
		
		final PipeMessage msg = ctx.getMessage();
		final JsonObject json = msg.getJson();
		final JsonObject body = json.getJsonObject("body"); 
		
		
		if (json.getString("to").equals(eu.rethink.mn.component.SessionManager.name)){
			ctx.next();
		}
		else	
		{
			final String accessToken = body.getString("accessToken");
			if (accessToken == null){
				ctx.fail(NAME, "No mandatory field 'accessToken'");
			}
			
			final JsonObject idToken = body.getJsonObject("idToken");
			if (idToken == null){
				ctx.fail(NAME, "No mandatory field 'idToken'");
			}
				
			final String user_id = idToken.getString("user_id");
			if (user_id == null){
				ctx.fail(NAME, "No mandatory field 'user_id'");
			}
			
			final String email = idToken.getString("email");
			if (email == null){
				ctx.fail(NAME, "No mandatory field 'email'");
			}
			
			final boolean verified = idToken.getBoolean("verified_email");
			if (!verified){
				ctx.fail(NAME, "No mandatory field 'verified_email' or email not verified");
			}
			
			final int expires_in = idToken.getInteger("expires_in");
			if (expires_in<=0){
				ctx.fail(NAME, "Access Token expired");
			}
			
			final String issuedTo = idToken.getString("issued_to");
			if (issuedTo == null){
				ctx.fail(NAME, "No mandatory field 'issuedTo'");
			}
			final String idp = body.getString("idp");
			if (idp == null){
				ctx.fail(NAME, "No mandatory field 'idp'");
			}
			
			
			Clients clients = null;
			try {
				clients = IDPsClientFactory.getIDPClient(IDPClientType.fromString(idp));	
			} catch (InvalidNameException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				@SuppressWarnings("unchecked")
				BaseOAuth20StateClient<OAuth20Profile> clt = (BaseOAuth20StateClient<OAuth20Profile>) clients.findClient(IDPClientType.classString(IDPClientType.fromString(idp)));
				
				final UserProfile profile = clt.getUserProfile(null,accessToken);
			
				Map <String,Object> atributes = profile.getAttributes();
									
				JsonArray email_atribute = new JsonArray(atributes.get("emails").toString());
				JsonObject emails = email_atribute.getJsonObject(0);
			
				if(!emails.getString("value").toString().equals(email)){
					ctx.fail(NAME, "Invalid Email");
				}
				
				if(!profile.getId().equals(user_id)){
					ctx.fail(NAME, "Invalid UserId");
				}

				ctx.next();
			

			} catch (InvalidNameException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	
	}
}