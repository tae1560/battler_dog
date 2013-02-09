package contest.lab.gala;

import org.cocos2d.nodes.CCSprite;

public class Bone extends Item {

	public Bone()
	{
		image = new CCSprite("item_bone.png");
	}
	public void acquireGage()
	{
		SkillGageLayer.gage += Manager.acquired_gage_per_bone;
		if(SkillGageLayer.gage > 100)
			SkillGageLayer.gage = 100;
		SkillGageLayer.updateGageBar();
		SkillGageLayer.updateSkillBtns();
	}
}