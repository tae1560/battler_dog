package contest.lab.gala;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItemSprite;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCLabelAtlas;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrameCache;
import org.cocos2d.nodes.CCTextureCache;

import android.content.Intent;
import contest.lab.gala.callback.OnMatchedCallback;
import contest.lab.gala.data.CurrentUserInformation;
import contest.lab.gala.data.User;
import contest.lab.gala.interfaces.LifeCycleInterface;
import contest.lab.gala.util.CommonUtils;

public class ReadyToFightLayer extends CCLayer implements LifeCycleInterface{
	int numOfEntryPerOnePage = 4;
	int numOfTotalEntries;
	int currentPageNum = 0;   // 0���� ����  currentPageNum * 4, +1, +2, +3 �� ���� ������

	CCSprite bg_readyLayer = null;
	CCSprite bg_myEntry = null;

	CCSprite btn_next_clicked = null;
	CCSprite btn_next_unclicked = null;
	CCSprite btn_before_clicked = null;
	CCSprite btn_before_unclicked = null;

	CCSprite btn_goRandomGame_unclicked = null;
	CCSprite btn_goRandomGame_clicked = null;

	CCSprite btn_setting_unclicked = null;
	CCSprite btn_setting_clicked = null;

	// characters[user][0] = character 0 �̹���, 
	// characters[user][1] = character 1 �̹���, ...
	CCSprite[] characters = null;
	CCLabel[] userIDs = null;
	CCLabelAtlas[] userNumOfWins = null;
	CCLabelAtlas[] ranking = null;
	
	// btn_challenge[user][0] = offline, btn_challenge[user][1] = online
	CCMenu[] btn_challenge = null;


	public static CCScene makeScene()
	{
		CCDirector.sharedDirector().purgeCachedData();
		CCDirector.sharedDirector().getSendCleanupToScene();
		CCSpriteFrameCache.purgeSharedSpriteFrameCache();
		CCTextureCache.purgeSharedTextureCache();
		
		CCScene scene = CCScene.node();
		CCLayer layer = new ReadyToFightLayer();
		scene.addChild(layer);
		
		BattlerDogActivity.current_lifecycle_callback = (LifeCycleInterface) layer;
		BattlerDogActivity.current_lifecycle_callback.onStart();
		
		NetworkManager.getInstance().setOnMatchedCallback(new OnMatchedCallback() {
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
		
		return scene;
	}
	public ReadyToFightLayer()
	{
		this.setIsTouchEnabled(true);
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
				
				CCDirector.sharedDirector().purgeCachedData();
				
//				CCScene scene = GameLayer.makeScene();
//				CCDirector.sharedDirector().replaceScene(scene);
				Intent intent = new Intent(CCDirector.sharedDirector().getActivity(), GameActivity.class);
				CCDirector.sharedDirector().getActivity().startActivity(intent);
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
		// ���� ���̾�� ����
	}
	// �ش� user�� ������ ĳ���͸� ������
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
	
	// >0 : alive
	// 0 : not alive
	private int layer_state = 0;
	@Override
	public void onStart() {		
		if (layer_state == 0) {
			layer_state ++;
			
			// initialize static variables
			bg_readyLayer = CCSprite.sprite("ranking/bg_ranking.png");
			bg_myEntry = CCSprite.sprite("ranking/bg_myentry.png");

			btn_next_clicked = CCSprite.sprite("ranking/btn_next_clicked.png");
			btn_next_unclicked = CCSprite.sprite("ranking/btn_next_unclicked.png");
			btn_before_clicked = CCSprite.sprite("ranking/btn_before_clicked.png");
			btn_before_unclicked = CCSprite.sprite("ranking/btn_before_unclicked.png");

			btn_goRandomGame_unclicked = CCSprite.sprite("ranking/btn_goRandomGame_unclicked.png");
			btn_goRandomGame_clicked = CCSprite.sprite("ranking/btn_goRandomGame_clicked.png");

			btn_setting_unclicked = CCSprite.sprite("ranking/btn_setting_unclicked.png");
			btn_setting_clicked = CCSprite.sprite("ranking/btn_setting_clicked.png");
			
			
			// initialize on constructor
			numOfTotalEntries = Manager.friendList.size();

			bg_readyLayer.setPosition(360 * Manager.ratio_width, 640 * Manager.ratio_height);
			bg_readyLayer.setScaleX(Manager.ratio_width);
			bg_readyLayer.setScaleY(Manager.ratio_height);
			this.addChild(bg_readyLayer);
			
			characters = new CCSprite[numOfTotalEntries];
			userIDs = new CCLabel[numOfTotalEntries];
			userNumOfWins = new CCLabelAtlas[numOfTotalEntries];
			btn_challenge = new CCMenu[numOfTotalEntries];
			ranking = new CCLabelAtlas[numOfTotalEntries];
			// ĳ���� ��������Ʈ ���� 
			for(int i = 0; i < numOfTotalEntries; i++)
			{
				if(Manager.friendList.get(i).id.equals(CurrentUserInformation.userID))
				{
					bg_myEntry.setPosition(366 * Manager.ratio_width, (1123 - (i % numOfEntryPerOnePage) * 160) * Manager.ratio_height);
					bg_myEntry.setVisible(false);
					bg_myEntry.setScaleX(Manager.ratio_width);
					bg_myEntry.setScaleY(Manager.ratio_height);
					this.addChild(bg_myEntry);
				}
				characters[i] = CCSprite.sprite(String.format("ranking/icon_char%d.png", Manager.friendList.get(i).character));
				characters[i].setPosition(240 * Manager.ratio_width, (1123 - (i % numOfEntryPerOnePage) * 160)*Manager.ratio_height);
				characters[i].setScaleX(Manager.ratio_width);
				characters[i].setScaleY(Manager.ratio_height);
				characters[i].setVisible(false);
				this.addChild(characters[i]);

				ranking[i] = CCLabelAtlas.label(String.valueOf(i), "ranking/ranking_font.png", 30*(int)Manager.ratio_width, 30 * (int)Manager.ratio_height, '0');
				ranking[i].setPosition(95 * Manager.ratio_width, (1123 - (i % numOfEntryPerOnePage) * 160) * Manager.ratio_height);
				ranking[i].setVisible(false);
				ranking[i].setScaleX(Manager.ratio_width);
				ranking[i].setScaleY(Manager.ratio_height);
				addChild(ranking[i]);
				
				userIDs[i] = CCLabel.makeLabel(Manager.friendList.get(i).id, "Arial", 40);
				userIDs[i].setPosition(331 * Manager.ratio_width, (1171 - (i % numOfEntryPerOnePage) * 160) * Manager.ratio_height);
				userIDs[i].setVisible(false);
				userIDs[i].setScaleX(Manager.ratio_width);
				userIDs[i].setScaleY(Manager.ratio_height);
				addChild(userIDs[i]);

				userNumOfWins[i] = CCLabelAtlas.label(Manager.friendList.get(i).number_of_wins + "", "ranking/win_font.png", 30 * (int)Manager.ratio_width, 30 * (int)Manager.ratio_height, '0');
				userNumOfWins[i].setPosition(356 * Manager.ratio_width, (1090 - (i % numOfEntryPerOnePage) * 160) * Manager.ratio_height);
				userNumOfWins[i].setVisible(false);
				userNumOfWins[i].setScaleX(Manager.ratio_width);
				userNumOfWins[i].setScaleY(Manager.ratio_height);
				addChild(userNumOfWins[i]);
			}

			// on, offline ��ư ����
			for(int i = 0; i < numOfTotalEntries; i++)
			{
				if(!Manager.friendList.get(i).id.equals(CurrentUserInformation.userID))
				{
					// ���� ���̶��
					if(Manager.friendList.get(i).is_logon)
					{
						CCSprite btn_challenge_unclick = CCSprite.sprite("ranking/btn_challenge_unclicked.png");
						CCSprite btn_challenge_click = CCSprite.sprite("ranking/btn_challenge_clicked.png");
						CCMenuItemSprite menu_challenge = CCMenuItemSprite.item(btn_challenge_unclick, btn_challenge_click, this, "requestMatchWithFriend");
						menu_challenge.setTag(i);
						CCMenu newMenu = CCMenu.menu(menu_challenge);
						newMenu.setVisible(false);
						newMenu.setScaleX(Manager.ratio_width);
						newMenu.setScaleY(Manager.ratio_height);
						newMenu.alignItemsHorizontally(30 * Manager.ratio_width);
						newMenu.setPosition(581 * Manager.ratio_width, (1120 - (i % numOfEntryPerOnePage) * 160) * Manager.ratio_height);
						this.addChild(newMenu);
						btn_challenge[i] = newMenu;
					}
					// ���� ���� �ƴ϶��
					else
					{
						CCSprite btn_challenge_unclick = CCSprite.sprite("ranking/btn_challenge_notactivated.png");
						CCSprite btn_challenge_click = CCSprite.sprite("ranking/btn_challenge_notactivated.png");
						CCMenuItemSprite menu_challenge = CCMenuItemSprite.item(btn_challenge_unclick, btn_challenge_click);
						CCMenu newMenu = CCMenu.menu(menu_challenge);
						newMenu.setVisible(false);
						newMenu.setScaleX(Manager.ratio_width);
						newMenu.setScaleY(Manager.ratio_height);
						newMenu.setPosition(581 * Manager.ratio_width, (1120 - (i % numOfEntryPerOnePage) * 160) * Manager.ratio_height);
						this.addChild(newMenu);
						btn_challenge[i] = newMenu;
					}
				}
			}

			CCMenuItemSprite btn_next = CCMenuItemSprite.item(btn_next_unclicked, btn_next_clicked, this, "clickedNextButton");
			CCMenuItemSprite btn_before = CCMenuItemSprite.item(btn_before_unclicked, btn_before_clicked, this, "clickedBeforeButton");
			CCMenu controlPage = CCMenu.menu(btn_before, btn_next);
			controlPage.alignItemsHorizontally(50 * Manager.ratio_width);
			controlPage.setPosition(240 * Manager.ratio_width, 320 * Manager.ratio_height);
			this.addChild(controlPage);

			updatePage();

			CCMenuItemSprite btn_random_game = CCMenuItemSprite.item(btn_goRandomGame_unclicked, btn_goRandomGame_clicked, this, "clickedRandomGameButton");
			CCMenuItemSprite btn_setting = CCMenuItemSprite.item(btn_setting_unclicked, btn_setting_clicked, this, "clickedSettingButton");
			CCMenu otherMenu = CCMenu.menu(btn_random_game, btn_setting);
			otherMenu.alignItemsHorizontally(50 * Manager.ratio_width);
			otherMenu.setPosition(240 * Manager.ratio_width, 100 * Manager.ratio_height);
			this.addChild(otherMenu);
		}
	}
	@Override
	public void onStop() {
		if (layer_state > 0) {
			layer_state = 0;
			
			bg_readyLayer = null;
			bg_myEntry = null;

			btn_next_clicked = null;
			btn_next_unclicked = null;
			btn_before_clicked = null;
			btn_before_unclicked = null;

			btn_goRandomGame_unclicked = null;
			btn_goRandomGame_clicked = null;

			btn_setting_unclicked = null;
			btn_setting_clicked = null;

			characters = null;
			userIDs = null;
			userNumOfWins = null;
//			CCLabelAtlas[] ranking;
			ranking = null;
			
			// btn_challenge[user][0] = offline, btn_challenge[user][1] = online
			btn_challenge = null;
			
			System.gc();
		}
		
	}
}
