create table user (
    id bigint primary key auto_increment,
    name varchar(255) unique not null,
    email varchar(255) unique not null,
    password varchar(255) not null,
    birth_date date not null,
    created_at timestamp not null,
    updated_at timestamp not null,
    active boolean default false
);