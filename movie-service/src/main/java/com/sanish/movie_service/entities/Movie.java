package com.sanish.movie_service.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "movies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "movie_number")
    private String movieNumber;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String genre;

    private Integer durationMinutes;

    private LocalDate releaseDate;

    private String language;

    private String posterUrl;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

     @JsonManagedReference
     @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
     private List<Showtime> showtimes;
}
