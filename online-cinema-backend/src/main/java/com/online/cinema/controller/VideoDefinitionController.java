package com.online.cinema.controller;

import com.online.cinema.controller.repr.NewVideoRepr;
import com.online.cinema.persist.Country;
import com.online.cinema.persist.Genre;
import com.online.cinema.service.VideoDefinitionService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/admin/")
@Data
@RequiredArgsConstructor
@Slf4j
public class VideoDefinitionController {
    private final VideoDefinitionService videoDefinitionService;

    @PostMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadVideo(NewVideoRepr newVideoRepr) {
        try {
            videoDefinitionService.saveNewVideo(newVideoRepr);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    @GetMapping("/genres")
    public List<Genre> findAllGenres() {
        return videoDefinitionService.findAllGenre();
    }

    @GetMapping("/countries")
    public List<Country> findAllCountries() {
        return videoDefinitionService.findAllCountries();
    }

    @PostMapping("/genres")
    public void addGenre(@RequestBody Genre genre) {
        videoDefinitionService.saveNewGenre(genre);
    }

    @PostMapping("/countries")
    public void addGenre(@RequestBody Country country) {
        videoDefinitionService.saveNewCountry(country);
    }
}
