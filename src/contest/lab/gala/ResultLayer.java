package contest.lab.gala;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItemSprite;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabelAtlas;
import org.cocos2d.nodes.CCSprite;

import contest.lab.gala.data.CurrentUserInformation;

import android.content.Intent;

public class ResultLayer extends CCLayer {

	CCSprite result;
	CCSprite bg_result = CCSprite.sprite("result/bg_result.png");
	CCSprite btn_back_unclicked = CCSprite.sprite("result/btn_back_unclicked.png");
	CCSprite btn_back_clicked = CCSprite.sprite("result/btn_back_clicked.png");
	
	CCSprite myCharacter;
	
	CCLabelAtlas numOfGames;
	CCLabelAtlas numOfWins;
	CCLabelAtlas numOfLoses;
	CCLabelAtlas numOfSuccessiveWins;
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
		
		if(Manager.resultOfGame)
			result = CCSprite.sprite("result/win.png");
		else
			result = CCSprite.sprite("result/lose.png");
		
		result.setPosition(360 * Manager.ratio_width, 1036 * Manager.ratio_height);
		result.setScaleX(Manager.ratio_width);
		result.setScaleY(Manager.ratio_height);
		this.addChild(result);
		
		CCMenuItemSprite btn_back = CCMenuItemSprite.item(btn_back_unclicked, btn_back_clicked, this, "goToFriendList");
		CCMenu backMenu = CCMenu.menu(btn_back);
		backMenu.setScaleX(Manager.ratio_width);
		backMenu.setScaleY(Manager.ratio_height);
		backMenu.setPosition(633 * Manager.ratio_width, 81 * Manager.ratio_height);
		this.addChild(backMenu);
		
		numOfGames = CCLabelAtlas.label(""+Manager.numOfGames, "result/numbers_results", 30 * (int)Manager.ratio_width, 30 * (int)Manager.ratio_height,'0');
		numOfGames.setPosition(160 * Manager.ratio_width, 360 * Manager.ratio_height);
		this.addChild(numOfGames);
		
		numOfWins =  CCLabelAtlas.label(""+Manager.numOfWins, "result/numbers_results", 30 * (int)Manager.ratio_width, 30 * (int)Manager.ratio_height,'0');
		numOfWins.setPosition(360 * Manager.ratio_width, 360 * Manager.ratio_height);
		this.addChild(numOfWins);
		
		numOfLoses = CCLabelAtlas.label(""+Manager.numOfLoses, "result/numbers_results", 30 * (int)Manager.ratio_width, 30 * (int)Manager.ratio_height,'0');
		numOfLoses.setPosition(520 * Manager.ratio_width, 360 * Manager.ratio_height);
		this.addChild(numOfLoses);
		
		numOfSuccessiveWins  = CCLabelAtlas.label(""+Manager.numOfSuccessiveWins, "result/numbers_results", 30 * (int)Manager.ratio_width, 30 * (int)Manager.ratio_height,'0');
		numOfSuccessiveWins.setPosition(430 * Manager.ratio_width, 630 * Manager.ratio_height);
		this.addChild(numOfSuccessiveWins);
		
		myCharacter = CCSprite.sprite(String.format("char%d_normal1.png", CurrentUserInformation.userChar));
		myCharacter.setScaleX(-1 * Manager.ratio_width);
		myCharacter.setScaleY(Manager.ratio_height);
		myCharacter.setPosition(210 * Manager.ratio_width, 730 * Manager.ratio_height);
		this.addChild(myCharacter);
	}
	public void goToFriendList(Object sender)
	{
		Intent intent = new Intent(CCDirector.sharedDirector().getActivity(), BattlerDogActivity.class);
		CCDirector.sharedDirector().getActivity().startActivity(intent);
	}
}
