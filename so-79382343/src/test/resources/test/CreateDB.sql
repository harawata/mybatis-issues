drop table if exists users;

create table users (
  id int,
  name varchar(20)
);

insert into users (id, name) values
(1, 'User1');
