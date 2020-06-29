package com.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
@RestController
@RequestMapping("/jokes")
public class JokesController {
    private final String JOKES_URL = "http://api.icndb.com/jokes/random?firstName=Timo&lastName=Jutila";
    private final String ERROR_MSG = "Something failed, and this is no joke :(";

    private RestTemplate restTemplate = new RestTemplate();

    /*@GetMapping("")
    public List<ResponseEntity<Joke>> getQuestions() {

        String url = "http://api.icndb.com/jokes";
        ResponseEntity<Joke> questions = restTemplate.<Joke>getForEntity(url, Joke.class);

        return Arrays.asList(questions);
    }*/

    /*@GetMapping("")
    public List<Joke> getJoke() {

        String url = "http://api.icndb.com/jokes";
        Joke jokes = restTemplate.getForObject(url, Joke.class);

        return Arrays.asList(jokes);
    }*/

    @GetMapping
    public String getRandomJoke() {
        try {
            Joke joke = restTemplate.getForObject(JOKES_URL, Joke.class);
            return joke.getValue().getJoke();
        } catch (Exception e) {
            return ERROR_MSG;
        }
    }
}

/*List<Questions> getReputation() {
        return questions;*/





