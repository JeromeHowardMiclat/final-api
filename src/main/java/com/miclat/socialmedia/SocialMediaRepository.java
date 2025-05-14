package com.miclat.socialmedia;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SocialMediaRepository extends JpaRepository<SocialMedia, Long> {

    @Query(value = "SELECT * FROM social_media p WHERE " +
            "SIMILARITY(LOWER(p.title)::text, LOWER(:key)::text) > 0.3 OR " +
            "SIMILARITY(LOWER(p.description)::text, LOWER(:key)::text) > 0.3 OR " +
            "SIMILARITY(LOWER(p.media_url)::text, LOWER(:key)::text) > 0.3 OR " +
            "SIMILARITY(LOWER(p.author)::text, LOWER(:key)::text) > 0.3",
            nativeQuery = true)
    List<SocialMedia> searchPosts(@Param("key") String key);
}
