package contest.lab.gala;

import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCGLSurfaceView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import contest.lab.gala.callback.OnGameEndedCallback;
import contest.lab.gala.data.User;

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
				Manager.numOfWins = user.total_wins;
				Manager.numOfLoses = user.total_loses;
				Manager.numOfSuccessiveWins = user.number_of_wins;
				Manager.numOfGames = Manager.numOfLoses + Manager.numOfWins;
				
				Intent intent = new Intent(GameActivity.this, ResultActivity.class);
				startActivity(intent);
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
