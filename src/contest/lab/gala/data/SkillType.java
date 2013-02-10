package contest.lab.gala.data;

public enum SkillType {	
	BARK(1), 
	BONE(2), 
	PUNCH(3);
	
	SkillType(int value) { this.value = value; }
	private final int value;
	public int toInteger() { return value; }
	public String toString() { return Integer.toString(value); }
	
	public static SkillType parseInt(int skillType) {
		switch (skillType) {
		case 1:
			return SkillType.BARK;
		case 2:
			return SkillType.BONE;
		case 3:
			return SkillType.PUNCH;
		default:
			return null;
		}
	}
}