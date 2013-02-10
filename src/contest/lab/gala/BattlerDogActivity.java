package contest.lab.gala;

import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCGLSurfaceView;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import contest.lab.gala.callback.LoginCallback;

public class BattlerDogActivity extends Activity implements LoginCallback {
	//** ���� ������ �޾��� ��, SkillGageLayer.getDamaged(int kindOfAttack);
	protected CCGLSurfaceView _glSurfaceView;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		_glSurfaceView = new CCGLSurfaceView(this);

		setContentView(_glSurfaceView);

	}
	@Override
	protected void onStart() {

		CCDirector.sharedDirector().attachInView(_glSurfaceView);

		CCDirector.sharedDirector().setDeviceOrientation(CCDirector.kCCDeviceOrientationPortrait);

		//		CCDirector.sharedDirector().setDisplayFPS(true);

		CCDirector.sharedDirector().setAnimationInterval(1.0f / 60.0f);

		CCScene scene = GameLayer.makeScene();
		CCDirector.sharedDirector().runWithScene(scene);
		super.onStart();
	}

	@Override
	public void didSuccessLogin() {
		// ȣ����
		// NetworkManager.getInstance().sendRequest("id password", NetworkManager.requestLogin, this);

		// �α��� ������
		// => ������ �ѱ��
	}
	
	public static void makeToast()
	{
		Toast.makeText(CCDirector.sharedDirector().getActivity(), "������ ¯ ", Toast.LENGTH_LONG).show();
	}
	
}
