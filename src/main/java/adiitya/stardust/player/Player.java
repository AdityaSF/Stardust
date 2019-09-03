package adiitya.stardust.player;

import adiitya.stardust.util.FileType;
import adiitya.stardust.util.TerrariaFile;
import lombok.Getter;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.io.File;
import java.math.BigInteger;

public class Player {

	private final File file;

	@Getter
	private long version;
	@Getter
	private long revision;
	@Getter
	private boolean favourite;

	public Player(File file) throws BadPaddingException, IllegalBlockSizeException {

		this.file = file;
		init();
	}

	private void init() throws BadPaddingException, IllegalBlockSizeException {

		TerrariaFile plr = new TerrariaFile(file);
		version = plr.readInt();

		validate(plr.readString(7), "relogic", "bad magic value: %s");
		validate(plr.readFileType(), FileType.PLAYER, "bad file type: %s");

		revision = plr.readInt();
		favourite = plr.readLong().and(BigInteger.ONE).equals(BigInteger.ONE);
	}

	private <T> void validate(T value, T expected, String message) {

		if (!value.equals(expected))
			throw new IllegalArgumentException(String.format(message, value));
	}
}
