package main.lexer.regularexpression;

class UnionRegularExpression extends RegularExpression {

	private RegularExpression leftChild;
	private RegularExpression rightChild;

	public UnionRegularExpression(RegularExpression leftChild,
			RegularExpression rightChild) {
		this.leftChild = leftChild;
		this.rightChild = rightChild;
	}
	
	public String toString() {
		return leftChild + "|" + rightChild;
	}

}
