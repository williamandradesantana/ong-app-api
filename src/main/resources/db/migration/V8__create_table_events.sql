create table tb_events (
  id binary(16) primary key not null,

  name varchar(200) not null,
  description varchar(500) not null,
  event_date timestamp not null,
  location varchar(150) not null,

  project_id binary(16) not null,

  enabled boolean not null default true,
  created_at timestamp not null default current_timestamp,
  updated_at timestamp not null default current_timestamp on update current_timestamp,

  constraint fk_event_project_id foreign key (project_id) references tb_projects(id)
);