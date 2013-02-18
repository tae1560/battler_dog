package contest.lab.gala;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCLabelAtlas;
import org.cocos2d.nodes.CCSprite;

public class ComboLayer extends CCLayer{
	
	static CCLabelAtlas combo = null;
	static CCSprite comboLetter = null;
	public static int numOfCombo = 0;
	public ComboLayer()
	{
		comboLetter = CCSprite.sprite("battle/combo.png");
		comboLetter.setScaleX(Manager.ratio_width);
		comboLetter.setScaleY(Manager.ratio_height);
		comboLetter.setPosition(142 * Manager.ratio_width, 288* Manager.ratio_height);
		comboLetter.setVisible(false);
		
		combo = CCLabelAtlas.label("" + numOfCombo, "battle/numbers_combo.png", 46, 70, '0');
		combo.setScaleX(Manager.ratio_width);
		combo.setScaleY(Manager.ratio_height);
		combo.setPosition(259 * Manager.ratio_width, 288 * Manager.ratio_height);
		combo.setVisible(false);
		this.addChild(combo);
	}
	static void showCombo()
	{
		comboLetter.setVisible(true);
		combo.setVisible(true);
		numOfCombo++;
		combo.setString("" + numOfCombo);
	}
	static void resetCombo()
	{
		if(numOfCombo > Manager.maxNumOfCombo)
			Manager.maxNumOfCombo = numOfCombo;
		numOfCombo = 0;
		comboLetter.setVisible(false);
		combo.setVisible(false);
	}
}
