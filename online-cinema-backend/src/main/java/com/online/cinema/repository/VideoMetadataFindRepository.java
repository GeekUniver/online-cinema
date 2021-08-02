package com.online.cinema.repository;

import com.online.cinema.persist.VideoMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoMetadataFindRepository extends JpaRepository<VideoMetadata, Long> {
    @Query(
            value = "SELECT * FROM video_metadata where year_filmed = ?1",
            nativeQuery = true)
    public List<VideoMetadata> findVideoMetadataByYear(Integer status);


    @Query(
            value = "select video_metadata.* " +
                    "from video_metadata join video_metadata_crew on video_metadata.id = video_metadata_crew.video_metadata_id " +
                    "join crew_classifier on video_metadata_crew.crew_classifier_id = crew_classifier.id " +
                    "where " +
                    "(crew_classifier.first_name = :firstName  and crew_classifier.last_name = :lastName " +
                    "or crew_classifier.first_name = :lastName and crew_classifier.last_name = :firstName) " +
                    "and (crew_classifier.patronymic = :patronymic or crew_classifier.patronymic is null)",
            nativeQuery = true)
    public List<VideoMetadata> findVideoMetadataByCrewFirstLastPatronymic(
                @Param("firstName") String firstName,
                @Param("lastName") String lastName,
                @Param("patronymic") String patronymic);


    public List<VideoMetadata> findAllByName(String name);

}
