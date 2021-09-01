package com.online.cinema.controller.repr;

import com.online.cinema.persist.VideoComment;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CommentRepr {

    private String comment;
    private Long appUserId;    //TODO
    private Long videoMetadataId;
//    private Long userCommentId; /* not mpv, TODO*/
    private String appUserLogin;
    private String data;

    public CommentRepr(VideoComment videoComment) {
        this.setComment(videoComment.getComment());
        this.setVideoMetadataId(videoComment.getVideoMetadataId());
        this.setAppUserId(videoComment.getAppUserId());
        String dt = (videoComment.getDt().getDayOfMonth() + "." + videoComment.getDt().getMonth().getValue() +"." + videoComment.getDt().getYear() + " в " +
        videoComment.getDt().getHour() + ":" + videoComment.getDt().getMinute());
        this.setData(dt);
    }
}