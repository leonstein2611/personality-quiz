package com.emilia.quiz;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class QuizResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String personalityType;
    private int rating;
    private LocalDateTime createdAt;

    // Standard-Konstruktor für JPA
    public QuizResult() {}

    public QuizResult(String personalityType, int rating) {
        this.personalityType = personalityType;
        this.rating = rating;
        this.createdAt = LocalDateTime.now();
    }

    // Getter (Wichtig für die Statistik später!)
    public Long getId() { return id; }
    public String getPersonalityType() { return personalityType; }
    public int getRating() { return rating; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}