package contest.lab.gala;

import java.util.ArrayList;

import org.cocos2d.nodes.CCDirector;
import org.cocos2d.sound.SoundEngine;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import contest.lab.gala.callback.JoinCallback;
import contest.lab.gala.callback.RequestFriendsCallback;
import contest.lab.gala.data.CurrentUserInformation;
import contest.lab.gala.data.User;

public class JoinActivity extends Activity implements JoinCallback{
	//////////ȸ����, �α��� �׽�Ʈ �� UI ///////////
	EditText et_id;
	EditText et_pw;
	int selected_character = -1;
	ProgressDialog waitDlg;

	//////////////////////////////////////////////
	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);

		SoundEngine.sharedEngine().preloadEffect(this, R.raw.effect_button);

		et_id = (EditText)findViewById(R.id.userIDEntry);
		et_pw = (EditText)findViewById(R.id.passwordEntry);
		

		RadioGroup group_characters = (RadioGroup)findViewById(R.id.rbgroup_characters);
		final RadioButton rb_char1 = (RadioButton)findViewById(R.id.rb_char1);
		final RadioButton rb_char2 = (RadioButton)findViewById(R.id.rb_char2);
		final RadioButton rb_char3 = (RadioButton)findViewById(R.id.rb_char3);
		final RadioButton rb_char4 = (RadioButton)findViewById(R.id.rb_char4);

		rb_char1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SoundEngine.sharedEngine().playEffect(JoinActivity.this, R.raw.effect_button);
			}
		});
		rb_char2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SoundEngine.sharedEngine().playEffect(JoinActivity.this, R.raw.effect_button);
			}
		});
		rb_char3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SoundEngine.sharedEngine().playEffect(JoinActivity.this, R.raw.effect_button);
			}
		});
		rb_char4.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SoundEngine.sharedEngine().playEffect(JoinActivity.this, R.raw.effect_button);
			}
		});
		
		ImageButton btn_join = (ImageButton)findViewById(R.id.btn_join);
		btn_join.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				SoundEngine.sharedEngine().playEffect(JoinActivity.this, R.raw.effect_button);

				if(rb_char1.isChecked())
				{
					selected_character = 0;
				}
				else if(rb_char2.isChecked())
				{
					selected_character = 1;
				}
				else if(rb_char3.isChecked())
				{
					selected_character = 2;
				}
				else
				{
					selected_character = 3;
				}
				
				if (selected_character >= 0) {
					NetworkManager.getInstance().doJoin(et_id.getText().toString(), et_pw.getText().toString(), selected_character, JoinActivity.this);					
					waitDlg = ProgressDialog.show(JoinActivity.this, "로드중...", "로드중 입니다. 잠시만 기다려주십시오", true,false);

				}				
			}
		});
	}
	@Override
	public void didSuccessJoin(User user) {
		CurrentUserInformation.userID = user.id;
		CurrentUserInformation.userChar = user.character;

		NetworkManager.getInstance().requestFriends(new RequestFriendsCallback() {
			
			@Override
			public void didGetFriends(ArrayList<User> friends) {
				waitDlg.dismiss();
				Manager.isFirstTime = true;
				Manager.friendList = (ArrayList<User>) friends.clone();
				Intent intent = new Intent(JoinActivity.this, BattlerDogActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
				startActivity(intent);
				finish();
			}
		});
	}
	@Override
	public void didFailedJoin(final String message) {
		runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				Toast.makeText(JoinActivity.this, "Failed Join - " + message, Toast.LENGTH_LONG).show();
				waitDlg.dismiss();
			}
		});
		
	}
	@Override
	protected void onDestroy() {
		
		super.onDestroy();
	}
}
