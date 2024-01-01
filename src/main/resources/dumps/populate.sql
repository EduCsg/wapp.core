insert into USERS (id, username, password, email)
values ('c1b21862-5911-4437-8d5a-1792bfd51801', 'admin_username', 'admin_pass', 'admin@test.com');

insert into ROUTINES (id, user_id, name)
values ('2efc196a-04b9-4f27-b70d-eeb70dcdb139', 'c1b21862-5911-4437-8d5a-1792bfd51801', 'routine_name');

insert into EXERCISES (id, name, muscle_group)
values ('2739634f-6f48-47cf-9529-bc7cfe55585e', 'exercise_name', 'muscle_group');
insert into EXERCISES (id, name, muscle_group)
values ('52542437-47df-4ccd-a3ab-1e19208bd0ea', 'exercise_2', 'muscle_group_2');

insert into ROUTINES_EXERCISES (id, routine_id, exercise_id, series)
values ('6a4bdd57-6205-4669-a4b7-9b1d28bd7c39', '2efc196a-04b9-4f27-b70d-eeb70dcdb139',
        '2739634f-6f48-47cf-9529-bc7cfe55585e', 3);
insert into ROUTINES_EXERCISES (id, routine_id, exercise_id, series)
values ('b0b6b6e9-9b0e-4b9e-9b0a-9b0b9b0c9b0d', '2efc196a-04b9-4f27-b70d-eeb70dcdb139',
        '52542437-47df-4ccd-a3ab-1e19208bd0ea', 4);

insert into WORKOUTS (id, user_id, name, description, date, duration, start_time, end_time)
values ('bcaf10c5-56f5-41d9-aae6-4ea2e15734e0', 'c1b21862-5911-4437-8d5a-1792bfd51801', 'workout_name',
        'workout_description', '2023-12-31', 60, '10:00:00', '11:00:00');

insert into EXERCISES_DONE (id, workout_id, user_id, exercise_id, exercise_order)
values ('c0e7a1b5-b328-499d-ba2f-52b372c57ecc', 'bcaf10c5-56f5-41d9-aae6-4ea2e15734e0',
        'c1b21862-5911-4437-8d5a-1792bfd51801', '2739634f-6f48-47cf-9529-bc7cfe55585e', 1);
insert into EXERCISES_DONE (id, workout_id, user_id, exercise_id, exercise_order)
values ('f9315f51-55f4-4b5a-a2f9-4b217c67c38b', 'bcaf10c5-56f5-41d9-aae6-4ea2e15734e0',
        'c1b21862-5911-4437-8d5a-1792bfd51801', '52542437-47df-4ccd-a3ab-1e19208bd0ea', 2);

insert into EXERCISES_SERIES (id, exercise_done_id, repetitions, weight, series_order)
values ('0ddaee16-dafe-4eba-b057-3e6b463a0425', 'c0e7a1b5-b328-499d-ba2f-52b372c57ecc', 10, 10, 1);
insert into EXERCISES_SERIES (id, exercise_done_id, repetitions, weight, series_order)
values ('e5cc7c71-aba1-4db7-81ae-00eaccf3c6e9', 'c0e7a1b5-b328-499d-ba2f-52b372c57ecc', 5, 6, 3);
insert into EXERCISES_SERIES (id, exercise_done_id, repetitions, weight, series_order)
values ('8db67660-fbb7-41bd-a52b-338940d1ab2a', 'f9315f51-55f4-4b5a-a2f9-4b217c67c38b', 10, 10, 1);
insert into EXERCISES_SERIES (id, exercise_done_id, repetitions, weight, series_order)
values ('bec8900a-0e3c-4cc5-b1b2-4eb56c70be54', 'f9315f51-55f4-4b5a-a2f9-4b217c67c38b', 5, 6, 3);
