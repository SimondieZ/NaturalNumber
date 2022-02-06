package com.trynumbers.attempt.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.trynumbers.attempt.entity.MyNumber;
import com.trynumbers.attempt.exceptions.NumberNotFoundException;
import com.trynumbers.attempt.service.NumberService;
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
	public MyNumber getNumberById(@PathVariable long id) throws NumberNotFoundException {
		return service.getNumberById(id);
	}
	
	@PostMapping("/numbers")
	public MyNumber addNewNumber(@RequestBody MyNumber number) {
		return service.saveNewNumber(number);
	}
	
	@PutMapping("/numbers/{id}")
	public MyNumber replaceMyNumber (@RequestBody MyNumber newNumber, @PathVariable long id) {
		return service.replaceMyNymber(newNumber, id);
	}
	
	@DeleteMapping("/numbers/{id}")
	public void deleteMyNumber(@PathVariable long id) {
		service.deleteMyNumber(id);
	}
}
