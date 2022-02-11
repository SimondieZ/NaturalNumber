package com.trynumbers.attempt;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trynumbers.attempt.controller.NumbersController;
import com.trynumbers.attempt.entity.MyNumber;
import com.trynumbers.attempt.representation.NumberModelAssembler;
import com.trynumbers.attempt.service.NumberService;
import com.trynumbers.utility.MyNumbersUtility;

import net.minidev.json.JSONArray;

@WebMvcTest(NumbersController.class)
public class NumbersResourceTest {
	
	@MockBean
	private NumberService numberService;
	@SpyBean
	private NumberModelAssembler assembler;

	@Autowired
	private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;
	
	@Test
	void shouldGetListOfNumbers() throws Exception {
		
		List<MyNumber> numbers = MyNumbersUtility.createListWithThreeNumbers();
		
		when(numberService.getAllNumbers()).thenReturn(numbers);
		
		mvc.perform(MockMvcRequestBuilders.get("/numbers"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaTypes.HAL_JSON))
				.andExpect(jsonPath("$._embedded.myNumberList", Matchers.hasSize(3)));	
	}
	
	@Test
	void shouldGetNumberById() throws Exception {
		
		long numberId=3;
		
		MyNumber numberThree = MyNumbersUtility.createMyNumberInstance();
		JSONArray array = MyNumbersUtility.convertToJsonArray(numberThree.getDivisors());

		when(numberService.getNumberById(numberId)).thenReturn(Optional.of(numberThree));
		
		mvc.perform(MockMvcRequestBuilders.get("/numbers/{id}", numberId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$.id").value((int)numberId))
                .andExpect(jsonPath("$.name").value((int)numberThree.getName()))
                .andExpect(jsonPath("$.romaNotation").value(numberThree.getRomaNotation()))
                .andExpect(jsonPath("$.binaryNotation").value(numberThree.getBinaryNotation()))
                .andExpect(jsonPath("$.description").value(numberThree.getDescription()))
                .andExpect(jsonPath("$.divisors").value(array));
			
	}
	
	@Test
	void shouldReturn404StatusWhenGetNumberByIdIsNotPresent() throws Exception {
		
		long someId = 3;
		
		String errorMessage = "Could not find the number " + someId;
		
		when(numberService.getNumberById(someId)).thenReturn(Optional.empty());
		
		mvc.perform(MockMvcRequestBuilders.get("/numbers/{id}", someId))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message", is(errorMessage)));
	}
	
    @Test
    void givenNumber_whenAdd_thenStatus201andNumberReturned() throws Exception {
    	
    	MyNumber numberThree = MyNumbersUtility.createMyNumberInstance();
		
        when(numberService.saveNewNumber(Mockito.any(MyNumber.class))).thenReturn(numberThree);
        mvc.perform(MockMvcRequestBuilders.post("/numbers")
                        .content(objectMapper.writeValueAsString(numberThree))
                        .contentType(MediaType.APPLICATION_JSON)
                   )
	                .andExpect(status().isCreated())
	                .andExpect(content().contentType(MediaTypes.HAL_JSON))
	                .andExpect(content().json(objectMapper.writeValueAsString(numberThree)));
    }

    @Test
    void giveNumber_whenUpdate_thenStatus201andUpdatedReturns() throws Exception {
    		
    	MyNumber rewritableNumber = new MyNumber.MyNumberBuilder()							// without id (to create or replace)
				.name(3)
				.romaNotation("III")
				.binaryNotation("3")
				.description("The first unique prime due to the properties of its reciprocal.")
				.divisors(new int[] { 1, 3 })
				.build();
		
    	MyNumber getFromDataBaseNumber = MyNumbersUtility.createMyNumberInstance();
		
        when(numberService.replaceMyNymber(rewritableNumber, 3)).thenReturn(getFromDataBaseNumber);
        
        mvc.perform(MockMvcRequestBuilders.put("/numbers/{id}", 3)
                        .content(objectMapper.writeValueAsString(rewritableNumber))
                        .contentType(MediaType.APPLICATION_JSON)
                   )
	                .andExpect(status().isCreated())
	                .andExpect(jsonPath("$.id").value(getFromDataBaseNumber.getId()))
	                .andExpect(jsonPath("$.name").value(rewritableNumber.getName()))
	                .andExpect(jsonPath("$.romaNotation").value(rewritableNumber.getRomaNotation()))
	                .andExpect(jsonPath("$.binaryNotation").value(rewritableNumber.getBinaryNotation()))
	                .andExpect(jsonPath("$.description").value(rewritableNumber.getDescription()))
	                .andExpect(jsonPath("$.divisors").value(MyNumbersUtility.convertToJsonArray(rewritableNumber.getDivisors())));
    }
    
    @Test
	void givenNumber_whenDelete_thenStatus204() throws Exception {

		long numberIdToDelete = 3;
		mvc.perform(MockMvcRequestBuilders.delete("/numbers/{id}", numberIdToDelete)).andExpect(status().isNoContent());
		verify(numberService).deleteMyNumber(numberIdToDelete);

	}
    

    
}
	
