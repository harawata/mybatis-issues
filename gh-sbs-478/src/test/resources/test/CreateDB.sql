drop table users if exists;

create table users (
  id int primary key,
  name varchar(20)
);

insert into users (id, name) values
(3, 'User3');
