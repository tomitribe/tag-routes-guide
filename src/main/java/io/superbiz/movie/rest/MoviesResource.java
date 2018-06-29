/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.superbiz.movie.rest;

import io.superbiz.movie.persistence.Movie;
import io.superbiz.movie.persistence.MoviesBean;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.Collection;
import java.util.List;

@ApplicationScoped
@Path("movies")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MoviesResource {
    @Inject
    private MoviesBean service;

    @PostConstruct
    private void init() {
        service.addMovie(new Movie("The Matrix", "The Wachowski Brothers", "Sci-Fi", 9, 1999));
        service.addMovie(new Movie("John Rambo", "Sylvester Stallone", "Action", 7, 2008));
        service.addMovie(new Movie("Starship Troopers", "Paul Verhoeven", "Sci-Fi", 7, 1997));
        service.addMovie(new Movie("Stargate", "Roland Emmerich", "Sci-Fi", 7, 1994));
    }

    @GET
    @Path("{id}")
    public Movie find(@PathParam("id") final Long id) {
        return service.find(id);
    }

    @GET
    public List<Movie> getMovies(@QueryParam("first") final Integer first,
                                 @DefaultValue("20")
                                 @QueryParam("max") final Integer max,
                                 @QueryParam("field") final String field,
                                 @QueryParam("searchTerm") final String searchTerm) {
        return service.getMovies(first, max, field, searchTerm);
    }

    @GET
    @Path("genres")
    public Collection<String> getGenres() {
        return service.getGenres();
    }

    @POST
    @Consumes("application/json")
    public Movie addMovie(final Movie movie) {
        service.addMovie(movie);
        return movie;
    }

    @PUT
    @Path("{id}")
    @Consumes("application/json")
    public Movie editMovie(final Movie movie) {
        service.editMovie(movie);
        return movie;
    }

    @DELETE
    @Path("{id}")
    public void deleteMovie(@PathParam("id") final long id) {
        service.deleteMovie(id);
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public int count(@QueryParam("field") final String field, @QueryParam("searchTerm") final String searchTerm) {
        return service.count(field, searchTerm);
    }
}
