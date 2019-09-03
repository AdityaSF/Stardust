package adiitya.stardust.player;

import adiitya.stardust.util.Difficulty;
import adiitya.stardust.util.Playtime;
import adiitya.stardust.util.TerrariaFile;
import lombok.Getter;

import java.math.BigInteger;

public class PlayerMetadata {

	@Getter private final long release;
	@Getter private final long revision;
	@Getter private final boolean favorite;
	@Getter private final String name;
	@Getter private final Difficulty difficulty;
	@Getter private final Playtime playtime;

	private PlayerMetadata(long release, long revision, boolean favorite, String name, Difficulty difficulty, Playtime playtime) {
		this.release = release;
		this.revision = revision;
		this.favorite = favorite;
		this.name = name;
		this.difficulty = difficulty;
		this.playtime = playtime;
	}

	public static PlayerMetadata fromTerariaFile(TerrariaFile file) {

		long release = file.at(0).readInt();
		long revision = file.at(12).readInt();
		boolean favorite = file.readUnsignedLong().and(BigInteger.ONE).equals(BigInteger.ONE);
		String name = file.readString();
		Difficulty difficulty = Difficulty.of(file.readByte());
		Playtime playtime = Playtime.fromTerrariaFile(file);

		return new PlayerMetadata(release, revision, favorite, name, difficulty, playtime);
	}
}
