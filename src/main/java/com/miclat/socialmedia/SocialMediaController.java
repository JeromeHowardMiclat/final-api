package com.miclat.socialmedia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/miclat")
@CrossOrigin(origins = "http://localhost:5173")
public class SocialMediaController {

    @Autowired
    private SocialMediaRepository socialMediaRepository;

    // Get all posts
    @GetMapping("/posts")
    public ResponseEntity<List<SocialMedia>> getAllPosts() {
        List<SocialMedia> posts = socialMediaRepository.findAll();
        return ResponseEntity.ok(posts);
    }

    // Create a new post
    @PostMapping("/post")
    public ResponseEntity<SocialMedia> addNewPost(@RequestBody SocialMedia post) {
        SocialMedia savedPost = socialMediaRepository.save(post);
        return ResponseEntity.ok(savedPost);
    }

    // Update a post by ID
    @PutMapping("/posts/{id}")
    public ResponseEntity<String> editPost(@PathVariable Long id, @RequestBody SocialMedia updatedPost) {
        Optional<SocialMedia> optionalPost = socialMediaRepository.findById(id);
        if (!optionalPost.isPresent()) {
            return ResponseEntity.badRequest().body("Post not found");
        }

        SocialMedia post = optionalPost.get();
        post.setAuthor(updatedPost.getAuthor());
        post.setTitle(updatedPost.getTitle());
        post.setDescription(updatedPost.getDescription());
        post.setMediaUrl(updatedPost.getMediaUrl());
        socialMediaRepository.save(post);

        return ResponseEntity.ok("Post with id " + id + " updated.");
    }

    // Delete a post by ID
    @DeleteMapping("/posts/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id) {
        if (!socialMediaRepository.existsById(id)) {
            return ResponseEntity.badRequest().body("Post not found");
        }

        socialMediaRepository.deleteById(id);
        return ResponseEntity.ok("Post with id " + id + " deleted.");
    }

    // Get a post by ID
    @GetMapping("/posts/{id}")
    public ResponseEntity<SocialMedia> getPostById(@PathVariable Long id) {
        Optional<SocialMedia> post = socialMediaRepository.findById(id);
        return post.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Search posts by title, description, or media URL
    @GetMapping("/posts/search")
    public ResponseEntity<?> searchPosts(@RequestParam(required = false) String key) {
        try {
            // Validate the 'key' parameter
            if (key == null || key.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Search key cannot be null or empty.");
            }

            // Perform the search
            List<SocialMedia> posts = socialMediaRepository.searchPosts(key);

            // Check if no results found
            if (posts.isEmpty()) {
                return ResponseEntity.status(404).body("No posts found for the given search key.");
            }

            return ResponseEntity.ok(posts);
        } catch (Exception e) {
            // Log the exception (stack trace)
            e.printStackTrace();

            // Return 500 Internal Server Error with a descriptive message
            return ResponseEntity.status(500).body("An error occurred while processing your request: " + e.getMessage());
        }
    }
    // Bulk upload posts
    @PostMapping("/bulk-posts")
    public ResponseEntity<String> addMultiplePosts(@RequestBody List<SocialMedia> posts) {
        if (posts.isEmpty()) {
            return ResponseEntity.badRequest().body("Post list is empty.");
        }

        socialMediaRepository.saveAll(posts);
        return ResponseEntity.ok("Successfully added " + posts.size() + " posts.");
    }
}