package com.trynumbers.attempt.representation;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.trynumbers.attempt.controller.NumbersController;
import com.trynumbers.attempt.entity.MyNumber;


/**
 * Class for components that convert MyNumber type into a RepresentationModel.
 * 
 * @author Serafim Sokhin
 */
@Component
public class NumberModelAssembler implements RepresentationModelAssembler<MyNumber, EntityModel<MyNumber>> {

	
	/**
	 * Wraps MyNumber into EntityModel type.
	 * @param number MyNumber entity
	 * @return entity model of MyNumber
	 */
	@Override
	public EntityModel<MyNumber> toModel(MyNumber number) {
		return EntityModel.of(number,
				linkTo(methodOn(NumbersController.class).getNumberById(number.getId())).withSelfRel(),
				linkTo(methodOn(NumbersController.class).getAllNumbers()).withRel("numbers"));
	}

}
