package contest.lab.gala;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;

import contest.lab.gala.callback.GetDamagedCallback;
import contest.lab.gala.data.SkillType;

import android.app.Activity;

public class BattleLayer extends CCLayer implements GetDamagedCallback{
	CCSprite bg_battlelayer = CCSprite.sprite("battle/bg_battlelayer.png");
	CCSprite bg_hp_bar = CCSprite.sprite("battle/bg_hp_bar.png");
	CCSprite hp_bar_mine = CCSprite.sprite("battle/hp_bar.png");
	CCSprite hp_bar_opponent = CCSprite.sprite("battle/hp_bar.png");
	CCSprite title = CCSprite.sprite("battle/title.png");
	static float hp;
	Manager m;
	
	public BattleLayer()
	{
		m = new Manager();
		
		bg_battlelayer.setPosition(360 * m.ratio_width, 1057 * m.ratio_height);
		bg_battlelayer.setScaleX(m.ratio_width);
		bg_battlelayer.setScaleY(m.ratio_height);
		this.addChild(bg_battlelayer);
		
		bg_hp_bar.setPosition(360 * m.ratio_width, 1240 * m.ratio_height);
		bg_hp_bar.setScaleX(m.ratio_width);
		bg_hp_bar.setScaleY(m.ratio_height);
		this.addChild(bg_hp_bar);
		
		hp_bar_mine.setPosition(174 * m.ratio_width, 1249 * m.ratio_height);
		hp_bar_mine.setScaleX(m.ratio_width);
		hp_bar_mine.setScaleY(m.ratio_height);
		this.addChild(hp_bar_mine);
		
		hp_bar_opponent.setPosition(546 * m.ratio_width, 1249 * m.ratio_height);
		hp_bar_opponent.setScaleX(m.ratio_width);
		hp_bar_opponent.setScaleY(m.ratio_height);
		this.addChild(hp_bar_opponent);
		
		title.setPosition(360 * m.ratio_width, 1238 * m.ratio_height);
		title.setScaleX(m.ratio_width);
		title.setScaleY(m.ratio_height);
		this.addChild(title);
		
		NetworkManager.getInstance().setGetDamagedCallback(this);
	}
	
	
	@Override
	///* 서버에게서 공격 정보를 받았을 때, Activity에서 호출하는 함수
	public void didGetDamaged(SkillType kindOfAttack) {
		switch(kindOfAttack.toInteger())
		{
			case 1 : 
				hp -= Manager.damaged_gage_per_attack_bark;
				SkillGageLayer.updateGageBar();
				//* 데미지 효과 애니메이션
				BattlerDogActivity.makeToast(1);
				break;
			case 2 :
				hp -= Manager.damaged_gage_per_attack_bone;
				SkillGageLayer.updateGageBar();
				//* 데미지 효과 애니메이션
				BattlerDogActivity.makeToast(2);
				break;
			case 3 :
				hp -= Manager.damaged_gage_per_attack_punch;
				SkillGageLayer.updateGageBar();
				//* 데미지 효과 애니메이션
				BattlerDogActivity.makeToast(3);
				break;
		}
	}
}
