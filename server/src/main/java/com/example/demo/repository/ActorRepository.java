package com.example.demo.repository;

import com.example.demo.entity.Actor;
import com.example.demo.entity.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Long> {
    List<Actor> findAllByCinema(Cinema cinema);
}
