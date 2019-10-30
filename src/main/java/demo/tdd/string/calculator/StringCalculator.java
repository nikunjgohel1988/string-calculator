package demo.tdd.string.calculator;

public class StringCalculator {

	private static String NUM_SEPARATOR = ",";

	public int add(String numbers) {
		if (numbers.isEmpty()) {
			return 0;
		}
		String[] numberArray = numbers.split(NUM_SEPARATOR);

		int sum = 0;

		for (String number : numberArray) {
			sum = sum + Integer.parseInt(number);
		}

		return sum;
	}

}
