package main.parser.grammar;

import java.util.ArrayList;
import java.util.List;

public class SetUtils {
	
	public static <E> List<List<E>> powerSet(List<E> set) {
		int size = set.size();
		SetUtils calculator = new SetUtils();
		return calculator.powerSetAux(set, size);
	}
	
	private <E> List<List<E>> powerSetAux(List<E> set, int size) {
		List<List<E>> returnList;
		if(size == 1) {
			returnList = new ArrayList<List<E>>();
			returnList.add(set);
			return returnList; 
		}
		List<E> firstHalf = new ArrayList<>(set);
		List<E> secondHalf = new ArrayList<>();
		for(int i = 0; i < size/2; i++) {
			secondHalf.add(set.get(i));
			firstHalf.remove(set.get(i));
		}
		List<List<E>> firstHalfSubSet = powerSetAux(firstHalf, firstHalf.size());
		List<List<E>> secondHalfSubSet = powerSetAux(secondHalf, secondHalf.size());
		returnList = combineHalves(firstHalfSubSet, secondHalfSubSet);
		return returnList;
	}
	
	private <E> List<List<E>> combineHalves(List<List<E>> firstHalf, List<List<E>> secondHalf) {
		List<List<E>> retList = new ArrayList<>();
		for(List<E> listOfElementFirst : firstHalf) {
			for(List<E> listOfElementSecond : secondHalf) {
				List<E> newList = new ArrayList<>();
				newList.addAll(listOfElementFirst);
				newList.addAll(listOfElementSecond);
				retList.add(newList);
			}
		}
		retList.addAll(firstHalf);
		retList.addAll(secondHalf);
		return retList;
	}
	
	//The constructor should be accessed only from within this class.
	private SetUtils() {
		
	}

}
