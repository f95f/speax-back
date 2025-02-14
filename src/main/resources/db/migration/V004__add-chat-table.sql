create table chat (
    id bigint primary key auto_increment,
    inviter_id not null,
    invitee_id not null,
    created_at timestamp not null,
    updated_at timestamp not null,
    active boolean default true
);