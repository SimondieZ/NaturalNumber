package com.trynumbers.attempt.service;

import java.util.List;
import java.util.Optional;

import com.trynumbers.attempt.entity.MyNumber;

public interface NumberService {
	public List<MyNumber> getAllNumbers();

	public Optional<MyNumber> getNumberById(long id);

	public MyNumber saveNewNumber(MyNumber number);

	public MyNumber replaceMyNymber(MyNumber newNumber, long id);

	public void deleteMyNumber(long id);
}
