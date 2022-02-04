package com.trynumbers.attempt.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trynumbers.attempt.entity.MyNumber;
import com.trynumbers.attempt.exceptions.NumberNotFoundException;
import com.trynumbers.attempt.repository.NumberRepository;

@Service
public class NumberService {

	private NumberRepository numRepos;

	@Autowired
	public NumberService(NumberRepository numRepos) {
		this.numRepos = numRepos;
	}

	public List<MyNumber> getAllNumbers() {
		return numRepos.findAll();
	}

	public MyNumber getNumberById(long id) throws NumberNotFoundException {
		return numRepos.findById(id).orElseThrow(() -> new NumberNotFoundException(id));
	}

	public MyNumber saveNewNumber(MyNumber number) {
		return numRepos.save(number);
	}

	public MyNumber replaceMyNymber(MyNumber newNumber, long id) {
		return numRepos.findById(id).map(number -> {
			number.setName(newNumber.getName());
			number.setBinaryNotation(newNumber.getBinaryNotation());
			number.setRomaNotation(newNumber.getRomaNotation());
			number.setDescription(newNumber.getDescription());
			return numRepos.save(number);
		}).orElseGet(() -> {
			// newNumber.setId(id);
			return numRepos.save(newNumber);
		});
	}

	public void deleteMyNumber(long id) {
		numRepos.deleteById(id);
	}
}
