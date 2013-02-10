package contest.lab.gala;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCLabel;

public class ComboLayer extends CCLayer{
	Manager m;
	
	static CCLabel combo;
	public static float numOfCombo = 0;
	public ComboLayer()
	{
		m = new Manager();
		combo = CCLabel.makeLabel("" + numOfCombo, "Arial", 40);
		combo.setPosition(360 * m.ratio_width, 640 * m.ratio_height);
		this.addChild(combo);
	}
	static void showCombo()
	{
		numOfCombo++;
		combo.setString("" + numOfCombo);
	}
	static void resetCombo()
	{
		numOfCombo = 0;
	}
}
