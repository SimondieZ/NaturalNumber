package com.trynumbers.attempt;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.trynumbers.attempt.entity.MyNumber;
import com.trynumbers.attempt.repository.NumberRepository;
import com.trynumbers.attempt.service.NumberService;
import com.trynumbers.utility.MyNumbersUtility;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class NumberServiceLayerTest {

	@MockBean
	private NumberRepository numberRepository;

	@Autowired
	private NumberService underTestNumberService;

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
		MyNumber numberThree = MyNumbersUtility.createMyNumberInstance();
		ArgumentCaptor<MyNumber> numberArgumentCaptor = ArgumentCaptor.forClass(MyNumber.class);
		// when
		underTestNumberService.saveNewNumber(numberThree);
		// then
		verify(numberRepository).save(numberArgumentCaptor.capture());
		MyNumber captuderNumber = numberArgumentCaptor.getValue();
		assertThat(captuderNumber).isEqualTo(numberThree);

	}

	@Test
	public void whenPassedNumberExistsInRepositoryItIsReplacedForPutHTTP() {
		
		long searchId = 3;
		
		MyNumber foundNumberById = MyNumbersUtility.createMyNumberInstance();
		
		MyNumber numberToUpdate = MyNumbersUtility.createMyNumberInstance();				// an object with an empty id is passed to the request body, so it is set id to 0
		numberToUpdate.setId(0);
		numberToUpdate.setDescription("Some changes to the found number description");

		when(numberRepository.findById(anyLong())).thenReturn(Optional.of(foundNumberById));
		ArgumentCaptor<MyNumber> numberArgumentCaptor = ArgumentCaptor.forClass(MyNumber.class);
		// when
		underTestNumberService.replaceMyNymber(numberToUpdate, searchId);
		// then
		verify(numberRepository, times(1)).save(numberArgumentCaptor.capture());
		MyNumber captuderNumber = numberArgumentCaptor.getValue();

		assertThat(captuderNumber).isNotEqualTo(numberToUpdate);
	}

	@Test
	public void whenPassedNumberDoesNotExsistInRepositoryItIsSavedForPutHTTP() {

		MyNumber numberToUpdate = MyNumbersUtility.createMyNumberInstance();				
		numberToUpdate.setId(0);

		when(numberRepository.findById(anyLong())).thenReturn(Optional.empty());
		ArgumentCaptor<MyNumber> numberArgumentCaptor = ArgumentCaptor.forClass(MyNumber.class);
		// when
		underTestNumberService.replaceMyNymber(numberToUpdate, 5);
		// then
		verify(numberRepository, times(1)).save(numberArgumentCaptor.capture());
		MyNumber captuderNumber = numberArgumentCaptor.getValue();

		assertThat(captuderNumber).isEqualTo(numberToUpdate);
	}

	@Test
	public void canCallDeleteTheNumberFromRepositoryWithAnyLongPassedParameterForDeleteHTTP() {

		underTestNumberService.deleteMyNumber(3);
		verify(numberRepository).deleteById(anyLong());

	}
}
