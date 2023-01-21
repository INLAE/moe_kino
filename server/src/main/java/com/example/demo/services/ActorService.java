package com.example.demo.services;

import com.example.demo.dto.ActorDTO;
import com.example.demo.entity.Actor;
import com.example.demo.entity.Cinema;
import com.example.demo.entity.User;
import com.example.demo.exceptions.CinemaNotFoundException;
import com.example.demo.repository.ActorRepository;
import com.example.demo.repository.CinemaRepository;
import com.example.demo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class ActorService {
    public static final Logger LOG = LoggerFactory.getLogger(ActorService.class);

    private final ActorRepository actorRepository;
    private final CinemaRepository cinemaRepository;
    private final UserRepository userRepository;

    @Autowired
    public ActorService(ActorRepository actorRepository, CinemaRepository cinemaRepository, UserRepository userRepository) {
        this.actorRepository = actorRepository;
        this.cinemaRepository = cinemaRepository;
        this.userRepository = userRepository;
    }

    public Actor saveActor(Long postId, ActorDTO actorDTO, Principal principal) {
        User user = getUserByPrincipal(principal);
        Cinema cinema = cinemaRepository.findById(postId)
                .orElseThrow(() -> new CinemaNotFoundException("Post cannot be found for username: " + user.getEmail()));

        Actor actor = new Actor();
        actor.setCinema(cinema);
        actor.setUserId(user.getId());
        actor.setUsername(user.getUsername());
        actor.setName(actorDTO.getName());
        actor.setDateOfBirth(actorDTO.getDateOfBirth());

        LOG.info("Saving actor for cinema: {}", cinema.getId());
        return actorRepository.save(actor);
    }

    public List<Actor> getAllActorForPost(Long postId) {
        Cinema cinema = cinemaRepository.findById(postId)
                .orElseThrow(() -> new CinemaNotFoundException("Post cannot be found"));
        List<Actor> actors = actorRepository.findAllByCinema(cinema);
        return actors;
    }

    public void deleteActor(Long actorId) {
        Optional<Actor> actor = actorRepository.findById(actorId);
        actor.ifPresent(actorRepository::delete);
    }


    private User getUserByPrincipal(Principal principal) {
        String username = principal.getName();
        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found with username " + username));
    }
}
