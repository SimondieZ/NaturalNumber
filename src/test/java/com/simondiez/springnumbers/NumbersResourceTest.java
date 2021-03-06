package com.simondiez.springnumbers;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simondiez.springnumbers.controller.NumbersController;
import com.simondiez.springnumbers.entity.NaturalNumber;
import com.simondiez.springnumbers.exceptions.NumberNotFoundException;
import com.simondiez.springnumbers.representation.NumberModelAssembler;
import com.simondiez.springnumbers.service.NumberService;
import com.simondiez.utility.MyNumbersUtility;

import net.minidev.json.JSONArray;

@WebMvcTest(NumbersController.class)
@ExtendWith(MockitoExtension.class)
public class NumbersResourceTest {
	
	@MockBean
	private NumberService numberService;
	@SpyBean
	private NumberModelAssembler assembler;
	@MockBean
	private UserDetailsService userDetailsService;

	@Autowired
	private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    
	
	@Test
	@WithMockUser(authorities = "developers:read")
	void shouldGetListOfNumbers() throws Exception {
		
		List<NaturalNumber> numbers = MyNumbersUtility.createListWithThreeNumbers();
		
		when(numberService.getAllNumbers()).thenReturn(numbers);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/numbers"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaTypes.HAL_JSON))
				.andExpect(jsonPath("$._embedded.naturalNumberList", Matchers.hasSize(numbers.size())));	
		
	}
	
	@Test
	@WithMockUser(authorities = "developers:read")
	void shouldGetNumberById() throws Exception {
		
		long numberId=3;
		
		NaturalNumber numberThree = MyNumbersUtility.createMyNumberInstance();
		JSONArray array = MyNumbersUtility.convertToJsonArray(numberThree.getDivisors());

		when(numberService.getNumberById(numberId)).thenReturn(Optional.of(numberThree));
		
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/numbers/{id}", numberId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$.id").value((int)numberId))
                .andExpect(jsonPath("$.value").value((int)numberThree.getValue()))
                .andExpect(jsonPath("$.romaNotation").value(numberThree.getRomaNotation()))
                .andExpect(jsonPath("$.binaryNotation").value(numberThree.getBinaryNotation()))
                .andExpect(jsonPath("$.description").value(numberThree.getDescription()))
                .andExpect(jsonPath("$.divisors").value(array));
		
	}
	
	@Test
	@WithMockUser(authorities = "developers:read")
	void shouldReturn404StatusWhenGetNumberByIdIsNotPresent() throws Exception {
		
		long someId = 3;
		
		NumberNotFoundException exeption = new NumberNotFoundException(someId);
		String errorMessage = exeption.getMessage();
		
		when(numberService.getNumberById(someId)).thenReturn(Optional.empty());
		
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/numbers/{id}", someId))
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.message").value(errorMessage));
		
	}
	
    @Test
    @WithMockUser(authorities = "developers:write")
    void givenNumber_whenAdd_thenStatus201andNumberReturned() throws Exception {
    	
    	NaturalNumber numberThree = MyNumbersUtility.createMyNumberInstance();
		
        when(numberService.saveNewNumber(Mockito.any(NaturalNumber.class))).thenReturn(numberThree);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/numbers")
                        .content(objectMapper.writeValueAsString(numberThree))
                        .contentType(MediaType.APPLICATION_JSON)
                   )
	                .andExpect(status().isCreated())
	                .andExpect(content().contentType(MediaTypes.HAL_JSON))
	                .andExpect(content().json(objectMapper.writeValueAsString(numberThree)));
        
    }

    @Test
    @WithMockUser(authorities = "developers:write")
    void giveNumber_whenUpdate_thenStatus201andUpdatedReturns() throws Exception {
    		
    	NaturalNumber rewritableNumber = new NaturalNumber.NumberBuilder()							// without id (to create or replace)
				.value(3)
				.romaNotation("III")
				.binaryNotation("3")
				.description("The first unique prime due to the properties of its reciprocal.")
				.divisors(new int[] { 1, 3 })
				.build();
		
    	NaturalNumber getFromDataBaseNumber = MyNumbersUtility.createMyNumberInstance();
		
        when(numberService.replaceMyNymber(rewritableNumber, 3)).thenReturn(getFromDataBaseNumber);
        
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/numbers/{id}", 3)
                        .content(objectMapper.writeValueAsString(rewritableNumber))
                        .contentType(MediaType.APPLICATION_JSON)
                   )
	                .andExpect(status().isCreated())
	                .andExpect(jsonPath("$.id").value(getFromDataBaseNumber.getId()))
	                .andExpect(jsonPath("$.value").value(rewritableNumber.getValue()))
	                .andExpect(jsonPath("$.romaNotation").value(rewritableNumber.getRomaNotation()))
	                .andExpect(jsonPath("$.binaryNotation").value(rewritableNumber.getBinaryNotation()))
	                .andExpect(jsonPath("$.description").value(rewritableNumber.getDescription()))
	                .andExpect(jsonPath("$.divisors").value(MyNumbersUtility.convertToJsonArray(rewritableNumber.getDivisors())));
        
    }
    
	@Test
	@WithMockUser(authorities = "developers:write")
	void givenNumber_whenDelete_thenStatus204() throws Exception {

		long numberIdToDelete = 3;
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/numbers/{id}", numberIdToDelete))
				.andExpect(status().isNoContent());
		verify(numberService).deleteMyNumber(numberIdToDelete);
	}
	
	@Test
	@WithMockUser(authorities = "developers:write")
	void givenNumber_whenDelete_thenStatus404() throws Exception {
		
		long numberIdToDelete = 3;	
		String errorMessage = String.format("No %s entity with id %s exists!", NaturalNumber.class.getCanonicalName(), numberIdToDelete);
		
		Mockito.doThrow(new EmptyResultDataAccessException(errorMessage,1)).when(numberService).deleteMyNumber(numberIdToDelete);
		
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/numbers/{id}", numberIdToDelete))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.message").value(errorMessage))
				.andExpect(status().isNotFound());
		
		verify(numberService).deleteMyNumber(numberIdToDelete);
		
	}
}
	