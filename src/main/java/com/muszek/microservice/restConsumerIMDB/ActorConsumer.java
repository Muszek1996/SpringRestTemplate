package com.muszek.microservice.restConsumerIMDB;

import com.muszek.microservice.fieldsOfIMDB.Actor;
import org.springframework.stereotype.Service;

@Service
public class ActorConsumer extends Thread implements Consume {
        ConsumerAndErrorChecker consumerAndErrorChecker = new ConsumerAndErrorChecker();
    @Override
    public Actor consume(String id) {
        String path = "https://java.kisim.eu.org/actors/" + id;
        Actor actor = consumerAndErrorChecker.get(path,Actor.class);
        return actor;
    }
}
