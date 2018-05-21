package com.muszek.microservice.httpRequestHandler;

import com.muszek.microservice.fieldsOfIMDB.Actor;
import com.muszek.microservice.fieldsOfIMDB.Movie;
import com.muszek.microservice.shortestPathOperations.FindShortestPath;
import com.muszek.microservice.restConsumerIMDB.ActorConsumer;
import com.muszek.microservice.restConsumerIMDB.MovieConsumer;
import com.muszek.microservice.restConsumerIMDB.MoviesByActorConsumer;
import org.jgrapht.GraphPath;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class httpController {

    private final ActorConsumer actorConsumer;
    private final MovieConsumer movieConsumer;
    private final MoviesByActorConsumer moviesByActorConsumer;
    private final FindShortestPath findShortestPath;


    public httpController(ActorConsumer aC, MovieConsumer mC, MoviesByActorConsumer mBAC, FindShortestPath gC){
        this.actorConsumer =aC;
        this.movieConsumer =mC;
        this.moviesByActorConsumer = mBAC;
        this.findShortestPath = gC;
    }


    @RequestMapping(value = "path/{actorA}/{actorB}", method = RequestMethod.GET)
    public String getActorsShortestPath(@PathVariable("actorA") String actorA,
                                        @PathVariable("actorB") String actorB) {
        GraphPath<Actor,Movie> a = findShortestPath.shortestPath(actorConsumer.consume(actorA), actorConsumer.consume(actorB));
        String r=a.toString();
        return r;
    }


    @RequestMapping(value = "movies/{imdb}", method = RequestMethod.GET)
    public String getMovie(@PathVariable("imdb")String movieId) {

        return movieConsumer.consume(movieId).toString();
    }
    @RequestMapping(value = "actors/{imdb}", method = RequestMethod.GET)
    public String getActor(@PathVariable("imdb")String actorId) {

        return actorConsumer.consume(actorId).toString();
    }
    @RequestMapping(value = "actors/{imdb}/movies", method = RequestMethod.GET)
    public String getMoviesByActor(@PathVariable("imdb")String actorId) {
       String str = "";
        List<Movie> movies = moviesByActorConsumer.consume(actorId);
        for (Movie m:movies
             ) {
            str+= m.toString();
        }
        return str;
    }

}
