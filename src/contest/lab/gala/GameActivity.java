package contest.lab.gala;

import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCGLSurfaceView;

import contest.lab.gala.callback.OnGameEndedCallback;
import contest.lab.gala.data.User;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class GameActivity extends Activity {
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

		//		CCScene scene = GameLayer.makeScene();
		CCScene scene = GameLayer.makeScene();
		CCDirector.sharedDirector().runWithScene(scene);
		
		NetworkManager.getInstance().setGameEndedCallback(new OnGameEndedCallback() {
			
			@Override
			public void onGameEnded(boolean isWin, User user) {
				// isWin => 1 => win the game
				Manager.resultOfGame = isWin;
			}
		});
//		showFriendList();
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

}
