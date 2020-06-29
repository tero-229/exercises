package com.example.demo;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

@RunWith(MockitoJUnitRunner.class)
public class JokesControllerTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private JokesController jokesController = new JokesController();

    private final String JOKES_URL  = "http://api.icndb.com/jokes/random?firstName=Timo&lastName=Jutila";
    private String ERROR_MSG = "Something failed, and this is no joke :(";

    @Test
    public void  testGetRandomJokeShouldReturnValidJoke() {
        String refJoke = "vitsi";

        Mockito.when(restTemplate.getForObject(JOKES_URL, Joke.class))
                .thenReturn(new Joke(refJoke));

        String joke = jokesController.getRandomJoke();

        Assert.assertEquals(joke, refJoke);
    }
    @Test
    public void testGetRandomJokeShouldReturnErrorMessageWhenExternalRequestFails() {
        Mockito.when(restTemplate.getForObject(JOKES_URL, Joke.class))
                .thenThrow(RestClientResponseException.class);

        String joke = jokesController.getRandomJoke();
        Assert.assertEquals(ERROR_MSG, joke);
    }
}
