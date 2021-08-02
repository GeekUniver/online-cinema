package com.online.cinema.persist;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "video_score")
@Data
@NoArgsConstructor
public class VideoScore{

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "app_user_id")
    private Long AppUserId;

    @Column(name = "video_metadata_id")
    private Long VideoMetadataId;

    @Column(name = "score")
    private Integer score;

    @Column(name = "dt")
    private OffsetDateTime dt;
}
