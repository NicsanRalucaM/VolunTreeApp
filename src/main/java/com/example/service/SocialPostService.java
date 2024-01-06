package com.example.service;

import com.example.entity.SocialPost;
import com.example.repository.SocialPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SocialPostService {

    @Autowired
    private SocialPostRepository socialPostRepository;

    public List<SocialPost> getAllSocialPosts() {
        return socialPostRepository.findAll();
    }

    public Optional<SocialPost> getSocialPostById(Long postId) {
        return socialPostRepository.findById(postId);
    }

    public List<SocialPost> getSocialPostByUserId(Long userId) {
        return socialPostRepository.findAll()
                .stream()
                .filter(post -> post.getUserId().equals(userId))
                .toList();
    }

    public SocialPost addSocialPost(SocialPost socialPost) {
        return socialPostRepository.save(socialPost);
    }

    public Optional<SocialPost> updateSocialPost(Long postId, SocialPost updatedSocialPost) {
        Optional<SocialPost> existingPostOptional = socialPostRepository.findById(postId);
        if (existingPostOptional.isPresent()) {
            SocialPost existingPost = existingPostOptional.get();
            existingPost.setUserId(updatedSocialPost.getUserId());
            existingPost.setTitle(updatedSocialPost.getTitle());
            existingPost.setContent(updatedSocialPost.getContent());
            existingPost.setPostDate(updatedSocialPost.getPostDate());
            SocialPost savedPost = socialPostRepository.save(existingPost);
            return Optional.of(savedPost);
        } else {
            return Optional.empty();
        }
    }

    public void deleteSocialPost(Long postId) {
        socialPostRepository.deleteById(postId);
    }
}
