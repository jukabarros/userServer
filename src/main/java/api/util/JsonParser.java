package api.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.json.JSONObject;

import api.model.User;

public class JsonParser {
	
	public User jsonToUser(String json) throws ParseException{
		User user = new User();
		
		JSONObject jsonObj = new JSONObject(json);
		
//		user.setId(jsonObj.getInt("id"));
		user.setName(jsonObj.getString("name"));
		user.setGender(jsonObj.getString("gender"));
		
		String dateOfBirthSTR = jsonObj.getString("dateOfBirth");
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		user.setDateOfBirth(sdf.parse(dateOfBirthSTR));
		
		return user;
	}

}
