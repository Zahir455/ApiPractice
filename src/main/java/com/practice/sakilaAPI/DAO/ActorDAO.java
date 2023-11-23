package com.practice.sakilaAPI.DAO;

import com.practice.sakilaAPI.DTO.ActorDTO;
import com.practice.sakilaAPI.entity.Actor;
import com.practice.sakilaAPI.repo.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ActorDAO {

    @Autowired
    private ActorRepository actorRepo;

    public ActorDAO(ActorRepository actorRepo) {
        this.actorRepo = actorRepo;
    }

    public ActorDTO update(ActorDTO actorDTO){
        Optional<Actor> optional = actorRepo.findById(actorDTO.getId());
        Actor actor = null;
        if(optional.isPresent())
            actor = optional.get();
        else
            return new ActorDTO(-1, null, null);
        if(actorDTO.getFirstName()!= null)
            actor.setFirstName(actorDTO.getFirstName());
        if(actorDTO.getLastName()!= null)
            actor.setLastName(actorDTO.getLastName());
        actorRepo.save(actor);
        actor = actorRepo.findById(actorDTO.getId()).get();
        return new ActorDTO(actor.getId(), actor.getFirstName(), actor.getLastName());
    }
}
