package contest.lab.gala;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCSprite;

public class BattleLayer extends CCLayer{
	CCSprite bg_battlelayer = CCSprite.sprite("battle/bg_battlelayer.png");
	CCSprite bg_hp_bar = CCSprite.sprite("battle/bg_hp_bar.png");
	CCSprite hp_bar_mine = CCSprite.sprite("battle/hp_bar.png");
	CCSprite hp_bar_opponent = CCSprite.sprite("battle/hp_bar.png");
	
	static float hp;
	Manager m;
	
	public BattleLayer()
	{
		m = new Manager();
		
		bg_battlelayer.setPosition(360 * m.ratio_width, 1057 * m.ratio_height);
		bg_battlelayer.setScaleX(m.ratio_width);
		bg_battlelayer.setScaleY(m.ratio_height);
		this.addChild(bg_battlelayer);
		
		bg_hp_bar.setPosition(360 * m.ratio_width, 1240 * m.ratio_height);
		bg_hp_bar.setScaleX(m.ratio_width);
		bg_hp_bar.setScaleY(m.ratio_height);
		this.addChild(bg_hp_bar);
		
		hp_bar_mine.setPosition(174 * m.ratio_width, 1249 * m.ratio_height);
		hp_bar_mine.setScaleX(m.ratio_width);
		hp_bar_mine.setScaleY(m.ratio_height);
		this.addChild(hp_bar_mine);
		
		hp_bar_opponent.setPosition(546 * m.ratio_width, 1249 * m.ratio_height);
		hp_bar_opponent.setScaleX(m.ratio_width);
		hp_bar_opponent.setScaleY(m.ratio_height);
		this.addChild(hp_bar_opponent);
	}
}
