package com.uees;


public class Movie {
        String actor;
        String actorID;
        String film;
        int year;
        int votes;
        double rating;
        String filmID;
    
        public Movie(String actor, String actorID, String film, int year, int votes, double rating, String filmID) {
            this.actor = actor;
            this.actorID = actorID;
            this.film = film;
            this.year = year;
            this.votes = votes;
            this.rating = rating;
            this.filmID = filmID;
        }
    
        @Override
        public String toString() {
            return "Movie{" +
                    "actor='" + actor + '\'' +
                    ", actorID='" + actorID + '\'' +
                    ", film='" + film + '\'' +
                    ", year=" + year +
                    ", votes=" + votes +
                    ", rating=" + rating +
                    ", filmID='" + filmID + '\'' +
                    '}';
        }
    
    
        public String getActor() {
            return actor;
        }
        public String getFilm() {
            return film;
        }
        public String getActorID() {
            return actorID;
        }
        public String getFilmID() {
            return filmID;
        }
        public double getRating() {
            return rating;
        }
        public int getVotes() {
            return votes;
        }
        public int getYear() {
            return year;
        }
    }
