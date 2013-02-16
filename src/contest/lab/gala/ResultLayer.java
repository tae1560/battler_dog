package contest.lab.gala;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItemSprite;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;

import android.content.Intent;

public class ResultLayer extends CCLayer {

	CCSprite bg_result = CCSprite.sprite("result/bg_result.png");
	CCSprite btn_back_unclicked = CCSprite.sprite("result/btn_back_unclicked.png");
	CCSprite btn_back_clicked = CCSprite.sprite("result/btn_back_clicked.png");
	static CCScene makeScene()
	{
		CCScene scene = CCScene.node();
		CCLayer layer = new ResultLayer();
		scene.addChild(layer);
		return scene;
	}
	public ResultLayer()
	{
		this.setIsTouchEnabled(true);
		
		bg_result.setScaleX(Manager.ratio_width);
		bg_result.setScaleY(Manager.ratio_height);
		bg_result.setPosition(360 * Manager.ratio_width, 640 * Manager.ratio_height);
		this.addChild(bg_result);
		
		CCMenuItemSprite btn_back = CCMenuItemSprite.item(btn_back_unclicked, btn_back_clicked, this, "goToFriendList");
		CCMenu backMenu = CCMenu.menu(btn_back);
		backMenu.setScaleX(Manager.ratio_width);
		backMenu.setScaleY(Manager.ratio_height);
		backMenu.setPosition(633 * Manager.ratio_width, 81 * Manager.ratio_height);
		this.addChild(backMenu);
	}
	public void goToFriendList(Object sender)
	{
		Intent intent = new Intent(CCDirector.sharedDirector().getActivity(), BattlerDogActivity.class);
		CCDirector.sharedDirector().getActivity().startActivity(intent);
	}
}
