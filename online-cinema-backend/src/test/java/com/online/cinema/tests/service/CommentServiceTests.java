package com.online.cinema.tests.service;

import com.online.cinema.controller.repr.CommentRepr;
import com.online.cinema.persist.User;
import com.online.cinema.persist.VideoComment;
import com.online.cinema.repository.CommentRepository;
import com.online.cinema.service.CommentService;
import com.online.cinema.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.OffsetDateTime;
import java.util.List;

@SpringBootTest(classes = CommentService.class)
public class CommentServiceTests {

    private final String COMMENT_INFO = "WOW! Comment!";
    private final Long COMMENT_ID = 10L;
    private final Long VIDEO_ID = 1L;
    private final Long USER_ID = 5L;
    private final String USER_NAME = "Alina";

    private CommentService commentService;

    @BeforeEach
    public void setup() {
        commentService = new CommentService(commentRepository, userService);
    }

    @MockBean
    private UserService userService;

    @MockBean
    private CommentRepository commentRepository;

    @Test
    public void findCommentsByVideoId() {
        VideoComment demoComment = new VideoComment();
        demoComment.setId(COMMENT_ID);
        demoComment.setDt(OffsetDateTime.now());
        demoComment.setAppUserId(USER_ID);
        demoComment.setComment(COMMENT_INFO);

        User user = new User();
        user.setId(USER_ID);
        user.setLogin(USER_NAME);

        Mockito
                .doReturn(user)
                .when(userService)
                .findById(USER_ID);

        Mockito
                .doReturn(List.of(demoComment))
                .when(commentRepository)
                .findByVideoMetadataId(VIDEO_ID);

        List<CommentRepr> listComments = commentService.findCommentsByVideoId(VIDEO_ID);

        Mockito.verify(commentRepository, Mockito.times(1)).findByVideoMetadataId(ArgumentMatchers.eq(VIDEO_ID));
        Assertions.assertEquals(COMMENT_INFO, listComments.get(0).getComment());
    }
}
