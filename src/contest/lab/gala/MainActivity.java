package contest.lab.gala;

import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCGLSurfaceView;
import org.cocos2d.sound.SoundEngine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import contest.lab.gala.util.CommonUtils;
import contest.lab.gala.util.LayerDestroyManager;

public class MainActivity extends Activity{
protected CCGLSurfaceView _glSurfaceView;
	
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		CommonUtils.debug("MainActivity onCreate");
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		setContentView(R.layout.main);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		_glSurfaceView = new CCGLSurfaceView(this);

		setContentView(_glSurfaceView);
		
		CCDirector.sharedDirector().attachInView(_glSurfaceView);

		CCDirector.sharedDirector().setDeviceOrientation(CCDirector.kCCDeviceOrientationPortrait);

		//		CCDirector.sharedDirector().setDisplayFPS(true);

		CCDirector.sharedDirector().setAnimationInterval(1.0f / 60.0f);

		Manager.setRatioes();
	}
	@Override
	protected void onStart() {
		CommonUtils.debug("MainActivity onStart");
		super.onStart();
		
		
//		CCScene scene = GameLayer.makeScene();
		CCScene scene = MainAnimationLayer.makeScene();
		CCDirector.sharedDirector().runWithScene(scene);
//		showFriendList();
		SoundEngine.sharedEngine().preloadSound(this, R.raw.background_start);
		SoundEngine.sharedEngine().preloadEffect(this, R.raw.effect_button);
	}

	public void goLoginPage()
	{
		Intent intent = new Intent(MainActivity.this, LoginActivity.class);
		startActivity(intent);
		finish();
//		CCScene scene = GameLayer.makeScene();
//		CCDirector.sharedDirector().replaceScene(scene);
	}
	public void goJoinPage()
	{
		Intent intent = new Intent(MainActivity.this, JoinActivity.class);
		startActivity(intent);
		finish();
	}
	
	protected void onDestroy() {
		CommonUtils.debug("MainActivity onDestroy");
		LayerDestroyManager.getInstance().deallocLayers();
		super.onDestroy();
	}
}
