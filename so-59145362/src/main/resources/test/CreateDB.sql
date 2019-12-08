DROP SCHEMA IF EXISTS mbtest;

CREATE SCHEMA mbtest;

create table mbtest.users (
  id serial primary key,
  name varchar(20)
);
