package adiitya.stardust.util;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class TerrariaFile {

	private static final byte[] ENCRYPTION_KEY = generateKey();

	private static final long UNSIGNED_INT = 0x00000000FFFFFFFFL;
	private static final BigInteger UNSIGNED_LONG = BigInteger.ONE.shiftLeft(Long.SIZE).subtract(BigInteger.ONE);

	private final ByteBuffer buf;

	public TerrariaFile(File file) throws BadPaddingException, IllegalBlockSizeException {
		this.buf = loadBytes(file);
	}

	public byte readByte() {
		return buf.get();
	}

	public long readInt() {
		return buf.getInt() & UNSIGNED_INT;
	}

	public BigInteger readLong() {
		return BigInteger.valueOf(buf.getLong())
				.and(UNSIGNED_LONG);
	}

	public String readString(int len) {

		byte[] bytes = new byte[len];
		buf.get(bytes);

		return new String(bytes);
	}

	public FileType readFileType() {
		return FileType.of(buf.get());
	}

	public void print(int len) {

		buf.mark();
		byte[] bytes = new byte[len];
		buf.get(bytes);
		buf.reset();

		System.out.println(Arrays.toString(bytes));
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

	private byte[] loadFile(File file) {

		try {
			return Files.readAllBytes(file.toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}

		return new byte[0];
	}

	private ByteBuffer loadBytes(File file) throws BadPaddingException, IllegalBlockSizeException {

		Cipher cipher = getCipher();
		byte[] bytes = loadFile(file);

		return ByteBuffer.wrap(cipher.doFinal(bytes)).order(ByteOrder.LITTLE_ENDIAN);
	}
}
