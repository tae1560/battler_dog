package contest.lab.gala;

import java.util.ArrayList;
import java.util.Random;

import org.cocos2d.actions.base.CCFiniteTimeAction;
import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.instant.CCCallFuncN;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItemSprite;
import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrameCache;
import org.cocos2d.nodes.CCTextureCache;
import org.cocos2d.sound.SoundEngine;
import org.cocos2d.types.CGPoint;

import contest.lab.gala.interfaces.LifeCycleInterface;
import contest.lab.gala.item.Bone;
import contest.lab.gala.item.Gum;
import contest.lab.gala.item.Item;
import contest.lab.gala.item.Redbull;
import contest.lab.gala.util.LayerDestroyManager;

public class GameLayer extends CCLayer implements LifeCycleInterface{
	ArrayList<Item> itemList;			
	ArrayList<CGPoint> itemPositions = null;	
	
	
	
	int numOfItems = 4;
	
	CCMenuItemSprite btn_bone= null;
	CCMenuItemSprite btn_gum= null;
	CCMenuItemSprite btn_redbull= null;
	CCMenu buttons= null;
	CCSprite btn_bone_unclick= null;
	CCSprite btn_bone_click= null;
	CCSprite btn_gum_unclick= null;
	CCSprite btn_gum_click= null;
	CCSprite btn_redbull_unclick= null;
	CCSprite btn_redbull_click= null;
	
	CCSprite bg_battlelayer= null;
	CCSprite bg_gamelayer= null;
	
	CCSprite rectangle1= null;
	CCSprite rectangle2= null;

	static CCScene makeScene()
	{
		CCDirector.sharedDirector().purgeCachedData();
		CCDirector.sharedDirector().getSendCleanupToScene();
		CCSpriteFrameCache.purgeSharedSpriteFrameCache();
		CCTextureCache.purgeSharedTextureCache();
		
		CCScene scene = CCScene.node();
		CCLayer layer1 = new GameLayer();
		CCLayer layer3 = new BattleLayer();
		CCLayer layer4 = new ComboLayer();
		
		scene.addChild(layer1);
		scene.addChild(layer3);
		scene.addChild(layer4);
		
		LayerDestroyManager.getInstance().addLayer((LifeCycleInterface)layer1);
		//LayerDestroyManager.getInstance().addLayer((LifeCycleInterface)layer2);
		LayerDestroyManager.getInstance().addLayer((LifeCycleInterface)layer3);
		LayerDestroyManager.getInstance().addLayer((LifeCycleInterface)layer4);
		
		return scene;
	}
	void init()
	{
		bg_gamelayer = CCSprite.sprite("minigame/bg_gamelayer.png");
		bg_gamelayer.setPosition(360 * Manager.ratio_width, 640 * Manager.ratio_height);
		this.addChild(bg_gamelayer);		
		
		itemPositions = new ArrayList<CGPoint>();
		for(int i = 0; i < numOfItems; i++)
		{
			CGPoint cgpoint = CCDirector.sharedDirector().convertToGL(CGPoint.ccp(360 * Manager.ratio_width, (920 - i * 135) * Manager.ratio_height));	//* ������ �ʿ�
			itemPositions.add(cgpoint);
		}
		
		itemList = new ArrayList<Item>();
		for(int i = 0; i < numOfItems; i++)
		{
			Item newItem = createNewItem();
			newItem.image.setPosition(itemPositions.get(i));
			newItem.image.setScaleX(Manager.ratio_width);
			newItem.image.setScaleY(Manager.ratio_height);
			itemList.add(newItem);
			this.addChild(newItem.image);
		}
		
		// button�� �޴��� addchild
		btn_bone = CCMenuItemSprite.item(btn_bone_unclick, btn_bone_click, this, "clickedBone");
		btn_gum = CCMenuItemSprite.item(btn_gum_unclick, btn_gum_click, this, "clickedGum");
		btn_redbull = CCMenuItemSprite.item(btn_redbull_unclick, btn_redbull_click, this, "clickedRedbull");
		
		btn_bone.setScaleX(Manager.ratio_width);
		btn_bone.setScaleY(Manager.ratio_height);
		btn_gum.setScaleX(Manager.ratio_width);
		btn_gum.setScaleY(Manager.ratio_height);
		btn_redbull.setScaleX(Manager.ratio_width);
		btn_redbull.setScaleY(Manager.ratio_height);
		
		buttons = CCMenu.menu(btn_bone, btn_gum, btn_redbull);
		buttons.alignItemsHorizontally(32 * Manager.ratio_width);
		buttons.setPosition(360 * Manager.ratio_width, 100 * Manager.ratio_height);
		this.addChild(buttons);
		
		rectangle1 = CCSprite.sprite("minigame/rectangle1.png");
		rectangle2 = CCSprite.sprite("minigame/rectangle2.png");
		rectangle1.setPosition(itemPositions.get(0).x, itemPositions.get(0).y - 10 * Manager.ratio_height);
		rectangle1.setScaleX(Manager.ratio_width);
		rectangle1.setScaleY(Manager.ratio_height);
		rectangle2.setScaleX(Manager.ratio_width);
		rectangle2.setScaleY(Manager.ratio_height);
		
		CCAnimation rectAnimation = CCAnimation.animation("animation", 0.2f);
		rectAnimation.addFrame(rectangle1.getTexture());
		rectAnimation.addFrame(rectangle2.getTexture());
		
		CCAnimate rectAnimate = CCAnimate.action(rectAnimation);
		rectangle1.runAction(CCRepeatForever.action(rectAnimate));
		this.addChild(rectangle1);
		

	}
	public void removeFight(Object sender)
	{
		CCSprite fight = (CCSprite) sender;
		this.removeChild(fight, true);
	}
	
	public void clickedBone(Object sender)
	{
		Item i = itemList.get(0);
		if(i instanceof Bone)
			clickedCorrectOne();
		else
			clickedWrongOne();
	}
	public void clickedGum(Object sender)
	{
		Item i = itemList.get(0);
		if(i instanceof Gum)
			clickedCorrectOne();
		else
			clickedWrongOne();
	}
	public void clickedRedbull(Object sender)
	{
		Item i = itemList.get(0);
		if(i instanceof Redbull)
			clickedCorrectOne();
		else
			clickedWrongOne();
	}
	void clickedCorrectOne()
	{
		ComboLayer.runningLayer.showCombo();
		
		// ���õ� �����ۿ� ���� ������ ȹ��(������ �� ������Ʈ ����)
		itemList.get(0).acquireGage();
		
		// ���õ� �������� ���̾�� ����
		this.removeChild(itemList.get(0).image, true);
		// ������ ����Ʈ���� ����
		itemList.remove(0);
		// ���ο� �������� ����Ʈ�� �߰�
		Item newItem = createNewItem();
		newItem.image.setScaleX(Manager.ratio_width);
		newItem.image.setScaleY(Manager.ratio_height);
		
		itemList.add(newItem);
		// �����۵��� ������ ������
		for(int i = 0; i < numOfItems; i++)
		{
			itemList.get(i).image.setPosition(itemPositions.get(i));
		}
		// ���ο� ������(����Ʈ�� ������)�� ���̾ �߰�
		this.addChild(itemList.get(numOfItems - 1).image);
	}
	void clickedWrongOne()
	{
		ComboLayer.resetCombo();
	}
	public GameLayer()
	{
		this.setIsTouchEnabled(true);
		btn_bone_unclick = CCSprite.sprite("minigame/btn_bone_unclick.png");
		btn_bone_click = CCSprite.sprite("minigame/btn_bone_click.png");
		btn_gum_unclick = CCSprite.sprite("minigame/btn_gum_unclick.png");
		btn_gum_click = CCSprite.sprite("minigame/btn_gum_click.png");
		btn_redbull_unclick = CCSprite.sprite("minigame/btn_redbull_unclick.png");
		btn_redbull_click = CCSprite.sprite("minigame/btn_redbull_click.png");

		init();
	}
	Item createNewItem()
	{
		Random random = new Random();
		int randomNumber = random.nextInt(3) + 1;
		Item newItem = null;
		switch(randomNumber)
		{
			case 1 :
				newItem = new Bone();
				break;
			case 2 :
				newItem = new Gum();
				break;
			case 3 :
				newItem = new Redbull();
				break;
		}
		return newItem;
	}
	@Override
	public void onDestroy() {
		itemList = null;			
		itemPositions = null;	
		btn_bone = null;
		btn_gum = null;
		btn_redbull = null;
		buttons = null;
		btn_bone_unclick = null;
		btn_bone_click = null;
		btn_gum_unclick = null;
		btn_gum_click = null;
		btn_redbull_unclick = null;
		btn_redbull_click = null;
		
		bg_battlelayer = null;
		bg_gamelayer = null;
		
		rectangle1 = null;
		rectangle2 = null;
		
//		LayerDestroyManager.getInstance().removeLayer(this);
	}	
}
