package contest.lab.gala;

import java.util.ArrayList;

import org.cocos2d.sound.SoundEngine;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
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
	ProgressDialog waitDlg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login);
		SoundEngine.sharedEngine().preloadEffect(this, R.raw.effect_button);
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
				SoundEngine.sharedEngine().playEffect(LoginActivity.this, R.raw.effect_button);

				// TODO Auto-generated method stub
				NetworkManager.getInstance().doLogin(et_id.getText().toString(), et_pw.getText().toString(), LoginActivity.this);
				waitDlg = ProgressDialog.show(LoginActivity.this, "로드중...", "로드중 입니다. 잠시만 기다려주십시오", true,false);

			}
		});
		super.onStart();
	}
	@Override
	public void didSuccessLogin(User user) {
		CurrentUserInformation.userID = user.id;
		CurrentUserInformation.userChar = user.character;
				
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				Toast.makeText(LoginActivity.this, "로그인 성공!!!!", Toast.LENGTH_LONG).show();
				waitDlg.dismiss();
				
				Intent intent = new Intent(LoginActivity.this, FriendLoadingActivity.class);
				startActivity(intent);
				finish();
			}
		});
	}
	@Override
	public void didFailedLogin(final String message) {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				Toast.makeText(LoginActivity.this, "Failed Login - " + message, Toast.LENGTH_LONG).show();
				waitDlg.dismiss();
			}
		});
	}
	@Override
	protected void onDestroy() {
		
		super.onDestroy();
	}
}
