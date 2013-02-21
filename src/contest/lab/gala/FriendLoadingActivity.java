package contest.lab.gala;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import contest.lab.gala.callback.RequestFriendsCallback;
import contest.lab.gala.data.User;


public class FriendLoadingActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loading);
		
		NetworkManager.getInstance().requestFriends(new RequestFriendsCallback() {

			@Override
			public void didGetFriends(ArrayList<User> friends) {
//				Manager.friendList = (ArrayList<User>) friends.clone();
				Manager.friendList = friends;
				Intent intent = new Intent(FriendLoadingActivity.this, BattlerDogActivity.class);
				startActivity(intent);
				finish();
			}
		});
	}
}
