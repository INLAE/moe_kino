package com.example.demo.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;

@Data
public class ActorDTO {

    private Long id;
    @NotEmpty
    private String name;
    private String username;
    private String dateOfBirth;
}
