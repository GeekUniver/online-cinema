package com.online.cinema.controller.repr;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class VideoMetadataRepr {
    private Long id;
    private String description;
    private String contentType;
    private String previewUrl;
    private String streamUrl;
    private String name;
    private Integer year_filmed;
}
