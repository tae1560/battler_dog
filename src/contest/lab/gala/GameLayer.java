package contest.lab.gala;

import java.util.ArrayList;
import java.util.Random;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItemSprite;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;

public class GameLayer extends CCLayer{
	// ȭ�鿡 ���̴� �������� ����Ʈ
	ArrayList<Item> itemList;			
	// item�� position���� ����
	ArrayList<CGPoint> itemPositions;	
	
	// ���� ���� ���� ������ ���� �Ŵ��� ��ü 
	// Manager.ratio_x; , Manager.ratio_y;
	Manager m;
	
	
	// �� ȭ�鿡 ���̴� �������� ����
	int numOfItems = 4;
	
	// ���� ��ư �޴�
	CCMenuItemSprite btn_bone;
	CCMenuItemSprite btn_gum;
	CCMenuItemSprite btn_redbull;
	CCMenu buttons;
	CCSprite btn_bone_unclick;
	CCSprite btn_bone_click;
	CCSprite btn_gum_unclick;
	CCSprite btn_gum_click;
	CCSprite btn_redbull_unclick;
	CCSprite btn_redbull_click;
	
	CCSprite bg_battlelayer;
	CCSprite bg_gamelayer;
	
	static CCScene makeScene()
	{
		CCScene scene = CCScene.node();
		CCLayer layer1 = new GameLayer();
		CCLayer layer2 = new SkillGageLayer();
		CCLayer layer3 = new BattleLayer();
		
		scene.addChild(layer1);
		scene.addChild(layer2);
		scene.addChild(layer3);
		
		return scene;
	}
	void init()
	{
		NetworkManager.getInstance().startSocketWithUsername("Hyunjeong");
		
		
		m = new Manager();
		
		bg_gamelayer = CCSprite.sprite("minigame/bg_gamelayer.png");
		bg_gamelayer.setPosition(360 * m.ratio_width, 640 * m.ratio_height);
		this.addChild(bg_gamelayer);
		
		
		
		// item���� position���� ����Ʈ�� ����
		itemPositions = new ArrayList<CGPoint>();
		for(int i = 0; i < numOfItems; i++)
		{
			CGPoint cgpoint = CCDirector.sharedDirector().convertToGL(CGPoint.ccp(360 * m.ratio_width, (920 - i * 130) * m.ratio_height));	//* ������ �ʿ�
			itemPositions.add(cgpoint);
		}
		
		// item���� position�� ����, addchild
		itemList = new ArrayList<Item>();
		for(int i = 0; i < numOfItems; i++)
		{
			Item newItem = createNewItem();
			newItem.image.setPosition(itemPositions.get(i));
			newItem.image.setScaleX(m.ratio_width);
			newItem.image.setScaleY(m.ratio_height);
			itemList.add(newItem);
			this.addChild(newItem.image);
		}
		
		// button�� �޴��� addchild
		btn_bone = CCMenuItemSprite.item(btn_bone_unclick, btn_bone_click, this, "clickedBone");
		btn_gum = CCMenuItemSprite.item(btn_gum_unclick, btn_gum_click, this, "clickedGum");
		btn_redbull = CCMenuItemSprite.item(btn_redbull_unclick, btn_redbull_click, this, "clickedRedbull");
		
		btn_bone.setScaleX(m.ratio_width);
		btn_bone.setScaleY(m.ratio_height);
		btn_gum.setScaleX(m.ratio_width);
		btn_gum.setScaleY(m.ratio_height);
		btn_redbull.setScaleX(m.ratio_width);
		btn_redbull.setScaleY(m.ratio_height);
		
		buttons = CCMenu.menu(btn_bone, btn_gum, btn_redbull);
		buttons.alignItemsHorizontally(32 * m.ratio_width);
		buttons.setPosition(360 * m.ratio_width, 100 * m.ratio_height);
		this.addChild(buttons);
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
		ComboLayer.showCombo();
		
		// ���õ� �����ۿ� ���� ������ ȹ��(������ �� ������Ʈ ����)
		itemList.get(0).acquireGage();
		
		// ���õ� �������� ���̾�� ����
		this.removeChild(itemList.get(0).image, true);
		// ������ ����Ʈ���� ����
		itemList.remove(0);
		// ���ο� �������� ����Ʈ�� �߰�
		Item newItem = createNewItem();
		newItem.image.setScaleX(m.ratio_width);
		newItem.image.setScaleY(m.ratio_height);
		
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
}
