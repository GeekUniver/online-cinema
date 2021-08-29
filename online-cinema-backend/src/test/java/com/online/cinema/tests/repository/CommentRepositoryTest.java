package com.online.cinema.tests.repository;

import com.online.cinema.persist.VideoComment;
import com.online.cinema.repository.CommentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
public class CommentRepositoryTest {
    private final Long VIDEO_METADATA_ID = 1L;
    private final Long NONEXISTENT_VIDEO_METADATA_ID = 1300L;
    @Autowired
    private CommentRepository commentRepository;

    @Test
    public void findByVideoMetadataIdTest(){
        List<VideoComment> listComments = commentRepository.findByVideoMetadataId(VIDEO_METADATA_ID);
        List<VideoComment> nonexistentListComments = commentRepository.findByVideoMetadataId(NONEXISTENT_VIDEO_METADATA_ID);
        Assertions.assertFalse(listComments.isEmpty());
        Assertions.assertTrue(nonexistentListComments.isEmpty());

        VideoComment newComment = new VideoComment();
        newComment.setComment("Фильм 1 очень и очень плох!");
        Assertions.assertEquals(newComment.getComment(), listComments.get(1).getComment());
    }
}
