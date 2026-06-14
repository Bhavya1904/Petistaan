package com.abhishekvermaa10.util;

import com.abhishekvermaa10.dto.DomesticPetDTO;
import com.abhishekvermaa10.dto.PetDTO;
import com.abhishekvermaa10.dto.WildPetDTO;
import com.abhishekvermaa10.entity.DomesticPet;
import com.abhishekvermaa10.entity.Pet;
import com.abhishekvermaa10.entity.WildPet;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PetMapper {

    String UNSUPPORTED_PET_INSTANCE = "Unsupported pet instance: %s";


    default PetDTO petToPetDTO(Pet pet) {
        return switch (pet){
            case DomesticPet domesticPet -> domesticPetToDomesticPetDTO(domesticPet);
            case WildPet wildPet -> wildPetToWildPetDTO(wildPet);
            default -> throw new IllegalArgumentException(String.format(UNSUPPORTED_PET_INSTANCE, pet));
        };
    }

    default Pet petDTOToPet(PetDTO petDTO) {
        return switch (petDTO){
            case DomesticPetDTO domesticPetDTO -> domesticPetDTOToDomesticPet(domesticPetDTO);
            case WildPetDTO wildPetDTO -> wildPetDTOToWildPet(wildPetDTO);
            default -> throw new IllegalArgumentException(String.format(UNSUPPORTED_PET_INSTANCE, petDTO));
        };
    }

    DomesticPetDTO domesticPetToDomesticPetDTO(DomesticPet domesticPet);
    DomesticPet domesticPetDTOToDomesticPet(DomesticPetDTO domesticPetDTO);
    WildPetDTO wildPetToWildPetDTO(WildPet wildPet);
    WildPet wildPetDTOToWildPet(WildPetDTO wildPetDTO);
}
