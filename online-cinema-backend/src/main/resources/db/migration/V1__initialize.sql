CREATE TABLE video_metadata(
                               id bigserial primary key,
                               file_name varchar(255) not null,
                               content_type varchar(20) not null,
                               description varchar(10240),
                               file_size bigint not null,
                               video_length bigint null
);


/*Пользователи системы. Префикс app нужен для совместимости с СУБД, где user зарезервировал*/
CREATE TABLE app_user(
                         id bigserial primary key,
                         email varchar(255) not null,
                         login varchar(255) not null,
                         password varchar(255) not null
);

/*Пользователи системы. Префикс app нужен для совместимости с СУБД, где role зарезервировал*/
CREATE TABLE app_role(
                         id bigserial primary key,
                         name varchar(255) not null
);

/*Роли пользователей, таблица соответствия*/
CREATE TABLE app_user_role(
                              app_user_id bigint references app_user(id),
                              app_role_id bigint references app_role(id)
);

/*Комментарии пользователей к видео
  Ссылка на пользователя и видео - обязательные атрибуты
  Ссылка на родительский комментарий - не обязательный (если пользователь ответил другому пользователю)
  dt - дата добавления комментария, по умолчанию текущая дата с сервера
  Признак удаления нужен для сохранения иерархической структуры комментов, при удалении коммента из середины ветки
 */
CREATE TABLE video_comment(
                              id bigserial primary key,
                              app_user_id bigint not null references app_user(id),
                              video_metadata_id bigint not null references video_metadata(id),
                              user_comment_id bigint null references video_comment(id),
                              comment varchar(10240) not null,
                              dt timestamp with time zone not null default current_timestamp,
    deleted smallint default 0 not null
);

/*Оценки пользователей к видео
  Ссылка на пользователя и видео - обязательный атрибуты
  dt - дата добавления оценки, по умолчанию текущая дата с сервера
 */
CREATE TABLE video_scores(
                             id bigserial primary key,
                             app_user_id bigint not null references app_user(id),
                             video_metadata_id bigint not null references video_metadata(id),
                             score smallint not null,
                             dt timestamp with time zone not null default current_timestamp
 );


/*Классификатор жанров фильмов*/
CREATE TABLE genre_classifier(
                                 id bigserial primary key,
                                 name varchar(255) not null
);

/*Жанры фильмов, таблица соответствия*/
CREATE TABLE video_metadata_genre(
                                     video_metadata_id bigint not null references video_metadata(id),
                                     genre_classifier_id  bigint not null references genre_classifier(id)
);


