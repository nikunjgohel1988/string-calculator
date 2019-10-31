package demo.tdd.string.calculator.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import demo.tdd.string.calculator.StringCalculator;

public class StringCalculatorTest {

	private StringCalculator stringCalculator;
	
	@Rule
    public ExpectedException thrown= ExpectedException.none();

	@Before
	public void setUp() throws Exception {
		stringCalculator = new StringCalculator();
	}

	@After
	public void tearDown() throws Exception {
		stringCalculator = null;
	}

	@Test
	public void test_simple_calculator() {
		String input = "";
		int expected = 0;

		int actual = stringCalculator.add(input);

		assertEquals(expected, actual);

		input = "4";
		expected = 4;

		actual = stringCalculator.add(input);

		assertEquals(expected, actual);

		input = "1,2";
		expected = 3;

		actual = stringCalculator.add(input);

		assertEquals(expected, actual);
	}

	@Test
	public void test_arbitrary_number_size() {
		String input = "1,2,3,4,5,6,7,8,9";
		int expected = 45;

		int actual = stringCalculator.add(input);

		assertEquals(expected, actual);
	}

	@Test
	public void test_newline_separator() {
		String input = "1\n2,3";
		int expected = 6;

		int actual = stringCalculator.add(input);

		assertEquals(expected, actual);
	}

	@Test
	public void test_custom_separators() {
		String input = "//;\n1;2";
		int expected = 3;

		int actual = stringCalculator.add(input);

		assertEquals(expected, actual);
	}
	
	@Test
	public void test_negative_numbers_throwing_error() {
		
		String expectedExceptionMessage = "negatives not allowed: -2 -3";
		thrown.expect(Exception.class);
		thrown.expectMessage(expectedExceptionMessage);
		
		String input = "1,-2,-3";		

		int actual = stringCalculator.add(input);
	}
}
