package com.example.demo.facade;

import com.example.demo.dto.ActorDTO;
import com.example.demo.entity.Actor;
import org.springframework.stereotype.Component;

@Component
public class ActorFacade {

    public ActorDTO actorToActorDTO(Actor actor) {
        ActorDTO actorDTO = new ActorDTO();
        actorDTO.setId(actor.getId());
        actorDTO.setUsername(actor.getUsername());
        actorDTO.setName(actor.getName());
        actorDTO.setDateOfBirth(actor.getDateOfBirth());
        return actorDTO;
    }

}
