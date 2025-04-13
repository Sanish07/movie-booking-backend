package com.sanish.movie_service.repositories;

import com.sanish.movie_service.entities.Screen;
import com.sanish.movie_service.entities.Theater;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ScreenRepository extends JpaRepository<Screen,Long>{
    List<Screen> findAllByTheater(Theater theater);
    Optional<Screen> findByTheaterAndScreenNumber(Theater theater, Integer screenNumber);
    void deleteByTheaterAndScreenNumber(Theater theater, Integer screenNumber);
}
