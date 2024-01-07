package com.example.controller;

import com.example.controller.SocialPostController;
import com.example.entity.SocialPost;
import com.example.entity.User;
import com.example.service.SocialPostService;
import com.example.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SocialPostControllerTest {

    @InjectMocks
    private SocialPostController socialPostController;

    @Mock
    private SocialPostService socialPostService;

    @Mock
    private UserService userService;

    @Test
    @WithMockUser(username = "test", password = "test", roles = "ADMIN")
    void getAllSocialPosts() {
        List<SocialPost> mockSocialPosts = Arrays.asList(new SocialPost(), new SocialPost());
        when(socialPostService.getAllSocialPosts()).thenReturn(mockSocialPosts);

        ResponseEntity<List<SocialPost>> responseEntity = socialPostController.getAllSocialPosts();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockSocialPosts, responseEntity.getBody());
    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "ADMIN")
    void getSocialPostById_ExistingPost() {
        Long postId = 1L;
        SocialPost mockSocialPost = new SocialPost();
        when(socialPostService.getSocialPostById(postId)).thenReturn(Optional.of(mockSocialPost));

        ResponseEntity<SocialPost> responseEntity = socialPostController.getSocialPostById(postId);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockSocialPost, responseEntity.getBody());
    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "ADMIN")
    void getSocialPostById_NonexistentPost() {
        Long postId = 1L;
        when(socialPostService.getSocialPostById(postId)).thenReturn(Optional.empty());
        ResponseEntity<SocialPost> responseEntity = socialPostController.getSocialPostById(postId);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "ADMIN")
    void addSocialPost() {
        SocialPost inputSocialPost = new SocialPost();
        SocialPost mockSavedSocialPost = new SocialPost();
        when(socialPostService.addSocialPost(inputSocialPost)).thenReturn(mockSavedSocialPost);
        ResponseEntity<SocialPost> responseEntity = socialPostController.addSocialPost(inputSocialPost);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(mockSavedSocialPost, responseEntity.getBody());
    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "ADMIN")
    void updateSocialPost_ExistingPost() {
        Long postId = 1L;
        SocialPost updatedSocialPost = new SocialPost();
        SocialPost mockUpdatedSocialPost = new SocialPost();
        when(socialPostService.updateSocialPost(postId, updatedSocialPost)).thenReturn(Optional.of(mockUpdatedSocialPost));
        ResponseEntity<SocialPost> responseEntity = socialPostController.updateSocialPost(postId, updatedSocialPost);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockUpdatedSocialPost, responseEntity.getBody());
    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "ADMIN")
    void updateSocialPost_NonexistentPost() {
        Long postId = 1L;
        SocialPost updatedSocialPost = new SocialPost();
        when(socialPostService.updateSocialPost(postId, updatedSocialPost)).thenReturn(Optional.empty());
        ResponseEntity<SocialPost> responseEntity = socialPostController.updateSocialPost(postId, updatedSocialPost);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "ADMIN")
    void deleteSocialPost() {
        Long postId = 1L;

        ResponseEntity<Void> responseEntity = socialPostController.deleteSocialPost(postId);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(socialPostService, times(1)).deleteSocialPost(postId);
    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "ADMIN")
    void getSocialPostsByUserId_ExistingUser() {
        Long userId = 1L;
        User mockUser = new User();
        when(userService.getUserById(userId)).thenReturn(Optional.of(mockUser));

        List<SocialPost> mockSocialPosts = Arrays.asList(new SocialPost(), new SocialPost());
        when(socialPostService.getSocialPostsByUserId(userId)).thenReturn(mockSocialPosts);

        ResponseEntity<List<SocialPost>> responseEntity = socialPostController.getSocialPostsByUserId(userId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockSocialPosts, responseEntity.getBody());
    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "ADMIN")
    void getSocialPostsByUserId_NonexistentUser() {
        Long userId = 1L;
        when(userService.getUserById(userId)).thenReturn(Optional.empty());

        ResponseEntity<List<SocialPost>> responseEntity = socialPostController.getSocialPostsByUserId(userId);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
}
