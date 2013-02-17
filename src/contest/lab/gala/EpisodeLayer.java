package contest.lab.gala;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.base.CCFiniteTimeAction;
import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.interval.CCMoveBy;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItemSprite;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;

public class EpisodeLayer extends CCLayer {
	CCSprite bg_episode = null;
	
	CCMenuItemSprite btn_next = null;
	CCMenu menu_next = null;
	CCSprite btn_next_clicked = null;
	CCSprite btn_next_unclicked = null;
	CCSprite cloud = null;
	CCSprite[] episodeList = null;
	
	CCFiniteTimeAction moveToRight = null;
	CCFiniteTimeAction moveToLeft = null;
	CCSequence moveOfCloudSequence = null;
	
	int currentPageNum = 0;
	
	public static CCScene makeScene()
	{
		CCScene scene = CCScene.node();
		CCLayer layer = new EpisodeLayer();
		scene.addChild(layer);
		return scene;
	}
	public EpisodeLayer() {
		this.setIsTouchEnabled(true);
		
		bg_episode = CCSprite.sprite("episode/bg_episode.png");
		bg_episode.setScaleX(Manager.ratio_width);
		bg_episode.setScaleY(Manager.ratio_height);
		bg_episode.setPosition(360 * Manager.ratio_width, 720 * Manager.ratio_height);
		this.addChild(bg_episode);
		
		moveToRight = CCMoveBy.action(5f, CGPoint.ccp(-720f * Manager.ratio_width, 0));
		moveToLeft = CCMoveBy.action(5f, CGPoint.ccp(720f * Manager.ratio_width, 0));
		moveOfCloudSequence = CCSequence.actions(moveToRight, moveToLeft);	
		
		cloud = CCSprite.sprite("episode/cloud.png");
		cloud.setScaleX(Manager.ratio_width);
		cloud.setScaleY(Manager.ratio_height);
		cloud.setPosition(720 * Manager.ratio_width, 1033 * Manager.ratio_height);
		cloud.runAction(CCRepeatForever.action(moveOfCloudSequence));
		this.addChild(cloud);
		
		episodeList = new CCSprite[6];
		
		for(int i = 0 ; i < 6; i++)
		{
			episodeList[i] = CCSprite.sprite(String.format("episode/episode%d.png", i + 1));
			episodeList[i].setScaleX(Manager.ratio_width);
			episodeList[i].setScaleY(Manager.ratio_height);
			episodeList[i].setPosition(360 * Manager.ratio_width, 793 * Manager.ratio_height);
			episodeList[i].setVisible(false);
			this.addChild(episodeList[i]);
		}
		episodeList[currentPageNum].setVisible(true);
		
		bg_episode = CCSprite.sprite("episode/bg_episode.png");
		
		btn_next_clicked = CCSprite.sprite("episode/btn_next_clicked.png");
		btn_next_unclicked = CCSprite.sprite("episode/btn_next_unclicked.png");
		
		btn_next = CCMenuItemSprite.item(btn_next_unclicked, btn_next_clicked, this, "updatePage");
		menu_next = CCMenu.menu(btn_next);
		menu_next.setAnchorPoint(0f,0f);
		menu_next.setPosition(585 * Manager.ratio_width, 1200 * Manager.ratio_height);
		menu_next.setScaleX(Manager.ratio_width);
		menu_next.setScaleY(Manager.ratio_height);
		this.addChild(menu_next);
		
		
	}
	public void updatePage(Object sender)
	{
		if(currentPageNum == 5)
		{
			CCScene scene = ReadyToFightLayer.makeScene();
			CCDirector.sharedDirector().replaceScene(scene);
		}
		currentPageNum++;
		for(int i = 0; i < 6; i++)
		{
			if(i == currentPageNum)
				episodeList[i].setVisible(true);
			else
				episodeList[i].setVisible(false);
		}
	}
}