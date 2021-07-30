package com.online.cinema.repository.specifications;

import com.online.cinema.persist.VideoMetadata;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.MultiValueMap;

public class VideoMetadataSpecifications {
    private static Specification<VideoMetadata> fileNameLike(String fileNamePart) {
        return ((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("fileName"), String.format("%%s%%", fileNamePart)));
    }

    public static Specification<VideoMetadata> build(MultiValueMap<String, String> params) {
        Specification<VideoMetadata> specification = Specification.where(null);
        if (params.containsKey("fileName") && !params.getFirst("fileName").isBlank()) {
            specification = specification.and(VideoMetadataSpecifications.fileNameLike(params.getFirst("fileName")));
        }
        return specification;
    }
}
