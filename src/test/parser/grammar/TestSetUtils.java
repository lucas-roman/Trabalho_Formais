package test.parser.grammar;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import main.parser.grammar.SetUtils;

import org.junit.Test;

public class TestSetUtils {
	
	@Test
	public void testSuperSet() {
		List<Integer> myList = new ArrayList<>();
		myList.add(1);
		myList.add(2);
		myList.add(1);
		myList.add(1);
		myList.add(1);
		myList.add(1);
		myList.add(1);
		myList.add(1);
		myList.add(1);
		myList.add(1);
		Assert.assertEquals(1023, SetUtils.powerSet(myList).size());
	}

}
