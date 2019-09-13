package adiitya.stardust.io;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class BinaryReaderTest {

	private static BinaryReader unencrypted;

	@BeforeAll
	static void beforeAll() throws IOException {

		unencrypted = new BinaryReader(Paths.get("src/test/resources/test1.bin"));
	}

	@Test
	void readUnsignedIntWithUnencryptedFileShouldPass() {
		assertEquals(159, unencrypted.at(0).readUnsignedInt());
	}

	@Test
	void readSignedIntWithUnencryptedFileShouldPass() {
		assertEquals(-159, unencrypted.at(4).readInt());
	}

	@Test
	void readTrueSingleByteBooleanWithUnencryptedFileShouldPass() {
		assertTrue(unencrypted.at(8).readBoolean());
	}

	@Test
	void readFalseSingleByteBooleanWithUnencryptedFileShouldPass() {
		assertFalse(unencrypted.at(9).readBoolean());
	}

	@Test
	void readTrueTwoByteBooleanWithUnencryptedFileShouldPass() {
		assertTrue(unencrypted.at(10).readBoolean(2));
	}

	@Test
	void readFalseTwoByteBooleanWithUnencryptedFileShouldPass() {
		assertFalse(unencrypted.at(12).readBoolean(2));
	}

	@Test
	void readTrueEightByteBooleanWithUnencryptedFileShouldPass() {
		assertTrue(unencrypted.at(14).readBoolean(8));
	}

	@Test
	void readFalseEightByteBooleanWithUnencryptedFileShouldPass() {
		assertFalse(unencrypted.at(22).readBoolean(8));
	}

	@Test
	void readVariableStringWtihUnencryptedFileShouldPass() {
		assertEquals("test", unencrypted.at(30).readString());
	}

	@Test
	void readFixedStringWtihUnencryptedFileShouldPass() {
		assertEquals("test", unencrypted.at(31).readString(4));
	}

	@Test
	void skipWithUnencryptedFileShouldPass() {
		unencrypted.at(0).skip(10);
		assertEquals(10, unencrypted.position());
	}
}
