package com.andis.score.service;

import com.andis.score.client.AmazonCompletionClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class ScoreCalculationService {

    private final AmazonCompletionClient client;

    public ScoreCalculationService(AmazonCompletionClient client) {
        this.client = client;
    }

    /**
     * Gets the score of a given keyword by calling the AmazonCompletionClient's getSuggestions() method
     * asynchronously
     *
     * @param keyword String value for which the score needs to be calculated for
     * @return a score
     */
    public int getScore(String keyword) {
        List<CompletableFuture<Integer>> futures = new ArrayList<>();

        for (int i = 1; i <= keyword.length(); i++) {
            CompletableFuture<Integer> score = client
                    .getSuggestionList(keyword.substring(0, i))
                    .thenApply(suggestions -> calculateScore(keyword, suggestions));

            futures.add(score);
        }

        int overallScore = futures.stream()
                .map(CompletableFuture::join)
                .mapToInt(Integer::intValue)
                .sum();

        return (int) calculateScorePercentage(overallScore, keyword.length());
    }

    /**
     * Calculates the percentage of the given overallScore depending of the word length.
     * With the assumption that there are always 10 suggestions, the formula is:
     * 100 / (wordLength * 10) * overallScore
     *
     * @param overallScore an integer that is a sum of all the calculated scores
     * @param wordLength the length of the keyword
     * @return a calculated percentage float
     */
    private float calculateScorePercentage(int overallScore, int wordLength) {
        return 10f / wordLength * overallScore;
    }

    /**
     * Calculates the score of a keyword appearing in the suggestions list.
     * Basically it loops through the suggestions and checks if the suggestion contains the given keyword.
     * If a suggestion contains the keyword, int score value is increased by 1.
     *
     * @param keyword String to look for in the suggestions
     * @param suggestions a list of suggestion strings
     * @return an integer with the value between 0 and the size of the suggestions list
     */
    private int calculateScore(String keyword, List<String> suggestions) {
        int score = 0;
        keyword = keyword.toLowerCase();

        //Implement a better scoring system, e.g. the earlier the number appears, the bigger score it gets
        for (String suggestion : suggestions) {
            if (suggestion.toLowerCase().contains(keyword)) {
                ++score;
            }
        }

        return score;
    }
}
