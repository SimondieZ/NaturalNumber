package com.trynumbers.attempt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trynumbers.attempt.entity.MyNumber;
import com.trynumbers.attempt.exceptions.NumberNotFoundException;
import com.trynumbers.attempt.repository.NumberRepository;


@Service
public class NumberServiceImpl implements NumberService{

	private final NumberRepository numRepos;
	

	@Autowired
	public NumberServiceImpl(NumberRepository numRepos) {
		this.numRepos = numRepos;
	}

	@Override
	public List<MyNumber> getAllNumbers() {
		return numRepos.findAll();
	}
	
/*	@Override
	public CollectionModel<EntityModel<MyNumber>> getAllNumbers() {
		List<MyNumber> allNumbers = numRepos.findAll();
		List<EntityModel<MyNumber>> listOfHateOasNumbers = allNumbers.stream()
				.map(assembler::toModel)
				.collect(Collectors.toList());
		return CollectionModel.of(listOfHateOasNumbers,
				linkTo(methodOn(NumbersController.class).getAllNumbers()).withSelfRel());
	}*/

	
	@Override
	public MyNumber getNumberById(long id) throws NumberNotFoundException {
		return numRepos.findById(id).orElseThrow(() -> new NumberNotFoundException(id));
	}
	
/*	@Override
	public EntityModel<MyNumber> getNumberById(long id) throws NumberNotFoundException {
		MyNumber number = numRepos.findById(id).orElseThrow(() -> new NumberNotFoundException(id));
		return assembler.toModel(number);
	}*/

	@Override
	public MyNumber saveNewNumber(MyNumber number) {
		return numRepos.save(number);
	}
	
	
/*	@Override
	public ResponseEntity<EntityModel<MyNumber>> saveNewNumber(MyNumber number) {
		EntityModel<MyNumber> entityModel = assembler.toModel(numRepos.save(number));
		return ResponseEntity
					.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
					.body(entityModel);
	}
*/
	
	
	@Override
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
	
/*	@Override
	public ResponseEntity<EntityModel<MyNumber>> replaceMyNymber(MyNumber newNumber, long id) {
		MyNumber updatedNumber = numRepos.findById(id).map(number -> {
			number.setName(newNumber.getName());
			number.setBinaryNotation(newNumber.getBinaryNotation());
			number.setRomaNotation(newNumber.getRomaNotation());
			number.setDescription(newNumber.getDescription());
			return numRepos.save(number);
		}).orElseGet(() -> {
			// newNumber.setId(id);
			return numRepos.save(newNumber);
		});
		
		EntityModel<MyNumber> entityModel = assembler.toModel(updatedNumber);
		
		return ResponseEntity
					.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
					.body(entityModel);
	}*/

	
	@Override
	public void deleteMyNumber(long id) {
		numRepos.deleteById(id);
	}
	
/*	@Override
	public ResponseEntity<Object> deleteMyNumber(long id) {
		numRepos.deleteById(id);
		return ResponseEntity.noContent().build();
	}*/
}
