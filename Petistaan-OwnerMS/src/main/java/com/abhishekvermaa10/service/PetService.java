package com.abhishekvermaa10.service;

import com.abhishekvermaa10.dto.PetDTO;

public interface PetService {
    Integer savePet(PetDTO petDTO);
    PetDTO findPet(Integer petId);
    void updatePet(Integer petId, String petName);
    void deletePet(Integer petId);
}
