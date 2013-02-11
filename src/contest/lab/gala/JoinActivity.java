package contest.lab.gala;

import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint.Join;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import contest.lab.gala.callback.JoinCallback;
import contest.lab.gala.callback.LoginCallback;
import contest.lab.gala.callback.OnMatchedCallback;
import contest.lab.gala.data.User;
import contest.lab.gala.util.CommonUtils;

public class JoinActivity extends Activity implements JoinCallback, LoginCallback{
	//////////ȸ������, �α��� �׽�Ʈ �� UI ///////////
	EditText et_id;
	EditText et_pw;
	int selected_character = -1;
	//////////////////////////////////////////////
	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		et_id = (EditText)findViewById(R.id.editText1);
		et_pw = (EditText)findViewById(R.id.editText2);

		RadioGroup group_characters = (RadioGroup)findViewById(R.id.rbgroup_characters);
		final RadioButton rb_char1 = (RadioButton)findViewById(R.id.rb_char1);
		final RadioButton rb_char2 = (RadioButton)findViewById(R.id.rb_char2);
		final RadioButton rb_char3 = (RadioButton)findViewById(R.id.rb_char3);
		final RadioButton rb_char4 = (RadioButton)findViewById(R.id.rb_char4);

		Button btn_join = (Button)findViewById(R.id.btn_join);
		Button btn_login = (Button)findViewById(R.id.btn_login);

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

		btn_login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				NetworkManager.getInstance().doLogin(et_id.getText().toString(), et_pw.getText().toString(), JoinActivity.this);
			}
		});
	}
	@Override
	public void didSuccessJoin() {
		// TODO Auto-generated method stub
		// ȣ����
		// NetworkManager.getInstance().sendRequest("id password", NetworkManager.requestLogin, this);

		// �α��� ������
		// => ������ �ѱ��
		runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				Toast.makeText(JoinActivity.this, "ȸ�� ���� ���� !!!! ", Toast.LENGTH_LONG).show();
				

				NetworkManager.getInstance().requestRandomMatching(new OnMatchedCallback() {
					
					@Override
					public void onMatched(User enemy) {
						// TODO Auto-generated method stub
						CommonUtils.debug("onMatched " + enemy.id);
						Intent intent = new Intent(JoinActivity.this, BattlerDogActivity.class);
						startActivity(intent);						
					}
				});
				
				
			}
		});
	}
	@Override
	public void didSuccessLogin() {
		// TODO Auto-generated method stub
		runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				Toast.makeText(JoinActivity.this, "�α��� ���� !!!! ", Toast.LENGTH_LONG).show();
				
				NetworkManager.getInstance().requestRandomMatching(new OnMatchedCallback() {
					
					@Override
					public void onMatched(User enemy) {
						// TODO Auto-generated method stub
						CommonUtils.debug("onMatched " + enemy.id);
						Intent intent = new Intent(JoinActivity.this, BattlerDogActivity.class);
						startActivity(intent);						
					}
				});
				
				
				
//				CCScene scene = ReadyToFightLayer.makeScene();
//				CCDirector.sharedDirector().runWithScene(scene);
			}
		});
		
	}
	@Override
	public void didFailedLogin(final String message) {
		runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				Toast.makeText(JoinActivity.this, "Failed Login - " + message, Toast.LENGTH_LONG).show();
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
