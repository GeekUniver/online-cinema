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
                         password varchar(60) not null
);

INSERT INTO app_user(email, login, password) VALUES
            ('u@u', 'user', '$2y$12$gRaobCsBpzWbJG.4PLIxG.MY5y7mGY6a6oZjVahDGChjKpLoUA/cK'),
            ('u1@u1', 'user1', '$2a$12$VRzM4mdk0I6mxrRWv2eojOi.EHh1SCl703dCx5Fqa2Sk6yjI8XT8O'),
            ('a@a', 'admin', '$2a$12$kDMxlytrBLsVXBAjS1vPOOKc0b6lu38y6a03gFUqS8JebDQmmAtbC');  /*$2y$12$a43MrZhiHGJrPb0XgNjkBeOtzFfVpeONilQxDMzq5hFteekOiM9dy*/

/*Пользователи системы. Префикс app нужен для совместимости с СУБД, где role зарезервировал*/
CREATE TABLE app_role(
                         id bigserial primary key,
                         name varchar(255) not null
);

INSERT INTO app_role(name) VALUES
            ('ROLE_CLIENT'),
            ('ROLE_ADMIN');

/*Роли пользователей, таблица соответствия*/
CREATE TABLE app_user_role(
                              app_user_id bigint references app_user(id),
                              app_role_id bigint references app_role(id)
);

INSERT INTO app_user_role(app_user_id, app_role_id) VALUES
            (1, 1),
            (2, 2);

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
                              dt timestamp with time zone default current_timestamp,
    deleted smallint default 0);

/*Оценки пользователей к видео
  Ссылка на пользователя и видео - обязательный атрибуты
  dt - дата добавления оценки, по умолчанию текущая дата с сервера
 */
CREATE TABLE video_score(
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


