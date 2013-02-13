package contest.lab.gala.item;

import org.cocos2d.nodes.CCSprite;

import contest.lab.gala.Manager;
import contest.lab.gala.BattleLayer;

public class Bone extends Item {

	public Bone()
	{
		image = new CCSprite("minigame/item_bone.png");
	}
	public void acquireGage()
	{
		BattleLayer.gage += Manager.acquired_gage_per_bone;
		if(BattleLayer.gage > 100)
			BattleLayer.gage = 100;
		BattleLayer.updateGageBar();
		BattleLayer.updateSkillBtns();
	}
}