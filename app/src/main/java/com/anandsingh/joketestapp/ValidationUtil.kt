package com.anandsingh.joketestapp

object ValidationUtil {

    fun validateMovie(movie: Joke) : Boolean {
        if (movie.joke.isNotEmpty() ) {
            return true
        }
        return false
    }
}