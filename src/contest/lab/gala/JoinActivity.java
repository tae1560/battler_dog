package contest.lab.gala;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Paint.Join;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import contest.lab.gala.callback.JoinCallback;

public class JoinActivity extends Activity implements JoinCallback{
	//////////회원가입, 로그인 테스트 용 UI ///////////
	EditText et_id;
	EditText et_pw;
	int selected_character;
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
			}
		});

		btn_login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				NetworkManager.getInstance().doJoin(et_id.getText().toString(), et_pw.getText().toString(), selected_character, JoinActivity.this);
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
		Toast.makeText(this, "회원 가입 성공 !!!! ", Toast.LENGTH_LONG).show();
		
	}
}
