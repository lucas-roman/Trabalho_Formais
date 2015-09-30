package main.lexer.regularexpression;

class ClosureRegularExpression extends RegularExpression {

	private RegularExpression regularExpression;

	public ClosureRegularExpression(RegularExpression regularExpression) {
		this.regularExpression = regularExpression;
	}
	
	public String toString() {
		return regularExpression + "*";
	}

}
