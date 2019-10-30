package demo.tdd.string.calculator;

public class StringCalculator {

	private static String NUM_SEPARATOR = "[\\n,]";

	public int add(String numbers) {
		if (numbers.isEmpty()) {
			return 0;
		}

		String[] numberArray;

		if (numbers.startsWith("//")) {
			int startIndex = numbers.indexOf("//") + 2;
			int newLineIndex = numbers.indexOf("\n", startIndex);
			String separators = numbers.substring(startIndex, newLineIndex);

			numbers = numbers.substring(newLineIndex + 1);
			
			numberArray = numbers.split(separators);

		} else {
			numberArray = numbers.split(NUM_SEPARATOR);
		}

		int sum = 0;

		for (String number : numberArray) {
			sum = sum + Integer.parseInt(number);
		}

		return sum;
	}

}
