package contest.lab.gala;

import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCGLSurfaceView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class BattlerDogActivity extends Activity {
	//** 공격 정보를 받았을 때, SkillGageLayer.getDamaged(int kindOfAttack);
	protected CCGLSurfaceView _glSurfaceView;
	
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		setContentView(R.layout.main);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		_glSurfaceView = new CCGLSurfaceView(this);

		setContentView(_glSurfaceView);
		
		
	}
	@Override
	protected void onStart() {

		super.onStart();
		
		CCDirector.sharedDirector().attachInView(_glSurfaceView);

		CCDirector.sharedDirector().setDeviceOrientation(CCDirector.kCCDeviceOrientationPortrait);

		//		CCDirector.sharedDirector().setDisplayFPS(true);

		CCDirector.sharedDirector().setAnimationInterval(1.0f / 60.0f);

		Manager.setRatioes();
//		CCScene scene = GameLayer.makeScene();
		CCScene scene = MainAnimationLayer.makeScene();
		CCDirector.sharedDirector().runWithScene(scene);
//		showFriendList();
	}

	public void showFriendList()
	{
		CCScene scene = ReadyToFightLayer.makeScene();
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
	
	public void goLoginPage()
	{
//		Intent intent = new Intent(BattlerDogActivity.this, JoinActivity.class);
//		startActivity(intent);
		CCScene scene = GameLayer.makeScene();
		CCDirector.sharedDirector().replaceScene(scene);
	}
	public void goJoinPage()
	{
		Intent intent = new Intent(BattlerDogActivity.this, JoinActivity.class);
		startActivity(intent);
	}
}
