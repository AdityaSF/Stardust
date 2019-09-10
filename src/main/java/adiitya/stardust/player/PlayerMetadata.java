package adiitya.stardust.player;

import adiitya.stardust.io.BinaryReader;
import adiitya.stardust.util.Difficulty;
import adiitya.stardust.util.Playtime;
import lombok.Getter;

public class PlayerMetadata {

	@Getter private final String name;
	@Getter private final Difficulty difficulty;
	@Getter private final Playtime playtime;
	@Getter private final int hair;
	@Getter private final byte hairDye;
	@Getter private final byte hideAcessories;
	@Getter private final byte hideEquipment;
	@Getter private final byte hideMisc;
	@Getter private final byte skinVariant;
	@Getter private final int health;
	@Getter private final int maxHealth;
	@Getter private final int mana;
	@Getter private final int maxMana;

	public PlayerMetadata(BinaryReader reader) {

		this.name = reader.readString();
		this.difficulty = Difficulty.of(reader.readByte());
		this.playtime = Playtime.fromTicks(reader.readLong());
		this.hair = reader.readInt();
		this.hairDye = reader.readByte();
		this.hideAcessories = reader.readByte();
		this.hideEquipment = reader.readByte();
		this.hideMisc = reader.readByte();
		this.skinVariant = reader.readByte();
		this.health = clamp(reader.readInt(), 0, 500);
		this.maxHealth = clamp(reader.readInt(), 10, 500);
		this.mana = clamp(reader.readInt(), 0, 300);
		this.maxMana = clamp(reader.readInt(), 0, 200);
	}

	private static int clamp(int value, int min, int max) {
		return value < min ? min : Math.min(value, max);
	}
}
