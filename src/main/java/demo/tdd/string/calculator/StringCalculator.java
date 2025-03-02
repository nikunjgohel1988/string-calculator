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
	private static final String PIPE = "|";
	private static final String ESCAPE_CHAR = "\\";
	private static final String OPENING_BRACE = "(";
	private static final String CLOSING_BRACE = ")";
	private static final String OPENING_BRACKET = "[";
	private static final String CLOSING_BRACKET = "]";

	private static final String REGEX_META_CHARS = ".$|()[{^?*+\\";

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

		if (numSeparatorsStr.startsWith(OPENING_BRACKET)) {
			Matcher m = CUSTOM_SEPARATOR_MATCH_PATTERN.matcher(numSeparatorsStr);

			int matchCount = m.groupCount();

			StringBuilder separatorPattern = new StringBuilder();

			if (matchCount > 1) {
				separatorPattern.append(OPENING_BRACKET);
			}

			boolean firstMatch = true;

			while (m.find()) {

				if (!firstMatch) {
					separatorPattern.append(PIPE);
				}

				separatorPattern.append(OPENING_BRACE);
				String separator = m.group(1);
				for (char ch : separator.toCharArray()) {
					if (isMetaChar(ch)) {
						separatorPattern.append(ESCAPE_CHAR);
					}
					separatorPattern.append(ch);
				}
				separatorPattern.append(CLOSING_BRACE);

				firstMatch = false;
			}

			if (matchCount > 1) {
				separatorPattern.append(CLOSING_BRACKET);
			}
			numSeperators.append(separatorPattern);

		} else {
			numSeperators.append(numSeparatorsStr);
		}
	}

	private boolean isMetaChar(char ch) {
		if (REGEX_META_CHARS.indexOf(ch) != -1) {
			return true;
		} else {
			return false;
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
