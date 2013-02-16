package contest.lab.gala;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import contest.lab.gala.callback.LoginCallback;
import contest.lab.gala.callback.RequestFriendsCallback;
import contest.lab.gala.data.CurrentUserInformation;
import contest.lab.gala.data.User;


public class LoginActivity extends Activity implements LoginCallback{

	EditText et_id;
	EditText et_pw;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		super.onStart();
	}
	@Override
	protected void onStart() {

		ImageButton btn_login = (ImageButton)findViewById(R.id.btn_login);
		et_id = (EditText)findViewById(R.id.userIDEntry);
		et_pw = (EditText)findViewById(R.id.passwordEntry);
		
		btn_login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				NetworkManager.getInstance().doLogin(et_id.getText().toString(), et_pw.getText().toString(), LoginActivity.this);
			}
		});
		super.onStart();
	}
	@Override
	public void didSuccessLogin(User user) {
		CurrentUserInformation.userID = user.id;
		CurrentUserInformation.userChar = user.character;
		NetworkManager.getInstance().requestFriends(new RequestFriendsCallback() {

			@Override
			public void didGetFriends(ArrayList<User> friends) {
				Manager.friendList = (ArrayList<User>) friends.clone();
				Intent intent = new Intent(LoginActivity.this, BattlerDogActivity.class);
				startActivity(intent);
			}
		});
//		NetworkManager.getInstance().requestRandomMatching(new OnMatchedCallback() {
//			@Override
//			public void onMatched(final User enemy) {
//				// TODO Auto-generated method stub
//				
//				CurrentUserInformation.opponentchar = enemy.character;
//				CurrentUserInformation.opponentID = enemy.id;
//				
//				CCScene scene = GameLayer.makeScene();
//				CCDirector.sharedDirector().replaceScene(scene);
//				
//				CommonUtils.debug("onMatched " + enemy.id);
//				
////				runOnUiThread(new Runnable() {
////
////					@Override
////					public void run() {
////						
////					}
////				});
//			}
//		});
		
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				Toast.makeText(LoginActivity.this, "로그인 성공!!!!", Toast.LENGTH_LONG).show();
			}
		});
	}
	@Override
	public void didFailedLogin(final String message) {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				Toast.makeText(LoginActivity.this, "Failed Login - " + message, Toast.LENGTH_LONG).show();
			}
		});
	}
}
