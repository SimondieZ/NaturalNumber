package com.simondiez.springnumbers.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.simondiez.springnumbers.entity.NaturalNumber;
import com.simondiez.springnumbers.exceptions.ErrorDetails;
import com.simondiez.springnumbers.exceptions.NumberNotFoundException;
import com.simondiez.springnumbers.representation.NumberModelAssembler;
import com.simondiez.springnumbers.service.NumberService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


/**
 * <ul>
 * <li>Intercepts incoming requests;
 * <li>converts the payload of the request to the internal structure of the data;
 * <li>gets data from the Model and advances that data to the View for rendering.
 * </ul>
 * 
 * @author Serafim Sokhin
 */
@RestController
@RequestMapping("/api/v1/numbers")
@Tag(name = "Number")
public class NumbersController {

	private final NumberService service;

	private final NumberModelAssembler assembler;

	@Autowired
	public NumbersController(NumberService service, NumberModelAssembler assembler) {
		this.service = service;
		this.assembler = assembler;
	}


	/**
	 * Handles the HTTP GET requests matched with /numbers URI expression.
	 * Requires an authorized user with read permission.
	 * @return list of all numbers, wrapped in a collection model 
	 */
	@Operation(summary = "Get all the numbers", responses = {
			@ApiResponse(description = "Get all the numbers request was completed successfully", responseCode = "200",
					content = @Content(mediaType = "application/hal+json", schema = @Schema(implementation = CollectionModel.class))),
			})
	@GetMapping
	@PreAuthorize("hasAuthority('developers:read')")
	public CollectionModel<EntityModel<NaturalNumber>> getAllNumbers() {
		List<NaturalNumber> allNumbers = service.getAllNumbers();
		List<EntityModel<NaturalNumber>> listOfHateOasNumbers = allNumbers.stream()
				.map(assembler::toModel)
				.collect(Collectors.toList());
		return CollectionModel.of(listOfHateOasNumbers,
				linkTo(methodOn(NumbersController.class).getAllNumbers()).withSelfRel());
	}

	/**
	 * Handles the HTTP GET requests matched with /numbers/{id} URI expression.
	 * Requires an authorized user with read permission.
	 * @param id - number identifier 
	 * @return  number with passed id, wrapped in an entity model
	 * @throws  NumberNotFoundException if number with passed id doesn't exist
	 */
	@Operation(summary = "Get number by id", responses = {
			@ApiResponse(description = "Number was found successfully", responseCode = "200", 
					content = @Content(mediaType = "application/hal+json", schema = @Schema(implementation = EntityModel.class))),
			@ApiResponse(description = "Number not found.", responseCode = "404",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))),
			@ApiResponse(description = "Internal Server Error.", responseCode = "500",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))),
			})
	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('developers:read')")
	public EntityModel<NaturalNumber> getNumberById(@PathVariable long id) {
		Optional<NaturalNumber> number = service.getNumberById(id);
		if(number.isPresent()) {
			return assembler.toModel(number.get());
		} else
			throw new NumberNotFoundException(id);	
	}

	/**
	 * Handles the HTTP POST requests matched with /numbers URI expression.
	 * Requires an authorized user with write permission.
	 * @param number - MyNumber entity 
	 * @return  response entity with status code 201 and created number, wrapped in an entity model
	 */
	@Operation(summary = "Save passed number", responses = {
			@ApiResponse(description = "Number was created successfully", responseCode = "201", 
					content = @Content(mediaType = "application/hal+json", schema = @Schema(implementation = EntityModel.class))),
			@ApiResponse(description = "Internal Server Error.", responseCode = "500",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))),
			})
	@PostMapping
	@PreAuthorize("hasAuthority('developers:write')")
	public ResponseEntity<EntityModel<NaturalNumber>> addNewNumber(@RequestBody NaturalNumber number) {
		NaturalNumber updatedNumber = service.saveNewNumber(number);
		EntityModel<NaturalNumber> entityModel = assembler.toModel(updatedNumber);
		
		return ResponseEntity
				.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
	}

	/**
	 * Handles the HTTP PUT requests matched with /numbers/{id} URI expression.
	 * Requires an authorized user with write permission.
	 * @param newNumber - MyNumber entity
	 * @param id - number identifier 
	 * @return  response entity with status code 201 and created(or replaced if exists) number, wrapped in an entity model
	 */
	@Operation(summary = "Either replace if exists or save passed number", responses = {
			@ApiResponse(description = "Number was created successfully", responseCode = "201", 
					content = @Content(mediaType = "application/hal+json", schema = @Schema(implementation = EntityModel.class))),
			@ApiResponse(description = "Internal Server Error.", responseCode = "500",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))),
			})
	@PutMapping("/{id}")
	@PreAuthorize("hasAuthority('developers:write')")
	public ResponseEntity<EntityModel<NaturalNumber>> replaceMyNumber(@RequestBody NaturalNumber newNumber, @PathVariable long id) {
		NaturalNumber updatedNumber = service.replaceMyNymber(newNumber, id);
		EntityModel<NaturalNumber> entityModel = assembler.toModel(updatedNumber);

		return ResponseEntity
				.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
	}

	/**
	 * Handles the HTTP DELETE requests matched with /numbers/{id} URI expression.
	 * Requires an authorized user with write permission.
	 * @param id - number identifier 
	 * @return  response entity with status code 204
	 */
	@Operation(summary = "Delete the number", responses = {
			@ApiResponse(description = "Number was deleted successfully", responseCode = "204"),
			@ApiResponse(description = "Internal Server Error.", responseCode = "500",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))),
			})
	@DeleteMapping("/{id}")
	@PreAuthorize("hasAuthority('developers:write')")
	public ResponseEntity<Object> deleteMyNumber(@PathVariable long id) {
		service.deleteMyNumber(id);
		return ResponseEntity.noContent().build();
	}
}
