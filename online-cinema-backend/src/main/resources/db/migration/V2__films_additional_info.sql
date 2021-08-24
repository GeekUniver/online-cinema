/*Дата съемки фильма*/
ALTER TABLE video_metadata ADD COLUMN year_filmed integer null;

/*Наименование фильма*/
ALTER TABLE video_metadata ADD COLUMN name varchar(255) not null default 'unnamed';

/*Бан пользователя*/
ALTER TABLE app_user ADD COLUMN banned smallint null ;


/*Классификатор людей, работающих над фильмом*/
CREATE TABLE crew_classifier(
     id bigserial primary key,
     first_name varchar(255) not null,
     last_name varchar(255) null,
     patronymic varchar(255) null,
     birthday timestamp with time zone null
);


/*Классификатор ролей людей, работающих над фильмом*/
CREATE TABLE crew_role_classifier(
    id bigserial primary key,
    name varchar(255) not null
);


/*Люди, работающие над фильмом*/
CREATE TABLE video_metadata_crew(
    id bigserial primary key,
    video_metadata_id bigint not null references video_metadata(id),
    crew_classifier_id  bigint not null references crew_classifier(id),
    crew_role_classifier_id bigint not null references crew_role_classifier(id)
);

/*Страны производства фильмов*/
CREATE TABLE country_classifier(
    id bigserial primary key,
    name varchar(255) not null
);

/*Привязка фильмов и стран производства*/
CREATE TABLE video_metadata_country(
    video_metadata_id bigint not null references video_metadata(id),
    country_classifier_id  bigint not null references country_classifier(id)
);
