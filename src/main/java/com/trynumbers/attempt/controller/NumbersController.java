package com.trynumbers.attempt.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.trynumbers.attempt.entity.MyNumber;
import com.trynumbers.attempt.exceptions.NumberNotFoundException;
import com.trynumbers.attempt.representation.NumberModelAssembler;
import com.trynumbers.attempt.service.NumberService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
public class NumbersController {

	private final NumberService service;

	private final NumberModelAssembler assembler;

	@Autowired
	public NumbersController(NumberService service, NumberModelAssembler assembler) {
		this.service = service;
		this.assembler = assembler;
	}

	@GetMapping("/numbers")
	public CollectionModel<EntityModel<MyNumber>> getAllNumbers() {
		List<MyNumber> allNumbers = service.getAllNumbers();
		List<EntityModel<MyNumber>> listOfHateOasNumbers = allNumbers.stream().map(assembler::toModel)
				.collect(Collectors.toList());
		return CollectionModel.of(listOfHateOasNumbers,
				linkTo(methodOn(NumbersController.class).getAllNumbers()).withSelfRel());
	}

	@GetMapping("/numbers/{id}")
	public EntityModel<MyNumber> getNumberById(@PathVariable long id) throws NumberNotFoundException {
		MyNumber number = service.getNumberById(id);
		return assembler.toModel(number);
	}

	@PostMapping("/numbers")
	public ResponseEntity<EntityModel<MyNumber>> addNewNumber(@RequestBody MyNumber number) {
		EntityModel<MyNumber> entityModel = assembler.toModel(service.saveNewNumber(number));
		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
	}

	@PutMapping("/numbers/{id}")
	public ResponseEntity<EntityModel<MyNumber>> replaceMyNumber(@RequestBody MyNumber newNumber, @PathVariable long id) {
		MyNumber updatedNumber = service.replaceMyNymber(newNumber, id);
		EntityModel<MyNumber> entityModel = assembler.toModel(updatedNumber);

		return ResponseEntity
				.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
	}

	@DeleteMapping("/numbers/{id}")
	public ResponseEntity<Object> deleteMyNumber(@PathVariable long id) {
		service.deleteMyNumber(id);
		return ResponseEntity.noContent().build();
	}
}
