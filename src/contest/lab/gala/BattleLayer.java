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

	CCSprite coming_bark;
	CCSprite going_bark;
	CCSprite coming_bone;
	CCSprite going_bone;
	CCSprite coming_punch;
	CCSprite going_punch;

	// 공격중인 캐릭터 이미지
	CCSprite attack_bark_opponent;
	CCSprite attack_bark_mine;

	CCSprite attack_bone_opponent;
	CCSprite attack_bone_mine;

	CCSprite attack_punch_opponent;
	CCSprite attack_punch_mine;

	CCFiniteTimeAction damagedAction;	// 날아오기

	CCFiniteTimeAction attackAction;	// 날아가기

	CCSequence damagedSequence_bark;	// 날아오고 나서 내가 맞는 애니매이션
	//	CCSequence damagedSequence_bone;
	//	CCSequence damagedSequence_punch;

	CCSequence attackSequence_bark;		// 날아가고 나서 상대방이 맞는 애니매이션
	//	CCSequence attackSequence_bone;
	//	CCSequence attackSequence_punch;

//	CCAnimation attackAnimation_mine;
//	CCAnimation attackAnimation_opponent;
//	CCAnimate attackAnimate_mine;
//	CCAnimate attackAnimate_opponent;

	CCAnimation damagedAnimation_mine;
	CCAnimation damagedAnimation_opponent;
	CCAnimate damagedAnimate_mine;
	CCAnimate damagedAnimate_opponent;

	CCAnimation normalAnimation_mine;
	CCAnimation normalAnimation_opponent;
	CCRepeatForever normalAnimate_mine;
	CCRepeatForever normalAnimate_opponent;

	CCSprite myCharacter_normal;
	CCSprite opponentCharacter_normal;
	CCSprite myCharacter_hurted;
	CCSprite opponentCharacter_hurted;

	
	public void makeNormalAnimation()
	{
		normalAnimation_mine = CCAnimation.animation("animation1");
		myCharacter_normal = CCSprite.sprite(String.format("character/char%d_normal0.png", CurrentUserInformation.userID));
		for(int i = 1; i <= 5; i++ )
		{
			CCSprite frame = CCSprite.sprite(String.format("character/char%d_normal%d", CurrentUserInformation.userID, i));
			frame.setScaleX(-1 * Manager.ratio_width);
			frame.setScaleY(Manager.ratio_height);
			normalAnimation_mine.addFrame(frame.getTexture());
		}
		normalAnimate_mine = CCRepeatForever.action(CCAnimate.action(normalAnimation_mine, true));


		normalAnimation_opponent = CCAnimation.animation("animation2");
		myCharacter_normal = CCSprite.sprite(String.format("character/char%d_normal0.png", CurrentUserInformation.opponentID));
		for(int i = 1; i <= 5; i++ )
		{
			CCSprite frame = CCSprite.sprite(String.format("character/char%d_normal%d", CurrentUserInformation.opponentID, i));
			frame.setScaleX(Manager.ratio_width);
			frame.setScaleY(Manager.ratio_height);
			normalAnimation_opponent.addFrame(frame.getTexture());
		}
		normalAnimate_opponent = CCRepeatForever.action(CCAnimate.action(normalAnimation_opponent, true));
	}

//	public void makeAttackAnimation()
//	{
//		attackAnimation_mine = CCAnimation.animation("animation3");
//		myCharacter_normal = CCSprite.sprite(String.format("character/char%d_attack0.png", CurrentUserInformation.userID));
//		for(int i = 1; i <= 5; i++ )
//		{
//			CCSprite frame = CCSprite.sprite(String.format("character/char%d_attack%d", CurrentUserInformation.userID, i));
//			frame.setScaleX(-1 * Manager.ratio_width);
//			frame.setScaleY(Manager.ratio_height);
//			attackAnimation_mine.addFrame(frame.getTexture());
//		}
//		attackAnimate_mine = CCAnimate.action(attackAnimation_mine, true);
//
//		attackAnimation_opponent = CCAnimation.animation("animation4");
//		myCharacter_normal = CCSprite.sprite(String.format("character/char%d_attack0.png", CurrentUserInformation.opponentID));
//		for(int i = 1; i <= 5; i++ )
//		{
//			CCSprite frame = CCSprite.sprite(String.format("character/char%d_attack%d", CurrentUserInformation.opponentID, i));
//			frame.setScaleX(Manager.ratio_width);
//			frame.setScaleY(Manager.ratio_height);
//			attackAnimation_opponent.addFrame(frame.getTexture());
//		}
//		attackAnimate_opponent = CCAnimate.action(attackAnimation_opponent, true);
//
//	}

	public void makeDemagedAnimation()
	{
		damagedAnimation_mine = CCAnimation.animation("animation5");
		myCharacter_normal = CCSprite.sprite(String.format("char%d_damaged0.png", CurrentUserInformation.userID));
		for(int i = 1; i <= 5; i++ )
		{
			CCSprite frame = CCSprite.sprite(String.format("char%d_damaged%d", CurrentUserInformation.userID, i));
			frame.setScaleX(-1 * Manager.ratio_width);
			frame.setScaleY(Manager.ratio_height);
			damagedAnimation_mine.addFrame(frame.getTexture());
		}
		damagedAnimate_mine = CCAnimate.action(damagedAnimation_mine, false);

		damagedAnimation_opponent = CCAnimation.animation("animation6");
		myCharacter_normal = CCSprite.sprite(String.format("char%d_damaged0.png", CurrentUserInformation.opponentID));
		for(int i = 1; i <= 5; i++ )
		{
			CCSprite frame = CCSprite.sprite(String.format("char%d_damaged%d", CurrentUserInformation.opponentID, i));
			frame.setScaleX(Manager.ratio_width);
			frame.setScaleY(Manager.ratio_height);
			damagedAnimation_opponent.addFrame(frame.getTexture());
		}
		damagedAnimate_opponent = CCAnimate.action(damagedAnimation_opponent, false);
	}
	public void makeActions()
	{
		damagedAction = CCMoveBy.action(1, CGPoint.make(-200, 0));
		attackAction = CCMoveBy.action(1, CGPoint.make(200, 0));
	}
	public void makeSequences()
	{
		CCCallFuncN afterDemaged_mine = CCCallFuncN.action(this, "runAfterDamagedAnimation_mine");
		damagedSequence_bark = CCSequence.actions(damagedAction, afterDemaged_mine);
		//		damagedSequence_bone;
		//		damagedSequence_punch;

		CCCallFuncN afterDamaged_opponent = CCCallFuncN.action(this, "runAfterDamagedAnimation_opponent");
		attackSequence_bark = CCSequence.actions(attackAction, afterDamaged_opponent);
		//		attackSequence_bone;
		//		attackSequence_punch;
	}
	public void runAfterDamagedAnimation_opponent(Object o)
	{
		opponentCharacter_normal.setVisible(false);
		this.addChild(opponentCharacter_hurted);
		/////////////////////////////
	}
	public void runAttackAnimation_mine(int kindOfAttack)
	{
		myCharacter_normal.stopAction(normalAnimate_mine);
		switch(kindOfAttack)
		{
		case 1 : 
			myCharacter_normal.setVisible(false);
//			opponentCharacter_normal.setVisible(false);
			attack_bark_mine.setVisible(true);

			going_bark.setPosition(225,1030);
			this.addChild(going_bark);
			going_bark.runAction(attackSequence_bark);
		}
	}

	public void runAttackAnimation_opponent(int kindOfAttack)
	{

	}

	public BattleLayer()
	{
		makeNormalAnimation();
//		makeAttackAnimation();
		makeDemagedAnimation();
		makeActions();
		makeSequences();

		myCharacter_normal = CCSprite.sprite(String.format("char%d_normal0.png", CurrentUserInformation.userChar));
		myCharacter_normal.setScaleX(Manager.ratio_width);
		myCharacter_normal.setScaleY(Manager.ratio_height);
		myCharacter_normal.setPosition(212 * Manager.ratio_width, 1019 * Manager.ratio_height);
		this.addChild(myCharacter_normal);
		myCharacter_normal.runAction(normalAnimate_mine);

		opponentCharacter_normal = CCSprite.sprite(String.format("char%d_normal0.png", CurrentUserInformation.opponentchar));
		opponentCharacter_normal.setScaleX(Manager.ratio_width);
		opponentCharacter_normal.setScaleY(Manager.ratio_height);
		opponentCharacter_normal.setPosition(508 * Manager.ratio_width, 1019 * Manager.ratio_height);
		this.addChild(opponentCharacter_normal);
		opponentCharacter_normal.runAction(normalAnimate_opponent);

		myCharacter_hurted = CCSprite.sprite(String.format("char%d_hurted0.png", CurrentUserInformation.userChar));
		myCharacter_hurted.setScaleX(Manager.ratio_width);
		myCharacter_hurted.setScaleY(Manager.ratio_height);
		myCharacter_hurted.setPosition(212 * Manager.ratio_width, 1019 * Manager.ratio_height);
		opponentCharacter_hurted = CCSprite.sprite(String.format("char%d_hurted0.png", CurrentUserInformation.opponentchar));
		opponentCharacter_hurted.setScaleX(Manager.ratio_width);
		opponentCharacter_hurted.setScaleY(Manager.ratio_height);
		opponentCharacter_hurted.setPosition(508 * Manager.ratio_width, 1019 * Manager.ratio_height);
		
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

		attack_bark_opponent = CCSprite.sprite(String.format("character/attack_bark%d", CurrentUserInformation.opponentchar));
		attack_bark_opponent.setScaleX(Manager.ratio_width);
		attack_bark_opponent.setScaleY(Manager.ratio_height);
		attack_bark_opponent.setVisible(false);
		this.addChild(attack_bark_opponent);
		attack_bark_mine = CCSprite.sprite(String.format("character/attack_bark%d", CurrentUserInformation.userChar));
		attack_bark_mine.setScaleX(-1 * Manager.ratio_width);
		attack_bark_mine.setScaleY(Manager.ratio_height);
		attack_bark_mine.setVisible(false);
		this.addChild(attack_bark_mine);

		attack_bone_opponent = CCSprite.sprite(String.format("character/attack_bone%d", CurrentUserInformation.opponentchar));
		attack_bone_opponent.setScaleX(Manager.ratio_width);
		attack_bone_opponent.setScaleY(Manager.ratio_height);
		attack_bone_opponent.setVisible(false);
		this.addChild(attack_bone_opponent);
		attack_bone_mine = CCSprite.sprite(String.format("character/attack_bone%d", CurrentUserInformation.userChar));
		attack_bone_mine.setScaleX(Manager.ratio_width);
		attack_bone_mine.setScaleY(-1 * Manager.ratio_height);
		attack_bone_mine.setVisible(false);
		this.addChild(attack_bone_mine);

		attack_punch_opponent = CCSprite.sprite(String.format("character/attack_bone%d", CurrentUserInformation.opponentchar));
		attack_punch_opponent.setScaleX(Manager.ratio_width);
		attack_punch_opponent.setScaleY(Manager.ratio_height);
		attack_punch_opponent.setVisible(false);
		this.addChild(attack_punch_opponent);
		attack_punch_mine = CCSprite.sprite(String.format("character/attack_bone%d", CurrentUserInformation.userChar));
		attack_punch_mine.setScaleX(Manager.ratio_width);
		attack_punch_mine.setScaleY(-1 * Manager.ratio_height);
		attack_punch_mine.setVisible(false);
		this.addChild(attack_punch_mine);

		coming_bark = CCSprite.sprite("battle/coming_bark.png");
		coming_bark.setScaleX(Manager.ratio_width);
		coming_bark.setScaleY(Manager.ratio_height);

		going_bark = CCSprite.sprite("battle/coming_bark.png");
		going_bark.setScaleX(Manager.ratio_width);
		going_bark.setScaleY(-1 * Manager.ratio_height);

		coming_bone = CCSprite.sprite("battle/coming_bone.png");
		coming_bone.setScaleX(Manager.ratio_width);
		coming_bone.setScaleY(Manager.ratio_height);

		going_bone = CCSprite.sprite("battle/coming_bone.png");
		going_bone.setScaleX(Manager.ratio_width);
		going_bone.setScaleY(-1 * Manager.ratio_height);

		coming_punch = CCSprite.sprite("battle/coming_punch.png");
		coming_punch.setScaleX(Manager.ratio_width);
		coming_punch.setScaleY(Manager.ratio_height);

		going_punch = CCSprite.sprite("battle/coming_punch.png");
		going_punch.setScaleX(Manager.ratio_width);
		going_punch.setScaleY(-1 * Manager.ratio_height);


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
