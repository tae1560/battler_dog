package contest.lab.gala;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItemSprite;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;

public class ReadyToFightLayer extends CCLayer{
	
	int numOfEntryPerOnePage = 4;
	int numOfTotalEntries;
	int currentPageNum = 0;   // 0부터 시작  currentPageNum * 4, +1, +2, +3 번 까지 보여줌
	
	CCSprite bg_readyLayer;
	CCSprite bg_rankInfo;
	
	CCSprite btn_next_clicked = CCSprite.sprite("btn_next_clicked.png");
	CCSprite btn_next_unclicked = CCSprite.sprite("btn_next_unclicked.png");
	CCSprite btn_before_clicked = CCSprite.sprite("btn_before_clicked.png");
	CCSprite btn_before_unclicked = CCSprite.sprite("btn_before_unclicked.png");
	
	CCSprite btn_goRandomGame_unclicked = CCSprite.sprite("btn_goRandomGame_unclicked.png");
	CCSprite btn_goRandomGame_clicked = CCSprite.sprite("btn_goRandomGame_clicked.png");
	
	CCSprite btn_setting_unclicked = CCSprite.sprite("btn_setting_unclicked.png");
	CCSprite btn_setting_clicked = CCSprite.sprite("btn_setting_clicked.png");
	
	// characters[user][0] = character 0 이미지, 
	// characters[user][1] = character 1 이미지, ...
	CCSprite[] characters;
	CCLabel[] userIDs;
	CCLabel[] userNumOfWins;
	
	// btn_challenge[user][0] = offline, btn_challenge[user][1] = online
	CCSprite[] btn_challenge;
	
	
	public static CCScene makeScene()
	{
		CCScene scene = CCScene.node();
		CCLayer layer = new ReadyToFightLayer();
		scene.addChild(layer);
		return scene;
	}
	public ReadyToFightLayer()
	{
		this.setIsTouchEnabled(true);
		
		numOfTotalEntries = Manager.friendList.size();
		
		characters = new CCSprite[numOfTotalEntries];
		userIDs = new CCLabel[numOfTotalEntries];
		btn_challenge = new CCSprite[numOfTotalEntries];
		
		// 캐릭터 스프라이트 세팅 
		for(int i = 0; i < numOfTotalEntries; i++)
		{
			characters[i] = CCSprite.sprite(String.format("character/icon_char%d.png", i));
			characters[i].setPosition(300, 200 + (i % numOfEntryPerOnePage) * 100);
			this.addChild(characters[i]);
		}
		
		// 사용자들 점수 보여주기
		for(int i = 0; i < numOfTotalEntries; i++)
		{
			userNumOfWins[i] = CCLabel.makeLabel(String.valueOf(Manager.friendList.get(i).number_of_wins)+ "연승", "Arial", 20);
			characters[i].setPosition(250, 200 + (i % numOfEntryPerOnePage) * 100);
			this.addChild(characters[i]);
		}
		
		// on, offline 버튼 세팅
		for(int i = 0; i < numOfTotalEntries; i++)
		{
			btn_challenge[i] = CCSprite.sprite(String.format("btn_challenge%d.png", i));
			btn_challenge[i].setPosition(400, 200 + (i % numOfEntryPerOnePage) * 100);
			this.addChild(btn_challenge[i]);
		}
		
		CCMenuItemSprite btn_next = CCMenuItemSprite.item(btn_next_unclicked, btn_next_clicked, this, "clickedNextButton");
		CCMenuItemSprite btn_before = CCMenuItemSprite.item(btn_before_unclicked, btn_before_clicked, this, "clickedBeforeButton");
		CCMenu controlPage = CCMenu.menu(btn_before, btn_next);
		controlPage.alignItemsHorizontally(10);
		controlPage.setPosition(240, 320);
		this.addChild(controlPage);
		
		updatePage();
		
		CCMenuItemSprite btn_random_game = CCMenuItemSprite.item(btn_goRandomGame_unclicked, btn_goRandomGame_clicked, this, "clickedRandomGameButton");
		CCMenuItemSprite btn_setting = CCMenuItemSprite.item(btn_setting_unclicked, btn_setting_clicked, this, "clickedSettingButton");
		CCMenu otherMenu = CCMenu.menu(btn_random_game, btn_setting);
		otherMenu.alignItemsHorizontally(20);
		otherMenu.setPosition(240, 100);
		this.addChild(otherMenu);
	}
	public void clickedBeforeButton(Object sender)
	{
		if(currentPageNum > 0)
		{
			currentPageNum --;
			updatePage();
		}
	}
	public void clickedNextButton(Object sender)
	{
		if(currentPageNum < (numOfTotalEntries / 4) - 1)
		{
			currentPageNum--;
			updatePage();
		}
	}
	public void clickedRandomGameButton(Object sender)
	{
		// 랜덤 매칭 해서 게임 레이어로 가기
	}
	public void clickedSettingButton(Object sender)
	{
		// 세팅 레이어로 가기
	}
	// 해당 user의 설정된 캐릭터를 보여줌
	public void updatePage()
	{
		for(int i = 0; i < numOfTotalEntries; i++)
		{
			if((i >= currentPageNum * 4) && (i < (currentPageNum + 1) * 4))
			{
				characters[i].setVisible(true);
				userIDs[i].setVisible(true);
				userNumOfWins[i].setVisible(true);
				btn_challenge[i].setVisible(true);
			}
			else
			{
				characters[i].setVisible(false);
				userIDs[i].setVisible(false);
				userNumOfWins[i].setVisible(false);
				btn_challenge[i].setVisible(false);
			}
		}
	}
}
