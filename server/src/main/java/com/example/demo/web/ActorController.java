package com.example.demo.web;

import com.example.demo.dto.ActorDTO;
import com.example.demo.entity.Actor;
import com.example.demo.facade.ActorFacade;
import com.example.demo.payload.response.MessageResponse;
import com.example.demo.services.ActorService;
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
@RequestMapping("api/actor")
@CrossOrigin
public class ActorController {

    @Autowired
    private ActorService actorService;
    @Autowired
    private ActorFacade actorFacade;
    @Autowired
    private ResponseErrorValidation responseErrorValidation;

    @PostMapping("/{postId}/create")
    public ResponseEntity<Object> createActor(@Valid @RequestBody ActorDTO actorDTO,
                                              @PathVariable("postId") String postId,
                                              BindingResult bindingResult,
                                              Principal principal) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        Actor actor = actorService.saveActor(Long.parseLong(postId), actorDTO, principal);
        ActorDTO createdActor = actorFacade.actorToActorDTO(actor);

        return new ResponseEntity<>(createdActor, HttpStatus.OK);
    }

    @GetMapping("/{postId}/all")
    public ResponseEntity<List<ActorDTO>> getAllActorsToPost(@PathVariable("postId") String postId) {
        List<ActorDTO> actorDTOList = actorService.getAllActorForPost(Long.parseLong(postId))
                .stream()
                .map(actorFacade::actorToActorDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<>(actorDTOList, HttpStatus.OK);
    }

    @PostMapping("/{actorId}/delete")
    public ResponseEntity<MessageResponse> deleteActor(@PathVariable("actorId") String actorId) {
        actorService.deleteActor(Long.parseLong(actorId));
        return new ResponseEntity<>(new MessageResponse("Post was deleted"), HttpStatus.OK);
    }
}
