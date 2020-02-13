package com.andis.score.client;

import org.json.JSONArray;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class AmazonCompletionClient {

    private final RestTemplate restTemplate;

    public AmazonCompletionClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder
                .setConnectTimeout(Duration.ofMillis(5000))
                .build();
    }

    /**
     * Create an URI with the given parameter a query parameter (q={parameter}).
     * Does a GET call to the URI. If the response status is OK(200), parses the received
     * JSON array and returns a list of suggestions. Else returns an empty list
     *
     * @param parameter the keyword to get the suggestion list by
     * @return a CompletableFuture of a string list
     */
    @Async
    public CompletableFuture<List<String>> getSuggestionList(String parameter) {
        URI uri = UriComponentsBuilder
                .fromHttpUrl("https://completion.amazon.com/search/complete")
                .queryParam("mkt", "1")
                .queryParam("search-alias", "aps")
                .queryParam("client", "amazon-search-ui")
                .query("q={keyword}")
                .build(parameter);

        ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            List<String> suggestionList = new ArrayList<>();
            for (Object o : new JSONArray(response.getBody()).getJSONArray(1)) {
                suggestionList.add((String) o);
            }

            return CompletableFuture.completedFuture(suggestionList);
        }

        return CompletableFuture.completedFuture(Collections.emptyList());
    }
}
