package contest.lab.gala;

import java.util.ArrayList;

import org.cocos2d.nodes.CCDirector;

import contest.lab.gala.data.User;

public class Manager {
	
	// Galaxy 3 해상도에 대한 본 화면의 해상도의 비율
	static float ratio_width;	
	static float ratio_height;
	
	static float acquired_gage_per_bone = 1;
	static float acquired_gage_per_gum = 2;
	static float acquired_gage_per_redbull = 3;
	
	static float required_gage_for_skill_bark = 10;
	static float required_gage_for_skill_bone = 15;
	static float required_gage_for_skill_punch = 20;
	
	static float damaged_gage_per_attack_bark = 10;
	static float damaged_gage_per_attack_bone = 20;
	static float damaged_gage_per_attack_punch = 30;
	
	static float full_gage = 100;
	static float full_hp = 100;
	
	static float position_of_btn_bone_x = 135;
	static float position_of_btn_bone_y = 136;
	
	static float position_of_btn_gum_x = 360;
	static float position_of_btn_gum_y = 136;
	
	static float position_of_btn_redbull_x = 585;
	static float position_of_btn_redbull_y = 136;
	
	static ArrayList<User> friendList;
	
	public Manager()
	{
		ratio_width = CCDirector.sharedDirector().displaySize().width / 720;
		ratio_height = CCDirector.sharedDirector().displaySize().height / 1280;
	}
}
