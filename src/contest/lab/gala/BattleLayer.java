package contest.lab.gala;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCSprite;

public class BattleLayer extends CCLayer{
	CCSprite bg_battlelayer;
	Manager m;
	
	public BattleLayer()
	{
		m = new Manager();
		
		bg_battlelayer = CCSprite.sprite("battle/bg_battlelayer.png");
		bg_battlelayer.setPosition(360 * m.ratio_width, 1057 * m.ratio_height);
		bg_battlelayer.setScaleX(m.ratio_width);
		bg_battlelayer.setScaleY(m.ratio_height);
		this.addChild(bg_battlelayer);
	}
}
