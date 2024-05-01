package com.dhia.Films.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.dhia.Films.model.Films;
import com.dhia.Films.service.FilmService;

@Controller
public class FilmsController {

    @Autowired
    FilmService filmsService;

    @GetMapping("/ListeFilms")
    public String listeFilms(ModelMap modelMap,
                             @RequestParam(name = "page", defaultValue = "0") int page,
                             @RequestParam(name = "size", defaultValue = "2") int size) {
        Page<Films> filmsPage = filmsService.getAllFilmsPerPage(page, size);
        modelMap.addAttribute("films", filmsPage);
        modelMap.addAttribute("pages", filmsPage.getTotalPages());
        modelMap.addAttribute("currentPage", page);
        modelMap.addAttribute("size", size);
        return "listeFilms";
    }



    @GetMapping("/showCreate")
    public String showCreate() {
        return "createFilm";
    }

    @PostMapping("/saveFilm")
    public String saveFilm(@ModelAttribute("film") Films film,
            @RequestParam("date") String date,
            ModelMap modelMap) throws ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date datePublication = dateFormat.parse(date);
        film.setDatePublication(datePublication);

        Films savedFilm = filmsService.saveFilms(film);
        String msg = "Film enregistré avec Id " + savedFilm.getIdFilm();
        modelMap.addAttribute("msg", msg);
        return "createFilm";
    }

    @RequestMapping("/supprimerFilm")
    public String supprimerFilm(@RequestParam("id") Long id,
            ModelMap modelMap,@RequestParam (name="page",defaultValue = "0") int page, 
            @RequestParam (name="size", defaultValue = "2") int size) {
        
    	filmsService.deleteFilmsById(id);
    	
    	Page<Films> filmsPage = filmsService.getAllFilmsPerPage(page, size);
        
        modelMap.addAttribute("films", filmsPage);
        modelMap.addAttribute("pages", new int[filmsPage.getTotalPages()]);
        modelMap.addAttribute("currentPage", page);
        modelMap.addAttribute("size", size);
        return "redirect:/ListeFilms?page=" + page + "&size=" + size;
    }

    @RequestMapping("/modifierFilm")
    public String editerFilm(@RequestParam("id") Long id,
            ModelMap modelMap) {
        Films film = filmsService.getFilms(id);
        modelMap.addAttribute("film", film);
        return "editerFilm";
    }

    @RequestMapping(value = "/updateFilm", method = RequestMethod.PUT)
    public String updateFilm(@ModelAttribute("film") Films film,
            @RequestParam("date") String date,
            ModelMap modelMap) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date datePublication = dateFormat.parse(date);
        film.setDatePublication(datePublication);

        filmsService.updateFilms(film);
        List<Films> films = filmsService.getAllFilms();
        modelMap.addAttribute("films", films);
        return "listeFilms";
    }
}
