insert into app_user (id, login, password, email)
values
(10,'UnauthorizedUser', '$2a$12$o3dvKAd7EhxzJaLWwPdTuuh3//C8EYyXo5X7UEDCKw9by6l1gBXQu', 'UnauthorizedUser@UnauthorizedUser.com');

insert into video_comment(app_user_id, video_metadata_id, comment, dt )
values
(1, 1, 'Фильм 1 очень и очень хорош!', '2021-08-18 20:26:13.055553+03'),
(1, 2, 'Фильм 2 очень и очень хорош!', '2021-08-18 20:26:13.055553+03'),
(2, 1, 'Фильм 1 очень и очень плох!', '2021-08-18 20:26:13.055553+03'),
(2, 2, 'Фильм 2 очень и очень плох!', '2021-08-18 20:26:13.055553+03');