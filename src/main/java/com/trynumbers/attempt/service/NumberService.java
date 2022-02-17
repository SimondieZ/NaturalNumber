package com.trynumbers.attempt.service;

import java.util.List;
import java.util.Optional;

import com.trynumbers.attempt.entity.NaturalNumber;

public interface NumberService {
	public List<NaturalNumber> getAllNumbers();

	public Optional<NaturalNumber> getNumberById(long id);

	public NaturalNumber saveNewNumber(NaturalNumber number);

	public NaturalNumber replaceMyNymber(NaturalNumber newNumber, long id);

	public void deleteMyNumber(long id);
}
