package com.muszek.microservice.restConsumerIMDB;

import com.muszek.microservice.fieldsOfIMDB.Movie;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Service
public class MoviesByActorConsumer {
    ConsumerAndErrorChecker consumerAndErrorChecker = new ConsumerAndErrorChecker();
    public List<Movie> consume(String id) {
        RestTemplate restTemplate = new RestTemplate();
        String path = "https://java.kisim.eu.org/actors/" + id + "/movies";
        List<Movie> movies = Arrays.asList(consumerAndErrorChecker.get(path,Movie[].class));
        return movies;
    }

}
