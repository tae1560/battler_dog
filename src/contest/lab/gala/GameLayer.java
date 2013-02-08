package contest.lab.gala;

import java.util.ArrayList;
import java.util.Random;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItemSprite;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;

public class GameLayer extends CCLayer{
	// ȭ�鿡 ���̴� �������� ����Ʈ
	ArrayList<Item> itemList;			
	// item�� position���� ����
	ArrayList<CGPoint> itemPositions;	
	
	// �� ȭ�鿡 ���̴� �������� ����
	int numOfItems = 4;
	
	// ���� ��ư �޴�
	CCMenuItemSprite btn_bone;
	CCMenuItemSprite btn_gum;
	CCMenuItemSprite btn_redbull;
	CCMenu buttons;
	CCSprite btn_bone_unclick = CCSprite.sprite("btn_bone_unclick.png");
	CCSprite btn_bone_click = CCSprite.sprite("btn_bone_click.png");
	CCSprite btn_gum_unclick = CCSprite.sprite("btn_gum_unclick.png");
	CCSprite btn_gum_click = CCSprite.sprite("btn_gum_click.png");
	CCSprite btn_redbull_unclick = CCSprite.sprite("btn_redbull_unclick.png");
	CCSprite btn_redbull_click = CCSprite.sprite("btn_redbull_click.png");
	
	
	static CCScene makeScene()
	{
		CCScene scene = CCScene.node();
		CCLayer layer = new GameLayer();
		layer.addChild(layer);
		return scene;
	}
	void init()
	{
		// item���� position���� ����Ʈ�� ����
		itemPositions = new ArrayList<CGPoint>();
		for(int i = 0; i < numOfItems; i++)
		{
			CGPoint cgpoint = CGPoint.ccp(480, 600 - i * 50);	//* ������ �ʿ�
			itemPositions.add(cgpoint);
		}
		
		// item���� position�� ����, addchild
		itemList = new ArrayList<Item>();
		for(int i = 0; i < numOfItems; i++)
		{
			Item newItem = createNewItem();
			newItem.image.setPosition(itemPositions.get(i));
			this.addChild(newItem.image);
		}
		
		// button�� �޴��� addchild
		btn_bone = CCMenuItemSprite.item(btn_bone_unclick, btn_bone_click, this, "clickedBone");
		btn_gum = CCMenuItemSprite.item(btn_gum_unclick, btn_gum_click, this, "clickedGum");
		btn_redbull = CCMenuItemSprite.item(btn_redbull_unclick, btn_redbull_click, this, "clickedRedbull");
		
		buttons = CCMenu.menu(btn_bone, btn_gum, btn_redbull);
		buttons.alignItemsHorizontally(20);
		buttons.setPosition(240, 100);
		this.addChild(buttons);
	}
	void clickedBone(Object Sender)
	{
		if(itemList.get(0) instanceof Bone)
			clickedCorrectOne();
		else
			clickedWrongOne();
	}
	void clickedGum(Object Sender)
	{
		if(itemList.get(0) instanceof Gum)
			clickedCorrectOne();
		else
			clickedWrongOne();
	}
	void clickedRedbull(Object Sender)
	{
		if(itemList.get(0) instanceof Redbull)
			clickedCorrectOne();
		else
			clickedWrongOne();
	}
	void clickedCorrectOne()
	{
		// ���õ� �����ۿ� ���� ������ ȹ��(������ �� ������Ʈ ����)
		itemList.get(0).acquireGage();
		
		// ���õ� �������� ���̾�� ����
		this.removeChild(itemList.get(0).image, true);
		// ������ ����Ʈ���� ����
		itemList.remove(0);
		// ���ο� �������� ����Ʈ�� �߰�
		Item newItem = createNewItem();
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
		
	}
	public GameLayer()
	{
		this.setIsTouchEnabled(true);
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
