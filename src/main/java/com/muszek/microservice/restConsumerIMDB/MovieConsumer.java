package com.muszek.microservice.restConsumerIMDB;

import com.muszek.microservice.fieldsOfIMDB.Movie;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MovieConsumer implements Consume {
    ConsumerAndErrorChecker consumerAndErrorChecker = new ConsumerAndErrorChecker();

    @Override
    public Movie consume(String id) {
        RestTemplate restTemplate = new RestTemplate();
        String path = "https://java.kisim.eu.org/movies/" + id;
        Movie movie = consumerAndErrorChecker.get(path,Movie.class);
        return movie;
    }
}

