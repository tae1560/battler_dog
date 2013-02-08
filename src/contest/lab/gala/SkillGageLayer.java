package contest.lab.gala;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;

import android.view.MotionEvent;

public class SkillGageLayer extends CCLayer{
	static CCSprite btn_skill_bark_activated = CCSprite.sprite("btn_skill_bark_activated.png");
	static CCSprite btn_skill_bark_normal = CCSprite.sprite("btn_skill_bark_normal.png");
	static CCSprite btn_skill_bone_activated = CCSprite.sprite("btn_skill_bone_activated.png");
	static CCSprite btn_skill_bone_normal = CCSprite.sprite("btn_skill_bone_normal.png");
	static CCSprite btn_skill_punch_activated = CCSprite.sprite("btn_skill_punch_activated.png");
	static CCSprite btn_skill_punch_normal = CCSprite.sprite("btn_skill_punch_normal.png");
	static CCSprite gage_bar = CCSprite.sprite("gage_bar.png");
	
	static float gage;
	
	public SkillGageLayer()
	{
		this.setIsTouchEnabled(true);
		
		gage = 0;
		gage_bar.setPosition(100, 300);
		gage_bar.setScaleY(gage / Manager.full_gage);
		this.addChild(gage_bar);
		
		// ��ų ��ư ��ġ ����, ���̾ �߰�
		btn_skill_bark_activated.setPosition(400, 500);
//		btn_skill_bark_activated.setVisible(false);	//* Ȯ�� �ʿ�
		this.addChild(btn_skill_bark_activated);
		btn_skill_bark_normal.setPosition(400, 500);
		this.addChild(btn_skill_bark_normal);
		
		btn_skill_bone_activated.setPosition(400, 560);
//		btn_skill_bone_activated.setVisible(false);
		this.addChild(btn_skill_bone_activated);
		btn_skill_bone_normal.setPosition(400, 560);
		this.addChild(btn_skill_bone_normal);
		
		btn_skill_punch_activated.setPosition(400, 620);
//		btn_skill_punch_activated.setVisible(false);
		this.addChild(btn_skill_punch_activated);
		btn_skill_punch_normal.setPosition(400, 620);
		this.addChild(btn_skill_punch_normal);
	}
	
	/*
	 * �������� ���� ������ �� ���� ������Ʈ
	 */
	static void updateGageBar()
	{
		gage_bar.setScaleY(gage / Manager.full_gage);
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
				gage -= Manager.damaged_gage_per_attack_bark;
				updateGageBar();
				//* ������ ȿ�� �ִϸ��̼�
				break;
			case 2 :
				gage -= Manager.damaged_gage_per_attack_bone;
				updateGageBar();
				//* ������ ȿ�� �ִϸ��̼�
				break;
			case 3 :
				gage -= Manager.damaged_gage_per_attack_punch;
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
