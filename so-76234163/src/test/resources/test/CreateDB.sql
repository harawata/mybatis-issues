drop table if exists users;

create table users (
  id int,
  name varchar(20),
  role bit(2)
);

insert into users (id, name, role) values
(1, 'User1', B'10');
