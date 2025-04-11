package com.sanish.movie_service.repositories;

import com.sanish.movie_service.entities.Screen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScreenRepository extends JpaRepository<Screen,Long>{
    List<Screen> findAllByScreenNumber(Integer screenNumber);
}
