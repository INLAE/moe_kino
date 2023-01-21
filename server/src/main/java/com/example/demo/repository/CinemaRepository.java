package com.example.demo.repository;

import com.example.demo.entity.Cinema;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CinemaRepository extends JpaRepository<Cinema, Long> {

    List<Cinema> findAllByUserOrderByCreatedDateDesc(User user);

    List<Cinema> findAllByOrderByCreatedDateDesc();

    Optional<Cinema> findPostByIdAndUser(Long id, User user);

}
