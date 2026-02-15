create table tb_roles (
    id BINARY(16) not null primary key,
    name varchar(20) not null unique,
    enabled boolean not null default true,
    created_at timestamp not null default current_timestamp,
    updated_at timestamp not null default current_timestamp on update current_timestamp
);