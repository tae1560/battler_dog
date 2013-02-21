package contest.lab.gala;

import org.cocos2d.actions.base.CCFiniteTimeAction;
import org.cocos2d.actions.instant.CCCallFuncN;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCScaleTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.actions.interval.CCSpawn;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCLabelAtlas;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;

import contest.lab.gala.interfaces.LifeCycleInterface;

public class ComboLayer extends CCLayer implements LifeCycleInterface{
	public static ComboLayer runningLayer;
	static CCLabelAtlas combo = null;
	static CCSprite comboLetter = null;
	public static int numOfCombo = 0;
	public ComboLayer()
	{
		numOfCombo = 0;
		
		runningLayer = this;
		
		comboLetter = CCSprite.sprite("battle/combo.png");
		comboLetter.setScaleX(Manager.ratio_width);
		comboLetter.setScaleY(Manager.ratio_height);
		comboLetter.setPosition(142 * Manager.ratio_width, 258* Manager.ratio_height);
		comboLetter.setVisible(false);
		this.addChild(comboLetter);
		
		combo = CCLabelAtlas.label("" + numOfCombo, "battle/numbers_combo.png", 46, 70, '0');
		combo.setScaleX(Manager.ratio_width);
		combo.setScaleY(Manager.ratio_height);
		combo.setPosition(262 * Manager.ratio_width, 228 * Manager.ratio_height);
		combo.setVisible(false);
		this.addChild(combo);
	}
	void showCombo()
	{
		numOfCombo++;
		
		CCSprite comboLetter = CCSprite.sprite("battle/combo.png");
		comboLetter.setScaleX(Manager.ratio_width * 0.5f);
		comboLetter.setScaleY(Manager.ratio_height * 0.5f);
		comboLetter.setPosition(160 * Manager.ratio_width, 150 * Manager.ratio_height);
		CCFiniteTimeAction action_goUp_letter = CCMoveTo.action(0.3f, CGPoint.ccp(142 * Manager.ratio_width, 258* Manager.ratio_height));
		CCFiniteTimeAction action_scale_letter = CCScaleTo.action(Manager.ratio_width, Manager.ratio_height);
		CCFiniteTimeAction action_delay_for2secs_letter = CCMoveTo.action(0.3f, CGPoint.ccp(142 * Manager.ratio_width, 258* Manager.ratio_height));
		CCSpawn spawn_letter = CCSpawn.actions(action_goUp_letter, action_scale_letter);
		CCCallFuncN deleteLetter = CCCallFuncN.action(this, "deleteLetter");
		CCSequence sequence_letter = CCSequence.actions(spawn_letter, action_delay_for2secs_letter, deleteLetter);
		comboLetter.runAction(sequence_letter);
		this.addChild(comboLetter);
		
		CCLabelAtlas combo = CCLabelAtlas.label("" + numOfCombo, "battle/numbers_combo.png", 46, 70, '0');
		combo.setScaleX(Manager.ratio_width * 0.5f);
		combo.setScaleY(Manager.ratio_height * 0.5f);
		combo.setPosition(240 * Manager.ratio_width, 120 * Manager.ratio_height);
		CCFiniteTimeAction action_goUp_number = CCMoveTo.action(0.3f, CGPoint.ccp(262 * Manager.ratio_width, 228* Manager.ratio_height));
		CCFiniteTimeAction action_scale_number = CCScaleTo.action(Manager.ratio_width, Manager.ratio_height);
		CCFiniteTimeAction action_delay_for2secs_number = CCMoveTo.action(0.3f, CGPoint.ccp(262 * Manager.ratio_width, 228* Manager.ratio_height));
		CCSpawn spawn_number = CCSpawn.actions(action_goUp_number, action_scale_number);
		CCCallFuncN deleteNumber = CCCallFuncN.action(this, "deleteNumber");
		CCSequence sequence_number = CCSequence.actions(spawn_number, action_delay_for2secs_number, deleteNumber);
		combo.runAction(sequence_number);
		this.addChild(combo);
		
	}
	public void deleteLetter(Object sender)
	{
		CCSprite object = (CCSprite) sender;
		this.removeChild(object, true);
	}
	public void deleteNumber(Object sender)
	{
		CCLabelAtlas object = (CCLabelAtlas) sender;
		this.removeChild(object, true);
	}
	static void resetCombo()
	{
		if(numOfCombo > Manager.maxNumOfCombo)
			Manager.maxNumOfCombo = numOfCombo;
		numOfCombo = 0;
		comboLetter.setVisible(false);
		combo.setVisible(false);
	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		combo = null;
	}
	
	long _attached_time = 0;
	@Override
	public long getTime() {
		return _attached_time;
	}
	@Override
	public void setTime(long time) {
		_attached_time = time;
	}
}
