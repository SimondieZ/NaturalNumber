package com.trynumbers.attempt;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.trynumbers.attempt.entity.NaturalNumber;
import com.trynumbers.attempt.repository.NumberRepository;
import com.trynumbers.attempt.service.NumberService;
import com.trynumbers.attempt.service.NumberServiceImpl;
import com.trynumbers.utility.MyNumbersUtility;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class NumberServiceLayerTest {

	@MockBean
	private NumberRepository numberRepository;

	private NumberService underTestNumberService;

	@BeforeEach
	public void setUp() {
		underTestNumberService = new NumberServiceImpl(numberRepository);
	}

	@Test
	public void canCallGetAllNumbersFromRepositoryForGetHTTP() {
		// when
		underTestNumberService.getAllNumbers();
		// then
		verify(numberRepository).findAll();
	}

	@Test
	public void canCallGetNumberByIdFromRepositoryForGetByIdHTTP() {

		underTestNumberService.getNumberById(5);
		
		verify(numberRepository).findById(anyLong());

	}

	@Test
	public void isTheEqualNumberPassedToTheRepositorySaveMethodForPostHTTP() {
		// given
		NaturalNumber numberThree = MyNumbersUtility.createMyNumberInstance();
		ArgumentCaptor<NaturalNumber> numberArgumentCaptor = ArgumentCaptor.forClass(NaturalNumber.class);
		// when
		underTestNumberService.saveNewNumber(numberThree);
		// then
		verify(numberRepository).save(numberArgumentCaptor.capture());
		NaturalNumber captuderNumber = numberArgumentCaptor.getValue();
		assertThat(captuderNumber).isEqualTo(numberThree);

	}

	@Test
	public void whenPassedNumberExistsInRepositoryItIsReplacedForPutHTTP() {
		
		long searchId = 3;
		
		NaturalNumber foundNumberById = MyNumbersUtility.createMyNumberInstance();
		
		NaturalNumber numberToUpdate = MyNumbersUtility.createMyNumberInstance();				// an object with an empty id is passed to the request body, so it is set id to 0
		numberToUpdate.setId(0);
		numberToUpdate.setDescription("Some changes to the found number description");

		when(numberRepository.findById(anyLong())).thenReturn(Optional.of(foundNumberById));
		ArgumentCaptor<NaturalNumber> numberArgumentCaptor = ArgumentCaptor.forClass(NaturalNumber.class);
		// when
		underTestNumberService.replaceMyNymber(numberToUpdate, searchId);
		// then
		verify(numberRepository, times(1)).save(numberArgumentCaptor.capture());
		NaturalNumber captuderNumber = numberArgumentCaptor.getValue();

		assertThat(captuderNumber).isNotEqualTo(numberToUpdate);
	}

	@Test
	public void whenPassedNumberDoesNotExsistInRepositoryItIsSavedForPutHTTP() {

		NaturalNumber numberToUpdate = MyNumbersUtility.createMyNumberInstance();				
		numberToUpdate.setId(0);

		when(numberRepository.findById(anyLong())).thenReturn(Optional.empty());
		ArgumentCaptor<NaturalNumber> numberArgumentCaptor = ArgumentCaptor.forClass(NaturalNumber.class);
		// when
		underTestNumberService.replaceMyNymber(numberToUpdate, 5);
		// then
		verify(numberRepository, times(1)).save(numberArgumentCaptor.capture());
		NaturalNumber captuderNumber = numberArgumentCaptor.getValue();

		assertThat(captuderNumber).isEqualTo(numberToUpdate);
	}

	@Test
	public void canCallDeleteTheNumberFromRepositoryWithAnyLongPassedParameterForDeleteHTTP() {

		underTestNumberService.deleteMyNumber(3);
		verify(numberRepository).deleteById(anyLong());

	}
}
