package contest.lab.gala;

import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCGLSurfaceView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity{
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

	public void goLoginPage()
	{
		Intent intent = new Intent(MainActivity.this, LoginActivity.class);
		startActivity(intent);
//		CCScene scene = GameLayer.makeScene();
//		CCDirector.sharedDirector().replaceScene(scene);
	}
	public void goJoinPage()
	{
		Intent intent = new Intent(MainActivity.this, JoinActivity.class);
		startActivity(intent);
		finish();
	}
}
