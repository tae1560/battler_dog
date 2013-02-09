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
		SkillGageLayer.updateGageBar();
	}
}