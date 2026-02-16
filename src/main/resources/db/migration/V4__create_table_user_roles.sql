create table tb_user_roles(
    id binary(16) not null primary key,
    user_id binary(16) not null,
    role_id binary(16) not null,
    enabled boolean not null default true,
    created_at timestamp not null default current_timestamp,
    updated_at timestamp not null default current_timestamp on update current_timestamp,

    constraint fk_user_roles_user foreign key (user_id) references tb_users(id),
    constraint fk_user_roles_role foreign key (role_id) references tb_roles(id),
    constraint uk_user_roles unique (user_id, role_id)
);