package com.simondiez.springnumbers.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simondiez.springnumbers.entity.NaturalNumber;
import com.simondiez.springnumbers.repository.NumberRepository;

@Service
public class NumberServiceImpl implements NumberService {

	private final NumberRepository numberRepository;

	@Autowired
	public NumberServiceImpl(NumberRepository numRepos) {
		this.numberRepository = numRepos;
	}

	@Override
	public List<NaturalNumber> getAllNumbers() {
		return numberRepository.findAll();
	}

	@Override
	public Optional<NaturalNumber> getNumberById(long id) {
		return numberRepository.findById(id);
	}

	@Override
	public NaturalNumber saveNewNumber(NaturalNumber number) {
		return numberRepository.save(number);
	}

	@Override
	public NaturalNumber replaceMyNymber(NaturalNumber newNumber, long id) {

		Optional<NaturalNumber> foundNumber = numberRepository.findById(id);
		if (foundNumber.isPresent()) {
			NaturalNumber number = foundNumber.get();
			number.setValue(newNumber.getValue());
			number.setBinaryNotation(newNumber.getBinaryNotation());
			number.setRomaNotation(newNumber.getRomaNotation());
			number.setDescription(newNumber.getDescription());
			number.setDivisors(newNumber.getDivisors());
			return numberRepository.save(number);
		} else {
			return numberRepository.save(newNumber);
		}
	}

	@Override
	public void deleteMyNumber(long id) {
		numberRepository.deleteById(id);
	}
}
