package contest.lab.gala.callback;

import java.util.ArrayList;

import contest.lab.gala.data.User;

public interface RequestFriendsCallback {
	public void didGetFriends(ArrayList<User> friends);
}
