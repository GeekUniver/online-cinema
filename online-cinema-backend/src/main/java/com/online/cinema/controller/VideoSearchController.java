package com.online.cinema.controller;

import com.online.cinema.persist.VideoMetadata;
import com.online.cinema.repository.VideoMetadataRepository;
import com.online.cinema.service.FindVideoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/v1/search")
@Slf4j
@RequiredArgsConstructor
@RestController
public class VideoSearchController {

    private final VideoMetadataRepository videoMetadataRepository;
    private final FindVideoService findVideoService;

    @GetMapping("/all")
    protected List<VideoMetadata> getAllVideo(){
        List<VideoMetadata> videoMetadataList;

        videoMetadataList = List.copyOf(videoMetadataRepository.findAll());
        videoMetadataList.forEach((v)-> {
            v.getCrewWithRole().forEach(crewWithRole -> crewWithRole.setVideoMetadata(null));
        });

        return videoMetadataList;
    }

    @GetMapping("/find/{condition}")
    protected List<VideoMetadata> getAllVideoByCondition(@PathVariable String condition){
        return findVideoService.find(condition);
    }
}

