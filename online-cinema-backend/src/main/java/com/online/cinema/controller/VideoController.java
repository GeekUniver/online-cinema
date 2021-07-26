package com.online.cinema.controller;

import com.online.cinema.exception_handlers.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRange;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import com.online.cinema.service.StreamBytesInfo;
import com.online.cinema.controller.repr.VideoMetadataRepr;
import com.online.cinema.service.VideoService;
import com.online.cinema.controller.repr.NewVideoRepr;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.List;
import java.util.function.Supplier;

@RequestMapping("/api/v1/video")
@Slf4j
@RequiredArgsConstructor
@RestController
public class  VideoController {

    private final VideoService videoService;

    @GetMapping("/all")
    public List<VideoMetadataRepr> findAllVideoMetadata() {
        return videoService.findAllVideoMetadata();
    }

    @GetMapping("/{id}")
    public VideoMetadataRepr findVideoMetadataById(@PathVariable("id") Long id) throws Throwable {
        return videoService.findById(id).orElseThrow((Supplier<Throwable>) () -> new NotFoundException("There is no movie with id=" + id));
    }

    @GetMapping(value = "/preview/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<StreamingResponseBody> getPreviewPicture(@PathVariable("id") Long id) {
        InputStream inputStream = videoService.getPreviewInputStream(id)
                .orElseThrow(NotFoundException::new);
        return ResponseEntity.ok(inputStream::transferTo);
    }

    @GetMapping("/stream/{id}")
    public ResponseEntity<StreamingResponseBody> streamVideo(@RequestHeader(value = "Range", required = false) String httpRangeHeader,
                                                             @PathVariable("id") Long id) {
        log.info("Requested range [{}] for file `{}`", httpRangeHeader, id);

        List<HttpRange> httpRangeList = HttpRange.parseRanges(httpRangeHeader);
        StreamBytesInfo streamBytesInfo = videoService.getStreamBytes(id, httpRangeList.size() > 0 ? httpRangeList.get(0) : null)
                .orElseThrow(NotFoundException::new);

        long byteLength = streamBytesInfo.getRangeEnd() - streamBytesInfo.getRangeStart() + 1;
        ResponseEntity.BodyBuilder builder = ResponseEntity.status(httpRangeList.size() > 0 ? HttpStatus.PARTIAL_CONTENT : HttpStatus.OK)
                .header("Content-Type", streamBytesInfo.getContentType())
                .header("Accept-Ranges", "bytes")
                .header("Content-Length", Long.toString(byteLength));

        if (httpRangeList.size() > 0) {
            builder.header("Content-Range",
                    "bytes " + streamBytesInfo.getRangeStart() +
                            "-" + streamBytesInfo.getRangeEnd() +
                            "/" + streamBytesInfo.getFileSize());
        }
        log.info("Providing bytes from {} to {}. We are at {}% of overall video.",
                streamBytesInfo.getRangeStart(), streamBytesInfo.getRangeEnd(),
                new DecimalFormat("###.##").format(100.0 * streamBytesInfo.getRangeStart() / streamBytesInfo.getFileSize()));
        return builder.body(streamBytesInfo.getResponseBody());
    }

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

