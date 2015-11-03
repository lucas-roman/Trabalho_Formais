package main.lexer.regularexpression;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import main.lexer.regularexpression.exceptions.IllegalRegularExpressionException;

public class StringToRE {

	/*
	 * UNIVERSIDADE FEDERAL DE SANTA CATARINA INE - DEPARTAMENTO DE INFORM�TICA
	 * E ESTAT�STICA LINGUAGENS FORMAIS E COMPILADORES
	 *
	 * @author LUCAS FINGER ROMAN
	 *
	 * @author RODRIGO PEDRO MARQUES Copyright � 2015
	 */

	/*
	 * I guess this is actually the worst, hardest to maintain class ever written by mankind...
	 * Jesus
	 */

	private static Map<Character, Integer> operatorPrecedence = new HashMap<>();

	private static final char KLEENE = '*';
	private static final char POSITIVE = '+';
	private static final char ALTERNATION = '|';
	private static final char CONCATENATION = '.';
	private static final char INTERROGATION = '?';

	static {
		operatorPrecedence.put(KLEENE, 2);
		operatorPrecedence.put(POSITIVE, 2);
		operatorPrecedence.put(INTERROGATION, 2);
		operatorPrecedence.put(CONCATENATION, 1);
		operatorPrecedence.put(ALTERNATION, 0);
	}

	public static RegularExpression stringToRE(String input) throws IllegalRegularExpressionException {
		String modifiedInput = insertConcatenationPoints(input);
		modifiedInput = reverseInversedUnaryOperatores(modifiedInput);
		String reversePol = reversePolishNotation(modifiedInput);
		Stack<RegularExpression> resultStack = new Stack<>();
		for (int i = 0; i < reversePol.length(); i++) {
			if (isLiteral(reversePol.charAt(i))) {
				resultStack.push(RegularExpression
						.createRegularExpression(reversePol.charAt(i)));
			} else if (reversePol.charAt(i) == '*') {
				if (resultStack.isEmpty()) {
					throw new IllegalRegularExpressionException();
				}
				RegularExpression re = resultStack.pop();
				RegularExpression toPush = re.kleene();
				resultStack.push(toPush);
			} else if (reversePol.charAt(i) == '´') {
				resultStack.push(RegularExpression
						.createRegularExpression('\0'));
			} else if (reversePol.charAt(i) == '+') {
				if (resultStack.isEmpty()) {
					throw new IllegalRegularExpressionException();
				}
				RegularExpression re = resultStack.pop();
				RegularExpression toPush = re.positive();
				resultStack.push(toPush);
			} else if (reversePol.charAt(i) == '?') {
				if (resultStack.isEmpty()) {
					throw new IllegalRegularExpressionException();
				}
				RegularExpression re = resultStack.pop();
				RegularExpression toPush = re.interrogation();
				resultStack.push(toPush);
			} else if (reversePol.charAt(i) == '.') {
				if (resultStack.isEmpty()) {
					throw new IllegalRegularExpressionException();
				}
				RegularExpression rhs = resultStack.pop();
				if (resultStack.isEmpty()) {
					throw new IllegalRegularExpressionException();
				}
				RegularExpression lhs = resultStack.pop();
				RegularExpression toPush = lhs.concatenate(rhs);
				resultStack.push(toPush);
			} else if (reversePol.charAt(i) == '|') {
				if (resultStack.isEmpty()) {
					throw new IllegalRegularExpressionException();
				}
				RegularExpression rhs = resultStack.pop();
				if (resultStack.isEmpty()) {
					throw new IllegalRegularExpressionException();
				}
				RegularExpression lhs = resultStack.pop();
				RegularExpression toPush = lhs.alternation(rhs);
				resultStack.push(toPush);
			}
			else if (reversePol.charAt(i) == '\\') {
				if(i == reversePol.length()) {
					throw new IllegalRegularExpressionException();
				}
				char lookAhead = reversePol.charAt(i + 1);
				resultStack.push(RegularExpression.createRegularExpression(lookAhead));
				i++;
			}
		}
		if(resultStack.size() > 1) {
			throw new IllegalRegularExpressionException();
		}
		return resultStack.pop();
	}

	private static String reverseInversedUnaryOperatores(String modifiedInput) throws IllegalRegularExpressionException {
		String result = "";
		char previousChar = 0;
		boolean reverseOnSlash = false;
		for (int i = 0; i < modifiedInput.length(); i++) {
			if(modifiedInput.charAt(i) == '\\' && !reverseOnSlash) {
				reverseOnSlash = true;
			}
			else if(modifiedInput.charAt(i) == '\\' && reverseOnSlash) {
				reverseOnSlash = false;
			}
			if (unary(modifiedInput.charAt(i))) {
				if (previousChar == ')') {
					int j = i;
					try {
						for (; modifiedInput.charAt(j) != '('; j--)
							;
					} catch (IndexOutOfBoundsException ex) {
						throw new IllegalRegularExpressionException();
					}
					String partResult = result.substring(0, j);
					partResult += modifiedInput.charAt(i);
					partResult += result.substring(j, i);
					result = partResult;
				}
				else if(previousChar != '\\' && reverseOnSlash) {
					String partResult = result.substring(0, i - 2);
					partResult += modifiedInput.charAt(i);
					partResult += modifiedInput.charAt(i - 2);
					partResult += modifiedInput.charAt(i - 1);
					result = partResult;
					reverseOnSlash = false;
				}
				else if(previousChar != '\\'){
					String partResult = result.substring(0, i - 1);
					partResult += modifiedInput.charAt(i);
					partResult += modifiedInput.charAt(i - 1);
					result = partResult;
				}
				else {
					result += modifiedInput.charAt(i);
				}
			} else {
				result += modifiedInput.charAt(i);
			}
			previousChar = modifiedInput.charAt(i);
		}
		return result;
	}

	private static boolean unary(char c) {
		return c == '*' || c == '?' || c == '+';
	}

	private static String insertConcatenationPoints(String input) {
		char previousCharacter = 0;
		String result = "";
		boolean wasValid = false;
		boolean escape = false;
		for (char c : input.toCharArray()) {
			if(c == '\\' && !escape) {
				escape = true;
			}
			else if(c == '\\'  && escape) {
				wasValid = true;
				escape = false;
			}
			else if (c != '\\' && escape) {
				wasValid = true;
				escape = false;
			}
			else {
				escape = false;
			}
			if (previousCharacter != '\\') {

				if ((isLiteral(c) || c == '(' || c == '´' || c == '\\')) {
					if (unary(previousCharacter) || previousCharacter == ')'
							|| isLiteral(previousCharacter)
							|| previousCharacter == '´') {
						result += '.';
					}
					else if(wasValid) {
						wasValid = false;
						result += '.';
					}

				}
				if(wasValid) {
					wasValid = false;
				}
			}
			result += c;
			previousCharacter = c;
		}
		return result;
	}

	private static boolean isLiteral(char c) {
		return (Character.isLetter(c) || Character.isDigit(c));
	}

	private static String reversePolishNotation(String input)
			throws IllegalRegularExpressionException {
		final Stack<Character> operationStack = new Stack<>();
		// Parse each char individually
		String result = "";
		boolean escaped = false;
		for (char c : input.toCharArray()) {
			if(c == '\\' && !escaped) {
				escaped = true;
				result += c;
			}
			else if (c == '\\' && escaped) {
				escaped = false;
				result += c;
			}
			else if (c != '\\' && escaped) {
				escaped = false;
				result += c;
			}
			else if (isLiteral(c) || c == '´') {
				result += c;
			} else if (isOperator(c)) {
				while (!operationStack.isEmpty()
						&& precedenceValue(c) <= precedenceValue(topOfStack(operationStack))
						&& topOfStack(operationStack) != '(') {
					char opVal = operationStack.pop();
					result += opVal;
				}
				operationStack.push(c);
			} else if (c == '(') {
				operationStack.push(c);
			} else if (c == ')') {
				if (operationStack.isEmpty()) {
					throw new IllegalRegularExpressionException();
				}
				while (topOfStack(operationStack) != '(') {
					result += operationStack.pop();
					if (operationStack.isEmpty()) {
						throw new IllegalRegularExpressionException();
					}
				}
				operationStack.pop();
			}
		}
		while (!operationStack.isEmpty()) {
			if (topOfStack(operationStack) == '('
					|| topOfStack(operationStack) == ')') {
				throw new IllegalRegularExpressionException();
			}
			result += operationStack.pop();
		}
		return result;
	}

	private static char topOfStack(Stack<Character> stack) {
		char result = stack.pop();
		stack.push(result);
		return result;
	}

	private static int precedenceValue(char c) {
		if (c == '(')
			return 3;
		return operatorPrecedence.get(c);
	}

	private static boolean isOperator(char c) {
		return operatorPrecedence.containsKey(c);

	}
}
