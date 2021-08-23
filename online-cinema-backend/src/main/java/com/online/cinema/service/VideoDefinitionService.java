package com.online.cinema.service;

import com.online.cinema.controller.repr.NewVideoRepr;
import com.online.cinema.persist.Country;
import com.online.cinema.persist.Genre;
import com.online.cinema.persist.VideoMetadata;
import com.online.cinema.repository.CountryRepository;
import com.online.cinema.repository.GenreRepository;
import com.online.cinema.repository.VideoMetadataRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.WRITE;

@Service
@RequiredArgsConstructor
@Data
@Slf4j
public class VideoDefinitionService {
    @Value("${data.folder}")
    private String dataFolder;
    private final VideoMetadataRepository videoMetadataRepository;
    private final FrameGrabberService frameGrabberService;
    private final GenreRepository genreRepository;
    private final CountryRepository countryRepository;


    @Transactional
    public void saveNewVideo(NewVideoRepr newVideoRepr) {
        VideoMetadata metadata = new VideoMetadata();
        metadata.setFileName(newVideoRepr.getFile().getOriginalFilename());
        metadata.setContentType(newVideoRepr.getFile().getContentType());
        metadata.setFileSize(newVideoRepr.getFile().getSize());
        metadata.setDescription(newVideoRepr.getDescription());
        metadata.setYear_filmed(newVideoRepr.getYear_filmed());
        metadata.setName(newVideoRepr.getName());
        metadata.setGenreList(genreRepository.findAllById(newVideoRepr.getGenreList().stream().map(Long::valueOf).collect(Collectors.toList())));
        metadata.setCountryList(countryRepository.findAllById(newVideoRepr.getCountryList().stream().map(Long::valueOf).collect(Collectors.toList())));
        videoMetadataRepository.save(metadata);

        Path directory = Path.of(dataFolder, metadata.getId().toString());
        try {
            Files.createDirectory(directory);
            Path file = Path.of(directory.toString(), newVideoRepr.getFile().getOriginalFilename());
            try (OutputStream output = Files.newOutputStream(file, CREATE, WRITE)) {
                newVideoRepr.getFile().getInputStream().transferTo(output);
            }
            long videoLength = frameGrabberService.generatePreviewPictures(file);
            metadata.setVideoLength(videoLength);
            videoMetadataRepository.save(metadata);
        } catch (IOException ex) {
            log.error("", ex);
            throw new IllegalStateException(ex);
        }
    }

    public List<Genre> findAllGenre() {
        return genreRepository.findAll();
    }

    public List<Country> findAllCountries() {
        return countryRepository.findAll();
    }
}
