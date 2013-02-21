package contest.lab.gala;

import java.util.Timer;
import java.util.TimerTask;

import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSpriteFrameCache;
import org.cocos2d.nodes.CCTextureCache;

import contest.lab.gala.util.LayerDestroyManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


public class StartActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		CCDirector.sharedDirector().purgeCachedData();
		CCDirector.sharedDirector().getSendCleanupToScene();
		CCSpriteFrameCache.purgeSharedSpriteFrameCache();
		CCTextureCache.purgeSharedTextureCache();
		LayerDestroyManager.getInstance().deallocLayers();
		
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				Intent intent = new Intent(StartActivity.this, MainActivity.class);
				startActivity(intent);
				finish();
			}
		}, 1000);
	}
}
