package contest.lab.gala;

import org.cocos2d.actions.base.CCFiniteTimeAction;
import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.instant.CCCallFuncN;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCMoveBy;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCLabelAtlas;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.ccColor3B;

import android.view.MotionEvent;
import contest.lab.gala.callback.GetDamagedCallback;
import contest.lab.gala.data.CurrentUserInformation;
import contest.lab.gala.data.SkillType;
import contest.lab.gala.interfaces.LifeCycleInterface;

public class BattleLayer extends CCLayer implements GetDamagedCallback, LifeCycleInterface{
	private static CCSprite btn_skill_bark_activated = null;
	private static CCSprite btn_skill_bark_normal = null;
	private static CCSprite btn_skill_bone_activated = null;
	private static CCSprite btn_skill_bone_normal = null;
	private static CCSprite btn_skill_punch_activated = null;
	private static CCSprite btn_skill_punch_normal = null;
	
	public static CCSprite gage_bar = null;
	public static CCSprite gage_bar_black = null;
	
	public static float gage;
	public static float thresholdOfHP = 30;
	////////////////////////////////////////////////////////////////////////////
	private CCSprite bg_battlelayer = null;
	private CCSprite bg_hp_bar = null;
	private CCSprite hp_bar_mine = null;
	private CCSprite hp_bar_opponent = null;
	private CCSprite title = null;

	static float hp_mine = 100;
	static float hp_opponent = 100;
	
	int ACTION_FLYING_MINE = 100;
	int ACTION_DAMAGED_MINE= 200;
	int ACTION_ATTACK_MINE= 300;
	int ACTION_FLYING_OPPONENT= 400;
	int ACTION_DAMAGED_OPPONENT = 500;
	int ACTION_ATTACK_OPPONENT = 600;
	static int numOfCurrentActions_mine = 0;
	static int numOfCurrentActions_opponent = 0;
	
	CCCallFuncN returnToNormalMode= null;
	CCSequence damagedAndReturnToNormal_mine= null;
	CCSequence damagedAndReturnToNormal_opponent= null;
	
	CCSprite coming_bark= null;
	CCSprite going_bark= null;
	CCSprite coming_bone= null;
	CCSprite going_bone= null;
	CCSprite coming_punch= null;
	CCSprite going_punch= null;

	// ������� ĳ���� �̹���
	CCSprite attack_bark_opponent= null;
	CCSprite attack_bark_mine= null;

	CCSprite attack_bone_opponent= null;
	CCSprite attack_bone_mine= null;

	CCSprite attack_punch_opponent= null;
	CCSprite attack_punch_mine= null;

	CCFiniteTimeAction damagedAction= null;	// ���ƿ���

	CCFiniteTimeAction attackAction= null;	// ���ư���

	CCSequence damagedSequence_bark= null;	// ���ƿ��� ���� ���� �´� �ִϸ��̼�

	CCSequence attackSequence_bark= null;		// ���ư��� ���� ������ �´� �ִϸ��̼�

	CCAnimation damagedAnimation_mine= null;
	CCAnimation damagedAnimation_opponent= null;
	CCAnimate damagedAnimate_mine= null;
	CCAnimate damagedAnimate_opponent= null;

	CCAnimation normalAnimation_mine= null;
	CCAnimation normalAnimation_opponent= null;
	CCAnimate normalAnimate_mine= null;
	CCAnimate normalAnimate_opponent= null;

	CCSprite myCharacter_normal= null;
	CCSprite opponentCharacter_normal= null;
	CCSprite myCharacter_hurted= null;
	CCSprite opponentCharacter_hurted= null;

	public void makeNormalAnimation()
	{
		normalAnimation_mine = CCAnimation.animation("animation1", 0.15f);
		for(int i = 2; i <= 5; i++ )
		{
			CCSprite frame = CCSprite.sprite(String.format("character/char%d_normal%d.png", CurrentUserInformation.userChar, i));
			frame.setScaleX(-1 * Manager.ratio_width);
			frame.setScaleY(Manager.ratio_height);
			frame.setPosition(212 * Manager.ratio_width, 1019 * Manager.ratio_height);
			normalAnimation_mine.addFrame(frame.getTexture());
		}
		normalAnimate_mine = CCAnimate.action(normalAnimation_mine, false);
		
		normalAnimation_opponent = CCAnimation.animation("animation2",0.15f);
		opponentCharacter_normal = CCSprite.sprite(String.format("character/char%d_normal1.png", CurrentUserInformation.opponentchar));
		for(int i = 5; i >= 2; i-- )
		{
			CCSprite frame = CCSprite.sprite(String.format("character/char%d_normal%d.png", CurrentUserInformation.opponentchar, i));
			frame.setScaleX(Manager.ratio_width);
			frame.setScaleY(Manager.ratio_height);
			frame.setPosition(508 * Manager.ratio_width, 1019 * Manager.ratio_height);
			normalAnimation_opponent.addFrame(frame.getTexture());
		}
		normalAnimate_opponent = CCAnimate.action(normalAnimation_opponent, true);
	}
	public void makeDemagedAnimation()
	{
		damagedAnimation_mine = CCAnimation.animation("animation5", 0.15f);
		myCharacter_hurted = CCSprite.sprite(String.format("character/char%d_hurted1.png", CurrentUserInformation.userChar));
		myCharacter_hurted.setPosition(139 * Manager.ratio_width, 1019 * Manager.ratio_height);
		for(int i = 1; i <= 5; i++ )
		{
			CCSprite frame = CCSprite.sprite(String.format("character/char%d_hurted%d.png", CurrentUserInformation.userChar, i));
			frame.setScaleX(-1 * Manager.ratio_width);
			frame.setScaleY(Manager.ratio_height);
			damagedAnimation_mine.addFrame(frame.getTexture());
		}
		damagedAnimate_mine = CCAnimate.action(damagedAnimation_mine, false);

		damagedAnimation_opponent = CCAnimation.animation("animation6", 0.15f);
		opponentCharacter_hurted = CCSprite.sprite(String.format("character/char%d_hurted1.png", CurrentUserInformation.opponentchar));
		opponentCharacter_hurted.setPosition(581 * Manager.ratio_width, 1019 * Manager.ratio_height);
		for(int i = 1; i <= 5; i++ )
		{
			CCSprite frame = CCSprite.sprite(String.format("character/char%d_hurted%d.png", CurrentUserInformation.opponentchar, i));
			frame.setScaleX(Manager.ratio_width);
			frame.setScaleY(Manager.ratio_height);
			damagedAnimation_opponent.addFrame(frame.getTexture());
		}
		damagedAnimate_opponent = CCAnimate.action(damagedAnimation_opponent, false);
	}
	public CCFiniteTimeAction makeDamagedAction()
	{
		return CCMoveBy.action(1, CGPoint.make(-200, 0));
	}
	public CCFiniteTimeAction makeAttackAction()
	{
		return CCMoveBy.action(1, CGPoint.make(200, 0));
	}
	public CCSequence makeMyAttackSequence(CCFiniteTimeAction attackAction)
	{
		CCCallFuncN afterDamaged_opponent = CCCallFuncN.action(this, "runAfterDamagedAnimation_opponent");
		CCSequence attackSequence = CCSequence.actions(attackAction, afterDamaged_opponent);		
		return attackSequence;
	}
	public CCSequence makeMyDamagedSequence(CCFiniteTimeAction damagedAction)
	{
		CCCallFuncN afterDamaged_opponent = CCCallFuncN.action(this, "runAfterDamagedAnimation_mine");
		CCSequence DamagedSequence = CCSequence.actions(damagedAction, afterDamaged_opponent);
		return DamagedSequence;
	}
	public void runAfterDamagedAnimation_opponent(Object o)
	{
		opponentCharacter_normal.setVisible(false);
		this.addChild(opponentCharacter_hurted, 1, ACTION_DAMAGED_OPPONENT);
		CCCallFuncN returnToNormalMode = CCCallFuncN.action(this, "returnToNormalMode");
		CCSequence damagedAndReturnToNormal_opponent = CCSequence.actions(damagedAnimate_opponent, returnToNormalMode);
		
		opponentCharacter_hurted.runAction(damagedAndReturnToNormal_opponent);
		/////////////////////////////
	}
	public void runAfterDamagedAnimation_mine(Object o)
	{
		CCCallFuncN returnToNormalMode = CCCallFuncN.action(this, "returnToNormalMode");
		CCSequence damagedAndReturnToNormal_mine = CCSequence.actions(damagedAnimate_mine, returnToNormalMode);
		
		myCharacter_normal.setVisible(false);
		this.addChild(myCharacter_hurted, 1, ACTION_DAMAGED_MINE);
		myCharacter_hurted.runAction(damagedAndReturnToNormal_mine);
	}
	public void returnToNormalMode(Object o)
	{
		updateHPBar();
		
		int mine, opponent;
		for(mine = 0; mine < numOfCurrentActions_mine; mine++)
		{
			this.removeChildByTag(ACTION_FLYING_MINE, true);
			this.removeChildByTag(ACTION_DAMAGED_OPPONENT, true);
			this.removeChildByTag(ACTION_ATTACK_MINE, true);
		}
		for(int j = 0; j < mine; j++)
			numOfCurrentActions_mine--;
		
		for(opponent = 0; opponent < numOfCurrentActions_opponent; opponent++)
		{
			this.removeChildByTag(ACTION_FLYING_OPPONENT, true);
			this.removeChildByTag(ACTION_DAMAGED_MINE, true);
			this.removeChildByTag(ACTION_ATTACK_OPPONENT, true);
		}
		for(int j = 0; j < opponent; j++)
			numOfCurrentActions_opponent--;
		myCharacter_normal.setVisible(true);
		opponentCharacter_normal.setVisible(true);	
	}

	private void init_statics() {
		btn_skill_bark_activated = CCSprite.sprite("minigame/btn_skill_bark_activated.png");
		btn_skill_bark_normal = CCSprite.sprite("minigame/btn_skill_bark_normal.png");
		btn_skill_bone_activated = CCSprite.sprite("minigame/btn_skill_bone_activated.png");
		btn_skill_bone_normal = CCSprite.sprite("minigame/btn_skill_bone_normal.png");
		btn_skill_punch_activated = CCSprite.sprite("minigame/btn_skill_punch_activated.png");
		btn_skill_punch_normal = CCSprite.sprite("minigame/btn_skill_punch_normal.png");
		
		gage_bar = CCSprite.sprite("minigame/gage_bar.png");
		gage_bar_black = CCSprite.sprite("minigame/bg_gage_bar.png");
		
		bg_battlelayer = CCSprite.sprite("battle/bg_battlelayer.png");
		bg_hp_bar = CCSprite.sprite("battle/bg_hp_bar.png");
		hp_bar_mine = CCSprite.sprite("battle/hp_bar.png");
		hp_bar_opponent = CCSprite.sprite("battle/hp_bar.png");
		title = CCSprite.sprite("battle/title.png");
	}

	public BattleLayer()
	{
		this.setIsTouchEnabled(true);

		btn_skill_bark_activated = CCSprite.sprite("minigame/btn_skill_bark_activated.png");
		btn_skill_bark_normal = CCSprite.sprite("minigame/btn_skill_bark_normal.png");
		btn_skill_bone_activated = CCSprite.sprite("minigame/btn_skill_bone_activated.png");
		btn_skill_bone_normal = CCSprite.sprite("minigame/btn_skill_bone_normal.png");
		btn_skill_punch_activated = CCSprite.sprite("minigame/btn_skill_punch_activated.png");
		btn_skill_punch_normal = CCSprite.sprite("minigame/btn_skill_punch_normal.png");
		
		gage_bar = CCSprite.sprite("minigame/gage_bar.png");
		gage_bar_black = CCSprite.sprite("minigame/bg_gage_bar.png");
		
		
		init_statics();
		
		gage = 0;
		gage_bar.setPosition(86 * Manager.ratio_width, 570 * Manager.ratio_height);
		gage_bar.setScaleX(Manager.ratio_width);
		gage_bar.setScaleY(Manager.ratio_height);
		this.addChild(gage_bar);
		
		gage_bar_black.setPosition(85 * Manager.ratio_width, 765 * Manager.ratio_height);
		gage_bar_black.setAnchorPoint(0.5f, 1f);
		gage_bar_black.setScaleX(Manager.ratio_width);
		gage_bar_black.setScaleY(1 - gage / Manager.full_gage);
		gage_bar_black.setScaleY(Manager.ratio_height);
		this.addChild(gage_bar_black);
		
		// ��ų ��ư ��ġ ����, ���̾ �߰�
		btn_skill_bark_activated.setPosition(631 * Manager.ratio_width, 733 * Manager.ratio_height);
		btn_skill_bark_activated.setScaleX(Manager.ratio_width);	//* Ȯ�� �ʿ�
		btn_skill_bark_activated.setScaleY(Manager.ratio_height);
		this.addChild(btn_skill_bark_activated);
		btn_skill_bark_normal.setPosition(631 * Manager.ratio_width, 733 * Manager.ratio_height);
		btn_skill_bark_normal.setScaleX(Manager.ratio_width);	//* Ȯ�� �ʿ�
		btn_skill_bark_normal.setScaleY(Manager.ratio_height);
		this.addChild(btn_skill_bark_normal);
		
		btn_skill_bone_activated.setPosition(631 * Manager.ratio_width, 589 * Manager.ratio_height);
		btn_skill_bone_activated.setScaleX(Manager.ratio_width);
		btn_skill_bone_activated.setScaleY(Manager.ratio_height);
		this.addChild(btn_skill_bone_activated);
		btn_skill_bone_normal.setPosition(631 * Manager.ratio_width, 589 * Manager.ratio_height);
		btn_skill_bone_normal.setScaleX(Manager.ratio_width);
		btn_skill_bone_normal.setScaleY(Manager.ratio_height);
		this.addChild(btn_skill_bone_normal);
		
		btn_skill_punch_activated.setPosition(631 * Manager.ratio_width, 439 * Manager.ratio_height);
		btn_skill_punch_activated.setScaleX(Manager.ratio_width);
		btn_skill_punch_activated.setScaleY(Manager.ratio_height);
		this.addChild(btn_skill_punch_activated);
		btn_skill_punch_normal.setPosition(631 * Manager.ratio_width, 439 * Manager.ratio_height);
		btn_skill_punch_normal.setScaleX(Manager.ratio_width);
		btn_skill_punch_normal.setScaleY(Manager.ratio_height);
		this.addChild(btn_skill_punch_normal);

		////////////////////////////////////////////////////////////////////////////
		bg_battlelayer.setPosition(360 * Manager.ratio_width, 1057 * Manager.ratio_height);
		bg_battlelayer.setScaleX(Manager.ratio_width);
		bg_battlelayer.setScaleY(Manager.ratio_height);
		this.addChild(bg_battlelayer);

		makeNormalAnimation();
		makeDemagedAnimation();

		myCharacter_normal = CCSprite.sprite(String.format("character/char%d_normal1.png", CurrentUserInformation.userChar));
		myCharacter_normal.setScaleX(-1 * Manager.ratio_width);
		myCharacter_normal.setScaleY(Manager.ratio_height);
		myCharacter_normal.setPosition(212 * Manager.ratio_width, 1019 * Manager.ratio_height);
		myCharacter_normal.runAction(CCRepeatForever.action(normalAnimate_mine));
		this.addChild(myCharacter_normal);

		opponentCharacter_normal = CCSprite.sprite(String.format("character/char%d_normal1.png", CurrentUserInformation.opponentchar));
		opponentCharacter_normal.setScaleX(Manager.ratio_width);
		opponentCharacter_normal.setScaleY(Manager.ratio_height);
		opponentCharacter_normal.setPosition(508 * Manager.ratio_width, 1019 * Manager.ratio_height);
		opponentCharacter_normal.runAction(CCRepeatForever.action(normalAnimate_opponent));
		this.addChild(opponentCharacter_normal);

		myCharacter_hurted = CCSprite.sprite(String.format("character/char%d_hurted1.png", CurrentUserInformation.userChar));
		myCharacter_hurted.setScaleX(-1 * Manager.ratio_width);
		myCharacter_hurted.setScaleY(Manager.ratio_height);
		myCharacter_hurted.setPosition(212 * Manager.ratio_width, 1019 * Manager.ratio_height);

		opponentCharacter_hurted = CCSprite.sprite(String.format("character/char%d_hurted1.png", CurrentUserInformation.opponentchar));
		opponentCharacter_hurted.setScaleX(Manager.ratio_width);
		opponentCharacter_hurted.setScaleY(Manager.ratio_height);
		opponentCharacter_hurted.setPosition(508 * Manager.ratio_width, 1019 * Manager.ratio_height);		

		bg_hp_bar.setPosition(360 * Manager.ratio_width, 1240 * Manager.ratio_height);
		bg_hp_bar.setScaleX(-1 * Manager.ratio_width);
		bg_hp_bar.setScaleY(Manager.ratio_height);
		this.addChild(bg_hp_bar);

		hp_bar_mine.setPosition(321 * Manager.ratio_width, 1240 * Manager.ratio_height);
		hp_bar_mine.setScaleX(Manager.ratio_width);
		hp_bar_mine.setScaleY(Manager.ratio_height);
		hp_bar_mine.setAnchorPoint(1f, 0.5f);
		this.addChild(hp_bar_mine);

		hp_bar_opponent.setPosition(400 * Manager.ratio_width, 1240	* Manager.ratio_height);
		hp_bar_opponent.setScaleX(Manager.ratio_width);
		hp_bar_opponent.setScaleY(Manager.ratio_height);
		hp_bar_opponent.setAnchorPoint(0f, 0.5f);
		this.addChild(hp_bar_opponent);
		
		title.setPosition(360 * Manager.ratio_width, 1235 * Manager.ratio_height);
		title.setScaleX(Manager.ratio_width);
		title.setScaleY(Manager.ratio_height);
		this.addChild(title);

		CCLabel x = CCLabel.makeLabel("400, 1247", "Arial", 30);
		x.setColor(ccColor3B.ccBLACK);
		x.setPosition(176, 1243);
		this.addChild(x);
		CCLabel l = CCLabel.makeLabel("176, 1243", "Arial", 20);
		l.setPosition(176, 1243);
		x.setColor(ccColor3B.ccBLACK);
		this.addChild(l);
		
		

		attack_bark_opponent = CCSprite.sprite(String.format("character/attack_bark%d.png", CurrentUserInformation.opponentchar));
		attack_bark_opponent.setScaleX(Manager.ratio_width);
		attack_bark_opponent.setScaleY(Manager.ratio_height);
		attack_bark_opponent.setPosition(508 * Manager.ratio_width, 1019 * Manager.ratio_height);
		attack_bark_mine = CCSprite.sprite(String.format("character/attack_bark%d.png", CurrentUserInformation.userChar));
		attack_bark_mine.setPosition(212 * Manager.ratio_width, 1019 * Manager.ratio_height);
		attack_bark_mine.setScaleX(-1 * Manager.ratio_width);
		attack_bark_mine.setScaleY(Manager.ratio_height);

		attack_bone_opponent = CCSprite.sprite(String.format("character/attack_bone%d.png", CurrentUserInformation.opponentchar));
		attack_bone_opponent.setScaleX(Manager.ratio_width);
		attack_bone_opponent.setScaleY(Manager.ratio_height);
		attack_bone_opponent.setPosition(508 * Manager.ratio_width, 1019 * Manager.ratio_height);
		attack_bone_mine = CCSprite.sprite(String.format("character/attack_bone%d.png", CurrentUserInformation.userChar));
		attack_bone_mine.setScaleX(-1 * Manager.ratio_width);
		attack_bone_mine.setScaleY(Manager.ratio_height);
		attack_bone_mine.setPosition(212 * Manager.ratio_width, 1019 * Manager.ratio_height);

		attack_punch_opponent = CCSprite.sprite(String.format("character/attack_punch%d.png", CurrentUserInformation.opponentchar));
		attack_punch_opponent.setScaleX(Manager.ratio_width);
		attack_punch_opponent.setScaleY(Manager.ratio_height);
		attack_punch_opponent.setPosition(508 * Manager.ratio_width, 1019 * Manager.ratio_height);
		attack_punch_mine = CCSprite.sprite(String.format("character/attack_punch%d.png", CurrentUserInformation.userChar));
		attack_punch_mine.setScaleX(-1 * Manager.ratio_width);
		attack_punch_mine.setScaleY(Manager.ratio_height);
		attack_punch_mine.setPosition(212 * Manager.ratio_width, 1019 * Manager.ratio_height);

		coming_bark = CCSprite.sprite("battle/coming_bark.png");
		coming_bark.setScaleX(Manager.ratio_width);
		coming_bark.setScaleY(Manager.ratio_height);
		going_bark = CCSprite.sprite("battle/coming_bark.png");
		going_bark.setScaleX(-1 * Manager.ratio_width);
		going_bark.setScaleY(Manager.ratio_height);

		coming_bone = CCSprite.sprite("battle/coming_bone.png");
		coming_bone.setScaleX(Manager.ratio_width);
		coming_bone.setScaleY(Manager.ratio_height);

		going_bone = CCSprite.sprite("battle/coming_bone.png");
		going_bone.setScaleX(-1 * Manager.ratio_width);
		going_bone.setScaleY(Manager.ratio_height);

		coming_punch = CCSprite.sprite("battle/coming_punch.png");
		coming_punch.setScaleX(Manager.ratio_width);
		coming_punch.setScaleY(Manager.ratio_height);

		going_punch = CCSprite.sprite("battle/coming_punch.png");
		going_punch.setScaleX(-1 * Manager.ratio_width);
		going_punch.setScaleY(Manager.ratio_height);

		NetworkManager.getInstance().setGetDamagedCallback(this);
	}


	@Override
	///* �������Լ� ��� ������ �޾��� ��, Activity���� ȣ���ϴ� �Լ�
	public void didGetDamaged(SkillType kindOfAttack) {
		switch(kindOfAttack.toInteger())
		{
		case 1 : 
			hp_mine -= Manager.damaged_gage_per_attack_bark;
			updateGageBar();
			//* ������ ȿ�� �ִϸ��̼�
			runAttackAnimation_opponent(1);
//			BattlerDogActivity.makeToast(1);
			break;
		case 2 :
			hp_mine -= Manager.damaged_gage_per_attack_bone;
			updateGageBar();
			runAttackAnimation_opponent(2);
			//* ������ ȿ�� �ִϸ��̼�
//			BattlerDogActivity.makeToast(2);
			break;
		case 3 :
			hp_mine -= Manager.damaged_gage_per_attack_punch;
			updateGageBar();
			//* ������ ȿ�� �ִϸ��̼�
			runAttackAnimation_opponent(3);
//			BattlerDogActivity.makeToast(3);
			break;
		}
	}

	public void runAttackAnimation_mine(int kindOfAttack) {
		numOfCurrentActions_mine++;
		
		CCFiniteTimeAction attackAction = makeAttackAction();
		CCSequence my_attack_sequence = makeMyAttackSequence(attackAction);
		
		myCharacter_normal.stopAction(normalAnimate_mine);
		myCharacter_normal.setVisible(false);
		switch(kindOfAttack)
		{
		case 1 : 
			myCharacter_normal.setVisible(false);
			this.addChild(attack_bark_mine, 1, ACTION_ATTACK_MINE);
			going_bark.setPosition(225 * Manager.ratio_width,1030 * Manager.ratio_height);
			this.addChild(going_bark, 500, ACTION_FLYING_MINE);
			going_bark.runAction(my_attack_sequence);
			break;
		case 2 : 
			myCharacter_normal.setVisible(false);
			this.addChild(attack_bone_mine, 1, ACTION_ATTACK_MINE);

			going_bone.setPosition(225 * Manager.ratio_width,1030 * Manager.ratio_height);
			this.addChild(going_bone, 500, ACTION_FLYING_MINE);
			going_bone.runAction(my_attack_sequence);
			break;
		case 3 : 
			myCharacter_normal.setVisible(false);
			this.addChild(attack_punch_mine, 1, ACTION_ATTACK_MINE);
			
			going_punch.setPosition(225 * Manager.ratio_width,1030 * Manager.ratio_height);
			this.addChild(going_punch, 500, ACTION_FLYING_MINE);
			going_punch.runAction(my_attack_sequence);
			break;
		}
	}
	
	public void runAttackAnimation_opponent(int kindOfAttack)
	{
		numOfCurrentActions_opponent++;
		
		CCFiniteTimeAction damagedAction = makeDamagedAction();
		CCSequence opponent_attack_sequence = makeMyDamagedSequence(damagedAction);
		
		opponentCharacter_normal.stopAction(normalAnimate_opponent);
		opponentCharacter_normal.setVisible(false);
		switch(kindOfAttack)
		{
		case 1 : 
			opponentCharacter_normal.setVisible(false);

			this.addChild(attack_bark_opponent, 1, ACTION_ATTACK_OPPONENT);
			
			coming_bark.setPosition(495 * Manager.ratio_width,1030 * Manager.ratio_height);
			this.addChild(coming_bark, 500, ACTION_FLYING_OPPONENT);
			coming_bark.runAction(opponent_attack_sequence);
			break;
		case 2 : 
			opponentCharacter_normal.setVisible(false);
			this.addChild(attack_bone_opponent, 1, ACTION_ATTACK_OPPONENT);
			
			coming_bone.setPosition(495 * Manager.ratio_width,1030 * Manager.ratio_height);
			this.addChild(coming_bone, 500, ACTION_FLYING_OPPONENT);
			coming_bone.runAction(opponent_attack_sequence);
			break;
		case 3 : 
			opponentCharacter_normal.setVisible(false);
			this.addChild(attack_punch_opponent, 1, ACTION_ATTACK_OPPONENT);
			
			coming_punch.setPosition(495 * Manager.ratio_width,1030 * Manager.ratio_height);
			this.addChild(coming_punch, 500, ACTION_FLYING_OPPONENT);
			coming_punch.runAction(opponent_attack_sequence);
			break;
		}
	}
	/*
	 * ��ų ��ư�� Ȱ��, ��Ȱ�� ���� ������Ʈ
	 */
	public static void updateSkillBtns()
	{
		// ��ų ��ư Ȱ��ȭ ==> normal image�� visibility��  false��
		if(gage >= Manager.required_gage_for_skill_punch)
		{
			btn_skill_bark_normal.setVisible(false);
			btn_skill_bone_normal.setVisible(false);
			btn_skill_punch_normal.setVisible(false);
		}
		else if(gage >= Manager.required_gage_for_skill_bone)
		{
			btn_skill_bark_normal.setVisible(false);
			btn_skill_bone_normal.setVisible(false);
			btn_skill_punch_normal.setVisible(true);
		}
		else if(gage >= Manager.required_gage_for_skill_bark)
		{
			btn_skill_bark_normal.setVisible(false);
			btn_skill_bone_normal.setVisible(true);
			btn_skill_punch_normal.setVisible(true);
		}
		else
		{
			btn_skill_bark_normal.setVisible(true);
			btn_skill_bone_normal.setVisible(true);
			btn_skill_punch_normal.setVisible(true);
		}
	}
	/*
	 * �������� �� ������ �� ���� ������Ʈ
	 */
	public static void updateGageBar()
	{
		gage_bar_black.setScaleY((1 - (gage / Manager.full_gage)) * Manager.ratio_height);
	}
	
	@Override
	public boolean ccTouchesBegan(MotionEvent event) {
		CGPoint touchPoint = CCDirector.sharedDirector().convertToGL(CGPoint.ccp(event.getX(),event.getY()));
		if(btn_skill_bark_normal.getBoundingBox().contains(touchPoint.x, touchPoint.y) && gage >= Manager.required_gage_for_skill_bark)
		{
			gage -= Manager.required_gage_for_skill_bark;
			hp_opponent -= Manager.damaged_gage_per_attack_bark;
			updateSkillBtns();			
			updateGageBar();
			runAttackAnimation_mine(1);
			NetworkManager.getInstance().sendAttack(SkillType.BARK);
		}
		else if(btn_skill_bone_normal.getBoundingBox().contains(touchPoint.x, touchPoint.y) && gage >= Manager.required_gage_for_skill_bone)
		{
			gage -= Manager.required_gage_for_skill_bone;
			hp_opponent -= Manager.damaged_gage_per_attack_bone;
			updateSkillBtns();
			updateGageBar();
			runAttackAnimation_mine(2);
			NetworkManager.getInstance().sendAttack(SkillType.BONE);
			///* �������� ��� ���� ���
		}
		else if(btn_skill_punch_normal.getBoundingBox().contains(touchPoint.x, touchPoint.y) && gage >= Manager.required_gage_for_skill_punch)
		{
			gage -= Manager.required_gage_for_skill_punch;
			hp_opponent -= Manager.damaged_gage_per_attack_punch;
			updateSkillBtns();
			updateGageBar();
			runAttackAnimation_mine(3);
			NetworkManager.getInstance().sendAttack(SkillType.PUNCH);
			///* �������� ��� ���� ���
		}
		return super.ccTouchesBegan(event);
	}

	public void updateHPBar()
	{
		hp_bar_mine.setScaleX(hp_mine/Manager.full_hp * Manager.ratio_width);
		hp_bar_opponent.setScaleX(hp_opponent/Manager.full_hp * Manager.ratio_width);
		if(hp_mine < thresholdOfHP)
		{
			this.schedule("playDangerAnimation", 0.5f);
		}
	}
	public void playDangerAnimation(float dt)
	{
		
	}
	
	@Override
	public void onDestroy() {
		btn_skill_bark_activated = null;
		btn_skill_bark_normal = null;
		btn_skill_bone_activated = null;
		btn_skill_bone_normal = null;
		btn_skill_punch_activated = null;
		btn_skill_punch_normal = null;
		
		gage_bar = null;
		gage_bar_black = null;
		
		bg_battlelayer = null;
		bg_hp_bar = null;
		hp_bar_mine = null;
		hp_bar_opponent = null;
		title = null;
		
		returnToNormalMode = null;
		damagedAndReturnToNormal_mine = null;
		damagedAndReturnToNormal_opponent = null;
		
		coming_bark = null;
		going_bark = null;
		coming_bone = null;
		going_bone = null;
		coming_punch = null;
		going_punch = null;

		attack_bark_opponent = null;
		attack_bark_mine = null;

		attack_bone_opponent = null;
		attack_bone_mine = null;

		attack_punch_opponent = null;
		attack_punch_mine = null;

		damagedAction = null;
		attackAction = null;
		damagedSequence_bark = null;
		attackSequence_bark = null;
		damagedAnimation_mine = null;
		damagedAnimation_opponent = null;
		damagedAnimate_mine = null;
		damagedAnimate_opponent = null;

		normalAnimation_mine = null;
		normalAnimation_opponent = null;
		normalAnimate_mine = null;
		normalAnimate_opponent = null;

		myCharacter_normal = null;
		opponentCharacter_normal = null;
		myCharacter_hurted = null;
		opponentCharacter_hurted = null;

		
//		LayerDestroyManager.getInstance().removeLayer(this);
	}
	
//	@Override
//	protected void finalize() throws Throwable {
//		onDestroy();
//		
//		super.finalize();
//	}

}
