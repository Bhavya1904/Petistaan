package com.abhishekvermaa10.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

/**
 * @author abhishekvermaa10
 */
@ToString(callSuper = true)
@Setter
@Getter
public class DomesticPetDTO extends PetDTO {

	private LocalDate birthDate;

}
