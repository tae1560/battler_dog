package contest.lab.gala.data;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
	public String id;
	public int character;
//	public int number_of_combo;
	public int number_of_wins;
	public boolean is_logon;
	
	public User(String id, int character, int number_of_wins, boolean is_logon) {
		this.id = id;
		this.character = character;
		this.number_of_wins = number_of_wins;
		this.is_logon = is_logon;
	}
	
	public static User parseJson(JSONObject jsonFriend) {
		User user = null;
		try {
			String id = jsonFriend.getString("id");
			int character = jsonFriend.getInt("character");
//			int number_of_combo = jsonFriend.getInt("number_of_combo");
			int number_of_wins = jsonFriend.getInt("number_of_wins");
			boolean is_logon = (jsonFriend.getInt("is_logon") == 1);
			
			user = new User(id, character, number_of_wins, is_logon);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return user;
	}
}
