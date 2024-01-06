package com.example.repository;

import com.example.entity.SocialPost;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class SocialPostRepositoryTest {

    @Autowired
    private SocialPostRepository socialPostRepository;

    @Test
    void testSaveSocialPost() {
        SocialPost socialPost = new SocialPost(null, 1L, "Title", "Content", LocalDateTime.now());
        SocialPost savedSocialPost = socialPostRepository.save(socialPost);
        assertNotNull(savedSocialPost.getId());
        Optional<SocialPost> retrievedSocialPost = socialPostRepository.findById(savedSocialPost.getId());
        assertTrue(retrievedSocialPost.isPresent());
        assertEquals("Title", retrievedSocialPost.get().getTitle());
        assertEquals("Content", retrievedSocialPost.get().getContent());
    }

    @Test
    void testFindByUserId() {
        SocialPost socialPost = new SocialPost(null, 1L, "Title", "Content", LocalDateTime.now());
        socialPostRepository.save(socialPost);

        List<SocialPost> userPosts = socialPostRepository.findByUserId(1L);
        assertFalse(userPosts.isEmpty());
        assertEquals("Title", userPosts.get(0).getTitle());
        assertEquals("Content", userPosts.get(0).getContent());
    }

    @Test
    void testDeleteSocialPost() {
        SocialPost socialPost = new SocialPost(null, 1L, "Title", "Content", LocalDateTime.now());
        SocialPost savedSocialPost = socialPostRepository.save(socialPost);

        socialPostRepository.deleteById(savedSocialPost.getId());
        Optional<SocialPost> retrievedSocialPost = socialPostRepository.findById(savedSocialPost.getId());
        assertTrue(retrievedSocialPost.isEmpty());
    }
}
