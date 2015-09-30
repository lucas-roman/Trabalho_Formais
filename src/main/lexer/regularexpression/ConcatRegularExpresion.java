package main.lexer.regularexpression;

class ConcatRegularExpresion extends RegularExpression {

	private RegularExpression leftChild;
	
	private RegularExpression rightChild;
	
	public ConcatRegularExpresion(RegularExpression leftChild,
			RegularExpression rightChild) {
		this.leftChild = leftChild;
		this.rightChild = rightChild;
	}
	
	
	public String toString() {
		return "" + leftChild + rightChild;
	}
	

}
