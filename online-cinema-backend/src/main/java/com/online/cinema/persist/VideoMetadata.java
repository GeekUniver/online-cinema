package com.online.cinema.persist;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "video_metadata")
@Data
@NoArgsConstructor
public class VideoMetadata {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String fileName;

    @Column
    private String contentType;

    @Column
    private String description;

    @Column
    private Long fileSize;

    @Column
    private Long videoLength;

}

