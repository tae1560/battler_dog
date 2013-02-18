package contest.lab.gala;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItemSprite;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.sound.SoundEngine;

import android.content.Intent;

public class SettingLayer extends CCLayer{
	CCSprite[] volumeLevels_bg = null;
	CCSprite[] volumeLevels_ef = null;
	
	CCSprite bg_setting = null;
	
	CCMenuItemSprite btn_next_bg = null;
	CCMenu menu_next_bg = null;
	CCMenuItemSprite btn_next_ef = null;
	CCMenu menu_next_ef = null;
	CCMenuItemSprite btn_before_bg = null;
	CCMenu menu_before_bg = null;
	CCMenuItemSprite btn_before_ef = null;
	CCMenu menu_before_ef = null;
	CCSprite bg_btn_next_unclicked = null;
	CCSprite bg_btn_next_clicked = null;
	CCSprite bg_btn_before_unclicked = null;
	CCSprite bg_btn_before_clicked = null;
	CCSprite ef_btn_next_unclicked = null;
	CCSprite ef_btn_next_clicked = null;
	CCSprite ef_btn_before_unclicked = null;
	CCSprite ef_btn_before_clicked = null;
	
	CCMenuItemSprite btn_cancel = null;
	CCMenu menu_cancel = null;
	CCSprite btn_cancel_clicked = null;
	CCSprite btn_cancel_unclicked = null;
	
	CCMenuItemSprite btn_logout = null;
	CCMenu menu_logout = null;
	CCSprite btn_logout_clicked = null;
	CCSprite btn_logout_unclicked = null;
	
	int levelOfBg;
	int levelOfEf;
	
	public static CCScene makeScene()
	{
		CCScene scene = CCScene.node();
		CCLayer layer = new SettingLayer();
		scene.addChild(layer);
		return scene;
	}
	SettingLayer()
	{
		levelOfBg = Manager.backgroundLevel;
		
		
		this.setIsTouchEnabled(true);
		
		bg_setting = CCSprite.sprite("setting/bg_setting.png");
		bg_setting.setScaleX(Manager.ratio_width);
		bg_setting.setScaleY(Manager.ratio_height);
		bg_setting.setPosition(360 * Manager.ratio_width, 640 * Manager.ratio_height);
		this.addChild(bg_setting);
	
		bg_btn_next_unclicked = CCSprite.sprite("setting/btn_next_unclicked.png");
		bg_btn_next_clicked = CCSprite.sprite("setting/btn_next_clicked.png");
		btn_next_bg = CCMenuItemSprite.item(bg_btn_next_unclicked, bg_btn_next_clicked, this, "clickedBgNext");
		btn_next_bg.setScaleX(Manager.ratio_width);
		btn_next_bg.setScaleY(Manager.ratio_height);
		menu_next_bg = CCMenu.menu(btn_next_bg);
		menu_next_bg.setAnchorPoint(0f, 0f);
		menu_next_bg.setPosition(519 * Manager.ratio_width, 1006 * Manager.ratio_height);
		this.addChild(menu_next_bg);
		
		bg_btn_before_unclicked = CCSprite.sprite("setting/btn_before_unclicked.png");
		bg_btn_before_clicked = CCSprite.sprite("setting/btn_before_clicked.png");
		btn_before_bg = CCMenuItemSprite.item(bg_btn_before_unclicked, bg_btn_before_clicked, this, "clickedBgBefore");
		btn_before_bg.setScaleX(Manager.ratio_width);
		btn_before_bg.setScaleY(Manager.ratio_height);
		menu_before_bg = CCMenu.menu(btn_before_bg);
		menu_before_bg.setAnchorPoint(0f, 0f);
		menu_before_bg.setPosition(175 * Manager.ratio_width, 1006 * Manager.ratio_height);
		this.addChild(menu_before_bg);
		
		ef_btn_next_unclicked = CCSprite.sprite("setting/btn_next_unclicked.png");
		ef_btn_next_clicked = CCSprite.sprite("setting/btn_next_clicked.png");
		btn_next_ef = CCMenuItemSprite.item(ef_btn_next_unclicked, ef_btn_next_clicked, this, "clickedEfNext");
		btn_next_ef.setScaleX(Manager.ratio_width);
		btn_next_ef.setScaleY(Manager.ratio_height);
		menu_next_ef = CCMenu.menu(btn_next_ef);
		menu_next_ef.setAnchorPoint(0f, 0f);
		menu_next_ef.setPosition(519 * Manager.ratio_width, 870 * Manager.ratio_height);
		this.addChild(menu_next_ef);
		
		ef_btn_before_unclicked = CCSprite.sprite("setting/btn_before_unclicked.png");
		ef_btn_before_clicked = CCSprite.sprite("setting/btn_before_clicked.png");
		btn_before_ef = CCMenuItemSprite.item(ef_btn_before_unclicked, ef_btn_before_clicked, this, "clickedEfBefore");
		btn_before_ef.setScaleX(Manager.ratio_width);
		btn_before_ef.setScaleY(Manager.ratio_height);
		menu_before_ef = CCMenu.menu(btn_before_ef);
		menu_before_ef.setAnchorPoint(0f, 0f);
		menu_before_ef.setPosition(175 * Manager.ratio_width, 870 * Manager.ratio_height);
		this.addChild(menu_before_ef);
		
		btn_cancel_clicked = CCSprite.sprite("setting/btn_cancel_clicked.png");
		btn_cancel_unclicked = CCSprite.sprite("setting/btn_cancel_unclicked.png");
		btn_cancel = CCMenuItemSprite.item(btn_cancel_unclicked, btn_cancel_clicked, this, "clickedCancel");
		btn_cancel.setScaleX(Manager.ratio_width);
		btn_cancel.setScaleY(Manager.ratio_height);
		menu_cancel = CCMenu.menu(btn_cancel);
		menu_cancel.setAnchorPoint(0f, 0f);
		menu_cancel.setPosition(633 * Manager.ratio_width, 81 * Manager.ratio_height);
		this.addChild(menu_cancel);
		
		btn_logout_clicked = CCSprite.sprite("setting/btn_logout_clicked.png");
		btn_logout_unclicked = CCSprite.sprite("setting/btn_logout_unclicked.png");
		btn_logout = CCMenuItemSprite.item(btn_logout_unclicked, btn_logout_clicked, this, "clickedLogout");
		btn_logout.setScaleX(Manager.ratio_width);
		btn_logout.setScaleY(Manager.ratio_height);
		menu_logout = CCMenu.menu(btn_logout);
		menu_logout.setAnchorPoint(0f, 0f);
		menu_logout.setPosition(467 * Manager.ratio_width,746 * Manager.ratio_height);
		this.addChild(menu_logout);
			
		//////////////////////////////////////////////////////////////////////////////
		volumeLevels_bg = new CCSprite[5];
		volumeLevels_ef = new CCSprite[5];
		
		volumeLevels_bg[0] = CCSprite.sprite("setting/selected.png");
		volumeLevels_bg[0].setScaleX(Manager.ratio_width);
		volumeLevels_bg[0].setScaleY(Manager.ratio_height);
		volumeLevels_ef[0] = CCSprite.sprite("setting/selected.png");
		volumeLevels_ef[0].setScaleX(Manager.ratio_width);
		volumeLevels_ef[0].setScaleY(Manager.ratio_height);
		
		for(int i = 1; i < 5; i++)
		{
			volumeLevels_bg[i] = CCSprite.sprite("setting/selected.png");
			volumeLevels_bg[i].setScaleX(Manager.ratio_width);
			volumeLevels_bg[i].setScaleY(Manager.ratio_height);
			
			volumeLevels_ef[i] = CCSprite.sprite("setting/selected.png");
			volumeLevels_ef[i].setScaleX(Manager.ratio_width);
			volumeLevels_ef[i].setScaleY(Manager.ratio_height);
		}
		
		volumeLevels_bg[0].setPosition(220 * Manager.ratio_width, 1007 * Manager.ratio_height);
		volumeLevels_bg[1].setPosition(276 * Manager.ratio_width, 1007 * Manager.ratio_height);
		volumeLevels_bg[2].setPosition(335 * Manager.ratio_width, 1007 * Manager.ratio_height);
		volumeLevels_bg[3].setPosition(393 * Manager.ratio_width, 1007 * Manager.ratio_height);
		volumeLevels_bg[4].setPosition(463 * Manager.ratio_width, 1007 * Manager.ratio_height);
		
		volumeLevels_ef[0].setPosition(220 * Manager.ratio_width, 871 * Manager.ratio_height);
		volumeLevels_ef[1].setPosition(276 * Manager.ratio_width, 871 * Manager.ratio_height);
		volumeLevels_ef[2].setPosition(335 * Manager.ratio_width, 871 * Manager.ratio_height);
		volumeLevels_ef[3].setPosition(393 * Manager.ratio_width, 871 * Manager.ratio_height);
		volumeLevels_ef[4].setPosition(463 * Manager.ratio_width, 871 * Manager.ratio_height);
		
		for(int i = 0; i < 5; i++)
		{
			volumeLevels_bg[i].setVisible(false);
			volumeLevels_ef[i].setVisible(false);
			this.addChild(volumeLevels_bg[i]);
			this.addChild(volumeLevels_ef[i]);
		}
		
		initBgLevel();
		initEfLevel();
		
		SoundEngine.sharedEngine().setSoundVolume(Manager.backgroundLevel * 0.25f);
		SoundEngine.sharedEngine().playSound(CCDirector.sharedDirector().getActivity(), R.raw.test_background, true);
	}
	
	public void initBgLevel()
	{
		for(int i = 0; i < 5; i++)
		{
			if(i == levelOfBg)
				volumeLevels_bg[i].setVisible(true);
			else
				volumeLevels_bg[i].setVisible(false);
		}
	}
	public void initEfLevel()
	{
		for(int i = 0; i < 5; i++)
		{
			if(i == levelOfEf)
				volumeLevels_ef[i].setVisible(true);
			else
				volumeLevels_ef[i].setVisible(false);
		}
	}
	public void clickedBgBefore(Object sender)
	{
		if(levelOfBg != 0)
		{
			levelOfBg--;
			for(int i = 0; i < 5; i++)
			{
				if(i == levelOfBg)
					volumeLevels_bg[i].setVisible(true);
				else
					volumeLevels_bg[i].setVisible(false);
			}
			Manager.backgroundLevel = levelOfBg;
			SoundEngine.sharedEngine().setSoundVolume(Manager.backgroundLevel * 0.25f);
		}
	}
	public void clickedBgNext(Object sender)
	{
		if(levelOfBg != 4)
		{
			levelOfBg++;
			for(int i = 0; i < 5; i++)
			{
				if(i == levelOfBg)
					volumeLevels_bg[i].setVisible(true);
				else
					volumeLevels_bg[i].setVisible(false);
			}
			Manager.backgroundLevel = levelOfBg;
			SoundEngine.sharedEngine().setSoundVolume(Manager.backgroundLevel * 0.25f);
		}
	}
	public void clickedEfBefore(Object sender)
	{
		if(levelOfEf != 0)
		{
			levelOfEf--;
			for(int i = 0; i < 5; i++)
			{
				if(i == levelOfEf)
					volumeLevels_ef[i].setVisible(true);
				else
					volumeLevels_ef[i].setVisible(false);
			}
			Manager.effectLevel = levelOfEf;
			SoundEngine.sharedEngine().setEffectsVolume(Manager.effectLevel * 0.25f);
		}
	}
	public void clickedEfNext(Object sender)
	{
		if(levelOfEf != 4)
		{
			levelOfEf++;
			for(int i = 0; i < 5; i++)
			{
				if(i == levelOfEf)
					volumeLevels_ef[i].setVisible(true);
				else
					volumeLevels_ef[i].setVisible(false);
			}
			Manager.effectLevel = levelOfEf;
			SoundEngine.sharedEngine().setEffectsVolume(Manager.effectLevel * 0.25f);
		}
	}
	public void clickedCancel(Object sender)
	{
//		CCScene scene = ReadyToFightLayer.makeScene();
//		CCDirector.sharedDirector().replaceScene(scene);
	}
	public void clickedLogout(Object sender)
	{
		////// 서버에게 로그아웃 메시지 보내기
		Intent intent = new Intent(CCDirector.sharedDirector().getActivity(), MainActivity.class);
		CCDirector.sharedDirector().getActivity().startActivity(intent);
	}
}
