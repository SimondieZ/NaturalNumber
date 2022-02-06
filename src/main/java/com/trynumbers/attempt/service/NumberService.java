package com.trynumbers.attempt.service;

import java.util.List;

import com.trynumbers.attempt.entity.MyNumber;
import com.trynumbers.attempt.exceptions.NumberNotFoundException;

public interface NumberService {
	public List<MyNumber> getAllNumbers();
	public MyNumber getNumberById(long id) throws NumberNotFoundException;
	public MyNumber saveNewNumber(MyNumber number);
	public MyNumber replaceMyNymber(MyNumber newNumber, long id);
	public void deleteMyNumber(long id);
}
