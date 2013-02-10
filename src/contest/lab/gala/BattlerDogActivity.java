package contest.lab.gala;

import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCGLSurfaceView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import contest.lab.gala.callback.LoginCallback;

public class BattlerDogActivity extends Activity implements LoginCallback {
	//** 공격 정보를 받았을 때, SkillGageLayer.getDamaged(int kindOfAttack);
	protected CCGLSurfaceView _glSurfaceView;
	
	//////////회원가입, 로그인 테스트 용 UI ///////////
	EditText et_id;
	EditText et_pw;
	int selected_character;
	//////////////////////////////////////////////
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//		_glSurfaceView = new CCGLSurfaceView(this);
//
//		setContentView(_glSurfaceView);

	}
	@Override
	protected void onStart() {

		super.onStart();
//		
//		CCDirector.sharedDirector().attachInView(_glSurfaceView);
//
//		CCDirector.sharedDirector().setDeviceOrientation(CCDirector.kCCDeviceOrientationPortrait);
//
//		//		CCDirector.sharedDirector().setDisplayFPS(true);
//
//		CCDirector.sharedDirector().setAnimationInterval(1.0f / 60.0f);

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
				
			}
		});
	}

	@Override
	public void didSuccessLogin() {
		// 호출방법
		// NetworkManager.getInstance().sendRequest("id password", NetworkManager.requestLogin, this);

		// 로그인 성공시
		// => 페이지 넘기기
	}
	public void runGame()
	{
		CCScene scene = GameLayer.makeScene();
		CCDirector.sharedDirector().runWithScene(scene);
	}
	
	public static void makeToast(final int kindOfAttack)
	{
		CCDirector.sharedDirector().getActivity().runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				Toast.makeText(CCDirector.sharedDirector().getActivity().getApplicationContext(), "조현정 짱 : " + kindOfAttack, Toast.LENGTH_LONG).show();
			}
		});
	}
}
