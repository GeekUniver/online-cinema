insert into VIDEO_METADATA(
    id,
    file_name,
    content_type,
    description,
    file_size,
    video_length
)
values
(1,'bumerang.mp4',	'video/mp4',	'bumerang',	8071691,	136185000),
(2,	'duny.mp4',	'video/mp4',	'дюны',	10263710,	221634000);

insert into app_role (name)
values
('ROLE_USER'),
('ROLE_ADMIN');

insert into app_user (login, password, email)
values
('bob', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'bob_johnson@gmail.com'),
('john', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'john_johnson@gmail.com'),
('Johny', '$2a$12$o3dvKAd7EhxzJaLWwPdTuuh3//C8EYyXo5X7UEDCKw9by6l1gBXQu', 'johny_johnsony@gmail.com');

insert into app_user_role (app_user_id, app_role_id)
values
(1, 1),
(2, 2),
(3, 2);