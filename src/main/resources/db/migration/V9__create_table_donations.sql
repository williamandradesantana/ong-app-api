create table tb_donations (
      id binary(16) primary key not null,

      amount decimal(18, 2) not null,
      donation_date timestamp not null,
      donation_status varchar(20) not null,
      payment_method varchar(20) not null,

      project_id binary(16) not null,
      user_id binary(16) not null,

      enabled boolean not null default true,
      created_at timestamp not null default current_timestamp,
      updated_at timestamp not null default current_timestamp on update current_timestamp,

      constraint fk_donation_project_id foreign key (project_id) references tb_projects(id),

      constraint fk_donation_user_id foreign key (user_id) references tb_users(id)
);