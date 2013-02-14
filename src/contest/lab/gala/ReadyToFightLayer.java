package contest.lab.gala;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItemSprite;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCLabelAtlas;
import org.cocos2d.nodes.CCSprite;

import contest.lab.gala.callback.OnMatchedCallback;
import contest.lab.gala.data.CurrentUserInformation;
import contest.lab.gala.data.User;
import contest.lab.gala.util.CommonUtils;

public class ReadyToFightLayer extends CCLayer{
	int numOfEntryPerOnePage = 4;
	int numOfTotalEntries;
	int currentPageNum = 0;   // 0부터 시작  currentPageNum * 4, +1, +2, +3 번 까지 보여줌

	CCSprite bg_readyLayer = CCSprite.sprite("ranking/bg_ranking.png");
	CCSprite bg_myEntry = CCSprite.sprite("ranking/bg_myentry.png");

	CCSprite btn_next_clicked = CCSprite.sprite("ranking/btn_next_clicked.png");
	CCSprite btn_next_unclicked = CCSprite.sprite("ranking/btn_next_unclicked.png");
	CCSprite btn_before_clicked = CCSprite.sprite("ranking/btn_before_clicked.png");
	CCSprite btn_before_unclicked = CCSprite.sprite("ranking/btn_before_unclicked.png");

	CCSprite btn_goRandomGame_unclicked = CCSprite.sprite("ranking/btn_goRandomGame_unclicked.png");
	CCSprite btn_goRandomGame_clicked = CCSprite.sprite("ranking/btn_goRandomGame_clicked.png");

	CCSprite btn_setting_unclicked = CCSprite.sprite("ranking/btn_setting_unclicked.png");
	CCSprite btn_setting_clicked = CCSprite.sprite("ranking/btn_setting_clicked.png");

	// characters[user][0] = character 0 이미지, 
	// characters[user][1] = character 1 이미지, ...
	CCSprite[] characters;
	CCLabel[] userIDs;
	CCLabel[] userNumOfWins;
//	CCLabelAtlas[] ranking;
	CCLabel[] ranking;
	
	// btn_challenge[user][0] = offline, btn_challenge[user][1] = online
	CCMenu[] btn_challenge;


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

		bg_readyLayer.setPosition(360 * Manager.ratio_width, 640 * Manager.ratio_height);
		bg_readyLayer.setScaleX(Manager.ratio_width);
		bg_readyLayer.setScaleY(Manager.ratio_height);
		this.addChild(bg_readyLayer);
		
		characters = new CCSprite[numOfTotalEntries];
		userIDs = new CCLabel[numOfTotalEntries];
		userNumOfWins = new CCLabel[numOfTotalEntries];
		btn_challenge = new CCMenu[numOfTotalEntries];
		ranking = new CCLabel[numOfTotalEntries];
		// 캐릭터 스프라이트 세팅 
		for(int i = 0; i < numOfTotalEntries; i++)
		{
			if(Manager.friendList.get(i).id.equals(CurrentUserInformation.userID))
			{
				bg_myEntry.setPosition(366 * Manager.ratio_width, (1123 - (i % numOfEntryPerOnePage) * 160) * Manager.ratio_height);
				bg_myEntry.setVisible(false);
				this.addChild(bg_myEntry);
			}
			characters[i] = CCSprite.sprite(String.format("ranking/icon_char%d.png", Manager.friendList.get(i).character));
			characters[i].setPosition(240 * Manager.ratio_width, (1123 - (i % numOfEntryPerOnePage) * 160)*Manager.ratio_height);
			characters[i].setVisible(false);
			this.addChild(characters[i]);

			//ranking[i] = CCLabelAtlas.label(i + "", "ranking/ranking_font.png", 10, 10, '0');
			ranking[i] = CCLabel.makeLabel(i + "", "Arial", 20);
			ranking[i].setPosition(95 * Manager.ratio_width, (1123 - (i % numOfEntryPerOnePage) * 160) * Manager.ratio_height);
			ranking[i].setVisible(false);
			addChild(ranking[i]);
			
			userIDs[i] = CCLabel.makeLabel(Manager.friendList.get(i).id, "Arial", 20);
			userIDs[i].setPosition(331 * Manager.ratio_width, (1171 - (i % numOfEntryPerOnePage) * 160) * Manager.ratio_height);
			userIDs[i].setVisible(false);
			addChild(userIDs[i]);

//			userNumOfWins[i] = CCLabelAtlas.label(Manager.friendList.get(i).number_of_wins + "", "ranking/win_font.png", 10, 10, '0');
			userNumOfWins[i] = CCLabel.makeLabel(Manager.friendList.get(i).number_of_wins + "", "Arial", 20);
			userNumOfWins[i].setPosition(356 * Manager.ratio_width, (1090 - (i % numOfEntryPerOnePage) * 160) * Manager.ratio_height);
			userNumOfWins[i].setVisible(false);
			addChild(userNumOfWins[i]);
		}

		// on, offline 버튼 세팅
		for(int i = 0; i < numOfTotalEntries; i++)
		{
			if(!Manager.friendList.get(i).id.equals(CurrentUserInformation.userID))
			{
				// 접속 중이라면
				if(Manager.friendList.get(i).is_logon)
				{
					CCSprite btn_challenge_unclick = CCSprite.sprite("ranking/btn_challenge_unclicked.png");
					CCSprite btn_challenge_click = CCSprite.sprite("ranking/btn_challenge_clicked.png");
					CCMenuItemSprite menu_challenge = CCMenuItemSprite.item(btn_challenge_unclick, btn_challenge_click, this, "requestMatchWithFriend");
					menu_challenge.setTag(i);
					CCMenu newMenu = CCMenu.menu(menu_challenge);
					newMenu.setVisible(false);
					newMenu.setPosition(581 * Manager.ratio_width, (1120 - (i % numOfEntryPerOnePage) * 160) * Manager.ratio_height);
					this.addChild(newMenu);
					btn_challenge[i] = newMenu;
				}
				// 접속 중이 아니라면
				else
				{
					CCSprite btn_challenge_unclick = CCSprite.sprite("ranking/btn_challenge_notactivated.png");
					CCSprite btn_challenge_click = CCSprite.sprite("ranking/btn_challenge_notactivated.png");
					CCMenuItemSprite menu_challenge = CCMenuItemSprite.item(btn_challenge_unclick, btn_challenge_click);
					CCMenu newMenu = CCMenu.menu(menu_challenge);
					newMenu.setVisible(false);
					newMenu.setPosition(581 * Manager.ratio_width, (1120 - (i % numOfEntryPerOnePage) * 160) * Manager.ratio_height);
					this.addChild(newMenu);
					btn_challenge[i] = newMenu;
				}
			}
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
	
	public void requestMatchWithFriend(Object sender)
	{
		CCMenuItemSprite menu = (CCMenuItemSprite) sender;
		int tag = menu.getTag();
		NetworkManager.getInstance().requestMatchingWithFriend(Manager.friendList.get(tag).id, new OnMatchedCallback() {
			
			@Override
			public void onMatched(User enemy) {
				CurrentUserInformation.opponentID = enemy.id;
				CurrentUserInformation.opponentchar = enemy.character;
				CCScene scene = GameLayer.makeScene();
				CCDirector.sharedDirector().replaceScene(scene);
			}
		});
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
		if(currentPageNum < numOfTotalEntries / 4)
		{
			currentPageNum++;
			updatePage();
		}
	}
	public void clickedRandomGameButton(Object sender)
	{
		NetworkManager.getInstance().requestRandomMatching(new OnMatchedCallback() {
			@Override
			public void onMatched(User enemy) {
				// TODO Auto-generated method stub
				CurrentUserInformation.opponentchar = enemy.character;
				CurrentUserInformation.opponentID = enemy.id;

				CommonUtils.debug("onMatched " + enemy.id);
				CCScene scene = GameLayer.makeScene();
				CCDirector.sharedDirector().replaceScene(scene);
			}
		});
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
				if(Manager.friendList.get(i).id.equals(CurrentUserInformation.userID))
				{
					bg_myEntry.setVisible(true);
				}
				characters[i].setVisible(true);
				userIDs[i].setVisible(true);
				userNumOfWins[i].setVisible(true);
				if(!Manager.friendList.get(i).id.equals(CurrentUserInformation.userID))
				{
					btn_challenge[i].setVisible(true);
				}
				
				ranking[i].setVisible(true);
			}
			else
			{
				if(Manager.friendList.get(i).id.equals(CurrentUserInformation.userID))
				{
					bg_myEntry.setVisible(false);
				}
				characters[i].setVisible(false);
				userIDs[i].setVisible(false);
				userNumOfWins[i].setVisible(false);
				if(!Manager.friendList.get(i).id.equals(CurrentUserInformation.userID))
				{
					btn_challenge[i].setVisible(false);
				}
				ranking[i].setVisible(false);
			}
		}
	}
}
