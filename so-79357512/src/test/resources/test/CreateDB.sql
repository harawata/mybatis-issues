drop table if exists users;

create table users (
  id serial,
  name varchar(20),
  disabled boolean
);

