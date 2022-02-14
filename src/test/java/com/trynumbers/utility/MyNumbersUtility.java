package com.trynumbers.utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.trynumbers.attempt.entity.MyNumber;

import net.minidev.json.JSONArray;

public class MyNumbersUtility {
	public static MyNumber createMyNumberInstance() {
		MyNumber number = new MyNumber.MyNumberBuilder()
				.id(3)
				.name(3)
				.romaNotation("III")
				.binaryNotation("3")
				.description("The first unique prime due to the properties of its reciprocal.")
				.divisors(new int[] { 1, 3 })
				.build();
		return number;
	}
	
	
	public static JSONArray convertToJsonArray(int[] array) {
		JSONArray jsonArray = new JSONArray();
		jsonArray.addAll(Arrays.stream(array).boxed().collect(Collectors.toList()));
		return jsonArray;
	}
	
	
	public static List<MyNumber> createListWithThreeNumbers() {
		List<MyNumber> numbers = new ArrayList<>();
		
		numbers.add(new MyNumber.MyNumberBuilder()
				.id(1)
				.name(1)
				.romaNotation("I")
				.binaryNotation("1")
				.description("The most common leading digit in many sets of data, a consequence of Benford's law.")
				.divisors(new int[] { 1 })
				.build());
		numbers.add(new MyNumber.MyNumberBuilder()
				.id(2)
				.name(2)
				.romaNotation("II")
				.binaryNotation("10")
				.description("The third Fibonacci number, and the third and fifth Perrin numbers.")
				.divisors(new int[] {1,2})
				.build());
		numbers.add(new MyNumber.MyNumberBuilder()
				.name(3)
				.romaNotation("III")
				.binaryNotation("3")
				.description("The first unique prime due to the properties of its reciprocal.")
				.divisors(new int[] {1,3})
				.build());
		return numbers;
	}
}
