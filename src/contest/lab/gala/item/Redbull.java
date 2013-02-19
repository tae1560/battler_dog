package contest.lab.gala.item;

import org.cocos2d.nodes.CCSprite;

import contest.lab.gala.BattleLayer;
import contest.lab.gala.Manager;

public class Redbull extends Item{
	public Redbull()
	{
		image = new CCSprite("minigame/item_redbull.png");
	}
	public void acquireGage()
	{
		BattleLayer.gage += Manager.acquired_gage_per_redbull;
		if(BattleLayer.gage >= 100)
			BattleLayer.gage = 100;
		BattleLayer.updateGageBar();
		BattleLayer.updateSkillBtns();
	}
}
