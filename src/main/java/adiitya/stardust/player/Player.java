package adiitya.stardust.player;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Player {

	private static final byte[] ENCRYPTION_KEY = generateKey();

	private final File file;

	public int version;

	public Player(File file) {

		this.file = file;

		init();
	}

	private void init() {

		try {

			ByteBuffer buf = loadBytes();

			version = buf.get() & 0xFF;
		} catch (BadPaddingException | IllegalBlockSizeException e) {
			e.printStackTrace();
		}
	}

	private ByteBuffer loadBytes() throws BadPaddingException, IllegalBlockSizeException {

		Cipher cipher = getCipher();
		byte[] bytes = loadFile();

		return ByteBuffer.wrap(cipher.doFinal(bytes));
	}

	private byte[] loadFile() {

		try {
			return Files.readAllBytes(file.toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}

		return new byte[0];
	}

	private Cipher getCipher() {

		SecretKey key = new SecretKeySpec(ENCRYPTION_KEY, "AES");
		IvParameterSpec iv = new IvParameterSpec(ENCRYPTION_KEY);

		try {

			Cipher aes = Cipher.getInstance("AES/CBC/NoPadding");
			aes.init(Cipher.DECRYPT_MODE, key, iv);

			return aes;
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		}

		return null;
	}

	private static byte[] generateKey() {

		byte[] unpadded = "h3y_gUyZ".getBytes(StandardCharsets.UTF_8);
		byte[] key = new byte[16];

		for (int i = 0; i < 8; i++) {
			key[i * 2] = unpadded[i];
			key[1 + i * 2] = (byte) ((char) 0);
		}

		return key;
	}
}
