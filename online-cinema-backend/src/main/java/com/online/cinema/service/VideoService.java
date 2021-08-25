package com.online.cinema.service;

import com.online.cinema.persist.CrewWithRole;
import com.online.cinema.persist.Genre;
import com.online.cinema.repository.GenreRepository;
import com.online.cinema.repository.VideoMetadataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
import java.util.Random;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.WRITE;
import static com.online.cinema.utils.Utils.removeFileExt;

@Slf4j
@RequiredArgsConstructor
@Service
public class VideoService {

    @Value("${data.folder}")
    private String dataFolder;

    private final VideoMetadataRepository videoMetadataRepository;
    private final GenreRepository genreRepository;

    private final FrameGrabberService frameGrabberService;

    public List<VideoMetadataRepr> findAllVideoMetadata() {
        return videoMetadataRepository.findAll().stream()
                .map(VideoService::convert)
                .collect(Collectors.toList());
    }

    public Long findRandomId() {
        List<VideoMetadataRepr> list = videoMetadataRepository
                .findAll().stream()
                .map(VideoService::convert)
                .collect(Collectors.toList());

        List<Long> listId =  list.stream()
                .map(VideoMetadataRepr::getId)
                .collect(Collectors.toList());

        return listId.get(new Random().nextInt(listId.size()));
    }

    public Optional<VideoMetadataRepr> findById(Long id) {
        return videoMetadataRepository.findById(id)
                .map(VideoService::convert);
    }

    public static VideoMetadataRepr convert(VideoMetadata vmd) {
        vmd.getCrewWithRole().stream().forEach(crewWithRole -> crewWithRole.setVideoMetadata(null));
        VideoMetadataRepr repr = new VideoMetadataRepr(vmd);
        repr.setPreviewUrl("/api/v1/video/preview/" + vmd.getId());
        repr.setStreamUrl("/api/v1/video/stream/" + vmd.getId());
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

