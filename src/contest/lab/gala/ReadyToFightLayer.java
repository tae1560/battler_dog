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
import org.cocos2d.sound.SoundEngine;

import android.content.Intent;
import contest.lab.gala.callback.OnMatchedCallback;
import contest.lab.gala.data.CurrentUserInformation;
import contest.lab.gala.data.User;
import contest.lab.gala.interfaces.LifeCycleInterface;
import contest.lab.gala.util.CommonUtils;
import contest.lab.gala.util.LayerDestroyManager;

public class ReadyToFightLayer extends CCLayer implements LifeCycleInterface, OnMatchedCallback{
	int numOfEntryPerOnePage = 4;
	int numOfTotalEntries;
	int currentPageNum = 0;   // 0���� ����  currentPageNum * 4, +1, +2, +3 �� ���� ������

	int selectedFriend = 0;
	
	CCSprite bg_readyLayer = null;
	CCSprite bg_myEntry = null;

	CCSprite popup = null;
	CCMenuItemSprite btn_ok = null;
	CCSprite btn_ok_unclicked = null;
	CCSprite btn_ok_clicked = null;
	CCMenuItemSprite btn_no = null;
	CCSprite btn_no_unclicked = null;
	CCSprite btn_no_clicked = null;
	CCMenu menu_popup = null;
	
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
	CCSprite[] successiveWins = null;
	CCLabelAtlas[] ranking = null;

	CCLabel currentPage = null;
	CCLabel totalPage = null;

	// btn_challenge[user][0] = offline, btn_challenge[user][1] = online
	CCMenu[] btn_challenge = null;


	public static CCScene makeScene()
	{
		CCDirector.sharedDirector().purgeCachedData();
		CCDirector.sharedDirector().getSendCleanupToScene();
		CCSpriteFrameCache.purgeSharedSpriteFrameCache();
		CCTextureCache.purgeSharedTextureCache();

		
		CCScene scene = CCScene.node();
		ReadyToFightLayer layer = new ReadyToFightLayer();
		scene.addChild(layer);

		LayerDestroyManager.getInstance().addLayer((LifeCycleInterface)layer);

		NetworkManager.getInstance().setOnMatchedCallback(layer);

		return scene;
	}
	public ReadyToFightLayer()
	{
		this.setIsTouchEnabled(true);

		onStart();
	}
	public void clickedNo(Object sender)
	{
		this.removeChild(popup, true);
		this.removeChild(menu_popup, true);
	}
	public void clickedYes(Object sender)
	{
		NetworkManager.getInstance().requestMatchingWithFriend(Manager.friendList.get(selectedFriend).id, this);
	}
	public void requestMatchWithFriend(Object sender)
	{
		CCMenuItemSprite menu = (CCMenuItemSprite) sender;
		selectedFriend = menu.getTag();
		
		SoundEngine.sharedEngine().playEffect(CCDirector.sharedDirector().getActivity(), R.raw.effect_button);
	
		this.addChild(popup);
		this.addChild(menu_popup);
		
		
			}
	public void clickedBeforeButton(Object sender)
	{
		SoundEngine.sharedEngine().playEffect(CCDirector.sharedDirector().getActivity(), R.raw.effect_button);
		
		if(currentPageNum > 0)
		{
			currentPageNum --;
			updatePage();
		}
	}
	public void clickedNextButton(Object sender)
	{
		SoundEngine.sharedEngine().playEffect(CCDirector.sharedDirector().getActivity(), R.raw.effect_button);
		
		if(currentPageNum < numOfTotalEntries / 4)
		{
			currentPageNum++;
			updatePage();
		}
	}
	public void clickedRandomGameButton(Object sender)
	{
		SoundEngine.sharedEngine().playEffect(CCDirector.sharedDirector().getActivity(), R.raw.effect_button);
		
		NetworkManager.getInstance().requestRandomMatching(this);
	}
	public void clickedSettingButton(Object sender)
	{
		SoundEngine.sharedEngine().playEffect(CCDirector.sharedDirector().getActivity(), R.raw.effect_button);
		
		SoundEngine.sharedEngine().pauseSound();
		CCScene scene = SettingLayer.makeScene();
		CCDirector.sharedDirector().replaceScene(scene);
	}
	// �ش� user�� ������ ĳ���͸� ������
	public void updatePage()
	{
		currentPage.setString((currentPageNum + 1) + "");
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
				successiveWins[i].setVisible(true);
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
				successiveWins[i].setVisible(false);
				ranking[i].setVisible(false);
			}
		}
	}

	public void onStart() {		
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
		successiveWins = new CCSprite[numOfTotalEntries];
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
			characters[i].setPosition(290 * Manager.ratio_width, (1123 - (i % numOfEntryPerOnePage) * 160)*Manager.ratio_height);
			characters[i].setScaleX(Manager.ratio_width);
			characters[i].setScaleY(Manager.ratio_height);
			characters[i].setVisible(false);
			this.addChild(characters[i]);

			ranking[i] = CCLabelAtlas.label(String.valueOf(i+1), "ranking/ranking_font.png", 63, 88, '0');
			ranking[i].setPosition(95 * Manager.ratio_width, (1090 - (i % numOfEntryPerOnePage) * 160) * Manager.ratio_height);
			ranking[i].setVisible(false);
			ranking[i].setScaleX(Manager.ratio_width);
			ranking[i].setScaleY(Manager.ratio_height);
			addChild(ranking[i]);

			userIDs[i] = CCLabel.makeLabel(Manager.friendList.get(i).id, "Arial", 40);
			userIDs[i].setPosition(430 * Manager.ratio_width, (1171 - (i % numOfEntryPerOnePage) * 160) * Manager.ratio_height);
			userIDs[i].setVisible(false);
			userIDs[i].setScaleX(Manager.ratio_width);
			userIDs[i].setScaleY(Manager.ratio_height);
			addChild(userIDs[i]);

			userNumOfWins[i] = CCLabelAtlas.label(Manager.friendList.get(i).number_of_wins + "", "ranking/win_font.png", 28, 38, '0');
			userNumOfWins[i].setPosition(406 * Manager.ratio_width, (1090 - (i % numOfEntryPerOnePage) * 160) * Manager.ratio_height);
			userNumOfWins[i].setVisible(false);
			userNumOfWins[i].setScaleX(Manager.ratio_width);
			userNumOfWins[i].setScaleY(Manager.ratio_height);
			addChild(userNumOfWins[i]);

			successiveWins[i] = CCSprite.sprite("ranking/successive_wins.png");
			successiveWins[i].setPosition(480 * Manager.ratio_width, (1110 - (i % numOfEntryPerOnePage) * 160) * Manager.ratio_height);
			successiveWins[i].setScaleX(Manager.ratio_width);
			successiveWins[i].setScaleY(Manager.ratio_height);
			successiveWins[i].setVisible(false);
			addChild(successiveWins[i]);	

			popup = CCSprite.sprite("ranking/popup.png");
			popup.setScaleX(Manager.ratio_width);
			popup.setScaleY(Manager.ratio_height);
			popup.setPosition(360 * Manager.ratio_width, 744 * Manager.ratio_height);
			
			btn_ok_clicked = CCSprite.sprite("ranking/btn_ok_clicked.png");
			btn_ok_unclicked = CCSprite.sprite("ranking/btn_ok_unclicked.png");
			btn_ok = CCMenuItemSprite.item(btn_ok_unclicked, btn_ok_clicked, this, "clickedYes");
			btn_ok.setScaleX(Manager.ratio_width);
			btn_ok.setScaleY(Manager.ratio_height);
			
			btn_no_unclicked = CCSprite.sprite("ranking/btn_no_unclicked.png");
			btn_no_clicked = CCSprite.sprite("ranking/btn_no_clicked.png");
			btn_no = CCMenuItemSprite.item(btn_no_unclicked, btn_no_clicked, this, "clickedNo");
			btn_no.setScaleX(Manager.ratio_width);
			btn_no.setScaleY(Manager.ratio_height);
			
			menu_popup = CCMenu.menu(btn_ok, btn_no);
			menu_popup.setAnchorPoint(0f,0f);
			menu_popup.setPosition(364 * Manager.ratio_width, 582 * Manager.ratio_height);
			menu_popup.alignItemsHorizontally(100f * Manager.ratio_width);
			
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
					newMenu.setAnchorPoint(0f, 0f);
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
					newMenu.setAnchorPoint(0f, 0f);
					this.addChild(newMenu);
					btn_challenge[i] = newMenu;
				}
			}
		}

		CCMenuItemSprite btn_next = CCMenuItemSprite.item(btn_next_unclicked, btn_next_clicked, this, "clickedNextButton");
		CCMenuItemSprite btn_before = CCMenuItemSprite.item(btn_before_unclicked, btn_before_clicked, this, "clickedBeforeButton");
		CCMenu controlPage = CCMenu.menu(btn_before, btn_next);
		controlPage.setScaleX(Manager.ratio_width);
		controlPage.setScaleY(Manager.ratio_height);
		controlPage.alignItemsHorizontally(170 * Manager.ratio_width);
		controlPage.setPosition(360 * Manager.ratio_width, 484 * Manager.ratio_height);
		controlPage.setAnchorPoint(0f, 0f);
		this.addChild(controlPage);


		CCMenuItemSprite btn_random_game = CCMenuItemSprite.item(btn_goRandomGame_unclicked, btn_goRandomGame_clicked, this, "clickedRandomGameButton");
		CCMenu otherMenu = CCMenu.menu(btn_random_game);
		otherMenu.alignItemsHorizontally(100 * Manager.ratio_width);
		otherMenu.setPosition(360 * Manager.ratio_width, 197 * Manager.ratio_height);
		otherMenu.setScaleX(Manager.ratio_width);
		otherMenu.setScaleY(Manager.ratio_height);
		otherMenu.setAnchorPoint(0f, 0f);
		this.addChild(otherMenu);

		CCMenuItemSprite btn_setting = CCMenuItemSprite.item(btn_setting_unclicked, btn_setting_clicked, this, "clickedSettingButton");
		CCMenu settingMenu = CCMenu.menu(btn_setting);
		settingMenu.alignItemsHorizontally(100 * Manager.ratio_width);
		settingMenu.setPosition(631 * Manager.ratio_width, 81 * Manager.ratio_height);
		settingMenu.setScaleX(Manager.ratio_width);
		settingMenu.setScaleY(Manager.ratio_height);
		settingMenu.setAnchorPoint(0f, 0f);
		this.addChild(settingMenu);

		currentPage = CCLabel.makeLabel("" + 1, "Arial", 45);
		currentPage.setScaleX(Manager.ratio_width);
		currentPage.setScaleY(Manager.ratio_height);
		currentPage.setPosition(330 * Manager.ratio_width, 484 * Manager.ratio_height);
		this.addChild(currentPage);

		totalPage = CCLabel.makeLabel("" + (1 + numOfTotalEntries/numOfEntryPerOnePage), "Arial", 45);
		totalPage.setScaleX(Manager.ratio_width);
		totalPage.setScaleY(Manager.ratio_height);
		totalPage.setPosition(390 * Manager.ratio_width, 484 * Manager.ratio_height);
		this.addChild(totalPage);
		updatePage();
	}
	@Override
	public void onDestroy() {
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
		//		CCLabelAtlas[] ranking;
		ranking = null;

		// btn_challenge[user][0] = offline, btn_challenge[user][1] = online
		btn_challenge = null;
		currentPage = null;
		totalPage = null;
		successiveWins = null;

		//		LayerDestroyManager.getInstance().removeLayer(this);
	}
	
	public void onMatched(User enemy) {
		SoundEngine.sharedEngine().pauseSound();
		// TODO Auto-generated method stub
		CurrentUserInformation.opponentchar = enemy.character;
		CurrentUserInformation.opponentID = enemy.id;

		CommonUtils.debug("onMatched " + enemy.id);
		Intent intent = new Intent(CCDirector.sharedDirector().getActivity(), GameActivity.class);
		CCDirector.sharedDirector().getActivity().startActivity(intent);
//		CCDirector.sharedDirector().getActivity().finish();
	}

	//	protected void finalize() throws Throwable {
	//		onDestroy();
	//		
	//		super.finalize();
	//	}
	
	long _attached_time = 0;
	@Override
	public long getTime() {
		return _attached_time;
	}
	@Override
	public void setTime(long time) {
		_attached_time = time;
	}
}
