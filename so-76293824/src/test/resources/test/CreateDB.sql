drop table if exists users;

create table users (
  id int,
  name varchar(20),
  mac macaddr

);

insert into users (id, name, mac) values
(1, 'User1', '08:00:2b:01:02:03');
