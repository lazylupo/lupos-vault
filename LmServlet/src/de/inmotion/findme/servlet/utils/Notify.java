package de.inmotion.findme.servlet.utils;

import org.codehaus.jackson.map.ObjectMapper;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

public class Notify {
	
	
	public static final String FOUND = "100";
	public static final String ACCEPTED = "200";
	public static final String REJECTED = "300";
	
	public static void notifyViaGCM(String registrationID, String code, String strMessage, Object obj, String collapseKey ) {
		
		ObjectMapper mapper = new ObjectMapper();

		try {
			
			String strObj = mapper.writeValueAsString(obj);
			System.out.println(obj);
			Sender sender = new Sender("AIzaSyCHN2RjeOEwhFiE_YaJ7s6jDyZ_paTEmKU");


			Message message = new Message.Builder()
					.collapseKey(collapseKey)
					//.timeToLive(3)
					.delayWhileIdle(false)
					.addData("code", code)
					.addData("message", strMessage)
					.addData("data", strObj)
					.build();

			@SuppressWarnings("unused")
			Result result = sender.send(message, registrationID, 1);


		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
