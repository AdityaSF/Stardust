package adiitya.stardust.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DifficultyTest {

	@Test
	void checkGivenZeroShouldReturnSoftcore() {
		assertEquals(Difficulty.SOFTCORE, Difficulty.of(0));
	}

	@Test
	void checkGivenOneShouldReturnMediumcore() {
		assertEquals(Difficulty.MEDIUMCORE, Difficulty.of(1));
	}

	@Test
	void checkGivenTwoShouldReturnHardcore() {
		assertEquals(Difficulty.HARDCORE, Difficulty.of(2));
	}

	@Test
	void checkGivenNegativeValueShouldReturnHardcore() {
		assertEquals(Difficulty.HARDCORE, Difficulty.of(-1));
	}

	@Test
	void checkGivenOutOfBoundsValueShouldReturnHardcore() {
		assertEquals(Difficulty.HARDCORE, Difficulty.of(5));
	}
}
