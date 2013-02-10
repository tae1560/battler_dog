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
		
		// 스킬 버튼 위치 설정, 레이어에 추가
		btn_skill_bark_activated.setPosition(631 * m.ratio_width, 733 * m.ratio_height);
		btn_skill_bark_activated.setScaleX(m.ratio_width);	//* 확인 필요
		btn_skill_bark_activated.setScaleY(m.ratio_height);
		this.addChild(btn_skill_bark_activated);
		btn_skill_bark_normal.setPosition(631 * m.ratio_width, 733 * m.ratio_height);
		btn_skill_bark_normal.setScaleX(m.ratio_width);	//* 확인 필요
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
	 * 게이지에 따른 게이지 바 길이 업데이트
	 */
	static void updateGageBar()
	{
		gage_bar_black.setScaleY((1 - (gage / Manager.full_gage)) * m.ratio_height);
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
				hp -= Manager.damaged_gage_per_attack_bark;
				updateGageBar();
				//* 데미지 효과 애니메이션
				break;
			case 2 :
				hp -= Manager.damaged_gage_per_attack_bone;
				updateGageBar();
				//* 데미지 효과 애니메이션
				break;
			case 3 :
				hp -= Manager.damaged_gage_per_attack_punch;
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
