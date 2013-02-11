package contest.lab.gala;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.types.ccColor3B;

public class ComboLayer extends CCLayer{
	Manager m;
	
	static CCLabel combo;
	public static float numOfCombo = 0;
	public ComboLayer()
	{
		m = new Manager();
		combo = CCLabel.makeLabel("" + numOfCombo, "Arial", 40);
		combo.setColor(ccColor3B.ccWHITE);
		combo.setPosition(200 * m.ratio_width, 300 * m.ratio_height);
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
		combo.setString("�ٺ� �ٺ� Ʋ�Ⱦ�");
	}
}