package com.trynumbers.attempt.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trynumbers.attempt.entity.MyNumber;
import com.trynumbers.attempt.repository.NumberRepository;

@Service("numberServiceImpl")
public class NumberServiceImpl implements NumberService {

	private final NumberRepository numRepos;

	@Autowired
	public NumberServiceImpl(NumberRepository numRepos) {
		this.numRepos = numRepos;
	}

	@Override
	public List<MyNumber> getAllNumbers() {
		return numRepos.findAll();
	}

	@Override
	public Optional<MyNumber> getNumberById(long id) {
		return numRepos.findById(id);
	}

	@Override
	public MyNumber saveNewNumber(MyNumber number) {
		return numRepos.save(number);
	}

	@Override
	public MyNumber replaceMyNymber(MyNumber newNumber, long id) {

		Optional<MyNumber> optNumber = numRepos.findById(id);
		if (optNumber.isPresent()) {
			MyNumber number = optNumber.get();
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
