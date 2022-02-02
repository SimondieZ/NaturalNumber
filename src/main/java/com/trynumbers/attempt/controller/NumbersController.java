package com.trynumbers.attempt.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.trynumbers.attempt.entity.MyNumber;
import com.trynumbers.attempt.service.NumberService;

@RestController
public class NumbersController {
	
	private final NumberService service;

	@Autowired
	public NumbersController(NumberService service) {
		this.service = service;
	}
	
	@GetMapping("/numbers")
	public List<MyNumber> allNumbers() {
		return service.getAllNumbers();
	}
	
	@GetMapping("/numbers/{id}")
	public MyNumber getNumberById(@PathVariable long id) {
		return service.getNumberById(id)
				.orElseThrow(() -> new NoSuchElementException());
	}
	
}
