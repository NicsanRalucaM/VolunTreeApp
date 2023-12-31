package com.example.controller;

import com.example.entity.SocialPost;
import com.example.entity.User;
import com.example.service.SocialPostService;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/socialposts")
public class SocialPostController {

    @Autowired
    private SocialPostService socialPostService;
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<SocialPost>> getAllSocialPosts() {
        List<SocialPost> socialPosts = socialPostService.getAllSocialPosts();
        return new ResponseEntity<>(socialPosts, HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<SocialPost> getSocialPostById(@PathVariable Long postId) {
        Optional<SocialPost> socialPost = socialPostService.getSocialPostById(postId);
        return socialPost.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<SocialPost> addSocialPost(@RequestBody SocialPost socialPost) {
        SocialPost newSocialPost = socialPostService.addSocialPost(socialPost);
        return new ResponseEntity<>(newSocialPost, HttpStatus.CREATED);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<SocialPost> updateSocialPost(@PathVariable Long postId, @RequestBody SocialPost updatedSocialPost) {
        Optional<SocialPost> socialPost = socialPostService.updateSocialPost(postId, updatedSocialPost);
        return socialPost.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deleteSocialPost(@PathVariable Long postId) {
        socialPostService.deleteSocialPost(postId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<SocialPost>> getSocialPostsByUserId(@PathVariable Long userId) {
        Optional<User> userOptional = userService.getUserById(userId);

        if (userOptional.isPresent()) {
            List<SocialPost> socialPosts = socialPostService.getSocialPostsByUserId(userId);
            return new ResponseEntity<>(socialPosts, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
