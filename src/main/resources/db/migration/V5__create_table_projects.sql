create table tb_projects (
    id BINARY(16) not null primary key,

    name varchar(150) not null unique,
    description varchar(500) not null,

    goal_amount decimal(18, 2) not null,
    current_amount decimal(18, 2) not null default 0.00,

    start_date timestamp not null,
    end_date timestamp not null,

    project_status varchar(20) not null,

    enabled boolean not null default true,
    created_at timestamp not null default current_timestamp,
    updated_at timestamp not null default current_timestamp on update current_timestamp
);