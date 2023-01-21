package com.example.demo.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CinemaDTO {

    private Long id;
    @NotEmpty
    private String title;
    @NotEmpty
    private String caption;
    private String genre;
    private String username;
    private int cost;
}
