package com.example.demo.facade;

import com.example.demo.dto.CinemaDTO;
import com.example.demo.entity.Cinema;
import org.springframework.stereotype.Component;

@Component
public class CinemaFacade {

    public CinemaDTO postToPostDTO(Cinema cinema) {
        CinemaDTO cinemaDTO = new CinemaDTO();
        cinemaDTO.setUsername(cinema.getUser().getUsername());
        cinemaDTO.setId(cinema.getId());
        cinemaDTO.setCaption(cinema.getCaption());
        cinemaDTO.setCost(cinema.getCost());
        cinemaDTO.setGenre(cinema.getGenre());
        cinemaDTO.setTitle(cinema.getTitle());

        return cinemaDTO;
    }

}
