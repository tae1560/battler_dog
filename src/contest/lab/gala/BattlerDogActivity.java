package contest.lab.gala;

import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import contest.lab.gala.callback.LoginCallback;

public class BattlerDogActivity extends Activity implements LoginCallback {
	//** 공격 정보를 받았을 때, SkillGageLayer.getDamaged(int kindOfAttack);
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		CCScene scene = GameLayer.makeScene();
        CCDirector.sharedDirector().runWithScene(scene);
    }

	@Override
	public void didSuccessLogin() {
		// 호출방법
		// NetworkManager.getInstance().sendRequest("id password", NetworkManager.requestLogin, this);
		
		// 로그인 성공시
		// => 페이지 넘기기
	}
}
