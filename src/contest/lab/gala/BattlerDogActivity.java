package contest.lab.gala;

import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCGLSurfaceView;
import org.cocos2d.sound.SoundEngine;

import contest.lab.gala.interfaces.LifeCycleInterface;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class BattlerDogActivity extends Activity {
	//** ��� ������ �޾��� ��, SkillGageLayer.getDamaged(int kindOfAttack);
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
		if (current_lifecycle_callback != null) {
			current_lifecycle_callback.onStart();
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
	
	public static LifeCycleInterface current_lifecycle_callback = null;
	public void exit(){
		super.onBackPressed();
	}
	public void onBackPressed(){
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			CCDirector.sharedDirector().pause();
			SoundEngine.sharedEngine().pauseSound();
			builder.setMessage("������� �������� ���ư��ðڽ��ϱ�?")
			.setCancelable(false)
			.setPositiveButton("Ȯ��", new DialogInterface.OnClickListener() {

				//	@Override
				public void onClick(DialogInterface arg0, int arg1) {
					//						Toast.makeText(getApplicationContext(),"ID value is "+Integer.toString(id), Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(BattlerDogActivity.this, MainActivity.class);
					startActivity(intent);
					
				}
			})
			.setNegativeButton("���", new DialogInterface.OnClickListener() {
				//@Override
				public void onClick(DialogInterface dialog, int which) {
					CCDirector.sharedDirector().resume();
					dialog.dismiss();
				}
			});
			AlertDialog alert = builder.create();
			alert.show();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		if (current_lifecycle_callback != null) {
			current_lifecycle_callback.onStop();
		}
		
		super.onStop();
	}
}
