package com.online.cinema.service;

import com.online.cinema.controller.repr.CommentRepr;
import com.online.cinema.persist.VideoComment;
import com.online.cinema.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserService userService;


    public void save(CommentRepr commentRepr) {
        VideoComment newComment = new VideoComment(commentRepr);
        newComment.setDt(OffsetDateTime.now());
        commentRepository.save(newComment);
    }

    public List<CommentRepr> findAllComments() {
        List<CommentRepr> listCommentRepr = commentRepository.findAll().stream()
                .map(videoComment -> new CommentRepr(videoComment))
                .collect(Collectors.toList());
        for (CommentRepr commentRepr : listCommentRepr) {
            commentRepr.setAppUserLogin(userService.findById(commentRepr.getAppUserId()).getLogin());
        }
        return listCommentRepr;
    }

    public List<CommentRepr> findCommentsByVideoId(Long id) {
        List<CommentRepr> listCommentRepr = commentRepository.findByVideoMetadataId(id).stream()
                .map(videoComment -> new CommentRepr(videoComment))
                .collect(Collectors.toList());
        for (CommentRepr commentRepr : listCommentRepr) {
            commentRepr.setAppUserLogin(userService.findById(commentRepr.getAppUserId()).getLogin());
        }
        return listCommentRepr;
    }

}
