package com.trynumbers.attempt.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trynumbers.attempt.entity.MyNumber;

import com.trynumbers.attempt.repository.NumberRepository;

@Service
public class NumberService {
	
	@Autowired
	private NumberRepository numRepos;
	
	public List<MyNumber> getAllNumbers() {
		return numRepos.findAll();
	}
	
	public Optional<MyNumber> getNumberById(long id) {
		return numRepos.findById(id);
	}
}
