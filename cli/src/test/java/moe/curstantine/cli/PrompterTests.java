package moe.curstantine.cli;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <strong>Acknowledgements</strong>
 * <p>
 * System.in instrumentation derived from: <a href="https://stackoverflow.com/questions/1647907/junit-how-to-simulate-system-in-testing">JUnit: How to simulate System.in testing?</a>
 */
class PrompterTest {
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;
	private final InputStream originalIn = System.in;

	@BeforeEach
	void setUp() {
		System.setOut(new PrintStream(outContent));
	}

	@AfterEach
	void restoreStreams() {
		System.setOut(originalOut);
		System.setIn(originalIn);
	}

	@ParameterizedTest
	@CsvSource({
			"1, 5, 3, '3 is within range'",
			"1, 5, 1, '1 is lower bound'",
			"1, 5, 5, '5 is upper bound'"
	})
	void testValidRangeInputs(int start, int end, int input, String scenario) {
		provideInput(String.valueOf(input));
		int result = Prompter.promptRangeInteger(start, end, "Test prompt: ", "Invalid input");
		assertEquals(input, result, scenario);
	}

	@ParameterizedTest
	@CsvSource({
			"1, 5, 0, 'Below lower bound'",
			"1, 5, 6, 'Above upper bound'"
	})
	void testInvalidRangeInputsWithRetry(int start, int end, int invalidInput, String scenario) {
		provideInput(String.valueOf(invalidInput));
		final int result = Prompter.promptRangeInteger(start, end, "Test prompt: ", "Invalid input");
		assertEquals(3, result, scenario);
		assertTrue(outContent.toString().contains("Invalid input"), "Should print invalid input message");
	}

	@Test
	void testBlankInputReturnsNegativeOne() {
		provideInput("\n");
		final int result = Prompter.promptRangeInteger(1, 5, "Test prompt: ", "Invalid input");
		assertEquals(-1, result, "Blank input should return -1");
	}

	@ParameterizedTest
	@ValueSource(strings = {"y", "Y", "yes", "Yes"})
	void testTrueConditionalInputs(String input) {
		provideInput(input);
		final boolean result = Prompter.promptConditional("Confirm?");
		assertTrue(result, "Should return true for " + input);
	}

	@ParameterizedTest
	@ValueSource(strings = {"n", "N", "no", "No"})
	void testFalseConditionalInputs(String input) {
		provideInput(input);
		final boolean result = Prompter.promptConditional("Confirm?");
		assertFalse(result, "Should return false for " + input);
	}

	// Helper method to simulate System.in input
	private void provideInput(String... inputs) {
		final String source = String.join(System.lineSeparator(), inputs);
		final ByteArrayInputStream testIn = new ByteArrayInputStream(source.getBytes());
		System.setIn(testIn);
	}
}
