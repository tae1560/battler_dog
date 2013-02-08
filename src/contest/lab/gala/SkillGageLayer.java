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
		
		// 스킬 버튼 위치 설정, 레이어에 추가
		btn_skill_bark_activated.setPosition(400, 500);
//		btn_skill_bark_activated.setVisible(false);	//* 확인 필요
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
	 * 게이지에 따른 게이지 바 길이 업데이트
	 */
	static void updateGageBar()
	{
		gage_bar.setScaleY(gage / Manager.full_gage);
	}
	
	/*
	 * 스킬 버튼의 활성, 비활성 상태 업데이트
	 */
	static void updateSkillBtns()
	{
		// 스킬 버튼 활성화 ==> normal image의 visibility를  false로
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
	///* 서버에게서 공격 정보를 받았을 때, Activity에서 호출하는 함수
	public static void getDamaged(int kindOfAttack)
	{
		switch(kindOfAttack)
		{
			case 1 : 
				gage -= Manager.damaged_gage_per_attack_bark;
				updateGageBar();
				//* 데미지 효과 애니메이션
				break;
			case 2 :
				gage -= Manager.damaged_gage_per_attack_bone;
				updateGageBar();
				//* 데미지 효과 애니메이션
				break;
			case 3 :
				gage -= Manager.damaged_gage_per_attack_punch;
				updateGageBar();
				//* 데미지 효과 애니메이션
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
			///* 서버에게 공격 정보 전송
		}
		else if(btn_skill_bone_normal.getBoundingBox().contains(touchPoint.x, touchPoint.y) && gage >= Manager.required_gage_for_skill_bone)
		{
			gage -= Manager.required_gage_for_skill_bone;
			updateSkillBtns();
			updateGageBar();
			///* 서버에게 공격 정보 전송
		}
		else if(btn_skill_punch_normal.getBoundingBox().contains(touchPoint.x, touchPoint.y) && gage >= Manager.required_gage_for_skill_punch)
		{
			gage -= Manager.required_gage_for_skill_punch;
			updateSkillBtns();
			updateGageBar();
			///* 서버에게 공격 정보 전송
		}
		return super.ccTouchesBegan(event);
	}
}
