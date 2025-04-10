package com.sanish.movie_service.repositories;


import com.sanish.movie_service.entities.Theater;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TheaterRepository extends JpaRepository<Theater, Long>{
    Optional<Theater> findByTheaterNumber(String theaterNumber);
}
