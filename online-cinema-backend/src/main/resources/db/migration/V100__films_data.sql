insert into app_user(login, password, email)
values('admin', '123', 'admin@yandex.ru');

insert into app_role(name)
values ('admin');

insert into app_user_role(
    app_user_id,
    app_role_id)
values
    (1,1);


/*Жанры фильмов*/
insert into genre_classifier(
    name)
values
    ('Фэнтези'),
    ('Семейный'),
    ('Приключения'),
    ('Драма'),
    ('Комедия');

/*Сьёмочная группа*/
insert into crew_classifier(
    id,
    first_name,
    last_name,
    patronymic,
    birthday)
values
    (1, 'Дэниел', 'Рэдклифф', null, '1989-07-23'),
    (2, 'Руперт', 'Гринт', null, '1988-08-24'),
    (3, 'Эмма', 'Уотсон', null, '1990-04-15'),
    (4, 'Ричард', 'Харрис', null, '1930-10-01'),
    (5, 'Алан', 'Рикман', null, '1946-02-21'),
    (6, 'Гарри', 'Олдман', null, '1958-03-21'),
    (7, 'Хелена', 'Бонем Картер', null, '1966-05-26'),

    (8, 'Крис', 'Коламбус', null, '1958-09-10'),
    (9, 'Альфонсо', 'Куарон', null, '1958-09-10'),
    (10, 'Майк', 'Ньюэлл', null, '1942-03-28'),
    (11, 'Дэвид', 'Йейтс', null, '1963-10-08'),

    (12, 'Бен', 'Аффлек', null, '1979-08-15'),
    (13, 'Мэтт', 'Дэймон', null, '1970-10-08'),
    (14, 'Сальма', 'Хайек', null, '1966-09-02'),
    (15, 'Джейсон', 'Мьюз', null, '1974-06-12'),
    (16, 'Кевин', 'Смит', null, '1970-08-02');


/*Классификатор ролей съемочной группы*/
insert into crew_role_classifier(
    id,
    name)
values
    (1, 'Актер'),
    (2, 'Режиссер');


/*Классификатор стран производства*/
insert into country_classifier(
    id,
    name)
values
    (1,'Великобритания'),
    (2,'США');



/*Обновляем данные о фильмах (если какие-то файлы есть в БД, чтобы отображать данные)*/
update VIDEO_METADATA
set
    name = 'Гарри Поттер и философский камень',
    year_filmed = 2001,
    description = 'Гарри поступает в школу магии Хогвартс и заводит друзей. Первая часть большой франшизы о маленьком волшебнике'
where
        id = 1;

update VIDEO_METADATA
set
    name = 'Гарри Поттер и Тайная комната',
    year_filmed = 2002,
    description = 'Домашний эльф, магический дневник и привидение. Второй год Гарри в школе Хогвартс — еще более захватывающий'
where
        id = 2;

update VIDEO_METADATA
set
    name = 'Гарри Поттер и узник Азкабана',
    year_filmed = 2004,
    description = 'Беглый маг, тайны прошлого и путешествия во времени. В третьей части поттерианы Альфонсо Куарон сгущает краски'
where
        id = 3;

update VIDEO_METADATA
set
    name = 'Гарри Поттер и Кубок огня',
    year_filmed = 2005,
    description = 'Гарри участвует в смертельном Турнире Трех Волшебников. Первая по-настоящему взрослая часть великой франшизы'
where
        id = 4;

update VIDEO_METADATA
set
    name = 'Гарри Поттер и Орден Феникса',
    year_filmed = 2007,
    description = 'Из-за диктатуры в школе Гарри тайно учит друзей защищаться от темных сил. Любимая часть Дэниэла Рэдклиффа'
where
        id = 5;

update VIDEO_METADATA
set
    name = 'Гарри Поттер и Принц-полукровка',
    year_filmed = 2009,
    description = 'Гарри помогает Дамблдору раскрыть главную тайну Волан-де-Морта. Шестая часть с душераздирающим финалом'
where
        id = 6;

update VIDEO_METADATA
set
    name = 'Гарри Поттер и Дары Смерти: Часть I',
    year_filmed = 2010,
    description = 'Друзья бросают школу, чтобы найти частицы души Темного Лорда. Мощное роуд-муви о верности идеалам'
where
        id = 7;

update VIDEO_METADATA
set
    name = 'Гарри Поттер и Дары Смерти: Часть II',
    year_filmed = 2011,
    description = 'В мире магии идет война, последнее сражение Гарри и Волан-де-Морта неизбежно приближается. Финал поттерианы'
where
        id = 8;

update VIDEO_METADATA
set
    name = 'Догма',
    year_filmed = 1999,
    description = 'Два падших ангела Локи и Бартлби, обречённые вечно торчать в штате Висконсин, узнают, что у них есть шанс вернуться в рай. Для этого нужно всего лишь воспользоваться католической догмой, провозглашенной кардиналом-новатором: каждый прошедший через освященную арку в соборе в Нью-Джерси получает отпущение грехов. '
where
        id = 9;

/*Добавление виртуальных фильмов*/
insert into VIDEO_METADATA(
    id,
    file_name,
    name,
    year_filmed,
    content_type,
    description,
    file_size,
    video_length)
select
    1,
    'file1.mp4',
    'Гарри Поттер и философский камень',
    2001,
    'video/mp4',
    'Гарри поступает в школу магии Хогвартс и заводит друзей. Первая часть большой франшизы о маленьком волшебнике',
    1,
    1
where
    not exists(select 1 from VIDEO_METADATA v where v.id = 1);


insert into VIDEO_METADATA(
    id,
    file_name,
    name,
    year_filmed,
    content_type,
    description,
    file_size,
    video_length)
select
    2,
    'file1.mp4',
    'Гарри Поттер и Тайная комната',
    2002,
    'video/mp4',
    'Домашний эльф, магический дневник и привидение. Второй год Гарри в школе Хогвартс — еще более захватывающий',
    1,
    1
where
    not exists(select 1 from VIDEO_METADATA v where v.id = 2);


insert into VIDEO_METADATA(
    id,
    file_name,
    name,
    year_filmed,
    content_type,
    description,
    file_size,
    video_length)
select
    3,
    'file1.mp4',
    'Гарри Поттер и узник Азкабана',
    2004,
    'video/mp4',
    'Беглый маг, тайны прошлого и путешествия во времени. В третьей части поттерианы Альфонсо Куарон сгущает краски',
    1,
    1
where
    not exists(select 1 from VIDEO_METADATA v where v.id = 3);


insert into VIDEO_METADATA(
    id,
    file_name,
    name,
    year_filmed,
    content_type,
    description,
    file_size,
    video_length)
select
    4,
    'file1.mp4',
    'Гарри Поттер и Кубок огня',
    2005,
    'video/mp4',
    'Беглый маг, Гарри участвует в смертельном Турнире Трех Волшебников. Первая по-настоящему взрослая часть великой франшизы',
    1,
    1
where
    not exists(select 1 from VIDEO_METADATA v where v.id = 4);


insert into VIDEO_METADATA(
    id,
    file_name,
    name,
    year_filmed,
    content_type,
    description,
    file_size,
    video_length)
select
    5,
    'file1.mp4',
    'Гарри Поттер и Орден Феникса',
    2007,
    'video/mp4',
    'Из-за диктатуры в школе Гарри тайно учит друзей защищаться от темных сил. Любимая часть Дэниэла Рэдклиффа',
    1,
    1
where
    not exists(select 1 from VIDEO_METADATA v where v.id = 5);


insert into VIDEO_METADATA(
    id,
    file_name,
    name,
    year_filmed,
    content_type,
    description,
    file_size,
    video_length)
select
    6,
    'file1.mp4',
    'Гарри Поттер и Принц-полукровка',
    2009,
    'video/mp4',
    'Гарри помогает Дамблдору раскрыть главную тайну Волан-де-Морта. Шестая часть с душераздирающим финалом',
    1,
    1
where
    not exists(select 1 from VIDEO_METADATA v where v.id = 6);


insert into VIDEO_METADATA(
    id,
    file_name,
    name,
    year_filmed,
    content_type,
    description,
    file_size,
    video_length)
select
    7,
    'file1.mp4',
    'Гарри Поттер и Дары Смерти: Часть I',
    2010,
    'video/mp4',
    'Друзья бросают школу, чтобы найти частицы души Темного Лорда. Мощное роуд-муви о верности идеалам',
    1,
    1
where
    not exists(select 1 from VIDEO_METADATA v where v.id = 7);


insert into VIDEO_METADATA(
    id,
    file_name,
    name,
    year_filmed,
    content_type,
    description,
    file_size,
    video_length)
select
    8,
    'file1.mp4',
    'Гарри Поттер и Дары Смерти: Часть II',
    2011,
    'video/mp4',
    'В мире магии идет война, последнее сражение Гарри и Волан-де-Морта неизбежно приближается. Финал поттерианы',
    1,
    1
where
    not exists(select 1 from VIDEO_METADATA v where v.id = 8);

insert into VIDEO_METADATA(
    id,
    file_name,
    name,
    year_filmed,
    content_type,
    description,
    file_size,
    video_length)
select
    9,
    'file1.mp4',
    'Догма',
    1999,
    'video/mp4',
    'Два падших ангела Локи и Бартлби, обречённые вечно торчать в штате Висконсин, узнают, что у них есть шанс вернуться в рай. Для этого нужно всего лишь воспользоваться католической догмой, провозглашенной кардиналом-новатором: каждый прошедший через освященную арку в соборе в Нью-Джерси получает отпущение грехов.',
    1,
    1
where
    not exists(select 1 from VIDEO_METADATA v where v.id = 9);


/*Привязка съемочных групп к фильмам*/
insert into video_metadata_crew(
    video_metadata_id,
    crew_classifier_id,
    crew_role_classifier_id)
values
    (1,1,1),
    (1,2,1),
    (1,3,1),
    (1,4,1),
    (1,5,1),
    (1,8,2);

insert into video_metadata_crew(
    video_metadata_id,
    crew_classifier_id,
    crew_role_classifier_id)
values
    (1,1,1),
    (1,2,1),
    (1,3,1),
    (1,4,1),
    (1,5,1),
    (1,8,2);

insert into video_metadata_crew(
    video_metadata_id,
    crew_classifier_id,
    crew_role_classifier_id)
values
    (2,1,1),
    (2,2,1),
    (2,3,1),
    (2,4,1),
    (2,5,1),
    (2,6,1),
    (2,9,2);

insert into video_metadata_crew(
    video_metadata_id,
    crew_classifier_id,
    crew_role_classifier_id)
values
    (3,1,1),
    (3,2,1),
    (3,3,1),
    (3,5,1),
    (3,10,2);

insert into video_metadata_crew(
    video_metadata_id,
    crew_classifier_id,
    crew_role_classifier_id)
values
    (4,1,1),
    (4,2,1),
    (4,3,1),
    (4,5,1),
    (4,10,2);


insert into video_metadata_crew(
    video_metadata_id,
    crew_classifier_id,
    crew_role_classifier_id)
values
    (5,1,1),
    (5,2,1),
    (5,3,1),
    (5,5,1),
    (5,11,2);

insert into video_metadata_crew(
    video_metadata_id,
    crew_classifier_id,
    crew_role_classifier_id)
values
    (6,1,1),
    (6,2,1),
    (6,3,1),
    (6,5,1),
    (6,7,1),
    (6,11,2);

insert into video_metadata_crew(
    video_metadata_id,
    crew_classifier_id,
    crew_role_classifier_id)
values
    (7,1,1),
    (7,2,1),
    (7,3,1),
    (7,5,1),
    (7,7,1),
    (7,11,2);

insert into video_metadata_crew(
    video_metadata_id,
    crew_classifier_id,
    crew_role_classifier_id)
values
    (8,1,1),
    (8,2,1),
    (8,3,1),
    (8,5,1),
    (8,7,1),
    (8,11,2);


insert into video_metadata_crew(
    video_metadata_id,
    crew_classifier_id,
    crew_role_classifier_id)
values
    (9,5,1),
    (9,12,1),
    (9,13,1),
    (9,14,1),
    (9,15,1),
    (9,16,1),
    (9,16,2);

/*Жанры фильмов*/
insert into video_metadata_genre(
    video_metadata_id,
    genre_classifier_id
)
values
    (1,1),(1,2),(1,3),
    (2,1),(2,2),(2,3),
    (3,1),(3,2),(3,3),
    (4,1),(4,2),(4,3),
    (5,1),(5,2),(5,3),
    (6,1),(6,2),(6,3),
    (7,1),(7,2),(7,3),
    (8,1),(8,2),(8,3),

    (9,1),(9,3),(9,4),(9,5);

/*Страны производства фильмов*/
insert into video_metadata_country(
    video_metadata_id,
    country_classifier_id)
values
    (1,1),(1,2),
    (2,1),(2,2),
    (3,1),(3,2),
    (4,1),(4,2),
    (5,1),(5,2),
    (6,1),(6,2),
    (7,1),(7,2),
    (8,1),(8,2),
    (9,1);