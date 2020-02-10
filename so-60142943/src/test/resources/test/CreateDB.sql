drop table if exists users;

create table users (
  id int,
  data jsonb
);

insert into users (id, data) values
(1, '{"a":1, "b":2}'),
(1, '{"a":1, "c":3}');
