package com.trynumbers.attempt.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trynumbers.attempt.entity.NaturalNumber;
import com.trynumbers.attempt.repository.NumberRepository;

@Service
public class NumberServiceImpl implements NumberService {

	private final NumberRepository numRepos;

	@Autowired
	public NumberServiceImpl(NumberRepository numRepos) {
		this.numRepos = numRepos;
	}

	@Override
	public List<NaturalNumber> getAllNumbers() {
		return numRepos.findAll();
	}

	@Override
	public Optional<NaturalNumber> getNumberById(long id) {
		return numRepos.findById(id);
	}

	@Override
	public NaturalNumber saveNewNumber(NaturalNumber number) {
		return numRepos.save(number);
	}

	@Override
	public NaturalNumber replaceMyNymber(NaturalNumber newNumber, long id) {

		Optional<NaturalNumber> optNumber = numRepos.findById(id);
		if (optNumber.isPresent()) {
			NaturalNumber number = optNumber.get();
			number.setName(newNumber.getName());
			number.setBinaryNotation(newNumber.getBinaryNotation());
			number.setRomaNotation(newNumber.getRomaNotation());
			number.setDescription(newNumber.getDescription());
			number.setDivisors(newNumber.getDivisors());
			return numRepos.save(number);
		} else {
			return numRepos.save(newNumber);
		}
	}

	@Override
	public void deleteMyNumber(long id) {
		numRepos.deleteById(id);
	}

}
