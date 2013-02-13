package contest.lab.gala.item;

import org.cocos2d.nodes.CCSprite;

import contest.lab.gala.Manager;
import contest.lab.gala.SkillGageLayer;

public class Gum extends Item{

	public Gum()
	{
		image = new CCSprite("minigame/item_gum.png");
	}
	public void acquireGage()
	{
		SkillGageLayer.gage += Manager.acquired_gage_per_gum;
		if(SkillGageLayer.gage > 100)
			SkillGageLayer.gage = 100;
		SkillGageLayer.updateGageBar();
		SkillGageLayer.updateSkillBtns();
	}
}
