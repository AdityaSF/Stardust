package adiitya.stardust.io;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class BinaryReaderTest {

	@Test
	void parseUnencryptedFileShouldPass() throws IOException {

		BinaryReader reader = new BinaryReader(Paths.get("src", "test", "resources", "test1.bin"));

		assertEquals(159, reader.readUnsignedInt());
		assertEquals(-159, reader.readInt());
		assertTrue(reader.readBoolean());
		assertFalse(reader.readBoolean());
		assertTrue(reader.readBoolean(2));
		assertFalse(reader.readBoolean(2));
		assertTrue(reader.readBoolean(8));
		assertFalse(reader.readBoolean(8));
		assertEquals("test", reader.readString());
		reader.skip(3);
		assertEquals(165, reader.readUnsignedByte());
	}
}
