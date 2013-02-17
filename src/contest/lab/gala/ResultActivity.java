package contest.lab.gala;

import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCGLSurfaceView;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class ResultActivity extends Activity {
	protected CCGLSurfaceView _glSurfaceView1;
	
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		setContentView(R.layout.main);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		_glSurfaceView1 = new CCGLSurfaceView(this);

		setContentView(_glSurfaceView1);
		
		
	}
	@Override
	protected void onStart() {

		super.onStart();
		
		CCDirector.sharedDirector().attachInView(_glSurfaceView1);

		CCDirector.sharedDirector().setDeviceOrientation(CCDirector.kCCDeviceOrientationPortrait);

		//		CCDirector.sharedDirector().setDisplayFPS(true);

		CCDirector.sharedDirector().setAnimationInterval(1.0f / 60.0f);

		Manager.setRatioes();
//		CCScene scene = GameLayer.makeScene();
		CCScene scene = ResultLayer.makeScene();
		CCDirector.sharedDirector().runWithScene(scene);
//		showFriendList();
	}
}
