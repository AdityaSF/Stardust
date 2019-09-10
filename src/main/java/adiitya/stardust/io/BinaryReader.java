package adiitya.stardust.io;

import java.io.*;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * This class reads binary data from an InputStream into java primitives.
 * The endianness of the stream is assumed to be little-endian.
 */
public class BinaryReader {

	private final ByteBuffer buf;

	public BinaryReader(InputStream input) throws IOException {

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int len;
		byte[] bytes = new byte[1024];

		while ((len = input.read(bytes)) != -1)
			out.write(bytes, 0, len);

		buf = ByteBuffer.wrap(out.toByteArray()).order(ByteOrder.LITTLE_ENDIAN);
		out.close();
		input.close();
	}

	public byte readByte() {
		return buf.get();
	}

	public int readInt() {
		return buf.getInt();
	}

	public int readUnsignedInt() {
		return (int) Integer.toUnsignedLong(readInt());
	}

	public long readLong() {
		return buf.getLong();
	}

	public String readString(int len) {

		byte[] bytes = new byte[len];
		buf.get(bytes);

		return new String(bytes);
	}

	public String readString() {
		return readString(readByte());
	}

	public boolean readBoolean(int len) {

		byte[] bytes = new byte[len];
		buf.get(bytes);

		return !new BigInteger(bytes).equals(BigInteger.ZERO);
	}

	public boolean readBoolean() {
		return readBoolean(1);
	}

	public void skip(int len) {

		byte[] bytes = new byte[len];
		buf.get(bytes);
	}
}
