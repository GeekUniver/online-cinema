package com.online.cinema.service;

import com.online.cinema.repository.VideoMetadataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpRange;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.online.cinema.persist.VideoMetadata;

import com.online.cinema.controller.repr.VideoMetadataRepr;
import com.online.cinema.controller.repr.NewVideoRepr;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.WRITE;
import static com.online.cinema.Utils.removeFileExt;

@Slf4j
@RequiredArgsConstructor
@Service
public class VideoService {

    @Value("${data.folder}")
    private String dataFolder;

    private final VideoMetadataRepository videoMetadataRepository;

    private final FrameGrabberService frameGrabberService;

    public List<VideoMetadataRepr> findAllVideoMetadata() {
        return videoMetadataRepository.findAll().stream()
                .map(VideoService::convert)
                .collect(Collectors.toList());
    }


    public Optional<VideoMetadataRepr> findById(Long id) {
        return videoMetadataRepository.findById(id)
                .map(VideoService::convert);
    }

    public static VideoMetadataRepr convert(VideoMetadata vmd) {
        VideoMetadataRepr repr = new VideoMetadataRepr();
        repr.setId(vmd.getId());
        repr.setPreviewUrl("/api/v1/video/preview/" + vmd.getId());
        repr.setStreamUrl("/api/v1/video/stream/" + vmd.getId());
        repr.setDescription(vmd.getDescription());
        repr.setContentType(vmd.getContentType());
        repr.setName(vmd.getName());
        repr.setYear_filmed(vmd.getYear_filmed());
        return repr;
    }

    public Optional<InputStream> getPreviewInputStream(Long id) {
        return videoMetadataRepository.findById(id)
                .flatMap(vmd -> {
                    Path previewPicturePath = Path.of(dataFolder,
                            vmd.getId().toString(),
                            removeFileExt(vmd.getFileName()) + ".jpeg");
                    if (!Files.exists(previewPicturePath)) {
                        return Optional.empty();
                    }
                    try {
                        return Optional.of(Files.newInputStream(previewPicturePath));
                    } catch (IOException ex) {
                        log.error("", ex);
                        return Optional.empty();
                    }
                });
    }

    @Transactional
    public void saveNewVideo(NewVideoRepr newVideoRepr) {
        VideoMetadata metadata = new VideoMetadata();
        metadata.setFileName(newVideoRepr.getFile().getOriginalFilename());
        metadata.setContentType(newVideoRepr.getFile().getContentType());
        metadata.setFileSize(newVideoRepr.getFile().getSize());
        metadata.setDescription(newVideoRepr.getDescription());
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

    public Optional<StreamBytesInfo> getStreamBytes(Long id, HttpRange range) {
        Optional<VideoMetadata> byId = videoMetadataRepository.findById(id);
        if (byId.isEmpty()) {
            return Optional.empty();
        }
        Path filePath = Path.of(dataFolder, Long.toString(id), byId.get().getFileName());
        if (!Files.exists(filePath)) {
            log.error("File {} not found", filePath);
            return Optional.empty();
        }
        try {
            long fileSize = Files.size(filePath);
            long chunkSize = fileSize / 100;
            if (range == null) {
                return Optional.of(new StreamBytesInfo(
                        out -> Files.newInputStream(filePath).transferTo(out),
                        fileSize, 0, fileSize, byId.get().getContentType()));
            }

            long rangeStart = range.getRangeStart(0);
            long rangeEnd = rangeStart + chunkSize; // range.getRangeEnd(fileSize);
            if (rangeEnd >= fileSize) {
                rangeEnd = fileSize - 1;
            }
            long finalRangeEnd = rangeEnd;
            return Optional.of(new StreamBytesInfo(
                    out -> {
                        try (InputStream inputStream = Files.newInputStream(filePath)) {
                            inputStream.skip(rangeStart);
                            byte[] bytes = inputStream.readNBytes((int) ((finalRangeEnd - rangeStart) + 1));
                            out.write(bytes);
                        }
                    },
                    fileSize, rangeStart, rangeEnd, byId.get().getContentType()));
        } catch (IOException ex) {
            log.error("", ex);
            return Optional.empty();
        }
    }
}

