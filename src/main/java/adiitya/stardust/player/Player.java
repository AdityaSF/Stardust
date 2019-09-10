package adiitya.stardust.player;

import adiitya.stardust.io.BinaryReader;
import lombok.Getter;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class Player {

	private final File file;

	@Getter
	private String name;

	@Getter
	private PlayerMetadata playerMetadata;

	public Player(File file) {

		this.file = file;
		init();
	}

	private void init() {

		try {

			byte[] key = createKey();

			Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
			cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "AES"), new IvParameterSpec(key));

			BinaryReader reader;

			try (InputStream in = Files.newInputStream(file.toPath(), StandardOpenOption.READ)) {
				reader = new BinaryReader(new CipherInputStream(in, cipher));
			}

			validate(reader.readInt(), 194, "Unsupported data version: %d");
			validate(reader.readString(7), "relogic", "bad magic value: %s");
			reader.skip(13);

			playerMetadata = new PlayerMetadata(reader);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}
	}

	private byte[] createKey() {

		byte[] key = new byte[16];
		byte[] chars = "h3y_gUyZ".getBytes(StandardCharsets.UTF_8);

		for (int i = 0; i < 8; i++) {
			key[i * 2] = chars[i];
			key[1 + i * 2] = 0;
		}

		return key;
	}

	private <T> void validate(T value, T expected, String message) {

		if (!value.equals(expected))
			throw new IllegalArgumentException(String.format(message, value));
	}
}
