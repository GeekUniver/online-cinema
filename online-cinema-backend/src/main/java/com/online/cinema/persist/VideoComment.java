package com.online.cinema.persist;

import com.online.cinema.controller.repr.NewCommentRepr;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.time.OffsetDateTime;

@Entity
@Table(name = "video_comment")
@Data
@NoArgsConstructor
public class VideoComment{

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "app_user_id")
    private Long appUserId;

    @Column(name = "video_metadata_id")
    private Long videoMetadataId;

    @Column(name = "user_comment_id")
    private Long userCommentId;

    @Column(name = "comment")
    private String comment;

    @Column(name = "dt")
    private OffsetDateTime dt;

    @Column(name = "deleted")
    private Integer deleted;

    public VideoComment(NewCommentRepr newCommentRepr){
        this.comment = newCommentRepr.getComment();
        this.appUserId = newCommentRepr.getAppUserId();
        this.videoMetadataId = newCommentRepr.getVideoMetadataId();
    }

}
