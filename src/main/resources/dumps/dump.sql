CREATE TABLE USERS
(
    id       varchar(36)  NOT NULL,
    username varchar(50)  NOT NULL UNIQUE,
    name     varchar(50)  NOT NULL,
    password varchar(255) NOT NULL,
    email    varchar(128) NOT NULL UNIQUE,

    PRIMARY KEY (id)
);

CREATE TABLE USER_METADATA
(
    id          varchar(36) NOT NULL,
    user_id     varchar(36) NOT NULL,
    height      int         NOT NULL,
    weight      int         NOT NULL,
    body_fat    int         NOT NULL,
    gender      varchar(50) NOT NULL,
    age         int         NOT NULL,
    inserted_at date        NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES USERS (id)
);

CREATE TABLE ROUTINES
(
    id      varchar(36) NOT NULL,
    user_id varchar(36) NOT NULL,
    name    varchar(50) NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES USERS (id)
);

CREATE TABLE EXERCISES
(
    id           varchar(36)  NOT NULL,
    name         varchar(50)  NOT NULL,
    muscle_group varchar(128) NOT NULL,
    inserted_by  varchar(36),

    PRIMARY KEY (id)
);


CREATE TABLE ROUTINES_EXERCISES
(
    id             varchar(36) NOT NULL,
    routine_id     varchar(36) NOT NULL,
    exercise_id    varchar(36) NOT NULL,
    exercise_order int         NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (routine_id) REFERENCES ROUTINES (id),
    FOREIGN KEY (exercise_id) REFERENCES EXERCISES (id)
);


CREATE TABLE WORKOUTS
(
    id          varchar(36)  NOT NULL,
    user_id     varchar(36)  NOT NULL,
    name        varchar(50)  NOT NULL,
    description varchar(255) NOT NULL,
    date        date         NOT NULL,
    duration    int          NOT NULL,
    start_time  time         NOT NULL,
    end_time    time         NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES USERS (id)
);


CREATE TABLE EXERCISES_DONE
(
    id             varchar(36) NOT NULL,
    workout_id     varchar(36) NOT NULL,
    user_id        varchar(36) NOT NULL,
    exercise_id    varchar(36) NOT NULL,
    exercise_order int         NOT NULL,
    description    varchar(255),

    PRIMARY KEY (id),
    FOREIGN KEY (workout_id) REFERENCES WORKOUTS (id),
    FOREIGN KEY (user_id) REFERENCES USERS (id),
    FOREIGN KEY (exercise_id) REFERENCES EXERCISES (id)
);

CREATE TABLE EXERCISES_SERIES
(
    id               varchar(36) NOT NULL,
    exercise_done_id varchar(36) NOT NULL,
    repetitions      int         NOT NULL,
    weight           int         NOT NULL,
    series_order     int         NOT NULL,
    description      varchar(255),

    PRIMARY KEY (id),
    FOREIGN KEY (exercise_done_id) REFERENCES EXERCISES_DONE (id)
);
