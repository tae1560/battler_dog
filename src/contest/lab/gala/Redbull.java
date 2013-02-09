package contest.lab.gala;

import org.cocos2d.nodes.CCSprite;

public class Redbull extends Item{
	
	public Redbull()
	{
		image = new CCSprite("item_redbull.png");
	}
	public void acquireGage()
	{
		SkillGageLayer.gage += Manager.acquired_gage_per_redbull;
		SkillGageLayer.updateGageBar();
	}
}
