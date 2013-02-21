package contest.lab.gala;

import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCGLSurfaceView;
import org.cocos2d.sound.SoundEngine;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import contest.lab.gala.callback.OnLogout;
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
		
		SoundEngine.sharedEngine().preloadEffect(this, R.raw.effect_button);
		
		
		SoundEngine.sharedEngine().preloadSound(this, R.raw.background_main);
		SoundEngine.sharedEngine().playSound(this, R.raw.background_main, true);
		
		if(Manager.isFirstTime)
		{
			Manager.isFirstTime = false;
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
	
	public void doLogout() {
		NetworkManager.getInstance().doLogout(new OnLogout() {
			
			@Override
			public void onLogoutSuccess() {
				// TODO Auto-generated method stub
				Intent intent = new Intent(BattlerDogActivity.this, MainActivity.class);
//				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				SoundEngine.sharedEngine().pauseSound();
			}
			
			@Override
			public void onLogoutFailed() {
				// TODO Auto-generated method stub
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						Toast.makeText(BattlerDogActivity.this, "로그아웃 실패", Toast.LENGTH_SHORT).show();
					}
				});
			}
		});
	}
	
	@Override
	public void onBackPressed() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		CCDirector.sharedDirector().pause();
		SoundEngine.sharedEngine().pauseSound();
		builder.setMessage("정말로 로그아웃 하시겠습니까?")
		.setCancelable(false)
		.setPositiveButton("예", new DialogInterface.OnClickListener() {

			//	@Override
			public void onClick(DialogInterface arg0, int arg1) {
				//						Toast.makeText(getApplicationContext(),"ID value is "+Integer.toString(id), Toast.LENGTH_SHORT).show();
				doLogout();
			}
		})
		.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
			//@Override
			public void onClick(DialogInterface dialog, int which) {
				CCDirector.sharedDirector().resume();
				dialog.dismiss();
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}
}
