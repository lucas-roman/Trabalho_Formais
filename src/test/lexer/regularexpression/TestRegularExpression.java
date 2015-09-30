package test.lexer.regularexpression;

import junit.framework.Assert;
import main.lexer.regularexpression.RegularExpression;

import org.junit.Test;

public class TestRegularExpression {
	
	private String regularExpressionValue1 = "(a|b)*(dª)";
	
	private String regularExpressionValue2 = "°";
	
	private RegularExpression rea = RegularExpression.createRegularExpression('a');
	private RegularExpression reb = RegularExpression.createRegularExpression('b');
	private RegularExpression red = RegularExpression.createRegularExpression('d');
	private RegularExpression reEmptyWord = RegularExpression.createRegularExpression('\0');
	private RegularExpression reEmpty = RegularExpression.emptyRegularExpression();

	@Test 
	public void testStringValue() {
		RegularExpression val1 = rea.union(reb).brace().closure().concatenate(red.concatenate(reEmptyWord).brace());
		Assert.assertEquals(regularExpressionValue1, val1.toString());
		Assert.assertEquals(regularExpressionValue2, reEmpty.toString());
	}


}
