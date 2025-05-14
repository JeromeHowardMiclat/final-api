package com.miclat.socialmedia;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class SocialMedia {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "social_media_seq")
    @SequenceGenerator(name = "social_media_seq", sequenceName = "social_media_seq", allocationSize = 1)
    private Long id;
    private String author;
    private String title;
    private String description;
    private String mediaUrl;
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now(); // This sets the current timestamp
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getMediaUrl() { return mediaUrl; }
    public void setMediaUrl(String mediaUrl) { this.mediaUrl = mediaUrl; }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}