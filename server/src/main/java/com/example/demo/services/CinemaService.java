package com.example.demo.services;

import com.example.demo.dto.CinemaDTO;
import com.example.demo.entity.Cinema;
import com.example.demo.entity.User;
import com.example.demo.exceptions.CinemaNotFoundException;
import com.example.demo.repository.CinemaRepository;
import com.example.demo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class CinemaService {
    public static final Logger LOG = LoggerFactory.getLogger(CinemaService.class);

    private final CinemaRepository cinemaRepository;
    private final UserRepository userRepository;

    @Autowired
    public CinemaService(CinemaRepository cinemaRepository, UserRepository userRepository) {
        this.cinemaRepository = cinemaRepository;
        this.userRepository = userRepository;
    }

    public Cinema createPost(CinemaDTO cinemaDTO, Principal principal) {
        User user = getUserByPrincipal(principal);
        Cinema cinema = new Cinema();
        cinema.setUser(user);
        cinema.setCaption(cinemaDTO.getCaption());
        cinema.setGenre(cinemaDTO.getGenre());
        cinema.setTitle(cinemaDTO.getTitle());
        cinema.setCost(cinemaDTO.getCost());

        LOG.info("Saving Post for User: {}", user.getEmail());
        return cinemaRepository.save(cinema);
    }

    public List<Cinema> getAllPosts() {
        return cinemaRepository.findAllByOrderByCreatedDateDesc();
    }

    public Cinema getPostById(Long postId, Principal principal) {
        User user = getUserByPrincipal(principal);
        return cinemaRepository.findPostByIdAndUser(postId, user)
                .orElseThrow(() -> new CinemaNotFoundException("Post cannot be found for username: " + user.getEmail()));
    }

    public List<Cinema> getAllPostForUser(Principal principal) {
        User user = getUserByPrincipal(principal);
        return cinemaRepository.findAllByUserOrderByCreatedDateDesc(user);
    }

    public void deletePost(Long postId, Principal principal) {
        Cinema cinema = getPostById(postId, principal);
        cinemaRepository.delete(cinema);
    }

    private User getUserByPrincipal(Principal principal) {
        String username = principal.getName();
        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found with username " + username));

    }
}
