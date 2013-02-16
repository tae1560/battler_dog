package contest.lab.gala;

import java.util.ArrayList;

import org.cocos2d.nodes.CCDirector;

import contest.lab.gala.data.User;

public class Manager {
	
	// Galaxy 3 해상도에 대한 본 화면의 해상도의 비율
	public static float ratio_width;	
	public static float ratio_height;
	
	public static float acquired_gage_per_bone = 1;
	public static float acquired_gage_per_gum = 2;
	public static float acquired_gage_per_redbull = 3;
	
	public static float required_gage_for_skill_bark = 10;
	public static float required_gage_for_skill_bone = 15;
	public static float required_gage_for_skill_punch = 20;
	
	public static float damaged_gage_per_attack_bark = 10;
	public static float damaged_gage_per_attack_bone = 20;
	public static float damaged_gage_per_attack_punch = 30;
	
	public static float full_gage = 100;
	public static float full_hp = 100;
	
	public static float position_of_btn_bone_x = 135;
	public static float position_of_btn_bone_y = 136;
	
	public static float position_of_btn_gum_x = 360;
	public static float position_of_btn_gum_y = 136;
	
	public static float position_of_btn_redbull_x = 585;
	public static float position_of_btn_redbull_y = 136;
	
	public static ArrayList<User> friendList;
	
	public static boolean resultOfGame;
	public static int numOfGames;
	public static int numOfWins;
	public static int numOfLoses;
	public static int numOfSuccessiveWins;
	
	public static void setRatioes()
	{
		ratio_width = CCDirector.sharedDirector().displaySize().width / 720;
		ratio_height = CCDirector.sharedDirector().displaySize().height / 1280;
	}
}
