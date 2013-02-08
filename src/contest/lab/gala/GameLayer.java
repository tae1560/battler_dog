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
	// 화면에 보이는 아이템의 리스트
	ArrayList<Item> itemList;			
	// item의 position들을 저장
	ArrayList<CGPoint> itemPositions;	
	
	// 한 화면에 보이는 아이템의 개수
	int numOfItems = 4;
	
	// 게임 버튼 메뉴
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
		// item들의 position들을 리스트로 저장
		itemPositions = new ArrayList<CGPoint>();
		for(int i = 0; i < numOfItems; i++)
		{
			CGPoint cgpoint = CGPoint.ccp(480, 600 - i * 50);	//* 재조정 필요
			itemPositions.add(cgpoint);
		}
		
		// item들의 position을 설정, addchild
		itemList = new ArrayList<Item>();
		for(int i = 0; i < numOfItems; i++)
		{
			Item newItem = createNewItem();
			newItem.image.setPosition(itemPositions.get(i));
			this.addChild(newItem.image);
		}
		
		// button들 메뉴로 addchild
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
		// 선택된 아이템에 의한 게이지 획득(게이지 바 업데이트 포함)
		itemList.get(0).acquireGage();
		
		// 선택된 아이템을 레이어에서 제거
		this.removeChild(itemList.get(0).image, true);
		// 아이템 리스트에서 제거
		itemList.remove(0);
		// 새로운 아이템을 리스트에 추가
		Item newItem = createNewItem();
		itemList.add(newItem);
		// 아이템들의 포지션 재조정
		for(int i = 0; i < numOfItems; i++)
		{
			itemList.get(i).image.setPosition(itemPositions.get(i));
		}
		// 새로운 아이템(리스트의 마지막)을 레이어에 추가
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
