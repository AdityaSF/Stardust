package adiitya.stardust.io;

import java.io.*;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.Channels;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.UnaryOperator;

/**
 * This class reads binary data from an InputStream into java primitives.
 * The endianness of the stream is assumed to be little-endian.
 */
public class BinaryReader {

	private final ByteBuffer buf;

	public BinaryReader(Path path) throws IOException {
		this(path, in -> in);
	}

	public BinaryReader(Path path, UnaryOperator<InputStream> input) throws IOException {
		this.buf = readBytes(path, input);
	}

	/**
	 * This method reads a signed byte from the buffer using the default
	 * method from ByteBuffer.
	 *
	 * @return the byte
	 * @see ByteBuffer#get()
	 */
	public byte readByte() {
		return buf.get();
	}

	/**
	 * This methods reads an unsigned byte from the buffer. It uses
	 * {@link Byte#toUnsignedInt(byte)} as opposed to manually converting
	 * the byte.
	 *
	 * @return the byte
	 * @see Byte#toUnsignedInt(byte)
	 * @see #readByte()
	 */
	public int readUnsignedByte() {
		return Byte.toUnsignedInt(buf.get());
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
	 * the integer.
	 *
	 * @return the integer
	 * @see Integer#toUnsignedLong(int)
	 * @see #readInt()
	 */
	public long readUnsignedInt() {
		return Integer.toUnsignedLong(readInt());
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
	 * This method sets the position of the internal ByteBuffer. For testing purposes only.
	 *
	 * @param pos the position
	 * @return the reader at the specified position
	 */
	BinaryReader at(int pos) {

		buf.position(pos);

		return this;
	}

	/**
	 * The method gets the position of the internal ByteBuffer. For testing purposes only.
	 *
	 * @return the position
	 */
	int position() {
		return buf.position();
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
	static ByteBuffer readBytes(Path path, UnaryOperator<InputStream> input) throws IOException {

		try (SeekableByteChannel channel = Files.newByteChannel(path)) {

			InputStream in = input.apply(Channels.newInputStream(channel));
			long size = channel.size();

			if (size > Integer.MAX_VALUE - 8L)
				throw new OutOfMemoryError("Required array size too large");

			return read(in, (int) size);
		}
	}

	private static ByteBuffer read(InputStream in, int size) throws IOException {

		ByteBuffer buf = ByteBuffer.allocate(size).order(ByteOrder.LITTLE_ENDIAN);

		byte[] bytes = new byte[1024];
		int read;

		while ((read = in.read(bytes)) != -1) {
			buf.put(bytes, 0, read);
			bytes = new byte[1024];
		}

		in.close();
		buf.flip();

		return buf;
	}
}
