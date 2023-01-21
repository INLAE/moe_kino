package com.example.demo.web;

import com.example.demo.dto.CinemaDTO;
import com.example.demo.entity.Cinema;
import com.example.demo.facade.CinemaFacade;
import com.example.demo.payload.response.MessageResponse;
import com.example.demo.services.CinemaService;
import com.example.demo.validations.ResponseErrorValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/post")
@CrossOrigin
public class CinemaController {

    @Autowired
    private CinemaFacade postFacade;
    @Autowired
    private CinemaService cinemaService;
    @Autowired
    private ResponseErrorValidation responseErrorValidation;

    @PostMapping("/create")
    public ResponseEntity<Object> createPost(@Valid @RequestBody CinemaDTO cinemaDTO,
                                             BindingResult bindingResult,
                                             Principal principal) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        Cinema cinema = cinemaService.createPost(cinemaDTO, principal);
        CinemaDTO createdPost = postFacade.postToPostDTO(cinema);

        return new ResponseEntity<>(createdPost, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CinemaDTO>> getAllPosts() {
        List<CinemaDTO> cinemaDTOList = cinemaService.getAllPosts()
                .stream()
                .map(postFacade::postToPostDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<>(cinemaDTOList, HttpStatus.OK);
    }

    @GetMapping("/user/posts")
    public ResponseEntity<List<CinemaDTO>> getAllPostsForUser(Principal principal) {
        List<CinemaDTO> cinemaDTOList = cinemaService.getAllPostForUser(principal)
                .stream()
                .map(postFacade::postToPostDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<>(cinemaDTOList, HttpStatus.OK);
    }

    @PostMapping("/{postId}/delete")
    public ResponseEntity<MessageResponse> deletePost(@PathVariable("postId") String postId, Principal principal) {
        cinemaService.deletePost(Long.parseLong(postId), principal);
        return new ResponseEntity<>(new MessageResponse("Post was deleted"), HttpStatus.OK);
    }
}
