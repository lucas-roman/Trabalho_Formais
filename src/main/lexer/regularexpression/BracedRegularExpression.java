package main.lexer.regularexpression;

class BracedRegularExpression extends RegularExpression {

	private RegularExpression regularExpression;

	public BracedRegularExpression(RegularExpression regularExpression) {
		this.regularExpression = regularExpression;
	}
	
	public String toString() {
		return "(" + regularExpression + ")";
	}

}
