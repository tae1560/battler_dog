package contest.lab.gala.data;

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
}