package contest.lab.gala;

import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;

import contest.lab.gala.callback.LoginCallback;

import android.app.Activity;
import android.os.Bundle;

public class BattlerDogActivity extends Activity implements LoginCallback {
	//** ���� ������ �޾��� ��, SkillGageLayer.getDamaged(int kindOfAttack);
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        CCScene scene = GameLayer.makeScene();
        CCDirector.sharedDirector().runWithScene(scene);
    }

	@Override
	public void didSuccessLogin() {
		// ȣ����
		// NetworkManager.getInstance().sendRequest("id password", NetworkManager.requestLogin, this);
		
		// �α��� ������
		// => ������ �ѱ��
	}
}