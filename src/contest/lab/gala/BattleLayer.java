package contest.lab.gala;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.base.CCFiniteTimeAction;
import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.instant.CCCallFuncN;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCMoveBy;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;

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
	
	CCSprite attack_bark_opponent = CCSprite.sprite("character/attack_bark.png");
	CCSprite attack_bark_mine = CCSprite.sprite("character/attack_bark.png");
	
	CCSprite attack_bone_opponent = CCSprite.sprite("character/attack_bone.png");
	CCSprite attack_bone_mine = CCSprite.sprite("character/attack_bone.png");
	
	CCSprite attack_punch_opponent = CCSprite.sprite("character/attack_punch.png");
	CCSprite attack_punch_mine = CCSprite.sprite("character/attack_punch.png");
	
	CCFiniteTimeAction demagedAction_bark;
	CCAction demagedAction_bone;
	CCAction demagedAction_punch;
	
	CCFiniteTimeAction attackAction_bark;
	CCAction attackAction_bone;
	CCAction attackAction_punch;
	
	CCSequence demagedSequence_bark;
	CCSequence demagedSequence_bone;
	CCSequence demagedSequence_punch;
	
	CCSequence attackSequence_bark;
	CCSequence attackSequence_bone;
	CCSequence attackSequence_punch;
	
	CCAnimation attackAnimation_mine;
	CCAnimation attackAnimation_opponent;
	CCAnimate attackAnimate_mine;
	CCAnimate attackAnimate_opponent;
	
	CCAnimation demagedAnimation_mine;
	CCAnimation demagedAnimation_opponent;
	CCAnimate demagedAnimate_mine;
	CCAnimate demagedAnimate_opponent;
	
	CCAnimation normalAnimation_mine;
	CCAnimation normalAnimation_opponent;
	CCAnimate normalAnimate_mine;
	CCAnimate normalAnimate_opponent;
	
	CCSprite myCharacter ;
	CCSprite opponentCharacter;
	
	public void makeNormalAnimation()
	{
		normalAnimation_mine = CCAnimation.animation("animation1");
		myCharacter = CCSprite.sprite(String.format("character/char%d_normal0.png", CurrentUserInformation.userID));
		for(int i = 1; i <= 5; i++ )
		{
			CCSprite frame = CCSprite.sprite(String.format("character/char%d_normal%d", CurrentUserInformation.userID, i));
			frame.setScaleX(-1 * Manager.ratio_width);
			frame.setScaleY(Manager.ratio_height);
			normalAnimation_mine.addFrame(frame.getTexture());
		}
		normalAnimate_mine = CCAnimate.action(normalAnimation_mine, true);
		
		normalAnimation_opponent = CCAnimation.animation("animation2");
		myCharacter = CCSprite.sprite(String.format("character/char%d_normal0.png", CurrentUserInformation.opponentID));
		for(int i = 1; i <= 5; i++ )
		{
			CCSprite frame = CCSprite.sprite(String.format("character/char%d_normal%d", CurrentUserInformation.opponentID, i));
			frame.setScaleX(Manager.ratio_width);
			frame.setScaleY(Manager.ratio_height);
			normalAnimation_opponent.addFrame(frame.getTexture());
		}
		normalAnimate_opponent = CCAnimate.action(normalAnimation_opponent, true);
	}
	
	public void makeAttackAnimation()
	{
		attackAnimation_mine = CCAnimation.animation("animation3");
		myCharacter = CCSprite.sprite(String.format("character/char%d_attack0.png", CurrentUserInformation.userID));
		for(int i = 1; i <= 5; i++ )
		{
			CCSprite frame = CCSprite.sprite(String.format("character/char%d_attack%d", CurrentUserInformation.userID, i));
			frame.setScaleX(-1 * Manager.ratio_width);
			frame.setScaleY(Manager.ratio_height);
			attackAnimation_mine.addFrame(frame.getTexture());
		}
		attackAnimate_mine = CCAnimate.action(attackAnimation_mine, true);
		
		attackAnimation_opponent = CCAnimation.animation("animation4");
		myCharacter = CCSprite.sprite(String.format("character/char%d_attack0.png", CurrentUserInformation.opponentID));
		for(int i = 1; i <= 5; i++ )
		{
			CCSprite frame = CCSprite.sprite(String.format("character/char%d_attack%d", CurrentUserInformation.opponentID, i));
			frame.setScaleX(Manager.ratio_width);
			frame.setScaleY(Manager.ratio_height);
			attackAnimation_opponent.addFrame(frame.getTexture());
		}
		attackAnimate_opponent = CCAnimate.action(attackAnimation_opponent, true);
		
	}
	
	public void makeDemagedAnimation()
	{
		demagedAnimation_mine = CCAnimation.animation("animation5");
		myCharacter = CCSprite.sprite(String.format("char%d_demaged0.png", CurrentUserInformation.userID));
		for(int i = 1; i <= 5; i++ )
		{
			CCSprite frame = CCSprite.sprite(String.format("char%d_demaged%d", CurrentUserInformation.userID, i));
			frame.setScaleX(-1 * Manager.ratio_width);
			frame.setScaleY(Manager.ratio_height);
			demagedAnimation_mine.addFrame(frame.getTexture());
		}
		demagedAnimate_mine = CCAnimate.action(demagedAnimation_mine, true);
		
		demagedAnimation_opponent = CCAnimation.animation("animation6");
		myCharacter = CCSprite.sprite(String.format("char%d_demaged0.png", CurrentUserInformation.opponentID));
		for(int i = 1; i <= 5; i++ )
		{
			CCSprite frame = CCSprite.sprite(String.format("char%d_demaged%d", CurrentUserInformation.opponentID, i));
			frame.setScaleX(Manager.ratio_width);
			frame.setScaleY(Manager.ratio_height);
			demagedAnimation_opponent.addFrame(frame.getTexture());
		}
		demagedAnimate_opponent = CCAnimate.action(demagedAnimation_opponent, true);
	}
	public void makeActions()
	{
		demagedAction_bark = CCMoveBy.action(1, CGPoint.make(-200, 0));
		attackAction_bark = CCMoveBy.action(1, CGPoint.make(200, 0));
	}
	public void makeSequences()
	{
		CCCallFuncN afterDemaged_mine = CCCallFuncN.action(this, "runDamagedAnimation_mine");
		demagedSequence_bark = CCSequence.actions(demagedAction_bark, afterDemaged_mine);
//		demagedSequence_bone;
//		demagedSequence_punch;
		
		CCCallFuncN afterDamaged_opponent = CCCallFuncN.action(this, "runDamagedAnmation_opponent");
		attackSequence_bark = CCSequence.actions(attackAction_bark, afterDamaged_opponent);
//		attackSequence_bone;
//		attackSequence_punch;
			
	}
	public BattleLayer()
	{
		makeNormalAnimation();
		makeAttackAnimation();
		makeDemagedAnimation();
		makeActions();
		makeSequences();
		
		myCharacter = CCSprite.sprite(String.format("char%d_normal0.png", CurrentUserInformation.userChar));
		myCharacter.setScaleX(Manager.ratio_width);
		myCharacter.setScaleY(Manager.ratio_height);
		this.addChild(myCharacter);
		
		opponentCharacter = CCSprite.sprite(String.format("char%d_normal0.png", CurrentUserInformation.opponentchar));
		opponentCharacter.setScaleX(Manager.ratio_width);
		opponentCharacter.setScaleY(Manager.ratio_height);
		this.addChild(opponentCharacter);
		
		bg_battlelayer.setPosition(360 * Manager.ratio_width, 1057 * Manager.ratio_height);
		bg_battlelayer.setScaleX(Manager.ratio_width);
		bg_battlelayer.setScaleY(Manager.ratio_height);
		this.addChild(bg_battlelayer);
		
		bg_hp_bar.setPosition(360 * Manager.ratio_width, 1240 * Manager.ratio_height);
		bg_hp_bar.setScaleX(-1 * Manager.ratio_width);
		bg_hp_bar.setScaleY(Manager.ratio_height);
		this.addChild(bg_hp_bar);
		
		hp_bar_mine.setPosition(174 * Manager.ratio_width, 1249 * Manager.ratio_height);
		hp_bar_mine.setScaleX(Manager.ratio_width);
		hp_bar_mine.setScaleY(Manager.ratio_height);
		this.addChild(hp_bar_mine);
		
		hp_bar_opponent.setPosition(546 * Manager.ratio_width, 1249 * Manager.ratio_height);
		hp_bar_opponent.setScaleX(Manager.ratio_width);
		hp_bar_opponent.setScaleY(Manager.ratio_height);
		this.addChild(hp_bar_opponent);
		
		title.setPosition(360 * Manager.ratio_width, 1238 * Manager.ratio_height);
		title.setScaleX(Manager.ratio_width);
		title.setScaleY(Manager.ratio_height);
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
