package com.online.cinema.service;

import com.online.cinema.controller.repr.NewCommentRepr;
import com.online.cinema.persist.VideoComment;
import com.online.cinema.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public void save (NewCommentRepr newCommentRepr){
        VideoComment newComment = new VideoComment(newCommentRepr);
        commentRepository.save(newComment);


    }
}
