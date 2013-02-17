package contest.lab.gala;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.types.ccColor3B;

public class ComboLayer extends CCLayer{
	Manager m;
	
	static CCLabel combo;
	public static int numOfCombo = 0;
	public ComboLayer()
	{
		m = new Manager();
		combo = CCLabel.makeLabel("" + numOfCombo, "Arial", 40);
		combo.setColor(ccColor3B.ccWHITE);
		combo.setPosition(200 * Manager.ratio_width, 300 * Manager.ratio_height);
		this.addChild(combo);
	}
	static void showCombo()
	{
		numOfCombo++;
		combo.setString("" + numOfCombo);
	}
	static void resetCombo()
	{
		if(numOfCombo > Manager.maxNumOfCombo)
			Manager.maxNumOfCombo = numOfCombo;
		numOfCombo = 0;
		combo.setString("바보바보 틀렸어");
	}
}
