package contest.lab.gala;

import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCGLSurfaceView;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import contest.lab.gala.interfaces.LifeCycleInterface;
import contest.lab.gala.util.LayerDestroyManager;

public class BattlerDogActivity extends Activity {
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
		
		CCDirector.sharedDirector().attachInView(_glSurfaceView);

		CCDirector.sharedDirector().setDeviceOrientation(CCDirector.kCCDeviceOrientationPortrait);

		//		CCDirector.sharedDirector().setDisplayFPS(true);

		CCDirector.sharedDirector().setAnimationInterval(1.0f / 60.0f);
		
		Manager.setRatioes();
	}
	@Override
	protected void onStart() {

		super.onStart();
		
		
		if(Manager.isFirstTime)
		{
			CCScene scene = EpisodeLayer.makeScene();
			CCDirector.sharedDirector().runWithScene(scene);
		}
		else
		{
			CCScene scene = ReadyToFightLayer.makeScene();
			CCDirector.sharedDirector().runWithScene(scene);
		}
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
				
				Toast.makeText(CCDirector.sharedDirector().getActivity().getApplicationContext(), "������ ¯ : " + kindOfAttack, Toast.LENGTH_LONG).show();
			}
		});
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		LayerDestroyManager.getInstance().deallocLayers();
		
		super.onDestroy();
	}
}
