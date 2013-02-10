package contest.lab.gala;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;

import android.view.MotionEvent;

public class SkillGageLayer extends CCLayer{
	static CCSprite btn_skill_bark_activated = CCSprite.sprite("minigame/btn_skill_bark_activated.png");
	static CCSprite btn_skill_bark_normal = CCSprite.sprite("minigame/btn_skill_bark_normal.png");
	static CCSprite btn_skill_bone_activated = CCSprite.sprite("minigame/btn_skill_bone_activated.png");
	static CCSprite btn_skill_bone_normal = CCSprite.sprite("minigame/btn_skill_bone_normal.png");
	static CCSprite btn_skill_punch_activated = CCSprite.sprite("minigame/btn_skill_punch_activated.png");
	static CCSprite btn_skill_punch_normal = CCSprite.sprite("minigame/btn_skill_punch_normal.png");
	
	static CCSprite gage_bar = CCSprite.sprite("minigame/gage_bar.png");
	static CCSprite gage_bar_black = CCSprite.sprite("minigame/bg_gage_bar.png");
	
	static float gage;
	static float hp;
	
	static Manager m;
	
	public SkillGageLayer()
	{
		this.setIsTouchEnabled(true);
		
		m = new Manager();
		
		gage = 0;
		gage_bar.setPosition(86 * m.ratio_width, 570 * m.ratio_height);
		gage_bar.setScaleX(m.ratio_width);
		gage_bar.setScaleY(m.ratio_height);
		this.addChild(gage_bar);
		
		gage_bar_black.setPosition(85 * m.ratio_width, 765 * m.ratio_height);
		gage_bar_black.setAnchorPoint(0.5f, 1f);
		gage_bar_black.setScaleX(m.ratio_width);
		gage_bar_black.setScaleY(1 - gage / m.full_gage);
		gage_bar_black.setScaleY(m.ratio_height);
		this.addChild(gage_bar_black);
		
		// ��ų ��ư ��ġ ����, ���̾ �߰�
		btn_skill_bark_activated.setPosition(631 * m.ratio_width, 733 * m.ratio_height);
		btn_skill_bark_activated.setScaleX(m.ratio_width);	//* Ȯ�� �ʿ�
		btn_skill_bark_activated.setScaleY(m.ratio_height);
		this.addChild(btn_skill_bark_activated);
		btn_skill_bark_normal.setPosition(631 * m.ratio_width, 733 * m.ratio_height);
		btn_skill_bark_normal.setScaleX(m.ratio_width);	//* Ȯ�� �ʿ�
		btn_skill_bark_normal.setScaleY(m.ratio_height);
		this.addChild(btn_skill_bark_normal);
		
		btn_skill_bone_activated.setPosition(631 * m.ratio_width, 589 * m.ratio_height);
		btn_skill_bone_activated.setScaleX(m.ratio_width);
		btn_skill_bone_activated.setScaleY(m.ratio_height);
		this.addChild(btn_skill_bone_activated);
		btn_skill_bone_normal.setPosition(631 * m.ratio_width, 589 * m.ratio_height);
		btn_skill_bone_normal.setScaleX(m.ratio_width);
		btn_skill_bone_normal.setScaleY(m.ratio_height);
		this.addChild(btn_skill_bone_normal);
		
		btn_skill_punch_activated.setPosition(631 * m.ratio_width, 439 * m.ratio_height);
		btn_skill_punch_activated.setScaleX(m.ratio_width);
		btn_skill_punch_activated.setScaleY(m.ratio_height);
		this.addChild(btn_skill_punch_activated);
		btn_skill_punch_normal.setPosition(631 * m.ratio_width, 439 * m.ratio_height);
		btn_skill_punch_normal.setScaleX(m.ratio_width);
		btn_skill_punch_normal.setScaleY(m.ratio_height);
		this.addChild(btn_skill_punch_normal);
	}
	
	/*
	 * �������� ���� ������ �� ���� ������Ʈ
	 */
	static void updateGageBar()
	{
		gage_bar_black.setScaleY((1 - (gage / Manager.full_gage)) * m.ratio_height);
	}
	
	/*
	 * ��ų ��ư�� Ȱ��, ��Ȱ�� ���� ������Ʈ
	 */
	static void updateSkillBtns()
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
	///* �������Լ� ���� ������ �޾��� ��, Activity���� ȣ���ϴ� �Լ�
	public static void getDamaged(int kindOfAttack)
	{
		switch(kindOfAttack)
		{
			case 1 : 
				hp -= Manager.damaged_gage_per_attack_bark;
				updateGageBar();
				//* ������ ȿ�� �ִϸ��̼�
				break;
			case 2 :
				hp -= Manager.damaged_gage_per_attack_bone;
				updateGageBar();
				//* ������ ȿ�� �ִϸ��̼�
				break;
			case 3 :
				hp -= Manager.damaged_gage_per_attack_punch;
				updateGageBar();
				//* ������ ȿ�� �ִϸ��̼�
				break;
		}
	}
	@Override
	public boolean ccTouchesBegan(MotionEvent event) {
		CGPoint touchPoint = CCDirector.sharedDirector().convertToGL(CGPoint.ccp(event.getX(),event.getY()));
		if(btn_skill_bark_normal.getBoundingBox().contains(touchPoint.x, touchPoint.y) && gage >= Manager.required_gage_for_skill_bark)
		{
			gage -= Manager.required_gage_for_skill_bark;
			updateSkillBtns();
			updateGageBar();
			///* �������� ���� ���� ����
		}
		else if(btn_skill_bone_normal.getBoundingBox().contains(touchPoint.x, touchPoint.y) && gage >= Manager.required_gage_for_skill_bone)
		{
			gage -= Manager.required_gage_for_skill_bone;
			updateSkillBtns();
			updateGageBar();
			///* �������� ���� ���� ����
		}
		else if(btn_skill_punch_normal.getBoundingBox().contains(touchPoint.x, touchPoint.y) && gage >= Manager.required_gage_for_skill_punch)
		{
			gage -= Manager.required_gage_for_skill_punch;
			updateSkillBtns();
			updateGageBar();
			///* �������� ���� ���� ����
		}
		return super.ccTouchesBegan(event);
	}
}
