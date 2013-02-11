package contest.lab.gala;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCLabelAtlas;
import org.cocos2d.nodes.CCSprite;

public class ReadyToFightLayer extends CCLayer{
	
	int numOfEntryPerOnePage = 4;
	
	
	CCSprite bg_readyLayer;
	CCSprite bg_rankInfo;
	CCSprite btn_next_clicked;
	CCSprite btn_next_unclicked;
	CCSprite btn_before_clicked;
	CCSprite btn_before_unclicked;
	CCSprite btn_goRandomGame;
	CCSprite btn_setting;
	
	// characters[user][0] = character 0 이미지, 
	// characters[user][1] = character 1 이미지, ...
	CCSprite[][] characters = new CCSprite[numOfEntryPerOnePage][4];
	CCLabel[] userIDs = new CCLabel[numOfEntryPerOnePage];
	CCLabelAtlas[] userScores = new CCLabelAtlas[numOfEntryPerOnePage];
	
	// btn_challenge[user][0] = offline, btn_challenge[user][1] = online
	CCSprite[][] btn_challenge = new CCSprite[numOfEntryPerOnePage][2];
	
	Manager m;	// 사용자이 아이디, 캐릭터
	
	
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
		
//		// 캐릭터 스프라이트 세팅 
//		for(int i = 0; i < numOfEntryPerOnePage; i++)
//		{
//			for(int j = 0; j < 4; j++)
//			{
//				characters[i][j] = CCSprite.sprite(String.format("character/icon_char%d.png", j));
//				characters[i][j].setPosition(100, 200 + i * 100);
//				this.addChild(characters[i][j]);
//			}
//		}
//		// on, offline 버튼 세팅
//		for(int i = 0; i < numOfEntryPerOnePage; i++)
//		{
//			for(int j = 0; j < 2; j++)
//			{
//				btn_challenge[i][j] = CCSprite.sprite(String.format("btn_challenge%d.png", j));
//				btn_challenge[i][j].setPosition(500, 200 + i * 100);
//				this.addChild(btn_challenge[i][j]);
//			}
//		}
	}
	// 해당 user의 설정된 캐릭터를 보여줌
	public void setUserCharacter(int user, int character)
	{
		for(int i = 0; i < 4; i++)
		{
			if(i == character)
				characters[user][i].setVisible(true);
			else
				characters[user][i].setVisible(false);
		}
	}
	public void setUserState(int user, boolean isOnline)
	{
		if(isOnline)
			btn_challenge[user][1].setVisible(true);		
		else
			btn_challenge[user][1].setVisible(false);
	}	
}
