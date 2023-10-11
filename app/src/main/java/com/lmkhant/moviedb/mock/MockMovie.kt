package com.lmkhant.moviedb.mock

import com.lmkhant.moviedb.domain.model.movie.Movie

val movie1 = Movie(
    adult = false,
    backdropPath = "https://marketplace.canva.com/EAE-HtAkK7o/1/0/1131w/canva-dark-grey-simple-samurai-warrior-action-movies-promotion-poster-0SiR6y7aypo.jpg",
    id = 3,
    originalLanguage = "en",
    originalTitle = "Movie 3",
    overview = "This is the overview of Movie Three.",
    popularity = 67.89,
    posterPath = "https://marketplace.canva.com/EAE-HtAkK7o/1/0/1131w/canva-dark-grey-simple-samurai-warrior-action-movies-promotion-poster-0SiR6y7aypo.jpg",
    releaseDate = "2023-09-10",
    title = "Movie Three, Movie 3 This is the overview of Movie Three.",
    video = true,
    voteAverage = 8.2,
    voteCount = 1200,
    favourite = false,
    type = "upComing",
    index = 1
)

val movies : List<Movie> = listOf(movie1, movie1, movie1, movie1)