package com.practice.sakilaAPI.repo;

import com.practice.sakilaAPI.entity.Actor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActorRepository extends JpaRepository<Actor, Integer> {

    List<Actor> findByLastName(String lastName);
}