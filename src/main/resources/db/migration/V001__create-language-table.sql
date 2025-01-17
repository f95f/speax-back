create table language (
    id bigint primary key,
    name varchar(255) unique not null,
    active boolean default true
);