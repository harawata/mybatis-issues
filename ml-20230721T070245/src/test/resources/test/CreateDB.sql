drop table users if exists;
drop table pets if exists;

create table users (
  id int,
  name varchar(20)
);

create table pets (
  id int,
  user_id int,
  name varchar(20)
);

insert into users (id, name) values
(1, 'User1'), (2, 'User2');

insert into pets (id, user_id, name) values
(1, 1, 'Pet1'), (2, 2, 'Pet2');
