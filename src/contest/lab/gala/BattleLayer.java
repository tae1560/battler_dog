package contest.lab.gala;

import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCSprite;

import contest.lab.gala.callback.GetDamagedCallback;
import contest.lab.gala.data.CurrentUserInformation;
import contest.lab.gala.data.SkillType;

public class BattleLayer extends CCLayer implements GetDamagedCallback{
	CCSprite bg_battlelayer = CCSprite.sprite("battle/bg_battlelayer.png");
	CCSprite bg_hp_bar = CCSprite.sprite("battle/bg_hp_bar.png");
	CCSprite hp_bar_mine = CCSprite.sprite("battle/hp_bar.png");
	CCSprite hp_bar_opponent = CCSprite.sprite("battle/hp_bar.png");
	CCSprite title = CCSprite.sprite("battle/title.png");
	static float hp;
	Manager m;
	
	CCSequence demagedSequence_bark;
	CCSequence demagedSequence_bone;
	CCSequence demagedSequence_punch;
	
	CCSequence attackSequence_bark;
	CCSequence attackSequence_bone;
	CCSequence attackSequence_punch;
	
	CCAnimation attackAnimation;
	CCAnimation demagedAnimation;
	CCAnimate attackAnimate;
	CCAnimate demagedAnimate;
	
	CCAnimation normalAnimation_mine;
	CCAnimation normalAnimation_opponent;
	CCAnimate normalAnimate_mine;
	CCAnimate normalAnimate_opponent;
	
	CCSprite myCharacter;
	CCSprite opponentCharacter;
	
	public void makeNormalAnimation()
	{
		normalAnimation_mine = CCAnimation.animation("animation1");
		myCharacter = CCSprite.sprite(String.format("char%d_normal0.png", CurrentUserInformation.userID));
		for(int i = 1; i <= 5; i++ )
		{
			CCSprite frame = CCSprite.sprite(String.format("char%d_normal%d", CurrentUserInformation.userID, i));
			frame.setScaleX(Manager.ratio_width);
			frame.setScaleY(Manager.ratio_height);
			normalAnimation_mine.addFrame(frame.getTexture());
		}
		normalAnimate_mine = CCAnimate.action(normalAnimation_mine, true);
		
		normalAnimation_opponent = CCAnimation.animation("animation2");
		myCharacter = CCSprite.sprite(String.format("char%d_normal0.png", CurrentUserInformation.opponentID));
		for(int i = 1; i <= 5; i++ )
		{
			CCSprite frame = CCSprite.sprite(String.format("char%d_normal%d", CurrentUserInformation.opponentID, i));
			frame.setScaleX(Manager.ratio_width);
			frame.setScaleY(Manager.ratio_height);
			normalAnimation_opponent.addFrame(frame.getTexture());
		}
		normalAnimate_opponent = CCAnimate.action(normalAnimation_opponent, true);
	}
	
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
