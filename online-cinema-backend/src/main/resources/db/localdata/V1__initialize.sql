CREATE TABLE video_metadata(
    id bigserial primary key,
    file_name varchar(255) not null,
    content_type varchar(20) not null,
    description varchar(10240),
    file_size bigserial not null,
    video_length bigserial
);


