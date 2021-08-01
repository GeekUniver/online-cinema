package com.online.cinema.repository.specifications;

import com.online.cinema.persist.VideoMetadata;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.MultiValueMap;

public class VideoMetadataSpecifications {
    private static Specification<VideoMetadata> fileNameLike(String fileName) {
        return ((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("fileName"), String.format("%%%s%%", fileName)));
    }

    public static Specification<VideoMetadata> build(MultiValueMap<String, String> params) {
        Specification<VideoMetadata> specification = Specification.where(null);
        if (params.containsKey("fileName") && !params.getFirst("fileName").isBlank()) {
            System.out.println(params.getFirst("fileName"));
            specification = specification.and(VideoMetadataSpecifications.fileNameLike(params.getFirst("fileName")));
        }
        return specification;
    }
}
