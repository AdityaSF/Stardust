package adiitya.stardust.util;

public enum Difficulty {

	SOFTCORE,
	MEDIUMCORE,
	HARDCORE;

	public static Difficulty of(int index) {
		return index > values().length || index < 0 ? HARDCORE :
				values()[index];
	}
}
