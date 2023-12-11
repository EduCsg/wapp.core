create table EXERCISES_DONE
(
    id             varchar(36) not null,
    workout_id     varchar(36) not null,
    exercise_id    varchar(36) not null,
    exercise_order int         not null,
    reps           int         not null,
    weight         int         not null,

    primary key (id)
);


create table EXERCISES
(
    id           varchar(36)  not null,
    name         varchar(255) not null,
    muscle_group varchar(255) not null,

    primary key (id)
);

create table WORKOUTS
(
    id          varchar(36)  not null,
    user_id     varchar(36)  not null,
    name        varchar(255) not null,
    description varchar(255) not null,
    date        date         not null,
    duration    int          not null,
    start_time  timestamp    not null,
    end_time    timestamp    not null,

    primary key (id)
);
