package eu.rethink.mn.pipeline.handlers;

import java.util.Map;
import org.pac4j.core.profile.UserProfile;
import org.pac4j.oauth.client.Google2Client;

import eu.rethink.mn.pipeline.PipeContext;
import eu.rethink.mn.pipeline.message.PipeMessage;
import io.vertx.core.Handler;
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
			final String accessToken = body.getString("access_token");
			if (accessToken == null){
				ctx.fail(NAME, "No mandatory field 'access_token'");
			}
			
			final JsonObject idToken = body.getJsonObject("idToken");
			if (idToken == null){
				ctx.fail(NAME, "No mandatory field 'idToken'");
			}
				
			final String id = idToken.getString("id");
			if (id == null){
				ctx.fail(NAME, "No mandatory field 'id'");
			}
			
			final String email = idToken.getString("email");
			if (email == null){
				ctx.fail(NAME, "No mandatory field 'email'");
			}
			
			final boolean verified = idToken.getBoolean("verified_email");
			if (!verified){
				ctx.fail(NAME, "No mandatory field 'verified_email' or email not verified");
			}
			
			Google2Client client  = new Google2Client("808329566012-tqr8qoh111942gd2kg007t0s8f277roi.apps.googleusercontent.com","Xx4rKucb5ZYTaXlcZX9HLfZW");
			client.setCallbackUrl("http://localhost");

			final UserProfile profile = client.getUserProfile(accessToken);
			
			Map <String,Object> atributes = profile.getAttributes();
			
			if(!atributes.get("email").toString().equals(email)){
				ctx.fail(NAME, "Invalid Email");
			}
			
			if(!profile.getId().equals(id)){
				ctx.fail(NAME, "Invalid UserId");
			}
			ctx.next();
		}
	
	}
}