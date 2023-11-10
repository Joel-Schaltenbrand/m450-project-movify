package ch.bbzbl.movify.service;

import ch.bbzbl.movify.constants.SessionConstants;
import ch.bbzbl.movify.dao.AbstractWebClient;
import ch.bbzbl.movify.factory.MovieFactory;
import ch.bbzbl.movify.model.movie.cast.CastModified;
import ch.bbzbl.movify.model.movie.cast.CastResult;
import ch.bbzbl.movify.model.movie.movie.Movie;
import ch.bbzbl.movify.model.movie.movie.MovieExtended;
import ch.bbzbl.movify.model.movie.movie.TopRatedMovie;
import ch.bbzbl.movify.model.movie.movie.TopRatedMovieResult;
import ch.bbzbl.movify.model.movie.trailer.TrailerResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl extends AbstractWebClient implements MovieService {

    private static final Gson GSON = new GsonBuilder().create();
    private final MultiValueMap<String, String> paramsDefault = new LinkedMultiValueMap<>();
    @Autowired
    private MovieFactory movieFactory;

    @Value("${moviedb.base.url}")
    private String baseUrl;

    @Autowired
    private HttpSession session;

    public MovieServiceImpl(@Value("${moviedb.apikey}") String apiKey) {
        paramsDefault.add("language", "de");
        paramsDefault.add("api_key", apiKey);
    }

    @Override
    public MovieExtended getRandomMovie() {
        Optional<Long> id;
        do {
            id = getMovieID();
        } while (id.isEmpty());
        return getMovieDetails(id.get());
    }

    @Override
    public MovieExtended getMovieDetails(long id) {
        return doGet(String.format("movie/%s", id), paramsDefault)
                .map(response -> {
                    Movie movie = GSON.fromJson(response, Movie.class);
                    return movieFactory.createMovieExtended(movie, getCast(movie.getId()), getMovieTrailer(movie.getId()));
                })
                .orElse(new MovieExtended());
    }

    @Override
    public List<MovieExtended> searchMovie(String search) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.addAll(paramsDefault);
        params.add("include_adult", "false");
        params.add("query", search);
        Optional<String> response = doGet("search/movie", params);
        if (response.isEmpty()) {
            return new ArrayList<>();
        }
        TopRatedMovieResult movieResult = GSON.fromJson(response.get(), TopRatedMovieResult.class);
        List<MovieExtended> list = new ArrayList<>();
        movieResult.getResults().stream().filter(movie -> !movie.getOverview().isEmpty())
                .forEach(movie -> list.add(getMovieDetails(movie.getId())));
        return list;
    }

    public Optional<Long> getMovieID() {
        List<TopRatedMovie> movies = checkSession();
        if (movies.size() <= 2) {
            movies.addAll(fetchTopRatedMovies());
        }
        TopRatedMovie chosenMovie = movies.get((int) (Math.random() * movies.size()));
        movies.remove(chosenMovie);
        session.setAttribute(SessionConstants.LOADED_IDS, movies);
        return Optional.of(chosenMovie.getId());
    }

    private List<TopRatedMovie> checkSession() {
        List<TopRatedMovie> movies = (List<TopRatedMovie>) session.getAttribute(SessionConstants.LOADED_IDS);
        if (movies == null || movies.size() == 0) {
            return new ArrayList<>();
        }
        return movies;
    }

    private List<TopRatedMovie> fetchTopRatedMovies() {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.addAll(paramsDefault);
        params.add("page", String.valueOf(getRandomPage()));
        params.add("include_adult", "false");
        Optional<String> response = doGet("movie/top_rated", params);
        if (response.isEmpty()) {
            return new ArrayList<>();
        }
        TopRatedMovieResult movieResult = GSON.fromJson(response.get(), TopRatedMovieResult.class);
        return movieResult.getResults().stream().filter(movie -> !movie.getOverview().isEmpty())
                .collect(Collectors.toList());
    }

    private int getRandomPage() {
        List<Integer> pages = (List<Integer>) session.getAttribute(SessionConstants.PAGE);
        if (pages == null) {
            pages = new ArrayList<>();
        }
        int page;
        do {
            page = (int) (Math.random() * 500 + 1);
        } while (pages.contains(page));
        pages.add(page);
        session.setAttribute(SessionConstants.PAGE, pages);
        return page;
    }

    private Optional<String> getMovieTrailer(Long id) {
        String endpoint = String.format("movie/%s/videos", id);
        return doGet(endpoint, paramsDefault)
                .map(response -> GSON.fromJson(response, TrailerResult.class).getResults())
                .filter(trailers -> !trailers.isEmpty())
                .map(trailers -> trailers.get(0).getKey())
                .or(Optional::empty);
    }

    private List<CastModified> getCast(long id) {
        Optional<String> responseAsString = doGet(String.format("movie/%s/credits", id), paramsDefault);
        return responseAsString.map(response -> {
            CastResult result = GSON.fromJson(response, CastResult.class);
            return movieFactory.createCastExtended(result.getCast().subList(0, Math.min(5, result.getCast().size())));
        }).orElse(Collections.emptyList());
    }

    @Override
    protected String baseUrl() {
        return baseUrl;
    }
}
