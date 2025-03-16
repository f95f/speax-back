create table chat (
    id bigint primary key auto_increment,
    inviter_id bigint not null,
    invitee_id bigint not null,
    created_at  timestamp not null,
    updated_at timestamp not null,
    active boolean default true
);