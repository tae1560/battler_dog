package contest.lab.gala;

import org.cocos2d.nodes.CCDirector;

public class Manager {
	// Galaxy 3 해상도에 대한 본 화면의 해상도의 비율
	float ratio_width;	
	float ratio_height;
	
	static float acquired_gage_per_bone = 5;
	static float acquired_gage_per_gum = 7;
	static float acquired_gage_per_redbull = 9;
	
	static float required_gage_for_skill_bark = 20;
	static float required_gage_for_skill_bone = 40;
	static float required_gage_for_skill_punch = 60;
	
	static float damaged_gage_per_attack_bark = 10;
	static float damaged_gage_per_attack_bone = 20;
	static float damaged_gage_per_attack_punch = 30;
	
	static float full_gage = 100;
	
	public Manager()
	{
		ratio_width = CCDirector.sharedDirector().displaySize().width / 720;
		ratio_height = CCDirector.sharedDirector().displaySize().height / 1280;
	}
}
