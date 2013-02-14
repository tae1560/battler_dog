package contest.lab.gala;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
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

		Button btn_login = (Button)findViewById(R.id.btn_login);

		et_id = (EditText)findViewById(R.id.et_id);
		et_pw = (EditText)findViewById(R.id.et_pw);

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
