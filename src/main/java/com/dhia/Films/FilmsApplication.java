package com.dhia.Films;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.dhia.Films.model.Films;
import com.dhia.Films.service.FilmService;

@SpringBootApplication
public class FilmsApplication implements CommandLineRunner {

    @Autowired
    FilmService filmService;

    public static void main(String[] args) {
        SpringApplication.run(FilmsApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        filmService.saveFilms(new Films("Inception", 15.99, new Date()));
        filmService.saveFilms(new Films("The Dark Knight", 19.99, new Date()));
        filmService.saveFilms(new Films("Interstellar", 14.99, new Date()));
    }
}
