package contest.lab.gala;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;

import android.view.MotionEvent;
import contest.lab.gala.callback.ClickAttackBtnCallback;
import contest.lab.gala.data.SkillType;

public class SkillGageLayer extends CCLayer{
	private static SkillGageLayer _instance;
	
	public static CCSprite btn_skill_bark_activated = CCSprite.sprite("minigame/btn_skill_bark_activated.png");
	public static CCSprite btn_skill_bark_normal = CCSprite.sprite("minigame/btn_skill_bark_normal.png");
	public static CCSprite btn_skill_bone_activated = CCSprite.sprite("minigame/btn_skill_bone_activated.png");
	public static CCSprite btn_skill_bone_normal = CCSprite.sprite("minigame/btn_skill_bone_normal.png");
	public static CCSprite btn_skill_punch_activated = CCSprite.sprite("minigame/btn_skill_punch_activated.png");
	public static CCSprite btn_skill_punch_normal = CCSprite.sprite("minigame/btn_skill_punch_normal.png");
	
	public static CCSprite gage_bar = CCSprite.sprite("minigame/gage_bar.png");
	public static CCSprite gage_bar_black = CCSprite.sprite("minigame/bg_gage_bar.png");
	
	private ClickAttackBtnCallback clickAttackBtnCallback = null;
	
	public static float gage;
	
//	public static Manager m;
	
	public static SkillGageLayer getInstance() {
		if (_instance == null) {
			_instance = new SkillGageLayer();
//			_instance.debug("make Instance");
		}
		return _instance;
	}
	
	public SkillGageLayer()
	{
		this.setIsTouchEnabled(true);
		
//		m = new Manager();
		
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
	}
	
	/*
	 * �������� ���� ������ �� ���� ������Ʈ
	 */
	public static void updateGageBar()
	{
		gage_bar_black.setScaleY((1 - (gage / Manager.full_gage)) * Manager.ratio_height);
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
	
	public void setClickAttackBtnCallback(ClickAttackBtnCallback callback){
		this.clickAttackBtnCallback = callback;
	}
	
	@Override
	public boolean ccTouchesBegan(MotionEvent event) {
		CGPoint touchPoint = CCDirector.sharedDirector().convertToGL(CGPoint.ccp(event.getX(),event.getY()));
		if(btn_skill_bark_normal.getBoundingBox().contains(touchPoint.x, touchPoint.y) && gage >= Manager.required_gage_for_skill_bark)
		{
			gage -= Manager.required_gage_for_skill_bark;
			updateSkillBtns();
			updateGageBar();
			if (this.clickAttackBtnCallback != null) {
				this.clickAttackBtnCallback.runAttackAnimation_mine(1);
			}
			NetworkManager.getInstance().sendAttack(SkillType.BARK);
		}
		else if(btn_skill_bone_normal.getBoundingBox().contains(touchPoint.x, touchPoint.y) && gage >= Manager.required_gage_for_skill_bone)
		{
			gage -= Manager.required_gage_for_skill_bone;
			updateSkillBtns();
			updateGageBar();
			if (this.clickAttackBtnCallback != null) {
				this.clickAttackBtnCallback.runAttackAnimation_mine(2);
			}
			NetworkManager.getInstance().sendAttack(SkillType.BONE);
			///* �������� ���� ���� ����
		}
		else if(btn_skill_punch_normal.getBoundingBox().contains(touchPoint.x, touchPoint.y) && gage >= Manager.required_gage_for_skill_punch)
		{
			gage -= Manager.required_gage_for_skill_punch;
			updateSkillBtns();
			updateGageBar();
			if (this.clickAttackBtnCallback != null) {
				this.clickAttackBtnCallback.runAttackAnimation_mine(3);
			}
			NetworkManager.getInstance().sendAttack(SkillType.PUNCH);
			///* �������� ���� ���� ����
		}
		return super.ccTouchesBegan(event);
	}
}
