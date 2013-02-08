package contest.lab.gala;

import org.cocos2d.nodes.CCSprite;

public class Gum extends Item{

	public Gum()
	{
		image = new CCSprite("kokomyen.png");
	}
	public void acquireGage()
	{
		SkillGageLayer.gage += Manager.acquired_gage_per_gum;
		SkillGageLayer.updateGageBar();
	}
}
