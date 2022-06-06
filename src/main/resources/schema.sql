-- CREATE TABLE IF NOT EXISTS Mpa
-- (
--     mpa_id int generated by default as identity primary key,
--     name   varchar(10) UNIQUE
-- );


CREATE TABLE IF NOT EXISTS Genres
(
    genre_id int generated by default as identity primary key,
    name     varchar(30) UNIQUE
);


CREATE TABLE IF NOT EXISTS Films
(
    film_id      int generated by default as identity primary key,
    name         varchar(100),
    description  varchar(200),
    release_date date,
    duration     int,
    mpa          varchar(10)
);

create unique index IF NOT EXISTS Films_index
    on Films (film_id);

CREATE TABLE IF NOT EXISTS Films_genre
(
    film_id  int REFERENCES Films (film_id),
    genre_id int REFERENCES Genres (genre_id),
    primary key (film_id, genre_id)
);

CREATE TABLE IF NOT EXISTS Users
(
    user_id  int generated by default as identity primary key,
    login    varchar(100),
    name     varchar(100),
    email    varchar(200) UNIQUE,
    birthday date
);

create unique index IF NOT EXISTS Users_index
    on Users (user_id);

CREATE TABLE IF NOT EXISTS Friendships
(
    user_id      int REFERENCES Users (user_id),
    friend_id    int REFERENCES Users (user_id),
    is_confirmed boolean,
    primary key (user_id, friend_id)
);


CREATE TABLE IF NOT EXISTS Likes
(
    film_id int REFERENCES Films (film_id),
    user_id int REFERENCES Users (user_id),
    primary key (film_id, user_id)
);

create unique index IF NOT EXISTS Likes_index
    on Likes (film_id);
