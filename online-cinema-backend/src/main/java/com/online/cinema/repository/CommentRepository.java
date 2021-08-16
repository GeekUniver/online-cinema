package com.online.cinema.repository;

import com.online.cinema.persist.VideoComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository <VideoComment, Long> {
}
