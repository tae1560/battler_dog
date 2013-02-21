package contest.lab.gala;

import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSpriteFrameCache;
import org.cocos2d.nodes.CCTextureCache;

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
		System.gc();
		
		Intent intent = new Intent(StartActivity.this, MainActivity.class);
		startActivity(intent);
		finish();
	}
}
