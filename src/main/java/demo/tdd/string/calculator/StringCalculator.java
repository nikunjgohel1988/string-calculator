package demo.tdd.string.calculator;

public class StringCalculator {

	private static String NUM_SEPARATOR = "[\\n,]";

	public int add(String numbers) throws Exception {

		boolean negNumbersPresent = false;
		StringBuilder sb = null;

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
			int curNum = Integer.parseInt(number);

			if (curNum > 1000) {
				continue;
			}

			if (curNum < 0) {
				negNumbersPresent = true;
				if (sb == null) {
					sb = new StringBuilder("negatives not allowed:");
				}
				sb.append(" " + curNum);
			}

			if (negNumbersPresent)
				continue;

			sum = sum + curNum;
		}

		if (negNumbersPresent) {
			throw new Exception(sb.toString());
		}

		return sum;
	}

}
