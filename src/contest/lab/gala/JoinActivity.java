package contest.lab.gala;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import contest.lab.gala.callback.JoinCallback;
import contest.lab.gala.callback.RequestFriendsCallback;
import contest.lab.gala.data.User;

public class JoinActivity extends Activity implements JoinCallback{
	//////////회원가입, 로그인 테스트 용 UI ///////////
	EditText et_id;
	EditText et_pw;
	int selected_character = -1;
	//////////////////////////////////////////////
	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		et_id = (EditText)findViewById(R.id.editText1);
		et_pw = (EditText)findViewById(R.id.editText2);

		RadioGroup group_characters = (RadioGroup)findViewById(R.id.rbgroup_characters);
		final RadioButton rb_char1 = (RadioButton)findViewById(R.id.rb_char1);
		final RadioButton rb_char2 = (RadioButton)findViewById(R.id.rb_char2);
		final RadioButton rb_char3 = (RadioButton)findViewById(R.id.rb_char3);
		final RadioButton rb_char4 = (RadioButton)findViewById(R.id.rb_char4);

		Button btn_join = (Button)findViewById(R.id.btn_join);
		btn_join.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(rb_char1.isChecked())
				{

					selected_character = 1;
				}
				else if(rb_char2.isChecked())
				{
					selected_character = 2;
				}
				else if(rb_char3.isChecked())
				{
					selected_character = 3;
				}
				else
				{
					selected_character = 4;
				}
				
				if (selected_character >= 0) {
					NetworkManager.getInstance().doJoin(et_id.getText().toString(), et_pw.getText().toString(), selected_character, JoinActivity.this);					
				}				
			}
		});
	}
	@Override
	public void didSuccessJoin() {
		// TODO Auto-generated method stub
		// 호출방법
		// NetworkManager.getInstance().sendRequest("id password", NetworkManager.requestLogin, this);

		// 로그인 성공시
		// => 페이지 넘기기
		runOnUiThread(new Runnable() {
			
			// for scalability
			@Override
			public void run() {
				Toast.makeText(JoinActivity.this, "회원 가입 성공 !!!! ", Toast.LENGTH_LONG).show();
				

//				NetworkManager.getInstance().requestRandomMatching(new OnMatchedCallback() {
//					
//					@Override
//					public void onMatched(User enemy) {
//						// TODO Auto-generated method stub
//						CommonUtils.debug("onMatched " + enemy.id);
//						Intent intent = new Intent(JoinActivity.this, BattlerDogActivity.class);
//						startActivity(intent);						
//					}
//				});	
				
				NetworkManager.getInstance().requestFriends(new RequestFriendsCallback() {
					
					@Override
					public void didGetFriends(ArrayList<User> friends) {
						Manager.friendList = (ArrayList<User>) friends.clone();
						Intent intent = new Intent(JoinActivity.this, BattlerDogActivity.class);
						startActivity(intent);
					}
				});
				
				
			}
		});
	}
	@Override
	public void didFailedJoin(final String message) {
		runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				Toast.makeText(JoinActivity.this, "Failed Join - " + message, Toast.LENGTH_LONG).show();
			}
		});
		
	}
}
