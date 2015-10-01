package main.lexer.regularexpression;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import javax.management.OperationsException;

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

	private static Map<Character, Integer> operatorPrecedence = new HashMap<>();

	static {
		operatorPrecedence.put('*', 2);
		operatorPrecedence.put('+', 2);
		operatorPrecedence.put('?', 2);
		operatorPrecedence.put('.', 1);
		operatorPrecedence.put('|', 0);
	}

	public static RegularExpression stringToRE(String input)
			throws IllegalRegularExpressionException {
		String modifiedInput = insertConcatenationPoints(input);
		modifiedInput = reverseInversedUnaryOperatores(modifiedInput);
		String reversePol = reversePolishNotation(modifiedInput);
		return null;
	}

	private static String reverseInversedUnaryOperatores(String modifiedInput) throws IllegalRegularExpressionException {
		String result = "";
		char previousChar = 0;
		for(int i = 0; i < modifiedInput.length(); i++) {
			if(unary(modifiedInput.charAt(i))) {
				if(previousChar == ')') {
					int j = i;
					try {
						for(; modifiedInput.charAt(j) != '('; j--);
					} catch (IndexOutOfBoundsException ex) {
						throw new IllegalRegularExpressionException();
					}
					String partResult = result.substring(0, j);
					partResult += modifiedInput.charAt(i);
					partResult += result.substring(j, i);
					result = partResult;
				}
				else {
					String partResult = result.substring(0, i - 1);
					partResult += modifiedInput.charAt(i);
					partResult += modifiedInput.charAt(i - 1);
					result = partResult;
				}
			}
			else {
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
		for (char c : input.toCharArray()) {
			if (isLiteral(c)) {
				if (isLiteral(previousCharacter)) {
					result += ".";
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
		for (char c : input.toCharArray()) {
			// Is operator
			if (isLiteral(c)) {
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
		System.out.println(result);
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
