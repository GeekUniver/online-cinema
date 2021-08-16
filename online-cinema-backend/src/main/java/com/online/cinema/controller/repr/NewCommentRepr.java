package com.online.cinema.controller.repr;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@Data
public class NewCommentRepr {

    private String comment;
    private Long appUserId;    //TODO
    private Long videoMetadataId;
//    private Long userCommentId; /* not mpv, TODO*/
}