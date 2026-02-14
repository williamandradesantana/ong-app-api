create table tb_users(
     id BINARY(16) not null primary key,
     username varchar(150) not null unique,
     email varchar(200) not null unique,
     password varchar(255) not null,
     enabled boolean not null default true,
     created_at timestamp not null default current_timestamp,
     updated_at timestamp not null default current_timestamp on update current_timestamp
);