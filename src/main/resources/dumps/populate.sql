insert into USERS (id, username, password, email, name)
values ('c1b21862-5911-4437-8d5a-1792bfd51801', 'admin',
        '$2a$12$4flzpBUy/jRfjJAHEC944.c947To53uwl11CfwD24baY6HeWgGmde', 'admin@wapp.com', 'admin_name');

insert into USER_METADATA (id, user_id, height, weight, body_fat, gender, age, inserted_at)
VALUES ('d848f795-5a3d-44b8-8e85-3cef10411181', 'c1b21862-5911-4437-8d5a-1792bfd51801', 170, 70.25, 16.5, 'Male', 19,
        '2023-12-31 00:00:00');

insert into ROUTINES (id, user_id, name)
values ('2efc196a-04b9-4f27-b70d-eeb70dcdb139', 'c1b21862-5911-4437-8d5a-1792bfd51801', 'Routine 01');
insert into ROUTINES (id, user_id, name)
values ('6084a1f0-cb3e-40d4-95a6-63e0e34077d4', 'c1b21862-5911-4437-8d5a-1792bfd51801', 'Routine 02');
insert into ROUTINES (id, user_id, name)
values ('35fc7b6a-dc76-418e-bdc8-abb6d8e2cd95', 'c1b21862-5911-4437-8d5a-1792bfd51801', 'Empty Routine');

insert into EXERCISES (id, name, muscle_group)
values ('2739634f-6f48-47cf-9529-bc7cfe55585e', 'exercise_name', 'muscle_group');
insert into EXERCISES (id, name, muscle_group, inserted_by)
values ('52542437-47df-4ccd-a3ab-1e19208bd0ea', 'exercise_by_user', 'muscle_group_2',
        'c1b21862-5911-4437-8d5a-1792bfd51801');

insert into ROUTINES_EXERCISES (id, routine_id, exercise_id, series, exercise_order)
values ('6a4bdd57-6205-4669-a4b7-9b1d28bd7c39', '2efc196a-04b9-4f27-b70d-eeb70dcdb139',
        '2739634f-6f48-47cf-9529-bc7cfe55585e', 1, 1);
insert into ROUTINES_EXERCISES (id, routine_id, exercise_id, series, exercise_order)
values ('b0b6b6e9-9b0e-4b9e-9b0a-9b0b9b0c9b0d', '2efc196a-04b9-4f27-b70d-eeb70dcdb139',
        '52542437-47df-4ccd-a3ab-1e19208bd0ea', 2, 2);
insert into ROUTINES_EXERCISES (id, routine_id, exercise_id, series, exercise_order)
values ('3e1d801e-5964-47b1-a9d0-dc644ff1d1e8', '6084a1f0-cb3e-40d4-95a6-63e0e34077d4',
        '52542437-47df-4ccd-a3ab-1e19208bd0ea', 3, 1);
insert into ROUTINES_EXERCISES (id, routine_id, exercise_id, series, exercise_order)
values ('cc2b9174-cce8-4130-901d-9875b14b937a', '6084a1f0-cb3e-40d4-95a6-63e0e34077d4',
        '2739634f-6f48-47cf-9529-bc7cfe55585e', 4, 2);

insert into WORKOUTS (id, user_id, name, description, duration, start_datetime, end_datetime)
values ('bcaf10c5-56f5-41d9-aae6-4ea2e15734e0', 'c1b21862-5911-4437-8d5a-1792bfd51801', 'workout_name',
        'workout_description', 60, '2024-12-31 00:00:00', '2024-12-31 23:59:59');

insert into EXERCISES_DONE (id, workout_id, user_id, exercise_id, exercise_order, description)
values ('c0e7a1b5-b328-499d-ba2f-52b372c57ecc', 'bcaf10c5-56f5-41d9-aae6-4ea2e15734e0',
        'c1b21862-5911-4437-8d5a-1792bfd51801', '2739634f-6f48-47cf-9529-bc7cfe55585e', 1, 'descricao 1 exercicio');
insert into EXERCISES_DONE (id, workout_id, user_id, exercise_id, exercise_order, description)
values ('f9315f51-55f4-4b5a-a2f9-4b217c67c38b', 'bcaf10c5-56f5-41d9-aae6-4ea2e15734e0',
        'c1b21862-5911-4437-8d5a-1792bfd51801', '52542437-47df-4ccd-a3ab-1e19208bd0ea', 2, 'descricao 2 exercicio');

insert into EXERCISES_SERIES (id, exercise_done_id, repetitions, weight, series_order, description)
values ('0ddaee16-dafe-4eba-b057-3e6b463a0425', 'c0e7a1b5-b328-499d-ba2f-52b372c57ecc', 10, 10, 1, 'descricao 1 serie');
insert into EXERCISES_SERIES (id, exercise_done_id, repetitions, weight, series_order, description)
values ('e5cc7c71-aba1-4db7-81ae-00eaccf3c6e9', 'c0e7a1b5-b328-499d-ba2f-52b372c57ecc', 5, 6, 3, 'descricao 2 serie');
insert into EXERCISES_SERIES (id, exercise_done_id, repetitions, weight, series_order, description)
values ('8db67660-fbb7-41bd-a52b-338940d1ab2a', 'f9315f51-55f4-4b5a-a2f9-4b217c67c38b', 10, 10, 1, 'descricao 3 serie');
insert into EXERCISES_SERIES (id, exercise_done_id, repetitions, weight, series_order, description)
values ('bec8900a-0e3c-4cc5-b1b2-4eb56c70be54', 'f9315f51-55f4-4b5a-a2f9-4b217c67c38b', 5, 6, 3, 'descricao 4 serie');
