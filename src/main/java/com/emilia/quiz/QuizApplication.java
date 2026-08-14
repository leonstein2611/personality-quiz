package com.emilia.quiz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = "com.emilia.quiz")
public class QuizApplication {
    

    public static void main(String[] args) {
        // Diese Zeile startet das gesamte Spring-Framework
        SpringApplication.run(QuizApplication.class, args);
        
        System.out.println("----------------------------------------------");
        System.out.println("🚀 Das BACKEND LÄUFT!");
        System.out.println("Öffne: http://localhost:8080/index.html");
        System.out.println("Hier sind die Stats:");
        System.out.println("http://localhost:8080/stats.html");
        System.out.println("----------------------------------------------");
    }
}

// Test um Daten einzusehen : http://localhost:8080/h2-console
// bei JDBC URL : jdbc:h2:file:./data/quizdb