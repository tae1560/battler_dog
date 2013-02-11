package contest.lab.gala;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCLabelAtlas;
import org.cocos2d.nodes.CCSprite;

public class ReadyToFightLayer extends CCLayer{
	CCSprite bg_readyLayer;
	CCSprite bg_rankInfo;
	CCSprite btn_next_clicked;
	CCSprite btn_next_unclicked;
	CCSprite btn_before_clicked;
	CCSprite btn_before_unclicked;
	CCSprite btn_goRandomGame;
	CCSprite btn_setting;
	
	CCSprite[][] characters = new CCSprite[4][4];
	CCLabel[] userIDs = new CCLabel[4];
	CCLabelAtlas[] userScores = new CCLabelAtlas[4];
	CCSprite[][] btn_challenge = new CCSprite[4][2];
	
	public static CCScene makeScene()
	{
		CCScene scene = CCScene.node();
		
		return scene;
	}
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
	public ReadyToFightLayer()
	{
		this.setIsTouchEnabled(true);
	}
}
