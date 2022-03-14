-- 1
SELECT imdb.id as imdb_id, tomatoes.viewer.rating as tomatoes_rating, imdb.rating as imdb_rating
FROM `mflix-sample`._default.`movies`
WHERE tomatoes.viewer.rating  <> 0
AND ABS(imdb.rating - tomatoes.viewer.rating) > 7;

-- 2
SELECT title 
FROM `mflix-sample`._default.`movies`
WHERE tomatoes.critic.meter = 100
AND tomatoes.viewer.rating IS NOT VALUED

-- 3
SELECT name, count(*) as cnt
FROM `mflix-sample`._default.`comments`
GROUP BY name
ORDER BY cnt DESC
LIMIT 10

-- 4
SELECT RAW name
FROM `mflix-sample`._default.`comments`
GROUP BY name
HAVING count(*) > 300

-- 5
SELECT imdb.id as imdb_id, imdb.rating as imdb_rating, `cast`
FROM `mflix-sample`._default.`movies`
WHERE imdb.rating > 8
AND "Ralph Fiennes" IN `cast`

-- 6
SELECT directors, COUNT(directors) AS count_films
FROM `mflix-sample`.`_default`.`movies`
UNNEST directors
GROUP BY directors
HAVING COUNT(directors) > 30
ORDER BY count_films DESC

-- 7
SELECT _id, title
FROM `mflix-sample`._default.`movies`
WHERE ARRAY_COUNT(directors) > 20

-- 8.1 (with "Woody Allen" as an example)
-- indexes are needed for this solution
CREATE INDEX def_movies_id ON `default`:`mflix-sample`.`_default`.`movies`(`_id`)
CREATE INDEX adv_movie_id ON `default`:`mflix-sample`.`_default`.`comments`(`movie_id`) 

SELECT RAW comments.text
FROM `mflix-sample`.`_default`.`movies`
INNER JOIN `mflix-sample`.`_default`.`comments` ON comments.movie_id = movies._id
WHERE "Woody Allen" IN movies.directors

-- 8.2 (with "Woody Allen" as an example)
SELECT RAW text
FROM `mflix-sample`.`_default`.`comments`
WHERE comments.movie_id IN (
    SELECT RAW _id
    FROM `mflix-sample`.`_default`.`movies`
    WHERE "Woody Allen" IN directors)

-- 9
UPDATE `mflix-sample`._default.theaters AS t
SET t.schedule = ARRAY s FOR s IN t.schedule WHEN s.hourBegin >= "18:00:00"
    OR s.movieId != movieId END;

-- 10
SELECT m._id as movie_id, m.title
FROM `mflix-sample`.`_default`.`movies` m
WHERE m._id IN (
    SELECT DISTINCT RAW schedule.movieId
    FROM `mflix-sample`._default.`theaters`
    UNNEST schedule)
AND m._id NOT IN (
    SELECT DISTINCT RAW schedule.movieId
    FROM `mflix-sample`._default.`theaters`
    UNNEST schedule
    WHERE schedule.hourBegin < "18:00:00")
