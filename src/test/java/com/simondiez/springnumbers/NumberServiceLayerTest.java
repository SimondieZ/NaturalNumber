package com.simondiez.springnumbers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.simondiez.springnumbers.entity.NaturalNumber;
import com.simondiez.springnumbers.repository.NumberRepository;
import com.simondiez.springnumbers.service.NumberService;
import com.simondiez.springnumbers.service.NumberServiceImpl;
import com.simondiez.utility.MyNumbersUtility;

@SpringBootTest(classes = { NumberRepository.class, NumberServiceImpl.class })
@ExtendWith(MockitoExtension.class)
public class NumberServiceLayerTest {

	@MockBean
	private NumberRepository numberRepository;

	@Autowired
	private NumberService underTestNumberService;

	@Test
	public void canCallGetAllNumbersFromRepositoryForGetHTTP() {

		underTestNumberService.getAllNumbers();
		verify(numberRepository).findAll();

	}

	@Test
	public void canCallGetNumberByIdFromRepositoryForGetByIdHTTP() {

		underTestNumberService.getNumberById(5);
		verify(numberRepository).findById(anyLong());

	}

	@Test
	public void isTheEqualNumberPassedToTheRepositorySaveMethodForPostHTTP() {

		NaturalNumber numberThree = MyNumbersUtility.createMyNumberInstance();
		ArgumentCaptor<NaturalNumber> numberArgumentCaptor = ArgumentCaptor.forClass(NaturalNumber.class);

		underTestNumberService.saveNewNumber(numberThree);

		verify(numberRepository).save(numberArgumentCaptor.capture());
		NaturalNumber captuderNumber = numberArgumentCaptor.getValue();

		assertEquals(captuderNumber, numberThree);

	}

	@Test
	public void whenPassedNumberExistsInRepositoryItIsReplacedForPutHTTP() {

		long searchId = 3;

		NaturalNumber foundNumberById = MyNumbersUtility.createMyNumberInstance();

		NaturalNumber numberToUpdate = MyNumbersUtility.createMyNumberInstance(); // an object with an empty id is passed to the request body, so it is set id to 0
		numberToUpdate.setId(0);
		numberToUpdate.setDescription("Some changes to the found number description");

		when(numberRepository.findById(anyLong())).thenReturn(Optional.of(foundNumberById));
		ArgumentCaptor<NaturalNumber> numberArgumentCaptor = ArgumentCaptor.forClass(NaturalNumber.class);

		underTestNumberService.replaceMyNymber(numberToUpdate, searchId);

		verify(numberRepository, times(1)).save(numberArgumentCaptor.capture());
		NaturalNumber captuderNumber = numberArgumentCaptor.getValue();

		assertNotEquals(captuderNumber, numberToUpdate);

	}

	@Test
	public void whenPassedNumberDoesNotExsistInRepositoryItIsSavedForPutHTTP() {

		NaturalNumber numberToUpdate = MyNumbersUtility.createMyNumberInstance();
		numberToUpdate.setId(0);

		when(numberRepository.findById(anyLong())).thenReturn(Optional.empty());
		ArgumentCaptor<NaturalNumber> numberArgumentCaptor = ArgumentCaptor.forClass(NaturalNumber.class);

		underTestNumberService.replaceMyNymber(numberToUpdate, 5);

		verify(numberRepository, times(1)).save(numberArgumentCaptor.capture());
		NaturalNumber captuderNumber = numberArgumentCaptor.getValue();

		assertEquals(captuderNumber, numberToUpdate);

	}

	@Test
	public void canCallDeleteTheNumberFromRepositoryWithAnyLongPassedParameterForDeleteHTTP() {

		underTestNumberService.deleteMyNumber(3);
		verify(numberRepository).deleteById(anyLong());

	}
}
