package com.andis.score.controller;

import com.andis.score.service.ScoreCalculationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/estimate")
public class ScoreController {

    private final ScoreCalculationService service;

    public ScoreController(ScoreCalculationService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Integer> calculateScore(@RequestParam String keyword) {
        return ResponseEntity.ok(
                service.getScore(keyword)
        );
    }
}
