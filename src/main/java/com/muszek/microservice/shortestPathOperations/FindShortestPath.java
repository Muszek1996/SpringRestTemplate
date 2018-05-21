package com.muszek.microservice.shortestPathOperations;
import com.muszek.microservice.fieldsOfIMDB.Actor;
import com.muszek.microservice.fieldsOfIMDB.Movie;
import com.muszek.microservice.restConsumerIMDB.MovieConsumer;
import com.muszek.microservice.restConsumerIMDB.MoviesByActorConsumer;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.BellmanFordShortestPath;
import org.jgrapht.graph.SimpleGraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FindShortestPath {
    private final MoviesByActorConsumer moviesByActorConsumer;
    private final MovieConsumer movieConsumer;
    private List<Movie> movies;
    private List<Movie> aMovies;
    private Set<Actor> actorsTmp;
    private Set<Actor> actorsToHandle;

    @Autowired
    public FindShortestPath(MoviesByActorConsumer moviesByActorConsumer,MovieConsumer movieConsumer) {
        this.moviesByActorConsumer = moviesByActorConsumer;
        this.movieConsumer = movieConsumer;
        actorsTmp = new TreeSet<>();
        actorsToHandle = new TreeSet<>();
        aMovies = new LinkedList<>();
        movies = new LinkedList<>();
    }

    public GraphPath<Actor,Movie> shortestPath(Actor actorA,Actor actorB){
        Graph<Actor, Movie> g = new SimpleGraph<>(Movie.class);
        g.addVertex(actorA);
        g.addVertex(actorB);
        actorsToHandle.add(actorA);
        actorsToHandle.add(actorB);

        boolean pathFound = false;
        int distance = 6;

        while(!pathFound&&--distance>0){
            System.out.println(distance);
            actorsToHandle.forEach((a)->{
                movies.addAll(moviesByActorConsumer.consume(a.getId()));
                movies.forEach((m)->{
                    List<Actor> actors = Arrays.asList(movieConsumer.consume(m.getId()).getActors());
                    actors.forEach((act)->{
                        actorsTmp.add(act);
                    });
                    actorsTmp.forEach((atmp)->{
                        if(!a.equals(atmp)){
                            if(g.addVertex(atmp)){
                                g.addEdge(a,atmp,m);
                            }
                        }
                    });
                });

            });
            actorsToHandle.clear();
            actorsToHandle.addAll(actorsTmp);
            actorsTmp.clear();

            GraphPath<Actor,Movie> path = BellmanFordAlgorithm.shortestPath(g,actorA,actorB);
            if(path!=null){
                pathFound = true;
                return path;
            }
        }



        return null;
    }


}
