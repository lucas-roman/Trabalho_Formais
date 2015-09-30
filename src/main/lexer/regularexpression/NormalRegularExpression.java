package main.lexer.regularexpression;

class NormalRegularExpression extends RegularExpression {

	private char recognizedChar;
	
	NormalRegularExpression(char c) {
		recognizedChar = c;
	}
	
	public String toString() {
		return "" + recognizedChar;
	}

}
