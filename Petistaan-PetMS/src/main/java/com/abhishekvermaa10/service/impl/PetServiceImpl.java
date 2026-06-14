package com.abhishekvermaa10.service.impl;

import java.util.List;
import java.util.Optional;

import com.abhishekvermaa10.dto.PetDTO;
import com.abhishekvermaa10.entity.Pet;
import com.abhishekvermaa10.exception.PetNotFoundException;
import com.abhishekvermaa10.util.PetMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.abhishekvermaa10.dto.PetCategoryStatisticsDTO;
import com.abhishekvermaa10.dto.PetGenderStatisticsDTO;
import com.abhishekvermaa10.dto.PetStatisticsDTO;
import com.abhishekvermaa10.enums.Gender;
import com.abhishekvermaa10.enums.PetType;
import com.abhishekvermaa10.repository.PetRepository;
import com.abhishekvermaa10.service.PetService;

import lombok.RequiredArgsConstructor;

/**
 * @author abhishekvermaa10
 */
@RequiredArgsConstructor
@Service
public class PetServiceImpl implements PetService {

	private final PetRepository petRepository;
	private final PetMapper petMapper;
	@Value("${pet.not.found}")
	private String petNotFound;

	@Override
	public PetStatisticsDTO getStatistics() {
		PetStatisticsDTO petStatisticsDTO = new PetStatisticsDTO();
		List<Object[]> rows = petRepository.fetchStatistics();
		for (Object[] row : rows) {
			String category = (String) row[0];
			Gender gender = (Gender) row[1];
			PetType type = (PetType) row[2];
			long count = (Long) row[3];
			petStatisticsDTO.incrementTotal(count);
			PetCategoryStatisticsDTO petCategoryStatisticsDTO = petStatisticsDTO.getOrCreateCategory(category);
			petCategoryStatisticsDTO.incrementTotal(count);
			PetGenderStatisticsDTO petGenderStatisticsDTO = petCategoryStatisticsDTO.getOrCreateGender(gender);
			petGenderStatisticsDTO.incrementTotal(count);
			petGenderStatisticsDTO.mergeOrCreateType(type, count);
		}
		return petStatisticsDTO;
	}

	@Override
	public Integer savePet(PetDTO petDTO) {
		Pet pet = petMapper.petDTOToPet(petDTO);
		petRepository.save(pet);
		return pet.getId();
	}

	@Override
	public PetDTO findPet(Integer petId) throws PetNotFoundException {
		PetDTO petDTO = petRepository.findById(petId)
				.map(petMapper :: petToPetDTO)
				.orElseThrow(() -> new PetNotFoundException(String.format(petNotFound, petId)));
		return petDTO;
	}

	@Override
	public void updatePet(Integer petId, String petName) throws PetNotFoundException{
		Pet pet = petRepository.findById(petId).orElseThrow(()
				-> new PetNotFoundException(
						String.format(petNotFound, petId)
		));
		pet.setName(petName);
		petRepository.save(pet);
	}

	@Override
	public void deletePet(Integer petId) throws PetNotFoundException{
		boolean petExists = petRepository.existsById(petId);
		if(!petExists) {
			throw new PetNotFoundException(String.format(petNotFound, petId));
		}
		petRepository.deleteById(petId);
	}

}
