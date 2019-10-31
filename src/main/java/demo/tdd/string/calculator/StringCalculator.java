package demo.tdd.string.calculator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringCalculator {

	private static final String DEFAULT_NUM_SEPARATORS = "[\\n|,]";
	private static final int DEFAULT_RETURN_VALUE = 0;

	private static final String CUSTOM_SEPARATORS_START_PATTERN = "//";
	private static final String CUSTOM_SEPARATORS_END_PATTERN = "\n";

	private static final Pattern CUSTOM_SEPARATOR_MATCH_PATTERN = Pattern.compile("\\[(.*?)\\]");

	private static final String SPACE = " ";

	private String[] numberArray;
	private StringBuilder numSeperators;

	public int add(String numbers) throws Exception {

		boolean negNumbersPresent = false;
		StringBuilder exceptionMessage = null;

		if (numbers.isEmpty()) {
			return DEFAULT_RETURN_VALUE;
		}

		if (numbers.startsWith(CUSTOM_SEPARATORS_START_PATTERN)) {

			int startIndex = numbers.indexOf(CUSTOM_SEPARATORS_START_PATTERN) + 2;
			int newLineIndex = numbers.indexOf(CUSTOM_SEPARATORS_END_PATTERN, startIndex);

			buildNumberSeparatorsSplitPattern(numbers.substring(startIndex, newLineIndex));

			numbers = numbers.substring(newLineIndex + 1);

			numberArray = numbers.split(numSeperators.toString());

		} else {
			numberArray = numbers.split(DEFAULT_NUM_SEPARATORS);
		}

		int sum = 0;

		for (String number : numberArray) {
			int curNum = Integer.parseInt(number);

			if (shouldIgnoreNumber(curNum)) {
				continue;
			}

			if (isNegativeNumber(curNum)) {
				negNumbersPresent = true;
				exceptionMessage = buildNegativesNotAllowedExceptionMessage(exceptionMessage, curNum);
				continue;
			}

			sum = sum + curNum;
		}

		if (negNumbersPresent) {
			throw new Exception(exceptionMessage.toString());
		}

		return sum;
	}

	private void buildNumberSeparatorsSplitPattern(String numSeparatorsStr) {
		if (numSeperators == null) {
			numSeperators = new StringBuilder();
		}

		if (numSeparatorsStr.charAt(0) == '[') {
			Matcher m = CUSTOM_SEPARATOR_MATCH_PATTERN.matcher(numSeparatorsStr);
			if (m.find()) {
				String separator = m.group(1);
				StringBuilder separatorPattern = new StringBuilder();
				for (char ch : separator.toCharArray()) {
					separatorPattern.append("\\");
					separatorPattern.append(ch);
				}

				numSeperators.append(separatorPattern);
			}
		} else {
			numSeperators.append(numSeparatorsStr);
		}
	}

	private boolean shouldIgnoreNumber(int num) {
		if (num > 1000) {
			return true;
		}
		return false;
	}

	private boolean isNegativeNumber(int num) {
		if (num < 0) {
			return true;
		}
		return false;
	}

	private StringBuilder buildNegativesNotAllowedExceptionMessage(StringBuilder exceptionMessage, int num) {
		if (exceptionMessage == null) {
			exceptionMessage = new StringBuilder("negatives not allowed:");
		}
		exceptionMessage.append(SPACE + num);

		return exceptionMessage;
	}

}
