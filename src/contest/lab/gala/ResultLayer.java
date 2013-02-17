package contest.lab.gala;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItemSprite;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabelAtlas;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;

import contest.lab.gala.data.CurrentUserInformation;

import android.content.Intent;

public class ResultLayer extends CCLayer {

	CCSprite result;
	CCSprite bg_result = CCSprite.sprite("result/bg_result.png");
	CCSprite btn_back_unclicked = CCSprite.sprite("result/btn_back_unclicked.png");
	CCSprite btn_back_clicked = CCSprite.sprite("result/btn_back_clicked.png");
	
	CCSprite myCharacter;
	
	CCLabelAtlas maxNumOfCombos;
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
//		btn_back.setPosition(633 * Manager.ratio_width, 81 * Manager.ratio_height);
		btn_back.setScaleX(Manager.ratio_width);
		btn_back.setScaleY(Manager.ratio_height);
		CCMenu backMenu = CCMenu.menu(btn_back);
		backMenu.setPosition(633 * Manager.ratio_width, 81 * Manager.ratio_height);
		backMenu.setAnchorPoint(0f, 0f);
		this.addChild(backMenu);
		
		numOfGames = CCLabelAtlas.label(""+Manager.numOfGames, "result/numbers_result.png", 28, 38, '0');
		numOfGames.setPosition(CGPoint.ccp(125 * Manager.ratio_width, 325 * Manager.ratio_height));
		numOfGames.setAnchorPoint(1.0f, 0.5f);
		numOfGames.setScaleX(Manager.ratio_width);
		numOfGames.setScaleY(Manager.ratio_height);
		this.addChild(numOfGames);
		
		numOfWins =  CCLabelAtlas.label(""+Manager.numOfWins, "result/numbers_result.png", 28, 38,'0');
		numOfWins.setPosition(325 * Manager.ratio_width, 325 * Manager.ratio_height);
		numOfWins.setAnchorPoint(1.0f, 0.5f);
		numOfWins.setScaleX(Manager.ratio_width);
		numOfWins.setScaleY(Manager.ratio_height);
		this.addChild(numOfWins);
		
		numOfLoses = CCLabelAtlas.label(""+Manager.numOfLoses, "result/numbers_result.png", 28, 38,'0');
		numOfLoses.setPosition(485 * Manager.ratio_width, 325 * Manager.ratio_height);
		numOfLoses.setAnchorPoint(1.0f, 0.5f);
		numOfLoses.setScaleX(Manager.ratio_width);
		numOfLoses.setScaleY(Manager.ratio_height);
		this.addChild(numOfLoses);
		
		numOfSuccessiveWins  = CCLabelAtlas.label(""+Manager.numOfSuccessiveWins, "result/numbers_wins.png", 70, 88, '0');
		numOfSuccessiveWins.setPosition(350 * Manager.ratio_width, 585 * Manager.ratio_height);
		numOfSuccessiveWins.setAnchorPoint(1.0f, 0.5f);
		numOfSuccessiveWins.setScaleX(Manager.ratio_width);
		numOfSuccessiveWins.setScaleY(Manager.ratio_height);
		this.addChild(numOfSuccessiveWins);
		
		maxNumOfCombos  = CCLabelAtlas.label(""+Manager.maxNumOfCombo, "result/numbers_combo.png", 50, 70, '0');
		maxNumOfCombos.setPosition(545 * Manager.ratio_width, 810 * Manager.ratio_height);
		maxNumOfCombos.setAnchorPoint(1.0f, 0.5f);
		maxNumOfCombos.setScaleX(Manager.ratio_width);
		maxNumOfCombos.setScaleY(Manager.ratio_height);
		this.addChild(maxNumOfCombos);
		
		myCharacter = CCSprite.sprite(String.format("character/char%d_normal1.png", CurrentUserInformation.userChar));
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
