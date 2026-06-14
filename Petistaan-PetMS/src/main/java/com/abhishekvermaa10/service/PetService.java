package com.abhishekvermaa10.service;

import com.abhishekvermaa10.dto.PetDTO;
import com.abhishekvermaa10.dto.PetStatisticsDTO;
import com.abhishekvermaa10.exception.PetNotFoundException;

/**
 * @author abhishekvermaa10
 */
public interface PetService {

	PetStatisticsDTO getStatistics();
	Integer savePet(PetDTO petDTO);
	PetDTO findPet(Integer petId) throws PetNotFoundException;
	void updatePet(Integer petId, String petName) throws PetNotFoundException;
	void deletePet(Integer petId) throws PetNotFoundException;

}
