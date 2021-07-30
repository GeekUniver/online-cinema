package com.online.cinema.repository;

import com.online.cinema.persist.VideoMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoMetadataRepository extends JpaRepository<VideoMetadata, Long>, JpaSpecificationExecutor<VideoMetadata> {
}

