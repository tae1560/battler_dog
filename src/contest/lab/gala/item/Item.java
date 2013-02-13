package contest.lab.gala.item;

import org.cocos2d.nodes.CCSprite;

public abstract class Item {
	public CCSprite image;
	public abstract void acquireGage();
}