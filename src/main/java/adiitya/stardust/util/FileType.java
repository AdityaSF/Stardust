package adiitya.stardust.util;

public enum FileType {

	NONE,
	MAP,
	WORLD,
	PLAYER;

	public static FileType of(int index) {
		return index > 4 || index < 0 ? NONE :
				values()[index];
	}
}
