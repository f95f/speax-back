create table message (
    id bigint primary key auto_increment,
    chat_id bigint not null,
    sender_id bigint not null,
    content text not null,
    translated_content text,
    created_at timestamp not null,
    updated_at timestamp not null
);