package contest.lab.gala;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;
import contest.lab.gala.callback.LoginCallback;
import contest.lab.gala.callback.OnMatchedCallback;
import contest.lab.gala.data.User;
import contest.lab.gala.util.CommonUtils;

public class LoginActivity extends Activity implements LoginCallback{

	@Override
	public void didSuccessLogin() {
		// TODO Auto-generated method stub
		runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				Toast.makeText(LoginActivity.this, "로그인 성공 !!!! ", Toast.LENGTH_LONG).show();
				
				NetworkManager.getInstance().requestRandomMatching(new OnMatchedCallback() {
					
					@Override
					public void onMatched(User enemy) {
						// TODO Auto-generated method stub
						CommonUtils.debug("onMatched " + enemy.id);
						Intent intent = new Intent(LoginActivity.this, BattlerDogActivity.class);
						startActivity(intent);						
					}
				});
				
				
				
//				CCScene scene = ReadyToFightLayer.makeScene();
//				CCDirector.sharedDirector().runWithScene(scene);
			}
		});
		
	}
	@Override
	public void didFailedLogin(final String message) {
		runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				Toast.makeText(LoginActivity.this, "Failed Login - " + message, Toast.LENGTH_LONG).show();
			}
		});
	}
}
