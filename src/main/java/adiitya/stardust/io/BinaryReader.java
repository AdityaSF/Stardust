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
		this.buf = readBytes(input);
	}

	/**
	 * This method reads a byte from the buffer using the default
	 * method from ByteBuffer.
	 *
	 * @return the byte
	 * @see ByteBuffer#get()
	 */
	public byte readByte() {
		return buf.get();
	}

	/**
	 * This method reads a signed integer from the buffer using the default
	 * method from ByteBuffer.
	 *
	 * @return the integer
	 * @see ByteBuffer#getInt()
	 */
	public int readInt() {
		return buf.getInt();
	}

	/**
	 * This methods reads an unsigned integer from the buffer. It uses
	 * {@link Integer#toUnsignedLong(int)} as opposed to manually converting
	 * the integer. It is then casted back to an integer and returned.
	 *
	 * @return the integer
	 * @see Integer#toUnsignedLong(int)
	 * @see #readInt()
	 */
	public int readUnsignedInt() {
		return (int) Integer.toUnsignedLong(readInt());
	}

	/**
	 * This method reads a signed long from the buffer using the default
	 * method from ByteBuffer.
	 *
	 * @return the long
	 * @see ByteBuffer#getLong()
	 */
	public long readLong() {
		return buf.getLong();
	}

	/**
	 * Reads a String of the specified length. This string uses UTF-8
	 * encoding.
	 *
	 * @param len the length of the String
	 * @return the String
	 */
	public String readString(int len) {

		byte[] bytes = new byte[len];
		buf.get(bytes);

		return new String(bytes);
	}

	/**
	 * Reads a String where the first byte is the amount of characters.
	 * @return the String
	 * @see #readString(int)
	 */
	public String readString() {
		return readString(readByte());
	}

	/**
	 * Reads a boolean that is the length specified.
	 *
	 * @param len the byte length of the boolean
	 * @return false if the number read is 0, otherwise true
	 */
	public boolean readBoolean(int len) {

		byte[] bytes = new byte[len];
		buf.get(bytes);

		return !new BigInteger(bytes).equals(BigInteger.ZERO);
	}

	/**
	 * Reads a boolean that is 1 byte long.
	 *
	 * @return the boolean
	 * @see #readBoolean(int)
	 */
	public boolean readBoolean() {
		return readBoolean(1);
	}

	/**
	 * This method skips the specified amount of bytes by reading
	 * them into a byte array.
	 *
	 * @param len the amoount of bytes to skip
	 */
	public void skip(int len) {

		byte[] bytes = new byte[len];
		buf.get(bytes);
	}

	/**
	 * Reads an InputStream into a ByteBuffer. This method reads the
	 * InputStream into a ByteArrayOutputStream before wrapping
	 * the resulting byte array in a ByteBuffer. The ByteBuffer with
	 * be little endian.
	 *
	 * @param input the InputStream to be read
	 * @return the buffer
	 * @throws IOException if an error occurs while reading from the InputStream
	 */
	static ByteBuffer readBytes(InputStream input) throws IOException {

		try (ByteArrayOutputStream byteStream = new ByteArrayOutputStream()) {

			int bytesRead;
			byte[] data = new byte[1024];

			while ((bytesRead = input.read(data)) != -1)
				byteStream.write(data, 0, bytesRead);

			return ByteBuffer.wrap(byteStream.toByteArray())
					.order(ByteOrder.LITTLE_ENDIAN);
		} finally {
			input.close();
		}
	}
}
