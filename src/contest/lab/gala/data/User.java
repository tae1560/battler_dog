package contest.lab.gala.data;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
	public String id;
	public int character;
//	public int number_of_combo;
	public int number_of_wins;
	public boolean is_logon;
	public int total_wins;
	public int total_loses;
	
	public User(String id, int character, int number_of_wins, boolean is_logon, int total_wins, int total_loses) {
		this.id = id;
		this.character = character;
		this.number_of_wins = number_of_wins;
		this.is_logon = is_logon;
		this.total_wins = total_wins;
		this.total_loses = total_loses;
	}

	public static User parseJson(JSONObject jsonFriend) {
		User user = null;
		try {
			String id = jsonFriend.getString("id");
			int character = jsonFriend.getInt("character");
//			int number_of_combo = jsonFriend.getInt("number_of_combo");
			int number_of_wins = jsonFriend.getInt("number_of_wins");
			boolean is_logon = (jsonFriend.getInt("is_logon") == 1);
			int total_wins = jsonFriend.getInt("total_wins");
			int total_loses = jsonFriend.getInt("total_loses");
			
			user = new User(id, character, number_of_wins, is_logon, total_wins, total_loses);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return user;
	}
}
