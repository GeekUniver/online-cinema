package com.online.cinema.controller;

import com.online.cinema.controller.repr.NewVideoRepr;
import com.online.cinema.service.VideoService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/admin/")
@Data
@RequiredArgsConstructor
@Slf4j
public class UploadVideoController {
//    private UploadVideoService uploadVideoService;
    private VideoService videoService;

    @PostMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadVideo(NewVideoRepr newVideoRepr) {
        log.info(newVideoRepr.getDescription());

        try {
            videoService.saveNewVideo(newVideoRepr);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
