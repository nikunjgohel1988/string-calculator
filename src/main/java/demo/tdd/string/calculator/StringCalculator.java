package demo.tdd.string.calculator;

public class StringCalculator {

	public int add(String numbers) {
		if (numbers.isEmpty()) {
			return 0;
		}
		String[] numberArray = numbers.split(",");

		int sum = 0;

		for (String number : numberArray) {
			sum = sum + Integer.parseInt(number);
		}

		return sum;
	}

}
