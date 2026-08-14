package com.emilia.quiz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") 
public class QuizController {

    @Autowired
    private QuizResultRepository repository;

    @GetMapping("/questions")
    public List<Question> getQuestions() {
        List<Question> questions = new ArrayList<>();

        questions.add(new Question("Fällt es Ihnen leicht Entscheidungen zu treffen?", List.of(
            new Question.Option("Wenn es sein muss", Map.of("FUNKTIONIERENDE", 5)),
            new Question.Option("Ohne Probleme", Map.of("ÜBERZEUGTE", 5)),
            new Question.Option("Es stellt eine Herausforderung dar", Map.of("GETRIEBENE", 5)),
            new Question.Option("Für mich selbst nicht", Map.of("KONTROLLIERENDE", 5))
        )));

        questions.add(new Question("Haben Sie klare Vorstellungen davon wer Sie sind?", List.of(
            new Question.Option("Absolut nicht", Map.of("GETRIEBENE", 5)),
            new Question.Option("Definitiv", Map.of("ÜBERZEUGTE", 5)),
            new Question.Option("Ich denke ja", Map.of("UNAUFFÄLLIGE", 5)),
            new Question.Option("Dadurch was die anderen sagen", Map.of("FUNKTIONIERENDE", 5))
        )));

        questions.add(new Question("Auf einer Party wäre ich...", List.of(
            new Question.Option("auf der Suche nach dem größten Spaß", Map.of("GETRIEBENE", 5)),
            new Question.Option("am Beobachten", Map.of("UNAUFFÄLLIGE", 5)),
            new Question.Option("der DJ", Map.of("KONTROLLIERENDE", 5)),
            new Question.Option("der Freund für alle", Map.of("FUNKTIONIERENDE", 5))
        )));

        questions.add(new Question("Wenn ich im Mittelpunkt bin würde ich am Liebsten?", List.of(
            new Question.Option("Den Moment strecken", Map.of("KONTROLLIERENDE", 5)),
            new Question.Option("Dem Albtraum entfliehen", Map.of("UNAUFFÄLLIGE", 5)),
            new Question.Option("Dort bleiben", Map.of("ÜBERZEUGTE", 5)),
            new Question.Option("Am liebsten verschwinden", Map.of("FUNKTIONIERENDE", 5))
        )));

        questions.add(new Question("Was tun Sie wenn Ihnen ein Fehler unterläuft?", List.of(
            new Question.Option("Den Fehler beheben", Map.of("FUNKTIONIERENDE", 5)),
            new Question.Option("Den Schuldigen suchen", Map.of("ÜBERZEUGTE", 5)),
            new Question.Option("Nach einer Lösung suchen", Map.of("GETRIEBENE", 5)),
            new Question.Option("Den Fehler unauffällig verschwinden lassen", Map.of("UNAUFFÄLLIGE", 5))
        )));

        questions.add(new Question("Passen Sie sich den Erwartungen anderer Menschen an?", List.of(
            new Question.Option("Im Gegenteil", Map.of("KONTROLLIERENDE", 5)),
            new Question.Option("Ja, Sie helfen mir", Map.of("GETRIEBENE", 5)),
            new Question.Option("Warum sollte ich?", Map.of("ÜBERZEUGTE", 5)),
            new Question.Option("Was sollte ich sonst tun?", Map.of("FUNKTIONIERENDE", 5))
        )));

        questions.add(new Question("Fühlen Sie sich anderen Menschen oft einen Schritt voraus?", List.of(
            new Question.Option("Ja, weil ich besser bin", Map.of("ÜBERZEUGTE", 5)),
            new Question.Option("Ja, weil ich alles plane", Map.of("KONTROLLIERENDE", 5)),
            new Question.Option("Ja, weil ich alles beobachte", Map.of("UNAUFFÄLLIGE", 5)),
            new Question.Option("Ja, weil ich schon alles probiert habe", Map.of("GETRIEBENE", 5))
        )));

        return questions;
    }

    private final Map<String, PersonalityType> typeDefinitions = Map.of(
        "FUNKTIONIERENDE", new PersonalityType("Funktionierende", "Der Funktionierende", "Du hältst alles am Laufen!"),
        "GETRIEBENE", new PersonalityType("Getriebene", "Der Getriebene", "Dein Leben ist eine Suche."),
        "UNAUFFÄLLIGE", new PersonalityType("Unauffällige", "Der Unauffällige", "Du kannst jedes Geschehen unbeachtet beobachten."),
        "ÜBERZEUGTE", new PersonalityType("Überzeugte", "Der Überzeugte", "Was du willst bekommst du auch."),
        "KONTROLLIERENDE", new PersonalityType("Kontrollierende", "Der Kontrollierende", "Du behältst immer den Überblick.")
    );

    @GetMapping("/types/{id}")
    public PersonalityType getTypeDetails(@PathVariable String id) {
        return typeDefinitions.getOrDefault(id.toUpperCase(), 
            new PersonalityType("Unzuordnenbare", "Unzuordnenbare", "Dein Typ passt in keine Schublade!"));
    }

    // NEU: Endpoint zum Speichern der Ergebnisse in der Datenbank
    @PostMapping("/submit-result")
    public void saveResult(@RequestBody Map<String, Object> payload) {
        String type = (String) payload.get("type");
        int rating = (int) payload.get("rating");
        
        QuizResult result = new QuizResult(type, rating);
        repository.save(result);
    }

    // Für die Statistik
    @GetMapping("/stats")
    public Map<String, Object> getStats() {
    List<QuizResult> allResults = repository.findAll();
    
    // 1. Verteilung der Typen zählen
    Map<String, Long> typeCounts = new HashMap<>();
    for (QuizResult res : allResults) {
        String type = res.getPersonalityType();
        typeCounts.put(type, typeCounts.getOrDefault(type, 0L) + 1);
    }

    // 2. Durchschnittsbewertung berechnen
    double avgRating = allResults.stream()
            .mapToInt(QuizResult::getRating)
            .average()
            .orElse(0.0);

    // 3. Alles in einer Antwort zusammenfassen
    Map<String, Object> response = new HashMap<>();
    response.put("counts", typeCounts);
    response.put("averageRating", Math.round(avgRating * 10.0) / 10.0); // Auf 1 Nachkommastelle runden
    response.put("totalParticipants", allResults.size());

    return response;
}
}