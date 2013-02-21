package contest.lab.gala;

import org.cocos2d.actions.instant.CCCallFuncN;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItemSprite;
import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.sound.SoundEngine;

import contest.lab.gala.interfaces.LifeCycleInterface;
import contest.lab.gala.util.LayerDestroyManager;

public class MainAnimationLayer extends CCLayer implements LifeCycleInterface{
	CCSprite[] animationFrames = null;
	CCSprite animationFirstFrame = null;
	
	CCAnimation animation = null;
	CCAnimate animate = null;
	
	CCSequence sequence = null;
	CCCallFuncN afterAnimation = null;
	
	CCMenu menu_login_join = null;
	CCSprite loginButton_unclicked = null;
	CCSprite loginButton_clicked = null;
	CCSprite joinButton_unclicked = null;
	CCSprite joinButton_clicked = null;
	CCMenuItemSprite btn_login = null;
	CCMenuItemSprite btn_join = null;
	
	
	static CCScene makeScene()
	{
		CCScene scene = CCScene.node();
		CCLayer layer = new MainAnimationLayer();
		scene.addChild(layer);
		
		LayerDestroyManager.getInstance().addLayer((LifeCycleInterface)layer);

		return scene;
	}
	public MainAnimationLayer()
	{
		this.setIsTouchEnabled(true);
		SoundEngine.sharedEngine().playSound(CCDirector.sharedDirector().getActivity(), R.raw.background_start, true);

		this.onCreate();
	}
	public void afterAnimation(Object o)
	{
		loginButton_unclicked.setVisible(true);
		loginButton_clicked.setVisible(true);
		joinButton_unclicked.setVisible(true);
		joinButton_clicked.setVisible(true);
		this.addChild(menu_login_join);
		menu_login_join.setVisible(true);
	}
	
	public void goLogin(Object sender)
	{
		SoundEngine.sharedEngine().playEffect(CCDirector.sharedDirector().getActivity(), R.raw.effect_button);
		MainActivity activity = (MainActivity)CCDirector.sharedDirector().getActivity();
		activity.goLoginPage();
//		CCScene scene = GameLayer.makeScene();
//		CCDirector.sharedDirector().replaceScene(scene);
	}
	
	public void goJoin(Object sender)
	{
		SoundEngine.sharedEngine().playEffect(CCDirector.sharedDirector().getActivity(), R.raw.effect_button);
		MainActivity activity = (MainActivity)CCDirector.sharedDirector().getActivity();
		activity.goJoinPage();
	}

	public void onCreate() {
		loginButton_unclicked = CCSprite.sprite("main_animation/btn_login_unclicked.png");
		loginButton_clicked = CCSprite.sprite("main_animation/btn_login_clicked.png");
		joinButton_unclicked = CCSprite.sprite("main_animation/btn_join_unclicked.png");
		joinButton_clicked = CCSprite.sprite("main_animation/btn_join_clicked.png");
		btn_login = CCMenuItemSprite.item(loginButton_unclicked, loginButton_clicked, this, "goLogin");
		btn_join = CCMenuItemSprite.item(joinButton_unclicked, joinButton_clicked, this, "goJoin");
		
		Manager.setRatioes();
		
		animation = CCAnimation.animation("mainAnimation", 0.2f);
		
		animationFirstFrame = CCSprite.sprite("main_animation/main_frame1.png");
		animationFirstFrame.setScaleX(Manager.ratio_width);
		animationFirstFrame.setScaleY(Manager.ratio_height);
		animationFirstFrame.setPosition(360 * Manager.ratio_width, 640 * Manager.ratio_height);
		this.addChild(animationFirstFrame);
		
		animationFrames = new CCSprite[30];
		
		for(int i = 0; i < 30; i++)
		{
			animationFrames[i] = CCSprite.sprite(String.format("main_animation/main_frame%d.png", i+1));
			animationFrames[i].setScaleX(Manager.ratio_width);
			animationFrames[i].setScaleY(Manager.ratio_height);
			animationFrames[i].setPosition(360 * Manager.ratio_width, 640 * Manager.ratio_height);
			animation.addFrame(animationFrames[i].getTexture());
		}
		afterAnimation = CCCallFuncN.action(this, "afterAnimation");
		animate = CCAnimate.action(animation, false);
		sequence = CCSequence.actions(animate, afterAnimation);
		animationFirstFrame.runAction(sequence);
		
		
		/// �޴���ư �߰�
//		btn_login = CCMenuItemSprite.item(loginButton_unclicked, loginButton_clicked, this, "goLogin");
//		btn_join = CCMenuItemSprite.item(joinButton_unclicked, joinButton_clicked, this, "goJoin");
		
		btn_login.setScaleX(Manager.ratio_width);
		btn_login.setScaleX(1.05f* Manager.ratio_width);
		btn_login.setScaleY(Manager.ratio_height);
		btn_join.setScaleX(Manager.ratio_width);
		btn_join.setScaleY(Manager.ratio_height);
		btn_join.setScaleX(1.05f * Manager.ratio_width);
		
		menu_login_join = CCMenu.menu(btn_login, btn_join);
		menu_login_join.setPosition(360* Manager.ratio_width, 96 * Manager.ratio_height);
		menu_login_join.alignItemsHorizontally(30);
		
		loginButton_unclicked.setVisible(false);
		loginButton_clicked.setVisible(false);
		joinButton_unclicked.setVisible(false);
		joinButton_clicked.setVisible(false);
		menu_login_join.setVisible(false);	
	
	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		animationFrames = null;
		animationFirstFrame = null;
		
		animation = null;
		animate = null;
		
		sequence = null;
		afterAnimation = null;
		
		menu_login_join = null;
		loginButton_unclicked = null;
		loginButton_clicked = null;
		joinButton_unclicked = null;
		joinButton_clicked = null;
		btn_login = null;
		btn_join = null;
		
//		LayerDestroyManager.getInstance().removeLayer(this);
	}
	
	long _attached_time = 0;
	@Override
	public long getTime() {
		return _attached_time;
	}
	@Override
	public void setTime(long time) {
		_attached_time = time;
	}
	
//	@Override
//	protected void finalize() throws Throwable {
//		onDestroy();
//		
//		super.finalize();
//	}
}
